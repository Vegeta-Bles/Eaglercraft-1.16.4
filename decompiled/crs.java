import com.google.common.collect.Lists;
import java.util.List;
import java.util.Random;
import javax.annotation.Nullable;

public class crs {
   private static final crs.f[] a = new crs.f[]{
      new crs.f(crs.n.class, 40, 0),
      new crs.f(crs.h.class, 5, 5),
      new crs.f(crs.d.class, 20, 0),
      new crs.f(crs.i.class, 20, 0),
      new crs.f(crs.j.class, 10, 6),
      new crs.f(crs.o.class, 5, 5),
      new crs.f(crs.l.class, 5, 5),
      new crs.f(crs.c.class, 5, 4),
      new crs.f(crs.a.class, 5, 4),
      new crs.f(crs.e.class, 10, 2) {
         @Override
         public boolean a(int var1) {
            return super.a(_snowman) && _snowman > 4;
         }
      },
      new crs.f(crs.g.class, 20, 1) {
         @Override
         public boolean a(int var1) {
            return super.a(_snowman) && _snowman > 5;
         }
      }
   };
   private static List<crs.f> b;
   private static Class<? extends crs.p> c;
   private static int d;
   private static final crs.k e = new crs.k();

   public static void a() {
      b = Lists.newArrayList();

      for (crs.f _snowman : a) {
         _snowman.c = 0;
         b.add(_snowman);
      }

      c = null;
   }

   private static boolean c() {
      boolean _snowman = false;
      d = 0;

      for (crs.f _snowmanx : b) {
         if (_snowmanx.d > 0 && _snowmanx.c < _snowmanx.d) {
            _snowman = true;
         }

         d = d + _snowmanx.b;
      }

      return _snowman;
   }

   private static crs.p a(Class<? extends crs.p> var0, List<cru> var1, Random var2, int var3, int var4, int var5, @Nullable gc var6, int var7) {
      crs.p _snowman = null;
      if (_snowman == crs.n.class) {
         _snowman = crs.n.a(_snowman, _snowman, _snowman, _snowman, _snowman, _snowman, _snowman);
      } else if (_snowman == crs.h.class) {
         _snowman = crs.h.a(_snowman, _snowman, _snowman, _snowman, _snowman, _snowman, _snowman);
      } else if (_snowman == crs.d.class) {
         _snowman = crs.d.a(_snowman, _snowman, _snowman, _snowman, _snowman, _snowman, _snowman);
      } else if (_snowman == crs.i.class) {
         _snowman = crs.i.a(_snowman, _snowman, _snowman, _snowman, _snowman, _snowman, _snowman);
      } else if (_snowman == crs.j.class) {
         _snowman = crs.j.a(_snowman, _snowman, _snowman, _snowman, _snowman, _snowman, _snowman);
      } else if (_snowman == crs.o.class) {
         _snowman = crs.o.a(_snowman, _snowman, _snowman, _snowman, _snowman, _snowman, _snowman);
      } else if (_snowman == crs.l.class) {
         _snowman = crs.l.a(_snowman, _snowman, _snowman, _snowman, _snowman, _snowman, _snowman);
      } else if (_snowman == crs.c.class) {
         _snowman = crs.c.a(_snowman, _snowman, _snowman, _snowman, _snowman, _snowman, _snowman);
      } else if (_snowman == crs.a.class) {
         _snowman = crs.a.a(_snowman, _snowman, _snowman, _snowman, _snowman, _snowman, _snowman);
      } else if (_snowman == crs.e.class) {
         _snowman = crs.e.a(_snowman, _snowman, _snowman, _snowman, _snowman, _snowman, _snowman);
      } else if (_snowman == crs.g.class) {
         _snowman = crs.g.a(_snowman, _snowman, _snowman, _snowman, _snowman, _snowman);
      }

      return _snowman;
   }

   private static crs.p b(crs.m var0, List<cru> var1, Random var2, int var3, int var4, int var5, gc var6, int var7) {
      if (!c()) {
         return null;
      } else {
         if (c != null) {
            crs.p _snowman = a(c, _snowman, _snowman, _snowman, _snowman, _snowman, _snowman, _snowman);
            c = null;
            if (_snowman != null) {
               return _snowman;
            }
         }

         int _snowman = 0;

         while (_snowman < 5) {
            _snowman++;
            int _snowmanx = _snowman.nextInt(d);

            for (crs.f _snowmanxx : b) {
               _snowmanx -= _snowmanxx.b;
               if (_snowmanx < 0) {
                  if (!_snowmanxx.a(_snowman) || _snowmanxx == _snowman.a) {
                     break;
                  }

                  crs.p _snowmanxxx = a(_snowmanxx.a, _snowman, _snowman, _snowman, _snowman, _snowman, _snowman, _snowman);
                  if (_snowmanxxx != null) {
                     _snowmanxx.c++;
                     _snowman.a = _snowmanxx;
                     if (!_snowmanxx.a()) {
                        b.remove(_snowmanxx);
                     }

                     return _snowmanxxx;
                  }
               }
            }
         }

         cra _snowmanx = crs.b.a(_snowman, _snowman, _snowman, _snowman, _snowman, _snowman);
         return _snowmanx != null && _snowmanx.b > 1 ? new crs.b(_snowman, _snowmanx, _snowman) : null;
      }
   }

   private static cru c(crs.m var0, List<cru> var1, Random var2, int var3, int var4, int var5, @Nullable gc var6, int var7) {
      if (_snowman > 50) {
         return null;
      } else if (Math.abs(_snowman - _snowman.g().a) <= 112 && Math.abs(_snowman - _snowman.g().c) <= 112) {
         cru _snowman = b(_snowman, _snowman, _snowman, _snowman, _snowman, _snowman, _snowman, _snowman + 1);
         if (_snowman != null) {
            _snowman.add(_snowman);
            _snowman.c.add(_snowman);
         }

         return _snowman;
      } else {
         return null;
      }
   }

   public static class a extends crs.p {
      private boolean a;

      public a(int var1, Random var2, cra var3, gc var4) {
         super(clb.t, _snowman);
         this.a(_snowman);
         this.d = this.a(_snowman);
         this.n = _snowman;
      }

      public a(csw var1, md var2) {
         super(clb.t, _snowman);
         this.a = _snowman.q("Chest");
      }

      @Override
      protected void a(md var1) {
         super.a(_snowman);
         _snowman.a("Chest", this.a);
      }

      @Override
      public void a(cru var1, List<cru> var2, Random var3) {
         this.a((crs.m)_snowman, _snowman, _snowman, 1, 1);
      }

      public static crs.a a(List<cru> var0, Random var1, int var2, int var3, int var4, gc var5, int var6) {
         cra _snowman = cra.a(_snowman, _snowman, _snowman, -1, -1, 0, 5, 5, 7, _snowman);
         return a(_snowman) && cru.a(_snowman, _snowman) == null ? new crs.a(_snowman, _snowman, _snowman, _snowman) : null;
      }

      @Override
      public boolean a(bsr var1, bsn var2, cfy var3, Random var4, cra var5, brd var6, fx var7) {
         this.a(_snowman, _snowman, 0, 0, 0, 4, 4, 6, true, _snowman, crs.e);
         this.a(_snowman, _snowman, _snowman, this.d, 1, 1, 0);
         this.a(_snowman, _snowman, _snowman, crs.p.a.a, 1, 1, 6);
         this.a(_snowman, _snowman, 3, 1, 2, 3, 1, 4, bup.du.n(), bup.du.n(), false);
         this.a(_snowman, bup.hX.n(), 3, 1, 1, _snowman);
         this.a(_snowman, bup.hX.n(), 3, 1, 5, _snowman);
         this.a(_snowman, bup.hX.n(), 3, 2, 2, _snowman);
         this.a(_snowman, bup.hX.n(), 3, 2, 4, _snowman);

         for (int _snowman = 2; _snowman <= 4; _snowman++) {
            this.a(_snowman, bup.hX.n(), 2, 1, _snowman, _snowman);
         }

         if (!this.a && _snowman.b(new fx(this.a(3, 3), this.d(2), this.b(3, 3)))) {
            this.a = true;
            this.a(_snowman, _snowman, _snowman, 3, 2, 3, cyq.y);
         }

         return true;
      }
   }

   public static class b extends crs.p {
      private final int a;

      public b(int var1, cra var2, gc var3) {
         super(clb.u, _snowman);
         this.a(_snowman);
         this.n = _snowman;
         this.a = _snowman != gc.c && _snowman != gc.d ? _snowman.d() : _snowman.f();
      }

      public b(csw var1, md var2) {
         super(clb.u, _snowman);
         this.a = _snowman.h("Steps");
      }

