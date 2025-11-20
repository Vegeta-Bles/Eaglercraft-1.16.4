package net.minecraft.datafixer.fix;

import com.google.common.collect.Maps;
import com.mojang.datafixers.DSL;
import com.mojang.datafixers.DataFix;
import com.mojang.datafixers.DataFixUtils;
import com.mojang.datafixers.TypeRewriteRule;
import com.mojang.datafixers.Typed;
import com.mojang.datafixers.schemas.Schema;
import com.mojang.datafixers.types.Type;
import com.mojang.datafixers.util.Either;
import com.mojang.datafixers.util.Pair;
import com.mojang.datafixers.util.Unit;
import com.mojang.serialization.Dynamic;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import net.minecraft.datafixer.TypeReferences;
import net.minecraft.datafixer.schema.IdentifierNormalizingSchema;

public class EntityBlockStateFix extends DataFix {
   private static final Map<String, Integer> BLOCK_NAME_TO_ID = (Map<String, Integer>)DataFixUtils.make(Maps.newHashMap(), _snowman -> {
      _snowman.put("minecraft:air", 0);
      _snowman.put("minecraft:stone", 1);
      _snowman.put("minecraft:grass", 2);
      _snowman.put("minecraft:dirt", 3);
      _snowman.put("minecraft:cobblestone", 4);
      _snowman.put("minecraft:planks", 5);
      _snowman.put("minecraft:sapling", 6);
      _snowman.put("minecraft:bedrock", 7);
      _snowman.put("minecraft:flowing_water", 8);
      _snowman.put("minecraft:water", 9);
      _snowman.put("minecraft:flowing_lava", 10);
      _snowman.put("minecraft:lava", 11);
      _snowman.put("minecraft:sand", 12);
      _snowman.put("minecraft:gravel", 13);
      _snowman.put("minecraft:gold_ore", 14);
      _snowman.put("minecraft:iron_ore", 15);
      _snowman.put("minecraft:coal_ore", 16);
      _snowman.put("minecraft:log", 17);
      _snowman.put("minecraft:leaves", 18);
      _snowman.put("minecraft:sponge", 19);
      _snowman.put("minecraft:glass", 20);
      _snowman.put("minecraft:lapis_ore", 21);
      _snowman.put("minecraft:lapis_block", 22);
      _snowman.put("minecraft:dispenser", 23);
      _snowman.put("minecraft:sandstone", 24);
      _snowman.put("minecraft:noteblock", 25);
      _snowman.put("minecraft:bed", 26);
      _snowman.put("minecraft:golden_rail", 27);
      _snowman.put("minecraft:detector_rail", 28);
      _snowman.put("minecraft:sticky_piston", 29);
      _snowman.put("minecraft:web", 30);
      _snowman.put("minecraft:tallgrass", 31);
      _snowman.put("minecraft:deadbush", 32);
      _snowman.put("minecraft:piston", 33);
      _snowman.put("minecraft:piston_head", 34);
      _snowman.put("minecraft:wool", 35);
      _snowman.put("minecraft:piston_extension", 36);
      _snowman.put("minecraft:yellow_flower", 37);
      _snowman.put("minecraft:red_flower", 38);
      _snowman.put("minecraft:brown_mushroom", 39);
      _snowman.put("minecraft:red_mushroom", 40);
      _snowman.put("minecraft:gold_block", 41);
      _snowman.put("minecraft:iron_block", 42);
      _snowman.put("minecraft:double_stone_slab", 43);
      _snowman.put("minecraft:stone_slab", 44);
      _snowman.put("minecraft:brick_block", 45);
      _snowman.put("minecraft:tnt", 46);
      _snowman.put("minecraft:bookshelf", 47);
      _snowman.put("minecraft:mossy_cobblestone", 48);
      _snowman.put("minecraft:obsidian", 49);
      _snowman.put("minecraft:torch", 50);
      _snowman.put("minecraft:fire", 51);
      _snowman.put("minecraft:mob_spawner", 52);
      _snowman.put("minecraft:oak_stairs", 53);
      _snowman.put("minecraft:chest", 54);
      _snowman.put("minecraft:redstone_wire", 55);
      _snowman.put("minecraft:diamond_ore", 56);
      _snowman.put("minecraft:diamond_block", 57);
      _snowman.put("minecraft:crafting_table", 58);
      _snowman.put("minecraft:wheat", 59);
      _snowman.put("minecraft:farmland", 60);
      _snowman.put("minecraft:furnace", 61);
      _snowman.put("minecraft:lit_furnace", 62);
      _snowman.put("minecraft:standing_sign", 63);
      _snowman.put("minecraft:wooden_door", 64);
      _snowman.put("minecraft:ladder", 65);
      _snowman.put("minecraft:rail", 66);
      _snowman.put("minecraft:stone_stairs", 67);
      _snowman.put("minecraft:wall_sign", 68);
      _snowman.put("minecraft:lever", 69);
      _snowman.put("minecraft:stone_pressure_plate", 70);
      _snowman.put("minecraft:iron_door", 71);
      _snowman.put("minecraft:wooden_pressure_plate", 72);
      _snowman.put("minecraft:redstone_ore", 73);
      _snowman.put("minecraft:lit_redstone_ore", 74);
      _snowman.put("minecraft:unlit_redstone_torch", 75);
      _snowman.put("minecraft:redstone_torch", 76);
      _snowman.put("minecraft:stone_button", 77);
      _snowman.put("minecraft:snow_layer", 78);
      _snowman.put("minecraft:ice", 79);
      _snowman.put("minecraft:snow", 80);
      _snowman.put("minecraft:cactus", 81);
      _snowman.put("minecraft:clay", 82);
      _snowman.put("minecraft:reeds", 83);
      _snowman.put("minecraft:jukebox", 84);
      _snowman.put("minecraft:fence", 85);
      _snowman.put("minecraft:pumpkin", 86);
      _snowman.put("minecraft:netherrack", 87);
      _snowman.put("minecraft:soul_sand", 88);
      _snowman.put("minecraft:glowstone", 89);
      _snowman.put("minecraft:portal", 90);
      _snowman.put("minecraft:lit_pumpkin", 91);
      _snowman.put("minecraft:cake", 92);
      _snowman.put("minecraft:unpowered_repeater", 93);
      _snowman.put("minecraft:powered_repeater", 94);
      _snowman.put("minecraft:stained_glass", 95);
      _snowman.put("minecraft:trapdoor", 96);
      _snowman.put("minecraft:monster_egg", 97);
      _snowman.put("minecraft:stonebrick", 98);
      _snowman.put("minecraft:brown_mushroom_block", 99);
      _snowman.put("minecraft:red_mushroom_block", 100);
      _snowman.put("minecraft:iron_bars", 101);
      _snowman.put("minecraft:glass_pane", 102);
      _snowman.put("minecraft:melon_block", 103);
      _snowman.put("minecraft:pumpkin_stem", 104);
      _snowman.put("minecraft:melon_stem", 105);
      _snowman.put("minecraft:vine", 106);
      _snowman.put("minecraft:fence_gate", 107);
      _snowman.put("minecraft:brick_stairs", 108);
      _snowman.put("minecraft:stone_brick_stairs", 109);
      _snowman.put("minecraft:mycelium", 110);
      _snowman.put("minecraft:waterlily", 111);
      _snowman.put("minecraft:nether_brick", 112);
      _snowman.put("minecraft:nether_brick_fence", 113);
      _snowman.put("minecraft:nether_brick_stairs", 114);
      _snowman.put("minecraft:nether_wart", 115);
      _snowman.put("minecraft:enchanting_table", 116);
      _snowman.put("minecraft:brewing_stand", 117);
      _snowman.put("minecraft:cauldron", 118);
      _snowman.put("minecraft:end_portal", 119);
      _snowman.put("minecraft:end_portal_frame", 120);
      _snowman.put("minecraft:end_stone", 121);
      _snowman.put("minecraft:dragon_egg", 122);
      _snowman.put("minecraft:redstone_lamp", 123);
      _snowman.put("minecraft:lit_redstone_lamp", 124);
      _snowman.put("minecraft:double_wooden_slab", 125);
      _snowman.put("minecraft:wooden_slab", 126);
      _snowman.put("minecraft:cocoa", 127);
      _snowman.put("minecraft:sandstone_stairs", 128);
      _snowman.put("minecraft:emerald_ore", 129);
      _snowman.put("minecraft:ender_chest", 130);
      _snowman.put("minecraft:tripwire_hook", 131);
      _snowman.put("minecraft:tripwire", 132);
      _snowman.put("minecraft:emerald_block", 133);
      _snowman.put("minecraft:spruce_stairs", 134);
      _snowman.put("minecraft:birch_stairs", 135);
      _snowman.put("minecraft:jungle_stairs", 136);
      _snowman.put("minecraft:command_block", 137);
      _snowman.put("minecraft:beacon", 138);
      _snowman.put("minecraft:cobblestone_wall", 139);
      _snowman.put("minecraft:flower_pot", 140);
      _snowman.put("minecraft:carrots", 141);
      _snowman.put("minecraft:potatoes", 142);
      _snowman.put("minecraft:wooden_button", 143);
      _snowman.put("minecraft:skull", 144);
      _snowman.put("minecraft:anvil", 145);
      _snowman.put("minecraft:trapped_chest", 146);
      _snowman.put("minecraft:light_weighted_pressure_plate", 147);
      _snowman.put("minecraft:heavy_weighted_pressure_plate", 148);
      _snowman.put("minecraft:unpowered_comparator", 149);
      _snowman.put("minecraft:powered_comparator", 150);
      _snowman.put("minecraft:daylight_detector", 151);
      _snowman.put("minecraft:redstone_block", 152);
      _snowman.put("minecraft:quartz_ore", 153);
      _snowman.put("minecraft:hopper", 154);
      _snowman.put("minecraft:quartz_block", 155);
      _snowman.put("minecraft:quartz_stairs", 156);
      _snowman.put("minecraft:activator_rail", 157);
      _snowman.put("minecraft:dropper", 158);
      _snowman.put("minecraft:stained_hardened_clay", 159);
      _snowman.put("minecraft:stained_glass_pane", 160);
      _snowman.put("minecraft:leaves2", 161);
      _snowman.put("minecraft:log2", 162);
      _snowman.put("minecraft:acacia_stairs", 163);
      _snowman.put("minecraft:dark_oak_stairs", 164);
      _snowman.put("minecraft:slime", 165);
      _snowman.put("minecraft:barrier", 166);
      _snowman.put("minecraft:iron_trapdoor", 167);
      _snowman.put("minecraft:prismarine", 168);
      _snowman.put("minecraft:sea_lantern", 169);
      _snowman.put("minecraft:hay_block", 170);
      _snowman.put("minecraft:carpet", 171);
      _snowman.put("minecraft:hardened_clay", 172);
      _snowman.put("minecraft:coal_block", 173);
      _snowman.put("minecraft:packed_ice", 174);
      _snowman.put("minecraft:double_plant", 175);
      _snowman.put("minecraft:standing_banner", 176);
      _snowman.put("minecraft:wall_banner", 177);
      _snowman.put("minecraft:daylight_detector_inverted", 178);
      _snowman.put("minecraft:red_sandstone", 179);
      _snowman.put("minecraft:red_sandstone_stairs", 180);
      _snowman.put("minecraft:double_stone_slab2", 181);
      _snowman.put("minecraft:stone_slab2", 182);
      _snowman.put("minecraft:spruce_fence_gate", 183);
      _snowman.put("minecraft:birch_fence_gate", 184);
      _snowman.put("minecraft:jungle_fence_gate", 185);
      _snowman.put("minecraft:dark_oak_fence_gate", 186);
      _snowman.put("minecraft:acacia_fence_gate", 187);
      _snowman.put("minecraft:spruce_fence", 188);
      _snowman.put("minecraft:birch_fence", 189);
      _snowman.put("minecraft:jungle_fence", 190);
      _snowman.put("minecraft:dark_oak_fence", 191);
      _snowman.put("minecraft:acacia_fence", 192);
      _snowman.put("minecraft:spruce_door", 193);
      _snowman.put("minecraft:birch_door", 194);
      _snowman.put("minecraft:jungle_door", 195);
      _snowman.put("minecraft:acacia_door", 196);
      _snowman.put("minecraft:dark_oak_door", 197);
      _snowman.put("minecraft:end_rod", 198);
      _snowman.put("minecraft:chorus_plant", 199);
      _snowman.put("minecraft:chorus_flower", 200);
      _snowman.put("minecraft:purpur_block", 201);
      _snowman.put("minecraft:purpur_pillar", 202);
      _snowman.put("minecraft:purpur_stairs", 203);
      _snowman.put("minecraft:purpur_double_slab", 204);
      _snowman.put("minecraft:purpur_slab", 205);
      _snowman.put("minecraft:end_bricks", 206);
      _snowman.put("minecraft:beetroots", 207);
      _snowman.put("minecraft:grass_path", 208);
      _snowman.put("minecraft:end_gateway", 209);
      _snowman.put("minecraft:repeating_command_block", 210);
      _snowman.put("minecraft:chain_command_block", 211);
      _snowman.put("minecraft:frosted_ice", 212);
      _snowman.put("minecraft:magma", 213);
      _snowman.put("minecraft:nether_wart_block", 214);
      _snowman.put("minecraft:red_nether_brick", 215);
      _snowman.put("minecraft:bone_block", 216);
      _snowman.put("minecraft:structure_void", 217);
      _snowman.put("minecraft:observer", 218);
      _snowman.put("minecraft:white_shulker_box", 219);
      _snowman.put("minecraft:orange_shulker_box", 220);
      _snowman.put("minecraft:magenta_shulker_box", 221);
      _snowman.put("minecraft:light_blue_shulker_box", 222);
      _snowman.put("minecraft:yellow_shulker_box", 223);
      _snowman.put("minecraft:lime_shulker_box", 224);
      _snowman.put("minecraft:pink_shulker_box", 225);
      _snowman.put("minecraft:gray_shulker_box", 226);
      _snowman.put("minecraft:silver_shulker_box", 227);
      _snowman.put("minecraft:cyan_shulker_box", 228);
      _snowman.put("minecraft:purple_shulker_box", 229);
      _snowman.put("minecraft:blue_shulker_box", 230);
      _snowman.put("minecraft:brown_shulker_box", 231);
      _snowman.put("minecraft:green_shulker_box", 232);
      _snowman.put("minecraft:red_shulker_box", 233);
      _snowman.put("minecraft:black_shulker_box", 234);
      _snowman.put("minecraft:white_glazed_terracotta", 235);
      _snowman.put("minecraft:orange_glazed_terracotta", 236);
      _snowman.put("minecraft:magenta_glazed_terracotta", 237);
      _snowman.put("minecraft:light_blue_glazed_terracotta", 238);
      _snowman.put("minecraft:yellow_glazed_terracotta", 239);
      _snowman.put("minecraft:lime_glazed_terracotta", 240);
      _snowman.put("minecraft:pink_glazed_terracotta", 241);
      _snowman.put("minecraft:gray_glazed_terracotta", 242);
      _snowman.put("minecraft:silver_glazed_terracotta", 243);
      _snowman.put("minecraft:cyan_glazed_terracotta", 244);
      _snowman.put("minecraft:purple_glazed_terracotta", 245);
      _snowman.put("minecraft:blue_glazed_terracotta", 246);
      _snowman.put("minecraft:brown_glazed_terracotta", 247);
      _snowman.put("minecraft:green_glazed_terracotta", 248);
      _snowman.put("minecraft:red_glazed_terracotta", 249);
      _snowman.put("minecraft:black_glazed_terracotta", 250);
      _snowman.put("minecraft:concrete", 251);
      _snowman.put("minecraft:concrete_powder", 252);
      _snowman.put("minecraft:structure_block", 255);
   });

