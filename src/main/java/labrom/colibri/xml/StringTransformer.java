package labrom.colibri.xml;

/**
 * This interface is used whenever a String value needs to be transformed into
 * another type.
 * 
 * You can provide {@link ElementContext} with an implementation of this
 * interface when string values extracted from XML need to be converted to a
 * type that Pulloid cannot convert to automatically.
 * 
 * StringTransformer is used in these two methods:
 * {@link ElementContext#createField(String, Class, StringTransformer)}, {@link ElementContext#createFieldOnAttribute(String, String, Class, StringTransformer)}.
 * In both cases a StringTransformer will be used to get the final value that
 * will populate the target field.
 * 
 * @author Romain Laboisse labrom@gmail.com
 * 
 * @param <T>
 *            The type of the transformed value.
 * 
 * @see ElementContext
 */
public interface StringTransformer<T> {

    T transform(String value);

}
