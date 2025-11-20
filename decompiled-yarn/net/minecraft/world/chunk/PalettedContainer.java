package net.minecraft.world.chunk;

import it.unimi.dsi.fastutil.ints.Int2IntMap;
import it.unimi.dsi.fastutil.ints.Int2IntOpenHashMap;
import java.util.Arrays;
import java.util.Objects;
import java.util.concurrent.locks.ReentrantLock;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.util.collection.IdList;
import net.minecraft.util.collection.PackedIntegerArray;
import net.minecraft.util.crash.CrashException;
import net.minecraft.util.crash.CrashReport;
import net.minecraft.util.crash.CrashReportSection;
import net.minecraft.util.math.MathHelper;

public class PalettedContainer<T> implements PaletteResizeListener<T> {
   private final Palette<T> fallbackPalette;
   private final PaletteResizeListener<T> noOpPaletteResizeHandler = (newSize, added) -> 0;
   private final IdList<T> idList;
   private final Function<CompoundTag, T> elementDeserializer;
   private final Function<T, CompoundTag> elementSerializer;
   private final T defaultValue;
   protected PackedIntegerArray data;
   private Palette<T> palette;
   private int paletteSize;
   private final ReentrantLock writeLock = new ReentrantLock();

   public void lock() {
      if (this.writeLock.isLocked() && !this.writeLock.isHeldByCurrentThread()) {
         String _snowman = Thread.getAllStackTraces()
            .keySet()
            .stream()
            .filter(Objects::nonNull)
            .map(thread -> thread.getName() + ": \n\tat " + Arrays.stream(thread.getStackTrace()).map(Object::toString).collect(Collectors.joining("\n\tat ")))
            .collect(Collectors.joining("\n"));
         CrashReport _snowmanx = new CrashReport("Writing into PalettedContainer from multiple threads", new IllegalStateException());
         CrashReportSection _snowmanxx = _snowmanx.addElement("Thread dumps");
         _snowmanxx.add("Thread dumps", _snowman);
         throw new CrashException(_snowmanx);
      } else {
         this.writeLock.lock();
      }
   }

   public void unlock() {
      this.writeLock.unlock();
   }

   public PalettedContainer(
      Palette<T> fallbackPalette, IdList<T> idList, Function<CompoundTag, T> elementDeserializer, Function<T, CompoundTag> elementSerializer, T defaultElement
   ) {
      this.fallbackPalette = fallbackPalette;
      this.idList = idList;
      this.elementDeserializer = elementDeserializer;
      this.elementSerializer = elementSerializer;
      this.defaultValue = defaultElement;
      this.setPaletteSize(4);
   }

   private static int toIndex(int x, int y, int z) {
      return y << 8 | z << 4 | x;
   }

   private void setPaletteSize(int size) {
      if (size != this.paletteSize) {
         this.paletteSize = size;
         if (this.paletteSize <= 4) {
            this.paletteSize = 4;
            this.palette = new ArrayPalette<>(this.idList, this.paletteSize, this, this.elementDeserializer);
         } else if (this.paletteSize < 9) {
            this.palette = new BiMapPalette<>(this.idList, this.paletteSize, this, this.elementDeserializer, this.elementSerializer);
         } else {
            this.palette = this.fallbackPalette;
            this.paletteSize = MathHelper.log2DeBruijn(this.idList.size());
         }

         this.palette.getIndex(this.defaultValue);
         this.data = new PackedIntegerArray(this.paletteSize, 4096);
      }
   }

   @Override
   public int onResize(int _snowman, T _snowman) {
      this.lock();
      PackedIntegerArray _snowmanxx = this.data;
      Palette<T> _snowmanxxx = this.palette;
      this.setPaletteSize(_snowman);

      for (int _snowmanxxxx = 0; _snowmanxxxx < _snowmanxx.getSize(); _snowmanxxxx++) {
         T _snowmanxxxxx = _snowmanxxx.getByIndex(_snowmanxx.get(_snowmanxxxx));
         if (_snowmanxxxxx != null) {
            this.set(_snowmanxxxx, _snowmanxxxxx);
         }
      }

      int _snowmanxxxxx = this.palette.getIndex(_snowman);
      this.unlock();
      return _snowmanxxxxx;
   }

   public T setSync(int x, int y, int z, T value) {
      this.lock();
      T _snowman = this.setAndGetOldValue(toIndex(x, y, z), value);
      this.unlock();
      return _snowman;
   }

   public T set(int x, int y, int z, T value) {
      return this.setAndGetOldValue(toIndex(x, y, z), value);
   }

   protected T setAndGetOldValue(int index, T value) {
      int _snowman = this.palette.getIndex(value);
      int _snowmanx = this.data.setAndGetOldValue(index, _snowman);
      T _snowmanxx = this.palette.getByIndex(_snowmanx);
      return _snowmanxx == null ? this.defaultValue : _snowmanxx;
   }

   protected void set(int _snowman, T _snowman) {
      int _snowmanxx = this.palette.getIndex(_snowman);
      this.data.set(_snowman, _snowmanxx);
   }

   public T get(int x, int y, int z) {
      return this.get(toIndex(x, y, z));
   }

