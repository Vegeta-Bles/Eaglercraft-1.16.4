import com.google.common.collect.Lists;
import java.util.List;
import java.util.Random;

public class crn {
   private static final vk[] a = new vk[]{
      new vk("underwater_ruin/warm_1"),
      new vk("underwater_ruin/warm_2"),
      new vk("underwater_ruin/warm_3"),
      new vk("underwater_ruin/warm_4"),
      new vk("underwater_ruin/warm_5"),
      new vk("underwater_ruin/warm_6"),
      new vk("underwater_ruin/warm_7"),
      new vk("underwater_ruin/warm_8")
   };
   private static final vk[] b = new vk[]{
      new vk("underwater_ruin/brick_1"),
      new vk("underwater_ruin/brick_2"),
      new vk("underwater_ruin/brick_3"),
      new vk("underwater_ruin/brick_4"),
      new vk("underwater_ruin/brick_5"),
      new vk("underwater_ruin/brick_6"),
      new vk("underwater_ruin/brick_7"),
      new vk("underwater_ruin/brick_8")
   };
   private static final vk[] c = new vk[]{
      new vk("underwater_ruin/cracked_1"),
      new vk("underwater_ruin/cracked_2"),
      new vk("underwater_ruin/cracked_3"),
      new vk("underwater_ruin/cracked_4"),
      new vk("underwater_ruin/cracked_5"),
      new vk("underwater_ruin/cracked_6"),
      new vk("underwater_ruin/cracked_7"),
      new vk("underwater_ruin/cracked_8")
   };
   private static final vk[] d = new vk[]{
      new vk("underwater_ruin/mossy_1"),
      new vk("underwater_ruin/mossy_2"),
      new vk("underwater_ruin/mossy_3"),
      new vk("underwater_ruin/mossy_4"),
      new vk("underwater_ruin/mossy_5"),
      new vk("underwater_ruin/mossy_6"),
      new vk("underwater_ruin/mossy_7"),
      new vk("underwater_ruin/mossy_8")
   };
   private static final vk[] e = new vk[]{
      new vk("underwater_ruin/big_brick_1"),
      new vk("underwater_ruin/big_brick_2"),
      new vk("underwater_ruin/big_brick_3"),
      new vk("underwater_ruin/big_brick_8")
   };
   private static final vk[] f = new vk[]{
      new vk("underwater_ruin/big_mossy_1"),
      new vk("underwater_ruin/big_mossy_2"),
      new vk("underwater_ruin/big_mossy_3"),
      new vk("underwater_ruin/big_mossy_8")
   };
   private static final vk[] g = new vk[]{
      new vk("underwater_ruin/big_cracked_1"),
      new vk("underwater_ruin/big_cracked_2"),
      new vk("underwater_ruin/big_cracked_3"),
      new vk("underwater_ruin/big_cracked_8")
   };
   private static final vk[] h = new vk[]{
      new vk("underwater_ruin/big_warm_4"), new vk("underwater_ruin/big_warm_5"), new vk("underwater_ruin/big_warm_6"), new vk("underwater_ruin/big_warm_7")
   };

   private static vk a(Random var0) {
      return x.a(a, _snowman);
   }

   private static vk b(Random var0) {
      return x.a(h, _snowman);
   }

   public static void a(csw var0, fx var1, bzm var2, List<cru> var3, Random var4, cmi var5) {
      boolean _snowman = _snowman.nextFloat() <= _snowman.c;
      float _snowmanx = _snowman ? 0.9F : 0.8F;
      a(_snowman, _snowman, _snowman, _snowman, _snowman, _snowman, _snowman, _snowmanx);
      if (_snowman && _snowman.nextFloat() <= _snowman.d) {
         a(_snowman, _snowman, _snowman, _snowman, _snowman, _snowman);
      }
   }

