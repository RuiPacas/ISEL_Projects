public class M {
    private static final int mMask = 0x20;

    public static void init() {
        HAL.init();
    }

    public static void main(String[] args) {

    }

    public static boolean isOn() {
        return HAL.isBit(mMask);
    }
}
