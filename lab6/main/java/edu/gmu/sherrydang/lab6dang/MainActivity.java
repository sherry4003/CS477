package edu.gmu.sherrydang.lab6dang;

import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v4.widget.SimpleCursorAdapter;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CursorAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Arrays;

import static edu.gmu.sherrydang.lab6dang.DatabaseOpenHelper.ITEM;
import static edu.gmu.sherrydang.lab6dang.DatabaseOpenHelper.TABLE_NAME;
import static edu.gmu.sherrydang.lab6dang.DatabaseOpenHelper._ID;
import static java.lang.Boolean.TRUE;

public class MainActivity extends AppCompatActivity {
    EditText elem;
    ListView listView;
    public SimpleCursorAdapter myAdapter;
    AlertDialog actions;
    int currentPos;
    SQLiteDatabase db = null;
    DatabaseOpenHelper dbHelper = null;
    Cursor mCursor;
    String[] columns = new String[] {"_id", DatabaseOpenHelper.ITEM};
    String task, currentdata;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        dbHelper = new DatabaseOpenHelper(this);
        db = dbHelper.getWritableDatabase();

        mCursor = db.query(TABLE_NAME, columns, null, null, null, null,
                null);

        listView = (ListView) findViewById(R.id.mylist);

        myAdapter = new SimpleCursorAdapter(this,
                android.R.layout.simple_list_item_1,
                mCursor,
                new String[] { "item" },
                new int[] { android.R.id.text1 });

        listView.setAdapter(myAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
             // (cant use this)  String task = myAdapter.getItem(position).toString();
                ArrayList<String> arrayList = new ArrayList<String>();
                mCursor.moveToPosition(position);
                String str = mCursor.getString(1);

                 task = ((TextView) view).getText().toString();
                if (task.startsWith("Done:")) {
                    // TODO Lab 6
                   ContentValues values = new ContentValues();
                    String newtask = task.substring(6);
                    values.put(DatabaseOpenHelper.ITEM, newtask);
                    db.insert(TABLE_NAME, null, values);

                    String rowId = mCursor.getString(0);  // get the id
                    if (db == null) db = dbHelper.getWritableDatabase();
                    db.delete(TABLE_NAME,"_id = ?",new String[]{rowId});
                    mCursor.requery();
                    myAdapter.notifyDataSetChanged();

                } else {
                    // TODO Lab 6
                    //                   myAdapter.remove(myAdapter.getItem(position));
                    //                     myAdapter.add("Done: " + task);
                    str = "Done: " + str;
                    mCursor.moveToFirst();
                    int i =0;
                    while (!mCursor.isAfterLast()){
                        if(i != position){
                            arrayList.add(mCursor.getString(1));
                        }
                        mCursor.moveToNext();
                        i++;
                    }
                    removeAll();
                    doAdd(str);
                    for (int j = 0; j <arrayList.size();j++){
                        doAdd(arrayList.get(j));
                    }
                }
            }
        });

        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
                                                public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                                                    Toast.makeText(getApplicationContext(), "Removing " + ((TextView) view).getText(), Toast.LENGTH_SHORT).show();
                                                    currentPos = position;
                                                    actions.show();
                                                    return true;
                                                }
                                            }
        );

        elem =  (EditText)findViewById(R.id.input);

        //myAdapter.add("Lab 3: Prof. White");
        AlertDialog.Builder builder = new
                AlertDialog.Builder(this);
        builder.setTitle("Are you sure you want to delete this item?");
        String[] options = {"Delete"};
        builder.setItems(options, actionListener);
        builder.setNegativeButton("Cancel", null);
        actions = builder.create();

    }
    DialogInterface.OnClickListener actionListener =
            new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    switch (which) {
                        case 0: // Delete
                            mCursor.moveToPosition(currentPos);
                            String rowId = mCursor.getString(0);  // get the id
                            if (db == null) db = dbHelper.getWritableDatabase();
                            db.delete(TABLE_NAME,"_id = ?",new String[]{rowId});
                            mCursor.requery();
                            myAdapter.notifyDataSetChanged();
                            //
                            // remove item from DB
                            break;
                        default:
                            break;
                    }
                }
            };


    public void onPause() {
        super.onPause();
        db.close();
    }


    public void addElem(View v) {
        String input = elem.getText().toString();
        doAdd(input);
        elem.setText("");
    }

    private void doAdd(String input) {
        if (!input.equals("")) {
         //   Toast.makeText(getApplicationContext(), "Adding " + input, Toast.LENGTH_SHORT).show();
            // add to db
            ContentValues cv = new ContentValues(1);
            cv.put(DatabaseOpenHelper.ITEM, input);
            if (db == null) db = dbHelper.getWritableDatabase();
            db.insert(TABLE_NAME, null, cv);
            mCursor.requery();

            myAdapter.notifyDataSetChanged();

        }
    }

    public void deleteDone(View v) {
        int len = myAdapter.getCount();

        mCursor.moveToFirst();
        while (!mCursor.isAfterLast()) {
            int iRow = mCursor.getColumnIndex(ITEM);
            task = mCursor.getString(iRow);
            if (task.startsWith("Done: ")) {
                // TODO Lab 6
                db.delete(DatabaseOpenHelper.TABLE_NAME, ITEM + "=?", new String[]{task});
            }
            mCursor.moveToNext();
        }
        mCursor.requery();
        myAdapter.notifyDataSetChanged();
    }
    /**
     * Remove all users and groups from database.
     */
    public void removeAll()
    {
        // db.delete(String tableName, String whereClause, String[] whereArgs);
        // If whereClause is null, it will delete all rows.
        SQLiteDatabase db = dbHelper.getWritableDatabase(); // helper is object extends SQLiteOpenHelper
        db.delete(DatabaseOpenHelper.TABLE_NAME, null, null);
    }
}