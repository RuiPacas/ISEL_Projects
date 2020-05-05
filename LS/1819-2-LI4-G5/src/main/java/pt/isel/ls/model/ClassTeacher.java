package pt.isel.ls.model;

public class ClassTeacher {

    private Integer teacherNumber;
    private String courseAcronym;
    private String identifierClass;
    private String semesterRepresent;

    public ClassTeacher(Integer teacherNumber, String courseAcronym, String identifierClass,
            String semesterRepresent) {
        this.teacherNumber = teacherNumber;
        this.courseAcronym = courseAcronym;
        this.identifierClass = identifierClass;
        this.semesterRepresent = semesterRepresent;
    }

    public Integer getTeacherNumber() {
        return teacherNumber;
    }

    public String getCourseAcronym() {
        return courseAcronym;
    }

    public String getIdentifierClass() {
        return identifierClass;
    }

    public String getSemesterRepresent() {
        return semesterRepresent;
    }

    @Override
    public String toString() {
        return String.format("Class Identifier = %s, Semester Represent = %s, Course Acronym = %s",
                identifierClass, semesterRepresent, courseAcronym);
    }
}
