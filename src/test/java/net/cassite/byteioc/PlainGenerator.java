package net.cassite.byteioc;

import net.cassite.byteioc.dependencies.Generator;

/**
 * generator for test
 */
public class PlainGenerator implements Generator {
        @Override
        public Object generate() {
                return new Bean2();
        }
}
