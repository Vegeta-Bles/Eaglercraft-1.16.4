import com.google.common.collect.Lists;
import java.util.List;
import java.util.Random;
import javax.annotation.Nullable;

public class crh {
   private static crh.c a(List<cru> var0, Random var1, int var2, int var3, int var4, @Nullable gc var5, int var6, ckb.b var7) {
      int _snowman = _snowman.nextInt(100);
      if (_snowman >= 80) {
         cra _snowmanx = crh.b.a(_snowman, _snowman, _snowman, _snowman, _snowman, _snowman);
         if (_snowmanx != null) {
            return new crh.b(_snowman, _snowmanx, _snowman, _snowman);
         }
      } else if (_snowman >= 70) {
         cra _snowmanx = crh.e.a(_snowman, _snowman, _snowman, _snowman, _snowman, _snowman);
         if (_snowmanx != null) {
            return new crh.e(_snowman, _snowmanx, _snowman, _snowman);
         }
      } else {
         cra _snowmanx = crh.a.a(_snowman, _snowman, _snowman, _snowman, _snowman, _snowman);
         if (_snowmanx != null) {
            return new crh.a(_snowman, _snowman, _snowmanx, _snowman, _snowman);
         }
      }

      return null;
   }

   private static crh.c b(cru var0, List<cru> var1, Random var2, int var3, int var4, int var5, gc var6, int var7) {
      if (_snowman > 8) {
         return null;
      } else if (Math.abs(_snowman - _snowman.g().a) <= 80 && Math.abs(_snowman - _snowman.g().c) <= 80) {
         ckb.b _snowman = ((crh.c)_snowman).a;
         crh.c _snowmanx = a(_snowman, _snowman, _snowman, _snowman, _snowman, _snowman, _snowman + 1, _snowman);
         if (_snowmanx != null) {
            _snowman.add(_snowmanx);
            _snowmanx.a(_snowman, _snowman, _snowman);
         }

         return _snowmanx;
      } else {
         return null;
      }
   }

   public static class a extends crh.c {
      private final boolean b;
      private final boolean c;
      private boolean d;
      private final int e;

      public a(csw var1, md var2) {
         super(clb.a, _snowman);
         this.b = _snowman.q("hr");
         this.c = _snowman.q("sc");
         this.d = _snowman.q("hps");
         this.e = _snowman.h("Num");
      }

      @Override
      protected void a(md var1) {
         super.a(_snowman);
         _snowman.a("hr", this.b);
         _snowman.a("sc", this.c);
         _snowman.a("hps", this.d);
         _snowman.b("Num", this.e);
      }

      public a(int var1, Random var2, cra var3, gc var4, ckb.b var5) {
         super(clb.a, _snowman, _snowman);
         this.a(_snowman);
         this.n = _snowman;
         this.b = _snowman.nextInt(3) == 0;
         this.c = !this.b && _snowman.nextInt(23) == 0;
         if (this.i().n() == gc.a.c) {
            this.e = _snowman.f() / 5;
         } else {
            this.e = _snowman.d() / 5;
         }
      }

      public static cra a(List<cru> var0, Random var1, int var2, int var3, int var4, gc var5) {
         cra _snowman = new cra(_snowman, _snowman, _snowman, _snowman, _snowman + 3 - 1, _snowman);

         int _snowmanx;
         for (_snowmanx = _snowman.nextInt(3) + 2; _snowmanx > 0; _snowmanx--) {
            int _snowmanxx = _snowmanx * 5;
            switch (_snowman) {
               case c:
               default:
                  _snowman.d = _snowman + 3 - 1;
                  _snowman.c = _snowman - (_snowmanxx - 1);
                  break;
               case d:
                  _snowman.d = _snowman + 3 - 1;
                  _snowman.f = _snowman + _snowmanxx - 1;
                  break;
               case e:
                  _snowman.a = _snowman - (_snowmanxx - 1);
                  _snowman.f = _snowman + 3 - 1;
                  break;
               case f:
                  _snowman.d = _snowman + _snowmanxx - 1;
                  _snowman.f = _snowman + 3 - 1;
            }

            if (cru.a(_snowman, _snowman) == null) {
               break;
            }
         }

         return _snowmanx > 0 ? _snowman : null;
      }

