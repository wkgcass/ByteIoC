package net.cassite.byteioc.annoreader;

import java.lang.annotation.Annotation;
import java.util.Collection;

/**
 * Handler of annotations
 */
public interface Handler {
        /**
         * this handler can handle at lease one of given annotations
         *
         * @param annotations annotations presented on the method to be handled
         * @return true if at lease one of given annotations can be handled
         */
        boolean canHandle(Collection<Annotation> annotations);
}
