import com.google.common.collect.Lists;
import java.util.List;
import java.util.Objects;
import java.util.Random;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import javax.annotation.Nullable;

public class cdj extends ccj {
   private vk a;
   private String b = "";
   private String c = "";
   private fx g = new fx(0, 1, 0);
   private fx h = fx.b;
   private byg i = byg.a;
   private bzm j = bzm.a;
   private cfo k = cfo.d;
   private boolean l = true;
   private boolean m;
   private boolean n;
   private boolean o = true;
   private float p = 1.0F;
   private long q;

   public cdj() {
      super(cck.t);
   }

   @Override
   public double i() {
      return 96.0;
   }

   @Override
   public md a(md var1) {
      super.a(_snowman);
      _snowman.a("name", this.d());
      _snowman.a("author", this.b);
      _snowman.a("metadata", this.c);
      _snowman.b("posX", this.g.u());
      _snowman.b("posY", this.g.v());
      _snowman.b("posZ", this.g.w());
      _snowman.b("sizeX", this.h.u());
      _snowman.b("sizeY", this.h.v());
      _snowman.b("sizeZ", this.h.w());
      _snowman.a("rotation", this.j.toString());
      _snowman.a("mirror", this.i.toString());
      _snowman.a("mode", this.k.toString());
      _snowman.a("ignoreEntities", this.l);
      _snowman.a("powered", this.m);
      _snowman.a("showair", this.n);
      _snowman.a("showboundingbox", this.o);
      _snowman.a("integrity", this.p);
      _snowman.a("seed", this.q);
      return _snowman;
   }

   @Override
   public void a(ceh var1, md var2) {
      super.a(_snowman, _snowman);
      this.a(_snowman.l("name"));
      this.b = _snowman.l("author");
      this.c = _snowman.l("metadata");
      int _snowman = afm.a(_snowman.h("posX"), -48, 48);
      int _snowmanx = afm.a(_snowman.h("posY"), -48, 48);
      int _snowmanxx = afm.a(_snowman.h("posZ"), -48, 48);
      this.g = new fx(_snowman, _snowmanx, _snowmanxx);
      int _snowmanxxx = afm.a(_snowman.h("sizeX"), 0, 48);
      int _snowmanxxxx = afm.a(_snowman.h("sizeY"), 0, 48);
      int _snowmanxxxxx = afm.a(_snowman.h("sizeZ"), 0, 48);
      this.h = new fx(_snowmanxxx, _snowmanxxxx, _snowmanxxxxx);

      try {
         this.j = bzm.valueOf(_snowman.l("rotation"));
      } catch (IllegalArgumentException var12) {
         this.j = bzm.a;
      }

      try {
         this.i = byg.valueOf(_snowman.l("mirror"));
      } catch (IllegalArgumentException var11) {
         this.i = byg.a;
      }

      try {
         this.k = cfo.valueOf(_snowman.l("mode"));
      } catch (IllegalArgumentException var10) {
         this.k = cfo.d;
      }

      this.l = _snowman.q("ignoreEntities");
      this.m = _snowman.q("powered");
      this.n = _snowman.q("showair");
      this.o = _snowman.q("showboundingbox");
      if (_snowman.e("integrity")) {
         this.p = _snowman.j("integrity");
      } else {
         this.p = 1.0F;
      }

      this.q = _snowman.i("seed");
      this.K();
   }

   private void K() {
      if (this.d != null) {
         fx _snowman = this.o();
         ceh _snowmanx = this.d.d_(_snowman);
         if (_snowmanx.a(bup.mY)) {
            this.d.a(_snowman, _snowmanx.a(caq.a, this.k), 2);
         }
      }
   }

   @Nullable
   @Override
   public ow a() {
      return new ow(this.e, 7, this.b());
   }

   @Override
   public md b() {
      return this.a(new md());
   }

   public boolean a(bfw var1) {
      if (!_snowman.eV()) {
         return false;
      } else {
         if (_snowman.cg().v) {
            _snowman.a(this);
         }

         return true;
      }
   }

   public String d() {
      return this.a == null ? "" : this.a.toString();
   }

   public String f() {
      return this.a == null ? "" : this.a.a();
   }

   public boolean g() {
      return this.a != null;
   }

   public void a(@Nullable String var1) {
      this.a(aft.b(_snowman) ? null : vk.a(_snowman));
   }

