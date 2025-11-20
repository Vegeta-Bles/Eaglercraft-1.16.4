package net.minecraft.datafixer.fix;

import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import com.mojang.datafixers.DSL;
import com.mojang.datafixers.DataFix;
import com.mojang.datafixers.DataFixUtils;
import com.mojang.datafixers.OpticFinder;
import com.mojang.datafixers.TypeRewriteRule;
import com.mojang.datafixers.Typed;
import com.mojang.datafixers.schemas.Schema;
import com.mojang.datafixers.types.Type;
import com.mojang.datafixers.util.Pair;
import com.mojang.serialization.Dynamic;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import javax.annotation.Nullable;
import net.minecraft.datafixer.TypeReferences;
import net.minecraft.datafixer.schema.IdentifierNormalizingSchema;

public class ItemInstanceTheFlatteningFix extends DataFix {
   private static final Map<String, String> FLATTENING_MAP = (Map<String, String>)DataFixUtils.make(Maps.newHashMap(), _snowman -> {
      _snowman.put("minecraft:stone.0", "minecraft:stone");
      _snowman.put("minecraft:stone.1", "minecraft:granite");
      _snowman.put("minecraft:stone.2", "minecraft:polished_granite");
      _snowman.put("minecraft:stone.3", "minecraft:diorite");
      _snowman.put("minecraft:stone.4", "minecraft:polished_diorite");
      _snowman.put("minecraft:stone.5", "minecraft:andesite");
      _snowman.put("minecraft:stone.6", "minecraft:polished_andesite");
      _snowman.put("minecraft:dirt.0", "minecraft:dirt");
      _snowman.put("minecraft:dirt.1", "minecraft:coarse_dirt");
      _snowman.put("minecraft:dirt.2", "minecraft:podzol");
      _snowman.put("minecraft:leaves.0", "minecraft:oak_leaves");
      _snowman.put("minecraft:leaves.1", "minecraft:spruce_leaves");
      _snowman.put("minecraft:leaves.2", "minecraft:birch_leaves");
      _snowman.put("minecraft:leaves.3", "minecraft:jungle_leaves");
      _snowman.put("minecraft:leaves2.0", "minecraft:acacia_leaves");
      _snowman.put("minecraft:leaves2.1", "minecraft:dark_oak_leaves");
      _snowman.put("minecraft:log.0", "minecraft:oak_log");
      _snowman.put("minecraft:log.1", "minecraft:spruce_log");
      _snowman.put("minecraft:log.2", "minecraft:birch_log");
      _snowman.put("minecraft:log.3", "minecraft:jungle_log");
      _snowman.put("minecraft:log2.0", "minecraft:acacia_log");
      _snowman.put("minecraft:log2.1", "minecraft:dark_oak_log");
      _snowman.put("minecraft:sapling.0", "minecraft:oak_sapling");
      _snowman.put("minecraft:sapling.1", "minecraft:spruce_sapling");
      _snowman.put("minecraft:sapling.2", "minecraft:birch_sapling");
      _snowman.put("minecraft:sapling.3", "minecraft:jungle_sapling");
      _snowman.put("minecraft:sapling.4", "minecraft:acacia_sapling");
      _snowman.put("minecraft:sapling.5", "minecraft:dark_oak_sapling");
      _snowman.put("minecraft:planks.0", "minecraft:oak_planks");
      _snowman.put("minecraft:planks.1", "minecraft:spruce_planks");
      _snowman.put("minecraft:planks.2", "minecraft:birch_planks");
      _snowman.put("minecraft:planks.3", "minecraft:jungle_planks");
      _snowman.put("minecraft:planks.4", "minecraft:acacia_planks");
      _snowman.put("minecraft:planks.5", "minecraft:dark_oak_planks");
      _snowman.put("minecraft:sand.0", "minecraft:sand");
      _snowman.put("minecraft:sand.1", "minecraft:red_sand");
      _snowman.put("minecraft:quartz_block.0", "minecraft:quartz_block");
      _snowman.put("minecraft:quartz_block.1", "minecraft:chiseled_quartz_block");
      _snowman.put("minecraft:quartz_block.2", "minecraft:quartz_pillar");
      _snowman.put("minecraft:anvil.0", "minecraft:anvil");
      _snowman.put("minecraft:anvil.1", "minecraft:chipped_anvil");
      _snowman.put("minecraft:anvil.2", "minecraft:damaged_anvil");
      _snowman.put("minecraft:wool.0", "minecraft:white_wool");
      _snowman.put("minecraft:wool.1", "minecraft:orange_wool");
      _snowman.put("minecraft:wool.2", "minecraft:magenta_wool");
      _snowman.put("minecraft:wool.3", "minecraft:light_blue_wool");
      _snowman.put("minecraft:wool.4", "minecraft:yellow_wool");
      _snowman.put("minecraft:wool.5", "minecraft:lime_wool");
      _snowman.put("minecraft:wool.6", "minecraft:pink_wool");
      _snowman.put("minecraft:wool.7", "minecraft:gray_wool");
      _snowman.put("minecraft:wool.8", "minecraft:light_gray_wool");
      _snowman.put("minecraft:wool.9", "minecraft:cyan_wool");
      _snowman.put("minecraft:wool.10", "minecraft:purple_wool");
      _snowman.put("minecraft:wool.11", "minecraft:blue_wool");
      _snowman.put("minecraft:wool.12", "minecraft:brown_wool");
      _snowman.put("minecraft:wool.13", "minecraft:green_wool");
      _snowman.put("minecraft:wool.14", "minecraft:red_wool");
      _snowman.put("minecraft:wool.15", "minecraft:black_wool");
      _snowman.put("minecraft:carpet.0", "minecraft:white_carpet");
      _snowman.put("minecraft:carpet.1", "minecraft:orange_carpet");
      _snowman.put("minecraft:carpet.2", "minecraft:magenta_carpet");
      _snowman.put("minecraft:carpet.3", "minecraft:light_blue_carpet");
      _snowman.put("minecraft:carpet.4", "minecraft:yellow_carpet");
      _snowman.put("minecraft:carpet.5", "minecraft:lime_carpet");
      _snowman.put("minecraft:carpet.6", "minecraft:pink_carpet");
      _snowman.put("minecraft:carpet.7", "minecraft:gray_carpet");
      _snowman.put("minecraft:carpet.8", "minecraft:light_gray_carpet");
      _snowman.put("minecraft:carpet.9", "minecraft:cyan_carpet");
      _snowman.put("minecraft:carpet.10", "minecraft:purple_carpet");
      _snowman.put("minecraft:carpet.11", "minecraft:blue_carpet");
      _snowman.put("minecraft:carpet.12", "minecraft:brown_carpet");
      _snowman.put("minecraft:carpet.13", "minecraft:green_carpet");
      _snowman.put("minecraft:carpet.14", "minecraft:red_carpet");
      _snowman.put("minecraft:carpet.15", "minecraft:black_carpet");
      _snowman.put("minecraft:hardened_clay.0", "minecraft:terracotta");
      _snowman.put("minecraft:stained_hardened_clay.0", "minecraft:white_terracotta");
      _snowman.put("minecraft:stained_hardened_clay.1", "minecraft:orange_terracotta");
      _snowman.put("minecraft:stained_hardened_clay.2", "minecraft:magenta_terracotta");
      _snowman.put("minecraft:stained_hardened_clay.3", "minecraft:light_blue_terracotta");
      _snowman.put("minecraft:stained_hardened_clay.4", "minecraft:yellow_terracotta");
      _snowman.put("minecraft:stained_hardened_clay.5", "minecraft:lime_terracotta");
      _snowman.put("minecraft:stained_hardened_clay.6", "minecraft:pink_terracotta");
      _snowman.put("minecraft:stained_hardened_clay.7", "minecraft:gray_terracotta");
      _snowman.put("minecraft:stained_hardened_clay.8", "minecraft:light_gray_terracotta");
      _snowman.put("minecraft:stained_hardened_clay.9", "minecraft:cyan_terracotta");
      _snowman.put("minecraft:stained_hardened_clay.10", "minecraft:purple_terracotta");
      _snowman.put("minecraft:stained_hardened_clay.11", "minecraft:blue_terracotta");
      _snowman.put("minecraft:stained_hardened_clay.12", "minecraft:brown_terracotta");
      _snowman.put("minecraft:stained_hardened_clay.13", "minecraft:green_terracotta");
      _snowman.put("minecraft:stained_hardened_clay.14", "minecraft:red_terracotta");
      _snowman.put("minecraft:stained_hardened_clay.15", "minecraft:black_terracotta");
      _snowman.put("minecraft:silver_glazed_terracotta.0", "minecraft:light_gray_glazed_terracotta");
      _snowman.put("minecraft:stained_glass.0", "minecraft:white_stained_glass");
      _snowman.put("minecraft:stained_glass.1", "minecraft:orange_stained_glass");
      _snowman.put("minecraft:stained_glass.2", "minecraft:magenta_stained_glass");
      _snowman.put("minecraft:stained_glass.3", "minecraft:light_blue_stained_glass");
      _snowman.put("minecraft:stained_glass.4", "minecraft:yellow_stained_glass");
      _snowman.put("minecraft:stained_glass.5", "minecraft:lime_stained_glass");
      _snowman.put("minecraft:stained_glass.6", "minecraft:pink_stained_glass");
      _snowman.put("minecraft:stained_glass.7", "minecraft:gray_stained_glass");
      _snowman.put("minecraft:stained_glass.8", "minecraft:light_gray_stained_glass");
      _snowman.put("minecraft:stained_glass.9", "minecraft:cyan_stained_glass");
      _snowman.put("minecraft:stained_glass.10", "minecraft:purple_stained_glass");
      _snowman.put("minecraft:stained_glass.11", "minecraft:blue_stained_glass");
      _snowman.put("minecraft:stained_glass.12", "minecraft:brown_stained_glass");
      _snowman.put("minecraft:stained_glass.13", "minecraft:green_stained_glass");
      _snowman.put("minecraft:stained_glass.14", "minecraft:red_stained_glass");
      _snowman.put("minecraft:stained_glass.15", "minecraft:black_stained_glass");
      _snowman.put("minecraft:stained_glass_pane.0", "minecraft:white_stained_glass_pane");
      _snowman.put("minecraft:stained_glass_pane.1", "minecraft:orange_stained_glass_pane");
      _snowman.put("minecraft:stained_glass_pane.2", "minecraft:magenta_stained_glass_pane");
      _snowman.put("minecraft:stained_glass_pane.3", "minecraft:light_blue_stained_glass_pane");
      _snowman.put("minecraft:stained_glass_pane.4", "minecraft:yellow_stained_glass_pane");
      _snowman.put("minecraft:stained_glass_pane.5", "minecraft:lime_stained_glass_pane");
      _snowman.put("minecraft:stained_glass_pane.6", "minecraft:pink_stained_glass_pane");
      _snowman.put("minecraft:stained_glass_pane.7", "minecraft:gray_stained_glass_pane");
      _snowman.put("minecraft:stained_glass_pane.8", "minecraft:light_gray_stained_glass_pane");
      _snowman.put("minecraft:stained_glass_pane.9", "minecraft:cyan_stained_glass_pane");
      _snowman.put("minecraft:stained_glass_pane.10", "minecraft:purple_stained_glass_pane");
      _snowman.put("minecraft:stained_glass_pane.11", "minecraft:blue_stained_glass_pane");
      _snowman.put("minecraft:stained_glass_pane.12", "minecraft:brown_stained_glass_pane");
      _snowman.put("minecraft:stained_glass_pane.13", "minecraft:green_stained_glass_pane");
      _snowman.put("minecraft:stained_glass_pane.14", "minecraft:red_stained_glass_pane");
      _snowman.put("minecraft:stained_glass_pane.15", "minecraft:black_stained_glass_pane");
      _snowman.put("minecraft:prismarine.0", "minecraft:prismarine");
      _snowman.put("minecraft:prismarine.1", "minecraft:prismarine_bricks");
      _snowman.put("minecraft:prismarine.2", "minecraft:dark_prismarine");
      _snowman.put("minecraft:concrete.0", "minecraft:white_concrete");
      _snowman.put("minecraft:concrete.1", "minecraft:orange_concrete");
      _snowman.put("minecraft:concrete.2", "minecraft:magenta_concrete");
      _snowman.put("minecraft:concrete.3", "minecraft:light_blue_concrete");
      _snowman.put("minecraft:concrete.4", "minecraft:yellow_concrete");
      _snowman.put("minecraft:concrete.5", "minecraft:lime_concrete");
      _snowman.put("minecraft:concrete.6", "minecraft:pink_concrete");
      _snowman.put("minecraft:concrete.7", "minecraft:gray_concrete");
      _snowman.put("minecraft:concrete.8", "minecraft:light_gray_concrete");
      _snowman.put("minecraft:concrete.9", "minecraft:cyan_concrete");
      _snowman.put("minecraft:concrete.10", "minecraft:purple_concrete");
      _snowman.put("minecraft:concrete.11", "minecraft:blue_concrete");
      _snowman.put("minecraft:concrete.12", "minecraft:brown_concrete");
      _snowman.put("minecraft:concrete.13", "minecraft:green_concrete");
      _snowman.put("minecraft:concrete.14", "minecraft:red_concrete");
      _snowman.put("minecraft:concrete.15", "minecraft:black_concrete");
      _snowman.put("minecraft:concrete_powder.0", "minecraft:white_concrete_powder");
      _snowman.put("minecraft:concrete_powder.1", "minecraft:orange_concrete_powder");
      _snowman.put("minecraft:concrete_powder.2", "minecraft:magenta_concrete_powder");
      _snowman.put("minecraft:concrete_powder.3", "minecraft:light_blue_concrete_powder");
      _snowman.put("minecraft:concrete_powder.4", "minecraft:yellow_concrete_powder");
      _snowman.put("minecraft:concrete_powder.5", "minecraft:lime_concrete_powder");
      _snowman.put("minecraft:concrete_powder.6", "minecraft:pink_concrete_powder");
      _snowman.put("minecraft:concrete_powder.7", "minecraft:gray_concrete_powder");
      _snowman.put("minecraft:concrete_powder.8", "minecraft:light_gray_concrete_powder");
      _snowman.put("minecraft:concrete_powder.9", "minecraft:cyan_concrete_powder");
      _snowman.put("minecraft:concrete_powder.10", "minecraft:purple_concrete_powder");
      _snowman.put("minecraft:concrete_powder.11", "minecraft:blue_concrete_powder");
      _snowman.put("minecraft:concrete_powder.12", "minecraft:brown_concrete_powder");
      _snowman.put("minecraft:concrete_powder.13", "minecraft:green_concrete_powder");
      _snowman.put("minecraft:concrete_powder.14", "minecraft:red_concrete_powder");
      _snowman.put("minecraft:concrete_powder.15", "minecraft:black_concrete_powder");
      _snowman.put("minecraft:cobblestone_wall.0", "minecraft:cobblestone_wall");
      _snowman.put("minecraft:cobblestone_wall.1", "minecraft:mossy_cobblestone_wall");
      _snowman.put("minecraft:sandstone.0", "minecraft:sandstone");
      _snowman.put("minecraft:sandstone.1", "minecraft:chiseled_sandstone");
      _snowman.put("minecraft:sandstone.2", "minecraft:cut_sandstone");
      _snowman.put("minecraft:red_sandstone.0", "minecraft:red_sandstone");
      _snowman.put("minecraft:red_sandstone.1", "minecraft:chiseled_red_sandstone");
      _snowman.put("minecraft:red_sandstone.2", "minecraft:cut_red_sandstone");
      _snowman.put("minecraft:stonebrick.0", "minecraft:stone_bricks");
      _snowman.put("minecraft:stonebrick.1", "minecraft:mossy_stone_bricks");
      _snowman.put("minecraft:stonebrick.2", "minecraft:cracked_stone_bricks");
      _snowman.put("minecraft:stonebrick.3", "minecraft:chiseled_stone_bricks");
      _snowman.put("minecraft:monster_egg.0", "minecraft:infested_stone");
      _snowman.put("minecraft:monster_egg.1", "minecraft:infested_cobblestone");
      _snowman.put("minecraft:monster_egg.2", "minecraft:infested_stone_bricks");
      _snowman.put("minecraft:monster_egg.3", "minecraft:infested_mossy_stone_bricks");
      _snowman.put("minecraft:monster_egg.4", "minecraft:infested_cracked_stone_bricks");
      _snowman.put("minecraft:monster_egg.5", "minecraft:infested_chiseled_stone_bricks");
      _snowman.put("minecraft:yellow_flower.0", "minecraft:dandelion");
      _snowman.put("minecraft:red_flower.0", "minecraft:poppy");
      _snowman.put("minecraft:red_flower.1", "minecraft:blue_orchid");
      _snowman.put("minecraft:red_flower.2", "minecraft:allium");
      _snowman.put("minecraft:red_flower.3", "minecraft:azure_bluet");
      _snowman.put("minecraft:red_flower.4", "minecraft:red_tulip");
      _snowman.put("minecraft:red_flower.5", "minecraft:orange_tulip");
      _snowman.put("minecraft:red_flower.6", "minecraft:white_tulip");
      _snowman.put("minecraft:red_flower.7", "minecraft:pink_tulip");
      _snowman.put("minecraft:red_flower.8", "minecraft:oxeye_daisy");
      _snowman.put("minecraft:double_plant.0", "minecraft:sunflower");
      _snowman.put("minecraft:double_plant.1", "minecraft:lilac");
      _snowman.put("minecraft:double_plant.2", "minecraft:tall_grass");
      _snowman.put("minecraft:double_plant.3", "minecraft:large_fern");
      _snowman.put("minecraft:double_plant.4", "minecraft:rose_bush");
      _snowman.put("minecraft:double_plant.5", "minecraft:peony");
      _snowman.put("minecraft:deadbush.0", "minecraft:dead_bush");
      _snowman.put("minecraft:tallgrass.0", "minecraft:dead_bush");
      _snowman.put("minecraft:tallgrass.1", "minecraft:grass");
      _snowman.put("minecraft:tallgrass.2", "minecraft:fern");
      _snowman.put("minecraft:sponge.0", "minecraft:sponge");
      _snowman.put("minecraft:sponge.1", "minecraft:wet_sponge");
      _snowman.put("minecraft:purpur_slab.0", "minecraft:purpur_slab");
      _snowman.put("minecraft:stone_slab.0", "minecraft:stone_slab");
      _snowman.put("minecraft:stone_slab.1", "minecraft:sandstone_slab");
      _snowman.put("minecraft:stone_slab.2", "minecraft:petrified_oak_slab");
      _snowman.put("minecraft:stone_slab.3", "minecraft:cobblestone_slab");
      _snowman.put("minecraft:stone_slab.4", "minecraft:brick_slab");
      _snowman.put("minecraft:stone_slab.5", "minecraft:stone_brick_slab");
      _snowman.put("minecraft:stone_slab.6", "minecraft:nether_brick_slab");
      _snowman.put("minecraft:stone_slab.7", "minecraft:quartz_slab");
      _snowman.put("minecraft:stone_slab2.0", "minecraft:red_sandstone_slab");
      _snowman.put("minecraft:wooden_slab.0", "minecraft:oak_slab");
      _snowman.put("minecraft:wooden_slab.1", "minecraft:spruce_slab");
      _snowman.put("minecraft:wooden_slab.2", "minecraft:birch_slab");
      _snowman.put("minecraft:wooden_slab.3", "minecraft:jungle_slab");
      _snowman.put("minecraft:wooden_slab.4", "minecraft:acacia_slab");
      _snowman.put("minecraft:wooden_slab.5", "minecraft:dark_oak_slab");
      _snowman.put("minecraft:coal.0", "minecraft:coal");
      _snowman.put("minecraft:coal.1", "minecraft:charcoal");
      _snowman.put("minecraft:fish.0", "minecraft:cod");
      _snowman.put("minecraft:fish.1", "minecraft:salmon");
      _snowman.put("minecraft:fish.2", "minecraft:clownfish");
      _snowman.put("minecraft:fish.3", "minecraft:pufferfish");
      _snowman.put("minecraft:cooked_fish.0", "minecraft:cooked_cod");
      _snowman.put("minecraft:cooked_fish.1", "minecraft:cooked_salmon");
      _snowman.put("minecraft:skull.0", "minecraft:skeleton_skull");
      _snowman.put("minecraft:skull.1", "minecraft:wither_skeleton_skull");
      _snowman.put("minecraft:skull.2", "minecraft:zombie_head");
      _snowman.put("minecraft:skull.3", "minecraft:player_head");
      _snowman.put("minecraft:skull.4", "minecraft:creeper_head");
      _snowman.put("minecraft:skull.5", "minecraft:dragon_head");
      _snowman.put("minecraft:golden_apple.0", "minecraft:golden_apple");
      _snowman.put("minecraft:golden_apple.1", "minecraft:enchanted_golden_apple");
      _snowman.put("minecraft:fireworks.0", "minecraft:firework_rocket");
      _snowman.put("minecraft:firework_charge.0", "minecraft:firework_star");
      _snowman.put("minecraft:dye.0", "minecraft:ink_sac");
      _snowman.put("minecraft:dye.1", "minecraft:rose_red");
      _snowman.put("minecraft:dye.2", "minecraft:cactus_green");
      _snowman.put("minecraft:dye.3", "minecraft:cocoa_beans");
      _snowman.put("minecraft:dye.4", "minecraft:lapis_lazuli");
      _snowman.put("minecraft:dye.5", "minecraft:purple_dye");
      _snowman.put("minecraft:dye.6", "minecraft:cyan_dye");
      _snowman.put("minecraft:dye.7", "minecraft:light_gray_dye");
      _snowman.put("minecraft:dye.8", "minecraft:gray_dye");
      _snowman.put("minecraft:dye.9", "minecraft:pink_dye");
      _snowman.put("minecraft:dye.10", "minecraft:lime_dye");
      _snowman.put("minecraft:dye.11", "minecraft:dandelion_yellow");
      _snowman.put("minecraft:dye.12", "minecraft:light_blue_dye");
      _snowman.put("minecraft:dye.13", "minecraft:magenta_dye");
      _snowman.put("minecraft:dye.14", "minecraft:orange_dye");
      _snowman.put("minecraft:dye.15", "minecraft:bone_meal");
      _snowman.put("minecraft:silver_shulker_box.0", "minecraft:light_gray_shulker_box");
      _snowman.put("minecraft:fence.0", "minecraft:oak_fence");
      _snowman.put("minecraft:fence_gate.0", "minecraft:oak_fence_gate");
      _snowman.put("minecraft:wooden_door.0", "minecraft:oak_door");
      _snowman.put("minecraft:boat.0", "minecraft:oak_boat");
      _snowman.put("minecraft:lit_pumpkin.0", "minecraft:jack_o_lantern");
      _snowman.put("minecraft:pumpkin.0", "minecraft:carved_pumpkin");
      _snowman.put("minecraft:trapdoor.0", "minecraft:oak_trapdoor");
      _snowman.put("minecraft:nether_brick.0", "minecraft:nether_bricks");
      _snowman.put("minecraft:red_nether_brick.0", "minecraft:red_nether_bricks");
      _snowman.put("minecraft:netherbrick.0", "minecraft:nether_brick");
      _snowman.put("minecraft:wooden_button.0", "minecraft:oak_button");
      _snowman.put("minecraft:wooden_pressure_plate.0", "minecraft:oak_pressure_plate");
      _snowman.put("minecraft:noteblock.0", "minecraft:note_block");
      _snowman.put("minecraft:bed.0", "minecraft:white_bed");
      _snowman.put("minecraft:bed.1", "minecraft:orange_bed");
      _snowman.put("minecraft:bed.2", "minecraft:magenta_bed");
      _snowman.put("minecraft:bed.3", "minecraft:light_blue_bed");
      _snowman.put("minecraft:bed.4", "minecraft:yellow_bed");
      _snowman.put("minecraft:bed.5", "minecraft:lime_bed");
      _snowman.put("minecraft:bed.6", "minecraft:pink_bed");
      _snowman.put("minecraft:bed.7", "minecraft:gray_bed");
      _snowman.put("minecraft:bed.8", "minecraft:light_gray_bed");
      _snowman.put("minecraft:bed.9", "minecraft:cyan_bed");
      _snowman.put("minecraft:bed.10", "minecraft:purple_bed");
      _snowman.put("minecraft:bed.11", "minecraft:blue_bed");
      _snowman.put("minecraft:bed.12", "minecraft:brown_bed");
      _snowman.put("minecraft:bed.13", "minecraft:green_bed");
      _snowman.put("minecraft:bed.14", "minecraft:red_bed");
      _snowman.put("minecraft:bed.15", "minecraft:black_bed");
      _snowman.put("minecraft:banner.15", "minecraft:white_banner");
      _snowman.put("minecraft:banner.14", "minecraft:orange_banner");
      _snowman.put("minecraft:banner.13", "minecraft:magenta_banner");
      _snowman.put("minecraft:banner.12", "minecraft:light_blue_banner");
      _snowman.put("minecraft:banner.11", "minecraft:yellow_banner");
      _snowman.put("minecraft:banner.10", "minecraft:lime_banner");
      _snowman.put("minecraft:banner.9", "minecraft:pink_banner");
      _snowman.put("minecraft:banner.8", "minecraft:gray_banner");
      _snowman.put("minecraft:banner.7", "minecraft:light_gray_banner");
      _snowman.put("minecraft:banner.6", "minecraft:cyan_banner");
      _snowman.put("minecraft:banner.5", "minecraft:purple_banner");
      _snowman.put("minecraft:banner.4", "minecraft:blue_banner");
      _snowman.put("minecraft:banner.3", "minecraft:brown_banner");
      _snowman.put("minecraft:banner.2", "minecraft:green_banner");
      _snowman.put("minecraft:banner.1", "minecraft:red_banner");
      _snowman.put("minecraft:banner.0", "minecraft:black_banner");
      _snowman.put("minecraft:grass.0", "minecraft:grass_block");
      _snowman.put("minecraft:brick_block.0", "minecraft:bricks");
      _snowman.put("minecraft:end_bricks.0", "minecraft:end_stone_bricks");
      _snowman.put("minecraft:golden_rail.0", "minecraft:powered_rail");
      _snowman.put("minecraft:magma.0", "minecraft:magma_block");
      _snowman.put("minecraft:quartz_ore.0", "minecraft:nether_quartz_ore");
      _snowman.put("minecraft:reeds.0", "minecraft:sugar_cane");
      _snowman.put("minecraft:slime.0", "minecraft:slime_block");
      _snowman.put("minecraft:stone_stairs.0", "minecraft:cobblestone_stairs");
      _snowman.put("minecraft:waterlily.0", "minecraft:lily_pad");
      _snowman.put("minecraft:web.0", "minecraft:cobweb");
      _snowman.put("minecraft:snow.0", "minecraft:snow_block");
      _snowman.put("minecraft:snow_layer.0", "minecraft:snow");
      _snowman.put("minecraft:record_11.0", "minecraft:music_disc_11");
      _snowman.put("minecraft:record_13.0", "minecraft:music_disc_13");
      _snowman.put("minecraft:record_blocks.0", "minecraft:music_disc_blocks");
      _snowman.put("minecraft:record_cat.0", "minecraft:music_disc_cat");
      _snowman.put("minecraft:record_chirp.0", "minecraft:music_disc_chirp");
      _snowman.put("minecraft:record_far.0", "minecraft:music_disc_far");
      _snowman.put("minecraft:record_mall.0", "minecraft:music_disc_mall");
      _snowman.put("minecraft:record_mellohi.0", "minecraft:music_disc_mellohi");
      _snowman.put("minecraft:record_stal.0", "minecraft:music_disc_stal");
      _snowman.put("minecraft:record_strad.0", "minecraft:music_disc_strad");
      _snowman.put("minecraft:record_wait.0", "minecraft:music_disc_wait");
      _snowman.put("minecraft:record_ward.0", "minecraft:music_disc_ward");
   });
   private static final Set<String> ORIGINAL_ITEM_NAMES = FLATTENING_MAP.keySet().stream().map(_snowman -> _snowman.substring(0, _snowman.indexOf(46))).collect(Collectors.toSet());
   private static final Set<String> DAMAGEABLE_ITEMS = Sets.newHashSet(
      new String[]{
         "minecraft:bow",
         "minecraft:carrot_on_a_stick",
         "minecraft:chainmail_boots",
         "minecraft:chainmail_chestplate",
         "minecraft:chainmail_helmet",
         "minecraft:chainmail_leggings",
         "minecraft:diamond_axe",
         "minecraft:diamond_boots",
         "minecraft:diamond_chestplate",
         "minecraft:diamond_helmet",
         "minecraft:diamond_hoe",
         "minecraft:diamond_leggings",
         "minecraft:diamond_pickaxe",
         "minecraft:diamond_shovel",
         "minecraft:diamond_sword",
         "minecraft:elytra",
         "minecraft:fishing_rod",
         "minecraft:flint_and_steel",
         "minecraft:golden_axe",
         "minecraft:golden_boots",
         "minecraft:golden_chestplate",
         "minecraft:golden_helmet",
         "minecraft:golden_hoe",
         "minecraft:golden_leggings",
         "minecraft:golden_pickaxe",
         "minecraft:golden_shovel",
         "minecraft:golden_sword",
         "minecraft:iron_axe",
         "minecraft:iron_boots",
         "minecraft:iron_chestplate",
         "minecraft:iron_helmet",
         "minecraft:iron_hoe",
         "minecraft:iron_leggings",
         "minecraft:iron_pickaxe",
         "minecraft:iron_shovel",
         "minecraft:iron_sword",
         "minecraft:leather_boots",
         "minecraft:leather_chestplate",
         "minecraft:leather_helmet",
         "minecraft:leather_leggings",
         "minecraft:shears",
         "minecraft:shield",
         "minecraft:stone_axe",
         "minecraft:stone_hoe",
         "minecraft:stone_pickaxe",
         "minecraft:stone_shovel",
         "minecraft:stone_sword",
         "minecraft:wooden_axe",
         "minecraft:wooden_hoe",
         "minecraft:wooden_pickaxe",
         "minecraft:wooden_shovel",
         "minecraft:wooden_sword"
      }
   );

