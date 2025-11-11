import com.mojang.datafixers.DSL;
import com.mojang.datafixers.schemas.Schema;
import com.mojang.datafixers.types.templates.TypeTemplate;
import com.mojang.datafixers.types.templates.Hook.HookFunction;
import java.util.Map;
import java.util.function.Supplier;

public class alp extends Schema {
   public alp(int var1, Schema var2) {
      super(_snowman, _snowman);
   }

   public void registerTypes(Schema var1, Map<String, Supplier<TypeTemplate>> var2, Map<String, Supplier<TypeTemplate>> var3) {
      super.registerTypes(_snowman, _snowman, _snowman);
      _snowman.registerType(
         true,
         akn.l,
         () -> DSL.hook(
               DSL.optionalFields(
                  "id",
                  akn.r.in(_snowman),
                  "tag",
                  DSL.optionalFields(
                     "EntityTag", akn.o.in(_snowman), "BlockEntityTag", akn.k.in(_snowman), "CanDestroy", DSL.list(akn.q.in(_snowman)), "CanPlaceOn", DSL.list(akn.q.in(_snowman))
                  )
               ),
               anl.a,
               HookFunction.IDENTITY
            )
      );
   }
}
