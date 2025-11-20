/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.collect.Lists
 *  com.mojang.brigadier.Message
 *  com.mojang.brigadier.context.StringRange
 *  com.mojang.brigadier.suggestion.Suggestion
 *  com.mojang.brigadier.suggestion.Suggestions
 */
package net.minecraft.network.packet.s2c.play;

import com.google.common.collect.Lists;
import com.mojang.brigadier.Message;
import com.mojang.brigadier.context.StringRange;
import com.mojang.brigadier.suggestion.Suggestion;
import com.mojang.brigadier.suggestion.Suggestions;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import net.minecraft.network.Packet;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.network.listener.ClientPlayPacketListener;
import net.minecraft.text.Text;
import net.minecraft.text.Texts;

public class CommandSuggestionsS2CPacket
implements Packet<ClientPlayPacketListener> {
    private int completionId;
    private Suggestions suggestions;

    public CommandSuggestionsS2CPacket() {
    }

    public CommandSuggestionsS2CPacket(int completionId, Suggestions suggestions) {
        this.completionId = completionId;
        this.suggestions = suggestions;
    }

    @Override
    public void read(PacketByteBuf buf) throws IOException {
        this.completionId = buf.readVarInt();
        int n = buf.readVarInt();
        _snowman = buf.readVarInt();
        StringRange _snowman2 = StringRange.between((int)n, (int)(n + _snowman));
        _snowman = buf.readVarInt();
        ArrayList _snowman3 = Lists.newArrayListWithCapacity((int)_snowman);
        for (_snowman = 0; _snowman < _snowman; ++_snowman) {
            String string = buf.readString(Short.MAX_VALUE);
            Text _snowman4 = buf.readBoolean() ? buf.readText() : null;
            _snowman3.add(new Suggestion(_snowman2, string, (Message)_snowman4));
        }
        this.suggestions = new Suggestions(_snowman2, (List)_snowman3);
    }

    @Override
    public void write(PacketByteBuf buf) throws IOException {
        buf.writeVarInt(this.completionId);
        buf.writeVarInt(this.suggestions.getRange().getStart());
        buf.writeVarInt(this.suggestions.getRange().getLength());
        buf.writeVarInt(this.suggestions.getList().size());
        for (Suggestion suggestion : this.suggestions.getList()) {
            buf.writeString(suggestion.getText());
            buf.writeBoolean(suggestion.getTooltip() != null);
            if (suggestion.getTooltip() == null) continue;
            buf.writeText(Texts.toText(suggestion.getTooltip()));
        }
    }

    @Override
    public void apply(ClientPlayPacketListener clientPlayPacketListener) {
        clientPlayPacketListener.onCommandSuggestions(this);
    }

    public int getCompletionId() {
        return this.completionId;
    }

    public Suggestions getSuggestions() {
        return this.suggestions;
    }
}

