package lexer;

abstract public class TokenReader {
    abstract Token tryReadToken(String input);
}
