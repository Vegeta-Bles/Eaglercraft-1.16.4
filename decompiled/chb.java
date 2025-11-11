import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;
import com.mojang.datafixers.DataFixer;
import com.mojang.serialization.Codec;
import com.mojang.serialization.DataResult;
import com.mojang.serialization.Dynamic;
import com.mojang.serialization.DynamicOps;
import com.mojang.serialization.OptionalDynamic;
import it.unimi.dsi.fastutil.longs.Long2ObjectMap;
import it.unimi.dsi.fastutil.longs.Long2ObjectOpenHashMap;
import it.unimi.dsi.fastutil.longs.LongLinkedOpenHashSet;
import java.io.File;
import java.io.IOException;
import java.util.Map;
import java.util.Optional;
import java.util.function.BooleanSupplier;
import java.util.function.Function;
import javax.annotation.Nullable;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class chb<R> implements AutoCloseable {
   private static final Logger a = LogManager.getLogger();
   private final cgv b;
   private final Long2ObjectMap<Optional<R>> c = new Long2ObjectOpenHashMap();
   private final LongLinkedOpenHashSet d = new LongLinkedOpenHashSet();
   private final Function<Runnable, Codec<R>> e;
   private final Function<Runnable, R> f;
   private final DataFixer g;
   private final aga h;

   public chb(File var1, Function<Runnable, Codec<R>> var2, Function<Runnable, R> var3, DataFixer var4, aga var5, boolean var6) {
      this.e = _snowman;
      this.f = _snowman;
      this.g = _snowman;
      this.h = _snowman;
      this.b = new cgv(_snowman, _snowman, _snowman.getName());
   }

   protected void a(BooleanSupplier var1) {
      while (!this.d.isEmpty() && _snowman.getAsBoolean()) {
         brd _snowman = gp.a(this.d.firstLong()).r();
         this.d(_snowman);
      }
   }

   @Nullable
   protected Optional<R> c(long var1) {
      return (Optional<R>)this.c.get(_snowman);
   }

   protected Optional<R> d(long var1) {
      gp _snowman = gp.a(_snowman);
      if (this.b(_snowman)) {
         return Optional.empty();
      } else {
         Optional<R> _snowmanx = this.c(_snowman);
         if (_snowmanx != null) {
            return _snowmanx;
         } else {
            this.b(_snowman.r());
            _snowmanx = this.c(_snowman);
            if (_snowmanx == null) {
               throw (IllegalStateException)x.c(new IllegalStateException());
            } else {
               return _snowmanx;
            }
         }
      }
   }

   protected boolean b(gp var1) {
      return brx.b(gp.c(_snowman.b()));
   }

   protected R e(long var1) {
      Optional<R> _snowman = this.d(_snowman);
      if (_snowman.isPresent()) {
         return _snowman.get();
      } else {
         R _snowmanx = this.f.apply(() -> this.a(_snowman));
         this.c.put(_snowman, Optional.of(_snowmanx));
         return _snowmanx;
      }
   }

   private void b(brd var1) {
      this.a(_snowman, mo.a, this.c(_snowman));
   }

   @Nullable
   private md c(brd var1) {
      try {
         return this.b.a(_snowman);
      } catch (IOException var3) {
         a.error("Error reading chunk {} data from disk", _snowman, var3);
         return null;
      }
   }

   private <T> void a(brd var1, DynamicOps<T> var2, @Nullable T var3) {
      if (_snowman == null) {
         for (int _snowman = 0; _snowman < 16; _snowman++) {
            this.c.put(gp.a(_snowman, _snowman).s(), Optional.empty());
         }
      } else {
         Dynamic<T> _snowman = new Dynamic(_snowman, _snowman);
         int _snowmanx = a(_snowman);
         int _snowmanxx = w.a().getWorldVersion();
         boolean _snowmanxxx = _snowmanx != _snowmanxx;
         Dynamic<T> _snowmanxxxx = this.g.update(this.h.a(), _snowman, _snowmanx, _snowmanxx);
         OptionalDynamic<T> _snowmanxxxxx = _snowmanxxxx.get("Sections");

         for (int _snowmanxxxxxx = 0; _snowmanxxxxxx < 16; _snowmanxxxxxx++) {
            long _snowmanxxxxxxx = gp.a(_snowman, _snowmanxxxxxx).s();
            Optional<R> _snowmanxxxxxxxx = _snowmanxxxxx.get(Integer.toString(_snowmanxxxxxx))
               .result()
               .flatMap(var3x -> this.e.apply(() -> this.a(_snowman)).parse(var3x).resultOrPartial(a::error));
            this.c.put(_snowmanxxxxxxx, _snowmanxxxxxxxx);
            _snowmanxxxxxxxx.ifPresent(var4x -> {
               this.b(_snowman);
               if (_snowman) {
                  this.a(_snowman);
               }
            });
         }
      }
   }

   private void d(brd var1) {
      Dynamic<mt> _snowman = this.a(_snowman, mo.a);
      mt _snowmanx = (mt)_snowman.getValue();
      if (_snowmanx instanceof md) {
         this.b.a(_snowman, (md)_snowmanx);
      } else {
         a.error("Expected compound tag, got {}", _snowmanx);
      }
   }

   private <T> Dynamic<T> a(brd var1, DynamicOps<T> var2) {
      Map<T, T> _snowman = Maps.newHashMap();

      for (int _snowmanx = 0; _snowmanx < 16; _snowmanx++) {
         long _snowmanxx = gp.a(_snowman, _snowmanx).s();
         this.d.remove(_snowmanxx);
         Optional<R> _snowmanxxx = (Optional<R>)this.c.get(_snowmanxx);
         if (_snowmanxxx != null && _snowmanxxx.isPresent()) {
            DataResult<T> _snowmanxxxx = this.e.apply(() -> this.a(_snowman)).encodeStart(_snowman, _snowmanxxx.get());
            String _snowmanxxxxx = Integer.toString(_snowmanx);
            _snowmanxxxx.resultOrPartial(a::error).ifPresent(var3x -> _snowman.put((T)_snowman.createString(_snowman), (T)var3x));
         }
      }

      return new Dynamic(
         _snowman, _snowman.createMap(ImmutableMap.of(_snowman.createString("Sections"), _snowman.createMap(_snowman), _snowman.createString("DataVersion"), _snowman.createInt(w.a().getWorldVersion())))
      );
   }

   protected void b(long var1) {
   }

   protected void a(long var1) {
      Optional<R> _snowman = (Optional<R>)this.c.get(_snowman);
      if (_snowman != null && _snowman.isPresent()) {
         this.d.add(_snowman);
      } else {
         a.warn("No data for position: {}", gp.a(_snowman));
      }
   }

   private static int a(Dynamic<?> var0) {
      return _snowman.get("DataVersion").asInt(1945);
   }

   public void a(brd var1) {
      if (!this.d.isEmpty()) {
         for (int _snowman = 0; _snowman < 16; _snowman++) {
            long _snowmanx = gp.a(_snowman, _snowman).s();
            if (this.d.contains(_snowmanx)) {
               this.d(_snowman);
               return;
            }
         }
      }
   }

   @Override
   public void close() throws IOException {
      this.b.close();
   }
}