      @Override
      public void a(cru var1, List<cru> var2, Random var3) {
         int _snowman = this.h();
         int _snowmanx = _snowman.nextInt(4);
         gc _snowmanxx = this.i();
         if (_snowmanxx != null) {
            switch (_snowmanxx) {
               case c:
               default:
                  if (_snowmanx <= 1) {
                     crh.b(_snowman, _snowman, _snowman, this.n.a, this.n.b - 1 + _snowman.nextInt(3), this.n.c - 1, _snowmanxx, _snowman);
                  } else if (_snowmanx == 2) {
                     crh.b(_snowman, _snowman, _snowman, this.n.a - 1, this.n.b - 1 + _snowman.nextInt(3), this.n.c, gc.e, _snowman);
                  } else {
                     crh.b(_snowman, _snowman, _snowman, this.n.d + 1, this.n.b - 1 + _snowman.nextInt(3), this.n.c, gc.f, _snowman);
                  }
                  break;
               case d:
                  if (_snowmanx <= 1) {
                     crh.b(_snowman, _snowman, _snowman, this.n.a, this.n.b - 1 + _snowman.nextInt(3), this.n.f + 1, _snowmanxx, _snowman);
                  } else if (_snowmanx == 2) {
                     crh.b(_snowman, _snowman, _snowman, this.n.a - 1, this.n.b - 1 + _snowman.nextInt(3), this.n.f - 3, gc.e, _snowman);
                  } else {
                     crh.b(_snowman, _snowman, _snowman, this.n.d + 1, this.n.b - 1 + _snowman.nextInt(3), this.n.f - 3, gc.f, _snowman);
                  }
                  break;
               case e:
                  if (_snowmanx <= 1) {
                     crh.b(_snowman, _snowman, _snowman, this.n.a - 1, this.n.b - 1 + _snowman.nextInt(3), this.n.c, _snowmanxx, _snowman);
                  } else if (_snowmanx == 2) {
                     crh.b(_snowman, _snowman, _snowman, this.n.a, this.n.b - 1 + _snowman.nextInt(3), this.n.c - 1, gc.c, _snowman);
                  } else {
                     crh.b(_snowman, _snowman, _snowman, this.n.a, this.n.b - 1 + _snowman.nextInt(3), this.n.f + 1, gc.d, _snowman);
                  }
                  break;
               case f:
                  if (_snowmanx <= 1) {
                     crh.b(_snowman, _snowman, _snowman, this.n.d + 1, this.n.b - 1 + _snowman.nextInt(3), this.n.c, _snowmanxx, _snowman);
                  } else if (_snowmanx == 2) {
                     crh.b(_snowman, _snowman, _snowman, this.n.d - 3, this.n.b - 1 + _snowman.nextInt(3), this.n.c - 1, gc.c, _snowman);
                  } else {
                     crh.b(_snowman, _snowman, _snowman, this.n.d - 3, this.n.b - 1 + _snowman.nextInt(3), this.n.f + 1, gc.d, _snowman);
                  }
            }
         }

         if (_snowman < 8) {
            if (_snowmanxx != gc.c && _snowmanxx != gc.d) {
               for (int _snowmanxxx = this.n.a + 3; _snowmanxxx + 3 <= this.n.d; _snowmanxxx += 5) {
                  int _snowmanxxxx = _snowman.nextInt(5);
                  if (_snowmanxxxx == 0) {
                     crh.b(_snowman, _snowman, _snowman, _snowmanxxx, this.n.b, this.n.c - 1, gc.c, _snowman + 1);
                  } else if (_snowmanxxxx == 1) {
                     crh.b(_snowman, _snowman, _snowman, _snowmanxxx, this.n.b, this.n.f + 1, gc.d, _snowman + 1);
                  }
               }
            } else {
               for (int _snowmanxxxx = this.n.c + 3; _snowmanxxxx + 3 <= this.n.f; _snowmanxxxx += 5) {
                  int _snowmanxxxxx = _snowman.nextInt(5);
                  if (_snowmanxxxxx == 0) {
                     crh.b(_snowman, _snowman, _snowman, this.n.a - 1, this.n.b, _snowmanxxxx, gc.e, _snowman + 1);
                  } else if (_snowmanxxxxx == 1) {
                     crh.b(_snowman, _snowman, _snowman, this.n.d + 1, this.n.b, _snowmanxxxx, gc.f, _snowman + 1);
                  }
               }
            }
         }
      }

