import com.google.common.collect.Lists;
import java.util.List;
import java.util.Random;

public class crd {
   private static final csx a = new csx().a(true).a(cse.b);
   private static final csx b = new csx().a(true).a(cse.d);
   private static final crd.b c = new crd.b() {
      @Override
      public void a() {
      }

      @Override
      public boolean a(csw var1, int var2, crd.a var3, fx var4, List<cru> var5, Random var6) {
         if (_snowman > 8) {
            return false;
         } else {
            bzm _snowman = _snowman.b.d();
            crd.a _snowmanx = crd.b(_snowman, crd.b(_snowman, _snowman, _snowman, "base_floor", _snowman, true));
            int _snowmanxx = _snowman.nextInt(3);
            if (_snowmanxx == 0) {
               _snowmanx = crd.b(_snowman, crd.b(_snowman, _snowmanx, new fx(-1, 4, -1), "base_roof", _snowman, true));
            } else if (_snowmanxx == 1) {
               _snowmanx = crd.b(_snowman, crd.b(_snowman, _snowmanx, new fx(-1, 0, -1), "second_floor_2", _snowman, false));
               _snowmanx = crd.b(_snowman, crd.b(_snowman, _snowmanx, new fx(-1, 8, -1), "second_roof", _snowman, false));
               crd.b(_snowman, crd.e, _snowman + 1, _snowmanx, null, _snowman, _snowman);
            } else if (_snowmanxx == 2) {
               _snowmanx = crd.b(_snowman, crd.b(_snowman, _snowmanx, new fx(-1, 0, -1), "second_floor_2", _snowman, false));
               _snowmanx = crd.b(_snowman, crd.b(_snowman, _snowmanx, new fx(-1, 4, -1), "third_floor_2", _snowman, false));
               _snowmanx = crd.b(_snowman, crd.b(_snowman, _snowmanx, new fx(-1, 8, -1), "third_roof", _snowman, true));
               crd.b(_snowman, crd.e, _snowman + 1, _snowmanx, null, _snowman, _snowman);
            }

            return true;
         }
      }
   };
   private static final List<afv<bzm, fx>> d = Lists.newArrayList(
      new afv[]{new afv<>(bzm.a, new fx(1, -1, 0)), new afv<>(bzm.b, new fx(6, -1, 1)), new afv<>(bzm.d, new fx(0, -1, 5)), new afv<>(bzm.c, new fx(5, -1, 6))}
   );
   private static final crd.b e = new crd.b() {
      @Override
      public void a() {
      }

      @Override
      public boolean a(csw var1, int var2, crd.a var3, fx var4, List<cru> var5, Random var6) {
         bzm _snowman = _snowman.b.d();
         crd.a var8 = crd.b(_snowman, crd.b(_snowman, _snowman, new fx(3 + _snowman.nextInt(2), -3, 3 + _snowman.nextInt(2)), "tower_base", _snowman, true));
         var8 = crd.b(_snowman, crd.b(_snowman, var8, new fx(0, 7, 0), "tower_piece", _snowman, true));
         crd.a _snowmanx = _snowman.nextInt(3) == 0 ? var8 : null;
         int _snowmanxx = 1 + _snowman.nextInt(3);

         for (int _snowmanxxx = 0; _snowmanxxx < _snowmanxx; _snowmanxxx++) {
            var8 = crd.b(_snowman, crd.b(_snowman, var8, new fx(0, 4, 0), "tower_piece", _snowman, true));
            if (_snowmanxxx < _snowmanxx - 1 && _snowman.nextBoolean()) {
               _snowmanx = var8;
            }
         }

         if (_snowmanx != null) {
            for (afv<bzm, fx> _snowmanxxxx : crd.d) {
               if (_snowman.nextBoolean()) {
                  crd.a _snowmanxxxxx = crd.b(_snowman, crd.b(_snowman, _snowmanx, _snowmanxxxx.b(), "bridge_end", _snowman.a(_snowmanxxxx.a()), true));
                  crd.b(_snowman, crd.f, _snowman + 1, _snowmanxxxxx, null, _snowman, _snowman);
               }
            }

            var8 = crd.b(_snowman, crd.b(_snowman, var8, new fx(-1, 4, -1), "tower_top", _snowman, true));
         } else {
            if (_snowman != 7) {
               return crd.b(_snowman, crd.h, _snowman + 1, var8, null, _snowman, _snowman);
            }

            var8 = crd.b(_snowman, crd.b(_snowman, var8, new fx(-1, 4, -1), "tower_top", _snowman, true));
         }

         return true;
      }
   };
   private static final crd.b f = new crd.b() {
      public boolean a;

      @Override
      public void a() {
         this.a = false;
      }

      @Override
      public boolean a(csw var1, int var2, crd.a var3, fx var4, List<cru> var5, Random var6) {
         bzm _snowman = _snowman.b.d();
         int _snowmanx = _snowman.nextInt(4) + 1;
         crd.a _snowmanxx = crd.b(_snowman, crd.b(_snowman, _snowman, new fx(0, 0, -4), "bridge_piece", _snowman, true));
         _snowmanxx.o = -1;
         int _snowmanxxx = 0;

         for (int _snowmanxxxx = 0; _snowmanxxxx < _snowmanx; _snowmanxxxx++) {
            if (_snowman.nextBoolean()) {
               _snowmanxx = crd.b(_snowman, crd.b(_snowman, _snowmanxx, new fx(0, _snowmanxxx, -4), "bridge_piece", _snowman, true));
               _snowmanxxx = 0;
            } else {
               if (_snowman.nextBoolean()) {
                  _snowmanxx = crd.b(_snowman, crd.b(_snowman, _snowmanxx, new fx(0, _snowmanxxx, -4), "bridge_steep_stairs", _snowman, true));
               } else {
                  _snowmanxx = crd.b(_snowman, crd.b(_snowman, _snowmanxx, new fx(0, _snowmanxxx, -8), "bridge_gentle_stairs", _snowman, true));
               }

               _snowmanxxx = 4;
            }
         }

         if (!this.a && _snowman.nextInt(10 - _snowman) == 0) {
            crd.b(_snowman, crd.b(_snowman, _snowmanxx, new fx(-8 + _snowman.nextInt(8), _snowmanxxx, -70 + _snowman.nextInt(10)), "ship", _snowman, true));
            this.a = true;
         } else if (!crd.b(_snowman, crd.c, _snowman + 1, _snowmanxx, new fx(-3, _snowmanxxx + 1, -11), _snowman, _snowman)) {
            return false;
         }

         _snowmanxx = crd.b(_snowman, crd.b(_snowman, _snowmanxx, new fx(4, _snowmanxxx, 0), "bridge_end", _snowman.a(bzm.c), true));
         _snowmanxx.o = -1;
         return true;
      }
   };
   private static final List<afv<bzm, fx>> g = Lists.newArrayList(
      new afv[]{
         new afv<>(bzm.a, new fx(4, -1, 0)), new afv<>(bzm.b, new fx(12, -1, 4)), new afv<>(bzm.d, new fx(0, -1, 8)), new afv<>(bzm.c, new fx(8, -1, 12))
      }
   );
   private static final crd.b h = new crd.b() {
      @Override
      public void a() {
      }

      @Override
      public boolean a(csw var1, int var2, crd.a var3, fx var4, List<cru> var5, Random var6) {
         bzm _snowman = _snowman.b.d();
         crd.a _snowmanx = crd.b(_snowman, crd.b(_snowman, _snowman, new fx(-3, 4, -3), "fat_tower_base", _snowman, true));
         _snowmanx = crd.b(_snowman, crd.b(_snowman, _snowmanx, new fx(0, 4, 0), "fat_tower_middle", _snowman, true));

         for (int _snowmanxx = 0; _snowmanxx < 2 && _snowman.nextInt(3) != 0; _snowmanxx++) {
            _snowmanx = crd.b(_snowman, crd.b(_snowman, _snowmanx, new fx(0, 8, 0), "fat_tower_middle", _snowman, true));

            for (afv<bzm, fx> _snowmanxxx : crd.g) {
               if (_snowman.nextBoolean()) {
                  crd.a _snowmanxxxx = crd.b(_snowman, crd.b(_snowman, _snowmanx, _snowmanxxx.b(), "bridge_end", _snowman.a(_snowmanxxx.a()), true));
                  crd.b(_snowman, crd.f, _snowman + 1, _snowmanxxxx, null, _snowman, _snowman);
               }
            }
         }

         _snowmanx = crd.b(_snowman, crd.b(_snowman, _snowmanx, new fx(-2, 8, -2), "fat_tower_top", _snowman, true));
         return true;
      }
   };

