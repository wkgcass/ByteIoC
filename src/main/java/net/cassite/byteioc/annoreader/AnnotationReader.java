package net.cassite.byteioc.annoreader;

import javassist.*;
import net.cassite.byteioc.DependencyReader;
import net.cassite.byteioc.bytecode.ClassPoolProvider;
import net.cassite.byteioc.dependencies.*;
import net.cassite.byteioc.exceptions.ReadingException;

import java.lang.annotation.Annotation;
import java.util.*;

/**
 * It's an implementation of DependencyReader, which reads the annotations presented on classes/fields/methods/constructors/parameters
 */
public class AnnotationReader implements DependencyReader {
        final List<ConstructorAnnotationHandler> constructorAnnotationHandlers = new ArrayList<ConstructorAnnotationHandler>();
        final List<FieldAnnotationHandler> fieldAnnotationHandlers = new ArrayList<FieldAnnotationHandler>();
        final List<MethodAnnotationHandler> methodAnnotationHandlers = new ArrayList<MethodAnnotationHandler>();
        final List<ParamAnnotationHandler> paramAnnotationHandlers = new ArrayList<ParamAnnotationHandler>();
        final List<TypeAnnotationHandler> typeAnnotationHandlers = new ArrayList<TypeAnnotationHandler>();
        final List<String> classes = new ArrayList<String>();
        final ClassPool classPool;

        public AnnotationReader(ClassPoolProvider provider) {
                this.classPool = provider.getClassPool();
        }

        String[] handleConstructorArgs(CtConstructor con, Helper helper) throws Exception {
                int length = con.getParameterTypes().length;
                String[] args = new String[length];
                for (int i = 0; i < length; ++i) {
                        args[i] = handleConstructorParam(con, i, helper);
                }
                return args;
        }

        @Override
        public void joinDependencies(Dependencies dependencies) throws Exception {
                Helper helper = new Helper(this, dependencies);
                for (String cls : classes) {
                        CtClass ctClass = classPool.get(cls);

                        CtConstructor[] constructors = ctClass.getConstructors();
                        for (CtConstructor con : constructors) {
                                int length = con.getParameterTypes().length;
                                String[] args = handleConstructorArgs(con, helper);
                                String nameC = handleConstructor(args, con, helper);
                                if (nameC == null && constructors.length != 1) {
                                        throw new ReadingException("Cannot find constructor " + con.getLongName() + " name");
                                } else if (nameC != null) {
                                        BClass[] bClasses = new BClass[length];
                                        CtClass[] ctClasses = con.getParameterTypes();
                                        for (int i = 0; i < length; ++i) {
                                                bClasses[i] = new BClass(ctClasses[i].getName());
                                        }
                                        ConstructorInfo info = new ConstructorInfo(new BClass(cls), new BConstructor(bClasses), args);
                                        dependencies.addConstructorInfo(nameC, info);
                                }
                        }

                        handleType(ctClass, helper);

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
                TypeChainHandler chain = new TypeChainHandler(typeAnnotationHandlers);
                return chain.handle(null, ctClass, annoList, chain, helper);
        }

        String handleMethodParam(CtMethod member, int index, Helper helper) throws Exception {
                Annotation[] annotations = (Annotation[]) member.getParameterAnnotations()[index];
                List<Annotation> annoList = Arrays.asList(annotations);
                ParamChainHandler chain = new ParamChainHandler(paramAnnotationHandlers);
                return chain.handleMethod(null, index, member, annoList, chain, helper);
        }

        String handleConstructorParam(CtConstructor member, int index, Helper helper) throws Exception {
                Annotation[] annotations = (Annotation[]) member.getParameterAnnotations()[index];
                List<Annotation> annoList = Arrays.asList(annotations);
                ParamChainHandler chain = new ParamChainHandler(paramAnnotationHandlers);
                return chain.handleConstructor(null, index, member, annoList, chain, helper);
        }

        String handleConstructor(String[] args, CtConstructor constructor, Helper helper) throws Exception {
                Annotation[] annotations = (Annotation[]) constructor.getAnnotations();
                List<Annotation> annoList = Arrays.asList(annotations);
                ConstructorChainHandler chain = new ConstructorChainHandler(constructorAnnotationHandlers);
                return chain.handle(null, args, constructor, annoList, chain, helper);
        }

        String[] handleMethod(String[] args, CtMethod method, Helper helper) throws Exception {
                Annotation[] annotations = (Annotation[]) method.getAnnotations();
                List<Annotation> annoList = Arrays.asList(annotations);
                MethodChainHandler chain = new MethodChainHandler(methodAnnotationHandlers);
                return chain.handle(args, method, annoList, chain, helper);
        }

        String handleField(CtField field, Helper helper) throws Exception {
                Annotation[] annotations = (Annotation[]) field.getAnnotations();
                List<Annotation> annoList = Arrays.asList(annotations);
                FieldChainHandler chain = new FieldChainHandler(fieldAnnotationHandlers);
                return chain.handle(null, field, annoList, chain, helper);
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
