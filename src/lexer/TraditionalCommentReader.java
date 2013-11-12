package lexer;

public class TraditionalCommentReader extends Automaton {
    public TraditionalCommentReader() {
        super(0, 5, 6);

        addTransition('/',   1,5,2,4,6);
        addTransition('*',   5,2,3,3,6);
        addGeneralTransition(5,5,2,2,6);
    }

    public Token tryReadToken(String input) {
        String prefix = calculateMaxPrefix(input);
        if(prefix != null)
            return new Token("tc", prefix);
        else
            return null;
    }
}
