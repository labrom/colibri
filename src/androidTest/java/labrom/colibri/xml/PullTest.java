package labrom.colibri.xml;

import android.test.AndroidTestCase;

import java.util.Map;
import labrom.colibri.xml.maps.Cursor4Maps;
import labrom.colibri.xml.maps.CursorDef4Maps;
import labrom.colibri.xml.maps.RootContext4Maps;


/**
 * This is the main test suite.
 * 
 * Each test method uses one of the corresponding files in the src/test/resources folder.
 * 
 * @author Romain Laboisse labrom@gmail.com
 *
 */
public class PullTest extends AndroidTestCase {

	/**
	 * Basic test.
	 * Sample file is basic.xml.
	 */
	public void testBasic() {
		
		CursorDef<Item> def = new RootContext<Item>().selectElement("root").selectElement("item").defineCursor(Item.class);
		def.getContext().selectElement("title").createField("title");
		def.getContext().createFieldOnAttribute("id", "id");
		def.getContext().selectElement("lastModified").createField("date");
		

		Cursor<Item> c = def.pull(getClass().getResourceAsStream("/basic.xml"));
		Item i = c.getNext();
		assertEquals(1, i.id);
		assertEquals("Alice au pays des merveilles", i.title);
		assertEquals("04/01/1970", i.date);

		i = c.getNext();
		assertEquals(2, i.id);
		assertEquals("L'ile au tresor", i.title);
		assertEquals("04/01/1971", i.date);
		
		assertFalse(c.hasNext());
		assertNull(c.getNext());

		// Start over, use the for each loop
		c = def.pull(getClass().getResourceAsStream("/basic.xml"));
		int count = 0;
		for(Item itm : c) {
			count ++;
			assertTrue(itm.id > 0);
			assertNotNull(itm.title);
			assertNotNull(itm.date);
		}
		assertEquals(2, count);
	}
	
    /**
     * Basic test, but record elements don't have children.
     * Sample file is attributesOnly.xml.
     */
    public void testAttributesOnly() {
        
        CursorDef<Item> def = new RootContext<Item>().selectElement("root").selectElement("item").defineCursor(Item.class);
        def.getContext().createFieldOnAttribute("id", "id");
        def.getContext().createFieldOnAttribute("title", "title");
        def.getContext().createFieldOnAttribute("date", "lastModified");
        

        Cursor<Item> c = def.pull(getClass().getResourceAsStream("/attributesOnly.xml"));
        Item i = c.getNext();
        assertEquals(1, i.id);
        assertEquals("Alice au pays des merveilles", i.title);
        assertEquals("04/01/1970", i.date);

        i = c.getNext();
        assertEquals(2, i.id);
        assertEquals("L'ile au tresor", i.title);
        assertEquals("04/01/1971", i.date);
        
        assertFalse(c.hasNext());
        assertNull(c.getNext());

        // Start over, use the for each loop
        c = def.pull(getClass().getResourceAsStream("/attributesOnly.xml"));
        int count = 0;
        for(Item itm : c) {
            count ++;
            assertTrue(itm.id > 0);
            assertNotNull(itm.title);
            assertNotNull(itm.date);
        }
        assertEquals(2, count);
    }

    /**
     * Basic test, but record elements have children that should be ignored.
     * Sample file is attributesOnlyIgnoredChildren.xml.
     */
    public void testAttributesOnlyIgnoredChildren() {
        
        CursorDef<Item> def = new RootContext<Item>().selectElement("root").selectElement("item").defineCursor(Item.class);
        def.getContext().createFieldOnAttribute("id", "id");
        def.getContext().createFieldOnAttribute("title", "title");
        def.getContext().createFieldOnAttribute("date", "lastModified");
        

        Cursor<Item> c = def.pull(getClass().getResourceAsStream("/attributesOnlyIgnoredChildren.xml"));
        Item i = c.getNext();
        assertEquals(1, i.id);
        assertEquals("Alice au pays des merveilles", i.title);
        assertEquals("04/01/1970", i.date);

        i = c.getNext();
        assertEquals(2, i.id);
        assertEquals("L'ile au tresor", i.title);
        assertEquals("04/01/1971", i.date);
        
        assertFalse(c.hasNext());
        assertNull(c.getNext());

        // Start over, use the for each loop
        c = def.pull(getClass().getResourceAsStream("/attributesOnlyIgnoredChildren.xml"));
        int count = 0;
        for(Item itm : c) {
            count ++;
            assertTrue(itm.id > 0);
            assertNotNull(itm.title);
            assertNotNull(itm.date);
        }
        assertEquals(2, count);
    }

