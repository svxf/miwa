package gg.cat.miwa;

import net.fabricmc.api.ClientModInitializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Miwa implements ClientModInitializer {


    public static final String MOD_NAME = "Miwa";
    public static final String MOD_ID = "miwa";
    public static final String MOD_VERSION = "1.0.0-1.21";
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_NAME);

    @Override
    public void onInitializeClient() {
        EventHandler.registerEventHandlers();
        KeyBinds.getKeyBinds().registerKeybinds();
    }
}
