package tests;

import junit.framework.TestCase;
import lexer.*;

public class SimpleLexerTest extends TestCase {
    /*
     * types are:
     *	"id" - identifier
     *	"i" - int
     *	"l" - long
     *	"ws" - whitespace
     *	"kw" - keyword from wordreader
     */
    public void testLexer() {

    }

    public void testReadNextToken() {
        checkNextToken("123 abc", "123", "i");
        checkNextToken("0x5_FL abc", "0x5_FL", "l");
        checkNextToken("qwer 5", "qwer", "id");
        checkNextToken("wordify  5", "wordify", "id");
        checkNextToken(" 	qwer 5", " 	", "ws"); //it's space and tab
        checkNextToken("word ify  5", "word", "kw");
        //assert results are null
        checkNextToken("");
        checkNextToken("-da");
    }

    public void testHasNextTokens() {
        checkHasNextTokens("word", true);
        checkHasNextTokens(" ", true);
        checkHasNextTokens("+", false);
        checkHasNextTokens("", false);
    }

    private class Tester {
        private SimpleLexer lexer;

        public Tester(String input) {
            lexer = new SimpleLexer(input,
                    new IdentifierReader(), new IntReader(), new WhitespaceReader(), new WordReader("word", false)
            );
        }

        public Token readNextToken() {
            return lexer.readNextToken();
        }

        public boolean hasNextTokens() {
            return lexer.hasNextTokens();
        }
    }

    private void checkNextToken(String input, String expectedText, String expectedType) {
        Tester tester = new Tester(input);
        Token readToken = tester.readNextToken();
        assertEquals(expectedText, readToken.getText());
        assertEquals(expectedType, readToken.getType());
    }

    private void checkNextToken(String input) {
        Tester nullTester = new Tester(input);
        assertNull(nullTester.readNextToken());
    }

    private void checkHasNextTokens(String input, boolean expected) {
        Tester presenceTester = new Tester(input);
        assertEquals(expected, presenceTester.hasNextTokens());
    }

}
