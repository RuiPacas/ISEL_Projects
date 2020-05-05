package pt.isel.ls.handler.handlers.groups;

import java.util.HashMap;
import java.util.LinkedList;
import pt.isel.ls.handler.CommandHandler;
import pt.isel.ls.handler.handlers.DeleteHandlersTest;
import pt.isel.ls.transactionmanager.TestTransactionManager;

public class DeleteStudentFromGroupTest extends DeleteHandlersTest {

    @Override
    protected String getValidReference() {
        return "Delete Successful";
    }

    @Override
    protected CommandHandler getHandler() {
        return new DeleteStudentFromGroup(new TestTransactionManager<>(ds));
    }

    @Override
    protected void getValidMap(HashMap<String, LinkedList<String>> map) {
        LinkedList<String> list = new LinkedList<>();
        list.add("44781");
        map.put("numStu", list);
        list = new LinkedList<>();
        list.add("1");
        map.put("gno", list);
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

    @Override
    protected void getInvalidMap(HashMap<String, LinkedList<String>> map) {
        LinkedList<String> list = new LinkedList<>();
        list.add("44781");
        map.put("numStu", list);
        list = new LinkedList<>();
        list.add("2");
        map.put("gno", list);
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