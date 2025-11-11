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

public class mo implements DynamicOps<mt> {
   public static final mo a = new mo();

   protected mo() {
   }

   public mt a() {
      return mf.b;
   }

   public <U> U a(DynamicOps<U> var1, mt var2) {
      switch (_snowman.a()) {
         case 0:
            return (U)_snowman.empty();
         case 1:
            return (U)_snowman.createByte(((mq)_snowman).h());
         case 2:
            return (U)_snowman.createShort(((mq)_snowman).g());
         case 3:
            return (U)_snowman.createInt(((mq)_snowman).f());
         case 4:
            return (U)_snowman.createLong(((mq)_snowman).e());
         case 5:
            return (U)_snowman.createFloat(((mq)_snowman).j());
         case 6:
            return (U)_snowman.createDouble(((mq)_snowman).i());
         case 7:
            return (U)_snowman.createByteList(ByteBuffer.wrap(((ma)_snowman).d()));
         case 8:
            return (U)_snowman.createString(_snowman.f_());
         case 9:
            return (U)this.convertList(_snowman, _snowman);
         case 10:
            return (U)this.convertMap(_snowman, _snowman);
         case 11:
            return (U)_snowman.createIntList(Arrays.stream(((mh)_snowman).g()));
         case 12:
            return (U)_snowman.createLongList(Arrays.stream(((mk)_snowman).g()));
         default:
            throw new IllegalStateException("Unknown tag type: " + _snowman);
      }
   }

   public DataResult<Number> a(mt var1) {
      return _snowman instanceof mq ? DataResult.success(((mq)_snowman).k()) : DataResult.error("Not a number");
   }

   public mt a(Number var1) {
      return me.a(_snowman.doubleValue());
   }

   public mt a(byte var1) {
      return mb.a(_snowman);
   }

   public mt a(short var1) {
      return mr.a(_snowman);
   }

   public mt a(int var1) {
      return mi.a(_snowman);
   }

   public mt a(long var1) {
      return ml.a(_snowman);
   }

   public mt a(float var1) {
      return mg.a(_snowman);
   }

   public mt a(double var1) {
      return me.a(_snowman);
   }

   public mt a(boolean var1) {
      return mb.a(_snowman);
   }

   public DataResult<String> b(mt var1) {
      return _snowman instanceof ms ? DataResult.success(_snowman.f_()) : DataResult.error("Not a string");
   }

   public mt a(String var1) {
      return ms.a(_snowman);
   }

   private static mc<?> a(byte var0, byte var1) {
      if (a(_snowman, _snowman, (byte)4)) {
         return new mk(new long[0]);
      } else if (a(_snowman, _snowman, (byte)1)) {
         return new ma(new byte[0]);
      } else {
         return (mc<?>)(a(_snowman, _snowman, (byte)3) ? new mh(new int[0]) : new mj());
      }
   }

   private static boolean a(byte var0, byte var1, byte var2) {
      return _snowman == _snowman && (_snowman == _snowman || _snowman == 0);
   }

   private static <T extends mt> void a(mc<T> var0, mt var1, mt var2) {
      if (_snowman instanceof mc) {
         mc<?> _snowman = (mc<?>)_snowman;
         _snowman.forEach(var1x -> _snowman.add((T)var1x));
      }

      _snowman.add((T)_snowman);
   }

   private static <T extends mt> void a(mc<T> var0, mt var1, List<mt> var2) {
      if (_snowman instanceof mc) {
         mc<?> _snowman = (mc<?>)_snowman;
         _snowman.forEach(var1x -> _snowman.add((T)var1x));
      }

      _snowman.forEach(var1x -> _snowman.add((T)var1x));
   }

   public DataResult<mt> a(mt var1, mt var2) {
      if (!(_snowman instanceof mc) && !(_snowman instanceof mf)) {
         return DataResult.error("mergeToList called with not a list: " + _snowman, _snowman);
      } else {
         mc<?> _snowman = a(_snowman instanceof mc ? ((mc)_snowman).d_() : 0, _snowman.a());
         a(_snowman, _snowman, _snowman);
         return DataResult.success(_snowman);
      }
   }

