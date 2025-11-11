import com.mojang.datafixers.DSL;
import com.mojang.datafixers.Typed;
import com.mojang.datafixers.schemas.Schema;
import com.mojang.datafixers.util.Pair;
import com.mojang.serialization.Dynamic;

public abstract class akv extends aie {
   public akv(String var1, Schema var2, boolean var3) {
      super(_snowman, _snowman, _snowman);
   }

   @Override
   protected Pair<String, Typed<?>> a(String var1, Typed<?> var2) {
      Pair<String, Dynamic<?>> _snowman = this.a(_snowman, (Dynamic<?>)_snowman.getOrCreate(DSL.remainderFinder()));
      return Pair.of(_snowman.getFirst(), _snowman.set(DSL.remainderFinder(), _snowman.getSecond()));
   }

   protected abstract Pair<String, Dynamic<?>> a(String var1, Dynamic<?> var2);
}
