package basesDeDados;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;


public class BDProduto extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "mydatabase.db";
    private static final int DATABASE_VERSION = 1;

    public BDProduto(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE tblProduto (referencia INTEGER PRIMARY KEY, preco FLOAT NOT NULL, sexo VARCHAR(20) NOT NULL, nome VARCHAR(100)	NOT	NULL, tamanho VARCHAR(30) NOT NULL, cor VARCHAR(30) NOT NULL, composicao VARCHAR(50) NOT NULL, imagem VARCHAR(200) NOT NULL, disponivel VARCHAR(10) NOT NULL)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.w("drop", "Upgrading database from version " + oldVersion + " to "
                + newVersion + ", which will destroy all old data");
        db.execSQL("DROP TABLE IF EXISTS tblProduto");
        onCreate(db);
    }
}

