import com.google.common.collect.Lists;
import java.util.List;
import java.util.Random;
import javax.annotation.Nullable;

public class elz implements elo {
   private final int a;
   private final List<elz.b> b;
   private final elo c;

   public elz(List<elz.b> var1) {
      this.b = _snowman;
      this.a = afz.a(_snowman);
      this.c = _snowman.get(0).b;
   }

   @Override
   public List<eba> a(@Nullable ceh var1, @Nullable gc var2, Random var3) {
      return afz.a(this.b, Math.abs((int)_snowman.nextLong()) % this.a).b.a(_snowman, _snowman, _snowman);
   }

   @Override
   public boolean a() {
      return this.c.a();
   }

   @Override
   public boolean b() {
      return this.c.b();
   }

   @Override
   public boolean c() {
      return this.c.c();
   }

   @Override
   public boolean d() {
      return this.c.d();
   }

   @Override
   public ekc e() {
      return this.c.e();
   }

   @Override
   public ebm f() {
      return this.c.f();
   }

   @Override
   public ebk g() {
      return this.c.g();
   }

   public static class a {
      private final List<elz.b> a = Lists.newArrayList();

      public a() {
      }

      public elz.a a(@Nullable elo var1, int var2) {
         if (_snowman != null) {
            this.a.add(new elz.b(_snowman, _snowman));
         }

         return this;
      }

      @Nullable
      public elo a() {
         if (this.a.isEmpty()) {
            return null;
         } else {
            return (elo)(this.a.size() == 1 ? this.a.get(0).b : new elz(this.a));
         }
      }
   }

   static class b extends afz.a {
      protected final elo b;

      public b(elo var1, int var2) {
         super(_snowman);
         this.b = _snowman;
      }
   }
}
