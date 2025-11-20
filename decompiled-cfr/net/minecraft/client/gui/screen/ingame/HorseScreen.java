/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.client.gui.screen.ingame;

import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.gui.screen.ingame.HandledScreen;
import net.minecraft.client.gui.screen.ingame.InventoryScreen;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.passive.AbstractDonkeyEntity;
import net.minecraft.entity.passive.HorseBaseEntity;
import net.minecraft.entity.passive.LlamaEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.screen.HorseScreenHandler;
import net.minecraft.util.Identifier;

public class HorseScreen
extends HandledScreen<HorseScreenHandler> {
    private static final Identifier TEXTURE = new Identifier("textures/gui/container/horse.png");
    private final HorseBaseEntity entity;
    private float mouseX;
    private float mouseY;

    public HorseScreen(HorseScreenHandler handler, PlayerInventory inventory, HorseBaseEntity entity) {
        super(handler, inventory, entity.getDisplayName());
        this.entity = entity;
        this.passEvents = false;
    }

    @Override
    protected void drawBackground(MatrixStack matrices, float delta, int mouseX, int mouseY) {
        RenderSystem.color4f(1.0f, 1.0f, 1.0f, 1.0f);
        this.client.getTextureManager().bindTexture(TEXTURE);
        int n = (this.width - this.backgroundWidth) / 2;
        _snowman = (this.height - this.backgroundHeight) / 2;
        this.drawTexture(matrices, n, _snowman, 0, 0, this.backgroundWidth, this.backgroundHeight);
        if (this.entity instanceof AbstractDonkeyEntity && (_snowman = (AbstractDonkeyEntity)this.entity).hasChest()) {
            this.drawTexture(matrices, n + 79, _snowman + 17, 0, this.backgroundHeight, _snowman.getInventoryColumns() * 18, 54);
        }
        if (this.entity.canBeSaddled()) {
            this.drawTexture(matrices, n + 7, _snowman + 35 - 18, 18, this.backgroundHeight + 54, 18, 18);
        }
        if (this.entity.hasArmorSlot()) {
            if (this.entity instanceof LlamaEntity) {
                this.drawTexture(matrices, n + 7, _snowman + 35, 36, this.backgroundHeight + 54, 18, 18);
            } else {
                this.drawTexture(matrices, n + 7, _snowman + 35, 0, this.backgroundHeight + 54, 18, 18);
            }
        }
        InventoryScreen.drawEntity(n + 51, _snowman + 60, 17, (float)(n + 51) - this.mouseX, (float)(_snowman + 75 - 50) - this.mouseY, this.entity);
    }

    @Override
    public void render(MatrixStack matrices, int mouseX, int mouseY, float delta) {
        this.renderBackground(matrices);
        this.mouseX = mouseX;
        this.mouseY = mouseY;
        super.render(matrices, mouseX, mouseY, delta);
        this.drawMouseoverTooltip(matrices, mouseX, mouseY);
    }
}

