package lexer;

import java.util.Arrays;

public class OperatorReader extends TokenReader {
    private final String[] operators = {
            "=", ">", "<", "!", "~", "?", ":", "==", "<=", ">=", "!=", "&&", "||",
            "++", "--", "+", "-", "*", "/", "&", "|", "^", "%", "<<", ">>", ">>>",
            "+=", "-=", "*=", "/=", "&=", "|=", "^=", "%=", "<<=", ">>=", ">>>=",
    };
    
    public Token tryReadToken(String input, int offset) {
        int len = input.length() - offset;
        for(String i : Arrays.asList(operators)) {
            int opLen = i.length();
            if(len >= opLen && i.equals(input.substring(offset, offset + opLen)))
                return new Token("op", i);
        }
        return null;
    }
}
