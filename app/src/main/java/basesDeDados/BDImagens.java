package basesDeDados;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Handler;
import android.os.Looper;

import java.util.ArrayList;
import java.util.List;

import catalogos.ImageListener;
import catalogos.Image;
import pt.ipp.estg.sportcenter.Utility;


public class BDImagens extends SQLiteOpenHelper {
    private static final String DB_NAME = "images.db";
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

    public void addData(Image image) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(PHOTO_URL, image.getUrl().getMedium());
        values.put(PHOTO, Utility.getPictureByteOfArray(image.getPicture()));
        values.put(TITLE, image.getName());
        db.insert(TABLE_NAME, null, values);
        db.close();
    }

    public void fetchData(ImageListener listener) {
        DataFetcher fetcher = new DataFetcher(listener, this.getWritableDatabase());
        fetcher.start();
    }

    public class DataFetcher extends Thread {
        private final ImageListener mListener;
        private final SQLiteDatabase mDb;

        public DataFetcher(ImageListener listener, SQLiteDatabase db) {
            mListener = listener;
            mDb = db;
        }

        @Override
        public void run() {
            Cursor cursor = mDb.rawQuery(GET_IMAGE_QUERY, null);
            final List<Image> dataList = new ArrayList<>();
            if (cursor.getCount() > 0) {
                if (cursor.moveToFirst()) {
                    do {
                        Image data = new Image();
                        data.setFromDatabase(true);
                        data.setPicture(Utility.getBitmapFromByte(cursor.getBlob(cursor.getColumnIndex(PHOTO))));
                        data.setName(cursor.getString(cursor.getColumnIndex(TITLE)));
                        dataList.add(data);
                        publishFlower(data);
                    } while (cursor.moveToNext());
                }
            }
            Handler handler = new Handler(Looper.getMainLooper());
            handler.post(new Runnable() {
                @Override
                public void run() {
                    mListener.onHideDialog();

                }
            });
            cursor.close();
        }

        public void publishFlower(final Image data) {
            Handler handler = new Handler(Looper.getMainLooper());
            handler.post(new Runnable() {
                @Override
                public void run() {
                    mListener.onDeliverData(data);
                }
            });
        }
    }
}
