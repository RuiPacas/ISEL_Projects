package pt.isel.ls.model;

public class Student {

    private String name;
    private String email;
    private Integer number;
    private String programmeId;

    public Student(String name, String email, Integer number, String programmeId) {
        this.name = name;
        this.email = email;
        this.number = number;
        this.programmeId = programmeId;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public Integer getNumber() {
        return number;
    }

    public String getProgrammeId() {
        return programmeId;
    }

    @Override
    public String toString() {
        return String.format("Student name = %s, email = %s, "
                + "Number = %d, Programme = %s", name, email, number, programmeId);
    }
}