      @Override
      protected boolean a(bsr var1, cra var2, Random var3, int var4, int var5, int var6, vk var7) {
         fx _snowman = new fx(this.a(_snowman, _snowman), this.d(_snowman), this.b(_snowman, _snowman));
         if (_snowman.b(_snowman) && _snowman.d_(_snowman).g() && !_snowman.d_(_snowman.c()).g()) {
            ceh _snowmanx = bup.ch.n().a(bza.c, _snowman.nextBoolean() ? cfk.a : cfk.b);
            this.a(_snowman, _snowmanx, _snowman, _snowman, _snowman, _snowman);
            bhq _snowmanxx = new bhq(_snowman.E(), (double)_snowman.u() + 0.5, (double)_snowman.v() + 0.5, (double)_snowman.w() + 0.5);
            _snowmanxx.a(_snowman, _snowman.nextLong());
            _snowman.c(_snowmanxx);
            return true;
         } else {
            return false;
         }
      }

      @Override
      public boolean a(bsr var1, bsn var2, cfy var3, Random var4, cra var5, brd var6, fx var7) {
         if (this.a(_snowman, _snowman)) {
            return false;
         } else {
            int _snowman = 0;
            int _snowmanx = 2;
            int _snowmanxx = 0;
            int _snowmanxxx = 2;
            int _snowmanxxxx = this.e * 5 - 1;
            ceh _snowmanxxxxx = this.a();
            this.a(_snowman, _snowman, 0, 0, 0, 2, 1, _snowmanxxxx, m, m, false);
            this.a(_snowman, _snowman, _snowman, 0.8F, 0, 2, 0, 2, 2, _snowmanxxxx, m, m, false, false);
            if (this.c) {
               this.a(_snowman, _snowman, _snowman, 0.6F, 0, 0, 0, 2, 1, _snowmanxxxx, bup.aQ.n(), m, false, true);
            }

            for (int _snowmanxxxxxx = 0; _snowmanxxxxxx < this.e; _snowmanxxxxxx++) {
               int _snowmanxxxxxxx = 2 + _snowmanxxxxxx * 5;
               this.a(_snowman, _snowman, 0, 0, _snowmanxxxxxxx, 2, 2, _snowman);
               this.a(_snowman, _snowman, _snowman, 0.1F, 0, 2, _snowmanxxxxxxx - 1);
               this.a(_snowman, _snowman, _snowman, 0.1F, 2, 2, _snowmanxxxxxxx - 1);
               this.a(_snowman, _snowman, _snowman, 0.1F, 0, 2, _snowmanxxxxxxx + 1);
               this.a(_snowman, _snowman, _snowman, 0.1F, 2, 2, _snowmanxxxxxxx + 1);
               this.a(_snowman, _snowman, _snowman, 0.05F, 0, 2, _snowmanxxxxxxx - 2);
               this.a(_snowman, _snowman, _snowman, 0.05F, 2, 2, _snowmanxxxxxxx - 2);
               this.a(_snowman, _snowman, _snowman, 0.05F, 0, 2, _snowmanxxxxxxx + 2);
               this.a(_snowman, _snowman, _snowman, 0.05F, 2, 2, _snowmanxxxxxxx + 2);
               if (_snowman.nextInt(100) == 0) {
                  this.a(_snowman, _snowman, _snowman, 2, 0, _snowmanxxxxxxx - 1, cyq.u);
               }

               if (_snowman.nextInt(100) == 0) {
                  this.a(_snowman, _snowman, _snowman, 0, 0, _snowmanxxxxxxx + 1, cyq.u);
               }

               if (this.c && !this.d) {
                  int _snowmanxxxxxxxx = this.d(0);
                  int _snowmanxxxxxxxxx = _snowmanxxxxxxx - 1 + _snowman.nextInt(3);
                  int _snowmanxxxxxxxxxx = this.a(1, _snowmanxxxxxxxxx);
                  int _snowmanxxxxxxxxxxx = this.b(1, _snowmanxxxxxxxxx);
                  fx _snowmanxxxxxxxxxxxx = new fx(_snowmanxxxxxxxxxx, _snowmanxxxxxxxx, _snowmanxxxxxxxxxxx);
                  if (_snowman.b(_snowmanxxxxxxxxxxxx) && this.a(_snowman, 1, 0, _snowmanxxxxxxxxx, _snowman)) {
                     this.d = true;
                     _snowman.a(_snowmanxxxxxxxxxxxx, bup.bP.n(), 2);
                     ccj _snowmanxxxxxxxxxxxxx = _snowman.c(_snowmanxxxxxxxxxxxx);
                     if (_snowmanxxxxxxxxxxxxx instanceof cdi) {
                        ((cdi)_snowmanxxxxxxxxxxxxx).d().a(aqe.i);
                     }
                  }
               }
            }

            for (int _snowmanxxxxxx = 0; _snowmanxxxxxx <= 2; _snowmanxxxxxx++) {
               for (int _snowmanxxxxxxxx = 0; _snowmanxxxxxxxx <= _snowmanxxxx; _snowmanxxxxxxxx++) {
                  int _snowmanxxxxxxxxx = -1;
                  ceh _snowmanxxxxxxxxxx = this.a(_snowman, _snowmanxxxxxx, -1, _snowmanxxxxxxxx, _snowman);
                  if (_snowmanxxxxxxxxxx.g() && this.a(_snowman, _snowmanxxxxxx, -1, _snowmanxxxxxxxx, _snowman)) {
                     int _snowmanxxxxxxxxxxx = -1;
                     this.a(_snowman, _snowmanxxxxx, _snowmanxxxxxx, -1, _snowmanxxxxxxxx, _snowman);
                  }
               }
            }

            if (this.b) {
               ceh _snowmanxxxxxx = bup.ch.n().a(bza.c, cfk.a);

               for (int _snowmanxxxxxxxxx = 0; _snowmanxxxxxxxxx <= _snowmanxxxx; _snowmanxxxxxxxxx++) {
                  ceh _snowmanxxxxxxxxxx = this.a(_snowman, 1, -1, _snowmanxxxxxxxxx, _snowman);
                  if (!_snowmanxxxxxxxxxx.g() && _snowmanxxxxxxxxxx.i(_snowman, new fx(this.a(1, _snowmanxxxxxxxxx), this.d(-1), this.b(1, _snowmanxxxxxxxxx)))) {
                     float _snowmanxxxxxxxxxxx = this.a(_snowman, 1, 0, _snowmanxxxxxxxxx, _snowman) ? 0.7F : 0.9F;
                     this.a(_snowman, _snowman, _snowman, _snowmanxxxxxxxxxxx, 1, 0, _snowmanxxxxxxxxx, _snowmanxxxxxx);
                  }
               }
            }

            return true;
         }
      }

