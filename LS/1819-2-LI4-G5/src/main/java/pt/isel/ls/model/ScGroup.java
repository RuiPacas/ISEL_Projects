package pt.isel.ls.model;

public class ScGroup {

    private Integer studentNumber;
    private String classIdentifier;
    private String semesterRepresent;
    private String courseAcronym;
    private Integer groupNumber;

    public ScGroup(Integer studentNumber, String classIdentifier, String semesterRepresent,
            String courseAcronym, Integer groupNumber) {
        this.studentNumber = studentNumber;
        this.classIdentifier = classIdentifier;
        this.semesterRepresent = semesterRepresent;
        this.courseAcronym = courseAcronym;
        this.groupNumber = groupNumber;
    }

    public Integer getStudentNumber() {
        return studentNumber;
    }

    public String getClassIdentifier() {
        return classIdentifier;
    }

    public String getSemesterRepresent() {
        return semesterRepresent;
    }

    public String getCourseAcronym() {
        return courseAcronym;
    }

    public Integer getGroupNumber() {
        return groupNumber;
    }

    @Override
    public String toString() {
        return String.format("Student Number = %d, Class Identifier = %s"
                        + ", Semester Represent = %s, Course Acronym = %s, Group Number = %d",
                studentNumber, classIdentifier, semesterRepresent, courseAcronym, groupNumber);
    }
}
