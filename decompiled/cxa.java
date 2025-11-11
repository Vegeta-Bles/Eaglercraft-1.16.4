import com.google.common.collect.Sets;
import java.util.EnumSet;
import java.util.Set;
import javax.annotation.Nullable;

public class cxa extends cxj {
   public cxa() {
   }

   @Override
   public void a(bsi var1, aqn var2) {
      super.a(_snowman, _snowman);
      this.j = _snowman.a(cwz.h);
   }

   @Override
   public void a() {
      this.b.a(cwz.h, this.j);
      super.a();
   }

   @Override
   public cxb b() {
      int _snowman;
      if (this.e() && this.b.aE()) {
         _snowman = afm.c(this.b.cE());
         fx.a _snowmanx = new fx.a(this.b.cD(), (double)_snowman, this.b.cH());

         for (buo _snowmanxx = this.a.d_(_snowmanx).b(); _snowmanxx == bup.A; _snowmanxx = this.a.d_(_snowmanx).b()) {
            _snowmanx.c(this.b.cD(), (double)(++_snowman), this.b.cH());
         }
      } else {
         _snowman = afm.c(this.b.cE() + 0.5);
      }

      fx _snowmanx = this.b.cB();
      cwz _snowmanxx = this.a(this.b, _snowmanx.u(), _snowman, _snowmanx.w());
      if (this.b.a(_snowmanxx) < 0.0F) {
         Set<fx> _snowmanxxx = Sets.newHashSet();
         _snowmanxxx.add(new fx(this.b.cc().a, (double)_snowman, this.b.cc().c));
         _snowmanxxx.add(new fx(this.b.cc().a, (double)_snowman, this.b.cc().f));
         _snowmanxxx.add(new fx(this.b.cc().d, (double)_snowman, this.b.cc().c));
         _snowmanxxx.add(new fx(this.b.cc().d, (double)_snowman, this.b.cc().f));

         for (fx _snowmanxxxx : _snowmanxxx) {
            cwz _snowmanxxxxx = this.a(this.b, _snowmanxxxx);
            if (this.b.a(_snowmanxxxxx) >= 0.0F) {
               return super.a(_snowmanxxxx.u(), _snowmanxxxx.v(), _snowmanxxxx.w());
            }
         }
      }

      return super.a(_snowmanx.u(), _snowman, _snowmanx.w());
   }

   @Override
   public cxh a(double var1, double var3, double var5) {
      return new cxh(super.a(afm.c(_snowman), afm.c(_snowman), afm.c(_snowman)));
   }

