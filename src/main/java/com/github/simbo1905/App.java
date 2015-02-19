package com.github.simbo1905;

import java.io.StringReader;

import org.xml.sax.Attributes;
import org.xml.sax.ContentHandler;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;
import org.xml.sax.helpers.XMLReaderFactory;

public class App {
	public static void main(String[] args) throws Exception {
		XMLReader parser = XMLReaderFactory
				.createXMLReader("hotsax.html.sax.SaxParser");

		final StringBuilder builder = new StringBuilder();

		ContentHandler handler = new DoNothingContentHandler() {

			StringBuilder wholeTag = new StringBuilder();

			@Override
			public void characters(char[] ch, int start, int length)
					throws SAXException {
				wholeTag.append(new String(ch, start, length));
			}

			@Override
			public void endElement(String namespaceURI, String localName,
					String qName) throws SAXException {
				wholeTag.append("</" + localName + ">");
			}

			@Override
			public void startElement(String namespaceURI, String localName,
					String qName, Attributes atts) throws SAXException {
				wholeTag.append("<" + localName);
				for (int i = 0; i < atts.getLength(); i++) {
					wholeTag.append(" " + atts.getLocalName(i) + "='"
							+ atts.getValue(i) + "'");
				}
				wholeTag.append(">");
				builder.append(wholeTag.toString());
				wholeTag = new StringBuilder();
			}

		};
		parser.setContentHandler(handler);

		// parser.parse(new InputSource( new StringReader(
		// "<div>this is the <em>end</em> my <br> friend <a href=\"whatever\">some link</a>"
		// ) ));
		parser.parse(new InputSource(
				new StringReader(
						"<img src=\"http://d2qxdzx5iw7vis.cloudfront.net/34775606.jpg\" />\n<br/><a href=\"htt")));

		System.out.println(builder.toString());

	}
}
