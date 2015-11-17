package net.cassite.byteioc.annoreader.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * mark the class as a singleton
 */
@Retention(RetentionPolicy.CLASS)
@Target(ElementType.TYPE)
public @interface Singleton {
}
