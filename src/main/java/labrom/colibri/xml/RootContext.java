package labrom.colibri.xml;

import java.io.IOException;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

/**
 * Starting point for creating a {@linkplain CursorDef cursor definition}, e.g. an XML-to-Java mapping.
 * 
 * A RootContext represents the root of the XML document, before any element is selected. Once a RootContext
 * is created, the top-level XML element must be explicitly selected by calling {@link #selectElement(String)} with the
 * name of the top-level XML element.
 * 
 * See {@link ElementContext#selectElement(String)} for more details on how to navigate through XML elements.
 * 
 * @author Romain Laboisse labrom@gmail.com
 *
 * @see ElementContext
 * 
 * @param <T> The type of objects that Pulloid will create. This class must be written by developers.
 */
public class RootContext<T> extends ElementContext<T> {
	
	
	/**
	 * Creates a new root context. This is the first step in defining a cursor definition
	 */
	public RootContext() {
		super(null, null);
	}


	@Override
	ElementContext<T> position(XmlPullParser p) throws XmlPullParserException, IOException {
		while(!ParserUtil.isStartDocument(p))
			p.nextTag();
		return this;
	}

}
