package basesDeDados;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by pmms8 on 1/13/2018.
 */

public class BDItens extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "itens.db";
    private static final int DATABASE_VERSION = 1;

    public BDItens(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE tblItem (id INTEGER PRIMARY KEY AUTOINCREMENT, preco FLOAT NOT NULL, quantidade INTEGER NOT NULL, nome VARCHAR(100)	NOT	NULL)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.w("drop", "Upgrading database from version " + oldVersion + " to "
                + newVersion + ", which will destroy all old data");
        db.execSQL("DROP TABLE IF EXISTS tblItem");
        onCreate(db);
    }
}
