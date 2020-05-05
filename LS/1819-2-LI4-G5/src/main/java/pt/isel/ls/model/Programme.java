package pt.isel.ls.model;

public class Programme {

    private String programmeId;
    private String name;
    private Integer numberOfSemesters;

    public Programme(String programmeId, String name, Integer numberOfSemesters) {
        this.programmeId = programmeId;
        this.name = name;
        this.numberOfSemesters = numberOfSemesters;
    }

    public String getProgrammeId() {
        return programmeId;
    }

    public String getName() {
        return name;
    }

    public Integer getNumberOfSemesters() {
        return numberOfSemesters;
    }

    @Override
    public String toString() {
        return String.format("Acronym = %s, Programme name = %s, Number of semesters = %d",
                programmeId, name, numberOfSemesters);
    }
}
