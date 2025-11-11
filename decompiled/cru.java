import com.google.common.collect.ImmutableSet;
import java.util.List;
import java.util.Random;
import java.util.Set;
import javax.annotation.Nullable;

public abstract class cru {
   protected static final ceh m = bup.lb.n();
   protected cra n;
   @Nullable
   private gc a;
   private byg b;
   private bzm c;
   protected int o;
   private final clb d;
   private static final Set<buo> e = ImmutableSet.builder()
      .add(bup.dW)
      .add(bup.bL)
      .add(bup.bM)
      .add(bup.cJ)
      .add(bup.im)
      .add(bup.iq)
      .add(bup.ip)
      .add(bup.in)
      .add(bup.io)
      .add(bup.cg)
      .add(bup.dH)
      .build();

   protected cru(clb var1, int var2) {
      this.d = _snowman;
      this.o = _snowman;
   }

   public cru(clb var1, md var2) {
      this(_snowman, _snowman.h("GD"));
      if (_snowman.e("BB")) {
         this.n = new cra(_snowman.n("BB"));
      }

      int _snowman = _snowman.h("O");
      this.a(_snowman == -1 ? null : gc.b(_snowman));
   }

   public final md f() {
      md _snowman = new md();
      _snowman.a("id", gm.aI.b(this.k()).toString());
      _snowman.a("BB", this.n.h());
      gc _snowmanx = this.i();
      _snowman.b("O", _snowmanx == null ? -1 : _snowmanx.d());
      _snowman.b("GD", this.o);
      this.a(_snowman);
      return _snowman;
   }

   protected abstract void a(md var1);

   public void a(cru var1, List<cru> var2, Random var3) {
   }

   public abstract boolean a(bsr var1, bsn var2, cfy var3, Random var4, cra var5, brd var6, fx var7);

   public cra g() {
      return this.n;
   }

   public int h() {
      return this.o;
   }

   public boolean a(brd var1, int var2) {
      int _snowman = _snowman.b << 4;
      int _snowmanx = _snowman.c << 4;
      return this.n.a(_snowman - _snowman, _snowmanx - _snowman, _snowman + 15 + _snowman, _snowmanx + 15 + _snowman);
   }

   public static cru a(List<cru> var0, cra var1) {
      for (cru _snowman : _snowman) {
         if (_snowman.g() != null && _snowman.g().b(_snowman)) {
            return _snowman;
         }
      }

      return null;
   }

   protected boolean a(brc var1, cra var2) {
      int _snowman = Math.max(this.n.a - 1, _snowman.a);
      int _snowmanx = Math.max(this.n.b - 1, _snowman.b);
      int _snowmanxx = Math.max(this.n.c - 1, _snowman.c);
      int _snowmanxxx = Math.min(this.n.d + 1, _snowman.d);
      int _snowmanxxxx = Math.min(this.n.e + 1, _snowman.e);
      int _snowmanxxxxx = Math.min(this.n.f + 1, _snowman.f);
      fx.a _snowmanxxxxxx = new fx.a();

      for (int _snowmanxxxxxxx = _snowman; _snowmanxxxxxxx <= _snowmanxxx; _snowmanxxxxxxx++) {
         for (int _snowmanxxxxxxxx = _snowmanxx; _snowmanxxxxxxxx <= _snowmanxxxxx; _snowmanxxxxxxxx++) {
            if (_snowman.d_(_snowmanxxxxxx.d(_snowmanxxxxxxx, _snowmanx, _snowmanxxxxxxxx)).c().a()) {
               return true;
            }

            if (_snowman.d_(_snowmanxxxxxx.d(_snowmanxxxxxxx, _snowmanxxxx, _snowmanxxxxxxxx)).c().a()) {
               return true;
            }
         }
      }

      for (int _snowmanxxxxxxx = _snowman; _snowmanxxxxxxx <= _snowmanxxx; _snowmanxxxxxxx++) {
         for (int _snowmanxxxxxxxx = _snowmanx; _snowmanxxxxxxxx <= _snowmanxxxx; _snowmanxxxxxxxx++) {
            if (_snowman.d_(_snowmanxxxxxx.d(_snowmanxxxxxxx, _snowmanxxxxxxxx, _snowmanxx)).c().a()) {
               return true;
            }

            if (_snowman.d_(_snowmanxxxxxx.d(_snowmanxxxxxxx, _snowmanxxxxxxxx, _snowmanxxxxx)).c().a()) {
               return true;
            }
         }
      }

      for (int _snowmanxxxxxxx = _snowmanxx; _snowmanxxxxxxx <= _snowmanxxxxx; _snowmanxxxxxxx++) {
         for (int _snowmanxxxxxxxx = _snowmanx; _snowmanxxxxxxxx <= _snowmanxxxx; _snowmanxxxxxxxx++) {
            if (_snowman.d_(_snowmanxxxxxx.d(_snowman, _snowmanxxxxxxxx, _snowmanxxxxxxx)).c().a()) {
               return true;
            }

            if (_snowman.d_(_snowmanxxxxxx.d(_snowmanxxx, _snowmanxxxxxxxx, _snowmanxxxxxxx)).c().a()) {
               return true;
            }
         }
      }

      return false;
   }

