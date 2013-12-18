package lexer;

public class KeywordReader extends TokenReader {
    public Token tryReadToken(String input, int offset) {
        int len = input.length() - offset;
        if (len == 0 || !Character.isJavaIdentifierStart(input.charAt(offset))) {
            return null;
        }
        int i = offset + 1;
        while (i < len && Character.isJavaIdentifierPart(input.charAt(i)))
            i++;
        String result = input.substring(offset, i);
        if (Keywords.contain(result))
            return new Token("id", result);
        else
            return null;
    }
}
