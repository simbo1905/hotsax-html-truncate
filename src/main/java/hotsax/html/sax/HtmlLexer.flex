
package hotsax.html.sax;

import java.io.*;
import java.util.StringTokenizer;

%%


%{
	private HtmlParser yyparser;
	private StringBuffer string = new StringBuffer();
	private StringBuffer text = new StringBuffer();
	private StringBuffer attr = new StringBuffer();
	private StringBuffer comment = new StringBuffer();
	private StringBuffer pi = new StringBuffer();
	private StringBuffer cdata = new StringBuffer();
	private StringBuffer script = new StringBuffer();
	private StringBuffer doctype = new StringBuffer();

	protected boolean debug = false;
	public void setDebug(boolean debug) { this.debug = debug; }
	public boolean getDebug() { return debug; }

	public void p(String s) { System.out.println(s); }
	
	public boolean getEOF() { return yy_atEOF; }

	public HtmlLexer(Reader r, HtmlParser p)
	{
		this(r);
		yyparser = p;
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
			token = yylex();
		}
		catch (Error err) {
			if (getDebug()) System.err.println("Caught error " + err.getMessage());
			yybegin(ERROR_RECOVER);
			if (yyparser != null)
				yyparser.yylval = empty_yylval;
			
			token = yylex(); // read ahead in ERROR_REOVER
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
      HtmlLexer scanner = null;
      try {
        scanner = new HtmlLexer( new java.io.FileReader(argv[i]) );
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

%class HtmlLexer
%implements SemanticLexer

%caseless

%state INNER ATTRVAL IGNORE_PI PI_COLLECT IGNORE_COMMENT IGNORE_CDATA IGNORE_DOCTYPE
%state APOSSTRING STRING ERROR_RECOVER
%full
/*   %debug  ** declare if wanting emitted main() by JFlex. We use our own main here. */

/* %type Yytoken */

ALPHA=[A-Za-z]
DIGIT=[0-9]
/* NONNEWLINE_WHITE_SPACE_CHAR=[\ \t\b\012] */
/*  NEWLINE=\r|\n|\r\n
	SIGN=\-|\+
	Path = (_|\/|:|\.|\;|\,|\?|{ALPHA}|{DIGIT})*
	Number = {SIGN}?{DIGIT}+
*/
WHITE_SPACE_CHAR=[\n\r\ \t\b\012]
EQUAL="="
Name = {ALPHA}({ALPHA}|{DIGIT}|_|\-)*

 
%%

<YYINITIAL> {
	"</" {  
            yybegin(INNER);
            return HtmlParser.ANGLE_END_OPEN;
	}

	"<!--" { 
            yybegin(IGNORE_COMMENT);
            return HtmlParser.COMMENT_START;
	}


	"<!DOCTYPE" {  
            yybegin(IGNORE_DOCTYPE);
            return HtmlParser.DOCTYPE_START;
    }

	"<![CDATA[" { 
            yybegin(IGNORE_CDATA);
            return HtmlParser.CDATA_START;
	}

	"<?" { 
            yybegin(IGNORE_PI);
            return HtmlParser.PI_OPEN;
	}	

	"<" {
            yybegin(INNER);
            return HtmlParser.ANGLE_OPEN;
	}	
		

     [^\<]* { 
         setLval(yytext());
         return HtmlParser.TEXT;
     }
}


/* The INNER state represents all possibilities between any two angle brackets <  INNER > */
<INNER> {
	"/>" {   
		yybegin(YYINITIAL);
		return HtmlParser.ANGLE_END_CLOSE;
	 }

	">" {   
		yybegin(YYINITIAL);
		return HtmlParser.ANGLE_CLOSE;
	 }

	 {EQUAL} {
		yybegin(ATTRVAL);
		return HtmlParser.EQUAL;
	 }

	 {Name} {
		setLval(yytext());
		return HtmlParser.NAME;
	 }

	{WHITE_SPACE_CHAR}* { } /* ignore whitespace */
}


/* we saw an EQUAL sign after a name, now we can be any where until the next bit of whitespace (unquoted) */
<ATTRVAL> {
	\" { string.setLength(0); yybegin(STRING); }

	\' { string.setLength(0); yybegin(APOSSTRING); }

	[^ \t\r\n>\'\"]+ {
		yybegin(INNER);
		setLval(yytext());
		return HtmlParser.VALUE;
	}

	">" { 
		yybegin(YYINITIAL);
		return HtmlParser.ANGLE_CLOSE;
	}

	[ \t\r\n]* {}
}



/* Here we ignore content (esp. markup) between some kinds of brackets and just report it as TEXT */

/* PIs do recognize a name, then possibly some text */
<IGNORE_PI> {
	{Name} {
		yybegin(PI_COLLECT);
		setLval(yytext());
		return HtmlParser.NAME;
	}
}

/* collect the rest of the PI if there is any */
<PI_COLLECT> {
	"?>" { 
		yybegin(YYINITIAL);
		return HtmlParser.PI_CLOSE;
	}	

	[^?]* { 
		setLval(yytext());
		return HtmlParser.TEXT;
	}
}


<IGNORE_COMMENT> {
    "-->" {
        yybegin(YYINITIAL);
        return HtmlParser.COMMENT_END;
    }

	"-" {						  /* handle any hanging minus signs E.g. i - 2; --a; etc. */
        setLval(yytext());
        return HtmlParser.TEXT;   /* this has to be handled correctly in parser */
	} 

    [^\-]* {
        setLval(yytext());
        return HtmlParser.TEXT;
    }
}

<IGNORE_CDATA> {
    "]]>" {
        yybegin(YYINITIAL);
        return HtmlParser.CDATA_END;
    }

    "]" {
        setLval(yytext());
        return HtmlParser.TEXT;
    }

    [^\]]* {
        setLval(yytext());
        return HtmlParser.TEXT;
    }
}

<IGNORE_DOCTYPE> {
    ">" {
        yybegin(YYINITIAL);
        return HtmlParser.ANGLE_CLOSE;
    }

    [^\>]* {
        setLval(yytext());
        return HtmlParser.TEXT;
    }
}


/* capture a string bounded by apostrophes "'" Aka single quotes
 * Ignore things like double quotes
 */
<APOSSTRING> {
	\' { yybegin(INNER); 
         setLval(string.toString());
	     return HtmlParser.STRING; 
           }
	[^']* { string.append(yytext()); }
}

/* capture a string bounded by double quotes
 * Ignore things like single quotes
 */
<STRING> {
	\" { yybegin(INNER); 
         setLval(string.toString());
	     return HtmlParser.STRING; 
           }
	[^"]* { string.append(yytext()); }
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



