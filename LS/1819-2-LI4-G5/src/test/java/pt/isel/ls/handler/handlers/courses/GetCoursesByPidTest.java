package pt.isel.ls.handler.handlers.courses;

import java.util.HashMap;
import java.util.LinkedList;
import pt.isel.ls.handler.CommandHandler;
import pt.isel.ls.handler.handlers.GetHandlersTest;
import pt.isel.ls.transactionmanager.TestTransactionManager;

public class GetCoursesByPidTest extends GetHandlersTest {

    @Override
    protected void getValidReference(LinkedList<String> reference) {
        reference
                .add("Programme Id = LEIC, Course Acronym = LS,"
                        + " Mandatory = true, Curricular Semester = 4");
    }

    @Override
    protected CommandHandler getHandler() {
        return new GetCoursesByPid(new TestTransactionManager<>(ds));
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


}