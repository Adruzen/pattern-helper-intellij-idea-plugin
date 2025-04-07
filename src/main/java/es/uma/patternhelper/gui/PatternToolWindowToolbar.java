package es.uma.patternhelper.gui;

import com.intellij.openapi.ui.Messages;
import com.intellij.ui.components.JBPanel;
import com.intellij.util.ui.JBUI;
import es.uma.patternhelper.logger.Logger;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class PatternToolWindowToolbar extends JBPanel<PatternToolWindowToolbar> {
    private final List<JButton> buttons = new ArrayList<>();

    /**
     * Creates a new Pattern Tool Window Toolbar.
     * Initializes the toolbar with a horizontal BoxLayout and adds buttons.
     */
    public PatternToolWindowToolbar() {
        setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
        setBorder(BorderFactory.createCompoundBorder(BorderFactory.createLineBorder(Color.BLACK), JBUI.Borders.empty(2, 5)));        createButtons();
    }

    /**
     * Creates and adds buttons to the toolbar.
     * This method now dynamically determines the number of buttons based on available icon resources.
     */
    private void createButtons() {
        int i = 1;
        while (true) {
            String iconPath = "/icons/icons-side-panel-" + String.format("%02d", i) + ".svg"; // Format with leading zero
            Icon icon = loadIcon(iconPath);
            if (icon != null) {
                JButton button = createIconButton(icon, "Button " + i);
                addButton(button);
                i++;
            } else {
                break; // Stop when no more icons are found
            }
        }
    }

    /**
     * Loads an icon from the given path.
     *
     * @param iconPath The path to the icon resource.
     * @return The loaded Icon, or null if the icon could not be loaded.
     */
    private Icon loadIcon(String iconPath) {
        try {
            return new ImageIcon(Objects.requireNonNull(getClass().getResource(iconPath)));
        } catch (Exception e) {
            Logger.getInstance().error("Error loading icon: " + iconPath);
            System.err.println("Error loading icon: " + iconPath);
            e.printStackTrace();
            return null;
        }
    }

    /**
     * Creates a button with an icon and an action listener.
     *
     * @param icon    The icon to display on the button.
     * @param tooltip The tooltip text for the button.
     * @return JButton The created button.
     */
    private JButton createIconButton(Icon icon, String tooltip) {
        JButton button = new JButton(icon);
        button.setToolTipText(tooltip);
        button.setBorderPainted(true);
        button.setContentAreaFilled(false);
        button.setFocusPainted(false);
        button.setPreferredSize(new Dimension(16, 16));
        // button.setMaximumSize(new Dimension(16, 16));
        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Replace this with your actual action
                Messages.showMessageDialog("Button clicked!", "Action", Messages.getInformationIcon());
            }
        });
        return button;
    }

    /**
     * Adds a button to the toolbar.
     *
     * @param button The button to add.
     */
    private void addButton(JButton button) {
        buttons.add(button);
        add(button);
    }
}