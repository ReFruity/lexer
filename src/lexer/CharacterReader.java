package lexer;

public class CharacterReader extends TokenReader {
    public CharacterReader() {
        super("final");
        
        addTransition("start", "'", "charLiteral");
        
        addTransition("charLiteral", "\\", "escapeSequence");
        addGeneralTransition("charLiteral", "symbol");
    }
    
    public Token tryReadToken(String input) {
        return null;
    }
}
