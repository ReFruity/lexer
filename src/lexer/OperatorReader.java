package lexer;

import java.util.Arrays;

public class OperatorReader extends TokenReader {
    private final String[] operators = {
            "=", ">", "<", "!", "~", "?", ":", "==", "<=", ">=", "!=", "&&", "||",
            "++", "--", "+", "-", "*", "/", "&", "|", "^", "%", "<<", ">>", ">>>",
            "+=", "-=", "*=", "/=", "&=", "|=", "^=", "%=", "<<=", ">>=", ">>>=",
    };
    
    public Token tryReadToken(String input) {
        for(String i: Arrays.asList(operators)) {
            if(i.equals(input.substring(0, i.length())))
                return new Token("op", i);
        }
        return null;
    }
}
