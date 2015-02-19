/* The following code was generated by JFlex 1.3.2 on 8/10/01 4:37 PM */


package hotsax.html.sax;

import java.io.*;
import java.util.*;


/**
 * This class is a scanner generated by 
 * <a href="http://www.jflex.de/">JFlex</a> 1.3.2
 * on 8/10/01 4:37 PM from the specification file
 * <tt>file:/home/edh/sourceforge/HotSAX/./build/src/hotsax/html/sax/ScriptLexer.flex</tt>
 */
class ScriptLexer implements SemanticLexer {

  /** This character denotes the end of file */
  final public static int YYEOF = -1;

  /** initial size of the lookahead buffer */
  final private static int YY_BUFFERSIZE = 16384;

  /** lexical states */
  final public static int ERROR_RECOVER = 2;
  final public static int IGNORE_CDATA = 1;
  final public static int YYINITIAL = 0;

  /** 
   * Translates characters to character classes
   */
  final private static char [] yycmap = {
     0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0, 
     0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0, 
     0,  2,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  3,  0,  5, 
     0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  1,  0,  4,  0, 
     0, 14,  0,  7, 13,  0,  0,  0,  0,  9,  0,  0,  0,  0,  0,  0, 
    10,  0,  8,  6, 11,  0,  0,  0,  0,  0,  0, 12,  0, 15,  0,  0, 
     0, 14,  0,  7, 13,  0,  0,  0,  0,  9,  0,  0,  0,  0,  0,  0, 
    10,  0,  8,  6, 11,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0, 
     0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0, 
     0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0, 
     0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0, 
     0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0, 
     0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0, 
     0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0, 
     0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0, 
     0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0
  };

  /** 
   * Translates a state to a row index in the transition table
   */
  final private static int yy_rowMap [] = { 
        0,    16,    32,    48,    64,    80,    48,    96,   112,   128, 
      144,   160,   176,   192,   208,   224,   240,   256,   144,   144, 
      272,   288,   304,   320,   336,   352,   368,   384,   400,   416, 
      144,   144
  };

  /** 
   * The packed transition table of the DFA (part 0)
   */
  final private static String yy_packed0 = 
    "\1\4\1\5\1\4\1\6\1\7\13\4\17\10\1\11"+
    "\4\12\1\13\13\12\1\4\1\0\1\4\1\0\14\4"+
    "\2\0\1\14\2\0\1\15\15\0\1\16\14\0\17\10"+
    "\20\0\1\17\4\12\1\0\13\12\23\0\1\20\10\0"+
    "\1\21\11\0\1\22\15\0\1\23\17\0\1\24\16\0"+
    "\1\23\23\0\1\25\17\0\1\26\25\0\1\27\12\0"+
    "\1\30\25\0\1\31\12\0\1\32\21\0\1\33\16\0"+
    "\1\34\23\0\1\35\14\0\1\36\20\0\1\37\7\0"+
    "\1\40\13\0";

  /** 
   * The transition table of the DFA
   */
  final private static int yytrans [] = yy_unpack();


  /* error codes */
  final private static int YY_UNKNOWN_ERROR = 0;
  final private static int YY_ILLEGAL_STATE = 1;
  final private static int YY_NO_MATCH = 2;
  final private static int YY_PUSHBACK_2BIG = 3;

  /* error messages for the codes above */
  final private static String YY_ERROR_MSG[] = {
    "Unkown internal scanner error",
    "Internal error: unknown state",
    "Error: could not match input",
    "Error: pushback value was too large"
  };

  /**
   * YY_ATTRIBUTE[aState] contains the attributes of state <code>aState</code>
   */
  private final static byte YY_ATTRIBUTE[] = {
     1,  1,  1,  1,  1,  1,  1,  1,  1,  1,  9,  0,  0,  0,  0,  0, 
     0,  0,  9,  9,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  9,  9
  };

  /** the input device */
  private java.io.Reader yy_reader;

  /** the current state of the DFA */
  private int yy_state;

  /** the current lexical state */
  private int yy_lexical_state = YYINITIAL;

  /** this buffer contains the current text to be matched and is
      the source of the yytext() string */
  private char yy_buffer[] = new char[YY_BUFFERSIZE];

