package net.cassite.byteioc.annoreader.handlers;

import javassist.CtField;
import net.cassite.byteioc.annoreader.FieldAnnotationHandler;
import net.cassite.byteioc.annoreader.FieldChainHandler;
import net.cassite.byteioc.annoreader.Helper;
import net.cassite.byteioc.annoreader.annotations.AutoWire;
import net.cassite.byteioc.annoreader.annotations.WireType;

import java.lang.annotation.Annotation;
import java.util.Collection;

/**
 * handler for annotation @AutoWire and defualt state without annotation(s)
 */
public class AutoWireFieldHandler implements FieldAnnotationHandler {
        private String doChain(String arg, CtField field, Collection<Annotation> annotations, FieldChainHandler chain, Helper helper) throws Exception {
                String argFromChain = chain.handle(arg, field, annotations, chain, helper);
                if (!Helper.resultChanged(arg, argFromChain) && arg == null) {
                        argFromChain = helper.getInstanceNameByClass(field.getType().getName());
                }
                return argFromChain;
        }

        @Override
        public String handle(String arg, CtField field, Collection<Annotation> annotations, FieldChainHandler chain, Helper helper) throws Exception {
                if (Helper.containsAnnotation(AutoWire.class, annotations)) {
                        return doChain(arg, field, annotations, chain, helper);
                } else {
                        if (AutoWireTypeHandler.getClassesToWire().containsKey(field.getDeclaringClass())) {
                                WireType type = AutoWireTypeHandler.getClassesToWire().get(field.getDeclaringClass());
                                switch (type) {
                                        case ALL:
                                        case FIELD:
                                                return doChain(arg, field, annotations, chain, helper);
                                        case METHODS:
                                        case SETTERS:
                                        case NONE:
                                        default:
                                                return null;
                                }
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
