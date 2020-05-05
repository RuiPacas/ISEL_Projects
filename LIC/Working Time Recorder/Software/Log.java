import java.io.File;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Log {
    private static final String LOGFILE = "LOG.txt";
    private Date in;
    private String name;
    private int number;
    private boolean action;

    public Log (Date in, User user, boolean action){
        this.in=in;
        this.name=user.getName();
        this.number=user.getNumber();
        this.action=action;
        FileAccess.write(LOGFILE,true,toString());
    }

    public String toString(){
        DateFormat a = new SimpleDateFormat("dd/MM/yyyy HH:mm ");
        String s=a.format(in);
        s+=((!action)? "-> " : "<- ");
        s+=number+":"+name;
        return s;
    }
}
