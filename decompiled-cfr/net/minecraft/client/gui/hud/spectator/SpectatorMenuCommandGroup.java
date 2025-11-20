/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.client.gui.hud.spectator;

import java.util.List;
import net.minecraft.client.gui.hud.spectator.SpectatorMenuCommand;
import net.minecraft.text.Text;

public interface SpectatorMenuCommandGroup {
    public List<SpectatorMenuCommand> getCommands();

    public Text getPrompt();
}