   public ItemInstanceTheFlatteningFix(Schema outputSchema, boolean changesType) {
      super(outputSchema, changesType);
   }

   public TypeRewriteRule makeRule() {
      Type<?> _snowman = this.getInputSchema().getType(TypeReferences.ITEM_STACK);
      OpticFinder<Pair<String, String>> _snowmanx = DSL.fieldFinder(
         "id", DSL.named(TypeReferences.ITEM_NAME.typeName(), IdentifierNormalizingSchema.getIdentifierType())
      );
      OpticFinder<?> _snowmanxx = _snowman.findField("tag");
      return this.fixTypeEverywhereTyped("ItemInstanceTheFlatteningFix", _snowman, _snowmanxxx -> {
         Optional<Pair<String, String>> _snowmanxxxx = _snowmanxxx.getOptional(_snowman);
         if (!_snowmanxxxx.isPresent()) {
            return _snowmanxxx;
         } else {
            Typed<?> _snowmanx = _snowmanxxx;
            Dynamic<?> _snowmanxxx = (Dynamic<?>)_snowmanxxx.get(DSL.remainderFinder());
            int _snowmanxxxx = _snowmanxxx.get("Damage").asInt(0);
            String _snowmanxxxxx = getItem((String)_snowmanxxxx.get().getSecond(), _snowmanxxxx);
            if (_snowmanxxxxx != null) {
               _snowmanx = _snowmanxxx.set(_snowman, Pair.of(TypeReferences.ITEM_NAME.typeName(), _snowmanxxxxx));
            }

            if (DAMAGEABLE_ITEMS.contains(_snowmanxxxx.get().getSecond())) {
               Typed<?> _snowmanxxxxxx = _snowmanxxx.getOrCreateTyped(_snowman);
               Dynamic<?> _snowmanxxxxxxx = (Dynamic<?>)_snowmanxxxxxx.get(DSL.remainderFinder());
               _snowmanxxxxxxx = _snowmanxxxxxxx.set("Damage", _snowmanxxxxxxx.createInt(_snowmanxxxx));
               _snowmanx = _snowmanx.set(_snowman, _snowmanxxxxxx.set(DSL.remainderFinder(), _snowmanxxxxxxx));
            }

            return _snowmanx.set(DSL.remainderFinder(), _snowmanxxx.remove("Damage"));
         }
      });
   }

   @Nullable
   public static String getItem(@Nullable String originalName, int damage) {
      if (ORIGINAL_ITEM_NAMES.contains(originalName)) {
         String _snowman = FLATTENING_MAP.get(originalName + '.' + damage);
         return _snowman == null ? FLATTENING_MAP.get(originalName + ".0") : _snowman;
      } else {
         return null;
      }
   }
}