    /**
	 * Some mapped elements are not present in the XML file.
	 * 
	 * Sample file is optional.xml.
	 */
	public void testOptional() {
		
		CursorDef<Item> def = new RootContext<Item>().selectElement("root").selectElement("item").defineCursor(Item.class);
		def.getContext().selectElement("title").createField("title");
		def.getContext().createFieldOnAttribute("id", "id");
		def.getContext().selectElement("lastModified").createField("date");
		

		Cursor<Item> c = def.pull(getClass().getResourceAsStream("/optional.xml"));
		Item i = c.getNext();
		assertEquals(1, i.id);
		assertEquals("Alice au pays des merveilles", i.title);
		assertNull(i.date);

		i = c.getNext();
		assertEquals(2, i.id);
		assertNull(i.title);
		assertEquals("04/01/1971", i.date);
		
		assertFalse(c.hasNext());
		assertNull(c.getNext());
	}
	
	/**
	 * Some mapped elements are not present in the XML file.
	 * This is a slight variation of {@link #testOptional()}.
	 * 
	 * Sample file is optional1.xml.
	 */
	public void testOptional1() {
		
		CursorDef<Item> def = new RootContext<Item>().selectElement("root").selectElement("item").defineCursor(Item.class);
		def.getContext().selectElement("title").createField("title");
		def.getContext().createFieldOnAttribute("id", "id");
		def.getContext().selectElement("lastModified").createField("date");
		

		Cursor<Item> c = def.pull(getClass().getResourceAsStream("/optional1.xml"));
		Item i = c.getNext();
		assertEquals(1, i.id);
		assertEquals("Alice au pays des merveilles", i.title);
		assertNull(i.date);
		
		assertFalse(c.hasNext());
		assertNull(c.getNext());
	}
	
	/**
	 * Some mapped elements are not present in the XML file.
	 * This is a slight variation of {@link #testOptional()}.
	 * 
	 * Sample file is optional2.xml.
	 */
	public void testOptional2() {
		
		CursorDef<Item> def = new RootContext<Item>().selectElement("root").selectElement("item").defineCursor(Item.class);
		def.getContext().selectElement("title").createField("title");
		def.getContext().createFieldOnAttribute("id", "id");
		def.getContext().selectElement("lastModified").createField("date");
		

		Cursor<Item> c = def.pull(getClass().getResourceAsStream("/optional2.xml"));
		Item i = c.getNext();
		assertEquals(2, i.id);
		assertNull(i.title);
		assertEquals("04/01/1971", i.date);
		
		assertFalse(c.hasNext());
		assertNull(c.getNext());
	}
	
	/**
	 * Some mapped elements are nested in an additional tag that is not mapped.
	 * 
	 * Sample file is levels.xml.
	 */
	public void testLevels() {
		
		CursorDef<Item> def = new RootContext<Item>().selectElement("root").selectElement("item").defineCursor(Item.class);
		def.getContext().selectElement("title").createField("title");
		def.getContext().createFieldOnAttribute("id", "id");
		def.getContext().selectElement("date").selectElement("lastModified").createField("date");
		

		Cursor<Item> c = def.pull(getClass().getResourceAsStream("/levels.xml"));
		Item i = c.getNext();
		assertEquals(1, i.id);
		assertEquals("Alice au pays des merveilles", i.title);
		assertEquals("04/01/1970", i.date);

		i = c.getNext();
		assertEquals(2, i.id);
		assertEquals("L'ile au tresor", i.title);
		assertEquals("04/01/1971", i.date);
		
		assertFalse(c.hasNext());
		assertNull(c.getNext());
	}

