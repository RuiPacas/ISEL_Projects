package pt.isel.ls.handler.handlers.courses;

import java.util.HashMap;
import java.util.LinkedList;
import pt.isel.ls.handler.CommandHandler;
import pt.isel.ls.handler.handlers.GetHandlersTest;
import pt.isel.ls.transactionmanager.TestTransactionManager;

public class GetCourseByAcrTest extends GetHandlersTest {

    @Override
    protected void getValidReference(LinkedList<String> reference) {
        reference
                .add("Course name = Laboratório de Software, Acronym = LS,"
                        + " Coordinator Teacher Number = 21");
    }

    @Override
    protected CommandHandler getHandler() {
        return new GetCourseByAcr(new TestTransactionManager<>(ds));
    }

    @Override
    protected void getValidMap(HashMap<String, LinkedList<String>> map) {
        LinkedList<String> list = new LinkedList<>();
        list.add("ls");
        map.put("acr", list);
    }

    @Override
    protected void getInvalidMap(HashMap<String, LinkedList<String>> map) {
        LinkedList<String> list = new LinkedList<>();
        list.add("aed");
        map.put("acr", list);

    }

    @Override
    protected int getExpectedNumberForInvalid() {
        return 1;
    }
}