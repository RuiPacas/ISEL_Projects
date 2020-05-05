package pt.isel.ls.handler.handlers.courses;

import java.util.HashMap;
import java.util.LinkedList;
import pt.isel.ls.handler.CommandHandler;
import pt.isel.ls.handler.handlers.GetHandlersTest;
import pt.isel.ls.transactionmanager.TestTransactionManager;

public class GetCoursesTest extends GetHandlersTest {

    @Override
    protected void getValidReference(LinkedList<String> reference) {
        reference
                .add("Course name = Laboratório de Software,"
                        + " Acronym = LS, Coordinator Teacher Number = 21");
        reference
                .add("Course name = Arquitetura de Computadores, "
                        + "Acronym = AC, Coordinator Teacher Number = 22");
        reference
                .add("Course name = Lógica e Sistemas Digitais,"
                        + " Acronym = LSD, Coordinator Teacher Number = 22");
    }

    @Override
    protected CommandHandler getHandler() {
        return new GetCourses(new TestTransactionManager<>(ds));
    }

    @Override
    protected void getValidMap(HashMap<String, LinkedList<String>> map) {
    }

    @Override
    protected void getInvalidMap(HashMap<String, LinkedList<String>> map) {
    }

}
