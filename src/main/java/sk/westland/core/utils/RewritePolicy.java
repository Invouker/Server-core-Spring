package sk.westland.core.utils;

import org.apache.logging.log4j.core.LogEvent;
import org.apache.logging.log4j.core.config.plugins.Plugin;
import org.apache.logging.log4j.core.config.plugins.PluginFactory;
import org.apache.logging.log4j.core.impl.ContextDataFactory;
import org.apache.logging.log4j.core.impl.Log4jLogEvent;
import org.apache.logging.log4j.message.SimpleMessage;
import org.apache.logging.log4j.util.StringMap;

@Plugin(name = "InjectMarkerPolicy", category = "Core", elementType = "rewritePolicy", printObject = true)
public class RewritePolicy implements org.apache.logging.log4j.core.appender.rewrite.RewritePolicy {
    @Override
    public LogEvent rewrite(LogEvent event) {
        Log4jLogEvent.Builder builder = new Log4jLogEvent.Builder();
        if (event.getMarker() != null) {
            StringMap contextData = ContextDataFactory.createContextData();
            contextData.putValue("_marker", event.getMarker().getName());
            builder.setContextData(contextData);
        }
        builder.setLoggerName(event.getLoggerName());
        builder.setMarker(event.getMarker());
        builder.setLoggerFqcn(event.getLoggerFqcn());
        builder.setLevel(event.getLevel());

        if(event.getMessage().getFormattedMessage().contains("test")) {
            System.out.println("Reformatted log");
            org.apache.logging.log4j.message.Message message = new SimpleMessage("randomText");
            builder.setMessage(message);
        }

        builder.setThrown(event.getThrown());
        builder.setContextStack(event.getContextStack());
        builder.setThreadName(event.getThreadName());
        builder.setSource(event.getSource());
        builder.setTimeMillis(event.getTimeMillis());
        return builder.build();
    }

    @PluginFactory
    public static RewritePolicy createPolicy() {
        return new RewritePolicy();
    }
}
