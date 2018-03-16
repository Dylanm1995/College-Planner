package ie.wit.collegeplanner.model;

/**
 * Created by Dylan on 15/03/2018.
 */

public class Assignment {

    int id;
    String assignmentName;
    String assignmentSubject;
    String assignmentNote;

    public Assignment(int id, String assignmentName, String assignmentSubject, String assignmentNote) {
        this.id = id;
        this.assignmentName = assignmentName;
        this.assignmentSubject = assignmentSubject;
        this.assignmentNote = assignmentNote;
    }

    public int getId() {
        return id;
    }

    public String getAssignmentName() {
        return assignmentName;
    }

    public String getAssignmentSubject() {
        return assignmentSubject;
    }

    public String getAssignmentNote() {
        return assignmentNote;
    }
}
