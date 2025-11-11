import com.mojang.datafixers.schemas.Schema;
import com.mojang.datafixers.util.Pair;
import com.mojang.serialization.Dynamic;
import java.util.Objects;

public class ahq extends akv {
   public ahq(Schema var1, boolean var2) {
      super("EntityElderGuardianSplitFix", _snowman, _snowman);
   }

   @Override
   protected Pair<String, Dynamic<?>> a(String var1, Dynamic<?> var2) {
      return Pair.of(Objects.equals(_snowman, "Guardian") && _snowman.get("Elder").asBoolean(false) ? "ElderGuardian" : _snowman, _snowman);
   }
}
