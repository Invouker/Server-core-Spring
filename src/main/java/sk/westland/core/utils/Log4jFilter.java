package sk.westland.core.utils;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.Marker;
import org.apache.logging.log4j.core.LogEvent;
import org.apache.logging.log4j.core.Logger;
import org.apache.logging.log4j.core.filter.AbstractFilter;
import org.apache.logging.log4j.message.Message;

public class Log4jFilter extends AbstractFilter {

    @Override
    public Result filter(Logger logger, Level level, Marker marker, String s, Object... objects) {
        System.out.println("3. Filter message: " + s);
        return Result.NEUTRAL;
    }

    @Override
    public Result filter(LogEvent logEvent) {
        if(!logEvent.getMessage().getFormattedMessage().contains("test")) {
            //System.out.println("2. logEvent.getMessage(): " + logEvent.getMessage().getFormattedMessage());
            return Result.NEUTRAL;
        }
        return Result.DENY;
    }

    @Override
    public Result filter(Logger logger, Level level, Marker marker, Message msg, Throwable t) {
        System.out.println("1. Filter message: " + msg.getFormattedMessage());
        return Result.NEUTRAL;
    }

    @Override
    public Result filter(Logger logger, Level level, Marker marker, Object msg, Throwable t) {
        String candidate = null;
        if (msg != null) {
            candidate = msg.toString();

            System.out.println("4. candidate: " + candidate);
        }

        return Result.NEUTRAL;
    }
}