      @Override
      protected void a(md var1) {
         super.a(_snowman);
         _snowman.b("Steps", this.a);
      }

      public static cra a(List<cru> var0, Random var1, int var2, int var3, int var4, gc var5) {
         int _snowman = 3;
         cra _snowmanx = cra.a(_snowman, _snowman, _snowman, -1, -1, 0, 5, 5, 4, _snowman);
         cru _snowmanxx = cru.a(_snowman, _snowmanx);
         if (_snowmanxx == null) {
            return null;
         } else {
            if (_snowmanxx.g().b == _snowmanx.b) {
               for (int _snowmanxxx = 3; _snowmanxxx >= 1; _snowmanxxx--) {
                  _snowmanx = cra.a(_snowman, _snowman, _snowman, -1, -1, 0, 5, 5, _snowmanxxx - 1, _snowman);
                  if (!_snowmanxx.g().b(_snowmanx)) {
                     return cra.a(_snowman, _snowman, _snowman, -1, -1, 0, 5, 5, _snowmanxxx, _snowman);
                  }
               }
            }

            return null;
         }
      }

      @Override
      public boolean a(bsr var1, bsn var2, cfy var3, Random var4, cra var5, brd var6, fx var7) {
         for (int _snowman = 0; _snowman < this.a; _snowman++) {
            this.a(_snowman, bup.du.n(), 0, 0, _snowman, _snowman);
            this.a(_snowman, bup.du.n(), 1, 0, _snowman, _snowman);
            this.a(_snowman, bup.du.n(), 2, 0, _snowman, _snowman);
            this.a(_snowman, bup.du.n(), 3, 0, _snowman, _snowman);
            this.a(_snowman, bup.du.n(), 4, 0, _snowman, _snowman);

            for (int _snowmanx = 1; _snowmanx <= 3; _snowmanx++) {
               this.a(_snowman, bup.du.n(), 0, _snowmanx, _snowman, _snowman);
               this.a(_snowman, bup.lb.n(), 1, _snowmanx, _snowman, _snowman);
               this.a(_snowman, bup.lb.n(), 2, _snowmanx, _snowman, _snowman);
               this.a(_snowman, bup.lb.n(), 3, _snowmanx, _snowman, _snowman);
               this.a(_snowman, bup.du.n(), 4, _snowmanx, _snowman, _snowman);
            }

            this.a(_snowman, bup.du.n(), 0, 4, _snowman, _snowman);
            this.a(_snowman, bup.du.n(), 1, 4, _snowman, _snowman);
            this.a(_snowman, bup.du.n(), 2, 4, _snowman, _snowman);
            this.a(_snowman, bup.du.n(), 3, 4, _snowman, _snowman);
            this.a(_snowman, bup.du.n(), 4, 4, _snowman, _snowman);
         }

         return true;
      }
   }

   public static class c extends crs.p {
      private final boolean a;
      private final boolean b;
      private final boolean c;
      private final boolean e;

      public c(int var1, Random var2, cra var3, gc var4) {
         super(clb.v, _snowman);
         this.a(_snowman);
         this.d = this.a(_snowman);
         this.n = _snowman;
         this.a = _snowman.nextBoolean();
         this.b = _snowman.nextBoolean();
         this.c = _snowman.nextBoolean();
         this.e = _snowman.nextInt(3) > 0;
      }

      public c(csw var1, md var2) {
         super(clb.v, _snowman);
         this.a = _snowman.q("leftLow");
         this.b = _snowman.q("leftHigh");
         this.c = _snowman.q("rightLow");
         this.e = _snowman.q("rightHigh");
      }

      @Override
      protected void a(md var1) {
         super.a(_snowman);
         _snowman.a("leftLow", this.a);
         _snowman.a("leftHigh", this.b);
         _snowman.a("rightLow", this.c);
         _snowman.a("rightHigh", this.e);
      }

      @Override
      public void a(cru var1, List<cru> var2, Random var3) {
         int _snowman = 3;
         int _snowmanx = 5;
         gc _snowmanxx = this.i();
         if (_snowmanxx == gc.e || _snowmanxx == gc.c) {
            _snowman = 8 - _snowman;
            _snowmanx = 8 - _snowmanx;
         }

         this.a((crs.m)_snowman, _snowman, _snowman, 5, 1);
         if (this.a) {
            this.b((crs.m)_snowman, _snowman, _snowman, _snowman, 1);
         }

         if (this.b) {
            this.b((crs.m)_snowman, _snowman, _snowman, _snowmanx, 7);
         }

         if (this.c) {
            this.c((crs.m)_snowman, _snowman, _snowman, _snowman, 1);
         }

         if (this.e) {
            this.c((crs.m)_snowman, _snowman, _snowman, _snowmanx, 7);
         }
      }

      public static crs.c a(List<cru> var0, Random var1, int var2, int var3, int var4, gc var5, int var6) {
         cra _snowman = cra.a(_snowman, _snowman, _snowman, -4, -3, 0, 10, 9, 11, _snowman);
         return a(_snowman) && cru.a(_snowman, _snowman) == null ? new crs.c(_snowman, _snowman, _snowman, _snowman) : null;
      }

      @Override
      public boolean a(bsr var1, bsn var2, cfy var3, Random var4, cra var5, brd var6, fx var7) {
         this.a(_snowman, _snowman, 0, 0, 0, 9, 8, 10, true, _snowman, crs.e);
         this.a(_snowman, _snowman, _snowman, this.d, 4, 3, 0);
         if (this.a) {
            this.a(_snowman, _snowman, 0, 3, 1, 0, 5, 3, m, m, false);
         }

         if (this.c) {
            this.a(_snowman, _snowman, 9, 3, 1, 9, 5, 3, m, m, false);
         }

         if (this.b) {
            this.a(_snowman, _snowman, 0, 5, 7, 0, 7, 9, m, m, false);
         }

         if (this.e) {
            this.a(_snowman, _snowman, 9, 5, 7, 9, 7, 9, m, m, false);
         }

         this.a(_snowman, _snowman, 5, 1, 10, 7, 3, 10, m, m, false);
         this.a(_snowman, _snowman, 1, 2, 1, 8, 2, 6, false, _snowman, crs.e);
         this.a(_snowman, _snowman, 4, 1, 5, 4, 4, 9, false, _snowman, crs.e);
         this.a(_snowman, _snowman, 8, 1, 5, 8, 4, 9, false, _snowman, crs.e);
         this.a(_snowman, _snowman, 1, 4, 7, 3, 4, 9, false, _snowman, crs.e);
         this.a(_snowman, _snowman, 1, 3, 5, 3, 3, 6, false, _snowman, crs.e);
         this.a(_snowman, _snowman, 1, 3, 4, 3, 3, 4, bup.hR.n(), bup.hR.n(), false);
         this.a(_snowman, _snowman, 1, 4, 6, 3, 4, 6, bup.hR.n(), bup.hR.n(), false);
         this.a(_snowman, _snowman, 5, 1, 7, 7, 1, 8, false, _snowman, crs.e);
         this.a(_snowman, _snowman, 5, 1, 9, 7, 1, 9, bup.hR.n(), bup.hR.n(), false);
         this.a(_snowman, _snowman, 5, 2, 7, 7, 2, 7, bup.hR.n(), bup.hR.n(), false);
         this.a(_snowman, _snowman, 4, 5, 7, 4, 5, 9, bup.hR.n(), bup.hR.n(), false);
         this.a(_snowman, _snowman, 8, 5, 7, 8, 5, 9, bup.hR.n(), bup.hR.n(), false);
         this.a(_snowman, _snowman, 5, 5, 7, 7, 5, 9, bup.hR.n().a(bzw.a, cfm.c), bup.hR.n().a(bzw.a, cfm.c), false);
         this.a(_snowman, bup.bM.n().a(cbn.a, gc.d), 6, 5, 6, _snowman);
         return true;
      }
   }

   public static class d extends crs.q {
      public d(int var1, Random var2, cra var3, gc var4) {
         super(clb.w, _snowman);
         this.a(_snowman);
         this.d = this.a(_snowman);
         this.n = _snowman;
      }

      public d(csw var1, md var2) {
         super(clb.w, _snowman);
      }

      @Override
      public void a(cru var1, List<cru> var2, Random var3) {
         gc _snowman = this.i();
         if (_snowman != gc.c && _snowman != gc.f) {
            this.c((crs.m)_snowman, _snowman, _snowman, 1, 1);
         } else {
            this.b((crs.m)_snowman, _snowman, _snowman, 1, 1);
         }
      }

