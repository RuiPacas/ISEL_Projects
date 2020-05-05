package pt.isel.ls.serializers.html;

public class Attribute {

    private String name;
    private String value;

    public Attribute(String name, Object value) {
        this.name = name;
        this.value = value.toString();
    }

    @Override
    public String toString() {
        return name + "=" + '"' + value + '"' + " ";
    }
}
