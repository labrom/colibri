package labrom.colibri.xml;

import java.io.IOException;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

class ParserUtil {
	
	
	static boolean moveToNextSibling(XmlPullParser parser) throws XmlPullParserException, IOException {
		String currentTag = parser.getName();
		int currentLevel = parser.getDepth();
		do {
			if(parser.getEventType() != XmlPullParser.END_DOCUMENT)
				parser.next();
			else
				return false;
		} while(!(currentLevel == parser.getDepth() && parser.getEventType() == XmlPullParser.END_TAG && currentTag.equals(parser.getName())));
		parser.nextTag(); // Next sibling's start tag or parent's end tag
		return true;
	}
	

	static boolean isStartTag(XmlPullParser parser) throws XmlPullParserException {
		return parser.getEventType() == XmlPullParser.START_TAG;
	}
	
	static boolean isEndTag(XmlPullParser parser) throws XmlPullParserException {
		return parser.getEventType() == XmlPullParser.END_TAG;
	}

	static boolean isStartDocument(XmlPullParser parser) throws XmlPullParserException {
		return parser.getEventType() == XmlPullParser.START_DOCUMENT;
	}
	
	static boolean isEndDocument(XmlPullParser parser) throws XmlPullParserException {
		return parser.getEventType() == XmlPullParser.END_DOCUMENT;
	}

}
