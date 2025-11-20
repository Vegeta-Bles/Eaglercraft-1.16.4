/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.annotation.Nullable
 */
package net.minecraft.world.chunk;

import java.util.function.Predicate;
import javax.annotation.Nullable;
import net.minecraft.nbt.ListTag;
import net.minecraft.network.PacketByteBuf;

public interface Palette<T> {
    public int getIndex(T var1);

    public boolean accepts(Predicate<T> var1);

    @Nullable
    public T getByIndex(int var1);

    public void fromPacket(PacketByteBuf var1);

    public void toPacket(PacketByteBuf var1);

    public int getPacketSize();

    public void fromTag(ListTag var1);
}

