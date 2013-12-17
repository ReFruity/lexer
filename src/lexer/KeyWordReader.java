package lexer;

public class KeywordReader extends TokenReader {
    public Token tryReadToken(String input, int offset) {
        int len = input.length() - offset;
        for(String i : Keywords.get()) {
            int wordLen = i.length();
            if(len >= wordLen && input.substring(offset, offset + wordLen).equals(i))
                return new Token("kw", i);
        }
        return null;
    }
}
