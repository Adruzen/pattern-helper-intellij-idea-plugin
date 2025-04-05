/**
 * Test class for verifying the loading of the custom icon used in the Pattern Helper plugin.
 * This class ensures that the 'Logo-side-panel.svg' icon can be loaded successfully.
 *
 * @author akud
 */
package es.uma.patternhelper.gui;

import com.intellij.openapi.util.IconLoader;
import com.intellij.testFramework.fixtures.BasePlatformTestCase;

import javax.swing.*;

public class IconLoadingTest extends BasePlatformTestCase {

    /**
     * Tests whether the 'Logo-side-panel.svg' icon is loaded correctly.
     * This method attempts to load the icon using IconLoader and asserts that the loaded icon is not null.
     *
     * @throws AssertionError If the icon cannot be loaded.
     */
    public void testIconIsLoaded() {
        Icon icon = IconLoader.getIcon("/icons/Logo-side-panel.svg", IconLoadingTest.class);
        assertNotNull("Icon should be loaded", icon);
    }
}