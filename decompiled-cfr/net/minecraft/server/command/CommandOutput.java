/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.server.command;

import java.util.UUID;
import net.minecraft.text.Text;

public interface CommandOutput {
    public static final CommandOutput DUMMY = new CommandOutput(){

        public void sendSystemMessage(Text message, UUID senderUuid) {
        }

        public boolean shouldReceiveFeedback() {
            return false;
        }

        public boolean shouldTrackOutput() {
            return false;
        }

        public boolean shouldBroadcastConsoleToOps() {
            return false;
        }
    };

    public void sendSystemMessage(Text var1, UUID var2);

    public boolean shouldReceiveFeedback();

    public boolean shouldTrackOutput();

    public boolean shouldBroadcastConsoleToOps();
}

