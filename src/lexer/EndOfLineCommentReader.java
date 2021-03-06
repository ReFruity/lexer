package lexer;

public class EndOfLineCommentReader extends TokenReader {

    public EndOfLineCommentReader() {
        super("comment");

        addTransition("start", "/", "slash");

        addTransition("slash", "/", "comment");

        addTransition("comment", "\n", "error");
        addGeneralTransition("comment", "comment");
    }

    public Token tryReadToken(String input, int offset) {
        String prefix = calculateMaxPrefix(input, offset);
        if(prefix != null)
            return new Token("eolc", prefix);
        else
            return null;
    }
}
