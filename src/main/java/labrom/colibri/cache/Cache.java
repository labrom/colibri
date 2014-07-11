package labrom.colibri.cache;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.util.UUID;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import static labrom.colibri.Util.*;

/**
 * @author Romain Laboisse labrom@gmail.com
 */
public class Cache {

    private static class FileAsyncWriter implements Runnable {
        private final File file;
        private final boolean overwrite;
        private final String content;

        private FileAsyncWriter(File file, boolean overwrite, String content) {
            this.file = file;
            this.overwrite = overwrite;
            this.content = content;
        }

        @Override
        public void run() {
            if(overwrite) {
                file.delete();
            }
            try {
                ByteBuffer bytes = ByteBuffer.wrap(content.getBytes("utf-8"));
                FileChannel fileChannel = new RandomAccessFile(file, "rw").getChannel();
                MappedByteBuffer map = fileChannel.map(FileChannel.MapMode.READ_WRITE, 0, bytes.limit());
                fileChannel.close();
                map.put(bytes);
            } catch (IOException e) {
                Log.e(TAG, "Unable to write file " + file.getAbsolutePath());
            }

        }
    }

    private static class FileAsyncReader implements Runnable {
        private final String uri;
        private final File file;
        private final FileListener listener;
        private final Handler handler;

        private FileAsyncReader(String uri, File file, FileListener listener, Handler handler) {
            this.uri = uri;
            this.file = file;
            this.listener = listener;
            this.handler = handler;
        }

        @Override
        public void run() {
            try {
                FileChannel fileChannel = new RandomAccessFile(file, "r").getChannel();
                MappedByteBuffer map = fileChannel.map(FileChannel.MapMode.READ_ONLY, 0, fileChannel.size());
                final String content = new String(map.array(), "utf-8");
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        listener.onFileLoaded(uri, content);
                    }
                });
            } catch (IOException e) {
                Log.e(TAG, "Unable to read file " + file.getAbsolutePath());
            }
        }
    }

    public interface FileListener {
        void onFileLoaded(String uri, String content);
    }

    private static Cache instance;

    private final Entries entries;
    private final File cacheDir;
//    private final File externalCacheDir;
    private final Executor diskExecutor = Executors.newSingleThreadExecutor();
    private final Handler handler;

    public static Cache get(Context ctx) {
        if(instance == null) {
            if(ctx == null) throw new NullPointerException();
            checkThread();
            instance = new Cache(ctx.getApplicationContext());
        }
        return instance;
    }

    private static void checkThread() {
        if(Thread.currentThread() != Looper.getMainLooper().getThread()) throw new IllegalStateException("Cache must be accessed from the main thread");
    }

    private Cache(Context ctx) {
        handler = new Handler();
        entries = new Entries(ctx);
        cacheDir = ctx.getCacheDir();
//        if(Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState())) {
//            externalCacheDir = ctx.getExternalCacheDir();
//        } else {
//            externalCacheDir = null;
//        }
    }

    public void putFile(String uri, String content) {
        if(uri == null || content == null) throw new NullPointerException();
        checkThread();
        CacheEntry entry = entries.get(uri);
        boolean overwrite = false;
        if(entry != null) {
            overwrite = true;
        } else {
            entry = new CacheEntry();
            entry.path = UUID.nameUUIDFromBytes(uri.getBytes()).toString();
            entries.put(uri, entry);
        }
        diskExecutor.execute(new FileAsyncWriter(new File(cacheDir, entry.path), overwrite, content));
    }

    public boolean hasFile(String uri) {
        return entries.containsKey(uri);
    }

    public void getFile(String uri, FileListener listener) {
        if(uri == null || listener == null) throw new NullPointerException();
        checkThread();
        CacheEntry entry = entries.get(uri);
        if(entry == null) return;
        diskExecutor.execute(new FileAsyncReader(uri, new File(cacheDir, entry.path), listener, handler));
    }

}
