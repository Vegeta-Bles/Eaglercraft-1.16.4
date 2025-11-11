import com.google.common.collect.Sets;
import java.util.Collection;
import java.util.Set;

public class bgc extends bga {
   private static final us<Integer> f = uv.a(bgc.class, uu.b);
   private bnt g = bnw.a;
   private final Set<apu> ag = Sets.newHashSet();
   private boolean ah;

   public bgc(aqe<? extends bgc> var1, brx var2) {
      super(_snowman, _snowman);
   }

   public bgc(brx var1, double var2, double var4, double var6) {
      super(aqe.c, _snowman, _snowman, _snowman, _snowman);
   }

   public bgc(brx var1, aqm var2) {
      super(aqe.c, _snowman, _snowman);
   }

   public void b(bmb var1) {
      if (_snowman.b() == bmd.ql) {
         this.g = bnv.d(_snowman);
         Collection<apu> _snowman = bnv.b(_snowman);
         if (!_snowman.isEmpty()) {
            for (apu _snowmanx : _snowman) {
               this.ag.add(new apu(_snowmanx));
            }
         }

         int _snowmanx = c(_snowman);
         if (_snowmanx == -1) {
            this.z();
         } else {
            this.c(_snowmanx);
         }
      } else if (_snowman.b() == bmd.kd) {
         this.g = bnw.a;
         this.ag.clear();
         this.R.b(f, -1);
      }
   }

   public static int c(bmb var0) {
      md _snowman = _snowman.o();
      return _snowman != null && _snowman.c("CustomPotionColor", 99) ? _snowman.h("CustomPotionColor") : -1;
   }

   private void z() {
      this.ah = false;
      if (this.g == bnw.a && this.ag.isEmpty()) {
         this.R.b(f, -1);
      } else {
         this.R.b(f, bnv.a(bnv.a(this.g, this.ag)));
      }
   }

   public void a(apu var1) {
      this.ag.add(_snowman);
      this.ab().b(f, bnv.a(bnv.a(this.g, this.ag)));
   }

   @Override
   protected void e() {
      super.e();
      this.R.a(f, -1);
   }

   @Override
   public void j() {
      super.j();
      if (this.l.v) {
         if (this.b) {
            if (this.c % 5 == 0) {
               this.b(1);
            }
         } else {
            this.b(2);
         }
      } else if (this.b && this.c != 0 && !this.ag.isEmpty() && this.c >= 600) {
         this.l.a(this, (byte)0);
         this.g = bnw.a;
         this.ag.clear();
         this.R.b(f, -1);
      }
   }

   private void b(int var1) {
      int _snowman = this.u();
      if (_snowman != -1 && _snowman > 0) {
         double _snowmanx = (double)(_snowman >> 16 & 0xFF) / 255.0;
         double _snowmanxx = (double)(_snowman >> 8 & 0xFF) / 255.0;
         double _snowmanxxx = (double)(_snowman >> 0 & 0xFF) / 255.0;

         for (int _snowmanxxxx = 0; _snowmanxxxx < _snowman; _snowmanxxxx++) {
            this.l.a(hh.u, this.d(0.5), this.cF(), this.g(0.5), _snowmanx, _snowmanxx, _snowmanxxx);
         }
      }
   }

   public int u() {
      return this.R.a(f);
   }

   private void c(int var1) {
      this.ah = true;
      this.R.b(f, _snowman);
   }

   @Override
   public void b(md var1) {
      super.b(_snowman);
      if (this.g != bnw.a && this.g != null) {
         _snowman.a("Potion", gm.U.b(this.g).toString());
      }

      if (this.ah) {
         _snowman.b("Color", this.u());
      }

      if (!this.ag.isEmpty()) {
         mj _snowman = new mj();

         for (apu _snowmanx : this.ag) {
            _snowman.add(_snowmanx.a(new md()));
         }

         _snowman.a("CustomPotionEffects", _snowman);
      }
   }

   @Override
   public void a(md var1) {
      super.a(_snowman);
      if (_snowman.c("Potion", 8)) {
         this.g = bnv.c(_snowman);
      }

      for (apu _snowman : bnv.b(_snowman)) {
         this.a(_snowman);
      }

      if (_snowman.c("Color", 99)) {
         this.c(_snowman.h("Color"));
      } else {
         this.z();
      }
   }

   @Override
   protected void a(aqm var1) {
      super.a(_snowman);

      for (apu _snowman : this.g.a()) {
         _snowman.c(new apu(_snowman.a(), Math.max(_snowman.b() / 8, 1), _snowman.c(), _snowman.d(), _snowman.e()));
      }

      if (!this.ag.isEmpty()) {
         for (apu _snowman : this.ag) {
            _snowman.c(_snowman);
         }
      }
   }

   @Override
   protected bmb m() {
      if (this.ag.isEmpty() && this.g == bnw.a) {
         return new bmb(bmd.kd);
      } else {
         bmb _snowman = new bmb(bmd.ql);
         bnv.a(_snowman, this.g);
         bnv.a(_snowman, this.ag);
         if (this.ah) {
            _snowman.p().b("CustomPotionColor", this.u());
         }

         return _snowman;
      }
   }

   @Override
   public void a(byte var1) {
      if (_snowman == 0) {
         int _snowman = this.u();
         if (_snowman != -1) {
            double _snowmanx = (double)(_snowman >> 16 & 0xFF) / 255.0;
            double _snowmanxx = (double)(_snowman >> 8 & 0xFF) / 255.0;
            double _snowmanxxx = (double)(_snowman >> 0 & 0xFF) / 255.0;

            for (int _snowmanxxxx = 0; _snowmanxxxx < 20; _snowmanxxxx++) {
               this.l.a(hh.u, this.d(0.5), this.cF(), this.g(0.5), _snowmanx, _snowmanxx, _snowmanxxx);
            }
         }
      } else {
         super.a(_snowman);
      }
   }
}
