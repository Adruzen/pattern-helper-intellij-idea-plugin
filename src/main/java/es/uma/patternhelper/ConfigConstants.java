package es.uma.patternhelper;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public final class ConfigConstants {
    private static Properties localProperties = new Properties();
    private static Properties sharedProperties = new Properties();
    private static boolean isInitialized = false;

    private ConfigConstants() {
        // Prevent instantiation
    }

    public static void initialize(File localPropertiesFile, File sharedPropertiesFile) {
        // Clear existing properties
        localProperties.clear();
        sharedProperties.clear();

        if (localPropertiesFile == null && sharedPropertiesFile == null) {
            isInitialized = false;
            return;
        }

        // Load shared properties if file exists
        if (sharedPropertiesFile != null && sharedPropertiesFile.exists()) {
            try (FileInputStream fis = new FileInputStream(sharedPropertiesFile)) {
                sharedProperties.load(fis);
            } catch (IOException e) {
                // Log error but continue - shared properties are optional
                System.err.println("Error loading shared properties: " + e.getMessage());
            }
        }

        // Load local properties if file exists
        if (localPropertiesFile != null && localPropertiesFile.exists()) {
            try (FileInputStream fis = new FileInputStream(localPropertiesFile)) {
                localProperties.load(fis);
            } catch (IOException e) {
                // Log error but continue - local properties are optional
                System.err.println("Error loading local properties: " + e.getMessage());
            }
        }

        isInitialized = true;
    }

    public static String get(String constantName) {
        if (!isInitialized) {
            throw new IllegalStateException("ConfigConstants has not been initialized");
        }

        // First try local properties
        String value = localProperties.getProperty(constantName);

        // If not found in local, try shared properties
        if (value == null) {
            value = sharedProperties.getProperty(constantName);
        }

        return value;
    }

    public static boolean isInicialised() {
        return isInitialized;
    }
}
