package Project;

public class Flag {

    private static boolean write = false;

    public static boolean isWrite() {
        return write;
    }

    public static void setWrite(boolean write) {
        Flag.write = write;
    }
}
