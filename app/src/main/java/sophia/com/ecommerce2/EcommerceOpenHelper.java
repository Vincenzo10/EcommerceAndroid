package sophia.com.ecommerce2;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import sophia.com.ecommerce2.data.Category;
import sophia.com.ecommerce2.data.Item;

/**
 * Created by archimede on 26/06/17.
 */

public class EcommerceOpenHelper extends SQLiteOpenHelper {
    private SQLiteDatabase mWritableDB;
    private SQLiteDatabase mReadableDB;

    ContentValues values = new ContentValues();
    ContentValues valuesItem = new ContentValues();

    // It's a good idea to always define a log tag like this.
    private static final String TAG = EcommerceOpenHelper.class.getSimpleName();

    // has to be 1 first time or app will crash
    private static final int DATABASE_VERSION = 8;
    public static final String CATEGORY_TABLE = "category";
    public static final String ITEM_TABLE = "item";
    private static final String DATABASE_NAME = "ecommerce";

    // Column names CATEGORY...
    public static final String KEY_ID = "_id";
    public static final String KEY_TITLE = "title";
    public static final String KEY_SUBTITLE = "subtitle";
    public static final String KEY_IMAGE = "imagePath";

    // Column names ITEM
    public static final String KEY_ITEM_ID = "_id";
    public static final String KEY_ITEM_CATEGORY = "category";
    public static final String KEY_ITEM_NAME = "name_prod";
    public static final String KEY_ITEM_DESCRIPTION = "description";
    public static final String KEY_ITEM_PRICE = "price";
    public static final String KEY_ITEM_PHOTO = "photoPath";

    // ... and a string array of columns.
    private static final String[] COLUMNS = { KEY_ID, KEY_TITLE, KEY_SUBTITLE, KEY_IMAGE };
    private static final String[] COLUMNSITEM = { KEY_ITEM_ID, KEY_ITEM_CATEGORY, KEY_ITEM_NAME, KEY_ITEM_DESCRIPTION, KEY_ITEM_PRICE, KEY_ITEM_PHOTO };

    public EcommerceOpenHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    private static final String CATEGORY_TABLE_CREATE =
            "CREATE TABLE " + CATEGORY_TABLE + " (" +
                    KEY_ID + " INTEGER PRIMARY KEY, " +
                    KEY_TITLE + " VARCHAR, " +
                    KEY_SUBTITLE + " TEXT, " +
                    KEY_IMAGE + " VARCHAR );";

    private static final String ITEM_TABLE_CREATE =
            "CREATE  TABLE " + ITEM_TABLE + " (" +
                    KEY_ITEM_ID + " INTEGER PRIMARY KEY, " +
                    KEY_ITEM_CATEGORY + " INTEGER, " +
                    KEY_ITEM_NAME + " VARCHAR NOT NULL , " +
                    KEY_ITEM_DESCRIPTION + " TEXT, " +
                    KEY_ITEM_PRICE + " DOUBLE NOT NULL , " +
                    KEY_ITEM_PHOTO + " VARCHAR);";

    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL(CATEGORY_TABLE_CREATE);
        db.execSQL(ITEM_TABLE_CREATE);
        fillDatabaseWithData(db);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        Log.w(EcommerceOpenHelper.class.getName(),
                "Upgrading database from version " + oldVersion + " to "
                        + newVersion + ", which will destroy all old data");
        db.execSQL("DROP TABLE IF EXISTS " + CATEGORY_TABLE);
        db.execSQL("DROP TABLE IF EXISTS " + ITEM_TABLE);

