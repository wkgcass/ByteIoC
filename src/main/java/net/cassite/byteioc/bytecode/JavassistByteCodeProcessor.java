package net.cassite.byteioc.bytecode;

import javassist.*;
import net.cassite.byteioc.ByteCodeProcessor;
import net.cassite.byteioc.dependencies.*;
import net.cassite.byteioc.exceptions.NameNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * process byte code with Javassist
 */
public class JavassistByteCodeProcessor implements ByteCodeProcessor {
        private static final Logger LOGGER = LoggerFactory.getLogger(JavassistByteCodeProcessor.class);

        private final ClassPool classPool;
        private static final String separator = "$";
        private static final String generateNamePostfix = "generatedByPureIoC";
        private static final String initMethodName = "initMethod" + separator + generateNamePostfix;
        private static final String initSingletonMethodName = "initSingletonMethod" + separator + generateNamePostfix;
        private static final String singletonFieldName = "singletonField" + separator + generateNamePostfix;

        private Set<BClass> generatedEmptyInitMethod = new HashSet<BClass>();
        private Set<BClass> finishedByteCodeGeneration = new HashSet<BClass>();

        public JavassistByteCodeProcessor(ClassPoolProvider classPoolProvider) {
                this.classPool = classPoolProvider.getClassPool();
        }

        /**
         * simulate <b>new</b> process
         *
         * @param constructorName constructor identified name
         * @param dependencies    dependency context
         * @return a string looks like new C(...,...,...); , <b>the end `;` NOT included</b>
         */
        public String instantiateClass(String constructorName, Dependencies dependencies) throws Exception {
                StringBuilder sb = new StringBuilder();
                sb.append("new ").append(dependencies.getConstructorInfoMap().get(constructorName).getCls().getClassName()).append("(");
                boolean isFirst = true;
                for (String arg : dependencies.getConstructorInfoMap().get(constructorName).getArgs()) {
                        if (isFirst) {
                                isFirst = false;
                        } else {
                                sb.append(",");
                        }
                        sb.append(generateArg(arg, dependencies));
                }
                sb.append(")");
                return sb.toString();
        }

        /**
         * simulate a singleton retrieve process
         *
         * @param singletonCls class of the singleton
         * @param dependencies dependency tree
         * @return a string looks like S.initSingletonMethod(); (the end `;` NOT included)
         */
        public String retrieveSingleton(BClass singletonCls, Dependencies dependencies) throws Exception {
                processFieldMethod(singletonCls, dependencies);
                return singletonCls.getClassName() + "." + initSingletonMethodName + "()";
        }

        public String generatePrimitive(PrimitiveInfo info) {
                if (info.isBool()) {
                        return info.get().toString();
                } else if (info.isByte()) {
                        return "(byte)" + info.get().toString();
                } else if (info.isChar()) {
                        return "'" + info.get().toString() + "'";
                } else if (info.isDouble()) {
                        return info.get().toString();
                } else if (info.isFloat()) {
                        return info.get().toString() + "f";
                } else if (info.isInt()) {
                        return info.get().toString();
                } else if (info.isLong()) {
                        return info.get().toString() + "L";
                } else if (info.isShort()) {
                        return "(short)" + info.get().toString();
                } else if (info.isString()) {
                        return "\"" + info.get().toString() + "\"";
                } else {
                        throw new RuntimeException();
                }
        }

        /**
         * generate argument
         *
         * @param name         name
         * @param dependencies dependency context
         * @return a string looks like new N(xxx,xxx,...) or S.initSingletonMethod() (only a expression returns an instance, without the end `;`)
         */
        public String generateArg(String name, Dependencies dependencies) throws Exception {
                if (dependencies.getConstructorInfoMap().containsKey(name)) {
                        ConstructorInfo info = dependencies.getConstructorInfoMap().get(name);
                        if (dependencies.getSingletonClasses().containsKey(info.getCls())) {
                                // is singleton
                                return retrieveSingleton(info.getCls(), dependencies);
                        } else {
                                // not singleton
                                return instantiateClass(name, dependencies);
                        }
                } else if (dependencies.getPrimitiveInfoMap().containsKey(name)) {
                        // primitive
                        PrimitiveInfo info = dependencies.getPrimitiveInfoMap().get(name);
                        return generatePrimitive(info);
                } else if (dependencies.getGeneratorMap().containsKey(name)) {
                        // generator
                        return "((Generator)" + generateArg(dependencies.getGeneratorMap().get(name), dependencies) + ").generate()";
                } else {
                        throw new NameNotFoundException();
                }
        }

