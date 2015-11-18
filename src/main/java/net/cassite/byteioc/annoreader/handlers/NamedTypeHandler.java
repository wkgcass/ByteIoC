package net.cassite.byteioc.annoreader.handlers;

import javassist.CtClass;
import net.cassite.byteioc.annoreader.Helper;
import net.cassite.byteioc.annoreader.TypeAnnotationHandler;
import net.cassite.byteioc.annoreader.TypeChainHandler;
import net.cassite.byteioc.annoreader.annotations.Named;

import java.lang.annotation.Annotation;
import java.util.Collection;

/**
 * type annotation handler for @Named
 */
public class NamedTypeHandler implements TypeAnnotationHandler {

        @Override
        public String handle(String name, CtClass aClass, Collection<Annotation> annotations, TypeChainHandler chain, Helper helper) throws Exception {
                String nameFromChain = chain.handle(name, aClass, annotations, chain, helper);
                if (Helper.resultChanged(name, nameFromChain)) {
                        return nameFromChain;
                }
                Named named = Helper.getAnnotation(Named.class, annotations);
                assert named != null;
                return named.value();
        }

        @Override
        public boolean canHandle(Collection<Annotation> annotations) {
                return Helper.containsAnnotation(Named.class, annotations);
        }
}
