import com.google.common.collect.Lists;
import java.util.List;
import java.util.Random;
import java.util.UUID;
import javax.annotation.Nullable;

public class ccq extends ccj implements cdm {
   private static final buo[] b = new buo[]{bup.gq, bup.gr, bup.gz, bup.gs};
   public int a;
   private float c;
   private boolean g;
   private boolean h;
   private final List<fx> i = Lists.newArrayList();
   @Nullable
   private aqm j;
   @Nullable
   private UUID k;
   private long l;

   public ccq() {
      this(cck.y);
   }

   public ccq(cck<?> var1) {
      super(_snowman);
   }

   @Override
   public void a(ceh var1, md var2) {
      super.a(_snowman, _snowman);
      if (_snowman.b("Target")) {
         this.k = _snowman.a("Target");
      } else {
         this.k = null;
      }
   }

   @Override
   public md a(md var1) {
      super.a(_snowman);
      if (this.j != null) {
         _snowman.a("Target", this.j.bS());
      }

      return _snowman;
   }

   @Nullable
   @Override
   public ow a() {
      return new ow(this.e, 5, this.b());
   }

   @Override
   public md b() {
      return this.a(new md());
   }

   @Override
   public void aj_() {
      this.a++;
      long _snowman = this.d.T();
      if (_snowman % 40L == 0L) {
         this.a(this.h());
         if (!this.d.v && this.d()) {
            this.j();
            this.k();
         }
      }

      if (_snowman % 80L == 0L && this.d()) {
         this.a(adq.bZ);
      }

      if (_snowman > this.l && this.d()) {
         this.l = _snowman + 60L + (long)this.d.u_().nextInt(40);
         this.a(adq.ca);
      }

      if (this.d.v) {
         this.l();
         this.y();
         if (this.d()) {
            this.c++;
         }
      }
   }

   private boolean h() {
      this.i.clear();

      for (int _snowman = -1; _snowman <= 1; _snowman++) {
         for (int _snowmanx = -1; _snowmanx <= 1; _snowmanx++) {
            for (int _snowmanxx = -1; _snowmanxx <= 1; _snowmanxx++) {
               fx _snowmanxxx = this.e.b(_snowman, _snowmanx, _snowmanxx);
               if (!this.d.A(_snowmanxxx)) {
                  return false;
               }
            }
         }
      }

      for (int _snowman = -2; _snowman <= 2; _snowman++) {
         for (int _snowmanx = -2; _snowmanx <= 2; _snowmanx++) {
            for (int _snowmanxxx = -2; _snowmanxxx <= 2; _snowmanxxx++) {
               int _snowmanxxxx = Math.abs(_snowman);
               int _snowmanxxxxx = Math.abs(_snowmanx);
               int _snowmanxxxxxx = Math.abs(_snowmanxxx);
               if ((_snowmanxxxx > 1 || _snowmanxxxxx > 1 || _snowmanxxxxxx > 1)
                  && (_snowman == 0 && (_snowmanxxxxx == 2 || _snowmanxxxxxx == 2) || _snowmanx == 0 && (_snowmanxxxx == 2 || _snowmanxxxxxx == 2) || _snowmanxxx == 0 && (_snowmanxxxx == 2 || _snowmanxxxxx == 2))) {
                  fx _snowmanxxxxxxx = this.e.b(_snowman, _snowmanx, _snowmanxxx);
                  ceh _snowmanxxxxxxxx = this.d.d_(_snowmanxxxxxxx);

                  for (buo _snowmanxxxxxxxxx : b) {
                     if (_snowmanxxxxxxxx.a(_snowmanxxxxxxxxx)) {
                        this.i.add(_snowmanxxxxxxx);
                     }
                  }
               }
            }
         }
      }

      this.b(this.i.size() >= 42);
      return this.i.size() >= 16;
   }

   private void j() {
      int _snowman = this.i.size();
      int _snowmanx = _snowman / 7 * 16;
      int _snowmanxx = this.e.u();
      int _snowmanxxx = this.e.v();
      int _snowmanxxxx = this.e.w();
      dci _snowmanxxxxx = new dci((double)_snowmanxx, (double)_snowmanxxx, (double)_snowmanxxxx, (double)(_snowmanxx + 1), (double)(_snowmanxxx + 1), (double)(_snowmanxxxx + 1))
         .g((double)_snowmanx)
         .b(0.0, (double)this.d.L(), 0.0);
      List<bfw> _snowmanxxxxxx = this.d.a(bfw.class, _snowmanxxxxx);
      if (!_snowmanxxxxxx.isEmpty()) {
         for (bfw _snowmanxxxxxxx : _snowmanxxxxxx) {
            if (this.e.a(_snowmanxxxxxxx.cB(), (double)_snowmanx) && _snowmanxxxxxxx.aF()) {
               _snowmanxxxxxxx.c(new apu(apw.C, 260, 0, true, true));
            }
         }
      }
   }