      public static crs.d a(List<cru> var0, Random var1, int var2, int var3, int var4, gc var5, int var6) {
         cra _snowman = cra.a(_snowman, _snowman, _snowman, -1, -1, 0, 5, 5, 5, _snowman);
         return a(_snowman) && cru.a(_snowman, _snowman) == null ? new crs.d(_snowman, _snowman, _snowman, _snowman) : null;
      }

      @Override
      public boolean a(bsr var1, bsn var2, cfy var3, Random var4, cra var5, brd var6, fx var7) {
         this.a(_snowman, _snowman, 0, 0, 0, 4, 4, 4, true, _snowman, crs.e);
         this.a(_snowman, _snowman, _snowman, this.d, 1, 1, 0);
         gc _snowman = this.i();
         if (_snowman != gc.c && _snowman != gc.f) {
            this.a(_snowman, _snowman, 4, 1, 1, 4, 3, 3, m, m, false);
         } else {
            this.a(_snowman, _snowman, 0, 1, 1, 0, 3, 3, m, m, false);
         }

         return true;
      }
   }

   public static class e extends crs.p {
      private final boolean a;

      public e(int var1, Random var2, cra var3, gc var4) {
         super(clb.x, _snowman);
         this.a(_snowman);
         this.d = this.a(_snowman);
         this.n = _snowman;
         this.a = _snowman.e() > 6;
      }

      public e(csw var1, md var2) {
         super(clb.x, _snowman);
         this.a = _snowman.q("Tall");
      }

      @Override
      protected void a(md var1) {
         super.a(_snowman);
         _snowman.a("Tall", this.a);
      }

      public static crs.e a(List<cru> var0, Random var1, int var2, int var3, int var4, gc var5, int var6) {
         cra _snowman = cra.a(_snowman, _snowman, _snowman, -4, -1, 0, 14, 11, 15, _snowman);
         if (!a(_snowman) || cru.a(_snowman, _snowman) != null) {
            _snowman = cra.a(_snowman, _snowman, _snowman, -4, -1, 0, 14, 6, 15, _snowman);
            if (!a(_snowman) || cru.a(_snowman, _snowman) != null) {
               return null;
            }
         }

         return new crs.e(_snowman, _snowman, _snowman, _snowman);
      }

      @Override
      public boolean a(bsr var1, bsn var2, cfy var3, Random var4, cra var5, brd var6, fx var7) {
         int _snowman = 11;
         if (!this.a) {
            _snowman = 6;
         }

         this.a(_snowman, _snowman, 0, 0, 0, 13, _snowman - 1, 14, true, _snowman, crs.e);
         this.a(_snowman, _snowman, _snowman, this.d, 4, 1, 0);
         this.a(_snowman, _snowman, _snowman, 0.07F, 2, 1, 1, 11, 4, 13, bup.aQ.n(), bup.aQ.n(), false, false);
         int _snowmanx = 1;
         int _snowmanxx = 12;

         for (int _snowmanxxx = 1; _snowmanxxx <= 13; _snowmanxxx++) {
            if ((_snowmanxxx - 1) % 4 == 0) {
               this.a(_snowman, _snowman, 1, 1, _snowmanxxx, 1, 4, _snowmanxxx, bup.n.n(), bup.n.n(), false);
               this.a(_snowman, _snowman, 12, 1, _snowmanxxx, 12, 4, _snowmanxxx, bup.n.n(), bup.n.n(), false);
               this.a(_snowman, bup.bM.n().a(cbn.a, gc.f), 2, 3, _snowmanxxx, _snowman);
               this.a(_snowman, bup.bM.n().a(cbn.a, gc.e), 11, 3, _snowmanxxx, _snowman);
               if (this.a) {
                  this.a(_snowman, _snowman, 1, 6, _snowmanxxx, 1, 9, _snowmanxxx, bup.n.n(), bup.n.n(), false);
                  this.a(_snowman, _snowman, 12, 6, _snowmanxxx, 12, 9, _snowmanxxx, bup.n.n(), bup.n.n(), false);
               }
            } else {
               this.a(_snowman, _snowman, 1, 1, _snowmanxxx, 1, 4, _snowmanxxx, bup.bI.n(), bup.bI.n(), false);
               this.a(_snowman, _snowman, 12, 1, _snowmanxxx, 12, 4, _snowmanxxx, bup.bI.n(), bup.bI.n(), false);
               if (this.a) {
                  this.a(_snowman, _snowman, 1, 6, _snowmanxxx, 1, 9, _snowmanxxx, bup.bI.n(), bup.bI.n(), false);
                  this.a(_snowman, _snowman, 12, 6, _snowmanxxx, 12, 9, _snowmanxxx, bup.bI.n(), bup.bI.n(), false);
               }
            }
         }

         for (int _snowmanxxxx = 3; _snowmanxxxx < 12; _snowmanxxxx += 2) {
            this.a(_snowman, _snowman, 3, 1, _snowmanxxxx, 4, 3, _snowmanxxxx, bup.bI.n(), bup.bI.n(), false);
            this.a(_snowman, _snowman, 6, 1, _snowmanxxxx, 7, 3, _snowmanxxxx, bup.bI.n(), bup.bI.n(), false);
            this.a(_snowman, _snowman, 9, 1, _snowmanxxxx, 10, 3, _snowmanxxxx, bup.bI.n(), bup.bI.n(), false);
         }

         if (this.a) {
            this.a(_snowman, _snowman, 1, 5, 1, 3, 5, 13, bup.n.n(), bup.n.n(), false);
            this.a(_snowman, _snowman, 10, 5, 1, 12, 5, 13, bup.n.n(), bup.n.n(), false);
            this.a(_snowman, _snowman, 4, 5, 1, 9, 5, 2, bup.n.n(), bup.n.n(), false);
            this.a(_snowman, _snowman, 4, 5, 12, 9, 5, 13, bup.n.n(), bup.n.n(), false);
            this.a(_snowman, bup.n.n(), 9, 5, 11, _snowman);
            this.a(_snowman, bup.n.n(), 8, 5, 11, _snowman);
            this.a(_snowman, bup.n.n(), 9, 5, 10, _snowman);
            ceh _snowmanxxxx = bup.cJ.n().a(bwq.d, Boolean.valueOf(true)).a(bwq.b, Boolean.valueOf(true));
            ceh _snowmanxxxxx = bup.cJ.n().a(bwq.a, Boolean.valueOf(true)).a(bwq.c, Boolean.valueOf(true));
            this.a(_snowman, _snowman, 3, 6, 3, 3, 6, 11, _snowmanxxxxx, _snowmanxxxxx, false);
            this.a(_snowman, _snowman, 10, 6, 3, 10, 6, 9, _snowmanxxxxx, _snowmanxxxxx, false);
            this.a(_snowman, _snowman, 4, 6, 2, 9, 6, 2, _snowmanxxxx, _snowmanxxxx, false);
            this.a(_snowman, _snowman, 4, 6, 12, 7, 6, 12, _snowmanxxxx, _snowmanxxxx, false);
            this.a(_snowman, bup.cJ.n().a(bwq.a, Boolean.valueOf(true)).a(bwq.b, Boolean.valueOf(true)), 3, 6, 2, _snowman);
            this.a(_snowman, bup.cJ.n().a(bwq.c, Boolean.valueOf(true)).a(bwq.b, Boolean.valueOf(true)), 3, 6, 12, _snowman);
            this.a(_snowman, bup.cJ.n().a(bwq.a, Boolean.valueOf(true)).a(bwq.d, Boolean.valueOf(true)), 10, 6, 2, _snowman);

            for (int _snowmanxxxxxx = 0; _snowmanxxxxxx <= 2; _snowmanxxxxxx++) {
               this.a(_snowman, bup.cJ.n().a(bwq.c, Boolean.valueOf(true)).a(bwq.d, Boolean.valueOf(true)), 8 + _snowmanxxxxxx, 6, 12 - _snowmanxxxxxx, _snowman);
               if (_snowmanxxxxxx != 2) {
                  this.a(_snowman, bup.cJ.n().a(bwq.a, Boolean.valueOf(true)).a(bwq.b, Boolean.valueOf(true)), 8 + _snowmanxxxxxx, 6, 11 - _snowmanxxxxxx, _snowman);
               }
            }

            ceh _snowmanxxxxxxx = bup.cg.n().a(bxv.a, gc.d);
            this.a(_snowman, _snowmanxxxxxxx, 10, 1, 13, _snowman);
            this.a(_snowman, _snowmanxxxxxxx, 10, 2, 13, _snowman);
            this.a(_snowman, _snowmanxxxxxxx, 10, 3, 13, _snowman);
            this.a(_snowman, _snowmanxxxxxxx, 10, 4, 13, _snowman);
            this.a(_snowman, _snowmanxxxxxxx, 10, 5, 13, _snowman);
            this.a(_snowman, _snowmanxxxxxxx, 10, 6, 13, _snowman);
            this.a(_snowman, _snowmanxxxxxxx, 10, 7, 13, _snowman);
            int _snowmanxxxxxxxx = 7;
            int _snowmanxxxxxxxxx = 7;
            ceh _snowmanxxxxxxxxxx = bup.cJ.n().a(bwq.b, Boolean.valueOf(true));
            this.a(_snowman, _snowmanxxxxxxxxxx, 6, 9, 7, _snowman);
            ceh _snowmanxxxxxxxxxxx = bup.cJ.n().a(bwq.d, Boolean.valueOf(true));
            this.a(_snowman, _snowmanxxxxxxxxxxx, 7, 9, 7, _snowman);
            this.a(_snowman, _snowmanxxxxxxxxxx, 6, 8, 7, _snowman);
            this.a(_snowman, _snowmanxxxxxxxxxxx, 7, 8, 7, _snowman);
            ceh _snowmanxxxxxxxxxxxx = _snowmanxxxxx.a(bwq.d, Boolean.valueOf(true)).a(bwq.b, Boolean.valueOf(true));
            this.a(_snowman, _snowmanxxxxxxxxxxxx, 6, 7, 7, _snowman);
            this.a(_snowman, _snowmanxxxxxxxxxxxx, 7, 7, 7, _snowman);
            this.a(_snowman, _snowmanxxxxxxxxxx, 5, 7, 7, _snowman);
            this.a(_snowman, _snowmanxxxxxxxxxxx, 8, 7, 7, _snowman);
            this.a(_snowman, _snowmanxxxxxxxxxx.a(bwq.a, Boolean.valueOf(true)), 6, 7, 6, _snowman);
            this.a(_snowman, _snowmanxxxxxxxxxx.a(bwq.c, Boolean.valueOf(true)), 6, 7, 8, _snowman);
            this.a(_snowman, _snowmanxxxxxxxxxxx.a(bwq.a, Boolean.valueOf(true)), 7, 7, 6, _snowman);
            this.a(_snowman, _snowmanxxxxxxxxxxx.a(bwq.c, Boolean.valueOf(true)), 7, 7, 8, _snowman);
            ceh _snowmanxxxxxxxxxxxxx = bup.bL.n();
            this.a(_snowman, _snowmanxxxxxxxxxxxxx, 5, 8, 7, _snowman);
            this.a(_snowman, _snowmanxxxxxxxxxxxxx, 8, 8, 7, _snowman);
            this.a(_snowman, _snowmanxxxxxxxxxxxxx, 6, 8, 6, _snowman);
            this.a(_snowman, _snowmanxxxxxxxxxxxxx, 6, 8, 8, _snowman);
            this.a(_snowman, _snowmanxxxxxxxxxxxxx, 7, 8, 6, _snowman);
            this.a(_snowman, _snowmanxxxxxxxxxxxxx, 7, 8, 8, _snowman);
         }

         this.a(_snowman, _snowman, _snowman, 3, 3, 5, cyq.w);
         if (this.a) {
            this.a(_snowman, m, 12, 9, 1, _snowman);
            this.a(_snowman, _snowman, _snowman, 12, 8, 1, cyq.w);
         }

         return true;
      }
   }

