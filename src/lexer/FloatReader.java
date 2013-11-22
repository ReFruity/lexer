package lexer;

public class FloatReader extends Automaton {
    private final String digits = "0123456789";

    public FloatReader() {
        super("fractional", "signedInt", "float", "double");

        link("double", "signedInt", "fractional");

        addTransition("start", digits, "integer");
        addTransition("start", ".", "dot");

        addTransition("integer", digits, "integer");
        addTransition("integer", "eE", "expIndicator");
        addTransition("integer", "fF", "float");
        addTransition("integer", "dD", "double");
        addTransition("integer", ".", "fractional");

        addTransition("dot", digits, "fractional");

        addTransition("fractional", digits, "fractional");
        addTransition("fractional", "eE", "expIndicator");
        addTransition("fractional", "fF", "float");
        addTransition("fractional", "dD", "double");

        addTransition("expIndicator", "+-", "sign");
        addTransition("expIndicator", digits, "signedInt");

        addTransition("sign", digits, "signedInt");

        addTransition("signedInt", digits, "signedInt");
        addTransition("signedInt", "fF", "float");
        addTransition("signedInt", "dD", "double");

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
