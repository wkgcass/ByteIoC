package net.cassite.byteioc.exceptions;

/**
 * thrown when reading and occurring exceptions
 */
public class ReadingException extends RuntimeException {
        public ReadingException() {
        }

        public ReadingException(String message) {
                super(message);
        }

        public ReadingException(String message, Throwable cause) {
                super(message, cause);
        }

        public ReadingException(Throwable cause) {
                super(cause);
        }
}
