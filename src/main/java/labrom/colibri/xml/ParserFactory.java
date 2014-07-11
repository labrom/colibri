package labrom.colibri.xml;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

/**
 * Provides with a static method to create an XmlPull parser.
 * 
 * @author Romain Laboisse labrom@gmail.com
 */
public class ParserFactory {
	
	private static XmlPullParserFactory factory;
	
	/**
	 * Creates a new XmlPull parser.
	 * @return An XmlPull parser.
	 * @throws XmlPullParserException
	 */
	public static XmlPullParser newParser() throws XmlPullParserException {
		synchronized(ParserFactory.class) {
			if(factory == null) {
				initFactory();
			}
			return factory.newPullParser();
		}
	}
	
	private static void initFactory() throws XmlPullParserException {
		factory = XmlPullParserFactory.newInstance();
		factory.setNamespaceAware(false);
		factory.setValidating(false);
	}

}
