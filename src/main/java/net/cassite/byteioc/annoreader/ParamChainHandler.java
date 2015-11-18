package net.cassite.byteioc.annoreader;

import javassist.CtConstructor;
import javassist.CtMethod;

import java.lang.annotation.Annotation;
import java.util.Collection;
import java.util.Iterator;

/**
 * A chain containing handlers to be invoked
 */
public class ParamChainHandler implements ParamAnnotationHandler {
        private Iterator<ParamAnnotationHandler> paramAnnotationHandlers;

        public ParamChainHandler(Iterable<ParamAnnotationHandler> paramAnnotationHandlers) {
                this.paramAnnotationHandlers = paramAnnotationHandlers.iterator();
        }

        @Override
        public boolean canHandle(Collection<Annotation> annotations) {
                return false;
        }

        @Override
        public String handleConstructor(String name, int index, CtConstructor member, Collection<Annotation> annotations, ParamChainHandler chain, Helper helper) throws Exception {
                if (paramAnnotationHandlers.hasNext()) {
                        return paramAnnotationHandlers.next().handleConstructor(name, index, member, annotations, chain, helper);
                } else {
                        return name;
                }
        }

        @Override
        public String handleMethod(String name, int index, CtMethod member, Collection<Annotation> annotations, ParamChainHandler chain, Helper helper) throws Exception {
                if (paramAnnotationHandlers.hasNext()) {
                        return paramAnnotationHandlers.next().handleMethod(name, index, member, annotations, chain, helper);
                } else {
                        return name;
                }
        }
}
