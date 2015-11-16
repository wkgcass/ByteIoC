package net.cassite.byteioc.exceptions;

/**
 * duplicate class when defining dependency
 */
public class DuplicateClassException extends IllegalArgumentException {
        public DuplicateClassException(String s) {
                super(s);
        }
}
