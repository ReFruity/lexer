package lexer;

public class TraditionalCommentReader extends Automaton {
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

    public Token tryReadToken(String input) {
        String prefix = calculateMaxPrefix(input);
        if(prefix != null)
            return new Token("tc", prefix);
        else
            return null;
    }
}
