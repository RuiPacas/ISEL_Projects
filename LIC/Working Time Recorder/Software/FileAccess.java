import java.io.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Scanner;

public class FileAccess {

    public static void write(String fileName,boolean append,String s){
        try{
            PrintWriter out = new PrintWriter(new FileWriter(fileName,append));
            out.println(s);
            out.close();
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }

    }

    public static ArrayList<String> read(String fileName) {
        ArrayList<String> s= new ArrayList<>();
        try {
            Scanner in = new Scanner(new FileReader(fileName));
            while(in.hasNextLine()){
               s.add(in.nextLine());
            }
            in.close();
        } catch (Exception e) {
            System.out.println(e);
        }
        finally {
            return s;
        }
    }

    public static void init() {    }
}

