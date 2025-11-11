import com.mojang.datafixers.DSL;
import com.mojang.datafixers.schemas.Schema;
import com.mojang.datafixers.types.templates.TypeTemplate;
import java.util.Map;
import java.util.function.Supplier;

public class alz extends aln {
   public alz(int var1, Schema var2) {
      super(_snowman, _snowman);
   }

   public Map<String, Supplier<TypeTemplate>> registerEntities(Schema var1) {
      Map<String, Supplier<TypeTemplate>> _snowman = super.registerEntities(_snowman);
      _snowman.registerSimple(_snowman, "minecraft:egg");
      _snowman.registerSimple(_snowman, "minecraft:ender_pearl");
      _snowman.registerSimple(_snowman, "minecraft:fireball");
      _snowman.register(_snowman, "minecraft:potion", var1x -> DSL.optionalFields("Potion", akn.l.in(_snowman)));
      _snowman.registerSimple(_snowman, "minecraft:small_fireball");
      _snowman.registerSimple(_snowman, "minecraft:snowball");
      _snowman.registerSimple(_snowman, "minecraft:wither_skull");
      _snowman.registerSimple(_snowman, "minecraft:xp_bottle");
      _snowman.register(_snowman, "minecraft:arrow", () -> DSL.optionalFields("inBlockState", akn.m.in(_snowman)));
      _snowman.register(_snowman, "minecraft:enderman", () -> DSL.optionalFields("carriedBlockState", akn.m.in(_snowman), alo.a(_snowman)));
      _snowman.register(_snowman, "minecraft:falling_block", () -> DSL.optionalFields("BlockState", akn.m.in(_snowman), "TileEntityData", akn.k.in(_snowman)));
      _snowman.register(_snowman, "minecraft:spectral_arrow", () -> DSL.optionalFields("inBlockState", akn.m.in(_snowman)));
      _snowman.register(_snowman, "minecraft:chest_minecart", () -> DSL.optionalFields("DisplayState", akn.m.in(_snowman), "Items", DSL.list(akn.l.in(_snowman))));
      _snowman.register(_snowman, "minecraft:commandblock_minecart", () -> DSL.optionalFields("DisplayState", akn.m.in(_snowman)));
      _snowman.register(_snowman, "minecraft:furnace_minecart", () -> DSL.optionalFields("DisplayState", akn.m.in(_snowman)));
      _snowman.register(_snowman, "minecraft:hopper_minecart", () -> DSL.optionalFields("DisplayState", akn.m.in(_snowman), "Items", DSL.list(akn.l.in(_snowman))));
      _snowman.register(_snowman, "minecraft:minecart", () -> DSL.optionalFields("DisplayState", akn.m.in(_snowman)));
      _snowman.register(_snowman, "minecraft:spawner_minecart", () -> DSL.optionalFields("DisplayState", akn.m.in(_snowman), akn.s.in(_snowman)));
      _snowman.register(_snowman, "minecraft:tnt_minecart", () -> DSL.optionalFields("DisplayState", akn.m.in(_snowman)));
      return _snowman;
   }
}
