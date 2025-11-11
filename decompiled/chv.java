import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import java.util.Map;
import java.util.Optional;
import javax.annotation.Nullable;

public class chv {
   public static final Codec<chv> a = RecordCodecBuilder.create(
      var0 -> var0.group(
               cmx.a.optionalFieldOf("stronghold").forGetter(var0x -> Optional.ofNullable(var0x.e)),
               Codec.simpleMap(gm.aG, cmy.a, gm.aG).fieldOf("structures").forGetter(var0x -> var0x.d)
            )
            .apply(var0, chv::new)
   );
   public static final ImmutableMap<cla<?>, cmy> b = ImmutableMap.builder()
      .put(cla.q, new cmy(32, 8, 10387312))
      .put(cla.f, new cmy(32, 8, 14357617))
      .put(cla.g, new cmy(32, 8, 14357618))
      .put(cla.e, new cmy(32, 8, 14357619))
      .put(cla.j, new cmy(32, 8, 14357620))
      .put(cla.b, new cmy(32, 8, 165745296))
      .put(cla.k, new cmy(1, 0, 0))
      .put(cla.l, new cmy(32, 5, 10387313))
      .put(cla.o, new cmy(20, 11, 10387313))
      .put(cla.d, new cmy(80, 20, 10387319))
      .put(cla.p, new cmy(1, 0, 0))
      .put(cla.c, new cmy(1, 0, 0))
      .put(cla.h, new cmy(40, 15, 34222645))
      .put(cla.i, new cmy(24, 4, 165745295))
      .put(cla.m, new cmy(20, 8, 14357621))
      .put(cla.s, new cmy(27, 4, 30084232))
      .put(cla.n, new cmy(27, 4, 30084232))
      .put(cla.r, new cmy(2, 1, 14357921))
      .build();
   public static final cmx c;
   private final Map<cla<?>, cmy> d;
   @Nullable
   private final cmx e;

   public chv(Optional<cmx> var1, Map<cla<?>, cmy> var2) {
      this.e = _snowman.orElse(null);
      this.d = _snowman;
   }

   public chv(boolean var1) {
      this.d = Maps.newHashMap(b);
      this.e = _snowman ? c : null;
   }

   public Map<cla<?>, cmy> a() {
      return this.d;
   }

   @Nullable
   public cmy a(cla<?> var1) {
      return this.d.get(_snowman);
   }

   @Nullable
   public cmx b() {
      return this.e;
   }

   static {
      for (cla<?> _snowman : gm.aG) {
         if (!b.containsKey(_snowman)) {
            throw new IllegalStateException("Structure feature without default settings: " + gm.aG.b(_snowman));
         }
      }

      c = new cmx(32, 3, 128);
   }
}
