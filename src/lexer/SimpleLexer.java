package lexer;

public class SimpleLexer {
    private String sourceString;
    private TokenReader[] tokenReadersArray;
    private int offset;

    public SimpleLexer(String sourceString, TokenReader... tokenReadersArray) {
        this.sourceString = sourceString;
        this.tokenReadersArray = tokenReadersArray;
        offset = 0;
    }

    public Token readNextToken() throws UnknownTokenException {
        Token maxToken = new Token("e", "");
        //String suffix = sourceString.substring(offset);
        boolean tokenWasSuccessfullyRead = false;
        for (TokenReader i : tokenReadersArray) {
            Token currentToken = i.tryReadToken(sourceString, offset);
            if (currentToken != null && currentToken.getText().length() > maxToken.getText().length()) {
                maxToken = currentToken;
                tokenWasSuccessfullyRead = true;
            }
        }
        if (!tokenWasSuccessfullyRead){
            throw new UnknownTokenException(sourceString);
        }
        offset += maxToken.getText().length();
        return maxToken;
    }

    public boolean hasNextTokens() {
        return offset != sourceString.length();
    }

    public class UnknownTokenException extends Exception {
        public String where;
        public UnknownTokenException(String where) {
            super("Unable to read next token from: '" + where + "'.");
            this.where = where;
        }
    }
}