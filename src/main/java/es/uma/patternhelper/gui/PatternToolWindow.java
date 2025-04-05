/**
 * Main tool window class for the Pattern Helper plugin.
 * Manages the UI panel that displays pattern-related information in the IDE.
 *
 * @author Your Team Name
 */

package es.uma.patternhelper.gui;

import com.intellij.openapi.project.Project;
import com.intellij.ui.components.JBPanel;
import java.awt.*;

public class PatternToolWindow {
    private final JBPanel<JBPanel<?>> mainPanel;
    private final Project project;

    /**
     * Creates a new Pattern Tool Window with the specified project context.
     * Initializes the main panel with a BorderLayout.
     *
     * @param project The IDE project this tool window belongs to
     */
    public PatternToolWindow(Project project) {
        this.project = project;
        this.mainPanel = new JBPanel<>();
        mainPanel.setLayout(new BorderLayout());  // You can set any layout you prefer
    }

    /**
     * Returns the main content panel of the tool window.
     *
     * @return JBPanel<?> The main panel containing the tool window content
     */
    public JBPanel<?> getContent() {
        return mainPanel;
    }
}
