package lexer;

public class IdentifierReader extends TokenReader {
    public Token tryReadToken(String input, int offset) {
        // http://docs.oracle.com/javase/specs/jls/se7/html/jls-3.html#jls-3.8
        int len = input.length();
        if (len - offset == 0 || !Character.isJavaIdentifierStart(input.charAt(offset))) {
            return null;
        }
        int i = offset + 1;
        while (i < len && Character.isJavaIdentifierPart(input.charAt(i)))
            i++;
        String result = input.substring(offset, i);
        if (!Keywords.contain(result))
            return new Token("id", result);
        else
            return null;
    }
}
