import com.google.common.collect.ImmutableList;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import it.unimi.dsi.fastutil.shorts.Short2ObjectMap;
import it.unimi.dsi.fastutil.shorts.Short2ObjectOpenHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.Predicate;
import java.util.stream.Stream;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.util.Supplier;

public class azq {
   private static final Logger a = LogManager.getLogger();
   private final Short2ObjectMap<azp> b = new Short2ObjectOpenHashMap();
   private final Map<azr, Set<azp>> c = Maps.newHashMap();
   private final Runnable d;
   private boolean e;

   public static Codec<azq> a(Runnable var0) {
      return RecordCodecBuilder.create(
            var1 -> var1.group(
                     RecordCodecBuilder.point(_snowman),
                     Codec.BOOL.optionalFieldOf("Valid", false).forGetter(var0x -> var0x.e),
                     azp.a(_snowman).listOf().fieldOf("Records").forGetter(var0x -> ImmutableList.copyOf(var0x.b.values()))
                  )
                  .apply(var1, azq::new)
         )
         .orElseGet(x.a("Failed to read POI section: ", a::error), () -> new azq(_snowman, false, ImmutableList.of()));
   }

   public azq(Runnable var1) {
      this(_snowman, true, ImmutableList.of());
   }

   private azq(Runnable var1, boolean var2, List<azp> var3) {
      this.d = _snowman;
      this.e = _snowman;
      _snowman.forEach(this::a);
   }

   public Stream<azp> a(Predicate<azr> var1, azo.b var2) {
      return this.c.entrySet().stream().filter(var1x -> _snowman.test(var1x.getKey())).flatMap(var0 -> var0.getValue().stream()).filter(_snowman.a());
   }

   public void a(fx var1, azr var2) {
      if (this.a(new azp(_snowman, _snowman, this.d))) {
         a.debug("Added POI of type {} @ {}", new Supplier[]{() -> _snowman, () -> _snowman});
         this.d.run();
      }
   }

   private boolean a(azp var1) {
      fx _snowman = _snowman.f();
      azr _snowmanx = _snowman.g();
      short _snowmanxx = gp.b(_snowman);
      azp _snowmanxxx = (azp)this.b.get(_snowmanxx);
      if (_snowmanxxx != null) {
         if (_snowmanx.equals(_snowmanxxx.g())) {
            return false;
         } else {
            throw (IllegalStateException)x.c(new IllegalStateException("POI data mismatch: already registered at " + _snowman));
         }
      } else {
         this.b.put(_snowmanxx, _snowman);
         this.c.computeIfAbsent(_snowmanx, var0 -> Sets.newHashSet()).add(_snowman);
         return true;
      }
   }

   public void a(fx var1) {
      azp _snowman = (azp)this.b.remove(gp.b(_snowman));
      if (_snowman == null) {
         a.error("POI data mismatch: never registered at " + _snowman);
      } else {
         this.c.get(_snowman.g()).remove(_snowman);
         a.debug("Removed POI of type {} @ {}", new Supplier[]{_snowman::g, _snowman::f});
         this.d.run();
      }
   }

   public boolean c(fx var1) {
      azp _snowman = (azp)this.b.get(gp.b(_snowman));
      if (_snowman == null) {
         throw (IllegalStateException)x.c(new IllegalStateException("POI never registered at " + _snowman));
      } else {
         boolean _snowmanx = _snowman.c();
         this.d.run();
         return _snowmanx;
      }
   }

   public boolean a(fx var1, Predicate<azr> var2) {
      short _snowman = gp.b(_snowman);
      azp _snowmanx = (azp)this.b.get(_snowman);
      return _snowmanx != null && _snowman.test(_snowmanx.g());
   }

   public Optional<azr> d(fx var1) {
      short _snowman = gp.b(_snowman);
      azp _snowmanx = (azp)this.b.get(_snowman);
      return _snowmanx != null ? Optional.of(_snowmanx.g()) : Optional.empty();
   }

   public void a(Consumer<BiConsumer<fx, azr>> var1) {
      if (!this.e) {
         Short2ObjectMap<azp> _snowman = new Short2ObjectOpenHashMap(this.b);
         this.b();
         _snowman.accept((var2x, var3) -> {
            short _snowmanx = gp.b(var2x);
            azp _snowmanx = (azp)_snowman.computeIfAbsent(_snowmanx, var3x -> new azp(var2x, var3, this.d));
            this.a(_snowmanx);
         });
         this.e = true;
         this.d.run();
      }
   }

   private void b() {
      this.b.clear();
      this.c.clear();
   }

   boolean a() {
      return this.e;
   }
}
