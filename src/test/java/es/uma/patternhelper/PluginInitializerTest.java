package es.uma.patternhelper;

import es.uma.patternhelper.logger.Logger;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

class PluginInitializerTest {
    @Test
    void shouldInitializeLogger() throws IOException {
        PluginInitializer test = new PluginInitializer();
        assertNotNull(Logger.getInstance());
    }
}