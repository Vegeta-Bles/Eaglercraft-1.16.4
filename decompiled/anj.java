import com.google.common.collect.Maps;
import com.mojang.datafixers.DSL;
import com.mojang.datafixers.schemas.Schema;
import com.mojang.datafixers.types.templates.TypeTemplate;
import com.mojang.datafixers.types.templates.Hook.HookFunction;
import com.mojang.serialization.Dynamic;
import com.mojang.serialization.DynamicOps;
import java.util.Map;
import java.util.function.Supplier;

public class anj extends aln {
   protected static final HookFunction b = new HookFunction() {
      public <T> T apply(DynamicOps<T> var1, T var2) {
         return anl.a(new Dynamic(_snowman, _snowman), ani.a, "minecraft:armor_stand");
      }
   };

   public anj(int var1, Schema var2) {
      super(_snowman, _snowman);
   }

   protected static void a(Schema var0, Map<String, Supplier<TypeTemplate>> var1, String var2) {
      _snowman.register(_snowman, _snowman, () -> alo.a(_snowman));
   }

   protected static void b(Schema var0, Map<String, Supplier<TypeTemplate>> var1, String var2) {
      _snowman.register(_snowman, _snowman, () -> DSL.optionalFields("inTile", akn.q.in(_snowman)));
   }

