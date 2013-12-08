package lexer;

public class SeparatorReader extends TokenReader {
    public Token tryReadToken(String input) {
        String firstChar = input.substring(0, 1);
        if("(){}[];,.".contains(firstChar))
            return new Token("sep", firstChar);
        else 
            return null;
    }
}
