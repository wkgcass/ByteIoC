package net.cassite.byteioc.dependencies;

/**
 * represents a field
 */
public class BField {
        private String fieldName;
        private BClass type;

        public BField(String fieldName, BClass type) {
                this.fieldName = fieldName;
                this.type = type;
        }

        public String getFieldName() {
                return fieldName;
        }

        public BClass getType() {
                return type;
        }

        @Override
        public int hashCode() {
                return fieldName.hashCode() + type.hashCode();
        }

        @Override
        public boolean equals(Object o) {
                if (o == null) return false;
                if (o instanceof BField) {
                        BField that = (BField) o;
                        return this.fieldName.equals(that.fieldName) && this.type.equals(that.type);
                } else {
                        return false;
                }
        }
}
