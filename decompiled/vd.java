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

public abstract class vd<T> implements DynamicOps<T> {
   protected final DynamicOps<T> a;

   protected vd(DynamicOps<T> var1) {
      this.a = _snowman;
   }

   public T empty() {
      return (T)this.a.empty();
   }

   public <U> U convertTo(DynamicOps<U> var1, T var2) {
      return (U)this.a.convertTo(_snowman, _snowman);
   }

   public DataResult<Number> getNumberValue(T var1) {
      return this.a.getNumberValue(_snowman);
   }

   public T createNumeric(Number var1) {
      return (T)this.a.createNumeric(_snowman);
   }

   public T createByte(byte var1) {
      return (T)this.a.createByte(_snowman);
   }

   public T createShort(short var1) {
      return (T)this.a.createShort(_snowman);
   }

   public T createInt(int var1) {
      return (T)this.a.createInt(_snowman);
   }

   public T createLong(long var1) {
      return (T)this.a.createLong(_snowman);
   }

   public T createFloat(float var1) {
      return (T)this.a.createFloat(_snowman);
   }

   public T createDouble(double var1) {
      return (T)this.a.createDouble(_snowman);
   }

   public DataResult<Boolean> getBooleanValue(T var1) {
      return this.a.getBooleanValue(_snowman);
   }

   public T createBoolean(boolean var1) {
      return (T)this.a.createBoolean(_snowman);
   }

   public DataResult<String> getStringValue(T var1) {
      return this.a.getStringValue(_snowman);
   }

   public T createString(String var1) {
      return (T)this.a.createString(_snowman);
   }

   public DataResult<T> mergeToList(T var1, T var2) {
      return this.a.mergeToList(_snowman, _snowman);
   }

   public DataResult<T> mergeToList(T var1, List<T> var2) {
      return this.a.mergeToList(_snowman, _snowman);
   }

   public DataResult<T> mergeToMap(T var1, T var2, T var3) {
      return this.a.mergeToMap(_snowman, _snowman, _snowman);
   }

   public DataResult<T> mergeToMap(T var1, MapLike<T> var2) {
      return this.a.mergeToMap(_snowman, _snowman);
   }

   public DataResult<Stream<Pair<T, T>>> getMapValues(T var1) {
      return this.a.getMapValues(_snowman);
   }

   public DataResult<Consumer<BiConsumer<T, T>>> getMapEntries(T var1) {
      return this.a.getMapEntries(_snowman);
   }

   public T createMap(Stream<Pair<T, T>> var1) {
      return (T)this.a.createMap(_snowman);
   }

   public DataResult<MapLike<T>> getMap(T var1) {
      return this.a.getMap(_snowman);
   }

   public DataResult<Stream<T>> getStream(T var1) {
      return this.a.getStream(_snowman);
   }

   public DataResult<Consumer<Consumer<T>>> getList(T var1) {
      return this.a.getList(_snowman);
   }

   public T createList(Stream<T> var1) {
      return (T)this.a.createList(_snowman);
   }

   public DataResult<ByteBuffer> getByteBuffer(T var1) {
      return this.a.getByteBuffer(_snowman);
   }

   public T createByteList(ByteBuffer var1) {
      return (T)this.a.createByteList(_snowman);
   }

   public DataResult<IntStream> getIntStream(T var1) {
      return this.a.getIntStream(_snowman);
   }

   public T createIntList(IntStream var1) {
      return (T)this.a.createIntList(_snowman);
   }

   public DataResult<LongStream> getLongStream(T var1) {
      return this.a.getLongStream(_snowman);
   }

   public T createLongList(LongStream var1) {
      return (T)this.a.createLongList(_snowman);
   }

   public T remove(T var1, String var2) {
      return (T)this.a.remove(_snowman, _snowman);
   }

   public boolean compressMaps() {
      return this.a.compressMaps();
   }

   public ListBuilder<T> listBuilder() {
      return this.a.listBuilder();
   }

   public RecordBuilder<T> mapBuilder() {
      return this.a.mapBuilder();
   }
}