	/**
	 * Mapped elements are surrounded by other elements that are not mapped.
	 * 
	 * Sample file is partial.xml.
	 */
	public void testPartial() {
		
		CursorDef<Item> def = new RootContext<Item>().selectElement("root").selectElement("item").defineCursor(Item.class);
		def.getContext().selectElement("title").createField("title");
		def.getContext().createFieldOnAttribute("id", "id");
		def.getContext().selectElement("lastModified").createField("date");
		

		Cursor<Item> c = def.pull(getClass().getResourceAsStream("/partial.xml"));
		Item i = c.getNext();
		assertEquals(1, i.id);
		assertEquals("Alice au pays des merveilles", i.title);
		assertEquals("04/01/1970", i.date);

		i = c.getNext();
		assertEquals(2, i.id);
		assertEquals("L'ile au tresor", i.title);
		assertEquals("04/01/1971", i.date);
		
		assertFalse(c.hasNext());
		assertNull(c.getNext());
	}
	
	/**
	 * Mapped elements are placed deeper in the XML structure, and are also surrounded by other non-mapped elements.
	 * 
	 * Sample file is deeper.xml.
	 */
	public void testDeeper() {
		
		CursorDef<Item> def = new RootContext<Item>().selectElement("root").selectElement("items").selectElement("item").defineCursor(Item.class);
		def.getContext().selectElement("title").createField("title");
		def.getContext().createFieldOnAttribute("id", "id");
		def.getContext().selectElement("lastModified").createField("date");
		

		Cursor<Item> c = def.pull(getClass().getResourceAsStream("/deeper.xml"));
		Item i = c.getNext();
		assertEquals(1, i.id);
		assertEquals("Alice au pays des merveilles", i.title);
		assertEquals("04/01/1970", i.date);

		i = c.getNext();
		assertEquals(2, i.id);
		assertEquals("L'ile au tresor", i.title);
		assertEquals("04/01/1971", i.date);
		
		assertFalse(c.hasNext());
		assertNull(c.getNext());
	}
	
	/**
	 * The same elements is mapped more than once ("field" element).
	 * 
	 * Sample file is multiple.xml.
	 */
	public void testMultiple() {
		
		CursorDef<Item> def = new RootContext<Item>().selectElement("root").selectElement("item").defineCursor(Item.class);
		def.getContext().selectElement("title").createField("title");
		def.getContext().createFieldOnAttribute("id", "id");
		def.getContext().selectElement("lastModified").createField("date");
		def.getContext().selectElement("field").createField("authorName");
		def.getContext().selectElement("field").createField("authorDateOfBirth");
		

		Cursor<Item> c = def.pull(getClass().getResourceAsStream("/multiple.xml"));
		Item i = c.getNext();
		assertEquals(1, i.id);
		assertEquals("Alice au pays des merveilles", i.title);
		assertEquals("04/01/1970", i.date);
		assertEquals("Lewis Carroll", i.authorName);
		assertEquals("27/01/1832", i.authorDateOfBirth);

		i = c.getNext();
		assertEquals(2, i.id);
		assertEquals("L'ile au tresor", i.title);
		assertEquals("04/01/1971", i.date);
		assertEquals("Robert Louis Stevenson", i.authorName);
		assertEquals("13/11/1850", i.authorDateOfBirth);
		
		assertFalse(c.hasNext());
		assertNull(c.getNext());
	}
	