   static class f {
      public final Class<? extends crs.p> a;
      public final int b;
      public int c;
      public final int d;

      public f(Class<? extends crs.p> var1, int var2, int var3) {
         this.a = _snowman;
         this.b = _snowman;
         this.d = _snowman;
      }

      public boolean a(int var1) {
         return this.d == 0 || this.c < this.d;
      }

      public boolean a() {
         return this.d == 0 || this.c < this.d;
      }
   }

   public static class g extends crs.p {
      private boolean a;

      public g(int var1, cra var2, gc var3) {
         super(clb.y, _snowman);
         this.a(_snowman);
         this.n = _snowman;
      }

      public g(csw var1, md var2) {
         super(clb.y, _snowman);
         this.a = _snowman.q("Mob");
      }

      @Override
      protected void a(md var1) {
         super.a(_snowman);
         _snowman.a("Mob", this.a);
      }

      @Override
      public void a(cru var1, List<cru> var2, Random var3) {
         if (_snowman != null) {
            ((crs.m)_snowman).b = this;
         }
      }

      public static crs.g a(List<cru> var0, int var1, int var2, int var3, gc var4, int var5) {
         cra _snowman = cra.a(_snowman, _snowman, _snowman, -4, -1, 0, 11, 8, 16, _snowman);
         return a(_snowman) && cru.a(_snowman, _snowman) == null ? new crs.g(_snowman, _snowman, _snowman) : null;
      }

