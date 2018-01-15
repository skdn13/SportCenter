package basesDeDados;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import catalogos.DataFetchListner;
import catalogos.DataFetcher;
import catalogos.DataModel;
import pt.ipp.estg.sportcenter.Utility;


public class BDImagens extends SQLiteOpenHelper {
    private static final String DB_NAME = "images";
    private static final int DB_VERSION = 1;
    public static final String TABLE_NAME = "image";
    public static final String DROP_QUERY = "DROP TABLE IF EXIST " + TABLE_NAME;
    public static final String GET_IMAGE_QUERY = "SELECT * FROM " + TABLE_NAME;
    public static final String PHOTO_URL = "photo_url";
    public static final String PHOTO = "photo";
    public static final String TITLE = "title";
    public static final String CREATE_TABLE_QUERY = "CREATE TABLE " + TABLE_NAME + "" +
            "(" +
            PHOTO_URL + " TEXT PRIMARY KEY not null," +
            PHOTO + " blob not null," +
            TITLE + " TEXT not null)";

    public BDImagens(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE_QUERY);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL(DROP_QUERY);
        this.onCreate(db);
    }

    public void addData(DataModel dataModel) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(PHOTO_URL, dataModel.getUrl().getMedium());
        values.put(PHOTO, Utility.getPictureByteOfArray(dataModel.getPicture()));
        values.put(TITLE, dataModel.getName());
        db.insert(TABLE_NAME, null, values);
        db.close();
    }

    public void fetchData(DataFetchListner listener) {
        DataFetcher fetcher = new DataFetcher(listener, this.getWritableDatabase());
        fetcher.start();
    }
}
