package net.cassite.byteioc.annoreader;

import javassist.*;
import net.cassite.byteioc.DependencyReader;
import net.cassite.byteioc.bytecode.ClassPoolProvider;
import net.cassite.byteioc.dependencies.*;
import net.cassite.byteioc.exceptions.ReadingException;

import java.lang.annotation.Annotation;
import java.lang.reflect.Member;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * It's an implementation of DependencyReader, which reads the annotations presented on classes/fields/methods/constructors/parameters
 */
public class AnnotationReader implements DependencyReader {
        private final List<ConstructorAnnotationHandler> constructorAnnotationHandlers = new ArrayList<ConstructorAnnotationHandler>();
        private final List<FieldAnnotationHandler> fieldAnnotationHandlers = new ArrayList<FieldAnnotationHandler>();
        private final List<MethodAnnotationHandler> methodAnnotationHandlers = new ArrayList<MethodAnnotationHandler>();
        private final List<ParamAnnotationHandler> paramAnnotationHandlers = new ArrayList<ParamAnnotationHandler>();
        private final List<TypeAnnotationHandler> typeAnnotationHandlers = new ArrayList<TypeAnnotationHandler>();
        private final List<String> classes = new ArrayList<String>();
        final ClassPool classPool;

        public AnnotationReader(ClassPoolProvider provider) {
                this.classPool = provider.getClassPool();
        }

        @Override
        public void joinDependencies(Dependencies dependencies) throws Exception {
                Helper helper = new Helper(this, dependencies);
                for (String cls : classes) {
                        CtClass ctClass = classPool.get(cls);
                        String name = handleType(ctClass, helper);

                        CtConstructor[] constructors = ctClass.getConstructors();
                        for (CtConstructor con : constructors) {
                                int length = con.getParameterTypes().length;
                                String[] args = new String[length];
                                for (int i = 0; i < length; ++i) {
                                        args[i] = handleConstructorParam(con, i, helper);
                                }
                                String nameC = handleConstructor(args, con, helper);
                                if (nameC == null && constructors.length != 1 && name == null) {
                                        throw new ReadingException("Cannot find constructor " + con.getLongName() + " name");
                                } else {
                                        BClass[] bClasses = new BClass[length];
                                        CtClass[] ctClasses = con.getParameterTypes();
                                        for (int i = 0; i < length; ++i) {
                                                bClasses[i] = new BClass(ctClasses[i].getName());
                                        }
                                        ConstructorInfo info = new ConstructorInfo(new BClass(cls), new BConstructor(bClasses), args);
                                        dependencies.addConstructorInfo(nameC == null ? name : nameC, info);
                                }
                        }

                        FieldMethodInfo info = new FieldMethodInfo(new BClass(cls));
                        for (CtMethod method : ctClass.getDeclaredMethods()) {
                                int length = method.getParameterTypes().length;
                                String[] args = new String[length];
                                for (int i = 0; i < length; ++i) {
                                        args[i] = handleMethodParam(method, i, helper);
                                }
                                args = handleMethod(args, method, helper);
                                if (null != args) {
                                        BClass[] bClasses = new BClass[length];
                                        CtClass[] ctClasses = method.getParameterTypes();
                                        for (int i = 0; i < length; ++i) {
                                                bClasses[i] = new BClass(ctClasses[i].getName());
                                        }
                                        info.addMethodArgs(new BMethod(method.getName(), bClasses), args);
                                }
                        }
                        for (CtField field : ctClass.getDeclaredFields()) {
                                String arg = handleField(field, helper);
                                if (null != arg) {
                                        info.addFieldArg(new BField(field.getName(), new BClass(field.getType().getName())), arg);
                                }
                        }

                        dependencies.addFieldMethodInfo(info);
                }
        }

