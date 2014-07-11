package labrom.colibri.xml;

import java.io.IOException;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

/**
 * Represents the context of a specific XML attibute.
 * This class is never used directly by client applications since there is no point
 * in navigating from XML elements. It is only used internally to allow mapping
 * of XML attributes to Java objects fields.
 * 
 * @author Romain Laboisse labrom@gmail.com
 *
 * @param <T>
 */
class AttributeContext<T> extends NodeContext<T> {

	protected AttributeContext(ElementContext<T> parent, String name) {
		super(parent, name);
	}

	@Override
	void hydrate(XmlPullParser p, Reflector<T> record) throws XmlPullParserException, IOException {
		String strval = p.getAttributeValue(null, name);
		Object val = transformer != null ? transformer.transform(strval) : strval;
		record.set(setter, val);
		if(nextSibling != null)
			nextSibling.hydrate(p, record);
	}

}
