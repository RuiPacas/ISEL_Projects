package pt.isel.ls.handler.handlers.users.students;

import java.util.HashMap;
import java.util.LinkedList;
import pt.isel.ls.handler.CommandHandler;
import pt.isel.ls.handler.handlers.PostHandlersTest;
import pt.isel.ls.transactionmanager.TestTransactionManager;

public class PostStudentTest extends PostHandlersTest {

    @Override
    protected String getValidReference() {
        return "Post succeeded";
    }

    @Override
    protected CommandHandler getHandler() {
        return new PostStudent(new TestTransactionManager<>(ds));
    }

    @Override
    protected void getValidMap(HashMap<String, LinkedList<String>> map) {
        LinkedList<String> list = new LinkedList<>();
        list.add("44356");
        map.put("num", list);
        list = new LinkedList<>();
        list.add("Paulo Ribeiro");
        map.put("name", list);
        list = new LinkedList<>();
        list.add("A44356@alunos.isel.pt");
        map.put("email", list);
        list = new LinkedList<>();
        list.add("LEIC");
        map.put("pid", list);
    }
}