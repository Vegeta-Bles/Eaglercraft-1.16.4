package net.minecraft.datafixer.fix;

import com.google.common.collect.Sets;
import com.mojang.datafixers.DSL;
import com.mojang.datafixers.DataFixUtils;
import com.mojang.datafixers.TypeRewriteRule;
import com.mojang.datafixers.schemas.Schema;
import com.mojang.serialization.Dynamic;
import java.util.Optional;
import java.util.Set;
import net.minecraft.datafixer.TypeReferences;

public class EntityUuidFix extends AbstractUuidFix {
   private static final Set<String> RIDEABLE_TAMEABLES = Sets.newHashSet();
   private static final Set<String> TAMEABLE_PETS = Sets.newHashSet();
   private static final Set<String> BREEDABLES = Sets.newHashSet();
   private static final Set<String> LEASHABLES = Sets.newHashSet();
   private static final Set<String> OTHER_LIVINGS = Sets.newHashSet();
   private static final Set<String> PROJECTILES = Sets.newHashSet();

   public EntityUuidFix(Schema outputSchema) {
      super(outputSchema, TypeReferences.ENTITY);
   }

   protected TypeRewriteRule makeRule() {
      return this.fixTypeEverywhereTyped("EntityUUIDFixes", this.getInputSchema().getType(this.typeReference), _snowman -> {
         _snowman = _snowman.update(DSL.remainderFinder(), EntityUuidFix::updateSelfUuid);

         for (String _snowmanx : RIDEABLE_TAMEABLES) {
            _snowman = this.updateTyped(_snowman, _snowmanx, EntityUuidFix::updateTameable);
         }

         for (String _snowmanx : TAMEABLE_PETS) {
            _snowman = this.updateTyped(_snowman, _snowmanx, EntityUuidFix::updateTameable);
         }

         for (String _snowmanx : BREEDABLES) {
            _snowman = this.updateTyped(_snowman, _snowmanx, EntityUuidFix::updateBreedable);
         }

         for (String _snowmanx : LEASHABLES) {
            _snowman = this.updateTyped(_snowman, _snowmanx, EntityUuidFix::updateLeashable);
         }

         for (String _snowmanx : OTHER_LIVINGS) {
            _snowman = this.updateTyped(_snowman, _snowmanx, EntityUuidFix::updateLiving);
         }

         for (String _snowmanx : PROJECTILES) {
            _snowman = this.updateTyped(_snowman, _snowmanx, EntityUuidFix::updateProjectile);
         }

         _snowman = this.updateTyped(_snowman, "minecraft:bee", EntityUuidFix::updateZombifiedPiglin);
         _snowman = this.updateTyped(_snowman, "minecraft:zombified_piglin", EntityUuidFix::updateZombifiedPiglin);
         _snowman = this.updateTyped(_snowman, "minecraft:fox", EntityUuidFix::updateFox);
         _snowman = this.updateTyped(_snowman, "minecraft:item", EntityUuidFix::updateItemEntity);
         _snowman = this.updateTyped(_snowman, "minecraft:shulker_bullet", EntityUuidFix::updateShulkerBullet);
         _snowman = this.updateTyped(_snowman, "minecraft:area_effect_cloud", EntityUuidFix::updateAreaEffectCloud);
         _snowman = this.updateTyped(_snowman, "minecraft:zombie_villager", EntityUuidFix::updateZombieVillager);
         _snowman = this.updateTyped(_snowman, "minecraft:evoker_fangs", EntityUuidFix::updateEvokerFangs);
         return this.updateTyped(_snowman, "minecraft:piglin", EntityUuidFix::updateAngryAtMemory);
      });
   }

   private static Dynamic<?> updateAngryAtMemory(Dynamic<?> _snowman) {
      return _snowman.update(
         "Brain", _snowmanx -> _snowmanx.update("memories", _snowmanxx -> _snowmanxx.update("minecraft:angry_at", _snowmanxxx -> updateStringUuid(_snowmanxxx, "value", "value").orElseGet(() -> {
                     LOGGER.warn("angry_at has no value.");
                     return _snowmanxxx;
                  })))
      );
   }

   private static Dynamic<?> updateEvokerFangs(Dynamic<?> _snowman) {
      return updateRegularMostLeast(_snowman, "OwnerUUID", "Owner").orElse(_snowman);
   }

