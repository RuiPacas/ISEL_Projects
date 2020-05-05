import isel.leic.utils.Time;

import javax.swing.text.DefaultEditorKit;

public class LCD { // Escreve no LCD usando a interface a 4 bits.
    private static final int LINES = 2, COLS = 16; // Dimensão do display.

    private final static int ENABLE_MASK = 0x40; // out Only needed in Parallel
    private final static int DATA_MASK = 0x0F; // out
    private final static int RS_MASK = 0x20; // out Only needed in parallel
    private static final boolean SERIAL = true;

    public static void main(String[] args) {
        HAL.init();
        SerialEmitter.init();
        init();

    }

    // Escreve um nibble de comando/dados no LCD
    private static void writeNibbleParallel(boolean rs, int data) {
        if (rs)
            HAL.setBits(RS_MASK);
        else
            HAL.clrBits(RS_MASK);
        HAL.setBits(ENABLE_MASK);
        HAL.writeBits(DATA_MASK, data);
        HAL.clrBits(ENABLE_MASK);
        Time.sleep(10);
    }

    private static void writeNibbleSerial(boolean rs, int data) {
        SerialEmitter.send(SerialEmitter.Destination.LCD, (data << 1) | ((rs) ? 1 : 0));
    }

    private static void writeNibble(boolean rs, int data) {
        if (SERIAL)
            writeNibbleSerial(rs, data);
        else
            writeNibbleParallel(rs, data);
    }

    /* // Escreve um byte de comando/dados no LCD
     private static void writeByte(boolean rs, int data) {
         writeNibble(rs, data>>4&DATA_MASK);
         writeNibble(rs, data&DATA_MASK);
     }*/
    private static void writeByte(boolean rs, int data) {
        writeNibble(rs, data >> 4);
        writeNibble(rs, data);
    }

    // Escreve um comando no LCD
    private static void writeCMD(int data) {
        writeByte(false, data);
    }

    // Escreve um dado no LCD
    private static void writeDATA(int data) {
        writeByte(true, data);
    }

    // Envia a sequência de iniciação para comunicação a 4 bits.
    public static void init() {
        //definir o protocolo de iniciacao
        Time.sleep(20);
        writeNibble(false, 0x03);
        Time.sleep(5);
        writeNibble(false, 0x03);
        Time.sleep(1);
        writeNibble(false, 0x03);
        writeNibble(false, 0x02);
        writeByte(false, 0x28);
        writeByte(false, 0x08);
        writeByte(false, 0x01);
        writeByte(false, 0x06);
        writeByte(false, 0x0F);
    }

    // Escreve um caráter na posição corrente.
    public static void write(char c) {
        writeDATA((int) c);
    }

    // Escreve uma string na posição corrente.
    public static void write(String txt) {
        for (int i = 0; i < txt.length(); i++) {
            write(txt.charAt(i));
        }
    }

    // Envia comando para posicionar cursor (‘lin’:0..LINES-1 , ‘col’:0..COLS-1)
    public static void cursor(int lin, int col) {
        writeByte(false, (lin == 0 ? 128 : 192) + col);
    }

    // Envia comando para limpar o ecrã e posicionar o cursor em (0,0)
    public static void clear() {
        writeByte(false, 0x01);
        cursor(0, 0);
    }


    public static void off(){
        writeByte(false,0x08);
    }

    public static int getStartingCol(String name){
        return (COLS-name.length())/2 ;
    }

    public static void clearLine(int line) {
        cursor(line,0);
        for (int i = 0; i < COLS; i++) {
            write(' ');
        }
    }
}