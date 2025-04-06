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

    /**
     * Tests whether the 'Logo-side-panel_dark.svg' icon is loaded correctly.
     * This method attempts to load the icon using IconLoader and asserts that the loaded icon is not null.
     *
     * @throws AssertionError If the icon cannot be loaded.
     */
    public void testDarkIconIsLoaded() {
        Icon icon = IconLoader.getIcon("/icons/Logo-side-panel_dark.svg", IconLoadingTest.class);
        assertNotNull("Icon should be loaded", icon);
    }

    /**
     * Tests whether the 'Logo-side-panel_light.svg' icon is loaded correctly.
     * This method attempts to load the icon using IconLoader and asserts that the loaded icon is not null.
     *
     * @throws AssertionError If the icon cannot be loaded.
     */
    public void testLightIconIsLoaded() {
        Icon icon = IconLoader.getIcon("/icons/Logo-side-panel_light.svg", IconLoadingTest.class);
        assertNotNull("Icon should be loaded", icon);
    }


    /**
     * Tests whether the 'icons-side-panel-XX.svg' icons are loaded correctly.
     * This method attempts to load the icon using IconLoader and asserts that the loaded icon is not null.
     *
     * @throws AssertionError If the icon cannot be loaded.
     */
    public void testSidePanelIconsAreLoaded() {
        int i = 1;
        while (true) {
            String iconPath = "/icons/icons-side-panel-" + String.format("%02d", i) + ".svg";
            if (getClass().getResource(iconPath) != null) {
                Icon icon = IconLoader.getIcon(iconPath, IconLoadingTest.class);
                assertNotNull("Icon " + iconPath + " should be loaded", icon);
                i++;
            } else {
                break;
            }
        }
    }
}