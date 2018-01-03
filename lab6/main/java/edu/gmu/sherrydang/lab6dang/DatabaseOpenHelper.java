package edu.gmu.sherrydang.lab6dang;

/**
 * Created by SherryDang on 10/17/2017.
 */


import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by white on 10/12/2017.
 */

public class DatabaseOpenHelper extends SQLiteOpenHelper {
    final static String TABLE_NAME = "birthdays";
    final static String ITEM = "item";
    final static String _ID = "_id";
    final private static String CREATE_CMD =
            "CREATE TABLE birthdays (" + _ID +
                    " INTEGER PRIMARY KEY AUTOINCREMENT, " +ITEM + " TEXT NOT NULL)";
    //    final static String CREATE_CMD = "CREATE TABLE " + TABLE_NAME +"("+ _ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +ITEM + " TEXT NOT NULL)";
    final private static String NAME = "todo_db";
    final private static Integer VERSION = 2;
    final private Context context;

    public DatabaseOpenHelper(Context context) {
        super(context, NAME, null, VERSION);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_CMD);


    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // TODO Auto-generated method stub
//        db.execSQL("DROP TABLE IF EXISTS " + CREATE_CMD);
//        onCreate(db);
    }

    void deleteDatabase ( ) {
        context.deleteDatabase(NAME);
    }
}