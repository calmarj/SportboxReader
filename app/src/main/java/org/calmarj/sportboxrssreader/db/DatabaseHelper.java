package org.calmarj.sportboxrssreader.db;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import org.calmarj.sportboxrssreader.model.Item;

import java.util.ArrayList;
import java.util.List;


public class DatabaseHelper extends SQLiteOpenHelper {

    static final String TABLE_ITEM = "items";
    static final String KEY_ITEM_ID = "id";
    static final String KEY_ITEM_TITLE = "title";
    static final String KEY_ITEM_DESCRIPTION = "description";
    static final String KEY_ITEM_LINK = "link";
    static final String KEY_ITEM_AUTHOR = "author";
    static final String KEY_ITEM_PUB_DATE = "pub_date";
    private static final String TAG = "DatabaseHelper";
    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "rssManager";
    private static final String CREATE_ITEM_TABLE = "CREATE TABLE " + TABLE_ITEM + "("
            + KEY_ITEM_ID + " INTEGER PRIMARY KEY,"
            + KEY_ITEM_TITLE + " TEXT,"
            + KEY_ITEM_DESCRIPTION + " TEXT,"
            + KEY_ITEM_LINK + " TEXT,"
            + KEY_ITEM_AUTHOR + " TEXT,"
            + KEY_ITEM_PUB_DATE + " TEXT)";

    private ContentResolver contentResolver;

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_ITEM_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS" + TABLE_ITEM);

        onCreate(db);
    }

    public void createItem(Item item) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues contentValues = new ContentValues();
        contentValues.put(KEY_ITEM_TITLE, item.getTitle());
        contentValues.put(KEY_ITEM_DESCRIPTION, item.getDescription());
        contentValues.put(KEY_ITEM_LINK, item.getLink());
        contentValues.put(KEY_ITEM_AUTHOR, item.getAuthor());
        contentValues.put(KEY_ITEM_PUB_DATE, item.getPubDate());

        contentResolver.insert(RSSContentProvider.CONTENT_URI, contentValues);
    }

    public List<Item> getAllItems() {
        List<Item> items = new ArrayList<>();
        String selectQuery = "SELECT * FROM " + TABLE_ITEM;

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            do {
                Item item = new Item();
                item.setTitle(cursor.getString(cursor.getColumnIndex(KEY_ITEM_TITLE)));
                item.setDescription(cursor.getString(cursor.getColumnIndex(KEY_ITEM_DESCRIPTION)));
                item.setLink(cursor.getString(cursor.getColumnIndex(KEY_ITEM_LINK)));
                item.setAuthor(cursor.getString(cursor.getColumnIndex(KEY_ITEM_AUTHOR)));
                item.setPubDate(cursor.getString(cursor.getColumnIndex(KEY_ITEM_PUB_DATE)));
                items.add(item);
            } while (cursor.moveToNext());
        }

        return items;
    }

    public Item getLastItem() {
        SQLiteDatabase db = this.getReadableDatabase();

        String selectQuery;
        return null;
    }


    public void closeDB() {
        SQLiteDatabase db = this.getReadableDatabase();
        if (db != null && db.isOpen())
            db.close();
    }
}
