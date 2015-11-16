package net.cassite.byteioc.dependencies;

/**
 * primitive info, including 8 primitives and String
 */
public class PrimitiveInfo {
        private boolean isBool = false;
        private boolean isInt = false;
        private boolean isLong = false;
        private boolean isShort = false;
        private boolean isFloat = false;
        private boolean isDouble = false;
        private boolean isByte = false;
        private boolean isChar = false;
        private boolean isString = false;

        public boolean isBool() {
                return isBool;
        }

        public boolean isInt() {
                return isInt;
        }

        public boolean isLong() {
                return isLong;
        }

        public boolean isShort() {
                return isShort;
        }

        public boolean isFloat() {
                return isFloat;
        }

        public boolean isDouble() {
                return isDouble;
        }

        public boolean isByte() {
                return isByte;
        }

        public boolean isChar() {
                return isChar;
        }

        public boolean isString() {
                return isString;
        }

        private boolean boolVal;
        private int intVal;
        private long longVal;
        private short shortVal;
        private float floatVal;
        private double doubleVal;
        private byte byteVal;
        private char charVal;
        private String stringVal;

        public PrimitiveInfo(boolean boolVal) {
                isBool = true;
                this.boolVal = boolVal;
        }

        public PrimitiveInfo(int intVal) {
                isInt = true;
                this.intVal = intVal;
        }

        public PrimitiveInfo(long longVal) {
                isLong = true;
                this.longVal = longVal;
        }

        public PrimitiveInfo(short shortVal) {
                isShort = true;
                this.shortVal = shortVal;
        }

        public PrimitiveInfo(float floatVal) {
                isFloat = true;
                this.floatVal = floatVal;
        }

        public PrimitiveInfo(double doubleVal) {
                isDouble = true;
                this.doubleVal = doubleVal;
        }

        public PrimitiveInfo(byte byteVal) {
                isByte = true;
                this.byteVal = byteVal;
        }

        public PrimitiveInfo(char charVal) {
                isChar = true;
                this.charVal = charVal;
        }

        public PrimitiveInfo(String stringVal) {
                isString = true;
                this.stringVal = stringVal;
        }

        public Object get() {
                if (isBool) {
                        return boolVal;
                } else if (isByte) {
                        return byteVal;
                } else if (isChar) {
                        return charVal;
                } else if (isDouble) {
                        return doubleVal;
                } else if (isFloat) {
                        return floatVal;
                } else if (isInt) {
                        return intVal;
                } else if (isLong) {
                        return longVal;
                } else if (isShort) {
                        return shortVal;
                } else if (isString) {
                        return stringVal;
                } else throw new RuntimeException();
        }
}
