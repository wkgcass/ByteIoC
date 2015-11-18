package net.cassite.byteioc.annoreader.handlers;

import javassist.CtField;
import net.cassite.byteioc.annoreader.FieldAnnotationHandler;
import net.cassite.byteioc.annoreader.FieldChainHandler;
import net.cassite.byteioc.annoreader.Helper;
import net.cassite.byteioc.annoreader.annotations.AutoWire;

import java.lang.annotation.Annotation;
import java.util.Collection;

/**
 * handler for annotation @AutoWire
 */
public class AutoWireFieldHandler implements FieldAnnotationHandler {
        @Override
        public String handle(String arg, CtField field, Collection<Annotation> annotations, FieldChainHandler chain, Helper helper) throws Exception {
                return chain.handle(arg, field, annotations, chain, helper);
        }

        @Override
        public boolean canHandle(Collection<Annotation> annotations) {
                return Helper.containsAnnotation(AutoWire.class, annotations);
        }
}