      private void a(bsr var1, cra var2, int var3, int var4, int var5, int var6, int var7, Random var8) {
         if (this.a(_snowman, _snowman, _snowman, _snowman, _snowman, _snowman)) {
            ceh _snowman = this.a();
            ceh _snowmanx = this.b();
            this.a(_snowman, _snowman, _snowman, _snowman, _snowman, _snowman, _snowman - 1, _snowman, _snowmanx.a(bwq.d, Boolean.valueOf(true)), m, false);
            this.a(_snowman, _snowman, _snowman, _snowman, _snowman, _snowman, _snowman - 1, _snowman, _snowmanx.a(bwq.b, Boolean.valueOf(true)), m, false);
            if (_snowman.nextInt(4) == 0) {
               this.a(_snowman, _snowman, _snowman, _snowman, _snowman, _snowman, _snowman, _snowman, _snowman, m, false);
               this.a(_snowman, _snowman, _snowman, _snowman, _snowman, _snowman, _snowman, _snowman, _snowman, m, false);
            } else {
               this.a(_snowman, _snowman, _snowman, _snowman, _snowman, _snowman, _snowman, _snowman, _snowman, m, false);
               this.a(_snowman, _snowman, _snowman, 0.05F, _snowman + 1, _snowman, _snowman - 1, bup.bM.n().a(cbn.a, gc.c));
               this.a(_snowman, _snowman, _snowman, 0.05F, _snowman + 1, _snowman, _snowman + 1, bup.bM.n().a(cbn.a, gc.d));
            }
         }
      }

