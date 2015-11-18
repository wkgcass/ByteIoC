package net.cassite.byteioc.annoreader.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * choose the default type/constructor to use
 */
@Retention(RetentionPolicy.CLASS)
@Target({ElementType.CONSTRUCTOR, ElementType.TYPE})
public @interface Default {
        String value() default "";

        String target() default "net.cassite.byteioc.annoreader.annotations.Default";
}
