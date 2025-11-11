import com.google.common.collect.ComparisonChain;
import com.google.common.collect.Ordering;
import com.mojang.authlib.GameProfile;
import com.mojang.blaze3d.systems.RenderSystem;
import java.util.Comparator;
import java.util.List;
import javax.annotation.Nullable;

public class dly extends dkw {
   private static final Ordering<dwx> a = Ordering.from(new dly.a());
   private final djz b;
   private final dkv c;
   private nr d;
   private nr e;
   private long i;
   private boolean j;

   public dly(djz var1, dkv var2) {
      this.b = _snowman;
      this.c = _snowman;
   }

   public nr a(dwx var1) {
      return _snowman.l() != null ? this.a(_snowman, _snowman.l().e()) : this.a(_snowman, ddl.a(_snowman.j(), new oe(_snowman.a().getName())));
   }

   private nr a(dwx var1, nx var2) {
      return _snowman.b() == bru.e ? _snowman.a(k.u) : _snowman;
   }

   public void a(boolean var1) {
      if (_snowman && !this.j) {
         this.i = x.b();
      }

      this.j = _snowman;
   }

   public void a(dfm var1, int var2, ddn var3, @Nullable ddk var4) {
      dwu _snowman = this.b.s.e;
      List<dwx> _snowmanx = a.sortedCopy(_snowman.e());
      int _snowmanxx = 0;
      int _snowmanxxx = 0;

      for (dwx _snowmanxxxx : _snowmanx) {
         int _snowmanxxxxx = this.b.g.a(this.a(_snowmanxxxx));
         _snowmanxx = Math.max(_snowmanxx, _snowmanxxxxx);
         if (_snowman != null && _snowman.f() != ddq.a.b) {
            _snowmanxxxxx = this.b.g.b(" " + _snowman.c(_snowmanxxxx.a().getName(), _snowman).b());
            _snowmanxxx = Math.max(_snowmanxxx, _snowmanxxxxx);
         }
      }

      _snowmanx = _snowmanx.subList(0, Math.min(_snowmanx.size(), 80));
      int _snowmanxxxxx = _snowmanx.size();
      int _snowmanxxxxxx = _snowmanxxxxx;

      int _snowmanxxxxxxx;
      for (_snowmanxxxxxxx = 1; _snowmanxxxxxx > 20; _snowmanxxxxxx = (_snowmanxxxxx + _snowmanxxxxxxx - 1) / _snowmanxxxxxxx) {
         _snowmanxxxxxxx++;
      }

      boolean _snowmanxxxxxxxx = this.b.F() || this.b.w().a().g();
      int _snowmanxxxxxxxxx;
      if (_snowman != null) {
         if (_snowman.f() == ddq.a.b) {
            _snowmanxxxxxxxxx = 90;
         } else {
            _snowmanxxxxxxxxx = _snowmanxxx;
         }
      } else {
         _snowmanxxxxxxxxx = 0;
      }

      int _snowmanxxxxxxxxxx = Math.min(_snowmanxxxxxxx * ((_snowmanxxxxxxxx ? 9 : 0) + _snowmanxx + _snowmanxxxxxxxxx + 13), _snowman - 50) / _snowmanxxxxxxx;
      int _snowmanxxxxxxxxxxx = _snowman / 2 - (_snowmanxxxxxxxxxx * _snowmanxxxxxxx + (_snowmanxxxxxxx - 1) * 5) / 2;
      int _snowmanxxxxxxxxxxxx = 10;
      int _snowmanxxxxxxxxxxxxx = _snowmanxxxxxxxxxx * _snowmanxxxxxxx + (_snowmanxxxxxxx - 1) * 5;
      List<afa> _snowmanxxxxxxxxxxxxxx = null;
      if (this.e != null) {
         _snowmanxxxxxxxxxxxxxx = this.b.g.b(this.e, _snowman - 50);

         for (afa _snowmanxxxxxxxxxxxxxxx : _snowmanxxxxxxxxxxxxxx) {
            _snowmanxxxxxxxxxxxxx = Math.max(_snowmanxxxxxxxxxxxxx, this.b.g.a(_snowmanxxxxxxxxxxxxxxx));
         }
      }

      List<afa> _snowmanxxxxxxxxxxxxxxx = null;
      if (this.d != null) {
         _snowmanxxxxxxxxxxxxxxx = this.b.g.b(this.d, _snowman - 50);

         for (afa _snowmanxxxxxxxxxxxxxxxx : _snowmanxxxxxxxxxxxxxxx) {
            _snowmanxxxxxxxxxxxxx = Math.max(_snowmanxxxxxxxxxxxxx, this.b.g.a(_snowmanxxxxxxxxxxxxxxxx));
         }
      }

      if (_snowmanxxxxxxxxxxxxxx != null) {
         a(_snowman, _snowman / 2 - _snowmanxxxxxxxxxxxxx / 2 - 1, _snowmanxxxxxxxxxxxx - 1, _snowman / 2 + _snowmanxxxxxxxxxxxxx / 2 + 1, _snowmanxxxxxxxxxxxx + _snowmanxxxxxxxxxxxxxx.size() * 9, Integer.MIN_VALUE);

         for (afa _snowmanxxxxxxxxxxxxxxxx : _snowmanxxxxxxxxxxxxxx) {
            int _snowmanxxxxxxxxxxxxxxxxx = this.b.g.a(_snowmanxxxxxxxxxxxxxxxx);
            this.b.g.a(_snowman, _snowmanxxxxxxxxxxxxxxxx, (float)(_snowman / 2 - _snowmanxxxxxxxxxxxxxxxxx / 2), (float)_snowmanxxxxxxxxxxxx, -1);
            _snowmanxxxxxxxxxxxx += 9;
         }

         _snowmanxxxxxxxxxxxx++;
      }

      a(_snowman, _snowman / 2 - _snowmanxxxxxxxxxxxxx / 2 - 1, _snowmanxxxxxxxxxxxx - 1, _snowman / 2 + _snowmanxxxxxxxxxxxxx / 2 + 1, _snowmanxxxxxxxxxxxx + _snowmanxxxxxx * 9, Integer.MIN_VALUE);
      int _snowmanxxxxxxxxxxxxxxxx = this.b.k.a(553648127);

      for (int _snowmanxxxxxxxxxxxxxxxxx = 0; _snowmanxxxxxxxxxxxxxxxxx < _snowmanxxxxx; _snowmanxxxxxxxxxxxxxxxxx++) {
         int _snowmanxxxxxxxxxxxxxxxxxx = _snowmanxxxxxxxxxxxxxxxxx / _snowmanxxxxxx;
         int _snowmanxxxxxxxxxxxxxxxxxxx = _snowmanxxxxxxxxxxxxxxxxx % _snowmanxxxxxx;
         int _snowmanxxxxxxxxxxxxxxxxxxxx = _snowmanxxxxxxxxxxx + _snowmanxxxxxxxxxxxxxxxxxx * _snowmanxxxxxxxxxx + _snowmanxxxxxxxxxxxxxxxxxx * 5;
         int _snowmanxxxxxxxxxxxxxxxxxxxxx = _snowmanxxxxxxxxxxxx + _snowmanxxxxxxxxxxxxxxxxxxx * 9;
         a(_snowman, _snowmanxxxxxxxxxxxxxxxxxxxx, _snowmanxxxxxxxxxxxxxxxxxxxxx, _snowmanxxxxxxxxxxxxxxxxxxxx + _snowmanxxxxxxxxxx, _snowmanxxxxxxxxxxxxxxxxxxxxx + 8, _snowmanxxxxxxxxxxxxxxxx);
         RenderSystem.color4f(1.0F, 1.0F, 1.0F, 1.0F);
         RenderSystem.enableAlphaTest();
         RenderSystem.enableBlend();
         RenderSystem.defaultBlendFunc();
         if (_snowmanxxxxxxxxxxxxxxxxx < _snowmanx.size()) {
            dwx _snowmanxxxxxxxxxxxxxxxxxxxxxx = _snowmanx.get(_snowmanxxxxxxxxxxxxxxxxx);
            GameProfile _snowmanxxxxxxxxxxxxxxxxxxxxxxx = _snowmanxxxxxxxxxxxxxxxxxxxxxx.a();
            if (_snowmanxxxxxxxx) {
               bfw _snowmanxxxxxxxxxxxxxxxxxxxxxxxx = this.b.r.b(_snowmanxxxxxxxxxxxxxxxxxxxxxxx.getId());
               boolean _snowmanxxxxxxxxxxxxxxxxxxxxxxxxx = _snowmanxxxxxxxxxxxxxxxxxxxxxxxx != null
                  && _snowmanxxxxxxxxxxxxxxxxxxxxxxxx.a(bfx.a)
                  && ("Dinnerbone".equals(_snowmanxxxxxxxxxxxxxxxxxxxxxxx.getName()) || "Grumm".equals(_snowmanxxxxxxxxxxxxxxxxxxxxxxx.getName()));
               this.b.M().a(_snowmanxxxxxxxxxxxxxxxxxxxxxx.g());
               int _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxx = 8 + (_snowmanxxxxxxxxxxxxxxxxxxxxxxxxx ? 8 : 0);
               int _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxx = 8 * (_snowmanxxxxxxxxxxxxxxxxxxxxxxxxx ? -1 : 1);
               dkw.a(_snowman, _snowmanxxxxxxxxxxxxxxxxxxxx, _snowmanxxxxxxxxxxxxxxxxxxxxx, 8, 8, 8.0F, (float)_snowmanxxxxxxxxxxxxxxxxxxxxxxxxxx, 8, _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxx, 64, 64);
               if (_snowmanxxxxxxxxxxxxxxxxxxxxxxxx != null && _snowmanxxxxxxxxxxxxxxxxxxxxxxxx.a(bfx.g)) {
                  int _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxx = 8 + (_snowmanxxxxxxxxxxxxxxxxxxxxxxxxx ? 8 : 0);
                  int _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxx = 8 * (_snowmanxxxxxxxxxxxxxxxxxxxxxxxxx ? -1 : 1);
                  dkw.a(
                     _snowman,
                     _snowmanxxxxxxxxxxxxxxxxxxxx,
                     _snowmanxxxxxxxxxxxxxxxxxxxxx,
                     8,
                     8,
                     40.0F,
                     (float)_snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxx,
                     8,
                     _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxx,
                     64,
                     64
                  );
               }

               _snowmanxxxxxxxxxxxxxxxxxxxx += 9;
            }

            this.b
               .g
               .a(
                  _snowman,
                  this.a(_snowmanxxxxxxxxxxxxxxxxxxxxxx),
                  (float)_snowmanxxxxxxxxxxxxxxxxxxxx,
                  (float)_snowmanxxxxxxxxxxxxxxxxxxxxx,
                  _snowmanxxxxxxxxxxxxxxxxxxxxxx.b() == bru.e ? -1862270977 : -1
               );
            if (_snowman != null && _snowmanxxxxxxxxxxxxxxxxxxxxxx.b() != bru.e) {
               int _snowmanxxxxxxxxxxxxxxxxxxxxxxxx = _snowmanxxxxxxxxxxxxxxxxxxxx + _snowmanxx + 1;
               int _snowmanxxxxxxxxxxxxxxxxxxxxxxxxx = _snowmanxxxxxxxxxxxxxxxxxxxxxxxx + _snowmanxxxxxxxxx;
               if (_snowmanxxxxxxxxxxxxxxxxxxxxxxxxx - _snowmanxxxxxxxxxxxxxxxxxxxxxxxx > 5) {
                  this.a(
                     _snowman,
                     _snowmanxxxxxxxxxxxxxxxxxxxxx,
                     _snowmanxxxxxxxxxxxxxxxxxxxxxxx.getName(),
                     _snowmanxxxxxxxxxxxxxxxxxxxxxxxx,
                     _snowmanxxxxxxxxxxxxxxxxxxxxxxxxx,
                     _snowmanxxxxxxxxxxxxxxxxxxxxxx,
                     _snowman
                  );
               }
            }

            this.a(_snowman, _snowmanxxxxxxxxxx, _snowmanxxxxxxxxxxxxxxxxxxxx - (_snowmanxxxxxxxx ? 9 : 0), _snowmanxxxxxxxxxxxxxxxxxxxxx, _snowmanxxxxxxxxxxxxxxxxxxxxxx);
         }
      }

      if (_snowmanxxxxxxxxxxxxxxx != null) {
         _snowmanxxxxxxxxxxxx += _snowmanxxxxxx * 9 + 1;
         a(_snowman, _snowman / 2 - _snowmanxxxxxxxxxxxxx / 2 - 1, _snowmanxxxxxxxxxxxx - 1, _snowman / 2 + _snowmanxxxxxxxxxxxxx / 2 + 1, _snowmanxxxxxxxxxxxx + _snowmanxxxxxxxxxxxxxxx.size() * 9, Integer.MIN_VALUE);

         for (afa _snowmanxxxxxxxxxxxxxxxxxx : _snowmanxxxxxxxxxxxxxxx) {
            int _snowmanxxxxxxxxxxxxxxxxxxx = this.b.g.a(_snowmanxxxxxxxxxxxxxxxxxx);
            this.b.g.a(_snowman, _snowmanxxxxxxxxxxxxxxxxxx, (float)(_snowman / 2 - _snowmanxxxxxxxxxxxxxxxxxxx / 2), (float)_snowmanxxxxxxxxxxxx, -1);
            _snowmanxxxxxxxxxxxx += 9;
         }
      }
   }

