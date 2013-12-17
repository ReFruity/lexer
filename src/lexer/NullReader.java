package lexer;

public class NullReader extends TokenReader {
    public Token tryReadToken(String input, int offset) {
        if(input.length() - offset >= 4 && input.substring(offset, offset + 4).equals("null"))
            return new Token("null", "null");
        return null;
    }
}
