package net.cassite.byteioc;

import net.cassite.byteioc.annoreader.annotations.AutoWire;

/**
 * bean for testing
 */
public class Bean1 {
        @AutoWire
        private String testStr;
        private int i;
        private Bean4 bean4;

        public Bean1(String testStr) {
                this.testStr = testStr;
        }

        public Bean1 setTestStr(String testStr) {
                this.testStr = testStr;
                return this;
        }

        public String getTestStr() {
                return testStr;
        }

        public int getI() {
                return i;
        }

        public Bean1 setI(int i) {
                this.i = i;
                return this;
        }

        public Bean4 getBean4() {
                return bean4;
        }

        public Bean1 setBean4(Bean4 bean4) {
                this.bean4 = bean4;
                return this;
        }
}
