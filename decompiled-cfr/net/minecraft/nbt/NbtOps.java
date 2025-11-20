/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.collect.Iterators
 *  com.google.common.collect.Lists
 *  com.google.common.collect.Maps
 *  com.google.common.collect.PeekingIterator
 *  com.mojang.datafixers.DataFixUtils
 *  com.mojang.datafixers.util.Pair
 *  com.mojang.serialization.DataResult
 *  com.mojang.serialization.DynamicOps
 *  com.mojang.serialization.MapLike
 *  com.mojang.serialization.RecordBuilder
 *  com.mojang.serialization.RecordBuilder$AbstractStringBuilder
 *  javax.annotation.Nullable
 */
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
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.stream.IntStream;
import java.util.stream.LongStream;
import java.util.stream.Stream;
import javax.annotation.Nullable;
import net.minecraft.nbt.AbstractListTag;
import net.minecraft.nbt.AbstractNumberTag;
import net.minecraft.nbt.ByteArrayTag;
import net.minecraft.nbt.ByteTag;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.DoubleTag;
import net.minecraft.nbt.EndTag;
import net.minecraft.nbt.FloatTag;
import net.minecraft.nbt.IntArrayTag;
import net.minecraft.nbt.IntTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.LongArrayTag;
import net.minecraft.nbt.LongTag;
import net.minecraft.nbt.ShortTag;
import net.minecraft.nbt.StringTag;
import net.minecraft.nbt.Tag;

