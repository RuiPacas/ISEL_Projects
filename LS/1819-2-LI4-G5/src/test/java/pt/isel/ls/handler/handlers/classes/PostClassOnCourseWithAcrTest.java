package pt.isel.ls.handler.handlers.classes;

import java.util.HashMap;
import java.util.LinkedList;
import pt.isel.ls.handler.CommandHandler;
import pt.isel.ls.handler.handlers.PostHandlersTest;
import pt.isel.ls.transactionmanager.TestTransactionManager;

public class PostClassOnCourseWithAcrTest extends PostHandlersTest {

    @Override
    protected String getValidReference() {
        return "Post succeeded";
    }


    @Override
    protected CommandHandler getHandler() {
        return new PostClassOnCourseWithAcr(new TestTransactionManager<>(ds));
    }

    @Override
    protected void getValidMap(HashMap<String, LinkedList<String>> map) {
        LinkedList<String> list = new LinkedList<>();
        list.add("ac");
        map.put("acr", list);
        list = new LinkedList<>();
        list.add("1415s");
        map.put("sem", list);
        list = new LinkedList<>();
        list.add("21D");
        map.put("num", list);
    }
}