package com.archbrey.letters;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBHelper extends SQLiteOpenHelper {

    public static final String DB_NAME = "LettersDB.db";

    public static final String SHORTCUTS_TABLE = "shortcuts";
    public static final String SHORTCUTS_KEYPAD_ID = "button";
    public static final String SHORTCUTS_PKGNAME = "packagename";

    public static final String FILTERED_TABLE = "filtered";
    public static final String FILTERED_PRIMARY_ID = "_id";
    public static final String FILTERED_FILTER_POSITION = "filterposition";
    public static final String FILTERED_PKGNAME = "packagename";

    public static final String FILTERS_TABLE = "filters";
    public static final String FILTERS_POSITION = "filterposition";
    public static final String FILTERS_NAME = "name";
    public static final String FILTERS_ALIAS = "alias";

    public DBHelper(Context context) {

        super(context, DB_NAME , null, 1);

    } //public DBHelper(Context context)

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

        //create shortcuts table
        String CREATE_TABLE = "CREATE TABLE " +
                SHORTCUTS_TABLE +
                "(" +
                SHORTCUTS_KEYPAD_ID + " INTEGER PRIMARY KEY, " +
                SHORTCUTS_PKGNAME + " TEXT" +
                ")";

        sqLiteDatabase.execSQL(CREATE_TABLE);

        //create filters table
        CREATE_TABLE = "CREATE TABLE " +
                FILTERED_TABLE +
                "(" +
                FILTERED_PRIMARY_ID + " INTEGER PRIMARY KEY, " +
                FILTERED_FILTER_POSITION + " INTEGER, " +
                FILTERED_PKGNAME + " TEXT" +
                ")";

        sqLiteDatabase.execSQL(CREATE_TABLE);

        //create filters table
        CREATE_TABLE = "CREATE TABLE " +
                FILTERS_TABLE +
                "(" +
                FILTERS_POSITION + " INTEGER PRIMARY KEY, " +
                FILTERS_NAME + " TEXT," +
                FILTERS_ALIAS + " TEXT" +
                ")";

        sqLiteDatabase.execSQL(CREATE_TABLE);

    } //public void onCreate(SQLiteDatabase sqLiteDatabase)

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    } //public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1)

    public void AssignShorcut(int getKeypadPosition, String getPackage){

        ContentValues values = new ContentValues();
        values.put (SHORTCUTS_KEYPAD_ID, String.valueOf(getKeypadPosition));
        values.put (SHORTCUTS_PKGNAME, getPackage);

        SQLiteDatabase db = this.getWritableDatabase();
        db.replace(SHORTCUTS_TABLE, null, values);
        db.close();

    } //public void AssignShorcut(String getpackage)

    public String RetrievePackage (int getKeypadPosition) {

        String getPackage;
        Cursor cursor;
        SQLiteDatabase db = this.getWritableDatabase();


        String query = "Select * FROM " +
                SHORTCUTS_TABLE +
                " WHERE " +
                SHORTCUTS_KEYPAD_ID +
                " = " +
                getKeypadPosition;


        cursor = db.rawQuery(query, null);


        if (cursor.moveToFirst()) { //if not query does not result to null
            getPackage = cursor.getString(cursor.getColumnIndex(SHORTCUTS_PKGNAME));
            cursor.close();
        } else
        { getPackage = " "; } //if (cursor.moveToFirst())

        return getPackage ;

    } //public void RetrieveShorcut(int getKeypadPosition)

}
