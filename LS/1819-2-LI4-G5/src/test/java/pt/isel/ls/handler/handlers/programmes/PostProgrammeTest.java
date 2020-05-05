package pt.isel.ls.handler.handlers.programmes;

import java.util.HashMap;
import java.util.LinkedList;
import pt.isel.ls.handler.CommandHandler;
import pt.isel.ls.handler.handlers.PostHandlersTest;
import pt.isel.ls.transactionmanager.TestTransactionManager;

public class PostProgrammeTest extends PostHandlersTest {

    @Override
    protected String getValidReference() {
        return "Post succeeded";
    }


    @Override
    protected CommandHandler getHandler() {
        return new PostProgramme(new TestTransactionManager<>(ds));
    }

    @Override
    protected void getValidMap(HashMap<String, LinkedList<String>> map) {
        LinkedList<String> list = new LinkedList<>();
        list.add("LMATE");
        map.put("pid", list);
        list = new LinkedList<>();
        list.add("LICENCIATURA APLICADA A TECNOLOGIA E A EMPRESA");
        map.put("name", list);
        list = new LinkedList<>();
        list.add("6");
        map.put("length", list);
    }
}