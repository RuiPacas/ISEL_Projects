package pt.isel.ls.model;

public class Group {

    private Integer number;

    public Group(Integer number) {
        this.number = number;
    }

    public Integer getNumber() {
        return number;
    }

    @Override
    public String toString() {
        return String.format("Group Number = %d", number);
    }
}
