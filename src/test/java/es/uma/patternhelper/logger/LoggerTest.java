package es.uma.patternhelper.logger;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Test class for the Logger.
 */
public class LoggerTest {

    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    private final ByteArrayOutputStream errContent = new ByteArrayOutputStream();
    private final PrintStream originalOut = System.out;
    private final PrintStream originalErr = System.err;

    @TempDir
    Path tempDir;

    private final Path logFile = Path.of(Logger.getInstance().getFilePath());

    /**
     * Sets up the output streams and log file before each test.
     *
     * @throws IOException If an I/O error occurs.
     */
    @BeforeEach
    public void setUpStreams() {
        System.setOut(new PrintStream(outContent));
        System.setErr(new PrintStream(errContent));
        if (Files.exists(logFile)) {
            Files.delete(logFile);
        }
    }

    /**
     * Restores the output streams and deletes the log file after each test.
     *
     * @throws IOException If an I/O error occurs.
     */
    @AfterEach
    public void restoreStreams() throws IOException {
        System.setOut(originalOut);
        System.setErr(originalErr);
        Files.deleteIfExists(logFile);
    }

    /**
     * Tests if the Logger is a singleton.
     */
    @Test
    public void testLoggerIsSingleton() {
        Logger logger1 = Logger.getInstance();
        Logger logger2 = Logger.getInstance();
        assertSame(logger1, logger2, "Logger should be a singleton");
    }

    /**
     * Tests the info logging functionality.
     */
    @Test
    public void testInfoLogging() {
        Logger logger = Logger.getInstance();
        logger.info("Test info message");
        assertTrue(outContent.toString().contains("Test info message"));
        assertTrue(outContent.toString().contains(LoggerTest.class.getName()));
    }

    /**
     * Tests the warn logging functionality.
     */
    @Test
    public void testWarnLogging() {
        Logger logger = Logger.getInstance();
        logger.warn("Test warn message");
        assertTrue(errContent.toString().contains("Test warn message"));
        assertTrue(errContent.toString().contains(LoggerTest.class.getName()));
    }

    /**
     * Tests the error logging functionality.
     */
    @Test
    public void testErrorLogging() {
        Logger logger = Logger.getInstance();
        logger.error("Test error message");
        assertTrue(errContent.toString().contains("Test error message"));
        assertTrue(errContent.toString().contains(LoggerTest.class.getName()));
    }

    /**
     * Tests the debug logging functionality.
     */
    @Test
    public void testDebugLogging() {
        Logger logger = Logger.getInstance();
        logger.debug("Test debug message");
        assertTrue(outContent.toString().contains("Test debug message"));
        assertTrue(outContent.toString().contains(LoggerTest.class.getName()));
    }

    /**
     * Tests logging with different class contexts.
     */
    @Test
    public void testLoggingWithDifferentClassContext() {
        Logger logger = Logger.getInstance();

        //Simulate logging from another class
        class AnotherClass {
            public void logFromAnotherClass(){
                logger.info("Message from AnotherClass");
            }
        }
        AnotherClass anotherClass = new AnotherClass();
        anotherClass.logFromAnotherClass();
        assertTrue(outContent.toString().contains("Message from AnotherClass"));
        assertTrue(outContent.toString().contains(AnotherClass.class.getName()));
    }

    /**
     * Tests logging with class context to file.
     *
     * @throws IOException If an I/O error occurs.
     */
    @Test
    public void testLoggingWithClassContextToFile() throws IOException {
        Logger logger = Logger.getInstance();
        logger.info("Test message with class context to file");
        String fileContent = Files.readString(logFile);
        assertTrue(fileContent.contains(LoggerTest.class.getName()));
    }

    /**
     * Tests logging an exception.
     */
    @Test
    public void testLoggingException() {
        Logger logger = Logger.getInstance();
        Exception exception = new RuntimeException("Test exception");
        logger.error("Test message with exception", exception);
        assertTrue(errContent.toString().contains("Test exception"));
        assertTrue(errContent.toString().contains(LoggerTest.class.getName()));
    }

    /**
     * Tests logging to a file.
     *
     * @throws IOException If an I/O error occurs.
     */
    @Test
    public void testLogToFile() throws IOException {
        Logger logger = Logger.getInstance();
        logger.info("Test log to file");

        String fileContent = Files.readString(logFile);
        assertTrue(fileContent.contains("Test log to file"));
        assertTrue(fileContent.contains(LoggerTest.class.getName()));
    }

    /**
     * Tests logging with a timestamp.
     *
     * @throws IOException If an I/O error occurs.
     */
    @Test
    public void testLogWithTimestamp() throws IOException {
        Logger logger = Logger.getInstance();
        logger.info("Test timestamp");

        String fileContent = Files.readString(logFile);
        String timestampRegex = "\\d{4}-\\d{2}-\\d{2} \\d{2}:\\d{2}:\\d{2}\\.\\d{3}";
        assertTrue(fileContent.matches("(?s).*" + timestampRegex + ".*Test timestamp.*"));
        assertTrue(fileContent.contains(LoggerTest.class.getName()));
    }
}