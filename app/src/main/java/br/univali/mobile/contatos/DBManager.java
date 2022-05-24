package br.univali.mobile.contatos;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.ListView;

public class DBManager extends SQLiteOpenHelper {

    private static final String DB_NAME = "contatosdb";
    private static final int DB_VERSION = 1;
    private static final String TABLE_NAME = "contatos";

    private static final String ID_COL = "id";
    private static final String NAME_COL = "name";
    private static final String TYPE_COL = "type";
    private static final String PHONE_COL = "phone_number";

    public DBManager(Context context) {

        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        String query = "CREATE TABLE " + TABLE_NAME + " ("
                + ID_COL + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + NAME_COL + " TEXT,"
                + TYPE_COL + " TEXT,"
                + PHONE_COL + " INTEGER)";

        db.execSQL(query);
    }

    public void addContato(String contatoName, String contatoType, String contatoPhone) {

        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();

        values.put(NAME_COL, contatoName);
        values.put(TYPE_COL, contatoType);
        values.put(PHONE_COL, contatoPhone);

        db.insert(TABLE_NAME, null, values);
        db.close();
    }

    public void deleteContato(String contatoName, String contatoType, String contatoPhone) {

        SQLiteDatabase db = this.getWritableDatabase();

        String whereClause = NAME_COL + " = ? ," + TYPE_COL + " = ? ," + PHONE_COL + " = ?";

        db.delete(TABLE_NAME, whereClause, new String[]{contatoName, contatoType, contatoPhone});
        db.close();
    }

    public void editContato(String contatoName, String contatoType, String contatoPhone, String newName, String newType, String newPhone) {

        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();

        values.put(NAME_COL, newName);
        values.put(TYPE_COL, newType);
        values.put(PHONE_COL, newPhone);

        String whereClause = NAME_COL + " = ? ," + TYPE_COL + " = ? ," + PHONE_COL + " = ?";

        db.update(TABLE_NAME, values, whereClause, new String[]{contatoName, contatoType, contatoPhone});
        db.close();
    }

    public void getContatos(Context context, ListView lv) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor data = db.query(TABLE_NAME, new String[]{ID_COL, NAME_COL, TYPE_COL, PHONE_COL}, null, null, null, null, null);
        int[] to;
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }
}