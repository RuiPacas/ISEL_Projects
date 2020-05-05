package pt.isel.ls.handler.handlers;

import java.io.FileNotFoundException;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.LinkedList;
import org.junit.Assert;
import pt.isel.ls.handler.BaseHandlerTest;
import pt.isel.ls.result.Result;

public abstract class PostHandlersTest extends BaseHandlerTest {

    @Override
    protected void getInvalidMap(HashMap<String, LinkedList<String>> map) {
    }

    @Override
    protected void validateResultWithValidReference(Result result)
            throws SQLException, FileNotFoundException {
        String expectedReference = getValidReference();
        result.showPlain(getPrintStream());
        LinkedList<String> list = new LinkedList<>();
        readFile(list);
        Assert.assertEquals(expectedReference, list.get(0));

    }

    protected abstract String getValidReference();

    @Override
    protected void validateResultWithInvalidReference(Result result) {
    }


}
