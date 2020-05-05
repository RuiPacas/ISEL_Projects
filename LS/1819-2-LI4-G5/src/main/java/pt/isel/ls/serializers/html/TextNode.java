package pt.isel.ls.serializers.html;

import java.io.PrintStream;

public class TextNode implements Node {

    private String text;

    public TextNode(String text) {
        this.text = text;
    }

    @Override
    public void print(PrintStream out, int column) {
        for (int i = 0; i < column; i++) {
            out.print('\t');
        }
        out.print(text);
    }

}
