package lexer;

public class WhitespaceReader extends TokenReader {
    public Token tryReadToken(String input, int offset) {
        if (input.length() == offset || !Character.isWhitespace(input.charAt(offset))) 
            return null;
        else
            return new Token("ws", input.substring(offset, offset + 1));
    }
}
