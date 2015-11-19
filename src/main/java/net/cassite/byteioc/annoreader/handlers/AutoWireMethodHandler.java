package net.cassite.byteioc.annoreader.handlers;

import javassist.CtMethod;
import net.cassite.byteioc.annoreader.Helper;
import net.cassite.byteioc.annoreader.MethodAnnotationHandler;
import net.cassite.byteioc.annoreader.MethodChainHandler;
import net.cassite.byteioc.annoreader.annotations.AutoWire;
import net.cassite.byteioc.annoreader.annotations.WireType;

import java.lang.annotation.Annotation;
import java.util.Collection;

/**
 * handles @AutoWire
 *
 * @see net.cassite.byteioc.annoreader.annotations.AutoWire
 */
public class AutoWireMethodHandler implements MethodAnnotationHandler {
        private String[] doChain(String[] args, CtMethod method, Collection<Annotation> annotations, MethodChainHandler chain, Helper helper) throws Exception {
                return chain.handle(args, method, annotations, chain, helper);
        }

        @Override
        public String[] handle(String[] args, CtMethod method, Collection<Annotation> annotations, MethodChainHandler chain, Helper helper) throws Exception {
                if (Helper.containsAnnotation(AutoWire.class, annotations)) {
                        return doChain(args, method, annotations, chain, helper);
                } else {
                        if (AutoWireTypeHandler.getClassesToWire().containsKey(method.getDeclaringClass())) {
                                WireType type = AutoWireTypeHandler.getClassesToWire().get(method.getDeclaringClass());
                                switch (type) {
                                        case ALL:
                                        case METHODS:
                                                return doChain(args, method, annotations, chain, helper);
                                        case SETTERS: {
                                                if (Helper.isSetter(method)) {
                                                        return doChain(args, method, annotations, chain, helper);
                                                } else {
                                                        return null;
                                                }
                                        }
                                }
                                return null;
                        } else {
                                if (Helper.isSetter(method)) {
                                        return doChain(args, method, annotations, chain, helper);
                                } else {
                                        return null;
                                }
                        }
                }
        }

        @Override
        public boolean canHandle(Collection<Annotation> annotations) {
                return true;
        }
}
