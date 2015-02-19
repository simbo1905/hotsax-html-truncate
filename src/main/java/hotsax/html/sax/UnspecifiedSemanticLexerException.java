
package hotsax.html.sax;


/** 
 * Exception thrown if no specific lexer exists to continue parsing the input.
 * This should never happen unless the default lexer is removed from the lexerHash. 
 */
public class UnspecifiedSemanticLexerException extends HtmlParserException {


	/**
	 * Construct a new UnspecifiedSemanticLexerException Exception
	 * @param msg The message to display when this exception is caught
	 */
	public UnspecifiedSemanticLexerException(String msg) {
		super(msg);
	}
}
