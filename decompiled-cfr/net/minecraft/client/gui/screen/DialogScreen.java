/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.collect.ImmutableList
 */
package net.minecraft.client.gui.screen;

import com.google.common.collect.ImmutableList;
import java.util.List;
import net.minecraft.class_5489;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.text.StringVisitable;
import net.minecraft.text.Text;

public class DialogScreen
extends Screen {
    private final StringVisitable message;
    private final ImmutableList<ChoiceButton> choiceButtons;
    private class_5489 lines = class_5489.field_26528;
    private int linesY;
    private int buttonWidth;

    protected DialogScreen(Text title, List<StringVisitable> list, ImmutableList<ChoiceButton> choiceButtons) {
        super(title);
        this.message = StringVisitable.concat(list);
        this.choiceButtons = choiceButtons;
    }

    @Override
    public String getNarrationMessage() {
        return super.getNarrationMessage() + ". " + this.message.getString();
    }

    @Override
    public void init(MinecraftClient client, int width, int height) {
        super.init(client, width, height);
        for (ChoiceButton choiceButton : this.choiceButtons) {
            this.buttonWidth = Math.max(this.buttonWidth, 20 + this.textRenderer.getWidth(choiceButton.message) + 20);
        }
        int n = 5 + this.buttonWidth + 5;
        _snowman = n * this.choiceButtons.size();
        this.lines = class_5489.method_30890(this.textRenderer, this.message, _snowman);
        _snowman = this.lines.method_30887() * this.textRenderer.fontHeight;
        this.linesY = (int)((double)height / 2.0 - (double)_snowman / 2.0);
        _snowman = this.linesY + _snowman + this.textRenderer.fontHeight * 2;
        _snowman = (int)((double)width / 2.0 - (double)_snowman / 2.0);
        for (ChoiceButton choiceButton : this.choiceButtons) {
            this.addButton(new ButtonWidget(_snowman, _snowman, this.buttonWidth, 20, choiceButton.message, choiceButton.pressAction));
            _snowman += n;
        }
    }

    @Override
    public void render(MatrixStack matrices, int mouseX, int mouseY, float delta) {
        this.renderBackgroundTexture(0);
        DialogScreen.drawCenteredText(matrices, this.textRenderer, this.title, this.width / 2, this.linesY - this.textRenderer.fontHeight * 2, -1);
        this.lines.method_30888(matrices, this.width / 2, this.linesY);
        super.render(matrices, mouseX, mouseY, delta);
    }

    @Override
    public boolean shouldCloseOnEsc() {
        return false;
    }

    public static final class ChoiceButton {
        private final Text message;
        private final ButtonWidget.PressAction pressAction;

        public ChoiceButton(Text message, ButtonWidget.PressAction pressAction) {
            this.message = message;
            this.pressAction = pressAction;
        }
    }
}

