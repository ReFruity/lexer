package lexer;

import java.util.Arrays;

public class OperatorReader extends TokenReader {
    private final String[] operators = {
            "=", ">", "<", "!", "~", "?", ":", "==", "<=", ">=", "!=", "&&", "||",
            "++", "--", "+", "-", "*", "/", "&", "|", "^", "%", "<<", ">>", ">>>",
            "+=", "-=", "*=", "/=", "&=", "|=", "^=", "%=", "<<=", ">>=", ">>>=",
    };
    
    public Token tryReadToken(String input) {
        int len = input.length();
        for(String i: Arrays.asList(operators)) {
            if(len >= i.length() && i.equals(input.substring(0, i.length())))
                return new Token("op", i);
        }
        return null;
    }
}
