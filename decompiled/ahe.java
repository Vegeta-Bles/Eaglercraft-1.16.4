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
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ahe extends DataFix {
   private static final Logger a = LogManager.getLogger();
   private static final BitSet b = new BitSet(256);
   private static final BitSet c = new BitSet(256);
   private static final Dynamic<?> d = agz.b("{Name:'minecraft:pumpkin'}");
   private static final Dynamic<?> e = agz.b("{Name:'minecraft:podzol',Properties:{snowy:'true'}}");
   private static final Dynamic<?> f = agz.b("{Name:'minecraft:grass_block',Properties:{snowy:'true'}}");
   private static final Dynamic<?> g = agz.b("{Name:'minecraft:mycelium',Properties:{snowy:'true'}}");
   private static final Dynamic<?> h = agz.b("{Name:'minecraft:sunflower',Properties:{half:'upper'}}");
   private static final Dynamic<?> i = agz.b("{Name:'minecraft:lilac',Properties:{half:'upper'}}");
   private static final Dynamic<?> j = agz.b("{Name:'minecraft:tall_grass',Properties:{half:'upper'}}");
   private static final Dynamic<?> k = agz.b("{Name:'minecraft:large_fern',Properties:{half:'upper'}}");
   private static final Dynamic<?> l = agz.b("{Name:'minecraft:rose_bush',Properties:{half:'upper'}}");
   private static final Dynamic<?> m = agz.b("{Name:'minecraft:peony',Properties:{half:'upper'}}");
   private static final Map<String, Dynamic<?>> n = (Map<String, Dynamic<?>>)DataFixUtils.make(Maps.newHashMap(), var0 -> {
      var0.put("minecraft:air0", agz.b("{Name:'minecraft:flower_pot'}"));
      var0.put("minecraft:red_flower0", agz.b("{Name:'minecraft:potted_poppy'}"));
      var0.put("minecraft:red_flower1", agz.b("{Name:'minecraft:potted_blue_orchid'}"));
      var0.put("minecraft:red_flower2", agz.b("{Name:'minecraft:potted_allium'}"));
      var0.put("minecraft:red_flower3", agz.b("{Name:'minecraft:potted_azure_bluet'}"));
      var0.put("minecraft:red_flower4", agz.b("{Name:'minecraft:potted_red_tulip'}"));
      var0.put("minecraft:red_flower5", agz.b("{Name:'minecraft:potted_orange_tulip'}"));
      var0.put("minecraft:red_flower6", agz.b("{Name:'minecraft:potted_white_tulip'}"));
      var0.put("minecraft:red_flower7", agz.b("{Name:'minecraft:potted_pink_tulip'}"));
      var0.put("minecraft:red_flower8", agz.b("{Name:'minecraft:potted_oxeye_daisy'}"));
      var0.put("minecraft:yellow_flower0", agz.b("{Name:'minecraft:potted_dandelion'}"));
      var0.put("minecraft:sapling0", agz.b("{Name:'minecraft:potted_oak_sapling'}"));
      var0.put("minecraft:sapling1", agz.b("{Name:'minecraft:potted_spruce_sapling'}"));
      var0.put("minecraft:sapling2", agz.b("{Name:'minecraft:potted_birch_sapling'}"));
      var0.put("minecraft:sapling3", agz.b("{Name:'minecraft:potted_jungle_sapling'}"));
      var0.put("minecraft:sapling4", agz.b("{Name:'minecraft:potted_acacia_sapling'}"));
      var0.put("minecraft:sapling5", agz.b("{Name:'minecraft:potted_dark_oak_sapling'}"));
      var0.put("minecraft:red_mushroom0", agz.b("{Name:'minecraft:potted_red_mushroom'}"));
      var0.put("minecraft:brown_mushroom0", agz.b("{Name:'minecraft:potted_brown_mushroom'}"));
      var0.put("minecraft:deadbush0", agz.b("{Name:'minecraft:potted_dead_bush'}"));
      var0.put("minecraft:tallgrass2", agz.b("{Name:'minecraft:potted_fern'}"));
      var0.put("minecraft:cactus0", agz.b(2240));
   });
   private static final Map<String, Dynamic<?>> o = (Map<String, Dynamic<?>>)DataFixUtils.make(Maps.newHashMap(), var0 -> {
      a(var0, 0, "skeleton", "skull");
      a(var0, 1, "wither_skeleton", "skull");
      a(var0, 2, "zombie", "head");
      a(var0, 3, "player", "head");
      a(var0, 4, "creeper", "head");
      a(var0, 5, "dragon", "head");
   });
   private static final Map<String, Dynamic<?>> p = (Map<String, Dynamic<?>>)DataFixUtils.make(Maps.newHashMap(), var0 -> {
      a(var0, "oak_door", 1024);
      a(var0, "iron_door", 1136);
      a(var0, "spruce_door", 3088);
      a(var0, "birch_door", 3104);
      a(var0, "jungle_door", 3120);
      a(var0, "acacia_door", 3136);
      a(var0, "dark_oak_door", 3152);
   });
   private static final Map<String, Dynamic<?>> q = (Map<String, Dynamic<?>>)DataFixUtils.make(Maps.newHashMap(), var0 -> {
      for (int _snowman = 0; _snowman < 26; _snowman++) {
         var0.put("true" + _snowman, agz.b("{Name:'minecraft:note_block',Properties:{powered:'true',note:'" + _snowman + "'}}"));
         var0.put("false" + _snowman, agz.b("{Name:'minecraft:note_block',Properties:{powered:'false',note:'" + _snowman + "'}}"));
      }
   });
   private static final Int2ObjectMap<String> r = (Int2ObjectMap<String>)DataFixUtils.make(new Int2ObjectOpenHashMap(), var0 -> {
      var0.put(0, "white");
      var0.put(1, "orange");
      var0.put(2, "magenta");
      var0.put(3, "light_blue");
      var0.put(4, "yellow");
      var0.put(5, "lime");
      var0.put(6, "pink");
      var0.put(7, "gray");
      var0.put(8, "light_gray");
      var0.put(9, "cyan");
      var0.put(10, "purple");
      var0.put(11, "blue");
      var0.put(12, "brown");
      var0.put(13, "green");
      var0.put(14, "red");
      var0.put(15, "black");
   });
   private static final Map<String, Dynamic<?>> s = (Map<String, Dynamic<?>>)DataFixUtils.make(Maps.newHashMap(), var0 -> {
      ObjectIterator var1 = r.int2ObjectEntrySet().iterator();

      while (var1.hasNext()) {
         Entry<String> _snowman = (Entry<String>)var1.next();
         if (!Objects.equals(_snowman.getValue(), "red")) {
            a(var0, _snowman.getIntKey(), (String)_snowman.getValue());
         }
      }
   });
   private static final Map<String, Dynamic<?>> t = (Map<String, Dynamic<?>>)DataFixUtils.make(Maps.newHashMap(), var0 -> {
      ObjectIterator var1 = r.int2ObjectEntrySet().iterator();

      while (var1.hasNext()) {
         Entry<String> _snowman = (Entry<String>)var1.next();
         if (!Objects.equals(_snowman.getValue(), "white")) {
            b(var0, 15 - _snowman.getIntKey(), (String)_snowman.getValue());
         }
      }
   });
   private static final Dynamic<?> u = agz.b(0);

   public ahe(Schema var1, boolean var2) {
      super(_snowman, _snowman);
   }

   private static void a(Map<String, Dynamic<?>> var0, int var1, String var2, String var3) {
      _snowman.put(_snowman + "north", agz.b("{Name:'minecraft:" + _snowman + "_wall_" + _snowman + "',Properties:{facing:'north'}}"));
      _snowman.put(_snowman + "east", agz.b("{Name:'minecraft:" + _snowman + "_wall_" + _snowman + "',Properties:{facing:'east'}}"));
      _snowman.put(_snowman + "south", agz.b("{Name:'minecraft:" + _snowman + "_wall_" + _snowman + "',Properties:{facing:'south'}}"));
      _snowman.put(_snowman + "west", agz.b("{Name:'minecraft:" + _snowman + "_wall_" + _snowman + "',Properties:{facing:'west'}}"));

      for (int _snowman = 0; _snowman < 16; _snowman++) {
         _snowman.put(_snowman + "" + _snowman, agz.b("{Name:'minecraft:" + _snowman + "_" + _snowman + "',Properties:{rotation:'" + _snowman + "'}}"));
      }
   }

   private static void a(Map<String, Dynamic<?>> var0, String var1, int var2) {
      _snowman.put(
         "minecraft:" + _snowman + "eastlowerleftfalsefalse",
         agz.b("{Name:'minecraft:" + _snowman + "',Properties:{facing:'east',half:'lower',hinge:'left',open:'false',powered:'false'}}")
      );
      _snowman.put(
         "minecraft:" + _snowman + "eastlowerleftfalsetrue",
         agz.b("{Name:'minecraft:" + _snowman + "',Properties:{facing:'east',half:'lower',hinge:'left',open:'false',powered:'true'}}")
      );
      _snowman.put(
         "minecraft:" + _snowman + "eastlowerlefttruefalse",
         agz.b("{Name:'minecraft:" + _snowman + "',Properties:{facing:'east',half:'lower',hinge:'left',open:'true',powered:'false'}}")
      );
      _snowman.put(
         "minecraft:" + _snowman + "eastlowerlefttruetrue",
         agz.b("{Name:'minecraft:" + _snowman + "',Properties:{facing:'east',half:'lower',hinge:'left',open:'true',powered:'true'}}")
      );
      _snowman.put("minecraft:" + _snowman + "eastlowerrightfalsefalse", agz.b(_snowman));
      _snowman.put(
         "minecraft:" + _snowman + "eastlowerrightfalsetrue",
         agz.b("{Name:'minecraft:" + _snowman + "',Properties:{facing:'east',half:'lower',hinge:'right',open:'false',powered:'true'}}")
      );
      _snowman.put("minecraft:" + _snowman + "eastlowerrighttruefalse", agz.b(_snowman + 4));
      _snowman.put(
         "minecraft:" + _snowman + "eastlowerrighttruetrue",
         agz.b("{Name:'minecraft:" + _snowman + "',Properties:{facing:'east',half:'lower',hinge:'right',open:'true',powered:'true'}}")
      );
      _snowman.put("minecraft:" + _snowman + "eastupperleftfalsefalse", agz.b(_snowman + 8));
      _snowman.put("minecraft:" + _snowman + "eastupperleftfalsetrue", agz.b(_snowman + 10));
      _snowman.put(
         "minecraft:" + _snowman + "eastupperlefttruefalse",
         agz.b("{Name:'minecraft:" + _snowman + "',Properties:{facing:'east',half:'upper',hinge:'left',open:'true',powered:'false'}}")
      );
      _snowman.put(
         "minecraft:" + _snowman + "eastupperlefttruetrue",
         agz.b("{Name:'minecraft:" + _snowman + "',Properties:{facing:'east',half:'upper',hinge:'left',open:'true',powered:'true'}}")
      );
      _snowman.put("minecraft:" + _snowman + "eastupperrightfalsefalse", agz.b(_snowman + 9));
      _snowman.put("minecraft:" + _snowman + "eastupperrightfalsetrue", agz.b(_snowman + 11));
      _snowman.put(
         "minecraft:" + _snowman + "eastupperrighttruefalse",
         agz.b("{Name:'minecraft:" + _snowman + "',Properties:{facing:'east',half:'upper',hinge:'right',open:'true',powered:'false'}}")
      );
      _snowman.put(
         "minecraft:" + _snowman + "eastupperrighttruetrue",
         agz.b("{Name:'minecraft:" + _snowman + "',Properties:{facing:'east',half:'upper',hinge:'right',open:'true',powered:'true'}}")
      );
      _snowman.put(
         "minecraft:" + _snowman + "northlowerleftfalsefalse",
         agz.b("{Name:'minecraft:" + _snowman + "',Properties:{facing:'north',half:'lower',hinge:'left',open:'false',powered:'false'}}")
      );
      _snowman.put(
         "minecraft:" + _snowman + "northlowerleftfalsetrue",
         agz.b("{Name:'minecraft:" + _snowman + "',Properties:{facing:'north',half:'lower',hinge:'left',open:'false',powered:'true'}}")
      );
      _snowman.put(
         "minecraft:" + _snowman + "northlowerlefttruefalse",
         agz.b("{Name:'minecraft:" + _snowman + "',Properties:{facing:'north',half:'lower',hinge:'left',open:'true',powered:'false'}}")
      );
      _snowman.put(
         "minecraft:" + _snowman + "northlowerlefttruetrue",
         agz.b("{Name:'minecraft:" + _snowman + "',Properties:{facing:'north',half:'lower',hinge:'left',open:'true',powered:'true'}}")
      );
      _snowman.put("minecraft:" + _snowman + "northlowerrightfalsefalse", agz.b(_snowman + 3));
      _snowman.put(
         "minecraft:" + _snowman + "northlowerrightfalsetrue",
         agz.b("{Name:'minecraft:" + _snowman + "',Properties:{facing:'north',half:'lower',hinge:'right',open:'false',powered:'true'}}")
      );
      _snowman.put("minecraft:" + _snowman + "northlowerrighttruefalse", agz.b(_snowman + 7));
      _snowman.put(
         "minecraft:" + _snowman + "northlowerrighttruetrue",
         agz.b("{Name:'minecraft:" + _snowman + "',Properties:{facing:'north',half:'lower',hinge:'right',open:'true',powered:'true'}}")
      );
      _snowman.put(
         "minecraft:" + _snowman + "northupperleftfalsefalse",
         agz.b("{Name:'minecraft:" + _snowman + "',Properties:{facing:'north',half:'upper',hinge:'left',open:'false',powered:'false'}}")
      );
      _snowman.put(
         "minecraft:" + _snowman + "northupperleftfalsetrue",
         agz.b("{Name:'minecraft:" + _snowman + "',Properties:{facing:'north',half:'upper',hinge:'left',open:'false',powered:'true'}}")
      );
      _snowman.put(
         "minecraft:" + _snowman + "northupperlefttruefalse",
         agz.b("{Name:'minecraft:" + _snowman + "',Properties:{facing:'north',half:'upper',hinge:'left',open:'true',powered:'false'}}")
      );
      _snowman.put(
         "minecraft:" + _snowman + "northupperlefttruetrue",
         agz.b("{Name:'minecraft:" + _snowman + "',Properties:{facing:'north',half:'upper',hinge:'left',open:'true',powered:'true'}}")
      );
      _snowman.put(
         "minecraft:" + _snowman + "northupperrightfalsefalse",
         agz.b("{Name:'minecraft:" + _snowman + "',Properties:{facing:'north',half:'upper',hinge:'right',open:'false',powered:'false'}}")
      );
      _snowman.put(
         "minecraft:" + _snowman + "northupperrightfalsetrue",
         agz.b("{Name:'minecraft:" + _snowman + "',Properties:{facing:'north',half:'upper',hinge:'right',open:'false',powered:'true'}}")
      );
      _snowman.put(
         "minecraft:" + _snowman + "northupperrighttruefalse",
         agz.b("{Name:'minecraft:" + _snowman + "',Properties:{facing:'north',half:'upper',hinge:'right',open:'true',powered:'false'}}")
      );
      _snowman.put(
         "minecraft:" + _snowman + "northupperrighttruetrue",
         agz.b("{Name:'minecraft:" + _snowman + "',Properties:{facing:'north',half:'upper',hinge:'right',open:'true',powered:'true'}}")
      );
      _snowman.put(
         "minecraft:" + _snowman + "southlowerleftfalsefalse",
         agz.b("{Name:'minecraft:" + _snowman + "',Properties:{facing:'south',half:'lower',hinge:'left',open:'false',powered:'false'}}")
      );
      _snowman.put(
         "minecraft:" + _snowman + "southlowerleftfalsetrue",
         agz.b("{Name:'minecraft:" + _snowman + "',Properties:{facing:'south',half:'lower',hinge:'left',open:'false',powered:'true'}}")
      );
      _snowman.put(
         "minecraft:" + _snowman + "southlowerlefttruefalse",
         agz.b("{Name:'minecraft:" + _snowman + "',Properties:{facing:'south',half:'lower',hinge:'left',open:'true',powered:'false'}}")
      );
      _snowman.put(
         "minecraft:" + _snowman + "southlowerlefttruetrue",
         agz.b("{Name:'minecraft:" + _snowman + "',Properties:{facing:'south',half:'lower',hinge:'left',open:'true',powered:'true'}}")
      );
      _snowman.put("minecraft:" + _snowman + "southlowerrightfalsefalse", agz.b(_snowman + 1));
      _snowman.put(
         "minecraft:" + _snowman + "southlowerrightfalsetrue",
         agz.b("{Name:'minecraft:" + _snowman + "',Properties:{facing:'south',half:'lower',hinge:'right',open:'false',powered:'true'}}")
      );
      _snowman.put("minecraft:" + _snowman + "southlowerrighttruefalse", agz.b(_snowman + 5));
      _snowman.put(
         "minecraft:" + _snowman + "southlowerrighttruetrue",
         agz.b("{Name:'minecraft:" + _snowman + "',Properties:{facing:'south',half:'lower',hinge:'right',open:'true',powered:'true'}}")
      );
      _snowman.put(
         "minecraft:" + _snowman + "southupperleftfalsefalse",
         agz.b("{Name:'minecraft:" + _snowman + "',Properties:{facing:'south',half:'upper',hinge:'left',open:'false',powered:'false'}}")
      );
      _snowman.put(
         "minecraft:" + _snowman + "southupperleftfalsetrue",
         agz.b("{Name:'minecraft:" + _snowman + "',Properties:{facing:'south',half:'upper',hinge:'left',open:'false',powered:'true'}}")
      );
      _snowman.put(
         "minecraft:" + _snowman + "southupperlefttruefalse",
         agz.b("{Name:'minecraft:" + _snowman + "',Properties:{facing:'south',half:'upper',hinge:'left',open:'true',powered:'false'}}")
      );
      _snowman.put(
         "minecraft:" + _snowman + "southupperlefttruetrue",
         agz.b("{Name:'minecraft:" + _snowman + "',Properties:{facing:'south',half:'upper',hinge:'left',open:'true',powered:'true'}}")
      );
      _snowman.put(
         "minecraft:" + _snowman + "southupperrightfalsefalse",
         agz.b("{Name:'minecraft:" + _snowman + "',Properties:{facing:'south',half:'upper',hinge:'right',open:'false',powered:'false'}}")
      );
      _snowman.put(
         "minecraft:" + _snowman + "southupperrightfalsetrue",
         agz.b("{Name:'minecraft:" + _snowman + "',Properties:{facing:'south',half:'upper',hinge:'right',open:'false',powered:'true'}}")
      );
      _snowman.put(
         "minecraft:" + _snowman + "southupperrighttruefalse",
         agz.b("{Name:'minecraft:" + _snowman + "',Properties:{facing:'south',half:'upper',hinge:'right',open:'true',powered:'false'}}")
      );
      _snowman.put(
         "minecraft:" + _snowman + "southupperrighttruetrue",
         agz.b("{Name:'minecraft:" + _snowman + "',Properties:{facing:'south',half:'upper',hinge:'right',open:'true',powered:'true'}}")
      );
      _snowman.put(
         "minecraft:" + _snowman + "westlowerleftfalsefalse",
         agz.b("{Name:'minecraft:" + _snowman + "',Properties:{facing:'west',half:'lower',hinge:'left',open:'false',powered:'false'}}")
      );
      _snowman.put(
         "minecraft:" + _snowman + "westlowerleftfalsetrue",
         agz.b("{Name:'minecraft:" + _snowman + "',Properties:{facing:'west',half:'lower',hinge:'left',open:'false',powered:'true'}}")
      );
      _snowman.put(
         "minecraft:" + _snowman + "westlowerlefttruefalse",
         agz.b("{Name:'minecraft:" + _snowman + "',Properties:{facing:'west',half:'lower',hinge:'left',open:'true',powered:'false'}}")
      );
      _snowman.put(
         "minecraft:" + _snowman + "westlowerlefttruetrue",
         agz.b("{Name:'minecraft:" + _snowman + "',Properties:{facing:'west',half:'lower',hinge:'left',open:'true',powered:'true'}}")
      );
      _snowman.put("minecraft:" + _snowman + "westlowerrightfalsefalse", agz.b(_snowman + 2));
      _snowman.put(
         "minecraft:" + _snowman + "westlowerrightfalsetrue",
         agz.b("{Name:'minecraft:" + _snowman + "',Properties:{facing:'west',half:'lower',hinge:'right',open:'false',powered:'true'}}")
      );
      _snowman.put("minecraft:" + _snowman + "westlowerrighttruefalse", agz.b(_snowman + 6));
      _snowman.put(
         "minecraft:" + _snowman + "westlowerrighttruetrue",
         agz.b("{Name:'minecraft:" + _snowman + "',Properties:{facing:'west',half:'lower',hinge:'right',open:'true',powered:'true'}}")
      );
      _snowman.put(
         "minecraft:" + _snowman + "westupperleftfalsefalse",
         agz.b("{Name:'minecraft:" + _snowman + "',Properties:{facing:'west',half:'upper',hinge:'left',open:'false',powered:'false'}}")
      );
      _snowman.put(
         "minecraft:" + _snowman + "westupperleftfalsetrue",
         agz.b("{Name:'minecraft:" + _snowman + "',Properties:{facing:'west',half:'upper',hinge:'left',open:'false',powered:'true'}}")
      );
      _snowman.put(
         "minecraft:" + _snowman + "westupperlefttruefalse",
         agz.b("{Name:'minecraft:" + _snowman + "',Properties:{facing:'west',half:'upper',hinge:'left',open:'true',powered:'false'}}")
      );
      _snowman.put(
         "minecraft:" + _snowman + "westupperlefttruetrue",
         agz.b("{Name:'minecraft:" + _snowman + "',Properties:{facing:'west',half:'upper',hinge:'left',open:'true',powered:'true'}}")
      );
      _snowman.put(
         "minecraft:" + _snowman + "westupperrightfalsefalse",
         agz.b("{Name:'minecraft:" + _snowman + "',Properties:{facing:'west',half:'upper',hinge:'right',open:'false',powered:'false'}}")
      );
      _snowman.put(
         "minecraft:" + _snowman + "westupperrightfalsetrue",
         agz.b("{Name:'minecraft:" + _snowman + "',Properties:{facing:'west',half:'upper',hinge:'right',open:'false',powered:'true'}}")
      );
      _snowman.put(
         "minecraft:" + _snowman + "westupperrighttruefalse",
         agz.b("{Name:'minecraft:" + _snowman + "',Properties:{facing:'west',half:'upper',hinge:'right',open:'true',powered:'false'}}")
      );
      _snowman.put(
         "minecraft:" + _snowman + "westupperrighttruetrue",
         agz.b("{Name:'minecraft:" + _snowman + "',Properties:{facing:'west',half:'upper',hinge:'right',open:'true',powered:'true'}}")
      );
   }

   private static void a(Map<String, Dynamic<?>> var0, int var1, String var2) {
      _snowman.put("southfalsefoot" + _snowman, agz.b("{Name:'minecraft:" + _snowman + "_bed',Properties:{facing:'south',occupied:'false',part:'foot'}}"));
      _snowman.put("westfalsefoot" + _snowman, agz.b("{Name:'minecraft:" + _snowman + "_bed',Properties:{facing:'west',occupied:'false',part:'foot'}}"));
      _snowman.put("northfalsefoot" + _snowman, agz.b("{Name:'minecraft:" + _snowman + "_bed',Properties:{facing:'north',occupied:'false',part:'foot'}}"));
      _snowman.put("eastfalsefoot" + _snowman, agz.b("{Name:'minecraft:" + _snowman + "_bed',Properties:{facing:'east',occupied:'false',part:'foot'}}"));
      _snowman.put("southfalsehead" + _snowman, agz.b("{Name:'minecraft:" + _snowman + "_bed',Properties:{facing:'south',occupied:'false',part:'head'}}"));
      _snowman.put("westfalsehead" + _snowman, agz.b("{Name:'minecraft:" + _snowman + "_bed',Properties:{facing:'west',occupied:'false',part:'head'}}"));
      _snowman.put("northfalsehead" + _snowman, agz.b("{Name:'minecraft:" + _snowman + "_bed',Properties:{facing:'north',occupied:'false',part:'head'}}"));
      _snowman.put("eastfalsehead" + _snowman, agz.b("{Name:'minecraft:" + _snowman + "_bed',Properties:{facing:'east',occupied:'false',part:'head'}}"));
      _snowman.put("southtruehead" + _snowman, agz.b("{Name:'minecraft:" + _snowman + "_bed',Properties:{facing:'south',occupied:'true',part:'head'}}"));
      _snowman.put("westtruehead" + _snowman, agz.b("{Name:'minecraft:" + _snowman + "_bed',Properties:{facing:'west',occupied:'true',part:'head'}}"));
      _snowman.put("northtruehead" + _snowman, agz.b("{Name:'minecraft:" + _snowman + "_bed',Properties:{facing:'north',occupied:'true',part:'head'}}"));
      _snowman.put("easttruehead" + _snowman, agz.b("{Name:'minecraft:" + _snowman + "_bed',Properties:{facing:'east',occupied:'true',part:'head'}}"));
   }

   private static void b(Map<String, Dynamic<?>> var0, int var1, String var2) {
      for (int _snowman = 0; _snowman < 16; _snowman++) {
         _snowman.put("" + _snowman + "_" + _snowman, agz.b("{Name:'minecraft:" + _snowman + "_banner',Properties:{rotation:'" + _snowman + "'}}"));
      }

      _snowman.put("north_" + _snowman, agz.b("{Name:'minecraft:" + _snowman + "_wall_banner',Properties:{facing:'north'}}"));
      _snowman.put("south_" + _snowman, agz.b("{Name:'minecraft:" + _snowman + "_wall_banner',Properties:{facing:'south'}}"));
      _snowman.put("west_" + _snowman, agz.b("{Name:'minecraft:" + _snowman + "_wall_banner',Properties:{facing:'west'}}"));
      _snowman.put("east_" + _snowman, agz.b("{Name:'minecraft:" + _snowman + "_wall_banner',Properties:{facing:'east'}}"));
   }

   public static String a(Dynamic<?> var0) {
      return _snowman.get("Name").asString("");
   }

   public static String a(Dynamic<?> var0, String var1) {
      return _snowman.get("Properties").get(_snowman).asString("");
   }

   public static int a(aet<Dynamic<?>> var0, Dynamic<?> var1) {
      int _snowman = _snowman.a(_snowman);
      if (_snowman == -1) {
         _snowman = _snowman.c(_snowman);
      }

      return _snowman;
   }

   private Dynamic<?> b(Dynamic<?> var1) {
      Optional<? extends Dynamic<?>> _snowman = _snowman.get("Level").result();
      return _snowman.isPresent() && _snowman.get().get("Sections").asStreamOpt().result().isPresent() ? _snowman.set("Level", new ahe.d((Dynamic<?>)_snowman.get()).a()) : _snowman;
   }

   public TypeRewriteRule makeRule() {
      Type<?> _snowman = this.getInputSchema().getType(akn.c);
      Type<?> _snowmanx = this.getOutputSchema().getType(akn.c);
      return this.writeFixAndRead("ChunkPalettedStorageFix", _snowman, _snowmanx, this::b);
   }

   public static int a(boolean var0, boolean var1, boolean var2, boolean var3) {
      int _snowman = 0;
      if (_snowman) {
         if (_snowman) {
            _snowman |= 2;
         } else if (_snowman) {
            _snowman |= 128;
         } else {
            _snowman |= 1;
         }
      } else if (_snowman) {
         if (_snowman) {
            _snowman |= 32;
         } else if (_snowman) {
            _snowman |= 8;
         } else {
            _snowman |= 16;
         }
      } else if (_snowman) {
         _snowman |= 4;
      } else if (_snowman) {
         _snowman |= 64;
      }

      return _snowman;
   }

   static {
      c.set(2);
      c.set(3);
      c.set(110);
      c.set(140);
      c.set(144);
      c.set(25);
      c.set(86);
      c.set(26);
      c.set(176);
      c.set(177);
      c.set(175);
      c.set(64);
      c.set(71);
      c.set(193);
      c.set(194);
      c.set(195);
      c.set(196);
      c.set(197);
      b.set(54);
      b.set(146);
      b.set(25);
      b.set(26);
      b.set(51);
      b.set(53);
      b.set(67);
      b.set(108);
      b.set(109);
      b.set(114);
      b.set(128);
      b.set(134);
      b.set(135);
      b.set(136);
      b.set(156);
      b.set(163);
      b.set(164);
      b.set(180);
      b.set(203);
      b.set(55);
      b.set(85);
      b.set(113);
      b.set(188);
      b.set(189);
      b.set(190);
      b.set(191);
      b.set(192);
      b.set(93);
      b.set(94);
      b.set(101);
      b.set(102);
      b.set(160);
      b.set(106);
      b.set(107);
      b.set(183);
      b.set(184);
      b.set(185);
      b.set(186);
      b.set(187);
      b.set(132);
      b.set(139);
      b.set(199);
   }

   static class a {
      private final byte[] a;

      public a() {
         this.a = new byte[2048];
      }

      public a(byte[] var1) {
         this.a = _snowman;
         if (_snowman.length != 2048) {
            throw new IllegalArgumentException("ChunkNibbleArrays should be 2048 bytes not: " + _snowman.length);
         }
      }

      public int a(int var1, int var2, int var3) {
         int _snowman = this.b(_snowman << 8 | _snowman << 4 | _snowman);
         return this.a(_snowman << 8 | _snowman << 4 | _snowman) ? this.a[_snowman] & 15 : this.a[_snowman] >> 4 & 15;
      }

      private boolean a(int var1) {
         return (_snowman & 1) == 0;
      }

      private int b(int var1) {
         return _snowman >> 1;
      }
   }

   public static enum b {
      a(ahe.b.b.b, ahe.b.a.b),
      b(ahe.b.b.a, ahe.b.a.b),
      c(ahe.b.b.b, ahe.b.a.c),
      d(ahe.b.b.a, ahe.b.a.c),
      e(ahe.b.b.b, ahe.b.a.a),
      f(ahe.b.b.a, ahe.b.a.a);

      private final ahe.b.a g;
      private final ahe.b.b h;

      private b(ahe.b.b var3, ahe.b.a var4) {
         this.g = _snowman;
         this.h = _snowman;
      }

      public ahe.b.b a() {
         return this.h;
      }

      public ahe.b.a b() {
         return this.g;
      }

      public static enum a {
         a,
         b,
         c;

         private a() {
         }
      }

      public static enum b {
         a(1),
         b(-1);

         private final int c;

         private b(int var3) {
            this.c = _snowman;
         }

         public int a() {
            return this.c;
         }
      }
   }

   static class c {
      private final aet<Dynamic<?>> b = new aet<>(32);
      private final List<Dynamic<?>> c;
      private final Dynamic<?> d;
      private final boolean e;
      private final Int2ObjectMap<IntList> f = new Int2ObjectLinkedOpenHashMap();
      private final IntList g = new IntArrayList();
      public final int a;
      private final Set<Dynamic<?>> h = Sets.newIdentityHashSet();
      private final int[] i = new int[4096];

      public c(Dynamic<?> var1) {
         this.c = Lists.newArrayList();
         this.d = _snowman;
         this.a = _snowman.get("Y").asInt(0);
         this.e = _snowman.get("Blocks").result().isPresent();
      }

      public Dynamic<?> a(int var1) {
         if (_snowman >= 0 && _snowman <= 4095) {
            Dynamic<?> _snowman = this.b.a(this.i[_snowman]);
            return _snowman == null ? ahe.u : _snowman;
         } else {
            return ahe.u;
         }
      }

      public void a(int var1, Dynamic<?> var2) {
         if (this.h.add(_snowman)) {
            this.c.add("%%FILTER_ME%%".equals(ahe.a(_snowman)) ? ahe.u : _snowman);
         }

         this.i[_snowman] = ahe.a(this.b, _snowman);
      }

      public int b(int var1) {
         if (!this.e) {
            return _snowman;
         } else {
            ByteBuffer _snowman = (ByteBuffer)this.d.get("Blocks").asByteBufferOpt().result().get();
            ahe.a _snowmanx = this.d.get("Data").asByteBufferOpt().map(var0 -> new ahe.a(DataFixUtils.toArray(var0))).result().orElseGet(ahe.a::new);
            ahe.a _snowmanxx = this.d.get("Add").asByteBufferOpt().map(var0 -> new ahe.a(DataFixUtils.toArray(var0))).result().orElseGet(ahe.a::new);
            this.h.add(ahe.u);
            ahe.a(this.b, ahe.u);
            this.c.add(ahe.u);

            for (int _snowmanxxx = 0; _snowmanxxx < 4096; _snowmanxxx++) {
               int _snowmanxxxx = _snowmanxxx & 15;
               int _snowmanxxxxx = _snowmanxxx >> 8 & 15;
               int _snowmanxxxxxx = _snowmanxxx >> 4 & 15;
               int _snowmanxxxxxxx = _snowmanxx.a(_snowmanxxxx, _snowmanxxxxx, _snowmanxxxxxx) << 12 | (_snowman.get(_snowmanxxx) & 255) << 4 | _snowmanx.a(_snowmanxxxx, _snowmanxxxxx, _snowmanxxxxxx);
               if (ahe.c.get(_snowmanxxxxxxx >> 4)) {
                  this.a(_snowmanxxxxxxx >> 4, _snowmanxxx);
               }

               if (ahe.b.get(_snowmanxxxxxxx >> 4)) {
                  int _snowmanxxxxxxxx = ahe.a(_snowmanxxxx == 0, _snowmanxxxx == 15, _snowmanxxxxxx == 0, _snowmanxxxxxx == 15);
                  if (_snowmanxxxxxxxx == 0) {
                     this.g.add(_snowmanxxx);
                  } else {
                     _snowman |= _snowmanxxxxxxxx;
                  }
               }

               this.a(_snowmanxxx, agz.b(_snowmanxxxxxxx));
            }

            return _snowman;
         }
      }

      private void a(int var1, int var2) {
         IntList _snowman = (IntList)this.f.get(_snowman);
         if (_snowman == null) {
            _snowman = new IntArrayList();
            this.f.put(_snowman, _snowman);
         }

         _snowman.add(_snowman);
      }

      public Dynamic<?> a() {
         Dynamic<?> _snowman = this.d;
         if (!this.e) {
            return _snowman;
         } else {
            _snowman = _snowman.set("Palette", _snowman.createList(this.c.stream()));
            int _snowmanx = Math.max(4, DataFixUtils.ceillog2(this.h.size()));
            agc _snowmanxx = new agc(_snowmanx, 4096);

            for (int _snowmanxxx = 0; _snowmanxxx < this.i.length; _snowmanxxx++) {
               _snowmanxx.a(_snowmanxxx, this.i[_snowmanxxx]);
            }

            _snowman = _snowman.set("BlockStates", _snowman.createLongList(Arrays.stream(_snowmanxx.a())));
            _snowman = _snowman.remove("Blocks");
            _snowman = _snowman.remove("Data");
            return _snowman.remove("Add");
         }
      }
   }

   static final class d {
      private int a;
      private final ahe.c[] b = new ahe.c[16];
      private final Dynamic<?> c;
      private final int d;
      private final int e;
      private final Int2ObjectMap<Dynamic<?>> f = new Int2ObjectLinkedOpenHashMap(16);

      public d(Dynamic<?> var1) {
         this.c = _snowman;
         this.d = _snowman.get("xPos").asInt(0) << 4;
         this.e = _snowman.get("zPos").asInt(0) << 4;
         _snowman.get("TileEntities").asStreamOpt().result().ifPresent(var1x -> var1x.forEach(var1xx -> {
               int _snowman = var1xx.get("x").asInt(0) - this.d & 15;
               int _snowmanx = var1xx.get("y").asInt(0);
               int _snowmanxx = var1xx.get("z").asInt(0) - this.e & 15;
               int _snowmanxxx = _snowmanx << 8 | _snowmanxx << 4 | _snowman;
               if (this.f.put(_snowmanxxx, var1xx) != null) {
                  ahe.a.warn("In chunk: {}x{} found a duplicate block entity at position: [{}, {}, {}]", this.d, this.e, _snowman, _snowmanx, _snowmanxx);
               }
            }));
         boolean _snowman = _snowman.get("convertedFromAlphaFormat").asBoolean(false);
         _snowman.get("Sections").asStreamOpt().result().ifPresent(var1x -> var1x.forEach(var1xx -> {
               ahe.c _snowmanx = new ahe.c((Dynamic<?>)var1xx);
               this.a = _snowmanx.b(this.a);
               this.b[_snowmanx.a] = _snowmanx;
            }));

         for (ahe.c _snowmanx : this.b) {
            if (_snowmanx != null) {
               ObjectIterator var7 = _snowmanx.f.entrySet().iterator();

               while (var7.hasNext()) {
                  java.util.Map.Entry<Integer, IntList> _snowmanxx = (java.util.Map.Entry<Integer, IntList>)var7.next();
                  int _snowmanxxx = _snowmanx.a << 12;
                  switch (_snowmanxx.getKey()) {
                     case 2:
                        IntListIterator var30 = _snowmanxx.getValue().iterator();

                        while (var30.hasNext()) {
                           int _snowmanxxxx = (Integer)var30.next();
                           _snowmanxxxx |= _snowmanxxx;
                           Dynamic<?> _snowmanxxxxx = this.a(_snowmanxxxx);
                           if ("minecraft:grass_block".equals(ahe.a(_snowmanxxxxx))) {
                              String _snowmanxxxxxx = ahe.a(this.a(a(_snowmanxxxx, ahe.b.b)));
                              if ("minecraft:snow".equals(_snowmanxxxxxx) || "minecraft:snow_layer".equals(_snowmanxxxxxx)) {
                                 this.a(_snowmanxxxx, ahe.f);
                              }
                           }
                        }
                        break;
                     case 3:
                        IntListIterator var29 = _snowmanxx.getValue().iterator();

                        while (var29.hasNext()) {
                           int _snowmanxxxx = (Integer)var29.next();
                           _snowmanxxxx |= _snowmanxxx;
                           Dynamic<?> _snowmanxxxxx = this.a(_snowmanxxxx);
                           if ("minecraft:podzol".equals(ahe.a(_snowmanxxxxx))) {
                              String _snowmanxxxxxx = ahe.a(this.a(a(_snowmanxxxx, ahe.b.b)));
                              if ("minecraft:snow".equals(_snowmanxxxxxx) || "minecraft:snow_layer".equals(_snowmanxxxxxx)) {
                                 this.a(_snowmanxxxx, ahe.e);
                              }
                           }
                        }
                        break;
                     case 25:
                        IntListIterator var28 = _snowmanxx.getValue().iterator();

                        while (var28.hasNext()) {
                           int _snowmanxxxx = (Integer)var28.next();
                           _snowmanxxxx |= _snowmanxxx;
                           Dynamic<?> _snowmanxxxxx = this.c(_snowmanxxxx);
                           if (_snowmanxxxxx != null) {
                              String _snowmanxxxxxx = Boolean.toString(_snowmanxxxxx.get("powered").asBoolean(false))
                                 + (byte)Math.min(Math.max(_snowmanxxxxx.get("note").asInt(0), 0), 24);
                              this.a(_snowmanxxxx, ahe.q.getOrDefault(_snowmanxxxxxx, ahe.q.get("false0")));
                           }
                        }
                        break;
                     case 26:
                        IntListIterator var27 = _snowmanxx.getValue().iterator();

                        while (var27.hasNext()) {
                           int _snowmanxxxx = (Integer)var27.next();
                           _snowmanxxxx |= _snowmanxxx;
                           Dynamic<?> _snowmanxxxxx = this.b(_snowmanxxxx);
                           Dynamic<?> _snowmanxxxxxx = this.a(_snowmanxxxx);
                           if (_snowmanxxxxx != null) {
                              int _snowmanxxxxxxx = _snowmanxxxxx.get("color").asInt(0);
                              if (_snowmanxxxxxxx != 14 && _snowmanxxxxxxx >= 0 && _snowmanxxxxxxx < 16) {
                                 String _snowmanxxxxxxxx = ahe.a(_snowmanxxxxxx, "facing") + ahe.a(_snowmanxxxxxx, "occupied") + ahe.a(_snowmanxxxxxx, "part") + _snowmanxxxxxxx;
                                 if (ahe.s.containsKey(_snowmanxxxxxxxx)) {
                                    this.a(_snowmanxxxx, ahe.s.get(_snowmanxxxxxxxx));
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
                        IntListIterator var26 = _snowmanxx.getValue().iterator();

                        while (var26.hasNext()) {
                           int _snowmanxxxx = (Integer)var26.next();
                           _snowmanxxxx |= _snowmanxxx;
                           Dynamic<?> _snowmanxxxxx = this.a(_snowmanxxxx);
                           if (ahe.a(_snowmanxxxxx).endsWith("_door")) {
                              Dynamic<?> _snowmanxxxxxx = this.a(_snowmanxxxx);
                              if ("lower".equals(ahe.a(_snowmanxxxxxx, "half"))) {
                                 int _snowmanxxxxxxx = a(_snowmanxxxx, ahe.b.b);
                                 Dynamic<?> _snowmanxxxxxxxx = this.a(_snowmanxxxxxxx);
                                 String _snowmanxxxxxxxxx = ahe.a(_snowmanxxxxxx);
                                 if (_snowmanxxxxxxxxx.equals(ahe.a(_snowmanxxxxxxxx))) {
                                    String _snowmanxxxxxxxxxx = ahe.a(_snowmanxxxxxx, "facing");
                                    String _snowmanxxxxxxxxxxx = ahe.a(_snowmanxxxxxx, "open");
                                    String _snowmanxxxxxxxxxxxx = _snowman ? "left" : ahe.a(_snowmanxxxxxxxx, "hinge");
                                    String _snowmanxxxxxxxxxxxxx = _snowman ? "false" : ahe.a(_snowmanxxxxxxxx, "powered");
                                    this.a(_snowmanxxxx, ahe.p.get(_snowmanxxxxxxxxx + _snowmanxxxxxxxxxx + "lower" + _snowmanxxxxxxxxxxxx + _snowmanxxxxxxxxxxx + _snowmanxxxxxxxxxxxxx));
                                    this.a(_snowmanxxxxxxx, ahe.p.get(_snowmanxxxxxxxxx + _snowmanxxxxxxxxxx + "upper" + _snowmanxxxxxxxxxxxx + _snowmanxxxxxxxxxxx + _snowmanxxxxxxxxxxxxx));
                                 }
                              }
                           }
                        }
                        break;
                     case 86:
                        IntListIterator var25 = _snowmanxx.getValue().iterator();

                        while (var25.hasNext()) {
                           int _snowmanxxxx = (Integer)var25.next();
                           _snowmanxxxx |= _snowmanxxx;
                           Dynamic<?> _snowmanxxxxx = this.a(_snowmanxxxx);
                           if ("minecraft:carved_pumpkin".equals(ahe.a(_snowmanxxxxx))) {
                              String _snowmanxxxxxx = ahe.a(this.a(a(_snowmanxxxx, ahe.b.a)));
                              if ("minecraft:grass_block".equals(_snowmanxxxxxx) || "minecraft:dirt".equals(_snowmanxxxxxx)) {
                                 this.a(_snowmanxxxx, ahe.d);
                              }
                           }
                        }
                        break;
                     case 110:
                        IntListIterator var24 = _snowmanxx.getValue().iterator();

                        while (var24.hasNext()) {
                           int _snowmanxxxx = (Integer)var24.next();
                           _snowmanxxxx |= _snowmanxxx;
                           Dynamic<?> _snowmanxxxxx = this.a(_snowmanxxxx);
                           if ("minecraft:mycelium".equals(ahe.a(_snowmanxxxxx))) {
                              String _snowmanxxxxxx = ahe.a(this.a(a(_snowmanxxxx, ahe.b.b)));
                              if ("minecraft:snow".equals(_snowmanxxxxxx) || "minecraft:snow_layer".equals(_snowmanxxxxxx)) {
                                 this.a(_snowmanxxxx, ahe.g);
                              }
                           }
                        }
                        break;
                     case 140:
                        IntListIterator var23 = _snowmanxx.getValue().iterator();

                        while (var23.hasNext()) {
                           int _snowmanxxxx = (Integer)var23.next();
                           _snowmanxxxx |= _snowmanxxx;
                           Dynamic<?> _snowmanxxxxx = this.c(_snowmanxxxx);
                           if (_snowmanxxxxx != null) {
                              String _snowmanxxxxxx = _snowmanxxxxx.get("Item").asString("") + _snowmanxxxxx.get("Data").asInt(0);
                              this.a(_snowmanxxxx, ahe.n.getOrDefault(_snowmanxxxxxx, ahe.n.get("minecraft:air0")));
                           }
                        }
                        break;
                     case 144:
                        IntListIterator var22 = _snowmanxx.getValue().iterator();

                        while (var22.hasNext()) {
                           int _snowmanxxxx = (Integer)var22.next();
                           _snowmanxxxx |= _snowmanxxx;
                           Dynamic<?> _snowmanxxxxx = this.b(_snowmanxxxx);
                           if (_snowmanxxxxx != null) {
                              String _snowmanxxxxxx = String.valueOf(_snowmanxxxxx.get("SkullType").asInt(0));
                              String _snowmanxxxxxxx = ahe.a(this.a(_snowmanxxxx), "facing");
                              String _snowmanxxxxxxxx;
                              if (!"up".equals(_snowmanxxxxxxx) && !"down".equals(_snowmanxxxxxxx)) {
                                 _snowmanxxxxxxxx = _snowmanxxxxxx + _snowmanxxxxxxx;
                              } else {
                                 _snowmanxxxxxxxx = _snowmanxxxxxx + String.valueOf(_snowmanxxxxx.get("Rot").asInt(0));
                              }

                              _snowmanxxxxx.remove("SkullType");
                              _snowmanxxxxx.remove("facing");
                              _snowmanxxxxx.remove("Rot");
                              this.a(_snowmanxxxx, ahe.o.getOrDefault(_snowmanxxxxxxxx, ahe.o.get("0north")));
                           }
                        }
                        break;
                     case 175:
                        IntListIterator var21 = _snowmanxx.getValue().iterator();

                        while (var21.hasNext()) {
                           int _snowmanxxxx = (Integer)var21.next();
                           _snowmanxxxx |= _snowmanxxx;
                           Dynamic<?> _snowmanxxxxx = this.a(_snowmanxxxx);
                           if ("upper".equals(ahe.a(_snowmanxxxxx, "half"))) {
                              Dynamic<?> _snowmanxxxxxx = this.a(a(_snowmanxxxx, ahe.b.a));
                              String _snowmanxxxxxxx = ahe.a(_snowmanxxxxxx);
                              if ("minecraft:sunflower".equals(_snowmanxxxxxxx)) {
                                 this.a(_snowmanxxxx, ahe.h);
                              } else if ("minecraft:lilac".equals(_snowmanxxxxxxx)) {
                                 this.a(_snowmanxxxx, ahe.i);
                              } else if ("minecraft:tall_grass".equals(_snowmanxxxxxxx)) {
                                 this.a(_snowmanxxxx, ahe.j);
                              } else if ("minecraft:large_fern".equals(_snowmanxxxxxxx)) {
                                 this.a(_snowmanxxxx, ahe.k);
                              } else if ("minecraft:rose_bush".equals(_snowmanxxxxxxx)) {
                                 this.a(_snowmanxxxx, ahe.l);
                              } else if ("minecraft:peony".equals(_snowmanxxxxxxx)) {
                                 this.a(_snowmanxxxx, ahe.m);
                              }
                           }
                        }
                        break;
                     case 176:
                     case 177:
                        IntListIterator var10 = _snowmanxx.getValue().iterator();

                        while (var10.hasNext()) {
                           int _snowmanxxxx = (Integer)var10.next();
                           _snowmanxxxx |= _snowmanxxx;
                           Dynamic<?> _snowmanxxxxx = this.b(_snowmanxxxx);
                           Dynamic<?> _snowmanxxxxxx = this.a(_snowmanxxxx);
                           if (_snowmanxxxxx != null) {
                              int _snowmanxxxxxxx = _snowmanxxxxx.get("Base").asInt(0);
                              if (_snowmanxxxxxxx != 15 && _snowmanxxxxxxx >= 0 && _snowmanxxxxxxx < 16) {
                                 String _snowmanxxxxxxxx = ahe.a(_snowmanxxxxxx, _snowmanxx.getKey() == 176 ? "rotation" : "facing") + "_" + _snowmanxxxxxxx;
                                 if (ahe.t.containsKey(_snowmanxxxxxxxx)) {
                                    this.a(_snowmanxxxx, ahe.t.get(_snowmanxxxxxxxx));
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
      private Dynamic<?> b(int var1) {
         return (Dynamic<?>)this.f.get(_snowman);
      }

      @Nullable
      private Dynamic<?> c(int var1) {
         return (Dynamic<?>)this.f.remove(_snowman);
      }

      public static int a(int var0, ahe.b var1) {
         switch (_snowman.b()) {
            case a: {
               int _snowman = (_snowman & 15) + _snowman.a().a();
               return _snowman >= 0 && _snowman <= 15 ? _snowman & -16 | _snowman : -1;
            }
            case b: {
               int _snowman = (_snowman >> 8) + _snowman.a().a();
               return _snowman >= 0 && _snowman <= 255 ? _snowman & 0xFF | _snowman << 8 : -1;
            }
            case c: {
               int _snowman = (_snowman >> 4 & 15) + _snowman.a().a();
               return _snowman >= 0 && _snowman <= 15 ? _snowman & -241 | _snowman << 4 : -1;
            }
            default:
               return -1;
         }
      }

      private void a(int var1, Dynamic<?> var2) {
         if (_snowman >= 0 && _snowman <= 65535) {
            ahe.c _snowman = this.d(_snowman);
            if (_snowman != null) {
               _snowman.a(_snowman & 4095, _snowman);
            }
         }
      }

      @Nullable
      private ahe.c d(int var1) {
         int _snowman = _snowman >> 12;
         return _snowman < this.b.length ? this.b[_snowman] : null;
      }

      public Dynamic<?> a(int var1) {
         if (_snowman >= 0 && _snowman <= 65535) {
            ahe.c _snowman = this.d(_snowman);
            return _snowman == null ? ahe.u : _snowman.a(_snowman & 4095);
         } else {
            return ahe.u;
         }
      }

      public Dynamic<?> a() {
         Dynamic<?> _snowman = this.c;
         if (this.f.isEmpty()) {
            _snowman = _snowman.remove("TileEntities");
         } else {
            _snowman = _snowman.set("TileEntities", _snowman.createList(this.f.values().stream()));
         }

         Dynamic<?> _snowmanx = _snowman.emptyMap();
         List<Dynamic<?>> _snowmanxx = Lists.newArrayList();

         for (ahe.c _snowmanxxx : this.b) {
            if (_snowmanxxx != null) {
               _snowmanxx.add(_snowmanxxx.a());
               _snowmanx = _snowmanx.set(String.valueOf(_snowmanxxx.a), _snowmanx.createIntList(Arrays.stream(_snowmanxxx.g.toIntArray())));
            }
         }

         Dynamic<?> _snowmanxxxx = _snowman.emptyMap();
         _snowmanxxxx = _snowmanxxxx.set("Sides", _snowmanxxxx.createByte((byte)this.a));
         _snowmanxxxx = _snowmanxxxx.set("Indices", _snowmanx);
         return _snowman.set("UpgradeData", _snowmanxxxx).set("Sections", _snowmanxxxx.createList(_snowmanxx.stream()));
      }
   }
}
