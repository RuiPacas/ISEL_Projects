package pt.isel.ls.model;

public class Class {

    private String identifier;
    private String semesterRepresent;
    private String courseAcronym;

    public Class(String identifier, String semesterRepresent, String courseAcronym) {
        this.identifier = identifier;
        this.semesterRepresent = semesterRepresent;
        this.courseAcronym = courseAcronym;
    }

    public String getIdentifier() {
        return identifier;
    }

    public String getSemesterRepresent() {
        return semesterRepresent;
    }

    public String getCourseAcronym() {
        return courseAcronym;
    }

    @Override
    public String toString() {
        return String.format("Class Identifier = %s,"
                        + " Semester Represent = %s, Course Acronym = %s",
                identifier, semesterRepresent, courseAcronym);
    }
}
