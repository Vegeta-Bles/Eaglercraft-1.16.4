package net.minecraft.datafixer.fix;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Maps;
import com.mojang.datafixers.DSL;
import com.mojang.datafixers.DataFix;
import com.mojang.datafixers.TypeRewriteRule;
import com.mojang.datafixers.Typed;
import com.mojang.datafixers.schemas.Schema;
import com.mojang.datafixers.types.Type;
import com.mojang.datafixers.util.Pair;
import com.mojang.serialization.Dynamic;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.Map.Entry;
import javax.annotation.Nullable;
import net.minecraft.datafixer.TypeReferences;
import org.apache.commons.lang3.StringUtils;

public class StatsCounterFix extends DataFix {
   private static final Set<String> SKIP = ImmutableSet.builder()
      .add("stat.craftItem.minecraft.spawn_egg")
      .add("stat.useItem.minecraft.spawn_egg")
      .add("stat.breakItem.minecraft.spawn_egg")
      .add("stat.pickup.minecraft.spawn_egg")
      .add("stat.drop.minecraft.spawn_egg")
      .build();
   private static final Map<String, String> RENAMED_GENERAL_STATS = ImmutableMap.builder()
      .put("stat.leaveGame", "minecraft:leave_game")
      .put("stat.playOneMinute", "minecraft:play_one_minute")
      .put("stat.timeSinceDeath", "minecraft:time_since_death")
      .put("stat.sneakTime", "minecraft:sneak_time")
      .put("stat.walkOneCm", "minecraft:walk_one_cm")
      .put("stat.crouchOneCm", "minecraft:crouch_one_cm")
      .put("stat.sprintOneCm", "minecraft:sprint_one_cm")
      .put("stat.swimOneCm", "minecraft:swim_one_cm")
      .put("stat.fallOneCm", "minecraft:fall_one_cm")
      .put("stat.climbOneCm", "minecraft:climb_one_cm")
      .put("stat.flyOneCm", "minecraft:fly_one_cm")
      .put("stat.diveOneCm", "minecraft:dive_one_cm")
      .put("stat.minecartOneCm", "minecraft:minecart_one_cm")
      .put("stat.boatOneCm", "minecraft:boat_one_cm")
      .put("stat.pigOneCm", "minecraft:pig_one_cm")
      .put("stat.horseOneCm", "minecraft:horse_one_cm")
      .put("stat.aviateOneCm", "minecraft:aviate_one_cm")
      .put("stat.jump", "minecraft:jump")
      .put("stat.drop", "minecraft:drop")
      .put("stat.damageDealt", "minecraft:damage_dealt")
      .put("stat.damageTaken", "minecraft:damage_taken")
      .put("stat.deaths", "minecraft:deaths")
      .put("stat.mobKills", "minecraft:mob_kills")
      .put("stat.animalsBred", "minecraft:animals_bred")
      .put("stat.playerKills", "minecraft:player_kills")
      .put("stat.fishCaught", "minecraft:fish_caught")
      .put("stat.talkedToVillager", "minecraft:talked_to_villager")
      .put("stat.tradedWithVillager", "minecraft:traded_with_villager")
      .put("stat.cakeSlicesEaten", "minecraft:eat_cake_slice")
      .put("stat.cauldronFilled", "minecraft:fill_cauldron")
      .put("stat.cauldronUsed", "minecraft:use_cauldron")
      .put("stat.armorCleaned", "minecraft:clean_armor")
      .put("stat.bannerCleaned", "minecraft:clean_banner")
      .put("stat.brewingstandInteraction", "minecraft:interact_with_brewingstand")
      .put("stat.beaconInteraction", "minecraft:interact_with_beacon")
      .put("stat.dropperInspected", "minecraft:inspect_dropper")
      .put("stat.hopperInspected", "minecraft:inspect_hopper")
      .put("stat.dispenserInspected", "minecraft:inspect_dispenser")
      .put("stat.noteblockPlayed", "minecraft:play_noteblock")
      .put("stat.noteblockTuned", "minecraft:tune_noteblock")
      .put("stat.flowerPotted", "minecraft:pot_flower")
      .put("stat.trappedChestTriggered", "minecraft:trigger_trapped_chest")
      .put("stat.enderchestOpened", "minecraft:open_enderchest")
      .put("stat.itemEnchanted", "minecraft:enchant_item")
      .put("stat.recordPlayed", "minecraft:play_record")
      .put("stat.furnaceInteraction", "minecraft:interact_with_furnace")
      .put("stat.craftingTableInteraction", "minecraft:interact_with_crafting_table")
      .put("stat.chestOpened", "minecraft:open_chest")
      .put("stat.sleepInBed", "minecraft:sleep_in_bed")
      .put("stat.shulkerBoxOpened", "minecraft:open_shulker_box")
      .build();
   private static final Map<String, String> RENAMED_ITEM_STATS = ImmutableMap.builder()
      .put("stat.craftItem", "minecraft:crafted")
      .put("stat.useItem", "minecraft:used")
      .put("stat.breakItem", "minecraft:broken")
      .put("stat.pickup", "minecraft:picked_up")
      .put("stat.drop", "minecraft:dropped")
      .build();
   private static final Map<String, String> RENAMED_ENTITY_STATS = ImmutableMap.builder()
      .put("stat.entityKilledBy", "minecraft:killed_by")
      .put("stat.killEntity", "minecraft:killed")
      .build();
   private static final Map<String, String> RENAMED_ENTITIES = ImmutableMap.builder()
      .put("Bat", "minecraft:bat")
      .put("Blaze", "minecraft:blaze")
      .put("CaveSpider", "minecraft:cave_spider")
      .put("Chicken", "minecraft:chicken")
      .put("Cow", "minecraft:cow")
      .put("Creeper", "minecraft:creeper")
      .put("Donkey", "minecraft:donkey")
      .put("ElderGuardian", "minecraft:elder_guardian")
      .put("Enderman", "minecraft:enderman")
      .put("Endermite", "minecraft:endermite")
      .put("EvocationIllager", "minecraft:evocation_illager")
      .put("Ghast", "minecraft:ghast")
      .put("Guardian", "minecraft:guardian")
      .put("Horse", "minecraft:horse")
      .put("Husk", "minecraft:husk")
      .put("Llama", "minecraft:llama")
      .put("LavaSlime", "minecraft:magma_cube")
      .put("MushroomCow", "minecraft:mooshroom")
      .put("Mule", "minecraft:mule")
      .put("Ozelot", "minecraft:ocelot")
      .put("Parrot", "minecraft:parrot")
      .put("Pig", "minecraft:pig")
      .put("PolarBear", "minecraft:polar_bear")
      .put("Rabbit", "minecraft:rabbit")
      .put("Sheep", "minecraft:sheep")
      .put("Shulker", "minecraft:shulker")
      .put("Silverfish", "minecraft:silverfish")
      .put("SkeletonHorse", "minecraft:skeleton_horse")
      .put("Skeleton", "minecraft:skeleton")
      .put("Slime", "minecraft:slime")
      .put("Spider", "minecraft:spider")
      .put("Squid", "minecraft:squid")
      .put("Stray", "minecraft:stray")
      .put("Vex", "minecraft:vex")
      .put("Villager", "minecraft:villager")
      .put("VindicationIllager", "minecraft:vindication_illager")
      .put("Witch", "minecraft:witch")
      .put("WitherSkeleton", "minecraft:wither_skeleton")
      .put("Wolf", "minecraft:wolf")
      .put("ZombieHorse", "minecraft:zombie_horse")
      .put("PigZombie", "minecraft:zombie_pigman")
      .put("ZombieVillager", "minecraft:zombie_villager")
      .put("Zombie", "minecraft:zombie")
      .build();

