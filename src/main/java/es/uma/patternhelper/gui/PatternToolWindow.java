package es.uma.patternhelper.gui;

import com.intellij.openapi.project.Project;
import com.intellij.ui.components.JBPanel;
import java.awt.*;

public class PatternToolWindow {
    private final JBPanel<JBPanel<?>> mainPanel;
    private final Project project;

    public PatternToolWindow(Project project) {
        this.project = project;
        this.mainPanel = new JBPanel<>();
        mainPanel.setLayout(new BorderLayout());  // You can set any layout you prefer
    }

    public JBPanel<?> getContent() {
        return mainPanel;
    }
}
