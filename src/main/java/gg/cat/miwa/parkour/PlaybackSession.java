package gg.cat.miwa.parkour;

import gg.cat.miwa.Miwa;
import gg.cat.miwa.render.GraphicsHelper;
import net.minecraft.client.MinecraftClient;

import java.util.List;

public class PlaybackSession implements ParkourSession {
    private final MinecraftClient mc = MinecraftClient.getInstance();
    private final List<Frame> recordFrames;
    private int currentFrameIndex = 0;
    private boolean isActive = false;

    private boolean lerpingToStart = false;
    private int lerpTicks = 0;
    private final int maxLerpTicks = 5;

    public PlaybackSession(List<Frame> recordFrames) {
        this.recordFrames = recordFrames;
    }

    @Override
    public ParkourSession onRecord() {
        Miwa.LOGGER.warn("Cannot record during playback.");
        return this;
    }

    @Override
    public ParkourSession onPlay() {
        Miwa.LOGGER.warn("Already playing.");
        return this;
    }

    @Override
    public ParkourSession onOverride() {
        Miwa.LOGGER.warn("Cannot override during playback.");
        return this;
    }

    @Override
    public void onClientTick() {
        if (mc.isPaused())
            return;

        if (!isActive) return;

        if (lerpingToStart) {
            lerpTicks++;
            if (lerpTicks >= maxLerpTicks) {
                lerpingToStart = false;
                Miwa.LOGGER.info("Finished lerping to start frame.");
            }
            return;
        }

        playBackInput();
    }

    @Override
    public void onRenderTick() {
        if (mc.isPaused() || mc.player == null || !isActive) return;

        float partialTicks = mc.getRenderTickCounter().getTickDelta(false);

        if (lerpingToStart) {
            if (recordFrames.isEmpty()) {
                cleanUp();
                return;
            }

            Frame first = recordFrames.get(0);
            float t = (lerpTicks + partialTicks) / maxLerpTicks;

            float lerpedYaw = GraphicsHelper.lerpAngle(t, mc.player.prevYaw, first.yaw);
            float lerpedPitch = GraphicsHelper.lerp(t, mc.player.prevPitch, first.pitch);

            mc.player.setYaw(lerpedYaw);
            mc.player.setPitch(lerpedPitch);
            return;
        }

        if (currentFrameIndex >= recordFrames.size() || currentFrameIndex <= 0) return;

        Frame currentFrame = recordFrames.get(currentFrameIndex);
        Frame previousFrame = recordFrames.get(currentFrameIndex - 1);

        float interpolatedYaw = GraphicsHelper.lerpAngle(partialTicks, previousFrame.yaw, currentFrame.yaw);
        float interpolatedPitch = GraphicsHelper.lerp(partialTicks, previousFrame.pitch, currentFrame.pitch);

        mc.player.setYaw(interpolatedYaw);
        mc.player.setPitch(interpolatedPitch);
    }

    @Override
    public boolean isActive() {
        return isActive;
    }

    @Override
    public void cleanUp() {
        isActive = false;
        resetInput();
        Miwa.LOGGER.info("Playback stopped.");
    }

    public void startPlayback() {
        isActive = true;
        currentFrameIndex = 0;
        lerpingToStart = true;
        lerpTicks = 0;
        Miwa.LOGGER.info("Started playback.");
    }

    private void playBackInput() {
        if ((currentFrameIndex >= recordFrames.size()) || mc.player == null || mc.player.isDead()) {
            cleanUp();
            return;
        }

        Frame frame = recordFrames.get(currentFrameIndex++);
        simulateInput(frame);
    }

    private void simulateInput(Frame frame) {
        mc.options.forwardKey.setPressed(frame.w);
        mc.options.leftKey.setPressed(frame.a);
        mc.options.backKey.setPressed(frame.s);
        mc.options.rightKey.setPressed(frame.d);
        mc.options.jumpKey.setPressed(frame.jump);
        mc.options.sneakKey.setPressed(frame.sneak);
        mc.player.setSprinting(frame.sprint);

        mc.player.setYaw(frame.yaw);
        mc.player.setPitch(frame.pitch);
    }

    private void resetInput() {
        if (mc.player == null) return;
        mc.options.forwardKey.setPressed(false);
        mc.options.leftKey.setPressed(false);
        mc.options.backKey.setPressed(false);
        mc.options.rightKey.setPressed(false);
        mc.options.jumpKey.setPressed(false);
        mc.options.sneakKey.setPressed(false);
        mc.player.setSprinting(false);
    }
}