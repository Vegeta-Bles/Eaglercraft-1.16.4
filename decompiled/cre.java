import com.google.common.collect.ImmutableMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

public class cre {
   private static final vk a = new vk("igloo/top");
   private static final vk b = new vk("igloo/middle");
   private static final vk c = new vk("igloo/bottom");
   private static final Map<vk, fx> d = ImmutableMap.of(a, new fx(3, 5, 5), b, new fx(1, 3, 1), c, new fx(3, 6, 7));
   private static final Map<vk, fx> e = ImmutableMap.of(a, fx.b, b, new fx(2, -3, 4), c, new fx(0, -3, -2));

   public static void a(csw var0, fx var1, bzm var2, List<cru> var3, Random var4) {
      if (_snowman.nextDouble() < 0.5) {
         int _snowman = _snowman.nextInt(8) + 4;
         _snowman.add(new cre.a(_snowman, c, _snowman, _snowman, _snowman * 3));

         for (int _snowmanx = 0; _snowmanx < _snowman - 1; _snowmanx++) {
            _snowman.add(new cre.a(_snowman, b, _snowman, _snowman, _snowmanx * 3));
         }
      }

      _snowman.add(new cre.a(_snowman, a, _snowman, _snowman, 0));
   }

   public static class a extends crx {
      private final vk d;
      private final bzm e;

      public a(csw var1, vk var2, fx var3, bzm var4, int var5) {
         super(clb.I, 0);
         this.d = _snowman;
         fx _snowman = cre.e.get(_snowman);
         this.c = _snowman.b(_snowman.u(), _snowman.v() - _snowman, _snowman.w());
         this.e = _snowman;
         this.a(_snowman);
      }

      public a(csw var1, md var2) {
         super(clb.I, _snowman);
         this.d = new vk(_snowman.l("Template"));
         this.e = bzm.valueOf(_snowman.l("Rot"));
         this.a(_snowman);
      }

      private void a(csw var1) {
         ctb _snowman = _snowman.a(this.d);
         csx _snowmanx = new csx().a(this.e).a(byg.a).a(cre.d.get(this.d)).a(cse.b);
         this.a(_snowman, this.c, _snowmanx);
      }

      @Override
      protected void a(md var1) {
         super.a(_snowman);
         _snowman.a("Template", this.d.toString());
         _snowman.a("Rot", this.e.name());
      }

      @Override
      protected void a(String var1, fx var2, bsk var3, Random var4, cra var5) {
         if ("chest".equals(_snowman)) {
            _snowman.a(_snowman, bup.a.n(), 3);
            ccj _snowman = _snowman.c(_snowman.c());
            if (_snowman instanceof ccn) {
               ((ccn)_snowman).a(cyq.C, _snowman.nextLong());
            }
         }
      }

      @Override
      public boolean a(bsr var1, bsn var2, cfy var3, Random var4, cra var5, brd var6, fx var7) {
         csx _snowman = new csx().a(this.e).a(byg.a).a(cre.d.get(this.d)).a(cse.b);
         fx _snowmanx = cre.e.get(this.d);
         fx _snowmanxx = this.c.a(ctb.a(_snowman, new fx(3 - _snowmanx.u(), 0, 0 - _snowmanx.w())));
         int _snowmanxxx = _snowman.a(chn.a.a, _snowmanxx.u(), _snowmanxx.w());
         fx _snowmanxxxx = this.c;
         this.c = this.c.b(0, _snowmanxxx - 90 - 1, 0);
         boolean _snowmanxxxxx = super.a(_snowman, _snowman, _snowman, _snowman, _snowman, _snowman, _snowman);
         if (this.d.equals(cre.a)) {
            fx _snowmanxxxxxx = this.c.a(ctb.a(_snowman, new fx(3, 0, 5)));
            ceh _snowmanxxxxxxx = _snowman.d_(_snowmanxxxxxx.c());
            if (!_snowmanxxxxxxx.g() && !_snowmanxxxxxxx.a(bup.cg)) {
               _snowman.a(_snowmanxxxxxx, bup.cE.n(), 3);
            }
         }

         this.c = _snowmanxxxx;
         return _snowmanxxxxx;
      }
   }
}
