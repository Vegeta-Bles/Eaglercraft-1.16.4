/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.client.gui.widget;

import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.ingame.BookScreen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.client.sound.PositionedSoundInstance;
import net.minecraft.client.sound.SoundManager;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.sound.SoundEvents;
import net.minecraft.text.LiteralText;

public class PageTurnWidget
extends ButtonWidget {
    private final boolean isNextPageButton;
    private final boolean playPageTurnSound;

    public PageTurnWidget(int x, int y, boolean isNextPageButton, ButtonWidget.PressAction action, boolean playPageTurnSound) {
        super(x, y, 23, 13, LiteralText.EMPTY, action);
        this.isNextPageButton = isNextPageButton;
        this.playPageTurnSound = playPageTurnSound;
    }

    @Override
    public void renderButton(MatrixStack matrices, int mouseX, int mouseY, float delta) {
        RenderSystem.color4f(1.0f, 1.0f, 1.0f, 1.0f);
        MinecraftClient.getInstance().getTextureManager().bindTexture(BookScreen.BOOK_TEXTURE);
        int n = 0;
        _snowman = 192;
        if (this.isHovered()) {
            n += 23;
        }
        if (!this.isNextPageButton) {
            _snowman += 13;
        }
        this.drawTexture(matrices, this.x, this.y, n, _snowman, 23, 13);
    }

    @Override
    public void playDownSound(SoundManager soundManager) {
        if (this.playPageTurnSound) {
            soundManager.play(PositionedSoundInstance.master(SoundEvents.ITEM_BOOK_PAGE_TURN, 1.0f));
        }
    }
}

