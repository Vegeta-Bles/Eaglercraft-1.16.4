/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.client.gui.hud.spectator;

import net.minecraft.client.gui.hud.spectator.SpectatorMenu;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.text.Text;

public interface SpectatorMenuCommand {
    public void use(SpectatorMenu var1);

    public Text getName();

    public void renderIcon(MatrixStack var1, float var2, int var3);

    public boolean isEnabled();
}

