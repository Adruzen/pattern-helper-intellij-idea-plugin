package es.uma.patternhelper.logger;

import es.uma.patternhelper.ConfigConstants;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Singleton Logger class for logging messages to console and file.
 */
public class Logger {
    private static Logger instance;
    private String LOG_FILE_PATH;

    /**
     * Private constructor to enforce singleton pattern.
     */

    private Logger() {
        if (ConfigConstants.isInicialised()) {
            LOG_FILE_PATH = ConfigConstants.get("LOG_FILE_PATH");
            if (LOG_FILE_PATH != null) {
                File logFile = new File(LOG_FILE_PATH);
                if (!logFile.getParentFile().exists()) {
                    logFile.getParentFile().mkdirs();
                }
            }
        } else {
            // Fallback if location cannot be determined
            LOG_FILE_PATH = "logfile.log";
        }

        System.out.println("Log file will be created at: " + LOG_FILE_PATH);
    }



    /**
     * Returns the singleton instance of the Logger.
     *
     * @return The Logger instance.
     */
    public static Logger getInstance() {
        if (instance == null) {
            instance = new Logger();
        }
        return instance;
    }

    /**
     * Resets instance (for testing purposes).
     *
     */
    public static void resetInstance() {
        instance = null;
    }

    /**
     * Returns file path String.
     *
     */
    public String getFilePath() {return this.LOG_FILE_PATH;}

    /**
     * Logs an info message.
     *
     * @param message The message to log.
     */
    public void info(String message) {
        log("INFO", message, System.out, getCallerClassName());
    }

    /**
     * Logs a warning message.
     *
     * @param message The message to log.
     */
    public void warn(String message) {
        log("WARN", message, System.err, getCallerClassName());
    }

    /**
     * Logs an error message.
     *
     * @param message The message to log.
     */
    public void error(String message) {
        log("ERROR", message, System.err, getCallerClassName());
    }

    /**
     * Logs an error message with an exception.
     *
     * @param message   The message to log.
     * @param e         The exception to log.
     */
    public void error(String message, Exception e) {
        log("ERROR", message + ": " + e.getMessage(), System.err, getCallerClassName());
    }

    /**
     * Logs a debug message.
     *
     * @param message The message to log.
     */
    public void debug(String message) {
        log("DEBUG", message, System.out, getCallerClassName());
    }

    /**
     * Logs a message with the specified level, message, stream, and class name.
     *
     * @param level     The log level (e.g., INFO, WARN, ERROR).
     * @param message   The message to log.
     * @param stream    The output stream (e.g., System.out, System.err).
     * @param className The name of the calling class.
     */
    private void log(String level, String message, java.io.PrintStream stream, String className) {
        String formattedMessage = formatMessage(level, message, className);
        stream.println(formattedMessage);
        writeToFile(formattedMessage);
    }

    /**
     * Formats the log message with timestamp, level, class name, and message.
     *
     * @param level     The log level.
     * @param message   The message to log.
     * @param className The name of the calling class.
     * @return The formatted log message.
     */
    private String formatMessage(String level, String message, String className) {
        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSS");
        String timestamp = now.format(formatter);
        return String.format("%s [%s] %s: %s", timestamp, level, className, message);
    }

    /**
     * Writes the log message to the log file.
     *
     * @param message The message to write.
     */
    private void writeToFile(String message) {
        File logFile = new File(LOG_FILE_PATH);
        System.out.println("Absolute path: " + logFile.getAbsolutePath());
        try (PrintWriter writer = new PrintWriter(new FileWriter(LOG_FILE_PATH, true))) {
            writer.println(message);
            writer.flush();
            System.out.println("File exists: " + logFile.exists());
            System.out.println("File length: " + logFile.length());
        } catch (IOException e) {
            System.err.println("Error writing to log file: " + e.getMessage());
            e.printStackTrace();
        }
    }


    /**
     * Gets the name of the class that called the logging method.
     *
     * @return The name of the calling class.
     */
    private String getCallerClassName() {
        StackTraceElement[] stackTrace = Thread.currentThread().getStackTrace();
        // stackTrace[0] is getStackTrace, stackTrace[1] is getCallerClassName, stackTrace[2] is the caller of log
        if (stackTrace.length >= 4) {
            return stackTrace[3].getClassName();
        }
        return "UnknownClass";
    }
}