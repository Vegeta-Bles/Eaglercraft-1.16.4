/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.annotation.Nullable
 */
package net.minecraft.client.gui.screen.ingame;

import com.mojang.blaze3d.systems.RenderSystem;
import javax.annotation.Nullable;
import net.minecraft.client.gui.screen.ingame.HandledScreen;
import net.minecraft.client.render.Tessellator;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.item.FilledMapItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.item.map.MapState;
import net.minecraft.screen.CartographyTableScreenHandler;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

public class CartographyTableScreen
extends HandledScreen<CartographyTableScreenHandler> {
    private static final Identifier TEXTURE = new Identifier("textures/gui/container/cartography_table.png");

    public CartographyTableScreen(CartographyTableScreenHandler handler, PlayerInventory inventory, Text title) {
        super(handler, inventory, title);
        this.titleY -= 2;
    }

    @Override
    public void render(MatrixStack matrices, int mouseX, int mouseY, float delta) {
        super.render(matrices, mouseX, mouseY, delta);
        this.drawMouseoverTooltip(matrices, mouseX, mouseY);
    }

    @Override
    protected void drawBackground(MatrixStack matrices, float delta, int mouseX, int mouseY) {
        MapState mapState;
        this.renderBackground(matrices);
        RenderSystem.color4f(1.0f, 1.0f, 1.0f, 1.0f);
        this.client.getTextureManager().bindTexture(TEXTURE);
        int n = this.x;
        _snowman = this.y;
        this.drawTexture(matrices, n, _snowman, 0, 0, this.backgroundWidth, this.backgroundHeight);
        Item _snowman2 = ((CartographyTableScreenHandler)this.handler).getSlot(1).getStack().getItem();
        boolean _snowman3 = _snowman2 == Items.MAP;
        boolean _snowman4 = _snowman2 == Items.PAPER;
        boolean _snowman5 = _snowman2 == Items.GLASS_PANE;
        ItemStack _snowman6 = ((CartographyTableScreenHandler)this.handler).getSlot(0).getStack();
        boolean _snowman7 = false;
        if (_snowman6.getItem() == Items.FILLED_MAP) {
            mapState = FilledMapItem.getMapState(_snowman6, this.client.world);
            if (mapState != null) {
                if (mapState.locked) {
                    _snowman7 = true;
                    if (_snowman4 || _snowman5) {
                        this.drawTexture(matrices, n + 35, _snowman + 31, this.backgroundWidth + 50, 132, 28, 21);
                    }
                }
                if (_snowman4 && mapState.scale >= 4) {
                    _snowman7 = true;
                    this.drawTexture(matrices, n + 35, _snowman + 31, this.backgroundWidth + 50, 132, 28, 21);
                }
            }
        } else {
            mapState = null;
        }
        this.drawMap(matrices, mapState, _snowman3, _snowman4, _snowman5, _snowman7);
    }

    private void drawMap(MatrixStack matrixStack, @Nullable MapState mapState, boolean bl, boolean bl2, boolean bl3, boolean bl4) {
        int n = this.x;
        _snowman = this.y;
        if (bl2 && !bl4) {
            this.drawTexture(matrixStack, n + 67, _snowman + 13, this.backgroundWidth, 66, 66, 66);
            this.drawMap(mapState, n + 85, _snowman + 31, 0.226f);
        } else if (bl) {
            this.drawTexture(matrixStack, n + 67 + 16, _snowman + 13, this.backgroundWidth, 132, 50, 66);
            this.drawMap(mapState, n + 86, _snowman + 16, 0.34f);
            this.client.getTextureManager().bindTexture(TEXTURE);
            RenderSystem.pushMatrix();
            RenderSystem.translatef(0.0f, 0.0f, 1.0f);
            this.drawTexture(matrixStack, n + 67, _snowman + 13 + 16, this.backgroundWidth, 132, 50, 66);
            this.drawMap(mapState, n + 70, _snowman + 32, 0.34f);
            RenderSystem.popMatrix();
        } else if (bl3) {
            this.drawTexture(matrixStack, n + 67, _snowman + 13, this.backgroundWidth, 0, 66, 66);
            this.drawMap(mapState, n + 71, _snowman + 17, 0.45f);
            this.client.getTextureManager().bindTexture(TEXTURE);
            RenderSystem.pushMatrix();
            RenderSystem.translatef(0.0f, 0.0f, 1.0f);
            this.drawTexture(matrixStack, n + 66, _snowman + 12, 0, this.backgroundHeight, 66, 66);
            RenderSystem.popMatrix();
        } else {
            this.drawTexture(matrixStack, n + 67, _snowman + 13, this.backgroundWidth, 0, 66, 66);
            this.drawMap(mapState, n + 71, _snowman + 17, 0.45f);
        }
    }

    private void drawMap(@Nullable MapState state, int x, int y, float size) {
        if (state != null) {
            RenderSystem.pushMatrix();
            RenderSystem.translatef(x, y, 1.0f);
            RenderSystem.scalef(size, size, 1.0f);
            VertexConsumerProvider.Immediate immediate = VertexConsumerProvider.immediate(Tessellator.getInstance().getBuffer());
            this.client.gameRenderer.getMapRenderer().draw(new MatrixStack(), immediate, state, true, 0xF000F0);
            immediate.draw();
            RenderSystem.popMatrix();
        }
    }
}

