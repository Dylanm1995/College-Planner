package ie.wit.collegeplanner.activities;

/**
 * Created by Dylan on 16/03/2018.
 */
/* References
https://www.androidtutorialpoint.com/storage/android-sqlite-database-tutorial/
https://www.journaldev.com/9438/android-sqlite-database-example-tutorial
https://www.androidcode.ninja/android-sqlite-tutorial/
https://www.simplifiedcoding.net/android-sqlite-database-example/
https://www.simplifiedcoding.net/sqlite-crud-example-in-android-activeandroid/
https://inducesmile.com/android/how-to-create-android-spl
 */
import android.content.Context;
import android.content.DialogInterface;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import ie.wit.collegeplanner.R;
import ie.wit.collegeplanner.model.Subject;


public class ViewSubjects extends Base {

    SQLiteDatabase database;
    List<Subject> subjectList;
    ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_subjects);
        database = openOrCreateDatabase(Subjects.DATABASE_NAME, MODE_PRIVATE, null);

        subjectList = new ArrayList<>();
        listView = (ListView) findViewById(R.id.listViewSubjects);
        loadSubjectsFromDatabase();
    }

    private void loadSubjectsFromDatabase() {
        String sql="SELECT * FROM subject";
        Cursor cursor = database.rawQuery(sql, null);

        if(cursor.moveToFirst()) {
            do {
                subjectList.add(new Subject(
                        cursor.getInt(0),
                        cursor.getString(1),
                        cursor.getString(2),
                        cursor.getString(3),
                        cursor.getString(4)
                ));
            } while (cursor.moveToNext());

            SubjectAdapter adapter = new SubjectAdapter(this, R.layout.list_layout_subject, subjectList, database);
            listView.setAdapter(adapter);
        }
    }
}

class SubjectAdapter extends ArrayAdapter<Subject> {

    Context context;
    int listLayout;
    List<Subject> subjectList;
    SQLiteDatabase database;

    public SubjectAdapter(Context context, int listLayout, List<Subject> subjectList, SQLiteDatabase database) {
        super(context, listLayout, subjectList);

        this.context = context;
        this.listLayout = listLayout;
        this.subjectList = subjectList;
        this.database = database;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(listLayout, null);



        //getting views
        TextView subjectNameTextView = view.findViewById(R.id.subjectNameTextView);
        TextView subjectTimetableNameTextView = view.findViewById(R.id.subjectTimetableNameTextView);
        TextView subjectRoomTextView = view.findViewById(R.id.subjectRoomTextView);
        TextView subjectTeacherTextView = view.findViewById(R.id.subjectTeacherTextView);

        //getting employee of the specified position
        final Subject subject = subjectList.get(position);

        //adding data to views
        subjectNameTextView.setText(subject.getSubjectName());
        subjectTimetableNameTextView.setText(subject.getSubjectTimetableName());
        subjectRoomTextView.setText(subject.getSubjectRoom());
        subjectTeacherTextView.setText(subject.getSubjectTeacher());

        //use these buttons later for update and delete operation
        Button buttonDelete = view.findViewById(R.id.buttonDeleteSubject);
        Button buttonEdit = view.findViewById(R.id.buttonEditSubject);

        //adding a clicklistener to button
        buttonEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                updateSubject(subject);
            }
        });

        //the delete operation
        buttonDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setTitle("Are you sure?");
                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        String sql = "DELETE FROM subject WHERE id = ?";
                        database.execSQL(sql, new Integer[]{subject.getId()});
                        reloadSubjectsFromDatabase();
                    }
                });
                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                });
                AlertDialog dialog = builder.create();
                dialog.show();
            }
        });
        return view;
    }

    private void updateSubject(final Subject subject) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);

        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.dialog_update_subject, null);
        builder.setView(view);

        final AlertDialog dialog = builder.create();
        dialog.show();

        final EditText editTextSubjectName = view.findViewById(R.id.editTextSubjectName);
        final EditText editTextSubjectTimetableName = view.findViewById(R.id.editTextSubjectTimetableName);
        final EditText editTextSubjectRoom = view.findViewById(R.id.editTextSubjectRoom);
        final EditText editTextSubjectTeacher = view.findViewById(R.id.editTextSubjectTeacher);

        editTextSubjectName.setText(subject.getSubjectName());
        editTextSubjectTimetableName.setText(subject.getSubjectTimetableName());
        editTextSubjectRoom.setText(subject.getSubjectRoom());
        editTextSubjectTeacher.setText(subject.getSubjectTeacher());

        view.findViewById(R.id.buttonUpdateSubject).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String subjectName = editTextSubjectName.getText().toString().trim();
                String subjectTimetableName = editTextSubjectTimetableName.getText().toString().trim();
                String subjectRoom = editTextSubjectRoom.getText().toString().trim();
                String subjectTeacher = editTextSubjectTeacher.getText().toString().trim();

                if (subjectName.isEmpty()) {
                    editTextSubjectName.setError("Subject name can't be blank");
                    editTextSubjectName.requestFocus();
                    return;
                }

                if (subjectTimetableName.isEmpty()) {
                    editTextSubjectTimetableName.setError("Timetable name can't be blank");
                    editTextSubjectTimetableName.requestFocus();
                    return;
                }

                if (subjectRoom.isEmpty()) {
                    editTextSubjectRoom.setError("Subject Room can't be blank");
                    editTextSubjectRoom.requestFocus();
                    return;
                }

                if (subjectTeacher.isEmpty()) {
                    editTextSubjectTeacher.setError("Subject Teacher can't be blank");
                    editTextSubjectTeacher.requestFocus();
                    return;
                }

                /*String sql = "UPDATE subject SET subjectName = ?, subjectTimetableName = ?, subjectRoom = ? subjectTeacher = ? WHERE id = ?";

                database.execSQL(sql, new String[]{subjectName, subjectTimetableName, subjectRoom, subjectTeacher, String.valueOf(subject.getId())});
                Toast.makeText(context, "Subject Updated", Toast.LENGTH_SHORT).show();
                reloadSubjectsFromDatabase();
                dialog.dismiss();*/
            }
        });
    }

    private void reloadSubjectsFromDatabase() {
        Cursor cursorSubjects = database.rawQuery("SELECT * FROM subject", null);
        if (cursorSubjects.moveToFirst()) {
            subjectList.clear();
            do {
                subjectList.add(new Subject(
                        cursorSubjects.getInt(0),
                        cursorSubjects.getString(1),
                        cursorSubjects.getString(2),
                        cursorSubjects.getString(3),
                        cursorSubjects.getString(4)
                ));
            } while (cursorSubjects.moveToNext());
        }
        cursorSubjects.close();
        notifyDataSetChanged();
    }
}


