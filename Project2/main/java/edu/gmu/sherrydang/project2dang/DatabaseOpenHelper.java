package edu.gmu.sherrydang.project2dang;

/**
 * Created by SherryDang on 10/29/2017.
 */

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.BaseColumns;

/**
 * Created by white on 10/12/2017.
 */

public class DatabaseOpenHelper extends SQLiteOpenHelper {
    final static String TABLE_NAME = "exercises";
    final static String _ID = "_id";
    final static String _NAME= "name";
    final static String _WEIGHT= "weight";
    final static String _REPS= "reps";
    final static String _SETS= "sets";
    final static String _NOTE= "note";


    final private static String NAME = "ex_db";
    final private static Integer VERSION = 3 ;
    SQLiteDatabase db = null;
    final private Context context;

    public DatabaseOpenHelper(Context context) {
        super(context, NAME, null, VERSION);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        String CREATE_CMD = "CREATE TABLE exercises (" + _ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + _NAME+ " TEXT NOT NULL,"
                + _WEIGHT + " TEXT NOT NULL,"
                + _REPS + " TEXT NOT NULL,"
                + _SETS + " TEXT NOT NULL,"
                +_NOTE + " TEXT NOT NULL);";
        db.execSQL(CREATE_CMD);

        db.execSQL("INSERT INTO " + TABLE_NAME+ "(_id, name, weight, reps, sets , note ) VALUES ('1', 'Sit up', '5', '3', '5', 'Free weight')");
        db.execSQL("INSERT INTO " + TABLE_NAME+ "(_id, name, weight, reps, sets , note ) VALUES ('2', 'Bicep curls', '6', '7', '3', 'Free weight')");
        db.execSQL("INSERT INTO " + TABLE_NAME+ "(_id, name, weight, reps, sets , note ) VALUES ('3', 'Shoulder lifts', '5', '10', '5', 'Free weight')");

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // TODO Auto-generated method stub
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    void deleteDatabase ( ) {
        context.deleteDatabase(NAME);
    }
}