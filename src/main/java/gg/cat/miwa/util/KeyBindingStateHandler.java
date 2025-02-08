package gg.cat.miwa.util;

import gg.cat.miwa.mixin.KeyBindingMixins;
import net.minecraft.client.option.KeyBinding;

public class KeyBindingStateHandler {
    private boolean isPressed = false;
    private boolean lastIsKeyDown = false;
    private boolean isKeyDown = false;

    private boolean capture = false;

    public void tick(boolean keyDown) {
        isKeyDown = isPressed = keyDown;
    }

    public void modifyKeyBindingState(KeyBinding keyBinding) {
        keyBinding.setPressed(isKeyDown);

        if (lastIsKeyDown == isKeyDown)
            isPressed = false;

        ((KeyBindingMixins) keyBinding).setTimesPressed(isPressed ? 1 : 0);

        lastIsKeyDown = isKeyDown;
    }

    public void reset() {
        isPressed = false;
        lastIsKeyDown = false;
        isKeyDown = false;
    }

    public void capturePress(KeyBinding keyBinding) {
        capture = keyBinding.isPressed();
    }

    public boolean getCapture() {
        return capture;
    }
}
