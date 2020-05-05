import java.io.PrintWriter;
import java.util.Scanner;

public class User {
    private static final int NAMEPOS = 2;
    private static final int NUMBERPOS = 0;
    private static final int PINPOS = 1;
    private static final int ACUMULATEDPOS = 3;
    private static final int INPOS = 4;
    private static final String FILTER_SEPARATOR = ";";
    private static final int NUMOFPARAMETERS = 4;
    private int number;
    private int pin;
    private String name;
    private long in = 0;
    private long acumulated = 0;


    public User(String name, int number, int pin) {
        this.name = name;
        this.number = number;
        this.pin = maskedPin(pin);
    }

    public User(String[] s) {
        this.name = s[NAMEPOS];
        number = Integer.parseInt(s[NUMBERPOS]);
        pin = (Integer.parseInt(s[PINPOS]));
        acumulated = Long.parseLong(s[ACUMULATEDPOS]);
        if (s.length == NUMOFPARAMETERS + 1)
             in = Long.parseLong(s[INPOS]);
        Users.addUser(this);
    }

    public void setPin(int pin) {
        this.pin = maskedPin(pin);
    }

    public void setIn(long in) {
        this.in = in;
    }

    public void setAcumulated(long acumulated) {
        this.acumulated += acumulated;
    }

    public String getName() {
        return name;
    }

    public long getIn() {
        return in;
    }

    public int getNumber() {
        return number;
    }

    public int getPin() {
        return pin;
    }

    public long getAcumulated() {
        return acumulated;
    }

    public boolean isIn() {
        return in != 0;
    }

    public int maskedPin(int pin) {
        return pin * 5 + 128;
    }


    public boolean IsPin(int pin) {
        return this.pin == maskedPin(pin);
    }



    public String toString() {
        String s = number + FILTER_SEPARATOR + pin + FILTER_SEPARATOR + name + FILTER_SEPARATOR + acumulated;
        if (isIn()) s+=FILTER_SEPARATOR + getIn();
        return s;
    }

    public static void fromString(String i) {
        String[] s = i.split(FILTER_SEPARATOR);
        User u = new User(s);
    }
}