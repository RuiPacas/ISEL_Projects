package pt.isel.ls.handler;

import java.util.LinkedList;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pt.isel.ls.exception.ArgumentException;

public class Parameters {

    private static final Logger logger = LoggerFactory.getLogger(Parameters.class);

    public static final class AcrClass {

        public static final String ACR = "acr";
        public static final String CLASS_ID = "num";

        public static String getAndValidFrom(String value) {
            if (value == null) {
                String message = "Missing a parameter - Please check your command";
                logger.error(message);
                throw new ArgumentException(message);
            }
            if (value.length() <= 5) {
                return value.toUpperCase();
            }
            String message = "Parameter " + value + " is invalid";
            logger.error(message);
            throw new ArgumentException(message);
        }
    }

    public static final class Number {

        public static final String PORT = "port";
        public static final String NUMBER = "num";
        public static final String TEACHER = "teacher";
        public static final String NUMBER_OF_SEMESTERS = "length";
        public static final String NUMDOC = "numDoc";
        public static final String NUM_STU = "numStu";
        public static final String GROUP_NUMBER = "gno";

        public static Integer getAndValidFrom(String number) {
            if (number == null) {
                String message = "Missing a parameter - Please check your command";
                logger.error(message);
                throw new ArgumentException(message);
            }
            if (isValidNumber(number)) {
                return Integer.parseInt(number);
            }
            String message = "Parameter " + number + " is invalid";
            logger.error(message);
            throw new ArgumentException(message);
        }

        public static LinkedList<Integer> getAndValidFrom(LinkedList<String> number) {
            if (number == null) {
                String message = "Missing a parameter - Please check your command";
                logger.error(message);
                throw new ArgumentException(message);
            }
            LinkedList<Integer> toReturn = new LinkedList<>();
            for (String n : number) {
                if (n.equals("")) {
                    continue;
                }
                if (!isValidNumber(n)) {
                    String message = "Parameter " + n + " is invalid";
                    logger.error(message);
                    throw new ArgumentException(message);
                }
                toReturn.add(Integer.parseInt(n));
            }
            return toReturn;
        }

        private static boolean isValidNumber(String number) {
            return number.chars().allMatch(Character::isDigit);
        }

    }

    public static final class Sem {

        public static final String SEM = "sem";

        public static String getAndValidFrom(String sem) {
            if (sem == null) {
                String message = "Missing a parameter - Please check your command";
                logger.error(message);
                throw new ArgumentException(message);
            }
            if (sem == null || sem.length() != 5) {
                String message = "Parameter " + sem + " is invalid";
                logger.error(message);
                throw new ArgumentException(message);
            }
            int firstYear = Integer.parseInt(sem.substring(0, 2));
            int secondYear = Integer.parseInt(sem.substring(2, 4));
            char c = sem.charAt(4);
            if (secondYear - firstYear == 1 && ((c == 'S' || c == 'W') || (c == 's' || c == 'w'))) {
                return sem.toUpperCase();
            }
            String message = "Parameter " + sem + " is invalid";
            logger.error(message);
            throw new ArgumentException(message);
        }
    }

    public static final class Name {

        public static final String NAME = "name";

        public static String getAndValidFrom(String name) {
            if (name == null) {
                String message = "Missing a parameter - Please check your command";
                logger.error(message);
                throw new ArgumentException(message);
            }
            for (char c : name.toCharArray()) {
                if (!Character.isLetter(c) && c != ' ') {
                    String message = "Parameter " + name + " is invalid";
                    logger.error(message);
                    throw new ArgumentException(message);
                }
            }
            return name;
        }
    }

    public static final class Programme {

        public static final String PROGRAMME_ID = "pid";

        public static String getAndValidFrom(String pid) {
            if (pid == null) {
                String message = "Missing a parameter - Please check your command";
                logger.error(message);
                throw new ArgumentException(message);
            }
            if (pid.chars().allMatch(Character::isLetter)) {
                return pid.toUpperCase();
            }
            String message = "Parameter " + pid + " is invalid";
            logger.error(message);
            throw new ArgumentException(message);
        }
    }

    public static final class Email {

        public static final String EMAIL = "email";

        public static String getAndValidFrom(String email) {
            if (email == null) {
                String message = "Missing a parameter - Please check your command";
                logger.error(message);
                throw new ArgumentException(message);
            }
            if (email.contains("@")) {
                return email.toLowerCase();
            }
            String message = "Parameter " + email + " is invalid";
            logger.error(message);
            throw new ArgumentException(message);
        }
    }

    public static final class Optional {

        public static final String OPTIONAL = "mandatory";

        public static boolean getAndValidFrom(String optional) {
            if (optional == null) {
                String message = "Missing a parameter - Please check your command";
                logger.error(message);
                throw new ArgumentException(message);
            }
            if (optional.toLowerCase().equals("true")
                    | optional.toLowerCase().equals("false")) {
                return optional.equals("true");
            }
            String message = "Parameter " + optional + " is invalid";
            logger.error(message);
            throw new ArgumentException(message);
        }
    }

    public static final class CurricularSemesters {

        public static final String CURRICULAR_SEMESTERS = "semesters";

        public static String getAndValidFrom(String sem) {
            if (sem == null) {
                String message = "Missing a parameter - Please check your command";
                logger.error(message);
                throw new ArgumentException(message);
            }
            String toReturn = "";
            for (String s : sem.split(",")) {
                if (!isValidNumber(s)) {
                    String message = "Parameter " + sem + " is invalid";
                    logger.error(message);
                    throw new ArgumentException(message);
                }
                toReturn += s + ",";
            }
            return toReturn.substring(0, toReturn.length() - 1);
        }

        private static boolean isValidNumber(String number) {
            return number.chars().allMatch(Character::isDigit);
        }
    }


}
