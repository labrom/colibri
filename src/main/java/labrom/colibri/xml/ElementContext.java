package labrom.colibri.xml;

import java.io.IOException;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

/**
 * Represents the context of a specific XML element. This class is one of the
 * few central classes of Pulloid, it allows to define a {@linkplain CursorDef
 * cursor definition} (that means that the parser will create a new instance of
 * class T every time it encounters such an XML element), it also allows to
 * select children elements and assign them to fields in class T, and the same
 * can be done for XML attributes.
 * 
 * @author Romain Laboisse labrom@gmail.com
 * 
 * @param <T>
 *            The type of objects that Pulloid will create. This class must be
 *            written by developers.
 */
public class ElementContext<T> extends NodeContext<T> {

    private int depth;
    private CursorDef<T> cursorDef;

    private ElementContext<T> lastChild;
    private ElementContext<T> firstChild;
    private AttributeContext<T> attrChain;

    protected ElementContext(ElementContext<T> parent, String name) {
        super(parent, name);
    }

    protected void setCursorDef(CursorDef<T> def) {
        this.cursorDef = def;
    }

    protected CursorDef<T> getCursorDef() {
        return this.cursorDef;
    }

    protected void setDepth(int depth) {
        this.depth = depth;
    }

    protected int getDepth() {
        return this.depth;
    }

    /**
     * Selects a child element of this XML element with the specified name. This
     * name is not namespace-aware, meaning that it should be specified exactly
     * as it appears in the XML and include a namespace prefix if there is one
     * (for example, a:title).
     * 
     * @param name
     * @return The selected XML child element.
     */
    public ElementContext<T> selectElement(String name) {
        ElementContext<T> ctx = createChildElementContext(name);
        selectElement(ctx);
        return ctx;
    }

    protected void selectElement(ElementContext<T> selected) {
        selected.depth = this.depth + 1;
        if (firstChild == null)
            firstChild = selected;
        else
            lastChild.nextSibling = selected;
        lastChild = selected;
    }

    protected ElementContext<T> createChildElementContext(String name) {
        return new ElementContext<T>(this, name);
    }

    /**
     * Maps the specified XML attribute value to a field in the target Java
     * class.
     * 
     * @param fieldName
     *            Name of the field to which this XML attribute value should be
     *            mapped.
     * @param attributeName
     *            Name of the XML attribute. This name is not namespace-aware,
     *            meaning that it should be specified exactly as it appears in
     *            the XML and include a namespace prefix if there is one (for
     *            example, a:title).
     * @return This object for method chaining.
     */
    public ElementContext<T> createFieldOnAttribute(String fieldName, String attributeName) {
        AttributeContext<T> ctx = new AttributeContext<T>(this, attributeName);
        ctx.registerField(fieldName);
        if (attrChain != null) // We don't care about the order for attributes,
                               // because we can get all of them while the
                               // parser is on the start tag
            ctx.nextSibling = attrChain;
        attrChain = ctx;
        return this;
    }

    /**
     * Maps the specified XML attribute value to a field in the target Java
     * class. This method is intended to be used only in situations where
     * {@link #createFieldOnAttribute(String, String)} cannot be used, that is,
     * when all conditions below are met:
     * <ul>
     * <li>The specified field is not public and a setter method needs to be
     * used.</li>
     * <li>There are multiple setter methods with the same right name and one
     * parameter.</li>
     * </ul>
     * It can also be used when all conditions below are met:
     * <ul>
     * <li>The target Java class is a Map.</li>
     * <li>You want to create a Map entry whose value is not a String.</li>
     * </ul>
     * In this case you need to specify the target type so Pulloid knows how to convert
     * the String value.
     * 
     * In all other situations, just use
     * {@link #createFieldOnAttribute(String, String)}.
     * 
     * @param fieldName
     *            Name of the field to which this XML attribute value should be
     *            mapped.
     * @param attributeName
     *            Name of the XML attribute. This name is not namespace-aware,
     *            meaning that name should be specified exactly as it appears in
     *            the XML and include a namespace prefix if there is one (for
     *            example, a:title).
     * @param type
     * @return This object for method chaining.
     */
    public ElementContext<T> createFieldOnAttribute(String fieldName, String attributeName, Class<?> type) {
        AttributeContext<T> ctx = new AttributeContext<T>(this, attributeName);
        ctx.registerField(fieldName, type);
        if (attrChain != null) // We don't care about the order for attributes,
                               // because we can get all of them while the
                               // parser is on the start tag
            ctx.nextSibling = attrChain;
        attrChain = ctx;
        return this;
    }

