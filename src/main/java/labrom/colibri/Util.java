package labrom.colibri;

import java.util.Collection;

/**
 * @author Romain Laboisse labrom@gmail.com
 */
public class Util {

    public static final String TAG = "labrom.colibri";

    public static final String trimOrNull(String s) {
        if (s == null) return null;
        if (s.isEmpty()) return null;
        String trimmed = s.trim();
        if (trimmed.isEmpty()) return null;
        return trimmed;
    }

    public static final boolean isEmptyTrim(String s) {
        if (s == null) return true;
        if (s.isEmpty()) return true;
        if (s.trim().isEmpty()) return true;
        return false;
    }

    public static boolean isEqualOrBothNull(Object o1, Object o2) {
        if (o1 == null && o2 == null) return true;
        if (o1 == null || o2 == null) return false;
        return o1.equals(o2);
    }

    public static final boolean isEmpty(Collection<?> coll) {
        return coll == null || coll.isEmpty();
    }

    public static int sizeOf(Collection<?> coll) {
        return coll == null ? 0 : coll.size();
    }

}
