package net.cassite.byteioc.annoreader;

import javassist.CtConstructor;
import javassist.CtMethod;

import java.lang.annotation.Annotation;
import java.util.Collection;

/**
 * Handler of parameter annotations
 */
public interface ParamAnnotationHandler extends Handler {
        /**
         * @param name        instance name
         * @param member      the member currently handling
         * @param annotations annotation presented on current handling parameter
         * @param helper      helper for handlers
         * @return instance name
         * @throws Exception possible exceptions
         */
        String handleConstructor(String name, CtConstructor member, Collection<Annotation> annotations, Helper helper) throws Exception;

        /**
         * @param name        instance name
         * @param member      the member currently handling
         * @param annotations annotation presented on current handling parameter
         * @param helper      helper for handlers
         * @return instance name
         * @throws Exception possible exceptions
         */
        String handleMethod(String name, CtMethod member, Collection<Annotation> annotations, Helper helper) throws Exception;
}
