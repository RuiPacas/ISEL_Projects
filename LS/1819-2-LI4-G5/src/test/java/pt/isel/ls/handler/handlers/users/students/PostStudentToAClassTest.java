package pt.isel.ls.handler.handlers.users.students;

import java.util.HashMap;
import java.util.LinkedList;
import pt.isel.ls.handler.CommandHandler;
import pt.isel.ls.handler.handlers.PostHandlersTest;
import pt.isel.ls.transactionmanager.TestTransactionManager;


public class PostStudentToAClassTest extends PostHandlersTest {

    @Override
    protected String getValidReference() {
        return "Post succeeded";
    }

    @Override
    protected CommandHandler getHandler() {
        return new PostStudentToAClass(new TestTransactionManager<>(ds));
    }

    @Override
    protected void getValidMap(HashMap<String, LinkedList<String>> map) {
        LinkedList<String> list = new LinkedList<>();
        list.add("41212");
        map.put("numStu", list);
        list = new LinkedList<>();
        list.add("41D");
        map.put("num", list);
        list = new LinkedList<>();
        list.add("1718w");
        map.put("sem", list);
        list = new LinkedList<>();
        list.add("ls");
        map.put("acr", list);
    }


}