package s172589.bursdagsplanner;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.net.Uri;

import java.util.List;

/**
 * Created by Roger on 20.10.2015.
 */
public class CustomProvider extends ContentProvider{
    public static final String PROVIDER = "s172589.contentproviderbok";
    public static final int KONTAKTER = 1;
    public static final int KONTAKTER_ID = 2;
    public static DBHandler db;

    public static final Uri CONTENT_URI = Uri.parse("content://"+ PROVIDER + "/ " + db.TABLE_KONTAKTER);
    private static final UriMatcher uriMatcher;
    static{
        uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
        uriMatcher.addURI(PROVIDER, db.TABLE_KONTAKTER,KONTAKTER_ID);
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
