package net.cassite.byteioc.annoreader.annotations;

import java.lang.annotation.*;

/**
 * wire all setters in the class
 */
@Retention(RetentionPolicy.CLASS)
@Inherited
@Target({ElementType.METHOD, ElementType.TYPE, ElementType.FIELD})
public @interface AutoWire {
        WireType value() default WireType.SETTERS;
}
