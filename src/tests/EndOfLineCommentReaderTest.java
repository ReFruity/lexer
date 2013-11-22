package tests;

import junit.framework.TestCase;
import lexer.EndOfLineCommentReader;
import lexer.Token;

public class EndOfLineCommentReaderTest extends TestCase {
    EndOfLineCommentReader reader = new EndOfLineCommentReader();

    public void testNotComments() {
        assertNull(reader.tryReadToken(""));
        assertNull(reader.tryReadToken("/"));
        assertNull(reader.tryReadToken("/\n"));
        assertNull(reader.tryReadToken("qwe"));
        assertNull(reader.tryReadToken("/qwe"));
    }

    public void testLinesWithTerminator() {
        checkLine("//comment\ntext", "//comment");
        checkLine("//comment\n", "//comment");
        checkLine("//\n", "//");
    }

    public void testLinesWithOutTerminator() {
        checkLine("//", "//");
        checkLine("//abc", "//abc");
    }

    private void checkLine(String input, String expectedText) {
        Token expectedToken = new Token("eolc", expectedText);
        assertEquals(expectedToken, reader.tryReadToken(input));
    }
}
