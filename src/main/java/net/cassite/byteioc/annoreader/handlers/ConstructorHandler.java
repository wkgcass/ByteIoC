package net.cassite.byteioc.annoreader.handlers;

import javassist.CtConstructor;
import net.cassite.byteioc.annoreader.ConstructorAnnotationHandler;
import net.cassite.byteioc.annoreader.ConstructorChainHandler;
import net.cassite.byteioc.annoreader.Helper;

import java.lang.annotation.Annotation;
import java.util.Collection;

/**
 * default handler for constructors
 */
public class ConstructorHandler implements ConstructorAnnotationHandler {
        @Override
        public String handle(String name, String[] args, CtConstructor constructor, Collection<Annotation> annotations, ConstructorChainHandler chain, Helper helper) throws Exception {
                String nameFromChain = chain.handle(name, args, constructor, annotations, chain, helper);
                if (Helper.resultChanged(name, nameFromChain)) {
                        return nameFromChain;
                }
                return helper.generateName();
        }

        @Override
        public boolean canHandle(Collection<Annotation> annotations) {
                return true;
        }
}
