import java.util.Set;
import java.util.function.Predicate;
import javax.annotation.Nullable;

public class ef implements Predicate<cel> {
   private final ceh a;
   private final Set<cfj<?>> b;
   @Nullable
   private final md c;

   public ef(ceh var1, Set<cfj<?>> var2, @Nullable md var3) {
      this.a = _snowman;
      this.b = _snowman;
      this.c = _snowman;
   }

   public ceh a() {
      return this.a;
   }

   public boolean a(cel var1) {
      ceh _snowman = _snowman.a();
      if (!_snowman.a(this.a.b())) {
         return false;
      } else {
         for (cfj<?> _snowmanx : this.b) {
            if (_snowman.c(_snowmanx) != this.a.c(_snowmanx)) {
               return false;
            }
         }

         if (this.c == null) {
            return true;
         } else {
            ccj _snowmanxx = _snowman.b();
            return _snowmanxx != null && mp.a(this.c, _snowmanxx.a(new md()), true);
         }
      }
   }

   public boolean a(aag var1, fx var2, int var3) {
      ceh _snowman = buo.b(this.a, (bry)_snowman, _snowman);
      if (_snowman.g()) {
         _snowman = this.a;
      }

      if (!_snowman.a(_snowman, _snowman, _snowman)) {
         return false;
      } else {
         if (this.c != null) {
            ccj _snowmanx = _snowman.c(_snowman);
            if (_snowmanx != null) {
               md _snowmanxx = this.c.g();
               _snowmanxx.b("x", _snowman.u());
               _snowmanxx.b("y", _snowman.v());
               _snowmanxx.b("z", _snowman.w());
               _snowmanx.a(_snowman, _snowmanxx);
            }
         }

         return true;
      }
   }
}
