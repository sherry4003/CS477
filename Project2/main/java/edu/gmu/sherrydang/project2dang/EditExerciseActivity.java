package edu.gmu.sherrydang.project2dang;
/**
 * Created by SherryDang on 11/1/2017.
 */
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class EditExerciseActivity extends AppCompatActivity {
    EditText  weighttxt, repstxt, setstxt, notetxt;
    TextView nametxt;
    String eName, eWeight, eReps, eSets, eNotes;
    String nName ,nWeight, nReps, nSets, nNotes;
    SQLiteDatabase db;
    DatabaseOpenHelper dbHelper ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_exercise);

        //get text from user input
        nametxt =  (TextView) findViewById(R.id.nametxt);
        weighttxt = (EditText) findViewById(R.id.weighttxt);
        repstxt = (EditText) findViewById(R.id.repstxt);
        setstxt = (EditText) findViewById(R.id.setstxt);
        notetxt = (EditText) findViewById(R.id.notetxt);

        //get data from the onselected item
        Intent intent = getIntent();
        eName = intent.getStringExtra("eName");
        eWeight = intent.getStringExtra("eWeight");
        eReps = intent.getStringExtra("eReps");
        eSets = intent.getStringExtra("eSets");
        eNotes = intent.getStringExtra("eNotes");

        nametxt.setText(eName);
        weighttxt.setText(eWeight);
        repstxt.setText(eReps);
        setstxt.setText(eSets);
        notetxt.setText(eNotes);

    }

    public void OnClickedUpdate (View view){
        // get the updated value the userinput
        nWeight = weighttxt.getText().toString();
        nReps = repstxt.getText().toString();
        nSets = setstxt.getText().toString();
        nNotes = notetxt.getText().toString();
        nName = nametxt.getText().toString();
        //add thevalue to Exercise object class
        Exercises newEx = new Exercises();
        newEx.setName(nName);
        newEx.setWeight(nWeight);
        newEx.setReps(nReps);
        newEx.setSets(nSets);
        newEx.setNote(nNotes);

        dbHelper = new DatabaseOpenHelper(this);
        db = dbHelper.getWritableDatabase();
        //get value from Execise class and pass them to contentvalues
        ContentValues values = new ContentValues();
        values.put(dbHelper._SETS, newEx.getSets());
        values.put(dbHelper._NAME , newEx.getName());
        values.put(dbHelper._NOTE, newEx.getNote());
        values.put(dbHelper._REPS, newEx.getReps());
        values.put(dbHelper._WEIGHT,newEx.getWeight());

        //update database values
        db.update(dbHelper.TABLE_NAME, values, dbHelper._NAME + "=?", new String[] {newEx.getName()});
        db.close();
        finish();
        Toast.makeText(this, "Updated " + newEx.getName(), Toast.LENGTH_SHORT).show();

    }

    //delete database
    public void OnClickedDelete (View view){
        dbHelper = new DatabaseOpenHelper(this);
        db = dbHelper.getWritableDatabase();
        // get the updated value the userinput
        nWeight = weighttxt.getText().toString();
        nReps = repstxt.getText().toString();
        nSets = setstxt.getText().toString();
        nNotes = notetxt.getText().toString();
        nName = nametxt.getText().toString();
        //add thevalue to Exercise class
        final Exercises newEx = new Exercises();
        newEx.setName(nName);
        newEx.setWeight(nWeight);
        newEx.setReps(nReps);
        newEx.setSets(nSets);
        newEx.setNote(nNotes);
       // deleteItem(newEx);
        DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                switch (which) {
                    case DialogInterface.BUTTON_POSITIVE: // Yes button clicked
                        db.delete(dbHelper.TABLE_NAME,dbHelper._NAME + "=?", new String[] {newEx.getName()} );
                        Toast.makeText(EditExerciseActivity.this, "Deleted " +newEx.getName(), Toast.LENGTH_SHORT).show();
                        finish();
                        break;
                    default:
                        Toast.makeText(EditExerciseActivity.this, "Canceled", Toast.LENGTH_SHORT).show();
                        break;
                }
            }
        };
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Are you sure you want to delete this item?")
                .setPositiveButton("Delete", dialogClickListener)
                .setNegativeButton("Cancel", dialogClickListener).show();
    }

    public void OnClickedBackButton (View view){
        finish();
    }
}
