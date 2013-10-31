package lexer;

public class WhitespaceReader extends TokenReader {
	/**
	 * Читает с начала строки максимальное количество пробельных символов.
	 * 
	 * @return Возвращает прочитанный префикс строки.
	 */
	public Token tryReadToken(String input) {
		int len = input.length();
		if (len == 0 || !Character.isWhitespace(input.charAt(0))) return null;
		int i = 0;
		while (i < len && Character.isWhitespace(input.charAt(i)))
			i++;
		return new Token("ws", input.substring(0, i));
	}
}
