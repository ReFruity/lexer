package lexer;

public class FloatReader extends Automaton {
    public FloatReader() {
        super(0, 0, 0);
        addTransition(' ', 0);
        addGeneralTransition(0);
    }

    public Token tryReadToken(String input) {
        String prefix = calculateMaxPrefix(input);
        if(prefix != null)
            return new Token("f", prefix);
        else
            return null;
    }
}
