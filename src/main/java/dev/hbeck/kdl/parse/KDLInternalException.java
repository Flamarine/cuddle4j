package dev.hbeck.kdl.parse;

/**
 * Thrown if an unexpected state is encountered while parsing a document. If you encounter this
 * please create an issue on <a href="https://github.com/hkolbeck/kdl4j/issues">...</a> with the offending document
 */
public class KDLInternalException extends RuntimeException {
    public KDLInternalException(String message) {
        super(message);
    }

    public KDLInternalException(String message, Throwable cause) {
        super(message, cause);
    }
}
