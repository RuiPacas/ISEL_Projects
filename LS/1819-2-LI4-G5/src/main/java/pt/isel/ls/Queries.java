package pt.isel.ls;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.LinkedList;
import pt.isel.ls.model.Class;
import pt.isel.ls.model.ClassTeacher;
import pt.isel.ls.model.Course;
import pt.isel.ls.model.Group;
import pt.isel.ls.model.Programme;
import pt.isel.ls.model.ProgrammeCourse;
import pt.isel.ls.model.ScGroup;
import pt.isel.ls.model.Student;
import pt.isel.ls.model.Teacher;
import pt.isel.ls.model.User;

public class Queries {


    public static LinkedList<Course> getCourses(HashMap<String, LinkedList<String>> values,
            Connection connection)
            throws SQLException {
        PreparedStatement ps = connection.prepareStatement("SELECT * FROM COURSE");
        ResultSet rs = ps.executeQuery();
        LinkedList<Course> courses = new LinkedList<>();
        while (rs.next()) {
            String name = rs.getString(1);
            String acronym = rs.getString(2);
            Integer courseNumber = rs.getInt(3);
            courses.add(new Course(name, acronym, courseNumber));
        }
        return courses;
    }

    public static int postCourse(String name, String acr, Integer teacherNumber,
            Connection connection) throws SQLException {
        PreparedStatement ps = connection
                .prepareStatement("INSERT INTO COURSE VALUES(?,?,?)");
        ps.setString(1, name);
        ps.setString(2, acr);
        ps.setInt(3, teacherNumber);
        return ps.executeUpdate();
    }

    public static LinkedList<Class> getClassesForACourse(String acr, Connection connection)
            throws SQLException {
        PreparedStatement ps = connection
                .prepareStatement("SELECT * FROM CLASS WHERE COURSE_ACRONYM = ?");
        ps.setString(1, acr);
        LinkedList<Class> classes = new LinkedList<>();
        ResultSet rs = ps.executeQuery();
        while (rs.next()) {
            String classId = rs.getString(1);
            String semesterRepresent = rs.getString(2);
            String courseAcronym = rs.getString(3);
            classes.add(new Class(classId, semesterRepresent, courseAcronym));
        }
        return classes;
    }

    public static Course getCoursesByAcr(String acronym, Connection connection,
            LinkedList<ProgrammeCourse> pc)
            throws SQLException {
        PreparedStatement ps = connection
                .prepareStatement("SELECT * FROM COURSE WHERE ACRONYM = ?");
        ps.setString(1, acronym);
        ResultSet rs = ps.executeQuery();
        Course course = null;
        if (rs.next()) {
            String courseName = rs.getString(1);
            String acr = rs.getString(2);
            Integer coordTeachNumb = rs.getInt(3);
            course = new Course(courseName, acr, coordTeachNumb);
            ps = connection.prepareStatement(
                    "SELECT PC.programme_id,PC.mandatory,PC.curricularsemester"
                            + " FROM PROGRAMME_COURSE as PC"
                            + " WHERE PC.courseacronym = ?");
            ps.setString(1, acr);
            rs = ps.executeQuery();
            while (rs.next()) {
                String programmeId = rs.getString(1);
                Boolean mandatory = rs.getBoolean(2);
                String curricularSemester = rs.getString(3);
                pc.add(new ProgrammeCourse(programmeId, acronym, mandatory,
                        curricularSemester));
            }
        }
        return course;
    }

    public static boolean queryThatCheckIfThereIsACourseWithAcr(String acronym,
            Connection connection)
            throws SQLException {
        PreparedStatement ps = connection
                .prepareStatement("SELECT * FROM COURSE WHERE ACRONYM = ?");
        ps.setString(1, acronym);
        return ps.executeQuery().next();
    }

