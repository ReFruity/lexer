package lexer;

public class FloatReader extends Automaton {
    private final String digits = "0123456789";

    public FloatReader() {
        super("float", "double");
        addTransition("start", digits, "digits");
    }

    public Token tryReadToken(String input) {
        String prefix = calculateMaxPrefix(input);
        if(prefix != null)
            if (getState().equals("float")) {
                return new Token("f", prefix, Float.parseFloat(prefix));
            }
            else if (getState().equals("double")) {
                return new Token("d", prefix, Double.parseDouble(prefix));
            }
        return null;
    }
}
