package pt.isel.ls.handler.handlers.courses;

import java.util.HashMap;
import java.util.LinkedList;
import pt.isel.ls.handler.CommandHandler;
import pt.isel.ls.handler.handlers.PostHandlersTest;
import pt.isel.ls.transactionmanager.TestTransactionManager;

public class PostCourseWithPidTest extends PostHandlersTest {

    @Override
    protected String getValidReference() {
        return "Post succeeded";
    }

    @Override
    protected CommandHandler getHandler() {
        return new PostCourseWithPid(new TestTransactionManager<>(ds));
    }

    @Override
    protected void getValidMap(HashMap<String, LinkedList<String>> map) {
        LinkedList<String> list = new LinkedList<>();
        list.add("LEIC");
        map.put("pid", list);
        list = new LinkedList<>();
        list.add("ac");
        map.put("acr", list);
        list = new LinkedList<>();
        list.add("true");
        map.put("mandatory", list);
        list = new LinkedList<>();
        list.add("2");
        map.put("semesters", list);
    }
}