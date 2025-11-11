import com.google.common.collect.Maps;
import com.mojang.datafixers.DSL;
import com.mojang.datafixers.schemas.Schema;
import com.mojang.datafixers.types.templates.TypeTemplate;
import com.mojang.datafixers.types.templates.Hook.HookFunction;
import java.util.Map;
import java.util.function.Supplier;

public class ame extends aln {
   public ame(int var1, Schema var2) {
      super(_snowman, _snowman);
   }

   protected static void a(Schema var0, Map<String, Supplier<TypeTemplate>> var1, String var2) {
      _snowman.register(_snowman, _snowman, () -> alo.a(_snowman));
   }

   protected static void b(Schema var0, Map<String, Supplier<TypeTemplate>> var1, String var2) {
      _snowman.register(_snowman, _snowman, () -> DSL.optionalFields("Items", DSL.list(akn.l.in(_snowman))));
   }

   public Map<String, Supplier<TypeTemplate>> registerEntities(Schema var1) {
      Map<String, Supplier<TypeTemplate>> _snowman = Maps.newHashMap();
      _snowman.registerSimple(_snowman, "minecraft:area_effect_cloud");
      a(_snowman, _snowman, "minecraft:armor_stand");
      _snowman.register(_snowman, "minecraft:arrow", var1x -> DSL.optionalFields("inBlockState", akn.m.in(_snowman)));
      a(_snowman, _snowman, "minecraft:bat");
      a(_snowman, _snowman, "minecraft:blaze");
      _snowman.registerSimple(_snowman, "minecraft:boat");
      a(_snowman, _snowman, "minecraft:cave_spider");
      _snowman.register(_snowman, "minecraft:chest_minecart", var1x -> DSL.optionalFields("DisplayState", akn.m.in(_snowman), "Items", DSL.list(akn.l.in(_snowman))));
      a(_snowman, _snowman, "minecraft:chicken");
      _snowman.register(_snowman, "minecraft:commandblock_minecart", var1x -> DSL.optionalFields("DisplayState", akn.m.in(_snowman)));
      a(_snowman, _snowman, "minecraft:cow");
      a(_snowman, _snowman, "minecraft:creeper");
      _snowman.register(_snowman, "minecraft:donkey", var1x -> DSL.optionalFields("Items", DSL.list(akn.l.in(_snowman)), "SaddleItem", akn.l.in(_snowman), alo.a(_snowman)));
      _snowman.registerSimple(_snowman, "minecraft:dragon_fireball");
      _snowman.registerSimple(_snowman, "minecraft:egg");
      a(_snowman, _snowman, "minecraft:elder_guardian");
      _snowman.registerSimple(_snowman, "minecraft:ender_crystal");
      a(_snowman, _snowman, "minecraft:ender_dragon");
      _snowman.register(_snowman, "minecraft:enderman", var1x -> DSL.optionalFields("carriedBlockState", akn.m.in(_snowman), alo.a(_snowman)));
      a(_snowman, _snowman, "minecraft:endermite");
      _snowman.registerSimple(_snowman, "minecraft:ender_pearl");
      _snowman.registerSimple(_snowman, "minecraft:evocation_fangs");
      a(_snowman, _snowman, "minecraft:evocation_illager");
      _snowman.registerSimple(_snowman, "minecraft:eye_of_ender_signal");
      _snowman.register(_snowman, "minecraft:falling_block", var1x -> DSL.optionalFields("BlockState", akn.m.in(_snowman), "TileEntityData", akn.k.in(_snowman)));
      _snowman.registerSimple(_snowman, "minecraft:fireball");
      _snowman.register(_snowman, "minecraft:fireworks_rocket", var1x -> DSL.optionalFields("FireworksItem", akn.l.in(_snowman)));
      _snowman.register(_snowman, "minecraft:furnace_minecart", var1x -> DSL.optionalFields("DisplayState", akn.m.in(_snowman)));
      a(_snowman, _snowman, "minecraft:ghast");
      a(_snowman, _snowman, "minecraft:giant");
      a(_snowman, _snowman, "minecraft:guardian");
      _snowman.register(_snowman, "minecraft:hopper_minecart", var1x -> DSL.optionalFields("DisplayState", akn.m.in(_snowman), "Items", DSL.list(akn.l.in(_snowman))));
      _snowman.register(_snowman, "minecraft:horse", var1x -> DSL.optionalFields("ArmorItem", akn.l.in(_snowman), "SaddleItem", akn.l.in(_snowman), alo.a(_snowman)));
      a(_snowman, _snowman, "minecraft:husk");
      _snowman.registerSimple(_snowman, "minecraft:illusion_illager");
      _snowman.register(_snowman, "minecraft:item", var1x -> DSL.optionalFields("Item", akn.l.in(_snowman)));
      _snowman.register(_snowman, "minecraft:item_frame", var1x -> DSL.optionalFields("Item", akn.l.in(_snowman)));
      _snowman.registerSimple(_snowman, "minecraft:leash_knot");
      _snowman.register(
         _snowman, "minecraft:llama", var1x -> DSL.optionalFields("Items", DSL.list(akn.l.in(_snowman)), "SaddleItem", akn.l.in(_snowman), "DecorItem", akn.l.in(_snowman), alo.a(_snowman))
      );
      _snowman.registerSimple(_snowman, "minecraft:llama_spit");
      a(_snowman, _snowman, "minecraft:magma_cube");
      _snowman.register(_snowman, "minecraft:minecart", var1x -> DSL.optionalFields("DisplayState", akn.m.in(_snowman)));
      a(_snowman, _snowman, "minecraft:mooshroom");
      _snowman.register(_snowman, "minecraft:mule", var1x -> DSL.optionalFields("Items", DSL.list(akn.l.in(_snowman)), "SaddleItem", akn.l.in(_snowman), alo.a(_snowman)));
      a(_snowman, _snowman, "minecraft:ocelot");
      _snowman.registerSimple(_snowman, "minecraft:painting");
      _snowman.registerSimple(_snowman, "minecraft:parrot");
      a(_snowman, _snowman, "minecraft:pig");
      a(_snowman, _snowman, "minecraft:polar_bear");
      _snowman.register(_snowman, "minecraft:potion", var1x -> DSL.optionalFields("Potion", akn.l.in(_snowman)));
      a(_snowman, _snowman, "minecraft:rabbit");
      a(_snowman, _snowman, "minecraft:sheep");
      a(_snowman, _snowman, "minecraft:shulker");
      _snowman.registerSimple(_snowman, "minecraft:shulker_bullet");
      a(_snowman, _snowman, "minecraft:silverfish");
      a(_snowman, _snowman, "minecraft:skeleton");
      _snowman.register(_snowman, "minecraft:skeleton_horse", var1x -> DSL.optionalFields("SaddleItem", akn.l.in(_snowman), alo.a(_snowman)));
      a(_snowman, _snowman, "minecraft:slime");
      _snowman.registerSimple(_snowman, "minecraft:small_fireball");
      _snowman.registerSimple(_snowman, "minecraft:snowball");
      a(_snowman, _snowman, "minecraft:snowman");
      _snowman.register(_snowman, "minecraft:spawner_minecart", var1x -> DSL.optionalFields("DisplayState", akn.m.in(_snowman), akn.s.in(_snowman)));
      _snowman.register(_snowman, "minecraft:spectral_arrow", var1x -> DSL.optionalFields("inBlockState", akn.m.in(_snowman)));
      a(_snowman, _snowman, "minecraft:spider");
      a(_snowman, _snowman, "minecraft:squid");
      a(_snowman, _snowman, "minecraft:stray");
      _snowman.registerSimple(_snowman, "minecraft:tnt");
      _snowman.register(_snowman, "minecraft:tnt_minecart", var1x -> DSL.optionalFields("DisplayState", akn.m.in(_snowman)));
      a(_snowman, _snowman, "minecraft:vex");
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
      a(_snowman, _snowman, "minecraft:vindication_illager");
      a(_snowman, _snowman, "minecraft:witch");
      a(_snowman, _snowman, "minecraft:wither");
      a(_snowman, _snowman, "minecraft:wither_skeleton");
      _snowman.registerSimple(_snowman, "minecraft:wither_skull");
      a(_snowman, _snowman, "minecraft:wolf");
      _snowman.registerSimple(_snowman, "minecraft:xp_bottle");
      _snowman.registerSimple(_snowman, "minecraft:xp_orb");
      a(_snowman, _snowman, "minecraft:zombie");
      _snowman.register(_snowman, "minecraft:zombie_horse", var1x -> DSL.optionalFields("SaddleItem", akn.l.in(_snowman), alo.a(_snowman)));
      a(_snowman, _snowman, "minecraft:zombie_pigman");
      a(_snowman, _snowman, "minecraft:zombie_villager");
      return _snowman;
   }

