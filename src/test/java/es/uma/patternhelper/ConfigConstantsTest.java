package es.uma.patternhelper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Path;

import static org.junit.jupiter.api.Assertions.*;

class ConfigConstantsTest {
    @TempDir
    Path tempDir;

    private static final String LOCAL_CONSTANT = "MY_LOCAL_PATH";
    private static final String SHARED_CONSTANT = "MY_SHARED_PATH";
    private static final String NONEXISTENT_CONSTANT = "DOES_NOT_EXIST";

    private File localPropertiesFile;
    private File sharedPropertiesFile;

    @BeforeEach
    void setUp() throws IOException {
        // Create test properties files
        localPropertiesFile = tempDir.resolve("local.properties").toFile();
        sharedPropertiesFile = tempDir.resolve("shared.properties").toFile();

        // Write test data to shared.properties
        try (FileWriter writer = new FileWriter(sharedPropertiesFile)) {
            writer.write(SHARED_CONSTANT + "=/shared/path/value\n");
        }

        // Write test data to local.properties
        try (FileWriter writer = new FileWriter(localPropertiesFile)) {
            writer.write(LOCAL_CONSTANT + "=/local/path/value\n");
            // Override shared constant in local
            writer.write(SHARED_CONSTANT + "=/local/override/value\n");
        }

        // Initialize ConfigConstants with test files
        ConfigConstants.initialize(localPropertiesFile, sharedPropertiesFile);
    }

    @Test
    void shouldGetValueFromLocalProperties() {
        String value = ConfigConstants.get(LOCAL_CONSTANT);
        assertEquals("/local/path/value", value);
    }

    @Test
    void shouldGetValueFromSharedProperties() {
        // Delete local.properties to ensure we're reading from shared
        localPropertiesFile.delete();
        ConfigConstants.initialize(localPropertiesFile, sharedPropertiesFile);

        String value = ConfigConstants.get(SHARED_CONSTANT);
        assertEquals("/shared/path/value", value);
    }

    @Test
    void shouldPreferLocalOverSharedProperties() {
        String value = ConfigConstants.get(SHARED_CONSTANT);
        assertEquals("/local/override/value", value);
    }

    @Test
    void shouldReturnNullForNonexistentConstant() {
        String value = ConfigConstants.get(NONEXISTENT_CONSTANT);
        assertNull(value);
    }

    @Test
    void shouldHandleMissingLocalProperties() {
        // Delete local.properties
        localPropertiesFile.delete();
        ConfigConstants.initialize(localPropertiesFile, sharedPropertiesFile);

        String value = ConfigConstants.get(SHARED_CONSTANT);
        assertNotNull(value);
        assertEquals("/shared/path/value", value);
    }

    @Test
    void shouldHandleMissingSharedProperties() {
        // Delete shared.properties
        sharedPropertiesFile.delete();
        ConfigConstants.initialize(localPropertiesFile, sharedPropertiesFile);

        String value = ConfigConstants.get(LOCAL_CONSTANT);
        assertNotNull(value);
        assertEquals("/local/path/value", value);
    }

    @Test
    void shouldHandleMissingBothPropertiesFiles() {
        // Delete both properties files
        localPropertiesFile.delete();
        sharedPropertiesFile.delete();
        ConfigConstants.initialize(localPropertiesFile, sharedPropertiesFile);

        String value = ConfigConstants.get(LOCAL_CONSTANT);
        assertNull(value);
    }

    @Test
    void shouldThrowExceptionWhenNotInitialized() {
        ConfigConstants.initialize(null, null);
        assertThrows(IllegalStateException.class, () -> {
            ConfigConstants.get(LOCAL_CONSTANT);
        });
    }
}
