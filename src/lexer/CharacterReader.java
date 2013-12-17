package lexer;

public class CharacterReader extends TokenReader {
    private final String octalDigits = "01234567";
    private final String hexDigits = "0123456789abcdefABCDEF";
    
    public CharacterReader() {
        super("final");
        
        addTransition("start", "\'", "charLiteral");
        
        addTransition("charLiteral", "\'\n\r", "error");
        addTransition("charLiteral", "\\", "escapeSequence");
        addGeneralTransition("charLiteral", "singleChar");
        
        addTransition("escapeSequence", "u", "utf16");
        addTransition("escapeSequence", "btnfr\"\'\\", "simpleEscape");
        addTransition("escapeSequence", "0123", "shortOctalEscape");
        addTransition("escapeSequence", "4567", "octalEscape");
        
        addTransition("utf16", hexDigits, "firstHD");
        addTransition("firstHD", hexDigits, "secondHD");
        addTransition("secondHD", hexDigits, "thirdHD");
        addTransition("thirdHD", hexDigits, "fourthHD");
        addTransition("fourthHD", "\'", "final");
        
        addTransition("simpleEscape", "\'", "final");
        
        addTransition("shortOctalEscape", octalDigits, "secondSOD");
        addTransition("secondSOD", octalDigits, "thirdSOD");
        
        addTransition("thirdSOD", "\'", "final");
        addTransition("secondSOD", "\'", "final");
        addTransition("shortOctalEscape", "\'", "final");
        
        addTransition("octalEscape", octalDigits, "secondOD");

        addTransition("octalEscape", "\'", "final");
        addTransition("secondOD", "\'", "final");   
        
        addTransition("singleChar", "\'", "final");
    }
    
    public Token tryReadToken(String input, int offset) {
        String prefix = calculateMaxPrefix(input, offset);
        if(prefix != null)
            return new Token("char", prefix);
        else
            return null;
    }
}
/*
        EscapeSequence:
        \ b     \u0008: backspace BS 
        \ t     \u0009: horizontal tab HT 
        \ n     \u000a: linefeed LF 
        \ f     \u000c: form feed FF 
        \ r     \u000d: carriage return CR 
        \ "     \u0022: double quote " 
        \ '     \u0027: single quote ' 
        \ \     \u005c: backslash \ 
        OctalEscape         \u0000 to \u00ff: from octal value 

        OctalEscape:
        \ OctalDigit
        \ OctalDigit OctalDigit
        \ ZeroToThree OctalDigit OctalDigit

        OctalDigit: one of
        0 1 2 3 4 5 6 7

        ZeroToThree: one of
        0 1 2 3
        
        \u12FA
        \345
        \77
*/