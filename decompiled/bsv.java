import com.google.common.collect.ImmutableList;
import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import it.unimi.dsi.fastutil.longs.Long2FloatLinkedOpenHashMap;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Random;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import javax.annotation.Nullable;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public final class bsv {
   public static final Logger a = LogManager.getLogger();
   public static final Codec<bsv> b = RecordCodecBuilder.create(
      var0 -> var0.group(
               bsv.d.a.forGetter(var0x -> var0x.j),
               bsv.b.r.fieldOf("category").forGetter(var0x -> var0x.o),
               Codec.FLOAT.fieldOf("depth").forGetter(var0x -> var0x.m),
               Codec.FLOAT.fieldOf("scale").forGetter(var0x -> var0x.n),
               bsz.a.fieldOf("effects").forGetter(var0x -> var0x.p),
               bsw.c.forGetter(var0x -> var0x.k),
               btg.c.forGetter(var0x -> var0x.l)
            )
            .apply(var0, bsv::new)
   );
   public static final Codec<bsv> c = RecordCodecBuilder.create(
      var0 -> var0.group(
               bsv.d.a.forGetter(var0x -> var0x.j),
               bsv.b.r.fieldOf("category").forGetter(var0x -> var0x.o),
               Codec.FLOAT.fieldOf("depth").forGetter(var0x -> var0x.m),
               Codec.FLOAT.fieldOf("scale").forGetter(var0x -> var0x.n),
               bsz.a.fieldOf("effects").forGetter(var0x -> var0x.p)
            )
            .apply(var0, (var0x, var1, var2, var3, var4) -> new bsv(var0x, var1, var2, var3, var4, bsw.b, btg.b))
   );
   public static final Codec<Supplier<bsv>> d = vf.a(gm.ay, b);
   public static final Codec<List<Supplier<bsv>>> e = vf.b(gm.ay, b);
   private final Map<Integer, List<cla<?>>> g = gm.aG.g().collect(Collectors.groupingBy(var0 -> var0.f().ordinal()));
   private static final cuc h = new cuc(new chx(1234L), ImmutableList.of(0));
   private static final cuc i = new cuc(new chx(3456L), ImmutableList.of(-2, -1, 0));
   public static final cuc f = new cuc(new chx(2345L), ImmutableList.of(0));
   private final bsv.d j;
   private final bsw k;
   private final btg l;
   private final float m;
   private final float n;
   private final bsv.b o;
   private final bsz p;
   private final ThreadLocal<Long2FloatLinkedOpenHashMap> q = ThreadLocal.withInitial(() -> x.a((Supplier<? extends Long2FloatLinkedOpenHashMap>)(() -> {
         Long2FloatLinkedOpenHashMap _snowman = new Long2FloatLinkedOpenHashMap(1024, 0.25F) {
            protected void rehash(int var1) {
            }
         };
         _snowman.defaultReturnValue(Float.NaN);
         return _snowman;
      })));

   private bsv(bsv.d var1, bsv.b var2, float var3, float var4, bsz var5, bsw var6, btg var7) {
      this.j = _snowman;
      this.k = _snowman;
      this.l = _snowman;
      this.o = _snowman;
      this.m = _snowman;
      this.n = _snowman;
      this.p = _snowman;
   }

   public int a() {
      return this.p.d();
   }

   public btg b() {
      return this.l;
   }

   public bsv.e c() {
      return this.j.b;
   }

   public boolean d() {
      return this.i() > 0.85F;
   }

   private float b(fx var1) {
      float _snowman = this.j.d.a(_snowman, this.k());
      if (_snowman.v() > 64) {
         float _snowmanx = (float)(h.a((double)((float)_snowman.u() / 8.0F), (double)((float)_snowman.w() / 8.0F), false) * 4.0);
         return _snowman - (_snowmanx + (float)_snowman.v() - 64.0F) * 0.05F / 30.0F;
      } else {
         return _snowman;
      }
   }

   public final float a(fx var1) {
      long _snowman = _snowman.a();
      Long2FloatLinkedOpenHashMap _snowmanx = this.q.get();
      float _snowmanxx = _snowmanx.get(_snowman);
      if (!Float.isNaN(_snowmanxx)) {
         return _snowmanxx;
      } else {
         float _snowmanxxx = this.b(_snowman);
         if (_snowmanx.size() == 1024) {
            _snowmanx.removeFirstFloat();
         }

         _snowmanx.put(_snowman, _snowmanxxx);
         return _snowmanxxx;
      }
   }

   public boolean a(brz var1, fx var2) {
      return this.a(_snowman, _snowman, true);
   }

   public boolean a(brz var1, fx var2, boolean var3) {
      if (this.a(_snowman) >= 0.15F) {
         return false;
      } else {
         if (_snowman.v() >= 0 && _snowman.v() < 256 && _snowman.a(bsf.b, _snowman) < 10) {
            ceh _snowman = _snowman.d_(_snowman);
            cux _snowmanx = _snowman.b(_snowman);
            if (_snowmanx.a() == cuy.c && _snowman.b() instanceof byb) {
               if (!_snowman) {
                  return true;
               }

               boolean _snowmanxx = _snowman.A(_snowman.f()) && _snowman.A(_snowman.g()) && _snowman.A(_snowman.d()) && _snowman.A(_snowman.e());
               if (!_snowmanxx) {
                  return true;
               }
            }
         }

         return false;
      }
   }

   public boolean b(brz var1, fx var2) {
      if (this.a(_snowman) >= 0.15F) {
         return false;
      } else {
         if (_snowman.v() >= 0 && _snowman.v() < 256 && _snowman.a(bsf.b, _snowman) < 10) {
            ceh _snowman = _snowman.d_(_snowman);
            if (_snowman.g() && bup.cC.n().a(_snowman, _snowman)) {
               return true;
            }
         }

         return false;
      }
   }

   public bsw e() {
      return this.k;
   }

   public void a(bsn var1, cfy var2, aam var3, long var4, chx var6, fx var7) {
      List<List<Supplier<civ<?, ?>>>> _snowman = this.k.c();
      int _snowmanx = chm.b.values().length;

      for (int _snowmanxx = 0; _snowmanxx < _snowmanx; _snowmanxx++) {
         int _snowmanxxx = 0;
         if (_snowman.a()) {
            for (cla<?> _snowmanxxxx : this.g.getOrDefault(_snowmanxx, Collections.emptyList())) {
               _snowman.b(_snowman, _snowmanxxx, _snowmanxx);
               int _snowmanxxxxx = _snowman.u() >> 4;
               int _snowmanxxxxxx = _snowman.w() >> 4;
               int _snowmanxxxxxxx = _snowmanxxxxx << 4;
               int _snowmanxxxxxxxx = _snowmanxxxxxx << 4;

               try {
                  _snowman.a(gp.a(_snowman), _snowmanxxxx).forEach(var8x -> var8x.a(_snowman, _snowman, _snowman, _snowman, new cra(_snowman, _snowman, _snowman + 15, _snowman + 15), new brd(_snowman, _snowman)));
               } catch (Exception var21) {
                  l _snowmanxxxxxxxxx = l.a(var21, "Feature placement");
                  _snowmanxxxxxxxxx.a("Feature").a("Id", gm.aG.b(_snowmanxxxx)).a("Description", () -> _snowman.toString());
                  throw new u(_snowmanxxxxxxxxx);
               }

               _snowmanxxx++;
            }
         }

         if (_snowman.size() > _snowmanxx) {
            for (Supplier<civ<?, ?>> _snowmanxxxx : _snowman.get(_snowmanxx)) {
               civ<?, ?> _snowmanxxxxx = _snowmanxxxx.get();
               _snowman.b(_snowman, _snowmanxxx, _snowmanxx);

               try {
                  _snowmanxxxxx.a(_snowman, _snowman, _snowman, _snowman);
               } catch (Exception var22) {
                  l _snowmanxxxxxx = l.a(var22, "Feature placement");
                  _snowmanxxxxxx.a("Feature").a("Id", gm.aE.b(_snowmanxxxxx.e)).a("Config", _snowmanxxxxx.f).a("Description", () -> _snowman.e.toString());
                  throw new u(_snowmanxxxxxx);
               }

               _snowmanxxx++;
            }
         }
      }
   }

   public int f() {
      return this.p.a();
   }

   public int a(double var1, double var3) {
      int _snowman = this.p.f().orElseGet(this::v);
      return this.p.g().a(_snowman, _snowman, _snowman);
   }

   private int v() {
      double _snowman = (double)afm.a(this.j.c, 0.0F, 1.0F);
      double _snowmanx = (double)afm.a(this.j.e, 0.0F, 1.0F);
      return brv.a(_snowman, _snowmanx);
   }

   public int g() {
      return this.p.e().orElseGet(this::w);
   }

   private int w() {
      double _snowman = (double)afm.a(this.j.c, 0.0F, 1.0F);
      double _snowmanx = (double)afm.a(this.j.e, 0.0F, 1.0F);
      return brr.a(_snowman, _snowmanx);
   }

   public void a(Random var1, cfw var2, int var3, int var4, int var5, double var6, ceh var8, ceh var9, int var10, long var11) {
      ctg<?> _snowman = this.k.d().get();
      _snowman.a(_snowman);
      _snowman.a(_snowman, _snowman, this, _snowman, _snowman, _snowman, _snowman, _snowman, _snowman, _snowman, _snowman);
   }

   public final float h() {
      return this.m;
   }

   public final float i() {
      return this.j.e;
   }

   public final float j() {
      return this.n;
   }

   public final float k() {
      return this.j.c;
   }

   public bsz l() {
      return this.p;
   }

   public final int m() {
      return this.p.b();
   }

   public final int n() {
      return this.p.c();
   }

   public Optional<bsu> o() {
      return this.p.h();
   }

   public Optional<adp> p() {
      return this.p.i();
   }

   public Optional<bst> q() {
      return this.p.j();
   }

   public Optional<bss> r() {
      return this.p.k();
   }

   public Optional<adn> s() {
      return this.p.l();
   }

   public final bsv.b t() {
      return this.o;
   }

   @Override
   public String toString() {
      vk _snowman = hk.i.b(this);
      return _snowman == null ? super.toString() : _snowman.toString();
   }

   public static class a {
      @Nullable
      private bsv.e a;
      @Nullable
      private bsv.b b;
      @Nullable
      private Float c;
      @Nullable
      private Float d;
      @Nullable
      private Float e;
      private bsv.f f = bsv.f.a;
      @Nullable
      private Float g;
      @Nullable
      private bsz h;
      @Nullable
      private btg i;
      @Nullable
      private bsw j;

      public a() {
      }

      public bsv.a a(bsv.e var1) {
         this.a = _snowman;
         return this;
      }

      public bsv.a a(bsv.b var1) {
         this.b = _snowman;
         return this;
      }

      public bsv.a a(float var1) {
         this.c = _snowman;
         return this;
      }

      public bsv.a b(float var1) {
         this.d = _snowman;
         return this;
      }

      public bsv.a c(float var1) {
         this.e = _snowman;
         return this;
      }

      public bsv.a d(float var1) {
         this.g = _snowman;
         return this;
      }

      public bsv.a a(bsz var1) {
         this.h = _snowman;
         return this;
      }

      public bsv.a a(btg var1) {
         this.i = _snowman;
         return this;
      }

      public bsv.a a(bsw var1) {
         this.j = _snowman;
         return this;
      }

      public bsv.a a(bsv.f var1) {
         this.f = _snowman;
         return this;
      }

      public bsv a() {
         if (this.a != null
            && this.b != null
            && this.c != null
            && this.d != null
            && this.e != null
            && this.g != null
            && this.h != null
            && this.i != null
            && this.j != null) {
            return new bsv(new bsv.d(this.a, this.e, this.f, this.g), this.b, this.c, this.d, this.h, this.j, this.i);
         } else {
            throw new IllegalStateException("You are missing parameters to build a proper biome\n" + this);
         }
      }

      @Override
      public String toString() {
         return "BiomeBuilder{\nprecipitation="
            + this.a
            + ",\nbiomeCategory="
            + this.b
            + ",\ndepth="
            + this.c
            + ",\nscale="
            + this.d
            + ",\ntemperature="
            + this.e
            + ",\ntemperatureModifier="
            + this.f
            + ",\ndownfall="
            + this.g
            + ",\nspecialEffects="
            + this.h
            + ",\nmobSpawnSettings="
            + this.i
            + ",\ngenerationSettings="
            + this.j
            + ",\n"
            + '}';
      }
   }

   public static enum b implements afs {
      a("none"),
      b("taiga"),
      c("extreme_hills"),
      d("jungle"),
      e("mesa"),
      f("plains"),
      g("savanna"),
      h("icy"),
      i("the_end"),
      j("beach"),
      k("forest"),
      l("ocean"),
      m("desert"),
      n("river"),
      o("swamp"),
      p("mushroom"),
      q("nether");

      public static final Codec<bsv.b> r = afs.a(bsv.b::values, bsv.b::a);
      private static final Map<String, bsv.b> s = Arrays.stream(values()).collect(Collectors.toMap(bsv.b::b, var0 -> (bsv.b)var0));
      private final String t;

      private b(String var3) {
         this.t = _snowman;
      }

      public String b() {
         return this.t;
      }

      public static bsv.b a(String var0) {
         return s.get(_snowman);
      }

      @Override
      public String a() {
         return this.t;
      }
   }

   public static class c {
      public static final Codec<bsv.c> a = RecordCodecBuilder.create(
         var0 -> var0.group(
                  Codec.floatRange(-2.0F, 2.0F).fieldOf("temperature").forGetter(var0x -> var0x.b),
                  Codec.floatRange(-2.0F, 2.0F).fieldOf("humidity").forGetter(var0x -> var0x.c),
                  Codec.floatRange(-2.0F, 2.0F).fieldOf("altitude").forGetter(var0x -> var0x.d),
                  Codec.floatRange(-2.0F, 2.0F).fieldOf("weirdness").forGetter(var0x -> var0x.e),
                  Codec.floatRange(0.0F, 1.0F).fieldOf("offset").forGetter(var0x -> var0x.f)
               )
               .apply(var0, bsv.c::new)
      );
      private final float b;
      private final float c;
      private final float d;
      private final float e;
      private final float f;

      public c(float var1, float var2, float var3, float var4, float var5) {
         this.b = _snowman;
         this.c = _snowman;
         this.d = _snowman;
         this.e = _snowman;
         this.f = _snowman;
      }

      @Override
      public boolean equals(Object var1) {
         if (this == _snowman) {
            return true;
         } else if (_snowman != null && this.getClass() == _snowman.getClass()) {
            bsv.c _snowman = (bsv.c)_snowman;
            if (Float.compare(_snowman.b, this.b) != 0) {
               return false;
            } else if (Float.compare(_snowman.c, this.c) != 0) {
               return false;
            } else {
               return Float.compare(_snowman.d, this.d) != 0 ? false : Float.compare(_snowman.e, this.e) == 0;
            }
         } else {
            return false;
         }
      }

      @Override
      public int hashCode() {
         int _snowman = this.b != 0.0F ? Float.floatToIntBits(this.b) : 0;
         _snowman = 31 * _snowman + (this.c != 0.0F ? Float.floatToIntBits(this.c) : 0);
         _snowman = 31 * _snowman + (this.d != 0.0F ? Float.floatToIntBits(this.d) : 0);
         return 31 * _snowman + (this.e != 0.0F ? Float.floatToIntBits(this.e) : 0);
      }

      public float a(bsv.c var1) {
         return (this.b - _snowman.b) * (this.b - _snowman.b)
            + (this.c - _snowman.c) * (this.c - _snowman.c)
            + (this.d - _snowman.d) * (this.d - _snowman.d)
            + (this.e - _snowman.e) * (this.e - _snowman.e)
            + (this.f - _snowman.f) * (this.f - _snowman.f);
      }
   }

   static class d {
      public static final MapCodec<bsv.d> a = RecordCodecBuilder.mapCodec(
         var0 -> var0.group(
                  bsv.e.d.fieldOf("precipitation").forGetter(var0x -> var0x.b),
                  Codec.FLOAT.fieldOf("temperature").forGetter(var0x -> var0x.c),
                  bsv.f.c.optionalFieldOf("temperature_modifier", bsv.f.a).forGetter(var0x -> var0x.d),
                  Codec.FLOAT.fieldOf("downfall").forGetter(var0x -> var0x.e)
               )
               .apply(var0, bsv.d::new)
      );
      private final bsv.e b;
      private final float c;
      private final bsv.f d;
      private final float e;

      private d(bsv.e var1, float var2, bsv.f var3, float var4) {
         this.b = _snowman;
         this.c = _snowman;
         this.d = _snowman;
         this.e = _snowman;
      }
   }

   public static enum e implements afs {
      a("none"),
      b("rain"),
      c("snow");

      public static final Codec<bsv.e> d = afs.a(bsv.e::values, bsv.e::a);
      private static final Map<String, bsv.e> e = Arrays.stream(values()).collect(Collectors.toMap(bsv.e::b, var0 -> (bsv.e)var0));
      private final String f;

      private e(String var3) {
         this.f = _snowman;
      }

      public String b() {
         return this.f;
      }

      public static bsv.e a(String var0) {
         return e.get(_snowman);
      }

      @Override
      public String a() {
         return this.f;
      }
   }

   public static enum f implements afs {
      a("none") {
         @Override
         public float a(fx var1, float var2) {
            return _snowman;
         }
      },
      b("frozen") {
         @Override
         public float a(fx var1, float var2) {
            double _snowman = bsv.i.a((double)_snowman.u() * 0.05, (double)_snowman.w() * 0.05, false) * 7.0;
            double _snowmanx = bsv.f.a((double)_snowman.u() * 0.2, (double)_snowman.w() * 0.2, false);
            double _snowmanxx = _snowman + _snowmanx;
            if (_snowmanxx < 0.3) {
               double _snowmanxxx = bsv.f.a((double)_snowman.u() * 0.09, (double)_snowman.w() * 0.09, false);
               if (_snowmanxxx < 0.8) {
                  return 0.2F;
               }
            }

            return _snowman;
         }
      };

      private final String d;
      public static final Codec<bsv.f> c = afs.a(bsv.f::values, bsv.f::a);
      private static final Map<String, bsv.f> e = Arrays.stream(values()).collect(Collectors.toMap(bsv.f::b, var0 -> (bsv.f)var0));

      public abstract float a(fx var1, float var2);

      private f(String var3) {
         this.d = _snowman;
      }

      public String b() {
         return this.d;
      }

      @Override
      public String a() {
         return this.d;
      }

      public static bsv.f a(String var0) {
         return e.get(_snowman);
      }
   }
}
