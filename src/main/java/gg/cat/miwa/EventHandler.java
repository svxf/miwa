package gg.cat.miwa;

import gg.cat.miwa.parkour.ParkourSession;
import gg.cat.miwa.parkour.PlaybackSession;
import gg.cat.miwa.parkour.Recording;
import gg.cat.miwa.parkour.RecordingSession;
import gg.cat.miwa.pignet.AutoGetSave;
import gg.cat.miwa.ui.MenuScreen;
import gg.cat.miwa.util.KeyBindingStateHandler;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientLifecycleEvents;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.rendering.v1.WorldRenderContext;
import net.fabricmc.fabric.api.client.rendering.v1.WorldRenderEvents;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.option.GameOptions;
import net.minecraft.util.hit.BlockHitResult;

import java.util.ArrayList;

public class EventHandler {
    public static final int MAX_HISTORY_LENGTH = 16;
    public static final MinecraftClient mc = MinecraftClient.getInstance();
    public static ArrayList<Recording> recordHistory = new ArrayList<>();
    public static KeyBindingStateHandler attackHandler = new KeyBindingStateHandler();
    public static KeyBindingStateHandler useHandler = new KeyBindingStateHandler();
    public static BlockHitResult hitResult = null;
    public static ParkourSession session = null;

    public static void addToHistory(Recording recording) {
        if (recordHistory.size() >= MAX_HISTORY_LENGTH) {
            recordHistory.remove(0);
        }
        recordHistory.add(recording);
    }

    public static void registerEventHandlers() {
        ClientTickEvents.START_CLIENT_TICK.register(EventHandler::onTick);
        ClientLifecycleEvents.CLIENT_STARTED.register(EventHandler::onClientStarted);
        ClientLifecycleEvents.CLIENT_STOPPING.register(EventHandler::onClientStopping);
        WorldRenderEvents.START.register(EventHandler::onStartRenderWorld);
        WorldRenderEvents.END.register(EventHandler::onEndRenderWorld);
    }

    public static void onHandleInputEvents() {
        GameOptions gameSettings = mc.options;

        hitResult = mc.crosshairTarget != null && mc.crosshairTarget instanceof BlockHitResult ? (BlockHitResult) mc.crosshairTarget : null;
        attackHandler.capturePress(gameSettings.attackKey);
        useHandler.capturePress(gameSettings.useKey);

        attackHandler.reset();
        useHandler.reset();
    }

    private static void onOpenScreen(MinecraftClient client, Screen screen, int scaledWidth, int scaledHeight) {
    }

    private static void onRenderHUD(DrawContext context, float partialTicks) {
    }

    private static void onStartRenderWorld(WorldRenderContext context) {
        if (session != null)
            session.onRenderTick();
    }

    private static void onEndRenderWorld(WorldRenderContext context) {
    }

    private static void onTick(MinecraftClient client) {
        if (mc.player == null) {
            recordHistory.clear();
            if (session != null) session.cleanUp();
            session = null;
            return;
        }

        KeyBinds keyBinds = KeyBinds.getKeyBinds();

        if (keyBinds.kbRecord.wasPressed()) {
            if (session != null) {
                session.cleanUp();
                session = null;
            } else {
                session = new RecordingSession().onRecord();
            }
        }

        if (keyBinds.kbPlay.wasPressed() && !recordHistory.isEmpty()) {
            if (session instanceof PlaybackSession && session.isActive())
            {
                session.cleanUp();
                session = null;
            } else {
                session = new PlaybackSession(recordHistory.get(recordHistory.size() - 1).getFrames()).onPlay();
                ((PlaybackSession) session).startPlayback();
            }
        }

        if (keyBinds.kbShowMenu.wasPressed()) {
            mc.setScreen(new MenuScreen());
        }

        if (keyBinds.kbAutoGetSave.wasPressed()) {
            new AutoGetSave().Find();
        }

        if (session != null) {
            session.onClientTick();
        }
    }

    private static void onClientStarted(MinecraftClient client) {
    }

    private static void onClientStopping(MinecraftClient client) {
    }
}
