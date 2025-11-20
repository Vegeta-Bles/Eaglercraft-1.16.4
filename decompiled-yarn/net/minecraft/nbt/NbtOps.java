package net.minecraft.nbt;

import com.google.common.collect.Iterators;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.PeekingIterator;
import com.mojang.datafixers.DataFixUtils;
import com.mojang.datafixers.util.Pair;
import com.mojang.serialization.DataResult;
import com.mojang.serialization.DynamicOps;
import com.mojang.serialization.MapLike;
import com.mojang.serialization.RecordBuilder;
import com.mojang.serialization.RecordBuilder.AbstractStringBuilder;
import java.nio.ByteBuffer;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.Map.Entry;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.stream.IntStream;
import java.util.stream.LongStream;
import java.util.stream.Stream;
import javax.annotation.Nullable;

public class NbtOps implements DynamicOps<Tag> {
   public static final NbtOps INSTANCE = new NbtOps();

   protected NbtOps() {
   }

   public Tag empty() {
      return EndTag.INSTANCE;
   }

   public <U> U convertTo(DynamicOps<U> _snowman, Tag _snowman) {
      switch (_snowman.getType()) {
         case 0:
            return (U)_snowman.empty();
         case 1:
            return (U)_snowman.createByte(((AbstractNumberTag)_snowman).getByte());
         case 2:
            return (U)_snowman.createShort(((AbstractNumberTag)_snowman).getShort());
         case 3:
            return (U)_snowman.createInt(((AbstractNumberTag)_snowman).getInt());
         case 4:
            return (U)_snowman.createLong(((AbstractNumberTag)_snowman).getLong());
         case 5:
            return (U)_snowman.createFloat(((AbstractNumberTag)_snowman).getFloat());
         case 6:
            return (U)_snowman.createDouble(((AbstractNumberTag)_snowman).getDouble());
         case 7:
            return (U)_snowman.createByteList(ByteBuffer.wrap(((ByteArrayTag)_snowman).getByteArray()));
         case 8:
            return (U)_snowman.createString(_snowman.asString());
         case 9:
            return (U)this.convertList(_snowman, _snowman);
         case 10:
            return (U)this.convertMap(_snowman, _snowman);
         case 11:
            return (U)_snowman.createIntList(Arrays.stream(((IntArrayTag)_snowman).getIntArray()));
         case 12:
            return (U)_snowman.createLongList(Arrays.stream(((LongArrayTag)_snowman).getLongArray()));
         default:
            throw new IllegalStateException("Unknown tag type: " + _snowman);
      }
   }

   public DataResult<Number> getNumberValue(Tag _snowman) {
      return _snowman instanceof AbstractNumberTag ? DataResult.success(((AbstractNumberTag)_snowman).getNumber()) : DataResult.error("Not a number");
   }

   public Tag createNumeric(Number _snowman) {
      return DoubleTag.of(_snowman.doubleValue());
   }

   public Tag createByte(byte _snowman) {
      return ByteTag.of(_snowman);
   }

   public Tag createShort(short _snowman) {
      return ShortTag.of(_snowman);
   }

   public Tag createInt(int _snowman) {
      return IntTag.of(_snowman);
   }

   public Tag createLong(long _snowman) {
      return LongTag.of(_snowman);
   }

   public Tag createFloat(float _snowman) {
      return FloatTag.of(_snowman);
   }

   public Tag createDouble(double _snowman) {
      return DoubleTag.of(_snowman);
   }

   public Tag createBoolean(boolean _snowman) {
      return ByteTag.of(_snowman);
   }

   public DataResult<String> getStringValue(Tag _snowman) {
      return _snowman instanceof StringTag ? DataResult.success(_snowman.asString()) : DataResult.error("Not a string");
   }

   public Tag createString(String _snowman) {
      return StringTag.of(_snowman);
   }

   private static AbstractListTag<?> method_29144(byte _snowman, byte _snowman) {
      if (method_29145(_snowman, _snowman, (byte)4)) {
         return new LongArrayTag(new long[0]);
      } else if (method_29145(_snowman, _snowman, (byte)1)) {
         return new ByteArrayTag(new byte[0]);
      } else {
         return (AbstractListTag<?>)(method_29145(_snowman, _snowman, (byte)3) ? new IntArrayTag(new int[0]) : new ListTag());
      }
   }

