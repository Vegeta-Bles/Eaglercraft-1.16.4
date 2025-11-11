import java.net.URI;
import java.net.URISyntaxException;
import java.util.UUID;
import javax.annotation.Nullable;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class dgb {
   public static dgb.b a = dgb.b.a;
   private static boolean b;
   private static final Logger c = LogManager.getLogger();
   private final String d;
   private final String e;
   private final djz f;
   private static final dgi g = new dgi();

   public static dgb a() {
      djz _snowman = djz.C();
      String _snowmanx = _snowman.J().c();
      String _snowmanxx = _snowman.J().a();
      if (!b) {
         b = true;
         String _snowmanxxx = System.getenv("realms.environment");
         if (_snowmanxxx == null) {
            _snowmanxxx = System.getProperty("realms.environment");
         }

         if (_snowmanxxx != null) {
            if ("LOCAL".equals(_snowmanxxx)) {
               d();
            } else if ("STAGE".equals(_snowmanxxx)) {
               b();
            }
         }
      }

      return new dgb(_snowmanxx, _snowmanx, _snowman);
   }

   public static void b() {
      a = dgb.b.b;
   }

   public static void c() {
      a = dgb.b.a;
   }

   public static void d() {
      a = dgb.b.c;
   }

   public dgb(String var1, String var2, djz var3) {
      this.d = _snowman;
      this.e = _snowman;
      this.f = _snowman;
      dgc.a(_snowman.L());
   }

   public dgs e() throws dhi {
      String _snowman = this.c("worlds");
      String _snowmanx = this.a(dge.a(_snowman));
      return dgs.a(_snowmanx);
   }

   public dgq a(long var1) throws dhi {
      String _snowman = this.c("worlds" + "/$ID".replace("$ID", String.valueOf(_snowman)));
      String _snowmanx = this.a(dge.a(_snowman));
      return dgq.c(_snowmanx);
   }

   public dgv f() throws dhi {
      String _snowman = this.c("activities/liveplayerlist");
      String _snowmanx = this.a(dge.a(_snowman));
      return dgv.a(_snowmanx);
   }

   public dgr b(long var1) throws dhi {
      String _snowman = this.c("worlds" + "/v1/$ID/join/pc".replace("$ID", "" + _snowman));
      String _snowmanx = this.a(dge.a(_snowman, 5000, 30000));
      return dgr.a(_snowmanx);
   }

   public void a(long var1, String var3, String var4) throws dhi {
      dgo _snowman = new dgo(_snowman, _snowman);
      String _snowmanx = this.c("worlds" + "/$WORLD_ID/initialize".replace("$WORLD_ID", String.valueOf(_snowman)));
      String _snowmanxx = g.a(_snowman);
      this.a(dge.a(_snowmanx, _snowmanxx, 5000, 10000));
   }

   public Boolean g() throws dhi {
      String _snowman = this.c("mco/available");
      String _snowmanx = this.a(dge.a(_snowman));
      return Boolean.valueOf(_snowmanx);
   }

   public Boolean h() throws dhi {
      String _snowman = this.c("mco/stageAvailable");
      String _snowmanx = this.a(dge.a(_snowman));
      return Boolean.valueOf(_snowmanx);
   }

   public dgb.a i() throws dhi {
      String _snowman = this.c("mco/client/compatible");
      String _snowmanx = this.a(dge.a(_snowman));

      try {
         return dgb.a.valueOf(_snowmanx);
      } catch (IllegalArgumentException var5) {
         throw new dhi(500, "Could not check compatible version, got response: " + _snowmanx, -1, "");
      }
   }

   public void a(long var1, String var3) throws dhi {
      String _snowman = this.c("invites" + "/$WORLD_ID/invite/$UUID".replace("$WORLD_ID", String.valueOf(_snowman)).replace("$UUID", _snowman));
      this.a(dge.b(_snowman));
   }

   public void c(long var1) throws dhi {
      String _snowman = this.c("invites" + "/$WORLD_ID".replace("$WORLD_ID", String.valueOf(_snowman)));
      this.a(dge.b(_snowman));
   }

   public dgq b(long var1, String var3) throws dhi {
      dgn _snowman = new dgn();
      _snowman.a(_snowman);
      String _snowmanx = this.c("invites" + "/$WORLD_ID".replace("$WORLD_ID", String.valueOf(_snowman)));
      String _snowmanxx = this.a(dge.b(_snowmanx, g.a(_snowman)));
      return dgq.c(_snowmanxx);
   }

   public dgh d(long var1) throws dhi {
      String _snowman = this.c("worlds" + "/$WORLD_ID/backups".replace("$WORLD_ID", String.valueOf(_snowman)));
      String _snowmanx = this.a(dge.a(_snowman));
      return dgh.a(_snowmanx);
   }

   public void b(long var1, String var3, String var4) throws dhi {
      dgo _snowman = new dgo(_snowman, _snowman);
      String _snowmanx = this.c("worlds" + "/$WORLD_ID".replace("$WORLD_ID", String.valueOf(_snowman)));
      this.a(dge.b(_snowmanx, g.a(_snowman)));
   }

   public void a(long var1, int var3, dgw var4) throws dhi {
      String _snowman = this.c("worlds" + "/$WORLD_ID/slot/$SLOT_ID".replace("$WORLD_ID", String.valueOf(_snowman)).replace("$SLOT_ID", String.valueOf(_snowman)));
      String _snowmanx = _snowman.c();
      this.a(dge.b(_snowman, _snowmanx));
   }

   public boolean a(long var1, int var3) throws dhi {
      String _snowman = this.c("worlds" + "/$WORLD_ID/slot/$SLOT_ID".replace("$WORLD_ID", String.valueOf(_snowman)).replace("$SLOT_ID", String.valueOf(_snowman)));
      String _snowmanx = this.a(dge.c(_snowman, ""));
      return Boolean.valueOf(_snowmanx);
   }

   public void c(long var1, String var3) throws dhi {
      String _snowman = this.a("worlds" + "/$WORLD_ID/backups".replace("$WORLD_ID", String.valueOf(_snowman)), "backupId=" + _snowman);
      this.a(dge.b(_snowman, "", 40000, 600000));
   }

   public dhf a(int var1, int var2, dgq.c var3) throws dhi {
      String _snowman = this.a("worlds" + "/templates/$WORLD_TYPE".replace("$WORLD_TYPE", _snowman.toString()), String.format("page=%d&pageSize=%d", _snowman, _snowman));
      String _snowmanx = this.a(dge.a(_snowman));
      return dhf.a(_snowmanx);
   }

   public Boolean d(long var1, String var3) throws dhi {
      String _snowman = "/minigames/$MINIGAME_ID/$WORLD_ID".replace("$MINIGAME_ID", _snowman).replace("$WORLD_ID", String.valueOf(_snowman));
      String _snowmanx = this.c("worlds" + _snowman);
      return Boolean.valueOf(this.a(dge.c(_snowmanx, "")));
   }

   public dgj e(long var1, String var3) throws dhi {
      String _snowman = "/$WORLD_ID/$PROFILE_UUID".replace("$WORLD_ID", String.valueOf(_snowman)).replace("$PROFILE_UUID", _snowman);
      String _snowmanx = this.c("ops" + _snowman);
      return dgj.a(this.a(dge.b(_snowmanx, "")));
   }

   public dgj f(long var1, String var3) throws dhi {
      String _snowman = "/$WORLD_ID/$PROFILE_UUID".replace("$WORLD_ID", String.valueOf(_snowman)).replace("$PROFILE_UUID", _snowman);
      String _snowmanx = this.c("ops" + _snowman);
      return dgj.a(this.a(dge.b(_snowmanx)));
   }

   public Boolean e(long var1) throws dhi {
      String _snowman = this.c("worlds" + "/$WORLD_ID/open".replace("$WORLD_ID", String.valueOf(_snowman)));
      String _snowmanx = this.a(dge.c(_snowman, ""));
      return Boolean.valueOf(_snowmanx);
   }

   public Boolean f(long var1) throws dhi {
      String _snowman = this.c("worlds" + "/$WORLD_ID/close".replace("$WORLD_ID", String.valueOf(_snowman)));
      String _snowmanx = this.a(dge.c(_snowman, ""));
      return Boolean.valueOf(_snowmanx);
   }

   public Boolean a(long var1, String var3, Integer var4, boolean var5) throws dhi {
      dgx _snowman = new dgx(_snowman, -1L, _snowman, _snowman);
      String _snowmanx = this.c("worlds" + "/$WORLD_ID/reset".replace("$WORLD_ID", String.valueOf(_snowman)));
      String _snowmanxx = this.a(dge.a(_snowmanx, g.a(_snowman), 30000, 80000));
      return Boolean.valueOf(_snowmanxx);
   }

   public Boolean g(long var1, String var3) throws dhi {
      dgx _snowman = new dgx(null, Long.valueOf(_snowman), -1, false);
      String _snowmanx = this.c("worlds" + "/$WORLD_ID/reset".replace("$WORLD_ID", String.valueOf(_snowman)));
      String _snowmanxx = this.a(dge.a(_snowmanx, g.a(_snowman), 30000, 80000));
      return Boolean.valueOf(_snowmanxx);
   }

   public dha g(long var1) throws dhi {
      String _snowman = this.c("subscriptions" + "/$WORLD_ID".replace("$WORLD_ID", String.valueOf(_snowman)));
      String _snowmanx = this.a(dge.a(_snowman));
      return dha.a(_snowmanx);
   }

   public int j() throws dhi {
      return this.k().a.size();
   }

   public dgl k() throws dhi {
      String _snowman = this.c("invites/pending");
      String _snowmanx = this.a(dge.a(_snowman));
      dgl _snowmanxx = dgl.a(_snowmanx);
      _snowmanxx.a.removeIf(this::a);
      return _snowmanxx;
   }

   private boolean a(dgk var1) {
      try {
         UUID _snowman = UUID.fromString(_snowman.d);
         return this.f.aB().e(_snowman);
      } catch (IllegalArgumentException var3) {
         return false;
      }
   }

   public void a(String var1) throws dhi {
      String _snowman = this.c("invites" + "/accept/$INVITATION_ID".replace("$INVITATION_ID", _snowman));
      this.a(dge.c(_snowman, ""));
   }

   public dhd b(long var1, int var3) throws dhi {
      String _snowman = this.c("worlds" + "/$WORLD_ID/slot/$SLOT_ID/download".replace("$WORLD_ID", String.valueOf(_snowman)).replace("$SLOT_ID", String.valueOf(_snowman)));
      String _snowmanx = this.a(dge.a(_snowman));
      return dhd.a(_snowmanx);
   }

   @Nullable
   public dhb h(long var1, @Nullable String var3) throws dhi {
      String _snowman = this.c("worlds" + "/$WORLD_ID/backups/upload".replace("$WORLD_ID", String.valueOf(_snowman)));
      return dhb.a(this.a(dge.c(_snowman, dhb.b(_snowman))));
   }

   public void b(String var1) throws dhi {
      String _snowman = this.c("invites" + "/reject/$INVITATION_ID".replace("$INVITATION_ID", _snowman));
      this.a(dge.c(_snowman, ""));
   }

   public void l() throws dhi {
      String _snowman = this.c("mco/tos/agreed");
      this.a(dge.b(_snowman, ""));
   }

   public dgp m() throws dhi {
      String _snowman = this.c("mco/v1/news");
      String _snowmanx = this.a(dge.a(_snowman, 5000, 10000));
      return dgp.a(_snowmanx);
   }

   public void a(dgm var1) throws dhi {
      String _snowman = this.c("regions/ping/stat");
      this.a(dge.b(_snowman, g.a(_snowman)));
   }

   public Boolean n() throws dhi {
      String _snowman = this.c("trial");
      String _snowmanx = this.a(dge.a(_snowman));
      return Boolean.valueOf(_snowmanx);
   }

   public void h(long var1) throws dhi {
      String _snowman = this.c("worlds" + "/$WORLD_ID".replace("$WORLD_ID", String.valueOf(_snowman)));
      this.a(dge.b(_snowman));
   }

   @Nullable
   private String c(String var1) {
      return this.a(_snowman, null);
   }

   @Nullable
   private String a(String var1, @Nullable String var2) {
      try {
         return new URI(a.e, a.d, "/" + _snowman, _snowman, null).toASCIIString();
      } catch (URISyntaxException var4) {
         var4.printStackTrace();
         return null;
      }
   }

   private String a(dge<?> var1) throws dhi {
      _snowman.a("sid", this.d);
      _snowman.a("user", this.e);
      _snowman.a("version", w.a().getName());

      try {
         int _snowman = _snowman.b();
         if (_snowman != 503 && _snowman != 277) {
            String _snowmanx = _snowman.c();
            if (_snowman >= 200 && _snowman < 300) {
               return _snowmanx;
            } else if (_snowman == 401) {
               String _snowmanxx = _snowman.c("WWW-Authenticate");
               c.info("Could not authorize you against Realms server: " + _snowmanxx);
               throw new dhi(_snowman, _snowmanxx, -1, _snowmanxx);
            } else if (_snowmanx != null && _snowmanx.length() != 0) {
               dgd _snowmanxx = dgd.a(_snowmanx);
               c.error("Realms http code: " + _snowman + " -  error code: " + _snowmanxx.b() + " -  message: " + _snowmanxx.a() + " - raw body: " + _snowmanx);
               throw new dhi(_snowman, _snowmanx, _snowmanxx);
            } else {
               c.error("Realms error code: " + _snowman + " message: " + _snowmanx);
               throw new dhi(_snowman, _snowmanx, _snowman, "");
            }
         } else {
            int _snowmanx = _snowman.a();
            throw new dhj(_snowmanx, _snowman);
         }
      } catch (dhh var5) {
         throw new dhi(500, "Could not connect to Realms: " + var5.getMessage(), -1, "");
      }
   }

   public static enum a {
      a,
      b,
      c;

      private a() {
      }
   }

   public static enum b {
      a("pc.realms.minecraft.net", "https"),
      b("pc-stage.realms.minecraft.net", "https"),
      c("localhost:8080", "http");

      public String d;
      public String e;

      private b(String var3, String var4) {
         this.d = _snowman;
         this.e = _snowman;
      }
   }
}
