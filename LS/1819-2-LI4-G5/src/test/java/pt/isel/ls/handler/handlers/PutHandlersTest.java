package pt.isel.ls.handler.handlers;

import java.io.FileNotFoundException;
import java.sql.SQLException;
import java.util.LinkedList;
import org.junit.Assert;
import pt.isel.ls.handler.BaseHandlerTest;
import pt.isel.ls.result.Result;

public abstract class PutHandlersTest extends BaseHandlerTest {

    @Override
    protected final void validateResultWithValidReference(Result result)
            throws SQLException, FileNotFoundException {
        result.showPlain(getPrintStream());
        LinkedList<String> list = new LinkedList<>();
        readFile(list);
        LinkedList<String> reference = new LinkedList<>();
        getValidReference(reference);
        Assert.assertEquals(reference.size(), list.size());
        for (int i = 0; i < list.size(); i++) {
            Assert.assertEquals(reference.get(i), list.get(i));
        }
    }

    @Override
    protected final void validateResultWithInvalidReference(Result result)
            throws FileNotFoundException, SQLException {
        result.showPlain(getPrintStream());
        LinkedList<String> list = new LinkedList<>();
        readFile(list);
        Assert.assertEquals(1, list.size());
        Assert.assertEquals("Put Failed", list.get(0));
    }


    protected abstract void getValidReference(LinkedList<String> reference);

}
