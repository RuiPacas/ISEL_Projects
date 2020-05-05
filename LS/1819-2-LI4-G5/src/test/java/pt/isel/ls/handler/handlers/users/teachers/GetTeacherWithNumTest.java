package pt.isel.ls.handler.handlers.users.teachers;

import java.util.HashMap;
import java.util.LinkedList;
import pt.isel.ls.handler.CommandHandler;
import pt.isel.ls.handler.handlers.GetHandlersTest;
import pt.isel.ls.transactionmanager.TestTransactionManager;

public class GetTeacherWithNumTest extends GetHandlersTest {


    @Override
    protected void getValidReference(LinkedList<String> reference) {
        reference.add("Teacher name = João, email = joao@isel.pt, Number = 21");
    }

    @Override
    protected CommandHandler getHandler() {
        return new GetTeacherWithNum(new TestTransactionManager<>(ds));
    }

    @Override
    protected void getValidMap(HashMap<String, LinkedList<String>> map) {
        LinkedList<String> list = new LinkedList<>();
        list.add("21");
        map.put("num", list);
    }

    @Override
    protected void getInvalidMap(HashMap<String, LinkedList<String>> map) {
        LinkedList<String> list = new LinkedList<>();
        list.add("23");
        map.put("num", list);

    }

    @Override
    protected int getExpectedNumberForInvalid() {
        return 1;
    }
}