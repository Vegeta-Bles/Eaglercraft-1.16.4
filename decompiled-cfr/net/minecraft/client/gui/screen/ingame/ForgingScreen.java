/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.client.gui.screen.ingame;

import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.gui.screen.ingame.HandledScreen;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.screen.ForgingScreenHandler;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.screen.ScreenHandlerListener;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import net.minecraft.util.collection.DefaultedList;

public class ForgingScreen<T extends ForgingScreenHandler>
extends HandledScreen<T>
implements ScreenHandlerListener {
    private Identifier texture;

    public ForgingScreen(T handler, PlayerInventory playerInventory, Text title, Identifier texture) {
        super(handler, playerInventory, title);
        this.texture = texture;
    }

    protected void setup() {
    }

    @Override
    protected void init() {
        super.init();
        this.setup();
        ((ForgingScreenHandler)this.handler).addListener(this);
    }

    @Override
    public void removed() {
        super.removed();
        ((ForgingScreenHandler)this.handler).removeListener(this);
    }

    @Override
    public void render(MatrixStack matrices, int mouseX, int mouseY, float delta) {
        this.renderBackground(matrices);
        super.render(matrices, mouseX, mouseY, delta);
        RenderSystem.disableBlend();
        this.renderForeground(matrices, mouseX, mouseY, delta);
        this.drawMouseoverTooltip(matrices, mouseX, mouseY);
    }

    protected void renderForeground(MatrixStack matrixStack, int mouseY, int n, float f) {
    }

    @Override
    protected void drawBackground(MatrixStack matrices, float delta, int mouseX, int mouseY) {
        RenderSystem.color4f(1.0f, 1.0f, 1.0f, 1.0f);
        this.client.getTextureManager().bindTexture(this.texture);
        int n = (this.width - this.backgroundWidth) / 2;
        _snowman = (this.height - this.backgroundHeight) / 2;
        this.drawTexture(matrices, n, _snowman, 0, 0, this.backgroundWidth, this.backgroundHeight);
        this.drawTexture(matrices, n + 59, _snowman + 20, 0, this.backgroundHeight + (((ForgingScreenHandler)this.handler).getSlot(0).hasStack() ? 0 : 16), 110, 16);
        if ((((ForgingScreenHandler)this.handler).getSlot(0).hasStack() || ((ForgingScreenHandler)this.handler).getSlot(1).hasStack()) && !((ForgingScreenHandler)this.handler).getSlot(2).hasStack()) {
            this.drawTexture(matrices, n + 99, _snowman + 45, this.backgroundWidth, 0, 28, 21);
        }
    }

    @Override
    public void onHandlerRegistered(ScreenHandler handler, DefaultedList<ItemStack> stacks) {
        this.onSlotUpdate(handler, 0, handler.getSlot(0).getStack());
    }

    @Override
    public void onPropertyUpdate(ScreenHandler handler, int property, int value) {
    }

    @Override
    public void onSlotUpdate(ScreenHandler handler, int slotId, ItemStack stack) {
    }
}

