package es.uma.patternhelper.logger;

import es.uma.patternhelper.ConfigConstants;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Test suite for Logger functionality.
 */
class LoggerTest {
    @TempDir
    Path tempDir;

    private File localPropertiesFile;
    private File sharedPropertiesFile;
    private File logFile;
    private File logDir;

    /**
     * Sets up the test environment before each test.
     */
    @BeforeEach
    void setUp() throws IOException {
        // Reset Logger instance to ensure clean state
        Logger.resetInstance();

        // Create test properties files
        localPropertiesFile = tempDir.resolve("local.properties").toFile();
        sharedPropertiesFile = tempDir.resolve("shared.properties").toFile();

        // Create logs directory
        logDir = tempDir.resolve("logs").toFile();
        if (!logDir.exists()) {
            assertTrue(logDir.mkdirs(), "Failed to create log directory");
        }

        // Define log file with absolute path
        logFile = logDir.toPath().resolve("patternhelper.log").toFile();
        String absoluteLogPath = logFile.getAbsolutePath().replace('\\', '/');

        // Set up shared.properties with absolute log path
        try (FileWriter writer = new FileWriter(sharedPropertiesFile)) {
            writer.write("LOG_FILE_PATH=" + absoluteLogPath);
        }

        // Initialize ConfigConstants with test files
        ConfigConstants.initialize(localPropertiesFile, sharedPropertiesFile);
    }

    /**
     * Verifies that ConfigConstants is properly initialized.
     */
    @Test
    void shouldInitializeConfigConstants() {
        String logPath = ConfigConstants.get("LOG_FILE_PATH");
        assertNotNull(logPath, "Log path should be available after initialization");
        assertEquals(logFile.getAbsolutePath().replace('\\', '/'), logPath,
                "Log path should match the configured path");
    }

    /**
     * Tests basic info message logging functionality.
     */
    @Test
    void shouldWriteInfoLogMessage() throws IOException {
        Logger logger = Logger.getInstance();
        String testMessage = "Test info message";
        logger.info(testMessage);

        assertTrue(logFile.exists(), "Log file should exist at: " + logFile.getAbsolutePath());
        String fileContent = Files.readString(logFile.toPath());
        assertTrue(fileContent.contains(testMessage), "Log file should contain the test message");
        assertTrue(fileContent.contains("[INFO]"), "Log file should contain the INFO level");
    }

    /**
     * Tests error message logging functionality.
     */
    @Test
    void shouldWriteErrorLogMessage() throws IOException {
        Logger logger = Logger.getInstance();
        String testMessage = "Test error message";
        logger.error(testMessage);

        assertTrue(logFile.exists(), "Log file should exist at: " + logFile.getAbsolutePath());
        String fileContent = Files.readString(logFile.toPath());
        assertTrue(fileContent.contains(testMessage), "Log file should contain the test message");
        assertTrue(fileContent.contains("[ERROR]"), "Log file should contain the ERROR level");
    }

    /**
     * Tests debug message logging functionality.
     */
    @Test
    void shouldWriteDebugLogMessage() throws IOException {
        Logger logger = Logger.getInstance();
        String testMessage = "Test debug message";
        logger.debug(testMessage);

        assertTrue(logFile.exists(), "Log file should exist at: " + logFile.getAbsolutePath());
        String fileContent = Files.readString(logFile.toPath());
        assertTrue(fileContent.contains(testMessage), "Log file should contain the test message");
        assertTrue(fileContent.contains("[DEBUG]"), "Log file should contain the DEBUG level");
    }

    /**
     * Tests warning message logging functionality.
     */
    @Test
    void shouldWriteWarnLogMessage() throws IOException {
        Logger logger = Logger.getInstance();
        String testMessage = "Test warning message";
        logger.warn(testMessage);

        assertTrue(logFile.exists(), "Log file should exist at: " + logFile.getAbsolutePath());
        String fileContent = Files.readString(logFile.toPath());
        assertTrue(fileContent.contains(testMessage), "Log file should contain the test message");
        assertTrue(fileContent.contains("[WARN]"), "Log file should contain the WARN level");
    }

    /**
     * Verifies that the Logger implements the singleton pattern correctly.
     */
    @Test
    void shouldMaintainSingletonInstance() {
        Logger logger1 = Logger.getInstance();
        Logger logger2 = Logger.getInstance();
        assertSame(logger1, logger2, "Multiple Logger.getInstance() calls should return the same instance");
    }


    /**
     * Tests that multiple log messages are properly appended to the file.
     */
    @Test
    void shouldAppendMultipleMessages() throws IOException {
        Logger logger = Logger.getInstance();
        String[] messages = {
                "First message",
                "Second message",
                "Third message"
        };

        for (String message : messages) {
            logger.info(message);
        }

        String fileContent = Files.readString(logFile.toPath());
        for (String message : messages) {
            assertTrue(fileContent.contains(message),
                    "Log file should contain message: " + message);
        }

        long lineCount = Files.lines(logFile.toPath()).count();
        assertEquals(messages.length, lineCount,
                "Log file should contain exactly one line per message");
    }

}
