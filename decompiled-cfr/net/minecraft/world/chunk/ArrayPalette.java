/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.annotation.Nullable
 */
package net.minecraft.world.chunk;

import java.util.function.Function;
import java.util.function.Predicate;
import javax.annotation.Nullable;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.util.collection.IdList;
import net.minecraft.world.chunk.Palette;
import net.minecraft.world.chunk.PaletteResizeListener;

public class ArrayPalette<T>
implements Palette<T> {
    private final IdList<T> idList;
    private final T[] array;
    private final PaletteResizeListener<T> resizeListener;
    private final Function<CompoundTag, T> valueDeserializer;
    private final int indexBits;
    private int size;

    public ArrayPalette(IdList<T> idList, int integer, PaletteResizeListener<T> resizeListener, Function<CompoundTag, T> valueDeserializer) {
        this.idList = idList;
        this.array = new Object[1 << integer];
        this.indexBits = integer;
        this.resizeListener = resizeListener;
        this.valueDeserializer = valueDeserializer;
    }

    @Override
    public int getIndex(T object) {
        int n;
        for (n = 0; n < this.size; ++n) {
            if (this.array[n] != object) continue;
            return n;
        }
        if ((n = this.size++) < this.array.length) {
            this.array[n] = object;
            return n;
        }
        return this.resizeListener.onResize(this.indexBits + 1, object);
    }

    @Override
    public boolean accepts(Predicate<T> predicate) {
        for (int i = 0; i < this.size; ++i) {
            if (!predicate.test(this.array[i])) continue;
            return true;
        }
        return false;
    }

    @Override
    @Nullable
    public T getByIndex(int index) {
        if (index >= 0 && index < this.size) {
            return this.array[index];
        }
        return null;
    }

    @Override
    public void fromPacket(PacketByteBuf buf) {
        this.size = buf.readVarInt();
        for (int i = 0; i < this.size; ++i) {
            this.array[i] = this.idList.get(buf.readVarInt());
        }
    }

    @Override
    public void toPacket(PacketByteBuf buf) {
        buf.writeVarInt(this.size);
        for (int i = 0; i < this.size; ++i) {
            buf.writeVarInt(this.idList.getRawId(this.array[i]));
        }
    }

    @Override
    public int getPacketSize() {
        int n = PacketByteBuf.getVarIntSizeBytes(this.getSize());
        for (_snowman = 0; _snowman < this.getSize(); ++_snowman) {
            n += PacketByteBuf.getVarIntSizeBytes(this.idList.getRawId(this.array[_snowman]));
        }
        return n;
    }

    public int getSize() {
        return this.size;
    }

    @Override
    public void fromTag(ListTag tag) {
        for (int i = 0; i < tag.size(); ++i) {
            this.array[i] = this.valueDeserializer.apply(tag.getCompound(i));
        }
        this.size = tag.size();
    }
}