        /**
         * generate init method to fill fields and invoke methods<br>
         * First it will check whether the class has already been modified (check <b>alreadyEnhanced</b> field existence)<br>
         * if true the process ends, otherwise go on<br>
         * Second it generates an empty method void initMethod(), it will be filled later<br>
         * Third it add the generated method to class, and insert invocation of the method to the beginning of all constructors.<br>
         * Fourth it will check whether the class is a singleton. If true, it will find corresponding constructor and generate static field and method<br>
         * using DCL to provide a singleton object. And set all constructors to private<br>
         * The empty method won't be filled until later process in JavassistByteCodeProcessor#fillInitMethod(BClass, Dependencies)
         *
         * @param cls          name of the class
         * @param dependencies dependency context
         * @throws Exception passable exceptions
         * @see JavassistByteCodeProcessor#fillInitMethod(BClass, Dependencies)
         */
        public void processFieldMethod(BClass cls, Dependencies dependencies) throws Exception {
                if (this.generatedEmptyInitMethod.contains(cls)) {
                        return;
                }
                CtClass ctClass = classPool.get(cls.getClassName());
                try {
                        ctClass.getField(alreadyEnhanced);
                } catch (NotFoundException e) {
                        // empty initMethod
                        CtMethod initMethod = new CtMethod(CtClass.voidType, initMethodName, null, ctClass);
                        initMethod.setBody("{}");
                        ctClass.addMethod(initMethod);
                        CtConstructor[] constructors = ctClass.getConstructors();
                        for (CtConstructor cons : constructors) {
                                cons.insertBeforeBody("{" + initMethod.getName() + "();}");
                        }
                        if (dependencies.getSingletonClasses().containsKey(cls)) {
                                // generate static field and method
                                CtField singletonField = new CtField(ctClass, singletonFieldName, ctClass);
                                singletonField.setModifiers(Modifier.STATIC);
                                ctClass.addField(singletonField);

                                CtMethod method = new CtMethod(ctClass, initSingletonMethodName, null, ctClass);
                                method.setModifiers(Modifier.STATIC);
                                String generated = "{" +
                                        "if(" + singletonFieldName + "==null){" +
                                        // DCL
                                        "synchronized(" + cls.getClassName() + ".class){" +
                                        "if(" + singletonFieldName + "==null){" +
                                        singletonFieldName + "=" + instantiateClass(dependencies.getSingletonClasses().get(cls), dependencies) + ";" +
                                        "}}}" +
                                        "return " + singletonFieldName + ";" +
                                        "}";
                                LOGGER.debug("javassist compiled code for singleton {} is - {}", cls.getClassName(), generated);
                                method.setBody(generated);
                                ctClass.addMethod(method);
                                // set constructors to private
                                for (CtConstructor cons : constructors) {
                                        cons.setModifiers(Modifier.PRIVATE);
                                }
                        }
                }
                this.generatedEmptyInitMethod.add(cls);
        }

        /**
         * set field value
         *
         * @param fieldName    name of the field
         * @param value        value to set
         * @param dependencies dependency context
         * @return a string looks like this.F = xxx; (a full field assignment, the end `;` included)
         */
        public String fillField(String fieldName, String value, Dependencies dependencies) throws Exception {
                return "this." + fieldName + "=" + generateArg(value, dependencies) + ";";
        }

        /**
         * invoke method
         *
         * @param method       info about the method
         * @param args         args to invoke
         * @param dependencies dependency context
         * @return a string looks like this.M(xxx,xxx,...); (a full invocation, the end `;` included)
         */
        public String fillMethod(BMethod method, String[] args, Dependencies dependencies) throws Exception {
                StringBuilder sb = new StringBuilder();
                sb.append("this.").append(method.methodName).append("(");
                boolean isFirst = true;
                for (String arg : args) {
                        if (isFirst) {
                                isFirst = false;
                        } else {
                                sb.append(",");
                        }
                        sb.append(generateArg(arg, dependencies));
                }
                sb.append(");");
                return sb.toString();
        }

        /**
         * fill initMethod
         *
         * @param cls          name of the class
         * @param dependencies dependency context
         * @throws Exception passable exceptions
         */
        public void fillInitMethod(BClass cls, Dependencies dependencies) throws Exception {
                if (finishedByteCodeGeneration.contains(cls)) {
                        return;
                }
                CtClass ctClass = classPool.get(cls.getClassName());
                try {
                        ctClass.getField(alreadyEnhanced);
                } catch (NotFoundException e) {
                        // initMethod
                        CtMethod initMethod = ctClass.getDeclaredMethod(initMethodName, null);

                        StringBuilder toInsert = new StringBuilder("{");
                        FieldMethodInfo info = dependencies.getFieldMethodInfoMap().get(cls);
                        // fields
                        for (BField field : info.getFieldArgs().keySet()) {
                                toInsert.append(fillField(field.getFieldName(), info.getFieldArgs().get(field), dependencies));
                        }
                        // methods
                        for (BMethod method : info.getMethodArgs().keySet()) {
                                toInsert.append(fillMethod(method, info.getMethodArgs().get(method), dependencies));
                        }
                        toInsert.append("}");
                        LOGGER.debug("javassist compiled code for {} is - {}", cls.getClassName(), toInsert);
                        initMethod.insertAfter(toInsert.toString());

                        // alreadyEnhanced flagField
                        CtField alreadyEnhancedField = new CtField(classPool.get("java.lang.Object"), alreadyEnhanced, ctClass);
                        alreadyEnhancedField.setModifiers(Modifier.STATIC);
                        ctClass.addField(alreadyEnhancedField);
                }
                finishedByteCodeGeneration.add(cls);
                ctClass.toClass();
        }

        @Override
        public void process(Dependencies dependencies) throws Exception {
                Map<BClass, FieldMethodInfo> fieldMethodInfoMap = dependencies.getFieldMethodInfoMap();

                for (BClass cls : fieldMethodInfoMap.keySet()) {
                        processFieldMethod(cls, dependencies);
                }

                for (BClass cls : fieldMethodInfoMap.keySet()) {
                        fillInitMethod(cls, dependencies);
                }
        }
}
