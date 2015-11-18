package net.cassite.byteioc.annoreader;

import javassist.CtClass;

import java.lang.annotation.Annotation;
import java.util.Collection;
import java.util.Iterator;

/**
 * A chain containing handlers to be invoked
 */
public class TypeChainHandler implements TypeAnnotationHandler {
        private Iterator<TypeAnnotationHandler> typeAnnotationHandlers;

        public TypeChainHandler(Iterable<TypeAnnotationHandler> typeAnnotationHandlers) {
                this.typeAnnotationHandlers = typeAnnotationHandlers.iterator();
        }

        @Override
        public boolean canHandle(Collection<Annotation> annotations) {
                return false;
        }

        @Override
        public String handle(String name, CtClass aClass, Collection<Annotation> annotations, TypeChainHandler chain, Helper helper) throws Exception {
                if (typeAnnotationHandlers.hasNext()) {
                        return typeAnnotationHandlers.next().handle(name, aClass, annotations, chain, helper);
                } else {
                        return name;
                }
        }
}
