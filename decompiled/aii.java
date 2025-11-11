import com.mojang.datafixers.schemas.Schema;
import com.mojang.datafixers.util.Pair;
import com.mojang.serialization.Dynamic;
import java.util.Objects;

public class aii extends akv {
   public aii(Schema var1, boolean var2) {
      super("EntitySkeletonSplitFix", _snowman, _snowman);
   }

   @Override
   protected Pair<String, Dynamic<?>> a(String var1, Dynamic<?> var2) {
      if (Objects.equals(_snowman, "Skeleton")) {
         int _snowman = _snowman.get("SkeletonType").asInt(0);
         if (_snowman == 1) {
            _snowman = "WitherSkeleton";
         } else if (_snowman == 2) {
            _snowman = "Stray";
         }
      }

      return Pair.of(_snowman, _snowman);
   }
}
