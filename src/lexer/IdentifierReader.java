package lexer;

public class IdentifierReader extends TokenReader {
    public Token tryReadToken(String input) {
        // http://docs.oracle.com/javase/specs/jls/se7/html/jls-3.html#jls-3.8
        int len = input.length();
        if (len == 0 || !Character.isJavaIdentifierStart(input.charAt(0))) {
            return null;
        }
        int i = 1;
        while (i < len && Character.isJavaIdentifierPart(input.charAt(i)))
            i++;
        String result = input.substring(0, i);
        if (!Keywords.contain(result))
            return new Token("id", result);
        else
            return null;
    }
}
