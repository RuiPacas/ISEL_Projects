package pt.isel.ls.handler.handlers.users.teachers;

import java.util.HashMap;
import java.util.LinkedList;
import pt.isel.ls.handler.CommandHandler;
import pt.isel.ls.handler.handlers.GetHandlersTest;
import pt.isel.ls.transactionmanager.TestTransactionManager;

public class GetTeachersTest extends GetHandlersTest {

    @Override
    protected void getValidReference(LinkedList<String> reference) {
        reference.add("Teacher name = João, email = joao@isel.pt, Number = 21");
        reference.add("Teacher name = Zé Artur, email = zeartur@isel.pt, Number = 22");
    }

    @Override
    protected CommandHandler getHandler() {
        return new GetTeachers(new TestTransactionManager<>(ds));
    }

    @Override
    protected void getValidMap(HashMap<String, LinkedList<String>> map) {
    }

    @Override
    protected void getInvalidMap(HashMap<String, LinkedList<String>> map) {
    }
}