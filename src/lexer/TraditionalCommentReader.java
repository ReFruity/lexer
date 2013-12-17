package lexer;

public class TraditionalCommentReader extends TokenReader {
    public TraditionalCommentReader() {
        super("comment", "asterisk", "final");

        addTransition("start", "/", "slash");

        addTransition("slash", "*", "comment");

        addTransition("comment", "*", "asterisk");
        addGeneralTransition("comment", "comment");

        addTransition("asterisk", "*", "asterisk");
        addTransition("asterisk", "/", "final");
        addGeneralTransition("asterisk", "comment");
    }

    public Token tryReadToken(String input, int offset) {
        String prefix = calculateMaxPrefix(input, offset);
        if(prefix != null)
            return new Token("tc", prefix);
        else
            return null;
    }
}
