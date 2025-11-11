import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Lists;
import java.util.List;
import javax.annotation.Nullable;

public class asm extends arv<bfj> {
   @Nullable
   private fx b;
   private long c;
   private int d;
   private final List<fx> e = Lists.newArrayList();

   public asm() {
      super(ImmutableMap.of(ayd.n, aye.b, ayd.m, aye.b, ayd.f, aye.a));
   }

   protected boolean a(aag var1, bfj var2) {
      if (!_snowman.V().b(brt.b)) {
         return false;
      } else if (_snowman.eX().b() != bfm.f) {
         return false;
      } else {
         fx.a _snowman = _snowman.cB().i();
         this.e.clear();

         for (int _snowmanx = -1; _snowmanx <= 1; _snowmanx++) {
            for (int _snowmanxx = -1; _snowmanxx <= 1; _snowmanxx++) {
               for (int _snowmanxxx = -1; _snowmanxxx <= 1; _snowmanxxx++) {
                  _snowman.c(_snowman.cD() + (double)_snowmanx, _snowman.cE() + (double)_snowmanxx, _snowman.cH() + (double)_snowmanxxx);
                  if (this.a(_snowman, _snowman)) {
                     this.e.add(new fx(_snowman));
                  }
               }
            }
         }

         this.b = this.a(_snowman);
         return this.b != null;
      }
   }

   @Nullable
   private fx a(aag var1) {
      return this.e.isEmpty() ? null : this.e.get(_snowman.u_().nextInt(this.e.size()));
   }

   private boolean a(fx var1, aag var2) {
      ceh _snowman = _snowman.d_(_snowman);
      buo _snowmanx = _snowman.b();
      buo _snowmanxx = _snowman.d_(_snowman.c()).b();
      return _snowmanx instanceof bvs && ((bvs)_snowmanx).h(_snowman) || _snowman.g() && _snowmanxx instanceof bwp;
   }

   protected void a(aag var1, bfj var2, long var3) {
      if (_snowman > this.c && this.b != null) {
         _snowman.cJ().a(ayd.n, new arx(this.b));
         _snowman.cJ().a(ayd.m, new ayf(new arx(this.b), 0.5F, 1));
      }
   }

   protected void b(aag var1, bfj var2, long var3) {
      _snowman.cJ().b(ayd.n);
      _snowman.cJ().b(ayd.m);
      this.d = 0;
      this.c = _snowman + 40L;
   }

   protected void c(aag var1, bfj var2, long var3) {
      if (this.b == null || this.b.a(_snowman.cA(), 1.0)) {
         if (this.b != null && _snowman > this.c) {
            ceh _snowman = _snowman.d_(this.b);
            buo _snowmanx = _snowman.b();
            buo _snowmanxx = _snowman.d_(this.b.c()).b();
            if (_snowmanx instanceof bvs && ((bvs)_snowmanx).h(_snowman)) {
               _snowman.a(this.b, true, _snowman);
            }

            if (_snowman.g() && _snowmanxx instanceof bwp && _snowman.fi()) {
               apa _snowmanxxx = _snowman.eU();

               for (int _snowmanxxxx = 0; _snowmanxxxx < _snowmanxxx.Z_(); _snowmanxxxx++) {
                  bmb _snowmanxxxxx = _snowmanxxx.a(_snowmanxxxx);
                  boolean _snowmanxxxxxx = false;
                  if (!_snowmanxxxxx.a()) {
                     if (_snowmanxxxxx.b() == bmd.kV) {
                        _snowman.a(this.b, bup.bW.n(), 3);
                        _snowmanxxxxxx = true;
                     } else if (_snowmanxxxxx.b() == bmd.oZ) {
                        _snowman.a(this.b, bup.eV.n(), 3);
                        _snowmanxxxxxx = true;
                     } else if (_snowmanxxxxx.b() == bmd.oY) {
                        _snowman.a(this.b, bup.eU.n(), 3);
                        _snowmanxxxxxx = true;
                     } else if (_snowmanxxxxx.b() == bmd.qg) {
                        _snowman.a(this.b, bup.iD.n(), 3);
                        _snowmanxxxxxx = true;
                     }
                  }

                  if (_snowmanxxxxxx) {
                     _snowman.a(null, (double)this.b.u(), (double)this.b.v(), (double)this.b.w(), adq.cr, adr.e, 1.0F, 1.0F);
                     _snowmanxxxxx.g(1);
                     if (_snowmanxxxxx.a()) {
                        _snowmanxxx.a(_snowmanxxxx, bmb.b);
                     }
                     break;
                  }
               }
            }

            if (_snowmanx instanceof bvs && !((bvs)_snowmanx).h(_snowman)) {
               this.e.remove(this.b);
               this.b = this.a(_snowman);
               if (this.b != null) {
                  this.c = _snowman + 20L;
                  _snowman.cJ().a(ayd.m, new ayf(new arx(this.b), 0.5F, 1));
                  _snowman.cJ().a(ayd.n, new arx(this.b));
               }
            }
         }

         this.d++;
      }
   }

   protected boolean d(aag var1, bfj var2, long var3) {
      return this.d < 200;
   }
}
