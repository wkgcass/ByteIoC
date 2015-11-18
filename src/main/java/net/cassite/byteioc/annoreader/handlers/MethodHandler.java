package net.cassite.byteioc.annoreader.handlers;

import javassist.CtMethod;
import net.cassite.byteioc.annoreader.Helper;
import net.cassite.byteioc.annoreader.MethodAnnotationHandler;
import net.cassite.byteioc.annoreader.MethodChainHandler;
import net.cassite.byteioc.annoreader.annotations.WireType;

import java.lang.annotation.Annotation;
import java.util.Collection;

/**
 * default method annotation handler
 */
public class MethodHandler implements MethodAnnotationHandler {

        @Override
        public String[] handle(String[] args, CtMethod method, Collection<Annotation> annotations, MethodChainHandler chain, Helper helper) throws Exception {
                String[] argsFromChain = chain.handle(args, method, annotations, chain, helper);
                if (AutoWireTypeHandler.getClassesToWire().containsKey(method.getDeclaringClass())) {
                        WireType type = AutoWireTypeHandler.getClassesToWire().get(method.getDeclaringClass());
                        switch (type) {
                                case ALL:
                                case METHODS:
                                        return argsFromChain;
                                case SETTERS: {
                                        if (Helper.isSetter(method)) {
                                                return argsFromChain;
                                        } else {
                                                return null;
                                        }
                                }
                        }
                        return null;
                } else {
                        if (Helper.isSetter(method)) {
                                return argsFromChain;
                        } else {
                                return null;
                        }
                }
        }

        @Override
        public boolean canHandle(Collection<Annotation> annotations) {
                return true;
        }

}