  /** the textposition at the last accepting state */
  private int yy_markedPos;

  /** the textposition at the last state to be included in yytext */
  private int yy_pushbackPos;

  /** the current text position in the buffer */
  private int yy_currentPos;

  /** startRead marks the beginning of the yytext() string in the buffer */
  private int yy_startRead;

  /** endRead marks the last character in the buffer, that has been read
      from input */
  private int yy_endRead;

  /** number of newlines encountered up to the start of the matched text */
  private int yyline;

  /** the number of characters up to the start of the matched text */
  private int yychar;

  /**
   * the number of characters from the last newline up to the start of the 
   * matched text
   */
  private int yycolumn; 

  /** 
   * yy_atBOL == true <=> the scanner is currently at the beginning of a line
   */
  private boolean yy_atBOL = true;

  /** yy_atEOF == true <=> the scanner is at the EOF */
  private boolean yy_atEOF;

  /** denotes if the user-EOF-code has already been executed */
  private boolean yy_eof_done;

  /* user code: */
	private HtmlParser yyparser;
	private StringBuffer text = new StringBuffer();
	private StringBuffer cdata = new StringBuffer();

	private Vector tokenQueue;

	protected boolean debug = false;
	public void setDebug(boolean debug) { this.debug = debug; }
	public boolean getDebug() { return debug; }

	public void p(String s) { System.out.println(s); }
	
	public boolean getEOF() { return yy_atEOF; }

	public ScriptLexer(Reader r, HtmlParser p)
	{
		this(r);
		yyparser = p;
		tokenQueue = new Vector();
	}

	// expected constructor to get called as an alternate SemanticLexer
	public ScriptLexer(HtmlParser p) {
		this(System.in);
		yyparser = p;
		tokenQueue = new Vector();
	}

	Lval lexer_yylval;
	Lval empty_yylval = new Lval("");
	private boolean first = false;
	private boolean last = false;

	public void yylexerror_reset() {
		System.err.println("char at : " + yycharat(yy_markedPos));
		yypushback(yylength() - 1);  // attempt at resetting this
	}

	/** 
	 *   mask the actual implementation of yylex to return the first SOF
	 *   and the final EOF. Marking the startDocument, EndDocuemnt events
	 *   Also catches supposedly unrecoverable Error. Forces new ERROR_RECOVER state.
     *   #return the token from yylex() - one of HtmlParser.XXXXX
	 */
	public int _yylex() 
		throws IOException
	{
		int token;

		lexer_yylval = empty_yylval; 
		try {
	
			if (tokenQueue.size() > 0) {
				Object o = tokenQueue.remove(0);
				if (o instanceof Lval) {
					setLval((Lval)o);
					o = tokenQueue.remove(0);
				}
				token = ((Integer)o).intValue();
		    }
			else {
				token = yylex();
				if (token == -2) { // then a force of returning the next item in the tokenQueue
					Object o = tokenQueue.remove(0);
					if (o instanceof Lval) {
						setLval((Lval)o);
						o = tokenQueue.remove(0);
					}
					token = ((Integer)o).intValue();
				}
			}
		}
		catch (Error err) {
			if (getDebug()) System.err.println("Caught error " + err.getMessage());
			yybegin(ERROR_RECOVER);
			if (yyparser != null)
				yyparser.yylval = empty_yylval;
			
			token = yylex(); // read ahead in ERROR_REOVER
		}		
		finally {
			cdata.setLength(0);
			text.setLength(0);
		}
		return token;
	}
	

	// set the LH side of the parser
	void setLval(String text)
	{
		lexer_yylval = new Lval(text);
			
		if (yyparser != null)
			yyparser.yylval = lexer_yylval;
	}

	void setLval(Attribute a)
	{
		lexer_yylval = new Lval(a);

		if (yyparser != null)
			yyparser.yylval = lexer_yylval;
	}

	void setLval(Lval l) {
		lexer_yylval = l;

		if (yyparser != null)
			yyparser.yylval = lexer_yylval;
	}


	/** Return the yy_reader for this class. Can be used to provide alternate scanner 
	  @return the Reader for this class */
	public Reader getReader() { return yy_reader; }
	public void setReader(Reader r) { yy_reader = r; }


