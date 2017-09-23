package id.geekgarden.esi.data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

import id.geekgarden.esi.data.model.tikets.SQLiteSparepart;
import id.geekgarden.esi.listtiket.activity.AddSparepart;
import id.geekgarden.esi.listtiket.activity.Sparepart;

/**
 * Created by komuri on 20/09/2017.
 */

public class DatabaseHandler extends SQLiteOpenHelper {
    // All Static variables
    // Database Version
    private static final int DATABASE_VERSION = 1;

    // Database Name
    private static final String DATABASE_NAME = "sparepartmanager";

    // Contacts table name
    private static final String TABLE_SPAREPART = "sparepart";

    // Contacts Table Columns names
    private static final String KEY_PART = "part_number";
    private static final String KEY_DESC = "description";
    private static final String KEY_QTY = "qty";
    private static final String KEY_STATUS = "status";
    private static final String KEY_KET = "keterangan";

    public DatabaseHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_CONTACTS_TABLE =
                "CREATE TABLE " + TABLE_SPAREPART + "("
                        + KEY_PART + " TEXT,"
                        + KEY_DESC + " TEXT,"
                        + KEY_QTY + " TEXT,"
                        + KEY_STATUS + " TEXT,"
                        + KEY_KET + " TEXT" + ");";
        db.execSQL(CREATE_CONTACTS_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        // Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_SPAREPART);

        // Create tables again
        onCreate(db);
    }

    // Adding new contact
    public void addSparepart(SQLiteSparepart sqLiteSparepart) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_PART, sqLiteSparepart.getPartnumber());
        values.put(KEY_DESC, sqLiteSparepart.getDescription());
        values.put(KEY_QTY, sqLiteSparepart.getQty());
        values.put(KEY_STATUS, sqLiteSparepart.getStatus());
        values.put(KEY_KET, sqLiteSparepart.getKeterangan());


        // Inserting Row
        db.insert(TABLE_SPAREPART, null, values);
        db.close(); // Closing database connection
    }

    // Getting single contact
    SQLiteSparepart getSparepart(int id) {
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(TABLE_SPAREPART, new String[] { KEY_PART,
                        KEY_DESC, KEY_QTY, KEY_STATUS, KEY_KET }, KEY_PART + "=?",
                new String[] { String.valueOf(id) }, null, null, null, null);
        if (cursor != null)
            cursor.moveToFirst();

        SQLiteSparepart sqLiteSparepart = new SQLiteSparepart(cursor.getString(0),
                cursor.getString(1), cursor.getString(2), cursor.getString(3), cursor.getString(4));
        // return contact
        return sqLiteSparepart;
    }

    // Getting All Contacts
    public List<SQLiteSparepart> getAllSparepart() {
        List<SQLiteSparepart> sparepartList = new ArrayList<SQLiteSparepart>();
        // Select All Query
        String selectQuery = "SELECT  * FROM " + TABLE_SPAREPART;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                SQLiteSparepart sqLiteSparepart = new SQLiteSparepart();
                sqLiteSparepart.setPartnumber(cursor.getString(0));
                sqLiteSparepart.setDescription(cursor.getString(1));
                sqLiteSparepart.setQty(cursor.getString(2));
                sqLiteSparepart.setStatus(cursor.getString(3));
                sqLiteSparepart.setKeterangan(cursor.getString(4));
                // Adding contact to list
                sparepartList.add(sqLiteSparepart);
            } while (cursor.moveToNext());
        }

        // return contact list
        return sparepartList;
    }

    // Updating single contact
    public int updateSparepart(SQLiteSparepart sqLiteSparepart) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_PART, sqLiteSparepart.getPartnumber());
        values.put(KEY_DESC, sqLiteSparepart.getDescription());
        values.put(KEY_QTY, sqLiteSparepart.getQty());
        values.put(KEY_STATUS, sqLiteSparepart.getStatus());
        values.put(KEY_KET,sqLiteSparepart.getKeterangan());

        // updating row
        return db.update(TABLE_SPAREPART, values, KEY_PART + " = ?",
                new String[] { String.valueOf(sqLiteSparepart.getPartnumber()) });
    }

    // Deleting single contact
    public void deleteSparepart(SQLiteSparepart sqLiteSparepart) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_SPAREPART, KEY_PART + " = ?",
                new String[] { String.valueOf(sqLiteSparepart.getPartnumber()) });
        db.close();
    }


    // Getting contacts Count
    public int getSparepartCount() {
        String countQuery = "SELECT  * FROM " + TABLE_SPAREPART;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        cursor.close();

        // return count
        return cursor.getCount();
    }

}
