import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import java.util.List;
import java.util.Map;

public enum dkg {
   a(new bmb(bmd.mh)),
   b(new bmb(bup.bG)),
   c(new bmb(bmd.lP)),
   d(new bmb(bmd.kD), new bmb(bmd.kv)),
   e(new bmb(bmd.lM), new bmb(bmd.kb)),
   f(new bmb(bmd.mh)),
   g(new bmb(bmd.lx)),
   h(new bmb(bup.b)),
   i(new bmb(bmd.lM), new bmb(bmd.oV)),
   j(new bmb(bmd.mh)),
   k(new bmb(bup.cy)),
   l(new bmb(bmd.kB), new bmb(bmd.lq)),
   m(new bmb(bmd.mh)),
   n(new bmb(bmd.lx)),
   o(new bmb(bmd.dJ)),
   p(new bmb(bmd.lt)),
   q(new bmb(bmd.lx)),
   r(new bmb(bmd.fJ));

   public static final List<dkg> s = ImmutableList.of(m, n);
   public static final List<dkg> t = ImmutableList.of(j, k, l);
   public static final List<dkg> u = ImmutableList.of(f, g, h, i);
   public static final List<dkg> v = ImmutableList.of(a, d, b, e, c);
   public static final Map<dkg, List<dkg>> w = ImmutableMap.of(
      a, ImmutableList.of(d, b, e, c), f, ImmutableList.of(g, h, i), j, ImmutableList.of(k, l), m, ImmutableList.of(n)
   );
   private final List<bmb> x;

   private dkg(bmb... var3) {
      this.x = ImmutableList.copyOf(_snowman);
   }

   public static List<dkg> a(bjk var0) {
      switch (_snowman) {
         case a:
            return v;
         case b:
            return u;
         case c:
            return t;
         case d:
            return s;
         default:
            return ImmutableList.of();
      }
   }

   public List<bmb> a() {
      return this.x;
   }
}
