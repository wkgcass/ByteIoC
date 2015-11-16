package net.cassite.byteioc.dependencies;

/**
 * info about what arguments to give a constructor to create instance
 */
public class ConstructorInfo {
        private final BClass cls;
        private final BConstructor constructor;
        private final String[] args;

        public ConstructorInfo(BClass cls, BConstructor constructor, String[] args) {
                this.cls = cls;
                this.constructor = constructor;
                this.args = args;
        }

        public BClass getCls() {
                return cls;
        }

        public BConstructor getConstructor() {
                return constructor;
        }

        public String[] getArgs() {
                return args;
        }
}
