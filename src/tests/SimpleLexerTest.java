package tests;

import junit.framework.TestCase;
import lexer.*;

import java.util.ArrayList;

public class SimpleLexerTest extends TestCase {
    /*
     * types are:
     *	"id" - identifier
     *	"i" - int
     *	"l" - long
     *	"ws" - whitespace
     *	"kw" - keyword from wordreader
     */
    // No exceptions
    public void testIdReader() throws SimpleLexer.UnknownTokenException {
        Tester idTester = new Tester(new IdentifierReader());
        idTester.checkReadNextToken("qwe42+'", new Token("id", "qwe42"));
    }

    public void testIntReader() throws SimpleLexer.UnknownTokenException {
        Tester intTester = new Tester(new IntReader());
        intTester.checkReadNextToken("0X14 5'", new Token("i", "0X14", 20));
    }

    public void testWordReader() throws SimpleLexer.UnknownTokenException {
        Tester kwTester = new Tester(new WordReader("keyword", false));
        kwTester.checkReadNextToken("keywordandanotherword", new Token("kw", "keyword"));
    }

    public void testConcurrentReaders() throws SimpleLexer.UnknownTokenException {
        Tester concurrentTester = new Tester(new WhitespaceReader(), new WordReader("identifier", false), new IdentifierReader());
        concurrentTester.checkOutput("some identifier",
                new Token("id", "some"), new Token("ws", " "),
                new Token("id", "identifier")
        );
        concurrentTester = new Tester(new WhitespaceReader(), new IdentifierReader(), new WordReader("keyword", false));
        concurrentTester.checkOutput("some keyword",
                new Token("id", "some"), new Token("ws", " "),
                new Token("kw", "keyword")
        );
    }

    public void testSimpleLexer() throws SimpleLexer.UnknownTokenException {
        Tester legitTester = new Tester(
                new IdentifierReader(), new IntReader(), new WhitespaceReader(),
                new WordReader("word", false), new WordReader("butanumber", true)
        );
        legitTester.checkOutput("0x42 isnta word ButANumber 6_6L",
                new Token("i", "0x42", 66), new Token("ws", " "),
                new Token("id", "isnta"), new Token("ws", " "),
                new Token("kw", "word"), new Token("ws", " "),
                new Token("kw", "ButANumber"), new Token("ws", " "),
                new Token("l", "6_6L", 66L)
        );
    }
    // With exceptions
    public void testEmptyString() {
        Tester emptyTester = new Tester(new WhitespaceReader());
        try {
            emptyTester.checkReadNextToken("", new Token("error", "wrong token"));
            fail("Empty string did not invoke any exceptions.");
        }
        catch (SimpleLexer.UnknownTokenException ex) {
            assertEquals("", ex.where);
        }
    }

    public void testExceptions() {
        Tester exTester = new Tester(new IntReader(), new WhitespaceReader());
        try {
            exTester.checkOutput("05\tabc",
                    new Token("i", "05", 5), new Token("ws", "\t"),
                    new Token("id", "abc")
            );
            fail("Exception tester did not invoke any exceptions.");
        }
        catch (SimpleLexer.UnknownTokenException ex) {
            assertEquals("abc", ex.where);
        }
    }

    private class Tester {
        private SimpleLexer lexer;
        TokenReader[] tokenReadersArray;

        public Tester(TokenReader... tokenReadersArray) {
            this.tokenReadersArray = tokenReadersArray;
        }

        private void checkOutput(String input, Token... expectedTokensArray)
                                                throws SimpleLexer.UnknownTokenException {
            lexer = new SimpleLexer(input, tokenReadersArray);
            ArrayList<Token> readTokensList = new ArrayList<Token>();
            while (lexer.hasNextTokens())
                readTokensList.add(lexer.readNextToken());

            Token[] readTokensArray = new Token[readTokensList.size()];
            readTokensList.toArray(readTokensArray);
            for (int i = 0; i < expectedTokensArray.length; i++)
                assertEquals(expectedTokensArray[i], readTokensArray[i]);
        }

        private void checkReadNextToken(String input, Token expectedToken)
                                                throws SimpleLexer.UnknownTokenException {
            lexer = new SimpleLexer(input, tokenReadersArray);
            assertEquals(expectedToken, lexer.readNextToken());
        }
    }
}