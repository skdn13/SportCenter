package catalogos;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Handler;
import android.os.Looper;

import pt.ipp.estg.sportcenter.Utility;

import static basesDeDados.BDImagens.GET_IMAGE_QUERY;
import static basesDeDados.BDImagens.PHOTO;
import static basesDeDados.BDImagens.TITLE;


public class DataFetcher extends Thread{
    private final DataFetchListner mListener;
    private final SQLiteDatabase mDb;

    public DataFetcher(DataFetchListner listener, SQLiteDatabase db) {
        mListener = listener;
        mDb = db;
    }
    @Override
    public void run() {
        //Select all data
        Cursor cursor = mDb.rawQuery(GET_IMAGE_QUERY, null);
        // checking database is not empty
        if (cursor.getCount() > 0) {
            // looping through all values and adding to list
            if (cursor.moveToFirst()) {
                do {
                    DataModel data = new DataModel();
                    data.setFromDatabase(true);
                    //getting bitmap value( shown in below)
                    data.setPicture(Utility.getBitmapFromByte(cursor.getBlob(cursor.getColumnIndex(PHOTO))));
                    data.setName(cursor.getString(cursor.getColumnIndex(TITLE)));
                    // adding data
                    publishFlower(data);
                } while (cursor.moveToNext());
            }
        }
        cursor.close();
    }
    // used to pass all data
    public void publishFlower(final DataModel data) {
        Handler handler = new Handler(Looper.getMainLooper());
        handler.post(new Runnable() {
            @Override
            public void run() {
                // passing data through onDeliverData()
                mListener.onDeliverData(data);
            }
        });
    }
}
