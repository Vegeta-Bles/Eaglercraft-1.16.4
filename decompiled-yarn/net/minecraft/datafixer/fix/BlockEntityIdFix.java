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

public class BlockEntityIdFix extends DataFix {
   private static final Map<String, String> RENAMED_BLOCK_ENTITIES = (Map<String, String>)DataFixUtils.make(Maps.newHashMap(), _snowman -> {
      _snowman.put("Airportal", "minecraft:end_portal");
      _snowman.put("Banner", "minecraft:banner");
      _snowman.put("Beacon", "minecraft:beacon");
      _snowman.put("Cauldron", "minecraft:brewing_stand");
      _snowman.put("Chest", "minecraft:chest");
      _snowman.put("Comparator", "minecraft:comparator");
      _snowman.put("Control", "minecraft:command_block");
      _snowman.put("DLDetector", "minecraft:daylight_detector");
      _snowman.put("Dropper", "minecraft:dropper");
      _snowman.put("EnchantTable", "minecraft:enchanting_table");
      _snowman.put("EndGateway", "minecraft:end_gateway");
      _snowman.put("EnderChest", "minecraft:ender_chest");
      _snowman.put("FlowerPot", "minecraft:flower_pot");
      _snowman.put("Furnace", "minecraft:furnace");
      _snowman.put("Hopper", "minecraft:hopper");
      _snowman.put("MobSpawner", "minecraft:mob_spawner");
      _snowman.put("Music", "minecraft:noteblock");
      _snowman.put("Piston", "minecraft:piston");
      _snowman.put("RecordPlayer", "minecraft:jukebox");
      _snowman.put("Sign", "minecraft:sign");
      _snowman.put("Skull", "minecraft:skull");
      _snowman.put("Structure", "minecraft:structure_block");
      _snowman.put("Trap", "minecraft:dispenser");
   });

   public BlockEntityIdFix(Schema outputSchema, boolean changesType) {
      super(outputSchema, changesType);
   }

   public TypeRewriteRule makeRule() {
      Type<?> _snowman = this.getInputSchema().getType(TypeReferences.ITEM_STACK);
      Type<?> _snowmanx = this.getOutputSchema().getType(TypeReferences.ITEM_STACK);
      TaggedChoiceType<String> _snowmanxx = this.getInputSchema().findChoiceType(TypeReferences.BLOCK_ENTITY);
      TaggedChoiceType<String> _snowmanxxx = this.getOutputSchema().findChoiceType(TypeReferences.BLOCK_ENTITY);
      return TypeRewriteRule.seq(
         this.convertUnchecked("item stack block entity name hook converter", _snowman, _snowmanx),
         this.fixTypeEverywhere(
            "BlockEntityIdFix", _snowmanxx, _snowmanxxx, _snowmanxxxx -> _snowmanxxxxx -> _snowmanxxxxx.mapFirst(_snowmanxxxxxx -> RENAMED_BLOCK_ENTITIES.getOrDefault(_snowmanxxxxxx, _snowmanxxxxxx))
         )
      );
   }
}
