import com.google.common.collect.Maps;
import com.mojang.datafixers.DSL;
import com.mojang.datafixers.DataFixUtils;
import com.mojang.datafixers.schemas.Schema;
import com.mojang.datafixers.types.templates.TypeTemplate;
import com.mojang.datafixers.types.templates.Hook.HookFunction;
import com.mojang.serialization.Dynamic;
import com.mojang.serialization.DynamicOps;
import java.util.Map;
import java.util.Objects;
import java.util.function.Supplier;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class anl extends Schema {
   private static final Logger b = LogManager.getLogger();
   private static final Map<String, String> c = (Map<String, String>)DataFixUtils.make(Maps.newHashMap(), var0 -> {
      var0.put("minecraft:furnace", "Furnace");
      var0.put("minecraft:lit_furnace", "Furnace");
      var0.put("minecraft:chest", "Chest");
      var0.put("minecraft:trapped_chest", "Chest");
      var0.put("minecraft:ender_chest", "EnderChest");
      var0.put("minecraft:jukebox", "RecordPlayer");
      var0.put("minecraft:dispenser", "Trap");
      var0.put("minecraft:dropper", "Dropper");
      var0.put("minecraft:sign", "Sign");
      var0.put("minecraft:mob_spawner", "MobSpawner");
      var0.put("minecraft:noteblock", "Music");
      var0.put("minecraft:brewing_stand", "Cauldron");
      var0.put("minecraft:enhanting_table", "EnchantTable");
      var0.put("minecraft:command_block", "CommandBlock");
      var0.put("minecraft:beacon", "Beacon");
      var0.put("minecraft:skull", "Skull");
      var0.put("minecraft:daylight_detector", "DLDetector");
      var0.put("minecraft:hopper", "Hopper");
      var0.put("minecraft:banner", "Banner");
      var0.put("minecraft:flower_pot", "FlowerPot");
      var0.put("minecraft:repeating_command_block", "CommandBlock");
      var0.put("minecraft:chain_command_block", "CommandBlock");
      var0.put("minecraft:standing_sign", "Sign");
      var0.put("minecraft:wall_sign", "Sign");
      var0.put("minecraft:piston_head", "Piston");
      var0.put("minecraft:daylight_detector_inverted", "DLDetector");
      var0.put("minecraft:unpowered_comparator", "Comparator");
      var0.put("minecraft:powered_comparator", "Comparator");
      var0.put("minecraft:wall_banner", "Banner");
      var0.put("minecraft:standing_banner", "Banner");
      var0.put("minecraft:structure_block", "Structure");
      var0.put("minecraft:end_portal", "Airportal");
      var0.put("minecraft:end_gateway", "EndGateway");
      var0.put("minecraft:shield", "Banner");
   });
   protected static final HookFunction a = new HookFunction() {
      public <T> T apply(DynamicOps<T> var1, T var2) {
         return anl.a(new Dynamic(_snowman, _snowman), anl.c, "ArmorStand");
      }
   };

   public anl(int var1, Schema var2) {
      super(_snowman, _snowman);
   }

   protected static TypeTemplate a(Schema var0) {
      return DSL.optionalFields("Equipment", DSL.list(akn.l.in(_snowman)));
   }

   protected static void a(Schema var0, Map<String, Supplier<TypeTemplate>> var1, String var2) {
      _snowman.register(_snowman, _snowman, () -> a(_snowman));
   }

   protected static void b(Schema var0, Map<String, Supplier<TypeTemplate>> var1, String var2) {
      _snowman.register(_snowman, _snowman, () -> DSL.optionalFields("inTile", akn.q.in(_snowman)));
   }

   protected static void c(Schema var0, Map<String, Supplier<TypeTemplate>> var1, String var2) {
      _snowman.register(_snowman, _snowman, () -> DSL.optionalFields("DisplayTile", akn.q.in(_snowman)));
   }

   protected static void d(Schema var0, Map<String, Supplier<TypeTemplate>> var1, String var2) {
      _snowman.register(_snowman, _snowman, () -> DSL.optionalFields("Items", DSL.list(akn.l.in(_snowman))));
   }

   public Map<String, Supplier<TypeTemplate>> registerEntities(Schema var1) {
      Map<String, Supplier<TypeTemplate>> _snowman = Maps.newHashMap();
      _snowman.register(_snowman, "Item", var1x -> DSL.optionalFields("Item", akn.l.in(_snowman)));
      _snowman.registerSimple(_snowman, "XPOrb");
      b(_snowman, _snowman, "ThrownEgg");
      _snowman.registerSimple(_snowman, "LeashKnot");
      _snowman.registerSimple(_snowman, "Painting");
      _snowman.register(_snowman, "Arrow", var1x -> DSL.optionalFields("inTile", akn.q.in(_snowman)));
      _snowman.register(_snowman, "TippedArrow", var1x -> DSL.optionalFields("inTile", akn.q.in(_snowman)));
      _snowman.register(_snowman, "SpectralArrow", var1x -> DSL.optionalFields("inTile", akn.q.in(_snowman)));
      b(_snowman, _snowman, "Snowball");
      b(_snowman, _snowman, "Fireball");
      b(_snowman, _snowman, "SmallFireball");
      b(_snowman, _snowman, "ThrownEnderpearl");
      _snowman.registerSimple(_snowman, "EyeOfEnderSignal");
      _snowman.register(_snowman, "ThrownPotion", var1x -> DSL.optionalFields("inTile", akn.q.in(_snowman), "Potion", akn.l.in(_snowman)));
      b(_snowman, _snowman, "ThrownExpBottle");
      _snowman.register(_snowman, "ItemFrame", var1x -> DSL.optionalFields("Item", akn.l.in(_snowman)));
      b(_snowman, _snowman, "WitherSkull");
      _snowman.registerSimple(_snowman, "PrimedTnt");
      _snowman.register(_snowman, "FallingSand", var1x -> DSL.optionalFields("Block", akn.q.in(_snowman), "TileEntityData", akn.k.in(_snowman)));
      _snowman.register(_snowman, "FireworksRocketEntity", var1x -> DSL.optionalFields("FireworksItem", akn.l.in(_snowman)));
      _snowman.registerSimple(_snowman, "Boat");
      _snowman.register(_snowman, "Minecart", () -> DSL.optionalFields("DisplayTile", akn.q.in(_snowman), "Items", DSL.list(akn.l.in(_snowman))));
      c(_snowman, _snowman, "MinecartRideable");
      _snowman.register(_snowman, "MinecartChest", var1x -> DSL.optionalFields("DisplayTile", akn.q.in(_snowman), "Items", DSL.list(akn.l.in(_snowman))));
      c(_snowman, _snowman, "MinecartFurnace");
      c(_snowman, _snowman, "MinecartTNT");
      _snowman.register(_snowman, "MinecartSpawner", () -> DSL.optionalFields("DisplayTile", akn.q.in(_snowman), akn.s.in(_snowman)));
      _snowman.register(_snowman, "MinecartHopper", var1x -> DSL.optionalFields("DisplayTile", akn.q.in(_snowman), "Items", DSL.list(akn.l.in(_snowman))));
      c(_snowman, _snowman, "MinecartCommandBlock");
      a(_snowman, _snowman, "ArmorStand");
      a(_snowman, _snowman, "Creeper");
      a(_snowman, _snowman, "Skeleton");
      a(_snowman, _snowman, "Spider");
      a(_snowman, _snowman, "Giant");
      a(_snowman, _snowman, "Zombie");
      a(_snowman, _snowman, "Slime");
      a(_snowman, _snowman, "Ghast");
      a(_snowman, _snowman, "PigZombie");
      _snowman.register(_snowman, "Enderman", var1x -> DSL.optionalFields("carried", akn.q.in(_snowman), a(_snowman)));
      a(_snowman, _snowman, "CaveSpider");
      a(_snowman, _snowman, "Silverfish");
      a(_snowman, _snowman, "Blaze");
      a(_snowman, _snowman, "LavaSlime");
      a(_snowman, _snowman, "EnderDragon");
      a(_snowman, _snowman, "WitherBoss");
      a(_snowman, _snowman, "Bat");
      a(_snowman, _snowman, "Witch");
      a(_snowman, _snowman, "Endermite");
      a(_snowman, _snowman, "Guardian");
      a(_snowman, _snowman, "Pig");
      a(_snowman, _snowman, "Sheep");
      a(_snowman, _snowman, "Cow");
      a(_snowman, _snowman, "Chicken");
      a(_snowman, _snowman, "Squid");
      a(_snowman, _snowman, "Wolf");
      a(_snowman, _snowman, "MushroomCow");
      a(_snowman, _snowman, "SnowMan");
      a(_snowman, _snowman, "Ozelot");
      a(_snowman, _snowman, "VillagerGolem");
      _snowman.register(_snowman, "EntityHorse", var1x -> DSL.optionalFields("Items", DSL.list(akn.l.in(_snowman)), "ArmorItem", akn.l.in(_snowman), "SaddleItem", akn.l.in(_snowman), a(_snowman)));
      a(_snowman, _snowman, "Rabbit");
      _snowman.register(
         _snowman,
         "Villager",
         var1x -> DSL.optionalFields(
               "Inventory",
               DSL.list(akn.l.in(_snowman)),
               "Offers",
               DSL.optionalFields("Recipes", DSL.list(DSL.optionalFields("buy", akn.l.in(_snowman), "buyB", akn.l.in(_snowman), "sell", akn.l.in(_snowman)))),
               a(_snowman)
            )
      );
      _snowman.registerSimple(_snowman, "EnderCrystal");
      _snowman.registerSimple(_snowman, "AreaEffectCloud");
      _snowman.registerSimple(_snowman, "ShulkerBullet");
      a(_snowman, _snowman, "Shulker");
      return _snowman;
   }

   public Map<String, Supplier<TypeTemplate>> registerBlockEntities(Schema var1) {
      Map<String, Supplier<TypeTemplate>> _snowman = Maps.newHashMap();
      d(_snowman, _snowman, "Furnace");
      d(_snowman, _snowman, "Chest");
      _snowman.registerSimple(_snowman, "EnderChest");
      _snowman.register(_snowman, "RecordPlayer", var1x -> DSL.optionalFields("RecordItem", akn.l.in(_snowman)));
      d(_snowman, _snowman, "Trap");
      d(_snowman, _snowman, "Dropper");
      _snowman.registerSimple(_snowman, "Sign");
      _snowman.register(_snowman, "MobSpawner", var1x -> akn.s.in(_snowman));
      _snowman.registerSimple(_snowman, "Music");
      _snowman.registerSimple(_snowman, "Piston");
      d(_snowman, _snowman, "Cauldron");
      _snowman.registerSimple(_snowman, "EnchantTable");
      _snowman.registerSimple(_snowman, "Airportal");
      _snowman.registerSimple(_snowman, "Control");
      _snowman.registerSimple(_snowman, "Beacon");
      _snowman.registerSimple(_snowman, "Skull");
      _snowman.registerSimple(_snowman, "DLDetector");
      d(_snowman, _snowman, "Hopper");
      _snowman.registerSimple(_snowman, "Comparator");
      _snowman.register(_snowman, "FlowerPot", var1x -> DSL.optionalFields("Item", DSL.or(DSL.constType(DSL.intType()), akn.r.in(_snowman))));
      _snowman.registerSimple(_snowman, "Banner");
      _snowman.registerSimple(_snowman, "Structure");
      _snowman.registerSimple(_snowman, "EndGateway");
      return _snowman;
   }

   public void registerTypes(Schema var1, Map<String, Supplier<TypeTemplate>> var2, Map<String, Supplier<TypeTemplate>> var3) {
      _snowman.registerType(false, akn.a, DSL::remainder);
      _snowman.registerType(false, akn.b, () -> DSL.optionalFields("Inventory", DSL.list(akn.l.in(_snowman)), "EnderItems", DSL.list(akn.l.in(_snowman))));
      _snowman.registerType(
         false,
         akn.c,
         () -> DSL.fields(
               "Level",
               DSL.optionalFields("Entities", DSL.list(akn.o.in(_snowman)), "TileEntities", DSL.list(akn.k.in(_snowman)), "TileTicks", DSL.list(DSL.fields("i", akn.q.in(_snowman))))
            )
      );
      _snowman.registerType(true, akn.k, () -> DSL.taggedChoiceLazy("id", DSL.string(), _snowman));
      _snowman.registerType(true, akn.o, () -> DSL.optionalFields("Riding", akn.o.in(_snowman), akn.p.in(_snowman)));
      _snowman.registerType(false, akn.n, () -> DSL.constType(aln.a()));
      _snowman.registerType(true, akn.p, () -> DSL.taggedChoiceLazy("id", DSL.string(), _snowman));
      _snowman.registerType(
         true,
         akn.l,
         () -> DSL.hook(
               DSL.optionalFields(
                  "id",
                  DSL.or(DSL.constType(DSL.intType()), akn.r.in(_snowman)),
                  "tag",
                  DSL.optionalFields(
                     "EntityTag", akn.o.in(_snowman), "BlockEntityTag", akn.k.in(_snowman), "CanDestroy", DSL.list(akn.q.in(_snowman)), "CanPlaceOn", DSL.list(akn.q.in(_snowman))
                  )
               ),
               a,
               HookFunction.IDENTITY
            )
      );
      _snowman.registerType(false, akn.e, DSL::remainder);
      _snowman.registerType(false, akn.q, () -> DSL.or(DSL.constType(DSL.intType()), DSL.constType(aln.a())));
      _snowman.registerType(false, akn.r, () -> DSL.constType(aln.a()));
      _snowman.registerType(false, akn.g, DSL::remainder);
      _snowman.registerType(
         false,
         akn.h,
         () -> DSL.optionalFields(
               "data", DSL.optionalFields("Features", DSL.compoundList(akn.t.in(_snowman)), "Objectives", DSL.list(akn.u.in(_snowman)), "Teams", DSL.list(akn.v.in(_snowman)))
            )
      );
      _snowman.registerType(false, akn.t, DSL::remainder);
      _snowman.registerType(false, akn.u, DSL::remainder);
      _snowman.registerType(false, akn.v, DSL::remainder);
      _snowman.registerType(true, akn.s, DSL::remainder);
      _snowman.registerType(false, akn.j, DSL::remainder);
      _snowman.registerType(true, akn.y, DSL::remainder);
   }

   protected static <T> T a(Dynamic<T> var0, Map<String, String> var1, String var2) {
      return (T)_snowman.update("tag", var3 -> var3.update("BlockEntityTag", var2x -> {
            String _snowman = _snowman.get("id").asString("");
            String _snowmanx = _snowman.get(aln.a(_snowman));
            if (_snowmanx == null) {
               b.warn("Unable to resolve BlockEntity for ItemStack: {}", _snowman);
               return var2x;
            } else {
               return var2x.set("id", _snowman.createString(_snowmanx));
            }
         }).update("EntityTag", var2x -> {
            String _snowman = _snowman.get("id").asString("");
            return Objects.equals(aln.a(_snowman), "minecraft:armor_stand") ? var2x.set("id", _snowman.createString(_snowman)) : var2x;
         })).getValue();
   }
}
