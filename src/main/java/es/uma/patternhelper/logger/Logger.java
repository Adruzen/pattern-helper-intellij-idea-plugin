/**
 * The Logger class provides a simple logging mechanism for applications.
 * It supports different log levels (INFO, WARN, ERROR, DEBUG) and can log to
 * the console or a specified file. It follows the Singleton pattern.
 *
 * @author akud
 */

package es.uma.patternhelper.logger;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Logger {
    private static Logger instance;
    private final Class<?> context;
    private String logFilePath;

    /**
     * Private constructor to enforce the Singleton pattern.
     *
     * @param context The class context from which the logger is called.
     */
    private Logger(Class<?> context) {
        this.context = context;
    }

    /**
     * Gets the singleton instance of the Logger.
     *
     * @param context The class context from which the logger is called.
     * @return The Logger instance.
     */
    public static Logger getInstance(Class<?> context) {
        if (instance == null) {
            instance = new Logger(context);
        }
        return instance;
    }

    /**
     * Sets the file path for logging.
     *
     * @param logFilePath The path to the log file.
     */
    public void setLogFile(String logFilePath) {
        this.logFilePath = logFilePath;
    }

    /**
     * Logs an informational message.
     *
     * @param message The message to log.
     */
    public void info(String message) {
        log("INFO", message, System.out);
    }

    /**
     * Logs a warning message.
     *
     * @param message The message to log.
     */
    public void warn(String message) {
        log("WARN", message, System.err);
    }

    /**
     * Logs an error message.
     *
     * @param message The message to log.
     */
    public void error(String message) {
        log("ERROR", message, System.err);
    }

    /**
     * Logs an error message along with an exception.
     *
     * @param message   The message to log.
     * @param e         The exception to log.
     */
    public void error(String message, Exception e) {
        log("ERROR", message + ": " + e.getMessage(), System.err);
    }

    /**
     * Logs a debug message.
     *
     * @param message The message to log.
     */
    public void debug(String message) {
        log("DEBUG", message, System.out);
    }

    /**
     * Logs a message with the specified level to the console and optionally to a file.
     *
     * @param level    The log level (e.g., INFO, WARN, ERROR, DEBUG).
     * @param message  The message to log.
     * @param stream   The output stream (System.out or System.err).
     */
    private void log(String level, String message, java.io.PrintStream stream) {
        String formattedMessage = formatMessage(level, message);
        stream.println(formattedMessage);
        if (logFilePath != null) {
            writeToFile(formattedMessage);
        }
    }

    /**
     * Formats the log message with a timestamp, log level, and class context.
     *
     * @param level   The log level.
     * @param message The message to log.
     * @return The formatted log message.
     */
    private String formatMessage(String level, String message) {
        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSS");
        String timestamp = now.format(formatter);
        return String.format("%s [%s] %s: %s", timestamp, level, context.getName(), message);
    }

    /**
     * Writes the log message to the specified log file.
     *
     * @param message The message to write.
     */
    private void writeToFile(String message) {
        try (PrintWriter writer = new PrintWriter(new FileWriter(logFilePath, true))) {
            writer.println(message);
        } catch (IOException e) {
            System.err.println("Error writing to log file: " + e.getMessage());
        }
    }
}