   private static boolean method_29145(byte _snowman, byte _snowman, byte _snowman) {
      return _snowman == _snowman && (_snowman == _snowman || _snowman == 0);
   }

   private static <T extends Tag> void method_29151(AbstractListTag<T> _snowman, Tag _snowman, Tag _snowman) {
      if (_snowman instanceof AbstractListTag) {
         AbstractListTag<?> _snowmanxxx = (AbstractListTag<?>)_snowman;
         _snowmanxxx.forEach(_snowmanxxxx -> _snowman.add((T)_snowmanxxxx));
      }

      _snowman.add((T)_snowman);
   }

   private static <T extends Tag> void method_29150(AbstractListTag<T> _snowman, Tag _snowman, List<Tag> _snowman) {
      if (_snowman instanceof AbstractListTag) {
         AbstractListTag<?> _snowmanxxx = (AbstractListTag<?>)_snowman;
         _snowmanxxx.forEach(_snowmanxxxx -> _snowman.add((T)_snowmanxxxx));
      }

      _snowman.forEach(_snowmanxxxx -> _snowman.add((T)_snowmanxxxx));
   }

   public DataResult<Tag> mergeToList(Tag _snowman, Tag _snowman) {
      if (!(_snowman instanceof AbstractListTag) && !(_snowman instanceof EndTag)) {
         return DataResult.error("mergeToList called with not a list: " + _snowman, _snowman);
      } else {
         AbstractListTag<?> _snowmanxx = method_29144(_snowman instanceof AbstractListTag ? ((AbstractListTag)_snowman).getElementType() : 0, _snowman.getType());
         method_29151(_snowmanxx, _snowman, _snowman);
         return DataResult.success(_snowmanxx);
      }
   }

   public DataResult<Tag> mergeToList(Tag _snowman, List<Tag> _snowman) {
      if (!(_snowman instanceof AbstractListTag) && !(_snowman instanceof EndTag)) {
         return DataResult.error("mergeToList called with not a list: " + _snowman, _snowman);
      } else {
         AbstractListTag<?> _snowmanxx = method_29144(
            _snowman instanceof AbstractListTag ? ((AbstractListTag)_snowman).getElementType() : 0, _snowman.stream().findFirst().map(Tag::getType).orElse((byte)0)
         );
         method_29150(_snowmanxx, _snowman, _snowman);
         return DataResult.success(_snowmanxx);
      }
   }

   public DataResult<Tag> mergeToMap(Tag _snowman, Tag _snowman, Tag _snowman) {
      if (!(_snowman instanceof CompoundTag) && !(_snowman instanceof EndTag)) {
         return DataResult.error("mergeToMap called with not a map: " + _snowman, _snowman);
      } else if (!(_snowman instanceof StringTag)) {
         return DataResult.error("key is not a string: " + _snowman, _snowman);
      } else {
         CompoundTag _snowmanxxx = new CompoundTag();
         if (_snowman instanceof CompoundTag) {
            CompoundTag _snowmanxxxx = (CompoundTag)_snowman;
            _snowmanxxxx.getKeys().forEach(_snowmanxxxxx -> _snowman.put(_snowmanxxxxx, _snowman.get(_snowmanxxxxx)));
         }

         _snowmanxxx.put(_snowman.asString(), _snowman);
         return DataResult.success(_snowmanxxx);
      }
   }

