package pt.isel.ls.handler.handlers.users.teachers;

import java.util.HashMap;
import java.util.LinkedList;
import pt.isel.ls.handler.CommandHandler;
import pt.isel.ls.handler.handlers.GetHandlersTest;
import pt.isel.ls.transactionmanager.TestTransactionManager;

public class GetTeachersInClassTest extends GetHandlersTest {

    @Override
    protected void getValidReference(LinkedList<String> reference) {
        reference.add("Teacher name = João, email = joao@isel.pt, Number = 21");
    }

    @Override
    protected CommandHandler getHandler() {
        return new GetTeachersInClass(new TestTransactionManager<>(ds));
    }

    @Override
    protected void getValidMap(HashMap<String, LinkedList<String>> map) {
        LinkedList<String> list = new LinkedList<>();
        list.add("ls");
        map.put("acr", list);
        list = new LinkedList<>();
        list.add("1718w");
        map.put("sem", list);
        list = new LinkedList<>();
        list.add("41D");
        map.put("num", list);
    }

    @Override
    protected void getInvalidMap(HashMap<String, LinkedList<String>> map) {
        LinkedList<String> list = new LinkedList<>();
        list.add("aed");
        map.put("acr", list);
        list = new LinkedList<>();
        list.add("1718w");
        map.put("sem", list);
        list = new LinkedList<>();
        list.add("41D");
        map.put("num", list);
    }
}