import isel.leic.utils.Time;

public class SerialEmitter {
    // Envia tramas para os diferentes módulos Serial Receiver.

    private final static int BUSY_MASK = 0x40; // in
    private final static int SCLK_MASK = 0x10; //out
    private final static int SDX_MASK = 0x40; //out same pin as ENABLE_MASK(LCD Class);

    public static enum Destination {
        DoorMechanism, LCD
    }

    public static void main(String[] args) {
        HAL.init();
        init();
        send(Destination.LCD, 0x41);
    }

    // Inicia a classe
    public static void init() {
        HAL.clrBits(SCLK_MASK);
        HAL.setBits(SDX_MASK);
    }

    // Envia uma trama para o SerialReceiver identificado o destino em addr e os bits de dados em‘data’.
    public static void send(Destination addr, int data) {
        while (isBusy()) ;
        int parity = 0;
        start();
        if (addr == Destination.LCD) {
            ++parity;
            HAL.setBits(SDX_MASK);
        } else if (addr == Destination.DoorMechanism)
            HAL.clrBits(SDX_MASK);

        for (int counter = 0; counter <= 4; counter++) {
            HAL.setBits(SCLK_MASK);
            if (data % 2 == 1) {
                HAL.setBits(SDX_MASK);
            } else {
                HAL.clrBits(SDX_MASK);
            }
            HAL.clrBits(SCLK_MASK);

            parity += data % 2;
            data >>= 1;
        }
        HAL.setBits(SCLK_MASK);
        if (parity % 2 == 1) HAL.setBits(SDX_MASK);
        else HAL.clrBits(SDX_MASK);
        HAL.clrBits(SCLK_MASK);
        HAL.setBits(SCLK_MASK);
        HAL.clrBits(SCLK_MASK);
        HAL.setBits(SDX_MASK);

    }

    private static void start() {
        HAL.clrBits(SDX_MASK);
    }

    // Retorna true se o canal série estiver ocupado
    public static boolean isBusy() {
        return HAL.isBit(BUSY_MASK);
    }


}