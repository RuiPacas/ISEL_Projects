package pt.isel.ls.handler.handlers.users.teachers;

import java.util.HashMap;
import java.util.LinkedList;
import pt.isel.ls.handler.CommandHandler;
import pt.isel.ls.handler.handlers.PostHandlersTest;
import pt.isel.ls.transactionmanager.TestTransactionManager;

public class PostTeacherTest extends PostHandlersTest {

    @Override
    protected String getValidReference() {
        return "Post succeeded";
    }


    @Override
    protected CommandHandler getHandler() {
        return new PostTeacher(new TestTransactionManager<>(ds));
    }

    @Override
    protected void getValidMap(HashMap<String, LinkedList<String>> map) {
        LinkedList<String> list = new LinkedList<>();
        list.add("32");
        map.put("num", list);
        list = new LinkedList<>();
        list.add("Cátia Vaz");
        map.put("name", list);
        list = new LinkedList<>();
        list.add("catiavaz@isel.pt");
        map.put("email", list);
    }
}