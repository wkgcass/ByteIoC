package net.cassite.byteioc.dependencies;

import java.util.Arrays;

/**
 * represents a constructor
 */
public class BConstructor {
        private BClass[] argClasses;

        public BConstructor(BClass[] argClasses) {
                this.argClasses = argClasses;
        }

        public BClass[] getArgClasses() {
                return argClasses;
        }

        @Override
        public int hashCode() {
                return Arrays.hashCode(argClasses);
        }

        @Override
        public boolean equals(Object o) {
                if (o == null) return false;
                if (o instanceof BConstructor) {
                        BConstructor that = (BConstructor) o;
                        return Arrays.equals(this.argClasses, that.argClasses);
                } else {
                        return false;
                }
        }
}
