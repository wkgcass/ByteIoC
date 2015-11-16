package net.cassite.byteioc.dependencies;

import java.util.HashMap;
import java.util.Map;

/**
 * info about what value to set a field, and what arguments to give a method to invoke
 */
public class FieldMethodInfo {
        private final BClass cls;
        /**
         * field name =$gt; instance alias
         */
        private final Map<BField, String> fieldArgs = new HashMap<BField, String>();
        private final Map<BMethod, String[]> methodArgs = new HashMap<BMethod, String[]>();

        public FieldMethodInfo(BClass cls) {
                this.cls = cls;
        }

        public BClass getCls() {
                return cls;
        }

        public FieldMethodInfo addFieldArg(BField field, String arg) {
                fieldArgs.put(field, arg);
                return this;
        }

        public FieldMethodInfo addMethodArgs(BMethod method, String[] args) {
                methodArgs.put(method, args);
                return this;
        }

        public Map<BField, String> getFieldArgs() {
                return fieldArgs;
        }

        public Map<BMethod, String[]> getMethodArgs() {
                return methodArgs;
        }
}
