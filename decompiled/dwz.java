import java.util.Collections;
import java.util.List;
import javax.annotation.Nullable;

public class dwz {
   public String a;
   public String b;
   public nr c;
   public nr d;
   public long e;
   public int f = w.a().getProtocolVersion();
   public nr g = new oe(w.a().getName());
   public boolean h;
   public List<nr> i = Collections.emptyList();
   private dwz.a j = dwz.a.c;
   @Nullable
   private String k;
   private boolean l;

   public dwz(String var1, String var2, boolean var3) {
      this.a = _snowman;
      this.b = _snowman;
      this.l = _snowman;
   }

   public md a() {
      md _snowman = new md();
      _snowman.a("name", this.a);
      _snowman.a("ip", this.b);
      if (this.k != null) {
         _snowman.a("icon", this.k);
      }

      if (this.j == dwz.a.a) {
         _snowman.a("acceptTextures", true);
      } else if (this.j == dwz.a.b) {
         _snowman.a("acceptTextures", false);
      }

      return _snowman;
   }

   public dwz.a b() {
      return this.j;
   }

   public void a(dwz.a var1) {
      this.j = _snowman;
   }

   public static dwz a(md var0) {
      dwz _snowman = new dwz(_snowman.l("name"), _snowman.l("ip"), false);
      if (_snowman.c("icon", 8)) {
         _snowman.a(_snowman.l("icon"));
      }

      if (_snowman.c("acceptTextures", 1)) {
         if (_snowman.q("acceptTextures")) {
            _snowman.a(dwz.a.a);
         } else {
            _snowman.a(dwz.a.b);
         }
      } else {
         _snowman.a(dwz.a.c);
      }

      return _snowman;
   }

   @Nullable
   public String c() {
      return this.k;
   }

   public void a(@Nullable String var1) {
      this.k = _snowman;
   }

   public boolean d() {
      return this.l;
   }

   public void a(dwz var1) {
      this.b = _snowman.b;
      this.a = _snowman.a;
      this.a(_snowman.b());
      this.k = _snowman.k;
      this.l = _snowman.l;
   }

   public static enum a {
      a("enabled"),
      b("disabled"),
      c("prompt");

      private final nr d;

      private a(String var3) {
         this.d = new of("addServer.resourcePack." + _snowman);
      }

      public nr a() {
         return this.d;
      }
   }
}
