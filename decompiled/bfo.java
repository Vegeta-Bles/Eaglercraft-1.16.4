import com.google.common.collect.Maps;
import java.util.Map;
import java.util.Optional;

public final class bfo {
   public static final bfo a = a("desert");
   public static final bfo b = a("jungle");
   public static final bfo c = a("plains");
   public static final bfo d = a("savanna");
   public static final bfo e = a("snow");
   public static final bfo f = a("swamp");
   public static final bfo g = a("taiga");
   private final String h;
   private static final Map<vj<bsv>, bfo> i = x.a(Maps.newHashMap(), var0 -> {
      var0.put(btb.L, a);
      var0.put(btb.N, a);
      var0.put(btb.c, a);
      var0.put(btb.r, a);
      var0.put(btb.ab, a);
      var0.put(btb.as, a);
      var0.put(btb.au, a);
      var0.put(btb.at, a);
      var0.put(btb.M, a);
      var0.put(btb.av, b);
      var0.put(btb.aw, b);
      var0.put(btb.v, b);
      var0.put(btb.x, b);
      var0.put(btb.w, b);
      var0.put(btb.ah, b);
      var0.put(btb.ai, b);
      var0.put(btb.K, d);
      var0.put(btb.J, d);
      var0.put(btb.aq, d);
      var0.put(btb.ar, d);
      var0.put(btb.Y, e);
      var0.put(btb.k, e);
      var0.put(btb.l, e);
      var0.put(btb.ag, e);
      var0.put(btb.A, e);
      var0.put(btb.n, e);
      var0.put(btb.E, e);
      var0.put(btb.F, e);
      var0.put(btb.am, e);
      var0.put(btb.m, e);
      var0.put(btb.g, f);
      var0.put(btb.af, f);
      var0.put(btb.an, g);
      var0.put(btb.ao, g);
      var0.put(btb.G, g);
      var0.put(btb.H, g);
      var0.put(btb.ac, g);
      var0.put(btb.ap, g);
      var0.put(btb.u, g);
      var0.put(btb.d, g);
      var0.put(btb.f, g);
      var0.put(btb.t, g);
      var0.put(btb.ae, g);
      var0.put(btb.I, g);
   });

   private bfo(String var1) {
      this.h = _snowman;
   }

   @Override
   public String toString() {
      return this.h;
   }

   private static bfo a(String var0) {
      return gm.a(gm.ah, new vk(_snowman), new bfo(_snowman));
   }

   public static bfo a(Optional<vj<bsv>> var0) {
      return _snowman.<bfo>flatMap(var0x -> Optional.ofNullable(i.get(var0x))).orElse(c);
   }
}
