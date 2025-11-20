/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.client.gui;

import java.util.UUID;
import net.minecraft.network.MessageType;
import net.minecraft.text.Text;

public interface ClientChatListener {
    public void onChatMessage(MessageType var1, Text var2, UUID var3);
}

