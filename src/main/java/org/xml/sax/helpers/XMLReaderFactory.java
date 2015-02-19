package org.xml.sax.helpers;

import org.xml.sax.XMLReader;
import org.xml.sax.SAXException;

/**
 * Factory for creating instances of XMLReader.
 *
 * This class contains static methods for creating an XML Reader based on either a string
 * or a System property.
 * 
 * This implementation is specific to the HotSAX SAX parser, but should work with other 
 * SAX implementations.
 */
final public class XMLReaderFactory
{
	private XMLReaderFactory()
	{
	}

    /**
     * Returns a new XMLReader from the value of a system property: org.xml.sax.driver.
     * @return new XMLReader from name of driver property.
     * @throws SAXException if property name is not specified or the reader could not be instantiated.
     */
	public static XMLReader createXMLReader() throws SAXException
    {
		String driverName = System.getProperty("org.xml.sax.driver");

		if (driverName != null)
			return createXMLReader(driverName);
		else
			throw new SAXException("property org.xml.sax.driver is not set");
	}

    /**
     * Returns a new XMLReader from the passed string.
     * @return new XMLReader from name.
     * @throws SAXException if name is not null or the reader could not be instantiated.
     */
	public static XMLReader createXMLReader(String name) throws SAXException
	{
		try
		{
			return (XMLReader)(Class.forName(name).newInstance());
		}
		catch(ClassNotFoundException cnfe)
		{
			throw new SAXException("XMLReader Class " + name +" not found : "+cnfe);
		}
		catch(IllegalAccessException iae)
		{
			throw new SAXException("XMLReader Access Error for class : "+name+". You may not have proper access permissions. "+iae);
		}
		catch(InstantiationException ine)
		{
			throw new SAXException("Cannot create an object of class : "+name+". It maybe an abstract class or Interface. "+ine);
		}
		catch(ClassCastException cce)
		{
			throw new SAXException("Class "+name+" does not implement XMLReader Interface. "+cce);
		}
	}
};