    public static int postClassOnCourseWithAcr(String classId, String semester, String acr,
            Connection connection) throws SQLException {
        PreparedStatement ps = connection
                .prepareStatement("INSERT INTO CLASS VALUES(?,?,?)");
        ps.setString(1, classId);
        ps.setString(2, semester);
        ps.setString(3, acr);
        return ps.executeUpdate();
    }

    public static LinkedList<Programme> getAllProgrammes(Connection connection)
            throws SQLException {
        PreparedStatement ps = connection.prepareStatement("SELECT * FROM PROGRAMME");
        ResultSet rs = ps.executeQuery();
        LinkedList<Programme> programmes = new LinkedList<>();
        while (rs.next()) {
            String pid = rs.getString(1);
            String name = rs.getString(2);
            Integer semesterNumber = rs.getInt(3);
            programmes.add(new Programme(pid, name, semesterNumber));
        }
        return programmes;
    }

    public static int postAProgramme(String programmeId, String name,
            Integer numOfSemesters, Connection connection) throws SQLException {
        PreparedStatement ps = connection
                .prepareStatement("INSERT INTO PROGRAMME VALUES(?,?,?)");
        ps.setString(1, programmeId);
        ps.setString(2, name);
        ps.setInt(3, numOfSemesters);
        return ps.executeUpdate();
    }

    public static Programme getProgrammeByPid(String pid, Connection connection)
            throws SQLException {
        PreparedStatement ps = connection
                .prepareStatement("SELECT * FROM PROGRAMME WHERE PROGRAMME_ID = ?");
        ps.setString(1, pid);
        ResultSet rs = ps.executeQuery();
        Programme programme = null;
        if (rs.next()) {
            String programmeId = rs.getString(1);
            String name = rs.getString(2);
            Integer numberOfSemesters = rs.getInt(3);
            programme = new Programme(programmeId, name, numberOfSemesters);
        }
        return programme;
    }

    public static int postCourseWithPid(String pid, String acr, boolean optional,
            String curricularSemester, Connection connection) throws SQLException {
        PreparedStatement ps = connection
                .prepareStatement("INSERT INTO PROGRAMME_COURSE VALUES(?,?,?,?)");
        ps.setString(1, pid);
        ps.setString(2, acr);
        ps.setBoolean(3, optional);
        ps.setString(4, curricularSemester);
        return ps.executeUpdate();
    }

    public static LinkedList<ProgrammeCourse> getProgrammeCoursesByPid(String pid,
            Connection connection) throws SQLException {
        PreparedStatement ps = connection
                .prepareStatement("SELECT * FROM PROGRAMME_COURSE WHERE PROGRAMME_ID=?");
        ps.setString(1, pid);
        ResultSet rs = ps.executeQuery();
        LinkedList<ProgrammeCourse> programmeCourses = new LinkedList<>();
        while (rs.next()) {
            String programmeId = rs.getString(1);
            String courseAcronym = rs.getString(2);
            Boolean mandatory = rs.getBoolean(3);
            String curricularSemester = rs.getString(4);
            programmeCourses.add(new ProgrammeCourse(programmeId, courseAcronym, mandatory,
                    curricularSemester));
        }
        return programmeCourses;
    }

    public static LinkedList<Teacher> getTeachers(Connection connection) throws SQLException {

        PreparedStatement ps = connection
                .prepareStatement("SELECT U.UNAME,T.EMAIL,T.NUMBER "
                        + "FROM TEACHER AS T INNER JOIN USERS AS U "
                        + "ON U.EMAIL=T.EMAIL");
        ResultSet rs = ps.executeQuery();
        LinkedList<Teacher> teachers = new LinkedList<>();
        while (rs.next()) {
            String name = rs.getString(1);
            String email = rs.getString(2);
            Integer number = rs.getInt(3);
            teachers.add(new Teacher(name, email, number));
        }
        return teachers;
    }

