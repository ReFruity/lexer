package lexer;

public class EndOfLineCommentReader extends Automaton {

    public EndOfLineCommentReader() {
        super(0, 3, 4);

        addTransition('/', 1,2,2);
        addTransition('\n', 3,3,4);
        addGeneralTransition(3,3,2);
    }

    public Token tryReadToken(String input) {
        String prefix = calculateMaxPrefix(input);
        if(prefix != null)
            return new Token("eoflc", prefix);
        else
            return null;
    }
}
