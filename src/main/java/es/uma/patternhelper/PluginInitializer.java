package es.uma.patternhelper;

import com.intellij.openapi.components.Service;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.startup.StartupActivity;
import es.uma.patternhelper.logger.Logger;
import org.jetbrains.annotations.NotNull;

import java.io.File;

@Service(Service.Level.APP)
public final class PluginInitializer implements StartupActivity {

    private static boolean isInitialized = false;
    private static final String DEFAULT_LOCAL_PROPERTIES = "local.properties";
    private static final String DEFAULT_SHARED_PROPERTIES = "shared.properties";

    public PluginInitializer() {
        if (!isInitialized) {
            // Initialize with default properties files
            File localProperties = new File(DEFAULT_LOCAL_PROPERTIES);
            File sharedProperties = new File(DEFAULT_SHARED_PROPERTIES);

            // First initialize ConfigConstants
            ConfigConstants.initialize(localProperties, sharedProperties);
            isInitialized = true;

            // Then get Logger instance
            Logger logger = Logger.getInstance();
            logger.info("Pattern Helper Plugin initialized.");
        }
    }

    public static void initialize(File localProperties, File sharedProperties) {
        if (!isInitialized) {
            ConfigConstants.initialize(localProperties, sharedProperties);
            isInitialized = true;
            Logger.getInstance().info("Pattern Helper Plugin initialized with custom properties.");
        }
    }

    public static PluginInitializer getInstance() {
        return com.intellij.openapi.application.ApplicationManager.getApplication()
                .getService(PluginInitializer.class);
    }

    @Override
    public void runActivity(@NotNull Project project) {
        if (!isInitialized) {
            File localProperties = new File(DEFAULT_LOCAL_PROPERTIES);
            File sharedProperties = new File(DEFAULT_SHARED_PROPERTIES);
            ConfigConstants.initialize(localProperties, sharedProperties);
            isInitialized = true;
        }
        Logger.getInstance().info("Plugin initialized for project: " + project.getName());
    }
}
