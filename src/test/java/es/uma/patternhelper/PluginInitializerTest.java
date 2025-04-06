package es.uma.patternhelper;

import es.uma.patternhelper.logger.Logger;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class PluginInitializerTest {
    @Test
    void shouldInitializeLoggerAndConfigConstants() {
        PluginInitializer test = new PluginInitializer();
        assertNotNull(Logger.getInstance());
        assertNull(ConfigConstants.get("ANY_KEY"));
    }
}