public class KBD { // Ler teclas. Métodos retornam ‘0’..’9’,’#’,’*’ ou NONE.

    private static final char NONE = 0;

    //Hardware table
   /* private static char[] table = { '1','2','3',NONE,
                                    '4','5','6',NONE,
                                    '7','8','9',NONE,
                                    '*','0','#',NONE
                                                    };*/

    //Simulator Table
    private static char[] table = {'1','2','3','4','5','6','7','8', '9','*','0','#', NONE,NONE,NONE,NONE};


    private final static int ACK_MASK = 0x80; // out
    private final static int DVAL_MASK = 0x80; // in
    private final static int DATA_MASK = 0x0F; // in


    public static void main(String[] args) {
        init();
        for(char key;;){
            key =getKey();
            if (key!=NONE) System.out.print(key);
        }

    }

    // Inicia a classe
    public static void init() {
        HAL.clrBits(ACK_MASK);

    }

    //resolver erro da tecla.

    // Retorna de imediato a tecla premida ou NONE se não há tecla premida.
    public static char getKey() {
        if(HAL.isBit(DVAL_MASK)) {
          int key = HAL.readBits(DATA_MASK);
           HAL.setBits(ACK_MASK);
           while(HAL.isBit(DVAL_MASK)){
                    // Espera por Dval = 0
                }
           HAL.clrBits(ACK_MASK);
           return table[key];
        }
        return NONE;
    }

    // Retorna quando a tecla for premida ou NONE após decorrido ‘timeout’ milisegundos.
    public static char waitKey(long timeout){
       long currTime= isel.leic.utils.Time.getTimeInMillis();
       long waitTime = currTime+timeout;
       char myChar;
       while((myChar=getKey())==NONE){
           if(isel.leic.utils.Time.getTimeInMillis()>=waitTime) break ;
       }
       return myChar;
    }


}