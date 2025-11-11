import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.stream.Stream;
import javax.annotation.Nullable;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class btg {
   public static final Logger a = LogManager.getLogger();
   public static final btg b = new btg(
      0.1F, Stream.of(aqo.values()).collect(ImmutableMap.toImmutableMap(var0 -> var0, var0 -> ImmutableList.of())), ImmutableMap.of(), false
   );
   public static final MapCodec<btg> c = RecordCodecBuilder.mapCodec(
      var0 -> var0.group(
               Codec.FLOAT.optionalFieldOf("creature_spawn_probability", 0.1F).forGetter(var0x -> var0x.d),
               Codec.simpleMap(aqo.g, btg.c.b.listOf().promotePartial(x.a("Spawn data: ", a::error)), afs.a(aqo.values()))
                  .fieldOf("spawners")
                  .forGetter(var0x -> var0x.e),
               Codec.simpleMap(gm.S, btg.b.a, gm.S).fieldOf("spawn_costs").forGetter(var0x -> var0x.f),
               Codec.BOOL.fieldOf("player_spawn_friendly").orElse(false).forGetter(btg::b)
            )
            .apply(var0, btg::new)
   );
   private final float d;
   private final Map<aqo, List<btg.c>> e;
   private final Map<aqe<?>, btg.b> f;
   private final boolean g;

   private btg(float var1, Map<aqo, List<btg.c>> var2, Map<aqe<?>, btg.b> var3, boolean var4) {
      this.d = _snowman;
      this.e = _snowman;
      this.f = _snowman;
      this.g = _snowman;
   }

   public List<btg.c> a(aqo var1) {
      return this.e.getOrDefault(_snowman, ImmutableList.of());
   }

   @Nullable
   public btg.b a(aqe<?> var1) {
      return this.f.get(_snowman);
   }

   public float a() {
      return this.d;
   }

   public boolean b() {
      return this.g;
   }

   public static class a {
      private final Map<aqo, List<btg.c>> a = Stream.of(aqo.values()).collect(ImmutableMap.toImmutableMap(var0 -> var0, var0 -> Lists.newArrayList()));
      private final Map<aqe<?>, btg.b> b = Maps.newLinkedHashMap();
      private float c = 0.1F;
      private boolean d;

      public a() {
      }

      public btg.a a(aqo var1, btg.c var2) {
         this.a.get(_snowman).add(_snowman);
         return this;
      }

      public btg.a a(aqe<?> var1, double var2, double var4) {
         this.b.put(_snowman, new btg.b(_snowman, _snowman));
         return this;
      }

      public btg.a a(float var1) {
         this.c = _snowman;
         return this;
      }

      public btg.a a() {
         this.d = true;
         return this;
      }

      public btg b() {
         return new btg(
            this.c,
            this.a.entrySet().stream().collect(ImmutableMap.toImmutableMap(Entry::getKey, var0 -> ImmutableList.copyOf((Collection)var0.getValue()))),
            ImmutableMap.copyOf(this.b),
            this.d
         );
      }
   }

   public static class b {
      public static final Codec<btg.b> a = RecordCodecBuilder.create(
         var0 -> var0.group(Codec.DOUBLE.fieldOf("energy_budget").forGetter(var0x -> var0x.b), Codec.DOUBLE.fieldOf("charge").forGetter(var0x -> var0x.c))
               .apply(var0, btg.b::new)
      );
      private final double b;
      private final double c;

      private b(double var1, double var3) {
         this.b = _snowman;
         this.c = _snowman;
      }

      public double a() {
         return this.b;
      }

      public double b() {
         return this.c;
      }
   }

   public static class c extends afz.a {
      public static final Codec<btg.c> b = RecordCodecBuilder.create(
         var0 -> var0.group(
                  gm.S.fieldOf("type").forGetter(var0x -> var0x.c),
                  Codec.INT.fieldOf("weight").forGetter(var0x -> var0x.a),
                  Codec.INT.fieldOf("minCount").forGetter(var0x -> var0x.d),
                  Codec.INT.fieldOf("maxCount").forGetter(var0x -> var0x.e)
               )
               .apply(var0, btg.c::new)
      );
      public final aqe<?> c;
      public final int d;
      public final int e;

      public c(aqe<?> var1, int var2, int var3, int var4) {
         super(_snowman);
         this.c = _snowman.e() == aqo.f ? aqe.ah : _snowman;
         this.d = _snowman;
         this.e = _snowman;
      }

      @Override
      public String toString() {
         return aqe.a(this.c) + "*(" + this.d + "-" + this.e + "):" + this.a;
      }
   }
}
