package net.cassite.byteioc.annoreader.handlers;

import javassist.CtClass;
import javassist.CtField;
import net.cassite.byteioc.annoreader.FieldAnnotationHandler;
import net.cassite.byteioc.annoreader.FieldChainHandler;
import net.cassite.byteioc.annoreader.Helper;
import net.cassite.byteioc.annoreader.annotations.Use;

import java.lang.annotation.Annotation;
import java.util.Collection;

/**
 * handler for annotation @Use
 */
public class UseFieldHandler implements FieldAnnotationHandler {
        @Override
        public String handle(String arg, CtField field, Collection<Annotation> annotations, FieldChainHandler chain, Helper helper) throws Exception {
                String argFromChain = chain.handle(arg, field, annotations, chain, helper);
                if (Helper.resultChanged(arg, argFromChain)) {
                        return argFromChain;
                }
                Use use = Helper.getAnnotation(Use.class, annotations);
                assert use != null;
                if (!"".equals(use.value())) {
                        return use.value();
                } else if (!"net.cassite.byteioc.annoreader.annotations.Use".equals(use.target())) {
                        return helper.getInstanceNameByClass(use.target());
                } else if (!"*(FE#89yF#($Yn98v".equals(use.val())) {
                        CtClass paramCls = field.getType();
                        return helper.getPrimitive(paramCls, use.val());
                }
                return argFromChain;
        }

        @Override
        public boolean canHandle(Collection<Annotation> annotations) {
                return Helper.containsAnnotation(Use.class, annotations);
        }
}
