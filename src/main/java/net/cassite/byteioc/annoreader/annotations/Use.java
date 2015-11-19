package net.cassite.byteioc.annoreader.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * automatically wire the class/setter/field/param
 */
@Retention(RetentionPolicy.CLASS)
@Target({ElementType.TYPE, ElementType.FIELD, ElementType.METHOD, ElementType.CONSTRUCTOR, ElementType.PARAMETER})
public @interface Use {
        String value() default "";

        String target() default "net.cassite.byteioc.annoreader.annotations.Use";

        String val() default "*(FE#89yF#($Yn98v";
}