   public DataResult<Tag> mergeToMap(Tag _snowman, MapLike<Tag> _snowman) {
      if (!(_snowman instanceof CompoundTag) && !(_snowman instanceof EndTag)) {
         return DataResult.error("mergeToMap called with not a map: " + _snowman, _snowman);
      } else {
         CompoundTag _snowmanxx = new CompoundTag();
         if (_snowman instanceof CompoundTag) {
            CompoundTag _snowmanxxx = (CompoundTag)_snowman;
            _snowmanxxx.getKeys().forEach(_snowmanxxxx -> _snowman.put(_snowmanxxxx, _snowman.get(_snowmanxxxx)));
         }

         List<Tag> _snowmanxxx = Lists.newArrayList();
         _snowman.entries().forEach(_snowmanxxxx -> {
            Tag _snowmanxxxx = (Tag)_snowmanxxxx.getFirst();
            if (!(_snowmanxxxx instanceof StringTag)) {
               _snowman.add(_snowmanxxxx);
            } else {
               _snowman.put(_snowmanxxxx.asString(), (Tag)_snowmanxxxx.getSecond());
            }
         });
         return !_snowmanxxx.isEmpty() ? DataResult.error("some keys are not strings: " + _snowmanxxx, _snowmanxx) : DataResult.success(_snowmanxx);
      }
   }

   public DataResult<Stream<Pair<Tag, Tag>>> getMapValues(Tag _snowman) {
      if (!(_snowman instanceof CompoundTag)) {
         return DataResult.error("Not a map: " + _snowman);
      } else {
         CompoundTag _snowmanx = (CompoundTag)_snowman;
         return DataResult.success(_snowmanx.getKeys().stream().map(_snowmanxx -> Pair.of(this.createString(_snowmanxx), _snowman.get(_snowmanxx))));
      }
   }

   public DataResult<Consumer<BiConsumer<Tag, Tag>>> getMapEntries(Tag _snowman) {
      if (!(_snowman instanceof CompoundTag)) {
         return DataResult.error("Not a map: " + _snowman);
      } else {
         CompoundTag _snowmanx = (CompoundTag)_snowman;
         return DataResult.success((Consumer<BiConsumer>)_snowmanxx -> _snowman.getKeys().forEach(_snowmanxxx -> _snowmanx.accept(this.createString(_snowmanxxx), _snowman.get(_snowmanxxx))));
      }
   }

   public DataResult<MapLike<Tag>> getMap(Tag _snowman) {
      if (!(_snowman instanceof CompoundTag)) {
         return DataResult.error("Not a map: " + _snowman);
      } else {
         final CompoundTag _snowmanx = (CompoundTag)_snowman;
         return DataResult.success(new MapLike<Tag>() {
            @Nullable
            public Tag get(Tag _snowman) {
               return _snowman.get(_snowman.asString());
            }

            @Nullable
            public Tag get(String _snowman) {
               return _snowman.get(_snowman);
            }

            public Stream<Pair<Tag, Tag>> entries() {
               return _snowman.getKeys().stream().map(_snowmanx -> Pair.of(NbtOps.this.createString(_snowmanx), _snowman.get(_snowmanx)));
            }

            @Override
            public String toString() {
               return "MapLike[" + _snowman + "]";
            }
         });
      }
   }

   public Tag createMap(Stream<Pair<Tag, Tag>> _snowman) {
      CompoundTag _snowmanx = new CompoundTag();
      _snowman.forEach(_snowmanxx -> _snowman.put(((Tag)_snowmanxx.getFirst()).asString(), (Tag)_snowmanxx.getSecond()));
      return _snowmanx;
   }

   public DataResult<Stream<Tag>> getStream(Tag _snowman) {
      return _snowman instanceof AbstractListTag ? DataResult.success(((AbstractListTag)_snowman).stream().map(_snowmanx -> _snowmanx)) : DataResult.error("Not a list");
   }

   public DataResult<Consumer<Consumer<Tag>>> getList(Tag _snowman) {
      if (_snowman instanceof AbstractListTag) {
         AbstractListTag<?> _snowmanx = (AbstractListTag<?>)_snowman;
         return DataResult.success(_snowmanx::forEach);
      } else {
         return DataResult.error("Not a list: " + _snowman);
      }
   }

   public DataResult<ByteBuffer> getByteBuffer(Tag _snowman) {
      return _snowman instanceof ByteArrayTag ? DataResult.success(ByteBuffer.wrap(((ByteArrayTag)_snowman).getByteArray())) : super.getByteBuffer(_snowman);
   }

   public Tag createByteList(ByteBuffer _snowman) {
      return new ByteArrayTag(DataFixUtils.toArray(_snowman));
   }