   protected T get(int index) {
      T _snowman = this.palette.getByIndex(this.data.get(index));
      return _snowman == null ? this.defaultValue : _snowman;
   }

   public void fromPacket(PacketByteBuf buf) {
      this.lock();
      int _snowman = buf.readByte();
      if (this.paletteSize != _snowman) {
         this.setPaletteSize(_snowman);
      }

      this.palette.fromPacket(buf);
      buf.readLongArray(this.data.getStorage());
      this.unlock();
   }

   public void toPacket(PacketByteBuf buf) {
      this.lock();
      buf.writeByte(this.paletteSize);
      this.palette.toPacket(buf);
      buf.writeLongArray(this.data.getStorage());
      this.unlock();
   }

   public void read(ListTag paletteTag, long[] data) {
      this.lock();
      int _snowman = Math.max(4, MathHelper.log2DeBruijn(paletteTag.size()));
      if (_snowman != this.paletteSize) {
         this.setPaletteSize(_snowman);
      }

      this.palette.fromTag(paletteTag);
      int _snowmanx = data.length * 64 / 4096;
      if (this.palette == this.fallbackPalette) {
         Palette<T> _snowmanxx = new BiMapPalette<>(this.idList, _snowman, this.noOpPaletteResizeHandler, this.elementDeserializer, this.elementSerializer);
         _snowmanxx.fromTag(paletteTag);
         PackedIntegerArray _snowmanxxx = new PackedIntegerArray(_snowman, 4096, data);

         for (int _snowmanxxxx = 0; _snowmanxxxx < 4096; _snowmanxxxx++) {
            this.data.set(_snowmanxxxx, this.fallbackPalette.getIndex(_snowmanxx.getByIndex(_snowmanxxx.get(_snowmanxxxx))));
         }
      } else if (_snowmanx == this.paletteSize) {
         System.arraycopy(data, 0, this.data.getStorage(), 0, data.length);
      } else {
         PackedIntegerArray _snowmanxx = new PackedIntegerArray(_snowmanx, 4096, data);

         for (int _snowmanxxx = 0; _snowmanxxx < 4096; _snowmanxxx++) {
            this.data.set(_snowmanxxx, _snowmanxx.get(_snowmanxxx));
         }
      }

      this.unlock();
   }

   public void write(CompoundTag _snowman, String _snowman, String _snowman) {
      this.lock();
      BiMapPalette<T> _snowmanxxx = new BiMapPalette<>(this.idList, this.paletteSize, this.noOpPaletteResizeHandler, this.elementDeserializer, this.elementSerializer);
      T _snowmanxxxx = this.defaultValue;
      int _snowmanxxxxx = _snowmanxxx.getIndex(this.defaultValue);
      int[] _snowmanxxxxxx = new int[4096];

      for (int _snowmanxxxxxxx = 0; _snowmanxxxxxxx < 4096; _snowmanxxxxxxx++) {
         T _snowmanxxxxxxxx = this.get(_snowmanxxxxxxx);
         if (_snowmanxxxxxxxx != _snowmanxxxx) {
            _snowmanxxxx = _snowmanxxxxxxxx;
            _snowmanxxxxx = _snowmanxxx.getIndex(_snowmanxxxxxxxx);
         }

         _snowmanxxxxxx[_snowmanxxxxxxx] = _snowmanxxxxx;
      }

      ListTag _snowmanxxxxxxx = new ListTag();
      _snowmanxxx.toTag(_snowmanxxxxxxx);
      _snowman.put(_snowman, _snowmanxxxxxxx);
      int _snowmanxxxxxxxx = Math.max(4, MathHelper.log2DeBruijn(_snowmanxxxxxxx.size()));
      PackedIntegerArray _snowmanxxxxxxxxx = new PackedIntegerArray(_snowmanxxxxxxxx, 4096);

      for (int _snowmanxxxxxxxxxx = 0; _snowmanxxxxxxxxxx < _snowmanxxxxxx.length; _snowmanxxxxxxxxxx++) {
         _snowmanxxxxxxxxx.set(_snowmanxxxxxxxxxx, _snowmanxxxxxx[_snowmanxxxxxxxxxx]);
      }

      _snowman.putLongArray(_snowman, _snowmanxxxxxxxxx.getStorage());
      this.unlock();
   }

   public int getPacketSize() {
      return 1 + this.palette.getPacketSize() + PacketByteBuf.getVarIntSizeBytes(this.data.getSize()) + this.data.getStorage().length * 8;
   }

   public boolean hasAny(Predicate<T> _snowman) {
      return this.palette.accepts(_snowman);
   }

   public void count(PalettedContainer.CountConsumer<T> consumer) {
      Int2IntMap _snowman = new Int2IntOpenHashMap();
      this.data.forEach(_snowmanx -> _snowman.put(_snowmanx, _snowman.get(_snowmanx) + 1));
      _snowman.int2IntEntrySet().forEach(_snowmanx -> consumer.accept(this.palette.getByIndex(_snowmanx.getIntKey()), _snowmanx.getIntValue()));
   }

   @FunctionalInterface
   public interface CountConsumer<T> {
      void accept(T object, int count);
   }
}
