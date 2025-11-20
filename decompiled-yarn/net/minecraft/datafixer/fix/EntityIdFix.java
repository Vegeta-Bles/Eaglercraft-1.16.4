package net.minecraft.datafixer.fix;

import com.google.common.collect.Maps;
import com.mojang.datafixers.DataFix;
import com.mojang.datafixers.DataFixUtils;
import com.mojang.datafixers.TypeRewriteRule;
import com.mojang.datafixers.schemas.Schema;
import com.mojang.datafixers.types.Type;
import com.mojang.datafixers.types.templates.TaggedChoice.TaggedChoiceType;
import java.util.Map;
import net.minecraft.datafixer.TypeReferences;

public class EntityIdFix extends DataFix {
   private static final Map<String, String> RENAMED_ENTITIES = (Map<String, String>)DataFixUtils.make(Maps.newHashMap(), _snowman -> {
      _snowman.put("AreaEffectCloud", "minecraft:area_effect_cloud");
      _snowman.put("ArmorStand", "minecraft:armor_stand");
      _snowman.put("Arrow", "minecraft:arrow");
      _snowman.put("Bat", "minecraft:bat");
      _snowman.put("Blaze", "minecraft:blaze");
      _snowman.put("Boat", "minecraft:boat");
      _snowman.put("CaveSpider", "minecraft:cave_spider");
      _snowman.put("Chicken", "minecraft:chicken");
      _snowman.put("Cow", "minecraft:cow");
      _snowman.put("Creeper", "minecraft:creeper");
      _snowman.put("Donkey", "minecraft:donkey");
      _snowman.put("DragonFireball", "minecraft:dragon_fireball");
      _snowman.put("ElderGuardian", "minecraft:elder_guardian");
      _snowman.put("EnderCrystal", "minecraft:ender_crystal");
      _snowman.put("EnderDragon", "minecraft:ender_dragon");
      _snowman.put("Enderman", "minecraft:enderman");
      _snowman.put("Endermite", "minecraft:endermite");
      _snowman.put("EyeOfEnderSignal", "minecraft:eye_of_ender_signal");
      _snowman.put("FallingSand", "minecraft:falling_block");
      _snowman.put("Fireball", "minecraft:fireball");
      _snowman.put("FireworksRocketEntity", "minecraft:fireworks_rocket");
      _snowman.put("Ghast", "minecraft:ghast");
      _snowman.put("Giant", "minecraft:giant");
      _snowman.put("Guardian", "minecraft:guardian");
      _snowman.put("Horse", "minecraft:horse");
      _snowman.put("Husk", "minecraft:husk");
      _snowman.put("Item", "minecraft:item");
      _snowman.put("ItemFrame", "minecraft:item_frame");
      _snowman.put("LavaSlime", "minecraft:magma_cube");
      _snowman.put("LeashKnot", "minecraft:leash_knot");
      _snowman.put("MinecartChest", "minecraft:chest_minecart");
      _snowman.put("MinecartCommandBlock", "minecraft:commandblock_minecart");
      _snowman.put("MinecartFurnace", "minecraft:furnace_minecart");
      _snowman.put("MinecartHopper", "minecraft:hopper_minecart");
      _snowman.put("MinecartRideable", "minecraft:minecart");
      _snowman.put("MinecartSpawner", "minecraft:spawner_minecart");
      _snowman.put("MinecartTNT", "minecraft:tnt_minecart");
      _snowman.put("Mule", "minecraft:mule");
      _snowman.put("MushroomCow", "minecraft:mooshroom");
      _snowman.put("Ozelot", "minecraft:ocelot");
      _snowman.put("Painting", "minecraft:painting");
      _snowman.put("Pig", "minecraft:pig");
      _snowman.put("PigZombie", "minecraft:zombie_pigman");
      _snowman.put("PolarBear", "minecraft:polar_bear");
      _snowman.put("PrimedTnt", "minecraft:tnt");
      _snowman.put("Rabbit", "minecraft:rabbit");
      _snowman.put("Sheep", "minecraft:sheep");
      _snowman.put("Shulker", "minecraft:shulker");
      _snowman.put("ShulkerBullet", "minecraft:shulker_bullet");
      _snowman.put("Silverfish", "minecraft:silverfish");
      _snowman.put("Skeleton", "minecraft:skeleton");
      _snowman.put("SkeletonHorse", "minecraft:skeleton_horse");
      _snowman.put("Slime", "minecraft:slime");
      _snowman.put("SmallFireball", "minecraft:small_fireball");
      _snowman.put("SnowMan", "minecraft:snowman");
      _snowman.put("Snowball", "minecraft:snowball");
      _snowman.put("SpectralArrow", "minecraft:spectral_arrow");
      _snowman.put("Spider", "minecraft:spider");
      _snowman.put("Squid", "minecraft:squid");
      _snowman.put("Stray", "minecraft:stray");
      _snowman.put("ThrownEgg", "minecraft:egg");
      _snowman.put("ThrownEnderpearl", "minecraft:ender_pearl");
      _snowman.put("ThrownExpBottle", "minecraft:xp_bottle");
      _snowman.put("ThrownPotion", "minecraft:potion");
      _snowman.put("Villager", "minecraft:villager");
      _snowman.put("VillagerGolem", "minecraft:villager_golem");
      _snowman.put("Witch", "minecraft:witch");
      _snowman.put("WitherBoss", "minecraft:wither");
      _snowman.put("WitherSkeleton", "minecraft:wither_skeleton");
      _snowman.put("WitherSkull", "minecraft:wither_skull");
      _snowman.put("Wolf", "minecraft:wolf");
      _snowman.put("XPOrb", "minecraft:xp_orb");
      _snowman.put("Zombie", "minecraft:zombie");
      _snowman.put("ZombieHorse", "minecraft:zombie_horse");
      _snowman.put("ZombieVillager", "minecraft:zombie_villager");
   });

   public EntityIdFix(Schema outputSchema, boolean changesType) {
      super(outputSchema, changesType);
   }

   public TypeRewriteRule makeRule() {
      TaggedChoiceType<String> _snowman = this.getInputSchema().findChoiceType(TypeReferences.ENTITY);
      TaggedChoiceType<String> _snowmanx = this.getOutputSchema().findChoiceType(TypeReferences.ENTITY);
      Type<?> _snowmanxx = this.getInputSchema().getType(TypeReferences.ITEM_STACK);
      Type<?> _snowmanxxx = this.getOutputSchema().getType(TypeReferences.ITEM_STACK);
      return TypeRewriteRule.seq(
         this.convertUnchecked("item stack entity name hook converter", _snowmanxx, _snowmanxxx),
         this.fixTypeEverywhere("EntityIdFix", _snowman, _snowmanx, _snowmanxxxx -> _snowmanxxxxx -> _snowmanxxxxx.mapFirst(_snowmanxxxxxx -> RENAMED_ENTITIES.getOrDefault(_snowmanxxxxxx, _snowmanxxxxxx)))
      );
   }
}
