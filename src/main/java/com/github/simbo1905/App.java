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

    		StringBuilder wholeTag = new StringBuilder();
    		boolean hasText = false;
    		boolean hasElements = false;
    		String lastStart = "";
    		
			@Override
			public void characters(char[] ch, int start, int length)
					throws SAXException {
				String text = (new String(ch, start, length)).trim();
				wholeTag.append(text);
				hasText = true;
			}

			@Override
			public void endElement(String namespaceURI, String localName,
					String qName) throws SAXException {
				if( !hasText && !hasElements && lastStart.equals(localName)) {
					builder.append("<"+localName+"/>");
				} else {
					wholeTag.append("</"+ localName +">");
					builder.append(wholeTag.toString());
				}
				
				wholeTag = new StringBuilder();
				hasText = false;
				hasElements = false;
			}

			@Override
			public void startElement(String namespaceURI, String localName,
					String qName, Attributes atts) throws SAXException {
				wholeTag.append("<"+ localName);
				for( int i = 0; i < atts.getLength(); i++) {
					wholeTag.append(" "+atts.getQName(i)+"='"+atts.getValue(i)+"'");
					hasElements = true;
				}
				wholeTag.append(">");
				lastStart = localName;
				hasText = false;
			}
    		
    	};
		parser.setContentHandler(handler);
		
		//parser.parse(new InputSource( new StringReader( "<div>this is the <em>end</em> my <br> friend <a href=\"whatever\">some link</a>" ) ));
		parser.parse(new InputSource( new StringReader( "<img src=\"http://d2qxdzx5iw7vis.cloudfront.net/34775606.jpg\" />\n<br/><a href=\"htt" ) ));
		
        System.out.println( builder.toString() );

    }
}