	/**
	 * An XML element has multiple mapped attributes.
	 * 
	 * Sample file is multipleAttributes.xml.
	 */
	public void testMultipleAttributes() {
		
		CursorDef<Item> def = new RootContext<Item>().selectElement("root").selectElement("item").defineCursor(Item.class);
		def.getContext().selectElement("title").createField("title");
		def.getContext().createFieldOnAttribute("id", "id");
		def.getContext().createFieldOnAttribute("date", "lastModified");
		

		Cursor<Item> c = def.pull(getClass().getResourceAsStream("/multipleAttributes.xml"));
		Item i = c.getNext();
		assertEquals(1, i.id);
		assertEquals("Alice au pays des merveilles", i.title);
		assertEquals("04/01/1970", i.date);

		i = c.getNext();
		assertEquals(2, i.id);
		assertEquals("L'ile au tresor", i.title);
		assertEquals("04/01/1971", i.date);
		
		assertFalse(c.hasNext());
		assertNull(c.getNext());
	}
	
	
	/**
	 * Multiple sub-elements of the same element are mapped.
	 * 
	 * Sample file is subElements.xml.
	 */
	public void testSubElements() {
		
		CursorDef<Item> def = new RootContext<Item>().selectElement("root").selectElement("item").defineCursor(Item.class);
		def.getContext().selectElement("title").createField("title");
		def.getContext().createFieldOnAttribute("id", "id");
		def.getContext().selectElement("lastModified").createField("date");
		ElementContext<Item> addressContext = def.getContext().selectElement("author");
		addressContext.selectElement("name").createField("authorName");
		addressContext.selectElement("dateOfBirth").createField("authorDateOfBirth");
		
		Cursor<Item> c = def.pull(getClass().getResourceAsStream("/subElements.xml"));
		Item i = c.getNext();
		assertEquals(1, i.id);
		assertEquals("Alice au pays des merveilles", i.title);
		assertEquals("04/01/1970", i.date);
		assertEquals("Lewis Carroll", i.authorName);
		assertEquals("27/01/1832", i.authorDateOfBirth);

		i = c.getNext();
		assertEquals(2, i.id);
		assertEquals("L'ile au tresor", i.title);
		assertEquals("04/01/1971", i.date);
		assertEquals("Robert Louis Stevenson", i.authorName);
		assertEquals("13/11/1850", i.authorDateOfBirth);
		
		assertFalse(c.hasNext());
		assertNull(c.getNext());
	}

	/**
	 * Multiple sub-elements of the same element are mapped, but they are not all present in the document.
	 * 
	 * Sample file is subElementsOptional.xml.
	 */
	public void testSubElementsOptional() {
		
		CursorDef<Item> def = new RootContext<Item>().selectElement("root").selectElement("item").defineCursor(Item.class);
		def.getContext().selectElement("title").createField("title");
		def.getContext().createFieldOnAttribute("id", "id");
		def.getContext().selectElement("lastModified").createField("date");
		ElementContext<Item> addressContext = def.getContext().selectElement("author");
		addressContext.selectElement("name").createField("authorName");
		addressContext.selectElement("dateOfBirth").createField("authorDateOfBirth");
		
		Cursor<Item> c = def.pull(getClass().getResourceAsStream("/subElementsOptional.xml"));
		Item i = c.getNext();
		assertEquals(1, i.id);
		assertEquals("Alice au pays des merveilles", i.title);
		assertEquals("04/01/1970", i.date);
		assertEquals("Lewis Carroll", i.authorName);
		assertNull(i.authorDateOfBirth);

		i = c.getNext();
		assertEquals(2, i.id);
		assertEquals("L'ile au tresor", i.title);
		assertEquals("04/01/1971", i.date);
		assertNull(i.authorName);
		assertEquals("13/11/1850", i.authorDateOfBirth);
		
		i = c.getNext();
		assertEquals(3, i.id);
		assertEquals("L'homme invisible", i.title);
		assertEquals("04/01/1972", i.date);
		assertNull(i.authorName);
		assertNull(i.authorDateOfBirth);

		assertFalse(c.hasNext());
		assertNull(c.getNext());
	}
	
