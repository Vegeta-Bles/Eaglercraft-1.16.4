import java.util.Random;

public class afh {
   private final int a;
   private final int b;

   public afh(int var1, int var2) {
      if (_snowman < _snowman) {
         throw new IllegalArgumentException("max must be >= minInclusive! Given minInclusive: " + _snowman + ", Given max: " + _snowman);
      } else {
         this.a = _snowman;
         this.b = _snowman;
      }
   }

   public static afh a(int var0, int var1) {
      return new afh(_snowman, _snowman);
   }

   public int a(Random var1) {
      return this.a == this.b ? this.a : _snowman.nextInt(this.b - this.a + 1) + this.a;
   }

   public int a() {
      return this.a;
   }

   public int b() {
      return this.b;
   }

   @Override
   public String toString() {
      return "IntRange[" + this.a + "-" + this.b + "]";
   }
}