   public DataResult<mt> a(mt var1, List<mt> var2) {
      if (!(_snowman instanceof mc) && !(_snowman instanceof mf)) {
         return DataResult.error("mergeToList called with not a list: " + _snowman, _snowman);
      } else {
         mc<?> _snowman = a(_snowman instanceof mc ? ((mc)_snowman).d_() : 0, _snowman.stream().findFirst().map(mt::a).orElse((byte)0));
         a(_snowman, _snowman, _snowman);
         return DataResult.success(_snowman);
      }
   }

   public DataResult<mt> a(mt var1, mt var2, mt var3) {
      if (!(_snowman instanceof md) && !(_snowman instanceof mf)) {
         return DataResult.error("mergeToMap called with not a map: " + _snowman, _snowman);
      } else if (!(_snowman instanceof ms)) {
         return DataResult.error("key is not a string: " + _snowman, _snowman);
      } else {
         md _snowman = new md();
         if (_snowman instanceof md) {
            md _snowmanx = (md)_snowman;
            _snowmanx.d().forEach(var2x -> _snowman.a(var2x, _snowman.c(var2x)));
         }

         _snowman.a(_snowman.f_(), _snowman);
         return DataResult.success(_snowman);
      }
   }

   public DataResult<mt> a(mt var1, MapLike<mt> var2) {
      if (!(_snowman instanceof md) && !(_snowman instanceof mf)) {
         return DataResult.error("mergeToMap called with not a map: " + _snowman, _snowman);
      } else {
         md _snowman = new md();
         if (_snowman instanceof md) {
            md _snowmanx = (md)_snowman;
            _snowmanx.d().forEach(var2x -> _snowman.a(var2x, _snowman.c(var2x)));
         }

         List<mt> _snowmanx = Lists.newArrayList();
         _snowman.entries().forEach(var2x -> {
            mt _snowmanxx = (mt)var2x.getFirst();
            if (!(_snowmanxx instanceof ms)) {
               _snowman.add(_snowmanxx);
            } else {
               _snowman.a(_snowmanxx.f_(), (mt)var2x.getSecond());
            }
         });
         return !_snowmanx.isEmpty() ? DataResult.error("some keys are not strings: " + _snowmanx, _snowman) : DataResult.success(_snowman);
      }
   }

   public DataResult<Stream<Pair<mt, mt>>> c(mt var1) {
      if (!(_snowman instanceof md)) {
         return DataResult.error("Not a map: " + _snowman);
      } else {
         md _snowman = (md)_snowman;
         return DataResult.success(_snowman.d().stream().map(var2x -> Pair.of(this.a(var2x), _snowman.c(var2x))));
      }
   }

   public DataResult<Consumer<BiConsumer<mt, mt>>> d(mt var1) {
      if (!(_snowman instanceof md)) {
         return DataResult.error("Not a map: " + _snowman);
      } else {
         md _snowman = (md)_snowman;
         return DataResult.success((Consumer<BiConsumer>)var2x -> _snowman.d().forEach(var3 -> var2x.accept(this.a(var3), _snowman.c(var3))));
      }
   }

   public DataResult<MapLike<mt>> e(mt var1) {
      if (!(_snowman instanceof md)) {
         return DataResult.error("Not a map: " + _snowman);
      } else {
         final md _snowman = (md)_snowman;
         return DataResult.success(new MapLike<mt>() {
            @Nullable
            public mt a(mt var1) {
               return _snowman.c(_snowman.f_());
            }

            @Nullable
            public mt a(String var1) {
               return _snowman.c(_snowman);
            }

            public Stream<Pair<mt, mt>> entries() {
               return _snowman.d().stream().map(var2x -> Pair.of(mo.this.a(var2x), _snowman.c(var2x)));
            }

            @Override
            public String toString() {
               return "MapLike[" + _snowman + "]";
            }
         });
      }
   }

   public mt a(Stream<Pair<mt, mt>> var1) {
      md _snowman = new md();
      _snowman.forEach(var1x -> _snowman.a(((mt)var1x.getFirst()).f_(), (mt)var1x.getSecond()));
      return _snowman;
   }

