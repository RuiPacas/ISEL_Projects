package pt.isel.ls.handler.handlers.users.students;

import java.util.HashMap;
import java.util.LinkedList;
import pt.isel.ls.handler.CommandHandler;
import pt.isel.ls.handler.handlers.GetHandlersTest;
import pt.isel.ls.transactionmanager.TestTransactionManager;

public class GetStudentWithNumTest extends GetHandlersTest {

    @Override
    protected void getValidReference(LinkedList<String> reference) {
        reference
                .add("Student name = Pedro, email = pedro@isel.pt,"
                        + " Number = 44781, Programme = LEIC");
    }

    @Override
    protected CommandHandler getHandler() {
        return new GetStudentWithNum(new TestTransactionManager<>(ds));
    }

    @Override
    protected void getValidMap(HashMap<String, LinkedList<String>> map) {
        LinkedList<String> list = new LinkedList<>();
        list.add("44781");
        map.put("num", list);
    }

    @Override
    protected void getInvalidMap(HashMap<String, LinkedList<String>> map) {
        LinkedList<String> list = new LinkedList<>();
        list.add("49000");
        map.put("num", list);
    }

    @Override
    protected int getExpectedNumberForInvalid() {
        return 1;
    }
}