        String handleType(CtClass ctClass, Helper helper) throws Exception {
                Annotation[] annotations = (Annotation[]) ctClass.getAnnotations();
                List<Annotation> annoList = Arrays.asList(annotations);
                String toReturn = null;
                for (TypeAnnotationHandler h : typeAnnotationHandlers) {
                        if (h.canHandle(annoList)) {
                                toReturn = h.handle(toReturn, ctClass, annoList, helper);
                        }
                }
                return toReturn;
        }

        String handleMethodParam(CtMethod member, int index, Helper helper) throws Exception {
                Annotation[] annotations = (Annotation[]) member.getParameterAnnotations()[index];
                List<Annotation> annoList = Arrays.asList(annotations);
                String toReturn = null;
                for (ParamAnnotationHandler h : paramAnnotationHandlers) {
                        if (h.canHandle(annoList)) {
                                toReturn = h.handleMethod(toReturn, member, annoList, helper);
                        }
                }
                return toReturn;
        }

        String handleConstructorParam(CtConstructor member, int index, Helper helper) throws Exception {
                Annotation[] annotations = (Annotation[]) member.getParameterAnnotations()[index];
                List<Annotation> annoList = Arrays.asList(annotations);
                String toReturn = null;
                for (ParamAnnotationHandler h : paramAnnotationHandlers) {
                        if (h.canHandle(annoList)) {
                                toReturn = h.handleConstructor(toReturn, member, annoList, helper);
                        }
                }
                return toReturn;
        }

        String handleConstructor(String[] args, CtConstructor constructor, Helper helper) throws Exception {
                Annotation[] annotations = (Annotation[]) constructor.getAnnotations();
                List<Annotation> annoList = Arrays.asList(annotations);
                String toReturn = null;
                for (ConstructorAnnotationHandler h : constructorAnnotationHandlers) {
                        if (h.canHandle(annoList)) {
                                toReturn = h.handle(toReturn, args, constructor, annoList, helper);
                        }
                }
                return toReturn;
        }

        String[] handleMethod(String[] args, CtMethod method, Helper helper) throws Exception {
                Annotation[] annotations = (Annotation[]) method.getAnnotations();
                List<Annotation> annoList = Arrays.asList(annotations);
                for (MethodAnnotationHandler h : methodAnnotationHandlers) {
                        if (h.canHandle(annoList)) {
                                args = h.handle(args, method, annoList, helper);
                        }
                }
                return args;
        }

        String handleField(CtField field, Helper helper) throws Exception {
                Annotation[] annotations = (Annotation[]) field.getAnnotations();
                List<Annotation> annoList = Arrays.asList(annotations);
                String toReturn = null;
                for (FieldAnnotationHandler h : fieldAnnotationHandlers) {
                        if (h.canHandle(annoList)) {
                                toReturn = h.handle(toReturn, field, annoList, helper);
                        }
                }
                return toReturn;
        }

        public void setConstructorAnnotationHandlers(List<ConstructorAnnotationHandler> constructorAnnotationHandlers) {
                this.constructorAnnotationHandlers.addAll(constructorAnnotationHandlers);
        }

        public void setFieldAnnotationHandlers(List<FieldAnnotationHandler> fieldAnnotationHandlers) {
                this.fieldAnnotationHandlers.addAll(fieldAnnotationHandlers);
        }

        public void setMethodAnnotationHandlers(List<MethodAnnotationHandler> methodAnnotationHandlers) {
                this.methodAnnotationHandlers.addAll(methodAnnotationHandlers);
        }

        public void setParamAnnotationHandlers(List<ParamAnnotationHandler> paramAnnotationHandlers) {
                this.paramAnnotationHandlers.addAll(paramAnnotationHandlers);
        }

        public void setTypeAnnotationHandlers(List<TypeAnnotationHandler> typeAnnotationHandlers) {
                this.typeAnnotationHandlers.addAll(typeAnnotationHandlers);
        }

        public void setClasses(List<String> classes) {
                this.classes.addAll(classes);
        }
}
