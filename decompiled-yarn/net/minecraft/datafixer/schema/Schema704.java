package net.minecraft.datafixer.schema;

import com.google.common.collect.Maps;
import com.mojang.datafixers.DSL;
import com.mojang.datafixers.DataFixUtils;
import com.mojang.datafixers.DSL.TypeReference;
import com.mojang.datafixers.schemas.Schema;
import com.mojang.datafixers.types.Type;
import com.mojang.datafixers.types.templates.TypeTemplate;
import com.mojang.datafixers.types.templates.Hook.HookFunction;
import com.mojang.serialization.Dynamic;
import com.mojang.serialization.DynamicOps;
import java.util.Map;
import java.util.Objects;
import java.util.function.Supplier;
import net.minecraft.datafixer.TypeReferences;

public class Schema704 extends Schema {
   protected static final Map<String, String> BLOCK_RENAMES = (Map<String, String>)DataFixUtils.make(Maps.newHashMap(), _snowman -> {
      _snowman.put("minecraft:furnace", "minecraft:furnace");
      _snowman.put("minecraft:lit_furnace", "minecraft:furnace");
      _snowman.put("minecraft:chest", "minecraft:chest");
      _snowman.put("minecraft:trapped_chest", "minecraft:chest");
      _snowman.put("minecraft:ender_chest", "minecraft:ender_chest");
      _snowman.put("minecraft:jukebox", "minecraft:jukebox");
      _snowman.put("minecraft:dispenser", "minecraft:dispenser");
      _snowman.put("minecraft:dropper", "minecraft:dropper");
      _snowman.put("minecraft:sign", "minecraft:sign");
      _snowman.put("minecraft:mob_spawner", "minecraft:mob_spawner");
      _snowman.put("minecraft:noteblock", "minecraft:noteblock");
      _snowman.put("minecraft:brewing_stand", "minecraft:brewing_stand");
      _snowman.put("minecraft:enhanting_table", "minecraft:enchanting_table");
      _snowman.put("minecraft:command_block", "minecraft:command_block");
      _snowman.put("minecraft:beacon", "minecraft:beacon");
      _snowman.put("minecraft:skull", "minecraft:skull");
      _snowman.put("minecraft:daylight_detector", "minecraft:daylight_detector");
      _snowman.put("minecraft:hopper", "minecraft:hopper");
      _snowman.put("minecraft:banner", "minecraft:banner");
      _snowman.put("minecraft:flower_pot", "minecraft:flower_pot");
      _snowman.put("minecraft:repeating_command_block", "minecraft:command_block");
      _snowman.put("minecraft:chain_command_block", "minecraft:command_block");
      _snowman.put("minecraft:shulker_box", "minecraft:shulker_box");
      _snowman.put("minecraft:white_shulker_box", "minecraft:shulker_box");
      _snowman.put("minecraft:orange_shulker_box", "minecraft:shulker_box");
      _snowman.put("minecraft:magenta_shulker_box", "minecraft:shulker_box");
      _snowman.put("minecraft:light_blue_shulker_box", "minecraft:shulker_box");
      _snowman.put("minecraft:yellow_shulker_box", "minecraft:shulker_box");
      _snowman.put("minecraft:lime_shulker_box", "minecraft:shulker_box");
      _snowman.put("minecraft:pink_shulker_box", "minecraft:shulker_box");
      _snowman.put("minecraft:gray_shulker_box", "minecraft:shulker_box");
      _snowman.put("minecraft:silver_shulker_box", "minecraft:shulker_box");
      _snowman.put("minecraft:cyan_shulker_box", "minecraft:shulker_box");
      _snowman.put("minecraft:purple_shulker_box", "minecraft:shulker_box");
      _snowman.put("minecraft:blue_shulker_box", "minecraft:shulker_box");
      _snowman.put("minecraft:brown_shulker_box", "minecraft:shulker_box");
      _snowman.put("minecraft:green_shulker_box", "minecraft:shulker_box");
      _snowman.put("minecraft:red_shulker_box", "minecraft:shulker_box");
      _snowman.put("minecraft:black_shulker_box", "minecraft:shulker_box");
      _snowman.put("minecraft:bed", "minecraft:bed");
      _snowman.put("minecraft:light_gray_shulker_box", "minecraft:shulker_box");
      _snowman.put("minecraft:banner", "minecraft:banner");
      _snowman.put("minecraft:white_banner", "minecraft:banner");
      _snowman.put("minecraft:orange_banner", "minecraft:banner");
      _snowman.put("minecraft:magenta_banner", "minecraft:banner");
      _snowman.put("minecraft:light_blue_banner", "minecraft:banner");
      _snowman.put("minecraft:yellow_banner", "minecraft:banner");
      _snowman.put("minecraft:lime_banner", "minecraft:banner");
      _snowman.put("minecraft:pink_banner", "minecraft:banner");
      _snowman.put("minecraft:gray_banner", "minecraft:banner");
      _snowman.put("minecraft:silver_banner", "minecraft:banner");
      _snowman.put("minecraft:cyan_banner", "minecraft:banner");
      _snowman.put("minecraft:purple_banner", "minecraft:banner");
      _snowman.put("minecraft:blue_banner", "minecraft:banner");
      _snowman.put("minecraft:brown_banner", "minecraft:banner");
      _snowman.put("minecraft:green_banner", "minecraft:banner");
      _snowman.put("minecraft:red_banner", "minecraft:banner");
      _snowman.put("minecraft:black_banner", "minecraft:banner");
      _snowman.put("minecraft:standing_sign", "minecraft:sign");
      _snowman.put("minecraft:wall_sign", "minecraft:sign");
      _snowman.put("minecraft:piston_head", "minecraft:piston");
      _snowman.put("minecraft:daylight_detector_inverted", "minecraft:daylight_detector");
      _snowman.put("minecraft:unpowered_comparator", "minecraft:comparator");
      _snowman.put("minecraft:powered_comparator", "minecraft:comparator");
      _snowman.put("minecraft:wall_banner", "minecraft:banner");
      _snowman.put("minecraft:standing_banner", "minecraft:banner");
      _snowman.put("minecraft:structure_block", "minecraft:structure_block");
      _snowman.put("minecraft:end_portal", "minecraft:end_portal");
      _snowman.put("minecraft:end_gateway", "minecraft:end_gateway");
      _snowman.put("minecraft:sign", "minecraft:sign");
      _snowman.put("minecraft:shield", "minecraft:banner");
   });
   protected static final HookFunction field_5745 = new HookFunction() {
      public <T> T apply(DynamicOps<T> _snowman, T _snowman) {
         return Schema99.method_5359(new Dynamic(_snowman, _snowman), Schema704.BLOCK_RENAMES, "ArmorStand");
      }
   };

