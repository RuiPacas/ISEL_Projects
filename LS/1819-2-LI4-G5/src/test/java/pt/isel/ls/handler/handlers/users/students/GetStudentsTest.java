package pt.isel.ls.handler.handlers.users.students;

import java.util.HashMap;
import java.util.LinkedList;
import pt.isel.ls.handler.CommandHandler;
import pt.isel.ls.handler.handlers.GetHandlersTest;
import pt.isel.ls.transactionmanager.TestTransactionManager;

public class GetStudentsTest extends GetHandlersTest {

    @Override
    protected void getValidReference(LinkedList<String> reference) {
        reference.add("Student name = Rita, email = rita@isel.pt, "
                + "Number = 41212, Programme = LEIC");
        reference.add("Student name = Joana, email = joana@isel.pt, "
                + "Number = 43333, Programme = LEIC");
        reference.add("Student name = Pedro, email = pedro@isel.pt, "
                + "Number = 44781, Programme = LEIC");
        reference.add("Student name = Diogo, email = diogo@isel.pt, "
                + "Number = 44815, Programme = LEIC");
        reference.add("Student name = Jéssica, email = jessica@isel.pt, "
                + "Number = 44820, Programme = LEIC");
    }

    @Override
    protected CommandHandler getHandler() {
        return new GetStudents(new TestTransactionManager<>(ds));
    }

    @Override
    protected void getValidMap(HashMap<String, LinkedList<String>> map) {
    }

    @Override
    protected void getInvalidMap(HashMap<String, LinkedList<String>> map) {
    }
}