   private static void a(csw var0, Random var1, bzm var2, fx var3, cmi var4, List<cru> var5) {
      int _snowman = _snowman.u();
      int _snowmanx = _snowman.w();
      fx _snowmanxx = ctb.a(new fx(15, 0, 15), byg.a, _snowman, fx.b).b(_snowman, 0, _snowmanx);
      cra _snowmanxxx = cra.a(_snowman, 0, _snowmanx, _snowmanxx.u(), 0, _snowmanxx.w());
      fx _snowmanxxxx = new fx(Math.min(_snowman, _snowmanxx.u()), 0, Math.min(_snowmanx, _snowmanxx.w()));
      List<fx> _snowmanxxxxx = a(_snowman, _snowmanxxxx.u(), _snowmanxxxx.w());
      int _snowmanxxxxxx = afm.a(_snowman, 4, 8);

      for (int _snowmanxxxxxxx = 0; _snowmanxxxxxxx < _snowmanxxxxxx; _snowmanxxxxxxx++) {
         if (!_snowmanxxxxx.isEmpty()) {
            int _snowmanxxxxxxxx = _snowman.nextInt(_snowmanxxxxx.size());
            fx _snowmanxxxxxxxxx = _snowmanxxxxx.remove(_snowmanxxxxxxxx);
            int _snowmanxxxxxxxxxx = _snowmanxxxxxxxxx.u();
            int _snowmanxxxxxxxxxxx = _snowmanxxxxxxxxx.w();
            bzm _snowmanxxxxxxxxxxxx = bzm.a(_snowman);
            fx _snowmanxxxxxxxxxxxxx = ctb.a(new fx(5, 0, 6), byg.a, _snowmanxxxxxxxxxxxx, fx.b).b(_snowmanxxxxxxxxxx, 0, _snowmanxxxxxxxxxxx);
            cra _snowmanxxxxxxxxxxxxxx = cra.a(_snowmanxxxxxxxxxx, 0, _snowmanxxxxxxxxxxx, _snowmanxxxxxxxxxxxxx.u(), 0, _snowmanxxxxxxxxxxxxx.w());
            if (!_snowmanxxxxxxxxxxxxxx.b(_snowmanxxx)) {
               a(_snowman, _snowmanxxxxxxxxx, _snowmanxxxxxxxxxxxx, _snowman, _snowman, _snowman, false, 0.8F);
            }
         }
      }
   }

   private static List<fx> a(Random var0, int var1, int var2) {
      List<fx> _snowman = Lists.newArrayList();
      _snowman.add(new fx(_snowman - 16 + afm.a(_snowman, 1, 8), 90, _snowman + 16 + afm.a(_snowman, 1, 7)));
      _snowman.add(new fx(_snowman - 16 + afm.a(_snowman, 1, 8), 90, _snowman + afm.a(_snowman, 1, 7)));
      _snowman.add(new fx(_snowman - 16 + afm.a(_snowman, 1, 8), 90, _snowman - 16 + afm.a(_snowman, 4, 8)));
      _snowman.add(new fx(_snowman + afm.a(_snowman, 1, 7), 90, _snowman + 16 + afm.a(_snowman, 1, 7)));
      _snowman.add(new fx(_snowman + afm.a(_snowman, 1, 7), 90, _snowman - 16 + afm.a(_snowman, 4, 6)));
      _snowman.add(new fx(_snowman + 16 + afm.a(_snowman, 1, 7), 90, _snowman + 16 + afm.a(_snowman, 3, 8)));
      _snowman.add(new fx(_snowman + 16 + afm.a(_snowman, 1, 7), 90, _snowman + afm.a(_snowman, 1, 7)));
      _snowman.add(new fx(_snowman + 16 + afm.a(_snowman, 1, 7), 90, _snowman - 16 + afm.a(_snowman, 4, 8)));
      return _snowman;
   }

   private static void a(csw var0, fx var1, bzm var2, List<cru> var3, Random var4, cmi var5, boolean var6, float var7) {
      if (_snowman.b == crm.b.a) {
         vk _snowman = _snowman ? b(_snowman) : a(_snowman);
         _snowman.add(new crn.a(_snowman, _snowman, _snowman, _snowman, _snowman, _snowman.b, _snowman));
      } else if (_snowman.b == crm.b.b) {
         vk[] _snowman = _snowman ? e : b;
         vk[] _snowmanx = _snowman ? g : c;
         vk[] _snowmanxx = _snowman ? f : d;
         int _snowmanxxx = _snowman.nextInt(_snowman.length);
         _snowman.add(new crn.a(_snowman, _snowman[_snowmanxxx], _snowman, _snowman, _snowman, _snowman.b, _snowman));
         _snowman.add(new crn.a(_snowman, _snowmanx[_snowmanxxx], _snowman, _snowman, 0.7F, _snowman.b, _snowman));
         _snowman.add(new crn.a(_snowman, _snowmanxx[_snowmanxxx], _snowman, _snowman, 0.5F, _snowman.b, _snowman));
      }
   }

   public static class a extends crx {
      private final crm.b d;
      private final float e;
      private final vk f;
      private final bzm g;
      private final boolean h;

      public a(csw var1, vk var2, fx var3, bzm var4, float var5, crm.b var6, boolean var7) {
         super(clb.H, 0);
         this.f = _snowman;
         this.c = _snowman;
         this.g = _snowman;
         this.e = _snowman;
         this.d = _snowman;
         this.h = _snowman;
         this.a(_snowman);
      }

