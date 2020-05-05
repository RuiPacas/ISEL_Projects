package pt.isel.ls.handler;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.PrintStream;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Scanner;
import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.postgresql.ds.PGSimpleDataSource;
import pt.isel.ls.result.Result;

public abstract class BaseHandlerTest {

    protected static PGSimpleDataSource ds;
    private static ByteArrayOutputStream outputStream;
    private static PrintStream ps;

    @BeforeClass
    public static void set() {
        String connectionString = System.getenv("POSTGRES_TEST");
        ds = new PGSimpleDataSource();
        ds.setUrl(connectionString);
    }

    @Before
    public void setUp() {
        outputStream = new ByteArrayOutputStream();
        ps = new PrintStream(outputStream);
    }

    @After
    public void after() {
        ps.close();
    }

    @Test
    public void shouldPassWithValidResult()
            throws SQLException, FileNotFoundException {
        HashMap<String, LinkedList<String>> map = new HashMap<>();
        getValidMap(map);
        Result result = getHandler().executeCommand(map);
        validateResultWithValidReference(result);
    }

    @Test
    public void shouldPassWithInvalidResult()
            throws SQLException, FileNotFoundException {
        HashMap<String, LinkedList<String>> map = new HashMap<>();
        getInvalidMap(map);
        if (!map.isEmpty()) {
            Result result = getHandler().executeCommand(map);
            validateResultWithInvalidReference(result);
        }
    }

    public PrintStream getPrintStream() {
        return ps;
    }

    public void readFile(LinkedList<String> list) {
        Scanner s = new Scanner(new ByteArrayInputStream(outputStream.toByteArray()));
        while (s.hasNext()) {
            list.add(s.nextLine());
        }
        s.close();
    }

    protected abstract CommandHandler getHandler();

    protected abstract void getValidMap(HashMap<String, LinkedList<String>> map);

    protected abstract void getInvalidMap(HashMap<String, LinkedList<String>> map);

    protected abstract void validateResultWithValidReference(Result r)
            throws SQLException, FileNotFoundException;

    protected abstract void validateResultWithInvalidReference(Result r)
            throws FileNotFoundException, SQLException;


}
