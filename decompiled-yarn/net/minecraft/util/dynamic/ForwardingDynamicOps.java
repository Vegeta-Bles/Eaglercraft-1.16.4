package net.minecraft.util.dynamic;

import com.mojang.datafixers.util.Pair;
import com.mojang.serialization.DataResult;
import com.mojang.serialization.DynamicOps;
import com.mojang.serialization.ListBuilder;
import com.mojang.serialization.MapLike;
import com.mojang.serialization.RecordBuilder;
import java.nio.ByteBuffer;
import java.util.List;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.stream.IntStream;
import java.util.stream.LongStream;
import java.util.stream.Stream;

public abstract class ForwardingDynamicOps<T> implements DynamicOps<T> {
   protected final DynamicOps<T> delegate;

   protected ForwardingDynamicOps(DynamicOps<T> delegate) {
      this.delegate = delegate;
   }

   public T empty() {
      return (T)this.delegate.empty();
   }

   public <U> U convertTo(DynamicOps<U> _snowman, T _snowman) {
      return (U)this.delegate.convertTo(_snowman, _snowman);
   }

   public DataResult<Number> getNumberValue(T _snowman) {
      return this.delegate.getNumberValue(_snowman);
   }

   public T createNumeric(Number _snowman) {
      return (T)this.delegate.createNumeric(_snowman);
   }

   public T createByte(byte _snowman) {
      return (T)this.delegate.createByte(_snowman);
   }

   public T createShort(short _snowman) {
      return (T)this.delegate.createShort(_snowman);
   }

   public T createInt(int _snowman) {
      return (T)this.delegate.createInt(_snowman);
   }

   public T createLong(long _snowman) {
      return (T)this.delegate.createLong(_snowman);
   }

   public T createFloat(float _snowman) {
      return (T)this.delegate.createFloat(_snowman);
   }

   public T createDouble(double _snowman) {
      return (T)this.delegate.createDouble(_snowman);
   }

   public DataResult<Boolean> getBooleanValue(T _snowman) {
      return this.delegate.getBooleanValue(_snowman);
   }

   public T createBoolean(boolean _snowman) {
      return (T)this.delegate.createBoolean(_snowman);
   }

   public DataResult<String> getStringValue(T _snowman) {
      return this.delegate.getStringValue(_snowman);
   }

   public T createString(String _snowman) {
      return (T)this.delegate.createString(_snowman);
   }

   public DataResult<T> mergeToList(T _snowman, T _snowman) {
      return this.delegate.mergeToList(_snowman, _snowman);
   }

   public DataResult<T> mergeToList(T _snowman, List<T> _snowman) {
      return this.delegate.mergeToList(_snowman, _snowman);
   }

   public DataResult<T> mergeToMap(T _snowman, T _snowman, T _snowman) {
      return this.delegate.mergeToMap(_snowman, _snowman, _snowman);
   }

   public DataResult<T> mergeToMap(T _snowman, MapLike<T> _snowman) {
      return this.delegate.mergeToMap(_snowman, _snowman);
   }

   public DataResult<Stream<Pair<T, T>>> getMapValues(T _snowman) {
      return this.delegate.getMapValues(_snowman);
   }

   public DataResult<Consumer<BiConsumer<T, T>>> getMapEntries(T _snowman) {
      return this.delegate.getMapEntries(_snowman);
   }

   public T createMap(Stream<Pair<T, T>> _snowman) {
      return (T)this.delegate.createMap(_snowman);
   }

   public DataResult<MapLike<T>> getMap(T _snowman) {
      return this.delegate.getMap(_snowman);
   }

   public DataResult<Stream<T>> getStream(T _snowman) {
      return this.delegate.getStream(_snowman);
   }

   public DataResult<Consumer<Consumer<T>>> getList(T _snowman) {
      return this.delegate.getList(_snowman);
   }

   public T createList(Stream<T> _snowman) {
      return (T)this.delegate.createList(_snowman);
   }

   public DataResult<ByteBuffer> getByteBuffer(T _snowman) {
      return this.delegate.getByteBuffer(_snowman);
   }

   public T createByteList(ByteBuffer _snowman) {
      return (T)this.delegate.createByteList(_snowman);
   }

   public DataResult<IntStream> getIntStream(T _snowman) {
      return this.delegate.getIntStream(_snowman);
   }

   public T createIntList(IntStream _snowman) {
      return (T)this.delegate.createIntList(_snowman);
   }

   public DataResult<LongStream> getLongStream(T _snowman) {
      return this.delegate.getLongStream(_snowman);
   }

   public T createLongList(LongStream _snowman) {
      return (T)this.delegate.createLongList(_snowman);
   }

   public T remove(T _snowman, String _snowman) {
      return (T)this.delegate.remove(_snowman, _snowman);
   }

   public boolean compressMaps() {
      return this.delegate.compressMaps();
   }

   public ListBuilder<T> listBuilder() {
      return this.delegate.listBuilder();
   }

   public RecordBuilder<T> mapBuilder() {
      return this.delegate.mapBuilder();
   }
}