   public Map<String, Supplier<TypeTemplate>> registerEntities(Schema var1) {
      Map<String, Supplier<TypeTemplate>> _snowman = Maps.newHashMap();
      _snowman.registerSimple(_snowman, "minecraft:area_effect_cloud");
      a(_snowman, _snowman, "minecraft:armor_stand");
      _snowman.register(_snowman, "minecraft:arrow", var1x -> DSL.optionalFields("inTile", akn.q.in(_snowman)));
      a(_snowman, _snowman, "minecraft:bat");
      a(_snowman, _snowman, "minecraft:blaze");
      _snowman.registerSimple(_snowman, "minecraft:boat");
      a(_snowman, _snowman, "minecraft:cave_spider");
      _snowman.register(_snowman, "minecraft:chest_minecart", var1x -> DSL.optionalFields("DisplayTile", akn.q.in(_snowman), "Items", DSL.list(akn.l.in(_snowman))));
      a(_snowman, _snowman, "minecraft:chicken");
      _snowman.register(_snowman, "minecraft:commandblock_minecart", var1x -> DSL.optionalFields("DisplayTile", akn.q.in(_snowman)));
      a(_snowman, _snowman, "minecraft:cow");
      a(_snowman, _snowman, "minecraft:creeper");
      _snowman.register(_snowman, "minecraft:donkey", var1x -> DSL.optionalFields("Items", DSL.list(akn.l.in(_snowman)), "SaddleItem", akn.l.in(_snowman), alo.a(_snowman)));
      _snowman.registerSimple(_snowman, "minecraft:dragon_fireball");
      b(_snowman, _snowman, "minecraft:egg");
      a(_snowman, _snowman, "minecraft:elder_guardian");
      _snowman.registerSimple(_snowman, "minecraft:ender_crystal");
      a(_snowman, _snowman, "minecraft:ender_dragon");
      _snowman.register(_snowman, "minecraft:enderman", var1x -> DSL.optionalFields("carried", akn.q.in(_snowman), alo.a(_snowman)));
      a(_snowman, _snowman, "minecraft:endermite");
      b(_snowman, _snowman, "minecraft:ender_pearl");
      _snowman.registerSimple(_snowman, "minecraft:eye_of_ender_signal");
      _snowman.register(_snowman, "minecraft:falling_block", var1x -> DSL.optionalFields("Block", akn.q.in(_snowman), "TileEntityData", akn.k.in(_snowman)));
      b(_snowman, _snowman, "minecraft:fireball");
      _snowman.register(_snowman, "minecraft:fireworks_rocket", var1x -> DSL.optionalFields("FireworksItem", akn.l.in(_snowman)));
      _snowman.register(_snowman, "minecraft:furnace_minecart", var1x -> DSL.optionalFields("DisplayTile", akn.q.in(_snowman)));
      a(_snowman, _snowman, "minecraft:ghast");
      a(_snowman, _snowman, "minecraft:giant");
      a(_snowman, _snowman, "minecraft:guardian");
      _snowman.register(_snowman, "minecraft:hopper_minecart", var1x -> DSL.optionalFields("DisplayTile", akn.q.in(_snowman), "Items", DSL.list(akn.l.in(_snowman))));
      _snowman.register(_snowman, "minecraft:horse", var1x -> DSL.optionalFields("ArmorItem", akn.l.in(_snowman), "SaddleItem", akn.l.in(_snowman), alo.a(_snowman)));
      a(_snowman, _snowman, "minecraft:husk");
      _snowman.register(_snowman, "minecraft:item", var1x -> DSL.optionalFields("Item", akn.l.in(_snowman)));
      _snowman.register(_snowman, "minecraft:item_frame", var1x -> DSL.optionalFields("Item", akn.l.in(_snowman)));
      _snowman.registerSimple(_snowman, "minecraft:leash_knot");
      a(_snowman, _snowman, "minecraft:magma_cube");
      _snowman.register(_snowman, "minecraft:minecart", var1x -> DSL.optionalFields("DisplayTile", akn.q.in(_snowman)));
      a(_snowman, _snowman, "minecraft:mooshroom");
      _snowman.register(_snowman, "minecraft:mule", var1x -> DSL.optionalFields("Items", DSL.list(akn.l.in(_snowman)), "SaddleItem", akn.l.in(_snowman), alo.a(_snowman)));
      a(_snowman, _snowman, "minecraft:ocelot");
      _snowman.registerSimple(_snowman, "minecraft:painting");
      _snowman.registerSimple(_snowman, "minecraft:parrot");
      a(_snowman, _snowman, "minecraft:pig");
      a(_snowman, _snowman, "minecraft:polar_bear");
      _snowman.register(_snowman, "minecraft:potion", var1x -> DSL.optionalFields("Potion", akn.l.in(_snowman), "inTile", akn.q.in(_snowman)));
      a(_snowman, _snowman, "minecraft:rabbit");
      a(_snowman, _snowman, "minecraft:sheep");
      a(_snowman, _snowman, "minecraft:shulker");
      _snowman.registerSimple(_snowman, "minecraft:shulker_bullet");
      a(_snowman, _snowman, "minecraft:silverfish");
      a(_snowman, _snowman, "minecraft:skeleton");
      _snowman.register(_snowman, "minecraft:skeleton_horse", var1x -> DSL.optionalFields("SaddleItem", akn.l.in(_snowman), alo.a(_snowman)));
      a(_snowman, _snowman, "minecraft:slime");
      b(_snowman, _snowman, "minecraft:small_fireball");
      b(_snowman, _snowman, "minecraft:snowball");
      a(_snowman, _snowman, "minecraft:snowman");
      _snowman.register(_snowman, "minecraft:spawner_minecart", var1x -> DSL.optionalFields("DisplayTile", akn.q.in(_snowman), akn.s.in(_snowman)));
      _snowman.register(_snowman, "minecraft:spectral_arrow", var1x -> DSL.optionalFields("inTile", akn.q.in(_snowman)));
      a(_snowman, _snowman, "minecraft:spider");
      a(_snowman, _snowman, "minecraft:squid");
      a(_snowman, _snowman, "minecraft:stray");
      _snowman.registerSimple(_snowman, "minecraft:tnt");
      _snowman.register(_snowman, "minecraft:tnt_minecart", var1x -> DSL.optionalFields("DisplayTile", akn.q.in(_snowman)));
      _snowman.register(
         _snowman,
         "minecraft:villager",
         var1x -> DSL.optionalFields(
               "Inventory",
               DSL.list(akn.l.in(_snowman)),
               "Offers",
               DSL.optionalFields("Recipes", DSL.list(DSL.optionalFields("buy", akn.l.in(_snowman), "buyB", akn.l.in(_snowman), "sell", akn.l.in(_snowman)))),
               alo.a(_snowman)
            )
      );
      a(_snowman, _snowman, "minecraft:villager_golem");
      a(_snowman, _snowman, "minecraft:witch");
      a(_snowman, _snowman, "minecraft:wither");
      a(_snowman, _snowman, "minecraft:wither_skeleton");
      b(_snowman, _snowman, "minecraft:wither_skull");
      a(_snowman, _snowman, "minecraft:wolf");
      b(_snowman, _snowman, "minecraft:xp_bottle");
      _snowman.registerSimple(_snowman, "minecraft:xp_orb");
      a(_snowman, _snowman, "minecraft:zombie");
      _snowman.register(_snowman, "minecraft:zombie_horse", var1x -> DSL.optionalFields("SaddleItem", akn.l.in(_snowman), alo.a(_snowman)));
      a(_snowman, _snowman, "minecraft:zombie_pigman");
      a(_snowman, _snowman, "minecraft:zombie_villager");
      _snowman.registerSimple(_snowman, "minecraft:evocation_fangs");
      a(_snowman, _snowman, "minecraft:evocation_illager");
      _snowman.registerSimple(_snowman, "minecraft:illusion_illager");
      _snowman.register(
         _snowman, "minecraft:llama", var1x -> DSL.optionalFields("Items", DSL.list(akn.l.in(_snowman)), "SaddleItem", akn.l.in(_snowman), "DecorItem", akn.l.in(_snowman), alo.a(_snowman))
      );
      _snowman.registerSimple(_snowman, "minecraft:llama_spit");
      a(_snowman, _snowman, "minecraft:vex");
      a(_snowman, _snowman, "minecraft:vindication_illager");
      return _snowman;
   }

   public void registerTypes(Schema var1, Map<String, Supplier<TypeTemplate>> var2, Map<String, Supplier<TypeTemplate>> var3) {
      super.registerTypes(_snowman, _snowman, _snowman);
      _snowman.registerType(true, akn.p, () -> DSL.taggedChoiceLazy("id", a(), _snowman));
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
