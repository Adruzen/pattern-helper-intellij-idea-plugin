package es.uma.patternhelper.logger;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Test suite for Logger functionality.
 */
class LoggerTest {
    private File logFile;

    /**
     * Sets up the test environment before each test.
     * Resets the Logger instance and sets the log file to the default location.
     *
     * @throws IOException If an I/O error occurs.
     */
    @BeforeEach
    void setUp() throws IOException {
        Logger.resetInstance();
        Logger.getInstance(); // Initialize logger to get default log file
        logFile = new File(Logger.getInstance().getFilePath());
    }

    /**
     * Cleans up after each test, including checking if the log file exists.
     * Resets the Logger instance.
     *
     * @throws IOException If an I/O error occurs.
     */
    @AfterEach
    void tearDown() throws IOException {
        assertTrue(logFile.exists());
        Logger.resetInstance();
    }


    /**
     * Tests basic info message logging functionality.
     *
     * @throws IOException If an I/O error occurs.
     */
    @Test
    void shouldWriteInfoLogMessage() throws IOException {
        Logger logger = Logger.getInstance();
        String testMessage = "Test info message";
        logger.info(testMessage);
        String fileContent = Files.readString(logFile.toPath());
        assertTrue(fileContent.contains(testMessage));
        assertTrue(fileContent.contains("[INFO]"));
    }

    /**
     * Tests error message logging functionality.
     *
     * @throws IOException If an I/O error occurs.
     */
    @Test
    void shouldWriteErrorLogMessage() throws IOException {
        Logger logger = Logger.getInstance();
        String testMessage = "Test error message";
        logger.error(testMessage);
        String fileContent = Files.readString(logFile.toPath());
        assertTrue(fileContent.contains(testMessage));
        assertTrue(fileContent.contains("[ERROR]"));
    }

    /**
     * Tests debug message logging functionality.
     *
     * @throws IOException If an I/O error occurs.
     */
    @Test
    void shouldWriteDebugLogMessage() throws IOException {
        Logger logger = Logger.getInstance();
        String testMessage = "Test debug message";
        logger.debug(testMessage);
        String fileContent = Files.readString(logFile.toPath());
        assertTrue(fileContent.contains(testMessage));
        assertTrue(fileContent.contains("[DEBUG]"));
    }

    /**
     * Tests warning message logging functionality.
     *
     * @throws IOException If an I/O error occurs.
     */
    @Test
    void shouldWriteWarnLogMessage() throws IOException {
        Logger logger = Logger.getInstance();
        String testMessage = "Test warning message";
        logger.warn(testMessage);
        String fileContent = Files.readString(logFile.toPath());
        assertTrue(fileContent.contains(testMessage));
        assertTrue(fileContent.contains("[WARN]"));
    }

    /**
     * Verifies that the Logger implements the singleton pattern correctly.
     *
     * @throws IOException If an I/O error occurs.
     */
    @Test
    void shouldMaintainSingletonInstance() throws IOException {
        Logger logger1 = Logger.getInstance();
        Logger logger2 = Logger.getInstance();
        assertSame(logger1, logger2);
    }

    /**
     * Tests that multiple log messages are properly appended to the file.
     *
     * @throws IOException If an I/O error occurs.
     */
    @Test
    void shouldAppendMultipleMessages() throws IOException {
        Logger logger = Logger.getInstance();
        String[] messages = {"First message", "Second message", "Third message"};
        for (String message : messages) {
            logger.info(message);
        }
        String fileContent = Files.readString(logFile.toPath());
        for (String message : messages) {
            assertTrue(fileContent.contains(message));
        }
        long lineCount = Files.lines(logFile.toPath()).count();
        assertEquals(messages.length, lineCount);
    }
}