    public static int createUser(String name, String email, Connection connection)
            throws SQLException {
        PreparedStatement ps = connection.prepareStatement("INSERT INTO USERS VALUES(?,?)");
        ps.setString(1, name);
        ps.setString(2, email);
        return ps.executeUpdate();
    }

    public static int postTeacher(String name, Integer number, String email, Connection connection)
            throws SQLException {
        createUser(name, email, connection);
        PreparedStatement ps = connection.prepareStatement("INSERT INTO TEACHER VALUES (?,?)");
        ps.setString(1, email);
        ps.setInt(2, number);
        return ps.executeUpdate();
    }

    public static LinkedList<Student> getStudents(Connection connection) throws SQLException {
        PreparedStatement ps = connection.prepareStatement(
                "SELECT U.UNAME,S.EMAIL,S.NUMBER,S.PROGRAMME_ID "
                        + "FROM STUDENT AS S INNER JOIN USERS AS U "
                        + "ON U.EMAIL=S.EMAIL "
                        + "ORDER BY S.NUMBER ");
        ResultSet rs = ps.executeQuery();
        LinkedList<Student> students = new LinkedList<>();
        while (rs.next()) {
            String name = rs.getString(1);
            String email = rs.getString(2);
            Integer number = rs.getInt(3);
            String pid = rs.getString(4);
            students.add(new Student(name, email, number, pid));
        }
        return students;
    }

    public static int postStudent(String name, String email, Integer number, String pid,
            Connection connection) throws SQLException {
        createUser(name, email, connection);
        PreparedStatement ps = connection.prepareStatement("INSERT INTO STUDENT VALUES (?,?,?)");
        ps.setString(1, email);
        ps.setInt(2, number);
        ps.setString(3, pid);
        return ps.executeUpdate();
    }

    public static Class getClassWithAcrSemAndId(String acr, String sem,
            String classId,
            Connection connection) throws SQLException {
        PreparedStatement ps = connection.prepareStatement(
                "SELECT * "
                        + "FROM CLASS AS C "
                        + "WHERE COURSE_ACRONYM=? AND SEMESTER_REPRESENT=? AND "
                        + "IDENTIFIER = ? "
                        + "ORDER BY COURSE_ACRONYM ");
        ps.setString(1, acr);
        ps.setString(2, sem);
        ps.setString(3, classId);
        ResultSet rs = ps.executeQuery();
        Class c = null;
        if (rs.next()) {
            c = new Class(classId, sem, acr);
        }
        return c;
    }

    public static LinkedList<Teacher> getTeachersWithAcrIdAndSem(String acr,
            String classId, String sem, Connection connection) throws SQLException {
        PreparedStatement ps = connection.prepareStatement(
                "SELECT U.UNAME,T.EMAIL,T.NUMBER "
                        + "FROM USERS AS U, TEACHER AS T , CLASS_TEACHER AS CT "
                        + "WHERE CT.TEACHERNUMBER=T.NUMBER AND T.EMAIL = U.EMAIL "
                        + "AND CT.C_ACRONYM=? AND CT.IDENTIFIERCLASS=? "
                        + "AND SEMESTER_REPRESENT=?"
                        + "ORDER BY T.NUMBER "
        );
        ps.setString(1, acr);
        ps.setString(2, classId);
        ps.setString(3, sem);
        ResultSet rs = ps.executeQuery();
        LinkedList<Teacher> teachers = new LinkedList<>();
        while (rs.next()) {
            String teacherName = rs.getString(1);
            String email = rs.getString(2);
            Integer number = (rs.getInt(3));
            teachers.add(new Teacher(teacherName, email, number));
        }
        return teachers;
    }

    public static int postIntoClassTeacher(Integer numDoc, String acr, String classId,
            String semester, Connection connection) throws SQLException {
        PreparedStatement ps = connection
                .prepareStatement("INSERT INTO CLASS_TEACHER VALUES(?,?,?,?)");
        ps.setInt(1, numDoc);
        ps.setString(2, acr);
        ps.setString(3, classId);
        ps.setString(4, semester);
        return ps.executeUpdate();
    }

