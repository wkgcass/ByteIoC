package net.cassite.byteioc.annoreader;

import javassist.CtConstructor;

import java.lang.annotation.Annotation;
import java.util.Collection;
import java.util.Iterator;

/**
 * A chain containing handlers to be invoked
 */
public class ConstructorChainHandler implements ConstructorAnnotationHandler {
        private Iterator<ConstructorAnnotationHandler> constructorAnnotationHandlers;

        public ConstructorChainHandler(Iterable<ConstructorAnnotationHandler> constructorAnnotationHandlers) {
                this.constructorAnnotationHandlers = constructorAnnotationHandlers.iterator();
        }


        @Override
        public String handle(String name, String[] args, CtConstructor constructor, Collection<Annotation> annotations, ConstructorChainHandler chain, Helper helper) throws Exception {
                if (constructorAnnotationHandlers.hasNext()) {
                        return constructorAnnotationHandlers.next().handle(name, args, constructor, annotations, chain, helper);
                } else {
                        return name;
                }
        }

        @Override
        public boolean canHandle(Collection<Annotation> annotations) {
                return false;
        }
}
