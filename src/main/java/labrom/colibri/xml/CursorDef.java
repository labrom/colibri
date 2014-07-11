package labrom.colibri.xml;

import java.io.InputStream;
import java.io.Reader;

import org.xmlpull.v1.XmlPullParserException;
/**
 * A cursor definition represents a mapping from XML structures to Java objects.
 * An instance of CursorDef can be (re)used to create one or multiple {@linkplain Cursor cursors} on an actual XML stream.
 * 
 * To obtain a CursorDef, use {@link RootContext} and {@link ElementContext}.
 * 
 * @author Romain Laboisse labrom@gmail.com
 * 
 * @see RootContext
 * @see ElementContext
 *
 * @param <T> The type of objects that Pulloid will create. This class must be written by developers.
 */
public class CursorDef<T> {
	
	Class<T> cursorType;
	ElementContext<T> ctx;
	
	protected CursorDef(ElementContext<T> ctx) {
		this(null, ctx);
	}
	
	protected CursorDef(Class<T> cursorType, ElementContext<T> ctx) {
		this.cursorType = cursorType;
		this.ctx = ctx;
	}
	
	public ElementContext<T> getContext() {
		return ctx;
	}
	
	/**
	 * Creates a new cursor. Many cursors can be created from a single CursorDef.
	 * @param input The XML input stream.
	 * @return A cursor.
	 */
	public Cursor<T> pull(InputStream input) {
		try {
			return new Cursor<T>(new Reflector<T>(cursorType), ctx, ParserFactory.newParser(), input);
		} catch (XmlPullParserException e) {
			throw new CursorException(e);
		}
	}

	/**
	 * Creates a new cursor. Many cursors can be created from a single CursorDef.
	 * @param reader The XML reader.
	 * @return A cursor.
	 */
	public Cursor<T> pull(Reader reader) {
		try {
			return new Cursor<T>(new Reflector<T>(cursorType), ctx, ParserFactory.newParser(), reader);
		} catch (XmlPullParserException e) {
			throw new CursorException(e);
		}
	}

	/**
	 * Creates a new cursor. Many cursors can be created from a single CursorDef.
	 * @param input The XML input stream.
	 * @param objectFactory An object factory that allows the application to provide its own objects instantiation mechanism.
	 * @return A cursor.
	 */
	public Cursor<T> pull(InputStream input, ObjectFactory<T> objectFactory) {
		try {
			return new Cursor<T>(new Reflector<T>(objectFactory), ctx, ParserFactory.newParser(), input);
		} catch (XmlPullParserException e) {
			throw new CursorException(e);
		}
	}

	/**
	 * Creates a new cursor. Many cursors can be created from a single CursorDef.
	 * @param reader The XML reader.
	 * @param objectFactory An object factory that allows the application to provide its own objects instantiation mechanism.
	 * @return A cursor.
	 */
	public Cursor<T> pull(Reader reader, ObjectFactory<T> objectFactory) {
		try {
			return new Cursor<T>(new Reflector<T>(objectFactory), ctx, ParserFactory.newParser(), reader);
		} catch (XmlPullParserException e) {
			throw new CursorException(e);
		}
	}
}
