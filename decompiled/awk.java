import java.util.EnumSet;

public class awk extends avv {
   private final aqu a;
   private double b;
   private double c;
   private double d;
   private final double e;

   public awk(aqu var1, double var2) {
      this.a = _snowman;
      this.e = _snowman;
      this.a(EnumSet.of(avv.a.a));
   }

   @Override
   public boolean a() {
      if (this.a.ev()) {
         return false;
      } else {
         dcn _snowman = azj.b(this.a, 16, 7, dcn.c(this.a.ew()));
         if (_snowman == null) {
            return false;
         } else {
            this.b = _snowman.b;
            this.c = _snowman.c;
            this.d = _snowman.d;
            return true;
         }
      }
   }

   @Override
   public boolean b() {
      return !this.a.x().m();
   }

   @Override
   public void c() {
      this.a.x().a(this.b, this.c, this.d, this.e);
   }
}