      @Override
      public boolean a(bsr var1, bsn var2, cfy var3, Random var4, cra var5, brd var6, fx var7) {
         this.a(_snowman, _snowman, 0, 0, 0, 10, 7, 15, false, _snowman, crs.e);
         this.a(_snowman, _snowman, _snowman, crs.p.a.c, 4, 1, 0);
         int _snowman = 6;
         this.a(_snowman, _snowman, 1, _snowman, 1, 1, _snowman, 14, false, _snowman, crs.e);
         this.a(_snowman, _snowman, 9, _snowman, 1, 9, _snowman, 14, false, _snowman, crs.e);
         this.a(_snowman, _snowman, 2, _snowman, 1, 8, _snowman, 2, false, _snowman, crs.e);
         this.a(_snowman, _snowman, 2, _snowman, 14, 8, _snowman, 14, false, _snowman, crs.e);
         this.a(_snowman, _snowman, 1, 1, 1, 2, 1, 4, false, _snowman, crs.e);
         this.a(_snowman, _snowman, 8, 1, 1, 9, 1, 4, false, _snowman, crs.e);
         this.a(_snowman, _snowman, 1, 1, 1, 1, 1, 3, bup.B.n(), bup.B.n(), false);
         this.a(_snowman, _snowman, 9, 1, 1, 9, 1, 3, bup.B.n(), bup.B.n(), false);
         this.a(_snowman, _snowman, 3, 1, 8, 7, 1, 12, false, _snowman, crs.e);
         this.a(_snowman, _snowman, 4, 1, 9, 6, 1, 11, bup.B.n(), bup.B.n(), false);
         ceh _snowmanx = bup.dH.n().a(bxq.a, Boolean.valueOf(true)).a(bxq.c, Boolean.valueOf(true));
         ceh _snowmanxx = bup.dH.n().a(bxq.d, Boolean.valueOf(true)).a(bxq.b, Boolean.valueOf(true));

         for (int _snowmanxxx = 3; _snowmanxxx < 14; _snowmanxxx += 2) {
            this.a(_snowman, _snowman, 0, 3, _snowmanxxx, 0, 4, _snowmanxxx, _snowmanx, _snowmanx, false);
            this.a(_snowman, _snowman, 10, 3, _snowmanxxx, 10, 4, _snowmanxxx, _snowmanx, _snowmanx, false);
         }

         for (int _snowmanxxx = 2; _snowmanxxx < 9; _snowmanxxx += 2) {
            this.a(_snowman, _snowman, _snowmanxxx, 3, 15, _snowmanxxx, 4, 15, _snowmanxx, _snowmanxx, false);
         }

         ceh _snowmanxxx = bup.dS.n().a(cak.a, gc.c);
         this.a(_snowman, _snowman, 4, 1, 5, 6, 1, 7, false, _snowman, crs.e);
         this.a(_snowman, _snowman, 4, 2, 6, 6, 2, 7, false, _snowman, crs.e);
         this.a(_snowman, _snowman, 4, 3, 7, 6, 3, 7, false, _snowman, crs.e);

         for (int _snowmanxxxx = 4; _snowmanxxxx <= 6; _snowmanxxxx++) {
            this.a(_snowman, _snowmanxxx, _snowmanxxxx, 1, 4, _snowman);
            this.a(_snowman, _snowmanxxx, _snowmanxxxx, 2, 5, _snowman);
            this.a(_snowman, _snowmanxxx, _snowmanxxxx, 3, 6, _snowman);
         }

         ceh _snowmanxxxx = bup.ed.n().a(bwj.a, gc.c);
         ceh _snowmanxxxxx = bup.ed.n().a(bwj.a, gc.d);
         ceh _snowmanxxxxxx = bup.ed.n().a(bwj.a, gc.f);
         ceh _snowmanxxxxxxx = bup.ed.n().a(bwj.a, gc.e);
         boolean _snowmanxxxxxxxx = true;
         boolean[] _snowmanxxxxxxxxx = new boolean[12];

         for (int _snowmanxxxxxxxxxx = 0; _snowmanxxxxxxxxxx < _snowmanxxxxxxxxx.length; _snowmanxxxxxxxxxx++) {
            _snowmanxxxxxxxxx[_snowmanxxxxxxxxxx] = _snowman.nextFloat() > 0.9F;
            _snowmanxxxxxxxx &= _snowmanxxxxxxxxx[_snowmanxxxxxxxxxx];
         }

         this.a(_snowman, _snowmanxxxx.a(bwj.b, Boolean.valueOf(_snowmanxxxxxxxxx[0])), 4, 3, 8, _snowman);
         this.a(_snowman, _snowmanxxxx.a(bwj.b, Boolean.valueOf(_snowmanxxxxxxxxx[1])), 5, 3, 8, _snowman);
         this.a(_snowman, _snowmanxxxx.a(bwj.b, Boolean.valueOf(_snowmanxxxxxxxxx[2])), 6, 3, 8, _snowman);
         this.a(_snowman, _snowmanxxxxx.a(bwj.b, Boolean.valueOf(_snowmanxxxxxxxxx[3])), 4, 3, 12, _snowman);
         this.a(_snowman, _snowmanxxxxx.a(bwj.b, Boolean.valueOf(_snowmanxxxxxxxxx[4])), 5, 3, 12, _snowman);
         this.a(_snowman, _snowmanxxxxx.a(bwj.b, Boolean.valueOf(_snowmanxxxxxxxxx[5])), 6, 3, 12, _snowman);
         this.a(_snowman, _snowmanxxxxxx.a(bwj.b, Boolean.valueOf(_snowmanxxxxxxxxx[6])), 3, 3, 9, _snowman);
         this.a(_snowman, _snowmanxxxxxx.a(bwj.b, Boolean.valueOf(_snowmanxxxxxxxxx[7])), 3, 3, 10, _snowman);
         this.a(_snowman, _snowmanxxxxxx.a(bwj.b, Boolean.valueOf(_snowmanxxxxxxxxx[8])), 3, 3, 11, _snowman);
         this.a(_snowman, _snowmanxxxxxxx.a(bwj.b, Boolean.valueOf(_snowmanxxxxxxxxx[9])), 7, 3, 9, _snowman);
         this.a(_snowman, _snowmanxxxxxxx.a(bwj.b, Boolean.valueOf(_snowmanxxxxxxxxx[10])), 7, 3, 10, _snowman);
         this.a(_snowman, _snowmanxxxxxxx.a(bwj.b, Boolean.valueOf(_snowmanxxxxxxxxx[11])), 7, 3, 11, _snowman);
         if (_snowmanxxxxxxxx) {
            ceh _snowmanxxxxxxxxxx = bup.ec.n();
            this.a(_snowman, _snowmanxxxxxxxxxx, 4, 3, 9, _snowman);
            this.a(_snowman, _snowmanxxxxxxxxxx, 5, 3, 9, _snowman);
            this.a(_snowman, _snowmanxxxxxxxxxx, 6, 3, 9, _snowman);
            this.a(_snowman, _snowmanxxxxxxxxxx, 4, 3, 10, _snowman);
            this.a(_snowman, _snowmanxxxxxxxxxx, 5, 3, 10, _snowman);
            this.a(_snowman, _snowmanxxxxxxxxxx, 6, 3, 10, _snowman);
            this.a(_snowman, _snowmanxxxxxxxxxx, 4, 3, 11, _snowman);
            this.a(_snowman, _snowmanxxxxxxxxxx, 5, 3, 11, _snowman);
            this.a(_snowman, _snowmanxxxxxxxxxx, 6, 3, 11, _snowman);
         }

         if (!this.a) {
            _snowman = this.d(3);
            fx _snowmanxxxxxxxxxx = new fx(this.a(5, 6), _snowman, this.b(5, 6));
            if (_snowman.b(_snowmanxxxxxxxxxx)) {
               this.a = true;
               _snowman.a(_snowmanxxxxxxxxxx, bup.bP.n(), 2);
               ccj _snowmanxxxxxxxxxxx = _snowman.c(_snowmanxxxxxxxxxx);
               if (_snowmanxxxxxxxxxxx instanceof cdi) {
                  ((cdi)_snowmanxxxxxxxxxxx).d().a(aqe.au);
               }
            }
         }

         return true;
      }
   }

   public static class h extends crs.p {
      public h(int var1, Random var2, cra var3, gc var4) {
         super(clb.z, _snowman);
         this.a(_snowman);
         this.d = this.a(_snowman);
         this.n = _snowman;
      }

      public h(csw var1, md var2) {
         super(clb.z, _snowman);
      }

      @Override
      public void a(cru var1, List<cru> var2, Random var3) {
         this.a((crs.m)_snowman, _snowman, _snowman, 1, 1);
      }

      public static crs.h a(List<cru> var0, Random var1, int var2, int var3, int var4, gc var5, int var6) {
         cra _snowman = cra.a(_snowman, _snowman, _snowman, -1, -1, 0, 9, 5, 11, _snowman);
         return a(_snowman) && cru.a(_snowman, _snowman) == null ? new crs.h(_snowman, _snowman, _snowman, _snowman) : null;
      }

      @Override
      public boolean a(bsr var1, bsn var2, cfy var3, Random var4, cra var5, brd var6, fx var7) {
         this.a(_snowman, _snowman, 0, 0, 0, 8, 4, 10, true, _snowman, crs.e);
         this.a(_snowman, _snowman, _snowman, this.d, 1, 1, 0);
         this.a(_snowman, _snowman, 1, 1, 10, 3, 3, 10, m, m, false);
         this.a(_snowman, _snowman, 4, 1, 1, 4, 3, 1, false, _snowman, crs.e);
         this.a(_snowman, _snowman, 4, 1, 3, 4, 3, 3, false, _snowman, crs.e);
         this.a(_snowman, _snowman, 4, 1, 7, 4, 3, 7, false, _snowman, crs.e);
         this.a(_snowman, _snowman, 4, 1, 9, 4, 3, 9, false, _snowman, crs.e);

         for (int _snowman = 1; _snowman <= 3; _snowman++) {
            this.a(_snowman, bup.dH.n().a(bxq.a, Boolean.valueOf(true)).a(bxq.c, Boolean.valueOf(true)), 4, _snowman, 4, _snowman);
            this.a(_snowman, bup.dH.n().a(bxq.a, Boolean.valueOf(true)).a(bxq.c, Boolean.valueOf(true)).a(bxq.b, Boolean.valueOf(true)), 4, _snowman, 5, _snowman);
            this.a(_snowman, bup.dH.n().a(bxq.a, Boolean.valueOf(true)).a(bxq.c, Boolean.valueOf(true)), 4, _snowman, 6, _snowman);
            this.a(_snowman, bup.dH.n().a(bxq.d, Boolean.valueOf(true)).a(bxq.b, Boolean.valueOf(true)), 5, _snowman, 5, _snowman);
            this.a(_snowman, bup.dH.n().a(bxq.d, Boolean.valueOf(true)).a(bxq.b, Boolean.valueOf(true)), 6, _snowman, 5, _snowman);
            this.a(_snowman, bup.dH.n().a(bxq.d, Boolean.valueOf(true)).a(bxq.b, Boolean.valueOf(true)), 7, _snowman, 5, _snowman);
         }

         this.a(_snowman, bup.dH.n().a(bxq.a, Boolean.valueOf(true)).a(bxq.c, Boolean.valueOf(true)), 4, 3, 2, _snowman);
         this.a(_snowman, bup.dH.n().a(bxq.a, Boolean.valueOf(true)).a(bxq.c, Boolean.valueOf(true)), 4, 3, 8, _snowman);
         ceh _snowman = bup.cr.n().a(bwb.a, gc.e);
         ceh _snowmanx = bup.cr.n().a(bwb.a, gc.e).a(bwb.e, cfd.a);
         this.a(_snowman, _snowman, 4, 1, 2, _snowman);
         this.a(_snowman, _snowmanx, 4, 2, 2, _snowman);
         this.a(_snowman, _snowman, 4, 1, 8, _snowman);
         this.a(_snowman, _snowmanx, 4, 2, 8, _snowman);
         return true;
      }
   }

   public static class i extends crs.q {
      public i(int var1, Random var2, cra var3, gc var4) {
         super(clb.A, _snowman);
         this.a(_snowman);
         this.d = this.a(_snowman);
         this.n = _snowman;
      }

      public i(csw var1, md var2) {
         super(clb.A, _snowman);
      }

