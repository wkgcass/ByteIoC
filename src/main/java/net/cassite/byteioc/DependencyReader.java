package net.cassite.byteioc;

import net.cassite.byteioc.dependencies.Dependencies;

/**
 * reads dependency and put them into the Dependencies instance
 */
public interface DependencyReader {
        void joinDependencies(Dependencies dependencies) throws Exception;
}
