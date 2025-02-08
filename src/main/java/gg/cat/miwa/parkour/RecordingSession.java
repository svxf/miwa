package gg.cat.miwa.parkour;

import gg.cat.miwa.EventHandler;
import gg.cat.miwa.Miwa;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.util.ScreenshotRecorder;
import net.minecraft.text.Text;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class RecordingSession implements ParkourSession {
    private final MinecraftClient mc = MinecraftClient.getInstance();
    private final List<Frame> recordFrames = new ArrayList<>();
    private boolean isActive = false;

    @Override
    public ParkourSession onRecord() {
        isActive = true;
        recordFrames.clear();
        Miwa.LOGGER.info("Started recording.");
        takeTempScreenshot();
        return this;
    }

    Path tempDir = Path.of(MinecraftClient.getInstance().runDirectory.getPath(), "miwa");
    Path tempScreenshotPath = tempDir.resolve("temp_screenshot.png");
    private void takeTempScreenshot() {
        MinecraftClient client = MinecraftClient.getInstance();
        if (client.world == null || client.getFramebuffer() == null) {
            return;
        }

        tempDir.toFile().mkdirs();

        ScreenshotRecorder.saveScreenshot(tempDir.toFile(), "temp_screenshot.png", client.getFramebuffer(), message -> {
        });
    }

    @Override
    public ParkourSession onPlay() {
        Miwa.LOGGER.warn("Cannot play during recording.");
        return this;
    }

    @Override
    public ParkourSession onOverride() {
        Miwa.LOGGER.warn("Cannot override during recording.");
        return this;
    }

    @Override
    public void onClientTick() {
        if (isActive) {
            recordInput();
        }
    }

    @Override
    public void onRenderTick() {
        // Optional: Add HUD or visual indicator during recording.
    }

    @Override
    public boolean isActive() {
        return isActive;
    }

    @Override
    public void cleanUp() {
        isActive = false;
        EventHandler.addToHistory(new Recording(recordFrames));
        Miwa.LOGGER.info("Stopped recording. Frames saved.");
    }

    private void recordInput() {
        if (mc.player == null) return;

        recordFrames.add(new Frame(
                mc.options.forwardKey.isPressed(),
                mc.options.leftKey.isPressed(),
                mc.options.backKey.isPressed(),
                mc.options.rightKey.isPressed(),
                mc.options.jumpKey.isPressed(),
                mc.player.isSprinting(),
                mc.options.sneakKey.isPressed(),
                mc.player.getYaw(),
                mc.player.getPitch()
        ));
    }
}
