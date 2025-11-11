import net.minecraft.world.level.ColorResolver;

public interface bra extends brc {
   float a(gc var1, boolean var2);

   cuo e();

   int a(fx var1, ColorResolver var2);

   default int a(bsf var1, fx var2) {
      return this.e().a(_snowman).b(_snowman);
   }

   default int b(fx var1, int var2) {
      return this.e().b(_snowman, _snowman);
   }

   default boolean e(fx var1) {
      return this.a(bsf.a, _snowman) >= this.K();
   }
}
