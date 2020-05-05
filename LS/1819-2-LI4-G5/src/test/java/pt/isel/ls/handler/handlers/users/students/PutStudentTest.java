package pt.isel.ls.handler.handlers.users.students;

import java.util.HashMap;
import java.util.LinkedList;
import pt.isel.ls.handler.CommandHandler;
import pt.isel.ls.handler.handlers.PutHandlersTest;
import pt.isel.ls.transactionmanager.TestTransactionManager;

public class PutStudentTest extends PutHandlersTest {

    @Override
    protected void getValidReference(LinkedList<String> reference) {
        reference.add("Put Succeeded");
    }

    @Override
    protected CommandHandler getHandler() {
        return new PutStudent(new TestTransactionManager<>(ds));
    }

    @Override
    protected void getValidMap(HashMap<String, LinkedList<String>> map) {
        LinkedList<String> list = new LinkedList<>();
        list.add("Joaquim");
        map.put("name", list);
        list = new LinkedList<>();
        list.add("joaquim@outlook.pt");
        map.put("email", list);
        list = new LinkedList<>();
        list.add("44820");
        list.add("44821");
        map.put("num", list);
        list = new LinkedList<>();
        list.add("LEIC");
        map.put("pid", list);
    }

    @Override
    protected void getInvalidMap(HashMap<String, LinkedList<String>> map) {
        LinkedList<String> list = new LinkedList<>();
        list.add("Joaquim");
        map.put("name", list);
        list = new LinkedList<>();
        list.add("joaquim@outlook.pt");
        map.put("email", list);
        list = new LinkedList<>();
        list.add("666");
        list.add("22");
        map.put("num", list);
        list = new LinkedList<>();
        list.add("LEIC");
        map.put("pid", list);
    }
}