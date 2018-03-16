package ie.wit.collegeplanner.activities;
/* References
https://www.androidtutorialpoint.com/storage/android-sqlite-database-tutorial/
https://www.journaldev.com/9438/android-sqlite-database-example-tutorial
https://www.androidcode.ninja/android-sqlite-tutorial/
https://www.simplifiedcoding.net/android-sqlite-database-example/
https://www.simplifiedcoding.net/sqlite-crud-example-in-android-activeandroid/
https://inducesmile.com/android/how-to-create-android-spl
 */
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import ie.wit.collegeplanner.R;

public class Assignments extends Base implements View.OnClickListener {

    TextView textViewViewAssignments;
    Spinner spinnerSubject;
    private EditText editTextAssignmentName;
    private EditText editTextAssignmentNote;
    SQLiteDatabase database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_assignments);

        textViewViewAssignments= (TextView) findViewById(R.id.textViewViewAssignments);
        editTextAssignmentName = (EditText) findViewById(R.id.editTextAssignmentName);
        editTextAssignmentNote = (EditText) findViewById(R.id.editTextAssignmentNote);
        spinnerSubject = (Spinner) findViewById(R.id.spinnerSubject);

        findViewById(R.id.buttonAddAssignment).setOnClickListener(this);
        textViewViewAssignments.setOnClickListener(this);

        //creating a database
        database = openOrCreateDatabase(DATABASE_NAME, MODE_PRIVATE, null);
        createAssignmentTable();
    }

    private void createAssignmentTable() {
        database.execSQL(
                "CREATE TABLE IF NOT EXISTS assignment (\n" +
                        "    id INTEGER PRIMARY KEY AUTOINCREMENT,\n" +
                        "    assignmentName varchar(200) NOT NULL,\n" +
                        "    assignmentSubject varchar(200) NOT NULL,\n" +
                        "    assignmentNote varchar(200) NOT NULL\n" +
                        ");"
        );
    }

    private boolean inputsAreCorrect(String assignmentName, String assignmentNote) {
        if (assignmentName.isEmpty()) {
            editTextAssignmentName.setError("Please enter assignment name");
            editTextAssignmentName.requestFocus();
            return false;
        }

        if (assignmentNote.isEmpty()) {
            editTextAssignmentNote.setError("Please enter assignment note");
            editTextAssignmentNote.requestFocus();
            return false;
        }

        return true;
    }

    //In this method we will do the create operation
    private void addAssignment() {
        String assignmentName = editTextAssignmentName.getText().toString().trim();
        String assignmentNote = editTextAssignmentNote.getText().toString().trim();
        String assignmentSubject = spinnerSubject.getSelectedItem().toString().trim();


        //validating the inputs
        if (inputsAreCorrect(assignmentName, assignmentNote)) {

            String insertSQL = "INSERT INTO assignment \n" +
                    "(assignmentName, assignmentNote, assignmentSubject)\n" +
                    "VALUES \n" +
                    "(?, ?, ?);";

            database.execSQL(insertSQL, new String[]{assignmentName, assignmentNote, assignmentSubject});

            Toast.makeText(this, "Subject Added Successfully", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.buttonAddAssignment:

                addAssignment();

                break;
            case R.id.textViewViewAssignments:

                startActivity(new Intent(this, ViewAssignments.class));

                break;
        }
    }
}