package org.calmarj.sportboxrssreader.db;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;

public class RSSContentProvider extends ContentProvider {
    public static final int ITEMS = 1;
    public static final int ITEMS_ID = 2;
    private static final String AUTHORITY = "org.calmarj.sportboxrssreader.db";
    private static final String ITEMS_TABLE = "items";
    public static final Uri CONTENT_URI = Uri.parse("content://" + AUTHORITY + "/" + ITEMS_TABLE);
    private static final UriMatcher mURImatcher = new UriMatcher(UriMatcher.NO_MATCH);

    static {
        mURImatcher.addURI(AUTHORITY, ITEMS_TABLE, ITEMS);
        mURImatcher.addURI(AUTHORITY, ITEMS_TABLE + "/#",
                ITEMS_ID);
    }

    private DatabaseHelper mDatabaseHelper;

    public RSSContentProvider() {
    }

    @Override
    public boolean onCreate() {
        boolean result = true;
        mDatabaseHelper = new DatabaseHelper(getContext());
        SQLiteDatabase db = mDatabaseHelper.getWritableDatabase();

        if (db == null) {
            result = false;
        }

        if (db != null) {
            if (db.isReadOnly()) {
                db.close();
                db = null;
                result = false;
            }
        }

        return result;
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        // Implement this to handle requests to delete one or more rows.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public String getType(Uri uri) {
        // TODO: Implement this to handle requests for the MIME type of the data
        // at the given URI.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public Uri insert(Uri uri, ContentValues values) {
        int uriType = mURImatcher.match(uri);

        SQLiteDatabase sqlDB = mDatabaseHelper.getWritableDatabase();

        long id = 0;
        switch (uriType) {
            case ITEMS:
                id = sqlDB.insert(DatabaseHelper.TABLE_ITEM,
                        null, values);
                break;
            default:
                throw new IllegalArgumentException("Unknown URI: " + uri);
        }
        getContext().getContentResolver().notifyChange(uri, null);
        return Uri.parse(ITEMS_TABLE + "/" + id);
    }

    @Override
    public Cursor query(Uri uri, String[] projection, String selection,
                        String[] selectionArgs, String sortOrder) {
        SQLiteQueryBuilder queryBuilder = new SQLiteQueryBuilder();
        queryBuilder.setTables(DatabaseHelper.TABLE_ITEM);

        int uriType = mURImatcher.match(uri);

        switch (uriType) {
            case ITEMS_ID:
                queryBuilder.appendWhere(DatabaseHelper.KEY_ITEM_ID + "="
                        + uri.getLastPathSegment());
                break;
            case ITEMS:
                break;
            default:
                throw new IllegalArgumentException("Unknown URI");
        }

        Cursor cursor = queryBuilder.query(mDatabaseHelper.getReadableDatabase(),
                projection, selection, selectionArgs, null, null, sortOrder);
        cursor.setNotificationUri(getContext().getContentResolver(), uri);
        return cursor;
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection,
                      String[] selectionArgs) {
        // TODO: Implement this to handle requests to update one or more rows.
        throw new UnsupportedOperationException("Not yet implemented");
    }
}
