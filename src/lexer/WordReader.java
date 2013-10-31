package lexer;

public class WordReader extends TokenReader {
	private final String word;
	private final boolean ignoreCase;

	// TODO ������ 1: �������� �������� ignoreCase ������������������ � ��������
	public WordReader(String word, boolean ignoreCase) {
		this.word = word;
		this.ignoreCase = ignoreCase;
	}
	
	public Token tryReadToken(String input) {
		if (this.ignoreCase == true) {
			input = input.toLowerCase();
			if (input.startsWith(word.toLowerCase()))
				return new Token("kw", word);
		}
		else if (input.startsWith(word))
			return new Token("kw", word);
		return null;
	}
	public static void main(String[] args) {
		WordReader reader = new WordReader("hello", true);
		WordReader reader2 = new WordReader("h", false);
		System.out.println("Testing WordReader...");
		test(reader, "", null);
		test(reader, "H", null);
		test(reader, "Hello world", "hello");
		test(reader, "hell", null);
		test(reader, "hello world", "hello");
		System.out.println("---------");
		test(reader2, "", null);
		test(reader2, "H", null);
		test(reader2, " Hello world", " ");
		test(reader2, "hell", null);
		test(reader2, "hello world", "hello");
		System.out.println("Testing finished");
	}

	public static void test(WordReader r, String input, String expected) {
		Token actualToken = r.tryReadToken(input);
		if (expected == null) {
			if (actualToken != null)
				System.out.println("ERROR: on \"" + input + "\" read \""
						+ actualToken + "\"");
		} else {
			if (!expected.equals(actualToken.getText()))
				System.out.println("ERROR: on \"" + input + "\" expected \""
						+ expected + "\" but was \"" + actualToken + "\"");
		}
	}

}
