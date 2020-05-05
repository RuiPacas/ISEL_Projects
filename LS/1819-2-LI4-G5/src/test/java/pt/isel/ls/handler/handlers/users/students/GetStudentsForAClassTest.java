package pt.isel.ls.handler.handlers.users.students;

import java.util.HashMap;
import java.util.LinkedList;
import pt.isel.ls.handler.CommandHandler;
import pt.isel.ls.handler.handlers.GetHandlersTest;
import pt.isel.ls.transactionmanager.TestTransactionManager;


public class GetStudentsForAClassTest extends GetHandlersTest {


    @Override
    protected void getValidReference(LinkedList<String> reference) {
        reference
                .add("Student name = Joana, email = joana@isel.pt, Number = 43333,"
                        + " Programme = LEIC, Class Identifier = 41D, "
                        + "Semester Represent = 1718W, Course Acronym = LS");
        reference
                .add("Student name = Pedro, email = pedro@isel.pt, Number = 44781,"
                        + " Programme = LEIC, Class Identifier = 41D, "
                        + "Semester Represent = 1718W, Course Acronym = LS");
        reference
                .add("Student name = Diogo, email = diogo@isel.pt, Number = 44815,"
                        + " Programme = LEIC, Class Identifier = 41D, "
                        + "Semester Represent = 1718W, Course Acronym = LS");
        reference
                .add("Student name = Jéssica, email = jessica@isel.pt, Number = 44820,"
                        + " Programme = LEIC, Class Identifier = 41D, "
                        + "Semester Represent = 1718W, Course Acronym = LS");


    }

    @Override
    protected CommandHandler getHandler() {
        return new GetStudentsForAClass(new TestTransactionManager<>(ds));
    }

    @Override
    protected void getValidMap(HashMap<String, LinkedList<String>> map) {
        LinkedList<String> list = new LinkedList<>();
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
        list.add("41D");
        map.put("num", list);
        list = new LinkedList<>();
        list.add("1718w");
        map.put("sem", list);
        list = new LinkedList<>();
        list.add("rcp");
        map.put("acr", list);
    }
}