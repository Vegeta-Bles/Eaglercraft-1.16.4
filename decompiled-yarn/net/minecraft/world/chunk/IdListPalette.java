package net.minecraft.world.chunk;

import java.util.function.Predicate;
import net.minecraft.nbt.ListTag;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.util.collection.IdList;

public class IdListPalette<T> implements Palette<T> {
   private final IdList<T> idList;
   private final T defaultValue;

   public IdListPalette(IdList<T> idList, T defaultValue) {
      this.idList = idList;
      this.defaultValue = defaultValue;
   }

   @Override
   public int getIndex(T object) {
      int _snowman = this.idList.getRawId(object);
      return _snowman == -1 ? 0 : _snowman;
   }

   @Override
   public boolean accepts(Predicate<T> _snowman) {
      return true;
   }

   @Override
   public T getByIndex(int index) {
      T _snowman = this.idList.get(index);
      return _snowman == null ? this.defaultValue : _snowman;
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