      private void a(bsr var1, cra var2, Random var3, float var4, int var5, int var6, int var7) {
         if (this.a(_snowman, _snowman, _snowman, _snowman, _snowman)) {
            this.a(_snowman, _snowman, _snowman, _snowman, _snowman, _snowman, _snowman, bup.aQ.n());
         }
      }
   }

   public static class b extends crh.c {
      private final gc b;
      private final boolean c;

      public b(csw var1, md var2) {
         super(clb.b, _snowman);
         this.c = _snowman.q("tf");
         this.b = gc.b(_snowman.h("D"));
      }

      @Override
      protected void a(md var1) {
         super.a(_snowman);
         _snowman.a("tf", this.c);
         _snowman.b("D", this.b.d());
      }

      public b(int var1, cra var2, @Nullable gc var3, ckb.b var4) {
         super(clb.b, _snowman, _snowman);
         this.b = _snowman;
         this.n = _snowman;
         this.c = _snowman.e() > 3;
      }

      public static cra a(List<cru> var0, Random var1, int var2, int var3, int var4, gc var5) {
         cra _snowman = new cra(_snowman, _snowman, _snowman, _snowman, _snowman + 3 - 1, _snowman);
         if (_snowman.nextInt(4) == 0) {
            _snowman.e += 4;
         }

         switch (_snowman) {
            case c:
            default:
               _snowman.a = _snowman - 1;
               _snowman.d = _snowman + 3;
               _snowman.c = _snowman - 4;
               break;
            case d:
               _snowman.a = _snowman - 1;
               _snowman.d = _snowman + 3;
               _snowman.f = _snowman + 3 + 1;
               break;
            case e:
               _snowman.a = _snowman - 4;
               _snowman.c = _snowman - 1;
               _snowman.f = _snowman + 3;
               break;
            case f:
               _snowman.d = _snowman + 3 + 1;
               _snowman.c = _snowman - 1;
               _snowman.f = _snowman + 3;
         }

         return cru.a(_snowman, _snowman) != null ? null : _snowman;
      }

      @Override
      public void a(cru var1, List<cru> var2, Random var3) {
         int _snowman = this.h();
         switch (this.b) {
            case c:
            default:
               crh.b(_snowman, _snowman, _snowman, this.n.a + 1, this.n.b, this.n.c - 1, gc.c, _snowman);
               crh.b(_snowman, _snowman, _snowman, this.n.a - 1, this.n.b, this.n.c + 1, gc.e, _snowman);
               crh.b(_snowman, _snowman, _snowman, this.n.d + 1, this.n.b, this.n.c + 1, gc.f, _snowman);
               break;
            case d:
               crh.b(_snowman, _snowman, _snowman, this.n.a + 1, this.n.b, this.n.f + 1, gc.d, _snowman);
               crh.b(_snowman, _snowman, _snowman, this.n.a - 1, this.n.b, this.n.c + 1, gc.e, _snowman);
               crh.b(_snowman, _snowman, _snowman, this.n.d + 1, this.n.b, this.n.c + 1, gc.f, _snowman);
               break;
            case e:
               crh.b(_snowman, _snowman, _snowman, this.n.a + 1, this.n.b, this.n.c - 1, gc.c, _snowman);
               crh.b(_snowman, _snowman, _snowman, this.n.a + 1, this.n.b, this.n.f + 1, gc.d, _snowman);
               crh.b(_snowman, _snowman, _snowman, this.n.a - 1, this.n.b, this.n.c + 1, gc.e, _snowman);
               break;
            case f:
               crh.b(_snowman, _snowman, _snowman, this.n.a + 1, this.n.b, this.n.c - 1, gc.c, _snowman);
               crh.b(_snowman, _snowman, _snowman, this.n.a + 1, this.n.b, this.n.f + 1, gc.d, _snowman);
               crh.b(_snowman, _snowman, _snowman, this.n.d + 1, this.n.b, this.n.c + 1, gc.f, _snowman);
         }

         if (this.c) {
            if (_snowman.nextBoolean()) {
               crh.b(_snowman, _snowman, _snowman, this.n.a + 1, this.n.b + 3 + 1, this.n.c - 1, gc.c, _snowman);
            }

            if (_snowman.nextBoolean()) {
               crh.b(_snowman, _snowman, _snowman, this.n.a - 1, this.n.b + 3 + 1, this.n.c + 1, gc.e, _snowman);
            }

            if (_snowman.nextBoolean()) {
               crh.b(_snowman, _snowman, _snowman, this.n.d + 1, this.n.b + 3 + 1, this.n.c + 1, gc.f, _snowman);
            }

            if (_snowman.nextBoolean()) {
               crh.b(_snowman, _snowman, _snowman, this.n.a + 1, this.n.b + 3 + 1, this.n.f + 1, gc.d, _snowman);
            }
         }
      }

