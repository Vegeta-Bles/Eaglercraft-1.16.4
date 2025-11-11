import com.google.gson.JsonElement;
import com.google.gson.JsonNull;
import com.google.gson.JsonObject;
import javax.annotation.Nullable;

public class aw {
   public static final aw a = aw.a.a().b();
   private final Boolean b;
   private final Boolean c;
   private final Boolean d;
   private final Boolean e;
   private final Boolean f;
   private final Boolean g;
   private final Boolean h;
   private final Boolean i;
   private final bg j;
   private final bg k;

   public aw(
      @Nullable Boolean var1,
      @Nullable Boolean var2,
      @Nullable Boolean var3,
      @Nullable Boolean var4,
      @Nullable Boolean var5,
      @Nullable Boolean var6,
      @Nullable Boolean var7,
      @Nullable Boolean var8,
      bg var9,
      bg var10
   ) {
      this.b = _snowman;
      this.c = _snowman;
      this.d = _snowman;
      this.e = _snowman;
      this.f = _snowman;
      this.g = _snowman;
      this.h = _snowman;
      this.i = _snowman;
      this.j = _snowman;
      this.k = _snowman;
   }

   public boolean a(aah var1, apk var2) {
      return this.a(_snowman.u(), _snowman.cA(), _snowman);
   }

   public boolean a(aag var1, dcn var2, apk var3) {
      if (this == a) {
         return true;
      } else if (this.b != null && this.b != _snowman.b()) {
         return false;
      } else if (this.c != null && this.c != _snowman.d()) {
         return false;
      } else if (this.d != null && this.d != _snowman.f()) {
         return false;
      } else if (this.e != null && this.e != _snowman.h()) {
         return false;
      } else if (this.f != null && this.f != _snowman.i()) {
         return false;
      } else if (this.g != null && this.g != _snowman.p()) {
         return false;
      } else if (this.h != null && this.h != _snowman.t()) {
         return false;
      } else if (this.i != null && this.i != (_snowman == apk.b)) {
         return false;
      } else {
         return !this.j.a(_snowman, _snowman, _snowman.j()) ? false : this.k.a(_snowman, _snowman, _snowman.k());
      }
   }

   public static aw a(@Nullable JsonElement var0) {
      if (_snowman != null && !_snowman.isJsonNull()) {
         JsonObject _snowman = afd.m(_snowman, "damage type");
         Boolean _snowmanx = a(_snowman, "is_projectile");
         Boolean _snowmanxx = a(_snowman, "is_explosion");
         Boolean _snowmanxxx = a(_snowman, "bypasses_armor");
         Boolean _snowmanxxxx = a(_snowman, "bypasses_invulnerability");
         Boolean _snowmanxxxxx = a(_snowman, "bypasses_magic");
         Boolean _snowmanxxxxxx = a(_snowman, "is_fire");
         Boolean _snowmanxxxxxxx = a(_snowman, "is_magic");
         Boolean _snowmanxxxxxxxx = a(_snowman, "is_lightning");
         bg _snowmanxxxxxxxxx = bg.a(_snowman.get("direct_entity"));
         bg _snowmanxxxxxxxxxx = bg.a(_snowman.get("source_entity"));
         return new aw(_snowmanx, _snowmanxx, _snowmanxxx, _snowmanxxxx, _snowmanxxxxx, _snowmanxxxxxx, _snowmanxxxxxxx, _snowmanxxxxxxxx, _snowmanxxxxxxxxx, _snowmanxxxxxxxxxx);
      } else {
         return a;
      }
   }

   @Nullable
   private static Boolean a(JsonObject var0, String var1) {
      return _snowman.has(_snowman) ? afd.j(_snowman, _snowman) : null;
   }

   public JsonElement a() {
      if (this == a) {
         return JsonNull.INSTANCE;
      } else {
         JsonObject _snowman = new JsonObject();
         this.a(_snowman, "is_projectile", this.b);
         this.a(_snowman, "is_explosion", this.c);
         this.a(_snowman, "bypasses_armor", this.d);
         this.a(_snowman, "bypasses_invulnerability", this.e);
         this.a(_snowman, "bypasses_magic", this.f);
         this.a(_snowman, "is_fire", this.g);
         this.a(_snowman, "is_magic", this.h);
         this.a(_snowman, "is_lightning", this.i);
         _snowman.add("direct_entity", this.j.a());
         _snowman.add("source_entity", this.k.a());
         return _snowman;
      }
   }

   private void a(JsonObject var1, String var2, @Nullable Boolean var3) {
      if (_snowman != null) {
         _snowman.addProperty(_snowman, _snowman);
      }
   }

   public static class a {
      private Boolean a;
      private Boolean b;
      private Boolean c;
      private Boolean d;
      private Boolean e;
      private Boolean f;
      private Boolean g;
      private Boolean h;
      private bg i = bg.a;
      private bg j = bg.a;

      public a() {
      }

      public static aw.a a() {
         return new aw.a();
      }

      public aw.a a(Boolean var1) {
         this.a = _snowman;
         return this;
      }

      public aw.a h(Boolean var1) {
         this.h = _snowman;
         return this;
      }

      public aw.a a(bg.a var1) {
         this.i = _snowman.b();
         return this;
      }

      public aw b() {
         return new aw(this.a, this.b, this.c, this.d, this.e, this.f, this.g, this.h, this.i, this.j);
      }
   }
}
