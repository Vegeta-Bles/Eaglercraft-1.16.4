import com.google.common.collect.ImmutableList;
import com.google.common.collect.Lists;
import com.mojang.datafixers.util.Pair;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import it.unimi.dsi.fastutil.objects.ObjectArrays;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class cok {
   private static final Logger c = LogManager.getLogger();
   public static final Codec<cok> a = RecordCodecBuilder.create(
      var0 -> var0.group(
               vk.a.fieldOf("name").forGetter(cok::b),
               vk.a.fieldOf("fallback").forGetter(cok::a),
               Codec.mapPair(coi.e.fieldOf("element"), Codec.INT.fieldOf("weight"))
                  .codec()
                  .listOf()
                  .promotePartial(x.a("Pool element: ", c::error))
                  .fieldOf("elements")
                  .forGetter(var0x -> var0x.e)
            )
            .apply(var0, cok::new)
   );
   public static final Codec<Supplier<cok>> b = vf.a(gm.ax, a);
   private final vk d;
   private final List<Pair<coi, Integer>> e;
   private final List<coi> f;
   private final vk g;
   private int h = Integer.MIN_VALUE;

   public cok(vk var1, vk var2, List<Pair<coi, Integer>> var3) {
      this.d = _snowman;
      this.e = _snowman;
      this.f = Lists.newArrayList();

      for (Pair<coi, Integer> _snowman : _snowman) {
         coi _snowmanx = (coi)_snowman.getFirst();

         for (int _snowmanxx = 0; _snowmanxx < _snowman.getSecond(); _snowmanxx++) {
            this.f.add(_snowmanx);
         }
      }

      this.g = _snowman;
   }

   public cok(vk var1, vk var2, List<Pair<Function<cok.a, ? extends coi>, Integer>> var3, cok.a var4) {
      this.d = _snowman;
      this.e = Lists.newArrayList();
      this.f = Lists.newArrayList();

      for (Pair<Function<cok.a, ? extends coi>, Integer> _snowman : _snowman) {
         coi _snowmanx = (coi)((Function)_snowman.getFirst()).apply(_snowman);
         this.e.add(Pair.of(_snowmanx, _snowman.getSecond()));

         for (int _snowmanxx = 0; _snowmanxx < _snowman.getSecond(); _snowmanxx++) {
            this.f.add(_snowmanx);
         }
      }

      this.g = _snowman;
   }

   public int a(csw var1) {
      if (this.h == Integer.MIN_VALUE) {
         this.h = this.f.stream().mapToInt(var1x -> var1x.a(_snowman, fx.b, bzm.a).e()).max().orElse(0);
      }

      return this.h;
   }

   public vk a() {
      return this.g;
   }

   public coi a(Random var1) {
      return this.f.get(_snowman.nextInt(this.f.size()));
   }

   public List<coi> b(Random var1) {
      return ImmutableList.copyOf(ObjectArrays.shuffle(this.f.toArray(new coi[0]), _snowman));
   }

   public vk b() {
      return this.d;
   }

   public int c() {
      return this.f.size();
   }

   public static enum a implements afs {
      a("terrain_matching", ImmutableList.of(new csi(chn.a.a, -1))),
      b("rigid", ImmutableList.of());

      public static final Codec<cok.a> c = afs.a(cok.a::values, cok.a::a);
      private static final Map<String, cok.a> d = Arrays.stream(values()).collect(Collectors.toMap(cok.a::b, var0 -> (cok.a)var0));
      private final String e;
      private final ImmutableList<csy> f;

      private a(String var3, ImmutableList<csy> var4) {
         this.e = _snowman;
         this.f = _snowman;
      }

      public String b() {
         return this.e;
      }

      public static cok.a a(String var0) {
         return d.get(_snowman);
      }

      public ImmutableList<csy> c() {
         return this.f;
      }

      @Override
      public String a() {
         return this.e;
      }
   }
}
