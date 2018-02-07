package basesDeDados;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class BDWishs extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "wishs.db";
    private static final int DATABASE_VERSION = 1;

    public BDWishs(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE tblWish (id INTEGER PRIMARY KEY NOT NULL , nome VARCHAR(100)	NOT	NULL)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.w("drop", "Upgrading database from version " + oldVersion + " to "
                + newVersion + ", which will destroy all old data");
        db.execSQL("DROP TABLE IF EXISTS tblWish");
        onCreate(db);
    }
}
