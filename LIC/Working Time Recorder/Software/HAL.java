import isel.leic.UsbPort;

public class HAL { // Virtualiza o acesso ao sistema UsbPort

    private static int out;

    public static void main(String[] args) {
        init();
    }

    // Inicia a Classe
    public static void init() {
        out(out = 0);
    }

    public static void out(int val) {
        UsbPort.out(~val);
    }

    public static int in() {
        return ~UsbPort.in();
    }

    // Retorna true se o bit tiver o valor lógico ‘1’
    public static boolean isBit(int mask) {
        return (readBits(mask) != 0);
    }

    // Retorna os valores dos bits representados por mask presentes no UsbPort
    public static int readBits(int mask) {
        int in = in();
        return (mask & in);
    }

    // Escreve nos bits representados por mask o valor de value
    public static void writeBits(int mask, int value) {
        out(out = (~mask&out | value&mask));
    }

    // Coloca os bits representados por mask no valor lógico ‘1’
    public static void setBits(int mask) {
        out(out = mask | out);
    }

    // Coloca os bits representados por mask no valor lógico ‘0’
    public static void clrBits(int mask) {
        out(out = (out & (~mask)));
    }
}