   @Override
   public int a(cxb[] var1, cxb var2) {
      int _snowman = 0;
      cxb _snowmanx = this.a(_snowman.a, _snowman.b, _snowman.c + 1);
      if (this.b(_snowmanx)) {
         _snowman[_snowman++] = _snowmanx;
      }

      cxb _snowmanxx = this.a(_snowman.a - 1, _snowman.b, _snowman.c);
      if (this.b(_snowmanxx)) {
         _snowman[_snowman++] = _snowmanxx;
      }

      cxb _snowmanxxx = this.a(_snowman.a + 1, _snowman.b, _snowman.c);
      if (this.b(_snowmanxxx)) {
         _snowman[_snowman++] = _snowmanxxx;
      }

      cxb _snowmanxxxx = this.a(_snowman.a, _snowman.b, _snowman.c - 1);
      if (this.b(_snowmanxxxx)) {
         _snowman[_snowman++] = _snowmanxxxx;
      }

      cxb _snowmanxxxxx = this.a(_snowman.a, _snowman.b + 1, _snowman.c);
      if (this.b(_snowmanxxxxx)) {
         _snowman[_snowman++] = _snowmanxxxxx;
      }

      cxb _snowmanxxxxxx = this.a(_snowman.a, _snowman.b - 1, _snowman.c);
      if (this.b(_snowmanxxxxxx)) {
         _snowman[_snowman++] = _snowmanxxxxxx;
      }

      cxb _snowmanxxxxxxx = this.a(_snowman.a, _snowman.b + 1, _snowman.c + 1);
      if (this.b(_snowmanxxxxxxx) && this.a(_snowmanx) && this.a(_snowmanxxxxx)) {
         _snowman[_snowman++] = _snowmanxxxxxxx;
      }

      cxb _snowmanxxxxxxxx = this.a(_snowman.a - 1, _snowman.b + 1, _snowman.c);
      if (this.b(_snowmanxxxxxxxx) && this.a(_snowmanxx) && this.a(_snowmanxxxxx)) {
         _snowman[_snowman++] = _snowmanxxxxxxxx;
      }

      cxb _snowmanxxxxxxxxx = this.a(_snowman.a + 1, _snowman.b + 1, _snowman.c);
      if (this.b(_snowmanxxxxxxxxx) && this.a(_snowmanxxx) && this.a(_snowmanxxxxx)) {
         _snowman[_snowman++] = _snowmanxxxxxxxxx;
      }

      cxb _snowmanxxxxxxxxxx = this.a(_snowman.a, _snowman.b + 1, _snowman.c - 1);
      if (this.b(_snowmanxxxxxxxxxx) && this.a(_snowmanxxxx) && this.a(_snowmanxxxxx)) {
         _snowman[_snowman++] = _snowmanxxxxxxxxxx;
      }

      cxb _snowmanxxxxxxxxxxx = this.a(_snowman.a, _snowman.b - 1, _snowman.c + 1);
      if (this.b(_snowmanxxxxxxxxxxx) && this.a(_snowmanx) && this.a(_snowmanxxxxxx)) {
         _snowman[_snowman++] = _snowmanxxxxxxxxxxx;
      }

      cxb _snowmanxxxxxxxxxxxx = this.a(_snowman.a - 1, _snowman.b - 1, _snowman.c);
      if (this.b(_snowmanxxxxxxxxxxxx) && this.a(_snowmanxx) && this.a(_snowmanxxxxxx)) {
         _snowman[_snowman++] = _snowmanxxxxxxxxxxxx;
      }

      cxb _snowmanxxxxxxxxxxxxx = this.a(_snowman.a + 1, _snowman.b - 1, _snowman.c);
      if (this.b(_snowmanxxxxxxxxxxxxx) && this.a(_snowmanxxx) && this.a(_snowmanxxxxxx)) {
         _snowman[_snowman++] = _snowmanxxxxxxxxxxxxx;
      }

      cxb _snowmanxxxxxxxxxxxxxx = this.a(_snowman.a, _snowman.b - 1, _snowman.c - 1);
      if (this.b(_snowmanxxxxxxxxxxxxxx) && this.a(_snowmanxxxx) && this.a(_snowmanxxxxxx)) {
         _snowman[_snowman++] = _snowmanxxxxxxxxxxxxxx;
      }

      cxb _snowmanxxxxxxxxxxxxxxx = this.a(_snowman.a + 1, _snowman.b, _snowman.c - 1);
      if (this.b(_snowmanxxxxxxxxxxxxxxx) && this.a(_snowmanxxxx) && this.a(_snowmanxxx)) {
         _snowman[_snowman++] = _snowmanxxxxxxxxxxxxxxx;
      }

      cxb _snowmanxxxxxxxxxxxxxxxx = this.a(_snowman.a + 1, _snowman.b, _snowman.c + 1);
      if (this.b(_snowmanxxxxxxxxxxxxxxxx) && this.a(_snowmanx) && this.a(_snowmanxxx)) {
         _snowman[_snowman++] = _snowmanxxxxxxxxxxxxxxxx;
      }

      cxb _snowmanxxxxxxxxxxxxxxxxx = this.a(_snowman.a - 1, _snowman.b, _snowman.c - 1);
      if (this.b(_snowmanxxxxxxxxxxxxxxxxx) && this.a(_snowmanxxxx) && this.a(_snowmanxx)) {
         _snowman[_snowman++] = _snowmanxxxxxxxxxxxxxxxxx;
      }

      cxb _snowmanxxxxxxxxxxxxxxxxxx = this.a(_snowman.a - 1, _snowman.b, _snowman.c + 1);
      if (this.b(_snowmanxxxxxxxxxxxxxxxxxx) && this.a(_snowmanx) && this.a(_snowmanxx)) {
         _snowman[_snowman++] = _snowmanxxxxxxxxxxxxxxxxxx;
      }

      cxb _snowmanxxxxxxxxxxxxxxxxxxx = this.a(_snowman.a + 1, _snowman.b + 1, _snowman.c - 1);
      if (this.b(_snowmanxxxxxxxxxxxxxxxxxxx)
         && this.a(_snowmanxxxxxxxxxxxxxxx)
         && this.a(_snowmanxxxx)
         && this.a(_snowmanxxx)
         && this.a(_snowmanxxxxx)
         && this.a(_snowmanxxxxxxxxxx)
         && this.a(_snowmanxxxxxxxxx)) {
         _snowman[_snowman++] = _snowmanxxxxxxxxxxxxxxxxxxx;
      }

      cxb _snowmanxxxxxxxxxxxxxxxxxxxx = this.a(_snowman.a + 1, _snowman.b + 1, _snowman.c + 1);
      if (this.b(_snowmanxxxxxxxxxxxxxxxxxxxx) && this.a(_snowmanxxxxxxxxxxxxxxxx) && this.a(_snowmanx) && this.a(_snowmanxxx) && this.a(_snowmanxxxxx) && this.a(_snowmanxxxxxxx) && this.a(_snowmanxxxxxxxxx)) {
         _snowman[_snowman++] = _snowmanxxxxxxxxxxxxxxxxxxxx;
      }

      cxb _snowmanxxxxxxxxxxxxxxxxxxxxx = this.a(_snowman.a - 1, _snowman.b + 1, _snowman.c - 1);
      if (this.b(_snowmanxxxxxxxxxxxxxxxxxxxxx)
         && this.a(_snowmanxxxxxxxxxxxxxxxxx)
         && this.a(_snowmanxxxx)
         && this.a(_snowmanxx) & this.a(_snowmanxxxxx)
         && this.a(_snowmanxxxxxxxxxx)
         && this.a(_snowmanxxxxxxxx)) {
         _snowman[_snowman++] = _snowmanxxxxxxxxxxxxxxxxxxxxx;
      }

      cxb _snowmanxxxxxxxxxxxxxxxxxxxxxx = this.a(_snowman.a - 1, _snowman.b + 1, _snowman.c + 1);
      if (this.b(_snowmanxxxxxxxxxxxxxxxxxxxxxx) && this.a(_snowmanxxxxxxxxxxxxxxxxxx) && this.a(_snowmanx) && this.a(_snowmanxx) & this.a(_snowmanxxxxx) && this.a(_snowmanxxxxxxx) && this.a(_snowmanxxxxxxxx)
         )
       {
         _snowman[_snowman++] = _snowmanxxxxxxxxxxxxxxxxxxxxxx;
      }

      cxb _snowmanxxxxxxxxxxxxxxxxxxxxxxx = this.a(_snowman.a + 1, _snowman.b - 1, _snowman.c - 1);
      if (this.b(_snowmanxxxxxxxxxxxxxxxxxxxxxxx)
         && this.a(_snowmanxxxxxxxxxxxxxxx)
         && this.a(_snowmanxxxx)
         && this.a(_snowmanxxx)
         && this.a(_snowmanxxxxxx)
         && this.a(_snowmanxxxxxxxxxxxxxx)
         && this.a(_snowmanxxxxxxxxxxxxx)) {
         _snowman[_snowman++] = _snowmanxxxxxxxxxxxxxxxxxxxxxxx;
      }

      cxb _snowmanxxxxxxxxxxxxxxxxxxxxxxxx = this.a(_snowman.a + 1, _snowman.b - 1, _snowman.c + 1);
      if (this.b(_snowmanxxxxxxxxxxxxxxxxxxxxxxxx)
         && this.a(_snowmanxxxxxxxxxxxxxxxx)
         && this.a(_snowmanx)
         && this.a(_snowmanxxx)
         && this.a(_snowmanxxxxxx)
         && this.a(_snowmanxxxxxxxxxxx)
         && this.a(_snowmanxxxxxxxxxxxxx)) {
         _snowman[_snowman++] = _snowmanxxxxxxxxxxxxxxxxxxxxxxxx;
      }

      cxb _snowmanxxxxxxxxxxxxxxxxxxxxxxxxx = this.a(_snowman.a - 1, _snowman.b - 1, _snowman.c - 1);
      if (this.b(_snowmanxxxxxxxxxxxxxxxxxxxxxxxxx)
         && this.a(_snowmanxxxxxxxxxxxxxxxxx)
         && this.a(_snowmanxxxx)
         && this.a(_snowmanxx)
         && this.a(_snowmanxxxxxx)
         && this.a(_snowmanxxxxxxxxxxxxxx)
         && this.a(_snowmanxxxxxxxxxxxx)) {
         _snowman[_snowman++] = _snowmanxxxxxxxxxxxxxxxxxxxxxxxxx;
      }

      cxb _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxx = this.a(_snowman.a - 1, _snowman.b - 1, _snowman.c + 1);
      if (this.b(_snowmanxxxxxxxxxxxxxxxxxxxxxxxxxx)
         && this.a(_snowmanxxxxxxxxxxxxxxxxxx)
         && this.a(_snowmanx)
         && this.a(_snowmanxx)
         && this.a(_snowmanxxxxxx)
         && this.a(_snowmanxxxxxxxxxxx)
         && this.a(_snowmanxxxxxxxxxxxx)) {
         _snowman[_snowman++] = _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxx;
      }

      return _snowman;
   }

