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
import org.apache.commons.lang3.StringUtils;

public class akx extends DataFix {
   private static final Set<String> a = ImmutableSet.builder()
      .add("stat.craftItem.minecraft.spawn_egg")
      .add("stat.useItem.minecraft.spawn_egg")
      .add("stat.breakItem.minecraft.spawn_egg")
      .add("stat.pickup.minecraft.spawn_egg")
      .add("stat.drop.minecraft.spawn_egg")
      .build();
   private static final Map<String, String> b = ImmutableMap.builder()
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
   private static final Map<String, String> c = ImmutableMap.builder()
      .put("stat.craftItem", "minecraft:crafted")
      .put("stat.useItem", "minecraft:used")
      .put("stat.breakItem", "minecraft:broken")
      .put("stat.pickup", "minecraft:picked_up")
      .put("stat.drop", "minecraft:dropped")
      .build();
   private static final Map<String, String> d = ImmutableMap.builder()
      .put("stat.entityKilledBy", "minecraft:killed_by")
      .put("stat.killEntity", "minecraft:killed")
      .build();
   private static final Map<String, String> e = ImmutableMap.builder()
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

   public akx(Schema var1, boolean var2) {
      super(_snowman, _snowman);
   }

   public TypeRewriteRule makeRule() {
      Type<?> _snowman = this.getOutputSchema().getType(akn.g);
      return this.fixTypeEverywhereTyped(
         "StatsCounterFix",
         this.getInputSchema().getType(akn.g),
         _snowman,
         var2 -> {
            Dynamic<?> _snowmanx = (Dynamic<?>)var2.get(DSL.remainderFinder());
            Map<Dynamic<?>, Dynamic<?>> _snowmanx = Maps.newHashMap();
            Optional<? extends Map<? extends Dynamic<?>, ? extends Dynamic<?>>> _snowmanxx = _snowmanx.getMapValues().result();
            if (_snowmanxx.isPresent()) {
               for (Entry<? extends Dynamic<?>, ? extends Dynamic<?>> _snowmanxxx : _snowmanxx.get().entrySet()) {
                  if (_snowmanxxx.getValue().asNumber().result().isPresent()) {
                     String _snowmanxxxx = _snowmanxxx.getKey().asString("");
                     if (!a.contains(_snowmanxxxx)) {
                        String _snowmanxxxxx;
                        String _snowmanxxxxxx;
                        if (b.containsKey(_snowmanxxxx)) {
                           _snowmanxxxxx = "minecraft:custom";
                           _snowmanxxxxxx = b.get(_snowmanxxxx);
                        } else {
                           int _snowmanxxxxxxx = StringUtils.ordinalIndexOf(_snowmanxxxx, ".", 2);
                           if (_snowmanxxxxxxx < 0) {
                              continue;
                           }

                           String _snowmanxxxxxxxx = _snowmanxxxx.substring(0, _snowmanxxxxxxx);
                           if ("stat.mineBlock".equals(_snowmanxxxxxxxx)) {
                              _snowmanxxxxx = "minecraft:mined";
                              _snowmanxxxxxx = this.b(_snowmanxxxx.substring(_snowmanxxxxxxx + 1).replace('.', ':'));
                           } else if (c.containsKey(_snowmanxxxxxxxx)) {
                              _snowmanxxxxx = c.get(_snowmanxxxxxxxx);
                              String _snowmanxxxxxxxxx = _snowmanxxxx.substring(_snowmanxxxxxxx + 1).replace('.', ':');
                              String _snowmanxxxxxxxxxx = this.a(_snowmanxxxxxxxxx);
                              _snowmanxxxxxx = _snowmanxxxxxxxxxx == null ? _snowmanxxxxxxxxx : _snowmanxxxxxxxxxx;
                           } else {
                              if (!d.containsKey(_snowmanxxxxxxxx)) {
                                 continue;
                              }

                              _snowmanxxxxx = d.get(_snowmanxxxxxxxx);
                              String _snowmanxxxxxxxxx = _snowmanxxxx.substring(_snowmanxxxxxxx + 1).replace('.', ':');
                              _snowmanxxxxxx = e.getOrDefault(_snowmanxxxxxxxxx, _snowmanxxxxxxxxx);
                           }
                        }

                        Dynamic<?> _snowmanxxxxxxxx = _snowmanx.createString(_snowmanxxxxx);
                        Dynamic<?> _snowmanxxxxxxxxx = _snowmanx.computeIfAbsent(_snowmanxxxxxxxx, var1x -> _snowman.emptyMap());
                        _snowmanx.put(_snowmanxxxxxxxx, _snowmanxxxxxxxxx.set(_snowmanxxxxxx, _snowmanxxx.getValue()));
                     }
                  }
               }
            }

            return (Typed)((Pair)_snowman.readTyped(_snowmanx.emptyMap().set("stats", _snowmanx.createMap(_snowmanx)))
                  .result()
                  .orElseThrow(() -> new IllegalStateException("Could not parse new stats object.")))
               .getFirst();
         }
      );
   }

   @Nullable
   protected String a(String var1) {
      return ajh.a(_snowman, 0);
   }

   protected String b(String var1) {
      return agz.a(_snowman);
   }
}
