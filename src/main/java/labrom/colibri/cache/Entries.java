package labrom.colibri.cache;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.HashMap;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import labrom.colibri.data.ActiveRecordList;
import labrom.colibri.data.Database;

/**
* @author Romain Laboisse labrom@gmail.com
*/
class Entries extends HashMap<String, CacheEntry> {

    private static final String COL_KEY = "key";
    private static final String COL_PATH = "file";
    private static final String WHERE_KEY = "key=?";
    private final Executor dbExecutor = Executors.newSingleThreadExecutor();

    private final Database db;

    Entries(Context ctx) {
        db = new Database(ctx) {
            @Override
            protected SQLiteOpenHelper createHelper(Context ctx) {
                return new SQLiteOpenHelper(ctx, CacheEntry.PROTO.getTableName(), null, 1) {
                    @Override
                    public void onCreate(SQLiteDatabase sqLiteDatabase) {
                        sqLiteDatabase.execSQL(String.format("CREATE TABLE %s (%s TEXT PRIMARY KEY NOT NULL, %s TEXT NOT NULL);", CacheEntry.PROTO.getTableName(), COL_KEY, COL_PATH));
                    }

                    @Override
                    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i2) {

                    }
                };
            }
        };

        ActiveRecordList<CacheEntry> entries = db.query(CacheEntry.PROTO, null, null, null, null);
        for(CacheEntry entry : entries.asList()) {
            super.put(entry.key, entry);
        }
    }

    @Override
    public CacheEntry put(final String key, final CacheEntry value) {
        if(key == null || value == null) throw new NullPointerException();
        if(containsKey(key)) {
            dbExecutor.execute(new Runnable() {
                public void run() {
                    db.update(value);
                }
            });
        } else {
            dbExecutor.execute(new Runnable() {
                public void run() {
                    db.persist(value);
                }
            });

        }
        return super.put(key, value);
    }

    @Override
    public CacheEntry remove(final Object key) {
        if(key == null) throw new NullPointerException();
        final CacheEntry entry = new CacheEntry();
        entry.key = key.toString();
        dbExecutor.execute(new Runnable() {
            @Override
            public void run() {
                db.delete(entry);
            }
        });
        return super.remove(key);
    }
}
