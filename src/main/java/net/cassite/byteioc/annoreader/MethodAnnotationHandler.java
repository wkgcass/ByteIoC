package net.cassite.byteioc.annoreader;

import javassist.CtMethod;
import net.cassite.byteioc.dependencies.FieldMethodInfo;

import java.lang.annotation.Annotation;
import java.util.Collection;

/**
 * Handler of method annotations
 */
public interface MethodAnnotationHandler extends Handler {

        /**
         * do handling, and add proper info into FieldMethodInfo
         *
         * @param args        arguments to invoke the method
         * @param method      current handling method
         * @param annotations annotations presented on the method
         * @param helper      helper for handlers
         * @return null if don't want to invoke the method, or String array representing arguments
         * @throws Exception passable exceptions
         */
        String[] handle(String[] args, CtMethod method, Collection<Annotation> annotations, Helper helper) throws Exception;
}
