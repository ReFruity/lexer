package lexer;

import java.util.Arrays;
import java.util.List;

public class OperatorReader extends TokenReader {
    private final static String[] operators = {
            "=", ">", "<", "!", "~", "?", ":", "==", "<=", ">=", "!=", "&&", "||",
            "++", "--", "+", "-", "*", "/", "&", "|", "^", "%", "<<", ">>", ">>>",
            "+=", "-=", "*=", "/=", "&=", "|=", "^=", "%=", "<<=", ">>=", ">>>=",
    };
    
    private static List<String> operatorsList = Arrays.asList(operators);

    private final static String symbols = "=><!~?:&|+-*/^%";
    
    public Token tryReadToken(String input, int offset) {
        int len = input.length();
        
        int i = offset;
        
        while (i < offset + 4 && i < len && symbols.indexOf(input.charAt(i)) > -1) 
            i++;
        
        String prefix = input.substring(offset, i);
        
        while(i > offset) {
            if (operatorsList.contains(prefix))
                return new Token("op", prefix);
            i--;
            prefix = prefix.substring(0, i - offset);
        }
        
        return null;
    }
}
