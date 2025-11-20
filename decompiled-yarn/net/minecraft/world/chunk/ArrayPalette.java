package net.minecraft.world.chunk;

import java.util.function.Function;
import java.util.function.Predicate;
import javax.annotation.Nullable;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.util.collection.IdList;

public class ArrayPalette<T> implements Palette<T> {
   private final IdList<T> idList;
   private final T[] array;
   private final PaletteResizeListener<T> resizeListener;
   private final Function<CompoundTag, T> valueDeserializer;
   private final int indexBits;
   private int size;

   public ArrayPalette(IdList<T> _snowman, int integer, PaletteResizeListener<T> resizeListener, Function<CompoundTag, T> valueDeserializer) {
      this.idList = _snowman;
      this.array = (T[])(new Object[1 << integer]);
      this.indexBits = integer;
      this.resizeListener = resizeListener;
      this.valueDeserializer = valueDeserializer;
   }

   @Override
   public int getIndex(T object) {
      for (int _snowman = 0; _snowman < this.size; _snowman++) {
         if (this.array[_snowman] == object) {
            return _snowman;
         }
      }

      int _snowmanx = this.size;
      if (_snowmanx < this.array.length) {
         this.array[_snowmanx] = object;
         this.size++;
         return _snowmanx;
      } else {
         return this.resizeListener.onResize(this.indexBits + 1, object);
      }
   }

   @Override
   public boolean accepts(Predicate<T> _snowman) {
      for (int _snowmanx = 0; _snowmanx < this.size; _snowmanx++) {
         if (_snowman.test(this.array[_snowmanx])) {
            return true;
         }
      }

      return false;
   }

   @Nullable
   @Override
   public T getByIndex(int index) {
      return index >= 0 && index < this.size ? this.array[index] : null;
   }

   @Override
   public void fromPacket(PacketByteBuf buf) {
      this.size = buf.readVarInt();

      for (int _snowman = 0; _snowman < this.size; _snowman++) {
         this.array[_snowman] = this.idList.get(buf.readVarInt());
      }
   }

   @Override
   public void toPacket(PacketByteBuf buf) {
      buf.writeVarInt(this.size);

      for (int _snowman = 0; _snowman < this.size; _snowman++) {
         buf.writeVarInt(this.idList.getRawId(this.array[_snowman]));
      }
   }

   @Override
   public int getPacketSize() {
      int _snowman = PacketByteBuf.getVarIntSizeBytes(this.getSize());

      for (int _snowmanx = 0; _snowmanx < this.getSize(); _snowmanx++) {
         _snowman += PacketByteBuf.getVarIntSizeBytes(this.idList.getRawId(this.array[_snowmanx]));
      }

      return _snowman;
   }

   public int getSize() {
      return this.size;
   }

   @Override
   public void fromTag(ListTag tag) {
      for (int _snowman = 0; _snowman < tag.size(); _snowman++) {
         this.array[_snowman] = this.valueDeserializer.apply(tag.getCompound(_snowman));
      }

      this.size = tag.size();
   }
}
