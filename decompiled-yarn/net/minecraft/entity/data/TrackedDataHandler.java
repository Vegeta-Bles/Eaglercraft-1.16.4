package net.minecraft.entity.data;

import net.minecraft.network.PacketByteBuf;

public interface TrackedDataHandler<T> {
   void write(PacketByteBuf data, T var2);

   T read(PacketByteBuf var1);

   default TrackedData<T> create(int _snowman) {
      return new TrackedData<>(_snowman, this);
   }

   T copy(T var1);
}
