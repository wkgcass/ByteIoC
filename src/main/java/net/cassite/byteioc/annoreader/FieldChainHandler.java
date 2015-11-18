package net.cassite.byteioc.annoreader;

import javassist.CtConstructor;
import javassist.CtField;

import java.lang.annotation.Annotation;
import java.util.Collection;
import java.util.Iterator;

/**
 * A chain containing handlers to be invoked
 */
public class FieldChainHandler implements FieldAnnotationHandler {
        private Iterator<FieldAnnotationHandler> fieldAnnotationHandlers;

        public FieldChainHandler(Iterable<FieldAnnotationHandler> fieldAnnotationHandlers) {
                this.fieldAnnotationHandlers = fieldAnnotationHandlers.iterator();
        }

        @Override
        public boolean canHandle(Collection<Annotation> annotations) {
                return false;
        }

        @Override
        public String handle(String arg, CtField field, Collection<Annotation> annotations, FieldChainHandler chain, Helper helper) throws Exception {
                if (fieldAnnotationHandlers.hasNext()) {
                        return fieldAnnotationHandlers.next().handle(arg, field, annotations, chain, helper);
                }
                return arg;
        }
}
