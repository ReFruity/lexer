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
    public void testSimpleLexer() {
        Tester legitTester = new Tester(
                new IdentifierReader(), new IntReader(), new WhitespaceReader(),
                new WordReader("word", false), new WordReader("butanumber", true)
        );
        try {
            legitTester.checkOutput("0x42 isnta word ButANumber 6_6L",
                    new Token("i", "0x42", 66), new Token("ws", " "),
                    new Token("id", "isnta"), new Token("ws", " "),
                    new Token("kw", "word"), new Token("ws", " "),
                    new Token("kw", "ButANumber"), new Token("ws", " "),
                    new Token("l", "6_6L", 66L)
            );

            legitTester.checkOutput("");
        }
        catch (SimpleLexer.UnknownTokenException ex) {
              fail();
        }
    }

    public void testExceptions() {
        Tester exTester = new Tester(new IntReader(), new WhitespaceReader());
        Tester exTester2 = new Tester(new IdentifierReader(), new WhitespaceReader(), new IntReader());
        try {
            exTester.checkOutput("05 abc",
                    new Token("i", "05", 5), new Token("ws", " "),
                    new Token("id", "abc")
            );

            fail();
        }
        catch (SimpleLexer.UnknownTokenException ex) {
            assertEquals("abc", ex.where);
        }
        try {
            exTester2.checkOutput("  Id42 0B1 +",
                    new Token("ws", " "), new Token("ws", " "),
                    new Token("id", "Id42"), new Token("ws", " "),
                    new Token("i", "0B1", 1), new Token("ws", " ")
            );

            fail();
        }
        catch (SimpleLexer.UnknownTokenException ex) {
            assertEquals("+", ex.where);
        }
    }

    private class Tester {
        private SimpleLexer lexer;
        TokenReader[] tokenReadersArray;

        public Tester(TokenReader... tokenReadersArray) {
            this.tokenReadersArray = tokenReadersArray;
        }

        private void checkOutput(String input, Token... expectedTokensArray) throws SimpleLexer.UnknownTokenException {
            lexer = new SimpleLexer(input, tokenReadersArray);
            ArrayList<Token> readTokensList = new ArrayList<Token>();
            while (lexer.hasNextTokens())
                readTokensList.add(lexer.readNextToken());

            Token[] readTokensArray = new Token[readTokensList.size()];
            readTokensList.toArray(readTokensArray);
            for (int i = 0; i < expectedTokensArray.length; i++)
                assertEquals(expectedTokensArray[i], readTokensArray[i]);
        }
    }
}