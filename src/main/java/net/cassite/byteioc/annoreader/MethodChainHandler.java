package net.cassite.byteioc.annoreader;

import javassist.CtMethod;

import java.lang.annotation.Annotation;
import java.util.Collection;
import java.util.Iterator;

/**
 * A chain containing handlers to be invoked
 */
public class MethodChainHandler implements MethodAnnotationHandler {
        private Iterator<MethodAnnotationHandler> methodAnnotationHandlers;

        public MethodChainHandler(Iterable<MethodAnnotationHandler> methodAnnotationHandlers) {
                this.methodAnnotationHandlers = methodAnnotationHandlers.iterator();
        }

        @Override
        public boolean canHandle(Collection<Annotation> annotations) {
                return false;
        }

        @Override
        public String[] handle(String[] args, CtMethod method, Collection<Annotation> annotations, MethodChainHandler chain, Helper helper) throws Exception {
                if (methodAnnotationHandlers.hasNext()) {
                        return methodAnnotationHandlers.next().handle(args, method, annotations, chain, helper);
                } else {
                        return args;
                }
        }
}
