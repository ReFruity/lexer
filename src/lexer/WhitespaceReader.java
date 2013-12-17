package lexer;

public class WhitespaceReader extends TokenReader {
    public Token tryReadToken(String input, int offset) {
        int len = input.length();
        if (len == offset || !Character.isWhitespace(input.charAt(offset))) return null;
        int i = offset;
        while (i < len && Character.isWhitespace(input.charAt(i)))
            i++;
        return new Token("ws", input.substring(offset, i));
    }
}