    public static LinkedList<Student> getStudentClassWithAcrSemAndId(String acr, String semester,
            String classId, Connection connection) throws SQLException {
        PreparedStatement ps = connection.prepareStatement(
                "SELECT U.UNAME,U.EMAIL,SC.STUDENT_NUMBER,S.PROGRAMME_ID "
                        + "FROM STUDENT_CLASS AS SC "
                        + "JOIN STUDENT AS S ON SC.STUDENT_NUMBER = S.NUMBER  "
                        + "JOIN USERS AS U ON S.EMAIL = U.EMAIL "
                        + "WHERE COURSE_ACRONYM=? AND SEMESTER_REPRESENT=? AND "
                        + "CLASS_IDENTIFIER = ? "
                        + "ORDER BY SC.STUDENT_NUMBER ");
        ps.setString(1, acr);
        ps.setString(2, semester);
        ps.setString(3, classId);
        LinkedList<Student> students = new LinkedList<>();
        ResultSet rs = ps.executeQuery();
        while (rs.next()) {
            String name = rs.getString(1);
            String email = rs.getString(2);
            Integer studentNumber = rs.getInt(3);
            String pid = rs.getString(4);
            students.add(new Student(name, email, studentNumber, pid));
        }
        return students;
    }

    public static int postStudentToAClass(Integer number, String classId, String semester,
            String acr, Connection connection) throws SQLException {
        PreparedStatement ps = connection
                .prepareStatement("INSERT INTO STUDENT_CLASS VALUES(?,?,?,?)");
        ps.setInt(1, number);
        ps.setString(2, classId);
        ps.setString(3, semester);
        ps.setString(4, acr);
        return ps.executeUpdate();
    }

    public static LinkedList<Group> getGroupsForAClass(String acr, String semester, String classId,
            Connection connection) throws SQLException {
        PreparedStatement ps = connection
                .prepareStatement("SELECT DISTINCT SCG.GROUP_NUMBER "
                        + "FROM SC_GROUP AS SCG "
                        + "WHERE COURSE_ACRONYM= ? AND SEMESTER_REPRESENT= ? "
                        + "AND CLASS_IDENTIFIER= ? "
                        + "ORDER BY GROUP_NUMBER ");
        ps.setString(1, acr);
        ps.setString(2, semester);
        ps.setString(3, classId);
        LinkedList<Group> groups = new LinkedList<>();
        ResultSet rs = ps.executeQuery();
        while (rs.next()) {
            groups.add(new Group(rs.getInt(1)));
        }
        return groups;
    }

    public static int queryThatReturnTheCorrectGroupNumber(String acr, String semester,
            String classId, Connection connection) throws SQLException {
        PreparedStatement ps = connection.prepareStatement("SELECT G.GROUP_NUMBER "
                + "FROM _GROUP AS G EXCEPT SELECT DISTINCT SCG.GROUP_NUMBER "
                + "FROM SC_GROUP AS SCG "
                + "WHERE COURSE_ACRONYM= ? AND SEMESTER_REPRESENT= ? "
                + "AND CLASS_IDENTIFIER= ? "
                + "ORDER BY GROUP_NUMBER ");
        ps.setString(1, acr);
        ps.setString(2, semester);
        ps.setString(3, classId);
        ResultSet rs = ps.executeQuery();
        int count = 1;
        //Algorithm to determinate the next group number, by crescent order
        if (!rs.next()) {
            ps = connection.prepareStatement("SELECT * FROM _GROUP "
                    + "ORDER BY GROUP_NUMBER");
            rs = ps.executeQuery();
            while (rs.next()) {
                if (rs.getInt(1) != count++) {
                    count--;
                    break;
                }
            }
            ps = connection.prepareStatement("INSERT INTO _GROUP VALUES (?)");
            ps.setInt(1, count);
            ps.executeUpdate();
        } else {
            count = rs.getInt(1);
        }
        return count;
    }

