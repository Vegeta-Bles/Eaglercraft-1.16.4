import com.google.common.collect.Lists;
import com.mojang.serialization.Codec;
import com.mojang.serialization.Dynamic;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.stream.Collectors;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class crp extends crx {
   private static final Logger d = LogManager.getLogger();
   private final vk e;
   private final bzm f;
   private final byg g;
   private final crp.b h;
   private final crp.a i;

   public crp(fx var1, crp.b var2, crp.a var3, vk var4, ctb var5, bzm var6, byg var7, fx var8) {
      super(clb.J, 0);
      this.c = _snowman;
      this.e = _snowman;
      this.f = _snowman;
      this.g = _snowman;
      this.h = _snowman;
      this.i = _snowman;
      this.a(_snowman, _snowman);
   }

   public crp(csw var1, md var2) {
      super(clb.J, _snowman);
      this.e = new vk(_snowman.l("Template"));
      this.f = bzm.valueOf(_snowman.l("Rotation"));
      this.g = byg.valueOf(_snowman.l("Mirror"));
      this.h = crp.b.a(_snowman.l("VerticalPlacement"));
      this.i = (crp.a)crp.a.a.parse(new Dynamic(mo.a, _snowman.c("Properties"))).getOrThrow(true, d::error);
      ctb _snowman = _snowman.a(this.e);
      this.a(_snowman, new fx(_snowman.a().u() / 2, 0, _snowman.a().w() / 2));
   }

   @Override
   protected void a(md var1) {
      super.a(_snowman);
      _snowman.a("Template", this.e.toString());
      _snowman.a("Rotation", this.f.name());
      _snowman.a("Mirror", this.g.name());
      _snowman.a("VerticalPlacement", this.h.a());
      crp.a.a.encodeStart(mo.a, this.i).resultOrPartial(d::error).ifPresent(var1x -> _snowman.a("Properties", var1x));
   }

   private void a(ctb var1, fx var2) {
      cse _snowman = this.i.d ? cse.b : cse.d;
      List<csq> _snowmanx = Lists.newArrayList();
      _snowmanx.add(a(bup.bE, 0.3F, bup.a));
      _snowmanx.add(this.c());
      if (!this.i.b) {
         _snowmanx.add(a(bup.cL, 0.07F, bup.iJ));
      }

      csx _snowmanxx = new csx().a(this.f).a(this.g).a(_snowman).a(_snowman).a(new cst(_snowmanx)).a(new csd(this.i.c)).a(new csk());
      if (this.i.g) {
         _snowmanxx.a(csc.b);
      }

      this.a(_snowman, this.c, _snowmanxx);
   }

   private csq c() {
      if (this.h == crp.b.c) {
         return a(bup.B, bup.iJ);
      } else {
         return this.i.b ? a(bup.B, bup.cL) : a(bup.B, 0.2F, bup.iJ);
      }
   }

   @Override
   public boolean a(bsr var1, bsn var2, cfy var3, Random var4, cra var5, brd var6, fx var7) {
      if (!_snowman.b(this.c)) {
         return true;
      } else {
         _snowman.c(this.a.b(this.b, this.c));
         boolean _snowman = super.a(_snowman, _snowman, _snowman, _snowman, _snowman, _snowman, _snowman);
         this.b(_snowman, _snowman);
         this.a(_snowman, _snowman);
         if (this.i.f || this.i.e) {
            fx.a(this.g()).forEach(var3x -> {
               if (this.i.f) {
                  this.a(_snowman, (bry)_snowman, var3x);
               }

               if (this.i.e) {
                  this.b(_snowman, _snowman, var3x);
               }
            });
         }

         return _snowman;
      }
   }

   @Override
   protected void a(String var1, fx var2, bsk var3, Random var4, cra var5) {
   }

   private void a(Random var1, bry var2, fx var3) {
      ceh _snowman = _snowman.d_(_snowman);
      if (!_snowman.g() && !_snowman.a(bup.dP)) {
         gc _snowmanx = gc.c.a.a(_snowman);
         fx _snowmanxx = _snowman.a(_snowmanx);
         ceh _snowmanxxx = _snowman.d_(_snowmanxx);
         if (_snowmanxxx.g()) {
            if (buo.a(_snowman.k(_snowman, _snowman), _snowmanx)) {
               cey _snowmanxxxx = cbi.a(_snowmanx.f());
               _snowman.a(_snowmanxx, bup.dP.n().a(_snowmanxxxx, Boolean.valueOf(true)), 3);
            }
         }
      }
   }

   private void b(Random var1, bry var2, fx var3) {
      if (_snowman.nextFloat() < 0.5F && _snowman.d_(_snowman).a(bup.cL) && _snowman.d_(_snowman.b()).g()) {
         _snowman.a(_snowman.b(), bup.ak.n().a(bxx.b, Boolean.valueOf(true)), 3);
      }
   }

   private void a(Random var1, bry var2) {
      for (int _snowman = this.n.a + 1; _snowman < this.n.d; _snowman++) {
         for (int _snowmanx = this.n.c + 1; _snowmanx < this.n.f; _snowmanx++) {
            fx _snowmanxx = new fx(_snowman, this.n.b, _snowmanx);
            if (_snowman.d_(_snowmanxx).a(bup.cL)) {
               this.c(_snowman, _snowman, _snowmanxx.c());
            }
         }
      }
   }

   private void c(Random var1, bry var2, fx var3) {
      fx.a _snowman = _snowman.i();
      this.d(_snowman, _snowman, _snowman);
      int _snowmanx = 8;

      while (_snowmanx > 0 && _snowman.nextFloat() < 0.5F) {
         _snowman.c(gc.a);
         _snowmanx--;
         this.d(_snowman, _snowman, _snowman);
      }
   }

   private void b(Random var1, bry var2) {
      boolean _snowman = this.h == crp.b.a || this.h == crp.b.c;
      gr _snowmanx = this.n.g();
      int _snowmanxx = _snowmanx.u();
      int _snowmanxxx = _snowmanx.w();
      float[] _snowmanxxxx = new float[]{1.0F, 1.0F, 1.0F, 1.0F, 1.0F, 1.0F, 1.0F, 0.9F, 0.9F, 0.8F, 0.7F, 0.6F, 0.4F, 0.2F};
      int _snowmanxxxxx = _snowmanxxxx.length;
      int _snowmanxxxxxx = (this.n.d() + this.n.f()) / 2;
      int _snowmanxxxxxxx = _snowman.nextInt(Math.max(1, 8 - _snowmanxxxxxx / 2));
      int _snowmanxxxxxxxx = 3;
      fx.a _snowmanxxxxxxxxx = fx.b.i();

      for (int _snowmanxxxxxxxxxx = _snowmanxx - _snowmanxxxxx; _snowmanxxxxxxxxxx <= _snowmanxx + _snowmanxxxxx; _snowmanxxxxxxxxxx++) {
         for (int _snowmanxxxxxxxxxxx = _snowmanxxx - _snowmanxxxxx; _snowmanxxxxxxxxxxx <= _snowmanxxx + _snowmanxxxxx; _snowmanxxxxxxxxxxx++) {
            int _snowmanxxxxxxxxxxxx = Math.abs(_snowmanxxxxxxxxxx - _snowmanxx) + Math.abs(_snowmanxxxxxxxxxxx - _snowmanxxx);
            int _snowmanxxxxxxxxxxxxx = Math.max(0, _snowmanxxxxxxxxxxxx + _snowmanxxxxxxx);
            if (_snowmanxxxxxxxxxxxxx < _snowmanxxxxx) {
               float _snowmanxxxxxxxxxxxxxx = _snowmanxxxx[_snowmanxxxxxxxxxxxxx];
               if (_snowman.nextDouble() < (double)_snowmanxxxxxxxxxxxxxx) {
                  int _snowmanxxxxxxxxxxxxxxx = a(_snowman, _snowmanxxxxxxxxxx, _snowmanxxxxxxxxxxx, this.h);
                  int _snowmanxxxxxxxxxxxxxxxx = _snowman ? _snowmanxxxxxxxxxxxxxxx : Math.min(this.n.b, _snowmanxxxxxxxxxxxxxxx);
                  _snowmanxxxxxxxxx.d(_snowmanxxxxxxxxxx, _snowmanxxxxxxxxxxxxxxxx, _snowmanxxxxxxxxxxx);
                  if (Math.abs(_snowmanxxxxxxxxxxxxxxxx - this.n.b) <= 3 && this.a(_snowman, _snowmanxxxxxxxxx)) {
                     this.d(_snowman, _snowman, _snowmanxxxxxxxxx);
                     if (this.i.e) {
                        this.b(_snowman, _snowman, _snowmanxxxxxxxxx);
                     }

                     this.c(_snowman, _snowman, _snowmanxxxxxxxxx.c());
                  }
               }
            }
         }
      }
   }

   private boolean a(bry var1, fx var2) {
      ceh _snowman = _snowman.d_(_snowman);
      return !_snowman.a(bup.a) && !_snowman.a(bup.bK) && !_snowman.a(bup.bR) && (this.h == crp.b.f || !_snowman.a(bup.B));
   }

   private void d(Random var1, bry var2, fx var3) {
      if (!this.i.b && _snowman.nextFloat() < 0.07F) {
         _snowman.a(_snowman, bup.iJ.n(), 3);
      } else {
         _snowman.a(_snowman, bup.cL.n(), 3);
      }
   }

   private static int a(bry var0, int var1, int var2, crp.b var3) {
      return _snowman.a(a(_snowman), _snowman, _snowman) - 1;
   }

   public static chn.a a(crp.b var0) {
      return _snowman == crp.b.c ? chn.a.c : chn.a.a;
   }

   private static csq a(buo var0, float var1, buo var2) {
      return new csq(new csr(_snowman, _snowman), csa.b, _snowman.n());
   }

   private static csq a(buo var0, buo var1) {
      return new csq(new csf(_snowman), csa.b, _snowman.n());
   }

   public static class a {
      public static final Codec<crp.a> a = RecordCodecBuilder.create(
         var0 -> var0.group(
                  Codec.BOOL.fieldOf("cold").forGetter(var0x -> var0x.b),
                  Codec.FLOAT.fieldOf("mossiness").forGetter(var0x -> var0x.c),
                  Codec.BOOL.fieldOf("air_pocket").forGetter(var0x -> var0x.d),
                  Codec.BOOL.fieldOf("overgrown").forGetter(var0x -> var0x.e),
                  Codec.BOOL.fieldOf("vines").forGetter(var0x -> var0x.f),
                  Codec.BOOL.fieldOf("replace_with_blackstone").forGetter(var0x -> var0x.g)
               )
               .apply(var0, crp.a::new)
      );
      public boolean b;
      public float c = 0.2F;
      public boolean d;
      public boolean e;
      public boolean f;
      public boolean g;

      public a() {
      }

      public <T> a(boolean var1, float var2, boolean var3, boolean var4, boolean var5, boolean var6) {
         this.b = _snowman;
         this.c = _snowman;
         this.d = _snowman;
         this.e = _snowman;
         this.f = _snowman;
         this.g = _snowman;
      }
   }

   public static enum b {
      a("on_land_surface"),
      b("partly_buried"),
      c("on_ocean_floor"),
      d("in_mountain"),
      e("underground"),
      f("in_nether");

      private static final Map<String, crp.b> g = Arrays.stream(values()).collect(Collectors.toMap(crp.b::a, var0 -> (crp.b)var0));
      private final String h;

      private b(String var3) {
         this.h = _snowman;
      }

      public String a() {
         return this.h;
      }

      public static crp.b a(String var0) {
         return g.get(_snowman);
      }
   }
}