   public EntityBlockStateFix(Schema outputSchema, boolean changesType) {
      super(outputSchema, changesType);
   }

   public static int getNumericalBlockId(String blockId) {
      Integer _snowman = BLOCK_NAME_TO_ID.get(blockId);
      return _snowman == null ? 0 : _snowman;
   }

   public TypeRewriteRule makeRule() {
      Schema _snowman = this.getInputSchema();
      Schema _snowmanx = this.getOutputSchema();
      Function<Typed<?>, Typed<?>> _snowmanxx = _snowmanxxx -> this.mergeIdAndData(_snowmanxxx, "DisplayTile", "DisplayData", "DisplayState");
      Function<Typed<?>, Typed<?>> _snowmanxxx = _snowmanxxxx -> this.mergeIdAndData(_snowmanxxxx, "inTile", "inData", "inBlockState");
      Type<Pair<Either<Pair<String, Either<Integer, String>>, Unit>, Dynamic<?>>> _snowmanxxxx = DSL.and(
         DSL.optional(
            DSL.field("inTile", DSL.named(TypeReferences.BLOCK_NAME.typeName(), DSL.or(DSL.intType(), IdentifierNormalizingSchema.getIdentifierType())))
         ),
         DSL.remainderType()
      );
      Function<Typed<?>, Typed<?>> _snowmanxxxxx = _snowmanxxxxxx -> _snowmanxxxxxx.update(_snowman.finder(), DSL.remainderType(), Pair::getSecond);
      return this.fixTypeEverywhereTyped("EntityBlockStateFix", _snowman.getType(TypeReferences.ENTITY), _snowmanx.getType(TypeReferences.ENTITY), _snowmanxxxxxx -> {
         _snowmanxxxxxx = this.useFunction(_snowmanxxxxxx, "minecraft:falling_block", this::method_15695);
         _snowmanxxxxxx = this.useFunction(_snowmanxxxxxx, "minecraft:enderman", _snowmanxxxxxxx -> this.mergeIdAndData(_snowmanxxxxxxx, "carried", "carriedData", "carriedBlockState"));
         _snowmanxxxxxx = this.useFunction(_snowmanxxxxxx, "minecraft:arrow", _snowman);
         _snowmanxxxxxx = this.useFunction(_snowmanxxxxxx, "minecraft:spectral_arrow", _snowman);
         _snowmanxxxxxx = this.useFunction(_snowmanxxxxxx, "minecraft:egg", _snowman);
         _snowmanxxxxxx = this.useFunction(_snowmanxxxxxx, "minecraft:ender_pearl", _snowman);
         _snowmanxxxxxx = this.useFunction(_snowmanxxxxxx, "minecraft:fireball", _snowman);
         _snowmanxxxxxx = this.useFunction(_snowmanxxxxxx, "minecraft:potion", _snowman);
         _snowmanxxxxxx = this.useFunction(_snowmanxxxxxx, "minecraft:small_fireball", _snowman);
         _snowmanxxxxxx = this.useFunction(_snowmanxxxxxx, "minecraft:snowball", _snowman);
         _snowmanxxxxxx = this.useFunction(_snowmanxxxxxx, "minecraft:wither_skull", _snowman);
         _snowmanxxxxxx = this.useFunction(_snowmanxxxxxx, "minecraft:xp_bottle", _snowman);
         _snowmanxxxxxx = this.useFunction(_snowmanxxxxxx, "minecraft:commandblock_minecart", _snowman);
         _snowmanxxxxxx = this.useFunction(_snowmanxxxxxx, "minecraft:minecart", _snowman);
         _snowmanxxxxxx = this.useFunction(_snowmanxxxxxx, "minecraft:chest_minecart", _snowman);
         _snowmanxxxxxx = this.useFunction(_snowmanxxxxxx, "minecraft:furnace_minecart", _snowman);
         _snowmanxxxxxx = this.useFunction(_snowmanxxxxxx, "minecraft:tnt_minecart", _snowman);
         _snowmanxxxxxx = this.useFunction(_snowmanxxxxxx, "minecraft:hopper_minecart", _snowman);
         return this.useFunction(_snowmanxxxxxx, "minecraft:spawner_minecart", _snowman);
      });
   }