    public static int postStudentsIntoAGroup(Integer i, String classId, String semester, String acr,
            int groupNumber, Connection connection) throws SQLException {
        PreparedStatement ps = connection
                .prepareStatement("INSERT INTO SC_GROUP VALUES(?,?,?,?,?)");
        ps.setInt(1, i);
        ps.setString(2, classId);
        ps.setString(3, semester);
        ps.setString(4, acr);
        ps.setInt(5, groupNumber);
        return ps.executeUpdate();
    }

    public static LinkedList<Student> getStudentClassWithAcrSemAndIdAndGroupNr(String acr,
            String semester, String classId, Integer groupNumber, Connection connection)
            throws SQLException {
        PreparedStatement ps = connection
                .prepareStatement("SELECT U.UNAME, S.NUMBER, "
                        + "S.PROGRAMME_ID, S.EMAIL "
                        + "FROM STUDENT AS S "
                        + "JOIN USERS AS U ON S.EMAIL = U.EMAIL "
                        + "JOIN SC_GROUP AS SC ON S.NUMBER = SC.STUDENT_NUMBER "
                        + "WHERE SC.COURSE_ACRONYM = ? AND SC.SEMESTER_REPRESENT =? AND "
                        + "SC.CLASS_IDENTIFIER = ? AND SC.GROUP_NUMBER = ? "
                        + "ORDER BY S.NUMBER ");
        ps.setString(1, acr);
        ps.setString(2, semester);
        ps.setString(3, classId);
        ps.setInt(4, groupNumber);
        LinkedList<Student> students = new LinkedList<>();
        ResultSet rs = ps.executeQuery();
        while (rs.next()) {
            String name = rs.getString(1);
            Integer studentNumber = rs.getInt(2);
            String pid = rs.getString(3);
            String email = rs.getString(4);
            students.add(new Student(name, email, studentNumber, pid));
        }
        return students;
    }

    public static boolean checkIfGroupNumberExistsInAClass(String acr, String semester,
            String classId, Integer groupNumber, Connection connection) throws SQLException {

        PreparedStatement ps = connection.prepareStatement("SELECT * "
                + "FROM SC_GROUP AS SC "
                + "WHERE CLASS_IDENTIFIER=? AND SEMESTER_REPRESENT =? AND COURSE_ACRONYM=? AND "
                + "GROUP_NUMBER=? ");
        ps.setString(1, classId);
        ps.setString(2, semester);
        ps.setString(3, acr);
        ps.setInt(4, groupNumber);
        return ps.executeQuery().next();
    }

    public static LinkedList<Class> getClassesWithAcrAndSem(String acr, String semester,
            Connection connection) throws SQLException {
        PreparedStatement ps = connection.prepareStatement(
                "SELECT * FROM CLASS WHERE COURSE_ACRONYM = ? "
                        + " AND SEMESTER_REPRESENT=? ");
        ps.setString(1, acr);
        ps.setString(2, semester);
        ResultSet rs = ps.executeQuery();
        LinkedList<Class> classes = new LinkedList<>();
        while (rs.next()) {
            String classIdentifier = rs.getString(1);
            String semesterRepresent = rs.getString(2);
            String courseAcronym = rs.getString(3);
            classes.add(new Class(classIdentifier, semesterRepresent, courseAcronym));
        }
        return classes;
    }

