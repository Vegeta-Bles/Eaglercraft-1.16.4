import com.mojang.datafixers.util.Pair;
import it.unimi.dsi.fastutil.objects.Object2ObjectLinkedOpenHashMap;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class dww {
   private static final Logger a = LogManager.getLogger();
   private final djz b;
   private final dwu c;
   private fx d = new fx(-1, -1, -1);
   private bmb e = bmb.b;
   private float f;
   private float g;
   private int h;
   private boolean i;
   private bru j = bru.b;
   private bru k = bru.a;
   private final Object2ObjectLinkedOpenHashMap<Pair<fx, sz.a>, dcn> l = new Object2ObjectLinkedOpenHashMap();
   private int m;

   public dww(djz var1, dwu var2) {
      this.b = _snowman;
      this.c = _snowman;
   }

   public void a(bfw var1) {
      this.j.a(_snowman.bC);
   }

   public void a(bru var1) {
      this.k = _snowman;
   }

   public void b(bru var1) {
      if (_snowman != this.j) {
         this.k = this.j;
      }

      this.j = _snowman;
      this.j.a(this.b.s.bC);
   }

   public boolean a() {
      return this.j.f();
   }

   public boolean a(fx var1) {
      if (this.b.s.a(this.b.r, _snowman, this.j)) {
         return false;
      } else {
         brx _snowman = this.b.r;
         ceh _snowmanx = _snowman.d_(_snowman);
         if (!this.b.s.dD().b().a(_snowmanx, _snowman, _snowman, this.b.s)) {
            return false;
         } else {
            buo _snowmanxx = _snowmanx.b();
            if ((_snowmanxx instanceof bvi || _snowmanxx instanceof caq || _snowmanxx instanceof bxr) && !this.b.s.eV()) {
               return false;
            } else if (_snowmanx.g()) {
               return false;
            } else {
               _snowmanxx.a(_snowman, _snowman, _snowmanx, this.b.s);
               cux _snowmanxxx = _snowman.b(_snowman);
               boolean _snowmanxxxx = _snowman.a(_snowman, _snowmanxxx.g(), 11);
               if (_snowmanxxxx) {
                  _snowmanxx.a((bry)_snowman, _snowman, _snowmanx);
               }

               return _snowmanxxxx;
            }
         }
      }
   }

   public boolean a(fx var1, gc var2) {
      if (this.b.s.a(this.b.r, _snowman, this.j)) {
         return false;
      } else if (!this.b.r.f().a(_snowman)) {
         return false;
      } else {
         if (this.j.e()) {
            ceh _snowman = this.b.r.d_(_snowman);
            this.b.ao().a(this.b.r, _snowman, _snowman, 1.0F);
            this.a(sz.a.a, _snowman, _snowman);
            this.a(_snowman);
            this.h = 5;
         } else if (!this.i || !this.b(_snowman)) {
            if (this.i) {
               this.a(sz.a.b, this.d, _snowman);
            }

            ceh _snowman = this.b.r.d_(_snowman);
            this.b.ao().a(this.b.r, _snowman, _snowman, 0.0F);
            this.a(sz.a.a, _snowman, _snowman);
            boolean _snowmanx = !_snowman.g();
            if (_snowmanx && this.f == 0.0F) {
               _snowman.a(this.b.r, _snowman, this.b.s);
            }

            if (_snowmanx && _snowman.a(this.b.s, this.b.s.l, _snowman) >= 1.0F) {
               this.a(_snowman);
            } else {
               this.i = true;
               this.d = _snowman;
               this.e = this.b.s.dD();
               this.f = 0.0F;
               this.g = 0.0F;
               this.b.r.a(this.b.s.Y(), this.d, (int)(this.f * 10.0F) - 1);
            }
         }

         return true;
      }
   }

   public void b() {
      if (this.i) {
         ceh _snowman = this.b.r.d_(this.d);
         this.b.ao().a(this.b.r, this.d, _snowman, -1.0F);
         this.a(sz.a.b, this.d, gc.a);
         this.i = false;
         this.f = 0.0F;
         this.b.r.a(this.b.s.Y(), this.d, -1);
         this.b.s.eS();
      }
   }

   public boolean b(fx var1, gc var2) {
      this.n();
      if (this.h > 0) {
         this.h--;
         return true;
      } else if (this.j.e() && this.b.r.f().a(_snowman)) {
         this.h = 5;
         ceh _snowman = this.b.r.d_(_snowman);
         this.b.ao().a(this.b.r, _snowman, _snowman, 1.0F);
         this.a(sz.a.a, _snowman, _snowman);
         this.a(_snowman);
         return true;
      } else if (this.b(_snowman)) {
         ceh _snowman = this.b.r.d_(_snowman);
         if (_snowman.g()) {
            this.i = false;
            return false;
         } else {
            this.f = this.f + _snowman.a(this.b.s, this.b.s.l, _snowman);
            if (this.g % 4.0F == 0.0F) {
               cae _snowmanx = _snowman.o();
               this.b.W().a(new emp(_snowmanx.f(), adr.e, (_snowmanx.a() + 1.0F) / 8.0F, _snowmanx.b() * 0.5F, _snowman));
            }

            this.g++;
            this.b.ao().a(this.b.r, _snowman, _snowman, afm.a(this.f, 0.0F, 1.0F));
            if (this.f >= 1.0F) {
               this.i = false;
               this.a(sz.a.c, _snowman, _snowman);
               this.a(_snowman);
               this.f = 0.0F;
               this.g = 0.0F;
               this.h = 5;
            }

            this.b.r.a(this.b.s.Y(), this.d, (int)(this.f * 10.0F) - 1);
            return true;
         }
      } else {
         return this.a(_snowman, _snowman);
      }
   }

   public float c() {
      return this.j.e() ? 5.0F : 4.5F;
   }

   public void d() {
      this.n();
      if (this.c.a().h()) {
         this.c.a().a();
      } else {
         this.c.a().m();
      }
   }

   private boolean b(fx var1) {
      bmb _snowman = this.b.s.dD();
      boolean _snowmanx = this.e.a() && _snowman.a();
      if (!this.e.a() && !_snowman.a()) {
         _snowmanx = _snowman.b() == this.e.b() && bmb.a(_snowman, this.e) && (_snowman.e() || _snowman.g() == this.e.g());
      }

      return _snowman.equals(this.d) && _snowmanx;
   }

   private void n() {
      int _snowman = this.b.s.bm.d;
      if (_snowman != this.m) {
         this.m = _snowman;
         this.c.a(new tj(this.m));
      }
   }

   public aou a(dzm var1, dwt var2, aot var3, dcj var4) {
      this.n();
      fx _snowman = _snowman.a();
      if (!this.b.r.f().a(_snowman)) {
         return aou.d;
      } else {
         bmb _snowmanx = _snowman.b(_snowman);
         if (this.j == bru.e) {
            this.c.a(new ts(_snowman, _snowman));
            return aou.a;
         } else {
            boolean _snowmanxx = !_snowman.dD().a() || !_snowman.dE().a();
            boolean _snowmanxxx = _snowman.eq() && _snowmanxx;
            if (!_snowmanxxx) {
               aou _snowmanxxxx = _snowman.d_(_snowman).a(_snowman, _snowman, _snowman, _snowman);
               if (_snowmanxxxx.a()) {
                  this.c.a(new ts(_snowman, _snowman));
                  return _snowmanxxxx;
               }
            }

            this.c.a(new ts(_snowman, _snowman));
            if (!_snowmanx.a() && !_snowman.eT().a(_snowmanx.b())) {
               boa _snowmanxxxx = new boa(_snowman, _snowman, _snowman);
               aou _snowmanxxxxx;
               if (this.j.e()) {
                  int _snowmanxxxxxx = _snowmanx.E();
                  _snowmanxxxxx = _snowmanx.a(_snowmanxxxx);
                  _snowmanx.e(_snowmanxxxxxx);
               } else {
                  _snowmanxxxxx = _snowmanx.a(_snowmanxxxx);
               }

               return _snowmanxxxxx;
            } else {
               return aou.c;
            }
         }
      }
   }

   public aou a(bfw var1, brx var2, aot var3) {
      if (this.j == bru.e) {
         return aou.c;
      } else {
         this.n();
         this.c.a(new tt(_snowman));
         bmb _snowman = _snowman.b(_snowman);
         if (_snowman.eT().a(_snowman.b())) {
            return aou.c;
         } else {
            int _snowmanx = _snowman.E();
            aov<bmb> _snowmanxx = _snowman.a(_snowman, _snowman, _snowman);
            bmb _snowmanxxx = _snowmanxx.b();
            if (_snowmanxxx != _snowman) {
               _snowman.a(_snowman, _snowmanxxx);
            }

            return _snowmanxx.a();
         }
      }
   }

   public dzm a(dwt var1, aeb var2, djm var3) {
      return this.a(_snowman, _snowman, _snowman, false, false);
   }

   public dzm a(dwt var1, aeb var2, djm var3, boolean var4, boolean var5) {
      return new dzm(this.b, _snowman, this.c, _snowman, _snowman, _snowman, _snowman);
   }

   public void a(bfw var1, aqa var2) {
      this.n();
      this.c.a(new sp(_snowman, _snowman.bu()));
      if (this.j != bru.e) {
         _snowman.f(_snowman);
         _snowman.eS();
      }
   }

   public aou a(bfw var1, aqa var2, aot var3) {
      this.n();
      this.c.a(new sp(_snowman, _snowman, _snowman.bu()));
      return this.j == bru.e ? aou.c : _snowman.a(_snowman, _snowman);
   }

   public aou a(bfw var1, aqa var2, dck var3, aot var4) {
      this.n();
      dcn _snowman = _snowman.e().a(_snowman.cD(), _snowman.cE(), _snowman.cH());
      this.c.a(new sp(_snowman, _snowman, _snowman, _snowman.bu()));
      return this.j == bru.e ? aou.c : _snowman.a(_snowman, _snowman, _snowman);
   }

   public bmb a(int var1, int var2, int var3, bik var4, bfw var5) {
      short _snowman = _snowman.bp.a(_snowman.bm);
      bmb _snowmanx = _snowman.bp.a(_snowman, _snowman, _snowman, _snowman);
      this.c.a(new sk(_snowman, _snowman, _snowman, _snowman, _snowmanx, _snowman));
      return _snowmanx;
   }

   public void a(int var1, boq<?> var2, boolean var3) {
      this.c.a(new sx(_snowman, _snowman, _snowman));
   }

   public void a(int var1, int var2) {
      this.c.a(new sj(_snowman, _snowman));
   }

   public void a(bmb var1, int var2) {
      if (this.j.e()) {
         this.c.a(new tm(_snowman, _snowman));
      }
   }

   public void a(bmb var1) {
      if (this.j.e() && !_snowman.a()) {
         this.c.a(new tm(-1, _snowman));
      }
   }

   public void b(bfw var1) {
      this.n();
      this.c.a(new sz(sz.a.f, fx.b, gc.a));
      _snowman.eb();
   }

   public boolean e() {
      return this.j.f();
   }

   public boolean f() {
      return !this.j.e();
   }

   public boolean g() {
      return this.j.e();
   }

   public boolean h() {
      return this.j.e();
   }

   public boolean i() {
      return this.b.s.br() && this.b.s.ct() instanceof bbb;
   }

   public boolean j() {
      return this.j == bru.e;
   }

   public bru k() {
      return this.k;
   }

   public bru l() {
      return this.j;
   }

   public boolean m() {
      return this.i;
   }

   public void a(int var1) {
      this.c.a(new sw(_snowman));
   }

   private void a(sz.a var1, fx var2, gc var3) {
      dzm _snowman = this.b.s;
      this.l.put(Pair.of(_snowman, _snowman), _snowman.cA());
      this.c.a(new sz(_snowman, _snowman, _snowman));
   }

   public void a(dwt var1, fx var2, ceh var3, sz.a var4, boolean var5) {
      dcn _snowman = (dcn)this.l.remove(Pair.of(_snowman, _snowman));
      ceh _snowmanx = _snowman.d_(_snowman);
      if ((_snowman == null || !_snowman || _snowman != sz.a.a && _snowmanx != _snowman) && _snowmanx != _snowman) {
         _snowman.b(_snowman, _snowman);
         bfw _snowmanxx = this.b.s;
         if (_snowman != null && _snowman == _snowmanxx.l && _snowmanxx.a(_snowman, _snowman)) {
            _snowmanxx.f(_snowman.b, _snowman.c, _snowman.d);
         }
      }

      while (this.l.size() >= 50) {
         Pair<fx, sz.a> _snowmanxx = (Pair<fx, sz.a>)this.l.firstKey();
         this.l.removeFirst();
         a.error("Too many unacked block actions, dropping " + _snowmanxx);
      }
   }
}
