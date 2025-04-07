package es.uma.patternhelper;

import com.intellij.openapi.project.Project;
import com.intellij.openapi.startup.ProjectActivity;
import es.uma.patternhelper.logger.Logger;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.IOException;

public final class PluginInitializer implements ProjectActivity {

    @Override
    public @Nullable Object execute(@NotNull Project project, @NotNull Continuation<? super Unit> continuation) {
        Logger logger;
        try {
            logger = Logger.getInstance();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        logger.info("Pattern Helper Plugin initialized.");
        return null;
    }
}
