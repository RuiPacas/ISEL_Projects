package pt.isel.ls.model;

public class Course {

    private String name;
    private String acronym;
    private Integer coordinatorNumber;

    public Course(String name, String acronym, Integer coordinatorNumber) {
        this.name = name;
        this.acronym = acronym;
        this.coordinatorNumber = coordinatorNumber;
    }

    public String getName() {
        return name;
    }

    public String getAcronym() {
        return acronym;
    }

    public Integer getCoordinatorNumber() {
        return coordinatorNumber;
    }

    @Override
    public String toString() {
        return String.format("Course name = %s, Acronym = %s, Coordinator Teacher Number = %d",
                name, acronym, coordinatorNumber);
    }
}
