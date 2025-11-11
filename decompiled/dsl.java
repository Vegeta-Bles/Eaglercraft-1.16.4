import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Lists;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Supplier;

public abstract class dsl {
   public static final dsl a = new dsl("default") {
      @Override
      protected cfy a(gm<bsv> var1, gm<chp> var2, long var3) {
         return new cho(new btj(_snowman, false, false, _snowman), _snowman, () -> _snowman.d(chp.c));
      }
   };
   private static final dsl e = new dsl("flat") {
      @Override
      protected cfy a(gm<bsv> var1, gm<chp> var2, long var3) {
         return new chl(cpf.a(_snowman));
      }
   };
   private static final dsl f = new dsl("large_biomes") {
      @Override
      protected cfy a(gm<bsv> var1, gm<chp> var2, long var3) {
         return new cho(new btj(_snowman, false, true, _snowman), _snowman, () -> _snowman.d(chp.c));
      }
   };
   public static final dsl b = new dsl("amplified") {
      @Override
      protected cfy a(gm<bsv> var1, gm<chp> var2, long var3) {
         return new cho(new btj(_snowman, false, false, _snowman), _snowman, () -> _snowman.d(chp.d));
      }
   };
   private static final dsl g = new dsl("single_biome_surface") {
      @Override
      protected cfy a(gm<bsv> var1, gm<chp> var2, long var3) {
         return new cho(new btd(_snowman.d(btb.b)), _snowman, () -> _snowman.d(chp.c));
      }
   };
   private static final dsl h = new dsl("single_biome_caves") {
      @Override
      public chw a(gn.b var1, long var2, boolean var4, boolean var5) {
         gm<bsv> _snowman = _snowman.b(gm.ay);
         gm<chd> _snowmanx = _snowman.b(gm.K);
         gm<chp> _snowmanxx = _snowman.b(gm.ar);
         return new chw(_snowman, _snowman, _snowman, chw.a(chd.a(_snowmanx, _snowman, _snowmanxx, _snowman), () -> _snowman.d(chd.l), this.a(_snowman, _snowmanxx, _snowman)));
      }

      @Override
      protected cfy a(gm<bsv> var1, gm<chp> var2, long var3) {
         return new cho(new btd(_snowman.d(btb.b)), _snowman, () -> _snowman.d(chp.g));
      }
   };
   private static final dsl i = new dsl("single_biome_floating_islands") {
      @Override
      protected cfy a(gm<bsv> var1, gm<chp> var2, long var3) {
         return new cho(new btd(_snowman.d(btb.b)), _snowman, () -> _snowman.d(chp.h));
      }
   };
   private static final dsl j = new dsl("debug_all_block_states") {
      @Override
      protected cfy a(gm<bsv> var1, gm<chp> var2, long var3) {
         return new chj(_snowman);
      }
   };
   protected static final List<dsl> c = Lists.newArrayList(new dsl[]{a, e, f, b, g, h, i, j});
   protected static final Map<Optional<dsl>, dsl.a> d = ImmutableMap.of(
      Optional.of(e),
      (dsl.a)(var0, var1) -> {
         cfy _snowman = var1.e();
         return new dnv(
            var0,
            var2x -> var0.c.a(new chw(var1.a(), var1.b(), var1.c(), chw.a(var0.c.b().b(gm.K), var1.d(), new chl(var2x)))),
            _snowman instanceof chl ? ((chl)_snowman).g() : cpf.a(var0.c.b().b(gm.ay))
         );
      },
      Optional.of(g),
      (dsl.a)(var0, var1) -> new dnu(var0, var0.c.b(), var2 -> var0.c.a(a(var0.c.b(), var1, g, var2)), a(var0.c.b(), var1)),
      Optional.of(h),
      (dsl.a)(var0, var1) -> new dnu(var0, var0.c.b(), var2 -> var0.c.a(a(var0.c.b(), var1, h, var2)), a(var0.c.b(), var1)),
      Optional.of(i),
      (dsl.a)(var0, var1) -> new dnu(var0, var0.c.b(), var2 -> var0.c.a(a(var0.c.b(), var1, i, var2)), a(var0.c.b(), var1))
   );
   private final nr k;

   private dsl(String var1) {
      this.k = new of("generator." + _snowman);
   }

   private static chw a(gn var0, chw var1, dsl var2, bsv var3) {
      bsy _snowman = new btd(_snowman);
      gm<chd> _snowmanx = _snowman.b(gm.K);
      gm<chp> _snowmanxx = _snowman.b(gm.ar);
      Supplier<chp> _snowmanxxx;
      if (_snowman == h) {
         _snowmanxxx = () -> _snowman.d(chp.g);
      } else if (_snowman == i) {
         _snowmanxxx = () -> _snowman.d(chp.h);
      } else {
         _snowmanxxx = () -> _snowman.d(chp.c);
      }

      return new chw(_snowman.a(), _snowman.b(), _snowman.c(), chw.a(_snowmanx, _snowman.d(), new cho(_snowman, _snowman.a(), _snowmanxxx)));
   }

   private static bsv a(gn var0, chw var1) {
      return _snowman.e().d().b().stream().findFirst().orElse(_snowman.b(gm.ay).d(btb.b));
   }

   public static Optional<dsl> a(chw var0) {
      cfy _snowman = _snowman.e();
      if (_snowman instanceof chl) {
         return Optional.of(e);
      } else {
         return _snowman instanceof chj ? Optional.of(j) : Optional.empty();
      }
   }

   public nr a() {
      return this.k;
   }

   public chw a(gn.b var1, long var2, boolean var4, boolean var5) {
      gm<bsv> _snowman = _snowman.b(gm.ay);
      gm<chd> _snowmanx = _snowman.b(gm.K);
      gm<chp> _snowmanxx = _snowman.b(gm.ar);
      return new chw(_snowman, _snowman, _snowman, chw.a(_snowmanx, chd.a(_snowmanx, _snowman, _snowmanxx, _snowman), this.a(_snowman, _snowmanxx, _snowman)));
   }

   protected abstract cfy a(gm<bsv> var1, gm<chp> var2, long var3);

   public interface a {
      dot createEditScreen(dsf var1, chw var2);
   }
}
