/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.world.chunk;

import java.util.function.Predicate;
import net.minecraft.nbt.ListTag;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.util.collection.IdList;
import net.minecraft.world.chunk.Palette;

public class IdListPalette<T>
implements Palette<T> {
    private final IdList<T> idList;
    private final T defaultValue;

    public IdListPalette(IdList<T> idList, T defaultValue) {
        this.idList = idList;
        this.defaultValue = defaultValue;
    }

    @Override
    public int getIndex(T object) {
        int n = this.idList.getRawId(object);
        return n == -1 ? 0 : n;
    }

    @Override
    public boolean accepts(Predicate<T> predicate) {
        return true;
    }

    @Override
    public T getByIndex(int index) {
        T t = this.idList.get(index);
        return t == null ? this.defaultValue : t;
    }

    @Override
    public void fromPacket(PacketByteBuf buf) {
    }

    @Override
    public void toPacket(PacketByteBuf buf) {
    }

    @Override
    public int getPacketSize() {
        return PacketByteBuf.getVarIntSizeBytes(0);
    }

    @Override
    public void fromTag(ListTag tag) {
    }
}

