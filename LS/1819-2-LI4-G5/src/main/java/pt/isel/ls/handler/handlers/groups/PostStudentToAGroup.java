package pt.isel.ls.handler.handlers.groups;

import static pt.isel.ls.handler.Parameters.AcrClass.ACR;
import static pt.isel.ls.handler.Parameters.AcrClass.CLASS_ID;
import static pt.isel.ls.handler.Parameters.Number.GROUP_NUMBER;
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
import pt.isel.ls.model.Group;
import pt.isel.ls.result.Message;
import pt.isel.ls.result.Result;
import pt.isel.ls.result.groups.GetGroupWithNumberResult;
import pt.isel.ls.template.Template;
import pt.isel.ls.transactionmanager.TransactionManager;

public class PostStudentToAGroup extends PostHandlerBase {

    private static final Logger logger = LoggerFactory.getLogger(PostStudentToAGroup.class);
    private TransactionManager<Result> transactionManager;

    public PostStudentToAGroup(TransactionManager<Result> transactionManager) {
        this.transactionManager = transactionManager;
    }

    @Override
    public Result executeCommand(HashMap<String, LinkedList<String>> values) {
        String[] acr = {null};
        String[] semester = {null};
        String[] classId = {null};
        Integer[] groupNumber = {null};
        try {
            acr[0] = AcrClass.getAndValidFrom(values.get(ACR).get(0));
            semester[0] = Sem.getAndValidFrom(values.get(SEM).get(0));
            classId[0] = AcrClass.getAndValidFrom(values.get(CLASS_ID).get(0));
            groupNumber[0] = Number.getAndValidFrom(values.get(GROUP_NUMBER).get(0));
            LinkedList<Integer> studentNumbers = Number.getAndValidFrom(values.get(NUM_STU));
            return transactionManager.run(connection -> {
                for (Integer studentNumber : studentNumbers) {
                    if (!Queries
                            .queryThatChecksIfThereIsACertainStudent(studentNumber, connection)) {
                        return new GetGroupWithNumberResult(
                                Queries.getStudentClassWithAcrSemAndIdAndGroupNr(acr[0],
                                        semester[0], classId[0], groupNumber[0], connection),
                                Queries.getClassWithAcrSemAndId(acr[0], semester[0], classId[0],
                                        connection), new Group(groupNumber[0]),
                                "Student " + studentNumber + " doesn't exist");
                    }
                    if (!Queries.queryThatChecksIfThereIsACertainStudentInAClass(acr[0], classId[0],
                            semester[0], studentNumber, connection)) {
                        return new GetGroupWithNumberResult(
                                Queries.getStudentClassWithAcrSemAndIdAndGroupNr(acr[0],
                                        semester[0], classId[0], groupNumber[0], connection),
                                Queries.getClassWithAcrSemAndId(acr[0], semester[0], classId[0],
                                        connection), new Group(groupNumber[0]),
                                "Student " + studentNumber
                                        + " is not in the class " + classId[0] + " on semester "
                                        + semester[0]);
                    }
                    if (Queries
                            .querytThatChecksIfTheStudentIsAlreadyInTheAGroup(acr[0], semester[0],
                                    classId[0], studentNumber, connection)) {
                        return new GetGroupWithNumberResult(
                                Queries.getStudentClassWithAcrSemAndIdAndGroupNr(acr[0],
                                        semester[0], classId[0], groupNumber[0], connection),
                                Queries.getClassWithAcrSemAndId(acr[0], semester[0], classId[0],
                                        connection), new Group(groupNumber[0]),
                                "Student " + studentNumber
                                        + " is already in a group");

                    }
                    if (Queries
                            .postStudentsIntoAGroup(studentNumber, classId[0], semester[0], acr[0],
                                    groupNumber[0],
                                    connection) == 0) {
                        return new Message("Post didn't succeeded", HttpStatusCode.BadRequest);
                    }
                }
                return new Message("Post succeeded", HttpStatusCode.SeeOther);
            });
        } catch (Exception e) {
            logger.error(e.getMessage());
            if (acr[0] != null && semester[0] != null && classId[0] != null
                    && groupNumber[0] != null) {
                return transactionManager.run(
                        connection -> new GetGroupWithNumberResult(
                                Queries.getStudentClassWithAcrSemAndIdAndGroupNr(acr[0],
                                        semester[0], classId[0], groupNumber[0], connection),
                                Queries.getClassWithAcrSemAndId(acr[0], semester[0], classId[0],
                                        connection), new Group(groupNumber[0]), e.getMessage()));
            }
            return new Message(e.getMessage(), HttpStatusCode.BadRequest);
        }
    }

    @Override
    public Template getTemplate() {
        return Template.of("/courses/{acr}/classes/{sem}/{num}/groups/{gno}");
    }

    @Override
    public String description() {
        return "adds a new student to a group, given the following "
                + "parameter that can occur multiple times : "
                + "numStu - student number.";
    }
}