    public static LinkedList<ClassTeacher> getClassTeachersWithTeacherNumber(Integer numDoc,
            Connection connection) throws SQLException {
        PreparedStatement ps = connection.prepareStatement(
                "SELECT C_ACRONYM,IDENTIFIERCLASS,SEMESTER_REPRESENT "
                        + " FROM CLASS_TEACHER "
                        + "WHERE TEACHERNUMBER= ?  ");
        ps.setInt(1, numDoc);
        LinkedList<ClassTeacher> classTeachers = new LinkedList<>();
        ClassTeacher ct;
        ResultSet rs = ps.executeQuery();
        while (rs.next()) {
            String courseAcronym = rs.getString(1);
            String classIdentifier = rs.getString(2);
            String semesterRepresent = rs.getString(3);
            ct = new ClassTeacher(numDoc, courseAcronym, classIdentifier,
                    semesterRepresent);
            classTeachers.add(ct);
        }
        return classTeachers;
    }

    public static boolean queryThatChecksIfThereIsACertainTeacher(Integer numDoc,
            Connection connection) throws SQLException {
        PreparedStatement ps = connection.prepareStatement("SELECT * FROM TEACHER "
                + "WHERE NUMBER=? ");
        ps.setInt(1, numDoc);
        return ps.executeQuery().next();
    }

    public static int deleteStudentFromGroup(Integer studentNumber, String classId, String semester,
            String acr, Integer groupNumber, Connection connection) throws SQLException {
        PreparedStatement ps = connection.prepareStatement("DELETE FROM SC_GROUP "
                + "WHERE STUDENT_NUMBER = ? AND CLASS_IDENTIFIER = ? AND "
                + "SEMESTER_REPRESENT = ? AND COURSE_ACRONYM = ? "
                + "AND GROUP_NUMBER = ? ");
        ps.setInt(1, studentNumber);
        ps.setString(2, classId);
        ps.setString(3, semester);
        ps.setString(4, acr);
        ps.setInt(5, groupNumber);
        return ps.executeUpdate();
    }

    public static Student getStudentWithNumber(Integer number, Connection connection)
            throws SQLException {
        Student student = null;
        PreparedStatement ps = connection
                .prepareStatement("SELECT U.UNAME,S.EMAIL,S.NUMBER,S.PROGRAMME_ID "
                        + "FROM STUDENT AS S INNER JOIN USERS AS U "
                        + "ON U.EMAIL=S.EMAIL "
                        + "WHERE NUMBER = ? "
                        + "ORDER BY S.NUMBER ");
        ps.setInt(1, number);
        ResultSet rs = ps.executeQuery();
        if (rs.next()) {
            String studentName = rs.getString(1);
            String studentEmail = rs.getString(2);
            Integer studentNumber = rs.getInt(3);
            String studentProgramme = rs.getString(4);
            student = new Student(studentName, studentEmail, studentNumber,
                    studentProgramme);
        }
        return student;
    }

    public static LinkedList<ScGroup> getScGroups(Integer number, Connection connection)
            throws SQLException {
        PreparedStatement ps = connection.prepareStatement(
                " SELECT SC.CLASS_IDENTIFIER,SC.SEMESTER_REPRESENT,"
                        + " SC.COURSE_ACRONYM,SC.GROUP_NUMBER "
                        + " FROM SC_GROUP AS SC "
                        + " WHERE STUDENT_NUMBER = ? ");
        ps.setInt(1, number);
        ResultSet rs = ps.executeQuery();
        LinkedList<ScGroup> scGroups = new LinkedList<>();
        while (rs.next()) {
            String classIdentifier = rs.getString(1);
            String semesterRepresent = rs.getString(2);
            String courseAcronym = rs.getString(3);
            Integer group = rs.getInt(4);
            scGroups.add(
                    new ScGroup(number, classIdentifier, semesterRepresent,
                            courseAcronym, group));
        }
        return scGroups;
    }

