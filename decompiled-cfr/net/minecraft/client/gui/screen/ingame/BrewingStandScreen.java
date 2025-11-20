/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.client.gui.screen.ingame;

import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.gui.screen.ingame.HandledScreen;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.screen.BrewingStandScreenHandler;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.MathHelper;

public class BrewingStandScreen
extends HandledScreen<BrewingStandScreenHandler> {
    private static final Identifier TEXTURE = new Identifier("textures/gui/container/brewing_stand.png");
    private static final int[] BUBBLE_PROGRESS = new int[]{29, 24, 20, 16, 11, 6, 0};

    public BrewingStandScreen(BrewingStandScreenHandler handler, PlayerInventory inventory, Text title) {
        super(handler, inventory, title);
    }

    @Override
    protected void init() {
        super.init();
        this.titleX = (this.backgroundWidth - this.textRenderer.getWidth(this.title)) / 2;
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
        this.drawTexture(matrices, n, _snowman, 0, 0, this.backgroundWidth, this.backgroundHeight);
        _snowman = ((BrewingStandScreenHandler)this.handler).getFuel();
        _snowman = MathHelper.clamp((18 * _snowman + 20 - 1) / 20, 0, 18);
        if (_snowman > 0) {
            this.drawTexture(matrices, n + 60, _snowman + 44, 176, 29, _snowman, 4);
        }
        if ((_snowman = ((BrewingStandScreenHandler)this.handler).getBrewTime()) > 0) {
            _snowman = (int)(28.0f * (1.0f - (float)_snowman / 400.0f));
            if (_snowman > 0) {
                this.drawTexture(matrices, n + 97, _snowman + 16, 176, 0, 9, _snowman);
            }
            if ((_snowman = BUBBLE_PROGRESS[_snowman / 2 % 7]) > 0) {
                this.drawTexture(matrices, n + 63, _snowman + 14 + 29 - _snowman, 185, 29 - _snowman, 12, _snowman);
            }
        }
    }
}

