/**
 * Test class for the Logger.
 * This class tests the functionality of the Logger class, including its singleton nature and logging capabilities.
 *
 * @author akud
 */

package es.uma.patternhelper.logger;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;

import static org.junit.jupiter.api.Assertions.*;


public class LoggerTest {

    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    private final ByteArrayOutputStream errContent = new ByteArrayOutputStream();
    private final PrintStream originalOut = System.out;
    private final PrintStream originalErr = System.err;

    @TempDir
    Path tempDir;

    private Path logFile;

    /**
     * Sets up the test environment before each test.
     * Redirects System.out and System.err to capture log output.
     */
    @BeforeEach
    public void setUpStreams() {
        System.setOut(new PrintStream(outContent));
        System.setErr(new PrintStream(errContent));
        logFile = tempDir.resolve("test.log");
        Logger.getInstance(LoggerTest.class).setLogFile(logFile.toString());
    }

    /**
     * Restores the test environment after each test.
     * Resets System.out and System.err to their original streams.
     */
    @AfterEach
    public void restoreStreams() throws IOException {
        System.setOut(originalOut);
        System.setErr(originalErr);
        Files.deleteIfExists(logFile);
    }

    /**
     * Tests if the Logger is a singleton.
     * Verifies that multiple calls to getInstance() return the same object.
     */
    @Test
    public void testLoggerIsSingleton() {
        Logger logger1 = Logger.getInstance(LoggerTest.class);
        Logger logger2 = Logger.getInstance(LoggerTest.class);
        assertSame(logger1, logger2, "Logger should be a singleton");
    }

    /**
     * Tests the info logging functionality.
     * Verifies that info messages are logged to System.out.
     */
    @Test
    public void testInfoLogging() {
        Logger logger = Logger.getInstance(LoggerTest.class);
        logger.info("Test info message");
        assertTrue(outContent.toString().contains("Test info message"));
    }

    /**
     * Tests the warn logging functionality.
     * Verifies that warn messages are logged to System.err.
     */
    @Test
    public void testWarnLogging() {
        Logger logger = Logger.getInstance(LoggerTest.class);
        logger.warn("Test warn message");
        assertTrue(errContent.toString().contains("Test warn message"));
    }

    /**
     * Tests the error logging functionality.
     * Verifies that error messages are logged to System.err.
     */
    @Test
    public void testErrorLogging() {
        Logger logger = Logger.getInstance(LoggerTest.class);
        logger.error("Test error message");
        assertTrue(errContent.toString().contains("Test error message"));
    }

    /**
     * Tests the debug logging functionality.
     * Verifies that debug messages are logged to System.out.
     */
    @Test
    public void testDebugLogging() {
        Logger logger = Logger.getInstance(LoggerTest.class);
        logger.debug("Test debug message");
        assertTrue(outContent.toString().contains("Test debug message"));
    }

    /**
     * Tests logging with class context.
     * Verifies that the log message contains the class name.
     */
    @Test
    public void testLoggingWithClassContext() {
        Logger logger = Logger.getInstance(LoggerTest.class);
        logger.info("Test message with class context");
        assertTrue(outContent.toString().contains(LoggerTest.class.getName()));
    }

    /**
     * Tests logging with class context to a file.
     * Verifies that the log message in the file contains the class name.
     */
    @Test
    public void testLoggingWithClassContextToFile() throws IOException {
        Logger logger = Logger.getInstance(LoggerTest.class);
        logger.info("Test message with class context to file");
        String fileContent = Files.readString(logFile);
        assertTrue(fileContent.contains(LoggerTest.class.getName()));
    }

    /**
     * Tests logging an exception.
     * Verifies that the exception message is logged to System.err.
     */
    @Test
    public void testLoggingException() {
        Logger logger = Logger.getInstance(LoggerTest.class);
        Exception exception = new RuntimeException("Test exception");
        logger.error("Test message with exception", exception);
        assertTrue(errContent.toString().contains("Test exception"));
    }

    /**
     * Tests logging to a file.
     * Verifies that log messages are written to the specified file.
     */
    @Test
    public void testLogToFile() throws IOException {
        Logger logger = Logger.getInstance(LoggerTest.class);
        logger.info("Test log to file");

        String fileContent = Files.readString(logFile);
        assertTrue(fileContent.contains("Test log to file"));
    }

    /**
     * Tests if log messages contain timestamps.
     * Verifies that log messages in the file contain a timestamp in the correct format.
     */
    @Test
    public void testLogWithTimestamp() throws IOException {
        Logger logger = Logger.getInstance(LoggerTest.class);
        logger.info("Test timestamp");

        String fileContent = Files.readString(logFile);
        String timestampRegex = "\\d{4}-\\d{2}-\\d{2} \\d{2}:\\d{2}:\\d{2}\\.\\d{3}";
        assertTrue(fileContent.matches("(?s).*"+timestampRegex+".*Test timestamp.*"));
    }
}