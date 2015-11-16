package net.cassite.byteioc.dependencies;

import java.util.Arrays;

/**
 * represent a method
 */
public class BMethod {
        public String methodName;
        public BClass[] argClasses;

        public BMethod(String methodName, BClass[] argClasses) {
                this.methodName = methodName;
                this.argClasses = argClasses;
        }

        public String getMethodName() {
                return methodName;
        }

        public BClass[] getArgClasses() {
                return argClasses;
        }

        @Override
        public int hashCode() {
                return methodName.hashCode() + Arrays.hashCode(argClasses);
        }

        @Override
        public boolean equals(Object o) {
                if (o == null) return false;
                if (o instanceof BMethod) {
                        BMethod that = (BMethod) o;
                        return this.methodName.equals(that.methodName) && Arrays.equals(this.argClasses, that.argClasses);
                } else {
                        return false;
                }
        }
}
