package lexer;

import java.util.List;
import java.util.Arrays;

public class Keywords {
    private static final String[] keywords = {
        "abstract", "continue", "for", "new", "switch", "assert", 
        "default", "if", "package", "synchronized", "boolean", "do", 
        "goto", "private", "this", "break", "double", "implements", 
        "protected", "throw", "byte", "else", "import", "public", 
        "throws", "case", "enum", "instanceof", "return", "transient", 
        "catch", "extends", "int", "short", "try", "char", "final", 
        "interface", "static", "void", "class", "finally", "long", 
        "strictfp", "volatile", "const", "float", "native", "super", 
        "while",
    };
    
    private static List keywordsList = Arrays.asList(keywords);
    
    public Keywords () {
        keywordsList = Arrays.asList(keywords);
    }
    
    public static boolean contain(String keyword) {
        return keywordsList.contains(keyword);
        //TODO: Revise this operation
    }
    
    public static String[] get() {
        return keywords;
    }
}