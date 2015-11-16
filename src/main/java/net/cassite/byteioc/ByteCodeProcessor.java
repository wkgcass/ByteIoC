package net.cassite.byteioc;

import net.cassite.byteioc.dependencies.Dependencies;

/**
 * generate or modify bytecode with given dependencies.<br>
 * the classes should be able to perform dependency injection simply using <b>new</b>
 */
public interface ByteCodeProcessor {
        /**
         * each modified bytecode will add a field and this string will be its field name
         */
        String alreadyEnhanced = "alreadyEnhanced$PureIoC$byteCodeProcessor";

        /**
         * modify bytecode so that the classes can do dependency injecting using <b>new</b>.<br>
         * and add static field to singleton objects
         *
         * @param dependencies dependency description
         * @throws Exception passable exceptions
         */
        void process(Dependencies dependencies) throws Exception;
}
