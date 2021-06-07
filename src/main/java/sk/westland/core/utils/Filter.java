package sk.westland.core.utils;

import java.util.logging.LogRecord;

public class Filter implements java.util.logging.Filter {
    @Override
    public boolean isLoggable(LogRecord record) {

        //System.out.println("record.getMessage(): " + record.getMessage());

        return true;
    }
}