   protected int a(int var1, int var2) {
      gc _snowman = this.i();
      if (_snowman == null) {
         return _snowman;
      } else {
         switch (_snowman) {
            case c:
            case d:
               return this.n.a + _snowman;
            case e:
               return this.n.d - _snowman;
            case f:
               return this.n.a + _snowman;
            default:
               return _snowman;
         }
      }
   }

   protected int d(int var1) {
      return this.i() == null ? _snowman : _snowman + this.n.b;
   }

   protected int b(int var1, int var2) {
      gc _snowman = this.i();
      if (_snowman == null) {
         return _snowman;
      } else {
         switch (_snowman) {
            case c:
               return this.n.f - _snowman;
            case d:
               return this.n.c + _snowman;
            case e:
            case f:
               return this.n.c + _snowman;
            default:
               return _snowman;
         }
      }
   }

   protected void a(bsr var1, ceh var2, int var3, int var4, int var5, cra var6) {
      fx _snowman = new fx(this.a(_snowman, _snowman), this.d(_snowman), this.b(_snowman, _snowman));
      if (_snowman.b(_snowman)) {
         if (this.b != byg.a) {
            _snowman = _snowman.a(this.b);
         }

         if (this.c != bzm.a) {
            _snowman = _snowman.a(this.c);
         }

         _snowman.a(_snowman, _snowman, 2);
         cux _snowmanx = _snowman.b(_snowman);
         if (!_snowmanx.c()) {
            _snowman.I().a(_snowman, _snowmanx.a(), 0);
         }

         if (e.contains(_snowman.b())) {
            _snowman.z(_snowman).e(_snowman);
         }
      }
   }

   protected ceh a(brc var1, int var2, int var3, int var4, cra var5) {
      int _snowman = this.a(_snowman, _snowman);
      int _snowmanx = this.d(_snowman);
      int _snowmanxx = this.b(_snowman, _snowman);
      fx _snowmanxxx = new fx(_snowman, _snowmanx, _snowmanxx);
      return !_snowman.b(_snowmanxxx) ? bup.a.n() : _snowman.d_(_snowmanxxx);
   }

   protected boolean a(brz var1, int var2, int var3, int var4, cra var5) {
      int _snowman = this.a(_snowman, _snowman);
      int _snowmanx = this.d(_snowman + 1);
      int _snowmanxx = this.b(_snowman, _snowman);
      fx _snowmanxxx = new fx(_snowman, _snowmanx, _snowmanxx);
      return !_snowman.b(_snowmanxxx) ? false : _snowmanx < _snowman.a(chn.a.c, _snowman, _snowmanxx);
   }

   protected void b(bsr var1, cra var2, int var3, int var4, int var5, int var6, int var7, int var8) {
      for (int _snowman = _snowman; _snowman <= _snowman; _snowman++) {
         for (int _snowmanx = _snowman; _snowmanx <= _snowman; _snowmanx++) {
            for (int _snowmanxx = _snowman; _snowmanxx <= _snowman; _snowmanxx++) {
               this.a(_snowman, bup.a.n(), _snowmanx, _snowman, _snowmanxx, _snowman);
            }
         }
      }
   }

   protected void a(bsr var1, cra var2, int var3, int var4, int var5, int var6, int var7, int var8, ceh var9, ceh var10, boolean var11) {
      for (int _snowman = _snowman; _snowman <= _snowman; _snowman++) {
         for (int _snowmanx = _snowman; _snowmanx <= _snowman; _snowmanx++) {
            for (int _snowmanxx = _snowman; _snowmanxx <= _snowman; _snowmanxx++) {
               if (!_snowman || !this.a((brc)_snowman, _snowmanx, _snowman, _snowmanxx, _snowman).g()) {
                  if (_snowman != _snowman && _snowman != _snowman && _snowmanx != _snowman && _snowmanx != _snowman && _snowmanxx != _snowman && _snowmanxx != _snowman) {
                     this.a(_snowman, _snowman, _snowmanx, _snowman, _snowmanxx, _snowman);
                  } else {
                     this.a(_snowman, _snowman, _snowmanx, _snowman, _snowmanxx, _snowman);
                  }
               }
            }
         }
      }
   }

