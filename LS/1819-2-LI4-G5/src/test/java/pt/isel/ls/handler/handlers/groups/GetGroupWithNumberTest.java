package pt.isel.ls.handler.handlers.groups;

import java.util.HashMap;
import java.util.LinkedList;
import pt.isel.ls.handler.CommandHandler;
import pt.isel.ls.handler.handlers.GetHandlersTest;
import pt.isel.ls.transactionmanager.TestTransactionManager;

public class GetGroupWithNumberTest extends GetHandlersTest {

    @Override
    protected void getValidReference(LinkedList<String> reference) {
        reference
                .add("Student name = Pedro, email = pedro@isel.pt, Number = 44781, "
                        + "Programme = LEIC, Group Number = 1");
        reference
                .add("Student name = Jéssica, email = jessica@isel.pt, "
                        + "Number = 44820, Programme = LEIC, Group Number = 1");
    }

    @Override
    protected CommandHandler getHandler() {
        return new GetGroupWithNumber(new TestTransactionManager<>(ds));
    }

    @Override
    protected void getValidMap(HashMap<String, LinkedList<String>> map) {
        LinkedList<String> list = new LinkedList<>();
        list.add("ls");
        map.put("acr", list);
        list = new LinkedList<>();
        list.add("41D");
        map.put("num", list);
        list = new LinkedList<>();
        list.add("1718w");
        map.put("sem", list);
        list = new LinkedList<>();
        list.add("1");
        map.put("gno", list);
    }

    @Override
    protected void getInvalidMap(HashMap<String, LinkedList<String>> map) {
        LinkedList<String> list = new LinkedList<>();
        list.add("ls");
        map.put("acr", list);
        list = new LinkedList<>();
        list.add("41D");
        map.put("num", list);
        list = new LinkedList<>();
        list.add("1718w");
        map.put("sem", list);
        list = new LinkedList<>();
        list.add("2");
        map.put("gno", list);
    }

}