import com.google.common.collect.ImmutableMap;
import java.util.Optional;
import javax.annotation.Nullable;

public class asy extends arv<aqn> {
   private int b;
   @Nullable
   private cxd c;
   @Nullable
   private fx d;
   private float e;

   public asy() {
      this(150, 250);
   }

   public asy(int var1, int var2) {
      super(ImmutableMap.of(ayd.D, aye.c, ayd.t, aye.b, ayd.m, aye.a), _snowman, _snowman);
   }

   protected boolean a(aag var1, aqn var2) {
      if (this.b > 0) {
         this.b--;
         return false;
      } else {
         arf<?> _snowman = _snowman.cJ();
         ayf _snowmanx = _snowman.c(ayd.m).get();
         boolean _snowmanxx = this.a(_snowman, _snowmanx);
         if (!_snowmanxx && this.a(_snowman, _snowmanx, _snowman.T())) {
            this.d = _snowmanx.a().b();
            return true;
         } else {
            _snowman.b(ayd.m);
            if (_snowmanxx) {
               _snowman.b(ayd.D);
            }

            return false;
         }
      }
   }

   protected boolean a(aag var1, aqn var2, long var3) {
      if (this.c != null && this.d != null) {
         Optional<ayf> _snowman = _snowman.cJ().c(ayd.m);
         ayj _snowmanx = _snowman.x();
         return !_snowmanx.m() && _snowman.isPresent() && !this.a(_snowman, _snowman.get());
      } else {
         return false;
      }
   }

   protected void b(aag var1, aqn var2, long var3) {
      if (_snowman.cJ().a(ayd.m) && !this.a(_snowman, _snowman.cJ().c(ayd.m).get()) && _snowman.x().t()) {
         this.b = _snowman.u_().nextInt(40);
      }

      _snowman.x().o();
      _snowman.cJ().b(ayd.m);
      _snowman.cJ().b(ayd.t);
      this.c = null;
   }

   protected void c(aag var1, aqn var2, long var3) {
      _snowman.cJ().a(ayd.t, this.c);
      _snowman.x().a(this.c, (double)this.e);
   }

   protected void d(aag var1, aqn var2, long var3) {
      cxd _snowman = _snowman.x().k();
      arf<?> _snowmanx = _snowman.cJ();
      if (this.c != _snowman) {
         this.c = _snowman;
         _snowmanx.a(ayd.t, _snowman);
      }

      if (_snowman != null && this.d != null) {
         ayf _snowmanxx = _snowmanx.c(ayd.m).get();
         if (_snowmanxx.a().b().j(this.d) > 4.0 && this.a(_snowman, _snowmanxx, _snowman.T())) {
            this.d = _snowmanxx.a().b();
            this.c(_snowman, _snowman, _snowman);
         }
      }
   }

   private boolean a(aqn var1, ayf var2, long var3) {
      fx _snowman = _snowman.a().b();
      this.c = _snowman.x().a(_snowman, 0);
      this.e = _snowman.b();
      arf<?> _snowmanx = _snowman.cJ();
      if (this.a(_snowman, _snowman)) {
         _snowmanx.b(ayd.D);
      } else {
         boolean _snowmanxx = this.c != null && this.c.j();
         if (_snowmanxx) {
            _snowmanx.b(ayd.D);
         } else if (!_snowmanx.a(ayd.D)) {
            _snowmanx.a(ayd.D, _snowman);
         }

         if (this.c != null) {
            return true;
         }

         dcn _snowmanxxx = azj.b((aqu)_snowman, 10, 7, dcn.c(_snowman));
         if (_snowmanxxx != null) {
            this.c = _snowman.x().a(_snowmanxxx.b, _snowmanxxx.c, _snowmanxxx.d, 0);
            return this.c != null;
         }
      }

      return false;
   }

   private boolean a(aqn var1, ayf var2) {
      return _snowman.a().b().k(_snowman.cB()) <= _snowman.c();
   }
}