    /**
     * Maps the specified XML attribute value to a field in the target Java
     * class. The specified string transformer will be used to transform the XML
     * attribute values to instances of the TT class. Use this method when you
     * need to populate fields (or map entries) with instances of a user-defined
     * class (TT) that the string transformer can create.
     * 
     * @param <TT>
     *            Type of objects the string transformer will create.
     * @param fieldName
     *            Name of the field to which this XML attribute value should be
     *            mapped.
     * @param attributeName
     *            Name of the XML attribute. This name is not namespace-aware,
     *            meaning that name should be specified exactly as it appears in
     *            the XML and include a namespace prefix if there is one (for
     *            example, a:title).
     * @param type
     *            Type of the field in the target Java class.
     * @param tr
     * @return This object for method chaining.
     */
    public <TT> ElementContext<T> createFieldOnAttribute(String fieldName, String attributeName, Class<TT> type, StringTransformer<TT> tr) {
        AttributeContext<T> ctx = new AttributeContext<T>(this, attributeName);
        ctx.registerField(fieldName, type, tr);
        if (attrChain != null) // We don't care about the order for attributes,
                               // because we can get all of them while the
                               // parser is on the start tag
            ctx.nextSibling = attrChain;
        attrChain = ctx;
        return this;
    }

    /**
     * Maps this XML element value to a field in the target Java class.
     * 
     * @param name
     *            Name of the field to which this XML element value should be
     *            mapped.
     * @return This object for method chaining.
     */
    public ElementContext<T> createField(String name) {
        registerField(name);
        return this;
    }

    /**
     * Maps this XML element value to a field in the target Java class. This
     * method is intended to be used only in situations where
     * {@link #createField(String)} cannot be used, that is, when all conditions
     * below are met:
     * <ul>
     * <li>The specified field is not public and a setter method needs to be
     * used.</li>
     * <li>There are multiple setter methods with the same right name and one
     * parameter.</li>
     * </ul>
     * It can also be used when all conditions below are met:
     * <ul>
     * <li>The target Java class is a Map.</li>
     * <li>You want to create a Map entry whose value is not a String.</li>
     * </ul>
     * In this case you need to specify the target type so Pulloid knows how to convert
     * the String value.
     * 
     * In all other situations, just use {@link #createField(String)}.
     * 
     * @param name
     *            Name of the field to which this XML element value should be
     *            mapped.
     * @param type
     *            Type of the field in the target Java class.
     * @return This object for method chaining.
     */
    public ElementContext<T> createField(String name, Class<?> type) {
        registerField(name, type);
        return this;
    }

    /**
     * Maps this XML element value to a field in the target Java class. The
     * specified string transformer will be used to transform the XML element
     * values to instances of the TT class. Use this method when you need to
     * populate fields (or map entries) with instances of a user-defined class
     * (TT) that the string transformer can create.
     * 
     * @param <TT>
     *            Type of objects the string transformer will create.
     * @param name
     *            Name of the field to which this XML element value should be
     *            mapped.
     * @param type
     *            Type of the field in the target Java class.
     * @param tr
     * @return This object for method chaining.
     */
    public <TT> ElementContext<T> createField(String name, Class<TT> type, StringTransformer<TT> tr) {
        registerField(name, type, tr);
        return this;
    }

    /**
     * Defines a cursor on this XML element.
     * 
     * @param cursorType
     *            Type of the Java objects that will be created and populated by
     *            the cursor.
     * @return A cursor definition that can be used to create cursors.
     */
    public CursorDef<T> defineCursor(Class<T> cursorType) {
        CursorDef<T> def = new CursorDef<T>(cursorType, this);
        setCursorDef(def);
        return def;
    }