      @Override
      public void a(cru var1, List<cru> var2, Random var3) {
         gc _snowman = this.i();
         if (_snowman != gc.c && _snowman != gc.f) {
            this.b((crs.m)_snowman, _snowman, _snowman, 1, 1);
         } else {
            this.c((crs.m)_snowman, _snowman, _snowman, 1, 1);
         }
      }

      public static crs.i a(List<cru> var0, Random var1, int var2, int var3, int var4, gc var5, int var6) {
         cra _snowman = cra.a(_snowman, _snowman, _snowman, -1, -1, 0, 5, 5, 5, _snowman);
         return a(_snowman) && cru.a(_snowman, _snowman) == null ? new crs.i(_snowman, _snowman, _snowman, _snowman) : null;
      }

      @Override
      public boolean a(bsr var1, bsn var2, cfy var3, Random var4, cra var5, brd var6, fx var7) {
         this.a(_snowman, _snowman, 0, 0, 0, 4, 4, 4, true, _snowman, crs.e);
         this.a(_snowman, _snowman, _snowman, this.d, 1, 1, 0);
         gc _snowman = this.i();
         if (_snowman != gc.c && _snowman != gc.f) {
            this.a(_snowman, _snowman, 0, 1, 1, 0, 3, 3, m, m, false);
         } else {
            this.a(_snowman, _snowman, 4, 1, 1, 4, 3, 3, m, m, false);
         }

         return true;
      }
   }

   public static class j extends crs.p {
      protected final int a;

      public j(int var1, Random var2, cra var3, gc var4) {
         super(clb.B, _snowman);
         this.a(_snowman);
         this.d = this.a(_snowman);
         this.n = _snowman;
         this.a = _snowman.nextInt(5);
      }

      public j(csw var1, md var2) {
         super(clb.B, _snowman);
         this.a = _snowman.h("Type");
      }

      @Override
      protected void a(md var1) {
         super.a(_snowman);
         _snowman.b("Type", this.a);
      }

      @Override
      public void a(cru var1, List<cru> var2, Random var3) {
         this.a((crs.m)_snowman, _snowman, _snowman, 4, 1);
         this.b((crs.m)_snowman, _snowman, _snowman, 1, 4);
         this.c((crs.m)_snowman, _snowman, _snowman, 1, 4);
      }

      public static crs.j a(List<cru> var0, Random var1, int var2, int var3, int var4, gc var5, int var6) {
         cra _snowman = cra.a(_snowman, _snowman, _snowman, -4, -1, 0, 11, 7, 11, _snowman);
         return a(_snowman) && cru.a(_snowman, _snowman) == null ? new crs.j(_snowman, _snowman, _snowman, _snowman) : null;
      }

      @Override
      public boolean a(bsr var1, bsn var2, cfy var3, Random var4, cra var5, brd var6, fx var7) {
         this.a(_snowman, _snowman, 0, 0, 0, 10, 6, 10, true, _snowman, crs.e);
         this.a(_snowman, _snowman, _snowman, this.d, 4, 1, 0);
         this.a(_snowman, _snowman, 4, 1, 10, 6, 3, 10, m, m, false);
         this.a(_snowman, _snowman, 0, 1, 4, 0, 3, 6, m, m, false);
         this.a(_snowman, _snowman, 10, 1, 4, 10, 3, 6, m, m, false);
         switch (this.a) {
            case 0:
               this.a(_snowman, bup.du.n(), 5, 1, 5, _snowman);
               this.a(_snowman, bup.du.n(), 5, 2, 5, _snowman);
               this.a(_snowman, bup.du.n(), 5, 3, 5, _snowman);
               this.a(_snowman, bup.bM.n().a(cbn.a, gc.e), 4, 3, 5, _snowman);
               this.a(_snowman, bup.bM.n().a(cbn.a, gc.f), 6, 3, 5, _snowman);
               this.a(_snowman, bup.bM.n().a(cbn.a, gc.d), 5, 3, 4, _snowman);
               this.a(_snowman, bup.bM.n().a(cbn.a, gc.c), 5, 3, 6, _snowman);
               this.a(_snowman, bup.hR.n(), 4, 1, 4, _snowman);
               this.a(_snowman, bup.hR.n(), 4, 1, 5, _snowman);
               this.a(_snowman, bup.hR.n(), 4, 1, 6, _snowman);
               this.a(_snowman, bup.hR.n(), 6, 1, 4, _snowman);
               this.a(_snowman, bup.hR.n(), 6, 1, 5, _snowman);
               this.a(_snowman, bup.hR.n(), 6, 1, 6, _snowman);
               this.a(_snowman, bup.hR.n(), 5, 1, 4, _snowman);
               this.a(_snowman, bup.hR.n(), 5, 1, 6, _snowman);
               break;
            case 1:
               for (int _snowman = 0; _snowman < 5; _snowman++) {
                  this.a(_snowman, bup.du.n(), 3, 1, 3 + _snowman, _snowman);
                  this.a(_snowman, bup.du.n(), 7, 1, 3 + _snowman, _snowman);
                  this.a(_snowman, bup.du.n(), 3 + _snowman, 1, 3, _snowman);
                  this.a(_snowman, bup.du.n(), 3 + _snowman, 1, 7, _snowman);
               }

               this.a(_snowman, bup.du.n(), 5, 1, 5, _snowman);
               this.a(_snowman, bup.du.n(), 5, 2, 5, _snowman);
               this.a(_snowman, bup.du.n(), 5, 3, 5, _snowman);
               this.a(_snowman, bup.A.n(), 5, 4, 5, _snowman);
               break;
            case 2:
               for (int _snowman = 1; _snowman <= 9; _snowman++) {
                  this.a(_snowman, bup.m.n(), 1, 3, _snowman, _snowman);
                  this.a(_snowman, bup.m.n(), 9, 3, _snowman, _snowman);
               }

               for (int _snowman = 1; _snowman <= 9; _snowman++) {
                  this.a(_snowman, bup.m.n(), _snowman, 3, 1, _snowman);
                  this.a(_snowman, bup.m.n(), _snowman, 3, 9, _snowman);
               }

               this.a(_snowman, bup.m.n(), 5, 1, 4, _snowman);
               this.a(_snowman, bup.m.n(), 5, 1, 6, _snowman);
               this.a(_snowman, bup.m.n(), 5, 3, 4, _snowman);
               this.a(_snowman, bup.m.n(), 5, 3, 6, _snowman);
               this.a(_snowman, bup.m.n(), 4, 1, 5, _snowman);
               this.a(_snowman, bup.m.n(), 6, 1, 5, _snowman);
               this.a(_snowman, bup.m.n(), 4, 3, 5, _snowman);
               this.a(_snowman, bup.m.n(), 6, 3, 5, _snowman);

               for (int _snowman = 1; _snowman <= 3; _snowman++) {
                  this.a(_snowman, bup.m.n(), 4, _snowman, 4, _snowman);
                  this.a(_snowman, bup.m.n(), 6, _snowman, 4, _snowman);
                  this.a(_snowman, bup.m.n(), 4, _snowman, 6, _snowman);
                  this.a(_snowman, bup.m.n(), 6, _snowman, 6, _snowman);
               }

               this.a(_snowman, bup.bL.n(), 5, 3, 5, _snowman);

               for (int _snowman = 2; _snowman <= 8; _snowman++) {
                  this.a(_snowman, bup.n.n(), 2, 3, _snowman, _snowman);
                  this.a(_snowman, bup.n.n(), 3, 3, _snowman, _snowman);
                  if (_snowman <= 3 || _snowman >= 7) {
                     this.a(_snowman, bup.n.n(), 4, 3, _snowman, _snowman);
                     this.a(_snowman, bup.n.n(), 5, 3, _snowman, _snowman);
                     this.a(_snowman, bup.n.n(), 6, 3, _snowman, _snowman);
                  }

                  this.a(_snowman, bup.n.n(), 7, 3, _snowman, _snowman);
                  this.a(_snowman, bup.n.n(), 8, 3, _snowman, _snowman);
               }

               ceh _snowman = bup.cg.n().a(bxv.a, gc.e);
               this.a(_snowman, _snowman, 9, 1, 3, _snowman);
               this.a(_snowman, _snowman, 9, 2, 3, _snowman);
               this.a(_snowman, _snowman, 9, 3, 3, _snowman);
               this.a(_snowman, _snowman, _snowman, 3, 4, 8, cyq.x);
         }

         return true;
      }
   }

   static class k extends cru.a {
      private k() {
      }

      @Override
      public void a(Random var1, int var2, int var3, int var4, boolean var5) {
         if (_snowman) {
            float _snowman = _snowman.nextFloat();
            if (_snowman < 0.2F) {
               this.a = bup.dw.n();
            } else if (_snowman < 0.5F) {
               this.a = bup.dv.n();
            } else if (_snowman < 0.55F) {
               this.a = bup.dA.n();
            } else {
               this.a = bup.du.n();
            }
         } else {
            this.a = bup.lb.n();
         }
      }
   }

