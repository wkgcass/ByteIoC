package net.cassite.byteioc.annoreader;

import javassist.*;
import net.cassite.byteioc.dependencies.Dependencies;

/**
 * helper for handlers
 */
public class Helper {
        private AnnotationReader reader;
        private Dependencies dependencies;

        Helper(AnnotationReader reader, Dependencies dependencies) {
                this.reader = reader;
                this.dependencies = dependencies;
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
}
