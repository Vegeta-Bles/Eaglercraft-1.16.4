import java.io.IOException;
import javax.annotation.Nullable;

public abstract class cfz implements cgj, AutoCloseable {
   public cfz() {
   }

   @Nullable
   public cgh a(int var1, int var2, boolean var3) {
      return (cgh)this.a(_snowman, _snowman, cga.m, _snowman);
   }

   @Nullable
   public cgh a(int var1, int var2) {
      return this.a(_snowman, _snowman, false);
   }

   @Nullable
   @Override
   public brc c(int var1, int var2) {
      return this.a(_snowman, _snowman, cga.a, false);
   }

   public boolean b(int var1, int var2) {
      return this.a(_snowman, _snowman, cga.m, false) != null;
   }

   @Nullable
   public abstract cfw a(int var1, int var2, cga var3, boolean var4);

   public abstract String e();

   @Override
   public void close() throws IOException {
   }

   public abstract cuo l();

   public void a(boolean var1, boolean var2) {
   }

   public void a(brd var1, boolean var2) {
   }

   public boolean a(aqa var1) {
      return true;
   }

   public boolean a(brd var1) {
      return true;
   }

   public boolean a(fx var1) {
      return true;
   }
}
