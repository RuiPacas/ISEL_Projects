import isel.leic.utils.Time;

import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.Scanner;

public class App {

    public static final int UIN_LENGTH = 3;
    public static final int PIN_LENGTH = 4;
    private static final int DOOR_SPEED = 5;
    private static final DateFormat dateAndHour = new SimpleDateFormat("dd/MM/yyyy HH:mm");
    private static final DateFormat dayAndHour = new SimpleDateFormat("EEE. HH:mm", Locale.UK);
    private static final DateFormat hour = new SimpleDateFormat("HH:mm");
    private static final int TIMEOUT = 5000;
    private static Date date = new Date();


    public static void main(String[] args) {
        init();
        LaunchApp();
    }


    private static void LaunchApp() {
        while (true) {
            if (M.isOn()) Maintenance();
            TUI.clear();
            TUI.writeMessage(dateAndHour.format(date), 0, 0);
            boolean cicle=true;
            while (cicle){
                if (M.isOn()){ Maintenance();
                TUI.writeMessage(dateAndHour.format(date), 0, 0);}
                if(dateChanged()) TUI.writeMessage(hour.format(date),0,11);
                TUI.clearLine(1);
                TUI.writeMessage("UIN:", 1, 0);
                int uin = TUI.readInteger(TIMEOUT, false, UIN_LENGTH, 1, 4);
                TUI.clearLine(1);
                TUI.writeMessage("PIN:", 1, 0);
                int pin = -1;
                if (uin != -1)
                    pin = TUI.readInteger(TIMEOUT, true, PIN_LENGTH, 1, 4);
                if (pin != -1 && Users.UserIsValid(uin, pin)) {
                    User user = Users.getUser(uin);
                    TUI.clear();
                    TUI.writeMessage("HELLO", 0, 5);
                    TUI.writeMessage(user.getName(), 1, TUI.setCol(user.getName()));
                    if (TUI.isPressed('#', TIMEOUT)) changePin(user);
                    new Log(date, user, user.isIn());
                    TUI.clear();
                    inOrOut(dayAndHour, date, user);
                    Users.save();
                    cicle=false;
                 }
            }
        }
    }

    private static void inOrOut(DateFormat b, Date date, User user) {
        NumberFormat form = new DecimalFormat("00");
        if (!user.isIn()) {
            //Entrar No Trabalho
            user.setIn(date.getTime());
            TUI.writeMessage(b.format(date), 0, 0);
            TUI.writeMessage(form.format(Users.miliToHours(user.getAcumulated())) + ":" + form.format(Users.miliToMinutes(user.getAcumulated())), 0, 11);
            TUI.writeMessage("???. ??:?? ??:??", 1, 0);
        } else {
            //Sair do Trabalho
            user.setAcumulated(date.getTime() - user.getIn());
            TUI.writeMessage(b.format(user.getIn()), 0, 0);
            TUI.writeMessage(b.format(date), 1, 0);
            TUI.writeMessage(form.format(Users.miliToHours(user.getAcumulated())) + ":" + form.format(Users.miliToMinutes(user.getAcumulated())), 1, 11);
            user.setIn(0);
        }
        Time.sleep(5000);
        doorSequence(user);
    }


    private static void Maintenance() {
        TUI.clear();
        TUI.writeMessage("Out of Service", 0, 1);
        TUI.writeMessage("Wait", 1, 6);
        while (M.isOn()) {
            Scanner input = new Scanner(System.in);
            System.out.println("Write 'Help' for command list");
            System.out.println("Insert command : ");
            String s = input.next();
            if (!exit(s))
                doCommand(s, input);
            else while (M.isOn()) ;
        }
        TUI.clear();
    }

    private static void changePin(User user) {
        TUI.clear();
        int firstEntry = -1;
        int secondEntry = -2;
        TUI.writeMessage("Change Pin ? ", 0, 2);
        TUI.writeMessage("(YES==*)", 1, 3);
        if (!TUI.isPressed('*', TIMEOUT)) return;
        TUI.clear();
        TUI.writeMessage("Insert New ", 0, 3);
        TUI.writeMessage("PIN:", 1, 0);
        firstEntry = TUI.readInteger(TIMEOUT, true, PIN_LENGTH, 1, 4);
        TUI.clear();
        if (firstEntry != -1) {
            TUI.writeMessage("RE-Insert New", 0, 2);
            TUI.writeMessage("PIN:", 1, 0);
            secondEntry = TUI.readInteger(TIMEOUT, true, PIN_LENGTH, 1, 4);
        }
        TUI.clear();
        TUI.writeMessage("PIN has been ", 0, 2);
        if (firstEntry == secondEntry) {
            TUI.writeMessage("Changed", 1, 5);
            user.setPin(firstEntry);
        } else {
            TUI.writeMessage("Helded", 1, 5);
            Time.sleep(2000);
        }
        Users.save();
        TUI.clear();
    }

    private static void doorSequence(User user) {
        TUI.clear();
        TUI.writeMessage(user.getName() + "", 0, TUI.setCol(user.getName()));
        TUI.writeMessage("Opening Door", 1, 2);
        Time.sleep(1000);
        Door.open(DOOR_SPEED);
        TUI.clear();
        TUI.writeMessage(user.getName() + "", 0, TUI.setCol(user.getName()));
        TUI.writeMessage("Door Opened", 1, 2);
        Time.sleep(1000);
        TUI.clear();
        TUI.writeMessage(user.getName() + "", 0, TUI.setCol(user.getName()));
        TUI.writeMessage("Closing Door...", 1, 1);
        Time.sleep(1000);
        Door.close(DOOR_SPEED);
    }

    private static boolean exit(String s) {
        return s.toLowerCase().equals("exit");
    }

    private static void doCommand(String s, Scanner input) {
        String command = s.toLowerCase();
        if (command.equals("create")) Users.create(input);
        else if (command.equals("delete")) Users.remove(input);
        else if (command.equals("list")) Users.listUsers();
        else if (command.equals("off")) off();
        else if (command.equals("help")) commandList();
        else System.out.println("Invalid command");
    }

    private static void off() {
        TUI.turnOff();
        System.exit(0);
    }

    private static void commandList() {
        System.out.println("The available  commands are : ");
        System.out.println("Create,Delete,List,Exit,OFF");

    }

    private static boolean dateChanged(){
        Date dNew = new Date();
        if((Users.miliToMinutes(dNew.getTime())-Users.miliToMinutes(date.getTime()))!=0) {
            date = dNew;
            return true;
        }
        return false;
    }
    private static int getDimension(int length) {
        int val = 10;
        for (int i = 1; i < length; ++i) {
            val *= 10;
        }
        return val;
    }

    private static void init() {
        M.init();
        TUI.init();
        Door.init();
        Users.setDimensions(getDimension(UIN_LENGTH), PIN_LENGTH, UIN_LENGTH);
        Users.init();
    }


}