   protected void a(bsr var1, cra var2, int var3, int var4, int var5, int var6, int var7, int var8, boolean var9, Random var10, cru.a var11) {
      for (int _snowman = _snowman; _snowman <= _snowman; _snowman++) {
         for (int _snowmanx = _snowman; _snowmanx <= _snowman; _snowmanx++) {
            for (int _snowmanxx = _snowman; _snowmanxx <= _snowman; _snowmanxx++) {
               if (!_snowman || !this.a((brc)_snowman, _snowmanx, _snowman, _snowmanxx, _snowman).g()) {
                  _snowman.a(_snowman, _snowmanx, _snowman, _snowmanxx, _snowman == _snowman || _snowman == _snowman || _snowmanx == _snowman || _snowmanx == _snowman || _snowmanxx == _snowman || _snowmanxx == _snowman);
                  this.a(_snowman, _snowman.a(), _snowmanx, _snowman, _snowmanxx, _snowman);
               }
            }
         }
      }
   }

   protected void a(
      bsr var1,
      cra var2,
      Random var3,
      float var4,
      int var5,
      int var6,
      int var7,
      int var8,
      int var9,
      int var10,
      ceh var11,
      ceh var12,
      boolean var13,
      boolean var14
   ) {
      for (int _snowman = _snowman; _snowman <= _snowman; _snowman++) {
         for (int _snowmanx = _snowman; _snowmanx <= _snowman; _snowmanx++) {
            for (int _snowmanxx = _snowman; _snowmanxx <= _snowman; _snowmanxx++) {
               if (!(_snowman.nextFloat() > _snowman) && (!_snowman || !this.a((brc)_snowman, _snowmanx, _snowman, _snowmanxx, _snowman).g()) && (!_snowman || this.a((brz)_snowman, _snowmanx, _snowman, _snowmanxx, _snowman))) {
                  if (_snowman != _snowman && _snowman != _snowman && _snowmanx != _snowman && _snowmanx != _snowman && _snowmanxx != _snowman && _snowmanxx != _snowman) {
                     this.a(_snowman, _snowman, _snowmanx, _snowman, _snowmanxx, _snowman);
                  } else {
                     this.a(_snowman, _snowman, _snowmanx, _snowman, _snowmanxx, _snowman);
                  }
               }
            }
         }
      }
   }

   protected void a(bsr var1, cra var2, Random var3, float var4, int var5, int var6, int var7, ceh var8) {
      if (_snowman.nextFloat() < _snowman) {
         this.a(_snowman, _snowman, _snowman, _snowman, _snowman, _snowman);
      }
   }

   protected void a(bsr var1, cra var2, int var3, int var4, int var5, int var6, int var7, int var8, ceh var9, boolean var10) {
      float _snowman = (float)(_snowman - _snowman + 1);
      float _snowmanx = (float)(_snowman - _snowman + 1);
      float _snowmanxx = (float)(_snowman - _snowman + 1);
      float _snowmanxxx = (float)_snowman + _snowman / 2.0F;
      float _snowmanxxxx = (float)_snowman + _snowmanxx / 2.0F;

      for (int _snowmanxxxxx = _snowman; _snowmanxxxxx <= _snowman; _snowmanxxxxx++) {
         float _snowmanxxxxxx = (float)(_snowmanxxxxx - _snowman) / _snowmanx;

         for (int _snowmanxxxxxxx = _snowman; _snowmanxxxxxxx <= _snowman; _snowmanxxxxxxx++) {
            float _snowmanxxxxxxxx = ((float)_snowmanxxxxxxx - _snowmanxxx) / (_snowman * 0.5F);

            for (int _snowmanxxxxxxxxx = _snowman; _snowmanxxxxxxxxx <= _snowman; _snowmanxxxxxxxxx++) {
               float _snowmanxxxxxxxxxx = ((float)_snowmanxxxxxxxxx - _snowmanxxxx) / (_snowmanxx * 0.5F);
               if (!_snowman || !this.a((brc)_snowman, _snowmanxxxxxxx, _snowmanxxxxx, _snowmanxxxxxxxxx, _snowman).g()) {
                  float _snowmanxxxxxxxxxxx = _snowmanxxxxxxxx * _snowmanxxxxxxxx + _snowmanxxxxxx * _snowmanxxxxxx + _snowmanxxxxxxxxxx * _snowmanxxxxxxxxxx;
                  if (_snowmanxxxxxxxxxxx <= 1.05F) {
                     this.a(_snowman, _snowman, _snowmanxxxxxxx, _snowmanxxxxx, _snowmanxxxxxxxxx, _snowman);
                  }
               }
            }
         }
      }
   }

