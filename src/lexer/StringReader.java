package lexer;

public class StringReader extends TokenReader {
    private final String octalDigits = "01234567";
    private final String hexDigits = "0123456789abcdefABCDEF";
    
    public StringReader() {
        super("final");
        
        addTransition("start", "\"", "string");
        
        addTransition("string", "\n\r", "error");
        addTransition("string", "\\", "escapeSequence");
        addGeneralTransition("string", "string");

        addTransition("escapeSequence", "u", "utf16");
        addTransition("escapeSequence", "btnfr\"\'\\", "string");
        addTransition("escapeSequence", "0123", "shortOctalEscape");
        addTransition("escapeSequence", "4567", "octalEscape");

        addTransition("utf16", hexDigits, "firstHD");
        addTransition("firstHD", hexDigits, "secondHD");
        addTransition("secondHD", hexDigits, "thirdHD");
        addTransition("thirdHD", hexDigits, "fourthHD");
        addTransition("fourthHD", "\'", "string");

        addTransition("simpleEscape", "\'", "string");

        addTransition("shortOctalEscape", octalDigits, "secondSOD");
        addTransition("secondSOD", octalDigits, "thirdSOD");

        addTransition("thirdSOD", "\'", "string");
        addTransition("secondSOD", "\'", "string");
        addTransition("shortOctalEscape", "\'", "string");

        addTransition("octalEscape", octalDigits, "secondOD");

        addTransition("octalEscape", "\'", "string");
        addTransition("secondOD", "\'", "string");
        
        addTransition("string", "\"", "final");
    }

    public Token tryReadToken(String input, int offset) {
        String prefix = calculateMaxPrefix(input, offset);
        if(prefix != null)
            return new Token("str", prefix);
        else
            return null;
    }
}