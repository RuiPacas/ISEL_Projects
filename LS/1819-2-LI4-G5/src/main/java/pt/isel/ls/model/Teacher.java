package pt.isel.ls.model;

public class Teacher {

    private String name;
    private String email;
    private Integer number;

    public Teacher(String name, String email, Integer number) {
        this.name = name;
        this.email = email;
        this.number = number;
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

    @Override
    public String toString() {
        return String.format("Teacher name = %s, email = %s, Number = %d", name, email, number);
    }
}
