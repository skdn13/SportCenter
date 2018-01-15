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
        Cursor cursor = mDb.rawQuery(GET_IMAGE_QUERY, null);
        if (cursor.getCount() > 0) {
            if (cursor.moveToFirst()) {
                do {
                    DataModel data = new DataModel();
                    data.setFromDatabase(true);
                    data.setPicture(Utility.getBitmapFromByte(cursor.getBlob(cursor.getColumnIndex(PHOTO))));
                    data.setName(cursor.getString(cursor.getColumnIndex(TITLE)));
                    publishFlower(data);
                } while (cursor.moveToNext());
            }
        }
        cursor.close();
    }

    public void publishFlower(final DataModel data) {
        Handler handler = new Handler(Looper.getMainLooper());
        handler.post(new Runnable() {
            @Override
            public void run() {
                mListener.onDeliverData(data);
            }
        });
    }
}
