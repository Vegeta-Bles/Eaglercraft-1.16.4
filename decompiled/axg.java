import java.util.EnumSet;

public class axg extends avv {
   private final bfe a;

   public axg(bfe var1) {
      this.a = _snowman;
      this.a(EnumSet.of(avv.a.c, avv.a.a));
   }

   @Override
   public boolean a() {
      if (!this.a.aX()) {
         return false;
      } else if (this.a.aE()) {
         return false;
      } else if (!this.a.ao()) {
         return false;
      } else if (this.a.w) {
         return false;
      } else {
         bfw _snowman = this.a.eM();
         if (_snowman == null) {
            return false;
         } else {
            return this.a.h(_snowman) > 16.0 ? false : _snowman.bp != null;
         }
      }
   }

   @Override
   public void c() {
      this.a.x().o();
   }

   @Override
   public void d() {
      this.a.f(null);
   }
}
