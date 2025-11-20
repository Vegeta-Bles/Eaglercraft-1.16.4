package net.minecraft.world.chunk;

import java.util.function.Function;
import java.util.function.Predicate;
import javax.annotation.Nullable;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.util.collection.IdList;
import net.minecraft.util.collection.Int2ObjectBiMap;

public class BiMapPalette<T> implements Palette<T> {
   private final IdList<T> idList;
   private final Int2ObjectBiMap<T> map;
   private final PaletteResizeListener<T> resizeHandler;
   private final Function<CompoundTag, T> elementDeserializer;
   private final Function<T, CompoundTag> elementSerializer;
   private final int indexBits;

   public BiMapPalette(
      IdList<T> idList,
      int indexBits,
      PaletteResizeListener<T> resizeHandler,
      Function<CompoundTag, T> elementDeserializer,
      Function<T, CompoundTag> elementSerializer
   ) {
      this.idList = idList;
      this.indexBits = indexBits;
      this.resizeHandler = resizeHandler;
      this.elementDeserializer = elementDeserializer;
      this.elementSerializer = elementSerializer;
      this.map = new Int2ObjectBiMap<>(1 << indexBits);
   }

   @Override
   public int getIndex(T object) {
      int _snowman = this.map.getRawId(object);
      if (_snowman == -1) {
         _snowman = this.map.add(object);
         if (_snowman >= 1 << this.indexBits) {
            _snowman = this.resizeHandler.onResize(this.indexBits + 1, object);
         }
      }

      return _snowman;
   }

   @Override
   public boolean accepts(Predicate<T> _snowman) {
      for (int _snowmanx = 0; _snowmanx < this.getIndexBits(); _snowmanx++) {
         if (_snowman.test(this.map.get(_snowmanx))) {
            return true;
         }
      }

      return false;
   }

   @Nullable
   @Override
   public T getByIndex(int index) {
      return this.map.get(index);
   }

   @Override
   public void fromPacket(PacketByteBuf buf) {
      this.map.clear();
      int _snowman = buf.readVarInt();

      for (int _snowmanx = 0; _snowmanx < _snowman; _snowmanx++) {
         this.map.add(this.idList.get(buf.readVarInt()));
      }
   }

   @Override
   public void toPacket(PacketByteBuf buf) {
      int _snowman = this.getIndexBits();
      buf.writeVarInt(_snowman);

      for (int _snowmanx = 0; _snowmanx < _snowman; _snowmanx++) {
         buf.writeVarInt(this.idList.getRawId(this.map.get(_snowmanx)));
      }
   }

   @Override
   public int getPacketSize() {
      int _snowman = PacketByteBuf.getVarIntSizeBytes(this.getIndexBits());

      for (int _snowmanx = 0; _snowmanx < this.getIndexBits(); _snowmanx++) {
         _snowman += PacketByteBuf.getVarIntSizeBytes(this.idList.getRawId(this.map.get(_snowmanx)));
      }

      return _snowman;
   }

   public int getIndexBits() {
      return this.map.size();
   }

   @Override
   public void fromTag(ListTag tag) {
      this.map.clear();

      for (int _snowman = 0; _snowman < tag.size(); _snowman++) {
         this.map.add(this.elementDeserializer.apply(tag.getCompound(_snowman)));
      }
   }

   public void toTag(ListTag tag) {
      for (int _snowman = 0; _snowman < this.getIndexBits(); _snowman++) {
         tag.add(this.elementSerializer.apply(this.map.get(_snowman)));
      }
   }
}