   public DataResult<IntStream> getIntStream(Tag _snowman) {
      return _snowman instanceof IntArrayTag ? DataResult.success(Arrays.stream(((IntArrayTag)_snowman).getIntArray())) : super.getIntStream(_snowman);
   }

   public Tag createIntList(IntStream _snowman) {
      return new IntArrayTag(_snowman.toArray());
   }

   public DataResult<LongStream> getLongStream(Tag _snowman) {
      return _snowman instanceof LongArrayTag ? DataResult.success(Arrays.stream(((LongArrayTag)_snowman).getLongArray())) : super.getLongStream(_snowman);
   }

   public Tag createLongList(LongStream _snowman) {
      return new LongArrayTag(_snowman.toArray());
   }

   public Tag createList(Stream<Tag> _snowman) {
      PeekingIterator<Tag> _snowmanx = Iterators.peekingIterator(_snowman.iterator());
      if (!_snowmanx.hasNext()) {
         return new ListTag();
      } else {
         Tag _snowmanxx = (Tag)_snowmanx.peek();
         if (_snowmanxx instanceof ByteTag) {
            List<Byte> _snowmanxxx = Lists.newArrayList(Iterators.transform(_snowmanx, _snowmanxxxx -> ((ByteTag)_snowmanxxxx).getByte()));
            return new ByteArrayTag(_snowmanxxx);
         } else if (_snowmanxx instanceof IntTag) {
            List<Integer> _snowmanxxx = Lists.newArrayList(Iterators.transform(_snowmanx, _snowmanxxxx -> ((IntTag)_snowmanxxxx).getInt()));
            return new IntArrayTag(_snowmanxxx);
         } else if (_snowmanxx instanceof LongTag) {
            List<Long> _snowmanxxx = Lists.newArrayList(Iterators.transform(_snowmanx, _snowmanxxxx -> ((LongTag)_snowmanxxxx).getLong()));
            return new LongArrayTag(_snowmanxxx);
         } else {
            ListTag _snowmanxxx = new ListTag();

            while (_snowmanx.hasNext()) {
               Tag _snowmanxxxx = (Tag)_snowmanx.next();
               if (!(_snowmanxxxx instanceof EndTag)) {
                  _snowmanxxx.add(_snowmanxxxx);
               }
            }

            return _snowmanxxx;
         }
      }
   }

   public Tag remove(Tag _snowman, String _snowman) {
      if (_snowman instanceof CompoundTag) {
         CompoundTag _snowmanxx = (CompoundTag)_snowman;
         CompoundTag _snowmanxxx = new CompoundTag();
         _snowmanxx.getKeys().stream().filter(_snowmanxxxx -> !Objects.equals(_snowmanxxxx, _snowman)).forEach(_snowmanxxxx -> _snowman.put(_snowmanxxxx, _snowman.get(_snowmanxxxx)));
         return _snowmanxxx;
      } else {
         return _snowman;
      }
   }

   @Override
   public String toString() {
      return "NBT";
   }

   public RecordBuilder<Tag> mapBuilder() {
      return new NbtOps.MapBuilder();
   }

   class MapBuilder extends AbstractStringBuilder<Tag, CompoundTag> {
      protected MapBuilder() {
         super(NbtOps.this);
      }

      protected CompoundTag initBuilder() {
         return new CompoundTag();
      }

      protected CompoundTag append(String _snowman, Tag _snowman, CompoundTag _snowman) {
         _snowman.put(_snowman, _snowman);
         return _snowman;
      }

      protected DataResult<Tag> build(CompoundTag _snowman, Tag _snowman) {
         if (_snowman == null || _snowman == EndTag.INSTANCE) {
            return DataResult.success(_snowman);
         } else if (!(_snowman instanceof CompoundTag)) {
            return DataResult.error("mergeToMap called with not a map: " + _snowman, _snowman);
         } else {
            CompoundTag _snowmanxx = new CompoundTag(Maps.newHashMap(((CompoundTag)_snowman).toMap()));

            for (Entry<String, Tag> _snowmanxxx : _snowman.toMap().entrySet()) {
               _snowmanxx.put(_snowmanxxx.getKey(), _snowmanxxx.getValue());
            }

            return DataResult.success(_snowmanxx);
         }
      }
   }
}
