package es.uma.patternhelper;

import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.ui.Messages;

public class HelloWorldAction extends AnAction {
    @Override
    public void actionPerformed(AnActionEvent e) {
        Messages.showMessageDialog("Hello from your IntelliJ Plugin!", "Greeting", Messages.getInformationIcon());
    }
}