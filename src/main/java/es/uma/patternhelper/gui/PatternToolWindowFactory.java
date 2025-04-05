package es.uma.patternhelper.gui;

import com.intellij.openapi.project.Project;
import com.intellij.openapi.wm.ToolWindow;
import com.intellij.openapi.wm.ToolWindowFactory;
import com.intellij.ui.content.Content;
import com.intellij.ui.content.ContentFactory;
import org.jetbrains.annotations.NotNull;


public class PatternToolWindowFactory  implements ToolWindowFactory {


    @Override
    public void createToolWindowContent(@NotNull Project project, @NotNull ToolWindow toolWindow) {

        // 1. Create your tool gui's content panel
        PatternToolWindow patternHelperToolWindow = new PatternToolWindow(project);

        // 2. Create a content instance to hold your panel
        ContentFactory contentFactory = ContentFactory.getInstance();
        Content content = contentFactory.createContent(
            patternHelperToolWindow.getContent(), // the component to display
            "", // tab name (empty string if you don't need tabs)
            false // is lockable?
        );

        // 3. Add the content to the tool gui
        toolWindow.getContentManager().addContent(content);
    }

    @Override
    public boolean shouldBeAvailable(@NotNull Project project) {
        return true;
    }
}