   private Typed<?> method_15695(Typed<?> _snowman) {
      Type<Either<Pair<String, Either<Integer, String>>, Unit>> _snowmanx = DSL.optional(
         DSL.field("Block", DSL.named(TypeReferences.BLOCK_NAME.typeName(), DSL.or(DSL.intType(), IdentifierNormalizingSchema.getIdentifierType())))
      );
      Type<Either<Pair<String, Dynamic<?>>, Unit>> _snowmanxx = DSL.optional(
         DSL.field("BlockState", DSL.named(TypeReferences.BLOCK_STATE.typeName(), DSL.remainderType()))
      );
      Dynamic<?> _snowmanxxx = (Dynamic<?>)_snowman.get(DSL.remainderFinder());
      return _snowman.update(
            _snowmanx.finder(),
            _snowmanxx,
            _snowmanxxxx -> {
               int _snowmanxxxx = (Integer)_snowmanxxxx.map(
                  _snowmanxxxxx -> (Integer)((Either)_snowmanxxxxx.getSecond()).map(_snowmanxxxxxx -> _snowmanxxxxxx, EntityBlockStateFix::getNumericalBlockId), _snowmanxxxxxxx -> {
                     Optional<Number> _snowmanxx = _snowman.get("TileID").asNumber().result();
                     return _snowmanxx.map(Number::intValue).orElseGet(() -> _snowman.get("Tile").asByte((byte)0) & 0xFF);
                  }
               );
               int _snowmanxxxxx = _snowman.get("Data").asInt(0) & 15;
               return Either.left(Pair.of(TypeReferences.BLOCK_STATE.typeName(), BlockStateFlattening.lookupState(_snowmanxxxx << 4 | _snowmanxxxxx)));
            }
         )
         .set(DSL.remainderFinder(), _snowmanxxx.remove("Data").remove("TileID").remove("Tile"));
   }

