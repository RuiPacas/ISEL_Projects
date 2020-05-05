package pt.isel.ls.handler.handlers;

import java.io.FileNotFoundException;
import java.sql.SQLException;
import java.util.LinkedList;
import org.junit.Assert;
import pt.isel.ls.handler.BaseHandlerTest;
import pt.isel.ls.result.Result;

public abstract class DeleteHandlersTest extends BaseHandlerTest {


    @Override
    protected void validateResultWithValidReference(Result r)
            throws SQLException, FileNotFoundException {
        String expectedReference = getValidReference();
        r.showPlain(getPrintStream());
        LinkedList<String> list = new LinkedList<>();
        readFile(list);
        Assert.assertEquals(expectedReference, list.get(0));

    }

    protected abstract String getValidReference();

    @Override
    protected void validateResultWithInvalidReference(Result r)
            throws FileNotFoundException, SQLException {
        r.showPlain(getPrintStream());
        LinkedList<String> list = new LinkedList<>();
        readFile(list);
        Assert.assertEquals("Delete unsuccessful", list.get(0));
    }
}