   private static crd.a b(csw var0, crd.a var1, fx var2, String var3, bzm var4, boolean var5) {
      crd.a _snowman = new crd.a(_snowman, _snowman, _snowman.c, _snowman, _snowman);
      fx _snowmanx = _snowman.a.a(_snowman.b, _snowman, _snowman.b, fx.b);
      _snowman.a(_snowmanx.u(), _snowmanx.v(), _snowmanx.w());
      return _snowman;
   }

   public static void a(csw var0, fx var1, bzm var2, List<cru> var3, Random var4) {
      h.a();
      c.a();
      f.a();
      e.a();
      crd.a _snowman = b(_snowman, new crd.a(_snowman, "base_floor", _snowman, _snowman, true));
      _snowman = b(_snowman, b(_snowman, _snowman, new fx(-1, 0, -1), "second_floor_1", _snowman, false));
      _snowman = b(_snowman, b(_snowman, _snowman, new fx(-1, 4, -1), "third_floor_1", _snowman, false));
      _snowman = b(_snowman, b(_snowman, _snowman, new fx(-1, 8, -1), "third_roof", _snowman, true));
      b(_snowman, e, 1, _snowman, null, _snowman, _snowman);
   }

   private static crd.a b(List<cru> var0, crd.a var1) {
      _snowman.add(_snowman);
      return _snowman;
   }

