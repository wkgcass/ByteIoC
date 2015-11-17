package net.cassite.byteioc.annoreader.annotations;

/**
 * choose the default type/constructor to use
 */
public @interface Default {
        String value() default "";

        Class<?> target() default Default.class;
}
