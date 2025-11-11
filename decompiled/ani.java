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

public class ani extends Schema {
   protected static final Map<String, String> a = (Map<String, String>)DataFixUtils.make(Maps.newHashMap(), var0 -> {
      var0.put("minecraft:furnace", "minecraft:furnace");
      var0.put("minecraft:lit_furnace", "minecraft:furnace");
      var0.put("minecraft:chest", "minecraft:chest");
      var0.put("minecraft:trapped_chest", "minecraft:chest");
      var0.put("minecraft:ender_chest", "minecraft:ender_chest");
      var0.put("minecraft:jukebox", "minecraft:jukebox");
      var0.put("minecraft:dispenser", "minecraft:dispenser");
      var0.put("minecraft:dropper", "minecraft:dropper");
      var0.put("minecraft:sign", "minecraft:sign");
      var0.put("minecraft:mob_spawner", "minecraft:mob_spawner");
      var0.put("minecraft:noteblock", "minecraft:noteblock");
      var0.put("minecraft:brewing_stand", "minecraft:brewing_stand");
      var0.put("minecraft:enhanting_table", "minecraft:enchanting_table");
      var0.put("minecraft:command_block", "minecraft:command_block");
      var0.put("minecraft:beacon", "minecraft:beacon");
      var0.put("minecraft:skull", "minecraft:skull");
      var0.put("minecraft:daylight_detector", "minecraft:daylight_detector");
      var0.put("minecraft:hopper", "minecraft:hopper");
      var0.put("minecraft:banner", "minecraft:banner");
      var0.put("minecraft:flower_pot", "minecraft:flower_pot");
      var0.put("minecraft:repeating_command_block", "minecraft:command_block");
      var0.put("minecraft:chain_command_block", "minecraft:command_block");
      var0.put("minecraft:shulker_box", "minecraft:shulker_box");
      var0.put("minecraft:white_shulker_box", "minecraft:shulker_box");
      var0.put("minecraft:orange_shulker_box", "minecraft:shulker_box");
      var0.put("minecraft:magenta_shulker_box", "minecraft:shulker_box");
      var0.put("minecraft:light_blue_shulker_box", "minecraft:shulker_box");
      var0.put("minecraft:yellow_shulker_box", "minecraft:shulker_box");
      var0.put("minecraft:lime_shulker_box", "minecraft:shulker_box");
      var0.put("minecraft:pink_shulker_box", "minecraft:shulker_box");
      var0.put("minecraft:gray_shulker_box", "minecraft:shulker_box");
      var0.put("minecraft:silver_shulker_box", "minecraft:shulker_box");
      var0.put("minecraft:cyan_shulker_box", "minecraft:shulker_box");
      var0.put("minecraft:purple_shulker_box", "minecraft:shulker_box");
      var0.put("minecraft:blue_shulker_box", "minecraft:shulker_box");
      var0.put("minecraft:brown_shulker_box", "minecraft:shulker_box");
      var0.put("minecraft:green_shulker_box", "minecraft:shulker_box");
      var0.put("minecraft:red_shulker_box", "minecraft:shulker_box");
      var0.put("minecraft:black_shulker_box", "minecraft:shulker_box");
      var0.put("minecraft:bed", "minecraft:bed");
      var0.put("minecraft:light_gray_shulker_box", "minecraft:shulker_box");
      var0.put("minecraft:banner", "minecraft:banner");
      var0.put("minecraft:white_banner", "minecraft:banner");
      var0.put("minecraft:orange_banner", "minecraft:banner");
      var0.put("minecraft:magenta_banner", "minecraft:banner");
      var0.put("minecraft:light_blue_banner", "minecraft:banner");
      var0.put("minecraft:yellow_banner", "minecraft:banner");
      var0.put("minecraft:lime_banner", "minecraft:banner");
      var0.put("minecraft:pink_banner", "minecraft:banner");
      var0.put("minecraft:gray_banner", "minecraft:banner");
      var0.put("minecraft:silver_banner", "minecraft:banner");
      var0.put("minecraft:cyan_banner", "minecraft:banner");
      var0.put("minecraft:purple_banner", "minecraft:banner");
      var0.put("minecraft:blue_banner", "minecraft:banner");
      var0.put("minecraft:brown_banner", "minecraft:banner");
      var0.put("minecraft:green_banner", "minecraft:banner");
      var0.put("minecraft:red_banner", "minecraft:banner");
      var0.put("minecraft:black_banner", "minecraft:banner");
      var0.put("minecraft:standing_sign", "minecraft:sign");
      var0.put("minecraft:wall_sign", "minecraft:sign");
      var0.put("minecraft:piston_head", "minecraft:piston");
      var0.put("minecraft:daylight_detector_inverted", "minecraft:daylight_detector");
      var0.put("minecraft:unpowered_comparator", "minecraft:comparator");
      var0.put("minecraft:powered_comparator", "minecraft:comparator");
      var0.put("minecraft:wall_banner", "minecraft:banner");
      var0.put("minecraft:standing_banner", "minecraft:banner");
      var0.put("minecraft:structure_block", "minecraft:structure_block");
      var0.put("minecraft:end_portal", "minecraft:end_portal");
      var0.put("minecraft:end_gateway", "minecraft:end_gateway");
      var0.put("minecraft:sign", "minecraft:sign");
      var0.put("minecraft:shield", "minecraft:banner");
   });
   protected static final HookFunction b = new HookFunction() {
      public <T> T apply(DynamicOps<T> var1, T var2) {
         return anl.a(new Dynamic(_snowman, _snowman), ani.a, "ArmorStand");
      }
   };

