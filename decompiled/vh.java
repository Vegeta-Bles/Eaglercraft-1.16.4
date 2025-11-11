import com.google.common.base.Suppliers;
import com.google.common.collect.Maps;
import com.google.gson.JsonElement;
import com.google.gson.JsonIOException;
import com.google.gson.JsonParser;
import com.google.gson.JsonSyntaxException;
import com.mojang.datafixers.util.Pair;
import com.mojang.serialization.Codec;
import com.mojang.serialization.DataResult;
import com.mojang.serialization.Decoder;
import com.mojang.serialization.DynamicOps;
import com.mojang.serialization.Encoder;
import com.mojang.serialization.JsonOps;
import com.mojang.serialization.Lifecycle;
import com.mojang.serialization.DataResult.PartialResult;
import it.unimi.dsi.fastutil.objects.Object2IntMap;
import it.unimi.dsi.fastutil.objects.Object2IntOpenCustomHashMap;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.nio.charset.StandardCharsets;
import java.util.Collection;
import java.util.IdentityHashMap;
import java.util.Map;
import java.util.Optional;
import java.util.OptionalInt;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class vh<T> extends vd<T> {
   private static final Logger b = LogManager.getLogger();
   private final vh.b c;
   private final gn.b d;
   private final Map<vj<? extends gm<?>>, vh.a<?>> e;
   private final vh<JsonElement> f;

   public static <T> vh<T> a(DynamicOps<T> var0, ach var1, gn.b var2) {
      return a(_snowman, vh.b.a(_snowman), _snowman);
   }

   public static <T> vh<T> a(DynamicOps<T> var0, vh.b var1, gn.b var2) {
      vh<T> _snowman = new vh<>(_snowman, _snowman, _snowman, Maps.newIdentityHashMap());
      gn.a(_snowman, _snowman);
      return _snowman;
   }

   private vh(DynamicOps<T> var1, vh.b var2, gn.b var3, IdentityHashMap<vj<? extends gm<?>>, vh.a<?>> var4) {
      super(_snowman);
      this.c = _snowman;
      this.d = _snowman;
      this.e = _snowman;
      this.f = _snowman == JsonOps.INSTANCE ? this : new vh<>(JsonOps.INSTANCE, _snowman, _snowman, _snowman);
   }

   protected <E> DataResult<Pair<Supplier<E>, T>> a(T var1, vj<? extends gm<E>> var2, Codec<E> var3, boolean var4) {
      Optional<gs<E>> _snowman = this.d.a(_snowman);
      if (!_snowman.isPresent()) {
         return DataResult.error("Unknown registry: " + _snowman);
      } else {
         gs<E> _snowmanx = _snowman.get();
         DataResult<Pair<vk, T>> _snowmanxx = vk.a.decode(this.a, _snowman);
         if (!_snowmanxx.result().isPresent()) {
            return !_snowman ? DataResult.error("Inline definitions not allowed here") : _snowman.decode(this, _snowman).map(var0 -> var0.mapFirst(var0x -> () -> var0x));
         } else {
            Pair<vk, T> _snowmanxxx = (Pair<vk, T>)_snowmanxx.result().get();
            vk _snowmanxxxx = (vk)_snowmanxxx.getFirst();
            return this.a(_snowman, _snowmanx, _snowman, _snowmanxxxx).map(var1x -> Pair.of(var1x, _snowman.getSecond()));
         }
      }
   }

   public <E> DataResult<gi<E>> a(gi<E> var1, vj<? extends gm<E>> var2, Codec<E> var3) {
      Collection<vk> _snowman = this.c.a(_snowman);
      DataResult<gi<E>> _snowmanx = DataResult.success(_snowman, Lifecycle.stable());
      String _snowmanxx = _snowman.a().a() + "/";

      for (vk _snowmanxxx : _snowman) {
         String _snowmanxxxx = _snowmanxxx.a();
         if (!_snowmanxxxx.endsWith(".json")) {
            b.warn("Skipping resource {} since it is not a json file", _snowmanxxx);
         } else if (!_snowmanxxxx.startsWith(_snowmanxx)) {
            b.warn("Skipping resource {} since it does not have a registry name prefix", _snowmanxxx);
         } else {
            String _snowmanxxxxx = _snowmanxxxx.substring(_snowmanxx.length(), _snowmanxxxx.length() - ".json".length());
            vk _snowmanxxxxxx = new vk(_snowmanxxx.b(), _snowmanxxxxx);
            _snowmanx = _snowmanx.flatMap(var4x -> this.a(_snowman, var4x, _snowman, _snowman).map(var1x -> var4x));
         }
      }

      return _snowmanx.setPartial(_snowman);
   }

   private <E> DataResult<Supplier<E>> a(vj<? extends gm<E>> var1, gs<E> var2, Codec<E> var3, vk var4) {
      vj<E> _snowman = vj.a(_snowman, _snowman);
      vh.a<E> _snowmanx = this.b(_snowman);
      DataResult<Supplier<E>> _snowmanxx = _snowmanx.a.get(_snowman);
      if (_snowmanxx != null) {
         return _snowmanxx;
      } else {
         Supplier<E> _snowmanxxx = Suppliers.memoize(() -> {
            E _snowmanxxxx = _snowman.a(_snowman);
            if (_snowmanxxxx == null) {
               throw new RuntimeException("Error during recursive registry parsing, element resolved too early: " + _snowman);
            } else {
               return _snowmanxxxx;
            }
         });
         _snowmanx.a.put(_snowman, DataResult.success(_snowmanxxx));
         DataResult<Pair<E, OptionalInt>> _snowmanxxxx = this.c.a(this.f, _snowman, _snowman, _snowman);
         Optional<Pair<E, OptionalInt>> _snowmanxxxxx = _snowmanxxxx.result();
         if (_snowmanxxxxx.isPresent()) {
            Pair<E, OptionalInt> _snowmanxxxxxx = _snowmanxxxxx.get();
            _snowman.a((OptionalInt)_snowmanxxxxxx.getSecond(), _snowman, _snowmanxxxxxx.getFirst(), _snowmanxxxx.lifecycle());
         }

         DataResult<Supplier<E>> _snowmanxxxxxx;
         if (!_snowmanxxxxx.isPresent() && _snowman.a(_snowman) != null) {
            _snowmanxxxxxx = DataResult.success((Supplier<E>)(() -> _snowman.a(_snowman)), Lifecycle.stable());
         } else {
            _snowmanxxxxxx = _snowmanxxxx.map(var2x -> () -> _snowman.a(_snowman));
         }

         _snowmanx.a.put(_snowman, _snowmanxxxxxx);
         return _snowmanxxxxxx;
      }
   }

   private <E> vh.a<E> b(vj<? extends gm<E>> var1) {
      return (vh.a<E>)this.e.computeIfAbsent(_snowman, var0 -> new vh.a());
   }

   protected <E> DataResult<gm<E>> a(vj<? extends gm<E>> var1) {
      return this.d.a(_snowman).map(var0 -> DataResult.success(var0, var0.b())).orElseGet(() -> DataResult.error("Unknown registry: " + _snowman));
   }

   static final class a<E> {
      private final Map<vj<E>, DataResult<Supplier<E>>> a = Maps.newIdentityHashMap();

      private a() {
      }
   }

   public interface b {
      Collection<vk> a(vj<? extends gm<?>> var1);

      <E> DataResult<Pair<E, OptionalInt>> a(DynamicOps<JsonElement> var1, vj<? extends gm<E>> var2, vj<E> var3, Decoder<E> var4);

      static vh.b a(final ach var0) {
         return new vh.b() {
            @Override
            public Collection<vk> a(vj<? extends gm<?>> var1) {
               return _snowman.a(_snowman.a().a(), var0x -> var0x.endsWith(".json"));
            }

            @Override
            public <E> DataResult<Pair<E, OptionalInt>> a(DynamicOps<JsonElement> var1, vj<? extends gm<E>> var2, vj<E> var3, Decoder<E> var4) {
               vk _snowman = _snowman.a();
               vk _snowmanx = new vk(_snowman.b(), _snowman.a().a() + "/" + _snowman.a() + ".json");

               try (
                  acg _snowmanxx = _snowman.a(_snowmanx);
                  Reader _snowmanxxx = new InputStreamReader(_snowmanxx.b(), StandardCharsets.UTF_8);
               ) {
                  JsonParser _snowmanxxxx = new JsonParser();
                  JsonElement _snowmanxxxxx = _snowmanxxxx.parse(_snowmanxxx);
                  return _snowman.parse(_snowman, _snowmanxxxxx).map(var0x -> Pair.of(var0x, OptionalInt.empty()));
               } catch (JsonIOException | JsonSyntaxException | IOException var42) {
                  return DataResult.error("Failed to parse " + _snowmanx + " file: " + var42.getMessage());
               }
            }

            @Override
            public String toString() {
               return "ResourceAccess[" + _snowman + "]";
            }
         };
      }

      public static final class a implements vh.b {
         private final Map<vj<?>, JsonElement> a = Maps.newIdentityHashMap();
         private final Object2IntMap<vj<?>> b = new Object2IntOpenCustomHashMap(x.k());
         private final Map<vj<?>, Lifecycle> c = Maps.newIdentityHashMap();

         public a() {
         }

         public <E> void a(gn.b var1, vj<E> var2, Encoder<E> var3, int var4, E var5, Lifecycle var6) {
            DataResult<JsonElement> _snowman = _snowman.encodeStart(vi.a(JsonOps.INSTANCE, _snowman), _snowman);
            Optional<PartialResult<JsonElement>> _snowmanx = _snowman.error();
            if (_snowmanx.isPresent()) {
               vh.b.error("Error adding element: {}", _snowmanx.get().message());
            } else {
               this.a.put(_snowman, (JsonElement)_snowman.result().get());
               this.b.put(_snowman, _snowman);
               this.c.put(_snowman, _snowman);
            }
         }

         @Override
         public Collection<vk> a(vj<? extends gm<?>> var1) {
            return this.a
               .keySet()
               .stream()
               .filter(var1x -> var1x.a(_snowman))
               .map(var1x -> new vk(var1x.a().b(), _snowman.a().a() + "/" + var1x.a().a() + ".json"))
               .collect(Collectors.toList());
         }

         @Override
         public <E> DataResult<Pair<E, OptionalInt>> a(DynamicOps<JsonElement> var1, vj<? extends gm<E>> var2, vj<E> var3, Decoder<E> var4) {
            JsonElement _snowman = this.a.get(_snowman);
            return _snowman == null
               ? DataResult.error("Unknown element: " + _snowman)
               : _snowman.parse(_snowman, _snowman).setLifecycle(this.c.get(_snowman)).map(var2x -> Pair.of(var2x, OptionalInt.of(this.b.getInt(_snowman))));
         }
      }
   }
}
