import java.util.List;
import java.util.Random;

public class crr {
   private static final fx a = new fx(4, 0, 15);
   private static final vk[] b = new vk[]{
      new vk("shipwreck/with_mast"),
      new vk("shipwreck/sideways_full"),
      new vk("shipwreck/sideways_fronthalf"),
      new vk("shipwreck/sideways_backhalf"),
      new vk("shipwreck/rightsideup_full"),
      new vk("shipwreck/rightsideup_fronthalf"),
      new vk("shipwreck/rightsideup_backhalf"),
      new vk("shipwreck/with_mast_degraded"),
      new vk("shipwreck/rightsideup_full_degraded"),
      new vk("shipwreck/rightsideup_fronthalf_degraded"),
      new vk("shipwreck/rightsideup_backhalf_degraded")
   };
   private static final vk[] c = new vk[]{
      new vk("shipwreck/with_mast"),
      new vk("shipwreck/upsidedown_full"),
      new vk("shipwreck/upsidedown_fronthalf"),
      new vk("shipwreck/upsidedown_backhalf"),
      new vk("shipwreck/sideways_full"),
      new vk("shipwreck/sideways_fronthalf"),
      new vk("shipwreck/sideways_backhalf"),
      new vk("shipwreck/rightsideup_full"),
      new vk("shipwreck/rightsideup_fronthalf"),
      new vk("shipwreck/rightsideup_backhalf"),
      new vk("shipwreck/with_mast_degraded"),
      new vk("shipwreck/upsidedown_full_degraded"),
      new vk("shipwreck/upsidedown_fronthalf_degraded"),
      new vk("shipwreck/upsidedown_backhalf_degraded"),
      new vk("shipwreck/sideways_full_degraded"),
      new vk("shipwreck/sideways_fronthalf_degraded"),
      new vk("shipwreck/sideways_backhalf_degraded"),
      new vk("shipwreck/rightsideup_full_degraded"),
      new vk("shipwreck/rightsideup_fronthalf_degraded"),
      new vk("shipwreck/rightsideup_backhalf_degraded")
   };

   public static void a(csw var0, fx var1, bzm var2, List<cru> var3, Random var4, cms var5) {
      vk _snowman = x.a(_snowman.b ? b : c, _snowman);
      _snowman.add(new crr.a(_snowman, _snowman, _snowman, _snowman, _snowman.b));
   }

   public static class a extends crx {
      private final bzm d;
      private final vk e;
      private final boolean f;

      public a(csw var1, vk var2, fx var3, bzm var4, boolean var5) {
         super(clb.ab, 0);
         this.c = _snowman;
         this.d = _snowman;
         this.e = _snowman;
         this.f = _snowman;
         this.a(_snowman);
      }

      public a(csw var1, md var2) {
         super(clb.ab, _snowman);
         this.e = new vk(_snowman.l("Template"));
         this.f = _snowman.q("isBeached");
         this.d = bzm.valueOf(_snowman.l("Rot"));
         this.a(_snowman);
      }

      @Override
      protected void a(md var1) {
         super.a(_snowman);
         _snowman.a("Template", this.e.toString());
         _snowman.a("isBeached", this.f);
         _snowman.a("Rot", this.d.name());
      }

      private void a(csw var1) {
         ctb _snowman = _snowman.a(this.e);
         csx _snowmanx = new csx().a(this.d).a(byg.a).a(crr.a).a(cse.d);
         this.a(_snowman, this.c, _snowmanx);
      }

      @Override
      protected void a(String var1, fx var2, bsk var3, Random var4, cra var5) {
         if ("map_chest".equals(_snowman)) {
            cdd.a(_snowman, _snowman, _snowman.c(), cyq.H);
         } else if ("treasure_chest".equals(_snowman)) {
            cdd.a(_snowman, _snowman, _snowman.c(), cyq.J);
         } else if ("supply_chest".equals(_snowman)) {
            cdd.a(_snowman, _snowman, _snowman.c(), cyq.I);
         }
      }

      @Override
      public boolean a(bsr var1, bsn var2, cfy var3, Random var4, cra var5, brd var6, fx var7) {
         int _snowman = 256;
         int _snowmanx = 0;
         fx _snowmanxx = this.a.a();
         chn.a _snowmanxxx = this.f ? chn.a.a : chn.a.c;
         int _snowmanxxxx = _snowmanxx.u() * _snowmanxx.w();
         if (_snowmanxxxx == 0) {
            _snowmanx = _snowman.a(_snowmanxxx, this.c.u(), this.c.w());
         } else {
            fx _snowmanxxxxx = this.c.b(_snowmanxx.u() - 1, 0, _snowmanxx.w() - 1);

            for (fx _snowmanxxxxxx : fx.a(this.c, _snowmanxxxxx)) {
               int _snowmanxxxxxxx = _snowman.a(_snowmanxxx, _snowmanxxxxxx.u(), _snowmanxxxxxx.w());
               _snowmanx += _snowmanxxxxxxx;
               _snowman = Math.min(_snowman, _snowmanxxxxxxx);
            }

            _snowmanx /= _snowmanxxxx;
         }

         int _snowmanxxxxx = this.f ? _snowman - _snowmanxx.v() / 2 - _snowman.nextInt(3) : _snowmanx;
         this.c = new fx(this.c.u(), _snowmanxxxxx, this.c.w());
         return super.a(_snowman, _snowman, _snowman, _snowman, _snowman, _snowman, _snowman);
      }
   }
}
