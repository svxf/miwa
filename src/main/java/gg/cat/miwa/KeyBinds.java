package gg.cat.miwa;

import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.minecraft.client.option.KeyBinding;
import org.lwjgl.glfw.GLFW;

public class KeyBinds {
    private static KeyBinds Instance;

    KeyBinding kbRecord;
    KeyBinding kbPlay;
    KeyBinding kbOverride;
    KeyBinding kbShowMenu;
    KeyBinding kbAutoGetSave;

    private KeyBinds() {
        createKeybinds();
    }

    private void createKeybinds() {
        kbRecord = new KeyBinding("gg.miwa.keybind.record", GLFW.GLFW_KEY_R, Miwa.MOD_NAME);
        kbPlay = new KeyBinding("gg.miwa.keybind.play", GLFW.GLFW_KEY_P, Miwa.MOD_NAME);
        kbOverride = new KeyBinding("gg.miwa.keybind.override", GLFW.GLFW_MOUSE_BUTTON_4, Miwa.MOD_NAME);
        kbShowMenu = new KeyBinding("gg.miwa.keybind.showMenu", GLFW.GLFW_KEY_M, Miwa.MOD_NAME);
        kbAutoGetSave = new KeyBinding("gg.miwa.keybind.autoGetSave", GLFW.GLFW_KEY_N, Miwa.MOD_NAME);
    }

    public void registerKeybinds() {
        KeyBindingHelper.registerKeyBinding(kbRecord);
        KeyBindingHelper.registerKeyBinding(kbPlay);
        KeyBindingHelper.registerKeyBinding(kbOverride);
        KeyBindingHelper.registerKeyBinding(kbShowMenu);
        KeyBindingHelper.registerKeyBinding(kbAutoGetSave);
    }

    public static KeyBinds getKeyBinds() {
        if (Instance == null)
            Instance = new KeyBinds();

        return Instance;
    }
}