   public DataResult<Stream<mt>> f(mt var1) {
      return _snowman instanceof mc ? DataResult.success(((mc)_snowman).stream().map(var0 -> (mt)var0)) : DataResult.error("Not a list");
   }

   public DataResult<Consumer<Consumer<mt>>> g(mt var1) {
      if (_snowman instanceof mc) {
         mc<?> _snowman = (mc<?>)_snowman;
         return DataResult.success(_snowman::forEach);
      } else {
         return DataResult.error("Not a list: " + _snowman);
      }
   }

   public DataResult<ByteBuffer> h(mt var1) {
      return _snowman instanceof ma ? DataResult.success(ByteBuffer.wrap(((ma)_snowman).d())) : super.getByteBuffer(_snowman);
   }

   public mt a(ByteBuffer var1) {
      return new ma(DataFixUtils.toArray(_snowman));
   }

   public DataResult<IntStream> i(mt var1) {
      return _snowman instanceof mh ? DataResult.success(Arrays.stream(((mh)_snowman).g())) : super.getIntStream(_snowman);
   }

   public mt a(IntStream var1) {
      return new mh(_snowman.toArray());
   }

   public DataResult<LongStream> j(mt var1) {
      return _snowman instanceof mk ? DataResult.success(Arrays.stream(((mk)_snowman).g())) : super.getLongStream(_snowman);
   }

   public mt a(LongStream var1) {
      return new mk(_snowman.toArray());
   }

   public mt b(Stream<mt> var1) {
      PeekingIterator<mt> _snowman = Iterators.peekingIterator(_snowman.iterator());
      if (!_snowman.hasNext()) {
         return new mj();
      } else {
         mt _snowmanx = (mt)_snowman.peek();
         if (_snowmanx instanceof mb) {
            List<Byte> _snowmanxx = Lists.newArrayList(Iterators.transform(_snowman, var0 -> ((mb)var0).h()));
            return new ma(_snowmanxx);
         } else if (_snowmanx instanceof mi) {
            List<Integer> _snowmanxx = Lists.newArrayList(Iterators.transform(_snowman, var0 -> ((mi)var0).f()));
            return new mh(_snowmanxx);
         } else if (_snowmanx instanceof ml) {
            List<Long> _snowmanxx = Lists.newArrayList(Iterators.transform(_snowman, var0 -> ((ml)var0).e()));
            return new mk(_snowmanxx);
         } else {
            mj _snowmanxx = new mj();

            while (_snowman.hasNext()) {
               mt _snowmanxxx = (mt)_snowman.next();
               if (!(_snowmanxxx instanceof mf)) {
                  _snowmanxx.add(_snowmanxxx);
               }
            }

            return _snowmanxx;
         }
      }
   }

   public mt a(mt var1, String var2) {
      if (_snowman instanceof md) {
         md _snowman = (md)_snowman;
         md _snowmanx = new md();
         _snowman.d().stream().filter(var1x -> !Objects.equals(var1x, _snowman)).forEach(var2x -> _snowman.a(var2x, _snowman.c(var2x)));
         return _snowmanx;
      } else {
         return _snowman;
      }
   }

   @Override
   public String toString() {
      return "NBT";
   }

   public RecordBuilder<mt> mapBuilder() {
      return new mo.a();
   }

   class a extends AbstractStringBuilder<mt, md> {
      protected a() {
         super(mo.this);
      }

      protected md a() {
         return new md();
      }

      protected md a(String var1, mt var2, md var3) {
         _snowman.a(_snowman, _snowman);
         return _snowman;
      }

      protected DataResult<mt> a(md var1, mt var2) {
         if (_snowman == null || _snowman == mf.b) {
            return DataResult.success(_snowman);
         } else if (!(_snowman instanceof md)) {
            return DataResult.error("mergeToMap called with not a map: " + _snowman, _snowman);
         } else {
            md _snowman = new md(Maps.newHashMap(((md)_snowman).h()));

            for (Entry<String, mt> _snowmanx : _snowman.h().entrySet()) {
               _snowman.a(_snowmanx.getKey(), _snowmanx.getValue());
            }

            return DataResult.success(_snowman);
         }
      }
   }
}
