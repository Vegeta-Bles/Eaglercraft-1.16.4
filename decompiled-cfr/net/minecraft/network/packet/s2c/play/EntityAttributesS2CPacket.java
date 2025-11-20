/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.collect.Lists
 */
package net.minecraft.network.packet.s2c.play;

import com.google.common.collect.Lists;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.UUID;
import net.minecraft.entity.attribute.EntityAttribute;
import net.minecraft.entity.attribute.EntityAttributeInstance;
import net.minecraft.entity.attribute.EntityAttributeModifier;
import net.minecraft.network.Packet;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.network.listener.ClientPlayPacketListener;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class EntityAttributesS2CPacket
implements Packet<ClientPlayPacketListener> {
    private int entityId;
    private final List<Entry> entries = Lists.newArrayList();

    public EntityAttributesS2CPacket() {
    }

    public EntityAttributesS2CPacket(int entityId, Collection<EntityAttributeInstance> attributes) {
        this.entityId = entityId;
        for (EntityAttributeInstance entityAttributeInstance : attributes) {
            this.entries.add(new Entry(entityAttributeInstance.getAttribute(), entityAttributeInstance.getBaseValue(), entityAttributeInstance.getModifiers()));
        }
    }

    @Override
    public void read(PacketByteBuf buf) throws IOException {
        this.entityId = buf.readVarInt();
        int n = buf.readInt();
        for (_snowman = 0; _snowman < n; ++_snowman) {
            Identifier identifier = buf.readIdentifier();
            EntityAttribute _snowman2 = Registry.ATTRIBUTE.get(identifier);
            double _snowman3 = buf.readDouble();
            ArrayList _snowman4 = Lists.newArrayList();
            int _snowman5 = buf.readVarInt();
            for (int i = 0; i < _snowman5; ++i) {
                UUID uUID = buf.readUuid();
                _snowman4.add(new EntityAttributeModifier(uUID, "Unknown synced attribute modifier", buf.readDouble(), EntityAttributeModifier.Operation.fromId(buf.readByte())));
            }
            this.entries.add(new Entry(_snowman2, _snowman3, _snowman4));
        }
    }

    @Override
    public void write(PacketByteBuf buf) throws IOException {
        buf.writeVarInt(this.entityId);
        buf.writeInt(this.entries.size());
        for (Entry entry : this.entries) {
            buf.writeIdentifier(Registry.ATTRIBUTE.getId(entry.getId()));
            buf.writeDouble(entry.getBaseValue());
            buf.writeVarInt(entry.getModifiers().size());
            for (EntityAttributeModifier entityAttributeModifier : entry.getModifiers()) {
                buf.writeUuid(entityAttributeModifier.getId());
                buf.writeDouble(entityAttributeModifier.getValue());
                buf.writeByte(entityAttributeModifier.getOperation().getId());
            }
        }
    }

    @Override
    public void apply(ClientPlayPacketListener clientPlayPacketListener) {
        clientPlayPacketListener.onEntityAttributes(this);
    }

    public int getEntityId() {
        return this.entityId;
    }

    public List<Entry> getEntries() {
        return this.entries;
    }

    public class Entry {
        private final EntityAttribute id;
        private final double baseValue;
        private final Collection<EntityAttributeModifier> modifiers;

        public Entry(EntityAttribute entityAttribute, double d, Collection<EntityAttributeModifier> collection) {
            this.id = entityAttribute;
            this.baseValue = d;
            this.modifiers = collection;
        }

        public EntityAttribute getId() {
            return this.id;
        }

        public double getBaseValue() {
            return this.baseValue;
        }

        public Collection<EntityAttributeModifier> getModifiers() {
            return this.modifiers;
        }
    }
}

