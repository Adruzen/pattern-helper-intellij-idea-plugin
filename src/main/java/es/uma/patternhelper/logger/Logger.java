package es.uma.patternhelper.logger;

import com.intellij.openapi.application.PathManager;
import es.uma.patternhelper.ConfigConstants;

import java.io.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Singleton Logger class for logging messages to console and file.
 */
public class Logger {
    private static Logger instance;
    private static File logFile;
    private static BufferedWriter bufferedWriter = null;

    /**
     * Private constructor to initialize the Logger.
     * Creates the log directory if it doesn't exist and sets up the log file.
     *
     * @throws IOException        If an I/O error occurs during file creation.
     * @throws SecurityException If a security manager exists and its `checkWrite` method denies write access to the log directory or file.
     */
    private Logger() throws IOException {
        String logDir = PathManager.getLogPath();
        // ensure that  the log directory exists
        if (!new File(logDir).exists()) {
            if (new File(logDir).mkdirs()) {
                System.out.println("Created log directory: " + logDir);
            } else {
                throw new IOException("Failed to create log directory: " + logDir);
            }
        }
        logFile = new File(logDir, "logfile.log");
        System.out.println("Starting logging to file: " + logFile.getAbsolutePath());
        if (bufferedWriter == null) {
            bufferedWriter = new BufferedWriter(new FileWriter(logFile));
        }
    }


    /**
     * Returns the singleton instance of the Logger.
     *
     * @return The Logger instance.
     */
    public static Logger getInstance() throws IOException {
        if (instance == null) {
            instance = new Logger();
        }
        return instance;
    }


    /**
     * Resets the singleton Logger instance, primarily for testing purposes.
     * Closes the current log file's writer if an instance exists and sets the
     * internal instance reference to null, allowing a fresh instance creation
     * on the next {@code getInstance()} call.
     *
     * @throws IOException If an error occurs closing the writer.
     */
    public static void resetInstance() throws IOException {
        if (instance != null) {
            if (bufferedWriter != null) {
                bufferedWriter = null;
            }
            instance = null;
        }
    }

    /**
     * Deletes the log file.
     */
    public static void deleteLogFile() {
        if (bufferedWriter != null) {
            try {
                bufferedWriter.close();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        if (logFile.exists()) {
            logFile.delete();
        }
    }



    /**
     * Returns file path String.
     *
     */
    public String getFilePath() {return logFile.getAbsolutePath();}

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
    private void log(String level, String message, PrintStream stream, String className) {
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
        try {
            bufferedWriter.write(message);
            bufferedWriter.newLine();
            bufferedWriter.flush();
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
