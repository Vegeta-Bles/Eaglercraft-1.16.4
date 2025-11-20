/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.client.gui.screen.ingame;

import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.gui.screen.ingame.HandledScreen;
import net.minecraft.client.gui.screen.ingame.ScreenHandlerProvider;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.screen.GenericContainerScreenHandler;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

public class GenericContainerScreen
extends HandledScreen<GenericContainerScreenHandler>
implements ScreenHandlerProvider<GenericContainerScreenHandler> {
    private static final Identifier TEXTURE = new Identifier("textures/gui/container/generic_54.png");
    private final int rows;

    public GenericContainerScreen(GenericContainerScreenHandler handler, PlayerInventory inventory, Text title) {
        super(handler, inventory, title);
        this.passEvents = false;
        int n = 222;
        _snowman = 114;
        this.rows = handler.getRows();
        this.backgroundHeight = 114 + this.rows * 18;
        this.playerInventoryTitleY = this.backgroundHeight - 94;
    }

    @Override
    public void render(MatrixStack matrices, int mouseX, int mouseY, float delta) {
        this.renderBackground(matrices);
        super.render(matrices, mouseX, mouseY, delta);
        this.drawMouseoverTooltip(matrices, mouseX, mouseY);
    }

    @Override
    protected void drawBackground(MatrixStack matrices, float delta, int mouseX, int mouseY) {
        RenderSystem.color4f(1.0f, 1.0f, 1.0f, 1.0f);
        this.client.getTextureManager().bindTexture(TEXTURE);
        int n = (this.width - this.backgroundWidth) / 2;
        _snowman = (this.height - this.backgroundHeight) / 2;
        this.drawTexture(matrices, n, _snowman, 0, 0, this.backgroundWidth, this.rows * 18 + 17);
        this.drawTexture(matrices, n, _snowman + this.rows * 18 + 17, 0, 126, this.backgroundWidth, 96);
    }
}

