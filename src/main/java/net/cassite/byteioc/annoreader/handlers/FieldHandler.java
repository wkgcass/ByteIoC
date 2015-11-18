package net.cassite.byteioc.annoreader.handlers;

import javassist.CtField;
import net.cassite.byteioc.annoreader.FieldAnnotationHandler;
import net.cassite.byteioc.annoreader.FieldChainHandler;
import net.cassite.byteioc.annoreader.Helper;
import net.cassite.byteioc.annoreader.annotations.WireType;

import java.lang.annotation.Annotation;
import java.util.Collection;

/**
 * default field handler
 */
public class FieldHandler implements FieldAnnotationHandler {
        @Override
        public String handle(String arg, CtField field, Collection<Annotation> annotations, FieldChainHandler chain, Helper helper) throws Exception {
                String argFromChain = chain.handle(arg, field, annotations, chain, helper);
                if (Helper.resultChanged(arg, argFromChain)) {
                        argFromChain = helper.getInstanceNameByClass(field.getType().getName());
                }
                if (AutoWireTypeHandler.getClassesToWire().containsKey(field.getDeclaringClass())) {
                        WireType type = AutoWireTypeHandler.getClassesToWire().get(field.getDeclaringClass());
                        switch (type) {
                                case ALL:
                                        return argFromChain;
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

        @Override
        public boolean canHandle(Collection<Annotation> annotations) {
                return true;
        }
}
