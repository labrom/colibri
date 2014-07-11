package labrom.colibri.xml;

import java.io.IOException;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

/**
 * Base abstract class for all XML nodes contexts.
 * 
 * Use {@linkplain RootContext} to start defining XML contexts.
 * 
 * @author Romain Laboisse labrom@gmail.com
 *
 * @param <T> The type of objects that Pulloid will create. This class must be written by developers.
 */
public abstract class NodeContext<T> {
	
	ElementContext<T> parent;
	NodeContext<T> nextSibling;
	String name;
	StringTransformer<?> transformer;
	FieldSetter<T> setter;


	
	protected NodeContext(ElementContext<T> parent, String name) {
		this.parent = parent;
		this.name = name;
	}
	

	public void setStringTransformer(StringTransformer<?> tr) {
		this.transformer = tr;
	}
	
	void registerField(String name) {
		registerField(name, null, null);
	}
	
	void registerField(String name, Class<?> type) {
		registerField(name, type, null);
	}
	
	/**
	 * Registers a field with a {@link StringTransformer}.
	 * @param <TT>
	 * @param name
	 * @param type The type of the field to set. If the {@link StringTransformer} is not null then the type must be specified too
	 * and type parameters must be the same.
	 * @param tr A string transformer. If not null, the {@code type} parameter must be non-null too.
	 */
	protected <TT> void registerField(String name, Class<TT> type, StringTransformer<TT> tr) {
		CursorDef<T> cursorDef = findCursorDef();
		if(cursorDef == null)
			throw new CursorException("Operation not allowed in this context, a cursor must be defined before fields are registered");
		if(tr != null && type == null)
			throw new CursorException("A string transformer was passed but no associated target type");
		this.setter = new FieldSetter<T>(cursorDef.cursorType, name, type);
		this.transformer = tr;
	}
	
	protected void registerField(String name, Class<?> type, StringTransformer<?> tr, FieldSetter<T> setter) {
		CursorDef<T> cursorDef = findCursorDef();
		if(cursorDef == null)
			throw new CursorException("Operation not allowed in this context, a cursor must be defined before fields are registered");
		this.setter = setter;
		this.transformer = tr;
	}

	CursorDef<T> findCursorDef() {
		if(parent != null)
			return parent.findCursorDef();
		return null;
	}


	abstract void hydrate(XmlPullParser p, Reflector<T> record) throws XmlPullParserException, IOException;

	
}
