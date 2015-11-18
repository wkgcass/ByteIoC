package net.cassite.byteioc.annoreader;

import javassist.CtField;
import net.cassite.byteioc.dependencies.FieldMethodInfo;

import java.lang.annotation.Annotation;
import java.util.Collection;

/**
 * Handler of field annotations
 */
public interface FieldAnnotationHandler extends Handler {
        /**
         * do handling, and add proper info into FieldMethodInfo
         *
         * @param arg         instance to fill the field
         * @param field       current handling field
         * @param annotations annotations presented on the field
         * @param chain       handler chain
         * @param helper      helper for handlers
         * @return instance to fill the field, null if don't want to fill
         * @throws Exception passable exceptions
         */
        String handle(String arg, CtField field, Collection<Annotation> annotations, FieldChainHandler chain, Helper helper) throws Exception;
}