   protected void a(dfm var1, int var2, int var3, int var4, dwx var5) {
      RenderSystem.color4f(1.0F, 1.0F, 1.0F, 1.0F);
      this.b.M().a(h);
      int _snowman = 0;
      int _snowmanx;
      if (_snowman.c() < 0) {
         _snowmanx = 5;
      } else if (_snowman.c() < 150) {
         _snowmanx = 0;
      } else if (_snowman.c() < 300) {
         _snowmanx = 1;
      } else if (_snowman.c() < 600) {
         _snowmanx = 2;
      } else if (_snowman.c() < 1000) {
         _snowmanx = 3;
      } else {
         _snowmanx = 4;
      }

      this.d(this.v() + 100);
      this.b(_snowman, _snowman + _snowman - 11, _snowman, 0, 176 + _snowmanx * 8, 10, 8);
      this.d(this.v() - 100);
   }

   private void a(ddk var1, int var2, String var3, int var4, int var5, dwx var6, dfm var7) {
      int _snowman = _snowman.a().c(_snowman, _snowman).b();
      if (_snowman.f() == ddq.a.b) {
         this.b.M().a(h);
         long _snowmanx = x.b();
         if (this.i == _snowman.q()) {
            if (_snowman < _snowman.m()) {
               _snowman.a(_snowmanx);
               _snowman.b((long)(this.c.d() + 20));
            } else if (_snowman > _snowman.m()) {
               _snowman.a(_snowmanx);
               _snowman.b((long)(this.c.d() + 10));
            }
         }

         if (_snowmanx - _snowman.o() > 1000L || this.i != _snowman.q()) {
            _snowman.b(_snowman);
            _snowman.c(_snowman);
            _snowman.a(_snowmanx);
         }

         _snowman.c(this.i);
         _snowman.b(_snowman);
         int _snowmanxx = afm.f((float)Math.max(_snowman, _snowman.n()) / 2.0F);
         int _snowmanxxx = Math.max(afm.f((float)(_snowman / 2)), Math.max(afm.f((float)(_snowman.n() / 2)), 10));
         boolean _snowmanxxxx = _snowman.p() > (long)this.c.d() && (_snowman.p() - (long)this.c.d()) / 3L % 2L == 1L;
         if (_snowmanxx > 0) {
            int _snowmanxxxxx = afm.d(Math.min((float)(_snowman - _snowman - 4) / (float)_snowmanxxx, 9.0F));
            if (_snowmanxxxxx > 3) {
               for (int _snowmanxxxxxx = _snowmanxx; _snowmanxxxxxx < _snowmanxxx; _snowmanxxxxxx++) {
                  this.b(_snowman, _snowman + _snowmanxxxxxx * _snowmanxxxxx, _snowman, _snowmanxxxx ? 25 : 16, 0, 9, 9);
               }

               for (int _snowmanxxxxxx = 0; _snowmanxxxxxx < _snowmanxx; _snowmanxxxxxx++) {
                  this.b(_snowman, _snowman + _snowmanxxxxxx * _snowmanxxxxx, _snowman, _snowmanxxxx ? 25 : 16, 0, 9, 9);
                  if (_snowmanxxxx) {
                     if (_snowmanxxxxxx * 2 + 1 < _snowman.n()) {
                        this.b(_snowman, _snowman + _snowmanxxxxxx * _snowmanxxxxx, _snowman, 70, 0, 9, 9);
                     }

                     if (_snowmanxxxxxx * 2 + 1 == _snowman.n()) {
                        this.b(_snowman, _snowman + _snowmanxxxxxx * _snowmanxxxxx, _snowman, 79, 0, 9, 9);
                     }
                  }

                  if (_snowmanxxxxxx * 2 + 1 < _snowman) {
                     this.b(_snowman, _snowman + _snowmanxxxxxx * _snowmanxxxxx, _snowman, _snowmanxxxxxx >= 10 ? 160 : 52, 0, 9, 9);
                  }

                  if (_snowmanxxxxxx * 2 + 1 == _snowman) {
                     this.b(_snowman, _snowman + _snowmanxxxxxx * _snowmanxxxxx, _snowman, _snowmanxxxxxx >= 10 ? 169 : 61, 0, 9, 9);
                  }
               }
            } else {
               float _snowmanxxxxxx = afm.a((float)_snowman / 20.0F, 0.0F, 1.0F);
               int _snowmanxxxxxxx = (int)((1.0F - _snowmanxxxxxx) * 255.0F) << 16 | (int)(_snowmanxxxxxx * 255.0F) << 8;
               String _snowmanxxxxxxxx = "" + (float)_snowman / 2.0F;
               if (_snowman - this.b.g.b(_snowmanxxxxxxxx + "hp") >= _snowman) {
                  _snowmanxxxxxxxx = _snowmanxxxxxxxx + "hp";
               }

               this.b.g.a(_snowman, _snowmanxxxxxxxx, (float)((_snowman + _snowman) / 2 - this.b.g.b(_snowmanxxxxxxxx) / 2), (float)_snowman, _snowmanxxxxxxx);
            }
         }
      } else {
         String _snowmanxx = k.o + "" + _snowman;
         this.b.g.a(_snowman, _snowmanxx, (float)(_snowman - this.b.g.b(_snowmanxx)), (float)_snowman, 16777215);
      }
   }

   public void a(@Nullable nr var1) {
      this.d = _snowman;
   }

   public void b(@Nullable nr var1) {
      this.e = _snowman;
   }

   public void a() {
      this.e = null;
      this.d = null;
   }

   static class a implements Comparator<dwx> {
      private a() {
      }

      public int a(dwx var1, dwx var2) {
         ddl _snowman = _snowman.j();
         ddl _snowmanx = _snowman.j();
         return ComparisonChain.start()
            .compareTrueFirst(_snowman.b() != bru.e, _snowman.b() != bru.e)
            .compare(_snowman != null ? _snowman.b() : "", _snowmanx != null ? _snowmanx.b() : "")
            .compare(_snowman.a().getName(), _snowman.a().getName(), String::compareToIgnoreCase)
            .result();
      }
   }
}
