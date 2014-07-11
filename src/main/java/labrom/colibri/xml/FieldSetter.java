package labrom.colibri.xml;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.Map;

/**
 * A field setter is used to set values to a specific field in a Java class. These values are mostly string values extracted from XML.
 * However it can be other types of values because of the use of {@link StringTransformer}.
 * 
 * @author Romain Laboisse labrom@gmail.com
 *
 * @param <T> The target object type.
 */
public class FieldSetter<T> {


	/**
	 * The list of types that this setter knows how to transform strings to.
	 */
	@SuppressWarnings("unchecked")
	private static final Collection<Class<?>> SUPPORTED_CONVERT_TYPES = new ArrayList<Class<?>>(Arrays.asList(
			boolean.class,
			Boolean.class,
			byte.class,
			Byte.class,
			short.class,
			Short.class,
			int.class,
			Integer.class,
			float.class,
			Float.class,
			long.class,
			Long.class,
			double.class,
			Double.class,
			BigInteger.class,
			BigDecimal.class,
			Number.class,
			Calendar.class,
			Date.class));
	
	private static final long PIVOT_DATE_MILLISEC = 33000000000l; // Around year 1971 in milli-seconds, around year 3000 in seconds
	
	private Field field;
	private Class<?> fieldType;
	private String fieldName;
	private Method setterMethod;
	private boolean canConvert;

	/**
	 * This constructor is the same as {@code FieldSetter(targetType, fieldName, null)}.
	 * @param targetType
	 * @param fieldName
	 */
	public FieldSetter(Class<T> targetType, String fieldName) {
		this(targetType, fieldName, null);
	}

	/**
	 * Creates a field setter.
	 * @param targetType The target type. If null, this setter will assume the target object is a Map of String, Object.
	 * @param fieldName Must not be null.
	 * @param targetFieldType The actual type of the field to set. It can be null however we make the assumption that if none is specified
	 * then this setter will be used with string values only.
	 */
	public FieldSetter(Class<T> targetType, String fieldName, Class<?> targetFieldType) {
		if(fieldName == null)
			throw new NullPointerException("fieldName must not be null");
		
		// If this is a Map, we'll just call put(String, Object) - we support Map<String, Object> only
		if(targetType == null || Map.class.isAssignableFrom(targetType)) {
			this.fieldName = fieldName;
			setFieldType(targetFieldType != null ? targetFieldType : String.class); // If not type specified, String is assumed
			return;
		}
		
		try {
			// We don't need to know the field type since we're just looking for the member field with the right name
			this.field = targetType.getField(fieldName);
			if(targetFieldType != null && field.getType() != targetFieldType) { // If a type was specified, check it's the right type
				throw new CursorException(String.format("Found a field but with wrong type: %1$s instead of assumed %2$s", field.getType().getName(), targetFieldType.getName()));
			}
			setFieldType(field.getType());
		} catch(NoSuchFieldException nsfe) {
			// No such field, trying setter method
			String setterMethodName = "set" + fieldName.substring(0, 1).toUpperCase() + fieldName.substring(1);

			if(targetFieldType != null) {
				try {
					this.setterMethod = targetType.getMethod(setterMethodName, targetFieldType);
					setFieldType(targetFieldType);
					return;
				} catch (SecurityException se) {
					throw new CursorException(se);
				} catch (NoSuchMethodException nsme) {
					throw new CursorException(String.format("Didn't find a setter method with the right type: %1$s", targetFieldType.getName()));
				}
			} else {
				for(Method m : targetType.getMethods()) {
					if(m.getName().equals(setterMethodName)) {
						Class<?>[] params = m.getParameterTypes();
						if(params.length == 1) { // Guess we found the right setter method
							this.setterMethod = m;
							setFieldType(params[0]);
							return;
						}
					}
				}
			}
			
			throw new CursorException(String.format("Unable to find a field or a setter method for field %1$s in class %2$s", fieldName, targetType.getName()));
		}
	}

	private void setFieldType(Class<?> type) {
		this.fieldType = type;
		this.canConvert = SUPPORTED_CONVERT_TYPES.contains(this.fieldType);
	}


	/**
	 * Converts a value to this setter's target field type if needed.
	 * @param value The value to set.
	 * @return The (eventually) converted value. If no conversion is needed or can be done,
	 * just returns {@code value}.
	 */
	final Object getFinalValue(Object value) {
		if(fieldType == null || value == null || !canConvert)
			return value;
		
		String strValue = value instanceof String ? (String)value : value.toString();

		// Boolean values
		if(fieldType == Boolean.class || fieldType == boolean.class) {
			if(value.equals(1) || value.equals("1"))
				return true;
			return strValue.equalsIgnoreCase("true");
		}
		
		// Numbers
		if(fieldType == Byte.class)
			return Byte.valueOf(strValue);
		if(fieldType == byte.class)
			return Byte.parseByte(strValue);
		if(fieldType == Short.class)
			return new Short(strValue);
		if(fieldType == short.class)
			return Short.parseShort(strValue);
		if(fieldType == Integer.class)
			return Integer.valueOf(strValue);
		if(fieldType == int.class)
			return Integer.parseInt(strValue);
		if(fieldType == Long.class)
			return Long.valueOf(strValue);
		if(fieldType == long.class)
			return Long.parseLong(strValue);
		if(fieldType == Float.class)
			return Float.valueOf(strValue);
		if(fieldType == float.class)
			return Float.parseFloat(strValue);
		if(fieldType == Double.class)
			return Double.valueOf(strValue);
		if(fieldType == double.class)
			return Double.parseDouble(strValue);
		if(fieldType == BigInteger.class)
			return new BigInteger(strValue);
		if(fieldType == BigDecimal.class)
			return new BigDecimal(strValue);
		// For numbers, fall back to Double
		if(fieldType == Number.class)
			return Double.valueOf(strValue);
		
		if(fieldType == Calendar.class || fieldType == Date.class) {
			long millis = 0; // Epoch
			if(value instanceof Number) {
				millis = ((Number)value).longValue();
			} else {
				try {
					millis = Long.parseLong(strValue);
				} catch(NumberFormatException e) {
					// TODO What to do here?
				}
			}
			if(millis < PIVOT_DATE_MILLISEC)
				millis *= 1000;
			if(fieldType == Calendar.class) {
				Calendar cal = Calendar.getInstance();
				cal.setTimeInMillis(millis);
				return cal;
			}
			return new Date(millis);
		}
		return value;
	}

	/**
	 * Sets a value to the field represented by this setter.
	 * @param target The target object.
	 * @param value The value to set.
	 * @return True if the field was successfully set, false otherwise.
	 */
	@SuppressWarnings("unchecked")
	public boolean set(T target, Object value) {
		if(value == null)
			return false;
		
		// In case of a map
		if(fieldName != null) {
			((Map<String, Object>)target).put(fieldName, getFinalValue(value));
			return true;
		}
		
		if(field != null) {
			// TODO Transform value is needed
			try {
				field.set(target, getFinalValue(value));
				return true;
			} catch(IllegalAccessException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} else if(setterMethod != null) {
			
			// TODO Transform value is needed
			try {
				setterMethod.invoke(target, getFinalValue(value));
				return true;
			} catch(IllegalAccessException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch(InvocationTargetException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return false;
	}
	
	String getFieldName() {
		if(fieldName != null)
			return fieldName;
		
		if(field != null)
			return field.getName();
		
		String field = setterMethod.getName().substring(3);
		field = field.substring(0, 1).toLowerCase() + field.substring(1);
		return field;
	}
}