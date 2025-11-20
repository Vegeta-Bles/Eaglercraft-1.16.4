/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.client.gui.screen.ingame;

import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.ingame.AbstractInventoryScreen;
import net.minecraft.client.gui.screen.ingame.CreativeInventoryScreen;
import net.minecraft.client.gui.screen.recipebook.RecipeBookProvider;
import net.minecraft.client.gui.screen.recipebook.RecipeBookWidget;
import net.minecraft.client.gui.widget.TexturedButtonWidget;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EntityRenderDispatcher;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.client.util.math.Vector3f;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.screen.AbstractRecipeScreenHandler;
import net.minecraft.screen.PlayerScreenHandler;
import net.minecraft.screen.slot.Slot;
import net.minecraft.screen.slot.SlotActionType;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.Quaternion;

public class InventoryScreen
extends AbstractInventoryScreen<PlayerScreenHandler>
implements RecipeBookProvider {
    private static final Identifier RECIPE_BUTTON_TEXTURE = new Identifier("textures/gui/recipe_button.png");
    private float mouseX;
    private float mouseY;
    private final RecipeBookWidget recipeBook = new RecipeBookWidget();
    private boolean open;
    private boolean narrow;
    private boolean mouseDown;

    public InventoryScreen(PlayerEntity player) {
        super(player.playerScreenHandler, player.inventory, new TranslatableText("container.crafting"));
        this.passEvents = true;
        this.titleX = 97;
    }

    @Override
    public void tick() {
        if (this.client.interactionManager.hasCreativeInventory()) {
            this.client.openScreen(new CreativeInventoryScreen(this.client.player));
            return;
        }
        this.recipeBook.update();
    }

    @Override
    protected void init() {
        if (this.client.interactionManager.hasCreativeInventory()) {
            this.client.openScreen(new CreativeInventoryScreen(this.client.player));
            return;
        }
        super.init();
        this.narrow = this.width < 379;
        this.recipeBook.initialize(this.width, this.height, this.client, this.narrow, (AbstractRecipeScreenHandler)this.handler);
        this.open = true;
        this.x = this.recipeBook.findLeftEdge(this.narrow, this.width, this.backgroundWidth);
        this.children.add(this.recipeBook);
        this.setInitialFocus(this.recipeBook);
        this.addButton(new TexturedButtonWidget(this.x + 104, this.height / 2 - 22, 20, 18, 0, 0, 19, RECIPE_BUTTON_TEXTURE, buttonWidget -> {
            this.recipeBook.reset(this.narrow);
            this.recipeBook.toggleOpen();
            this.x = this.recipeBook.findLeftEdge(this.narrow, this.width, this.backgroundWidth);
            ((TexturedButtonWidget)buttonWidget).setPos(this.x + 104, this.height / 2 - 22);
            this.mouseDown = true;
        }));
    }

    @Override
    protected void drawForeground(MatrixStack matrices, int mouseX, int mouseY) {
        this.textRenderer.draw(matrices, this.title, (float)this.titleX, (float)this.titleY, 0x404040);
    }

    @Override
    public void render(MatrixStack matrices, int mouseX, int mouseY, float delta) {
        this.renderBackground(matrices);
        boolean bl = this.drawStatusEffects = !this.recipeBook.isOpen();
        if (this.recipeBook.isOpen() && this.narrow) {
            this.drawBackground(matrices, delta, mouseX, mouseY);
            this.recipeBook.render(matrices, mouseX, mouseY, delta);
        } else {
            this.recipeBook.render(matrices, mouseX, mouseY, delta);
            super.render(matrices, mouseX, mouseY, delta);
            this.recipeBook.drawGhostSlots(matrices, this.x, this.y, false, delta);
        }
        this.drawMouseoverTooltip(matrices, mouseX, mouseY);
        this.recipeBook.drawTooltip(matrices, this.x, this.y, mouseX, mouseY);
        this.mouseX = mouseX;
        this.mouseY = mouseY;
    }

    @Override
    protected void drawBackground(MatrixStack matrices, float delta, int mouseX, int mouseY) {
        RenderSystem.color4f(1.0f, 1.0f, 1.0f, 1.0f);
        this.client.getTextureManager().bindTexture(BACKGROUND_TEXTURE);
        int n = this.x;
        _snowman = this.y;
        this.drawTexture(matrices, n, _snowman, 0, 0, this.backgroundWidth, this.backgroundHeight);
        InventoryScreen.drawEntity(n + 51, _snowman + 75, 30, (float)(n + 51) - this.mouseX, (float)(_snowman + 75 - 50) - this.mouseY, this.client.player);
    }

    public static void drawEntity(int x, int y, int size, float mouseX, float mouseY, LivingEntity entity) {
        float f = (float)Math.atan(mouseX / 40.0f);
        _snowman = (float)Math.atan(mouseY / 40.0f);
        RenderSystem.pushMatrix();
        RenderSystem.translatef(x, y, 1050.0f);
        RenderSystem.scalef(1.0f, 1.0f, -1.0f);
        MatrixStack _snowman2 = new MatrixStack();
        _snowman2.translate(0.0, 0.0, 1000.0);
        _snowman2.scale(size, size, size);
        Quaternion _snowman3 = Vector3f.POSITIVE_Z.getDegreesQuaternion(180.0f);
        Quaternion _snowman4 = Vector3f.POSITIVE_X.getDegreesQuaternion(_snowman * 20.0f);
        _snowman3.hamiltonProduct(_snowman4);
        _snowman2.multiply(_snowman3);
        _snowman = entity.bodyYaw;
        _snowman = entity.yaw;
        _snowman = entity.pitch;
        _snowman = entity.prevHeadYaw;
        _snowman = entity.headYaw;
        entity.bodyYaw = 180.0f + f * 20.0f;
        entity.yaw = 180.0f + f * 40.0f;
        entity.pitch = -_snowman * 20.0f;
        entity.headYaw = entity.yaw;
        entity.prevHeadYaw = entity.yaw;
        EntityRenderDispatcher _snowman5 = MinecraftClient.getInstance().getEntityRenderDispatcher();
        _snowman4.conjugate();
        _snowman5.setRotation(_snowman4);
        _snowman5.setRenderShadows(false);
        VertexConsumerProvider.Immediate _snowman6 = MinecraftClient.getInstance().getBufferBuilders().getEntityVertexConsumers();
        RenderSystem.runAsFancy(() -> _snowman5.render(entity, 0.0, 0.0, 0.0, 0.0f, 1.0f, _snowman2, _snowman6, 0xF000F0));
        _snowman6.draw();
        _snowman5.setRenderShadows(true);
        entity.bodyYaw = _snowman;
        entity.yaw = _snowman;
        entity.pitch = _snowman;
        entity.prevHeadYaw = _snowman;
        entity.headYaw = _snowman;
        RenderSystem.popMatrix();
    }

    @Override
    protected boolean isPointWithinBounds(int xPosition, int yPosition, int width, int height, double pointX, double pointY) {
        return (!this.narrow || !this.recipeBook.isOpen()) && super.isPointWithinBounds(xPosition, yPosition, width, height, pointX, pointY);
    }

    @Override
    public boolean mouseClicked(double mouseX, double mouseY, int button) {
        if (this.recipeBook.mouseClicked(mouseX, mouseY, button)) {
            this.setFocused(this.recipeBook);
            return true;
        }
        if (this.narrow && this.recipeBook.isOpen()) {
            return false;
        }
        return super.mouseClicked(mouseX, mouseY, button);
    }

    @Override
    public boolean mouseReleased(double mouseX, double mouseY, int button) {
        if (this.mouseDown) {
            this.mouseDown = false;
            return true;
        }
        return super.mouseReleased(mouseX, mouseY, button);
    }

    @Override
    protected boolean isClickOutsideBounds(double mouseX, double mouseY, int left, int top, int button) {
        boolean bl = mouseX < (double)left || mouseY < (double)top || mouseX >= (double)(left + this.backgroundWidth) || mouseY >= (double)(top + this.backgroundHeight);
        return this.recipeBook.isClickOutsideBounds(mouseX, mouseY, this.x, this.y, this.backgroundWidth, this.backgroundHeight, button) && bl;
    }

    @Override
    protected void onMouseClick(Slot slot, int invSlot, int clickData, SlotActionType actionType) {
        super.onMouseClick(slot, invSlot, clickData, actionType);
        this.recipeBook.slotClicked(slot);
    }

    @Override
    public void refreshRecipeBook() {
        this.recipeBook.refresh();
    }

    @Override
    public void removed() {
        if (this.open) {
            this.recipeBook.close();
        }
        super.removed();
    }

    @Override
    public RecipeBookWidget getRecipeBookWidget() {
        return this.recipeBook;
    }
}

