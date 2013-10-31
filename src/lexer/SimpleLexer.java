package lexer;

import java.util.ArrayList;

public class SimpleLexer {
	private String sourceString;
	private ArrayList<TokenReader> tokenReadersList;
	
	public SimpleLexer(String sourceString, ArrayList<TokenReader> tokenReadersList) {
		this.sourceString = sourceString;
		this.tokenReadersList = tokenReadersList;
	}
	
	public Token readNextToken() {
		Token maxToken = new Token ("e", "");
		for(TokenReader i : tokenReadersList) {
            int a = 5;
			Token currentToken = i.tryReadToken(sourceString);
			if(currentToken != null && currentToken.getText().length() >= maxToken.getText().length()) {
				maxToken = currentToken;
			}
		}
		if(maxToken != null) {
			sourceString = sourceString.substring(maxToken.getText().length());
			return maxToken;
		}
		else
			return null;
	}
	
	public boolean hasNextTokens() {
		return !sourceString.isEmpty();
	}
}
