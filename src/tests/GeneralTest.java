package tests;

import junit.framework.TestCase;
import lexer.*;

import java.io.FileReader;
import java.io.IOException;

public class GeneralTest extends TestCase {
    public void testTokenReader() throws IOException {
        testFile(".\\src\\javasrc\\TokenReader.java.resource");
        //Done: 0.115s -> 0.063 -> 0.073
    }
    
    public void testCharacterLibrary() throws IOException {
        testFile(".\\src\\javasrc\\Character.java.resource");
        //Done: 1m 22s -> 5s -> 3s
    }

    public void testFilesLibrary() throws IOException {
        testFile(".\\src\\javasrc\\Files.java.resource");
        //Done: 11s -> 1s -> 1s
    }

    public void testBigDecimalLibrary() throws IOException {
        testFile(".\\src\\javasrc\\BigDecimal.java.resource");
        //Done: 39s -> 3s -> 2s
    }

    public void testLargeLibrary() throws IOException {
        testFile(".\\src\\javasrc\\Large.java.resource");
        //1320kb
        //Done: ??s -> 1m 49s -> 1m 29s
    }

    public void test1mbLibrary() throws IOException {
        testFile(".\\src\\javasrc\\1mb.java.resource");
        //1000kb
        //Done: ??s -> 1m -> 44s
    }

    public void testAutomatonReaders() throws IOException {
        testFile(".\\src\\javasrc\\Automaton.java.resource");
        //560kb
        //Done: ??s -> 52s;
        //52s
    }

    public void testNonAutomatonReaders() throws IOException {
        testFile(".\\src\\javasrc\\NonAutomaton.java.resource");
        //532kb
        //Done: ??s -> 2m 42s
        //2m 49s
    }
    
    private void testFile(String source) throws IOException {
        String entireFile = readEntireFile(source);
                
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
        assertEquals(true, tokensNum >= entireFile.length()/30);
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
}
