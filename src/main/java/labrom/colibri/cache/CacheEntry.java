package labrom.colibri.cache;

import android.content.ContentValues;
import android.database.Cursor;

import labrom.colibri.data.ActiveRecord;
import labrom.colibri.data.SelectionArgs;

/**
 * @author Romain Laboisse labrom@gmail.com
 */
public class CacheEntry extends ActiveRecord {

    static final CacheEntry PROTO = new CacheEntry();

    public String key;
    public String path;

    @Override
    protected void hydrateFromCursor(Cursor c) {
        path = c.getString(1);
    }

    @Override
    protected void populateForUpdate(ContentValues v) {

    }

    @Override
    protected void populateFull(ContentValues v) {

    }

    @Override
    protected void populateForQueryByExample(SelectionArgs selection) {

    }

    @Override
    public String getTableName() {
        return "cache";
    }
}
