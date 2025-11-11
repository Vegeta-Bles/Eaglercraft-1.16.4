import java.util.Arrays;
import java.util.Collection;
import java.util.Map;
import java.util.stream.Collectors;
import javax.annotation.Nullable;

public abstract class ddp {
   public ddp() {
   }

   public boolean a(@Nullable ddp var1) {
      return _snowman == null ? false : this == _snowman;
   }

   public abstract String b();

   public abstract nx d(nr var1);

   public abstract boolean i();

   public abstract boolean h();

   public abstract ddp.b j();

   public abstract k n();

   public abstract Collection<String> g();

   public abstract ddp.b k();

   public abstract ddp.a l();

   public static enum a {
      a("always", 0),
      b("never", 1),
      c("pushOtherTeams", 2),
      d("pushOwnTeam", 3);

      private static final Map<String, ddp.a> g = Arrays.stream(values()).collect(Collectors.toMap(var0 -> var0.e, var0 -> (ddp.a)var0));
      public final String e;
      public final int f;

      @Nullable
      public static ddp.a a(String var0) {
         return g.get(_snowman);
      }

      private a(String var3, int var4) {
         this.e = _snowman;
         this.f = _snowman;
      }

      public nr b() {
         return new of("team.collision." + this.e);
      }
   }

   public static enum b {
      a("always", 0),
      b("never", 1),
      c("hideForOtherTeams", 2),
      d("hideForOwnTeam", 3);

      private static final Map<String, ddp.b> g = Arrays.stream(values()).collect(Collectors.toMap(var0 -> var0.e, var0 -> (ddp.b)var0));
      public final String e;
      public final int f;

      @Nullable
      public static ddp.b a(String var0) {
         return g.get(_snowman);
      }

      private b(String var3, int var4) {
         this.e = _snowman;
         this.f = _snowman;
      }

      public nr b() {
         return new of("team.visibility." + this.e);
      }
   }
}
