import java.util.EnumSet;

public class axb extends avv {
   private final are a;

   public axb(are var1) {
      this.a = _snowman;
      this.a(EnumSet.of(avv.a.c, avv.a.a));
   }

   @Override
   public boolean b() {
      return this.a.eO();
   }

   @Override
   public boolean a() {
      if (!this.a.eK()) {
         return false;
      } else if (this.a.aH()) {
         return false;
      } else if (!this.a.ao()) {
         return false;
      } else {
         aqm _snowman = this.a.eN();
         if (_snowman == null) {
            return true;
         } else {
            return this.a.h((aqa)_snowman) < 144.0 && _snowman.cZ() != null ? false : this.a.eO();
         }
      }
   }

   @Override
   public void c() {
      this.a.x().o();
      this.a.v(true);
   }

   @Override
   public void d() {
      this.a.v(false);
   }
}
