package net.beast462.int2204.mimir.Core;

import java.io.IOException;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class Logger {
    private final OutputStream out;
    public static volatile Logger defaultLogger = new Logger(System.out);

    private static String now() {
        return new SimpleDateFormat("dd/MM/yyyy HH:mm").format(Calendar.getInstance().getTime());
    }

    public Logger() {
        this.out = System.out;
    }

    public Logger(OutputStream out) {
        this.out = out;
    }

    private static String generateMessage(String message, String level) {
        return String.format("[%s][%s] :: %s\n", now(), level, message);
    }

    public void info(String message) {
        var data = generateMessage(message, "info").getBytes();

        try {
            out.write(data);
        } catch (IOException exception) {
            defaultLogger.error("Cannot write log to stream");
            exception.printStackTrace();
        }
    }

    public void error(String message) {
        var data = generateMessage(message, "error").getBytes();

        try {
            out.write(data);
        } catch (IOException exception) {
            defaultLogger.error("Cannot write log to stream");
            exception.printStackTrace();
        }
    }

    public void warn(String message) {
        var data = generateMessage(message, "warn").getBytes();

        try {
            out.write(data);
        } catch (IOException exception) {
            defaultLogger.error("Cannot write log to stream");
            exception.printStackTrace();
        }
    }
}
