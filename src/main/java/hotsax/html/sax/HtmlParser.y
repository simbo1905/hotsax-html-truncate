/* NewGrammar.y - attempt at cleaning HtmlParser.  TODO: Rename to HtmlParser.y
 *   The heart and soul of HotSAX. This grammar is sufficient to handle HTML/XML/XHTML tags  
 *   without getting prssiy about the syntax. Thus we can fire SAX events w/o regard to open/closed 
 *   tags. 

 * The semantic model of HotSAX is simply that there is a document,
 *   which may or may not have stuff in it;
 *   the stuff may contain things like start, end or empty tags, or PIs, DOCTYPEs, comments or CDATA.
 *   We don't care in what order these occur.
 */

%{

import java.io.*;
import java.util.*;


%}
      
%token SOF ANGLE_OPEN ANGLE_CLOSE ANGLE_END_OPEN ANGLE_END_CLOSE 
%token ATTR EQUAL VALUE STRING TEXT COMMENT_START COMMENT_END NAME
%token PI_OPEN PI_CLOSE DOCTYPE_START CDATA_START CDATA_END EOF 



%%


document: {	
			delegate.startDocument();  
			delegate.endDocument();
		  }
		|          { delegate.startDocument();  }
		  docstuff { delegate.endDocument(); }
		;

docstuff: start
	   | docstuff start             /* our recursion occurs here */
	   ;

start:	 element 
	   ;

element:     element_start
           | element_end                /* HTML may or may not have these. */
           | comment
           | pi
           | cdata
           | doctype
           | TEXT                       { delegate.characters($$); }
           ;


comment:    COMMENT_START { saved.setLength(0); }
			  comment_text COMMENT_END { delegate.comment(new HtmlParserVal(saved.toString())); }
            ;

comment_text: TEXT               { saved.append($$); }
		    | comment_text TEXT  { saved.append($$); }
		    ;

element_start:  ANGLE_OPEN NAME { delegate.startElement(); } /* clear attributes */
                attributes  { 
                    delegate.startElement($2, delegate.getAttributes());
                    tagName = $2;
					setSemanticLexer(tagName.toString());   // see if script defines a new lexer
                }  
                angle_end   
             ;

/* handle both normal and empty tags */
angle_end: ANGLE_CLOSE 
        |  ANGLE_END_CLOSE   { delegate.endElement(tagName); }  // special case: we saw start and end in same
        ; 


element_end:  ANGLE_END_OPEN NAME ANGLE_CLOSE { 
			    try {
					tagName = $2;
					if (getSemanticLexer(tagName.toString()) != null)
						revertSemanticLexer();   // if we were in ScriptLexer, maybe we are back
					delegate.endElement($2); 
				}
				catch(HtmlParserException ex) {
					yyerror(ex.getMessage());
				}
			  }  /* </ name > */
             ;


attributes: attribute attributes
          | 
          ;
                                         /* resolve:  valign=top size=-1 alt=123.jpg href="#anch" src='http:scripts/s.js' noshade */
attribute:  NAME EQUAL VALUE            { delegate.addAttribute($1.toString(), $3.toString()); }
           | NAME EQUAL NAME             { delegate.addAttribute($1.toString(), $3.toString()); }
           | NAME EQUAL STRING           { delegate.addAttribute($1.toString(), $3.toString()); }
           | NAME                        { delegate.addAttribute($$.toString(), $$.toString()); } /* e.g. noshade="noshade" */
           ;


pi:           PI_OPEN NAME TEXT PI_CLOSE { delegate.processingInstruction($2, $3); } /* note, delegate handles ignoring,processing xml declaration */
            | PI_OPEN NAME PI_CLOSE { delegate.processingInstruction($2, null); }
            ; 


/* eventually we want to make this into a pluggable lexer/parser switch. */

cdata:      CDATA_START {
					delegate.startCDATA();
				}
				text CDATA_END {
					 delegate.endCDATA(); 
				}
           ;

text:	TEXT text {delegate.characters($1); }
		|
		;


/* TODO: This needs to resolve startEntity, DeclHandlers etc. For now just report TEXT */
doctype: DOCTYPE_START TEXT { delegate.startDTD($$); }
            ANGLE_CLOSE { delegate.endDTD(); }
       ;





/*********/



