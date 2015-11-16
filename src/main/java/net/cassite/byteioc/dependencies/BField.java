package net.cassite.byteioc.dependencies;

/**
 * represents a field
 */
public class BField {
        private String fieldName;

        public BField(String fieldName) {
                this.fieldName = fieldName;
        }

        public String getFieldName() {
                return fieldName;
        }

        @Override
        public int hashCode() {
                return fieldName.hashCode();
        }

        @Override
        public boolean equals(Object o) {
                if (o == null) return false;
                if (o instanceof BField) {
                        BField that = (BField) o;
                        return this.fieldName.equals(that.fieldName);
                } else {
                        return false;
                }
        }
}
