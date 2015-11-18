package net.cassite.byteioc.annoreader.handlers;

import javassist.CtConstructor;
import net.cassite.byteioc.annoreader.ConstructorAnnotationHandler;
import net.cassite.byteioc.annoreader.ConstructorChainHandler;
import net.cassite.byteioc.annoreader.Helper;
import net.cassite.byteioc.annoreader.annotations.Default;

import java.lang.annotation.Annotation;
import java.util.Collection;

/**
 * handler for annotation @Default
 */
public class DefaultConstructorHandler implements ConstructorAnnotationHandler {
        @Override
        public String handle(String name, String[] args, CtConstructor constructor, Collection<Annotation> annotations, ConstructorChainHandler chain, Helper helper) throws Exception {
                String nameFromChain = chain.handle(name, args, constructor, annotations, chain, helper);
                helper.getDefaultConstructorsToUse().put(constructor.getDeclaringClass(), nameFromChain);
                return nameFromChain;
        }

        @Override
        public boolean canHandle(Collection<Annotation> annotations) {
                return Helper.containsAnnotation(Default.class, annotations);
        }
}
