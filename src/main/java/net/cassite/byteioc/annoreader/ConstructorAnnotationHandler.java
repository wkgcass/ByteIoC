package net.cassite.byteioc.annoreader;

import javassist.CtConstructor;

import java.lang.annotation.Annotation;
import java.util.Collection;

/**
 * Handler of constructor annotations
 */
public interface ConstructorAnnotationHandler extends Handler {

        /**
         * do handling, and generate constructor info<br>
         *
         * @param name        instance name
         * @param args        arguments to fill the constructor
         * @param constructor current handling constructor
         * @param annotations annotations presented on the constructor
         * @param helper      helper for handlers
         * @return name of the constructorInfo to be generated
         * @throws Exception passable exceptions
         */
        String handle(String name, String[] args, CtConstructor constructor, Collection<Annotation> annotations, Helper helper) throws Exception;
}
