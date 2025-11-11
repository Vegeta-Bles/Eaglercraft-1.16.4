import java.util.List;
import java.util.stream.IntStream;
import javax.annotation.Nullable;

public class cde extends cdd implements ape, cdm {
   private static final int[] a = IntStream.range(0, 27).toArray();
   private gj<bmb> b = gj.a(27, bmb.b);
   private int c;
   private cde.a i = cde.a.a;
   private float j;
   private float k;
   @Nullable
   private bkx l;
   private boolean m;

   public cde(@Nullable bkx var1) {
      super(cck.w);
      this.l = _snowman;
   }

   public cde() {
      this(null);
      this.m = true;
   }

   @Override
   public void aj_() {
      this.h();
      if (this.i == cde.a.b || this.i == cde.a.d) {
         this.m();
      }
   }

   protected void h() {
      this.k = this.j;
      switch (this.i) {
         case a:
            this.j = 0.0F;
            break;
         case b:
            this.j += 0.1F;
            if (this.j >= 1.0F) {
               this.m();
               this.i = cde.a.c;
               this.j = 1.0F;
               this.x();
            }
            break;
         case d:
            this.j -= 0.1F;
            if (this.j <= 0.0F) {
               this.i = cde.a.a;
               this.j = 0.0F;
               this.x();
            }
            break;
         case c:
            this.j = 1.0F;
      }
   }

   public cde.a j() {
      return this.i;
   }

   public dci a(ceh var1) {
      return this.b(_snowman.c(bzs.a));
   }

   public dci b(gc var1) {
      float _snowman = this.a(1.0F);
      return dde.b().a().b((double)(0.5F * _snowman * (float)_snowman.i()), (double)(0.5F * _snowman * (float)_snowman.j()), (double)(0.5F * _snowman * (float)_snowman.k()));
   }

   private dci c(gc var1) {
      gc _snowman = _snowman.f();
      return this.b(_snowman).a((double)_snowman.i(), (double)_snowman.j(), (double)_snowman.k());
   }

   private void m() {
      ceh _snowman = this.d.d_(this.o());
      if (_snowman.b() instanceof bzs) {
         gc _snowmanx = _snowman.c(bzs.a);
         dci _snowmanxx = this.c(_snowmanx).a(this.e);
         List<aqa> _snowmanxxx = this.d.a(null, _snowmanxx);
         if (!_snowmanxxx.isEmpty()) {
            for (int _snowmanxxxx = 0; _snowmanxxxx < _snowmanxxx.size(); _snowmanxxxx++) {
               aqa _snowmanxxxxx = _snowmanxxx.get(_snowmanxxxx);
               if (_snowmanxxxxx.y_() != cvc.d) {
                  double _snowmanxxxxxx = 0.0;
                  double _snowmanxxxxxxx = 0.0;
                  double _snowmanxxxxxxxx = 0.0;
                  dci _snowmanxxxxxxxxx = _snowmanxxxxx.cc();
                  switch (_snowmanx.n()) {
                     case a:
                        if (_snowmanx.e() == gc.b.a) {
                           _snowmanxxxxxx = _snowmanxx.d - _snowmanxxxxxxxxx.a;
                        } else {
                           _snowmanxxxxxx = _snowmanxxxxxxxxx.d - _snowmanxx.a;
                        }

                        _snowmanxxxxxx += 0.01;
                        break;
                     case b:
                        if (_snowmanx.e() == gc.b.a) {
                           _snowmanxxxxxxx = _snowmanxx.e - _snowmanxxxxxxxxx.b;
                        } else {
                           _snowmanxxxxxxx = _snowmanxxxxxxxxx.e - _snowmanxx.b;
                        }

                        _snowmanxxxxxxx += 0.01;
                        break;
                     case c:
                        if (_snowmanx.e() == gc.b.a) {
                           _snowmanxxxxxxxx = _snowmanxx.f - _snowmanxxxxxxxxx.c;
                        } else {
                           _snowmanxxxxxxxx = _snowmanxxxxxxxxx.f - _snowmanxx.c;
                        }

                        _snowmanxxxxxxxx += 0.01;
                  }

                  _snowmanxxxxx.a(aqr.d, new dcn(_snowmanxxxxxx * (double)_snowmanx.i(), _snowmanxxxxxxx * (double)_snowmanx.j(), _snowmanxxxxxxxx * (double)_snowmanx.k()));
               }
            }
         }
      }
   }

   @Override
   public int Z_() {
      return this.b.size();
   }

   @Override
   public boolean a_(int var1, int var2) {
      if (_snowman == 1) {
         this.c = _snowman;
         if (_snowman == 0) {
            this.i = cde.a.d;
            this.x();
         }

         if (_snowman == 1) {
            this.i = cde.a.b;
            this.x();
         }

         return true;
      } else {
         return super.a_(_snowman, _snowman);
      }
   }

   private void x() {
      this.p().a(this.v(), this.o(), 3);
   }

   @Override
   public void c_(bfw var1) {
      if (!_snowman.a_()) {
         if (this.c < 0) {
            this.c = 0;
         }

         this.c++;
         this.d.a(this.e, this.p().b(), 1, this.c);
         if (this.c == 1) {
            this.d.a(null, this.e, adq.ne, adr.e, 0.5F, this.d.t.nextFloat() * 0.1F + 0.9F);
         }
      }
   }

   @Override
   public void b_(bfw var1) {
      if (!_snowman.a_()) {
         this.c--;
         this.d.a(this.e, this.p().b(), 1, this.c);
         if (this.c <= 0) {
            this.d.a(null, this.e, adq.nd, adr.e, 0.5F, this.d.t.nextFloat() * 0.1F + 0.9F);
         }
      }
   }

   @Override
   protected nr g() {
      return new of("container.shulkerBox");
   }

   @Override
   public void a(ceh var1, md var2) {
      super.a(_snowman, _snowman);
      this.d(_snowman);
   }

   @Override
   public md a(md var1) {
      super.a(_snowman);
      return this.e(_snowman);
   }

   public void d(md var1) {
      this.b = gj.a(this.Z_(), bmb.b);
      if (!this.b(_snowman) && _snowman.c("Items", 9)) {
         aoo.b(_snowman, this.b);
      }
   }

   public md e(md var1) {
      if (!this.c(_snowman)) {
         aoo.a(_snowman, this.b, false);
      }

      return _snowman;
   }

   @Override
   protected gj<bmb> f() {
      return this.b;
   }

   @Override
   protected void a(gj<bmb> var1) {
      this.b = _snowman;
   }

   @Override
   public int[] a(gc var1) {
      return a;
   }

   @Override
   public boolean a(int var1, bmb var2, @Nullable gc var3) {
      return !(buo.a(_snowman.b()) instanceof bzs);
   }

   @Override
   public boolean b(int var1, bmb var2, gc var3) {
      return true;
   }

   public float a(float var1) {
      return afm.g(_snowman, this.k, this.j);
   }

   @Nullable
   public bkx k() {
      if (this.m) {
         this.l = bzs.c(this.p().b());
         this.m = false;
      }

      return this.l;
   }

   @Override
   protected bic a(int var1, bfv var2) {
      return new bjo(_snowman, _snowman, this);
   }

   public boolean l() {
      return this.i == cde.a.a;
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
