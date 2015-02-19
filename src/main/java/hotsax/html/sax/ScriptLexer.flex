
package hotsax.html.sax;

import java.io.*;
import java.util.*;

%%


%{
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
%} 

%byaccj

%class ScriptLexer
%implements SemanticLexer


%state IGNORE_CDATA 
%state ERROR_RECOVER
%full
%caseless


/*
ALPHA=[A-Za-z]
DIGIT=[0-9]
 NONNEWLINE_WHITE_SPACE_CHAR=[\ \t\b\012] 
  NEWLINE=\r|\n|\r\n
	SIGN=\-|\+
	Path = (_|\/|:|\.|\;|\,|\?|{ALPHA}|{DIGIT})*
	Number = {SIGN}?{DIGIT}+
WHITE_SPACE_CHAR=[\n\r\ \t\b\012]
EQUAL="="
Name = {ALPHA}({ALPHA}|{DIGIT}|_|\-)*
*/

 
%%


/* works only only inside of script tags */
<YYINITIAL> {
	"<!--" {}	
	"-->" {}

	/* any close of this script tag returns the collected text, followed by tag stuff */
	"</script>" {
		tokenQueue.add(new Integer(HtmlParser.ANGLE_END_OPEN)); // </
		String name = yytext();
		name = name.substring(2, 8);  						    // original case of script, Script, SCRIPT
		tokenQueue.add(new Lval(name));
		tokenQueue.add(new Integer(HtmlParser.NAME));			// script
		tokenQueue.add(new Integer(HtmlParser.ANGLE_CLOSE));   // >
		return -2;
	}	

	"<![CDATA[" {
		yybegin(IGNORE_CDATA);
		cdata.setLength(0);
		return HtmlParser.CDATA_START;
	}

	"<" { 
		text.append(yytext());
	}
		
	">" { 
		text.append(yytext());
	}

	"-" {
		text.append(yytext());
	}
		
	/* collect all other text */
    [^<\-]* { 
		text.append(yytext());
		tokenQueue.add(new Lval(new String(text.toString())));
		tokenQueue.add(new Integer(HtmlParser.TEXT));
		text.setLength(0);
	}
}

/* XHTML script blocks might have all script code inside a CDATA element */	
<IGNORE_CDATA> {
    "]]>" {
        yybegin(YYINITIAL);
		tokenQueue.add(new Lval(cdata.toString()));  
		tokenQueue.add(new Integer(HtmlParser.TEXT));
		tokenQueue.add(new Integer(HtmlParser.CDATA_END));
		return -2;
    }

    "]" {
		cdata.append(yytext());
    }

    [^\]]* {
		cdata.append(yytext());
    }
}

/**
 * Try to recover from some scanner unmatch. Scan ahead for a closing '>'
 */
<ERROR_RECOVER> {
	">" {
		yybegin(YYINITIAL);
		if (getDebug()) System.err.println("ERROR_RECOVER: recovered");
		return HtmlParser.ANGLE_CLOSE;
	}

	[^>]* {
		/* ignore all this till recover */
	}
}



