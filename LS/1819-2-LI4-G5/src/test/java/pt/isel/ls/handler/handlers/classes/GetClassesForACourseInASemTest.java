package pt.isel.ls.handler.handlers.classes;

import java.util.HashMap;
import java.util.LinkedList;
import pt.isel.ls.handler.CommandHandler;
import pt.isel.ls.handler.handlers.GetHandlersTest;
import pt.isel.ls.transactionmanager.TestTransactionManager;

public class GetClassesForACourseInASemTest extends GetHandlersTest {

    @Override
    protected void getValidReference(LinkedList<String> reference) {
        reference.add("Class Identifier = 41D, Semester Represent = 1718W, Course Acronym = LS");
    }

    @Override
    protected CommandHandler getHandler() {
        return new GetClassesForACourseInASem(new TestTransactionManager<>(ds));
    }

    @Override
    protected void getValidMap(HashMap<String, LinkedList<String>> map) {
        LinkedList<String> list = new LinkedList<>();
        list.add("ls");
        map.put("acr", list);
        list = new LinkedList<>();
        list.add("1718w");
        map.put("sem", list);
    }

    @Override
    protected void getInvalidMap(HashMap<String, LinkedList<String>> map) {
        LinkedList<String> list = new LinkedList<>();
        list.add("ls");
        map.put("acr", list);
        list = new LinkedList<>();
        list.add("1617w");
        map.put("sem", list);
    }

}