package lexer;

public class WhitespaceReader extends TokenReader {
    public Token tryReadToken(String input, int offset) {
        if (input.length() == offset || !Character.isWhitespace(input.charAt(offset))) 
            return null;
        else {
            int i = offset + 1;
            while(i < input.length() && Character.isWhitespace(input.charAt(i)))
                i++;
            return new Token("ws", input.substring(offset, i));
        }
    }
}
