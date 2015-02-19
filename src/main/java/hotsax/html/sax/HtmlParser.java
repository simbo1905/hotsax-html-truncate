//### This file created by BYACC 1.8(/Java extension  1.1)
//### Java capabilities added 7 Jan 97, Bob Jamison
//### Updated : 27 Nov 97  -- Bob Jamison, Joe Nieten
//###           01 Jan 98  -- Bob Jamison -- fixed generic semantic constructor
//###           01 Jun 99  -- Bob Jamison -- added Runnable support
//###           06 Aug 00  -- Bob Jamison -- made state variables class-global
//###           03 Jan 01  -- Bob Jamison -- improved flags, tracing
//###           16 May 01  -- Bob Jamison -- added custom stack sizing
//### Please send bug reports to rjamison@lincom-asg.com
//### static char yysccsid[] = "@(#)yaccpar	1.8 (Berkeley) 01/20/90";



package hotsax.html.sax;



//#line 13 "/home/edh/sourceforge/HotSAX/build/src/hotsax/html/sax/HtmlParser.y"

import java.io.*;
import java.util.*;


//#line 21 "HtmlParser.java"




/**
 * Encapsulates yacc() parser functionality in a Java
 *        class for quick code development
 */
public class HtmlParser
{

boolean yydebug;        //do I want debug output?
int yynerrs;            //number of errors so far
int yyerrflag;          //was there an error?
int yychar;             //the current working character

//########## MESSAGES ##########
//###############################################################
// method: debug
//###############################################################
void debug(String msg)
{
  if (yydebug)
    System.out.println(msg);
}

//########## STATE STACK ##########
final static int YYSTACKSIZE = 500;  //maximum stack size
int statestk[],stateptr;           //state stack
int stateptrmax;                     //highest index of stackptr
int statemax;                        //state when highest index reached
//###############################################################
// methods: state stack push,pop,drop,peek
//###############################################################
void state_push(int state)
{
  if (stateptr>=YYSTACKSIZE)         //overflowed?
    return;
  statestk[++stateptr]=state;
  if (stateptr>statemax)
    {
    statemax=state;
    stateptrmax=stateptr;
    }
}
int state_pop()
{
  if (stateptr<0)                    //underflowed?
    return -1;
  return statestk[stateptr--];
}
void state_drop(int cnt)
{
int ptr;
  ptr=stateptr-cnt;
  if (ptr<0)
    return;
  stateptr = ptr;
}
int state_peek(int relative)
{
int ptr;
  ptr=stateptr-relative;
  if (ptr<0)
    return -1;
  return statestk[ptr];
}
//###############################################################
// method: init_stacks : allocate and prepare stacks
//###############################################################
boolean init_stacks()
{
  statestk = new int[YYSTACKSIZE];
  stateptr = -1;
  statemax = -1;
  stateptrmax = -1;
  val_init();
  return true;
}
//###############################################################
// method: dump_stacks : show n levels of the stacks
//###############################################################
void dump_stacks(int count)
{
int i;
  System.out.println("=index==state====value=     s:"+stateptr+"  v:"+valptr);
  for (i=0;i<count;i++)
    System.out.println(" "+i+"    "+statestk[i]+"      "+valstk[i]);
  System.out.println("======================");
}


//########## SEMANTIC VALUES ##########
//public class HtmlParserVal is defined in HtmlParserVal.java


String   yytext;//user variable to return contextual strings
HtmlParserVal yyval; //used to return semantic vals from action routines
HtmlParserVal yylval;//the 'lval' (result) I got from yylex()
HtmlParserVal valstk[];
int valptr;
//###############################################################
// methods: value stack push,pop,drop,peek.
//###############################################################
void val_init()
{
  valstk=new HtmlParserVal[YYSTACKSIZE];
  yyval=new HtmlParserVal(0);
  yylval=new HtmlParserVal(0);
  valptr=-1;
}
void val_push(HtmlParserVal val)
{
  if (valptr>=YYSTACKSIZE)
    return;
  valstk[++valptr]=val;
}
HtmlParserVal val_pop()
{
  if (valptr<0)
    return new HtmlParserVal(-1);
  return valstk[valptr--];
}
void val_drop(int cnt)
{
int ptr;
  ptr=valptr-cnt;
  if (ptr<0)
    return;
  valptr = ptr;
}
HtmlParserVal val_peek(int relative)
{
int ptr;
  ptr=valptr-relative;
  if (ptr<0)
    return new HtmlParserVal(-1);
  return valstk[ptr];
}
//#### end semantic value section ####
public final static short SOF=257;
public final static short ANGLE_OPEN=258;
public final static short ANGLE_CLOSE=259;
public final static short ANGLE_END_OPEN=260;
public final static short ANGLE_END_CLOSE=261;
public final static short ATTR=262;
public final static short EQUAL=263;
public final static short VALUE=264;
public final static short STRING=265;
public final static short TEXT=266;
public final static short COMMENT_START=267;
public final static short COMMENT_END=268;
public final static short NAME=269;
public final static short PI_OPEN=270;
public final static short PI_CLOSE=271;
public final static short DOCTYPE_START=272;
public final static short CDATA_START=273;
public final static short CDATA_END=274;
public final static short EOF=275;
public final static short YYERRCODE=256;
final static short yylhs[] = {                           -1,
    0,    2,    0,    1,    1,    3,    4,    4,    4,    4,
    4,    4,    4,   12,    7,   11,   11,   14,   16,    5,
   15,   15,    6,   13,   13,   17,   17,   17,   17,    8,
    8,   19,    9,   18,   18,   20,   10,
};
final static short yylen[] = {                            2,
    0,    0,    2,    1,    2,    1,    1,    1,    1,    1,
    1,    1,    1,    0,    4,    1,    2,    0,    0,    6,
    1,    1,    3,    2,    0,    3,    3,    3,    1,    4,
    3,    0,    4,    2,    0,    0,    4,
};
final static short yydefred[] = {                         0,
    0,    0,    0,    0,   13,   14,    0,    0,   32,    0,
    4,    6,    7,    8,    9,   10,   11,   12,   18,    0,
    0,    0,   36,    0,    5,    0,   23,   16,    0,    0,
   31,    0,    0,    0,    0,   19,    0,   17,   15,   30,
   37,   34,   33,    0,    0,   24,   26,   28,   27,   21,
   22,   20,
};
final static short yydgoto[] = {                          1,
   10,    2,   11,   12,   13,   14,   15,   16,   17,   18,
   29,   21,   36,   26,   52,   45,   37,   34,   24,   32,
};
final static short yysindex[] = {                         0,
    0, -258, -265, -262,    0,    0, -259, -247,    0, -258,
    0,    0,    0,    0,    0,    0,    0,    0,    0, -241,
 -239, -260,    0, -238,    0, -240,    0,    0, -246, -237,
    0, -229, -238, -243, -231,    0, -240,    0,    0,    0,
    0,    0,    0, -248, -236,    0,    0,    0,    0,    0,
    0,    0,
};
final static short yyrindex[] = {                         1,
    0,    0,    0,    0,    0,    0,    0,    0,    0,   33,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0, -234,    0, -235,    0,    0,    0,    0,
    0,    0, -234,    0, -256,    0, -235,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,
};
final static short yygindex[] = {                         0,
    0,    0,   25,    0,    0,    0,    0,    0,    0,    0,
    0,    0,   -1,    0,    0,    0,    0,    4,    0,    0,
};
final static int YYTABLESIZE=274;
final static short yytable[] = {                          3,
    1,    4,   29,   19,   29,   30,   20,    5,    6,   22,
   31,    7,   29,    8,    9,   47,   48,   27,   23,   38,
   49,   39,   50,   25,   51,   25,   28,   33,   35,   41,
   43,   44,    3,   40,   25,   46,   42,    0,    0,   35,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    2,    0,
    2,    0,    0,    0,    0,    0,    2,    2,    0,    0,
    2,    0,    2,    2,
};
final static short yycheck[] = {                        258,
    0,  260,  259,  269,  261,  266,  269,  266,  267,  269,
  271,  270,  269,  272,  273,  264,  265,  259,  266,  266,
  269,  268,  259,  259,  261,  261,  266,  266,  269,  259,
  274,  263,    0,  271,   10,   37,   33,   -1,   -1,  274,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,  258,   -1,
  260,   -1,   -1,   -1,   -1,   -1,  266,  267,   -1,   -1,
  270,   -1,  272,  273,
};
final static short YYFINAL=1;
final static short YYMAXTOKEN=275;
final static String yyname[] = {
"end-of-file",null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,"SOF","ANGLE_OPEN","ANGLE_CLOSE","ANGLE_END_OPEN",
"ANGLE_END_CLOSE","ATTR","EQUAL","VALUE","STRING","TEXT","COMMENT_START",
"COMMENT_END","NAME","PI_OPEN","PI_CLOSE","DOCTYPE_START","CDATA_START",
"CDATA_END","EOF",
};
final static String yyrule[] = {
"$accept : document",
"document :",
"$$1 :",
"document : $$1 docstuff",
"docstuff : start",
"docstuff : docstuff start",
"start : element",
"element : element_start",
"element : element_end",
"element : comment",
"element : pi",
"element : cdata",
"element : doctype",
"element : TEXT",
"$$2 :",
"comment : COMMENT_START $$2 comment_text COMMENT_END",
"comment_text : TEXT",
"comment_text : comment_text TEXT",
"$$3 :",
"$$4 :",
"element_start : ANGLE_OPEN NAME $$3 attributes $$4 angle_end",
"angle_end : ANGLE_CLOSE",
"angle_end : ANGLE_END_CLOSE",
"element_end : ANGLE_END_OPEN NAME ANGLE_CLOSE",
"attributes : attribute attributes",
"attributes :",
"attribute : NAME EQUAL VALUE",
"attribute : NAME EQUAL NAME",
"attribute : NAME EQUAL STRING",
"attribute : NAME",
"pi : PI_OPEN NAME TEXT PI_CLOSE",
"pi : PI_OPEN NAME PI_CLOSE",
"$$5 :",
"cdata : CDATA_START $$5 text CDATA_END",
"text : TEXT text",
"text :",
"$$6 :",
"doctype : DOCTYPE_START TEXT $$6 ANGLE_CLOSE",
};

//#line 136 "/home/edh/sourceforge/HotSAX/build/src/hotsax/html/sax/HtmlParser.y"

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
    //System.err.println ("Error: " + error);
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
//#line 499 "HtmlParser.java"
//###############################################################
// method: yylexdebug : check lexer state
//###############################################################
void yylexdebug(int state,int ch)
{
String s=null;
  if (ch < 0) ch=0;
  if (ch <= YYMAXTOKEN) //check index bounds
     s = yyname[ch];    //now get it
  if (s==null)
    s = "illegal-symbol";
  debug("state "+state+", reading "+ch+" ("+s+")");
}





//The following are now global, to aid in error reporting
int yyn;       //next next thing to do
int yym;       //
int yystate;   //current parsing state from state table
String yys;    //current token string


//###############################################################
// method: yyparse : parse input and execute indicated items
//###############################################################
int yyparse()
{
boolean doaction;
  init_stacks();
  yynerrs = 0;
  yyerrflag = 0;
  yychar = -1;          //impossible char forces a read
  yystate=0;            //initial state
  state_push(yystate);  //save it
  while (true) //until parsing is done, either correctly, or w/error
    {
    doaction=true;
    if (yydebug) debug("loop"); 
    //#### NEXT ACTION (from reduction table)
    for (yyn=yydefred[yystate];yyn==0;yyn=yydefred[yystate])
      {
      if (yydebug) debug("yyn:"+yyn+"  state:"+yystate+"  yychar:"+yychar);
      if (yychar < 0)      //we want a char?
        {
        yychar = yylex();  //get next token
        if (yydebug) debug(" next yychar:"+yychar);
        //#### ERROR CHECK ####
        if (yychar < 0)    //it it didn't work/error
          {
          yychar = 0;      //change it to default string (no -1!)
          if (yydebug)
            yylexdebug(yystate,yychar);
          }
        }//yychar<0
      yyn = yysindex[yystate];  //get amount to shift by (shift index)
      if ((yyn != 0) && (yyn += yychar) >= 0 &&
          yyn <= YYTABLESIZE && yycheck[yyn] == yychar)
        {
        if (yydebug)
          debug("state "+yystate+", shifting to state "+yytable[yyn]);
        //#### NEXT STATE ####
        yystate = yytable[yyn];//we are in a new state
        state_push(yystate);   //save it
        val_push(yylval);      //push our lval as the input for next rule
        yychar = -1;           //since we have 'eaten' a token, say we need another
        if (yyerrflag > 0)     //have we recovered an error?
           --yyerrflag;        //give ourselves credit
        doaction=false;        //but don't process yet
        break;   //quit the yyn=0 loop
        }

    yyn = yyrindex[yystate];  //reduce
    if ((yyn !=0 ) && (yyn += yychar) >= 0 &&
            yyn <= YYTABLESIZE && yycheck[yyn] == yychar)
      {   //we reduced!
      if (yydebug) debug("reduce");
      yyn = yytable[yyn];
      doaction=true; //get ready to execute
      break;         //drop down to actions
      }
    else //ERROR RECOVERY
      {
      if (yyerrflag==0)
        {
        yyerror("syntax error");
        yynerrs++;
        }
      if (yyerrflag < 3) //low error count?
        {
        yyerrflag = 3;
        while (true)   //do until break
          {
          if (stateptr<0)   //check for under & overflow here
            {
            yyerror("stack underflow. aborting...");  //note lower case 's'
            return 1;
            }
          yyn = yysindex[state_peek(0)];
          if ((yyn != 0) && (yyn += YYERRCODE) >= 0 &&
                    yyn <= YYTABLESIZE && yycheck[yyn] == YYERRCODE)
            {
            if (yydebug)
              debug("state "+state_peek(0)+", error recovery shifting to state "+yytable[yyn]+" ");
            yystate = yytable[yyn];
            state_push(yystate);
            val_push(yylval);
            doaction=false;
            break;
            }
          else
            {
            if (yydebug)
              debug("error recovery discarding state "+state_peek(0)+" ");
            if (stateptr<0)   //check for under & overflow here
              {
              yyerror("Stack underflow. aborting...");  //capital 'S'
              return 1;
              }
            state_pop();
            val_pop();
            }
          }
        }
      else            //discard this token
        {
        if (yychar == 0)
          return 1; //yyabort
        if (yydebug)
          {
          yys = null;
          if (yychar <= YYMAXTOKEN) yys = yyname[yychar];
          if (yys == null) yys = "illegal-symbol";
          debug("state "+yystate+", error recovery discards token "+yychar+" ("+yys+")");
          }
        yychar = -1;  //read another
        }
      }//end error recovery
    }//yyn=0 loop
    if (!doaction)   //any reason not to proceed?
      continue;      //skip action
    yym = yylen[yyn];          //get count of terminals on rhs
    if (yydebug)
      debug("state "+yystate+", reducing "+yym+" by rule "+yyn+" ("+yyrule[yyn]+")");
    if (yym>0)                 //if count of rhs not 'nil'
      yyval = val_peek(yym-1); //get current semantic value
    switch(yyn)
      {
//########## USER-SUPPLIED ACTIONS ##########
case 1:
//#line 29 "/home/edh/sourceforge/HotSAX/build/src/hotsax/html/sax/HtmlParser.y"
{	
			delegate.startDocument();  
			delegate.endDocument();
		  }
break;
case 2:
//#line 33 "/home/edh/sourceforge/HotSAX/build/src/hotsax/html/sax/HtmlParser.y"
{ delegate.startDocument();  }
break;
case 3:
//#line 34 "/home/edh/sourceforge/HotSAX/build/src/hotsax/html/sax/HtmlParser.y"
{ delegate.endDocument(); }
break;
case 13:
//#line 50 "/home/edh/sourceforge/HotSAX/build/src/hotsax/html/sax/HtmlParser.y"
{ delegate.characters(yyval); }
break;
case 14:
//#line 54 "/home/edh/sourceforge/HotSAX/build/src/hotsax/html/sax/HtmlParser.y"
{ saved.setLength(0); }
break;
case 15:
//#line 55 "/home/edh/sourceforge/HotSAX/build/src/hotsax/html/sax/HtmlParser.y"
{ delegate.comment(new HtmlParserVal(saved.toString())); }
break;
case 16:
//#line 58 "/home/edh/sourceforge/HotSAX/build/src/hotsax/html/sax/HtmlParser.y"
{ saved.append(yyval); }
break;
case 17:
//#line 59 "/home/edh/sourceforge/HotSAX/build/src/hotsax/html/sax/HtmlParser.y"
{ saved.append(yyval); }
break;
case 18:
//#line 62 "/home/edh/sourceforge/HotSAX/build/src/hotsax/html/sax/HtmlParser.y"
{ delegate.startElement(); }
break;
case 19:
//#line 63 "/home/edh/sourceforge/HotSAX/build/src/hotsax/html/sax/HtmlParser.y"
{ 
                    delegate.startElement(val_peek(2), delegate.getAttributes());
                    tagName = val_peek(2);
					setSemanticLexer(tagName.toString());   /* see if script defines a new lexer*/
                }
break;
case 22:
//#line 73 "/home/edh/sourceforge/HotSAX/build/src/hotsax/html/sax/HtmlParser.y"
{ delegate.endElement(tagName); }
break;
case 23:
//#line 77 "/home/edh/sourceforge/HotSAX/build/src/hotsax/html/sax/HtmlParser.y"
{ 
			    try {
					tagName = val_peek(1);
					if (getSemanticLexer(tagName.toString()) != null)
						revertSemanticLexer();   /* if we were in ScriptLexer, maybe we are back*/
					delegate.endElement(val_peek(1)); 
				}
				catch(HtmlParserException ex) {
					yyerror(ex.getMessage());
				}
			  }
break;
case 26:
//#line 95 "/home/edh/sourceforge/HotSAX/build/src/hotsax/html/sax/HtmlParser.y"
{ delegate.addAttribute(val_peek(2).toString(), val_peek(0).toString()); }
break;
case 27:
//#line 96 "/home/edh/sourceforge/HotSAX/build/src/hotsax/html/sax/HtmlParser.y"
{ delegate.addAttribute(val_peek(2).toString(), val_peek(0).toString()); }
break;
case 28:
//#line 97 "/home/edh/sourceforge/HotSAX/build/src/hotsax/html/sax/HtmlParser.y"
{ delegate.addAttribute(val_peek(2).toString(), val_peek(0).toString()); }
break;
case 29:
//#line 98 "/home/edh/sourceforge/HotSAX/build/src/hotsax/html/sax/HtmlParser.y"
{ delegate.addAttribute(yyval.toString(), yyval.toString()); }
break;
case 30:
//#line 102 "/home/edh/sourceforge/HotSAX/build/src/hotsax/html/sax/HtmlParser.y"
{ delegate.processingInstruction(val_peek(2), val_peek(1)); }
break;
case 31:
//#line 103 "/home/edh/sourceforge/HotSAX/build/src/hotsax/html/sax/HtmlParser.y"
{ delegate.processingInstruction(val_peek(1), null); }
break;
case 32:
//#line 109 "/home/edh/sourceforge/HotSAX/build/src/hotsax/html/sax/HtmlParser.y"
{
					delegate.startCDATA();
				}
break;
case 33:
//#line 112 "/home/edh/sourceforge/HotSAX/build/src/hotsax/html/sax/HtmlParser.y"
{
					 delegate.endCDATA(); 
				}
break;
case 34:
//#line 117 "/home/edh/sourceforge/HotSAX/build/src/hotsax/html/sax/HtmlParser.y"
{delegate.characters(val_peek(1)); }
break;
case 36:
//#line 123 "/home/edh/sourceforge/HotSAX/build/src/hotsax/html/sax/HtmlParser.y"
{ delegate.startDTD(yyval); }
break;
case 37:
//#line 124 "/home/edh/sourceforge/HotSAX/build/src/hotsax/html/sax/HtmlParser.y"
{ delegate.endDTD(); }
break;
//#line 759 "HtmlParser.java"
//########## END OF USER-SUPPLIED ACTIONS ##########
    }//switch
    //#### Now let's reduce... ####
    if (yydebug) debug("reduce");
    state_drop(yym);             //we just reduced yylen states
    yystate = state_peek(0);     //get new state
    val_drop(yym);               //corresponding value drop
    yym = yylhs[yyn];            //select next TERMINAL(on lhs)
    if (yystate == 0 && yym == 0)//done? 'rest' state and at first TERMINAL
      {
      debug("After reduction, shifting from state 0 to state "+YYFINAL+"");
      yystate = YYFINAL;         //explicitly say we're done
      state_push(YYFINAL);       //and save it
      val_push(yyval);           //also save the semantic value of parsing
      if (yychar < 0)            //we want another character?
        {
        yychar = yylex();        //get next character
        if (yychar<0) yychar=0;  //clean, if necessary
        if (yydebug)
          yylexdebug(yystate,yychar);
        }
      if (yychar == 0)          //Good exit (if lex returns 0 ;-)
         break;                 //quit the loop--all DONE
      }//if yystate
    else                        //else not done yet
      {                         //get next state and push, for next yydefred[]
      yyn = yygindex[yym];      //find out where to go
      if ((yyn != 0) && (yyn += yystate) >= 0 &&
            yyn <= YYTABLESIZE && yycheck[yyn] == yystate)
        yystate = yytable[yyn]; //get new state
      else
        yystate = yydgoto[yym]; //else go to new defred
      debug("after reduction, shifting from state "+state_peek(0)+" to state "+yystate+"");
      state_push(yystate);     //going again, so push state & val...
      val_push(yyval);         //for next action
      }
    }//main loop
  return 0;//yyaccept!!
}
//## end of method parse() ######################################



//## run() --- for Thread #######################################
/**
 * A default run method, used for operating this parser
 * object in the background.  It is intended for extending Thread
 * or implementing Runnable.  Turn off with -Jnorun .
 */
public void run()
{
  yyparse();
}
//## end of method run() ########################################



//## Constructors ###############################################
//## The -Jnoconstruct option was used ##
//###############################################################



}
//################### END OF CLASS ##############################
