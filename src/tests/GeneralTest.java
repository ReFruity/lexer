package tests;

import junit.framework.TestCase;
import lexer.*;

import java.io.FileReader;
import java.io.IOException;

public class GeneralTest extends TestCase {
    public void testTokenReader() throws IOException {
        testFile(".\\src\\javasrc\\TokenReader.java.resource");
        //Done: 0.115s -> 0.063
    }
    
    public void testCharacterLibrary() throws IOException {
        testFile(".\\src\\javasrc\\Character.java.resource");
        //Done: 1m 22s -> 5s
    }

    public void testFilesLibrary() throws IOException {
        testFile(".\\src\\javasrc\\Files.java.resource");
        //Done: 11s -> 1s
    }

    public void testBigDecimalLibrary() throws IOException {
        testFile(".\\src\\javasrc\\BigDecimal.java.resource");
        //Done: 39s -> 3s
    }

    public void testLargeLibrary() throws IOException {
        testFile(".\\src\\javasrc\\Large.java.resource");
        //1320kb
        //Done: ??s -> 1m 49 s
    }

    public void test1mbLibrary() throws IOException {
        testFile(".\\src\\javasrc\\1mb.java.resource");
        //1000kb
        //Done: ??s -> 1m
    }

    private void testFile(String source) throws IOException {
        String entireFile = readEntireFile(source);
        
        //TokenReader[] kwReaders = buildKeywordReaders(Keywords.get());
                
        TokenReader[] tokenReaders = {
            new AnnotationReader(),
            new CharacterReader(),
            new EndOfLineCommentReader(),
            new FloatReader(),
            new IdentifierReader(),
            new IntReader(),
            new KeywordReader(),
            new NullReader(),
            new OperatorReader(),
            new SeparatorReader(),
            new StringReader(),
            new TraditionalCommentReader(),
            new WhitespaceReader(),
        };
        
       // tokenReaders = joinArrays(tokenReaders, kwReaders);

        SimpleLexer lexer = new SimpleLexer(entireFile, tokenReaders);
        
        String resultString = "";
        int tokensNum = 0;
        
        try {
            while(lexer.hasNextTokens()) {
                resultString += lexer.readNextToken().getText();
                tokensNum++;
            }
        } catch (SimpleLexer.UnknownTokenException e) {
            fail("Unknown token at: " + e.where + "\n");
        }
        
        System.out.println("Number of tokens in test: " + tokensNum + ".");
        assertEquals(entireFile, resultString);
        assertEquals(tokensNum >= entireFile.length()/30, true);
    }

    private String readEntireFile(String source) throws IOException {
        FileReader in = new FileReader(source);
        StringBuilder contents = new StringBuilder();
        char[] buffer = new char[4096];
        int read = 0;
        do {
            contents.append(buffer, 0, read);
            read = in.read(buffer);
        } while (read >= 0);
        System.out.println("Done reading file.");
        return contents.toString();
    }
    
    private TokenReader[] joinArrays(TokenReader[] array1, TokenReader[] array2) {
        TokenReader[] result = new TokenReader[array1.length + array2.length];
        System.arraycopy(array1, 0, result, 0, array1.length);
        System.arraycopy(array2, 0, result, array1.length, array2.length);
        System.out.println("Done joining arrays.");
        return result;
    }
    
    private TokenReader[] buildKeywordReaders (String[] keywords) {
        TokenReader[] kwReaders = new TokenReader[keywords.length];
        for(int i = 0; i < keywords.length; i++) {
            kwReaders[i] = new WordReader(keywords[i], false);
        }
        System.out.println("Done building keyword readers.");
        return kwReaders;
    }
}
