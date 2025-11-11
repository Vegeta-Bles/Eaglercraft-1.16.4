import java.util.function.Predicate;
import javax.annotation.Nullable;

public class axs<T extends aqm> extends axq<T> {
   private int i = 0;

   public axs(bhc var1, Class<T> var2, boolean var3, @Nullable Predicate<aqm> var4) {
      super(_snowman, _snowman, 500, _snowman, false, _snowman);
   }

   public int h() {
      return this.i;
   }

   public void j() {
      this.i--;
   }

   @Override
   public boolean a() {
      if (this.i > 0 || !this.e.cY().nextBoolean()) {
         return false;
      } else if (!((bhc)this.e).fb()) {
         return false;
      } else {
         this.g();
         return this.c != null;
      }
   }

   @Override
   public void c() {
      this.i = 200;
      super.c();
   }
}