      public a(csw var1, md var2) {
         super(clb.H, _snowman);
         this.f = new vk(_snowman.l("Template"));
         this.g = bzm.valueOf(_snowman.l("Rot"));
         this.e = _snowman.j("Integrity");
         this.d = crm.b.valueOf(_snowman.l("BiomeType"));
         this.h = _snowman.q("IsLarge");
         this.a(_snowman);
      }

      private void a(csw var1) {
         ctb _snowman = _snowman.a(this.f);
         csx _snowmanx = new csx().a(this.g).a(byg.a).a(cse.d);
         this.a(_snowman, this.c, _snowmanx);
      }

      @Override
      protected void a(md var1) {
         super.a(_snowman);
         _snowman.a("Template", this.f.toString());
         _snowman.a("Rot", this.g.name());
         _snowman.a("Integrity", this.e);
         _snowman.a("BiomeType", this.d.toString());
         _snowman.a("IsLarge", this.h);
      }

      @Override
      protected void a(String var1, fx var2, bsk var3, Random var4, cra var5) {
         if ("chest".equals(_snowman)) {
            _snowman.a(_snowman, bup.bR.n().a(bve.d, Boolean.valueOf(_snowman.b(_snowman).a(aef.b))), 2);
            ccj _snowman = _snowman.c(_snowman);
            if (_snowman instanceof ccn) {
               ((ccn)_snowman).a(this.h ? cyq.F : cyq.E, _snowman.nextLong());
            }
         } else if ("drowned".equals(_snowman)) {
            bde _snowman = aqe.q.a(_snowman.E());
            _snowman.es();
            _snowman.a(_snowman, 0.0F, 0.0F);
            _snowman.a(_snowman, _snowman.d(_snowman), aqp.d, null, null);
            _snowman.l(_snowman);
            if (_snowman.v() > _snowman.t_()) {
               _snowman.a(_snowman, bup.a.n(), 2);
            } else {
               _snowman.a(_snowman, bup.A.n(), 2);
            }
         }
      }

      @Override
      public boolean a(bsr var1, bsn var2, cfy var3, Random var4, cra var5, brd var6, fx var7) {
         this.b.b().a(new csg(this.e)).a(cse.d);
         int _snowman = _snowman.a(chn.a.c, this.c.u(), this.c.w());
         this.c = new fx(this.c.u(), _snowman, this.c.w());
         fx _snowmanx = ctb.a(new fx(this.a.a().u() - 1, 0, this.a.a().w() - 1), byg.a, this.g, fx.b).a(this.c);
         this.c = new fx(this.c.u(), this.a(this.c, _snowman, _snowmanx), this.c.w());
         return super.a(_snowman, _snowman, _snowman, _snowman, _snowman, _snowman, _snowman);
      }

      private int a(fx var1, brc var2, fx var3) {
         int _snowman = _snowman.v();
         int _snowmanx = 512;
         int _snowmanxx = _snowman - 1;
         int _snowmanxxx = 0;

         for (fx _snowmanxxxx : fx.a(_snowman, _snowman)) {
            int _snowmanxxxxx = _snowmanxxxx.u();
            int _snowmanxxxxxx = _snowmanxxxx.w();
            int _snowmanxxxxxxx = _snowman.v() - 1;
            fx.a _snowmanxxxxxxxx = new fx.a(_snowmanxxxxx, _snowmanxxxxxxx, _snowmanxxxxxx);
            ceh _snowmanxxxxxxxxx = _snowman.d_(_snowmanxxxxxxxx);

            for (cux _snowmanxxxxxxxxxx = _snowman.b(_snowmanxxxxxxxx);
               (_snowmanxxxxxxxxx.g() || _snowmanxxxxxxxxxx.a(aef.b) || _snowmanxxxxxxxxx.b().a(aed.U)) && _snowmanxxxxxxx > 1;
               _snowmanxxxxxxxxxx = _snowman.b(_snowmanxxxxxxxx)
            ) {
               _snowmanxxxxxxxx.d(_snowmanxxxxx, --_snowmanxxxxxxx, _snowmanxxxxxx);
               _snowmanxxxxxxxxx = _snowman.d_(_snowmanxxxxxxxx);
            }

            _snowmanx = Math.min(_snowmanx, _snowmanxxxxxxx);
            if (_snowmanxxxxxxx < _snowmanxx - 2) {
               _snowmanxxx++;
            }
         }

         int _snowmanxxxx = Math.abs(_snowman.u() - _snowman.u());
         if (_snowmanxx - _snowmanx > 2 && _snowmanxxx > _snowmanxxxx - 2) {
            _snowman = _snowmanx + 1;
         }

         return _snowman;
      }
   }
}