   private boolean a(@Nullable cxb var1) {
      return _snowman != null && _snowman.k >= 0.0F;
   }

   private boolean b(@Nullable cxb var1) {
      return _snowman != null && !_snowman.i;
   }

   @Nullable
   @Override
   protected cxb a(int var1, int var2, int var3) {
      cxb _snowman = null;
      cwz _snowmanx = this.a(this.b, _snowman, _snowman, _snowman);
      float _snowmanxx = this.b.a(_snowmanx);
      if (_snowmanxx >= 0.0F) {
         _snowman = super.a(_snowman, _snowman, _snowman);
         _snowman.l = _snowmanx;
         _snowman.k = Math.max(_snowman.k, _snowmanxx);
         if (_snowmanx == cwz.c) {
            _snowman.k++;
         }
      }

      return _snowmanx != cwz.b && _snowmanx != cwz.c ? _snowman : _snowman;
   }

   @Override
   public cwz a(brc var1, int var2, int var3, int var4, aqn var5, int var6, int var7, int var8, boolean var9, boolean var10) {
      EnumSet<cwz> _snowman = EnumSet.noneOf(cwz.class);
      cwz _snowmanx = cwz.a;
      fx _snowmanxx = _snowman.cB();
      _snowmanx = this.a(_snowman, _snowman, _snowman, _snowman, _snowman, _snowman, _snowman, _snowman, _snowman, _snowman, _snowmanx, _snowmanxx);
      if (_snowman.contains(cwz.f)) {
         return cwz.f;
      } else {
         cwz _snowmanxxx = cwz.a;

         for (cwz _snowmanxxxx : _snowman) {
            if (_snowman.a(_snowmanxxxx) < 0.0F) {
               return _snowmanxxxx;
            }

            if (_snowman.a(_snowmanxxxx) >= _snowman.a(_snowmanxxx)) {
               _snowmanxxx = _snowmanxxxx;
            }
         }

         return _snowmanx == cwz.b && _snowman.a(_snowmanxxx) == 0.0F ? cwz.b : _snowmanxxx;
      }
   }

