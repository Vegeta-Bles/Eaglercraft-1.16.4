import com.mojang.datafixers.DSL;
import com.mojang.datafixers.DataFixUtils;
import com.mojang.datafixers.Typed;
import com.mojang.datafixers.schemas.Schema;
import com.mojang.serialization.Dynamic;

public class agn extends ajv {
   public agn(Schema var1, boolean var2) {
      super(_snowman, _snowman, "BlockEntityBannerColorFix", akn.k, "minecraft:banner");
   }

   public Dynamic<?> a(Dynamic<?> var1) {
      _snowman = _snowman.update("Base", var0 -> var0.createInt(15 - var0.asInt(0)));
      return _snowman.update(
         "Patterns",
         var0 -> (Dynamic)DataFixUtils.orElse(
               var0.asStreamOpt()
                  .map(var0x -> var0x.map(var0xx -> var0xx.update("Color", var0xxx -> var0xxx.createInt(15 - var0xxx.asInt(0)))))
                  .map(var0::createList)
                  .result(),
               var0
            )
      );
   }

   @Override
   protected Typed<?> a(Typed<?> var1) {
      return _snowman.update(DSL.remainderFinder(), this::a);
   }
}
