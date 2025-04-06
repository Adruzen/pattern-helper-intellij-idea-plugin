/**
 * Initializes the Pattern Helper Plugin.
 * This class is an ApplicationComponent that sets up the logger when the plugin is loaded.
 */

package es.uma.patternhelper;

import com.intellij.openapi.components.ApplicationComponent;
import es.uma.patternhelper.logger.Logger;


public class PluginInitializer implements ApplicationComponent {

    /**
     * Initializes the component.
     * Sets the log file and logs an initialization message.
     */
    @Override
    public void initComponent() {
        Logger logger = Logger.getInstance(PluginInitializer.class);
        logger.setLogFile("pattern_helper.log");
        logger.info("Pattern Helper Plugin initialized.");
    }
}