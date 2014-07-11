package labrom.colibri.xml;

import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;
import java.util.Iterator;
import java.util.NoSuchElementException;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

/**
 * A cursor on an XML stream. Before using a Cursor one must define a {@link CursorDef} that describes the mapping
 * from XML elements and attributes to the Java class variables.
 * 
 * @author Romain Laboisse labrom@gmail.com
 * 
 * @param <T> The type of objects that Pulloid will create. This class must be written by developers.
 */
public class Cursor<T> implements Iterator<T>, Iterable<T> {
	

	private ElementContext<T> ctx;
	private XmlPullParser parser;
	private Reflector<T> reflector;
	
	private T next;
	
	
	protected Cursor(Reflector<T> reflector, ElementContext<T> ctx, XmlPullParser parser, InputStream input) throws XmlPullParserException {
		this.ctx = ctx;
		this.parser = parser;
		parser.setInput(input, null);
		this.reflector = reflector;
	}

	protected Cursor(Reflector<T> reflector, ElementContext<T> ctx, XmlPullParser parser, Reader input) throws XmlPullParserException {
		this.ctx = ctx;
		this.parser = parser;
		parser.setInput(input);
		this.reflector = reflector;
	}

	@Override
	public boolean hasNext() {
		try {
			if(ParserUtil.isEndDocument(parser))
				return false;
			if(ctx.position(parser) != ctx)
				return false;

			next = reflector.createObject();
			ctx.hydrate(parser, reflector);
			return true;
		} catch (XmlPullParserException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
	}
	
	public T getNext() {
		if(next != null || hasNext()) {
			T result = next;
			next = null;
			return result;
		}
		return null; // FIXME Should throw an Exception instead?
	}
	
	@Override
	public T next() {
		T next = getNext();
		if(next == null)
			throw new NoSuchElementException();
		return next;
	}
	
	@Override
	public void remove() {
		throw new UnsupportedOperationException();
	}
	
	@Override
	public Iterator<T> iterator() {
		return this;
	}
	

}
