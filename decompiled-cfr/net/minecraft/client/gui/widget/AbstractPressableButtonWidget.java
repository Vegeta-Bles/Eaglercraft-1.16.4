/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.client.gui.widget;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.widget.AbstractButtonWidget;
import net.minecraft.text.Text;

public abstract class AbstractPressableButtonWidget
extends AbstractButtonWidget {
    public AbstractPressableButtonWidget(int n, int n2, int n3, int n4, Text text) {
        super(n, n2, n3, n4, text);
    }

    public abstract void onPress();

    @Override
    public void onClick(double mouseX, double mouseY) {
        this.onPress();
    }

    @Override
    public boolean keyPressed(int keyCode, int scanCode, int modifiers) {
        if (!this.active || !this.visible) {
            return false;
        }
        if (keyCode == 257 || keyCode == 32 || keyCode == 335) {
            this.playDownSound(MinecraftClient.getInstance().getSoundManager());
            this.onPress();
            return true;
        }
        return false;
    }
}

