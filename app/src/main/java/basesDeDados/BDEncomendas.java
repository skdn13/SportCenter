package basesDeDados;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;


public class BDEncomendas extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "encomendas.db";
    private static final int DATABASE_VERSION = 1;

    public BDEncomendas(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE tblEncomenda (numero INTEGER PRIMARY KEY NOT NULL , nome VARCHAR(100) NOT NULL, conteudo VARCHAR(1000) NOT NULL, total FLOAT NOT NULL)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.w("drop", "Upgrading database from version " + oldVersion + " to "
                + newVersion + ", which will destroy all old data");
        db.execSQL("DROP TABLE IF EXISTS tblEncomenda");
        onCreate(db);
    }
}
