/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.collect.Maps
 *  com.google.common.collect.Sets
 */
package net.minecraft.network.packet.s2c.play;

import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import java.io.IOException;
import java.util.Collection;
import java.util.Map;
import java.util.Set;
import net.minecraft.advancement.Advancement;
import net.minecraft.advancement.AdvancementProgress;
import net.minecraft.network.Packet;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.network.listener.ClientPlayPacketListener;
import net.minecraft.util.Identifier;

public class AdvancementUpdateS2CPacket
implements Packet<ClientPlayPacketListener> {
    private boolean clearCurrent;
    private Map<Identifier, Advancement.Task> toEarn;
    private Set<Identifier> toRemove;
    private Map<Identifier, AdvancementProgress> toSetProgress;

    public AdvancementUpdateS2CPacket() {
    }

    public AdvancementUpdateS2CPacket(boolean clearCurrent, Collection<Advancement> toEarn, Set<Identifier> toRemove, Map<Identifier, AdvancementProgress> toSetProgress) {
        this.clearCurrent = clearCurrent;
        this.toEarn = Maps.newHashMap();
        for (Advancement advancement : toEarn) {
            this.toEarn.put(advancement.getId(), advancement.createTask());
        }
        this.toRemove = toRemove;
        this.toSetProgress = Maps.newHashMap(toSetProgress);
    }

    @Override
    public void apply(ClientPlayPacketListener clientPlayPacketListener) {
        clientPlayPacketListener.onAdvancements(this);
    }

    @Override
    public void read(PacketByteBuf buf) throws IOException {
        Identifier identifier;
        this.clearCurrent = buf.readBoolean();
        this.toEarn = Maps.newHashMap();
        this.toRemove = Sets.newLinkedHashSet();
        this.toSetProgress = Maps.newHashMap();
        int n = buf.readVarInt();
        for (_snowman = 0; _snowman < n; ++_snowman) {
            identifier = buf.readIdentifier();
            Advancement.Task _snowman2 = Advancement.Task.fromPacket(buf);
            this.toEarn.put(identifier, _snowman2);
        }
        n = buf.readVarInt();
        for (_snowman = 0; _snowman < n; ++_snowman) {
            identifier = buf.readIdentifier();
            this.toRemove.add(identifier);
        }
        n = buf.readVarInt();
        for (_snowman = 0; _snowman < n; ++_snowman) {
            identifier = buf.readIdentifier();
            this.toSetProgress.put(identifier, AdvancementProgress.fromPacket(buf));
        }
    }

    @Override
    public void write(PacketByteBuf buf) throws IOException {
        buf.writeBoolean(this.clearCurrent);
        buf.writeVarInt(this.toEarn.size());
        for (Map.Entry<Identifier, Advancement.Task> entry : this.toEarn.entrySet()) {
            Identifier identifier = entry.getKey();
            Advancement.Task _snowman2 = entry.getValue();
            buf.writeIdentifier(identifier);
            _snowman2.toPacket(buf);
        }
        buf.writeVarInt(this.toRemove.size());
        for (Identifier identifier : this.toRemove) {
            buf.writeIdentifier(identifier);
        }
        buf.writeVarInt(this.toSetProgress.size());
        for (Map.Entry entry : this.toSetProgress.entrySet()) {
            buf.writeIdentifier((Identifier)entry.getKey());
            ((AdvancementProgress)entry.getValue()).toPacket(buf);
        }
    }

    public Map<Identifier, Advancement.Task> getAdvancementsToEarn() {
        return this.toEarn;
    }

    public Set<Identifier> getAdvancementIdsToRemove() {
        return this.toRemove;
    }

    public Map<Identifier, AdvancementProgress> getAdvancementsToProgress() {
        return this.toSetProgress;
    }

    public boolean shouldClearCurrent() {
        return this.clearCurrent;
    }
}

