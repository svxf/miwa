package gg.cat.miwa.ui;

import com.google.common.reflect.TypeToken;
import com.google.gson.Gson;
import com.mojang.blaze3d.pipeline.RenderPipeline;
import gg.cat.miwa.EventHandler;
import gg.cat.miwa.Miwa;
import gg.cat.miwa.parkour.Recording;
import gg.cat.miwa.ui.widgets.ButtonWidget;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.gl.RenderPipelines;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.TextFieldWidget;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.texture.NativeImage;
import net.minecraft.client.texture.NativeImageBackedTexture;
import net.minecraft.client.texture.TextureManager;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import org.jetbrains.annotations.NotNull;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Type;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class MenuScreen extends Screen {
    private final MinecraftClient mc = MinecraftClient.getInstance();
    private TextFieldWidget saveNameField;
    private List<String> savesList;
    private int selectedSaveIndex = -1;
    private String currentTab = "Save";
    private final Gson gson = new Gson();

    public MenuScreen() {
        super(Text.of("miwa"));
    }

    @Override
    protected void init() {
        int x = 6;
        int y = 6;

        TextRenderer textRenderer = MinecraftClient.getInstance().textRenderer;

        ButtonWidget saveButton;
        ButtonWidget loadButton;

        saveButton = new ButtonWidget(
                6, 6, 12 + textRenderer.getWidth("Save"), 18, Text.of("Save"), button -> {
            setTab("Save");
        });

        loadButton = new ButtonWidget(
                6 + 12 + textRenderer.getWidth("Save") + 6, 6, 12 + textRenderer.getWidth("Load"), 18, Text.of("Load"), button -> {
            setTab("Load");
        });

        this.addDrawableChild(saveButton);
        this.addDrawableChild(loadButton);

        switch (currentTab) {
            case "Save":
                saveButton.setSelected(true);
                loadButton.setSelected(false);
                setupSaveTab();
                break;
            case "Load":
                saveButton.setSelected(false);
                loadButton.setSelected(true);
                setupLoadTab();
                break;
        }
    }

    private void setTab(String tab) {
        this.currentTab = tab;
        this.clearChildren();
        init();
    }

    private void setupSaveTab() {
        this.saveNameField = new TextFieldWidget(this.textRenderer, this.width / 2 - 100, this.height / 2 - 50, 200, 20, Text.of("Save Name"));
        this.addDrawableChild(saveNameField);

        ButtonWidget saveButton = new ButtonWidget(
                this.width / 2 - 50, this.height / 2, 80 + textRenderer.getWidth("Save"), 18, Text.of("Save"), button -> {
            saveRecording();
        });

        this.addDrawableChild(saveButton);
    }

    private boolean isRedTabActive = true;
    private boolean isPurpleTabActive = false;

    private void setupLoadTab() {
        loadSaves();

        this.addDrawableChild(new ButtonWidget(
                6, 18 + 12, 60, 18, Text.of("Red"), button -> {
            isRedTabActive = true;
            isPurpleTabActive = false;
            updateSaveButtons();
        }));

        this.addDrawableChild(new ButtonWidget(
                72, 18 + 12, 60, 18, Text.of("Purple"), button -> {
            isRedTabActive = false;
            isPurpleTabActive = true;
            updateSaveButtons();
        }));

        this.addDrawableChild(new ButtonWidget(
                138, 18 + 12, 60, 18, Text.of("Other"), button -> {
            isRedTabActive = false;
            isPurpleTabActive = false;
            updateSaveButtons();
        }));

        updateSaveButtons();
    }

    private List<ButtonWidget> saveButtons = new ArrayList<>();
    private ButtonWidget openButton = null;

    private int currentPage = 0;
    private ButtonWidget prevPageButton;
    private ButtonWidget nextPageButton;
    private final int SAVES_PER_PAGE = 15;

    private void updateSaveButtons() {
        // Clear old buttons
        for (ButtonWidget b : saveButtons) this.remove(b);
        saveButtons.clear();

        if (openButton != null) {
            this.remove(openButton);
            openButton = null;
        }
        if (prevPageButton != null) {
            this.remove(prevPageButton);
            prevPageButton = null;
        }
        if (nextPageButton != null) {
            this.remove(nextPageButton);
            nextPageButton = null;
        }

        // Pick the active list
        List<String> activeSaves = isRedTabActive ? redSaves :
                isPurpleTabActive ? purpleSaves :
                        otherSaves;

        this.savesList = activeSaves;

        // Number of pages
        int totalPages = (int) Math.ceil(activeSaves.size() / (double) SAVES_PER_PAGE);
        if (currentPage >= totalPages) currentPage = totalPages - 1;
        if (currentPage < 0) currentPage = 0;

        // Starting/ending indexes for this page
        int start = currentPage * SAVES_PER_PAGE;
        int end = Math.min(start + SAVES_PER_PAGE, activeSaves.size());

        // Layout buttons
        int yOffset = 18 + 12 + 22;

        for (int i = start; i < end; i++) {
            int indexOnPage = i - start;

            ButtonWidget saveButton = getButtonWidget(activeSaves, i, yOffset + indexOnPage * 22);
            this.addDrawableChild(saveButton);
            saveButtons.add(saveButton);
        }

        // Add Open button
        int bottomY = yOffset + (end - start) * 22 + 10;

        openButton = new ButtonWidget(
                this.width / 2 - 50,
                bottomY,
                100,
                20,
                Text.of("Open"),
                b -> openSelectedSave()
        );
        openButton.visible = false;
        this.addDrawableChild(openButton);

        // --- PAGE BUTTONS ---
        int buttonY = 385;

        prevPageButton = new ButtonWidget(
                6,
                buttonY,
                20,
                20,
                Text.of("<"),
                button -> {
                    if (currentPage > 0) {
                        currentPage--;
                        updateSaveButtons();
                    }
                }
        );
        prevPageButton.setEnabled(currentPage > 0);
        this.addDrawableChild(prevPageButton);

        nextPageButton = new ButtonWidget(
                6 + 20 + 5,
                buttonY,
                20,
                20,
                Text.of(">"),
                button -> {
                    if (currentPage < totalPages - 1) {
                        currentPage++;
                        updateSaveButtons();
                    }
                }
        );
        nextPageButton.setEnabled(currentPage < totalPages - 1);
        this.addDrawableChild(nextPageButton);
    }

    private @NotNull ButtonWidget getButtonWidget(List<String> activeSaves, int i, int yOffset) {
        String saveName = activeSaves.get(i);

        return new ButtonWidget(
                6, yOffset, 200, 18, Text.of(saveName), button -> {
            for (ButtonWidget b : saveButtons) {
                b.setSelected(false);
            }

            selectedSaveIndex = i;
            button.setSelected(true);

            updateOpenButtonPosition(button);
            updatePreview(saveName);
        });
    }

    private void updateOpenButtonPosition(ButtonWidget selectedButton) {
        for (var child : this.children()) {
            if (child instanceof ButtonWidget button && button.getMessage().getString().equals("Open")) {
                button.setX(selectedButton.getX() + selectedButton.getWidth() + 6);
                button.setY(selectedButton.getY());
                button.visible = true;
                break;
            }
        }
    }

    private Identifier currentPreviewImage;
    private void updatePreview(String saveName) {
        saveName = saveName.replace(" ", "_");
        currentPreviewImage = getPreviewImageForSave(saveName);
    }

    @Override
    public void render(DrawContext context, int mouseX, int mouseY, float partialTicks) {
//        this.renderBackground(context, mouseX, mouseY, partialTicks);
        super.render(context, mouseX, mouseY, partialTicks);

        if (Objects.equals(currentTab, "Load")) {
            int previewXStart = (int) (this.width / 2 - 160);
            int previewXEnd = (int) (this.width / 2f + 350);
            int previewYStart = 18+12;
            int previewYEnd = 330;

            context.fill(previewXStart, previewYStart, previewXEnd, previewYEnd, 0x90000000);

            if (currentPreviewImage != null) {
                context.drawTexture(
                        RenderPipelines.GUI_TEXTURED,
                        currentPreviewImage,
                        previewXStart + 4,
                        previewYStart + 4,
                        0f,
                        0f,
                        previewXEnd - previewXStart - 8,
                        previewYEnd - previewYStart - 8,
                        previewXEnd - previewXStart,
                        previewYEnd - previewYStart
                );
            }
        }
    }

    private Identifier getPreviewImageForSave(String saveName) {
        Path saveDir = Path.of(MinecraftClient.getInstance().runDirectory.getPath(), "miwa", "saves");
        Path saveImagePath = saveDir.resolve(saveName + ".png");

        if (Files.exists(saveImagePath)) {
            try {
                BufferedImage bufferedImage = ImageIO.read(saveImagePath.toFile());
                NativeImage nativeImage = new NativeImage(bufferedImage.getWidth(), bufferedImage.getHeight(), false);

                for (int x = 0; x < bufferedImage.getWidth(); x++) {
                    for (int y = 0; y < bufferedImage.getHeight(); y++) {
                        int argb = bufferedImage.getRGB(x, y);
                        nativeImage.setColorArgb(x, y, argb);
                    }
                }

                TextureManager textureManager = MinecraftClient.getInstance().getTextureManager();
                Identifier textureId = Identifier.of("miwa", "saves/" + saveName);

                textureManager.registerTexture(
                        textureId,
                        new NativeImageBackedTexture(() -> "miwa_preview_image", nativeImage)
                );

                return textureId;
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return null;
    }

    private void saveRecording() {
        List<Recording> recordHistory = EventHandler.recordHistory;
        if (recordHistory.isEmpty()) {
            mc.player.sendMessage(Text.of("No recording found to save."), false);
            return;
        }

        String saveName = saveNameField.getText().isEmpty() ? "UnnamedRecording" : saveNameField.getText();
        saveName = saveName.replace(" ", "_");

        File saveDir = new File(mc.runDirectory, "miwa/saves");
        if (!saveDir.exists() && !saveDir.mkdirs()) {
            mc.player.sendMessage(Text.of("Failed to create saves directory."), false);
            return;
        }

        File saveFile = new File(saveDir, saveName + ".json");
        try (FileWriter writer = new FileWriter(saveFile)) {
            writer.write(recordHistory.get(recordHistory.size() - 1).toJson());
            mc.player.sendMessage(Text.of("Recording saved as " + saveFile.getName()), false);
        } catch (IOException e) {
            mc.player.sendMessage(Text.of("Failed to save recording: " + e.getMessage()), false);
        }

        saveScreenshotWithName(saveName);
        mc.setScreen(null);
    }

    private void saveScreenshotWithName(String saveName) {
        Path tempDir = Path.of(MinecraftClient.getInstance().runDirectory.getPath(), "miwa", "screenshots");
        Path tempScreenshotPath = tempDir.resolve("temp_screenshot.png");

        Path saveDir = Path.of(MinecraftClient.getInstance().runDirectory.getPath(), "miwa", "saves");
        saveDir.toFile().mkdirs();

        Path savePath = saveDir.resolve(saveName + ".png");
        try {
            Files.move(tempScreenshotPath, savePath, StandardCopyOption.REPLACE_EXISTING);
            MinecraftClient.getInstance().player.sendMessage(Text.of("Screenshot saved as: " + savePath), false);
            tempScreenshotPath = null;
        } catch (IOException e) {
            MinecraftClient.getInstance().player.sendMessage(Text.of("Failed to save screenshot: " + e.getMessage()), false);
        }
    }

    private List<String> redSaves = new ArrayList<>();
    private List<String> purpleSaves = new ArrayList<>();
    private List<String> otherSaves = new ArrayList<>();

    private void loadSaves() {
        File saveDir = new File(mc.runDirectory, "miwa/saves");
        if (!saveDir.exists()) {
            savesList = new ArrayList<>();
            return;
        }

        redSaves.clear();
        purpleSaves.clear();
        otherSaves.clear();

        for (File file : Objects.requireNonNull(saveDir.listFiles())) {
            if (file.getName().endsWith(".json")) {
                String saveName = file.getName().replace(".json", "");
                if (saveName.startsWith("red_")) {
                    redSaves.add(saveName);
                } else if (saveName.startsWith("purple_")) {
                    purpleSaves.add(saveName);
                } else {
                    otherSaves.add(saveName);
                }
            }
        }
    }

    private void openSelectedSave() {
        if (savesList == null || selectedSaveIndex < 0 || selectedSaveIndex >= savesList.size()) {
            mc.player.sendMessage(Text.of("No save selected."), false);
            return;
        }

        String selectedSave = savesList.get(selectedSaveIndex);
        File saveFile = new File(mc.runDirectory, "miwa/saves/" + selectedSave + ".json");

        try (FileReader reader = new FileReader(saveFile)) {
            Type recordingType = new TypeToken<Recording>() {}.getType();
            Recording recording = gson.fromJson(reader, recordingType);

            Miwa.LOGGER.info(String.valueOf(recording));
            EventHandler.recordHistory.clear();
            EventHandler.recordHistory.add(recording);
            mc.player.sendMessage(Text.of("Loaded recording: " + selectedSave), false);
        } catch (IOException e) {
            mc.player.sendMessage(Text.of("Failed to load recording: " + e.getMessage()), false);
        }

        mc.setScreen(null);
    }

    @Override
    public boolean shouldPause() {
        return false;
    }
}
