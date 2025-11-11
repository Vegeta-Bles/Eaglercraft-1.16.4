import com.google.gson.JsonObject;
import java.util.Objects;

public class dgw extends dhc {
   public Boolean a;
   public Boolean b;
   public Boolean c;
   public Boolean d;
   public Integer e;
   public Boolean f;
   public Boolean g;
   public Integer h;
   public Integer i;
   public String j;
   public long k;
   public String l;
   public boolean m;
   public boolean n;
   private static final String o = null;

   public dgw(Boolean var1, Boolean var2, Boolean var3, Boolean var4, Integer var5, Boolean var6, Integer var7, Integer var8, Boolean var9, String var10) {
      this.a = _snowman;
      this.b = _snowman;
      this.c = _snowman;
      this.d = _snowman;
      this.e = _snowman;
      this.f = _snowman;
      this.h = _snowman;
      this.i = _snowman;
      this.g = _snowman;
      this.j = _snowman;
   }

   public static dgw a() {
      return new dgw(true, true, true, true, 0, false, 2, 0, false, "");
   }

   public static dgw b() {
      dgw _snowman = a();
      _snowman.a(true);
      return _snowman;
   }

   public void a(boolean var1) {
      this.n = _snowman;
   }

   public static dgw a(JsonObject var0) {
      dgw _snowman = new dgw(
         dip.a("pvp", _snowman, true),
         dip.a("spawnAnimals", _snowman, true),
         dip.a("spawnMonsters", _snowman, true),
         dip.a("spawnNPCs", _snowman, true),
         dip.a("spawnProtection", _snowman, 0),
         dip.a("commandBlocks", _snowman, false),
         dip.a("difficulty", _snowman, 2),
         dip.a("gameMode", _snowman, 0),
         dip.a("forceGameMode", _snowman, false),
         dip.a("slotName", _snowman, "")
      );
      _snowman.k = dip.a("worldTemplateId", _snowman, -1L);
      _snowman.l = dip.a("worldTemplateImage", _snowman, o);
      _snowman.m = dip.a("adventureMap", _snowman, false);
      return _snowman;
   }

   public String a(int var1) {
      if (this.j != null && !this.j.isEmpty()) {
         return this.j;
      } else {
         return this.n ? ekx.a("mco.configure.world.slot.empty") : this.b(_snowman);
      }
   }

   public String b(int var1) {
      return ekx.a("mco.configure.world.slot", _snowman);
   }

   public String c() {
      JsonObject _snowman = new JsonObject();
      if (!this.a) {
         _snowman.addProperty("pvp", this.a);
      }

      if (!this.b) {
         _snowman.addProperty("spawnAnimals", this.b);
      }

      if (!this.c) {
         _snowman.addProperty("spawnMonsters", this.c);
      }

      if (!this.d) {
         _snowman.addProperty("spawnNPCs", this.d);
      }

      if (this.e != 0) {
         _snowman.addProperty("spawnProtection", this.e);
      }

      if (this.f) {
         _snowman.addProperty("commandBlocks", this.f);
      }

      if (this.h != 2) {
         _snowman.addProperty("difficulty", this.h);
      }

      if (this.i != 0) {
         _snowman.addProperty("gameMode", this.i);
      }

      if (this.g) {
         _snowman.addProperty("forceGameMode", this.g);
      }

      if (!Objects.equals(this.j, "")) {
         _snowman.addProperty("slotName", this.j);
      }

      return _snowman.toString();
   }

   public dgw d() {
      return new dgw(this.a, this.b, this.c, this.d, this.e, this.f, this.h, this.i, this.g, this.j);
   }
}
