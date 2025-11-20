/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.client.gui.screen;

import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.text.Text;

public class SaveLevelScreen
extends Screen {
    public SaveLevelScreen(Text text) {
        super(text);
    }

    @Override
    public boolean shouldCloseOnEsc() {
        return false;
    }

    @Override
    public void render(MatrixStack matrices, int mouseX, int mouseY, float delta) {
        this.renderBackgroundTexture(0);
        SaveLevelScreen.drawCenteredText(matrices, this.textRenderer, this.title, this.width / 2, 70, 0xFFFFFF);
        super.render(matrices, mouseX, mouseY, delta);
    }
}

