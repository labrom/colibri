/**
 * <p>
 * Contains all public classes for Pulloid.
 * </p>
 * <p>
 * Pulloid is an API that allows to developers to easily and quickly define XML to Java object mappings.
 * Once the mapping defined, Pulloid will parse the XML stream (using an XmlPull parser) and create the associated Java objects.
 * </p>
 * <p>
 * Pulloid doesn't handle deep XML structures but focuses on providing a cursor on XML data sets. That means that it is suitable to XML
 * structures that represent a list of objects, those objects having simple properties.
 * </p>
 * <p>
 * To use Pulloid one must start by creating a Java class that represents the desired object output.
 * This class can have public variable members or can define setter methods. Pulloid will do its best to automatically 
 * translate from strings in the XML to other types of variables in the Java objects - for example, numeric types and dates are handled
 * automatically.
 * </p>
 * <p>
 * Then a mapping definition needs to be created. The starting point is the {@link labrom.colibri.xml.RootContext} class. By using this class, along with
 * {@link labrom.colibri.xml.ElementContext}, {@link labrom.colibri.xml.CursorDef} it is possible to define how the parser should traverse the various elements and
 * create the Java objects and populate their fields as is navigates forward through the XML structure.
 * </p>
 * <p>
 * Once the mapping definition is there (a mapping definition is represented by an instance of {@link labrom.colibri.xml.CursorDef}) a {@linkplain labrom.colibri.xml.Cursor cursor} can be created from an
 * XML stream.
 * </p>
 * <p>
 * In a nutshell, the workflow is as following:<ol>
 * <li>Create a {@link labrom.colibri.xml.RootContext}.</li>
 * <li>Use {@link labrom.colibri.xml.ElementContext#selectElement(String)} to navigate down to the XML element that represents an entry in the cursor.</li>
 * <li>Create a {@linkplain labrom.colibri.xml.CursorDef cursor definition} by calling {@link labrom.colibri.xml.ElementContext#defineCursor(Class)}.</li>
 * <li>Map child XML elements and/or attributes to the Java class fields using the various methods in {@link labrom.colibri.xml.ElementContext} (when holding a reference to the {@link labrom.colibri.xml.CursorDef} instance, it is possible to get a reference to the {@link labrom.colibri.xml.ElementContext} instance by calling {@link labrom.colibri.xml.CursorDef#getContext()}).</li>
 * <li>Call {@link labrom.colibri.xml.CursorDef#pull(java.io.InputStream)} (or the one with a {@link java.io.Reader}) to create a parser on an XML stream (note: a cursor definition can be reused multiple times).</li>
 * </ol>
 * </p>
 */
package labrom.colibri.xml;
