/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.collect.Lists
 *  com.mojang.datafixers.util.Pair
 */
package net.minecraft.network.packet.s2c.play;

import com.google.common.collect.Lists;
import com.mojang.datafixers.util.Pair;
import java.io.IOException;
import java.util.List;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraft.network.Packet;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.network.listener.ClientPlayPacketListener;

public class EntityEquipmentUpdateS2CPacket
implements Packet<ClientPlayPacketListener> {
    private int id;
    private final List<Pair<EquipmentSlot, ItemStack>> equipmentList;

    public EntityEquipmentUpdateS2CPacket() {
        this.equipmentList = Lists.newArrayList();
    }

    public EntityEquipmentUpdateS2CPacket(int id, List<Pair<EquipmentSlot, ItemStack>> equipmentList) {
        this.id = id;
        this.equipmentList = equipmentList;
    }

    @Override
    public void read(PacketByteBuf buf) throws IOException {
        this.id = buf.readVarInt();
        EquipmentSlot[] equipmentSlotArray = EquipmentSlot.values();
        do {
            byte by = buf.readByte();
            EquipmentSlot _snowman2 = equipmentSlotArray[by & 0x7F];
            ItemStack _snowman3 = buf.readItemStack();
            this.equipmentList.add((Pair<EquipmentSlot, ItemStack>)Pair.of((Object)((Object)_snowman2), (Object)_snowman3));
        } while ((by & 0xFFFFFF80) != 0);
    }

    @Override
    public void write(PacketByteBuf buf) throws IOException {
        buf.writeVarInt(this.id);
        int n = this.equipmentList.size();
        for (_snowman = 0; _snowman < n; ++_snowman) {
            Pair<EquipmentSlot, ItemStack> pair = this.equipmentList.get(_snowman);
            EquipmentSlot _snowman2 = (EquipmentSlot)((Object)pair.getFirst());
            boolean _snowman3 = _snowman != n - 1;
            int _snowman4 = _snowman2.ordinal();
            buf.writeByte(_snowman3 ? _snowman4 | 0xFFFFFF80 : _snowman4);
            buf.writeItemStack((ItemStack)pair.getSecond());
        }
    }

    @Override
    public void apply(ClientPlayPacketListener clientPlayPacketListener) {
        clientPlayPacketListener.onEquipmentUpdate(this);
    }

    public int getId() {
        return this.id;
    }

    public List<Pair<EquipmentSlot, ItemStack>> getEquipmentList() {
        return this.equipmentList;
    }
}