   public StatsCounterFix(Schema outputSchema, boolean changesType) {
      super(outputSchema, changesType);
   }

   public TypeRewriteRule makeRule() {
      Type<?> _snowman = this.getOutputSchema().getType(TypeReferences.STATS);
      return this.fixTypeEverywhereTyped(
         "StatsCounterFix",
         this.getInputSchema().getType(TypeReferences.STATS),
         _snowman,
         _snowmanx -> {
            Dynamic<?> _snowmanx = (Dynamic<?>)_snowmanx.get(DSL.remainderFinder());
            Map<Dynamic<?>, Dynamic<?>> _snowmanxx = Maps.newHashMap();
            Optional<? extends Map<? extends Dynamic<?>, ? extends Dynamic<?>>> _snowmanxxx = _snowmanx.getMapValues().result();
            if (_snowmanxxx.isPresent()) {
               for (Entry<? extends Dynamic<?>, ? extends Dynamic<?>> _snowmanxxxx : _snowmanxxx.get().entrySet()) {
                  if (_snowmanxxxx.getValue().asNumber().result().isPresent()) {
                     String _snowmanxxxxx = _snowmanxxxx.getKey().asString("");
                     if (!SKIP.contains(_snowmanxxxxx)) {
                        String _snowmanxxxxxx;
                        String _snowmanxxxxxxx;
                        if (RENAMED_GENERAL_STATS.containsKey(_snowmanxxxxx)) {
                           _snowmanxxxxxx = "minecraft:custom";
                           _snowmanxxxxxxx = RENAMED_GENERAL_STATS.get(_snowmanxxxxx);
                        } else {
                           int _snowmanxxxxxxxx = StringUtils.ordinalIndexOf(_snowmanxxxxx, ".", 2);
                           if (_snowmanxxxxxxxx < 0) {
                              continue;
                           }

                           String _snowmanxxxxxxxxx = _snowmanxxxxx.substring(0, _snowmanxxxxxxxx);
                           if ("stat.mineBlock".equals(_snowmanxxxxxxxxx)) {
                              _snowmanxxxxxx = "minecraft:mined";
                              _snowmanxxxxxxx = this.getBlock(_snowmanxxxxx.substring(_snowmanxxxxxxxx + 1).replace('.', ':'));
                           } else if (RENAMED_ITEM_STATS.containsKey(_snowmanxxxxxxxxx)) {
                              _snowmanxxxxxx = RENAMED_ITEM_STATS.get(_snowmanxxxxxxxxx);
                              String _snowmanxxxxxxxxxx = _snowmanxxxxx.substring(_snowmanxxxxxxxx + 1).replace('.', ':');
                              String _snowmanxxxxxxxxxxx = this.getItem(_snowmanxxxxxxxxxx);
                              _snowmanxxxxxxx = _snowmanxxxxxxxxxxx == null ? _snowmanxxxxxxxxxx : _snowmanxxxxxxxxxxx;
                           } else {
                              if (!RENAMED_ENTITY_STATS.containsKey(_snowmanxxxxxxxxx)) {
                                 continue;
                              }

                              _snowmanxxxxxx = RENAMED_ENTITY_STATS.get(_snowmanxxxxxxxxx);
                              String _snowmanxxxxxxxxxx = _snowmanxxxxx.substring(_snowmanxxxxxxxx + 1).replace('.', ':');
                              _snowmanxxxxxxx = RENAMED_ENTITIES.getOrDefault(_snowmanxxxxxxxxxx, _snowmanxxxxxxxxxx);
                           }
                        }

                        Dynamic<?> _snowmanxxxxxxxxx = _snowmanx.createString(_snowmanxxxxxx);
                        Dynamic<?> _snowmanxxxxxxxxxx = _snowmanxx.computeIfAbsent(_snowmanxxxxxxxxx, _snowmanxxxxxxxxxxx -> _snowman.emptyMap());
                        _snowmanxx.put(_snowmanxxxxxxxxx, _snowmanxxxxxxxxxx.set(_snowmanxxxxxxx, _snowmanxxxx.getValue()));
                     }
                  }
               }
            }

            return (Typed)((Pair)_snowman.readTyped(_snowmanx.emptyMap().set("stats", _snowmanx.createMap(_snowmanxx)))
                  .result()
                  .orElseThrow(() -> new IllegalStateException("Could not parse new stats object.")))
               .getFirst();
         }
      );
   }

   @Nullable
   protected String getItem(String _snowman) {
      return ItemInstanceTheFlatteningFix.getItem(_snowman, 0);
   }

   protected String getBlock(String _snowman) {
      return BlockStateFlattening.lookupBlock(_snowman);
   }
}