   public Map<String, Supplier<TypeTemplate>> registerBlockEntities(Schema var1) {
      Map<String, Supplier<TypeTemplate>> _snowman = Maps.newHashMap();
      b(_snowman, _snowman, "minecraft:furnace");
      b(_snowman, _snowman, "minecraft:chest");
      b(_snowman, _snowman, "minecraft:trapped_chest");
      _snowman.registerSimple(_snowman, "minecraft:ender_chest");
      _snowman.register(_snowman, "minecraft:jukebox", var1x -> DSL.optionalFields("RecordItem", akn.l.in(_snowman)));
      b(_snowman, _snowman, "minecraft:dispenser");
      b(_snowman, _snowman, "minecraft:dropper");
      _snowman.registerSimple(_snowman, "minecraft:sign");
      _snowman.register(_snowman, "minecraft:mob_spawner", var1x -> akn.s.in(_snowman));
      _snowman.register(_snowman, "minecraft:piston", var1x -> DSL.optionalFields("blockState", akn.m.in(_snowman)));
      b(_snowman, _snowman, "minecraft:brewing_stand");
      _snowman.registerSimple(_snowman, "minecraft:enchanting_table");
      _snowman.registerSimple(_snowman, "minecraft:end_portal");
      _snowman.registerSimple(_snowman, "minecraft:beacon");
      _snowman.registerSimple(_snowman, "minecraft:skull");
      _snowman.registerSimple(_snowman, "minecraft:daylight_detector");
      b(_snowman, _snowman, "minecraft:hopper");
      _snowman.registerSimple(_snowman, "minecraft:comparator");
      _snowman.registerSimple(_snowman, "minecraft:banner");
      _snowman.registerSimple(_snowman, "minecraft:structure_block");
      _snowman.registerSimple(_snowman, "minecraft:end_gateway");
      _snowman.registerSimple(_snowman, "minecraft:command_block");
      b(_snowman, _snowman, "minecraft:shulker_box");
      _snowman.registerSimple(_snowman, "minecraft:bed");
      return _snowman;
   }