   public static class l extends crs.p {
      private final boolean a;

      public l(clb var1, int var2, Random var3, int var4, int var5) {
         super(_snowman, _snowman);
         this.a = true;
         this.a(gc.c.a.a(_snowman));
         this.d = crs.p.a.a;
         if (this.i().n() == gc.a.c) {
            this.n = new cra(_snowman, 64, _snowman, _snowman + 5 - 1, 74, _snowman + 5 - 1);
         } else {
            this.n = new cra(_snowman, 64, _snowman, _snowman + 5 - 1, 74, _snowman + 5 - 1);
         }
      }

      public l(int var1, Random var2, cra var3, gc var4) {
         super(clb.C, _snowman);
         this.a = false;
         this.a(_snowman);
         this.d = this.a(_snowman);
         this.n = _snowman;
      }

      public l(clb var1, md var2) {
         super(_snowman, _snowman);
         this.a = _snowman.q("Source");
      }

      public l(csw var1, md var2) {
         this(clb.C, _snowman);
      }

      @Override
      protected void a(md var1) {
         super.a(_snowman);
         _snowman.a("Source", this.a);
      }

      @Override
      public void a(cru var1, List<cru> var2, Random var3) {
         if (this.a) {
            crs.c = crs.c.class;
         }

         this.a((crs.m)_snowman, _snowman, _snowman, 1, 1);
      }

      public static crs.l a(List<cru> var0, Random var1, int var2, int var3, int var4, gc var5, int var6) {
         cra _snowman = cra.a(_snowman, _snowman, _snowman, -1, -7, 0, 5, 11, 5, _snowman);
         return a(_snowman) && cru.a(_snowman, _snowman) == null ? new crs.l(_snowman, _snowman, _snowman, _snowman) : null;
      }

      @Override
      public boolean a(bsr var1, bsn var2, cfy var3, Random var4, cra var5, brd var6, fx var7) {
         this.a(_snowman, _snowman, 0, 0, 0, 4, 10, 4, true, _snowman, crs.e);
         this.a(_snowman, _snowman, _snowman, this.d, 1, 7, 0);
         this.a(_snowman, _snowman, _snowman, crs.p.a.a, 1, 1, 4);
         this.a(_snowman, bup.du.n(), 2, 6, 1, _snowman);
         this.a(_snowman, bup.du.n(), 1, 5, 1, _snowman);
         this.a(_snowman, bup.hR.n(), 1, 6, 1, _snowman);
         this.a(_snowman, bup.du.n(), 1, 5, 2, _snowman);
         this.a(_snowman, bup.du.n(), 1, 4, 3, _snowman);
         this.a(_snowman, bup.hR.n(), 1, 5, 3, _snowman);
         this.a(_snowman, bup.du.n(), 2, 4, 3, _snowman);
         this.a(_snowman, bup.du.n(), 3, 3, 3, _snowman);
         this.a(_snowman, bup.hR.n(), 3, 4, 3, _snowman);
         this.a(_snowman, bup.du.n(), 3, 3, 2, _snowman);
         this.a(_snowman, bup.du.n(), 3, 2, 1, _snowman);
         this.a(_snowman, bup.hR.n(), 3, 3, 1, _snowman);
         this.a(_snowman, bup.du.n(), 2, 2, 1, _snowman);
         this.a(_snowman, bup.du.n(), 1, 1, 1, _snowman);
         this.a(_snowman, bup.hR.n(), 1, 2, 1, _snowman);
         this.a(_snowman, bup.du.n(), 1, 1, 2, _snowman);
         this.a(_snowman, bup.hR.n(), 1, 1, 3, _snowman);
         return true;
      }
   }

   public static class m extends crs.l {
      public crs.f a;
      @Nullable
      public crs.g b;
      public final List<cru> c = Lists.newArrayList();

      public m(Random var1, int var2, int var3) {
         super(clb.D, 0, _snowman, _snowman, _snowman);
      }

      public m(csw var1, md var2) {
         super(clb.D, _snowman);
      }
   }

   public static class n extends crs.p {
      private final boolean a;
      private final boolean b;

      public n(int var1, Random var2, cra var3, gc var4) {
         super(clb.E, _snowman);
         this.a(_snowman);
         this.d = this.a(_snowman);
         this.n = _snowman;
         this.a = _snowman.nextInt(2) == 0;
         this.b = _snowman.nextInt(2) == 0;
      }

      public n(csw var1, md var2) {
         super(clb.E, _snowman);
         this.a = _snowman.q("Left");
         this.b = _snowman.q("Right");
      }

      @Override
      protected void a(md var1) {
         super.a(_snowman);
         _snowman.a("Left", this.a);
         _snowman.a("Right", this.b);
      }

      @Override
      public void a(cru var1, List<cru> var2, Random var3) {
         this.a((crs.m)_snowman, _snowman, _snowman, 1, 1);
         if (this.a) {
            this.b((crs.m)_snowman, _snowman, _snowman, 1, 2);
         }

         if (this.b) {
            this.c((crs.m)_snowman, _snowman, _snowman, 1, 2);
         }
      }

      public static crs.n a(List<cru> var0, Random var1, int var2, int var3, int var4, gc var5, int var6) {
         cra _snowman = cra.a(_snowman, _snowman, _snowman, -1, -1, 0, 5, 5, 7, _snowman);
         return a(_snowman) && cru.a(_snowman, _snowman) == null ? new crs.n(_snowman, _snowman, _snowman, _snowman) : null;
      }

      @Override
      public boolean a(bsr var1, bsn var2, cfy var3, Random var4, cra var5, brd var6, fx var7) {
         this.a(_snowman, _snowman, 0, 0, 0, 4, 4, 6, true, _snowman, crs.e);
         this.a(_snowman, _snowman, _snowman, this.d, 1, 1, 0);
         this.a(_snowman, _snowman, _snowman, crs.p.a.a, 1, 1, 6);
         ceh _snowman = bup.bM.n().a(cbn.a, gc.f);
         ceh _snowmanx = bup.bM.n().a(cbn.a, gc.e);
         this.a(_snowman, _snowman, _snowman, 0.1F, 1, 2, 1, _snowman);
         this.a(_snowman, _snowman, _snowman, 0.1F, 3, 2, 1, _snowmanx);
         this.a(_snowman, _snowman, _snowman, 0.1F, 1, 2, 5, _snowman);
         this.a(_snowman, _snowman, _snowman, 0.1F, 3, 2, 5, _snowmanx);
         if (this.a) {
            this.a(_snowman, _snowman, 0, 1, 2, 0, 3, 4, m, m, false);
         }

         if (this.b) {
            this.a(_snowman, _snowman, 4, 1, 2, 4, 3, 4, m, m, false);
         }

         return true;
      }
   }

   public static class o extends crs.p {
      public o(int var1, Random var2, cra var3, gc var4) {
         super(clb.F, _snowman);
         this.a(_snowman);
         this.d = this.a(_snowman);
         this.n = _snowman;
      }

      public o(csw var1, md var2) {
         super(clb.F, _snowman);
      }

      @Override
      public void a(cru var1, List<cru> var2, Random var3) {
         this.a((crs.m)_snowman, _snowman, _snowman, 1, 1);
      }

      public static crs.o a(List<cru> var0, Random var1, int var2, int var3, int var4, gc var5, int var6) {
         cra _snowman = cra.a(_snowman, _snowman, _snowman, -1, -7, 0, 5, 11, 8, _snowman);
         return a(_snowman) && cru.a(_snowman, _snowman) == null ? new crs.o(_snowman, _snowman, _snowman, _snowman) : null;
      }

      @Override
      public boolean a(bsr var1, bsn var2, cfy var3, Random var4, cra var5, brd var6, fx var7) {
         this.a(_snowman, _snowman, 0, 0, 0, 4, 10, 7, true, _snowman, crs.e);
         this.a(_snowman, _snowman, _snowman, this.d, 1, 7, 0);
         this.a(_snowman, _snowman, _snowman, crs.p.a.a, 1, 1, 7);
         ceh _snowman = bup.ci.n().a(cak.a, gc.d);

         for (int _snowmanx = 0; _snowmanx < 6; _snowmanx++) {
            this.a(_snowman, _snowman, 1, 6 - _snowmanx, 1 + _snowmanx, _snowman);
            this.a(_snowman, _snowman, 2, 6 - _snowmanx, 1 + _snowmanx, _snowman);
            this.a(_snowman, _snowman, 3, 6 - _snowmanx, 1 + _snowmanx, _snowman);
            if (_snowmanx < 5) {
               this.a(_snowman, bup.du.n(), 1, 5 - _snowmanx, 1 + _snowmanx, _snowman);
               this.a(_snowman, bup.du.n(), 2, 5 - _snowmanx, 1 + _snowmanx, _snowman);
               this.a(_snowman, bup.du.n(), 3, 5 - _snowmanx, 1 + _snowmanx, _snowman);
            }
         }

         return true;
      }
   }

