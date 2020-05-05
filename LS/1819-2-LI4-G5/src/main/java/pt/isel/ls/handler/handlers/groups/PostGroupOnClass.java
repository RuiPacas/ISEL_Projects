package pt.isel.ls.handler.handlers.groups;

import static pt.isel.ls.handler.Parameters.AcrClass.ACR;
import static pt.isel.ls.handler.Parameters.Number.NUMBER;
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
import pt.isel.ls.result.groups.GetGroupsForAClassResult;
import pt.isel.ls.template.Template;
import pt.isel.ls.transactionmanager.TransactionManager;

public class PostGroupOnClass extends PostHandlerBase {

    private static final Logger logger = LoggerFactory.getLogger(PostGroupOnClass.class);
    private TransactionManager<Result> transactionManager;

    public PostGroupOnClass(
            TransactionManager<Result> transactionManager) {
        this.transactionManager = transactionManager;
    }

    @Override
    public Result executeCommand(HashMap<String, LinkedList<String>> values) {
        String[] acr = {null};
        String[] semester = {null};
        String[] classId = {null};
        try {
            acr[0] = AcrClass.getAndValidFrom(values.get(ACR).get(0));
            semester[0] = Sem.getAndValidFrom(values.get(SEM).get(0));
            classId[0] = AcrClass.getAndValidFrom(values.get(NUMBER).get(0));
            LinkedList<Integer> studentNumber = Number.getAndValidFrom(values.get(NUM_STU));

            return transactionManager.run(
                    connection -> {
                        for (int i = 0; i < studentNumber.size(); i++) {
                            if (!Queries
                                    .queryThatChecksIfThereIsACertainStudent(studentNumber.get(i),
                                            connection)) {
                                return new GetGroupsForAClassResult(
                                        Queries.getGroupsForAClass(acr[0], semester[0], classId[0],
                                                connection),
                                        Queries.getClassWithAcrSemAndId(acr[0], semester[0],
                                                classId[0],
                                                connection), "Student " + studentNumber.get(i)
                                        + " doesn't exist");
                            }
                            if (!Queries.queryThatChecksIfThereIsACertainStudentInAClass(acr[0],
                                    classId[0],
                                    semester[0], studentNumber.get(i), connection)) {
                                return new GetGroupsForAClassResult(
                                        Queries.getGroupsForAClass(acr[0], semester[0], classId[0],
                                                connection),
                                        Queries.getClassWithAcrSemAndId(acr[0], semester[0],
                                                classId[0],
                                                connection), "Student " + studentNumber.get(i)
                                        + " is not in the class " + classId[0] + " on semester "
                                        + semester[0]);
                            }
                            if (Queries.querytThatChecksIfTheStudentIsAlreadyInTheAGroup(acr[0],
                                    semester[0],
                                    classId[0], studentNumber.get(i), connection)) {
                                return new GetGroupsForAClassResult(
                                        Queries.getGroupsForAClass(acr[0], semester[0], classId[0],
                                                connection),
                                        Queries.getClassWithAcrSemAndId(acr[0], semester[0],
                                                classId[0],
                                                connection), "Student " + studentNumber.get(i)
                                        + " is already in a group");

                            }
                        }
                        int groupNumber = Queries
                                .queryThatReturnTheCorrectGroupNumber(acr[0], semester[0],
                                        classId[0],
                                        connection);

                        for (Integer i : studentNumber) {
                            if (Queries
                                    .postStudentsIntoAGroup(i, classId[0], semester[0], acr[0],
                                            groupNumber,
                                            connection) == 0) {
                                return new Message("Post didn't succeeded",
                                        HttpStatusCode.BadRequest);
                            }
                        }
                        return new Message("Post succeeded, Group Number created = " + groupNumber,
                                HttpStatusCode.SeeOther);
                    }
            );
        } catch (Exception e) {
            logger.error(e.getMessage());
            if (acr[0] != null && classId[0] != null && semester[0] != null) {
                return transactionManager.run(connection -> new GetGroupsForAClassResult(
                        Queries.getGroupsForAClass(acr[0], semester[0], classId[0], connection),
                        Queries.getClassWithAcrSemAndId(acr[0], semester[0], classId[0],
                                connection), e.getMessage()));
            }
            return new Message(e.getMessage(), HttpStatusCode.BadRequest);
        }
    }


    @Override
    public Template getTemplate() {
        return Template.of("/courses/{acr}/classes/{sem}/{num}/groups");
    }

    @Override
    public String description() {
        return "adds a new group to a class, given the following parameter"
                + " that can occur multiple times :"
                + "numStu - student number (must already be enrolled in the class."
                + "If successful, the command must return the created group number.";
    }
}