   public Schema704(int versionKey, Schema parent) {
      super(versionKey, parent);
   }

   protected static void method_5296(Schema _snowman, Map<String, Supplier<TypeTemplate>> _snowman, String _snowman) {
      _snowman.register(_snowman, _snowman, () -> DSL.optionalFields("Items", DSL.list(TypeReferences.ITEM_STACK.in(_snowman))));
   }

   public Type<?> getChoiceType(TypeReference _snowman, String _snowman) {
      return Objects.equals(_snowman.typeName(), TypeReferences.BLOCK_ENTITY.typeName())
         ? super.getChoiceType(_snowman, IdentifierNormalizingSchema.normalize(_snowman))
         : super.getChoiceType(_snowman, _snowman);
   }

   public Map<String, Supplier<TypeTemplate>> registerBlockEntities(Schema _snowman) {
      Map<String, Supplier<TypeTemplate>> _snowmanx = Maps.newHashMap();
      method_5296(_snowman, _snowmanx, "minecraft:furnace");
      method_5296(_snowman, _snowmanx, "minecraft:chest");
      _snowman.registerSimple(_snowmanx, "minecraft:ender_chest");
      _snowman.register(_snowmanx, "minecraft:jukebox", _snowmanxx -> DSL.optionalFields("RecordItem", TypeReferences.ITEM_STACK.in(_snowman)));
      method_5296(_snowman, _snowmanx, "minecraft:dispenser");
      method_5296(_snowman, _snowmanx, "minecraft:dropper");
      _snowman.registerSimple(_snowmanx, "minecraft:sign");
      _snowman.register(_snowmanx, "minecraft:mob_spawner", _snowmanxx -> TypeReferences.UNTAGGED_SPAWNER.in(_snowman));
      _snowman.registerSimple(_snowmanx, "minecraft:noteblock");
      _snowman.registerSimple(_snowmanx, "minecraft:piston");
      method_5296(_snowman, _snowmanx, "minecraft:brewing_stand");
      _snowman.registerSimple(_snowmanx, "minecraft:enchanting_table");
      _snowman.registerSimple(_snowmanx, "minecraft:end_portal");
      _snowman.registerSimple(_snowmanx, "minecraft:beacon");
      _snowman.registerSimple(_snowmanx, "minecraft:skull");
      _snowman.registerSimple(_snowmanx, "minecraft:daylight_detector");
      method_5296(_snowman, _snowmanx, "minecraft:hopper");
      _snowman.registerSimple(_snowmanx, "minecraft:comparator");
      _snowman.register(_snowmanx, "minecraft:flower_pot", _snowmanxx -> DSL.optionalFields("Item", DSL.or(DSL.constType(DSL.intType()), TypeReferences.ITEM_NAME.in(_snowman))));
      _snowman.registerSimple(_snowmanx, "minecraft:banner");
      _snowman.registerSimple(_snowmanx, "minecraft:structure_block");
      _snowman.registerSimple(_snowmanx, "minecraft:end_gateway");
      _snowman.registerSimple(_snowmanx, "minecraft:command_block");
      return _snowmanx;
   }

   public void registerTypes(Schema _snowman, Map<String, Supplier<TypeTemplate>> entityTypes, Map<String, Supplier<TypeTemplate>> blockEntityTypes) {
      super.registerTypes(_snowman, entityTypes, blockEntityTypes);
      _snowman.registerType(false, TypeReferences.BLOCK_ENTITY, () -> DSL.taggedChoiceLazy("id", IdentifierNormalizingSchema.getIdentifierType(), blockEntityTypes));
      _snowman.registerType(
         true,
         TypeReferences.ITEM_STACK,
         () -> DSL.hook(
               DSL.optionalFields(
                  "id",
                  TypeReferences.ITEM_NAME.in(_snowman),
                  "tag",
                  DSL.optionalFields(
                     "EntityTag",
                     TypeReferences.ENTITY_TREE.in(_snowman),
                     "BlockEntityTag",
                     TypeReferences.BLOCK_ENTITY.in(_snowman),
                     "CanDestroy",
                     DSL.list(TypeReferences.BLOCK_NAME.in(_snowman)),
                     "CanPlaceOn",
                     DSL.list(TypeReferences.BLOCK_NAME.in(_snowman))
                  )
               ),
               field_5745,
               HookFunction.IDENTITY
            )
      );
   }
}
