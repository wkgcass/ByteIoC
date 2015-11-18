package net.cassite.byteioc.annoreader.handlers;

import javassist.CtMethod;
import net.cassite.byteioc.annoreader.Helper;
import net.cassite.byteioc.annoreader.MethodAnnotationHandler;
import net.cassite.byteioc.annoreader.MethodChainHandler;
import net.cassite.byteioc.annoreader.annotations.AutoWire;

import java.lang.annotation.Annotation;
import java.util.Collection;

/**
 * handles @AutoWire
 *
 * @see net.cassite.byteioc.annoreader.annotations.AutoWire
 */
public class AutoWireMethodHandler implements MethodAnnotationHandler {
        @Override
        public String[] handle(String[] args, CtMethod method, Collection<Annotation> annotations, MethodChainHandler chain, Helper helper) throws Exception {
                // do wiring
                return args;
        }

        @Override
        public boolean canHandle(Collection<Annotation> annotations) {
                return Helper.containsAnnotation(AutoWire.class, annotations);
        }
}
