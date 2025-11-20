/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.collect.Ordering
 */
package net.minecraft.client.gui.screen.ingame;

import com.google.common.collect.Ordering;
import com.mojang.blaze3d.systems.RenderSystem;
import java.util.Collection;
import java.util.List;
import net.minecraft.client.gui.screen.ingame.HandledScreen;
import net.minecraft.client.resource.language.I18n;
import net.minecraft.client.texture.Sprite;
import net.minecraft.client.texture.StatusEffectSpriteManager;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffectUtil;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.text.Text;

public abstract class AbstractInventoryScreen<T extends ScreenHandler>
extends HandledScreen<T> {
    protected boolean drawStatusEffects;

    public AbstractInventoryScreen(T t, PlayerInventory playerInventory, Text text) {
        super(t, playerInventory, text);
    }

    @Override
    protected void init() {
        super.init();
        this.applyStatusEffectOffset();
    }

    protected void applyStatusEffectOffset() {
        if (this.client.player.getStatusEffects().isEmpty()) {
            this.x = (this.width - this.backgroundWidth) / 2;
            this.drawStatusEffects = false;
        } else {
            this.x = 160 + (this.width - this.backgroundWidth - 200) / 2;
            this.drawStatusEffects = true;
        }
    }

    @Override
    public void render(MatrixStack matrices, int mouseX, int mouseY, float delta) {
        super.render(matrices, mouseX, mouseY, delta);
        if (this.drawStatusEffects) {
            this.drawStatusEffects(matrices);
        }
    }

    private void drawStatusEffects(MatrixStack matrixStack) {
        int n = this.x - 124;
        Collection<StatusEffectInstance> _snowman2 = this.client.player.getStatusEffects();
        if (_snowman2.isEmpty()) {
            return;
        }
        RenderSystem.color4f(1.0f, 1.0f, 1.0f, 1.0f);
        _snowman = 33;
        if (_snowman2.size() > 5) {
            _snowman = 132 / (_snowman2.size() - 1);
        }
        List _snowman3 = Ordering.natural().sortedCopy(_snowman2);
        this.drawStatusEffectBackgrounds(matrixStack, n, _snowman, _snowman3);
        this.drawStatusEffectSprites(matrixStack, n, _snowman, _snowman3);
        this.drawStatusEffectDescriptions(matrixStack, n, _snowman, _snowman3);
    }

    private void drawStatusEffectBackgrounds(MatrixStack matrixStack, int n, int n2, Iterable<StatusEffectInstance> iterable) {
        this.client.getTextureManager().bindTexture(BACKGROUND_TEXTURE);
        int n3 = this.y;
        for (StatusEffectInstance statusEffectInstance : iterable) {
            RenderSystem.color4f(1.0f, 1.0f, 1.0f, 1.0f);
            this.drawTexture(matrixStack, n, n3, 0, 166, 140, 32);
            n3 += n2;
        }
    }

    private void drawStatusEffectSprites(MatrixStack matrixStack, int n, int n2, Iterable<StatusEffectInstance> iterable) {
        StatusEffectSpriteManager statusEffectSpriteManager = this.client.getStatusEffectSpriteManager();
        int _snowman2 = this.y;
        for (StatusEffectInstance statusEffectInstance : iterable) {
            StatusEffect statusEffect = statusEffectInstance.getEffectType();
            Sprite _snowman3 = statusEffectSpriteManager.getSprite(statusEffect);
            this.client.getTextureManager().bindTexture(_snowman3.getAtlas().getId());
            AbstractInventoryScreen.drawSprite(matrixStack, n + 6, _snowman2 + 7, this.getZOffset(), 18, 18, _snowman3);
            _snowman2 += n2;
        }
    }

    private void drawStatusEffectDescriptions(MatrixStack matrixStack, int n, int n2, Iterable<StatusEffectInstance> iterable) {
        int n3 = this.y;
        for (StatusEffectInstance statusEffectInstance : iterable) {
            String string = I18n.translate(statusEffectInstance.getEffectType().getTranslationKey(), new Object[0]);
            if (statusEffectInstance.getAmplifier() >= 1 && statusEffectInstance.getAmplifier() <= 9) {
                string = string + ' ' + I18n.translate("enchantment.level." + (statusEffectInstance.getAmplifier() + 1), new Object[0]);
            }
            this.textRenderer.drawWithShadow(matrixStack, string, (float)(n + 10 + 18), (float)(n3 + 6), 0xFFFFFF);
            _snowman = StatusEffectUtil.durationToString(statusEffectInstance, 1.0f);
            this.textRenderer.drawWithShadow(matrixStack, _snowman, (float)(n + 10 + 18), (float)(n3 + 6 + 10), 0x7F7F7F);
            n3 += n2;
        }
    }
}

