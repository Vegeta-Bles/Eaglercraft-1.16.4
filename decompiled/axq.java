import java.util.EnumSet;
import java.util.function.Predicate;
import javax.annotation.Nullable;

public class axq<T extends aqm> extends axx {
   protected final Class<T> a;
   protected final int b;
   protected aqm c;
   protected azg d;

   public axq(aqn var1, Class<T> var2, boolean var3) {
      this(_snowman, _snowman, _snowman, false);
   }

   public axq(aqn var1, Class<T> var2, boolean var3, boolean var4) {
      this(_snowman, _snowman, 10, _snowman, _snowman, null);
   }

   public axq(aqn var1, Class<T> var2, int var3, boolean var4, boolean var5, @Nullable Predicate<aqm> var6) {
      super(_snowman, _snowman, _snowman);
      this.a = _snowman;
      this.b = _snowman;
      this.a(EnumSet.of(avv.a.d));
      this.d = new azg().a(this.k()).a(_snowman);
   }

   @Override
   public boolean a() {
      if (this.b > 0 && this.e.cY().nextInt(this.b) != 0) {
         return false;
      } else {
         this.g();
         return this.c != null;
      }
   }

   protected dci a(double var1) {
      return this.e.cc().c(_snowman, 4.0, _snowman);
   }

   protected void g() {
      if (this.a != bfw.class && this.a != aah.class) {
         this.c = this.e.l.b(this.a, this.d, this.e, this.e.cD(), this.e.cG(), this.e.cH(), this.a(this.k()));
      } else {
         this.c = this.e.l.a(this.d, this.e, this.e.cD(), this.e.cG(), this.e.cH());
      }
   }

   @Override
   public void c() {
      this.e.h(this.c);
      super.c();
   }

   public void a(@Nullable aqm var1) {
      this.c = _snowman;
   }
}
