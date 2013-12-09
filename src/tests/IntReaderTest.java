package tests;

import junit.framework.TestCase;
import lexer.IntReader;
import lexer.Token;

public class IntReaderTest extends TestCase {
    private IntReader reader = new IntReader();

    public void testNotANumbers() {
        //generic
        assertNull(reader.tryReadToken(""));
        assertNull(reader.tryReadToken("qwerty"));
        assertNull(reader.tryReadToken("_2_"));
        assertNull(reader.tryReadToken("32_"));
        assertNull(reader.tryReadToken("321w"));
        assertNull(reader.tryReadToken("32a5l"));
        //hex
        assertNull(reader.tryReadToken("0x"));
        assertNull(reader.tryReadToken("0xE_l"));
        //binary
        assertNull(reader.tryReadToken("0bp"));
        assertNull(reader.tryReadToken("0b____"));
        //octal
        assertNull(reader.tryReadToken("032p"));
        assertNull(reader.tryReadToken("0___q"));
        assertNull(reader.tryReadToken("00_"));
    }

    public void testDecimal() {
        checkInt("0", "0", 0);
        checkInt("0;", "0", 0);
        checkInt("0+", "0", 0);
        checkInt("0l qwe", "0l", 0l);
        checkInt("1", "1", 1);
        checkInt("32_15", "32_15", 3215);
        checkInt("123l", "123l", 123L);
        checkInt("123 qwe", "123", 123);
        checkInt("5+6", "5", 5);
        checkInt("9223372036854775807L", "9223372036854775807L", 9223372036854775807L);
    }

    public void testHex() {
        checkInt("0x10", "0x10", 16);
        checkInt("0X0F+0x1", "0X0F", 15);
        checkInt("0XF_0 asdf", "0XF_0", 240);
        checkInt("0xAl", "0xAl", 10L);
        checkInt("0XBL", "0XBL", 11L);
        checkInt("0xFFFFFFFF;", "0xFFFFFFFF", 4294967295L);
    }

    public void testBinary() {
        checkInt("0b10", "0b10", 2);
        checkInt("0b01", "0b01", 1);
        checkInt("0b0_1", "0b0_1", 1);
        checkInt("0B11l", "0B11l", 3L);
        checkInt("0b1L", "0b1L", 1L);
    }

    public void testOct() {
        checkInt("010", "010", 8);
        checkInt("001", "001", 1);
        checkInt("0__5", "0__5", 5);
        checkInt("009", "00", 0);
        checkInt("018", "01", 1);
        checkInt("010l", "010l", 8L);
        checkInt("011L", "011L", 9L);
    }

    private void checkInt(String input, String expectedToken, int expectedValue) {
        Token token = reader.tryReadToken(input);
        assertEquals("i", token.getType());
        assertEquals(expectedToken, token.getText());
        assertEquals(expectedValue, token.getValue());
    }

    private void checkInt(String input, String expectedToken, long expectedValue) {
        Token token = reader.tryReadToken(input);
        assertEquals("l", token.getType());
        assertEquals(expectedToken, token.getText());
        assertEquals(expectedValue, token.getValue());
    }
}
