import com.mojang.serialization.Codec;
import com.mojang.serialization.DataResult;
import java.util.Random;

public class cnz extends cnt {
   public static final Codec<cnz> b = aup.a(ceh.b).comapFlatMap(cnz::a, var0 -> var0.c).fieldOf("entries").codec();
   private final aup<ceh> c;

   private static DataResult<cnz> a(aup<ceh> var0) {
      return _snowman.b() ? DataResult.error("WeightedStateProvider with no states") : DataResult.success(new cnz(_snowman));
   }

   private cnz(aup<ceh> var1) {
      this.c = _snowman;
   }

   @Override
   protected cnu<?> a() {
      return cnu.b;
   }

   public cnz() {
      this(new aup<>());
   }

   public cnz a(ceh var1, int var2) {
      this.c.a(_snowman, _snowman);
      return this;
   }

   @Override
   public ceh a(Random var1, fx var2) {
      return this.c.b(_snowman);
   }
}
