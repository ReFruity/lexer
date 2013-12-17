package lexer;

public class IntReader extends TokenReader {
    private enum Radix {
        BINARY(3, 2), OCTAL(2, 8), DECIMAL(1, 10), HEXADECIMAL(3, 16), NAN(0, 0);
        private int shift, representation;

        private Radix(int shift, int representation) {
            this.shift = shift;
            this.representation = representation;
        }
    }

    private boolean isValidSymbol(Radix r, char x) {
        switch (r) {
            case BINARY:
                return x == '0' || x == '1';
            case OCTAL:
                return '0' <= x && x <= '7';
            case DECIMAL:
                return Character.isDigit(x);
            case HEXADECIMAL:
                x = Character.toUpperCase(x);
                return Character.isDigit(x) || ('A' <= x && x <= 'F');
            default:
                return false;
        }
    }

    private boolean isValidSymbolOrUnderscore(Radix r, char x) {
        return isValidSymbol(r, x) || x == '_';
    }

    private Radix determineRadix(String input, int offset) {
        Radix plausibleRadix;
        int len = input.length() - offset;

        if (len == 0 || !Character.isDigit(input.charAt(offset)))
            return Radix.NAN;

        if (input.charAt(offset) == '0') {
            if (len == 1) {
                return Radix.DECIMAL;
            }
            
            char secondChar = Character.toUpperCase(input.charAt(offset + 1));

            if (!(Character.isDigit(secondChar) || Character.isLetter(secondChar))) {
                return Radix.DECIMAL;
            }
            
            if (secondChar == 'B') { //binary
                if (len == 2 || !isValidSymbol(Radix.BINARY, input.charAt(offset + 2)))
                    return Radix.NAN;
                plausibleRadix = Radix.BINARY;
            } else if (isValidSymbolOrUnderscore(Radix.OCTAL, secondChar)) { //octal
                plausibleRadix = Radix.OCTAL;
            } else if (secondChar == 'L') { //decimal long 0
                plausibleRadix = Radix.DECIMAL;
            } else if (secondChar == 'X') { //hex
                if (len == 2 || !isValidSymbol(Radix.HEXADECIMAL, input.charAt(offset + 2)))
                    return Radix.NAN;
                plausibleRadix = Radix.HEXADECIMAL;
            } else
                return Radix.NAN;
        } else { //decimal
            plausibleRadix = Radix.DECIMAL;
        }

        return plausibleRadix;
    }

    public Token tryReadToken(String input, int offset) {
        int len = input.length();

        Radix plausibleRadix = determineRadix(input, offset);

        if (plausibleRadix == Radix.NAN)
            return null;

        int i = offset + plausibleRadix.shift;
        while (i < len && isValidSymbolOrUnderscore(plausibleRadix, input.charAt(i)))
            i++;
        if (i < len && Character.isLetter(input.charAt(i)))
            i++;

        String javaNum = input.substring(offset, i);
        char lastChar = Character.toUpperCase(javaNum.charAt(i - 1 - offset));
        if (i > offset + 2 && lastChar == 'L' && javaNum.charAt(i - 2 - offset) == '_') //underscore just before 'L'
            return null;
        String num = javaNum.replaceAll("_", "").substring(plausibleRadix.shift - 1);

        if (isValidSymbol(plausibleRadix, lastChar))
            try {
                return new Token("i", javaNum, Integer.parseInt(num, plausibleRadix.representation));
            }
            catch (NumberFormatException e) {
                return new Token ("i", javaNum, (int)Long.parseLong(num, plausibleRadix.representation));
            }
        else if (lastChar == 'L') {
            num = num.substring(0, num.length() - 1);
            return new Token("l", javaNum, Long.parseLong(num, plausibleRadix.representation));
        }

        return null;
    }
}
