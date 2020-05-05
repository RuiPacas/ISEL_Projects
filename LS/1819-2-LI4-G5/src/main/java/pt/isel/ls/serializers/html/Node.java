package pt.isel.ls.serializers.html;

import java.io.PrintStream;

public interface Node {

    void print(PrintStream out, int column);
}
