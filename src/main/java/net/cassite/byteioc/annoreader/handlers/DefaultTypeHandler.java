package net.cassite.byteioc.annoreader.handlers;

import javassist.CtClass;
import net.cassite.byteioc.annoreader.Helper;
import net.cassite.byteioc.annoreader.TypeAnnotationHandler;
import net.cassite.byteioc.annoreader.TypeChainHandler;
import net.cassite.byteioc.annoreader.annotations.Default;

import java.lang.annotation.Annotation;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

/**
 * handler for annotation @Default
 */
public class DefaultTypeHandler implements TypeAnnotationHandler {
        @Override
        public String handle(String name, CtClass aClass, Collection<Annotation> annotations, TypeChainHandler chain, Helper helper) throws Exception {
                String nameFromChain = chain.handle(name, aClass, annotations, chain, helper);
                if (Helper.resultChanged(name, nameFromChain)) {
                        return nameFromChain;
                }
                Default def = Helper.getAnnotation(Default.class, annotations);
                assert def != null;
                if (!"".equals(def.value())) {
                        return def.value();
                } else if (!"net.cassite.byteioc.annoreader.annotations.Default".equals(def.target())) {
                        return helper.getInstanceNameByClass(def.target());
                }
                return nameFromChain;
        }

        @Override
        public boolean canHandle(Collection<Annotation> annotations) {
                return Helper.containsAnnotation(Default.class, annotations);
        }
}