   private void k() {
      aqm _snowman = this.j;
      int _snowmanx = this.i.size();
      if (_snowmanx < 42) {
         this.j = null;
      } else if (this.j == null && this.k != null) {
         this.j = this.x();
         this.k = null;
      } else if (this.j == null) {
         List<aqm> _snowmanxx = this.d.a(aqm.class, this.m(), var0 -> var0 instanceof bdi && var0.aF());
         if (!_snowmanxx.isEmpty()) {
            this.j = _snowmanxx.get(this.d.t.nextInt(_snowmanxx.size()));
         }
      } else if (!this.j.aX() || !this.e.a(this.j.cB(), 8.0)) {
         this.j = null;
      }

      if (this.j != null) {
         this.d.a(null, this.j.cD(), this.j.cE(), this.j.cH(), adq.cb, adr.e, 1.0F, 1.0F);
         this.j.a(apk.o, 4.0F);
      }

      if (_snowman != this.j) {
         ceh _snowmanxx = this.p();
         this.d.a(this.e, _snowmanxx, _snowmanxx, 2);
      }
   }

   private void l() {
      if (this.k == null) {
         this.j = null;
      } else if (this.j == null || !this.j.bS().equals(this.k)) {
         this.j = this.x();
         if (this.j == null) {
            this.k = null;
         }
      }
   }

   private dci m() {
      int _snowman = this.e.u();
      int _snowmanx = this.e.v();
      int _snowmanxx = this.e.w();
      return new dci((double)_snowman, (double)_snowmanx, (double)_snowmanxx, (double)(_snowman + 1), (double)(_snowmanx + 1), (double)(_snowmanxx + 1)).g(8.0);
   }

   @Nullable
   private aqm x() {
      List<aqm> _snowman = this.d.a(aqm.class, this.m(), var1x -> var1x.bS().equals(this.k));
      return _snowman.size() == 1 ? _snowman.get(0) : null;
   }

   private void y() {
      Random _snowman = this.d.t;
      double _snowmanx = (double)(afm.a((float)(this.a + 35) * 0.1F) / 2.0F + 0.5F);
      _snowmanx = (_snowmanx * _snowmanx + _snowmanx) * 0.3F;
      dcn _snowmanxx = new dcn((double)this.e.u() + 0.5, (double)this.e.v() + 1.5 + _snowmanx, (double)this.e.w() + 0.5);

      for (fx _snowmanxxx : this.i) {
         if (_snowman.nextInt(50) == 0) {
            float _snowmanxxxx = -0.5F + _snowman.nextFloat();
            float _snowmanxxxxx = -2.0F + _snowman.nextFloat();
            float _snowmanxxxxxx = -0.5F + _snowman.nextFloat();
            fx _snowmanxxxxxxx = _snowmanxxx.b(this.e);
            dcn _snowmanxxxxxxxx = new dcn((double)_snowmanxxxx, (double)_snowmanxxxxx, (double)_snowmanxxxxxx).b((double)_snowmanxxxxxxx.u(), (double)_snowmanxxxxxxx.v(), (double)_snowmanxxxxxxx.w());
            this.d.a(hh.ae, _snowmanxx.b, _snowmanxx.c, _snowmanxx.d, _snowmanxxxxxxxx.b, _snowmanxxxxxxxx.c, _snowmanxxxxxxxx.d);
         }
      }

      if (this.j != null) {
         dcn _snowmanxxxx = new dcn(this.j.cD(), this.j.cG(), this.j.cH());
         float _snowmanxxxxx = (-0.5F + _snowman.nextFloat()) * (3.0F + this.j.cy());
         float _snowmanxxxxxx = -1.0F + _snowman.nextFloat() * this.j.cz();
         float _snowmanxxxxxxx = (-0.5F + _snowman.nextFloat()) * (3.0F + this.j.cy());
         dcn _snowmanxxxxxxxx = new dcn((double)_snowmanxxxxx, (double)_snowmanxxxxxx, (double)_snowmanxxxxxxx);
         this.d.a(hh.ae, _snowmanxxxx.b, _snowmanxxxx.c, _snowmanxxxx.d, _snowmanxxxxxxxx.b, _snowmanxxxxxxxx.c, _snowmanxxxxxxxx.d);
      }
   }

   public boolean d() {
      return this.g;
   }

   public boolean f() {
      return this.h;
   }

   private void a(boolean var1) {
      if (_snowman != this.g) {
         this.a(_snowman ? adq.bY : adq.cc);
      }

      this.g = _snowman;
   }

   private void b(boolean var1) {
      this.h = _snowman;
   }

   public float a(float var1) {
      return (this.c + _snowman) * -0.0375F;
   }

   public void a(adp var1) {
      this.d.a(null, this.e, _snowman, adr.e, 1.0F, 1.0F);
   }
}
