package hotsax.html.sax;

import org.xml.sax.*;
import org.xml.sax.helpers.*;
import org.xml.sax.ext.*;

import java.io.*;
import java.util.*;

/**
 * ParserDelegate - provides a clean interface between the
 *   Byacc/J generated HtmlParse and the SaxParser.
 *  This cleanly separates what is to be done from actually doing it. A HtmlParser
 * can be combined with a DOM or SAX backend.
 */


public interface ParserDelegate {


	// ContentHandler interface methods.
	// If any of these fire a SAXException, it is reported to parser.yyerror()

	/**
	 * Parse a startDocument event and pass it to the resigtered content handler.
	 * This method fires in response to a HtmlParser.EOF lexer token beging recognised.
	 * SOF is a virtual token fired as the first event after the file is opened.
	 */
	public void startDocument();

      /** 
        * Parse a PI and pass it to the contentHandler event
        *  (does not pass xml declaration:  <?xml version = 1>)
		* Separates the target from the data by using whitespace.
        * 
        */ 
	public void processingInstruction(HtmlParserVal target, HtmlParserVal lval);

	/**
  	 * Initialize the start of a start element. Prepares the attribute list
	 * to collect any attributes.
	 */
	public void startElement();

	/**
          * Adds an attribute to the list. The name of the attribute is normalized
          * to lowercase 
	  */
	public void addAttribute(String name, String value);

	public HtmlParserVal getAttributes();
	public void startElement(HtmlParserVal lval, HtmlParserVal attrList);

	/**
	 * Fire startElement event. Note handled the actual beginning of the element by now
	 *  and have collected all attributes (if any)
	 */
	public void startElement(HtmlParserVal lval);

	/**
     * collect characters from parse stream. Unwrap the HtmlParserVal.sval 
	 * String to a character array. 
     */
	public void characters(HtmlParserVal lval); 


	/**
     *  Fire endElement event. The name of the element is passed to the event handler.
	 *   Note these might be optionally missing in the HTML case.
     */
	public void endElement(HtmlParserVal lval);

	/**
	 * Fire endDocument event.
	 */
	public void endDocument();

	// LexicalHandler interface functions.

	/**
	 * comment handler
	 * Note, these are delegate to the XMLReader's LexicalHandler if any
	 */
	public void comment(HtmlParserVal lval);


	/**
	 * CDATA handler
	 * Note, these are delegate to the XMLReader's LexicalHandler if any
	 * 	This only marks the start boundary condition. Text still goes through characters()
	 */
	public void startCDATA();

	/**
	 * CDATA handler
	 * Note, these are delegate to the XMLReader's LexicalHandler if any
	 * 	This only marks the end boundary of the CDATA section. Text still goes through characters()
	 */
	public void endCDATA();

	/**
	 * Start the beginning of the DOCTYPE (DTD) declaration
	 * Note, these are delegate to the XMLReader's LexicalHandler if any
     * @param lval HtmlParserVal represents the document type handle, system id or public id from the DOCTYPE declaration
	 */
	public void startDTD(HtmlParserVal lval);

	/**
     *  End the DOCTYPE declaration
     */
	public void endDTD();

    /**
     * Set the the XMLReader this delegate is reading from
     * @param reader the XMLReader or really the current SAXParser
     */
    public void setXMLReader(XMLReader reader); 

}
