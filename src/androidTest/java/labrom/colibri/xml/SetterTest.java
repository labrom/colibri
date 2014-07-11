package labrom.colibri.xml;

import android.test.AndroidTestCase;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class SetterTest extends AndroidTestCase {
	
	
	static class Bean1 {
		public String string1;
		public int int1;
		public Integer int11;
		String inaccessible;
		public boolean bool1;
		public Boolean bool11;
		public Calendar cal1;
		public Date date1;
		
	}
	
	static class Bean2 {
		String string2;
		int int2;
		Integer int22;
		boolean bool2;
		Boolean bool22;
		Calendar cal2;
		Date date2;
		
		public String getString2() {
			return string2;
		}
		public void setString2(String string2) {
			this.string2 = string2;
		}
		public int getInt2() {
			return int2;
		}
		public void setInt2(int int2) {
			this.int2 = int2;
		}
		public Integer getInt22() {
			return int22;
		}
		public void setInt22(Integer int22) {
			this.int22 = int22;
		}
		public boolean isBool2() {
			return bool2;
		}
		public void setBool2(boolean bool2) {
			this.bool2 = bool2;
		}
		public Boolean getBool22() {
			return bool22;
		}
		public void setBool22(Boolean bool22) {
			this.bool22 = bool22;
		}
		public Calendar getCal2() {
			return cal2;
		}
		public void setCal2(Calendar cal2) {
			this.cal2 = cal2;
		}
		public Date getDate2() {
			return date2;
		}
		public void setDate2(Date date2) {
			this.date2 = date2;
		}
	}
	
	
	public void testSetString1() {
		Bean1 b = new Bean1();
		
		new FieldSetter<Bean1>(Bean1.class, "string1", String.class).set(b, "test");
		assertEquals("test", b.string1);
		new FieldSetter<Bean1>(Bean1.class, "string1").set(b, "testt");
		assertEquals("testt", b.string1);
	}
	
	public void testSetString2() {
		Bean2 b = new Bean2();
		
		new FieldSetter<Bean2>(Bean2.class, "string2", String.class).set(b, "test");
		assertEquals("test", b.string2);
		new FieldSetter<Bean2>(Bean2.class, "string2").set(b, "testt");
		assertEquals("testt", b.string2);
	}

	public void testNotAccessible1() {
		Bean1 b = new Bean1();

		try {
			new FieldSetter<Bean1>(Bean1.class, "inaccessible").set(b, "I tried");
		} catch(Exception e) {
			assertTrue(e instanceof CursorException);
		}
	}
	
	public void testNotFound1() {
		Bean1 b = new Bean1();

		try {
			new FieldSetter<Bean1>(Bean1.class, "inexistant").set(b, "I tried");
		} catch(Exception e) {
			assertTrue(e instanceof CursorException);
		}
	}
	
	public void testNotFound2() {
		Bean2 b = new Bean2();

		try {
			new FieldSetter<Bean2>(Bean2.class, "inexistant").set(b, "I tried");
		} catch(Exception e) {
			assertTrue(e instanceof CursorException);
		}
	}
	
	public void testWrongDeclaredType1() {
		try {
			new FieldSetter<Bean1>(Bean1.class, "int1", Calendar.class);
		} catch(Exception e) {
			assertTrue(e instanceof CursorException);
		}
	}

	public void testWrongDeclaredType2() {
		try {
			new FieldSetter<Bean2>(Bean2.class, "int2", Calendar.class);
		} catch(Exception e) {
			assertTrue(e instanceof CursorException);
		}
		try {
			new FieldSetter<Bean2>(Bean2.class, "int2", Integer.class);
		} catch(Exception e) {
			assertTrue(e instanceof CursorException);
		}
	}

	public void testConvertToBool1() {
		Bean1 b = new Bean1();
		
		new FieldSetter<Bean1>(Bean1.class, "bool1").set(b, "true");
		assertEquals(true, b.bool1);
		new FieldSetter<Bean1>(Bean1.class, "bool11").set(b, "1");
		assertEquals(true, (boolean)b.bool11);
		new FieldSetter<Bean1>(Bean1.class, "bool1").set(b, "");
		assertEquals(false, b.bool1);
		new FieldSetter<Bean1>(Bean1.class, "bool11").set(b, "0");
		assertEquals(false, (boolean)b.bool11);
		new FieldSetter<Bean1>(Bean1.class, "bool1", boolean.class).set(b, "true");
		assertEquals(true, b.bool1);
		new FieldSetter<Bean1>(Bean1.class, "bool11", Boolean.class).set(b, "1");
		assertEquals(true, (boolean)b.bool11);
		new FieldSetter<Bean1>(Bean1.class, "bool1", boolean.class).set(b, "");
		assertEquals(false, b.bool1);
		new FieldSetter<Bean1>(Bean1.class, "bool11", Boolean.class).set(b, "0");
		assertEquals(false, (boolean)b.bool11);
	}
	
	public void testConvertToBool2() {
		Bean2 b = new Bean2();
		
		new FieldSetter<Bean2>(Bean2.class, "bool2").set(b, "true");
		assertEquals(true, b.bool2);
		new FieldSetter<Bean2>(Bean2.class, "bool22").set(b, "1");
		assertEquals(true, (boolean)b.bool22);
		new FieldSetter<Bean2>(Bean2.class, "bool2").set(b, "");
		assertEquals(false, b.bool2);
		new FieldSetter<Bean2>(Bean2.class, "bool22").set(b, "0");
		assertEquals(false, (boolean)b.bool22);
		new FieldSetter<Bean2>(Bean2.class, "bool2", boolean.class).set(b, "true");
		assertEquals(true, b.bool2);
		new FieldSetter<Bean2>(Bean2.class, "bool22", Boolean.class).set(b, "1");
		assertEquals(true, (boolean)b.bool22);
		new FieldSetter<Bean2>(Bean2.class, "bool2", boolean.class).set(b, "");
		assertEquals(false, b.bool2);
		new FieldSetter<Bean2>(Bean2.class, "bool22", Boolean.class).set(b, "0");
		assertEquals(false, (boolean)b.bool22);
	}

	public void testConvertToNumber1() {
		Bean1 b = new Bean1();
		
		new FieldSetter<Bean1>(Bean1.class, "int1").set(b, "1");
		assertEquals(1, b.int1);
		new FieldSetter<Bean1>(Bean1.class, "int11").set(b, "11");
		assertEquals((Integer)11, b.int11);
		new FieldSetter<Bean1>(Bean1.class, "int1", int.class).set(b, "1");
		assertEquals(1, b.int1);
		new FieldSetter<Bean1>(Bean1.class, "int11", Integer.class).set(b, "11");
		assertEquals((Integer)11, b.int11);
	}

	public void testConvertToNumber2() {
		Bean2 b = new Bean2();
		
		new FieldSetter<Bean2>(Bean2.class, "int2").set(b, "2");
		assertEquals(2, b.int2);
		new FieldSetter<Bean2>(Bean2.class, "int22").set(b, "22");
		assertEquals((Integer)22, b.int22);
		new FieldSetter<Bean2>(Bean2.class, "int2", int.class).set(b, "2");
		assertEquals(2, b.int2);
		new FieldSetter<Bean2>(Bean2.class, "int22", Integer.class).set(b, "22");
		assertEquals((Integer)22, b.int22);
	}

	public void testConvertToDate1() {
		Bean1 b = new Bean1();
		
		new FieldSetter<Bean1>(Bean1.class, "cal1").set(b, 1300000000);
		assertEquals(2011, b.cal1.get(Calendar.YEAR));
		new FieldSetter<Bean1>(Bean1.class, "cal1").set(b, 1300000000000l);
		assertEquals(2011, b.cal1.get(Calendar.YEAR));
		new FieldSetter<Bean1>(Bean1.class, "date1").set(b, 1300000000);
		assertEquals(2011, getCalendar(b.date1).get(Calendar.YEAR));
		new FieldSetter<Bean1>(Bean1.class, "date1").set(b, 1300000000000l);
		assertEquals(2011, getCalendar(b.date1).get(Calendar.YEAR));
	}
	
	public void testConvertToDate2() {
		Bean2 b = new Bean2();
		
		new FieldSetter<Bean2>(Bean2.class, "cal2").set(b, 1300000000);
		assertEquals(2011, b.cal2.get(Calendar.YEAR));
		new FieldSetter<Bean2>(Bean2.class, "cal2").set(b, 1300000000000l);
		assertEquals(2011, b.cal2.get(Calendar.YEAR));
		new FieldSetter<Bean2>(Bean2.class, "date2").set(b, 1300000000);
		assertEquals(2011, getCalendar(b.date2).get(Calendar.YEAR));
		new FieldSetter<Bean2>(Bean2.class, "date2").set(b, 1300000000000l);
		assertEquals(2011, getCalendar(b.date2).get(Calendar.YEAR));
	}

	private Calendar getCalendar(Date d) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(d);
		return cal;
	}
	


	public void testSetWrongType1() {
		Bean1 b = new Bean1();
		
		try {
			new FieldSetter<Bean1>(Bean1.class, "int1").set(b, "_1");
			fail("A NumberFormatException should have been thrown");
		} catch(Exception e) {
			assertTrue(e instanceof NumberFormatException);
		}
	}
	
	public void testSetWrongType2() {
		Bean2 b = new Bean2();

		try {
			new FieldSetter<Bean2>(Bean2.class, "int2").set(b, "_2");
			fail("A NumberFormatException should have been thrown");
		} catch(Exception e) {
			assertTrue(e instanceof NumberFormatException);
		}
	}
	
	public void testMap() {
		Map<String, Object> map = new HashMap<String, Object>();
		
		new FieldSetter<Map<String, Object>>(null, "field1").set(map, "value1");
		assertEquals("value1", map.get("field1"));
		new FieldSetter<Map<String, Object>>(null, "field2", Integer.class).set(map, "2");
		assertEquals(2, map.get("field2"));
		new FieldSetter<Map<String, Object>>(null, "field3", int.class).set(map, "3");
		assertEquals(3, map.get("field3"));
		new FieldSetter<Map<String, Object>>(null, "field4").set(map, "4");
		assertEquals("4", map.get("field4")); // String assumed if not target field type is set
		new FieldSetter<Map<String, Object>>(null, "field5").set(map, 5);
		assertEquals(5, map.get("field5"));
	}
}