    /**
     * Defines a cursor on this XML element. Instead of using a user-defined
     * Java type, cursors created from the cursor context will populate Java
     * maps.
     * 
     * @return A cursor definition that can be used to create cursors.
     */
    public CursorDef<T> defineCursor() {
        CursorDef<T> def = new CursorDef<T>(this);
        setCursorDef(def);
        return def;
    }

    @Override
    CursorDef<T> findCursorDef() {
        if (this.cursorDef != null)
            return this.cursorDef;
        return super.findCursorDef();
    }

    /**
     * Move parser cursor to the next occurrence of this element. If there is no
     * such occurrence, will try to position to the next sibling, and so on.
     * 
     * @param p
     * @return The XML element where the the current position is :D
     * @throws XmlPullParserException
     * @throws IOException
     */
    ElementContext<T> position(XmlPullParser p) throws XmlPullParserException, IOException {
        /*
         * This context is the cursor, move forward until we encounter the next
         * cursor tag,  there's no need to look for next sibling match
         */
        if (cursorDef != null) {
            try {
                while (this.depth != p.getDepth() || !name.equals(p.getName()) || !ParserUtil.isStartTag(p)) {
                    if (ParserUtil.isEndDocument(p))
                        return null;
                    p.next();
                }
            } catch (XmlPullParserException e) {
                return null;
            }
            return this;
        }

        /*
         * Starting from here is the non-cursor case: some elements may be
         * missing, other elements may be ignored
         */

        // Go to the right depth
        try {
            while (this.depth != p.getDepth() || ParserUtil.isEndTag(p)) {
                p.nextTag();
                if (p.getName().equals(parent.name))
                    return null;
            }
        } catch (XmlPullParserException e) {
            return null;
        }

        // Try to match self, or siblings
        do {
            ElementContext<T> pos = matchSelfOrSibling(p);
            if (pos != null)
                return pos;
        } while (ParserUtil.moveToNextSibling(p)); // Skip sibling

        return null;
    }

    /**
     * Figures out if one of the next siblings of this element might match the
     * parser current cursor position.
     * 
     * @param p
     * @return This XML element or one of the next sibling elements.
     * @throws XmlPullParserException
     */
    ElementContext<T> matchSelfOrSibling(XmlPullParser p) throws XmlPullParserException {
        if (p.getName().equals(name) && ParserUtil.isStartTag(p)) {
            return this;
        }
        if (nextSibling != null)
            return ((ElementContext<T>) nextSibling).matchSelfOrSibling(p);
        return null;
    }

    /**
     * Moves the parser cursor to the next occurrence of this element and fills
     * the current record. If this element has children and/or siblings, then
     * calls the same method on those elements. Does the same for attributes if
     * there are any.
     * 
     * @see #position(org.xmlpull.v1.XmlPullParser)
     */
    @Override
    void hydrate(XmlPullParser p, Reflector<T> record) throws XmlPullParserException, IOException {
        ElementContext<T> pos = null;
        if (cursorDef == null) { // If we are in the element that created the
                                 // cursor there's no need to position() as
                                 // it's already been done from the cursor
            pos = position(p);
            if (pos == null)
                return;
        } else {
            pos = this;
        }

        if (pos == this) {
            if (attrChain != null)
                attrChain.hydrate(p, record); // Do all attributes, no need to
                                              // position, we're at the right
                                              // position already
            if (firstChild != null) {
                firstChild.hydrate(p, record); // Send to children
            } else if (setter != null) {
                String strval = p.nextText(); // Move to my end tag
                Object val = transformer != null ? transformer.transform(strval) : strval;
                record.set(setter, val);
            } else if(pos == this && !ParserUtil.isEndDocument(p)) {
                p.next(); // Move out of start tag
            }
            if (cursorDef == null && nextSibling != null) {
                nextSibling.hydrate(p, record);
            }
        } else {
            pos.hydrate(p, record);
        }
    }

}
