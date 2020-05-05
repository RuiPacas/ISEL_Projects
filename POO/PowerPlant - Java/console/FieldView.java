package console;

import isel.leic.pg.Console;

public class FieldView extends View {
    private String label;
    private String value;

    public FieldView(String label, int top, int left, String value) {
        super(top, left, 2, label.length(), Console.LIGHT_GRAY);
        this.label = label;
        fillValue(value);
    }
    public FieldView(int top, int left, String value) {
        super(top, left, 1, value.length(), Console.LIGHT_GRAY);
        fillValue(value);
    }

    @Override
    public void paint() {
        if (label!=null) {
            Console.color(Console.BLACK, Console.CYAN);
            print(0, 0, label);
        }
        Console.color(Console.BLACK, Console.LIGHT_GRAY);
        print(label!=null?1:0,0,value);
    }

    public void setValue(String txt) {
        fillValue(txt);
        paint();
    }

    private void fillValue(String txt) {
        StringBuilder sb = new StringBuilder(txt);
        while (sb.length()<width) {
            sb.insert(0,' ');
            if (sb.length()<width) sb.append(' ');
        }
        value = sb.toString();
    }

    public void setValue(int value) {
        setValue(Integer.toString(value));
    }
}
