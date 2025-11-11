import java.nio.file.Path;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

public class zh extends zk<zh> {
   public final boolean a = this.a("online-mode", true);
   public final boolean b = this.a("prevent-proxy-connections", false);
   public final String c = this.a("server-ip", "");
   public final boolean d = this.a("spawn-animals", true);
   public final boolean e = this.a("spawn-npcs", true);
   public final boolean f = this.a("pvp", true);
   public final boolean g = this.a("allow-flight", false);
   public final String h = this.a("resource-pack", "");
   public final String i = this.a("motd", "A Minecraft Server");
   public final boolean j = this.a("force-gamemode", false);
   public final boolean k = this.a("enforce-whitelist", false);
   public final aor l = this.a("difficulty", a(aor::a, aor::a), aor::c, aor.b);
   public final bru m = this.a("gamemode", a(bru::a, bru::a), bru::b, bru.b);
   public final String n = this.a("level-name", "world");
   public final int o = this.a("server-port", 25565);
   public final int p = this.a("max-build-height", var0 -> afm.a((var0 + 8) / 16 * 16, 64, 256), 256);
   public final Boolean q = this.b("announce-player-achievements");
   public final boolean r = this.a("enable-query", false);
   public final int s = this.a("query.port", 25565);
   public final boolean t = this.a("enable-rcon", false);
   public final int u = this.a("rcon.port", 25575);
   public final String v = this.a("rcon.password", "");
   public final String w = this.a("resource-pack-hash");
   public final String x = this.a("resource-pack-sha1", "");
   public final boolean y = this.a("hardcore", false);
   public final boolean z = this.a("allow-nether", true);
   public final boolean A = this.a("spawn-monsters", true);
   public final boolean B;
   public final boolean C;
   public final boolean D;
   public final int E;
   public final int F;
   public final int G;
   public final long H;
   public final int I;
   public final int J;
   public final int K;
   public final int L;
   public final boolean M;
   public final boolean N;
   public final int O;
   public final boolean P;
   public final boolean Q;
   public final boolean R;
   public final int S;
   public final String T;
   public final zk<zh>.a<Integer> U;
   public final zk<zh>.a<Boolean> V;
   public final chw W;

   public zh(Properties var1, gn var2) {
      super(_snowman);
      if (this.a("snooper-enabled", true)) {
      }

      this.B = false;
      this.C = this.a("use-native-transport", true);
      this.D = this.a("enable-command-block", false);
      this.E = this.a("spawn-protection", 16);
      this.F = this.a("op-permission-level", 4);
      this.G = this.a("function-permission-level", 2);
      this.H = this.a("max-tick-time", TimeUnit.MINUTES.toMillis(1L));
      this.I = this.a("rate-limit", 0);
      this.J = this.a("view-distance", 10);
      this.K = this.a("max-players", 20);
      this.L = this.a("network-compression-threshold", 256);
      this.M = this.a("broadcast-rcon-to-ops", true);
      this.N = this.a("broadcast-console-to-ops", true);
      this.O = this.a("max-world-size", var0 -> afm.a(var0, 1, 29999984), 29999984);
      this.P = this.a("sync-chunk-writes", true);
      this.Q = this.a("enable-jmx-monitoring", false);
      this.R = this.a("enable-status", true);
      this.S = this.a("entity-broadcast-range-percentage", var0 -> afm.a(var0, 10, 1000), 100);
      this.T = this.a("text-filtering-config", "");
      this.U = this.b("player-idle-timeout", 0);
      this.V = this.b("white-list", false);
      this.W = chw.a(_snowman, _snowman);
   }

   public static zh a(gn var0, Path var1) {
      return new zh(a(_snowman), _snowman);
   }

   protected zh a(gn var1, Properties var2) {
      return new zh(_snowman, _snowman);
   }
}
