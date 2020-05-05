package pt.isel.ls.model;

public class StudentClass {

    private Integer studentNumber;
    private String classIdentifier;
    private String semesterRepresent;
    private String courseAcronym;

    public StudentClass(Integer studentNumber, String classIdentifier, String semesterRepresent,
            String courseAcronym) {
        this.studentNumber = studentNumber;
        this.classIdentifier = classIdentifier;
        this.semesterRepresent = semesterRepresent;
        this.courseAcronym = courseAcronym;
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

    @Override
    public String toString() {
        return String
                .format("Student Number = %d, Class Identifier = %s, Semester Represent = %s, "
                                + "Course Acronym = %s", studentNumber, classIdentifier,
                        semesterRepresent, courseAcronym);
    }
}


