import java.util.Random;
import javax.annotation.Nullable;

public abstract class bxf extends buo {
   protected final gc a;
   protected final boolean b;
   protected final ddh c;

   protected bxf(ceg.c var1, gc var2, ddh var3, boolean var4) {
      super(_snowman);
      this.a = _snowman;
      this.c = _snowman;
      this.b = _snowman;
   }

   @Nullable
   @Override
   public ceh a(bny var1) {
      ceh _snowman = _snowman.p().d_(_snowman.a().a(this.a));
      return !_snowman.a(this.c()) && !_snowman.a(this.d()) ? this.a(_snowman.p()) : this.d().n();
   }

   public ceh a(bry var1) {
      return this.n();
   }

   @Override
   public boolean a(ceh var1, brz var2, fx var3) {
      fx _snowman = _snowman.a(this.a.f());
      ceh _snowmanx = _snowman.d_(_snowman);
      buo _snowmanxx = _snowmanx.b();
      return !this.c(_snowmanxx) ? false : _snowmanxx == this.c() || _snowmanxx == this.d() || _snowmanx.d(_snowman, _snowman, this.a);
   }

   @Override
   public void a(ceh var1, aag var2, fx var3, Random var4) {
      if (!_snowman.a(_snowman, _snowman)) {
         _snowman.b(_snowman, true);
      }
   }

   protected boolean c(buo var1) {
      return true;
   }

   @Override
   public ddh b(ceh var1, brc var2, fx var3, dcs var4) {
      return this.c;
   }

   protected abstract bxh c();

   protected abstract buo d();
}
