import java.util.List;
import java.util.Random;
import javax.annotation.Nullable;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class cdk extends cdl implements cdm {
   private static final Logger a = LogManager.getLogger();
   private long b;
   private int c;
   @Nullable
   private fx g;
   private boolean h;

   public cdk() {
      super(cck.u);
   }

   @Override
   public md a(md var1) {
      super.a(_snowman);
      _snowman.a("Age", this.b);
      if (this.g != null) {
         _snowman.a("ExitPortal", mp.a(this.g));
      }

      if (this.h) {
         _snowman.a("ExactTeleport", this.h);
      }

      return _snowman;
   }

   @Override
   public void a(ceh var1, md var2) {
      super.a(_snowman, _snowman);
      this.b = _snowman.i("Age");
      if (_snowman.c("ExitPortal", 10)) {
         this.g = mp.b(_snowman.p("ExitPortal"));
      }

      this.h = _snowman.q("ExactTeleport");
   }

   @Override
   public double i() {
      return 256.0;
   }

   @Override
   public void aj_() {
      boolean _snowman = this.d();
      boolean _snowmanx = this.f();
      this.b++;
      if (_snowmanx) {
         this.c--;
      } else if (!this.d.v) {
         List<aqa> _snowmanxx = this.d.a(aqa.class, new dci(this.o()), cdk::a);
         if (!_snowmanxx.isEmpty()) {
            this.b(_snowmanxx.get(this.d.t.nextInt(_snowmanxx.size())));
         }

         if (this.b % 2400L == 0L) {
            this.h();
         }
      }

      if (_snowman != this.d() || _snowmanx != this.f()) {
         this.X_();
      }
   }

   public static boolean a(aqa var0) {
      return aqd.g.test(_snowman) && !_snowman.cr().ai();
   }

   public boolean d() {
      return this.b < 200L;
   }

   public boolean f() {
      return this.c > 0;
   }

   public float a(float var1) {
      return afm.a(((float)this.b + _snowman) / 200.0F, 0.0F, 1.0F);
   }

   public float b(float var1) {
      return 1.0F - afm.a(((float)this.c - _snowman) / 40.0F, 0.0F, 1.0F);
   }

   @Nullable
   @Override
   public ow a() {
      return new ow(this.e, 8, this.b());
   }

   @Override
   public md b() {
      return this.a(new md());
   }

   public void h() {
      if (!this.d.v) {
         this.c = 40;
         this.d.a(this.o(), this.p().b(), 1, 0);
         this.X_();
      }
   }

   @Override
   public boolean a_(int var1, int var2) {
      if (_snowman == 1) {
         this.c = 40;
         return true;
      } else {
         return super.a_(_snowman, _snowman);
      }
   }

   public void b(aqa var1) {
      if (this.d instanceof aag && !this.f()) {
         this.c = 100;
         if (this.g == null && this.d.Y() == brx.i) {
            this.a((aag)this.d);
         }

         if (this.g != null) {
            fx _snowman = this.h ? this.g : this.k();
            aqa _snowmanx;
            if (_snowman instanceof bgv) {
               aqa _snowmanxx = ((bgv)_snowman).v();
               if (_snowmanxx instanceof aah) {
                  ac.d.a((aah)_snowmanxx, this.d.d_(this.o()));
               }

               if (_snowmanxx != null) {
                  _snowmanx = _snowmanxx;
                  _snowman.ad();
               } else {
                  _snowmanx = _snowman;
               }
            } else {
               _snowmanx = _snowman.cr();
            }

            _snowmanx.ah();
            _snowmanx.m((double)_snowman.u() + 0.5, (double)_snowman.v(), (double)_snowman.w() + 0.5);
         }

         this.h();
      }
   }

   private fx k() {
      fx _snowman = a(this.d, this.g.b(0, 2, 0), 5, false);
      a.debug("Best exit position for portal at {} is {}", this.g, _snowman);
      return _snowman.b();
   }

   private void a(aag var1) {
      dcn _snowman = new dcn((double)this.o().u(), 0.0, (double)this.o().w()).d();
      dcn _snowmanx = _snowman.a(1024.0);

      for (int _snowmanxx = 16; a(_snowman, _snowmanx).b() > 0 && _snowmanxx-- > 0; _snowmanx = _snowmanx.e(_snowman.a(-16.0))) {
         a.debug("Skipping backwards past nonempty chunk at {}", _snowmanx);
      }

      for (int var6 = 16; a(_snowman, _snowmanx).b() == 0 && var6-- > 0; _snowmanx = _snowmanx.e(_snowman.a(16.0))) {
         a.debug("Skipping forward past empty chunk at {}", _snowmanx);
      }

      a.debug("Found chunk at {}", _snowmanx);
      cgh _snowmanxx = a(_snowman, _snowmanx);
      this.g = a(_snowmanxx);
      if (this.g == null) {
         this.g = new fx(_snowmanx.b + 0.5, 75.0, _snowmanx.d + 0.5);
         a.debug("Failed to find suitable block, settling on {}", this.g);
         kh.e.a(_snowman, _snowman.i().g(), new Random(this.g.a()), this.g);
      } else {
         a.debug("Found block at {}", this.g);
      }

      this.g = a(_snowman, this.g, 16, true);
      a.debug("Creating portal at {}", this.g);
      this.g = this.g.b(10);
      this.a(_snowman, this.g);
      this.X_();
   }

   private static fx a(brc var0, fx var1, int var2, boolean var3) {
      fx _snowman = null;

      for (int _snowmanx = -_snowman; _snowmanx <= _snowman; _snowmanx++) {
         for (int _snowmanxx = -_snowman; _snowmanxx <= _snowman; _snowmanxx++) {
            if (_snowmanx != 0 || _snowmanxx != 0 || _snowman) {
               for (int _snowmanxxx = 255; _snowmanxxx > (_snowman == null ? 0 : _snowman.v()); _snowmanxxx--) {
                  fx _snowmanxxxx = new fx(_snowman.u() + _snowmanx, _snowmanxxx, _snowman.w() + _snowmanxx);
                  ceh _snowmanxxxxx = _snowman.d_(_snowmanxxxx);
                  if (_snowmanxxxxx.r(_snowman, _snowmanxxxx) && (_snowman || !_snowmanxxxxx.a(bup.z))) {
                     _snowman = _snowmanxxxx;
                     break;
                  }
               }
            }
         }
      }

      return _snowman == null ? _snowman : _snowman;
   }

   private static cgh a(brx var0, dcn var1) {
      return _snowman.d(afm.c(_snowman.b / 16.0), afm.c(_snowman.d / 16.0));
   }

   @Nullable
   private static fx a(cgh var0) {
      brd _snowman = _snowman.g();
      fx _snowmanx = new fx(_snowman.d(), 30, _snowman.e());
      int _snowmanxx = _snowman.b() + 16 - 1;
      fx _snowmanxxx = new fx(_snowman.f(), _snowmanxx, _snowman.g());
      fx _snowmanxxxx = null;
      double _snowmanxxxxx = 0.0;

      for (fx _snowmanxxxxxx : fx.a(_snowmanx, _snowmanxxx)) {
         ceh _snowmanxxxxxxx = _snowman.d_(_snowmanxxxxxx);
         fx _snowmanxxxxxxxx = _snowmanxxxxxx.b();
         fx _snowmanxxxxxxxxx = _snowmanxxxxxx.b(2);
         if (_snowmanxxxxxxx.a(bup.ee) && !_snowman.d_(_snowmanxxxxxxxx).r(_snowman, _snowmanxxxxxxxx) && !_snowman.d_(_snowmanxxxxxxxxx).r(_snowman, _snowmanxxxxxxxxx)) {
            double _snowmanxxxxxxxxxx = _snowmanxxxxxx.a(0.0, 0.0, 0.0, true);
            if (_snowmanxxxx == null || _snowmanxxxxxxxxxx < _snowmanxxxxx) {
               _snowmanxxxx = _snowmanxxxxxx;
               _snowmanxxxxx = _snowmanxxxxxxxxxx;
            }
         }
      }

      return _snowmanxxxx;
   }

   private void a(aag var1, fx var2) {
      cjl.D.b(clz.a(this.o(), false)).a(_snowman, _snowman.i().g(), new Random(), _snowman);
   }

   @Override
   public boolean a(gc var1) {
      return buo.c(this.p(), this.d, this.o(), _snowman);
   }

   public int j() {
      int _snowman = 0;

      for (gc _snowmanx : gc.values()) {
         _snowman += this.a(_snowmanx) ? 1 : 0;
      }

      return _snowman;
   }

   public void a(fx var1, boolean var2) {
      this.h = _snowman;
      this.g = _snowman;
   }
}
