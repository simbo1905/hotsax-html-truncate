package hotsax.html.sax;

import org.xml.sax.*;
import org.xml.sax.helpers.*;
import org.xml.sax.ext.*;

import java.io.*;
import java.util.*;



/**
 * SaxHandlerDelegate - provides a clean interface between the
 *   Byacc/J generated HtmlParse and the SaxParser.
 */

public class SaxHandlerDelegate implements ParserDelegate {

	private HtmlParser parser = null;
	private XMLReader reader = null;
	private ContentHandler contentHandler = null;
	private LexicalHandler lexicalHandler = null; 		// this one my not exist for Sax parser/Sax client combo

	private org.xml.sax.helpers.AttributesImpl attrList;   // collect attributes in a list


	public SaxHandlerDelegate(HtmlParser HtmlParser) {
		this.parser = parser;
		attrList = new org.xml.sax.helpers.AttributesImpl();
	}


	// ContentHandler interface methods.
	// If any of these fire a SAXException, it is reported to parser.yyerror()

	/**
	 * Parse a startDocument event and pass it to the resigtered content handler.
	 * This method fires in response to a HtmlParser.EOF lexer token beging recognised.
	 * SOF is a virtual token fired as the first event after the file is opened.
	 */
	public void startDocument() 
        {
		try {
		    if (contentHandler != null)
			contentHandler.startDocument();	
                        
		}
		catch (SAXException ex)
		{
		    parser.yyerror(ex.getMessage());
		}
	}


      /** 
        * Parse a PI and pass it to the contentHandler event
        *  (does not pass xml declaration:  <?xml version = 1>)
		* Separates the target from the data by using whitespace.
        * 
        */ 
	public void processingInstruction(HtmlParserVal target, HtmlParserVal lval)
	{
		try {
            if (contentHandler != null) {
                StringTokenizer stok = new StringTokenizer(lval.sval);  // default delim = \sp
                
                if (stok.hasMoreElements())
                {
                    String data;
                    if (stok.hasMoreElements())
                        data = stok.nextToken();
                    else
                        data = "";
                    if (!target.equals("xml"))
                        contentHandler.processingInstruction(target.toString(), data);
                }
            }
		}
		catch (SAXException ex)
		{
			parser.yyerror(ex.getMessage());
		}
	}

	/**
  	 * Initialize the start of a start element. Prepares the attribute list
	 * to collect any attributes.
	 */
	public void startElement() 
	{
		attrList.clear();
	}

	/**
          * Adds an attribute to the list. The name of the attribute is normalized
          * to lowercase 
	  */
	public void addAttribute(String name, String value) {
		attrList.addAttribute("", "", name, "NMTOKEN", value);
	}

	public HtmlParserVal getAttributes() {
		HtmlParserVal aList = new HtmlParserVal(attrList);
		return aList;
	}

	public void startElement(HtmlParserVal lval, HtmlParserVal attrList) {
		try {
			if (contentHandler != null)
			{
				 contentHandler.startElement("", lval.sval, "", (Attributes)attrList.obj);
			}
		}
		catch (SAXException ex)
		{
			parser.yyerror(ex.getMessage());
		}
	}

	/**
	 * Fire startElement event. Note handled the actual beginning of the element by now
	 *  and have collected all attributes (if any)
	 */
	public void startElement(HtmlParserVal lval) {
            try {
                if (contentHandler != null)
                {
                    contentHandler.startElement("", lval.sval, "", attrList);
                }
            }
            catch (SAXException ex)
            {
                parser.yyerror(ex.getMessage());
            }
	}


	/**
     * collect characters from parse stream. Unwrap the HtmlParserVal.sval 
	 * String to a character array. 
     * TODO: After creating a LexicalHandler, make sure this gets called
	 *       in the comment state.
     * TODO: This might be better done in the collection process
     *   rather than always using a String. I.e. getting a bunch of chars instead of
     *   incrementally appending one char at a time from yytext() 
     */
	public void characters(HtmlParserVal lval) 
	{
            try {
                if (contentHandler != null) // first unwrap to wrap later? for speed?
                {
                    char ch[] = lval.sval.toCharArray();		
                    contentHandler.characters(ch, 0, lval.sval.length());
                }
            }
            catch (SAXException ex)
            {
                    parser.yyerror(ex.getMessage());
            }
	}