   protected void b(bsr var1, ceh var2, int var3, int var4, int var5, cra var6) {
      int _snowman = this.a(_snowman, _snowman);
      int _snowmanx = this.d(_snowman);
      int _snowmanxx = this.b(_snowman, _snowman);
      if (_snowman.b(new fx(_snowman, _snowmanx, _snowmanxx))) {
         while ((_snowman.w(new fx(_snowman, _snowmanx, _snowmanxx)) || _snowman.d_(new fx(_snowman, _snowmanx, _snowmanxx)).c().a()) && _snowmanx > 1) {
            _snowman.a(new fx(_snowman, _snowmanx, _snowmanxx), _snowman, 2);
            _snowmanx--;
         }
      }
   }

   protected boolean a(bsr var1, cra var2, Random var3, int var4, int var5, int var6, vk var7) {
      fx _snowman = new fx(this.a(_snowman, _snowman), this.d(_snowman), this.b(_snowman, _snowman));
      return this.a(_snowman, _snowman, _snowman, _snowman, _snowman, null);
   }

   public static ceh a(brc var0, fx var1, ceh var2) {
      gc _snowman = null;

      for (gc _snowmanx : gc.c.a) {
         fx _snowmanxx = _snowman.a(_snowmanx);
         ceh _snowmanxxx = _snowman.d_(_snowmanxx);
         if (_snowmanxxx.a(bup.bR)) {
            return _snowman;
         }

         if (_snowmanxxx.i(_snowman, _snowmanxx)) {
            if (_snowman != null) {
               _snowman = null;
               break;
            }

            _snowman = _snowmanx;
         }
      }

      if (_snowman != null) {
         return _snowman.a(bxm.aq, _snowman.f());
      } else {
         gc _snowmanx = _snowman.c(bxm.aq);
         fx _snowmanxxxx = _snowman.a(_snowmanx);
         if (_snowman.d_(_snowmanxxxx).i(_snowman, _snowmanxxxx)) {
            _snowmanx = _snowmanx.f();
            _snowmanxxxx = _snowman.a(_snowmanx);
         }

         if (_snowman.d_(_snowmanxxxx).i(_snowman, _snowmanxxxx)) {
            _snowmanx = _snowmanx.g();
            _snowmanxxxx = _snowman.a(_snowmanx);
         }

         if (_snowman.d_(_snowmanxxxx).i(_snowman, _snowmanxxxx)) {
            _snowmanx = _snowmanx.f();
            _snowmanxxxx = _snowman.a(_snowmanx);
         }

         return _snowman.a(bxm.aq, _snowmanx);
      }
   }

   protected boolean a(bsk var1, cra var2, Random var3, fx var4, vk var5, @Nullable ceh var6) {
      if (_snowman.b(_snowman) && !_snowman.d_(_snowman).a(bup.bR)) {
         if (_snowman == null) {
            _snowman = a(_snowman, _snowman, bup.bR.n());
         }

         _snowman.a(_snowman, _snowman, 2);
         ccj _snowman = _snowman.c(_snowman);
         if (_snowman instanceof ccn) {
            ((ccn)_snowman).a(_snowman, _snowman.nextLong());
         }

         return true;
      } else {
         return false;
      }
   }

   protected boolean a(bsr var1, cra var2, Random var3, int var4, int var5, int var6, gc var7, vk var8) {
      fx _snowman = new fx(this.a(_snowman, _snowman), this.d(_snowman), this.b(_snowman, _snowman));
      if (_snowman.b(_snowman) && !_snowman.d_(_snowman).a(bup.as)) {
         this.a(_snowman, bup.as.n().a(bwa.a, _snowman), _snowman, _snowman, _snowman, _snowman);
         ccj _snowmanx = _snowman.c(_snowman);
         if (_snowmanx instanceof ccs) {
            ((ccs)_snowmanx).a(_snowman, _snowman.nextLong());
         }

         return true;
      } else {
         return false;
      }
   }

   public void a(int var1, int var2, int var3) {
      this.n.a(_snowman, _snowman, _snowman);
   }

   @Nullable
   public gc i() {
      return this.a;
   }

   public void a(@Nullable gc var1) {
      this.a = _snowman;
      if (_snowman == null) {
         this.c = bzm.a;
         this.b = byg.a;
      } else {
         switch (_snowman) {
            case d:
               this.b = byg.b;
               this.c = bzm.a;
               break;
            case e:
               this.b = byg.b;
               this.c = bzm.b;
               break;
            case f:
               this.b = byg.a;
               this.c = bzm.b;
               break;
            default:
               this.b = byg.a;
               this.c = bzm.a;
         }
      }
   }

   public bzm ap_() {
      return this.c;
   }

   public clb k() {
      return this.d;
   }

   public abstract static class a {
      protected ceh a = bup.a.n();

      protected a() {
      }

      public abstract void a(Random var1, int var2, int var3, int var4, boolean var5);

      public ceh a() {
         return this.a;
      }
   }
}
