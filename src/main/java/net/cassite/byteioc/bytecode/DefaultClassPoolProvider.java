package net.cassite.byteioc.bytecode;

import javassist.ClassPool;

/**
 * use ClassPool.getDefault() to retrieve class pool
 *
 * @see ClassPool#getDefault()
 */
public class DefaultClassPoolProvider implements ClassPoolProvider {

        @Override
        public ClassPool getClassPool() {
                return ClassPool.getDefault();
        }
}
