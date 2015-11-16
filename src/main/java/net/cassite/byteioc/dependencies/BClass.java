package net.cassite.byteioc.dependencies;

/**
 * represents a class
 */
public class BClass {
        private String className;

        public BClass(String className) {
                this.className = className;
        }

        public String getClassName() {
                return className;
        }

        @Override
        public int hashCode() {
                return className.hashCode();
        }

        @Override
        public boolean equals(Object o) {
                if (o == null) return false;
                if (o instanceof BClass) {
                        BClass that = (BClass) o;
                        return that.className.equals(this.className);
                } else {
                        return false;
                }
        }
}
