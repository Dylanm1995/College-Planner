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
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Button;

import ie.wit.collegeplanner.R;

public class Subjects extends Base implements View.OnClickListener {

    private EditText editTextSubjectName;
    private EditText editTextSubjectTimetableName;
    private EditText editTextSubjectRoom;
    private EditText editTextSubjectTeacher;
    TextView textViewViewSubjects;
    private Button buttonAddSubject;
    SQLiteDatabase database;
    private final int SPLASH_DISPLAY_LENGTH = 300;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_subjects);
/*
        new Handler().postDelayed(new Runnable(){
            @Override
            public void run() {
                // Create an Intent that will start the Menu-Activity.
                Intent mainIntent = new Intent(Subjects.this, Splash.class);
                Subjects.this.startActivity(mainIntent);
                Subjects.this.finish();
            }
        }, SPLASH_DISPLAY_LENGTH);*/


        editTextSubjectName = (EditText) findViewById(R.id.editTextSubjectName);
        editTextSubjectTimetableName = (EditText) findViewById(R.id.editTextSubjectTimetableName);
        editTextSubjectRoom = (EditText) findViewById(R.id.editTextSubjectRoom);
        editTextSubjectTeacher = (EditText) findViewById(R.id.editTextSubjectTeacher);

        findViewById(R.id.buttonAddSubject).setOnClickListener(this);
        findViewById(R.id.textViewViewSubjects).setOnClickListener(this);

        //creating a database
        database = openOrCreateDatabase(DATABASE_NAME, MODE_PRIVATE, null);
        createSubjectTable();

    }
    private void createSubjectTable() {
        database.execSQL(
                "CREATE TABLE IF NOT EXISTS subject (\n" +
                        "    id INTEGER PRIMARY KEY AUTOINCREMENT,\n" +
                        "    subjectName varchar(200) NOT NULL,\n" +
                        "    subjectTimetableName varchar(200) NOT NULL,\n" +
                        "    subjectRoom varchar(200) NOT NULL,\n" +
                        "    subjectTeacher varchar(200) NOT NULL\n" +
                        ");"
        );
    }
    //this method will validate the name and salary
    //dept does not need validation as it is a spinner and it cannot be empty
    private boolean inputsAreCorrect(String subjectName, String subjectTimetableName, String subjectRoom, String subjectTeacher) {
        if (subjectName.isEmpty()) {
            editTextSubjectName.setError("Please enter a Subject Name");
            editTextSubjectName.requestFocus();
            return false;
        }

        if (subjectTimetableName.isEmpty()) {
            editTextSubjectTimetableName.setError("Please enter Subject Timetable Name (Max 3 Characters)");
            editTextSubjectTimetableName.requestFocus();
            return false;
        }

        if (subjectRoom.isEmpty()) {
            editTextSubjectRoom.setError("Please enter Subject Room");
            editTextSubjectRoom.requestFocus();
            return false;
        }

        if (subjectTeacher.isEmpty()) {
            editTextSubjectTeacher.setError("Please enter Subject Room");
            editTextSubjectTeacher.requestFocus();
            return false;
        }
        return true;
    }

    //In this method we will do the create operation
    private void addSubject() {
        String subjectName = editTextSubjectName.getText().toString().trim();
        String subjectTimetableName = editTextSubjectTimetableName.getText().toString().trim();
        String subjectRoom = editTextSubjectRoom.getText().toString().trim();
        String subjectTeacher = editTextSubjectTeacher.getText().toString().trim();


        //validating the inputs
        if (inputsAreCorrect(subjectName, subjectTimetableName, subjectRoom, subjectTeacher)) {

            String insertSQL = "INSERT INTO subject \n" +
                    "(subjectName, subjectTimetableName, subjectRoom, subjectTeacher)\n" +
                    "VALUES \n" +
                    "(?, ?, ?, ?);";

            database.execSQL(insertSQL, new String[]{subjectName, subjectTimetableName, subjectRoom, subjectTeacher});

            Toast.makeText(this, "Subject Added Successfully", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.buttonAddSubject:
                addSubject();

                break;
            case R.id.textViewViewSubjects:

                startActivity(new Intent(this, ViewSubjects.class));

                break;
        }
    }
}