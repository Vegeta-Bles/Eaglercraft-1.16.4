import java.util.function.BiConsumer;
import java.util.function.Predicate;
import javax.annotation.Nullable;

public class djj extends dkc {
   private final Predicate<dkd> Y;
   private final BiConsumer<dkd, Boolean> Z;
   @Nullable
   private final nr aa;

   public djj(String var1, Predicate<dkd> var2, BiConsumer<dkd, Boolean> var3) {
      this(_snowman, null, _snowman, _snowman);
   }

   public djj(String var1, @Nullable nr var2, Predicate<dkd> var3, BiConsumer<dkd, Boolean> var4) {
      super(_snowman);
      this.Y = _snowman;
      this.Z = _snowman;
      this.aa = _snowman;
   }

   public void a(dkd var1, String var2) {
      this.a(_snowman, "true".equals(_snowman));
   }

   public void a(dkd var1) {
      this.a(_snowman, !this.b(_snowman));
      _snowman.b();
   }

   private void a(dkd var1, boolean var2) {
      this.Z.accept(_snowman, _snowman);
   }

   public boolean b(dkd var1) {
      return this.Y.test(_snowman);
   }

   @Override
   public dlh a(dkd var1, int var2, int var3, int var4) {
      if (this.aa != null) {
         this.a(djz.C().g.b(this.aa, 200));
      }

      return new dlw(_snowman, _snowman, _snowman, 20, this, this.c(_snowman), var2x -> {
         this.a(_snowman);
         var2x.a(this.c(_snowman));
      });
   }

   public nr c(dkd var1) {
      return nq.a(this.a(), this.b(_snowman));
   }
}
