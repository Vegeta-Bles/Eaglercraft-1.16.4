import java.util.function.BiConsumer;
import java.util.function.BiFunction;

public class djp extends dkc {
   private final BiConsumer<dkd, Integer> Y;
   private final BiFunction<dkd, djp, nr> Z;

   public djp(String var1, BiConsumer<dkd, Integer> var2, BiFunction<dkd, djp, nr> var3) {
      super(_snowman);
      this.Y = _snowman;
      this.Z = _snowman;
   }

   public void a(dkd var1, int var2) {
      this.Y.accept(_snowman, _snowman);
      _snowman.b();
   }

   @Override
   public dlh a(dkd var1, int var2, int var3, int var4) {
      return new dlw(_snowman, _snowman, _snowman, 20, this, this.c(_snowman), var2x -> {
         this.a(_snowman, 1);
         var2x.a(this.c(_snowman));
      });
   }

   public nr c(dkd var1) {
      return this.Z.apply(_snowman, this);
   }
}
