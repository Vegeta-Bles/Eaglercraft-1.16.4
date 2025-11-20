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

   public <U> U convertTo(DynamicOps<U> dynamicOps, Tag arg) {
      switch (arg.getType()) {
         case 0:
            return (U)dynamicOps.empty();
         case 1:
            return (U)dynamicOps.createByte(((AbstractNumberTag)arg).getByte());
         case 2:
            return (U)dynamicOps.createShort(((AbstractNumberTag)arg).getShort());
         case 3:
            return (U)dynamicOps.createInt(((AbstractNumberTag)arg).getInt());
         case 4:
            return (U)dynamicOps.createLong(((AbstractNumberTag)arg).getLong());
         case 5:
            return (U)dynamicOps.createFloat(((AbstractNumberTag)arg).getFloat());
         case 6:
            return (U)dynamicOps.createDouble(((AbstractNumberTag)arg).getDouble());
         case 7:
            return (U)dynamicOps.createByteList(ByteBuffer.wrap(((ByteArrayTag)arg).getByteArray()));
         case 8:
            return (U)dynamicOps.createString(arg.asString());
         case 9:
            return (U)this.convertList(dynamicOps, arg);
         case 10:
            return (U)this.convertMap(dynamicOps, arg);
         case 11:
            return (U)dynamicOps.createIntList(Arrays.stream(((IntArrayTag)arg).getIntArray()));
         case 12:
            return (U)dynamicOps.createLongList(Arrays.stream(((LongArrayTag)arg).getLongArray()));
         default:
            throw new IllegalStateException("Unknown tag type: " + arg);
      }
   }

   public DataResult<Number> getNumberValue(Tag arg) {
      return arg instanceof AbstractNumberTag ? DataResult.success(((AbstractNumberTag)arg).getNumber()) : DataResult.error("Not a number");
   }

   public Tag createNumeric(Number number) {
      return DoubleTag.of(number.doubleValue());
   }

   public Tag createByte(byte b) {
      return ByteTag.of(b);
   }

   public Tag createShort(short s) {
      return ShortTag.of(s);
   }

   public Tag createInt(int i) {
      return IntTag.of(i);
   }

   public Tag createLong(long l) {
      return LongTag.of(l);
   }

   public Tag createFloat(float f) {
      return FloatTag.of(f);
   }

   public Tag createDouble(double d) {
      return DoubleTag.of(d);
   }

   public Tag createBoolean(boolean bl) {
      return ByteTag.of(bl);
   }

   public DataResult<String> getStringValue(Tag arg) {
      return arg instanceof StringTag ? DataResult.success(arg.asString()) : DataResult.error("Not a string");
   }

   public Tag createString(String string) {
      return StringTag.of(string);
   }

   private static AbstractListTag<?> method_29144(byte b, byte c) {
      if (method_29145(b, c, (byte)4)) {
         return new LongArrayTag(new long[0]);
      } else if (method_29145(b, c, (byte)1)) {
         return new ByteArrayTag(new byte[0]);
      } else {
         return (AbstractListTag<?>)(method_29145(b, c, (byte)3) ? new IntArrayTag(new int[0]) : new ListTag());
      }
   }

   private static boolean method_29145(byte b, byte c, byte d) {
      return b == d && (c == d || c == 0);
   }

   private static <T extends Tag> void method_29151(AbstractListTag<T> arg, Tag arg2, Tag arg3) {
      if (arg2 instanceof AbstractListTag) {
         AbstractListTag<?> lv = (AbstractListTag<?>)arg2;
         lv.forEach(arg2x -> arg.add((T)arg2x));
      }

      arg.add((T)arg3);
   }

   private static <T extends Tag> void method_29150(AbstractListTag<T> arg, Tag arg2, List<Tag> list) {
      if (arg2 instanceof AbstractListTag) {
         AbstractListTag<?> lv = (AbstractListTag<?>)arg2;
         lv.forEach(arg2x -> arg.add((T)arg2x));
      }

      list.forEach(arg2x -> arg.add((T)arg2x));
   }

   public DataResult<Tag> mergeToList(Tag arg, Tag arg2) {
      if (!(arg instanceof AbstractListTag) && !(arg instanceof EndTag)) {
         return DataResult.error("mergeToList called with not a list: " + arg, arg);
      } else {
         AbstractListTag<?> lv = method_29144(arg instanceof AbstractListTag ? ((AbstractListTag)arg).getElementType() : 0, arg2.getType());
         method_29151(lv, arg, arg2);
         return DataResult.success(lv);
      }
   }

   public DataResult<Tag> mergeToList(Tag arg, List<Tag> list) {
      if (!(arg instanceof AbstractListTag) && !(arg instanceof EndTag)) {
         return DataResult.error("mergeToList called with not a list: " + arg, arg);
      } else {
         AbstractListTag<?> lv = method_29144(
            arg instanceof AbstractListTag ? ((AbstractListTag)arg).getElementType() : 0, list.stream().findFirst().map(Tag::getType).orElse((byte)0)
         );
         method_29150(lv, arg, list);
         return DataResult.success(lv);
      }
   }

   public DataResult<Tag> mergeToMap(Tag arg, Tag arg2, Tag arg3) {
      if (!(arg instanceof CompoundTag) && !(arg instanceof EndTag)) {
         return DataResult.error("mergeToMap called with not a map: " + arg, arg);
      } else if (!(arg2 instanceof StringTag)) {
         return DataResult.error("key is not a string: " + arg2, arg);
      } else {
         CompoundTag lv = new CompoundTag();
         if (arg instanceof CompoundTag) {
            CompoundTag lv2 = (CompoundTag)arg;
            lv2.getKeys().forEach(string -> lv.put(string, lv2.get(string)));
         }

         lv.put(arg2.asString(), arg3);
         return DataResult.success(lv);
      }
   }

   public DataResult<Tag> mergeToMap(Tag arg, MapLike<Tag> mapLike) {
      if (!(arg instanceof CompoundTag) && !(arg instanceof EndTag)) {
         return DataResult.error("mergeToMap called with not a map: " + arg, arg);
      } else {
         CompoundTag lv = new CompoundTag();
         if (arg instanceof CompoundTag) {
            CompoundTag lv2 = (CompoundTag)arg;
            lv2.getKeys().forEach(string -> lv.put(string, lv2.get(string)));
         }

         List<Tag> list = Lists.newArrayList();
         mapLike.entries().forEach(pair -> {
            Tag lvx = (Tag)pair.getFirst();
            if (!(lvx instanceof StringTag)) {
               list.add(lvx);
            } else {
               lv.put(lvx.asString(), (Tag)pair.getSecond());
            }
         });
         return !list.isEmpty() ? DataResult.error("some keys are not strings: " + list, lv) : DataResult.success(lv);
      }
   }

   public DataResult<Stream<Pair<Tag, Tag>>> getMapValues(Tag arg) {
      if (!(arg instanceof CompoundTag)) {
         return DataResult.error("Not a map: " + arg);
      } else {
         CompoundTag lv = (CompoundTag)arg;
         return DataResult.success(lv.getKeys().stream().map(string -> Pair.of(this.createString(string), lv.get(string))));
      }
   }

   public DataResult<Consumer<BiConsumer<Tag, Tag>>> getMapEntries(Tag arg) {
      if (!(arg instanceof CompoundTag)) {
         return DataResult.error("Not a map: " + arg);
      } else {
         CompoundTag lv = (CompoundTag)arg;
         return DataResult.success(
            (Consumer<BiConsumer<Tag, Tag>>)biConsumer -> lv.getKeys().forEach(string -> biConsumer.accept(this.createString(string), lv.get(string)))
         );
      }
   }

   public DataResult<MapLike<Tag>> getMap(Tag arg) {
      if (!(arg instanceof CompoundTag)) {
         return DataResult.error("Not a map: " + arg);
      } else {
         final CompoundTag lv = (CompoundTag)arg;
         return DataResult.success(new MapLike<Tag>() {
            @Nullable
            public Tag get(Tag arg) {
               return lv.get(arg.asString());
            }

            @Nullable
            public Tag get(String string) {
               return lv.get(string);
            }

            public Stream<Pair<Tag, Tag>> entries() {
               return lv.getKeys().stream().map(string -> Pair.of(NbtOps.this.createString(string), lv.get(string)));
            }

            @Override
            public String toString() {
               return "MapLike[" + lv + "]";
            }
         });
      }
   }

   public Tag createMap(Stream<Pair<Tag, Tag>> stream) {
      CompoundTag lv = new CompoundTag();
      stream.forEach(pair -> lv.put(((Tag)pair.getFirst()).asString(), (Tag)pair.getSecond()));
      return lv;
   }

   public DataResult<Stream<Tag>> getStream(Tag arg) {
      return arg instanceof AbstractListTag ? DataResult.success(((AbstractListTag)arg).stream().map(argx -> argx)) : DataResult.error("Not a list");
   }

   public DataResult<Consumer<Consumer<Tag>>> getList(Tag arg) {
      if (arg instanceof AbstractListTag) {
         AbstractListTag<?> lv = (AbstractListTag<?>)arg;
         return DataResult.success(lv::forEach);
      } else {
         return DataResult.error("Not a list: " + arg);
      }
   }

   public DataResult<ByteBuffer> getByteBuffer(Tag arg) {
      return arg instanceof ByteArrayTag
         ? DataResult.success(ByteBuffer.wrap(((ByteArrayTag)arg).getByteArray()))
         : DataResult.error("Not a byte array: " + arg);
   }

   public Tag createByteList(ByteBuffer byteBuffer) {
      return new ByteArrayTag(DataFixUtils.toArray(byteBuffer));
   }

   public DataResult<IntStream> getIntStream(Tag arg) {
      return arg instanceof IntArrayTag
         ? DataResult.success(Arrays.stream(((IntArrayTag)arg).getIntArray()))
         : DataResult.error("Not an int array: " + arg);
   }

   public Tag createIntList(IntStream intStream) {
      return new IntArrayTag(intStream.toArray());
   }

   public DataResult<LongStream> getLongStream(Tag arg) {
      return arg instanceof LongArrayTag
         ? DataResult.success(Arrays.stream(((LongArrayTag)arg).getLongArray()))
         : DataResult.error("Not a long array: " + arg);
   }

   public Tag createLongList(LongStream longStream) {
      return new LongArrayTag(longStream.toArray());
   }

   public Tag createList(Stream<Tag> stream) {
      PeekingIterator<Tag> peekingIterator = Iterators.peekingIterator(stream.iterator());
      if (!peekingIterator.hasNext()) {
         return new ListTag();
      } else {
         Tag lv = (Tag)peekingIterator.peek();
         if (lv instanceof ByteTag) {
            List<Byte> list = Lists.newArrayList(Iterators.transform(peekingIterator, arg -> ((ByteTag)arg).getByte()));
            return new ByteArrayTag(list);
         } else if (lv instanceof IntTag) {
            List<Integer> list2 = Lists.newArrayList(Iterators.transform(peekingIterator, arg -> ((IntTag)arg).getInt()));
            return new IntArrayTag(list2);
         } else if (lv instanceof LongTag) {
            List<Long> list3 = Lists.newArrayList(Iterators.transform(peekingIterator, arg -> ((LongTag)arg).getLong()));
            return new LongArrayTag(list3);
         } else {
            ListTag lv2 = new ListTag();

            while (peekingIterator.hasNext()) {
               Tag lv3 = (Tag)peekingIterator.next();
               if (!(lv3 instanceof EndTag)) {
                  lv2.add(lv3);
               }
            }

            return lv2;
         }
      }
   }

   public Tag remove(Tag arg, String string) {
      if (arg instanceof CompoundTag) {
         CompoundTag lv = (CompoundTag)arg;
         CompoundTag lv2 = new CompoundTag();
         lv.getKeys().stream().filter(string2 -> !Objects.equals(string2, string)).forEach(stringx -> lv2.put(stringx, lv.get(stringx)));
         return lv2;
      } else {
         return arg;
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

      protected CompoundTag append(String string, Tag arg, CompoundTag arg2) {
         arg2.put(string, arg);
         return arg2;
      }

      protected DataResult<Tag> build(CompoundTag arg, Tag arg2) {
         if (arg2 == null || arg2 == EndTag.INSTANCE) {
            return DataResult.success(arg);
         } else if (!(arg2 instanceof CompoundTag)) {
            return DataResult.error("mergeToMap called with not a map: " + arg2, arg2);
         } else {
            CompoundTag lv = new CompoundTag(Maps.newHashMap(((CompoundTag)arg2).toMap()));

            for (Entry<String, Tag> entry : arg.toMap().entrySet()) {
               lv.put(entry.getKey(), entry.getValue());
            }

            return DataResult.success(lv);
         }
      }
   }
}
