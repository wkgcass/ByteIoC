package net.cassite.byteioc.dependencies;

/**
 * generates an instance<br>
 * something like beanfactory in spring.
 */
public interface Generator {
        Object generate();
}
