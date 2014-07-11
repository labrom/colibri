package labrom.colibri.xml.maps;

import java.util.Map;

import labrom.colibri.xml.CursorDef;
import labrom.colibri.xml.ElementContext;


/**
 * A sub-class of {@link ElementContext} for use with maps instead of user-defined classes.
 * This class invalidates the use of {@link #defineCursor(Class)}, just use {@link #defineCursor()} instead.
 * 
 * @author Romain Laboisse labrom@gmail.com
 *
 */
public class ElementContext4Maps extends ElementContext<Map<String, Object>> {

	protected ElementContext4Maps(ElementContext<Map<String, Object>> parent, String name) {
		super(parent, name);
	}
	
	
	@Override
	public ElementContext4Maps selectElement(String name) {
		return (ElementContext4Maps)super.selectElement(name);
	}
	
	@Override
	protected ElementContext<Map<String, Object>> createChildElementContext(String name) {
		return new ElementContext4Maps(this, name);
	}

	/**
	 * Use {@link #defineCursor()} instead of this method.
	 * @throws UnsupportedOperationException Always throws this exception.
	 */
	@Override
	public CursorDef<Map<String, Object>> defineCursor(Class<Map<String, Object>> cursorType) {
		throw new UnsupportedOperationException("Use defineCursor() instead");
	}

	@Override
	public CursorDef4Maps defineCursor() {
		CursorDef4Maps def = new CursorDef4Maps(this);
		setCursorDef(def);
		return def;
	}


}
