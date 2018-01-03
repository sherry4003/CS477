package edu.gmu.sherrydang.project2dang;
/**
 * Created by SherryDang on 11/1/2017.
 */
import android.app.Activity;
import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

import static edu.gmu.sherrydang.project2dang.DatabaseOpenHelper.TABLE_NAME;
import static edu.gmu.sherrydang.project2dang.DatabaseOpenHelper._ID;
import static edu.gmu.sherrydang.project2dang.DatabaseOpenHelper._NAME;
import static edu.gmu.sherrydang.project2dang.DatabaseOpenHelper._WEIGHT;
import static edu.gmu.sherrydang.project2dang.DatabaseOpenHelper._REPS;
import static edu.gmu.sherrydang.project2dang.DatabaseOpenHelper._SETS;
import static edu.gmu.sherrydang.project2dang.DatabaseOpenHelper._NOTE;

public class AddNewExercisesAcitivity extends Activity  {
    EditText nametxt, weighttxt, repstxt, setstxt, notetxt;
    ListView listView;
    String name, weight, reps, sets, notes;
    Exercises e;
    SQLiteDatabase db;
    DatabaseOpenHelper dbHelper ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_new_exercises_acitivity);

        //get text from user input
        nametxt =  (EditText) findViewById(R.id.nameEdit);
        weighttxt = (EditText) findViewById(R.id.weighttxt);
        repstxt = (EditText) findViewById(R.id.repstxt);
        setstxt = (EditText) findViewById(R.id.setstxt);
        notetxt = (EditText) findViewById(R.id.notetxt);

    }

    public void OnclikedBackButton (View view){
        finish();
    }

    public void OnClickedSaved(View view){
        name = nametxt.getText().toString();
        weight = weighttxt.getText().toString();
        reps = repstxt.getText().toString();
        sets = setstxt.getText().toString();
        notes = notetxt.getText().toString();
        if (nametxt.length() !=0 && weighttxt.length() !=0 && repstxt.length() !=0 && setstxt.length() !=0 && notetxt.length() !=0) {
            e = new Exercises();
            e.setName(name);
            e.setWeight(weight);
            e.setReps(reps);
            e.setSets(sets);
            e.setNote(notes);
            //insert values to database
            AddData(e);
            nametxt.setText("");
            weighttxt.setText("");
            repstxt.setText("");
            setstxt.setText("");
            notetxt.setText("");

            finish();
        }else{
            Toast.makeText(this, "Fill up the blank please", Toast.LENGTH_SHORT).show();
        }
    }

    public void AddData (Exercises exercises){
        dbHelper = new DatabaseOpenHelper(this);
        db = dbHelper.getWritableDatabase();

        //get value from Execise class and pass them to contentvalues
        ContentValues values = new ContentValues();
        values.put(_NAME , exercises.getName());
        values.put(_WEIGHT,exercises.getWeight());
        values.put(_REPS, exercises.getReps());
        values.put(_SETS, exercises.getSets());
        values.put(_NOTE, exercises.getNote());

        if (db == null) db = dbHelper.getWritableDatabase();
        Toast.makeText(this, "Adding " + exercises.getName(), Toast.LENGTH_SHORT).show();
        db.insert(TABLE_NAME, null, values);
        db.close();
    }


}
