import com.mojang.datafixers.DSL;
import com.mojang.datafixers.DataFix;
import com.mojang.datafixers.TypeRewriteRule;
import com.mojang.datafixers.schemas.Schema;
import java.util.Optional;
import java.util.UUID;

public class aij extends DataFix {
   public aij(Schema var1, boolean var2) {
      super(_snowman, _snowman);
   }

   public TypeRewriteRule makeRule() {
      return this.fixTypeEverywhereTyped(
         "EntityStringUuidFix",
         this.getInputSchema().getType(akn.p),
         var0 -> var0.update(
               DSL.remainderFinder(),
               var0x -> {
                  Optional<String> _snowman = var0x.get("UUID").asString().result();
                  if (_snowman.isPresent()) {
                     UUID _snowmanx = UUID.fromString(_snowman.get());
                     return var0x.remove("UUID")
                        .set("UUIDMost", var0x.createLong(_snowmanx.getMostSignificantBits()))
                        .set("UUIDLeast", var0x.createLong(_snowmanx.getLeastSignificantBits()));
                  } else {
                     return var0x;
                  }
               }
            )
      );
   }
}
