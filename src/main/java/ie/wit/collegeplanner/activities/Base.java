package ie.wit.collegeplanner.activities;

/**
 * Created by Dylan on 16/03/2018.
 */

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
import ie.wit.collegeplanner.R;
import ie.wit.collegeplanner.model.Subject;

public class Base extends AppCompatActivity
{
    public static List<Subject> listSubjects = new ArrayList<Subject>();
   // public final int       subjectLimit       = 100;
    //public int             totalSubjects = 0;
    //public int amount;
    public static final String DATABASE_NAME = "CollegePlannerDatabase";


    /*public boolean newSubject(Subject subject)
    {
        boolean targetAchieved = totalSubjects > subjectLimit;
        if (!targetAchieved)
        {
            listSubjects.add(subject);
            totalSubjects += amount;
        }
        else
            Toast.makeText(this, "Target Exceeded!", Toast.LENGTH_SHORT).show();

        return targetAchieved;
    }*/

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_base, menu);
        return true;
    }

    @Override
    public boolean onPrepareOptionsMenu (Menu menu){
        super.onPrepareOptionsMenu(menu);
        MenuItem subject = menu.findItem(R.id.menuSubject);
        MenuItem viewSubjects = menu.findItem(R.id.menuViewSubjects);
        MenuItem assignment = menu.findItem(R.id.menuAssignment);
        MenuItem viewAssignments = menu.findItem(R.id.menuViewAssignments);

        //if(listSubjects.isEmpty())
        //   viewSubjects.setEnabled(false);
        //else
        //   viewSubjects.setEnabled(true);

        if(this instanceof Subjects){
            subject.setVisible(false);
            //if(!listSubjects.isEmpty())
            //    viewSubjects.setVisible(true);
            //if(!listAssignments.isEmpty())
            //    viewAssignments.setVisible(true);
        }

        else {
            subject.setVisible(true);
        }

        if(this instanceof ViewSubjects){
            viewSubjects.setVisible(false);
            //if(!listSubjects.isEmpty())
            //    viewSubjects.setVisible(true);
            //if(!listAssignments.isEmpty())
            //    viewAssignments.setVisible(true);
        }

        else {
            viewSubjects.setVisible(true);

        }

        if(this instanceof Assignments){
            assignment.setVisible(false);
            //if(!listSubjects.isEmpty())
            //    viewSubjects.setVisible(true);
            //if(!listAssignments.isEmpty())
            //    viewAssignments.setVisible(true);
        }

        else {
            assignment.setVisible(true);

        }

        if(this instanceof ViewAssignments){
            viewAssignments.setVisible(false);
            //if(!listSubjects.isEmpty())
            //    viewSubjects.setVisible(true);
            //if(!listAssignments.isEmpty())
            //    viewAssignments.setVisible(true);
        }

        else {
            viewAssignments.setVisible(true);

        }
        return true;
    }


    public void report(MenuItem item)
    {
        startActivity (new Intent(this, ViewSubjects.class));
        Toast.makeText(this, "View Subjects Selected", Toast.LENGTH_SHORT).show();

    }

    public void subjects(MenuItem item)
    {
        startActivity (new Intent(this, Subjects.class));
        Toast.makeText(this, "Subjects Selected", Toast.LENGTH_SHORT).show();

    }

    public void assignmentReport(MenuItem item)
    {
        startActivity (new Intent(this, ViewAssignments.class));
        Toast.makeText(this, "View Assignments Selected", Toast.LENGTH_SHORT).show();

    }

    public void assignments(MenuItem item)
    {
        startActivity (new Intent(this, Assignments.class));
        Toast.makeText(this, "Assignments Selected", Toast.LENGTH_SHORT).show();

    }
}
