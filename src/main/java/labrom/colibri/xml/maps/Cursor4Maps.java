package labrom.colibri.xml.maps;

import java.io.InputStream;
import java.io.Reader;
import java.util.Map;

import labrom.colibri.xml.Cursor;
import labrom.colibri.xml.ElementContext;
import labrom.colibri.xml.Reflector;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

/**
 * A sub-class of {@link Cursor} for use with maps instead of user-defined classes.
 * 
 * @author Romain Laboisse labrom@gmail.com
 *
 */
public class Cursor4Maps extends Cursor<Map<String, Object>> {

	protected Cursor4Maps(Reflector<Map<String, Object>> reflector, ElementContext<Map<String, Object>> ctx, XmlPullParser parser, InputStream input) throws XmlPullParserException {
		super(reflector, ctx, parser, input);
	}

	protected Cursor4Maps(Reflector<Map<String, Object>> reflector, ElementContext<Map<String, Object>> ctx, XmlPullParser parser, Reader input) throws XmlPullParserException {
		super(reflector, ctx, parser, input);
	}
	
	

}
