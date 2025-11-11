import java.util.concurrent.Executor;
import javax.annotation.Nullable;

public class aas implements aap {
   private final aap a;
   private final aoe<Runnable> b;

   public aas(aap var1, Executor var2) {
      this.a = _snowman;
      this.b = aoe.a(_snowman, "progressListener");
   }

   @Override
   public void a(brd var1) {
      this.b.a(() -> this.a.a(_snowman));
   }

   @Override
   public void a(brd var1, @Nullable cga var2) {
      this.b.a(() -> this.a.a(_snowman, _snowman));
   }

   @Override
   public void b() {
      this.b.a(this.a::b);
   }
}
