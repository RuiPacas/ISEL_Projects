import isel.leic.utils.Time;

public class Door {
    // Controla o mecanismo da porta.
    // Inicia a classe, estabelecendo os valores iniciais.

    public static void init() {
        HAL.init();
        SerialEmitter.init();
        close(5);
    }

    private static void writeCMD(int speed, boolean open){
        SerialEmitter.send(SerialEmitter.Destination.DoorMechanism,speed<<1|(open?1:0));
    }
    // Envia comando para abrir a porta, indicando a velocidade
    public static void open(int speed) {
        writeCMD(speed,true);
    }

    // Envia comando para fechar a porta, indicando a velocidade
    public static void close(int speed) {
        writeCMD(speed,false);
    }

    // Retorna true se o tiver terminado o comando
    public static boolean isFinished() {
        return !SerialEmitter.isBusy();
    }
}