    public static boolean putStudent(String newName, String newEmail,
            Integer newNumber, Integer actualNumber,
            String programme, Connection connection) throws SQLException {
        PreparedStatement ps = connection
                .prepareStatement("SELECT S.EMAIL FROM STUDENT AS S "
                        + " WHERE NUMBER=?");
        ps.setInt(1, actualNumber);
        ResultSet rs = ps.executeQuery();
        if (rs.next()) {
            ps = connection.prepareStatement(
                    "UPDATE USERS SET EMAIL = ?, UNAME = ? WHERE EMAIL = ? ");
            ps.setString(1, newEmail);
            ps.setString(2, newName);
            ps.setString(3, rs.getString(1));
            if (ps.executeUpdate() == 1) {
                ps = connection.prepareStatement(
                        "UPDATE STUDENT SET NUMBER = ?,PROGRAMME_ID =? WHERE EMAIL = ? ");
                ps.setInt(1, newNumber);
                ps.setString(2, programme);
                ps.setString(3, newEmail);
                if (ps.executeUpdate() == 1) {
                    ps = connection.prepareStatement(
                            "UPDATE STUDENT SET NUMBER = ?,PROGRAMME_ID =? WHERE EMAIL = ? ");
                    ps.setInt(1, newNumber);
                    ps.setString(2, programme);
                    ps.setString(3, newEmail);
                    return ps.executeUpdate() == 1;
                }
            }
        }
        return false;
    }

    public static Teacher getTeacherWithNumber(Integer number, Connection connection)
            throws SQLException {
        Teacher teacher = null;
        PreparedStatement ps = connection
                .prepareStatement("SELECT U.UNAME,T.EMAIL,T.NUMBER "
                        + " FROM TEACHER AS T INNER JOIN USERS AS U "
                        + " ON U.EMAIL=T.EMAIL "
                        + " WHERE NUMBER  = ? "
                        + "ORDER BY T.NUMBER ");
        ps.setInt(1, number);
        ResultSet rs = ps.executeQuery();
        if (rs.next()) {
            String teacherName = rs.getString(1);
            String teacherEmail = rs.getString(2);
            Integer teacherNumber = rs.getInt(3);
            teacher = new Teacher(teacherName, teacherEmail, teacherNumber);
        }
        return teacher;

    }

    public static LinkedList<String> getCoursesAcronymWithCoordNumber(Integer number,
            Connection connection) throws SQLException {
        PreparedStatement ps = connection.prepareStatement(" SELECT DISTINCT C.ACRONYM "
                + " FROM COURSE AS C "
                + " WHERE C.COORDINATORNUMBER=? ");
        ps.setInt(1, number);
        LinkedList<String> coursesAcronyms = new LinkedList<>();
        ResultSet rs = ps.executeQuery();
        while (rs.next()) {
            String acr = rs.getString(1);
            coursesAcronyms.add(acr);
        }
        return coursesAcronyms;
    }

    public static boolean putTeacher(String name, String newEmail, Integer actualNumber,
            Integer newNumber, Connection connection) throws SQLException {
        PreparedStatement ps = connection
                .prepareStatement("SELECT T.EMAIL FROM TEACHER AS T "
                        + " WHERE NUMBER=? ");
        ps.setInt(1, actualNumber);
        ResultSet rs = ps.executeQuery();
        if (rs.next()) {
            ps = connection.prepareStatement(
                    "UPDATE USERS SET EMAIL = ?, UNAME = ? WHERE EMAIL = ? ");
            ps.setString(1, newEmail);
            ps.setString(2, name);
            ps.setString(3, rs.getString(1));
            if (ps.executeUpdate() == 1) {
                ps = connection
                        .prepareStatement("UPDATE TEACHER SET NUMBER = ? WHERE EMAIL = ? ");
                ps.setInt(1, newNumber);
                ps.setString(2, newEmail);
                return ps.executeUpdate() == 1;

            }
        }
        return false;
    }

    public static boolean queryThatCheckIfThereIsACourseWithAcrInProgramme(String pid, String acr,
            Connection connection) throws SQLException {
        PreparedStatement ps = connection
                .prepareStatement("SELECT * FROM PROGRAMME_COURSE WHERE PROGRAMME_ID=? "
                        + "AND COURSEACRONYM = ? ");
        ps.setString(1, pid);
        ps.setString(2, acr);
        return ps.executeQuery().next();
    }

