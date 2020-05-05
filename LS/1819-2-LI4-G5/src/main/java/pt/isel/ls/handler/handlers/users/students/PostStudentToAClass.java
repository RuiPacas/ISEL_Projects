package pt.isel.ls.handler.handlers.users.students;

import static pt.isel.ls.handler.Parameters.AcrClass.ACR;
import static pt.isel.ls.handler.Parameters.AcrClass.CLASS_ID;
import static pt.isel.ls.handler.Parameters.Number.NUM_STU;
import static pt.isel.ls.handler.Parameters.Sem.SEM;

import java.util.HashMap;
import java.util.LinkedList;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pt.isel.ls.Queries;
import pt.isel.ls.handler.Parameters.AcrClass;
import pt.isel.ls.handler.Parameters.Number;
import pt.isel.ls.handler.Parameters.Sem;
import pt.isel.ls.handler.PostHandlerBase;
import pt.isel.ls.http.HttpStatusCode;
import pt.isel.ls.result.Message;
import pt.isel.ls.result.Result;
import pt.isel.ls.result.users.students.GetStudentsForAClassResult;
import pt.isel.ls.template.Template;
import pt.isel.ls.transactionmanager.TransactionManager;

public class PostStudentToAClass extends PostHandlerBase {

    private static final Logger logger = LoggerFactory.getLogger(PostStudentToAClass.class);
    private TransactionManager<Result> transactionManager;

    public PostStudentToAClass(TransactionManager<Result> transactionManager) {
        this.transactionManager = transactionManager;
    }

    @Override
    public Result executeCommand(HashMap<String, LinkedList<String>> values) {
        String[] acr = {null};
        String[] semester = {null};
        String[] classId = {null};
        try {
            classId[0] = AcrClass.getAndValidFrom(values.get(CLASS_ID).get(0));
            semester[0] = Sem.getAndValidFrom(values.get(SEM).get(0));
            acr[0] = AcrClass.getAndValidFrom(values.get(ACR).get(0));
            Integer number = Number.getAndValidFrom(values.get(NUM_STU).get(0));
            return transactionManager.run(
                    connection -> {
                        if (!Queries.queryThatChecksIfThereIsACertainStudent(number, connection)) {
                            return new GetStudentsForAClassResult(
                                    Queries.getStudentClassWithAcrSemAndId(acr[0], semester[0],
                                            classId[0],
                                            connection),
                                    Queries.getClassWithAcrSemAndId(acr[0], semester[0], classId[0],
                                            connection), "Student " + number + " doesn't exist");
                        }
                        if (Queries.queryThatChecksIfThereIsAStudentrInAClassInASemesterInACourse(
                                acr[0], classId[0], semester[0], number, connection)) {
                            return new GetStudentsForAClassResult(
                                    Queries.getStudentClassWithAcrSemAndId(acr[0], semester[0],
                                            classId[0],
                                            connection),
                                    Queries.getClassWithAcrSemAndId(acr[0], semester[0], classId[0],
                                            connection), "Student is already in this class");
                        }
                        if (Queries.postStudentToAClass(number, classId[0], semester[0], acr[0],
                                connection)
                                == 1) {
                            return new Message("Post succeeded", HttpStatusCode.SeeOther);
                        }
                        return new Message("Post didn't succeeded", HttpStatusCode.BadRequest);
                    }
            );
        } catch (Exception e) {
            logger.error(e.getMessage());
            if (acr[0] != null && semester[0] != null && classId[0] != null) {
                return transactionManager.run(connection -> new GetStudentsForAClassResult(
                        Queries.getStudentClassWithAcrSemAndId(acr[0], semester[0], classId[0],
                                connection),
                        Queries.getClassWithAcrSemAndId(acr[0], semester[0], classId[0],
                                connection), e.getMessage()));
            }
            return new Message(e.getMessage(), HttpStatusCode.BadRequest);
        }
    }

    @Override
    public Template getTemplate() {
        return Template.of("/courses/{acr}/classes/{sem}/{num}/students");
    }

    @Override
    public String description() {
        return "adds a new student to a class, given the following parameters: "
                + "numStu - student number.";
    }


}
