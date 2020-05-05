package pt.isel.ls.handler.handlers.users;

import java.util.HashMap;
import java.util.LinkedList;
import pt.isel.ls.handler.CommandHandler;
import pt.isel.ls.handler.handlers.GetHandlersTest;
import pt.isel.ls.transactionmanager.TestTransactionManager;

public class GetUsersTest extends GetHandlersTest {

    @Override
    protected void getValidReference(LinkedList<String> reference) {
        reference.add("User name = João, Email = joao@isel.pt");
        reference.add("User name = Pedro, Email = pedro@isel.pt");
        reference.add("User name = Zé Artur, Email = zeartur@isel.pt");
        reference.add("User name = Jéssica, Email = jessica@isel.pt");
        reference.add("User name = Diogo, Email = diogo@isel.pt");
        reference.add("User name = Rita, Email = rita@isel.pt");
        reference.add("User name = Joana, Email = joana@isel.pt");

    }

    @Override
    protected CommandHandler getHandler() {
        return new GetUsers(new TestTransactionManager<>(ds));
    }

    @Override
    protected void getValidMap(HashMap<String, LinkedList<String>> map) {
    }

    @Override
    protected void getInvalidMap(HashMap<String, LinkedList<String>> map) {
    }
}