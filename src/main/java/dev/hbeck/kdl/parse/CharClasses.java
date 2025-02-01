package dev.hbeck.kdl.parse;

import java.util.Optional;

/**
 * Various functions used during parsing and printing to check character membership in various character classes.
 * <p>
 * Also contains functions for transforming characters into their escape sequences.
 */
public class CharClasses {

    /**
     * Check if the character is valid at the beginning of a numeric value
     *
     * @param c the character to check
     * @return true if the character is valid, false otherwise
     */
    public static boolean isValidNumericStart(int c) {
        return switch (c) {
            case '+', '-', '0', '1', '2', '3', '4', '5', '6', '7', '8', '9' -> true;
            default -> false;
        };
    }

    /**
     * Check if the character is valid in a bare identifier after the first character
     *
     * @param c the character to check
     * @return true if the character is valid, false otherwise
     */
    public static boolean isValidBareIdChar(int c) {
        if (c <= 0x20 || c > 0x10FFFF) {
            return false;
        }

        return switch (c) {
            case '\u0085', '\u2028', '\u2029', '\\', '/', '(', ')', '{', '}', '<', '>', ';', '[', ']', '=', ',', '"',
                 '\u00A0', '\u1680', '\u2000', '\u2001', '\u2002', '\u2003', '\u2004', '\u2005', '\u2006', '\u2007',
                 '\u2008', '\u2009', '\u200A', '\u202F', '\u205F', '\u3000', '\uFEFF' -> false;
            default -> true;
        };
    }

    /**
     * Check if the character is valid in a bare identifier as the first character
     *
     * @param c the character to check
     * @return true if the character is valid, false otherwise
     */
    public static boolean isValidBareIdStart(int c) {
        return !isValidDecimalChar(c) && isValidBareIdChar(c);
    }

    /**
     * Check if a string is a valid bare identifier
     *
     * @param string the string to check
     * @return true if the string is a valid bare id, false otherwise
     */
    public static boolean isValidBareId(String string) {
        if (string.isEmpty()) {
            return false;
        }

        final boolean validBareIdStart = isValidBareIdStart(string.charAt(0));
        if (string.length() == 1 || !validBareIdStart) {
            return validBareIdStart;
        }

        for (int i = 0; i < string.length(); i++) {
            if (!isValidBareIdChar(string.charAt(i))) {
                return false;
            }
        }

        return true;
    }

    /**
     * Check if the character is a valid decimal digit
     *
     * @param c the character to check
     * @return true if the character is valid, false otherwise
     */
    public static boolean isValidDecimalChar(int c) {
        return '0' <= c && c <= '9';
    }

    /**
     * Check if the character is a valid hexadecimal digit
     *
     * @param c the character to check
     * @return true if the character is valid, false otherwise
     */
    public static boolean isValidHexChar(int c) {
        return switch (c) {
            case '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'a', 'B', 'b', 'C', 'c', 'D', 'd', 'E', 'e',
                 'F', 'f' -> true;
            default -> false;
        };
    }

    /**
     * Check if the character is a valid octal digit
     *
     * @param c the character to check
     * @return true if the character is valid, false otherwise
     */
    public static boolean isValidOctalChar(int c) {
        return '0' <= c && c <= '7';
    }

    /**
     * Check if the character is a valid binary digit
     *
     * @param c the character to check
     * @return true if the character is valid, false otherwise
     */
    public static boolean isValidBinaryChar(int c) {
        return c == '0' || c == '1';
    }

    /**
     * Check if the character is contained in one of the three literal values: true, false, and null
     *
     * @param c the character to check
     * @return true if the character appears in a literal, false otherwise
     */
    public static boolean isLiteralChar(int c) {
        return switch (c) {
            case 't', 'r', 'u', 'e', 'n', 'l', 'f', 'a', 's' -> true;
            default -> false;
        };
    }

    /**
     * Check if the character is contained in one of the six keywords:
     * true, false, null, inf, -inf, and nan
     *
     * @param c the character to check
     * @return true if the character appears in a keyword, false otherwise
     */
    public static boolean isKeywordChar(int c) {
        return isLiteralChar(c) || switch (c) {
            case 'i', 'n', 'f', '-', 'a' -> true;
            default -> false;
        };
    }

    /**
     * Check if the character is a unicode newline of any kind
     *
     * @param c the character to check
     * @return true if the character is a unicode newline, false otherwise
     */
    public static boolean isUnicodeLinespace(int c) {
        return switch (c) {
            case '\r', '\n', '\u0085', '\u000C', '\u2028', '\u2029' -> true;
            default -> false;
        };
    }

    /**
     * Check if the character is unicode whitespace of any kind
     *
     * @param c the character to check
     * @return true if the character is unicode whitespace, false otherwise
     */
    public static boolean isUnicodeWhitespace(int c) {
        return switch (c) {
            case '\u0009', '\u0020', '\u00A0', '\u1680', '\u2000', '\u2001', '\u2002', '\u2003', '\u2004', '\u2005',
                 '\u2006', '\u2007', '\u2008', '\u2009', '\u200A', '\u202F', '\u205F', '\u3000' -> true;
            default -> false;
        };
    }

    /**
     * Check if the character is an ASCII character that can be printed unescaped
     *
     * @param c the character to check
     * @return true if the character is printable unescaped, false otherwise
     */
    public static boolean isPrintableAscii(int c) {
        return ' ' <= c && c <= '~';
    }

    public static boolean isNonAscii(int c) {
        return c > 127;
    }

    public static boolean mustEscape(int c) {
        return c == '\\' || c == '"';
    }

    private static final Optional<String> ESC_BACKSLASH = Optional.of("\\\\");
    private static final Optional<String> ESC_BACKSPACE = Optional.of("\\b");
    private static final Optional<String> ESC_NEWLINE = Optional.of("\\n");
    private static final Optional<String> ESC_FORM_FEED = Optional.of("\\f");
    private static final Optional<String> ESC_FORWARD_SLASH = Optional.of("\\/");
    private static final Optional<String> ESC_TAB = Optional.of("\\t");
    private static final Optional<String> ESC_CR = Optional.of("\\r");
    private static final Optional<String> ESC_QUOTE = Optional.of("\\\"");

    /**
     * Get the escape sequence for characters from the ASCII character set
     *
     * @param c the character to check
     * @return An Optional wrapping the escape sequence string if the character needs to be escaped, or false otherwise
     */
    public static Optional<String> getCommonEscape(int c) {
        return switch (c) {
            case '\\' -> ESC_BACKSLASH;
            case '\b' -> ESC_BACKSPACE;
            case '\n' -> ESC_NEWLINE;
            case '\f' -> ESC_FORM_FEED;
            case '/' -> ESC_FORWARD_SLASH;
            case '\t' -> ESC_TAB;
            case '\r' -> ESC_CR;
            case '"' -> ESC_QUOTE;
            default -> Optional.empty();
        };
    }

    public static boolean isCommonEscape(int c) {
        return switch (c) {
            case '\\', '\b', '\n', '\f', '\t', '\r', '"' -> true;
            default -> false;
        };
    }

    /**
     * Get the escape sequence for any character
     *
     * @param c the character to check
     * @return The escape sequence string
     */
    public static String getEscapeIncludingUnicode(int c) {
        return getCommonEscape(c).orElseGet(() -> String.format("\\u{%x}", c));
    }
}
