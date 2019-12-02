package edu.mines.utils;

public final class WildCardMatcher {

    /**
     * fast wildcard string matcher that checks for '*' multi-char wildcards, and '?' without requiring the use of regular expressions
     *
     * @param pattern the wildcard pattern (i.e.  foo*bar)
     * @param name    the string against which pattern should test
     * @returns boolean if match
     */
    public static boolean match(String pattern, String name) {

        int px = 0, nx = 0, nextPx = 0, nextNx = 0;
        int patternLen = pattern.length();
        int nameLen = name.length();

        while (px < patternLen || nx < nameLen) {
            if (px < patternLen) {
                char c = pattern.charAt(px);
                switch (c) {
                    case '?': // single-character wildcard
                        if (nx < nameLen) {
                            px++;
                            nx++;
                            continue;
                        }
                        break;
                    case '*': // zero-or-more-character wildcard
                        // Try to match at nx. If that doesn't work out, restart at nx+1 next.
                        nextPx = px;
                        nextNx = nx + 1;
                        px++;
                        continue;
                    default: // ordinary character
                        if (nx < nameLen && name.charAt(nx) == c) {
                            px++;
                            nx++;
                            continue;
                        }
                }
            }
            // Mismatch. Maybe restart.
            if (0 < nextNx && nextNx <= nameLen) {
                px = nextPx;
                nx = nextNx;
                continue;
            }
            return false;
        }
        // Matched all of pattern to all of name. Success.
        return true;
    }

}