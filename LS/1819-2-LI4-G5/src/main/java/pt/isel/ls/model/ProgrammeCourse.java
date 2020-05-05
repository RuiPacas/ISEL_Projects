package pt.isel.ls.model;

public class ProgrammeCourse {

    private String programmeId;
    private String courseAcronym;
    private Boolean mandatory;
    private String curricularSemester;

    public ProgrammeCourse(String programmeId, String courseAcronym, Boolean mandatory,
            String curricularSemester) {
        this.programmeId = programmeId;
        this.courseAcronym = courseAcronym;
        this.mandatory = mandatory;
        this.curricularSemester = curricularSemester;
    }

    public String getProgrammeId() {
        return programmeId;
    }

    public String getCourseAcronym() {
        return courseAcronym;
    }

    public Boolean getMandatory() {
        return mandatory;
    }

    public String getCurricularSemester() {
        return curricularSemester;
    }

    @Override
    public String toString() {
        return String.format("Programme Id = %s, Course Acronym = %s,"
                        + " Mandatory = %s, Curricular Semester = %s", programmeId,
                courseAcronym, mandatory, curricularSemester);
    }
}
