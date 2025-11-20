package net.minecraft.datafixer.fix;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import com.mojang.datafixers.DataFix;
import com.mojang.datafixers.DataFixUtils;
import com.mojang.datafixers.TypeRewriteRule;
import com.mojang.datafixers.schemas.Schema;
import com.mojang.datafixers.types.Type;
import com.mojang.serialization.Dynamic;
import it.unimi.dsi.fastutil.ints.Int2ObjectLinkedOpenHashMap;
import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import it.unimi.dsi.fastutil.ints.Int2ObjectOpenHashMap;
import it.unimi.dsi.fastutil.ints.IntArrayList;
import it.unimi.dsi.fastutil.ints.IntList;
import it.unimi.dsi.fastutil.ints.IntListIterator;
import it.unimi.dsi.fastutil.ints.Int2ObjectMap.Entry;
import it.unimi.dsi.fastutil.objects.ObjectIterator;
import java.nio.ByteBuffer;
import java.util.Arrays;
import java.util.BitSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import javax.annotation.Nullable;
import net.minecraft.datafixer.TypeReferences;
import net.minecraft.util.collection.Int2ObjectBiMap;
import net.minecraft.util.math.WordPackedArray;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ChunkPalettedStorageFix extends DataFix {
   private static final Logger LOGGER = LogManager.getLogger();
   private static final BitSet BLOCKS_NEEDING_SIDE_UPDATE = new BitSet(256);
   private static final BitSet BLOCKS_NEEDING_IN_PLACE_UPDATE = new BitSet(256);
   private static final Dynamic<?> PUMPKIN = BlockStateFlattening.parseState("{Name:'minecraft:pumpkin'}");
   private static final Dynamic<?> PODZOL = BlockStateFlattening.parseState("{Name:'minecraft:podzol',Properties:{snowy:'true'}}");
   private static final Dynamic<?> SNOWY_GRASS = BlockStateFlattening.parseState("{Name:'minecraft:grass_block',Properties:{snowy:'true'}}");
   private static final Dynamic<?> SNOWY_MYCELIUM = BlockStateFlattening.parseState("{Name:'minecraft:mycelium',Properties:{snowy:'true'}}");
   private static final Dynamic<?> SUNFLOWER_UPPER = BlockStateFlattening.parseState("{Name:'minecraft:sunflower',Properties:{half:'upper'}}");
   private static final Dynamic<?> LILAC_UPPER = BlockStateFlattening.parseState("{Name:'minecraft:lilac',Properties:{half:'upper'}}");
   private static final Dynamic<?> GRASS_UPPER = BlockStateFlattening.parseState("{Name:'minecraft:tall_grass',Properties:{half:'upper'}}");
   private static final Dynamic<?> FERN_UPPER = BlockStateFlattening.parseState("{Name:'minecraft:large_fern',Properties:{half:'upper'}}");
   private static final Dynamic<?> ROSE_UPPER = BlockStateFlattening.parseState("{Name:'minecraft:rose_bush',Properties:{half:'upper'}}");
   private static final Dynamic<?> PEONY_UPPER = BlockStateFlattening.parseState("{Name:'minecraft:peony',Properties:{half:'upper'}}");
   private static final Map<String, Dynamic<?>> FLOWER_POT = (Map<String, Dynamic<?>>)DataFixUtils.make(Maps.newHashMap(), _snowman -> {
      _snowman.put("minecraft:air0", BlockStateFlattening.parseState("{Name:'minecraft:flower_pot'}"));
      _snowman.put("minecraft:red_flower0", BlockStateFlattening.parseState("{Name:'minecraft:potted_poppy'}"));
      _snowman.put("minecraft:red_flower1", BlockStateFlattening.parseState("{Name:'minecraft:potted_blue_orchid'}"));
      _snowman.put("minecraft:red_flower2", BlockStateFlattening.parseState("{Name:'minecraft:potted_allium'}"));
      _snowman.put("minecraft:red_flower3", BlockStateFlattening.parseState("{Name:'minecraft:potted_azure_bluet'}"));
      _snowman.put("minecraft:red_flower4", BlockStateFlattening.parseState("{Name:'minecraft:potted_red_tulip'}"));
      _snowman.put("minecraft:red_flower5", BlockStateFlattening.parseState("{Name:'minecraft:potted_orange_tulip'}"));
      _snowman.put("minecraft:red_flower6", BlockStateFlattening.parseState("{Name:'minecraft:potted_white_tulip'}"));
      _snowman.put("minecraft:red_flower7", BlockStateFlattening.parseState("{Name:'minecraft:potted_pink_tulip'}"));
      _snowman.put("minecraft:red_flower8", BlockStateFlattening.parseState("{Name:'minecraft:potted_oxeye_daisy'}"));
      _snowman.put("minecraft:yellow_flower0", BlockStateFlattening.parseState("{Name:'minecraft:potted_dandelion'}"));
      _snowman.put("minecraft:sapling0", BlockStateFlattening.parseState("{Name:'minecraft:potted_oak_sapling'}"));
      _snowman.put("minecraft:sapling1", BlockStateFlattening.parseState("{Name:'minecraft:potted_spruce_sapling'}"));
      _snowman.put("minecraft:sapling2", BlockStateFlattening.parseState("{Name:'minecraft:potted_birch_sapling'}"));
      _snowman.put("minecraft:sapling3", BlockStateFlattening.parseState("{Name:'minecraft:potted_jungle_sapling'}"));
      _snowman.put("minecraft:sapling4", BlockStateFlattening.parseState("{Name:'minecraft:potted_acacia_sapling'}"));
      _snowman.put("minecraft:sapling5", BlockStateFlattening.parseState("{Name:'minecraft:potted_dark_oak_sapling'}"));
      _snowman.put("minecraft:red_mushroom0", BlockStateFlattening.parseState("{Name:'minecraft:potted_red_mushroom'}"));
      _snowman.put("minecraft:brown_mushroom0", BlockStateFlattening.parseState("{Name:'minecraft:potted_brown_mushroom'}"));
      _snowman.put("minecraft:deadbush0", BlockStateFlattening.parseState("{Name:'minecraft:potted_dead_bush'}"));
      _snowman.put("minecraft:tallgrass2", BlockStateFlattening.parseState("{Name:'minecraft:potted_fern'}"));
      _snowman.put("minecraft:cactus0", BlockStateFlattening.lookupState(2240));
   });
   private static final Map<String, Dynamic<?>> SKULL = (Map<String, Dynamic<?>>)DataFixUtils.make(Maps.newHashMap(), _snowman -> {
      buildSkull(_snowman, 0, "skeleton", "skull");
      buildSkull(_snowman, 1, "wither_skeleton", "skull");
      buildSkull(_snowman, 2, "zombie", "head");
      buildSkull(_snowman, 3, "player", "head");
      buildSkull(_snowman, 4, "creeper", "head");
      buildSkull(_snowman, 5, "dragon", "head");
   });
   private static final Map<String, Dynamic<?>> DOOR = (Map<String, Dynamic<?>>)DataFixUtils.make(Maps.newHashMap(), _snowman -> {
      buildDoor(_snowman, "oak_door", 1024);
      buildDoor(_snowman, "iron_door", 1136);
      buildDoor(_snowman, "spruce_door", 3088);
      buildDoor(_snowman, "birch_door", 3104);
      buildDoor(_snowman, "jungle_door", 3120);
      buildDoor(_snowman, "acacia_door", 3136);
      buildDoor(_snowman, "dark_oak_door", 3152);
   });
   private static final Map<String, Dynamic<?>> NOTE_BLOCK = (Map<String, Dynamic<?>>)DataFixUtils.make(Maps.newHashMap(), _snowman -> {
      for (int _snowmanx = 0; _snowmanx < 26; _snowmanx++) {
         _snowman.put("true" + _snowmanx, BlockStateFlattening.parseState("{Name:'minecraft:note_block',Properties:{powered:'true',note:'" + _snowmanx + "'}}"));
         _snowman.put("false" + _snowmanx, BlockStateFlattening.parseState("{Name:'minecraft:note_block',Properties:{powered:'false',note:'" + _snowmanx + "'}}"));
      }
   });
   private static final Int2ObjectMap<String> COLORS = (Int2ObjectMap<String>)DataFixUtils.make(new Int2ObjectOpenHashMap(), _snowman -> {
      _snowman.put(0, "white");
      _snowman.put(1, "orange");
      _snowman.put(2, "magenta");
      _snowman.put(3, "light_blue");
      _snowman.put(4, "yellow");
      _snowman.put(5, "lime");
      _snowman.put(6, "pink");
      _snowman.put(7, "gray");
      _snowman.put(8, "light_gray");
      _snowman.put(9, "cyan");
      _snowman.put(10, "purple");
      _snowman.put(11, "blue");
      _snowman.put(12, "brown");
      _snowman.put(13, "green");
      _snowman.put(14, "red");
      _snowman.put(15, "black");
   });
   private static final Map<String, Dynamic<?>> BED = (Map<String, Dynamic<?>>)DataFixUtils.make(Maps.newHashMap(), _snowman -> {
      ObjectIterator var1 = COLORS.int2ObjectEntrySet().iterator();

      while (var1.hasNext()) {
         Entry<String> _snowmanx = (Entry<String>)var1.next();
         if (!Objects.equals(_snowmanx.getValue(), "red")) {
            buildBed(_snowman, _snowmanx.getIntKey(), (String)_snowmanx.getValue());
         }
      }
   });
   private static final Map<String, Dynamic<?>> BANNER = (Map<String, Dynamic<?>>)DataFixUtils.make(Maps.newHashMap(), _snowman -> {
      ObjectIterator var1 = COLORS.int2ObjectEntrySet().iterator();

      while (var1.hasNext()) {
         Entry<String> _snowmanx = (Entry<String>)var1.next();
         if (!Objects.equals(_snowmanx.getValue(), "white")) {
            buildBanner(_snowman, 15 - _snowmanx.getIntKey(), (String)_snowmanx.getValue());
         }
      }
   });
   private static final Dynamic<?> AIR = BlockStateFlattening.lookupState(0);

   public ChunkPalettedStorageFix(Schema outputSchema, boolean changesType) {
      super(outputSchema, changesType);
   }

   private static void buildSkull(Map<String, Dynamic<?>> out, int _snowman, String mob, String block) {
      out.put(_snowman + "north", BlockStateFlattening.parseState("{Name:'minecraft:" + mob + "_wall_" + block + "',Properties:{facing:'north'}}"));
      out.put(_snowman + "east", BlockStateFlattening.parseState("{Name:'minecraft:" + mob + "_wall_" + block + "',Properties:{facing:'east'}}"));
      out.put(_snowman + "south", BlockStateFlattening.parseState("{Name:'minecraft:" + mob + "_wall_" + block + "',Properties:{facing:'south'}}"));
      out.put(_snowman + "west", BlockStateFlattening.parseState("{Name:'minecraft:" + mob + "_wall_" + block + "',Properties:{facing:'west'}}"));

      for (int _snowmanx = 0; _snowmanx < 16; _snowmanx++) {
         out.put(_snowman + "" + _snowmanx, BlockStateFlattening.parseState("{Name:'minecraft:" + mob + "_" + block + "',Properties:{rotation:'" + _snowmanx + "'}}"));
      }
   }

   private static void buildDoor(Map<String, Dynamic<?>> out, String name, int _snowman) {
      out.put(
         "minecraft:" + name + "eastlowerleftfalsefalse",
         BlockStateFlattening.parseState("{Name:'minecraft:" + name + "',Properties:{facing:'east',half:'lower',hinge:'left',open:'false',powered:'false'}}")
      );
      out.put(
         "minecraft:" + name + "eastlowerleftfalsetrue",
         BlockStateFlattening.parseState("{Name:'minecraft:" + name + "',Properties:{facing:'east',half:'lower',hinge:'left',open:'false',powered:'true'}}")
      );
      out.put(
         "minecraft:" + name + "eastlowerlefttruefalse",
         BlockStateFlattening.parseState("{Name:'minecraft:" + name + "',Properties:{facing:'east',half:'lower',hinge:'left',open:'true',powered:'false'}}")
      );
      out.put(
         "minecraft:" + name + "eastlowerlefttruetrue",
         BlockStateFlattening.parseState("{Name:'minecraft:" + name + "',Properties:{facing:'east',half:'lower',hinge:'left',open:'true',powered:'true'}}")
      );
      out.put("minecraft:" + name + "eastlowerrightfalsefalse", BlockStateFlattening.lookupState(_snowman));
      out.put(
         "minecraft:" + name + "eastlowerrightfalsetrue",
         BlockStateFlattening.parseState("{Name:'minecraft:" + name + "',Properties:{facing:'east',half:'lower',hinge:'right',open:'false',powered:'true'}}")
      );
      out.put("minecraft:" + name + "eastlowerrighttruefalse", BlockStateFlattening.lookupState(_snowman + 4));
      out.put(
         "minecraft:" + name + "eastlowerrighttruetrue",
         BlockStateFlattening.parseState("{Name:'minecraft:" + name + "',Properties:{facing:'east',half:'lower',hinge:'right',open:'true',powered:'true'}}")
      );
      out.put("minecraft:" + name + "eastupperleftfalsefalse", BlockStateFlattening.lookupState(_snowman + 8));
      out.put("minecraft:" + name + "eastupperleftfalsetrue", BlockStateFlattening.lookupState(_snowman + 10));
      out.put(
         "minecraft:" + name + "eastupperlefttruefalse",
         BlockStateFlattening.parseState("{Name:'minecraft:" + name + "',Properties:{facing:'east',half:'upper',hinge:'left',open:'true',powered:'false'}}")
      );
      out.put(
         "minecraft:" + name + "eastupperlefttruetrue",
         BlockStateFlattening.parseState("{Name:'minecraft:" + name + "',Properties:{facing:'east',half:'upper',hinge:'left',open:'true',powered:'true'}}")
      );
      out.put("minecraft:" + name + "eastupperrightfalsefalse", BlockStateFlattening.lookupState(_snowman + 9));
      out.put("minecraft:" + name + "eastupperrightfalsetrue", BlockStateFlattening.lookupState(_snowman + 11));
      out.put(
         "minecraft:" + name + "eastupperrighttruefalse",
         BlockStateFlattening.parseState("{Name:'minecraft:" + name + "',Properties:{facing:'east',half:'upper',hinge:'right',open:'true',powered:'false'}}")
      );
      out.put(
         "minecraft:" + name + "eastupperrighttruetrue",
         BlockStateFlattening.parseState("{Name:'minecraft:" + name + "',Properties:{facing:'east',half:'upper',hinge:'right',open:'true',powered:'true'}}")
      );
      out.put(
         "minecraft:" + name + "northlowerleftfalsefalse",
         BlockStateFlattening.parseState("{Name:'minecraft:" + name + "',Properties:{facing:'north',half:'lower',hinge:'left',open:'false',powered:'false'}}")
      );
      out.put(
         "minecraft:" + name + "northlowerleftfalsetrue",
         BlockStateFlattening.parseState("{Name:'minecraft:" + name + "',Properties:{facing:'north',half:'lower',hinge:'left',open:'false',powered:'true'}}")
      );
      out.put(
         "minecraft:" + name + "northlowerlefttruefalse",
         BlockStateFlattening.parseState("{Name:'minecraft:" + name + "',Properties:{facing:'north',half:'lower',hinge:'left',open:'true',powered:'false'}}")
      );
      out.put(
         "minecraft:" + name + "northlowerlefttruetrue",
         BlockStateFlattening.parseState("{Name:'minecraft:" + name + "',Properties:{facing:'north',half:'lower',hinge:'left',open:'true',powered:'true'}}")
      );
      out.put("minecraft:" + name + "northlowerrightfalsefalse", BlockStateFlattening.lookupState(_snowman + 3));
      out.put(
         "minecraft:" + name + "northlowerrightfalsetrue",
         BlockStateFlattening.parseState("{Name:'minecraft:" + name + "',Properties:{facing:'north',half:'lower',hinge:'right',open:'false',powered:'true'}}")
      );
      out.put("minecraft:" + name + "northlowerrighttruefalse", BlockStateFlattening.lookupState(_snowman + 7));
      out.put(
         "minecraft:" + name + "northlowerrighttruetrue",
         BlockStateFlattening.parseState("{Name:'minecraft:" + name + "',Properties:{facing:'north',half:'lower',hinge:'right',open:'true',powered:'true'}}")
      );
      out.put(
         "minecraft:" + name + "northupperleftfalsefalse",
         BlockStateFlattening.parseState("{Name:'minecraft:" + name + "',Properties:{facing:'north',half:'upper',hinge:'left',open:'false',powered:'false'}}")
      );
      out.put(
         "minecraft:" + name + "northupperleftfalsetrue",
         BlockStateFlattening.parseState("{Name:'minecraft:" + name + "',Properties:{facing:'north',half:'upper',hinge:'left',open:'false',powered:'true'}}")
      );
      out.put(
         "minecraft:" + name + "northupperlefttruefalse",
         BlockStateFlattening.parseState("{Name:'minecraft:" + name + "',Properties:{facing:'north',half:'upper',hinge:'left',open:'true',powered:'false'}}")
      );
      out.put(
         "minecraft:" + name + "northupperlefttruetrue",
         BlockStateFlattening.parseState("{Name:'minecraft:" + name + "',Properties:{facing:'north',half:'upper',hinge:'left',open:'true',powered:'true'}}")
      );
      out.put(
         "minecraft:" + name + "northupperrightfalsefalse",
         BlockStateFlattening.parseState("{Name:'minecraft:" + name + "',Properties:{facing:'north',half:'upper',hinge:'right',open:'false',powered:'false'}}")
      );
      out.put(
         "minecraft:" + name + "northupperrightfalsetrue",
         BlockStateFlattening.parseState("{Name:'minecraft:" + name + "',Properties:{facing:'north',half:'upper',hinge:'right',open:'false',powered:'true'}}")
      );
      out.put(
         "minecraft:" + name + "northupperrighttruefalse",
         BlockStateFlattening.parseState("{Name:'minecraft:" + name + "',Properties:{facing:'north',half:'upper',hinge:'right',open:'true',powered:'false'}}")
      );
      out.put(
         "minecraft:" + name + "northupperrighttruetrue",
         BlockStateFlattening.parseState("{Name:'minecraft:" + name + "',Properties:{facing:'north',half:'upper',hinge:'right',open:'true',powered:'true'}}")
      );
      out.put(
         "minecraft:" + name + "southlowerleftfalsefalse",
         BlockStateFlattening.parseState("{Name:'minecraft:" + name + "',Properties:{facing:'south',half:'lower',hinge:'left',open:'false',powered:'false'}}")
      );
      out.put(
         "minecraft:" + name + "southlowerleftfalsetrue",
         BlockStateFlattening.parseState("{Name:'minecraft:" + name + "',Properties:{facing:'south',half:'lower',hinge:'left',open:'false',powered:'true'}}")
      );
      out.put(
         "minecraft:" + name + "southlowerlefttruefalse",
         BlockStateFlattening.parseState("{Name:'minecraft:" + name + "',Properties:{facing:'south',half:'lower',hinge:'left',open:'true',powered:'false'}}")
      );
      out.put(
         "minecraft:" + name + "southlowerlefttruetrue",
         BlockStateFlattening.parseState("{Name:'minecraft:" + name + "',Properties:{facing:'south',half:'lower',hinge:'left',open:'true',powered:'true'}}")
      );
      out.put("minecraft:" + name + "southlowerrightfalsefalse", BlockStateFlattening.lookupState(_snowman + 1));
      out.put(
         "minecraft:" + name + "southlowerrightfalsetrue",
         BlockStateFlattening.parseState("{Name:'minecraft:" + name + "',Properties:{facing:'south',half:'lower',hinge:'right',open:'false',powered:'true'}}")
      );
      out.put("minecraft:" + name + "southlowerrighttruefalse", BlockStateFlattening.lookupState(_snowman + 5));
      out.put(
         "minecraft:" + name + "southlowerrighttruetrue",
         BlockStateFlattening.parseState("{Name:'minecraft:" + name + "',Properties:{facing:'south',half:'lower',hinge:'right',open:'true',powered:'true'}}")
      );
      out.put(
         "minecraft:" + name + "southupperleftfalsefalse",
         BlockStateFlattening.parseState("{Name:'minecraft:" + name + "',Properties:{facing:'south',half:'upper',hinge:'left',open:'false',powered:'false'}}")
      );
      out.put(
         "minecraft:" + name + "southupperleftfalsetrue",
         BlockStateFlattening.parseState("{Name:'minecraft:" + name + "',Properties:{facing:'south',half:'upper',hinge:'left',open:'false',powered:'true'}}")
      );
      out.put(
         "minecraft:" + name + "southupperlefttruefalse",
         BlockStateFlattening.parseState("{Name:'minecraft:" + name + "',Properties:{facing:'south',half:'upper',hinge:'left',open:'true',powered:'false'}}")
      );
      out.put(
         "minecraft:" + name + "southupperlefttruetrue",
         BlockStateFlattening.parseState("{Name:'minecraft:" + name + "',Properties:{facing:'south',half:'upper',hinge:'left',open:'true',powered:'true'}}")
      );
      out.put(
         "minecraft:" + name + "southupperrightfalsefalse",
         BlockStateFlattening.parseState("{Name:'minecraft:" + name + "',Properties:{facing:'south',half:'upper',hinge:'right',open:'false',powered:'false'}}")
      );
      out.put(
         "minecraft:" + name + "southupperrightfalsetrue",
         BlockStateFlattening.parseState("{Name:'minecraft:" + name + "',Properties:{facing:'south',half:'upper',hinge:'right',open:'false',powered:'true'}}")
      );
      out.put(
         "minecraft:" + name + "southupperrighttruefalse",
         BlockStateFlattening.parseState("{Name:'minecraft:" + name + "',Properties:{facing:'south',half:'upper',hinge:'right',open:'true',powered:'false'}}")
      );
      out.put(
         "minecraft:" + name + "southupperrighttruetrue",
         BlockStateFlattening.parseState("{Name:'minecraft:" + name + "',Properties:{facing:'south',half:'upper',hinge:'right',open:'true',powered:'true'}}")
      );
      out.put(
         "minecraft:" + name + "westlowerleftfalsefalse",
         BlockStateFlattening.parseState("{Name:'minecraft:" + name + "',Properties:{facing:'west',half:'lower',hinge:'left',open:'false',powered:'false'}}")
      );
      out.put(
         "minecraft:" + name + "westlowerleftfalsetrue",
         BlockStateFlattening.parseState("{Name:'minecraft:" + name + "',Properties:{facing:'west',half:'lower',hinge:'left',open:'false',powered:'true'}}")
      );
      out.put(
         "minecraft:" + name + "westlowerlefttruefalse",
         BlockStateFlattening.parseState("{Name:'minecraft:" + name + "',Properties:{facing:'west',half:'lower',hinge:'left',open:'true',powered:'false'}}")
      );
      out.put(
         "minecraft:" + name + "westlowerlefttruetrue",
         BlockStateFlattening.parseState("{Name:'minecraft:" + name + "',Properties:{facing:'west',half:'lower',hinge:'left',open:'true',powered:'true'}}")
      );
      out.put("minecraft:" + name + "westlowerrightfalsefalse", BlockStateFlattening.lookupState(_snowman + 2));
      out.put(
         "minecraft:" + name + "westlowerrightfalsetrue",
         BlockStateFlattening.parseState("{Name:'minecraft:" + name + "',Properties:{facing:'west',half:'lower',hinge:'right',open:'false',powered:'true'}}")
      );
      out.put("minecraft:" + name + "westlowerrighttruefalse", BlockStateFlattening.lookupState(_snowman + 6));
      out.put(
         "minecraft:" + name + "westlowerrighttruetrue",
         BlockStateFlattening.parseState("{Name:'minecraft:" + name + "',Properties:{facing:'west',half:'lower',hinge:'right',open:'true',powered:'true'}}")
      );
      out.put(
         "minecraft:" + name + "westupperleftfalsefalse",
         BlockStateFlattening.parseState("{Name:'minecraft:" + name + "',Properties:{facing:'west',half:'upper',hinge:'left',open:'false',powered:'false'}}")
      );
      out.put(
         "minecraft:" + name + "westupperleftfalsetrue",
         BlockStateFlattening.parseState("{Name:'minecraft:" + name + "',Properties:{facing:'west',half:'upper',hinge:'left',open:'false',powered:'true'}}")
      );
      out.put(
         "minecraft:" + name + "westupperlefttruefalse",
         BlockStateFlattening.parseState("{Name:'minecraft:" + name + "',Properties:{facing:'west',half:'upper',hinge:'left',open:'true',powered:'false'}}")
      );
      out.put(
         "minecraft:" + name + "westupperlefttruetrue",
         BlockStateFlattening.parseState("{Name:'minecraft:" + name + "',Properties:{facing:'west',half:'upper',hinge:'left',open:'true',powered:'true'}}")
      );
      out.put(
         "minecraft:" + name + "westupperrightfalsefalse",
         BlockStateFlattening.parseState("{Name:'minecraft:" + name + "',Properties:{facing:'west',half:'upper',hinge:'right',open:'false',powered:'false'}}")
      );
      out.put(
         "minecraft:" + name + "westupperrightfalsetrue",
         BlockStateFlattening.parseState("{Name:'minecraft:" + name + "',Properties:{facing:'west',half:'upper',hinge:'right',open:'false',powered:'true'}}")
      );
      out.put(
         "minecraft:" + name + "westupperrighttruefalse",
         BlockStateFlattening.parseState("{Name:'minecraft:" + name + "',Properties:{facing:'west',half:'upper',hinge:'right',open:'true',powered:'false'}}")
      );
      out.put(
         "minecraft:" + name + "westupperrighttruetrue",
         BlockStateFlattening.parseState("{Name:'minecraft:" + name + "',Properties:{facing:'west',half:'upper',hinge:'right',open:'true',powered:'true'}}")
      );
   }

   private static void buildBed(Map<String, Dynamic<?>> out, int _snowman, String _snowman) {
      out.put(
         "southfalsefoot" + _snowman, BlockStateFlattening.parseState("{Name:'minecraft:" + _snowman + "_bed',Properties:{facing:'south',occupied:'false',part:'foot'}}")
      );
      out.put("westfalsefoot" + _snowman, BlockStateFlattening.parseState("{Name:'minecraft:" + _snowman + "_bed',Properties:{facing:'west',occupied:'false',part:'foot'}}"));
      out.put(
         "northfalsefoot" + _snowman, BlockStateFlattening.parseState("{Name:'minecraft:" + _snowman + "_bed',Properties:{facing:'north',occupied:'false',part:'foot'}}")
      );
      out.put("eastfalsefoot" + _snowman, BlockStateFlattening.parseState("{Name:'minecraft:" + _snowman + "_bed',Properties:{facing:'east',occupied:'false',part:'foot'}}"));
      out.put(
         "southfalsehead" + _snowman, BlockStateFlattening.parseState("{Name:'minecraft:" + _snowman + "_bed',Properties:{facing:'south',occupied:'false',part:'head'}}")
      );
      out.put("westfalsehead" + _snowman, BlockStateFlattening.parseState("{Name:'minecraft:" + _snowman + "_bed',Properties:{facing:'west',occupied:'false',part:'head'}}"));
      out.put(
         "northfalsehead" + _snowman, BlockStateFlattening.parseState("{Name:'minecraft:" + _snowman + "_bed',Properties:{facing:'north',occupied:'false',part:'head'}}")
      );
      out.put("eastfalsehead" + _snowman, BlockStateFlattening.parseState("{Name:'minecraft:" + _snowman + "_bed',Properties:{facing:'east',occupied:'false',part:'head'}}"));
      out.put("southtruehead" + _snowman, BlockStateFlattening.parseState("{Name:'minecraft:" + _snowman + "_bed',Properties:{facing:'south',occupied:'true',part:'head'}}"));
      out.put("westtruehead" + _snowman, BlockStateFlattening.parseState("{Name:'minecraft:" + _snowman + "_bed',Properties:{facing:'west',occupied:'true',part:'head'}}"));
      out.put("northtruehead" + _snowman, BlockStateFlattening.parseState("{Name:'minecraft:" + _snowman + "_bed',Properties:{facing:'north',occupied:'true',part:'head'}}"));
      out.put("easttruehead" + _snowman, BlockStateFlattening.parseState("{Name:'minecraft:" + _snowman + "_bed',Properties:{facing:'east',occupied:'true',part:'head'}}"));
   }

   private static void buildBanner(Map<String, Dynamic<?>> out, int _snowman, String _snowman) {
      for (int _snowmanxx = 0; _snowmanxx < 16; _snowmanxx++) {
         out.put("" + _snowmanxx + "_" + _snowman, BlockStateFlattening.parseState("{Name:'minecraft:" + _snowman + "_banner',Properties:{rotation:'" + _snowmanxx + "'}}"));
      }

      out.put("north_" + _snowman, BlockStateFlattening.parseState("{Name:'minecraft:" + _snowman + "_wall_banner',Properties:{facing:'north'}}"));
      out.put("south_" + _snowman, BlockStateFlattening.parseState("{Name:'minecraft:" + _snowman + "_wall_banner',Properties:{facing:'south'}}"));
      out.put("west_" + _snowman, BlockStateFlattening.parseState("{Name:'minecraft:" + _snowman + "_wall_banner',Properties:{facing:'west'}}"));
      out.put("east_" + _snowman, BlockStateFlattening.parseState("{Name:'minecraft:" + _snowman + "_wall_banner',Properties:{facing:'east'}}"));
   }

   public static String getName(Dynamic<?> _snowman) {
      return _snowman.get("Name").asString("");
   }

   public static String getProperty(Dynamic<?> _snowman, String _snowman) {
      return _snowman.get("Properties").get(_snowman).asString("");
   }

   public static int addTo(Int2ObjectBiMap<Dynamic<?>> _snowman, Dynamic<?> _snowman) {
      int _snowmanxx = _snowman.getRawId(_snowman);
      if (_snowmanxx == -1) {
         _snowmanxx = _snowman.add(_snowman);
      }

      return _snowmanxx;
   }

   private Dynamic<?> fixChunk(Dynamic<?> _snowman) {
      Optional<? extends Dynamic<?>> _snowmanx = _snowman.get("Level").result();
      return _snowmanx.isPresent() && _snowmanx.get().get("Sections").asStreamOpt().result().isPresent()
         ? _snowman.set("Level", new ChunkPalettedStorageFix.Level((Dynamic<?>)_snowmanx.get()).transform())
         : _snowman;
   }

   public TypeRewriteRule makeRule() {
      Type<?> _snowman = this.getInputSchema().getType(TypeReferences.CHUNK);
      Type<?> _snowmanx = this.getOutputSchema().getType(TypeReferences.CHUNK);
      return this.writeFixAndRead("ChunkPalettedStorageFix", _snowman, _snowmanx, this::fixChunk);
   }

   public static int getSideToUpgradeFlag(boolean west, boolean east, boolean north, boolean south) {
      int _snowman = 0;
      if (north) {
         if (east) {
            _snowman |= 2;
         } else if (west) {
            _snowman |= 128;
         } else {
            _snowman |= 1;
         }
      } else if (south) {
         if (west) {
            _snowman |= 32;
         } else if (east) {
            _snowman |= 8;
         } else {
            _snowman |= 16;
         }
      } else if (east) {
         _snowman |= 4;
      } else if (west) {
         _snowman |= 64;
      }

      return _snowman;
   }

   static {
      BLOCKS_NEEDING_IN_PLACE_UPDATE.set(2);
      BLOCKS_NEEDING_IN_PLACE_UPDATE.set(3);
      BLOCKS_NEEDING_IN_PLACE_UPDATE.set(110);
      BLOCKS_NEEDING_IN_PLACE_UPDATE.set(140);
      BLOCKS_NEEDING_IN_PLACE_UPDATE.set(144);
      BLOCKS_NEEDING_IN_PLACE_UPDATE.set(25);
      BLOCKS_NEEDING_IN_PLACE_UPDATE.set(86);
      BLOCKS_NEEDING_IN_PLACE_UPDATE.set(26);
      BLOCKS_NEEDING_IN_PLACE_UPDATE.set(176);
      BLOCKS_NEEDING_IN_PLACE_UPDATE.set(177);
      BLOCKS_NEEDING_IN_PLACE_UPDATE.set(175);
      BLOCKS_NEEDING_IN_PLACE_UPDATE.set(64);
      BLOCKS_NEEDING_IN_PLACE_UPDATE.set(71);
      BLOCKS_NEEDING_IN_PLACE_UPDATE.set(193);
      BLOCKS_NEEDING_IN_PLACE_UPDATE.set(194);
      BLOCKS_NEEDING_IN_PLACE_UPDATE.set(195);
      BLOCKS_NEEDING_IN_PLACE_UPDATE.set(196);
      BLOCKS_NEEDING_IN_PLACE_UPDATE.set(197);
      BLOCKS_NEEDING_SIDE_UPDATE.set(54);
      BLOCKS_NEEDING_SIDE_UPDATE.set(146);
      BLOCKS_NEEDING_SIDE_UPDATE.set(25);
      BLOCKS_NEEDING_SIDE_UPDATE.set(26);
      BLOCKS_NEEDING_SIDE_UPDATE.set(51);
      BLOCKS_NEEDING_SIDE_UPDATE.set(53);
      BLOCKS_NEEDING_SIDE_UPDATE.set(67);
      BLOCKS_NEEDING_SIDE_UPDATE.set(108);
      BLOCKS_NEEDING_SIDE_UPDATE.set(109);
      BLOCKS_NEEDING_SIDE_UPDATE.set(114);
      BLOCKS_NEEDING_SIDE_UPDATE.set(128);
      BLOCKS_NEEDING_SIDE_UPDATE.set(134);
      BLOCKS_NEEDING_SIDE_UPDATE.set(135);
      BLOCKS_NEEDING_SIDE_UPDATE.set(136);
      BLOCKS_NEEDING_SIDE_UPDATE.set(156);
      BLOCKS_NEEDING_SIDE_UPDATE.set(163);
      BLOCKS_NEEDING_SIDE_UPDATE.set(164);
      BLOCKS_NEEDING_SIDE_UPDATE.set(180);
      BLOCKS_NEEDING_SIDE_UPDATE.set(203);
      BLOCKS_NEEDING_SIDE_UPDATE.set(55);
      BLOCKS_NEEDING_SIDE_UPDATE.set(85);
      BLOCKS_NEEDING_SIDE_UPDATE.set(113);
      BLOCKS_NEEDING_SIDE_UPDATE.set(188);
      BLOCKS_NEEDING_SIDE_UPDATE.set(189);
      BLOCKS_NEEDING_SIDE_UPDATE.set(190);
      BLOCKS_NEEDING_SIDE_UPDATE.set(191);
      BLOCKS_NEEDING_SIDE_UPDATE.set(192);
      BLOCKS_NEEDING_SIDE_UPDATE.set(93);
      BLOCKS_NEEDING_SIDE_UPDATE.set(94);
      BLOCKS_NEEDING_SIDE_UPDATE.set(101);
      BLOCKS_NEEDING_SIDE_UPDATE.set(102);
      BLOCKS_NEEDING_SIDE_UPDATE.set(160);
      BLOCKS_NEEDING_SIDE_UPDATE.set(106);
      BLOCKS_NEEDING_SIDE_UPDATE.set(107);
      BLOCKS_NEEDING_SIDE_UPDATE.set(183);
      BLOCKS_NEEDING_SIDE_UPDATE.set(184);
      BLOCKS_NEEDING_SIDE_UPDATE.set(185);
      BLOCKS_NEEDING_SIDE_UPDATE.set(186);
      BLOCKS_NEEDING_SIDE_UPDATE.set(187);
      BLOCKS_NEEDING_SIDE_UPDATE.set(132);
      BLOCKS_NEEDING_SIDE_UPDATE.set(139);
      BLOCKS_NEEDING_SIDE_UPDATE.set(199);
   }

   static class ChunkNibbleArray {
      private final byte[] contents;

      public ChunkNibbleArray() {
         this.contents = new byte[2048];
      }

      public ChunkNibbleArray(byte[] _snowman) {
         this.contents = _snowman;
         if (_snowman.length != 2048) {
            throw new IllegalArgumentException("ChunkNibbleArrays should be 2048 bytes not: " + _snowman.length);
         }
      }

      public int get(int x, int y, int _snowman) {
         int _snowmanx = this.getRawIndex(y << 8 | _snowman << 4 | x);
         return this.usesLowNibble(y << 8 | _snowman << 4 | x) ? this.contents[_snowmanx] & 15 : this.contents[_snowmanx] >> 4 & 15;
      }

      private boolean usesLowNibble(int index) {
         return (index & 1) == 0;
      }

      private int getRawIndex(int index) {
         return index >> 1;
      }
   }

   public static enum Facing {
      DOWN(ChunkPalettedStorageFix.Facing.Direction.NEGATIVE, ChunkPalettedStorageFix.Facing.Axis.Y),
      UP(ChunkPalettedStorageFix.Facing.Direction.POSITIVE, ChunkPalettedStorageFix.Facing.Axis.Y),
      NORTH(ChunkPalettedStorageFix.Facing.Direction.NEGATIVE, ChunkPalettedStorageFix.Facing.Axis.Z),
      SOUTH(ChunkPalettedStorageFix.Facing.Direction.POSITIVE, ChunkPalettedStorageFix.Facing.Axis.Z),
      WEST(ChunkPalettedStorageFix.Facing.Direction.NEGATIVE, ChunkPalettedStorageFix.Facing.Axis.X),
      EAST(ChunkPalettedStorageFix.Facing.Direction.POSITIVE, ChunkPalettedStorageFix.Facing.Axis.X);

      private final ChunkPalettedStorageFix.Facing.Axis axis;
      private final ChunkPalettedStorageFix.Facing.Direction direction;

      private Facing(ChunkPalettedStorageFix.Facing.Direction direction, ChunkPalettedStorageFix.Facing.Axis var4) {
         this.axis = _snowman;
         this.direction = direction;
      }

      public ChunkPalettedStorageFix.Facing.Direction getDirection() {
         return this.direction;
      }

      public ChunkPalettedStorageFix.Facing.Axis getAxis() {
         return this.axis;
      }

      public static enum Axis {
         X,
         Y,
         Z;

         private Axis() {
         }
      }

      public static enum Direction {
         POSITIVE(1),
         NEGATIVE(-1);

         private final int offset;

         private Direction(int var3) {
            this.offset = _snowman;
         }

         public int getOffset() {
            return this.offset;
         }
      }
   }

   static final class Level {
      private int sidesToUpgrade;
      private final ChunkPalettedStorageFix.Section[] sections = new ChunkPalettedStorageFix.Section[16];
      private final Dynamic<?> level;
      private final int xPos;
      private final int yPos;
      private final Int2ObjectMap<Dynamic<?>> blockEntities = new Int2ObjectLinkedOpenHashMap(16);

      public Level(Dynamic<?> _snowman) {
         this.level = _snowman;
         this.xPos = _snowman.get("xPos").asInt(0) << 4;
         this.yPos = _snowman.get("zPos").asInt(0) << 4;
         _snowman.get("TileEntities")
            .asStreamOpt()
            .result()
            .ifPresent(
               _snowmanx -> _snowmanx.forEach(
                     _snowmanxxxxxx -> {
                        int _snowmanx = _snowmanxxxxxx.get("x").asInt(0) - this.xPos & 15;
                        int _snowmanxx = _snowmanxxxxxx.get("y").asInt(0);
                        int _snowmanxxx = _snowmanxxxxxx.get("z").asInt(0) - this.yPos & 15;
                        int _snowmanxxxx = _snowmanxx << 8 | _snowmanxxx << 4 | _snowmanx;
                        if (this.blockEntities.put(_snowmanxxxx, _snowmanxxxxxx) != null) {
                           ChunkPalettedStorageFix.LOGGER
                              .warn("In chunk: {}x{} found a duplicate block entity at position: [{}, {}, {}]", this.xPos, this.yPos, _snowmanx, _snowmanxx, _snowmanxxx);
                        }
                     }
                  )
            );
         boolean _snowmanx = _snowman.get("convertedFromAlphaFormat").asBoolean(false);
         _snowman.get("Sections").asStreamOpt().result().ifPresent(_snowmanxx -> _snowmanxx.forEach(_snowmanxxx -> {
               ChunkPalettedStorageFix.Section _snowmanxxx = new ChunkPalettedStorageFix.Section(_snowmanxxx);
               this.sidesToUpgrade = _snowmanxxx.visit(this.sidesToUpgrade);
               this.sections[_snowmanxxx.y] = _snowmanxxx;
            }));

         for (ChunkPalettedStorageFix.Section _snowmanxx : this.sections) {
            if (_snowmanxx != null) {
               ObjectIterator var7 = _snowmanxx.inPlaceUpdates.entrySet().iterator();

               while (var7.hasNext()) {
                  java.util.Map.Entry<Integer, IntList> _snowmanxxx = (java.util.Map.Entry<Integer, IntList>)var7.next();
                  int _snowmanxxxx = _snowmanxx.y << 12;
                  switch (_snowmanxxx.getKey()) {
                     case 2:
                        IntListIterator var30 = _snowmanxxx.getValue().iterator();

                        while (var30.hasNext()) {
                           int _snowmanxxxxx = (Integer)var30.next();
                           _snowmanxxxxx |= _snowmanxxxx;
                           Dynamic<?> _snowmanxxxxxx = this.getBlock(_snowmanxxxxx);
                           if ("minecraft:grass_block".equals(ChunkPalettedStorageFix.getName(_snowmanxxxxxx))) {
                              String _snowmanxxxxxxx = ChunkPalettedStorageFix.getName(this.getBlock(adjacentTo(_snowmanxxxxx, ChunkPalettedStorageFix.Facing.UP)));
                              if ("minecraft:snow".equals(_snowmanxxxxxxx) || "minecraft:snow_layer".equals(_snowmanxxxxxxx)) {
                                 this.setBlock(_snowmanxxxxx, ChunkPalettedStorageFix.SNOWY_GRASS);
                              }
                           }
                        }
                        break;
                     case 3:
                        IntListIterator var29 = _snowmanxxx.getValue().iterator();

                        while (var29.hasNext()) {
                           int _snowmanxxxxx = (Integer)var29.next();
                           _snowmanxxxxx |= _snowmanxxxx;
                           Dynamic<?> _snowmanxxxxxx = this.getBlock(_snowmanxxxxx);
                           if ("minecraft:podzol".equals(ChunkPalettedStorageFix.getName(_snowmanxxxxxx))) {
                              String _snowmanxxxxxxx = ChunkPalettedStorageFix.getName(this.getBlock(adjacentTo(_snowmanxxxxx, ChunkPalettedStorageFix.Facing.UP)));
                              if ("minecraft:snow".equals(_snowmanxxxxxxx) || "minecraft:snow_layer".equals(_snowmanxxxxxxx)) {
                                 this.setBlock(_snowmanxxxxx, ChunkPalettedStorageFix.PODZOL);
                              }
                           }
                        }
                        break;
                     case 25:
                        IntListIterator var28 = _snowmanxxx.getValue().iterator();

                        while (var28.hasNext()) {
                           int _snowmanxxxxx = (Integer)var28.next();
                           _snowmanxxxxx |= _snowmanxxxx;
                           Dynamic<?> _snowmanxxxxxx = this.removeBlockEntity(_snowmanxxxxx);
                           if (_snowmanxxxxxx != null) {
                              String _snowmanxxxxxxx = Boolean.toString(_snowmanxxxxxx.get("powered").asBoolean(false))
                                 + (byte)Math.min(Math.max(_snowmanxxxxxx.get("note").asInt(0), 0), 24);
                              this.setBlock(_snowmanxxxxx, ChunkPalettedStorageFix.NOTE_BLOCK.getOrDefault(_snowmanxxxxxxx, ChunkPalettedStorageFix.NOTE_BLOCK.get("false0")));
                           }
                        }
                        break;
                     case 26:
                        IntListIterator var27 = _snowmanxxx.getValue().iterator();

                        while (var27.hasNext()) {
                           int _snowmanxxxxx = (Integer)var27.next();
                           _snowmanxxxxx |= _snowmanxxxx;
                           Dynamic<?> _snowmanxxxxxx = this.getBlockEntity(_snowmanxxxxx);
                           Dynamic<?> _snowmanxxxxxxx = this.getBlock(_snowmanxxxxx);
                           if (_snowmanxxxxxx != null) {
                              int _snowmanxxxxxxxx = _snowmanxxxxxx.get("color").asInt(0);
                              if (_snowmanxxxxxxxx != 14 && _snowmanxxxxxxxx >= 0 && _snowmanxxxxxxxx < 16) {
                                 String _snowmanxxxxxxxxx = ChunkPalettedStorageFix.getProperty(_snowmanxxxxxxx, "facing")
                                    + ChunkPalettedStorageFix.getProperty(_snowmanxxxxxxx, "occupied")
                                    + ChunkPalettedStorageFix.getProperty(_snowmanxxxxxxx, "part")
                                    + _snowmanxxxxxxxx;
                                 if (ChunkPalettedStorageFix.BED.containsKey(_snowmanxxxxxxxxx)) {
                                    this.setBlock(_snowmanxxxxx, ChunkPalettedStorageFix.BED.get(_snowmanxxxxxxxxx));
                                 }
                              }
                           }
                        }
                        break;
                     case 64:
                     case 71:
                     case 193:
                     case 194:
                     case 195:
                     case 196:
                     case 197:
                        IntListIterator var26 = _snowmanxxx.getValue().iterator();

                        while (var26.hasNext()) {
                           int _snowmanxxxxx = (Integer)var26.next();
                           _snowmanxxxxx |= _snowmanxxxx;
                           Dynamic<?> _snowmanxxxxxx = this.getBlock(_snowmanxxxxx);
                           if (ChunkPalettedStorageFix.getName(_snowmanxxxxxx).endsWith("_door")) {
                              Dynamic<?> _snowmanxxxxxxx = this.getBlock(_snowmanxxxxx);
                              if ("lower".equals(ChunkPalettedStorageFix.getProperty(_snowmanxxxxxxx, "half"))) {
                                 int _snowmanxxxxxxxx = adjacentTo(_snowmanxxxxx, ChunkPalettedStorageFix.Facing.UP);
                                 Dynamic<?> _snowmanxxxxxxxxx = this.getBlock(_snowmanxxxxxxxx);
                                 String _snowmanxxxxxxxxxx = ChunkPalettedStorageFix.getName(_snowmanxxxxxxx);
                                 if (_snowmanxxxxxxxxxx.equals(ChunkPalettedStorageFix.getName(_snowmanxxxxxxxxx))) {
                                    String _snowmanxxxxxxxxxxx = ChunkPalettedStorageFix.getProperty(_snowmanxxxxxxx, "facing");
                                    String _snowmanxxxxxxxxxxxx = ChunkPalettedStorageFix.getProperty(_snowmanxxxxxxx, "open");
                                    String _snowmanxxxxxxxxxxxxx = _snowmanx ? "left" : ChunkPalettedStorageFix.getProperty(_snowmanxxxxxxxxx, "hinge");
                                    String _snowmanxxxxxxxxxxxxxx = _snowmanx ? "false" : ChunkPalettedStorageFix.getProperty(_snowmanxxxxxxxxx, "powered");
                                    this.setBlock(
                                       _snowmanxxxxx,
                                       ChunkPalettedStorageFix.DOOR
                                          .get(_snowmanxxxxxxxxxx + _snowmanxxxxxxxxxxx + "lower" + _snowmanxxxxxxxxxxxxx + _snowmanxxxxxxxxxxxx + _snowmanxxxxxxxxxxxxxx)
                                    );
                                    this.setBlock(
                                       _snowmanxxxxxxxx,
                                       ChunkPalettedStorageFix.DOOR
                                          .get(_snowmanxxxxxxxxxx + _snowmanxxxxxxxxxxx + "upper" + _snowmanxxxxxxxxxxxxx + _snowmanxxxxxxxxxxxx + _snowmanxxxxxxxxxxxxxx)
                                    );
                                 }
                              }
                           }
                        }
                        break;
                     case 86:
                        IntListIterator var25 = _snowmanxxx.getValue().iterator();

                        while (var25.hasNext()) {
                           int _snowmanxxxxx = (Integer)var25.next();
                           _snowmanxxxxx |= _snowmanxxxx;
                           Dynamic<?> _snowmanxxxxxx = this.getBlock(_snowmanxxxxx);
                           if ("minecraft:carved_pumpkin".equals(ChunkPalettedStorageFix.getName(_snowmanxxxxxx))) {
                              String _snowmanxxxxxxx = ChunkPalettedStorageFix.getName(this.getBlock(adjacentTo(_snowmanxxxxx, ChunkPalettedStorageFix.Facing.DOWN)));
                              if ("minecraft:grass_block".equals(_snowmanxxxxxxx) || "minecraft:dirt".equals(_snowmanxxxxxxx)) {
                                 this.setBlock(_snowmanxxxxx, ChunkPalettedStorageFix.PUMPKIN);
                              }
                           }
                        }
                        break;
                     case 110:
                        IntListIterator var24 = _snowmanxxx.getValue().iterator();

                        while (var24.hasNext()) {
                           int _snowmanxxxxx = (Integer)var24.next();
                           _snowmanxxxxx |= _snowmanxxxx;
                           Dynamic<?> _snowmanxxxxxx = this.getBlock(_snowmanxxxxx);
                           if ("minecraft:mycelium".equals(ChunkPalettedStorageFix.getName(_snowmanxxxxxx))) {
                              String _snowmanxxxxxxx = ChunkPalettedStorageFix.getName(this.getBlock(adjacentTo(_snowmanxxxxx, ChunkPalettedStorageFix.Facing.UP)));
                              if ("minecraft:snow".equals(_snowmanxxxxxxx) || "minecraft:snow_layer".equals(_snowmanxxxxxxx)) {
                                 this.setBlock(_snowmanxxxxx, ChunkPalettedStorageFix.SNOWY_MYCELIUM);
                              }
                           }
                        }
                        break;
                     case 140:
                        IntListIterator var23 = _snowmanxxx.getValue().iterator();

                        while (var23.hasNext()) {
                           int _snowmanxxxxx = (Integer)var23.next();
                           _snowmanxxxxx |= _snowmanxxxx;
                           Dynamic<?> _snowmanxxxxxx = this.removeBlockEntity(_snowmanxxxxx);
                           if (_snowmanxxxxxx != null) {
                              String _snowmanxxxxxxx = _snowmanxxxxxx.get("Item").asString("") + _snowmanxxxxxx.get("Data").asInt(0);
                              this.setBlock(
                                 _snowmanxxxxx, ChunkPalettedStorageFix.FLOWER_POT.getOrDefault(_snowmanxxxxxxx, ChunkPalettedStorageFix.FLOWER_POT.get("minecraft:air0"))
                              );
                           }
                        }
                        break;
                     case 144:
                        IntListIterator var22 = _snowmanxxx.getValue().iterator();

                        while (var22.hasNext()) {
                           int _snowmanxxxxx = (Integer)var22.next();
                           _snowmanxxxxx |= _snowmanxxxx;
                           Dynamic<?> _snowmanxxxxxx = this.getBlockEntity(_snowmanxxxxx);
                           if (_snowmanxxxxxx != null) {
                              String _snowmanxxxxxxx = String.valueOf(_snowmanxxxxxx.get("SkullType").asInt(0));
                              String _snowmanxxxxxxxx = ChunkPalettedStorageFix.getProperty(this.getBlock(_snowmanxxxxx), "facing");
                              String _snowmanxxxxxxxxx;
                              if (!"up".equals(_snowmanxxxxxxxx) && !"down".equals(_snowmanxxxxxxxx)) {
                                 _snowmanxxxxxxxxx = _snowmanxxxxxxx + _snowmanxxxxxxxx;
                              } else {
                                 _snowmanxxxxxxxxx = _snowmanxxxxxxx + String.valueOf(_snowmanxxxxxx.get("Rot").asInt(0));
                              }

                              _snowmanxxxxxx.remove("SkullType");
                              _snowmanxxxxxx.remove("facing");
                              _snowmanxxxxxx.remove("Rot");
                              this.setBlock(_snowmanxxxxx, ChunkPalettedStorageFix.SKULL.getOrDefault(_snowmanxxxxxxxxx, ChunkPalettedStorageFix.SKULL.get("0north")));
                           }
                        }
                        break;
                     case 175:
                        IntListIterator var21 = _snowmanxxx.getValue().iterator();

                        while (var21.hasNext()) {
                           int _snowmanxxxxx = (Integer)var21.next();
                           _snowmanxxxxx |= _snowmanxxxx;
                           Dynamic<?> _snowmanxxxxxx = this.getBlock(_snowmanxxxxx);
                           if ("upper".equals(ChunkPalettedStorageFix.getProperty(_snowmanxxxxxx, "half"))) {
                              Dynamic<?> _snowmanxxxxxxx = this.getBlock(adjacentTo(_snowmanxxxxx, ChunkPalettedStorageFix.Facing.DOWN));
                              String _snowmanxxxxxxxx = ChunkPalettedStorageFix.getName(_snowmanxxxxxxx);
                              if ("minecraft:sunflower".equals(_snowmanxxxxxxxx)) {
                                 this.setBlock(_snowmanxxxxx, ChunkPalettedStorageFix.SUNFLOWER_UPPER);
                              } else if ("minecraft:lilac".equals(_snowmanxxxxxxxx)) {
                                 this.setBlock(_snowmanxxxxx, ChunkPalettedStorageFix.LILAC_UPPER);
                              } else if ("minecraft:tall_grass".equals(_snowmanxxxxxxxx)) {
                                 this.setBlock(_snowmanxxxxx, ChunkPalettedStorageFix.GRASS_UPPER);
                              } else if ("minecraft:large_fern".equals(_snowmanxxxxxxxx)) {
                                 this.setBlock(_snowmanxxxxx, ChunkPalettedStorageFix.FERN_UPPER);
                              } else if ("minecraft:rose_bush".equals(_snowmanxxxxxxxx)) {
                                 this.setBlock(_snowmanxxxxx, ChunkPalettedStorageFix.ROSE_UPPER);
                              } else if ("minecraft:peony".equals(_snowmanxxxxxxxx)) {
                                 this.setBlock(_snowmanxxxxx, ChunkPalettedStorageFix.PEONY_UPPER);
                              }
                           }
                        }
                        break;
                     case 176:
                     case 177:
                        IntListIterator var10 = _snowmanxxx.getValue().iterator();

                        while (var10.hasNext()) {
                           int _snowmanxxxxx = (Integer)var10.next();
                           _snowmanxxxxx |= _snowmanxxxx;
                           Dynamic<?> _snowmanxxxxxx = this.getBlockEntity(_snowmanxxxxx);
                           Dynamic<?> _snowmanxxxxxxx = this.getBlock(_snowmanxxxxx);
                           if (_snowmanxxxxxx != null) {
                              int _snowmanxxxxxxxx = _snowmanxxxxxx.get("Base").asInt(0);
                              if (_snowmanxxxxxxxx != 15 && _snowmanxxxxxxxx >= 0 && _snowmanxxxxxxxx < 16) {
                                 String _snowmanxxxxxxxxx = ChunkPalettedStorageFix.getProperty(_snowmanxxxxxxx, _snowmanxxx.getKey() == 176 ? "rotation" : "facing")
                                    + "_"
                                    + _snowmanxxxxxxxx;
                                 if (ChunkPalettedStorageFix.BANNER.containsKey(_snowmanxxxxxxxxx)) {
                                    this.setBlock(_snowmanxxxxx, ChunkPalettedStorageFix.BANNER.get(_snowmanxxxxxxxxx));
                                 }
                              }
                           }
                        }
                  }
               }
            }
         }
      }

      @Nullable
      private Dynamic<?> getBlockEntity(int _snowman) {
         return (Dynamic<?>)this.blockEntities.get(_snowman);
      }

      @Nullable
      private Dynamic<?> removeBlockEntity(int _snowman) {
         return (Dynamic<?>)this.blockEntities.remove(_snowman);
      }

      public static int adjacentTo(int _snowman, ChunkPalettedStorageFix.Facing direction) {
         switch (direction.getAxis()) {
            case X:
               int _snowmanxxx = (_snowman & 15) + direction.getDirection().getOffset();
               return _snowmanxxx >= 0 && _snowmanxxx <= 15 ? _snowman & -16 | _snowmanxxx : -1;
            case Y:
               int _snowmanxx = (_snowman >> 8) + direction.getDirection().getOffset();
               return _snowmanxx >= 0 && _snowmanxx <= 255 ? _snowman & 0xFF | _snowmanxx << 8 : -1;
            case Z:
               int _snowmanx = (_snowman >> 4 & 15) + direction.getDirection().getOffset();
               return _snowmanx >= 0 && _snowmanx <= 15 ? _snowman & -241 | _snowmanx << 4 : -1;
            default:
               return -1;
         }
      }

      private void setBlock(int _snowman, Dynamic<?> _snowman) {
         if (_snowman >= 0 && _snowman <= 65535) {
            ChunkPalettedStorageFix.Section _snowmanxx = this.getSection(_snowman);
            if (_snowmanxx != null) {
               _snowmanxx.setBlock(_snowman & 4095, _snowman);
            }
         }
      }

      @Nullable
      private ChunkPalettedStorageFix.Section getSection(int _snowman) {
         int _snowmanx = _snowman >> 12;
         return _snowmanx < this.sections.length ? this.sections[_snowmanx] : null;
      }

      public Dynamic<?> getBlock(int _snowman) {
         if (_snowman >= 0 && _snowman <= 65535) {
            ChunkPalettedStorageFix.Section _snowmanx = this.getSection(_snowman);
            return _snowmanx == null ? ChunkPalettedStorageFix.AIR : _snowmanx.getBlock(_snowman & 4095);
         } else {
            return ChunkPalettedStorageFix.AIR;
         }
      }

      public Dynamic<?> transform() {
         Dynamic<?> _snowman = this.level;
         if (this.blockEntities.isEmpty()) {
            _snowman = _snowman.remove("TileEntities");
         } else {
            _snowman = _snowman.set("TileEntities", _snowman.createList(this.blockEntities.values().stream()));
         }

         Dynamic<?> _snowmanx = _snowman.emptyMap();
         List<Dynamic<?>> _snowmanxx = Lists.newArrayList();

         for (ChunkPalettedStorageFix.Section _snowmanxxx : this.sections) {
            if (_snowmanxxx != null) {
               _snowmanxx.add(_snowmanxxx.transform());
               _snowmanx = _snowmanx.set(String.valueOf(_snowmanxxx.y), _snowmanx.createIntList(Arrays.stream(_snowmanxxx.innerPositions.toIntArray())));
            }
         }

         Dynamic<?> _snowmanxxxx = _snowman.emptyMap();
         _snowmanxxxx = _snowmanxxxx.set("Sides", _snowmanxxxx.createByte((byte)this.sidesToUpgrade));
         _snowmanxxxx = _snowmanxxxx.set("Indices", _snowmanx);
         return _snowman.set("UpgradeData", _snowmanxxxx).set("Sections", _snowmanxxxx.createList(_snowmanxx.stream()));
      }
   }

   static class Section {
      private final Int2ObjectBiMap<Dynamic<?>> paletteMap = new Int2ObjectBiMap<>(32);
      private final List<Dynamic<?>> paletteData;
      private final Dynamic<?> section;
      private final boolean hasBlocks;
      private final Int2ObjectMap<IntList> inPlaceUpdates = new Int2ObjectLinkedOpenHashMap();
      private final IntList innerPositions = new IntArrayList();
      public final int y;
      private final Set<Dynamic<?>> seenStates = Sets.newIdentityHashSet();
      private final int[] states = new int[4096];

      public Section(Dynamic<?> _snowman) {
         this.paletteData = Lists.newArrayList();
         this.section = _snowman;
         this.y = _snowman.get("Y").asInt(0);
         this.hasBlocks = _snowman.get("Blocks").result().isPresent();
      }

      public Dynamic<?> getBlock(int index) {
         if (index >= 0 && index <= 4095) {
            Dynamic<?> _snowman = this.paletteMap.get(this.states[index]);
            return _snowman == null ? ChunkPalettedStorageFix.AIR : _snowman;
         } else {
            return ChunkPalettedStorageFix.AIR;
         }
      }

      public void setBlock(int pos, Dynamic<?> _snowman) {
         if (this.seenStates.add(_snowman)) {
            this.paletteData.add("%%FILTER_ME%%".equals(ChunkPalettedStorageFix.getName(_snowman)) ? ChunkPalettedStorageFix.AIR : _snowman);
         }

         this.states[pos] = ChunkPalettedStorageFix.addTo(this.paletteMap, _snowman);
      }

      public int visit(int sidesToUpgrade) {
         if (!this.hasBlocks) {
            return sidesToUpgrade;
         } else {
            ByteBuffer _snowman = (ByteBuffer)this.section.get("Blocks").asByteBufferOpt().result().get();
            ChunkPalettedStorageFix.ChunkNibbleArray _snowmanx = this.section
               .get("Data")
               .asByteBufferOpt()
               .map(_snowmanxx -> new ChunkPalettedStorageFix.ChunkNibbleArray(DataFixUtils.toArray(_snowmanxx)))
               .result()
               .orElseGet(ChunkPalettedStorageFix.ChunkNibbleArray::new);
            ChunkPalettedStorageFix.ChunkNibbleArray _snowmanxx = this.section
               .get("Add")
               .asByteBufferOpt()
               .map(_snowmanxxx -> new ChunkPalettedStorageFix.ChunkNibbleArray(DataFixUtils.toArray(_snowmanxxx)))
               .result()
               .orElseGet(ChunkPalettedStorageFix.ChunkNibbleArray::new);
            this.seenStates.add(ChunkPalettedStorageFix.AIR);
            ChunkPalettedStorageFix.addTo(this.paletteMap, ChunkPalettedStorageFix.AIR);
            this.paletteData.add(ChunkPalettedStorageFix.AIR);

            for (int _snowmanxxx = 0; _snowmanxxx < 4096; _snowmanxxx++) {
               int _snowmanxxxx = _snowmanxxx & 15;
               int _snowmanxxxxx = _snowmanxxx >> 8 & 15;
               int _snowmanxxxxxx = _snowmanxxx >> 4 & 15;
               int _snowmanxxxxxxx = _snowmanxx.get(_snowmanxxxx, _snowmanxxxxx, _snowmanxxxxxx) << 12 | (_snowman.get(_snowmanxxx) & 255) << 4 | _snowmanx.get(_snowmanxxxx, _snowmanxxxxx, _snowmanxxxxxx);
               if (ChunkPalettedStorageFix.BLOCKS_NEEDING_IN_PLACE_UPDATE.get(_snowmanxxxxxxx >> 4)) {
                  this.addInPlaceUpdate(_snowmanxxxxxxx >> 4, _snowmanxxx);
               }

               if (ChunkPalettedStorageFix.BLOCKS_NEEDING_SIDE_UPDATE.get(_snowmanxxxxxxx >> 4)) {
                  int _snowmanxxxxxxxx = ChunkPalettedStorageFix.getSideToUpgradeFlag(_snowmanxxxx == 0, _snowmanxxxx == 15, _snowmanxxxxxx == 0, _snowmanxxxxxx == 15);
                  if (_snowmanxxxxxxxx == 0) {
                     this.innerPositions.add(_snowmanxxx);
                  } else {
                     sidesToUpgrade |= _snowmanxxxxxxxx;
                  }
               }

               this.setBlock(_snowmanxxx, BlockStateFlattening.lookupState(_snowmanxxxxxxx));
            }

            return sidesToUpgrade;
         }
      }

      private void addInPlaceUpdate(int section, int index) {
         IntList _snowman = (IntList)this.inPlaceUpdates.get(section);
         if (_snowman == null) {
            _snowman = new IntArrayList();
            this.inPlaceUpdates.put(section, _snowman);
         }

         _snowman.add(index);
      }

      public Dynamic<?> transform() {
         Dynamic<?> _snowman = this.section;
         if (!this.hasBlocks) {
            return _snowman;
         } else {
            _snowman = _snowman.set("Palette", _snowman.createList(this.paletteData.stream()));
            int _snowmanx = Math.max(4, DataFixUtils.ceillog2(this.seenStates.size()));
            WordPackedArray _snowmanxx = new WordPackedArray(_snowmanx, 4096);

            for (int _snowmanxxx = 0; _snowmanxxx < this.states.length; _snowmanxxx++) {
               _snowmanxx.set(_snowmanxxx, this.states[_snowmanxxx]);
            }

            _snowman = _snowman.set("BlockStates", _snowman.createLongList(Arrays.stream(_snowmanxx.getAlignedArray())));
            _snowman = _snowman.remove("Blocks");
            _snowman = _snowman.remove("Data");
            return _snowman.remove("Add");
         }
      }
   }
}
