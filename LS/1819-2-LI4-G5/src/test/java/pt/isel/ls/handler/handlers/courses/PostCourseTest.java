package pt.isel.ls.handler.handlers.courses;

import java.util.HashMap;
import java.util.LinkedList;
import pt.isel.ls.handler.CommandHandler;
import pt.isel.ls.handler.handlers.PostHandlersTest;
import pt.isel.ls.transactionmanager.TestTransactionManager;

public class PostCourseTest extends PostHandlersTest {

    @Override
    protected String getValidReference() {
        return "Post succeeded";
    }


    @Override
    protected CommandHandler getHandler() {
        return new PostCourse(new TestTransactionManager<>(ds));
    }

    @Override
    protected void getValidMap(HashMap<String, LinkedList<String>> map) {
        LinkedList<String> list = new LinkedList<>();
        list.add("pg");
        map.put("acr", list);
        list = new LinkedList<>();
        list.add("21");
        map.put("teacher", list);
        list = new LinkedList<>();
        list.add("programação");
        map.put("name", list);

    }
}