   private static Dynamic<?> updateZombieVillager(Dynamic<?> _snowman) {
      return updateRegularMostLeast(_snowman, "ConversionPlayer", "ConversionPlayer").orElse(_snowman);
   }

   private static Dynamic<?> updateAreaEffectCloud(Dynamic<?> _snowman) {
      return updateRegularMostLeast(_snowman, "OwnerUUID", "Owner").orElse(_snowman);
   }

   private static Dynamic<?> updateShulkerBullet(Dynamic<?> _snowman) {
      _snowman = updateCompoundUuid(_snowman, "Owner", "Owner").orElse(_snowman);
      return updateCompoundUuid(_snowman, "Target", "Target").orElse(_snowman);
   }

   private static Dynamic<?> updateItemEntity(Dynamic<?> _snowman) {
      _snowman = updateCompoundUuid(_snowman, "Owner", "Owner").orElse(_snowman);
      return updateCompoundUuid(_snowman, "Thrower", "Thrower").orElse(_snowman);
   }

   private static Dynamic<?> updateFox(Dynamic<?> _snowman) {
      Optional<Dynamic<?>> _snowmanx = _snowman.get("TrustedUUIDs")
         .result()
         .map(_snowmanxx -> _snowman.createList(_snowmanxx.asStream().map(_snowmanxxx -> (Dynamic)createArrayFromCompoundUuid(_snowmanxxx).orElseGet(() -> {
                  LOGGER.warn("Trusted contained invalid data.");
                  return _snowmanxxx;
               }))));
      return (Dynamic<?>)DataFixUtils.orElse(_snowmanx.map(_snowmanxx -> _snowman.remove("TrustedUUIDs").set("Trusted", _snowmanxx)), _snowman);
   }

   private static Dynamic<?> updateZombifiedPiglin(Dynamic<?> _snowman) {
      return updateStringUuid(_snowman, "HurtBy", "HurtBy").orElse(_snowman);
   }

   private static Dynamic<?> updateTameable(Dynamic<?> _snowman) {
      Dynamic<?> _snowmanx = updateBreedable(_snowman);
      return updateStringUuid(_snowmanx, "OwnerUUID", "Owner").orElse(_snowmanx);
   }

   private static Dynamic<?> updateBreedable(Dynamic<?> _snowman) {
      Dynamic<?> _snowmanx = updateLeashable(_snowman);
      return updateRegularMostLeast(_snowmanx, "LoveCause", "LoveCause").orElse(_snowmanx);
   }

   private static Dynamic<?> updateLeashable(Dynamic<?> _snowman) {
      return updateLiving(_snowman).update("Leash", _snowmanx -> updateRegularMostLeast(_snowmanx, "UUID", "UUID").orElse(_snowmanx));
   }

   public static Dynamic<?> updateLiving(Dynamic<?> _snowman) {
      return _snowman.update(
         "Attributes",
         _snowmanxx -> _snowman.createList(
               _snowmanxx.asStream()
                  .map(
                     _snowmanxxx -> _snowmanxxx.update(
                           "Modifiers",
                           _snowmanxxxxx -> _snowmanxxx.createList(_snowmanxxxxx.asStream().map(_snowmanxxxxxx -> (Dynamic)updateRegularMostLeast(_snowmanxxxxxx, "UUID", "UUID").orElse(_snowmanxxxxxx)))
                        )
                  )
            )
      );
   }

   private static Dynamic<?> updateProjectile(Dynamic<?> _snowman) {
      return (Dynamic<?>)DataFixUtils.orElse(_snowman.get("OwnerUUID").result().map(_snowmanxx -> _snowman.remove("OwnerUUID").set("Owner", _snowmanxx)), _snowman);
   }

   public static Dynamic<?> updateSelfUuid(Dynamic<?> _snowman) {
      return updateRegularMostLeast(_snowman, "UUID", "UUID").orElse(_snowman);
   }

