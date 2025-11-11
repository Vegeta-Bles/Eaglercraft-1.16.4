import java.util.EnumSet;
import java.util.Random;
import javax.annotation.Nullable;

public class avo extends avv {
   protected final aqu a;
   private double b;
   private double c;
   private double d;
   private final double e;
   private final brx f;

   public avo(aqu var1, double var2) {
      this.a = _snowman;
      this.e = _snowman;
      this.f = _snowman.l;
      this.a(EnumSet.of(avv.a.a));
   }

   @Override
   public boolean a() {
      if (this.a.A() != null) {
         return false;
      } else if (!this.f.M()) {
         return false;
      } else if (!this.a.bq()) {
         return false;
      } else if (!this.f.e(this.a.cB())) {
         return false;
      } else {
         return !this.a.b(aqf.f).a() ? false : this.g();
      }
   }

   protected boolean g() {
      dcn _snowman = this.h();
      if (_snowman == null) {
         return false;
      } else {
         this.b = _snowman.b;
         this.c = _snowman.c;
         this.d = _snowman.d;
         return true;
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

   @Nullable
   protected dcn h() {
      Random _snowman = this.a.cY();
      fx _snowmanx = this.a.cB();

      for (int _snowmanxx = 0; _snowmanxx < 10; _snowmanxx++) {
         fx _snowmanxxx = _snowmanx.b(_snowman.nextInt(20) - 10, _snowman.nextInt(6) - 3, _snowman.nextInt(20) - 10);
         if (!this.f.e(_snowmanxxx) && this.a.f(_snowmanxxx) < 0.0F) {
            return dcn.c(_snowmanxxx);
         }
      }

      return null;
   }
}