   private Typed<?> mergeIdAndData(Typed<?> _snowman, String oldIdKey, String oldDataKey, String newStateKey) {
      Type<Pair<String, Either<Integer, String>>> _snowmanx = DSL.field(
         oldIdKey, DSL.named(TypeReferences.BLOCK_NAME.typeName(), DSL.or(DSL.intType(), IdentifierNormalizingSchema.getIdentifierType()))
      );
      Type<Pair<String, Dynamic<?>>> _snowmanxx = DSL.field(newStateKey, DSL.named(TypeReferences.BLOCK_STATE.typeName(), DSL.remainderType()));
      Dynamic<?> _snowmanxxx = (Dynamic<?>)_snowman.getOrCreate(DSL.remainderFinder());
      return _snowman.update(_snowmanx.finder(), _snowmanxx, _snowmanxxxx -> {
         int _snowmanxxxx = (Integer)((Either)_snowmanxxxx.getSecond()).map(_snowmanxxxxxx -> _snowmanxxxxxx, EntityBlockStateFix::getNumericalBlockId);
         int _snowmanxxxxx = _snowman.get(oldDataKey).asInt(0) & 15;
         return Pair.of(TypeReferences.BLOCK_STATE.typeName(), BlockStateFlattening.lookupState(_snowmanxxxx << 4 | _snowmanxxxxx));
      }).set(DSL.remainderFinder(), _snowmanxxx.remove(oldDataKey));
   }

   private Typed<?> useFunction(Typed<?> _snowman, String entityId, Function<Typed<?>, Typed<?>> function) {
      Type<?> _snowmanx = this.getInputSchema().getChoiceType(TypeReferences.ENTITY, entityId);
      Type<?> _snowmanxx = this.getOutputSchema().getChoiceType(TypeReferences.ENTITY, entityId);
      return _snowman.updateTyped(DSL.namedChoice(entityId, _snowmanx), _snowmanxx, function);
   }
}
