package com.github.simbo1905;

import java.io.StringReader;

import org.xml.sax.Attributes;
import org.xml.sax.ContentHandler;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;
import org.xml.sax.helpers.XMLReaderFactory;

public class App 
{
    public static void main( String[] args ) throws Exception
    {
    	XMLReader parser = XMLReaderFactory.createXMLReader("hotsax.html.sax.SaxParser");
    	
    	final StringBuilder builder = new StringBuilder();
    	
    	ContentHandler handler = new DoNothingContentHandler(){

			@Override
			public void endElement(String namespaceURI, String localName,
					String qName) throws SAXException {
				builder.append("</"+ localName +">");
			}

			@Override
			public void startElement(String namespaceURI, String localName,
					String qName, Attributes atts) throws SAXException {
				builder.append("<"+ localName + " ");
				for( int i = 0; i < atts.getLength(); i++) {
					builder.append(atts.getLocalName(i)+"='"+atts.getValue(i)+"'");
				}
				builder.append(">");
			}
    		
    	};
		parser.setContentHandler(handler);
		
		String myString = "<img src=\"http://d2qxdzx5iw7vis.cloudfront.net/34775606.jpg\" />\n<br/><a href=\"htt";
		
		InputSource inputSource = new InputSource( new StringReader( myString ) );
		
		parser.parse(inputSource);
    	
        System.out.println( builder.toString() );
    }
}
