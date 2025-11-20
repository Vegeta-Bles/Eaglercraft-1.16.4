package net.minecraft.datafixer.fix;

import com.google.common.collect.ImmutableMap;
import com.mojang.datafixers.DSL;
import com.mojang.datafixers.DataFix;
import com.mojang.datafixers.DataFixUtils;
import com.mojang.datafixers.OpticFinder;
import com.mojang.datafixers.TypeRewriteRule;
import com.mojang.datafixers.Typed;
import com.mojang.datafixers.schemas.Schema;
import com.mojang.datafixers.types.Type;
import com.mojang.serialization.Dynamic;
import java.util.Map;
import net.minecraft.datafixer.TypeReferences;

public class RenameItemStackAttributesFix extends DataFix {
   private static final Map<String, String> RENAMES = ImmutableMap.builder()
      .put("generic.maxHealth", "generic.max_health")
      .put("Max Health", "generic.max_health")
      .put("zombie.spawnReinforcements", "zombie.spawn_reinforcements")
      .put("Spawn Reinforcements Chance", "zombie.spawn_reinforcements")
      .put("horse.jumpStrength", "horse.jump_strength")
      .put("Jump Strength", "horse.jump_strength")
      .put("generic.followRange", "generic.follow_range")
      .put("Follow Range", "generic.follow_range")
      .put("generic.knockbackResistance", "generic.knockback_resistance")
      .put("Knockback Resistance", "generic.knockback_resistance")
      .put("generic.movementSpeed", "generic.movement_speed")
      .put("Movement Speed", "generic.movement_speed")
      .put("generic.flyingSpeed", "generic.flying_speed")
      .put("Flying Speed", "generic.flying_speed")
      .put("generic.attackDamage", "generic.attack_damage")
      .put("generic.attackKnockback", "generic.attack_knockback")
      .put("generic.attackSpeed", "generic.attack_speed")
      .put("generic.armorToughness", "generic.armor_toughness")
      .build();

   public RenameItemStackAttributesFix(Schema outputSchema) {
      super(outputSchema, false);
   }

   protected TypeRewriteRule makeRule() {
      Type<?> _snowman = this.getInputSchema().getType(TypeReferences.ITEM_STACK);
      OpticFinder<?> _snowmanx = _snowman.findField("tag");
      return TypeRewriteRule.seq(
         this.fixTypeEverywhereTyped("Rename ItemStack Attributes", _snowman, _snowmanxx -> _snowmanxx.updateTyped(_snowman, RenameItemStackAttributesFix::updateAttributeModifiers)),
         new TypeRewriteRule[]{
            this.fixTypeEverywhereTyped(
               "Rename Entity Attributes", this.getInputSchema().getType(TypeReferences.ENTITY), RenameItemStackAttributesFix::updatePlayerAttributes
            ),
            this.fixTypeEverywhereTyped(
               "Rename Player Attributes", this.getInputSchema().getType(TypeReferences.PLAYER), RenameItemStackAttributesFix::updatePlayerAttributes
            )
         }
      );
   }

   private static Dynamic<?> updateAttributeName(Dynamic<?> _snowman) {
      return (Dynamic<?>)DataFixUtils.orElse(_snowman.asString().result().map(_snowmanx -> RENAMES.getOrDefault(_snowmanx, _snowmanx)).map(_snowman::createString), _snowman);
   }

   private static Typed<?> updateAttributeModifiers(Typed<?> typed) {
      return typed.update(
         DSL.remainderFinder(),
         _snowman -> _snowman.update(
               "AttributeModifiers",
               _snowmanx -> (Dynamic)DataFixUtils.orElse(
                     _snowmanx.asStreamOpt()
                        .result()
                        .map(_snowmanxx -> _snowmanxx.map(_snowmanxxx -> _snowmanxxx.update("AttributeName", RenameItemStackAttributesFix::updateAttributeName)))
                        .map(_snowmanx::createList),
                     _snowmanx
                  )
            )
      );
   }

   private static Typed<?> updatePlayerAttributes(Typed<?> typed) {
      return typed.update(
         DSL.remainderFinder(),
         _snowman -> _snowman.update(
               "Attributes",
               _snowmanx -> (Dynamic)DataFixUtils.orElse(
                     _snowmanx.asStreamOpt()
                        .result()
                        .map(_snowmanxx -> _snowmanxx.map(_snowmanxxx -> _snowmanxxx.update("Name", RenameItemStackAttributesFix::updateAttributeName)))
                        .map(_snowmanx::createList),
                     _snowmanx
                  )
            )
      );
   }
}