	/**
  	 * Sets this lexer to the same yybuffer, and character positions as the other lexer
     */
	public void setBuffer(SemanticLexer lexer) {
		if (this.yy_buffer.length != lexer.getyyBuffer().length) {
      		char newBuffer[] = new char[lexer.getyyBuffer().length];
			this.yy_buffer = newBuffer;
		}
      	System.arraycopy(lexer.getyyBuffer(), 0, yy_buffer, 0, yy_buffer.length);
		this.yy_currentPos = lexer.getyyCurrentPos();
		this.yy_markedPos = lexer.getyyMarkedPos();
		this.yy_pushbackPos = lexer.getyyPushbackPos();
		this.yy_endRead = lexer.getyyEndRead();
		this.yy_startRead = lexer.getyyStartRead();
	}

	public char[] getyyBuffer() { return yy_buffer; }
	public int getyyCurrentPos() { return yy_currentPos; } 
	public int getyyMarkedPos() { return yy_markedPos; }
	public int getyyPushbackPos() { return yy_pushbackPos; }
	public int getyyEndRead() { return yy_endRead; }
	public int getyyStartRead() { return yy_startRead; }
	public void printBuffer() {
		for (int i = 0; i < yy_endRead; i++) {
			System.out.print(yy_buffer[i]);
		}
	}

  /**
   * Runs the scanner on input files.
   *
   * This main method is the debugging routine for the scanner.
   * It prints each returned token to System.out until the end of
   * file is reached, or an error occured.
   *
   * @param argv   the command line, contains the filenames to run
   *               the scanner on.
   */
  public static void main(String argv[]) {
    for (int i = 0; i < argv.length; i++) {
      ScriptLexer scanner = null;
      try {
        scanner = new ScriptLexer( new java.io.FileReader(argv[i]), (HtmlParser)null );
      }
      catch (java.io.FileNotFoundException e) {
        System.out.println("File not found : \""+argv[i]+"\"");
        System.exit(1);
      }
      catch (java.io.IOException e) {
        System.out.println("Error opening file \""+argv[i]+"\"");
        System.exit(1);
      }
      catch (ArrayIndexOutOfBoundsException e) {
        System.out.println("Usage : java HtmlLexer <inputfile>");
        System.exit(1);
      }

	  scanner.setDebug(true);
      try {
        do {
          System.out.println(scanner._yylex() + " : " + scanner.yytext() +  " lval:" + scanner.lexer_yylval);
        } while (!scanner.yy_atEOF);

      }
      catch (java.io.IOException e) {
        System.out.println("An I/O error occured while scanning :");
        System.out.println(e);
        System.exit(1);
      }
      catch (Exception e) {
        e.printStackTrace();
        System.exit(1);
      }
    }
  }


  /**
   * Creates a new scanner
   * There is also a java.io.InputStream version of this constructor.
   *
   * @param   in  the java.io.Reader to read input from.
   */
  ScriptLexer(java.io.Reader in) {
    this.yy_reader = in;
  }

  /**
   * Creates a new scanner.
   * There is also java.io.Reader version of this constructor.
   *
   * @param   in  the java.io.Inputstream to read input from.
   */
  ScriptLexer(java.io.InputStream in) {
    this(new java.io.InputStreamReader(in));
  }

  /** 
   * Unpacks the split, compressed DFA transition table.
   *
   * @return the unpacked transition table
   */
  private static int [] yy_unpack() {
    int [] trans = new int[432];
    int offset = 0;
    offset = yy_unpack(yy_packed0, offset, trans);
    return trans;
  }

  /** 
   * Unpacks the compressed DFA transition table.
   *
   * @param packed   the packed transition table
   * @return         the index of the last entry
   */
  private static int yy_unpack(String packed, int offset, int [] trans) {
    int i = 0;       /* index in packed string  */
    int j = offset;  /* index in unpacked array */
    int l = packed.length();
    while (i < l) {
      int count = packed.charAt(i++);
      int value = packed.charAt(i++);
      value--;
      do trans[j++] = value; while (--count > 0);
    }
    return j;
  }


