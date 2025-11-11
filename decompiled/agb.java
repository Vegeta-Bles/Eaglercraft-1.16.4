import com.google.common.collect.ImmutableMap;
import com.mojang.datafixers.DSL;
import com.mojang.datafixers.DataFixer;
import com.mojang.datafixers.DataFixerBuilder;
import com.mojang.datafixers.Typed;
import com.mojang.datafixers.schemas.Schema;
import java.util.Map;
import java.util.Objects;
import java.util.function.BiFunction;
import java.util.function.UnaryOperator;

public class agb {
   private static final BiFunction<Integer, Schema, Schema> a = Schema::new;
   private static final BiFunction<Integer, Schema, Schema> b = aln::new;
   private static final DataFixer c = b();

   private static DataFixer b() {
      DataFixerBuilder _snowman = new DataFixerBuilder(w.a().getWorldVersion());
      a(_snowman);
      return _snowman.build(x.e());
   }

   public static DataFixer a() {
      return c;
   }

   private static void a(DataFixerBuilder var0) {
      Schema _snowman = _snowman.addSchema(99, anl::new);
      Schema _snowmanx = _snowman.addSchema(100, alo::new);
      _snowman.addFixer(new ahr(_snowmanx, true));
      Schema _snowmanxx = _snowman.addSchema(101, a);
      _snowman.addFixer(new agu(_snowmanxx, false));
      Schema _snowmanxxx = _snowman.addSchema(102, alp::new);
      _snowman.addFixer(new aiy(_snowmanxxx, true));
      _snowman.addFixer(new aja(_snowmanxxx, false));
      Schema _snowmanxxxx = _snowman.addSchema(105, a);
      _snowman.addFixer(new ajd(_snowmanxxxx, true));
      Schema _snowmanxxxxx = _snowman.addSchema(106, alr::new);
      _snowman.addFixer(new aju(_snowmanxxxxx, true));
      Schema _snowmanxxxxxx = _snowman.addSchema(107, als::new);
      _snowman.addFixer(new ahx(_snowmanxxxxxx, true));
      Schema _snowmanxxxxxxx = _snowman.addSchema(108, a);
      _snowman.addFixer(new aij(_snowmanxxxxxxx, true));
      Schema _snowmanxxxxxxxx = _snowman.addSchema(109, a);
      _snowman.addFixer(new ahs(_snowmanxxxxxxxx, true));
      Schema _snowmanxxxxxxxxx = _snowman.addSchema(110, a);
      _snowman.addFixer(new aht(_snowmanxxxxxxxxx, true));
      Schema _snowmanxxxxxxxxxx = _snowman.addSchema(111, a);
      _snowman.addFixer(new ahy(_snowmanxxxxxxxxxx, true));
      Schema _snowmanxxxxxxxxxxx = _snowman.addSchema(113, a);
      _snowman.addFixer(new aid(_snowmanxxxxxxxxxxx, true));
      Schema _snowmanxxxxxxxxxxxx = _snowman.addSchema(135, alu::new);
      _snowman.addFixer(new aif(_snowmanxxxxxxxxxxxx, true));
      Schema _snowmanxxxxxxxxxxxxx = _snowman.addSchema(143, alv::new);
      _snowman.addFixer(new ail(_snowmanxxxxxxxxxxxxx, true));
      Schema _snowmanxxxxxxxxxxxxxx = _snowman.addSchema(147, a);
      _snowman.addFixer(new ahl(_snowmanxxxxxxxxxxxxxx, true));
      Schema _snowmanxxxxxxxxxxxxxxx = _snowman.addSchema(165, a);
      _snowman.addFixer(new ajk(_snowmanxxxxxxxxxxxxxxx, true));
      Schema _snowmanxxxxxxxxxxxxxxxx = _snowman.addSchema(501, and::new);
      _snowman.addFixer(new age(_snowmanxxxxxxxxxxxxxxxx, "Add 1.10 entities fix", akn.p));
      Schema _snowmanxxxxxxxxxxxxxxxxx = _snowman.addSchema(502, a);
      _snowman.addFixer(
         ajb.a(
            _snowmanxxxxxxxxxxxxxxxxx,
            "cooked_fished item renamer",
            var0x -> Objects.equals(aln.a(var0x), "minecraft:cooked_fished") ? "minecraft:cooked_fish" : var0x
         )
      );
      _snowman.addFixer(new aip(_snowmanxxxxxxxxxxxxxxxxx, false));
      Schema _snowmanxxxxxxxxxxxxxxxxxx = _snowman.addSchema(505, a);
      _snowman.addFixer(new akc(_snowmanxxxxxxxxxxxxxxxxxx, false));
      Schema _snowmanxxxxxxxxxxxxxxxxxxx = _snowman.addSchema(700, ane::new);
      _snowman.addFixer(new ahq(_snowmanxxxxxxxxxxxxxxxxxxx, true));
      Schema _snowmanxxxxxxxxxxxxxxxxxxxx = _snowman.addSchema(701, anf::new);
      _snowman.addFixer(new aii(_snowmanxxxxxxxxxxxxxxxxxxxx, true));
      Schema _snowmanxxxxxxxxxxxxxxxxxxxxx = _snowman.addSchema(702, ang::new);
      _snowman.addFixer(new aio(_snowmanxxxxxxxxxxxxxxxxxxxxx, true));
      Schema _snowmanxxxxxxxxxxxxxxxxxxxxxx = _snowman.addSchema(703, anh::new);
      _snowman.addFixer(new ahu(_snowmanxxxxxxxxxxxxxxxxxxxxxx, true));
      Schema _snowmanxxxxxxxxxxxxxxxxxxxxxxx = _snowman.addSchema(704, ani::new);
      _snowman.addFixer(new agq(_snowmanxxxxxxxxxxxxxxxxxxxxxxx, true));
      Schema _snowmanxxxxxxxxxxxxxxxxxxxxxxxx = _snowman.addSchema(705, anj::new);
      _snowman.addFixer(new ahv(_snowmanxxxxxxxxxxxxxxxxxxxxxxxx, true));
      Schema _snowmanxxxxxxxxxxxxxxxxxxxxxxxxx = _snowman.addSchema(804, b);
      _snowman.addFixer(new aiw(_snowmanxxxxxxxxxxxxxxxxxxxxxxxxx, true));
      Schema _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxx = _snowman.addSchema(806, b);
      _snowman.addFixer(new ajj(_snowmanxxxxxxxxxxxxxxxxxxxxxxxxxx, false));
      Schema _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxx = _snowman.addSchema(808, ank::new);
      _snowman.addFixer(new age(_snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxx, "added shulker box", akn.k));
      Schema _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxx = _snowman.addSchema(808, 1, b);
      _snowman.addFixer(new aig(_snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxx, false));
      Schema _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxx = _snowman.addSchema(813, b);
      _snowman.addFixer(new ajc(_snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxx, false));
      _snowman.addFixer(new agt(_snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxx, false));
      Schema _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx = _snowman.addSchema(816, b);
      _snowman.addFixer(new akf(_snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx, false));
      Schema _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx = _snowman.addSchema(820, b);
      _snowman.addFixer(ajb.a(_snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx, "totem item renamer", a("minecraft:totem", "minecraft:totem_of_undying")));
      Schema _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx = _snowman.addSchema(1022, alq::new);
      _snowman.addFixer(new alj(_snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx, "added shoulder entities to players", akn.b));
      Schema _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx = _snowman.addSchema(1125, alt::new);
      _snowman.addFixer(new agi(_snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx, true));
      _snowman.addFixer(new agj(_snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx, false));
      Schema _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx = _snowman.addSchema(1344, b);
      _snowman.addFixer(new akd(_snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx, false));
      Schema _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx = _snowman.addSchema(1446, b);
      _snowman.addFixer(new ake(_snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx, false));
      Schema _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx = _snowman.addSchema(1450, b);
      _snowman.addFixer(new aha(_snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx, false));
      Schema _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx = _snowman.addSchema(1451, alw::new);
      _snowman.addFixer(new age(_snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx, "AddTrappedChestFix", akn.k));
      Schema _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx = _snowman.addSchema(1451, 1, alx::new);
      _snowman.addFixer(new ahe(_snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx, true));
      Schema _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx = _snowman.addSchema(1451, 2, aly::new);
      _snowman.addFixer(new ago(_snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx, true));
      Schema _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx = _snowman.addSchema(1451, 3, alz::new);
      _snowman.addFixer(new ahm(_snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx, true));
      _snowman.addFixer(new ajf(_snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx, false));
      Schema _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx = _snowman.addSchema(1451, 4, ama::new);
      _snowman.addFixer(new agw(_snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx, true));
      _snowman.addFixer(new ajh(_snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx, false));
      Schema _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx = _snowman.addSchema(1451, 5, amb::new);
      _snowman.addFixer(new age(_snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx, "RemoveNoteBlockFlowerPotFix", akn.k));
      _snowman.addFixer(new ajg(_snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx, false));
      _snowman.addFixer(new ain(_snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx, false));
      _snowman.addFixer(new agn(_snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx, false));
      _snowman.addFixer(new ajp(_snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx, false));
      Schema _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx = _snowman.addSchema(1451, 6, amc::new);
      _snowman.addFixer(new akx(_snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx, true));
      _snowman.addFixer(new agr(_snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx, false));
      Schema _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx = _snowman.addSchema(1451, 7, amd::new);
      _snowman.addFixer(new aku(_snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx, true));
      Schema _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx = _snowman.addSchema(1451, 7, b);
      _snowman.addFixer(new alg(_snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx, false));
      Schema _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx = _snowman.addSchema(1456, b);
      _snowman.addFixer(new ahw(_snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx, false));
      Schema _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx = _snowman.addSchema(1458, b);
      _snowman.addFixer(new ahp(_snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx, false));
      _snowman.addFixer(new aix(_snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx, false));
      _snowman.addFixer(new agp(_snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx, false));
      Schema _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx = _snowman.addSchema(1460, ame::new);
      _snowman.addFixer(new ahz(_snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx, false));
      Schema _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx = _snowman.addSchema(1466, amf::new);
      _snowman.addFixer(new ahi(_snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx, true));
      Schema _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx = _snowman.addSchema(1470, amg::new);
      _snowman.addFixer(new age(_snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx, "Add 1.13 entities fix", akn.p));
      Schema _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx = _snowman.addSchema(1474, b);
      _snowman.addFixer(new ahj(_snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx, false));
      _snowman.addFixer(
         agx.a(
            _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx,
            "Colorless shulker block fixer",
            var0x -> Objects.equals(aln.a(var0x), "minecraft:purple_shulker_box") ? "minecraft:shulker_box" : var0x
         )
      );
      _snowman.addFixer(
         ajb.a(
            _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx,
            "Colorless shulker item fixer",
            var0x -> Objects.equals(aln.a(var0x), "minecraft:purple_shulker_box") ? "minecraft:shulker_box" : var0x
         )
      );
      Schema _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx = _snowman.addSchema(1475, b);
      _snowman.addFixer(
         agx.a(
            _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx,
            "Flowing fixer",
            a(ImmutableMap.of("minecraft:flowing_water", "minecraft:water", "minecraft:flowing_lava", "minecraft:lava"))
         )
      );
      Schema _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx = _snowman.addSchema(1480, b);
      _snowman.addFixer(agx.a(_snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx, "Rename coral blocks", a(akr.a)));
      _snowman.addFixer(ajb.a(_snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx, "Rename coral items", a(akr.a)));
      Schema _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx = _snowman.addSchema(1481, amh::new);
      _snowman.addFixer(new age(_snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx, "Add conduit", akn.k));
      Schema _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx = _snowman.addSchema(1483, ami::new);
      _snowman.addFixer(new aib(_snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx, true));
      _snowman.addFixer(ajb.a(_snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx, "Rename pufferfish egg item", a(aib.a)));
      Schema _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx = _snowman.addSchema(1484, b);
      _snowman.addFixer(
         ajb.a(
            _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx,
            "Rename seagrass items",
            a(ImmutableMap.of("minecraft:sea_grass", "minecraft:seagrass", "minecraft:tall_sea_grass", "minecraft:tall_seagrass"))
         )
      );
      _snowman.addFixer(
         agx.a(
            _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx,
            "Rename seagrass blocks",
            a(ImmutableMap.of("minecraft:sea_grass", "minecraft:seagrass", "minecraft:tall_sea_grass", "minecraft:tall_seagrass"))
         )
      );
      _snowman.addFixer(new aiu(_snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx, false));
      Schema _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx = _snowman.addSchema(1486, amj::new);
      _snowman.addFixer(new aho(_snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx, true));
      _snowman.addFixer(ajb.a(_snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx, "Rename cod/salmon egg items", a(aho.b)));
      Schema _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx = _snowman.addSchema(1487, b);
      _snowman.addFixer(
         ajb.a(
            _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx,
            "Rename prismarine_brick(s)_* blocks",
            a(
               ImmutableMap.of(
                  "minecraft:prismarine_bricks_slab",
                  "minecraft:prismarine_brick_slab",
                  "minecraft:prismarine_bricks_stairs",
                  "minecraft:prismarine_brick_stairs"
               )
            )
         )
      );
      _snowman.addFixer(
         agx.a(
            _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx,
            "Rename prismarine_brick(s)_* items",
            a(
               ImmutableMap.of(
                  "minecraft:prismarine_bricks_slab",
                  "minecraft:prismarine_brick_slab",
                  "minecraft:prismarine_bricks_stairs",
                  "minecraft:prismarine_brick_stairs"
               )
            )
         )
      );
      Schema _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx = _snowman.addSchema(1488, b);
      _snowman.addFixer(
         agx.a(
            _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx,
            "Rename kelp/kelptop",
            a(ImmutableMap.of("minecraft:kelp_top", "minecraft:kelp", "minecraft:kelp", "minecraft:kelp_plant"))
         )
      );
      _snowman.addFixer(ajb.a(_snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx, "Rename kelptop", a("minecraft:kelp_top", "minecraft:kelp")));
      _snowman.addFixer(
         new ajv(
            _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx, false, "Command block block entity custom name fix", akn.k, "minecraft:command_block"
         ) {
            @Override
            protected Typed<?> a(Typed<?> var1) {
               return _snowman.update(DSL.remainderFinder(), ahp::a);
            }
         }
      );
      _snowman.addFixer(
         new ajv(
            _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx,
            false,
            "Command block minecart custom name fix",
            akn.p,
            "minecraft:commandblock_minecart"
         ) {
            @Override
            protected Typed<?> a(Typed<?> var1) {
               return _snowman.update(DSL.remainderFinder(), ahp::a);
            }
         }
      );
      _snowman.addFixer(new aiv(_snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx, false));
      Schema _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx = _snowman.addSchema(1490, b);
      _snowman.addFixer(agx.a(_snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx, "Rename melon_block", a("minecraft:melon_block", "minecraft:melon")));
      _snowman.addFixer(
         ajb.a(
            _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx,
            "Rename melon_block/melon/speckled_melon",
            a(
               ImmutableMap.of(
                  "minecraft:melon_block",
                  "minecraft:melon",
                  "minecraft:melon",
                  "minecraft:melon_slice",
                  "minecraft:speckled_melon",
                  "minecraft:glistering_melon_slice"
               )
            )
         )
      );
      Schema _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx = _snowman.addSchema(1492, b);
      _snowman.addFixer(new ahh(_snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx, false));
      Schema _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx = _snowman.addSchema(1494, b);
      _snowman.addFixer(new aje(_snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx, false));
      Schema _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx = _snowman.addSchema(1496, b);
      _snowman.addFixer(new ajn(_snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx, false));
      Schema _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx = _snowman.addSchema(1500, b);
      _snowman.addFixer(new ags(_snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx, false));
      Schema _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx = _snowman.addSchema(1501, b);
      _snowman.addFixer(new agf(_snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx, false));
      Schema _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx = _snowman.addSchema(1502, b);
      _snowman.addFixer(new akj(_snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx, false));
      Schema _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx = _snowman.addSchema(1506, b);
      _snowman.addFixer(new ajo(_snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx, false));
      Schema _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx = _snowman.addSchema(1510, amk::new);
      _snowman.addFixer(agx.a(_snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx, "Block renamening fix", a(aik.b)));
      _snowman.addFixer(ajb.a(_snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx, "Item renamening fix", a(aik.c)));
      _snowman.addFixer(new akl(_snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx, false));
      _snowman.addFixer(new aik(_snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx, true));
      _snowman.addFixer(new ala(_snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx, false));
      Schema _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx = _snowman.addSchema(1514, b);
      _snowman.addFixer(new ajx(_snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx, false));
      _snowman.addFixer(new alb(_snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx, false));
      _snowman.addFixer(new ajy(_snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx, false));
      Schema _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx = _snowman.addSchema(1515, b);
      _snowman.addFixer(agx.a(_snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx, "Rename coral fan blocks", a(akq.a)));
      Schema _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx = _snowman.addSchema(1624, b);
      _snowman.addFixer(new alc(_snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx, false));
      Schema _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx = _snowman.addSchema(1800, aml::new);
      _snowman.addFixer(new age(_snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx, "Added 1.14 mobs fix", akn.p));
      _snowman.addFixer(ajb.a(_snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx, "Rename dye items", a(ahk.a)));
      Schema _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx = _snowman.addSchema(1801, amm::new);
      _snowman.addFixer(new age(_snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx, "Added Illager Beast", akn.p));
      Schema _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx = _snowman.addSchema(1802, b);
      _snowman.addFixer(
         agx.a(
            _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx,
            "Rename sign blocks & stone slabs",
            a(
               ImmutableMap.of(
                  "minecraft:stone_slab",
                  "minecraft:smooth_stone_slab",
                  "minecraft:sign",
                  "minecraft:oak_sign",
                  "minecraft:wall_sign",
                  "minecraft:oak_wall_sign"
               )
            )
         )
      );
      _snowman.addFixer(
         ajb.a(
            _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx,
            "Rename sign item & stone slabs",
            a(ImmutableMap.of("minecraft:stone_slab", "minecraft:smooth_stone_slab", "minecraft:sign", "minecraft:oak_sign"))
         )
      );
      Schema _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx = _snowman.addSchema(1803, b);
      _snowman.addFixer(new aiz(_snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx, false));
      Schema _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx = _snowman.addSchema(1904, amn::new);
      _snowman.addFixer(new age(_snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx, "Added Cats", akn.p));
      _snowman.addFixer(new ahn(_snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx, false));
      Schema _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx = _snowman.addSchema(1905, b);
      _snowman.addFixer(new ahf(_snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx, false));
      Schema _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx = _snowman.addSchema(1906, amo::new);
      _snowman.addFixer(new age(_snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx, "Add POI Blocks", akn.k));
      Schema _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx = _snowman.addSchema(1909, amp::new);
      _snowman.addFixer(new age(_snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx, "Add jigsaw", akn.k));
      Schema _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx = _snowman.addSchema(1911, b);
      _snowman.addFixer(new ahg(_snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx, false));
      Schema _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx = _snowman.addSchema(1917, b);
      _snowman.addFixer(new ahb(_snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx, false));
      Schema _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx = _snowman.addSchema(1918, b);
      _snowman.addFixer(new ald(_snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx, "minecraft:villager"));
      _snowman.addFixer(new ald(_snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx, "minecraft:zombie_villager"));
      Schema _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx = _snowman.addSchema(1920, amq::new);
      _snowman.addFixer(new ajw(_snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx, false));
      _snowman.addFixer(new age(_snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx, "Add campfire", akn.k));
      Schema _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx = _snowman.addSchema(1925, b);
      _snowman.addFixer(new ajr(_snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx, false));
      Schema _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx = _snowman.addSchema(1928, amr::new);
      _snowman.addFixer(new aic(_snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx, true));
      _snowman.addFixer(ajb.a(_snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx, "Rename ravager egg item", a(aic.a)));
      Schema _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx = _snowman.addSchema(1929, ams::new);
      _snowman.addFixer(
         new age(_snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx, "Add Wandering Trader and Trader Llama", akn.p)
      );
      Schema _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx = _snowman.addSchema(1931, amt::new);
      _snowman.addFixer(new age(_snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx, "Added Fox", akn.p));
      Schema _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx = _snowman.addSchema(1936, b);
      _snowman.addFixer(new akb(_snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx, false));
      Schema _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx = _snowman.addSchema(1946, b);
      _snowman.addFixer(new aks(_snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx, false));
      Schema _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx = _snowman.addSchema(1948, b);
      _snowman.addFixer(new aka(_snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx, false));
      Schema _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx = _snowman.addSchema(1953, b);
      _snowman.addFixer(new ajz(_snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx, false));
      Schema _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx = _snowman.addSchema(1955, b);
      _snowman.addFixer(new alf(_snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx, false));
      _snowman.addFixer(new alk(_snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx, false));
      Schema _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx = _snowman.addSchema(1961, b);
      _snowman.addFixer(new ahd(_snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx, false));
      Schema _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx = _snowman.addSchema(1963, b);
      _snowman.addFixer(new ako(_snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx, false));
      Schema _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx = _snowman.addSchema(2100, amu::new);
      _snowman.addFixer(new age(_snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx, "Added Bee and Bee Stinger", akn.p));
      _snowman.addFixer(new age(_snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx, "Add beehive", akn.k));
      _snowman.addFixer(
         new akk(
            _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx,
            false,
            "Rename sugar recipe",
            a("minecraft:sugar", "sugar_from_sugar_cane")
         )
      );
      _snowman.addFixer(
         new agg(
            _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx,
            false,
            "Rename sugar recipe advancement",
            a("minecraft:recipes/misc/sugar", "minecraft:recipes/misc/sugar_from_sugar_cane")
         )
      );
      Schema _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx = _snowman.addSchema(2202, b);
      _snowman.addFixer(new ahc(_snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx, false));
      Schema _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx = _snowman.addSchema(2209, b);
      _snowman.addFixer(
         ajb.a(
            _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx,
            "Rename bee_hive item to beehive",
            a("minecraft:bee_hive", "minecraft:beehive")
         )
      );
      _snowman.addFixer(new agk(_snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx));
      _snowman.addFixer(
         agx.a(
            _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx,
            "Rename bee_hive block to beehive",
            a("minecraft:bee_hive", "minecraft:beehive")
         )
      );
      Schema _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx = _snowman.addSchema(2211, b);
      _snowman.addFixer(new akz(_snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx, false));
      Schema _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx = _snowman.addSchema(2218, b);
      _snowman.addFixer(new air(_snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx, false));
      Schema _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx = _snowman.addSchema(2501, amv::new);
      _snowman.addFixer(new ais(_snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx, true));
      Schema _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx = _snowman.addSchema(2502, amw::new);
      _snowman.addFixer(new age(_snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx, "Added Hoglin", akn.p));
      Schema _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx = _snowman.addSchema(2503, b);
      _snowman.addFixer(new alh(_snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx, false));
      _snowman.addFixer(
         new agg(
            _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx,
            false,
            "Composter category change",
            a("minecraft:recipes/misc/composter", "minecraft:recipes/decorations/composter")
         )
      );
      Schema _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx = _snowman.addSchema(2505, amx::new);
      _snowman.addFixer(new age(_snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx, "Added Piglin", akn.p));
      _snowman.addFixer(new ajs(_snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx, "minecraft:villager"));
      Schema _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx = _snowman.addSchema(2508, b);
      _snowman.addFixer(
         ajb.a(
            _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx,
            "Renamed fungi items to fungus",
            a(ImmutableMap.of("minecraft:warped_fungi", "minecraft:warped_fungus", "minecraft:crimson_fungi", "minecraft:crimson_fungus"))
         )
      );
      _snowman.addFixer(
         agx.a(
            _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx,
            "Renamed fungi blocks to fungus",
            a(ImmutableMap.of("minecraft:warped_fungi", "minecraft:warped_fungus", "minecraft:crimson_fungi", "minecraft:crimson_fungus"))
         )
      );
      Schema _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx = _snowman.addSchema(2509, amy::new);
      _snowman.addFixer(new aiq(_snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx));
      _snowman.addFixer(
         ajb.a(
            _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx,
            "Rename zombie pigman egg item",
            a(aiq.a)
         )
      );
      Schema _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx = _snowman.addSchema(2511, b);
      _snowman.addFixer(new aia(_snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx));
      Schema _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx = _snowman.addSchema(2514, b);
      _snowman.addFixer(new aim(_snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx));
      _snowman.addFixer(new agv(_snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx));
      _snowman.addFixer(new akh(_snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx));
      _snowman.addFixer(new ajq(_snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx));
      _snowman.addFixer(new akt(_snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx));
      _snowman.addFixer(new aji(_snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx));
      Schema _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx = _snowman.addSchema(2516, b);
      _snowman.addFixer(new ait(_snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx, "minecraft:villager"));
      _snowman.addFixer(
         new ait(_snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx, "minecraft:zombie_villager")
      );
      Schema _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx = _snowman.addSchema(2518, b);
      _snowman.addFixer(new ajl(_snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx, false));
      _snowman.addFixer(new ajm(_snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx, false));
      Schema _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx = _snowman.addSchema(2519, amz::new);
      _snowman.addFixer(
         new age(_snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx, "Added Strider", akn.p)
      );
      Schema _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx = _snowman.addSchema(2522, ana::new);
      _snowman.addFixer(
         new age(_snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx, "Added Zoglin", akn.p)
      );
      Schema _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx = _snowman.addSchema(2523, b);
      _snowman.addFixer(new agh(_snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx));
      Schema _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx = _snowman.addSchema(2527, b);
      _snowman.addFixer(new agm(_snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx));
      Schema _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx = _snowman.addSchema(2528, b);
      _snowman.addFixer(
         ajb.a(
            _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx,
            "Rename soul fire torch and soul fire lantern",
            a(ImmutableMap.of("minecraft:soul_fire_torch", "minecraft:soul_torch", "minecraft:soul_fire_lantern", "minecraft:soul_lantern"))
         )
      );
      _snowman.addFixer(
         agx.a(
            _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx,
            "Rename soul fire torch and soul fire lantern",
            a(
               ImmutableMap.of(
                  "minecraft:soul_fire_torch",
                  "minecraft:soul_torch",
                  "minecraft:soul_fire_wall_torch",
                  "minecraft:soul_wall_torch",
                  "minecraft:soul_fire_lantern",
                  "minecraft:soul_lantern"
               )
            )
         )
      );
      Schema _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx = _snowman.addSchema(2529, b);
      _snowman.addFixer(new aky(_snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx, false));
      Schema _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx = _snowman.addSchema(2531, b);
      _snowman.addFixer(new akm(_snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx));
      Schema _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx = _snowman.addSchema(2533, b);
      _snowman.addFixer(new ale(_snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx));
      Schema _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx = _snowman.addSchema(2535, b);
      _snowman.addFixer(new aih(_snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx));
      Schema _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx = _snowman.addSchema(2550, b);
      _snowman.addFixer(new ali(_snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx));
      Schema _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx = _snowman.addSchema(
         2551, anb::new
      );
      _snowman.addFixer(
         new alj(
            _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx,
            "add types to WorldGenData",
            akn.y
         )
      );
      Schema _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx = _snowman.addSchema(2552, b);
      _snowman.addFixer(
         new akp(
            _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx,
            false,
            "Nether biome rename",
            ImmutableMap.of("minecraft:nether", "minecraft:nether_wastes")
         )
      );
      Schema _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx = _snowman.addSchema(2553, b);
      _snowman.addFixer(new agl(_snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx, false));
      Schema _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx = _snowman.addSchema(2558, b);
      _snowman.addFixer(new ajt(_snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx, false));
      _snowman.addFixer(
         new akg(
            _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx,
            false,
            "Rename swapHands setting",
            "key_key.swapHands",
            "key_key.swapOffhand"
         )
      );
      Schema _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx = _snowman.addSchema(
         2568, anc::new
      );
      _snowman.addFixer(
         new age(
            _snowmanxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx,
            "Added Piglin Brute",
            akn.p
         )
      );
   }

   private static UnaryOperator<String> a(Map<String, String> var0) {
      return var1 -> _snowman.getOrDefault(var1, var1);
   }

   private static UnaryOperator<String> a(String var0, String var1) {
      return var2 -> Objects.equals(var2, _snowman) ? _snowman : var2;
   }
}