   private static boolean b(csw var0, crd.b var1, int var2, crd.a var3, fx var4, List<cru> var5, Random var6) {
      if (_snowman > 8) {
         return false;
      } else {
         List<cru> _snowman = Lists.newArrayList();
         if (_snowman.a(_snowman, _snowman, _snowman, _snowman, _snowman, _snowman)) {
            boolean _snowmanx = false;
            int _snowmanxx = _snowman.nextInt();

            for (cru _snowmanxxx : _snowman) {
               _snowmanxxx.o = _snowmanxx;
               cru _snowmanxxxx = cru.a(_snowman, _snowmanxxx.g());
               if (_snowmanxxxx != null && _snowmanxxxx.o != _snowman.o) {
                  _snowmanx = true;
                  break;
               }
            }

            if (!_snowmanx) {
               _snowman.addAll(_snowman);
               return true;
            }
         }

         return false;
      }
   }

   public static class a extends crx {
      private final String d;
      private final bzm e;
      private final boolean f;

      public a(csw var1, String var2, fx var3, bzm var4, boolean var5) {
         super(clb.Y, 0);
         this.d = _snowman;
         this.c = _snowman;
         this.e = _snowman;
         this.f = _snowman;
         this.a(_snowman);
      }

      public a(csw var1, md var2) {
         super(clb.Y, _snowman);
         this.d = _snowman.l("Template");
         this.e = bzm.valueOf(_snowman.l("Rot"));
         this.f = _snowman.q("OW");
         this.a(_snowman);
      }

      private void a(csw var1) {
         ctb _snowman = _snowman.a(new vk("end_city/" + this.d));
         csx _snowmanx = (this.f ? crd.a : crd.b).a().a(this.e);
         this.a(_snowman, this.c, _snowmanx);
      }

      @Override
      protected void a(md var1) {
         super.a(_snowman);
         _snowman.a("Template", this.d);
         _snowman.a("Rot", this.e.name());
         _snowman.a("OW", this.f);
      }

      @Override
      protected void a(String var1, fx var2, bsk var3, Random var4, cra var5) {
         if (_snowman.startsWith("Chest")) {
            fx _snowman = _snowman.c();
            if (_snowman.b(_snowman)) {
               cdd.a(_snowman, _snowman, _snowman, cyq.c);
            }
         } else if (_snowman.startsWith("Sentry")) {
            bdw _snowman = aqe.as.a(_snowman.E());
            _snowman.d((double)_snowman.u() + 0.5, (double)_snowman.v() + 0.5, (double)_snowman.w() + 0.5);
            _snowman.h(_snowman);
            _snowman.c(_snowman);
         } else if (_snowman.startsWith("Elytra")) {
            bcp _snowman = new bcp(_snowman.E(), _snowman, this.e.a(gc.d));
            _snowman.a(new bmb(bmd.qo), false);
            _snowman.c(_snowman);
         }
      }
   }

   interface b {
      void a();

      boolean a(csw var1, int var2, crd.a var3, fx var4, List<cru> var5, Random var6);
   }
}
