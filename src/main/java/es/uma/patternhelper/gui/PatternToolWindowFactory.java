/**
 * Factory class for creating the Pattern Helper tool window.
 * Implements ToolWindowFactory to integrate with IntelliJ's tool window system.
 *
 * @author akud
 */

package es.uma.patternhelper.gui;

import com.intellij.openapi.project.Project;
import com.intellij.openapi.wm.ToolWindow;
import com.intellij.openapi.wm.ToolWindowFactory;
import com.intellij.ui.content.Content;
import com.intellij.ui.content.ContentFactory;
import org.jetbrains.annotations.NotNull;


public class PatternToolWindowFactory implements ToolWindowFactory {

    /**
     * Creates and initializes the content for the Pattern Helper tool window.
     * Sets up the main panel and adds it to the tool window's content manager.
     *
     * @param project The current IDE project
     * @param toolWindow The tool window to populate with content
     */
    @Override
    public void createToolWindowContent(@NotNull Project project, @NotNull ToolWindow toolWindow) {
        // 1. Create your tool window's content panel
        PatternToolWindow patternHelperToolWindow = new PatternToolWindow(project);

        // 2. Create a content instance to hold your panel
        ContentFactory contentFactory = ContentFactory.getInstance();
        Content content = contentFactory.createContent(
            patternHelperToolWindow.getContent(),   // the component to display
            "",                                     // tab name (empty string if you don't need tabs)
            false                                   // is lockable?
        );

        // 3. Add the content to the tool window
        toolWindow.getContentManager().addContent(content);
    }

    /**
     * Determines if the tool window should be available in the current project.
     * Currently always returns true to show the tool window in all projects.
     *
     * @param project The current IDE project
     * @return boolean Always returns true
     */
    @Override
    public boolean shouldBeAvailable(@NotNull Project project) {
        return true;
    }
}
