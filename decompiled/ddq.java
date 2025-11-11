import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;
import com.google.common.collect.ImmutableMap.Builder;
import java.util.Map;
import java.util.Optional;

public class ddq {
   public static final Map<String, ddq> a = Maps.newHashMap();
   public static final ddq b = new ddq("dummy");
   public static final ddq c = new ddq("trigger");
   public static final ddq d = new ddq("deathCount");
   public static final ddq e = new ddq("playerKillCount");
   public static final ddq f = new ddq("totalKillCount");
   public static final ddq g = new ddq("health", true, ddq.a.b);
   public static final ddq h = new ddq("food", true, ddq.a.a);
   public static final ddq i = new ddq("air", true, ddq.a.a);
   public static final ddq j = new ddq("armor", true, ddq.a.a);
   public static final ddq k = new ddq("xp", true, ddq.a.a);
   public static final ddq l = new ddq("level", true, ddq.a.a);
   public static final ddq[] m = new ddq[]{
      new ddq("teamkill." + k.a.f()),
      new ddq("teamkill." + k.b.f()),
      new ddq("teamkill." + k.c.f()),
      new ddq("teamkill." + k.d.f()),
      new ddq("teamkill." + k.e.f()),
      new ddq("teamkill." + k.f.f()),
      new ddq("teamkill." + k.g.f()),
      new ddq("teamkill." + k.h.f()),
      new ddq("teamkill." + k.i.f()),
      new ddq("teamkill." + k.j.f()),
      new ddq("teamkill." + k.k.f()),
      new ddq("teamkill." + k.l.f()),
      new ddq("teamkill." + k.m.f()),
      new ddq("teamkill." + k.n.f()),
      new ddq("teamkill." + k.o.f()),
      new ddq("teamkill." + k.p.f())
   };
   public static final ddq[] n = new ddq[]{
      new ddq("killedByTeam." + k.a.f()),
      new ddq("killedByTeam." + k.b.f()),
      new ddq("killedByTeam." + k.c.f()),
      new ddq("killedByTeam." + k.d.f()),
      new ddq("killedByTeam." + k.e.f()),
      new ddq("killedByTeam." + k.f.f()),
      new ddq("killedByTeam." + k.g.f()),
      new ddq("killedByTeam." + k.h.f()),
      new ddq("killedByTeam." + k.i.f()),
      new ddq("killedByTeam." + k.j.f()),
      new ddq("killedByTeam." + k.k.f()),
      new ddq("killedByTeam." + k.l.f()),
      new ddq("killedByTeam." + k.m.f()),
      new ddq("killedByTeam." + k.n.f()),
      new ddq("killedByTeam." + k.o.f()),
      new ddq("killedByTeam." + k.p.f())
   };
   private final String o;
   private final boolean p;
   private final ddq.a q;

   public ddq(String var1) {
      this(_snowman, false, ddq.a.a);
   }

   protected ddq(String var1, boolean var2, ddq.a var3) {
      this.o = _snowman;
      this.p = _snowman;
      this.q = _snowman;
      a.put(_snowman, this);
   }

   public static Optional<ddq> a(String var0) {
      if (a.containsKey(_snowman)) {
         return Optional.of(a.get(_snowman));
      } else {
         int _snowman = _snowman.indexOf(58);
         return _snowman < 0 ? Optional.empty() : gm.ag.b(vk.a(_snowman.substring(0, _snowman), '.')).flatMap(var2 -> a((adz<?>)var2, vk.a(_snowman.substring(_snowman + 1), '.')));
      }
   }

   private static <T> Optional<ddq> a(adz<T> var0, vk var1) {
      return _snowman.a().b(_snowman).map(_snowman::b);
   }

   public String c() {
      return this.o;
   }

   public boolean d() {
      return this.p;
   }

   public ddq.a e() {
      return this.q;
   }

   public static enum a {
      a("integer"),
      b("hearts");

      private final String c;
      private static final Map<String, ddq.a> d;

      private a(String var3) {
         this.c = _snowman;
      }

      public String a() {
         return this.c;
      }

      public static ddq.a a(String var0) {
         return d.getOrDefault(_snowman, a);
      }

      static {
         Builder<String, ddq.a> _snowman = ImmutableMap.builder();

         for (ddq.a _snowmanx : values()) {
            _snowman.put(_snowmanx.c, _snowmanx);
         }

         d = _snowman.build();
      }
   }
}
