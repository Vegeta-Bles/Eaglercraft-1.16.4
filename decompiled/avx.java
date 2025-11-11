import java.util.List;
import java.util.stream.Collectors;
import javax.annotation.Nullable;

public class avx extends awt {
   public avx(aqu var1, double var2) {
      super(_snowman, _snowman, 240, false);
   }

   @Nullable
   @Override
   protected dcn g() {
      float _snowman = this.a.l.t.nextFloat();
      if (this.a.l.t.nextFloat() < 0.3F) {
         return this.j();
      } else {
         dcn _snowmanx;
         if (_snowman < 0.7F) {
            _snowmanx = this.k();
            if (_snowmanx == null) {
               _snowmanx = this.l();
            }
         } else {
            _snowmanx = this.l();
            if (_snowmanx == null) {
               _snowmanx = this.k();
            }
         }

         return _snowmanx == null ? this.j() : _snowmanx;
      }
   }

   @Nullable
   private dcn j() {
      return azj.b(this.a, 10, 7);
   }

   @Nullable
   private dcn k() {
      aag _snowman = (aag)this.a.l;
      List<bfj> _snowmanx = _snowman.a(aqe.aP, this.a.cc().g(32.0), this::a);
      if (_snowmanx.isEmpty()) {
         return null;
      } else {
         bfj _snowmanxx = _snowmanx.get(this.a.l.t.nextInt(_snowmanx.size()));
         dcn _snowmanxxx = _snowmanxx.cA();
         return azj.a(this.a, 10, 7, _snowmanxxx);
      }
   }

   @Nullable
   private dcn l() {
      gp _snowman = this.m();
      if (_snowman == null) {
         return null;
      } else {
         fx _snowmanx = this.a(_snowman);
         return _snowmanx == null ? null : azj.a(this.a, 10, 7, dcn.c(_snowmanx));
      }
   }

   @Nullable
   private gp m() {
      aag _snowman = (aag)this.a.l;
      List<gp> _snowmanx = gp.a(gp.a(this.a), 2).filter(var1x -> _snowman.b(var1x) == 0).collect(Collectors.toList());
      return _snowmanx.isEmpty() ? null : _snowmanx.get(_snowman.t.nextInt(_snowmanx.size()));
   }

   @Nullable
   private fx a(gp var1) {
      aag _snowman = (aag)this.a.l;
      azo _snowmanx = _snowman.y();
      List<fx> _snowmanxx = _snowmanx.c(var0 -> true, _snowman.q(), 8, azo.b.b).map(azp::f).collect(Collectors.toList());
      return _snowmanxx.isEmpty() ? null : _snowmanxx.get(_snowman.t.nextInt(_snowmanxx.size()));
   }

   private boolean a(bfj var1) {
      return _snowman.a(this.a.l.T());
   }
}
