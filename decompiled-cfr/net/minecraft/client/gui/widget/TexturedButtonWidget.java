/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.client.gui.widget;

import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.text.LiteralText;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

public class TexturedButtonWidget
extends ButtonWidget {
    private final Identifier texture;
    private final int u;
    private final int v;
    private final int hoveredVOffset;
    private final int textureWidth;
    private final int textureHeight;

    public TexturedButtonWidget(int x, int y, int width, int height, int u, int v, int hoveredVOffset, Identifier texture, ButtonWidget.PressAction pressAction) {
        this(x, y, width, height, u, v, hoveredVOffset, texture, 256, 256, pressAction);
    }

    public TexturedButtonWidget(int x, int y, int width, int height, int u, int v, int hoveredVOffset, Identifier texture, int textureWidth, int textureHeight, ButtonWidget.PressAction pressAction) {
        this(x, y, width, height, u, v, hoveredVOffset, texture, textureWidth, textureHeight, pressAction, LiteralText.EMPTY);
    }

    public TexturedButtonWidget(int n, int n2, int n3, int n4, int n5, int n6, int n7, Identifier identifier, int n8, int n9, ButtonWidget.PressAction pressAction, Text text) {
        this(n, n2, n3, n4, n5, n6, n7, identifier, n8, n9, pressAction, EMPTY, text);
    }

    public TexturedButtonWidget(int x, int y, int width, int height, int u, int v, int hoveredVOffset, Identifier texture, int textureWidth, int textureHeight, ButtonWidget.PressAction pressAction, ButtonWidget.TooltipSupplier tooltipSupplier, Text text) {
        super(x, y, width, height, text, pressAction, tooltipSupplier);
        this.textureWidth = textureWidth;
        this.textureHeight = textureHeight;
        this.u = u;
        this.v = v;
        this.hoveredVOffset = hoveredVOffset;
        this.texture = texture;
    }

    public void setPos(int x, int y) {
        this.x = x;
        this.y = y;
    }

    @Override
    public void renderButton(MatrixStack matrices, int mouseX, int mouseY, float delta) {
        MinecraftClient minecraftClient = MinecraftClient.getInstance();
        minecraftClient.getTextureManager().bindTexture(this.texture);
        int _snowman2 = this.v;
        if (this.isHovered()) {
            _snowman2 += this.hoveredVOffset;
        }
        RenderSystem.enableDepthTest();
        TexturedButtonWidget.drawTexture(matrices, this.x, this.y, this.u, _snowman2, this.width, this.height, this.textureWidth, this.textureHeight);
        if (this.isHovered()) {
            this.renderToolTip(matrices, mouseX, mouseY);
        }
    }
}

