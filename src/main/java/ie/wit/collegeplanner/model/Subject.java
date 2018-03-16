package ie.wit.collegeplanner.model;

/**
 * Created by Dylan on 15/03/2018.
 */

public class Subject {
    int id;
    String subjectName;
    String subjectTimetableName;
    String subjectRoom;
    String subjectTeacher;

    public Subject(int id, String subjectName, String subjectTimetableName, String subjectRoom, String subjectTeacher) {
        this.id = id;
        this.subjectName = subjectName;
        this.subjectTimetableName = subjectTimetableName;
        this.subjectRoom = subjectRoom;
        this.subjectTeacher = subjectTeacher;
    }

    public int getId() {
        return id;
    }

    public String getSubjectName() {
        return subjectName;
    }

    public String getSubjectTimetableName() {
        return subjectTimetableName;
    }

    public String getSubjectRoom() {
        return subjectRoom;
    }

    public String getSubjectTeacher() {
        return subjectTeacher;
    }
}
