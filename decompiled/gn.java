import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableMap.Builder;
import com.mojang.serialization.Codec;
import com.mojang.serialization.DataResult;
import com.mojang.serialization.JsonOps;
import com.mojang.serialization.Lifecycle;
import com.mojang.serialization.codecs.UnboundedMapCodec;
import java.util.Map;
import java.util.Optional;
import java.util.Map.Entry;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import javax.annotation.Nullable;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public abstract class gn {
   private static final Logger a = LogManager.getLogger();
   private static final Map<vj<? extends gm<?>>, gn.a<?>> b = x.a((Supplier<Map<vj<? extends gm<?>>, gn.a<?>>>)(() -> {
      Builder<vj<? extends gm<?>>, gn.a<?>> _snowman = ImmutableMap.builder();
      a(_snowman, gm.K, chd.d, chd.d);
      a(_snowman, gm.ay, bsv.b, bsv.c);
      a(_snowman, gm.as, ctg.a);
      a(_snowman, gm.at, cib.a);
      a(_snowman, gm.au, civ.a);
      a(_snowman, gm.av, ciw.a);
      a(_snowman, gm.aw, cta.l);
      a(_snowman, gm.ax, cok.a);
      a(_snowman, gm.ar, chp.a);
      return _snowman.build();
   }));
   private static final gn.b c = x.a((Supplier<gn.b>)(() -> {
      gn.b _snowman = new gn.b();
      chd.a(_snowman);
      b.keySet().stream().filter(var0x -> !var0x.equals(gm.K)).forEach(var1 -> a(_snowman, (vj<? extends gm<?>>)var1));
      return _snowman;
   }));

   public gn() {
   }

   public abstract <E> Optional<gs<E>> a(vj<? extends gm<E>> var1);

   public <E> gs<E> b(vj<? extends gm<E>> var1) {
      return this.a(_snowman).orElseThrow(() -> new IllegalStateException("Missing registry: " + _snowman));
   }

   public gm<chd> a() {
      return this.b(gm.K);
   }

   private static <E> void a(Builder<vj<? extends gm<?>>, gn.a<?>> var0, vj<? extends gm<E>> var1, Codec<E> var2) {
      _snowman.put(_snowman, new gn.a<>(_snowman, _snowman, null));
   }

   private static <E> void a(Builder<vj<? extends gm<?>>, gn.a<?>> var0, vj<? extends gm<E>> var1, Codec<E> var2, Codec<E> var3) {
      _snowman.put(_snowman, new gn.a<>(_snowman, _snowman, _snowman));
   }

   public static gn.b b() {
      gn.b _snowman = new gn.b();
      vh.b.a _snowmanx = new vh.b.a();

      for (gn.a<?> _snowmanxx : b.values()) {
         a(_snowman, _snowmanx, _snowmanxx);
      }

      vh.a(JsonOps.INSTANCE, _snowmanx, _snowman);
      return _snowman;
   }

   private static <E> void a(gn.b var0, vh.b.a var1, gn.a<E> var2) {
      vj<? extends gm<E>> _snowman = _snowman.a();
      boolean _snowmanx = !_snowman.equals(gm.ar) && !_snowman.equals(gm.K);
      gm<E> _snowmanxx = c.b(_snowman);
      gs<E> _snowmanxxx = _snowman.b(_snowman);

      for (Entry<vj<E>, E> _snowmanxxxx : _snowmanxx.d()) {
         E _snowmanxxxxx = _snowmanxxxx.getValue();
         if (_snowmanx) {
            _snowman.a(c, _snowmanxxxx.getKey(), _snowman.b(), _snowmanxx.a(_snowmanxxxxx), _snowmanxxxxx, _snowmanxx.d(_snowmanxxxxx));
         } else {
            _snowmanxxx.a(_snowmanxx.a(_snowmanxxxxx), _snowmanxxxx.getKey(), _snowmanxxxxx, _snowmanxx.d(_snowmanxxxxx));
         }
      }
   }

   private static <R extends gm<?>> void a(gn.b var0, vj<R> var1) {
      gm<R> _snowman = (gm<R>)hk.b;
      gm<?> _snowmanx = _snowman.a(_snowman);
      if (_snowmanx == null) {
         throw new IllegalStateException("Missing builtin registry: " + _snowman);
      } else {
         a(_snowman, _snowmanx);
      }
   }

   private static <E> void a(gn.b var0, gm<E> var1) {
      gs<E> _snowman = _snowman.a(_snowman.f()).orElseThrow(() -> new IllegalStateException("Missing registry: " + _snowman.f()));

      for (Entry<vj<E>, E> _snowmanx : _snowman.d()) {
         E _snowmanxx = _snowmanx.getValue();
         _snowman.a(_snowman.a(_snowmanxx), _snowmanx.getKey(), _snowmanxx, _snowman.d(_snowmanxx));
      }
   }

   public static void a(gn.b var0, vh<?> var1) {
      for (gn.a<?> _snowman : b.values()) {
         a(_snowman, _snowman, _snowman);
      }
   }

   private static <E> void a(vh<?> var0, gn.b var1, gn.a<E> var2) {
      vj<? extends gm<E>> _snowman = _snowman.a();
      gi<E> _snowmanx = Optional.ofNullable(_snowman.b.get(_snowman)).map(var0x -> (gi<E>)var0x).orElseThrow(() -> new IllegalStateException("Missing registry: " + _snowman));
      DataResult<gi<E>> _snowmanxx = _snowman.a(_snowmanx, _snowman.a(), _snowman.b());
      _snowmanxx.error().ifPresent(var0x -> a.error("Error loading registry data: {}", var0x.message()));
   }

   static final class a<E> {
      private final vj<? extends gm<E>> a;
      private final Codec<E> b;
      @Nullable
      private final Codec<E> c;

      public a(vj<? extends gm<E>> var1, Codec<E> var2, @Nullable Codec<E> var3) {
         this.a = _snowman;
         this.b = _snowman;
         this.c = _snowman;
      }

      public vj<? extends gm<E>> a() {
         return this.a;
      }

      public Codec<E> b() {
         return this.b;
      }

      @Nullable
      public Codec<E> c() {
         return this.c;
      }

      public boolean d() {
         return this.c != null;
      }
   }

   public static final class b extends gn {
      public static final Codec<gn.b> a = d();
      private final Map<? extends vj<? extends gm<?>>, ? extends gi<?>> b;

      private static <E> Codec<gn.b> d() {
         Codec<vj<? extends gm<E>>> _snowman = vk.a.xmap(vj::a, vj::a);
         Codec<gi<E>> _snowmanx = _snowman.partialDispatch(
            "type", var0x -> DataResult.success(var0x.f()), var0x -> c(var0x).map(var1x -> gi.a(var0x, Lifecycle.experimental(), var1x))
         );
         UnboundedMapCodec<? extends vj<? extends gm<?>>, ? extends gi<?>> _snowmanxx = Codec.unboundedMap(_snowman, _snowmanx);
         return a(_snowmanxx);
      }

      private static <K extends vj<? extends gm<?>>, V extends gi<?>> Codec<gn.b> a(UnboundedMapCodec<K, V> var0) {
         return _snowman.xmap(
            gn.b::new,
            var0x -> (ImmutableMap)var0x.b
                  .entrySet()
                  .stream()
                  .filter(var0xx -> gn.b.get(var0xx.getKey()).d())
                  .collect(ImmutableMap.toImmutableMap(Entry::getKey, Entry::getValue))
         );
      }

      private static <E> DataResult<? extends Codec<E>> c(vj<? extends gm<E>> var0) {
         return Optional.ofNullable(gn.b.get(_snowman))
            .map(var0x -> var0x.c())
            .<DataResult<? extends Codec<E>>>map(DataResult::success)
            .orElseGet(() -> DataResult.error("Unknown or not serializable registry: " + _snowman));
      }

      public b() {
         this(gn.b.keySet().stream().collect(Collectors.toMap(Function.identity(), gn.b::d)));
      }

      private b(Map<? extends vj<? extends gm<?>>, ? extends gi<?>> var1) {
         this.b = _snowman;
      }

      private static <E> gi<?> d(vj<? extends gm<?>> var0) {
         return new gi<>(_snowman, Lifecycle.stable());
      }

      @Override
      public <E> Optional<gs<E>> a(vj<? extends gm<E>> var1) {
         return Optional.ofNullable(this.b.get(_snowman)).map(var0 -> (gs<E>)var0);
      }
   }
}