   public void a(@Nullable vk var1) {
      this.a = _snowman;
   }

   public void a(aqm var1) {
      this.b = _snowman.R().getString();
   }

   public fx h() {
      return this.g;
   }

   public void b(fx var1) {
      this.g = _snowman;
   }

   public fx j() {
      return this.h;
   }

   public void c(fx var1) {
      this.h = _snowman;
   }

   public byg k() {
      return this.i;
   }

   public void b(byg var1) {
      this.i = _snowman;
   }

   public bzm l() {
      return this.j;
   }

   public void b(bzm var1) {
      this.j = _snowman;
   }

   public String m() {
      return this.c;
   }

   public void b(String var1) {
      this.c = _snowman;
   }

   public cfo x() {
      return this.k;
   }

   public void a(cfo var1) {
      this.k = _snowman;
      ceh _snowman = this.d.d_(this.o());
      if (_snowman.a(bup.mY)) {
         this.d.a(this.o(), _snowman.a(caq.a, _snowman), 2);
      }
   }

   public void y() {
      switch (this.x()) {
         case a:
            this.a(cfo.b);
            break;
         case b:
            this.a(cfo.c);
            break;
         case c:
            this.a(cfo.d);
            break;
         case d:
            this.a(cfo.a);
      }
   }

   public boolean z() {
      return this.l;
   }

   public void a(boolean var1) {
      this.l = _snowman;
   }

   public float A() {
      return this.p;
   }

   public void a(float var1) {
      this.p = _snowman;
   }

   public long B() {
      return this.q;
   }

   public void a(long var1) {
      this.q = _snowman;
   }

   public boolean C() {
      if (this.k != cfo.a) {
         return false;
      } else {
         fx _snowman = this.o();
         int _snowmanx = 80;
         fx _snowmanxx = new fx(_snowman.u() - 80, 0, _snowman.w() - 80);
         fx _snowmanxxx = new fx(_snowman.u() + 80, 255, _snowman.w() + 80);
         List<cdj> _snowmanxxxx = this.a(_snowmanxx, _snowmanxxx);
         List<cdj> _snowmanxxxxx = this.a(_snowmanxxxx);
         if (_snowmanxxxxx.size() < 1) {
            return false;
         } else {
            cra _snowmanxxxxxx = this.a(_snowman, _snowmanxxxxx);
            if (_snowmanxxxxxx.d - _snowmanxxxxxx.a > 1 && _snowmanxxxxxx.e - _snowmanxxxxxx.b > 1 && _snowmanxxxxxx.f - _snowmanxxxxxx.c > 1) {
               this.g = new fx(_snowmanxxxxxx.a - _snowman.u() + 1, _snowmanxxxxxx.b - _snowman.v() + 1, _snowmanxxxxxx.c - _snowman.w() + 1);
               this.h = new fx(_snowmanxxxxxx.d - _snowmanxxxxxx.a - 1, _snowmanxxxxxx.e - _snowmanxxxxxx.b - 1, _snowmanxxxxxx.f - _snowmanxxxxxx.c - 1);
               this.X_();
               ceh _snowmanxxxxxxx = this.d.d_(_snowman);
               this.d.a(_snowman, _snowmanxxxxxxx, _snowmanxxxxxxx, 3);
               return true;
            } else {
               return false;
            }
         }
      }
   }

   private List<cdj> a(List<cdj> var1) {
      Predicate<cdj> _snowman = var1x -> var1x.k == cfo.c && Objects.equals(this.a, var1x.a);
      return _snowman.stream().filter(_snowman).collect(Collectors.toList());
   }

   private List<cdj> a(fx var1, fx var2) {
      List<cdj> _snowman = Lists.newArrayList();

      for (fx _snowmanx : fx.a(_snowman, _snowman)) {
         ceh _snowmanxx = this.d.d_(_snowmanx);
         if (_snowmanxx.a(bup.mY)) {
            ccj _snowmanxxx = this.d.c(_snowmanx);
            if (_snowmanxxx != null && _snowmanxxx instanceof cdj) {
               _snowman.add((cdj)_snowmanxxx);
            }
         }
      }

      return _snowman;
   }