  /**
   * Gets the next input character.
   *
   * @return      the next character of the input stream, EOF if the
   *              end of the stream is reached.
   * @exception   IOException  if any I/O-Error occurs
   */
  private int yy_advance() throws java.io.IOException {

    /* standard case */
    if (yy_currentPos < yy_endRead) return yy_buffer[yy_currentPos++];

    /* if the eof is reached, we don't need to work hard */ 
    if (yy_atEOF) return YYEOF;

    /* otherwise: need to refill the buffer */

    /* first: make room (if you can) */
    if (yy_startRead > 0) {
      System.arraycopy(yy_buffer, yy_startRead, 
                       yy_buffer, 0, 
                       yy_endRead-yy_startRead);

      /* translate stored positions */
      yy_endRead-= yy_startRead;
      yy_currentPos-= yy_startRead;
      yy_markedPos-= yy_startRead;
      yy_pushbackPos-= yy_startRead;
      yy_startRead = 0;
    }

    /* is the buffer big enough? */
    if (yy_currentPos >= yy_buffer.length) {
      /* if not: blow it up */
      char newBuffer[] = new char[yy_currentPos*2];
      System.arraycopy(yy_buffer, 0, newBuffer, 0, yy_buffer.length);
      yy_buffer = newBuffer;
    }

    /* finally: fill the buffer with new input */
    int numRead = yy_reader.read(yy_buffer, yy_endRead, 
                                            yy_buffer.length-yy_endRead);

    if ( numRead == -1 ) return YYEOF;

    yy_endRead+= numRead;

    return yy_buffer[yy_currentPos++];
  }


  /**
   * Closes the input stream.
   */
  final public void yyclose() throws java.io.IOException {
    yy_atEOF = true;            /* indicate end of file */
    yy_endRead = yy_startRead;  /* invalidate buffer    */

    if (yy_reader != null)
      yy_reader.close();
  }


  /**
   * Closes the current stream, and resets the
   * scanner to read from a new input stream.
   *
   * All internal variables are reset, the old input stream 
   * <b>cannot</b> be reused (internal buffer is discarded and lost).
   * Lexical state is set to <tt>YY_INITIAL</tt>.
   *
   * @param reader   the new input stream 
   */
  final public void yyreset(java.io.Reader reader) throws java.io.IOException {
    yyclose();
    yy_reader = reader;
    yy_atBOL  = true;
    yy_atEOF  = false;
    yy_endRead = yy_startRead = 0;
    yy_currentPos = yy_markedPos = yy_pushbackPos = 0;
    yyline = yychar = yycolumn = 0;
    yy_lexical_state = YYINITIAL;
  }


  /**
   * Returns the current lexical state.
   */
  final public int yystate() {
    return yy_lexical_state;
  }


  /**
   * Enters a new lexical state
   *
   * @param newState the new lexical state
   */
  final public void yybegin(int newState) {
    yy_lexical_state = newState;
  }


  /**
   * Returns the text matched by the current regular expression.
   */
  final public String yytext() {
    return new String( yy_buffer, yy_startRead, yy_markedPos-yy_startRead );
  }


  /**
   * Returns the character at position <tt>pos</tt> from the 
   * matched text. 
   * 
   * It is equivalent to yytext().charAt(pos), but faster
   *
   * @param pos the position of the character to fetch. 
   *            A value from 0 to yylength()-1.
   *
   * @return the character at position pos
   */
  final public char yycharat(int pos) {
    return yy_buffer[yy_startRead+pos];
  }


  /**
   * Returns the length of the matched text region.
   */
  final public int yylength() {
    return yy_markedPos-yy_startRead;
  }


  /**
   * Reports an error that occured while scanning.
   *
   * In a wellformed scanner (no or only correct usage of 
   * yypushback(int) and a match-all fallback rule) this method 
   * will only be called with things that "Can't Possibly Happen".
   * If this method is called, something is seriously wrong
   * (e.g. a JFlex bug producing a faulty scanner etc.).
   *
   * Usual syntax/scanner level error handling should be done
   * in error fallback rules.
   *
   * @param   errorCode  the code of the errormessage to display
   */
  private void yy_ScanError(int errorCode) {
    String message;
    try {
      message = YY_ERROR_MSG[errorCode];
    }
    catch (ArrayIndexOutOfBoundsException e) {
      message = YY_ERROR_MSG[YY_UNKNOWN_ERROR];
    }

    throw new Error(message);
  } 


