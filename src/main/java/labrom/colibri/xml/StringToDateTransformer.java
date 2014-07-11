package labrom.colibri.xml;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
/**
 * This string transformer uses the {@link SimpleDateFormat} class to
 * parse String values and return {@link Date} objects.
 * 
 * Although Pulloid natively supports conversion to {@link Date} objects,
 * this is only from string values that actually represent a timestamp.
 * 
 * This class is able to parse the string representation of a date, like 3/19/2001 4:47 PM for example.
 * 
 * 
 * @author Romain Laboisse labrom@gmail.com
 * @see SimpleDateFormat
 */
public class StringToDateTransformer implements StringTransformer<Date> {
    
    private SimpleDateFormat format;
    
    /**
     * 
     * @param pattern The date format pattern. Please the documentation of {@link SimpleDateFormat}
     * for more details.
     */
    public StringToDateTransformer(String pattern) {
        this.format = new SimpleDateFormat(pattern);
    }

    @Override
    public Date transform(String value) {
        try {
            return this.format.parse(value);
        } catch (ParseException e) {
            throw new CursorException("Error occurred when parsing formatted date: " + value, e);
        }
    }

}