   @Override
   public cwz a(brc var1, int var2, int var3, int var4) {
      fx.a _snowman = new fx.a();
      cwz _snowmanx = b(_snowman, _snowman.d(_snowman, _snowman, _snowman));
      if (_snowmanx == cwz.b && _snowman >= 1) {
         ceh _snowmanxx = _snowman.d_(_snowman.d(_snowman, _snowman - 1, _snowman));
         cwz _snowmanxxx = b(_snowman, _snowman.d(_snowman, _snowman - 1, _snowman));
         if (_snowmanxxx == cwz.m || _snowmanxx.a(bup.iJ) || _snowmanxxx == cwz.g || _snowmanxx.a(aed.ay)) {
            _snowmanx = cwz.m;
         } else if (_snowmanxxx == cwz.o) {
            _snowmanx = cwz.o;
         } else if (_snowmanxxx == cwz.q) {
            _snowmanx = cwz.q;
         } else if (_snowmanxxx == cwz.x) {
            _snowmanx = cwz.x;
         } else if (_snowmanxxx == cwz.f) {
            _snowmanx = cwz.f;
         } else {
            _snowmanx = _snowmanxxx != cwz.c && _snowmanxxx != cwz.b && _snowmanxxx != cwz.h ? cwz.c : cwz.b;
         }
      }

      if (_snowmanx == cwz.c || _snowmanx == cwz.b) {
         _snowmanx = a(_snowman, _snowman.d(_snowman, _snowman, _snowman), _snowmanx);
      }

      return _snowmanx;
   }

   private cwz a(aqn var1, fx var2) {
      return this.a(_snowman, _snowman.u(), _snowman.v(), _snowman.w());
   }

   private cwz a(aqn var1, int var2, int var3, int var4) {
      return this.a(this.a, _snowman, _snowman, _snowman, _snowman, this.d, this.e, this.f, this.d(), this.c());
   }
}
