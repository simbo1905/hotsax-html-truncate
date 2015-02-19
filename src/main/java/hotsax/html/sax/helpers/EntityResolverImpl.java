/*
 * EntityResolverImpl.java
 *
 * Created on June 28, 2001, 1:16 AM
 */

package hotsax.html.sax.helpers;


import java.util.*;
import java.io.*;
import org.xml.sax.*;

/**
 * Special Enitity resolver to handle URIs like file: etc
 
 * @author  edh
 * @version 
 */
public class EntityResolverImpl implements EntityResolver {

    /** Creates new EntityResolverImpl */
    public EntityResolverImpl() {
    }

    
    /** 
     * Resolve anu local entities based on URI. Like file://home/edh/index.html etc.
     */
    
    public InputSource resolveEntity(String publicId, String systemId) 
        throws org.xml.sax.SAXException, java.io.IOException {
            
        StringTokenizer tokenizer = new StringTokenizer(systemId, ":");

        if (tokenizer.hasMoreElements()) {
            String protocol = tokenizer.nextToken();
            if (protocol.equalsIgnoreCase("file")) {   // open a reader on the file stream
                String path = tokenizer.nextToken();
                path = path.substring(1);              // should be /home/edh/index.html
                Reader fileReader = new FileReader(path);
                return new InputSource(fileReader);
            }
        }

        return null;  // the default
    }
    
}
