package net.cassite.byteioc.bytecode;

import javassist.ClassPool;

/**
 * Provider of javassist class pool
 */
public interface ClassPoolProvider {
        ClassPool getClassPool();
}
