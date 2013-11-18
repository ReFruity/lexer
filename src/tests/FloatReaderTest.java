package tests;

import junit.framework.TestCase;
import lexer.FloatReader;
import lexer.Token;

public class FloatReaderTest extends TestCase {
    private FloatReader reader = new FloatReader();

    public void testNotFloats() {
        assertNull(reader.tryReadToken(""));
        assertNull(reader.tryReadToken("."));
        assertNull(reader.tryReadToken("._5"));
        assertNull(reader.tryReadToken("0"));
    }

    public void testFloats() {
        checkNumber("1e1f", new Token("f", "1e1f", 1e1f));
    }

    public void testDoubles() {
        checkNumber("1e1", new Token("d", "1e1", 1e1));
    }

    private void checkNumber(String input, Token expectedToken) {
        assertEquals(expectedToken, reader.tryReadToken(input));
    }
}
//        Examples of float literals:
//
//        1e1f    2.f    .3f    0f    3.14f    6.022137e+23f
//        Examples of double literals:
//
//        1e1    2.    .3    0.0    3.14    1e-9d    1e137
