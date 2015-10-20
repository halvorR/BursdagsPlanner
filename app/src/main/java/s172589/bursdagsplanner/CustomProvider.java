package s172589.bursdagsplanner;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;

import java.util.List;

/**
 * Created by Roger on 20.10.2015.
 */
public class CustomProvider extends ContentProvider{
    public static final String PROVIDER = "s172589.bursdagsplanner";
    public static final int KONTAKTER = 1;
    public static final int MKONTAKTER = 2;
    public static DBHandler db;

    public static final Uri CONTENT_URI = Uri.parse("content://"+ PROVIDER + "/" + db.TABLE_KONTAKTER);
    private static final UriMatcher uriMatcher;
    static{
        uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
        uriMatcher.addURI(PROVIDER, db.TABLE_KONTAKTER,MKONTAKTER);
        uriMatcher.addURI(PROVIDER, db.TABLE_KONTAKTER+"/#",KONTAKTER);
    }


    @Override
    public boolean onCreate() {
        return false;
    }

    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
        return null;
    }

    @Override
    public String getType(Uri uri) {
        switch (uriMatcher.match(uri)) {
            case MKONTAKTER:
                return
                        "vnd.android.cursor.dir/vnd." + PROVIDER;

            case KONTAKTER:
                return
                        "vnd.android.cursor.item/vnd." + PROVIDER;

            default:
                throw new
                        IllegalArgumentException("Ugyldig URI" + uri);
        }
    }

    @Override
    public Uri insert(Uri uri, ContentValues values) {
        DBHandler dbH = new DBHandler(getContext());
        SQLiteDatabase sdb = dbH.getWritableDatabase();
        sdb.insert(db.TABLE_KONTAKTER, null, values);

        Cursor c = sdb.query(db.TABLE_KONTAKTER, null, null, null, null, null, null);
        c.moveToLast();
        long minid=c.getLong(0);
        getContext().getContentResolver().notifyChange(uri,null);

        return ContentUris.withAppendedId(uri,minid);
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
