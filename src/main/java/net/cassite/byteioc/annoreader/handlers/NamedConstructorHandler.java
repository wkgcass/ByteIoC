package net.cassite.byteioc.annoreader.handlers;

import javassist.CtConstructor;
import net.cassite.byteioc.annoreader.ConstructorAnnotationHandler;
import net.cassite.byteioc.annoreader.ConstructorChainHandler;
import net.cassite.byteioc.annoreader.Helper;
import net.cassite.byteioc.annoreader.annotations.Named;

import java.lang.annotation.Annotation;
import java.util.Collection;

/**
 * handler for annotation @Named
 */
public class NamedConstructorHandler implements ConstructorAnnotationHandler {

        @Override
        public String handle(String name, String[] args, CtConstructor constructor, Collection<Annotation> annotations, ConstructorChainHandler chain, Helper helper) throws Exception {
                String nameFromChain = chain.handle(name, args, constructor, annotations, chain, helper);
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