   abstract static class p extends cru {
      protected crs.p.a d = crs.p.a.a;

      protected p(clb var1, int var2) {
         super(_snowman, _snowman);
      }

      public p(clb var1, md var2) {
         super(_snowman, _snowman);
         this.d = crs.p.a.valueOf(_snowman.l("EntryDoor"));
      }

      @Override
      protected void a(md var1) {
         _snowman.a("EntryDoor", this.d.name());
      }

      protected void a(bsr var1, Random var2, cra var3, crs.p.a var4, int var5, int var6, int var7) {
         switch (_snowman) {
            case a:
               this.a(_snowman, _snowman, _snowman, _snowman, _snowman, _snowman + 3 - 1, _snowman + 3 - 1, _snowman, m, m, false);
               break;
            case b:
               this.a(_snowman, bup.du.n(), _snowman, _snowman, _snowman, _snowman);
               this.a(_snowman, bup.du.n(), _snowman, _snowman + 1, _snowman, _snowman);
               this.a(_snowman, bup.du.n(), _snowman, _snowman + 2, _snowman, _snowman);
               this.a(_snowman, bup.du.n(), _snowman + 1, _snowman + 2, _snowman, _snowman);
               this.a(_snowman, bup.du.n(), _snowman + 2, _snowman + 2, _snowman, _snowman);
               this.a(_snowman, bup.du.n(), _snowman + 2, _snowman + 1, _snowman, _snowman);
               this.a(_snowman, bup.du.n(), _snowman + 2, _snowman, _snowman, _snowman);
               this.a(_snowman, bup.cf.n(), _snowman + 1, _snowman, _snowman, _snowman);
               this.a(_snowman, bup.cf.n().a(bwb.e, cfd.a), _snowman + 1, _snowman + 1, _snowman, _snowman);
               break;
            case c:
               this.a(_snowman, bup.lb.n(), _snowman + 1, _snowman, _snowman, _snowman);
               this.a(_snowman, bup.lb.n(), _snowman + 1, _snowman + 1, _snowman, _snowman);
               this.a(_snowman, bup.dH.n().a(bxq.d, Boolean.valueOf(true)), _snowman, _snowman, _snowman, _snowman);
               this.a(_snowman, bup.dH.n().a(bxq.d, Boolean.valueOf(true)), _snowman, _snowman + 1, _snowman, _snowman);
               this.a(_snowman, bup.dH.n().a(bxq.b, Boolean.valueOf(true)).a(bxq.d, Boolean.valueOf(true)), _snowman, _snowman + 2, _snowman, _snowman);
               this.a(_snowman, bup.dH.n().a(bxq.b, Boolean.valueOf(true)).a(bxq.d, Boolean.valueOf(true)), _snowman + 1, _snowman + 2, _snowman, _snowman);
               this.a(_snowman, bup.dH.n().a(bxq.b, Boolean.valueOf(true)).a(bxq.d, Boolean.valueOf(true)), _snowman + 2, _snowman + 2, _snowman, _snowman);
               this.a(_snowman, bup.dH.n().a(bxq.b, Boolean.valueOf(true)), _snowman + 2, _snowman + 1, _snowman, _snowman);
               this.a(_snowman, bup.dH.n().a(bxq.b, Boolean.valueOf(true)), _snowman + 2, _snowman, _snowman, _snowman);
               break;
            case d:
               this.a(_snowman, bup.du.n(), _snowman, _snowman, _snowman, _snowman);
               this.a(_snowman, bup.du.n(), _snowman, _snowman + 1, _snowman, _snowman);
               this.a(_snowman, bup.du.n(), _snowman, _snowman + 2, _snowman, _snowman);
               this.a(_snowman, bup.du.n(), _snowman + 1, _snowman + 2, _snowman, _snowman);
               this.a(_snowman, bup.du.n(), _snowman + 2, _snowman + 2, _snowman, _snowman);
               this.a(_snowman, bup.du.n(), _snowman + 2, _snowman + 1, _snowman, _snowman);
               this.a(_snowman, bup.du.n(), _snowman + 2, _snowman, _snowman, _snowman);
               this.a(_snowman, bup.cr.n(), _snowman + 1, _snowman, _snowman, _snowman);
               this.a(_snowman, bup.cr.n().a(bwb.e, cfd.a), _snowman + 1, _snowman + 1, _snowman, _snowman);
               this.a(_snowman, bup.cB.n().a(buv.aq, gc.c), _snowman + 2, _snowman + 1, _snowman + 1, _snowman);
               this.a(_snowman, bup.cB.n().a(buv.aq, gc.d), _snowman + 2, _snowman + 1, _snowman - 1, _snowman);
         }
      }

      protected crs.p.a a(Random var1) {
         int _snowman = _snowman.nextInt(5);
         switch (_snowman) {
            case 0:
            case 1:
            default:
               return crs.p.a.a;
            case 2:
               return crs.p.a.b;
            case 3:
               return crs.p.a.c;
            case 4:
               return crs.p.a.d;
         }
      }

      @Nullable
      protected cru a(crs.m var1, List<cru> var2, Random var3, int var4, int var5) {
         gc _snowman = this.i();
         if (_snowman != null) {
            switch (_snowman) {
               case c:
                  return crs.c(_snowman, _snowman, _snowman, this.n.a + _snowman, this.n.b + _snowman, this.n.c - 1, _snowman, this.h());
               case d:
                  return crs.c(_snowman, _snowman, _snowman, this.n.a + _snowman, this.n.b + _snowman, this.n.f + 1, _snowman, this.h());
               case e:
                  return crs.c(_snowman, _snowman, _snowman, this.n.a - 1, this.n.b + _snowman, this.n.c + _snowman, _snowman, this.h());
               case f:
                  return crs.c(_snowman, _snowman, _snowman, this.n.d + 1, this.n.b + _snowman, this.n.c + _snowman, _snowman, this.h());
            }
         }

         return null;
      }

      @Nullable
      protected cru b(crs.m var1, List<cru> var2, Random var3, int var4, int var5) {
         gc _snowman = this.i();
         if (_snowman != null) {
            switch (_snowman) {
               case c:
                  return crs.c(_snowman, _snowman, _snowman, this.n.a - 1, this.n.b + _snowman, this.n.c + _snowman, gc.e, this.h());
               case d:
                  return crs.c(_snowman, _snowman, _snowman, this.n.a - 1, this.n.b + _snowman, this.n.c + _snowman, gc.e, this.h());
               case e:
                  return crs.c(_snowman, _snowman, _snowman, this.n.a + _snowman, this.n.b + _snowman, this.n.c - 1, gc.c, this.h());
               case f:
                  return crs.c(_snowman, _snowman, _snowman, this.n.a + _snowman, this.n.b + _snowman, this.n.c - 1, gc.c, this.h());
            }
         }

         return null;
      }

      @Nullable
      protected cru c(crs.m var1, List<cru> var2, Random var3, int var4, int var5) {
         gc _snowman = this.i();
         if (_snowman != null) {
            switch (_snowman) {
               case c:
                  return crs.c(_snowman, _snowman, _snowman, this.n.d + 1, this.n.b + _snowman, this.n.c + _snowman, gc.f, this.h());
               case d:
                  return crs.c(_snowman, _snowman, _snowman, this.n.d + 1, this.n.b + _snowman, this.n.c + _snowman, gc.f, this.h());
               case e:
                  return crs.c(_snowman, _snowman, _snowman, this.n.a + _snowman, this.n.b + _snowman, this.n.f + 1, gc.d, this.h());
               case f:
                  return crs.c(_snowman, _snowman, _snowman, this.n.a + _snowman, this.n.b + _snowman, this.n.f + 1, gc.d, this.h());
            }
         }

         return null;
      }

      protected static boolean a(cra var0) {
         return _snowman != null && _snowman.b > 10;
      }

      public static enum a {
         a,
         b,
         c,
         d;

         private a() {
         }
      }
   }

   public abstract static class q extends crs.p {
      protected q(clb var1, int var2) {
         super(_snowman, _snowman);
      }

      public q(clb var1, md var2) {
         super(_snowman, _snowman);
      }
   }
}
