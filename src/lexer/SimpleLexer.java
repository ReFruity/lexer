package lexer;

public class SimpleLexer {
    private String sourceString;
    private TokenReader[] tokenReadersArray;
    private int shift;

    public SimpleLexer(String sourceString, TokenReader... tokenReadersArray) {
        this.sourceString = sourceString;
        this.tokenReadersArray = tokenReadersArray;
        this.shift = 0;
    }

    public Token readNextToken() throws UnknownTokenException {
        Token maxToken = new Token("e", "");
        for (TokenReader i : tokenReadersArray) {
            Token currentToken = i.tryReadToken(sourceString.substring(shift));
            if (currentToken != null && currentToken.getText().length() >= maxToken.getText().length()) {
                maxToken = currentToken;
            }
        }
        if (maxToken.getText().length() == 0)
            throw new UnknownTokenException(sourceString.substring(shift));
        shift += maxToken.getText().length();
        return maxToken;
    }

    public boolean hasNextTokens() {
        return shift != sourceString.length();
    }

    public class UnknownTokenException extends Exception {
        public String where;
        public UnknownTokenException(String where) {
            super("Unable to read next token from: '" + where + "'.");
            this.where = where;
        }
    }
}