  /**
   * Pushes the specified amount of characters back into the input stream.
   *
   * They will be read again by then next call of the scanning method
   *
   * @param number  the number of characters to be read again.
   *                This number must not be greater than yylength()!
   */
  private void yypushback(int number)  {
    if ( number > yylength() )
      yy_ScanError(YY_PUSHBACK_2BIG);

    yy_markedPos -= number;
  }


  /**
   * Contains user EOF-code, which will be executed exactly once,
   * when the end of file is reached
   */
  private void yy_do_eof() throws java.io.IOException {
    if (!yy_eof_done) {
      yy_eof_done = true;
      yyclose();
    }
  }


  /**
   * Resumes scanning until the next regular expression is matched,
   * the end of input is encountered or an I/O-Error occurs.
   *
   * @return      the next token
   * @exception   IOException  if any I/O-Error occurs
   */
  public int yylex() throws java.io.IOException {
    int yy_input;
    int yy_action;


    while (true) {

      yy_action = -1;

      yy_currentPos = yy_startRead = yy_markedPos;

      yy_state = yy_lexical_state;


      yy_forAction: {
        while (true) {

          yy_input = yy_advance();

          if ( yy_input == YYEOF ) break yy_forAction;

          int yy_next = yytrans[ yy_rowMap[yy_state] + yycmap[yy_input] ];
          if (yy_next == -1) break yy_forAction;
          yy_state = yy_next;

          int yy_attributes = YY_ATTRIBUTE[yy_state];
          if ( (yy_attributes & 1) > 0 ) {
            yy_action = yy_state; 
            yy_markedPos = yy_currentPos; 
            if ( (yy_attributes & 8) > 0 ) break yy_forAction;
          }

        }
      }


      switch (yy_action) {

        case 19: 
          { 
        yybegin(YYINITIAL);
		tokenQueue.add(new Lval(cdata.toString()));  
		tokenQueue.add(new Integer(HtmlParser.TEXT));
		tokenQueue.add(new Integer(HtmlParser.CDATA_END));
		return -2;
     }
        case 33: break;
        case 1: 
        case 7: 
        case 8: 
          { 
		cdata.append(yytext());
     }
        case 34: break;
        case 4: 
        case 6: 
          {  
		text.append(yytext());
	 }
        case 35: break;
        case 0: 
        case 3: 
          {  
		text.append(yytext());
		tokenQueue.add(new Lval(new String(text.toString())));
		tokenQueue.add(new Integer(HtmlParser.TEXT));
		text.setLength(0);
	 }
        case 36: break;
        case 31: 
          { 
		tokenQueue.add(new Integer(HtmlParser.ANGLE_END_OPEN)); // </
		String name = yytext();
		name = name.substring(2, 8);  						    // original case of script, Script, SCRIPT
		tokenQueue.add(new Lval(name));
		tokenQueue.add(new Integer(HtmlParser.NAME));			// script
		tokenQueue.add(new Integer(HtmlParser.ANGLE_CLOSE));   // >
		return -2;
	 }
        case 37: break;
        case 30: 
          { 
		yybegin(IGNORE_CDATA);
		cdata.setLength(0);
		return HtmlParser.CDATA_START;
	 }
        case 38: break;
        case 10: 
          { 
		yybegin(YYINITIAL);
		if (getDebug()) System.err.println("ERROR_RECOVER: recovered");
		return HtmlParser.ANGLE_CLOSE;
	 }
        case 39: break;
        case 5: 
          { 
		text.append(yytext());
	 }
        case 40: break;
        case 2: 
        case 9: 
          { 
		/* ignore all this till recover */
	 }
        case 41: break;
        case 18: 
          {  }
        case 42: break;
        default: 
          if (yy_input == YYEOF && yy_startRead == yy_currentPos) {
            yy_atEOF = true;
            yy_do_eof();
              { return 0; }
          } 
          else {
            yy_ScanError(YY_NO_MATCH);
          }
      }
    }
  }


}
