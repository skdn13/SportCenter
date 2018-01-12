package pt.ipp.estg.sportcenter;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by pmms8 on 1/7/2018.
 */

public class DataBase extends SQLiteOpenHelper {
    // All Static variables
    // Database Name
    private static final String DB_NAME = "images";
    // Database Version
    private static final int DB_VERSION = 1;
    // Table name
    public static final String TABLE_NAME = "image";
    // Drop table query
    public static final String DROP_QUERY = "DROP TABLE IF EXIST " + TABLE_NAME;
    // Select all query
    public static final String GET_IMAGE_QUERY = "SELECT * FROM " + TABLE_NAME;
    // image table column names
    public static final String PHOTO_URL = "photo_url";
    public static final String PHOTO = "photo";
    public static final String TITLE = "title";
    // Create table
    //
    public static final String CREATE_TABLE_QUERY = "CREATE TABLE " + TABLE_NAME + "" +
            "(" +
            PHOTO_URL + " TEXT PRIMARY KEY not null," +
            PHOTO + " blob not null," +
            TITLE + " TEXT not null)";

    public DataBase(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    // Creating tables
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE_QUERY);
    }

    // Upgrading tables
    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        // Drop older table if existed
        db.execSQL(DROP_QUERY);
        // Create tables again
        this.onCreate(db);
    }

    public void addData(DataModel dataModel) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(PHOTO_URL, dataModel.getUrl().getMedium());
        values.put(PHOTO, Utility.getPictureByteOfArray(dataModel.getPicture()));
        values.put(TITLE, dataModel.getName());
        // Inserting row
        db.insert(TABLE_NAME, null, values);
        // Closing DataBase connection
        db.close();
    }

    // fetchData accepts parameter as DataFetchListner (shows in next step)
    public void fetchData(DataFetchListner listener) {
//DataFecher method accepts listener and writable database(step 6)
        DataFetcher fetcher = new DataFetcher(listener, this.getWritableDatabase());
        fetcher.start();
    }
}
