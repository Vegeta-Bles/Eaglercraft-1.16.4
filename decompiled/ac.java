import com.google.common.collect.Maps;
import java.util.Map;
import javax.annotation.Nullable;

public class ac {
   private static final Map<vk, af<?>> Q = Maps.newHashMap();
   public static final bm a = a(new bm());
   public static final bt b = a(new bt(new vk("player_killed_entity")));
   public static final bt c = a(new bt(new vk("entity_killed_player")));
   public static final bc d = a(new bc());
   public static final bn e = a(new bn());
   public static final ch f = a(new ch());
   public static final ce g = a(new ce());
   public static final bf h = a(new bf());
   public static final ba i = a(new ba());
   public static final bi j = a(new bi());
   public static final ap k = a(new ap());
   public static final as l = a(new as());
   public static final cs m = a(new cs());
   public static final cn n = a(new cn());
   public static final ao o = a(new ao());
   public static final bx p = a(new bx(new vk("location")));
   public static final bx q = a(new bx(new vk("slept_in_bed")));
   public static final au r = a(new au());
   public static final cr s = a(new cr());
   public static final bo t = a(new bo());
   public static final bu u = a(new bu());
   public static final aq v = a(new aq());
   public static final cq w = a(new cq());
   public static final co x = a(new co());
   public static final cd y = a(new cd());
   public static final at z = a(new at());
   public static final az A = a(new az());
   public static final ct B = a(new ct());
   public static final cc C = a(new cc());
   public static final bk D = a(new bk());
   public static final ar E = a(new ar());
   public static final cj F = a(new cj());
   public static final bs G = a(new bs());
   public static final bx H = a(new bx(new vk("hero_of_the_village")));
   public static final bx I = a(new bx(new vk("voluntary_exile")));
   public static final cl J = a(new cl());
   public static final am K = a(new am());
   public static final cp L = a(new cp());
   public static final br M = a(new br());
   public static final by N = a(new by());
   public static final bp O = a(new bp());
   public static final cf P = a(new cf());

   private static <T extends af<?>> T a(T var0) {
      if (Q.containsKey(_snowman.a())) {
         throw new IllegalArgumentException("Duplicate criterion id " + _snowman.a());
      } else {
         Q.put(_snowman.a(), _snowman);
         return _snowman;
      }
   }

   @Nullable
   public static <T extends ag> af<T> a(vk var0) {
      return (af<T>)Q.get(_snowman);
   }

   public static Iterable<? extends af<?>> a() {
      return Q.values();
   }
}
