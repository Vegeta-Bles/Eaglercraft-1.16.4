/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.client.gui.widget;

import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.widget.AbstractButtonWidget;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.text.LiteralText;
import net.minecraft.util.Identifier;

public class ToggleButtonWidget
extends AbstractButtonWidget {
    protected Identifier texture;
    protected boolean toggled;
    protected int u;
    protected int v;
    protected int pressedUOffset;
    protected int hoverVOffset;

    public ToggleButtonWidget(int x, int y, int width, int height, boolean toggled) {
        super(x, y, width, height, LiteralText.EMPTY);
        this.toggled = toggled;
    }

    public void setTextureUV(int u, int v, int pressedUOffset, int hoverVOffset, Identifier texture) {
        this.u = u;
        this.v = v;
        this.pressedUOffset = pressedUOffset;
        this.hoverVOffset = hoverVOffset;
        this.texture = texture;
    }

    public void setToggled(boolean toggled) {
        this.toggled = toggled;
    }

    public boolean isToggled() {
        return this.toggled;
    }

    public void setPos(int x, int y) {
        this.x = x;
        this.y = y;
    }

    @Override
    public void renderButton(MatrixStack matrices, int mouseX, int mouseY, float delta) {
        MinecraftClient minecraftClient = MinecraftClient.getInstance();
        minecraftClient.getTextureManager().bindTexture(this.texture);
        RenderSystem.disableDepthTest();
        int _snowman2 = this.u;
        int _snowman3 = this.v;
        if (this.toggled) {
            _snowman2 += this.pressedUOffset;
        }
        if (this.isHovered()) {
            _snowman3 += this.hoverVOffset;
        }
        this.drawTexture(matrices, this.x, this.y, _snowman2, _snowman3, this.width, this.height);
        RenderSystem.enableDepthTest();
    }
}