	/**
	 * Same example as the basic() test but uses Java maps instead of instances of user-defined class.
	 * This test uses the built-in Xxxx4Maps classes.
	 * 
	 * Sample file is basic.xml.
	 */
	public void testMapBuiltin() {
		CursorDef4Maps def = new RootContext4Maps().selectElement("root").selectElement("item").defineCursor();
		def.getContext().selectElement("title").createField("title");
		def.getContext().createFieldOnAttribute("id", "id", int.class);
		def.getContext().selectElement("lastModified").createField("date");
		

		Cursor4Maps c = def.pull(getClass().getResourceAsStream("/basic.xml"));
		Map<String, Object> i = c.getNext();
		assertEquals(1, i.get("id"));
		assertEquals("Alice au pays des merveilles", i.get("title"));
		assertEquals("04/01/1970", i.get("date"));

		i = c.getNext();
		assertEquals(2, i.get("id"));
		assertEquals("L'ile au tresor", i.get("title"));
		assertEquals("04/01/1971", i.get("date"));
		
		assertFalse(c.hasNext());
		assertNull(c.getNext());

		// Start over, use the for each loop
		c = def.pull(getClass().getResourceAsStream("/basic.xml"));
		int count = 0;
		for(Map<String, Object> itm : c) {
			count ++;
			assertTrue((Integer)itm.get("id") > 0);
			assertNotNull(itm.get("title"));
			assertNotNull(itm.get("date"));
		}
		assertEquals(2, count);
	}

	/**
	 * Same example as the basic() test but uses raw Java maps instead of instances of user-defined class.
	 * 
	 * Sample file is basic.xml.
	 */
	public void testMapRaw() {
		CursorDef<Map<String, Object>> def = new RootContext<Map<String, Object>>().selectElement("root").selectElement("item").defineCursor(null);
		def.getContext().selectElement("title").createField("title");
		def.getContext().createFieldOnAttribute("id", "id", int.class);
		def.getContext().selectElement("lastModified").createField("date");
		

		Cursor<Map<String, Object>> c = def.pull(getClass().getResourceAsStream("/basic.xml"));
		Map<String, Object> i = c.getNext();
		assertEquals(1, i.get("id"));
		assertEquals("Alice au pays des merveilles", i.get("title"));
		assertEquals("04/01/1970", i.get("date"));

		i = c.getNext();
		assertEquals(2, i.get("id"));
		assertEquals("L'ile au tresor", i.get("title"));
		assertEquals("04/01/1971", i.get("date"));
		
		assertFalse(c.hasNext());
		assertNull(c.getNext());

		// Start over, use the for each loop
		c = def.pull(getClass().getResourceAsStream("/basic.xml"));
		int count = 0;
		for(Map<String, Object> itm : c) {
			count ++;
			assertTrue((Integer)itm.get("id") > 0);
			assertNotNull(itm.get("title"));
			assertNotNull(itm.get("date"));
		}
		assertEquals(2, count);
	}
	
	/**
	 * Same example as the basic() test but uses a user-defined subclass of Java map.
	 * 
	 * Sample file is basic.xml.
	 */
	public void testMapSubclass() {
		CursorDef<CustomMap> def = new RootContext<CustomMap>().selectElement("root").selectElement("item").defineCursor(CustomMap.class);
		def.getContext().selectElement("title").createField("title");
		def.getContext().createFieldOnAttribute("id", "id", int.class);
		def.getContext().selectElement("lastModified").createField("date");
		

		Cursor<CustomMap> c = def.pull(getClass().getResourceAsStream("/basic.xml"));
		CustomMap i = c.getNext();
		assertEquals(1, i.get("id"));
		assertEquals("Alice au pays des merveilles", i.get("title"));
		assertEquals("04/01/1970", i.get("date"));

		i = c.getNext();
		assertEquals(2, i.get("id"));
		assertEquals("L'ile au tresor", i.get("title"));
		assertEquals("04/01/1971", i.get("date"));
		
		assertFalse(c.hasNext());
		assertNull(c.getNext());

		// Start over, use the for each loop
		c = def.pull(getClass().getResourceAsStream("/basic.xml"));
		int count = 0;
		for(CustomMap itm : c) {
			count ++;
			assertTrue((Integer)itm.get("id") > 0);
			assertNotNull(itm.get("title"));
			assertNotNull(itm.get("date"));
		}
		assertEquals(2, count);
	}
	

	
	

}
