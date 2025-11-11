import java.util.EnumSet;
import javax.annotation.Nullable;

public class awt extends avv {
   protected final aqu a;
   protected double b;
   protected double c;
   protected double d;
   protected final double e;
   protected int f;
   protected boolean g;
   private boolean h;

   public awt(aqu var1, double var2) {
      this(_snowman, _snowman, 120);
   }

   public awt(aqu var1, double var2, int var4) {
      this(_snowman, _snowman, _snowman, true);
   }

   public awt(aqu var1, double var2, int var4, boolean var5) {
      this.a = _snowman;
      this.e = _snowman;
      this.f = _snowman;
      this.h = _snowman;
      this.a(EnumSet.of(avv.a.a));
   }

   @Override
   public boolean a() {
      if (this.a.bs()) {
         return false;
      } else {
         if (!this.g) {
            if (this.h && this.a.dd() >= 100) {
               return false;
            }

            if (this.a.cY().nextInt(this.f) != 0) {
               return false;
            }
         }

         dcn _snowman = this.g();
         if (_snowman == null) {
            return false;
         } else {
            this.b = _snowman.b;
            this.c = _snowman.c;
            this.d = _snowman.d;
            this.g = false;
            return true;
         }
      }
   }

   @Nullable
   protected dcn g() {
      return azj.a(this.a, 10, 7);
   }

   @Override
   public boolean b() {
      return !this.a.x().m() && !this.a.bs();
   }

   @Override
   public void c() {
      this.a.x().a(this.b, this.c, this.d, this.e);
   }

   @Override
   public void d() {
      this.a.x().o();
      super.d();
   }

   public void h() {
      this.g = true;
   }

   public void a(int var1) {
      this.f = _snowman;
   }
}
