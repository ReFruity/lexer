package lexer;

public class NullReader extends TokenReader {
    public Token tryReadToken(String input) {
        if(input.substring(0, 4).equals("null"))
            return new Token("null", "null");
        else
            return null;
    }
}
