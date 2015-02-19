/*
 * SaxParser.java
 *
 * Created on May 20, 2001, 12:26 AM
 */

package hotsax.html.sax;

import java.io.*;
import java.net.URL;
import java.net.MalformedURLException;
import java.util.*;
import org.xml.sax.*;
import org.xml.sax.helpers.*;
//import org.apache.xerces.utils.*;
//import org.apache.xerces.readers.*;
import hotsax.html.sax.helpers.EntityResolverImpl;

/**
 * SaxParser - lite SAX parser. Based only on 
 * @author  edh
 * @version 
 */
public class SaxParser  implements org.xml.sax.XMLReader {

    private HtmlParser yyparser;
    private EntityResolver entityResolver;
    private DTDHandler dtdHandler;
    private ContentHandler contentHandler;
    private ErrorHandler errorHandler;

    private org.xml.sax.helpers.AttributesImpl attrList;   // collect attributes in a list
    
    //private EntityHandler entityHandler;
    
    /** properties are set but ignored */
    private HashMap properties;    
    
    /** features are set but ignored */
    private HashMap features;
    
    /** lexer interface */
    private HtmlLexer lexer;
    
	/** IO reader to use */
    private Reader reader;
    
    /** Creates new SaxParser */
    public SaxParser() {
        properties = new HashMap();
        features = new HashMap();
        //entityHandler = new DefaultEntityHandler(new StringPool(), null);
        //entityHandler.setReaderFactory(new DefaultReaderFactory());
        
        entityResolver = new EntityResolverImpl();
        dtdHandler = new DefaultHandler();
        contentHandler = new DefaultHandler();
        errorHandler = new DefaultHandler();
        
        attrList = new org.xml.sax.helpers.AttributesImpl(); 

		yyparser = new HtmlParser();  // must set the reader before using
    }

    
    /**
     * create a Reader to be used by the lexer based on the InputSource
     * Such as 
     */
    protected Reader createReader(InputSource source)
        throws IOException, MalformedURLException
    {
        // create reader from source's character stream
        if (source.getCharacterStream() != null) {
            return source.getCharacterStream();
        }

        // create reader from source's byte stream
        if (source.getEncoding() != null && source.getByteStream() != null) {
            java.io.Reader reader = new InputStreamReader(source.getByteStream(), source.getEncoding());
            return reader;
        }

        // create new input stream
        InputStream is = source.getByteStream();
        if (is == null) {

            // create url and open the stream
            URL url = new URL(source.getSystemId());
            is = url.openStream();
        }

        return new InputStreamReader(is);
        
    }
        
    
    public org.xml.sax.ContentHandler getContentHandler() {
        return contentHandler;
    }
    
    public Object getProperty(String p1) throws org.xml.sax.SAXNotRecognizedException, org.xml.sax.SAXNotSupportedException {
        return properties.get(p1);
    }
    
    public void setFeature(String p1,boolean p2) throws org.xml.sax.SAXNotRecognizedException, org.xml.sax.SAXNotSupportedException {
        Boolean bool = new Boolean(p2);
        features.put(p1, bool);
    }
    
    public void setEntityResolver(org.xml.sax.EntityResolver p1) {
        entityResolver = p1;
    }

    
    public void setContentHandler(org.xml.sax.ContentHandler p1) {
        contentHandler = p1;
    }
    
    public void setDTDHandler(org.xml.sax.DTDHandler p1) {
        dtdHandler = p1;
    }
    
    public org.xml.sax.ErrorHandler getErrorHandler() {
        return errorHandler;
    }
    
    public org.xml.sax.EntityResolver getEntityResolver() {
        return entityResolver;
    }
    
    public void setErrorHandler(org.xml.sax.ErrorHandler p1) {
        errorHandler = p1;
    }
    
    public org.xml.sax.DTDHandler getDTDHandler() {
        return dtdHandler;
    }
    
    public void setProperty(String p1, Object p2) throws org.xml.sax.SAXNotRecognizedException, org.xml.sax.SAXNotSupportedException {
        properties.put(p1, p2);
    }
    
    public boolean getFeature(String p1) throws org.xml.sax.SAXNotRecognizedException, org.xml.sax.SAXNotSupportedException {
        Boolean bool = (Boolean)features.get(p1);
        
        return bool.booleanValue();
    }


    /**
     * Parser setup code. Initialize a new reader based on type of URI
     * Call into actual parser below with newly created InputSource.
     *   handles URIs of type "http://" and file://
     *
     * @param p1 URI to open
     */
    
    public void parse(String p1) 
        throws IOException, org.xml.sax.SAXException 
    {
        InputSource source = entityResolver.resolveEntity(p1,p1);
        try {
           if (source == null) { // entity resolver didn't do its job, try normal URL opening
              source = new InputSource(p1);
            
              source.setCharacterStream(createReader(source)); 
           }
           parse(source);
        }
        catch (Exception ex)
        {
            System.err.println("caught exception parsing " + ex.getClass().getName() + " " + ex.getMessage());
            ex.printStackTrace();
        }
        finally {
            // NOTE: Changed code to attempt to close the stream
            //       even after parsing failure. -Ac
            try {
                Reader reader = source.getCharacterStream();
                if (reader != null) { 
                    reader.close();
                }
                else {
                    InputStream is = source.getByteStream();
                    if (is != null) {
                        is.close();
                    }
                }
            }
            catch (IOException e) {
                // ignore
            }
        }
           
    }
    
    
    /**
     * Parse the input document using the current InputSource's reader
     */
    
    public void parse(org.xml.sax.InputSource p1) 
        throws java.io.IOException, org.xml.sax.SAXException 
    {
        Boolean debugWrapper = (Boolean)properties.get("debug");
        boolean debug = (debugWrapper == null ? false : debugWrapper.booleanValue());
        
        Reader reader = p1.getCharacterStream();
        
		yyparser.setReader(reader);
		//yyparser = new HtmlParser(reader);
		ParserDelegate delegate = yyparser.getParserDelegate();
        delegate.setXMLReader(this);

        if (debug) {
            yyparser.yydebug = true;
        }
        yyparser.yyparse();    // fires off Content/Lexical handler events vis ParserDelegate
        
    }

    public void startDocument()
        throws SAXException
    {
        if (contentHandler != null)
            contentHandler.startDocument();
    }        

    public void endDocument()
        throws SAXException
    {
        if (contentHandler != null)
            contentHandler.endDocument();
    }        
    
    
    
    /**
     * collect attributes into a list and call ContentHandler.startElement
     **/
    public void startElement(String name)
        throws SAXException
    {
        if (contentHandler != null)
            contentHandler.startElement("", name, "", (Attributes)attrList);
    }
    
    public void endElement(String name)
        throws SAXException
    {
        if (contentHandler != null)
            contentHandler.endElement("", name, "");
    }
    
	// accessor methods
	
	/** 
	 * Return the HtmlParser handle.
	 * @return the handle to yyparser.
     */
    public HtmlParser getyyParser() { return yyparser; }
}