   public ani(int var1, Schema var2) {
      super(_snowman, _snowman);
   }

   protected static void a(Schema var0, Map<String, Supplier<TypeTemplate>> var1, String var2) {
      _snowman.register(_snowman, _snowman, () -> DSL.optionalFields("Items", DSL.list(akn.l.in(_snowman))));
   }

   public Type<?> getChoiceType(TypeReference var1, String var2) {
      return Objects.equals(_snowman.typeName(), akn.k.typeName()) ? super.getChoiceType(_snowman, aln.a(_snowman)) : super.getChoiceType(_snowman, _snowman);
   }

   public Map<String, Supplier<TypeTemplate>> registerBlockEntities(Schema var1) {
      Map<String, Supplier<TypeTemplate>> _snowman = Maps.newHashMap();
      a(_snowman, _snowman, "minecraft:furnace");
      a(_snowman, _snowman, "minecraft:chest");
      _snowman.registerSimple(_snowman, "minecraft:ender_chest");
      _snowman.register(_snowman, "minecraft:jukebox", var1x -> DSL.optionalFields("RecordItem", akn.l.in(_snowman)));
      a(_snowman, _snowman, "minecraft:dispenser");
      a(_snowman, _snowman, "minecraft:dropper");
      _snowman.registerSimple(_snowman, "minecraft:sign");
      _snowman.register(_snowman, "minecraft:mob_spawner", var1x -> akn.s.in(_snowman));
      _snowman.registerSimple(_snowman, "minecraft:noteblock");
      _snowman.registerSimple(_snowman, "minecraft:piston");
      a(_snowman, _snowman, "minecraft:brewing_stand");
      _snowman.registerSimple(_snowman, "minecraft:enchanting_table");
      _snowman.registerSimple(_snowman, "minecraft:end_portal");
      _snowman.registerSimple(_snowman, "minecraft:beacon");
      _snowman.registerSimple(_snowman, "minecraft:skull");
      _snowman.registerSimple(_snowman, "minecraft:daylight_detector");
      a(_snowman, _snowman, "minecraft:hopper");
      _snowman.registerSimple(_snowman, "minecraft:comparator");
      _snowman.register(_snowman, "minecraft:flower_pot", var1x -> DSL.optionalFields("Item", DSL.or(DSL.constType(DSL.intType()), akn.r.in(_snowman))));
      _snowman.registerSimple(_snowman, "minecraft:banner");
      _snowman.registerSimple(_snowman, "minecraft:structure_block");
      _snowman.registerSimple(_snowman, "minecraft:end_gateway");
      _snowman.registerSimple(_snowman, "minecraft:command_block");
      return _snowman;
   }

   public void registerTypes(Schema var1, Map<String, Supplier<TypeTemplate>> var2, Map<String, Supplier<TypeTemplate>> var3) {
      super.registerTypes(_snowman, _snowman, _snowman);
      _snowman.registerType(false, akn.k, () -> DSL.taggedChoiceLazy("id", aln.a(), _snowman));
      _snowman.registerType(
         true,
         akn.l,
         () -> DSL.hook(
               DSL.optionalFields(
                  "id",
                  akn.r.in(_snowman),
                  "tag",
                  DSL.optionalFields(
                     "EntityTag", akn.o.in(_snowman), "BlockEntityTag", akn.k.in(_snowman), "CanDestroy", DSL.list(akn.q.in(_snowman)), "CanPlaceOn", DSL.list(akn.q.in(_snowman))
                  )
               ),
               b,
               HookFunction.IDENTITY
            )
      );
   }
}
