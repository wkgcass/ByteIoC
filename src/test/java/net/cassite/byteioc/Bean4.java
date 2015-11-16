package net.cassite.byteioc;

/**
 * bean4 for test
 */
public class Bean4 {
        private Bean3 bean3;
        private Bean1 bean1;

        public Bean1 getBean1() {
                return bean1;
        }

        public Bean4 setBean1(Bean1 bean1) {
                this.bean1 = bean1;
                return this;
        }

        public Bean3 getBean3() {
                return bean3;
        }

        public Bean4 setBean3(Bean3 bean3) {
                this.bean3 = bean3;
                return this;
        }
}
