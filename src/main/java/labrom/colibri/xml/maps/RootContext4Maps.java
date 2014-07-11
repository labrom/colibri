package labrom.colibri.xml.maps;

import java.util.Map;

import labrom.colibri.xml.ElementContext;
import labrom.colibri.xml.RootContext;


/**
 * A sub-class of {@link RootContext} for use with maps instead of user-defined classes.
 * 
 * To create a new cursor definition for use specifically with maps, start with this class and the rest will follow: 
 * you will end up with a {@link CursorDef4Maps} that allows you to create {@linkplain Cursor4Maps cursors} that contain
 * instances of {@code Map<String, Object>}.
 * 
 * @author Romain Laboisse labrom@gmail.com
 *
 */
public class RootContext4Maps extends RootContext<Map<String, Object>> {
	
	
	
	@Override
	protected ElementContext<Map<String, Object>> createChildElementContext(String name) {
		return new ElementContext4Maps(this, name);
	}
	
	@Override
	public ElementContext4Maps selectElement(String name) {
		return (ElementContext4Maps)super.selectElement(name);
	}

}
