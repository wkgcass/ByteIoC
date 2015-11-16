package net.cassite.byteioc;

/**
 * bean3 for test
 */
public class Bean3 {
        private Bean1 bean1;
        private Bean2 bean2;

        public Bean3(Bean1 bean1) {
                this.bean1 = bean1;
        }

        public Bean1 getBean1() {
                return bean1;
        }

        public Bean3 setBean1(Bean1 bean1) {
                this.bean1 = bean1;
                return this;
        }

        public Bean2 getBean2() {
                return bean2;
        }

        public Bean3 setBean2(Bean2 bean2) {
                this.bean2 = bean2;
                return this;
        }
}
