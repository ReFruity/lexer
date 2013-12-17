package lexer;

public class SeparatorReader extends TokenReader {
    public Token tryReadToken(String input, int offset) {
        String firstChar = input.substring(offset, offset + 1);
        if("(){}[];,.".contains(firstChar))
            return new Token("sep", firstChar);
        else 
            return null;
    }
}