        onCreate(db);
    }

    private void fillDatabaseWithData(SQLiteDatabase db) {
        for (int i = 0; i < 20; i++){
            values.put(KEY_TITLE, "Category " + i);
            values.put(KEY_SUBTITLE, "SubTitle Category " + i);
            values.put(KEY_IMAGE, "http://lorempixel.com/400/400/abstract/1");

            db.insert(CATEGORY_TABLE, null, values);
        }

        for (int i = 0; i < 20; i++){
            valuesItem.put(KEY_ITEM_NAME, "Item name" + i);
            valuesItem.put(KEY_ITEM_DESCRIPTION, "Description item " + i);
            valuesItem.put(KEY_ITEM_PRICE, 19.90 + i);
            valuesItem.put(KEY_ITEM_PHOTO, "http://lorempixel.com/400/400/abstract/2");
            valuesItem.put(KEY_ITEM_CATEGORY, i + 1);

            long id = db.insert(ITEM_TABLE, null, valuesItem);
            Log.d("","" + id);
        }



    }

    public Category queryCategory(int position){
        String query = "SELECT  * FROM " + CATEGORY_TABLE +
                " ORDER BY " + KEY_TITLE + " ASC " +
                "LIMIT " + position + ",1";

        Cursor cursor = null;
        Category entry = new Category();

        try {
            if (mReadableDB == null) {
                mReadableDB = getReadableDatabase();
            }

            cursor = mReadableDB.rawQuery(query, null);

            cursor.moveToFirst();

            entry.setmId(cursor.getInt(cursor.getColumnIndex(KEY_ID)));
            entry.setTitle(cursor.getString(cursor.getColumnIndex(KEY_TITLE)));
            entry.setSubTitle(cursor.getString(cursor.getColumnIndex(KEY_SUBTITLE)));
            entry.setImagePath(cursor.getString(cursor.getColumnIndex(KEY_IMAGE)));

        } catch (Exception e) {
            Log.d(TAG, "EXCEPTION! " + e);
        } finally {
            cursor.close();
            return entry;
        }
    }

    public Item queryItem(int id){
        String query = "SELECT  * FROM " + ITEM_TABLE +
                " WHERE _id=?";

        Cursor cursor = null;
        Item entry = new Item();

        try {
            if (mReadableDB == null) {
                mReadableDB = getReadableDatabase();
            }

            cursor = mReadableDB.rawQuery(query, new String[]{String.valueOf(id)});

            cursor.moveToFirst();

            entry.setmId(cursor.getInt(cursor.getColumnIndex(KEY_ITEM_ID)));
            entry.setName(cursor.getString(cursor.getColumnIndex(KEY_ITEM_NAME)));
            entry.setDescription(cursor.getString(cursor.getColumnIndex(KEY_ITEM_DESCRIPTION)));
            entry.setPrice(cursor.getDouble(cursor.getColumnIndex(KEY_ITEM_PRICE)));
            entry.setPhotoItem(cursor.getString(cursor.getColumnIndex(KEY_ITEM_PHOTO)));

            entry.setCategory(cursor.getInt(cursor.getColumnIndex(KEY_ITEM_CATEGORY)));

        } catch (Exception e) {
            Log.d(TAG, "EXCEPTION! " + e);
        } finally {
            cursor.close();
            return entry;
        }
    }

    public List<Category> getAllCategory() {
        List<Category> listCategory = new ArrayList<>();
        String query = "SELECT  * FROM " + CATEGORY_TABLE;

        Cursor cursor = null;


        try {
            if (mReadableDB == null) {
                mReadableDB = getReadableDatabase();
            }

            cursor = mReadableDB.rawQuery(query, null);

            //cursor.moveToFirst();

            while(cursor.moveToNext()){
                Category entry = new Category();
                entry.setmId(cursor.getInt(cursor.getColumnIndex(KEY_ID)));
                entry.setTitle(cursor.getString(cursor.getColumnIndex(KEY_TITLE)));
                entry.setSubTitle(cursor.getString(cursor.getColumnIndex(KEY_SUBTITLE)));
                entry.setImagePath(cursor.getString(cursor.getColumnIndex(KEY_IMAGE)));
                listCategory.add(entry);
            }



        } catch (Exception e) {
            Log.d(TAG, "EXCEPTION! " + e);
        } finally {
            cursor.close();
            return listCategory;
        }

    }

    public List<Item> getAllItem(int categoryId) {
        List<Item> listItem = new ArrayList<>();
        String query = "SELECT  * FROM " + ITEM_TABLE + " WHERE category==" + categoryId;

        Cursor cursor = null;


        try {
            if (mReadableDB == null) {
                mReadableDB = getReadableDatabase();
            }

            cursor = mReadableDB.rawQuery(query, null);

            //cursor.moveToFirst();

            while(cursor.moveToNext()){
                Item entry = new Item();

                entry.setmId(cursor.getInt(cursor.getColumnIndex(KEY_ID)));
                entry.setName(cursor.getString(cursor.getColumnIndex(KEY_ITEM_NAME)));
                entry.setDescription(cursor.getString(cursor.getColumnIndex(KEY_ITEM_DESCRIPTION)));
                entry.setPrice(cursor.getDouble(cursor.getColumnIndex(KEY_ITEM_PRICE)));
                entry.setPhotoItem(cursor.getString(cursor.getColumnIndex(KEY_ITEM_PHOTO)));

                entry.setCategory(cursor.getInt(cursor.getColumnIndex(KEY_ITEM_CATEGORY)));
                listItem.add(entry);
            }

        } catch (Exception e) {
            Log.d(TAG, "EXCEPTION! " + e);
        } finally {
            cursor.close();
            return listItem;
        }

    }


}
