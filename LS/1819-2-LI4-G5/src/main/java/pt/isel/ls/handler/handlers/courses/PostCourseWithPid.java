package pt.isel.ls.handler.handlers.courses;

import static pt.isel.ls.handler.Parameters.AcrClass.ACR;
import static pt.isel.ls.handler.Parameters.CurricularSemesters.CURRICULAR_SEMESTERS;
import static pt.isel.ls.handler.Parameters.Optional.OPTIONAL;
import static pt.isel.ls.handler.Parameters.Programme.PROGRAMME_ID;

import java.util.HashMap;
import java.util.LinkedList;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pt.isel.ls.Queries;
import pt.isel.ls.handler.Parameters.AcrClass;
import pt.isel.ls.handler.Parameters.CurricularSemesters;
import pt.isel.ls.handler.Parameters.Optional;
import pt.isel.ls.handler.PostHandlerBase;
import pt.isel.ls.http.HttpStatusCode;
import pt.isel.ls.model.Programme;
import pt.isel.ls.model.ProgrammeCourse;
import pt.isel.ls.result.Message;
import pt.isel.ls.result.Result;
import pt.isel.ls.result.courses.GetCoursesByPidResult;
import pt.isel.ls.template.Template;
import pt.isel.ls.transactionmanager.TransactionManager;

public class PostCourseWithPid extends PostHandlerBase {

    private static final Logger logger = LoggerFactory.getLogger(PostCourseWithPid.class);
    private TransactionManager<Result> transactionManager;

    public PostCourseWithPid(TransactionManager<Result> transactionManager) {
        this.transactionManager = transactionManager;
    }


    @Override
    public Result executeCommand(HashMap<String, LinkedList<String>> values) {
        String[] pid = {null};
        try {
            pid[0] = pt.isel.ls.handler.Parameters.Programme
                    .getAndValidFrom(values.get(PROGRAMME_ID).get(0));
            String acr = AcrClass.getAndValidFrom(values.get(ACR).get(0));
            boolean optional = Optional.getAndValidFrom(values.get(OPTIONAL).get(0));
            String curricularSemester = CurricularSemesters
                    .getAndValidFrom(values.get(CURRICULAR_SEMESTERS).get(0));
            return transactionManager.run(connection -> {
                Programme programme = Queries.getProgrammeByPid(pid[0], connection);
                LinkedList<ProgrammeCourse> programmeCoursesByPid = Queries
                        .getProgrammeCoursesByPid(pid[0], connection);
                if (programme == null) {
                    return new GetCoursesByPidResult(programmeCoursesByPid,
                            pid[0], "No such Programme");
                }
                if (!checkIfCurricularSemesterIsValid(curricularSemester, programme)) {
                    return new GetCoursesByPidResult(programmeCoursesByPid,
                            pid[0], "Invalid curricular semester");
                }
                if (!Queries.queryThatCheckIfThereIsACourseWithAcr(acr, connection)) {
                    return new GetCoursesByPidResult(programmeCoursesByPid,
                            pid[0], "No such course");
                }
                if (Queries.queryThatCheckIfThereIsACourseWithAcrInProgramme(pid[0], acr,
                        connection)) {
                    return new GetCoursesByPidResult(programmeCoursesByPid,
                            pid[0], "Course is already in " + pid[0]);
                }

                if (Queries.postCourseWithPid(pid[0], acr, optional, curricularSemester, connection)
                        == 1) {
                    return new Message("Post succeeded", HttpStatusCode.SeeOther);
                }
                return new Message("Post didn't succeeded", HttpStatusCode.BadRequest);
            });
        } catch (Exception e) {
            logger.error(e.getMessage());
            if (pid[0] == null) {
                return new Message(e.getMessage(), HttpStatusCode.BadRequest);
            }
            return transactionManager.run(connection -> {
                        LinkedList<ProgrammeCourse> programmeCoursesByPid = Queries
                                .getProgrammeCoursesByPid(pid[0], connection);
                        return new GetCoursesByPidResult(programmeCoursesByPid,
                                pid[0], e.getMessage());
                    }
            );
        }
    }

    private boolean checkIfCurricularSemesterIsValid(String curricularSemester, Programme p) {
        String[] semesters = curricularSemester.split(",");
        for (String s : semesters) {
            if (Integer.valueOf(s) > p.getNumberOfSemesters()) {
                return false;
            }
        }
        return true;
    }

    @Override
    public Template getTemplate() {
        return Template.of("/programmes/{pid}/courses");
    }

    @Override
    public String description() {
        return "adds a new course to the programme pid, given the following parameters: "
                + "acr - the course acronym, "
                + "mandatory - true if the course is mandatory, "
                + "semesters - comma separated list of curricular semesters.";
    }
}
