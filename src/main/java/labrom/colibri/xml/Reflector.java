package labrom.colibri.xml;

import java.util.HashMap;


/**
 * Is responsible for instantiating objects in a {@link Cursor}.
 * This can be done in three different ways:<ol>
 * <li>By instantiating a class (use {@link #Reflector(Class)})</li>
 * <li>By using a {@link ObjectFactory} (use {@link #Reflector(ObjectFactory)})</li>
 * <li>By creating a Map (use {@link #Reflector()})</li>
 * </ol>
 * 
 * @author Romain Laboisse labrom@gmail.com
 *
 * @param <T> The type of objects to create. If this Reflector is intended to work with a Map, T MUST be Map<String, Object> for things to work as expected.
 */
public class Reflector<T> {


	T object;
	private Class<T> clazz;
	private ObjectFactory<T> factory;
	
	/**
	 * This reflector will use a Map. This is the same as calling any of the
	 * other constructors will a null argument.
	 */
	public Reflector() {
		// In this case we'll use a map
	}
	
	/**
	 * 
	 * @param clazz The class to instantiate. If null, will use a Map (same a {@link #Reflector()}).
	 */
	public Reflector(Class<T> clazz) {
		this.clazz = clazz;
	}
	
	/**
	 * 
	 * @param f The object factory. If null, will use a Map (same a {@link #Reflector()}).
	 */
	public Reflector(ObjectFactory<T> f) {
		this.factory = f;
	}
	
	
	@SuppressWarnings("unchecked")
	T createObject() {
		// If there's a factory, use it
		if(factory != null) {
			object =  factory.createObject();
			return object;
		}
		
		// If there's no class, use a Map
		if(clazz == null) {
			// T must be Map<String, Object> for things to work as expected.
			object = (T)new HashMap<String, Object>();
			return object;
		}
		
		// There's a class, try to instantiate from it
		try {
			object = clazz.newInstance();
		} catch (IllegalAccessException e) {
			throw new CursorException(e);
		} catch (InstantiationException e) {
			throw new CursorException(e);
		}
		return object;
	}
	
	public void set(FieldSetter<T> setter, Object value) {
		setter.set(object, value);
	}
	

}
