package com.example.ashutosh.gpstracker;

/**
 * Created by ashutosh on 14/02/18.
 */

import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.Cursor;
import android.content.Context;
import android.content.ContentValues;

import java.util.ArrayList;


public class MyDBHandler extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1; // change version only when database structure is changed
    private static final String DATABASE_NAME = "contacts.db";
    private static final String TABLE_CONTACTS = "contacts";
    private static final String COLUMN_ID = "_id";
    private static final String COLUMN_CONTACTNAME = "contactName";
    private static final String COLUMN_CONTACTNUMBER = "contactNumber";

    public MyDBHandler(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, DATABASE_NAME, factory, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

        String query = "CREATE TABLE " + TABLE_CONTACTS + "(" +
                COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_CONTACTNAME + " TEXT NOT NULL, " +
                COLUMN_CONTACTNUMBER + " TEXT NOT NULL " + ");";

        sqLiteDatabase.execSQL(query);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_CONTACTS);
        onCreate(sqLiteDatabase);

    }

    // Add a new row (contact) to the database (use Contact Object)
    public void addContact(Contacts contact){
        ContentValues values = new ContentValues();
        values.put(COLUMN_CONTACTNAME, contact.getContactName());
        values.put(COLUMN_CONTACTNUMBER, contact.getContactNumber());
        SQLiteDatabase db = getWritableDatabase();
        db.insert(TABLE_CONTACTS, null, values);
        db.close();

    }

    // Delete a contact from database
    public void deleteContact(String contactName){
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL("DELETE FROM " + TABLE_CONTACTS + " WHERE " + COLUMN_CONTACTNAME + "=\"" + contactName + "\";" );
    }

    // Print our the database as a string
    public String databaseToString(){
        String dbString = "";
        String contactNumberString = "";
        String contactNameString = "";
        SQLiteDatabase db = getWritableDatabase();
        String query = "SELECT * FROM " + TABLE_CONTACTS + " WHERE 1;"; // WHERE 1 satisfies for all conditions

        // Cursor points to a location in your results
        Cursor c = db.rawQuery(query, null);

        // Move to the first row in your results
        c.moveToFirst();

        while(!c.isAfterLast()){
            if(c.getString(c.getColumnIndex("contactName"))!=null){
                contactNumberString = c.getString(c.getColumnIndex("contactNumber"));
                contactNameString = c.getString(c.getColumnIndex("contactName"));

                dbString += contactNameString + " : " + contactNumberString + "\n";
            }
            c.moveToNext();
        }

        db.close();
        return dbString;
    }

    // List of contact numbers
    public ArrayList<String> contactNumberList(){
        String contactNumberString = "";
        ArrayList<String> contactList = new ArrayList<>();
        SQLiteDatabase db = getWritableDatabase();

        String query = "SELECT * FROM " + TABLE_CONTACTS + " WHERE 1;"; // WHERE 1 satisfies for all conditions
        Cursor c = db.rawQuery(query, null);
        c.moveToFirst();

        while(!c.isAfterLast()){
            if(c.getString(c.getColumnIndex("contactName"))!=null){
                contactNumberString = c.getString(c.getColumnIndex("contactNumber"));
                contactList.add(contactNumberString);
            }
            c.moveToNext();
        }

        db.close();
        return contactList;
    }
}
