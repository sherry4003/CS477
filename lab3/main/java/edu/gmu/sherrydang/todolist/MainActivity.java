package edu.gmu.sherrydang.todolist;

import android.support.annotation.MainThread;
import android.support.v7.app.AlertDialog;
import android.content.DialogInterface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Arrays;

import static edu.gmu.sherrydang.todolist.R.id.parent;

public class MainActivity extends AppCompatActivity {
    EditText elem;
    ListView listView;
    ArrayAdapter myAdapter;
    int idx = 0 ;
    private ArrayList <String> arrayList;
    private int currentPos;

    AlertDialog actions;

    // show the diago alert before deleting
    DialogInterface.OnClickListener actionListener = new DialogInterface.OnClickListener() {
        @Override public void onClick(DialogInterface dialog, int which) {
            switch (which) {
                case 0: // Delete
                    myAdapter.remove(myAdapter.getItem(currentPos));
                    break;
                default:
                    break;
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        listView = (ListView) findViewById(R.id.mylist);

        // Param1 - context
        // Param2 - layout for the row
        // Param3 - ID of the view associated with the row
        // Param4 - the Array of data

        String[] items = {"groceries", "wash car", "pickout drycleaning", "libray", "clean basement"};
        arrayList = new ArrayList<>(Arrays.asList(items));
        myAdapter = new ArrayAdapter<String>(this, R.layout.line, arrayList);

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Are you sure want to delete this item?");
        String []options = {"Delete"};
        builder.setItems(options, actionListener);
        builder.setNegativeButton("Cancel", null);
        actions = builder.create();

        listView.setAdapter(myAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String item = String.valueOf(parent.getItemAtPosition(position));
                String nitem;
         //       Toast.makeText(getApplicationContext(), "Item " + ((TextView) view).getText(), Toast.LENGTH_SHORT).show();

                String done = "Done:";

                if (item.length() >4 && item.substring(0,5).equals(done)){
                    ((TextView) view).setText(item);
                    myAdapter.remove(myAdapter.getItem(position));
                    nitem = item.substring(6);
                    myAdapter.insert(nitem,0);

                    elem.setText("");
                }
                else{
                    item = "Done: " + ((TextView) view).getText();
                    myAdapter.remove(myAdapter.getItem(position));
                    String doneItem = (String) item;
                    myAdapter.add(doneItem);
                    elem.setText("");
                }

            }
        });


        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
                                                public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                                                    Toast.makeText(getApplicationContext(), "Removing " + ((TextView) view).getText(), Toast.LENGTH_SHORT).show();
                                                    currentPos = position ;
                                                    actions.show();
                                                //    myAdapter.remove(myAdapter.getItem(position));
                                                    return true;
                                                }
                                            }
        );

        elem =  (EditText)findViewById(R.id.input);

        final TextView.OnEditorActionListener mReturnListener =
                new TextView.OnEditorActionListener() {
                    public boolean onEditorAction(TextView view, int actionId, KeyEvent event) {
                        // If the action is a key-up event on the return key, send the message
                        if (actionId == EditorInfo.IME_NULL && event.getAction() == KeyEvent.ACTION_UP) {
                            String input = view.getText().toString();
                            if (input.equals("")){
                                Toast.makeText(MainActivity.this,"Please enter new task!", Toast.LENGTH_SHORT).show();
                            }else {
                                  myAdapter.add(input);
                          //      arrayList.add(0, input);
                                myAdapter.notifyDataSetChanged();
                                Toast.makeText(getApplicationContext(), "Adding " + input, Toast.LENGTH_SHORT).show();
                                view.setText("");
                            }
                        }
                        return true;
                    }
                };
        elem.setOnEditorActionListener(mReturnListener);

    }

    public void deleteDone(View v) {
        for (int i = 0; i < myAdapter.getCount(); i++){
            String itemDelete = myAdapter.getItem(i).toString();
            if (itemDelete.length() >5 && itemDelete.substring(0,5).equals("Done:")){
                myAdapter.remove(itemDelete);
                i--;
            }
        }

    }

    public void addElem(View v) {
        String input = elem.getText().toString();
        myAdapter.add(input);
        elem.setText("");
    }
}