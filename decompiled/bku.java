import java.util.Collection;
import javax.annotation.Nullable;

public class bku extends blx {
   public bku(blx.a var1) {
      super(_snowman);
   }

   @Override
   public boolean e(bmb var1) {
      return true;
   }

   @Override
   public boolean a(ceh var1, brx var2, fx var3, bfw var4) {
      if (!_snowman.v) {
         this.a(_snowman, _snowman, _snowman, _snowman, false, _snowman.b(aot.a));
      }

      return false;
   }

   @Override
   public aou a(boa var1) {
      bfw _snowman = _snowman.n();
      brx _snowmanx = _snowman.p();
      if (!_snowmanx.v && _snowman != null) {
         fx _snowmanxx = _snowman.a();
         this.a(_snowman, _snowmanx.d_(_snowmanxx), _snowmanx, _snowmanxx, true, _snowman.m());
      }

      return aou.a(_snowmanx.v);
   }

   private void a(bfw var1, ceh var2, bry var3, fx var4, boolean var5, bmb var6) {
      if (_snowman.eV()) {
         buo _snowman = _snowman.b();
         cei<buo, ceh> _snowmanx = _snowman.m();
         Collection<cfj<?>> _snowmanxx = _snowmanx.d();
         String _snowmanxxx = gm.Q.b(_snowman).toString();
         if (_snowmanxx.isEmpty()) {
            a(_snowman, new of(this.a() + ".empty", _snowmanxxx));
         } else {
            md _snowmanxxxx = _snowman.a("DebugProperty");
            String _snowmanxxxxx = _snowmanxxxx.l(_snowmanxxx);
            cfj<?> _snowmanxxxxxx = _snowmanx.a(_snowmanxxxxx);
            if (_snowman) {
               if (_snowmanxxxxxx == null) {
                  _snowmanxxxxxx = _snowmanxx.iterator().next();
               }

               ceh _snowmanxxxxxxx = a(_snowman, _snowmanxxxxxx, _snowman.eq());
               _snowman.a(_snowman, _snowmanxxxxxxx, 18);
               a(_snowman, new of(this.a() + ".update", _snowmanxxxxxx.f(), a(_snowmanxxxxxxx, _snowmanxxxxxx)));
            } else {
               _snowmanxxxxxx = a(_snowmanxx, _snowmanxxxxxx, _snowman.eq());
               String _snowmanxxxxxxx = _snowmanxxxxxx.f();
               _snowmanxxxx.a(_snowmanxxx, _snowmanxxxxxxx);
               a(_snowman, new of(this.a() + ".select", _snowmanxxxxxxx, a(_snowman, _snowmanxxxxxx)));
            }
         }
      }
   }

   private static <T extends Comparable<T>> ceh a(ceh var0, cfj<T> var1, boolean var2) {
      return _snowman.a(_snowman, a(_snowman.a(), _snowman.c(_snowman), _snowman));
   }

   private static <T> T a(Iterable<T> var0, @Nullable T var1, boolean var2) {
      return _snowman ? x.b(_snowman, _snowman) : x.a(_snowman, _snowman);
   }

   private static void a(bfw var0, nr var1) {
      ((aah)_snowman).a(_snowman, no.c, x.b);
   }

   private static <T extends Comparable<T>> String a(ceh var0, cfj<T> var1) {
      return _snowman.a(_snowman.c(_snowman));
   }
}
