/**
 * Test class for PluginInitializer.
 * This class tests the initialization of the plugin, specifically the logger setup.
 */

package es.uma.patternhelper;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.nio.file.Files;
import java.nio.file.Paths;


public class PluginInitializerTest {

    /**
     * Tests the initialization of the component.
     * Verifies that the log file is created and the initialization message is logged.
     */
    @Test
    void testInitComponent() throws Exception {
        // Redirect System.out to capture output
        PrintStream originalOut = System.out;
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        System.setOut(new PrintStream(bos));

        // Initialize the component
        PluginInitializer initializer = new PluginInitializer();
        initializer.initComponent();

        // Check if log file was created
        assertTrue(Files.exists(Paths.get("pattern_helper.log")));

        // Check if the log message was printed
        String output = bos.toString();
        assertTrue(output.contains("Pattern Helper Plugin initialized."));

        // Clean up: Restore System.out and delete log file
        System.setOut(originalOut);
        Files.delete(Paths.get("pattern_helper.log"));
    }
}