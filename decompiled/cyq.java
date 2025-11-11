import com.google.common.collect.Sets;
import java.util.Collections;
import java.util.Set;

public class cyq {
   private static final Set<vk> az = Sets.newHashSet();
   private static final Set<vk> aA = Collections.unmodifiableSet(az);
   public static final vk a = new vk("empty");
   public static final vk b = a("chests/spawn_bonus_chest");
   public static final vk c = a("chests/end_city_treasure");
   public static final vk d = a("chests/simple_dungeon");
   public static final vk e = a("chests/village/village_weaponsmith");
   public static final vk f = a("chests/village/village_toolsmith");
   public static final vk g = a("chests/village/village_armorer");
   public static final vk h = a("chests/village/village_cartographer");
   public static final vk i = a("chests/village/village_mason");
   public static final vk j = a("chests/village/village_shepherd");
   public static final vk k = a("chests/village/village_butcher");
   public static final vk l = a("chests/village/village_fletcher");
   public static final vk m = a("chests/village/village_fisher");
   public static final vk n = a("chests/village/village_tannery");
   public static final vk o = a("chests/village/village_temple");
   public static final vk p = a("chests/village/village_desert_house");
   public static final vk q = a("chests/village/village_plains_house");
   public static final vk r = a("chests/village/village_taiga_house");
   public static final vk s = a("chests/village/village_snowy_house");
   public static final vk t = a("chests/village/village_savanna_house");
   public static final vk u = a("chests/abandoned_mineshaft");
   public static final vk v = a("chests/nether_bridge");
   public static final vk w = a("chests/stronghold_library");
   public static final vk x = a("chests/stronghold_crossing");
   public static final vk y = a("chests/stronghold_corridor");
   public static final vk z = a("chests/desert_pyramid");
   public static final vk A = a("chests/jungle_temple");
   public static final vk B = a("chests/jungle_temple_dispenser");
   public static final vk C = a("chests/igloo_chest");
   public static final vk D = a("chests/woodland_mansion");
   public static final vk E = a("chests/underwater_ruin_small");
   public static final vk F = a("chests/underwater_ruin_big");
   public static final vk G = a("chests/buried_treasure");
   public static final vk H = a("chests/shipwreck_map");
   public static final vk I = a("chests/shipwreck_supply");
   public static final vk J = a("chests/shipwreck_treasure");
   public static final vk K = a("chests/pillager_outpost");
   public static final vk L = a("chests/bastion_treasure");
   public static final vk M = a("chests/bastion_other");
   public static final vk N = a("chests/bastion_bridge");
   public static final vk O = a("chests/bastion_hoglin_stable");
   public static final vk P = a("chests/ruined_portal");
   public static final vk Q = a("entities/sheep/white");
   public static final vk R = a("entities/sheep/orange");
   public static final vk S = a("entities/sheep/magenta");
   public static final vk T = a("entities/sheep/light_blue");
   public static final vk U = a("entities/sheep/yellow");
   public static final vk V = a("entities/sheep/lime");
   public static final vk W = a("entities/sheep/pink");
   public static final vk X = a("entities/sheep/gray");
   public static final vk Y = a("entities/sheep/light_gray");
   public static final vk Z = a("entities/sheep/cyan");
   public static final vk aa = a("entities/sheep/purple");
   public static final vk ab = a("entities/sheep/blue");
   public static final vk ac = a("entities/sheep/brown");
   public static final vk ad = a("entities/sheep/green");
   public static final vk ae = a("entities/sheep/red");
   public static final vk af = a("entities/sheep/black");
   public static final vk ag = a("gameplay/fishing");
   public static final vk ah = a("gameplay/fishing/junk");
   public static final vk ai = a("gameplay/fishing/treasure");
   public static final vk aj = a("gameplay/fishing/fish");
   public static final vk ak = a("gameplay/cat_morning_gift");
   public static final vk al = a("gameplay/hero_of_the_village/armorer_gift");
   public static final vk am = a("gameplay/hero_of_the_village/butcher_gift");
   public static final vk an = a("gameplay/hero_of_the_village/cartographer_gift");
   public static final vk ao = a("gameplay/hero_of_the_village/cleric_gift");
   public static final vk ap = a("gameplay/hero_of_the_village/farmer_gift");
   public static final vk aq = a("gameplay/hero_of_the_village/fisherman_gift");
   public static final vk ar = a("gameplay/hero_of_the_village/fletcher_gift");
   public static final vk as = a("gameplay/hero_of_the_village/leatherworker_gift");
   public static final vk at = a("gameplay/hero_of_the_village/librarian_gift");
   public static final vk au = a("gameplay/hero_of_the_village/mason_gift");
   public static final vk av = a("gameplay/hero_of_the_village/shepherd_gift");
   public static final vk aw = a("gameplay/hero_of_the_village/toolsmith_gift");
   public static final vk ax = a("gameplay/hero_of_the_village/weaponsmith_gift");
   public static final vk ay = a("gameplay/piglin_bartering");

   private static vk a(String var0) {
      return a(new vk(_snowman));
   }

   private static vk a(vk var0) {
      if (az.add(_snowman)) {
         return _snowman;
      } else {
         throw new IllegalArgumentException(_snowman + " is already a registered built-in loot table");
      }
   }

   public static Set<vk> a() {
      return aA;
   }
}
