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
        assertNull(reader.tryReadToken("12e"));
    }

    public void testFloats() {
        checkNumber("1e1f", new Token("f", "1e1f", 1e1f));
        checkNumber("2.f", new Token("f", "2.f", 2.f));
        checkNumber(".3f", new Token("f", ".3f", .3f));
        checkNumber("0f", new Token("f", "0f", 0f));
        checkNumber("3.14f", new Token("f", "3.14f", 3.14f));
        checkNumber("6.022137e+23f", new Token("f", "6.022137e+23f", 6.022137e+23f));
    }

    public void testDoubles() {
        checkNumber("1e1", new Token("d", "1e1", 1e1));
        checkNumber("2.", new Token("d", "2.", 2.d));
        checkNumber(".3", new Token("d", ".3", .3));
        checkNumber("0.0", new Token("d", "0.0", 0d));
        checkNumber("3.14", new Token("d", "3.14", 3.14));
        checkNumber("1e-9d", new Token("d", "1e-9d", 1e-9d));
        checkNumber("1e137", new Token("d", "1e137", 1e137));
    }

    public void testBackOff() {
        checkNumber("1.23e", new Token("d", "1.23", 1.23));
        checkNumber("5.6e-u", new Token("d", "5.6", 5.6));
        checkNumber("5.6fe", new Token("f", "5.6f", 5.6f));
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
