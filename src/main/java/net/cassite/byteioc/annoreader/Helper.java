package net.cassite.byteioc.annoreader;

import javassist.*;
import net.cassite.byteioc.dependencies.Dependencies;
import net.cassite.byteioc.dependencies.PrimitiveInfo;

import java.lang.annotation.Annotation;
import java.util.*;

/**
 * helper for handlers
 */
public class Helper {
        private AnnotationReader reader;
        private Dependencies dependencies;

        private Map<CtClass, String> defaultConstructorsToUse = new HashMap<CtClass, String>();

        private Map<CtClass, >

        Helper(AnnotationReader reader, Dependencies dependencies) {
                this.reader = reader;
                this.dependencies = dependencies;
        }

        public Map<CtClass, String> getDefaultConstructorsToUse() {
                return defaultConstructorsToUse;
        }

        public ClassPool getClassPool() {
                return reader.classPool;
        }

        public String handleType(String className) throws Exception {
                return reader.handleType(getClassPool().get(className), this);
        }

        public String handleMethodParam(CtMethod member, int index) throws Exception {
                return reader.handleMethodParam(member, index, this);
        }

        public String handleConstructorParam(CtConstructor member, int index) throws Exception {
                return reader.handleConstructorParam(member, index, this);
        }

        public String handleConstructor(CtConstructor constructor) throws Exception {
                return handleConstructor(new String[constructor.getParameterTypes().length], constructor);
        }

        public String handleConstructor(String[] args, CtConstructor constructor) throws Exception {
                return reader.handleConstructor(args, constructor, this);
        }

        public String[] handleMethod(CtMethod method) throws Exception {
                return handleMethod(new String[method.getParameterTypes().length], method);
        }

        public String[] handleMethod(String[] args, CtMethod method) throws Exception {
                return reader.handleMethod(args, method, this);
        }

        public String handleField(CtField field) throws Exception {
                return reader.handleField(field, this);
        }

        public Dependencies getDependencies() {
                return dependencies;
        }

        public String getInstanceNameByClass(String className) {
                for (CtClass cls : getDefaultConstructorsToUse().keySet()) {
                        if (cls.getName().equals(className)) {
                                return getDefaultConstructorsToUse().get(cls);
                        }
                }
                // not mapped
                try {
                        CtClass cls = reader.classPool.get(className);
                        String name = reader.handleType(cls, this);
                        if (name != null) {
                                getDefaultConstructorsToUse().put(cls, name);
                                return name;
                        }
                        // name not found on type
                        if (cls.getConstructors().length == 1) {
                                CtConstructor con = cls.getConstructors()[0];
                                String[] args = reader.handleConstructorArgs(con, this);
                                String nameFromConstructor = reader.handleConstructor(args, con, this);
                                if (nameFromConstructor == null) {
                                        // name not found, then generate one
                                        nameFromConstructor = generateName();
                                }
                                getDefaultConstructorsToUse().put(cls, nameFromConstructor);
                                return nameFromConstructor;
                        } else {

                                throw new IllegalArgumentException("Class Has Multiple Constructors");
                        }
                } catch (Exception e) {
                        throw new RuntimeException(e);
                }
        }

        private String addPrimitive(PrimitiveInfo info) {
                String name = generateName();
                dependencies.addPrimitiveInfo(name, info);
                return name;
        }

        public String addPrimitive(String primitive) {
                PrimitiveInfo info = new PrimitiveInfo(primitive);
                return addPrimitive(info);
        }

        public String addPrimitive(int primitive) {
                PrimitiveInfo info = new PrimitiveInfo(primitive);
                return addPrimitive(info);
        }

        public String addPrimitive(byte primitive) {
                PrimitiveInfo info = new PrimitiveInfo(primitive);
                return addPrimitive(info);
        }

        public String addPrimitive(short primitive) {
                PrimitiveInfo info = new PrimitiveInfo(primitive);
                return addPrimitive(info);
        }

        public String addPrimitive(long primitive) {
                PrimitiveInfo info = new PrimitiveInfo(primitive);
                return addPrimitive(info);
        }

        public String addPrimitive(double primitive) {
                PrimitiveInfo info = new PrimitiveInfo(primitive);
                return addPrimitive(info);
        }

        public String addPrimitive(float primitive) {
                PrimitiveInfo info = new PrimitiveInfo(primitive);
                return addPrimitive(info);
        }

        public String addPrimitive(boolean primitive) {
                PrimitiveInfo info = new PrimitiveInfo(primitive);
                return addPrimitive(info);
        }

        public String addPrimitive(char primitive) {
                PrimitiveInfo info = new PrimitiveInfo(primitive);
                return addPrimitive(info);
        }

        public String generateName() {
                UUID uuid = UUID.randomUUID();
                return uuid.toString().replace("-", "");
        }

        public static boolean isSetter(CtMethod method) {
                String name = method.getName();
                try {
                        if (name.startsWith("set") && name.length() > 3 && name.charAt(3) >= 'A' && name.charAt(3) <= 'Z'
                                && (method.getReturnType() == CtClass.voidType || method.getReturnType() == method.getDeclaringClass())
                                && method.getParameterTypes().length == 1) {
                                return true;
                        } else {
                                return false;
                        }
                } catch (Exception e) {
                        throw new RuntimeException(e);
                }
        }

        public static boolean containsAnnotation(Class<? extends Annotation> annoClass, Collection<Annotation> annotations) {
                return null != getAnnotation(annoClass, annotations);
        }

        @SuppressWarnings("unchecked")
        public static <A extends Annotation> A getAnnotation(Class<A> annoClass, Collection<Annotation> annotations) {
                for (Annotation anno : annotations) {
                        if (annoClass.isInstance(anno)) {
                                return (A) anno;
                        }
                }
                return null;
        }

        public static boolean resultChanged(String name1, String name2) {
                return name1 == null ? name2 != null : !name1.equals(name2);
        }

        public static boolean resultChanged(String[] args1, String[] args2) {
                return args1 == null ? args2 != null : (args2 == null || !Arrays.equals(args1, args2));
        }

        public String getPrimitive(CtClass type, String val) {
                if (type.getName().equals("java.lang.Integer") || type.getName().equals("int")) {
                        return addPrimitive(Integer.parseInt(val));
                } else if (type.getName().equals("java.lang.Double") || type.getName().equals("double")) {
                        return addPrimitive(Double.parseDouble(val));
                } else if (type.getName().equals("java.lang.Float") || type.getName().equals("float")) {
                        return addPrimitive(Float.parseFloat(val));
                } else if (type.getName().equals("java.lang.Short") || type.getName().equals("short")) {
                        return addPrimitive(Short.parseShort(val));
                } else if (type.getName().equals("java.lang.Long") || type.getName().equals("long")) {
                        return addPrimitive(Long.parseLong(val));
                } else if (type.getName().equals("java.lang.Byte") || type.getName().equals("byte")) {
                        return addPrimitive(Byte.parseByte(val));
                } else if (type.getName().equals("java.lang.Boolean") || type.getName().equals("boolean")) {
                        return addPrimitive(Boolean.parseBoolean(val));
                } else if (type.getName().equals("java.lang.Character") || type.getName().equals("char")) {
                        return addPrimitive(val.charAt(0));
                } else {
                        return addPrimitive(val);
                }
        }

        public static String[] argsBackup(String[] args) {
                if (null == args) {
                        return null;
                } else {
                        return Arrays.copyOf(args, args.length);
                }
        }
}
