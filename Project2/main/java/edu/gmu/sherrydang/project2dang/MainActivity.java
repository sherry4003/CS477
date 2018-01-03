package edu.gmu.sherrydang.project2dang;
/**
 * Created by SherryDang on 10/30/2017.
 */

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import static edu.gmu.sherrydang.project2dang.DatabaseOpenHelper.TABLE_NAME;
import static edu.gmu.sherrydang.project2dang.DatabaseOpenHelper._NAME;
import static edu.gmu.sherrydang.project2dang.DatabaseOpenHelper._WEIGHT;
import static edu.gmu.sherrydang.project2dang.DatabaseOpenHelper._REPS;
import static edu.gmu.sherrydang.project2dang.DatabaseOpenHelper._SETS;
import static edu.gmu.sherrydang.project2dang.DatabaseOpenHelper._NOTE;

public class MainActivity extends Activity {
    private ListView listView;
    private ListAdapter myAdapter;

    private ArrayList<Exercises> exercisesList ;
    Cursor mCursor;
    int currentPos;
    AlertDialog actions;
    SQLiteDatabase db;
    DatabaseOpenHelper dbHelper ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        dbHelper = new DatabaseOpenHelper(this);
        db = dbHelper.getWritableDatabase();
        listView = (ListView) findViewById(R.id.mylist);

        //arraylist to store all data and pass to use for editExercise activity
        exercisesList = new ArrayList<Exercises>();
        //create a listview adapter
        myAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, myExercisesList() );
        listView.setAdapter(myAdapter);

        //open editExericse activity
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Exercises newEx = exercisesList.get(position);
                Toast.makeText(getApplicationContext(), "Clicked on :" + newEx, Toast.LENGTH_SHORT).show();

                //get data of the current oncliked item and pass to EditExercise class
                Intent editIntent = new Intent (MainActivity.this, EditExerciseActivity.class);
                editIntent.putExtra("eName", newEx.getName());
                editIntent.putExtra("eWeight", newEx.getWeight());
                editIntent.putExtra("eReps", newEx.getReps());
                editIntent.putExtra("eSets", newEx.getSets());
                editIntent.putExtra("eNotes", newEx.getNote());
                startActivity(editIntent);
            }
        });

        //delelte item by long pressing item
        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener(){
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(getApplicationContext(), "Item: " + ((TextView) view).getText(), Toast.LENGTH_SHORT).show();
                currentPos = position+1;
                actions.show();
                return true;
            }
        });

        //create a alertDialog
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Are you sure you want to delete this item?");
        String[] options = {"Delete"};
        builder.setItems(options, actionListener);
        builder.setNegativeButton("Cancel", null);
        actions = builder.create();
    }

    //add new button , open AddNewExercisesAcitivity
    public void OnClickedAddExercise(View view) {
        Intent intent = new Intent(MainActivity.this, AddNewExercisesAcitivity.class);
        startActivity(intent);
    }

    //create data for  listadapter
    public ArrayList<String> myExercisesList() {
        ArrayList <String> itemlist = new ArrayList<String>();

        DatabaseOpenHelper dbHelper = new DatabaseOpenHelper(this);
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        mCursor = db.query(TABLE_NAME, null, null, null, null, null, null);
        mCursor.moveToFirst();
        while (mCursor.moveToNext()){
            String nName = mCursor.getString(mCursor.getColumnIndex(_NAME));
            String nWeight = mCursor.getString(mCursor.getColumnIndex(_WEIGHT));
            String nReps = mCursor.getString(mCursor.getColumnIndex(_REPS));
            String nSets = mCursor.getString(mCursor.getColumnIndex(_SETS));
            String nNote = mCursor.getString(mCursor.getColumnIndex(_NOTE));

            Exercises e = new Exercises();
            e.setName(nName);
            e.setWeight(nWeight);
            e.setReps(nReps);
            e.setSets(nSets);
            e.setNote(nNote);
            exercisesList.add(e); // store all data to the arraylist
            itemlist.add(nName); //only add name of the exercise to the listview
        }
        return itemlist;
    }

    //longclicked delete
    DialogInterface.OnClickListener actionListener =
            new DialogInterface.OnClickListener() {

                @Override
                public void onClick(DialogInterface dialog, int which) {
                    switch (which) {
                        case 0: // Delete
                            mCursor.moveToPosition(currentPos);
                            String row = mCursor.getString(1);  // get the id
                            Toast.makeText(getApplicationContext(), "Exercise name: " + row, Toast.LENGTH_SHORT).show();
                            db.delete(TABLE_NAME, dbHelper._NAME + "=?",new String[]{row});
                            mCursor.requery();
                            ((BaseAdapter)myAdapter).notifyDataSetChanged();
                            break;
                        default:
                            break;
                    }
                }
            };

    @Override
    protected void onResume() {
        super.onResume();
        exercisesList = new ArrayList<Exercises>();
        myAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, myExercisesList());
        listView.setAdapter(myAdapter);
    }

    @Override
    protected void onStart() {
        super.onStart();
        exercisesList = new ArrayList<Exercises>();
        myAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, myExercisesList());
        listView.setAdapter(myAdapter);
    }
    public void deleteItem (Exercises e){
        dbHelper = new DatabaseOpenHelper(this);
        db = dbHelper.getWritableDatabase();
        db.delete(dbHelper.TABLE_NAME,dbHelper._NAME + "=?", new String[] {e.getName()} );
    }

}
