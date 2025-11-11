import java.util.EnumSet;
import javax.annotation.Nullable;

public class axl extends avv {
   private final avv a;
   private final int b;
   private boolean c;

   public axl(int var1, avv var2) {
      this.b = _snowman;
      this.a = _snowman;
   }

   public boolean a(axl var1) {
      return this.C_() && _snowman.h() < this.h();
   }

   @Override
   public boolean a() {
      return this.a.a();
   }

   @Override
   public boolean b() {
      return this.a.b();
   }

   @Override
   public boolean C_() {
      return this.a.C_();
   }

   @Override
   public void c() {
      if (!this.c) {
         this.c = true;
         this.a.c();
      }
   }

   @Override
   public void d() {
      if (this.c) {
         this.c = false;
         this.a.d();
      }
   }

   @Override
   public void e() {
      this.a.e();
   }

   @Override
   public void a(EnumSet<avv.a> var1) {
      this.a.a(_snowman);
   }

   @Override
   public EnumSet<avv.a> i() {
      return this.a.i();
   }

   public boolean g() {
      return this.c;
   }

   public int h() {
      return this.b;
   }

   public avv j() {
      return this.a;
   }

   @Override
   public boolean equals(@Nullable Object var1) {
      if (this == _snowman) {
         return true;
      } else {
         return _snowman != null && this.getClass() == _snowman.getClass() ? this.a.equals(((axl)_snowman).a) : false;
      }
   }

   @Override
   public int hashCode() {
      return this.a.hashCode();
   }
}
