package sk.westland.core.utils;

public class RunnableDelay {

    private static int x = 1;

    private static int[] delay = {
            0,1,2,3,4,5,6,7,8,
            9,10,11,12,13,14,
            15,16,17,18,19,20 };

    public static int DELAY() {
        x++;
        return delay[x % 20] + x % 20;
    }
}
