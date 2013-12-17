package lexer;

public class WordReader extends TokenReader {
    private final String word;
    private final boolean ignoreCase;

    public WordReader(String word, boolean ignoreCase) {
        this.word = word;
        this.ignoreCase = ignoreCase;
    }

    public Token tryReadToken(String input, int offset) {
        int wordLen = word.length();
        if (word.regionMatches(ignoreCase, 0, input, offset, wordLen))
            return new Token("kw", word);

        return null;
    }
}
