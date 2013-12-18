package lexer;

public class SeparatorReader extends TokenReader {
    public Token tryReadToken(String input, int offset) {
        char firstChar = input.charAt(offset);
        if("(){}[];,.".indexOf(firstChar) > -1)
            return new Token("sep", Character.toString(firstChar));
        else 
            return null;
    }
}
