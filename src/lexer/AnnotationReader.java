package lexer;

/**
 * Predefined Annotation Reader
 * Reads prefixes like @Override
 * http://docs.oracle.com/javase/specs/jls/se7/html/jls-9.html#jls-9.6.3
 */

public class AnnotationReader extends TokenReader {
    private final String letters = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";
    
    public AnnotationReader() {
        super("final");
        
        addTransition("start", "@", "annotation");
        
        addTransition("annotation", letters, "annotation");
        addGeneralTransition("annotation", "final");
    }

    public Token tryReadToken(String input, int offset) {
        String prefix = calculateMaxPrefix(input, offset);
        if(prefix != null)
            return new Token("annot", prefix);
        else
            return null;
    }
}