      @Override
      public boolean a(bsr var1, bsn var2, cfy var3, Random var4, cra var5, brd var6, fx var7) {
         if (this.a(_snowman, _snowman)) {
            return false;
         } else {
            ceh _snowman = this.a();
            if (this.c) {
               this.a(_snowman, _snowman, this.n.a + 1, this.n.b, this.n.c, this.n.d - 1, this.n.b + 3 - 1, this.n.f, m, m, false);
               this.a(_snowman, _snowman, this.n.a, this.n.b, this.n.c + 1, this.n.d, this.n.b + 3 - 1, this.n.f - 1, m, m, false);
               this.a(_snowman, _snowman, this.n.a + 1, this.n.e - 2, this.n.c, this.n.d - 1, this.n.e, this.n.f, m, m, false);
               this.a(_snowman, _snowman, this.n.a, this.n.e - 2, this.n.c + 1, this.n.d, this.n.e, this.n.f - 1, m, m, false);
               this.a(_snowman, _snowman, this.n.a + 1, this.n.b + 3, this.n.c + 1, this.n.d - 1, this.n.b + 3, this.n.f - 1, m, m, false);
            } else {
               this.a(_snowman, _snowman, this.n.a + 1, this.n.b, this.n.c, this.n.d - 1, this.n.e, this.n.f, m, m, false);
               this.a(_snowman, _snowman, this.n.a, this.n.b, this.n.c + 1, this.n.d, this.n.e, this.n.f - 1, m, m, false);
            }

            this.a(_snowman, _snowman, this.n.a + 1, this.n.b, this.n.c + 1, this.n.e);
            this.a(_snowman, _snowman, this.n.a + 1, this.n.b, this.n.f - 1, this.n.e);
            this.a(_snowman, _snowman, this.n.d - 1, this.n.b, this.n.c + 1, this.n.e);
            this.a(_snowman, _snowman, this.n.d - 1, this.n.b, this.n.f - 1, this.n.e);

            for (int _snowmanx = this.n.a; _snowmanx <= this.n.d; _snowmanx++) {
               for (int _snowmanxx = this.n.c; _snowmanxx <= this.n.f; _snowmanxx++) {
                  if (this.a(_snowman, _snowmanx, this.n.b - 1, _snowmanxx, _snowman).g() && this.a(_snowman, _snowmanx, this.n.b - 1, _snowmanxx, _snowman)) {
                     this.a(_snowman, _snowman, _snowmanx, this.n.b - 1, _snowmanxx, _snowman);
                  }
               }
            }

            return true;
         }
      }

      private void a(bsr var1, cra var2, int var3, int var4, int var5, int var6) {
         if (!this.a(_snowman, _snowman, _snowman + 1, _snowman, _snowman).g()) {
            this.a(_snowman, _snowman, _snowman, _snowman, _snowman, _snowman, _snowman, _snowman, this.a(), m, false);
         }
      }
   }

   abstract static class c extends cru {
      protected ckb.b a;

      public c(clb var1, int var2, ckb.b var3) {
         super(_snowman, _snowman);
         this.a = _snowman;
      }

      public c(clb var1, md var2) {
         super(_snowman, _snowman);
         this.a = ckb.b.a(_snowman.h("MST"));
      }

      @Override
      protected void a(md var1) {
         _snowman.b("MST", this.a.ordinal());
      }

      protected ceh a() {
         switch (this.a) {
            case a:
            default:
               return bup.n.n();
            case b:
               return bup.s.n();
         }
      }

      protected ceh b() {
         switch (this.a) {
            case a:
            default:
               return bup.cJ.n();
            case b:
               return bup.iq.n();
         }
      }

      protected boolean a(brc var1, cra var2, int var3, int var4, int var5, int var6) {
         for (int _snowman = _snowman; _snowman <= _snowman; _snowman++) {
            if (this.a(_snowman, _snowman, _snowman + 1, _snowman, _snowman).g()) {
               return false;
            }
         }

         return true;
      }
   }

