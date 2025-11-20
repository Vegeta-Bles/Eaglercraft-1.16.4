/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.client.gui.screen;

import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.class_5489;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.client.options.GameOptions;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.Identifier;
import net.minecraft.util.Util;

public class DemoScreen
extends Screen {
    private static final Identifier DEMO_BG = new Identifier("textures/gui/demo_background.png");
    private class_5489 field_26538 = class_5489.field_26528;
    private class_5489 field_26539 = class_5489.field_26528;

    public DemoScreen() {
        super(new TranslatableText("demo.help.title"));
    }

    @Override
    protected void init() {
        int n = -16;
        this.addButton(new ButtonWidget(this.width / 2 - 116, this.height / 2 + 62 + -16, 114, 20, new TranslatableText("demo.help.buy"), buttonWidget -> {
            buttonWidget.active = false;
            Util.getOperatingSystem().open("http://www.minecraft.net/store?source=demo");
        }));
        this.addButton(new ButtonWidget(this.width / 2 + 2, this.height / 2 + 62 + -16, 114, 20, new TranslatableText("demo.help.later"), buttonWidget -> {
            this.client.openScreen(null);
            this.client.mouse.lockCursor();
        }));
        GameOptions _snowman2 = this.client.options;
        this.field_26538 = class_5489.method_30892(this.textRenderer, new TranslatableText("demo.help.movementShort", _snowman2.keyForward.getBoundKeyLocalizedText(), _snowman2.keyLeft.getBoundKeyLocalizedText(), _snowman2.keyBack.getBoundKeyLocalizedText(), _snowman2.keyRight.getBoundKeyLocalizedText()), new TranslatableText("demo.help.movementMouse"), new TranslatableText("demo.help.jump", _snowman2.keyJump.getBoundKeyLocalizedText()), new TranslatableText("demo.help.inventory", _snowman2.keyInventory.getBoundKeyLocalizedText()));
        this.field_26539 = class_5489.method_30890(this.textRenderer, new TranslatableText("demo.help.fullWrapped"), 218);
    }

    @Override
    public void renderBackground(MatrixStack matrices) {
        super.renderBackground(matrices);
        RenderSystem.color4f(1.0f, 1.0f, 1.0f, 1.0f);
        this.client.getTextureManager().bindTexture(DEMO_BG);
        int n = (this.width - 248) / 2;
        _snowman = (this.height - 166) / 2;
        this.drawTexture(matrices, n, _snowman, 0, 0, 248, 166);
    }

    @Override
    public void render(MatrixStack matrices, int mouseX, int mouseY, float delta) {
        this.renderBackground(matrices);
        int n = (this.width - 248) / 2 + 10;
        _snowman = (this.height - 166) / 2 + 8;
        this.textRenderer.draw(matrices, this.title, (float)n, (float)_snowman, 0x1F1F1F);
        _snowman = this.field_26538.method_30896(matrices, n, _snowman + 12, 12, 0x4F4F4F);
        this.field_26539.method_30896(matrices, n, _snowman + 20, this.textRenderer.fontHeight, 0x1F1F1F);
        super.render(matrices, mouseX, mouseY, delta);
    }
}

