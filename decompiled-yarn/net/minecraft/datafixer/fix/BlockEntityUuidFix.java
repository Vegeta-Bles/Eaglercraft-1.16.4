package net.minecraft.datafixer.fix;

import com.mojang.datafixers.TypeRewriteRule;
import com.mojang.datafixers.schemas.Schema;
import com.mojang.serialization.Dynamic;
import net.minecraft.datafixer.TypeReferences;

public class BlockEntityUuidFix extends AbstractUuidFix {
   public BlockEntityUuidFix(Schema outputSchema) {
      super(outputSchema, TypeReferences.BLOCK_ENTITY);
   }

   protected TypeRewriteRule makeRule() {
      return this.fixTypeEverywhereTyped("BlockEntityUUIDFix", this.getInputSchema().getType(this.typeReference), _snowman -> {
         _snowman = this.updateTyped(_snowman, "minecraft:conduit", this::updateConduit);
         return this.updateTyped(_snowman, "minecraft:skull", this::updateSkull);
      });
   }

   private Dynamic<?> updateSkull(Dynamic<?> _snowman) {
      return _snowman.get("Owner")
         .get()
         .map(_snowmanx -> updateStringUuid(_snowmanx, "Id", "Id").orElse(_snowmanx))
         .map(_snowmanxx -> _snowman.remove("Owner").set("SkullOwner", _snowmanxx))
         .result()
         .orElse(_snowman);
   }

   private Dynamic<?> updateConduit(Dynamic<?> _snowman) {
      return updateCompoundUuid(_snowman, "target_uuid", "Target").orElse(_snowman);
   }
}
