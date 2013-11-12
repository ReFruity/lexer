package tests;

import junit.framework.TestCase;
import lexer.TraditionalCommentReader;
import lexer.Token;

public class TraditionalCommentReaderTest extends TestCase {
    TraditionalCommentReader reader = new TraditionalCommentReader();

    public void testNotComments() {
        assertNull(reader.tryReadToken(""));
        assertNull(reader.tryReadToken("*/"));
        assertNull(reader.tryReadToken("/\n"));
        assertNull(reader.tryReadToken("qwe"));
        assertNull(reader.tryReadToken("//qwe"));
    }

    public void testLinesWithTerminator() {
        checkLine("/**comment\nte**/xt", "/**comment\nte**/");
        checkLine("/*comment */\n", "/*comment */");
        checkLine("/*/*/", "/*/*/");
    }

    public void testLinesWithOutTerminator() {
        checkLine("/*", "/*");
        checkLine("/*abc\nj*", "/*abc\nj*");
    }

    private void checkLine(String input, String expectedText) {
        Token expectedToken = new Token("tc", expectedText);
        assertEquals(expectedToken, reader.tryReadToken(input));
    }

}