   public static class d extends crh.c {
      private final List<cra> b = Lists.newLinkedList();

      public d(int var1, Random var2, int var3, int var4, ckb.b var5) {
         super(clb.c, _snowman, _snowman);
         this.a = _snowman;
         this.n = new cra(_snowman, 50, _snowman, _snowman + 7 + _snowman.nextInt(6), 54 + _snowman.nextInt(6), _snowman + 7 + _snowman.nextInt(6));
      }

      public d(csw var1, md var2) {
         super(clb.c, _snowman);
         mj _snowman = _snowman.d("Entrances", 11);

         for (int _snowmanx = 0; _snowmanx < _snowman.size(); _snowmanx++) {
            this.b.add(new cra(_snowman.f(_snowmanx)));
         }
      }

      @Override
      public void a(cru var1, List<cru> var2, Random var3) {
         int _snowman = this.h();
         int _snowmanx = this.n.e() - 3 - 1;
         if (_snowmanx <= 0) {
            _snowmanx = 1;
         }

         int _snowmanxx = 0;

         while (_snowmanxx < this.n.d()) {
            _snowmanxx += _snowman.nextInt(this.n.d());
            if (_snowmanxx + 3 > this.n.d()) {
               break;
            }

            crh.c _snowmanxxx = crh.b(_snowman, _snowman, _snowman, this.n.a + _snowmanxx, this.n.b + _snowman.nextInt(_snowmanx) + 1, this.n.c - 1, gc.c, _snowman);
            if (_snowmanxxx != null) {
               cra _snowmanxxxx = _snowmanxxx.g();
               this.b.add(new cra(_snowmanxxxx.a, _snowmanxxxx.b, this.n.c, _snowmanxxxx.d, _snowmanxxxx.e, this.n.c + 1));
            }

            _snowmanxx += 4;
         }

         _snowmanxx = 0;

         while (_snowmanxx < this.n.d()) {
            _snowmanxx += _snowman.nextInt(this.n.d());
            if (_snowmanxx + 3 > this.n.d()) {
               break;
            }

            crh.c _snowmanxxx = crh.b(_snowman, _snowman, _snowman, this.n.a + _snowmanxx, this.n.b + _snowman.nextInt(_snowmanx) + 1, this.n.f + 1, gc.d, _snowman);
            if (_snowmanxxx != null) {
               cra _snowmanxxxx = _snowmanxxx.g();
               this.b.add(new cra(_snowmanxxxx.a, _snowmanxxxx.b, this.n.f - 1, _snowmanxxxx.d, _snowmanxxxx.e, this.n.f));
            }

            _snowmanxx += 4;
         }

         _snowmanxx = 0;

         while (_snowmanxx < this.n.f()) {
            _snowmanxx += _snowman.nextInt(this.n.f());
            if (_snowmanxx + 3 > this.n.f()) {
               break;
            }

            crh.c _snowmanxxx = crh.b(_snowman, _snowman, _snowman, this.n.a - 1, this.n.b + _snowman.nextInt(_snowmanx) + 1, this.n.c + _snowmanxx, gc.e, _snowman);
            if (_snowmanxxx != null) {
               cra _snowmanxxxx = _snowmanxxx.g();
               this.b.add(new cra(this.n.a, _snowmanxxxx.b, _snowmanxxxx.c, this.n.a + 1, _snowmanxxxx.e, _snowmanxxxx.f));
            }

            _snowmanxx += 4;
         }

         _snowmanxx = 0;

         while (_snowmanxx < this.n.f()) {
            _snowmanxx += _snowman.nextInt(this.n.f());
            if (_snowmanxx + 3 > this.n.f()) {
               break;
            }

            cru _snowmanxxx = crh.b(_snowman, _snowman, _snowman, this.n.d + 1, this.n.b + _snowman.nextInt(_snowmanx) + 1, this.n.c + _snowmanxx, gc.f, _snowman);
            if (_snowmanxxx != null) {
               cra _snowmanxxxx = _snowmanxxx.g();
               this.b.add(new cra(this.n.d - 1, _snowmanxxxx.b, _snowmanxxxx.c, this.n.d, _snowmanxxxx.e, _snowmanxxxx.f));
            }

            _snowmanxx += 4;
         }
      }

