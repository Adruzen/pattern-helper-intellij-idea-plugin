package es.uma.patternhelper;

import com.intellij.openapi.components.Service;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.startup.StartupActivity;
import es.uma.patternhelper.logger.Logger;
import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.io.IOException;

@Service(Service.Level.APP)
public final class PluginInitializer implements StartupActivity {

    private static boolean isInitialized = false;

    public PluginInitializer() throws IOException {
        if (!isInitialized) {
            isInitialized = true;

            // Then get Logger instance
            Logger logger = Logger.getInstance();
            logger.info("Pattern Helper Plugin initialized.");
        }
    }

    public static void initialize(File localProperties, File sharedProperties) throws IOException {
        if (!isInitialized) {
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
            isInitialized = true;
        }
        try {
            Logger.getInstance().info("Plugin initialized for project: " + project.getName());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
