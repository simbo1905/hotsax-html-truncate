
package hotsax.html.sax;

import java.io.*;

public interface SemanticLexer {

    /**
     *	Wrapper for jflex generated yylex() method.
     *  @return the HtmlParser.XXXX token id.
	 *  @exception IOException thrown if the input could not be read
     */
	public int _yylex() throws IOException;

    /**
     * Ser the Reader for the lexer.
	 * @param r Reader to set
     */
	public void setReader(Reader r);

    /**
     * Get the current Reader
     * @return the Reader for this SemanticLexer
     */
	public Reader getReader();

    /**
     * Set the Lexer state. Usually called by the HtmlParser and set to
     * HtmlLexer.YYINITIAL
     * @param state the state to set/reset the lexer state to  
     */
	public void yybegin(int state);

    /**
     * Sets the buffer from another SemanticLexer, probably the default one.
     * @param lexer The SemanticLexer to read the buffer from.
     */
	public void setBuffer(SemanticLexer lexer);

    /**
     * Return the character buffer of the lexer. Exposes private yy_buffer
     * @return the yy_buffer 
     */
	public char[] getyyBuffer();

    /**
     * Return the current position of the lexer. Exposes private yy_currentPos
     * @return the yy_currentPos
     */
	public int getyyCurrentPos();

    /**
     * Return the marked position of the lexer. Exposes private yy_markedPos
     * @return the yy_markedPos
     */
	public int getyyMarkedPos();

    /**
     * Return the pushback position of the lexer. Exposes private yy_pushbackPos
     * @return the yy_pushbackPos
     */
	public int getyyPushbackPos();

    /**
     * Return the end read of the buffer of the lexer. Exposes private yy_endRead
     * @return the yy_endRead
     */
	public int getyyEndRead();

    /**
     * Return the start read of the buffer of the lexer. Exposes private yy_startRead
     * @return the yy_startRead
     */
	public int getyyStartRead();

    /**
	 * Debugs the yy_buffer. Prints the content of the buffer on System.out
     */
	public void printBuffer();
}