   public void registerTypes(Schema var1, Map<String, Supplier<TypeTemplate>> var2, Map<String, Supplier<TypeTemplate>> var3) {
      _snowman.registerType(false, akn.a, DSL::remainder);
      _snowman.registerType(false, akn.w, () -> DSL.constType(a()));
      _snowman.registerType(
         false,
         akn.b,
         () -> DSL.optionalFields(
               "RootVehicle",
               DSL.optionalFields("Entity", akn.o.in(_snowman)),
               "Inventory",
               DSL.list(akn.l.in(_snowman)),
               "EnderItems",
               DSL.list(akn.l.in(_snowman)),
               DSL.optionalFields(
                  "ShoulderEntityLeft",
                  akn.o.in(_snowman),
                  "ShoulderEntityRight",
                  akn.o.in(_snowman),
                  "recipeBook",
                  DSL.optionalFields("recipes", DSL.list(akn.w.in(_snowman)), "toBeDisplayed", DSL.list(akn.w.in(_snowman)))
               )
            )
      );
      _snowman.registerType(
         false,
         akn.c,
         () -> DSL.fields(
               "Level",
               DSL.optionalFields(
                  "Entities",
                  DSL.list(akn.o.in(_snowman)),
                  "TileEntities",
                  DSL.list(akn.k.in(_snowman)),
                  "TileTicks",
                  DSL.list(DSL.fields("i", akn.q.in(_snowman))),
                  "Sections",
                  DSL.list(DSL.optionalFields("Palette", DSL.list(akn.m.in(_snowman))))
               )
            )
      );
      _snowman.registerType(true, akn.k, () -> DSL.taggedChoiceLazy("id", a(), _snowman));
      _snowman.registerType(true, akn.o, () -> DSL.optionalFields("Passengers", DSL.list(akn.o.in(_snowman)), akn.p.in(_snowman)));
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
               anj.b,
               HookFunction.IDENTITY
            )
      );
      _snowman.registerType(false, akn.d, () -> DSL.compoundList(DSL.list(akn.l.in(_snowman))));
      _snowman.registerType(false, akn.e, DSL::remainder);
      _snowman.registerType(
         false,
         akn.f,
         () -> DSL.optionalFields(
               "entities",
               DSL.list(DSL.optionalFields("nbt", akn.o.in(_snowman))),
               "blocks",
               DSL.list(DSL.optionalFields("nbt", akn.k.in(_snowman))),
               "palette",
               DSL.list(akn.m.in(_snowman))
            )
      );
      _snowman.registerType(false, akn.q, () -> DSL.constType(a()));
      _snowman.registerType(false, akn.r, () -> DSL.constType(a()));
      _snowman.registerType(false, akn.m, DSL::remainder);
      Supplier<TypeTemplate> _snowman = () -> DSL.compoundList(akn.r.in(_snowman), DSL.constType(DSL.intType()));
      _snowman.registerType(
         false,
         akn.g,
         () -> DSL.optionalFields(
               "stats",
               DSL.optionalFields(
                  "minecraft:mined",
                  DSL.compoundList(akn.q.in(_snowman), DSL.constType(DSL.intType())),
                  "minecraft:crafted",
                  _snowman.get(),
                  "minecraft:used",
                  _snowman.get(),
                  "minecraft:broken",
                  _snowman.get(),
                  "minecraft:picked_up",
                  _snowman.get(),
                  DSL.optionalFields(
                     "minecraft:dropped",
                     _snowman.get(),
                     "minecraft:killed",
                     DSL.compoundList(akn.n.in(_snowman), DSL.constType(DSL.intType())),
                     "minecraft:killed_by",
                     DSL.compoundList(akn.n.in(_snowman), DSL.constType(DSL.intType())),
                     "minecraft:custom",
                     DSL.compoundList(DSL.constType(a()), DSL.constType(DSL.intType()))
                  )
               )
            )
      );
      _snowman.registerType(
         false,
         akn.h,
         () -> DSL.optionalFields(
               "data", DSL.optionalFields("Features", DSL.compoundList(akn.t.in(_snowman)), "Objectives", DSL.list(akn.u.in(_snowman)), "Teams", DSL.list(akn.v.in(_snowman)))
            )
      );
      _snowman.registerType(
         false,
         akn.t,
         () -> DSL.optionalFields("Children", DSL.list(DSL.optionalFields("CA", akn.m.in(_snowman), "CB", akn.m.in(_snowman), "CC", akn.m.in(_snowman), "CD", akn.m.in(_snowman))))
      );
      _snowman.registerType(false, akn.u, DSL::remainder);
      _snowman.registerType(false, akn.v, DSL::remainder);
      _snowman.registerType(true, akn.s, () -> DSL.optionalFields("SpawnPotentials", DSL.list(DSL.fields("Entity", akn.o.in(_snowman))), "SpawnData", akn.o.in(_snowman)));
      _snowman.registerType(
         false,
         akn.i,
         () -> DSL.optionalFields(
               "minecraft:adventure/adventuring_time",
               DSL.optionalFields("criteria", DSL.compoundList(akn.x.in(_snowman), DSL.constType(DSL.string()))),
               "minecraft:adventure/kill_a_mob",
               DSL.optionalFields("criteria", DSL.compoundList(akn.n.in(_snowman), DSL.constType(DSL.string()))),
               "minecraft:adventure/kill_all_mobs",
               DSL.optionalFields("criteria", DSL.compoundList(akn.n.in(_snowman), DSL.constType(DSL.string()))),
               "minecraft:husbandry/bred_all_animals",
               DSL.optionalFields("criteria", DSL.compoundList(akn.n.in(_snowman), DSL.constType(DSL.string())))
            )
      );
      _snowman.registerType(false, akn.x, () -> DSL.constType(a()));
      _snowman.registerType(false, akn.n, () -> DSL.constType(a()));
      _snowman.registerType(false, akn.j, DSL::remainder);
      _snowman.registerType(true, akn.y, DSL::remainder);
   }
}