      @Override
      public boolean a(bsr var1, bsn var2, cfy var3, Random var4, cra var5, brd var6, fx var7) {
         if (this.a(_snowman, _snowman)) {
            return false;
         } else {
            this.a(_snowman, _snowman, this.n.a, this.n.b, this.n.c, this.n.d, this.n.b, this.n.f, bup.j.n(), m, true);
            this.a(_snowman, _snowman, this.n.a, this.n.b + 1, this.n.c, this.n.d, Math.min(this.n.b + 3, this.n.e), this.n.f, m, m, false);

            for (cra _snowman : this.b) {
               this.a(_snowman, _snowman, _snowman.a, _snowman.e - 2, _snowman.c, _snowman.d, _snowman.e, _snowman.f, m, m, false);
            }

            this.a(_snowman, _snowman, this.n.a, this.n.b + 4, this.n.c, this.n.d, this.n.e, this.n.f, m, false);
            return true;
         }
      }

      @Override
      public void a(int var1, int var2, int var3) {
         super.a(_snowman, _snowman, _snowman);

         for (cra _snowman : this.b) {
            _snowman.a(_snowman, _snowman, _snowman);
         }
      }

      @Override
      protected void a(md var1) {
         super.a(_snowman);
         mj _snowman = new mj();

         for (cra _snowmanx : this.b) {
            _snowman.add(_snowmanx.h());
         }

         _snowman.a("Entrances", _snowman);
      }
   }

   public static class e extends crh.c {
      public e(int var1, cra var2, gc var3, ckb.b var4) {
         super(clb.d, _snowman, _snowman);
         this.a(_snowman);
         this.n = _snowman;
      }

      public e(csw var1, md var2) {
         super(clb.d, _snowman);
      }

      public static cra a(List<cru> var0, Random var1, int var2, int var3, int var4, gc var5) {
         cra _snowman = new cra(_snowman, _snowman - 5, _snowman, _snowman, _snowman + 3 - 1, _snowman);
         switch (_snowman) {
            case c:
            default:
               _snowman.d = _snowman + 3 - 1;
               _snowman.c = _snowman - 8;
               break;
            case d:
               _snowman.d = _snowman + 3 - 1;
               _snowman.f = _snowman + 8;
               break;
            case e:
               _snowman.a = _snowman - 8;
               _snowman.f = _snowman + 3 - 1;
               break;
            case f:
               _snowman.d = _snowman + 8;
               _snowman.f = _snowman + 3 - 1;
         }

         return cru.a(_snowman, _snowman) != null ? null : _snowman;
      }

      @Override
      public void a(cru var1, List<cru> var2, Random var3) {
         int _snowman = this.h();
         gc _snowmanx = this.i();
         if (_snowmanx != null) {
            switch (_snowmanx) {
               case c:
               default:
                  crh.b(_snowman, _snowman, _snowman, this.n.a, this.n.b, this.n.c - 1, gc.c, _snowman);
                  break;
               case d:
                  crh.b(_snowman, _snowman, _snowman, this.n.a, this.n.b, this.n.f + 1, gc.d, _snowman);
                  break;
               case e:
                  crh.b(_snowman, _snowman, _snowman, this.n.a - 1, this.n.b, this.n.c, gc.e, _snowman);
                  break;
               case f:
                  crh.b(_snowman, _snowman, _snowman, this.n.d + 1, this.n.b, this.n.c, gc.f, _snowman);
            }
         }
      }

      @Override
      public boolean a(bsr var1, bsn var2, cfy var3, Random var4, cra var5, brd var6, fx var7) {
         if (this.a(_snowman, _snowman)) {
            return false;
         } else {
            this.a(_snowman, _snowman, 0, 5, 0, 2, 7, 1, m, m, false);
            this.a(_snowman, _snowman, 0, 0, 7, 2, 2, 8, m, m, false);

            for (int _snowman = 0; _snowman < 5; _snowman++) {
               this.a(_snowman, _snowman, 0, 5 - _snowman - (_snowman < 4 ? 1 : 0), 2 + _snowman, 2, 7 - _snowman, 2 + _snowman, m, m, false);
            }

            return true;
         }
      }
   }
}
