package es.uma.patternhelper.gui;

import com.intellij.testFramework.fixtures.BasePlatformTestCase;
import com.intellij.openapi.wm.ToolWindow;
import com.intellij.openapi.wm.ToolWindowAnchor;
import com.intellij.openapi.wm.ToolWindowManager;
import com.intellij.ui.content.Content;

public class PatternToolWindowTest extends BasePlatformTestCase {

    private ToolWindow toolWindow;

    @Override
    protected void setUp() throws Exception {
        super.setUp();

        // Register the tool window for testing
        ToolWindowManager toolWindowManager = ToolWindowManager.getInstance(getProject());
        toolWindow = toolWindowManager
                .registerToolWindow("Pattern Helper", false, ToolWindowAnchor.RIGHT);

        // Create and set content
        PatternToolWindowFactory factory = new PatternToolWindowFactory();
        factory.createToolWindowContent(getProject(), toolWindow);

    }

    @Override
    protected String getTestDataPath() {
        return "src/test/resources";
    }

    public void testToolWindowIsCreated() {
        // Wait for the tool window to be registered
        ToolWindowManager toolWindowManager = ToolWindowManager.getInstance(getProject());
        ToolWindow toolWindow = toolWindowManager.getToolWindow("Pattern Helper");

        // Basic assertions
        assertNotNull("Tool window should be registered", toolWindow);
        String anchor = toolWindow.getAnchor().toString();

        // Check content
        Content[] contents = toolWindow.getContentManager().getContents();
        assertEquals("Tool window should have one content panel", 1, contents.length);

        // Verify the content is our panel
        assertTrue("Content should be JBPanel",
                  contents[0].getComponent() instanceof com.intellij.ui.components.JBPanel);
    }

    public void testToolWindowInitialState() {
        ToolWindowManager toolWindowManager = ToolWindowManager.getInstance(getProject());
        ToolWindow toolWindow = toolWindowManager.getToolWindow("Pattern Helper");

        assertFalse("Tool window should not be visible by default",
                    toolWindow.isVisible());
    }
}
