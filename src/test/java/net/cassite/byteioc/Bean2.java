package net.cassite.byteioc;

/**
 * bean2 for test
 */
public class Bean2 {
        private Bean1 bean1;

        public Bean1 getBean1() {
                return bean1;
        }

        public Bean2 setBean1(Bean1 bean1) {
                this.bean1 = bean1;
                return this;
        }
}
