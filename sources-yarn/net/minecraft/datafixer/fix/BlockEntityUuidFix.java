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
      return this.fixTypeEverywhereTyped("BlockEntityUUIDFix", this.getInputSchema().getType(this.typeReference), typed -> {
         typed = this.updateTyped(typed, "minecraft:conduit", this::updateConduit);
         return this.updateTyped(typed, "minecraft:skull", this::updateSkull);
      });
   }

   private <T> Dynamic<T> updateSkull(Dynamic<T> dynamic) {
      return dynamic.get("Owner").result().map(owner -> {
            @SuppressWarnings("unchecked")
            Dynamic<T> fixed = (Dynamic<T>)updateStringUuid(owner, "Id", "Id").orElse(owner);
            return dynamic.remove("Owner").set("SkullOwner", fixed);
         }).orElse(dynamic);
   }

   private <T> Dynamic<T> updateConduit(Dynamic<T> dynamic) {
      return updateCompoundUuid(dynamic, "target_uuid", "Target").map(result -> {
            @SuppressWarnings("unchecked")
            Dynamic<T> fixed = (Dynamic<T>)result;
            return fixed;
         }).orElse(dynamic);
   }
}
