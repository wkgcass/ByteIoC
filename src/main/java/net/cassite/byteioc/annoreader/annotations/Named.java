package net.cassite.byteioc.annoreader.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * set a name to constructors or generators<br>
 * only use this annotation on a generator implementation if present on a <b>type</b>
 */
@Retention(RetentionPolicy.CLASS)
@Target({ElementType.CONSTRUCTOR, ElementType.TYPE})
public @interface Named {
        String value();
}