   private cra a(fx var1, List<cdj> var2) {
      cra _snowman;
      if (_snowman.size() > 1) {
         fx _snowmanx = _snowman.get(0).o();
         _snowman = new cra(_snowmanx, _snowmanx);
      } else {
         _snowman = new cra(_snowman, _snowman);
      }

      for (cdj _snowmanx : _snowman) {
         fx _snowmanxx = _snowmanx.o();
         if (_snowmanxx.u() < _snowman.a) {
            _snowman.a = _snowmanxx.u();
         } else if (_snowmanxx.u() > _snowman.d) {
            _snowman.d = _snowmanxx.u();
         }

         if (_snowmanxx.v() < _snowman.b) {
            _snowman.b = _snowmanxx.v();
         } else if (_snowmanxx.v() > _snowman.e) {
            _snowman.e = _snowmanxx.v();
         }

         if (_snowmanxx.w() < _snowman.c) {
            _snowman.c = _snowmanxx.w();
         } else if (_snowmanxx.w() > _snowman.f) {
            _snowman.f = _snowmanxx.w();
         }
      }

      return _snowman;
   }

   public boolean D() {
      return this.b(true);
   }

   public boolean b(boolean var1) {
      if (this.k == cfo.a && !this.d.v && this.a != null) {
         fx _snowman = this.o().a(this.g);
         aag _snowmanx = (aag)this.d;
         csw _snowmanxx = _snowmanx.n();

         ctb _snowmanxxx;
         try {
            _snowmanxxx = _snowmanxx.a(this.a);
         } catch (v var8) {
            return false;
         }

         _snowmanxxx.a(this.d, _snowman, this.h, !this.l, bup.iN);
         _snowmanxxx.a(this.b);
         if (_snowman) {
            try {
               return _snowmanxx.c(this.a);
            } catch (v var7) {
               return false;
            }
         } else {
            return true;
         }
      } else {
         return false;
      }
   }

   public boolean a(aag var1) {
      return this.a(_snowman, true);
   }

   private static Random b(long var0) {
      return _snowman == 0L ? new Random(x.b()) : new Random(_snowman);
   }

   public boolean a(aag var1, boolean var2) {
      if (this.k == cfo.b && this.a != null) {
         csw _snowman = _snowman.n();

         ctb _snowmanx;
         try {
            _snowmanx = _snowman.b(this.a);
         } catch (v var6) {
            return false;
         }

         return _snowmanx == null ? false : this.a(_snowman, _snowman, _snowmanx);
      } else {
         return false;
      }
   }

   public boolean a(aag var1, boolean var2, ctb var3) {
      fx _snowman = this.o();
      if (!aft.b(_snowman.b())) {
         this.b = _snowman.b();
      }

      fx _snowmanx = _snowman.a();
      boolean _snowmanxx = this.h.equals(_snowmanx);
      if (!_snowmanxx) {
         this.h = _snowmanx;
         this.X_();
         ceh _snowmanxxx = _snowman.d_(_snowman);
         _snowman.a(_snowman, _snowmanxxx, _snowmanxxx, 3);
      }

      if (_snowman && !_snowmanxx) {
         return false;
      } else {
         csx _snowmanxxx = new csx().a(this.i).a(this.j).a(this.l).a(null);
         if (this.p < 1.0F) {
            _snowmanxxx.b().a(new csg(afm.a(this.p, 0.0F, 1.0F))).a(b(this.q));
         }

         fx _snowmanxxxx = _snowman.a(this.g);
         _snowman.a(_snowman, _snowmanxxxx, _snowmanxxx, b(this.q));
         return true;
      }
   }

   public void E() {
      if (this.a != null) {
         aag _snowman = (aag)this.d;
         csw _snowmanx = _snowman.n();
         _snowmanx.d(this.a);
      }
   }

   public boolean F() {
      if (this.k == cfo.b && !this.d.v && this.a != null) {
         aag _snowman = (aag)this.d;
         csw _snowmanx = _snowman.n();

         try {
            return _snowmanx.b(this.a) != null;
         } catch (v var4) {
            return false;
         }
      } else {
         return false;
      }
   }

   public boolean G() {
      return this.m;
   }

   public void c(boolean var1) {
      this.m = _snowman;
   }

   public boolean H() {
      return this.n;
   }

   public void d(boolean var1) {
      this.n = _snowman;
   }

   public boolean I() {
      return this.o;
   }

   public void e(boolean var1) {
      this.o = _snowman;
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