    public static boolean queryThatChecksIfEmailIsInUse(String email, Connection connection)
            throws SQLException {
        PreparedStatement ps = connection
                .prepareStatement("SELECT * FROM USERS WHERE EMAIL=? ");
        ps.setString(1, email);
        return ps.executeQuery().next();
    }

    public static boolean queryThatChecksIfThereIsACertainStudent(Integer number,
            Connection connection) throws SQLException {
        PreparedStatement ps = connection.prepareStatement("SELECT * FROM STUDENT "
                + "WHERE NUMBER=? ");
        ps.setInt(1, number);
        return ps.executeQuery().next();
    }

    public static boolean queryThatChecksIfThereIsATeacherInACourseWithSem(String acr,
            String classId, String semester, Integer numDoc, Connection connection)
            throws SQLException {
        PreparedStatement ps = connection.prepareStatement("SELECT * FROM CLASS_TEACHER "
                + " WHERE TEACHERNUMBER=? AND C_ACRONYM=? AND "
                + "IDENTIFIERCLASS=? AND SEMESTER_REPRESENT=?");
        ps.setInt(1, numDoc);
        ps.setString(2, acr);
        ps.setString(3, classId);
        ps.setString(4, semester);
        return ps.executeQuery().next();
    }

    public static boolean queryThatChecksIfThereIsAStudentrInAClassInASemesterInACourse(
            String acronym,
            String classId, String semester, Integer numberStu, Connection connection)
            throws SQLException {
        PreparedStatement ps = connection.prepareStatement("SELECT * FROM STUDENT_CLASS WHERE "
                + " STUDENT_NUMBER=? AND CLASS_IDENTIFIER=? AND"
                + " SEMESTER_REPRESENT=? AND COURSE_ACRONYM=?");
        ps.setInt(1, numberStu);
        ps.setString(2, classId);
        ps.setString(3, semester);
        ps.setString(4, acronym);
        return ps.executeQuery().next();
    }

    public static boolean querytThatChecksIfTheStudentIsAlreadyInTheAGroup(String acr,
            String semester,
            String classId, Integer studentNumber, Connection connection)
            throws SQLException {
        PreparedStatement ps = connection.prepareStatement("SELECT * FROM SC_GROUP WHERE "
                + " COURSE_ACRONYM = ? AND CLASS_IDENTIFIER = ? "
                + "AND SEMESTER_REPRESENT = ? AND STUDENT_NUMBER = ?");
        ps.setString(1, acr);
        ps.setString(2, classId);
        ps.setString(3, semester);
        ps.setInt(4, studentNumber);
        return ps.executeQuery().next();
    }

    public static boolean queryThatChecksIfThereIsACertainStudentInAClass(String acr,
            String classId, String semester, Integer studentNumber, Connection connection)
            throws SQLException {
        PreparedStatement ps = connection.prepareStatement("SELECT * FROM STUDENT_CLASS WHERE "
                + " COURSE_ACRONYM = ? AND CLASS_IDENTIFIER = ? "
                + "AND SEMESTER_REPRESENT = ? AND STUDENT_NUMBER = ?");
        ps.setString(1, acr);
        ps.setString(2, classId);
        ps.setString(3, semester);
        ps.setInt(4, studentNumber);
        return ps.executeQuery().next();
    }

    public static LinkedList<User> getUsers(Connection connection) throws SQLException {
        PreparedStatement ps = connection.prepareStatement("SELECT * FROM USERS");
        ResultSet rs = ps.executeQuery();
        LinkedList<User> users = new LinkedList<>();
        while (rs.next()) {
            String name = rs.getString(1);
            String email = rs.getString(2);
            users.add(new User(name, email));
        }
        return users;
    }
}
