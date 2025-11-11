import com.mojang.datafixers.schemas.Schema;
import com.mojang.datafixers.util.Pair;
import com.mojang.serialization.Dynamic;
import java.util.Objects;

public class ahn extends akv {
   public ahn(Schema var1, boolean var2) {
      super("EntityCatSplitFix", _snowman, _snowman);
   }

   @Override
   protected Pair<String, Dynamic<?>> a(String var1, Dynamic<?> var2) {
      if (Objects.equals("minecraft:ocelot", _snowman)) {
         int _snowman = _snowman.get("CatType").asInt(0);
         if (_snowman == 0) {
            String _snowmanx = _snowman.get("Owner").asString("");
            String _snowmanxx = _snowman.get("OwnerUUID").asString("");
            if (_snowmanx.length() > 0 || _snowmanxx.length() > 0) {
               _snowman.set("Trusting", _snowman.createBoolean(true));
            }
         } else if (_snowman > 0 && _snowman < 4) {
            _snowman = _snowman.set("CatType", _snowman.createInt(_snowman));
            _snowman = _snowman.set("OwnerUUID", _snowman.createString(_snowman.get("OwnerUUID").asString("")));
            return Pair.of("minecraft:cat", _snowman);
         }
      }

      return Pair.of(_snowman, _snowman);
   }
}
