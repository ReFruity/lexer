package lexer;

public class SimpleLexer {
    private String sourceString;
    private TokenReader[] tokenReadersArray;

    public SimpleLexer(String sourceString, TokenReader... tokenReadersArray) {
        this.sourceString = sourceString;
        this.tokenReadersArray = tokenReadersArray;
    }

    public Token readNextToken() {
        Token maxToken = new Token("e", "");
        if (sourceString.isEmpty())
            //throw new Exception("wqe");
            for (TokenReader i : tokenReadersArray) {
                Token currentToken = i.tryReadToken(sourceString);
                if (currentToken.getText().length() >= maxToken.getText().length()) {
                    maxToken = currentToken;
                }
            }
        if (maxToken != null) {
            sourceString = sourceString.substring(maxToken.getText().length());
            return maxToken;
        } else
            return null;
    }

    public boolean hasNextTokens() {
        return !sourceString.isEmpty();
    }
}
