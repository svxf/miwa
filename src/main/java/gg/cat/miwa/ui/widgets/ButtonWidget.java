package gg.cat.miwa.ui.widgets;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.narration.NarrationMessageBuilder;
import net.minecraft.client.gui.widget.ClickableWidget;
import net.minecraft.text.Text;

public class ButtonWidget extends ClickableWidget {
    private boolean selected;
    private boolean enabled = true;
    private final PressAction onPress;

    public ButtonWidget(int x, int y, int width, int height, Text message, PressAction onPress) {
        super(x, y, width, height, message);
        this.onPress = onPress;
    }

    @Override
    public void onClick(double mouseX, double mouseY) {
        if (this.enabled && this.onPress != null) {
            this.onPress.onPress(this);
        }
    }

    @Override
    protected void renderWidget(DrawContext context, int mouseX, int mouseY, float delta) {
        int backgroundColor = this.enabled ? (isHovered() ? Style.defaults().bgHovered : Style.defaults().bgDefault) : Style.defaults().bgDisabled;
        int textColor = this.enabled ? Style.defaults().textDefault : Style.defaults().textDisabled;

        context.fill(getX(), getY(), getX() + this.width, getY() + this.height, backgroundColor);

        TextRenderer textRenderer = MinecraftClient.getInstance().textRenderer;
        int strWidth = textRenderer.getWidth(getMessage());
        int strHeight = textRenderer.fontHeight;
        int textX = getX() + (this.width - strWidth) / 2;
        int textY = getY() + (this.height - strHeight) / 2;

        context.drawText(textRenderer, getMessage(), textX, textY, textColor, false);

        if (this.enabled && this.selected) {
            int rectYStart = getY() + this.height;
            int rectYEnd = rectYStart + 1;
            context.fill(getX(), rectYStart, getX() + this.width, rectYEnd, 0xFF94E4D3);
        }
    }

    @Override
    protected void appendClickableNarrations(NarrationMessageBuilder builder) {
        return;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }

    public static class Style {
        public int bgHovered, bgDefault, bgDisabled;
        public int textDefault, textDisabled;

        public static Style defaults() {
            var style = new Style();
            style.bgHovered = 0xE0000000;
            style.bgDefault = 0x90000000;
            style.bgDisabled = 0x60000000;
            style.textDefault = 0xFFFFFFFF;
            style.textDisabled = 0x90FFFFFF;

            return style;
        }
    }

    @FunctionalInterface
    public interface PressAction {
        void onPress(ButtonWidget button);
    }
}
