package sk.westland;

import org.junit.Test;

import java.util.regex.Pattern;

public class RegexTest {

    @Test
    public void RegexTestt() {
        System.out.println("1.1: " + checkWebsite("westland"));
        System.out.println("1.2: " + checkWebsite("tralala.sk"));
        System.out.println("1.3: " + checkWebsite("tralba"));
        System.out.println("1.4: " + checkWebsite("www.best"));

        System.out.println("2.0: " + checkIp("178.41.39.27"));
        System.out.println("2.1: " + checkIp("178.41.39.27:25565"));
        System.out.println("2.2: " + checkIp("192.168.0.1"));
        System.out.println("2.3: " + checkIp("127.0.0.1"));
        System.out.println("2.4: " + checkIp("98.987.328.557:25565"));
        System.out.println("2.5: " + checkIp("203.120.223.13:25565"));
    }

    private boolean checkIp(String message) {
        Pattern pattern = Pattern.compile("^(([0-9]|[1-9][0-9]|1[0-9]{2}|2[0-4][0-9]|25[0-5])\\.){3}([0-9]|[1-9][0-9]|1[0-9]{2}|2[0-4][0-9]|25[0-5]):[0-9]+$");
        Pattern pattern_ = Pattern.compile("^(?:[0-9]{1,3}\\.){3}[0-9]{1,3}$");
        if(pattern.matcher(message).matches() || pattern_.matcher(message).matches()) {
            return true;
        }
        return false;
    }


    private boolean checkWebsite(String message) {
        if(message.contains("spigot") || message.contains("westland") || message.contains("facebook")|| message.contains("instagram") )
            return false;

        Pattern pattern = Pattern.compile("(?:[a-z0-9](?:[a-z0-9-]{0,61}[a-z0-9])?\\.)+[a-z0-9][a-z0-9-]{0,61}[a-z0-9]");
        if(pattern.matcher(message).matches()) {
            return true;
        }
        return false;
    }
}
