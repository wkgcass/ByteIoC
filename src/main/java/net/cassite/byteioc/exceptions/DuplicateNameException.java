package net.cassite.byteioc.exceptions;

/**
 * duplicate name when defining dependency
 */
public class DuplicateNameException extends IllegalArgumentException {
        public DuplicateNameException(String s) {
                super(s);
        }
}
