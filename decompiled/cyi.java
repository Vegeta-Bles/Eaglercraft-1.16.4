import com.mojang.serialization.Dynamic;
import com.mojang.serialization.OptionalDynamic;

public class cyi {
   private final int a;
   private final long b;
   private final String c;
   private final int d;
   private final boolean e;

   public cyi(int var1, long var2, String var4, int var5, boolean var6) {
      this.a = _snowman;
      this.b = _snowman;
      this.c = _snowman;
      this.d = _snowman;
      this.e = _snowman;
   }

   public static cyi a(Dynamic<?> var0) {
      int _snowman = _snowman.get("version").asInt(0);
      long _snowmanx = _snowman.get("LastPlayed").asLong(0L);
      OptionalDynamic<?> _snowmanxx = _snowman.get("Version");
      return _snowmanxx.result().isPresent()
         ? new cyi(
            _snowman, _snowmanx, _snowmanxx.get("Name").asString(w.a().getName()), _snowmanxx.get("Id").asInt(w.a().getWorldVersion()), _snowmanxx.get("Snapshot").asBoolean(!w.a().isStable())
         )
         : new cyi(_snowman, _snowmanx, "", 0, false);
   }

   public int a() {
      return this.a;
   }

   public long b() {
      return this.b;
   }

   public String c() {
      return this.c;
   }

   public int d() {
      return this.d;
   }

   public boolean e() {
      return this.e;
   }
}
