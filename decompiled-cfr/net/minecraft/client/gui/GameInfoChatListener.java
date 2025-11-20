/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.client.gui;

import java.util.UUID;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.ClientChatListener;
import net.minecraft.network.MessageType;
import net.minecraft.text.Text;

public class GameInfoChatListener
implements ClientChatListener {
    private final MinecraftClient client;

    public GameInfoChatListener(MinecraftClient client) {
        this.client = client;
    }

    @Override
    public void onChatMessage(MessageType messageType, Text message, UUID senderUuid) {
        this.client.inGameHud.setOverlayMessage(message, false);
    }
}

