package labrom.colibri.xml.maps;

import java.io.InputStream;
import java.io.Reader;
import java.util.Map;

import labrom.colibri.xml.CursorDef;
import labrom.colibri.xml.CursorException;
import labrom.colibri.xml.ParserFactory;
import labrom.colibri.xml.Reflector;
import org.xmlpull.v1.XmlPullParserException;

/**
 * A sub-class of {@link CursorDef} for use with maps instead of user-defined classes.
 * 
 * @author Romain Laboisse labrom@gmail.com
 *
 */
public class CursorDef4Maps extends CursorDef<Map<String, Object>> {

	protected CursorDef4Maps(ElementContext4Maps ctx) {
		super(ctx);
	}
	
	@Override
	public Cursor4Maps pull(InputStream input) {
		try {
			return new Cursor4Maps(new Reflector<Map<String, Object>>(), getContext(), ParserFactory.newParser(), input);
		} catch (XmlPullParserException e) {
			throw new CursorException(e);
		}
	}
	
	@Override
	public Cursor4Maps pull(Reader reader) {
		try {
			return new Cursor4Maps(new Reflector<Map<String, Object>>(), getContext(), ParserFactory.newParser(), reader);
		} catch (XmlPullParserException e) {
			throw new CursorException(e);
		}
	}
}