	/**
     *  Fire endElement event. The name of the element is passed to the event handler.
	 *   Note these might be optionally missing in the HTML case.
     */
	public void endElement(HtmlParserVal lval) 
	{
            try {
                if (contentHandler != null)
                    contentHandler.endElement("", lval.sval, "");
            }
            catch (SAXException ex)
            {
                parser.yyerror(ex.getMessage());
            }
    }

	/**
	 * Fire endDocument event.
	 */
	public void endDocument() 
	{
            try {
                if (contentHandler != null)
                    contentHandler.endDocument();
            }
            catch (SAXException ex)
            {
                parser.yyerror(ex.getMessage());
            }
	}

	// LexicalHandler interface functions.

	/**
	 * comment handler
	 * Note, these are delegate to the XMLReader's LexicalHandler if any
	 * TODO:  Check the property of the reader for its existance.
	 */
	public void comment(HtmlParserVal lval) {
            try {
                if (lexicalHandler != null)
                {
                    char ch[] = lval.sval.toCharArray();		
                    lexicalHandler.comment(ch, 0, lval.sval.length());
                }
            }
            catch (SAXException ex)
            {
                parser.yyerror(ex.getMessage());
            }
	}


	/**
	 * CDATA handler
	 * Note, these are delegate to the XMLReader's LexicalHandler if any
	 * 	This only marks the start boundary condition. Text still goes through characters()
	 */
	public void startCDATA() {
            try {
                if (lexicalHandler != null)
                {
                    lexicalHandler.startCDATA();
                }
            }
            catch (SAXException ex)
            {
                    parser.yyerror(ex.getMessage());
            }
	}

	/**
	 * CDATA handler
	 * Note, these are delegate to the XMLReader's LexicalHandler if any
	 * 	This only marks the end boundary of the CDATA section. Text still goes through characters()
	 */
	public void endCDATA() {
            try {
                if (lexicalHandler != null)
                {
                    lexicalHandler.endCDATA();
                }
            }
            catch (SAXException ex)
            {
                    parser.yyerror(ex.getMessage());
            }
	}

	/**
	 * Start the beginning of the DOCTYPE (DTD) declaration
	 * Note, these are delegate to the XMLReader's LexicalHandler if any
	 */
	public void startDTD(HtmlParserVal lval) {
            try {
               if (lexicalHandler != null)
                {
                    StringTokenizer stok = new StringTokenizer(lval.sval);  // default delim = \sp

                    if (stok.hasMoreElements())
                    {
                        String target = stok.nextToken();
                        String data;
                        if (stok.hasMoreElements())
                            data = stok.nextToken();
                        else
                            data = "";

                        lexicalHandler.startDTD(target, data, null);
                    }
                }
            }
            catch (SAXException ex)
            {
                    parser.yyerror(ex.getMessage());
            }
	}

	/**
     *  End the DOCTYPE declaration
     */
	public void endDTD()	{
            try {
                if (lexicalHandler != null)
                    lexicalHandler.endDTD();
            }
            catch (SAXException ex)
            {
                    parser.yyerror(ex.getMessage());
            }
	}





    /**
     * used by the SaxParser to set itself in ParserDelegate
     */
    public void setXMLReader(XMLReader reader) {
            this.reader = reader;

        try {
            if (reader != null)
            {
                contentHandler = reader.getContentHandler(); // good idea to init first
                lexicalHandler = (LexicalHandler)reader.getProperty("http://xml.org/sax/properties/lexical-handler");
            }
        }
        catch (SAXNotRecognizedException ex)
        {
                System.err.println("No lexical handler set in property 'http://xml.org/sax/properties/lexical-handler'");
        }
        catch (SAXNotSupportedException ex)
        {
                System.err.println("Lexical handler property not supported");
        }

    }

}
