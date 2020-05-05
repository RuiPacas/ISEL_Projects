public class TUI { //Text User Interface

    private static final char CLEAR_BUTTON = '*';
    private static final char HASH_BUTTON = '#';


    public static void main(String[] args) {
       init();

        LCD.cursor(0, 0);
        LCD.write("Introduzir codigo");
        System.out.println((readInteger(5000,false, 8, 1, 3)));
        }

    public static void writeMessage(String message,int lin , int col){
        LCD.cursor(lin,col);
        LCD.write(message);
    }
    public static void clear(){
        LCD.clear();
    }

    /**
     * Retorna o valor dos digitos introduzidos ,além disso,escreve no lcd nas posições
     * line e col os numberOfDigits caracteres com escrita normal ou ocultada pelo
     * boolean hide
     **/
    public static int readInteger(int timeout ,boolean hide, int numberOfDigits, int line, int col) {
        int value = 0;
        if (numberOfDigits <= 0) return -1;
        char digit;
        LCD.cursor(line, col);
        for (int i = 0; i < numberOfDigits; i++) LCD.write('?');
        LCD.cursor(line, col);
        for (int i = 0; i < numberOfDigits; i++) {
            digit = 0;
            if ((digit = KBD.waitKey(timeout))==0) {
                return -1;
            }
            if (digit == CLEAR_BUTTON) {
                if (i == 0) return -1;
                else {
                    LCD.cursor(line, col);
                    for (int j = 0; j < numberOfDigits; j++) LCD.write('?');
                    LCD.cursor(line, col);
                    i = -1;
                    value = 0;
                }
            }
            else if(digit==HASH_BUTTON){
                i--;
            }
            else {
                LCD.write(hide ? '*' : digit);
                int aux = digit - '0';
                int power = numberOfDigits - 1 - i;
                while (power != 0) {
                    aux *= 10;
                    --power;
                }
                value += aux;
            }
        }
        return value;
    }

    public static void init() {
        HAL.init();
        KBD.init();
        SerialEmitter.init();
        LCD.init();
    }

    public static void turnOff(){
        clear();
        LCD.off();
    }

    public static boolean isPressed(char c, int time) {
        return KBD.waitKey(time)==c;
    }

    public static int setCol(String name){
        return LCD.getStartingCol(name);
    }

    public static void clearLine(int line) {
        LCD.clearLine(line);
    }
}