public class NbtOps
implements DynamicOps<Tag> {
    public static final NbtOps INSTANCE = new NbtOps();

    protected NbtOps() {
    }

    public Tag empty() {
        return EndTag.INSTANCE;
    }

    public <U> U convertTo(DynamicOps<U> dynamicOps, Tag tag) {
        switch (tag.getType()) {
            case 0: {
                return (U)dynamicOps.empty();
            }
            case 1: {
                return (U)dynamicOps.createByte(((AbstractNumberTag)tag).getByte());
            }
            case 2: {
                return (U)dynamicOps.createShort(((AbstractNumberTag)tag).getShort());
            }
            case 3: {
                return (U)dynamicOps.createInt(((AbstractNumberTag)tag).getInt());
            }
            case 4: {
                return (U)dynamicOps.createLong(((AbstractNumberTag)tag).getLong());
            }
            case 5: {
                return (U)dynamicOps.createFloat(((AbstractNumberTag)tag).getFloat());
            }
            case 6: {
                return (U)dynamicOps.createDouble(((AbstractNumberTag)tag).getDouble());
            }
            case 7: {
                return (U)dynamicOps.createByteList(ByteBuffer.wrap(((ByteArrayTag)tag).getByteArray()));
            }
            case 8: {
                return (U)dynamicOps.createString(tag.asString());
            }
            case 9: {
                return (U)this.convertList(dynamicOps, tag);
            }
            case 10: {
                return (U)this.convertMap(dynamicOps, tag);
            }
            case 11: {
                return (U)dynamicOps.createIntList(Arrays.stream(((IntArrayTag)tag).getIntArray()));
            }
            case 12: {
                return (U)dynamicOps.createLongList(Arrays.stream(((LongArrayTag)tag).getLongArray()));
            }
        }
        throw new IllegalStateException("Unknown tag type: " + tag);
    }

    public DataResult<Number> getNumberValue(Tag tag) {
        if (tag instanceof AbstractNumberTag) {
            return DataResult.success((Object)((AbstractNumberTag)tag).getNumber());
        }
        return DataResult.error((String)"Not a number");
    }

    public Tag createNumeric(Number number) {
        return DoubleTag.of(number.doubleValue());
    }

    public Tag createByte(byte by) {
        return ByteTag.of(by);
    }

    public Tag createShort(short s) {
        return ShortTag.of(s);
    }

    public Tag createInt(int n) {
        return IntTag.of(n);
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

    public DataResult<String> getStringValue(Tag tag) {
        if (tag instanceof StringTag) {
            return DataResult.success((Object)tag.asString());
        }
        return DataResult.error((String)"Not a string");
    }

    public Tag createString(String string) {
        return StringTag.of(string);
    }

    private static AbstractListTag<?> method_29144(byte by, byte by2) {
        if (NbtOps.method_29145(by, by2, (byte)4)) {
            return new LongArrayTag(new long[0]);
        }
        if (NbtOps.method_29145(by, by2, (byte)1)) {
            return new ByteArrayTag(new byte[0]);
        }
        if (NbtOps.method_29145(by, by2, (byte)3)) {
            return new IntArrayTag(new int[0]);
        }
        return new ListTag();
    }

    private static boolean method_29145(byte by, byte by2, byte by3) {
        return by == by3 && (by2 == by3 || by2 == 0);
    }

    private static <T extends Tag> void method_29151(AbstractListTag<T> abstractListTag2, Tag tag2, Tag tag3) {
        AbstractListTag abstractListTag2;
        if (tag2 instanceof AbstractListTag) {
            AbstractListTag abstractListTag3 = (AbstractListTag)tag2;
            abstractListTag3.forEach(tag -> abstractListTag2.add(tag));
        }
        abstractListTag2.add(tag3);
    }

    private static <T extends Tag> void method_29150(AbstractListTag<T> abstractListTag, Tag tag2, List<Tag> list2) {
        List<Tag> list2;
        if (tag2 instanceof AbstractListTag) {
            AbstractListTag abstractListTag2 = (AbstractListTag)tag2;
            abstractListTag2.forEach(tag -> abstractListTag.add(tag));
        }
        list2.forEach(tag -> abstractListTag.add(tag));
    }

    public DataResult<Tag> mergeToList(Tag tag, Tag tag2) {
        if (!(tag instanceof AbstractListTag) && !(tag instanceof EndTag)) {
            return DataResult.error((String)("mergeToList called with not a list: " + tag), (Object)tag);
        }
        AbstractListTag<?> abstractListTag = NbtOps.method_29144(tag instanceof AbstractListTag ? ((AbstractListTag)tag).getElementType() : (byte)0, tag2.getType());
        NbtOps.method_29151(abstractListTag, tag, tag2);
        return DataResult.success(abstractListTag);
    }

    public DataResult<Tag> mergeToList(Tag tag, List<Tag> list) {
        if (!(tag instanceof AbstractListTag) && !(tag instanceof EndTag)) {
            return DataResult.error((String)("mergeToList called with not a list: " + tag), (Object)tag);
        }
        AbstractListTag<?> abstractListTag = NbtOps.method_29144(tag instanceof AbstractListTag ? ((AbstractListTag)tag).getElementType() : (byte)0, list.stream().findFirst().map(Tag::getType).orElse((byte)0));
        NbtOps.method_29150(abstractListTag, tag, list);
        return DataResult.success(abstractListTag);
    }

    public DataResult<Tag> mergeToMap(Tag tag, Tag tag2, Tag tag3) {
        if (!(tag instanceof CompoundTag) && !(tag instanceof EndTag)) {
            return DataResult.error((String)("mergeToMap called with not a map: " + tag), (Object)tag);
        }
        if (!(tag2 instanceof StringTag)) {
            return DataResult.error((String)("key is not a string: " + tag2), (Object)tag);
        }
        CompoundTag compoundTag = new CompoundTag();
        if (tag instanceof CompoundTag) {
            _snowman = (CompoundTag)tag;
            _snowman.getKeys().forEach(string -> compoundTag.put((String)string, _snowman.get((String)string)));
        }
        compoundTag.put(tag2.asString(), tag3);
        return DataResult.success((Object)compoundTag);
    }

    public DataResult<Tag> mergeToMap(Tag tag, MapLike<Tag> mapLike) {
        Object object;
        if (!(tag instanceof CompoundTag) && !(tag instanceof EndTag)) {
            return DataResult.error((String)("mergeToMap called with not a map: " + tag), (Object)tag);
        }
        CompoundTag compoundTag = new CompoundTag();
        if (tag instanceof CompoundTag) {
            object = (CompoundTag)tag;
            ((CompoundTag)object).getKeys().forEach(arg_0 -> NbtOps.method_29159(compoundTag, (CompoundTag)object, arg_0));
        }
        object = Lists.newArrayList();
        mapLike.entries().forEach(arg_0 -> NbtOps.method_29147((List)object, compoundTag, arg_0));
        if (!object.isEmpty()) {
            return DataResult.error((String)("some keys are not strings: " + object), (Object)compoundTag);
        }
        return DataResult.success((Object)compoundTag);
    }

    public DataResult<Stream<Pair<Tag, Tag>>> getMapValues(Tag tag) {
        if (!(tag instanceof CompoundTag)) {
            return DataResult.error((String)("Not a map: " + tag));
        }
        CompoundTag compoundTag = (CompoundTag)tag;
        return DataResult.success(compoundTag.getKeys().stream().map(string -> Pair.of((Object)this.createString((String)string), (Object)compoundTag.get((String)string))));
    }

    public DataResult<Consumer<BiConsumer<Tag, Tag>>> getMapEntries(Tag tag) {
        if (!(tag instanceof CompoundTag)) {
            return DataResult.error((String)("Not a map: " + tag));
        }
        CompoundTag compoundTag = (CompoundTag)tag;
        return DataResult.success(biConsumer -> compoundTag.getKeys().forEach(string -> biConsumer.accept(this.createString((String)string), compoundTag.get((String)string))));
    }

    public DataResult<MapLike<Tag>> getMap(Tag tag) {
        if (!(tag instanceof CompoundTag)) {
            return DataResult.error((String)("Not a map: " + tag));
        }
        final CompoundTag compoundTag = (CompoundTag)tag;
        return DataResult.success((Object)new MapLike<Tag>(){

            @Nullable
            public Tag get(Tag tag) {
                return compoundTag.get(tag.asString());
            }

            @Nullable
            public Tag get(String string) {
                return compoundTag.get(string);
            }

            public Stream<Pair<Tag, Tag>> entries() {
                return compoundTag.getKeys().stream().map(string -> Pair.of((Object)NbtOps.this.createString((String)string), (Object)compoundTag.get((String)string)));
            }

            public String toString() {
                return "MapLike[" + compoundTag + "]";
            }

            @Nullable
            public /* synthetic */ Object get(String string) {
                return this.get(string);
            }

            @Nullable
            public /* synthetic */ Object get(Object object) {
                return this.get((Tag)object);
            }
        });
    }

    public Tag createMap(Stream<Pair<Tag, Tag>> stream) {
        CompoundTag compoundTag = new CompoundTag();
        stream.forEach(pair -> compoundTag.put(((Tag)pair.getFirst()).asString(), (Tag)pair.getSecond()));
        return compoundTag;
    }

    public DataResult<Stream<Tag>> getStream(Tag tag2) {
        if (tag2 instanceof AbstractListTag) {
            return DataResult.success(((AbstractListTag)tag2).stream().map(tag -> tag));
        }
        return DataResult.error((String)"Not a list");
    }

    public DataResult<Consumer<Consumer<Tag>>> getList(Tag tag2) {
        Tag tag2;
        if (tag2 instanceof AbstractListTag) {
            AbstractListTag abstractListTag = (AbstractListTag)tag2;
            return DataResult.success(abstractListTag::forEach);
        }
        return DataResult.error((String)("Not a list: " + tag2));
    }

    public DataResult<ByteBuffer> getByteBuffer(Tag tag) {
        if (tag instanceof ByteArrayTag) {
            return DataResult.success((Object)ByteBuffer.wrap(((ByteArrayTag)tag).getByteArray()));
        }
        return super.getByteBuffer((Object)tag);
    }

    public Tag createByteList(ByteBuffer byteBuffer) {
        return new ByteArrayTag(DataFixUtils.toArray((ByteBuffer)byteBuffer));
    }

    public DataResult<IntStream> getIntStream(Tag tag) {
        if (tag instanceof IntArrayTag) {
            return DataResult.success((Object)Arrays.stream(((IntArrayTag)tag).getIntArray()));
        }
        return super.getIntStream((Object)tag);
    }

    public Tag createIntList(IntStream intStream) {
        return new IntArrayTag(intStream.toArray());
    }

    public DataResult<LongStream> getLongStream(Tag tag) {
        if (tag instanceof LongArrayTag) {
            return DataResult.success((Object)Arrays.stream(((LongArrayTag)tag).getLongArray()));
        }
        return super.getLongStream((Object)tag);
    }

    public Tag createLongList(LongStream longStream) {
        return new LongArrayTag(longStream.toArray());
    }

    public Tag createList(Stream<Tag> stream) {
        PeekingIterator peekingIterator = Iterators.peekingIterator(stream.iterator());
        if (!peekingIterator.hasNext()) {
            return new ListTag();
        }
        Tag _snowman2 = (Tag)peekingIterator.peek();
        if (_snowman2 instanceof ByteTag) {
            ArrayList arrayList = Lists.newArrayList((Iterator)Iterators.transform((Iterator)peekingIterator, tag -> ((ByteTag)tag).getByte()));
            return new ByteArrayTag(arrayList);
        }
        if (_snowman2 instanceof IntTag) {
            ArrayList _snowman3 = Lists.newArrayList((Iterator)Iterators.transform((Iterator)peekingIterator, tag -> ((IntTag)tag).getInt()));
            return new IntArrayTag(_snowman3);
        }
        if (_snowman2 instanceof LongTag) {
            ArrayList _snowman4 = Lists.newArrayList((Iterator)Iterators.transform((Iterator)peekingIterator, tag -> ((LongTag)tag).getLong()));
            return new LongArrayTag(_snowman4);
        }
        ListTag listTag = new ListTag();
        while (peekingIterator.hasNext()) {
            Tag tag2 = (Tag)peekingIterator.next();
            if (tag2 instanceof EndTag) continue;
            listTag.add(tag2);
        }
        return listTag;
    }

    public Tag remove(Tag tag2, String string3) {
        Tag tag2;
        if (tag2 instanceof CompoundTag) {
            CompoundTag compoundTag = (CompoundTag)tag2;
            _snowman = new CompoundTag();
            compoundTag.getKeys().stream().filter(string2 -> !Objects.equals(string2, string3)).forEach(string -> _snowman.put((String)string, compoundTag.get((String)string)));
            return _snowman;
        }
        return tag2;
    }

    public String toString() {
        return "NBT";
    }

    public RecordBuilder<Tag> mapBuilder() {
        return new MapBuilder();
    }

    public /* synthetic */ Object remove(Object object, String string) {
        return this.remove((Tag)object, string);
    }

    public /* synthetic */ Object createLongList(LongStream longStream) {
        return this.createLongList(longStream);
    }

    public /* synthetic */ DataResult getLongStream(Object object) {
        return this.getLongStream((Tag)object);
    }

    public /* synthetic */ Object createIntList(IntStream intStream) {
        return this.createIntList(intStream);
    }

    public /* synthetic */ DataResult getIntStream(Object object) {
        return this.getIntStream((Tag)object);
    }

    public /* synthetic */ Object createByteList(ByteBuffer byteBuffer) {
        return this.createByteList(byteBuffer);
    }

    public /* synthetic */ DataResult getByteBuffer(Object object) {
        return this.getByteBuffer((Tag)object);
    }

    public /* synthetic */ Object createList(Stream stream) {
        return this.createList(stream);
    }

    public /* synthetic */ DataResult getList(Object object) {
        return this.getList((Tag)object);
    }

    public /* synthetic */ DataResult getStream(Object object) {
        return this.getStream((Tag)object);
    }

    public /* synthetic */ DataResult getMap(Object object) {
        return this.getMap((Tag)object);
    }

    public /* synthetic */ Object createMap(Stream stream) {
        return this.createMap(stream);
    }

    public /* synthetic */ DataResult getMapEntries(Object object) {
        return this.getMapEntries((Tag)object);
    }

    public /* synthetic */ DataResult getMapValues(Object object) {
        return this.getMapValues((Tag)object);
    }

    public /* synthetic */ DataResult mergeToMap(Object object, MapLike mapLike) {
        return this.mergeToMap((Tag)object, (MapLike<Tag>)mapLike);
    }

    public /* synthetic */ DataResult mergeToMap(Object object, Object object2, Object object3) {
        return this.mergeToMap((Tag)object, (Tag)object2, (Tag)object3);
    }

    public /* synthetic */ DataResult mergeToList(Object object, List list) {
        return this.mergeToList((Tag)object, (List<Tag>)list);
    }

    public /* synthetic */ DataResult mergeToList(Object object, Object object2) {
        return this.mergeToList((Tag)object, (Tag)object2);
    }

    public /* synthetic */ Object createString(String string) {
        return this.createString(string);
    }

    public /* synthetic */ DataResult getStringValue(Object object) {
        return this.getStringValue((Tag)object);
    }

    public /* synthetic */ Object createBoolean(boolean bl) {
        return this.createBoolean(bl);
    }

    public /* synthetic */ Object createDouble(double d) {
        return this.createDouble(d);
    }

    public /* synthetic */ Object createFloat(float f) {
        return this.createFloat(f);
    }

    public /* synthetic */ Object createLong(long l) {
        return this.createLong(l);
    }

    public /* synthetic */ Object createInt(int n) {
        return this.createInt(n);
    }

    public /* synthetic */ Object createShort(short s) {
        return this.createShort(s);
    }

    public /* synthetic */ Object createByte(byte by) {
        return this.createByte(by);
    }

    public /* synthetic */ Object createNumeric(Number number) {
        return this.createNumeric(number);
    }

    public /* synthetic */ DataResult getNumberValue(Object object) {
        return this.getNumberValue((Tag)object);
    }

    public /* synthetic */ Object convertTo(DynamicOps dynamicOps, Object object) {
        return this.convertTo(dynamicOps, (Tag)object);
    }

    public /* synthetic */ Object empty() {
        return this.empty();
    }

    private static /* synthetic */ void method_29147(List list, CompoundTag compoundTag, Pair pair) {
        Tag tag = (Tag)pair.getFirst();
        if (!(tag instanceof StringTag)) {
            list.add(tag);
            return;
        }
        compoundTag.put(tag.asString(), (Tag)pair.getSecond());
    }

    private static /* synthetic */ void method_29159(CompoundTag compoundTag, CompoundTag compoundTag2, String string) {
        compoundTag.put(string, compoundTag2.get(string));
    }

    class MapBuilder
    extends RecordBuilder.AbstractStringBuilder<Tag, CompoundTag> {
        protected MapBuilder() {
            super((DynamicOps)NbtOps.this);
        }

        protected CompoundTag initBuilder() {
            return new CompoundTag();
        }

        protected CompoundTag append(String string, Tag tag, CompoundTag compoundTag) {
            compoundTag.put(string, tag);
            return compoundTag;
        }

        protected DataResult<Tag> build(CompoundTag compoundTag, Tag tag2) {
            Tag tag2;
            if (tag2 == null || tag2 == EndTag.INSTANCE) {
                return DataResult.success((Object)compoundTag);
            }
            if (tag2 instanceof CompoundTag) {
                CompoundTag compoundTag2 = new CompoundTag(Maps.newHashMap(((CompoundTag)tag2).toMap()));
                for (Map.Entry<String, Tag> entry : compoundTag.toMap().entrySet()) {
                    compoundTag2.put(entry.getKey(), entry.getValue());
                }
                return DataResult.success((Object)compoundTag2);
            }
            return DataResult.error((String)("mergeToMap called with not a map: " + tag2), (Object)tag2);
        }

        protected /* synthetic */ Object append(String string, Object object, Object object2) {
            return this.append(string, (Tag)object, (CompoundTag)object2);
        }

        protected /* synthetic */ DataResult build(Object object, Object object2) {
            return this.build((CompoundTag)object, (Tag)object2);
        }

        protected /* synthetic */ Object initBuilder() {
            return this.initBuilder();
        }
    }
}

