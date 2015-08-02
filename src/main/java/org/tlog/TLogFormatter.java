package org.tlog;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Formatter;
import java.util.logging.Handler;
import java.util.logging.LogRecord;

public class TLogFormatter extends Formatter{

    private static final DateFormat df = new SimpleDateFormat("dd/MM/yyyy hh:mm:ss.SSS");

    public String format(LogRecord record) {
        StringBuilder builder = new StringBuilder("\n");
        builder.append(record.getLevel()).append(": ");
        builder.append(df.format(new Date(record.getMillis()))).append(": ");
        builder.append(TPath.getThreadVal().get("request-id")).append(": ");
        builder.append(TPath.getThreadVal().get("context-root")).append(": ");
        builder.append(formatMessage(record)).append("\n");
        return builder.toString();
    }
    
    public String getHead(Handler h) {
        return super.getHead(h);
    }

    public String getTail(Handler h) {
        return super.getTail(h);
    }

}