   static {
      RIDEABLE_TAMEABLES.add("minecraft:donkey");
      RIDEABLE_TAMEABLES.add("minecraft:horse");
      RIDEABLE_TAMEABLES.add("minecraft:llama");
      RIDEABLE_TAMEABLES.add("minecraft:mule");
      RIDEABLE_TAMEABLES.add("minecraft:skeleton_horse");
      RIDEABLE_TAMEABLES.add("minecraft:trader_llama");
      RIDEABLE_TAMEABLES.add("minecraft:zombie_horse");
      TAMEABLE_PETS.add("minecraft:cat");
      TAMEABLE_PETS.add("minecraft:parrot");
      TAMEABLE_PETS.add("minecraft:wolf");
      BREEDABLES.add("minecraft:bee");
      BREEDABLES.add("minecraft:chicken");
      BREEDABLES.add("minecraft:cow");
      BREEDABLES.add("minecraft:fox");
      BREEDABLES.add("minecraft:mooshroom");
      BREEDABLES.add("minecraft:ocelot");
      BREEDABLES.add("minecraft:panda");
      BREEDABLES.add("minecraft:pig");
      BREEDABLES.add("minecraft:polar_bear");
      BREEDABLES.add("minecraft:rabbit");
      BREEDABLES.add("minecraft:sheep");
      BREEDABLES.add("minecraft:turtle");
      BREEDABLES.add("minecraft:hoglin");
      LEASHABLES.add("minecraft:bat");
      LEASHABLES.add("minecraft:blaze");
      LEASHABLES.add("minecraft:cave_spider");
      LEASHABLES.add("minecraft:cod");
      LEASHABLES.add("minecraft:creeper");
      LEASHABLES.add("minecraft:dolphin");
      LEASHABLES.add("minecraft:drowned");
      LEASHABLES.add("minecraft:elder_guardian");
      LEASHABLES.add("minecraft:ender_dragon");
      LEASHABLES.add("minecraft:enderman");
      LEASHABLES.add("minecraft:endermite");
      LEASHABLES.add("minecraft:evoker");
      LEASHABLES.add("minecraft:ghast");
      LEASHABLES.add("minecraft:giant");
      LEASHABLES.add("minecraft:guardian");
      LEASHABLES.add("minecraft:husk");
      LEASHABLES.add("minecraft:illusioner");
      LEASHABLES.add("minecraft:magma_cube");
      LEASHABLES.add("minecraft:pufferfish");
      LEASHABLES.add("minecraft:zombified_piglin");
      LEASHABLES.add("minecraft:salmon");
      LEASHABLES.add("minecraft:shulker");
      LEASHABLES.add("minecraft:silverfish");
      LEASHABLES.add("minecraft:skeleton");
      LEASHABLES.add("minecraft:slime");
      LEASHABLES.add("minecraft:snow_golem");
      LEASHABLES.add("minecraft:spider");
      LEASHABLES.add("minecraft:squid");
      LEASHABLES.add("minecraft:stray");
      LEASHABLES.add("minecraft:tropical_fish");
      LEASHABLES.add("minecraft:vex");
      LEASHABLES.add("minecraft:villager");
      LEASHABLES.add("minecraft:iron_golem");
      LEASHABLES.add("minecraft:vindicator");
      LEASHABLES.add("minecraft:pillager");
      LEASHABLES.add("minecraft:wandering_trader");
      LEASHABLES.add("minecraft:witch");
      LEASHABLES.add("minecraft:wither");
      LEASHABLES.add("minecraft:wither_skeleton");
      LEASHABLES.add("minecraft:zombie");
      LEASHABLES.add("minecraft:zombie_villager");
      LEASHABLES.add("minecraft:phantom");
      LEASHABLES.add("minecraft:ravager");
      LEASHABLES.add("minecraft:piglin");
      OTHER_LIVINGS.add("minecraft:armor_stand");
      PROJECTILES.add("minecraft:arrow");
      PROJECTILES.add("minecraft:dragon_fireball");
      PROJECTILES.add("minecraft:firework_rocket");
      PROJECTILES.add("minecraft:fireball");
      PROJECTILES.add("minecraft:llama_spit");
      PROJECTILES.add("minecraft:small_fireball");
      PROJECTILES.add("minecraft:snowball");
      PROJECTILES.add("minecraft:spectral_arrow");
      PROJECTILES.add("minecraft:egg");
      PROJECTILES.add("minecraft:ender_pearl");
      PROJECTILES.add("minecraft:experience_bottle");
      PROJECTILES.add("minecraft:potion");
      PROJECTILES.add("minecraft:trident");
      PROJECTILES.add("minecraft:wither_skull");
   }
}
