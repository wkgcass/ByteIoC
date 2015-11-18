package net.cassite.byteioc.annoreader;

import javassist.CtClass;

import java.lang.annotation.Annotation;
import java.util.Collection;

/**
 * Handler of type annotations
 */
public interface TypeAnnotationHandler extends Handler {
        /**
         * do handling
         *
         * @param name        current chosen instance name
         * @param aClass      current handling type (class/interface)
         * @param annotations annotations on the type
         * @param chain       handler chain
         * @param helper      helper for handlers
         * @return chosen instance name
         * @throws Exception passable exceptions
         */
        String handle(String name, CtClass aClass, Collection<Annotation> annotations, TypeChainHandler chain, Helper helper) throws Exception;
}
