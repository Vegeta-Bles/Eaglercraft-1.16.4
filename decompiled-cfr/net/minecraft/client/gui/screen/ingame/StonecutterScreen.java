/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.client.gui.screen.ingame;

import com.mojang.blaze3d.systems.RenderSystem;
import java.util.List;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.ingame.HandledScreen;
import net.minecraft.client.sound.PositionedSoundInstance;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.recipe.StonecuttingRecipe;
import net.minecraft.screen.StonecutterScreenHandler;
import net.minecraft.sound.SoundEvents;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.MathHelper;

public class StonecutterScreen
extends HandledScreen<StonecutterScreenHandler> {
    private static final Identifier TEXTURE = new Identifier("textures/gui/container/stonecutter.png");
    private float scrollAmount;
    private boolean mouseClicked;
    private int scrollOffset;
    private boolean canCraft;

    public StonecutterScreen(StonecutterScreenHandler handler, PlayerInventory inventory, Text title) {
        super(handler, inventory, title);
        handler.setContentsChangedListener(this::onInventoryChange);
        --this.titleY;
    }

    @Override
    public void render(MatrixStack matrices, int mouseX, int mouseY, float delta) {
        super.render(matrices, mouseX, mouseY, delta);
        this.drawMouseoverTooltip(matrices, mouseX, mouseY);
    }

    @Override
    protected void drawBackground(MatrixStack matrices, float delta, int mouseX, int mouseY) {
        this.renderBackground(matrices);
        RenderSystem.color4f(1.0f, 1.0f, 1.0f, 1.0f);
        this.client.getTextureManager().bindTexture(TEXTURE);
        int n = this.x;
        _snowman = this.y;
        this.drawTexture(matrices, n, _snowman, 0, 0, this.backgroundWidth, this.backgroundHeight);
        _snowman = (int)(41.0f * this.scrollAmount);
        this.drawTexture(matrices, n + 119, _snowman + 15 + _snowman, 176 + (this.shouldScroll() ? 0 : 12), 0, 12, 15);
        _snowman = this.x + 52;
        _snowman = this.y + 14;
        _snowman = this.scrollOffset + 12;
        this.renderRecipeBackground(matrices, mouseX, mouseY, _snowman, _snowman, _snowman);
        this.renderRecipeIcons(_snowman, _snowman, _snowman);
    }

    @Override
    protected void drawMouseoverTooltip(MatrixStack matrices, int x, int y) {
        super.drawMouseoverTooltip(matrices, x, y);
        if (this.canCraft) {
            int n = this.x + 52;
            _snowman = this.y + 14;
            _snowman = this.scrollOffset + 12;
            List<StonecuttingRecipe> _snowman2 = ((StonecutterScreenHandler)this.handler).getAvailableRecipes();
            for (_snowman = this.scrollOffset; _snowman < _snowman && _snowman < ((StonecutterScreenHandler)this.handler).getAvailableRecipeCount(); ++_snowman) {
                _snowman = _snowman - this.scrollOffset;
                _snowman = n + _snowman % 4 * 16;
                _snowman = _snowman + _snowman / 4 * 18 + 2;
                if (x < _snowman || x >= _snowman + 16 || y < _snowman || y >= _snowman + 18) continue;
                this.renderTooltip(matrices, _snowman2.get(_snowman).getOutput(), x, y);
            }
        }
    }

    private void renderRecipeBackground(MatrixStack matrixStack, int n, int n2, int n3, int n4, int n5) {
        for (_snowman = this.scrollOffset; _snowman < n5 && _snowman < ((StonecutterScreenHandler)this.handler).getAvailableRecipeCount(); ++_snowman) {
            _snowman = _snowman - this.scrollOffset;
            _snowman = n3 + _snowman % 4 * 16;
            _snowman = _snowman / 4;
            _snowman = n4 + _snowman * 18 + 2;
            _snowman = this.backgroundHeight;
            if (_snowman == ((StonecutterScreenHandler)this.handler).getSelectedRecipe()) {
                _snowman += 18;
            } else if (n >= _snowman && n2 >= _snowman && n < _snowman + 16 && n2 < _snowman + 18) {
                _snowman += 36;
            }
            this.drawTexture(matrixStack, _snowman, _snowman - 1, 0, _snowman, 16, 18);
        }
    }

    private void renderRecipeIcons(int x, int y, int scrollOffset) {
        List<StonecuttingRecipe> list = ((StonecutterScreenHandler)this.handler).getAvailableRecipes();
        for (int i = this.scrollOffset; i < scrollOffset && i < ((StonecutterScreenHandler)this.handler).getAvailableRecipeCount(); ++i) {
            _snowman = i - this.scrollOffset;
            _snowman = x + _snowman % 4 * 16;
            _snowman = _snowman / 4;
            _snowman = y + _snowman * 18 + 2;
            this.client.getItemRenderer().renderInGuiWithOverrides(list.get(i).getOutput(), _snowman, _snowman);
        }
    }

    @Override
    public boolean mouseClicked(double mouseX, double mouseY, int button) {
        this.mouseClicked = false;
        if (this.canCraft) {
            int n = this.x + 52;
            _snowman = this.y + 14;
            _snowman = this.scrollOffset + 12;
            for (_snowman = this.scrollOffset; _snowman < _snowman; ++_snowman) {
                _snowman = _snowman - this.scrollOffset;
                double d = mouseX - (double)(n + _snowman % 4 * 16);
                _snowman = mouseY - (double)(_snowman + _snowman / 4 * 18);
                if (!(d >= 0.0) || !(_snowman >= 0.0) || !(d < 16.0) || !(_snowman < 18.0) || !((StonecutterScreenHandler)this.handler).onButtonClick(this.client.player, _snowman)) continue;
                MinecraftClient.getInstance().getSoundManager().play(PositionedSoundInstance.master(SoundEvents.UI_STONECUTTER_SELECT_RECIPE, 1.0f));
                this.client.interactionManager.clickButton(((StonecutterScreenHandler)this.handler).syncId, _snowman);
                return true;
            }
            n = this.x + 119;
            _snowman = this.y + 9;
            if (mouseX >= (double)n && mouseX < (double)(n + 12) && mouseY >= (double)_snowman && mouseY < (double)(_snowman + 54)) {
                this.mouseClicked = true;
            }
        }
        return super.mouseClicked(mouseX, mouseY, button);
    }

    @Override
    public boolean mouseDragged(double mouseX, double mouseY, int button, double deltaX, double deltaY) {
        if (this.mouseClicked && this.shouldScroll()) {
            int n = this.y + 14;
            _snowman = n + 54;
            this.scrollAmount = ((float)mouseY - (float)n - 7.5f) / ((float)(_snowman - n) - 15.0f);
            this.scrollAmount = MathHelper.clamp(this.scrollAmount, 0.0f, 1.0f);
            this.scrollOffset = (int)((double)(this.scrollAmount * (float)this.getMaxScroll()) + 0.5) * 4;
            return true;
        }
        return super.mouseDragged(mouseX, mouseY, button, deltaX, deltaY);
    }

    @Override
    public boolean mouseScrolled(double mouseX, double mouseY, double amount) {
        if (this.shouldScroll()) {
            int n = this.getMaxScroll();
            this.scrollAmount = (float)((double)this.scrollAmount - amount / (double)n);
            this.scrollAmount = MathHelper.clamp(this.scrollAmount, 0.0f, 1.0f);
            this.scrollOffset = (int)((double)(this.scrollAmount * (float)n) + 0.5) * 4;
        }
        return true;
    }

    private boolean shouldScroll() {
        return this.canCraft && ((StonecutterScreenHandler)this.handler).getAvailableRecipeCount() > 12;
    }

    protected int getMaxScroll() {
        return (((StonecutterScreenHandler)this.handler).getAvailableRecipeCount() + 4 - 1) / 4 - 3;
    }

    private void onInventoryChange() {
        this.canCraft = ((StonecutterScreenHandler)this.handler).canCraft();
        if (!this.canCraft) {
            this.scrollAmount = 0.0f;
            this.scrollOffset = 0;
        }
    }
}

