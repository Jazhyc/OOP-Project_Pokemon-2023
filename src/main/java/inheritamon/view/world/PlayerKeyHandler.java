package inheritamon.view.world;

import javax.swing.*;

import inheritamon.view.SoundHandler;
import inheritamon.view.world.sidebar.SidePanel;

import java.awt.event.*;
import java.util.*;

public class PlayerKeyHandler {

    private boolean upPressed, downPressed, leftPressed, rightPressed;

    SidePanel sidePanel;

    private SoundHandler soundHandler;

    
    public PlayerKeyHandler(JComponent component, SidePanel sidePanel) {
        InputMap inputMap = component.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW);
        ActionMap actionMap = component.getActionMap();

        soundHandler = SoundHandler.getInstance();

        this.sidePanel = sidePanel;

        // It appears that using a modifier like shift or ctrl is not possible with this method

        // Define the key bindings and actions
        Map<Integer, String> keyBindings = new HashMap<>();
        keyBindings.put(KeyEvent.VK_W, "up");
        keyBindings.put(KeyEvent.VK_S, "down");
        keyBindings.put(KeyEvent.VK_A, "left");
        keyBindings.put(KeyEvent.VK_D, "right");
        keyBindings.put(KeyEvent.VK_ESCAPE, "escape");

        // Add support for arrow keys as well
        keyBindings.put(KeyEvent.VK_UP, "up");
        keyBindings.put(KeyEvent.VK_DOWN, "down");
        keyBindings.put(KeyEvent.VK_LEFT, "left");
        keyBindings.put(KeyEvent.VK_RIGHT, "right");

        // Bind the keys to their corresponding actions using a loop
        for (Map.Entry<Integer, String> entry : keyBindings.entrySet()) {
            int keyCode = entry.getKey();
            String actionName = entry.getValue();

            // Bind the key press and release events to their corresponding actions
            inputMap.put(KeyStroke.getKeyStroke(keyCode, 0), actionName + " pressed");
            inputMap.put(KeyStroke.getKeyStroke(keyCode, 0, true), actionName + " released");

            // Account for modifiers as well
            // Ugly, but it works
            inputMap.put(KeyStroke.getKeyStroke(keyCode, InputEvent.SHIFT_DOWN_MASK), actionName + " pressed");
            inputMap.put(KeyStroke.getKeyStroke(keyCode, InputEvent.SHIFT_DOWN_MASK, true), actionName + " released");
            inputMap.put(KeyStroke.getKeyStroke(keyCode, InputEvent.CTRL_DOWN_MASK), actionName + " pressed");
            inputMap.put(KeyStroke.getKeyStroke(keyCode, InputEvent.CTRL_DOWN_MASK, true), actionName + " released");
            inputMap.put(KeyStroke.getKeyStroke(keyCode, InputEvent.ALT_DOWN_MASK), actionName + " pressed");
            inputMap.put(KeyStroke.getKeyStroke(keyCode, InputEvent.ALT_DOWN_MASK, true), actionName + " released");

            // Define the actions for the key press and release events
            actionMap.put(actionName + " pressed", new AbstractAction() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    setKeyState(actionName, true);
                }
            });
            actionMap.put(actionName + " released", new AbstractAction() {
                @Override
                public void actionPerformed(ActionEvent e) {

                    // Ignore if escape is released
                    if (actionName.equals("escape")) {
                        return;
                    }

                    setKeyState(actionName, false);
                }
            });
        }
        
    }

private void setKeyState(String actionName, boolean state) {
    switch (actionName) {
        case "up":
            upPressed = state;
            break;
        case "down":
            downPressed = state;
            break;
        case "left":
            leftPressed = state;
            break;
        case "right":
            rightPressed = state;
            break;
        case "escape":
            sidePanel.setVisible(!sidePanel.isVisible());
            sidePanel.hidePokemonDataPanel();
            soundHandler.playSound("select");
            break;
    }
}
    public boolean isUpPressed() {
        return !sidePanel.isVisible() && upPressed;
    }

    public boolean isDownPressed() {
        return !sidePanel.isVisible() && downPressed;
    }

    public boolean isLeftPressed() {
        return !sidePanel.isVisible() && leftPressed;
    }

    public boolean isRightPressed() {
        return !sidePanel.isVisible() && rightPressed;
    }
}
