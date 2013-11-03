package tests;

import junit.framework.TestCase;
import lexer.IdentifierReader;
import lexer.Token;

public class IdentifierReaderTest extends TestCase {
    private IdentifierReader reader = new IdentifierReader();

    public void testNulls() {
        assertNull(reader.tryReadToken(""));
        assertNull(reader.tryReadToken("234"));
    }

    public void testIdReader() {
        check("a das", "a");
        check("q51we-das", "q51we");
    }

    private void check(String input, String expected) {
        Token token = reader.tryReadToken(input);
        assertEquals("id", token.getType());
        assertEquals(expected, token.getText());
    }
}
