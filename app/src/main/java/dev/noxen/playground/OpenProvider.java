package dev.noxen.playground;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.net.Uri;
import android.os.Bundle;
import android.os.ParcelFileDescriptor;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Exported provider without permissions. It is deliberately naive: caller-supplied
 * projection, selection and path reach SQLite and the file system unchanged, so
 * inbound provider calls can be followed to their effects.
 */
public class OpenProvider extends ContentProvider {

    private SQLiteOpenHelper helper;

    @Override
    public boolean onCreate() {
        Context context = getContext();
        helper = new SQLiteOpenHelper(context, "open_provider.db", null, 1) {
            @Override
            public void onCreate(SQLiteDatabase db) {
                db.execSQL("CREATE TABLE notes (_id INTEGER PRIMARY KEY, title TEXT, body TEXT)");
                db.execSQL("INSERT INTO notes (title, body) VALUES ('first', 'public note')");
                db.execSQL("CREATE TABLE secrets (_id INTEGER PRIMARY KEY, token TEXT)");
                db.execSQL("INSERT INTO secrets (token) VALUES ('playground-secret-token')");
            }

            @Override
            public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            }
        };
        File shared = new File(context.getFilesDir(), "shared");
        File report = new File(shared, "report.txt");
        if (shared.mkdirs() || !report.exists()) {
            try (FileOutputStream out = new FileOutputStream(report)) {
                out.write("playground report\n".getBytes());
            } catch (IOException ignored) {
            }
        }
        return true;
    }

    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
        return helper.getReadableDatabase().query("notes", projection, selection, selectionArgs, null, null, sortOrder);
    }

    @Override
    public Bundle call(String method, String arg, Bundle extras) {
        Bundle result = new Bundle();
        if ("countNotes".equals(method)) {
            try (Cursor cursor = helper.getReadableDatabase().rawQuery("SELECT COUNT(*) FROM notes", null)) {
                cursor.moveToFirst();
                result.putInt("count", cursor.getInt(0));
            }
        }
        return result;
    }

    @Override
    public ParcelFileDescriptor openFile(Uri uri, String mode) throws FileNotFoundException {
        File file = new File(new File(getContext().getFilesDir(), "shared"), uri.getLastPathSegment());
        return ParcelFileDescriptor.open(file, ParcelFileDescriptor.MODE_READ_ONLY);
    }

    @Override
    public String getType(Uri uri) {
        return null;
    }

    @Override
    public Uri insert(Uri uri, ContentValues values) {
        return null;
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        return 0;
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {
        return 0;
    }
}
