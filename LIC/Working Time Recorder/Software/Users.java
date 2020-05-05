import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Scanner;

public class Users {

    private static User[] allUsers;
    private static final String USERSFILE = "USERS.txt";
    private static int pinLength = 0;
    private static int uLength = 0;

    public static void init() {
        load();
        FileAccess.init();
    }

    public static void setDimensions(int dim, int pLength, int uL) {
        allUsers = new User[dim];
        pinLength = pLength;
        uLength = uL;

    }


    public static boolean createNewUser(String name, int number, int pin) {
        if (canAdd(number)) {
            allUsers[number] = new User(name, number, pin);
            return true;
        }
        return false;
    }

    public static void addUser(User u) {
        int number = u.getNumber();
        if (canAdd(number)) {
            allUsers[number] = u;
        }
    }


    private static boolean canAdd(int number) {
        return allUsers[number] == null;
    }


    public static boolean UserIsValid(int number, int pin) {
        User u;
        if ((u = allUsers[number]) == null) return false;
        return u.IsPin(pin);
    }

    public static User getUser(int number) {
        return allUsers[number];
    }

    public static long miliToHours(long value) {
        return ((value / 1000) / 60) / 60;
    }

    public static long miliToMinutes(long value) {
        long hours = miliToHours(value);
        hours = hours * 60 * 60 * 1000;
        long a = value - hours;
        return (a / 1000) / 60;
    }


    public static void load() {
        ArrayList<String> al = FileAccess.read(USERSFILE);
        for (String s : al)
            User.fromString(s);
    }


    public static void save() {
        int counter = 0;
        for (int i = 0; i < allUsers.length; i++) {
            if (allUsers[i] != null) {
                User u = allUsers[i];
                String s = u.toString();
                FileAccess.write(USERSFILE, counter++ != 0, s);
            }
        }
    }


    public static void remove(Scanner input) {
        System.out.println("Uin? (0-" + (allUsers.length - 1) + ")");
        int i = input.nextInt();
        if (i >= 0 && i < allUsers.length && allUsers[i] != null) {
            System.out.println(allUsers[i].getName() + " : Do you want to delete this user? (Yes/No)");
            if ((input.next()).toLowerCase().equals("yes")) {
                allUsers[i] = null;
                save();
                System.out.println("User removed.");
            }
        } else System.out.println("This Uin doesn't exist.");
    }

    private static String getFormat(int i) {
        String s = "";
        for (int j = 0; j < i; j++) {
            s += "0";
        }
        return s;
    }

    public static void create(Scanner input) {
        NumberFormat form = new DecimalFormat(getFormat(uLength));
        int number;
        for (number = 0; number < allUsers.length; number++) if (allUsers[number] == null) break;
        System.out.println("Enter User name.");
        input.nextLine();
        String name = input.nextLine();
        System.out.println("Enter a " + pinLength + " digit pin");
        int pin = input.nextInt();
        if (createNewUser(name, number, pin)) {
            save();
            System.out.println("User number is " + form.format(number));
            System.out.println("New User created.");
        }
    }

    public static void listUsers() {
        DateFormat b = new SimpleDateFormat("HH:mm");
        for (int i = 0; i < allUsers.length; i++) {
            if (allUsers[i] != null && allUsers[i].isIn()) {
                User u = allUsers[i];
                System.out.println(i + " - " + u.getName() + " - " + b.format(u.getIn()));
            }
        }
    }
}