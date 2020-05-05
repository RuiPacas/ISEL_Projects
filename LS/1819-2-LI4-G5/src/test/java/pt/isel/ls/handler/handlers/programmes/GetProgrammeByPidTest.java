package pt.isel.ls.handler.handlers.programmes;

import java.util.HashMap;
import java.util.LinkedList;
import pt.isel.ls.handler.CommandHandler;
import pt.isel.ls.handler.handlers.GetHandlersTest;
import pt.isel.ls.transactionmanager.TestTransactionManager;

public class GetProgrammeByPidTest extends GetHandlersTest {

    @Override
    protected void getValidReference(LinkedList<String> reference) {
        reference
                .add("Acronym = LEIC, Programme name = LICENCIATURA ENGENHARIA INFORMATICA "
                        + "E COMPUTADORES, Number of semesters = 6");
    }

    @Override
    protected CommandHandler getHandler() {
        return new GetProgrammeByPid(new TestTransactionManager<>(ds));
    }

    @Override
    protected void getValidMap(HashMap<String, LinkedList<String>> map) {
        LinkedList<String> list = new LinkedList<>();
        list.add("LEIC");
        map.put("pid", list);
    }

    @Override
    protected void getInvalidMap(HashMap<String, LinkedList<String>> map) {
        LinkedList<String> list = new LinkedList<>();
        list.add("LMATE");
        map.put("pid", list);
    }

    @Override
    protected int getExpectedNumberForInvalid() {
        return 1;
    }
}