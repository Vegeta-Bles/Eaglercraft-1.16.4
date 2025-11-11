import com.mojang.authlib.GameProfile;
import com.mojang.util.UUIDTypeAdapter;
import java.util.Arrays;
import java.util.Locale;
import java.util.Map;
import java.util.UUID;
import java.util.function.Function;
import java.util.stream.Collectors;
import javax.annotation.Nullable;

public class dkm {
   private final String a;
   private final String b;
   private final String c;
   private final dkm.a d;

   public dkm(String var1, String var2, String var3, String var4) {
      this.a = _snowman;
      this.b = _snowman;
      this.c = _snowman;
      this.d = dkm.a.a(_snowman);
   }

   public String a() {
      return "token:" + this.c + ":" + this.b;
   }

   public String b() {
      return this.b;
   }

   public String c() {
      return this.a;
   }

   public String d() {
      return this.c;
   }

   public GameProfile e() {
      try {
         UUID _snowman = UUIDTypeAdapter.fromString(this.b());
         return new GameProfile(_snowman, this.c());
      } catch (IllegalArgumentException var2) {
         return new GameProfile(null, this.c());
      }
   }

   public static enum a {
      a("legacy"),
      b("mojang");

      private static final Map<String, dkm.a> c = Arrays.stream(values()).collect(Collectors.toMap(var0 -> var0.d, Function.identity()));
      private final String d;

      private a(String var3) {
         this.d = _snowman;
      }

      @Nullable
      public static dkm.a a(String var0) {
         return c.get(_snowman.toLowerCase(Locale.ROOT));
      }
   }
}
