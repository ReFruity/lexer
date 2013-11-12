package tests;

import junit.framework.TestCase;
import lexer.FloatReader;

public class FloatReaderTest extends TestCase {
    private FloatReader reader = new FloatReader();

    public void testNotANumbers() {
        assertNull(reader.tryReadToken(""));
        assertNull(reader.tryReadToken(".5"));
    }

    public void testFloat() {

    }

    public void testDouble() {

    }
}
