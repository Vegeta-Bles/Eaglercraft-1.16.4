import java.util.EnumSet;

public class axd extends avv {
   private final bdc a;
   private aqm b;

   public axd(bdc var1) {
      this.a = _snowman;
      this.a(EnumSet.of(avv.a.a));
   }

   @Override
   public boolean a() {
      aqm _snowman = this.a.A();
      return this.a.eK() > 0 || _snowman != null && this.a.h((aqa)_snowman) < 9.0;
   }

   @Override
   public void c() {
      this.a.x().o();
      this.b = this.a.A();
   }

   @Override
   public void d() {
      this.b = null;
   }

   @Override
   public void e() {
      if (this.b == null) {
         this.a.a(-1);
      } else if (this.a.h((aqa)this.b) > 49.0) {
         this.a.a(-1);
      } else if (!this.a.z().a(this.b)) {
         this.a.a(-1);
      } else {
         this.a.a(1);
      }
   }
}
