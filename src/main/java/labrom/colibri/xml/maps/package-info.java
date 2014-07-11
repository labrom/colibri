/**
 * <p>
 * This package contains specific sub-classes that make it easier to define and create cursors
 * that work with maps instead of user-defined object classes.
 * </p>
 * The workflow is almost identical to the regular workflow in package org.pulloid:<ol>
 * <li>Create a {@link labrom.colibri.xml.maps.RootContext4Maps}.</li>
 * <li>Use {@link labrom.colibri.xml.maps.ElementContext4Maps#selectElement(String)} to navigate down to the XML element that represents an entry in the cursor.</li>
 * <li>Create a {@linkplain labrom.colibri.xml.maps.CursorDef4Maps cursor definition} by calling {@link labrom.colibri.xml.maps.ElementContext4Maps#defineCursor()} (that's actually the most notable difference apart from the different class names).</li>
 * <li>Map child XML elements and/or attributes to the Java class fields using the various methods in {@link labrom.colibri.xml.maps.ElementContext4Maps} (when holding a reference to the {@link labrom.colibri.xml.maps.CursorDef4Maps} instance, it is possible to get a reference to the {@link labrom.colibri.xml.ElementContext} instance by calling {@link labrom.colibri.xml.CursorDef#getContext()}).</li>
 * <li>Call {@link labrom.colibri.xml.maps.CursorDef4Maps#pull(java.io.InputStream)} (or the one with a {@link java.io.Reader}) to create a parser on an XML stream (note: a cursor definition can be reused multiple times).</li>
 * </ol>
 */
package labrom.colibri.xml.maps;

