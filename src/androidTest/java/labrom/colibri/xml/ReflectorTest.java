package labrom.colibri.xml;

import android.test.AndroidTestCase;

import java.util.Map;

public class ReflectorTest extends AndroidTestCase {
	
	
	static class Bean1 {
	}
	

	
	public void testCreateObject1() {
		Reflector<Bean1> r = new Reflector<Bean1>(Bean1.class);
		r.createObject();
		assertNotNull(r.object);
		assertEquals(Bean1.class, r.object.getClass());
	}
	
	
	public void testMap() {
		Reflector<Map<String, Object>> r = new Reflector<Map<String, Object>>();
		r.createObject();
		assertNotNull(r.object);
		assertTrue(Map.class.isAssignableFrom(r.object.getClass()));
	}
	
	public void testUseFactory() {
		Reflector<Bean1> r = new Reflector<Bean1>(new ObjectFactory<Bean1>() {
			@Override
			public Bean1 createObject() {
				return new Bean1();
			}
		});
		r.createObject();
		assertNotNull(r.object);
		assertEquals(Bean1.class, r.object.getClass());
	}

}
