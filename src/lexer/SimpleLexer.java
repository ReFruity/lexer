package lexer;

public class SimpleLexer {
    private final String sourceString;
    private final int sourceLen;
    private TokenReader[] tokenReadersArray;
    private int offset;

    public SimpleLexer(String sourceString, TokenReader... tokenReadersArray) {
        this.sourceString = sourceString;
        this.sourceLen = sourceString.length();
        this.tokenReadersArray = tokenReadersArray;
        offset = 0;
    }

    public Token readNextToken() throws UnknownTokenException {
        Token maxToken = null;
//        String suffix = sourceString.substring(offset);
        for (TokenReader i : tokenReadersArray) {
            Token currentToken = i.tryReadToken(sourceString, offset);
//            Token currentToken = i.tryReadToken(suffix);
            if (currentToken != null && 
                    (maxToken == null || currentToken.getText().length() > maxToken.getText().length())) {
                maxToken = currentToken;
            }
        }
        if (maxToken == null){
            throw new UnknownTokenException(sourceString.substring(offset));
        }
        offset += maxToken.getText().length();
        return maxToken;
    }

    public boolean hasNextTokens() {
        return offset != sourceLen;
    }

    public class UnknownTokenException extends Exception {
        public String where;
        public UnknownTokenException(String where) {
            super("Unable to read next token from: '" + where + "'.");
            this.where = where;
        }
    }
}