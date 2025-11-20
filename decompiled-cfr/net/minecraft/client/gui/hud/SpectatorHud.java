/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.client.gui.hud;

import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawableHelper;
import net.minecraft.client.gui.hud.spectator.SpectatorMenu;
import net.minecraft.client.gui.hud.spectator.SpectatorMenuCloseCallback;
import net.minecraft.client.gui.hud.spectator.SpectatorMenuCommand;
import net.minecraft.client.gui.hud.spectator.SpectatorMenuState;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import net.minecraft.util.Util;
import net.minecraft.util.math.MathHelper;

public class SpectatorHud
extends DrawableHelper
implements SpectatorMenuCloseCallback {
    private static final Identifier WIDGETS_TEXTURE = new Identifier("textures/gui/widgets.png");
    public static final Identifier SPECTATOR_TEXTURE = new Identifier("textures/gui/spectator_widgets.png");
    private final MinecraftClient client;
    private long lastInteractionTime;
    private SpectatorMenu spectatorMenu;

    public SpectatorHud(MinecraftClient client) {
        this.client = client;
    }

    public void selectSlot(int slot) {
        this.lastInteractionTime = Util.getMeasuringTimeMs();
        if (this.spectatorMenu != null) {
            this.spectatorMenu.useCommand(slot);
        } else {
            this.spectatorMenu = new SpectatorMenu(this);
        }
    }

    private float getSpectatorMenuHeight() {
        long l = this.lastInteractionTime - Util.getMeasuringTimeMs() + 5000L;
        return MathHelper.clamp((float)l / 2000.0f, 0.0f, 1.0f);
    }

    public void render(MatrixStack matrixStack, float f) {
        if (this.spectatorMenu == null) {
            return;
        }
        _snowman = this.getSpectatorMenuHeight();
        if (_snowman <= 0.0f) {
            this.spectatorMenu.close();
            return;
        }
        int n = this.client.getWindow().getScaledWidth() / 2;
        _snowman = this.getZOffset();
        this.setZOffset(-90);
        _snowman = MathHelper.floor((float)this.client.getWindow().getScaledHeight() - 22.0f * _snowman);
        SpectatorMenuState _snowman2 = this.spectatorMenu.getCurrentState();
        this.renderSpectatorMenu(matrixStack, _snowman, n, _snowman, _snowman2);
        this.setZOffset(_snowman);
    }

    protected void renderSpectatorMenu(MatrixStack matrixStack, float f, int n, int n2, SpectatorMenuState spectatorMenuState) {
        RenderSystem.enableRescaleNormal();
        RenderSystem.enableBlend();
        RenderSystem.defaultBlendFunc();
        RenderSystem.color4f(1.0f, 1.0f, 1.0f, f);
        this.client.getTextureManager().bindTexture(WIDGETS_TEXTURE);
        this.drawTexture(matrixStack, n - 91, n2, 0, 0, 182, 22);
        if (spectatorMenuState.getSelectedSlot() >= 0) {
            this.drawTexture(matrixStack, n - 91 - 1 + spectatorMenuState.getSelectedSlot() * 20, n2 - 1, 0, 22, 24, 22);
        }
        for (int i = 0; i < 9; ++i) {
            this.renderSpectatorCommand(matrixStack, i, this.client.getWindow().getScaledWidth() / 2 - 90 + i * 20 + 2, n2 + 3, f, spectatorMenuState.getCommand(i));
        }
        RenderSystem.disableRescaleNormal();
        RenderSystem.disableBlend();
    }

    private void renderSpectatorCommand(MatrixStack matrixStack, int n, int n2, float f, float f2, SpectatorMenuCommand spectatorMenuCommand) {
        this.client.getTextureManager().bindTexture(SPECTATOR_TEXTURE);
        if (spectatorMenuCommand != SpectatorMenu.BLANK_COMMAND) {
            int n3 = (int)(f2 * 255.0f);
            RenderSystem.pushMatrix();
            RenderSystem.translatef(n2, f, 0.0f);
            float _snowman2 = spectatorMenuCommand.isEnabled() ? 1.0f : 0.25f;
            RenderSystem.color4f(_snowman2, _snowman2, _snowman2, f2);
            spectatorMenuCommand.renderIcon(matrixStack, _snowman2, n3);
            RenderSystem.popMatrix();
            if (n3 > 3 && spectatorMenuCommand.isEnabled()) {
                Text text = this.client.options.keysHotbar[n].getBoundKeyLocalizedText();
                this.client.textRenderer.drawWithShadow(matrixStack, text, (float)(n2 + 19 - 2 - this.client.textRenderer.getWidth(text)), f + 6.0f + 3.0f, 0xFFFFFF + (n3 << 24));
            }
        }
    }

    public void render(MatrixStack matrixStack) {
        int n = (int)(this.getSpectatorMenuHeight() * 255.0f);
        if (n > 3 && this.spectatorMenu != null) {
            SpectatorMenuCommand spectatorMenuCommand = this.spectatorMenu.getSelectedCommand();
            Text text = _snowman = spectatorMenuCommand == SpectatorMenu.BLANK_COMMAND ? this.spectatorMenu.getCurrentGroup().getPrompt() : spectatorMenuCommand.getName();
            if (_snowman != null) {
                int n2 = (this.client.getWindow().getScaledWidth() - this.client.textRenderer.getWidth(_snowman)) / 2;
                _snowman = this.client.getWindow().getScaledHeight() - 35;
                RenderSystem.pushMatrix();
                RenderSystem.enableBlend();
                RenderSystem.defaultBlendFunc();
                this.client.textRenderer.drawWithShadow(matrixStack, _snowman, (float)n2, (float)_snowman, 0xFFFFFF + (n << 24));
                RenderSystem.disableBlend();
                RenderSystem.popMatrix();
            }
        }
    }

    @Override
    public void close(SpectatorMenu menu) {
        this.spectatorMenu = null;
        this.lastInteractionTime = 0L;
    }

    public boolean isOpen() {
        return this.spectatorMenu != null;
    }

    public void cycleSlot(double offset) {
        int n = this.spectatorMenu.getSelectedSlot() + (int)offset;
        while (!(n < 0 || n > 8 || this.spectatorMenu.getCommand(n) != SpectatorMenu.BLANK_COMMAND && this.spectatorMenu.getCommand(n).isEnabled())) {
            n = (int)((double)n + offset);
        }
        if (n >= 0 && n <= 8) {
            this.spectatorMenu.useCommand(n);
            this.lastInteractionTime = Util.getMeasuringTimeMs();
        }
    }

    public void useSelectedCommand() {
        this.lastInteractionTime = Util.getMeasuringTimeMs();
        if (this.isOpen()) {
            int n = this.spectatorMenu.getSelectedSlot();
            if (n != -1) {
                this.spectatorMenu.useCommand(n);
            }
        } else {
            this.spectatorMenu = new SpectatorMenu(this);
        }
    }
}