%%

  public final String DEFAULT_LEXER_NAME = "hotsax:default"; // name of the default lexer. distinguishes via namespace from some other "default" tag

  private SemanticLexer lexer;
  private SemanticLexer savedLexer;   // hold it in while we switch to another lexer, see ScriptLexer

  private HashMap lexerHash;
  private Reader reader;

  private boolean debug = false;

  private StringBuffer saved = new StringBuffer("");

  /* Helpers */
  private ParserDelegate delegate;
  public ParserDelegate getDelegate() { return delegate; }
  private HtmlParserVal tagName; 

  private int yylex () {
    int yyl_token = -1;
    try {
      yyl_token = lexer._yylex();
	
		if (yydebug)
			System.out.println("token: " + yyl_token + " " + yyname[yyl_token] + "  " + yylval.toString());
    }
    catch (IOException e) {
      System.err.println("IO error :"+e);
    }
    return yyl_token;
  }

  public void yyerror (String error) {
    System.err.println ("Error: " + error);
  }


  /**
   * Create new HtmlParser with specified reader 
   */
  public HtmlParser(Reader r) {
    reader = r;
    delegate = new SaxHandlerDelegate(this);
	lexerInit();  // setup the semantic lexers for this
  }

  /**
   * Create new HtmlParser with no reader. One must be set via setReader. 
   * Calling setReader will also call lexerInit()
   */
  public HtmlParser() {
	reader = null;
	delegate = new SaxHandlerDelegate(this);
  }

  /**
   * Set the ParserDelegate. Can be a SaxHandlerDelegate or DebugDelegate. Could be used to write
   * a DOM handler too.
   * @param ParserDelegate to set
   */
   public void setParserDelegate(ParserDelegate delegate) {
		this.delegate = delegate;
   }

  /**
   * Get the current ParserDelegate
   */
   public ParserDelegate getParserDelegate() {
		return delegate;
	}

   /**
    * Initialize the lexer hash. Setup the default lexer and any others. This is called by the 
	* constructor, so new lexers can be added at any time after the parser is built.
    */
   public void lexerInit() {
	lexerHash = new HashMap();
	addSemanticLexer(DEFAULT_LEXER_NAME, new HtmlLexer(reader, this));				// set the primary lexer
	addSemanticLexer("script", new ScriptLexer(reader, this));
	addSemanticLexer("style", new StyleLexer(reader, this));
	lexer = getSemanticLexer(DEFAULT_LEXER_NAME);
   }

  /**
   * Check the possibility of an alternate lexer for this semantic state.
   *  E.g. script tags would invoke the ScriptLexer which creates a token stream different than X/HTML markup
   */
  public SemanticLexer getSemanticLexer(String name) {
	 return (SemanticLexer)lexerHash.get(name.toLowerCase());
  }

 
  /**
   *  Change the lexer to the one specified in the name. If no lexer for this
   *  token exists, the previous lexer remains in effect. 
   *  The state of the current lexer is copied to the new lexer.
   *  Note, by default "html" refers to  the HtmlLexer which is set when HtmlParser is initialized. 
   *  However, no html tag is required to set it, it is the first lexe by default. The html tag has no effect.
   *  Note: This may change in the future. The existance of a xml processing instruction
   *  may switch the lexer from the  one.
   */
  public void setSemanticLexer(String name) {
	SemanticLexer newlexer = getSemanticLexer(name);
	if (newlexer != null) {		// possible establish a different lexer
		newlexer.setBuffer(lexer);
		lexer = newlexer;
		lexer.yybegin(HtmlLexer.YYINITIAL); // establish the initial state of the lexer
		if (debug) 
			System.out.println("lexer switched to " + lexer.getClass().getName());
	}
  }


  /**
   * Revert the SemanticLexer to the default value. This is signified by the key "default". Under
   * notmal circumstances, this would be HtmlLexer. But it might be different if the xml PI were seen
   * and that specific processing instruction set the lexer to an XML lexer.
   * @exception UnspecifiedSemanticLexerException thrown if the Default lexer disappeared.
   */
   public void revertSemanticLexer() 
	  throws UnspecifiedSemanticLexerException
   {
	  if (getSemanticLexer(DEFAULT_LEXER_NAME) != null)
		  setSemanticLexer(DEFAULT_LEXER_NAME);
	  else
		  throw new UnspecifiedSemanticLexerException("No lexer defined to revert to");
   }

  /**
   *  Add a new Semantic Lexer to the hash of available lexers. Once set, 
   *  the parser will switch lexers to to this one. The case is set to lower case before
   *  it is stored as a key. The end tag will revert the lexer if set. This handles
   *  cases like <script></SCRIPT>
   *  The lexer must implement SemanticLexer and must be constructed with this parsers Reader (reader.)
   *  <code>
		yyparser.addSemanyicLexer("style", new StyleLexer(yyparser.getReader(), yyparser));
      </code>
   *  @param name The lower case name of the tag or attribute this should key on.
   *  @param lexer The SemanticLexer to use when this name token appears. 
   *  @see setSemanticLexer
   */
  public void addSemanticLexer(String name, SemanticLexer lexer) {
	 lexerHash.put(name.toLowerCase(), lexer);	 
  }

  /**
   * Return the reader the parser is using.
   */
  public Reader getReader() {
	 return reader;
  }

  /**
   * Set the reader the parser is using.
   * Also establishes any default and newly defined SemanticLexers
   */
  public void setReader(Reader r) {
	 reader = r;
	 lexerInit();
  }

	// testing -----------
  static boolean interactive;

  public static void main(String args[]) throws IOException {
    System.out.println("HotSAX Parser");

    HtmlParser yyparser;
    if ( args.length > 0 ) {
      // parse a file
      yyparser = new HtmlParser(new FileReader(args[0]));
    }
    else {
      // interactive mode
      System.out.println("[Quit with CTRL-D]");
      System.out.print("Expression: ");
      interactive = true;
      yyparser = new HtmlParser(new InputStreamReader(System.in));
      yyparser.yydebug = true;
    }

	for (int i = 1; i < args.length; i++) {
		if (args[i].equalsIgnoreCase("debug")) {
			yyparser.setParserDelegate(new DebugParserDelegate(yyparser));
			yyparser.debug = true;
		}
		if (args[i].equalsIgnoreCase("verbose")) {
			yyparser.yydebug = true;
		}
	}

    yyparser.yyparse();
    
    if (interactive) {
      System.out.println();
      System.out.println("Have a nice day");
    }
  }
