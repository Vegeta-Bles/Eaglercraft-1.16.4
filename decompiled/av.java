import com.google.gson.JsonElement;
import com.google.gson.JsonNull;
import com.google.gson.JsonObject;
import javax.annotation.Nullable;

public class av {
   public static final av a = av.a.a().b();
   private final bz.c b;
   private final bz.c c;
   private final bg d;
   private final Boolean e;
   private final aw f;

   public av() {
      this.b = bz.c.e;
      this.c = bz.c.e;
      this.d = bg.a;
      this.e = null;
      this.f = aw.a;
   }

   public av(bz.c var1, bz.c var2, bg var3, @Nullable Boolean var4, aw var5) {
      this.b = _snowman;
      this.c = _snowman;
      this.d = _snowman;
      this.e = _snowman;
      this.f = _snowman;
   }

   public boolean a(aah var1, apk var2, float var3, float var4, boolean var5) {
      if (this == a) {
         return true;
      } else if (!this.b.d(_snowman)) {
         return false;
      } else if (!this.c.d(_snowman)) {
         return false;
      } else if (!this.d.a(_snowman, _snowman.k())) {
         return false;
      } else {
         return this.e != null && this.e != _snowman ? false : this.f.a(_snowman, _snowman);
      }
   }

   public static av a(@Nullable JsonElement var0) {
      if (_snowman != null && !_snowman.isJsonNull()) {
         JsonObject _snowman = afd.m(_snowman, "damage");
         bz.c _snowmanx = bz.c.a(_snowman.get("dealt"));
         bz.c _snowmanxx = bz.c.a(_snowman.get("taken"));
         Boolean _snowmanxxx = _snowman.has("blocked") ? afd.j(_snowman, "blocked") : null;
         bg _snowmanxxxx = bg.a(_snowman.get("source_entity"));
         aw _snowmanxxxxx = aw.a(_snowman.get("type"));
         return new av(_snowmanx, _snowmanxx, _snowmanxxxx, _snowmanxxx, _snowmanxxxxx);
      } else {
         return a;
      }
   }

   public JsonElement a() {
      if (this == a) {
         return JsonNull.INSTANCE;
      } else {
         JsonObject _snowman = new JsonObject();
         _snowman.add("dealt", this.b.d());
         _snowman.add("taken", this.c.d());
         _snowman.add("source_entity", this.d.a());
         _snowman.add("type", this.f.a());
         if (this.e != null) {
            _snowman.addProperty("blocked", this.e);
         }

         return _snowman;
      }
   }

   public static class a {
      private bz.c a = bz.c.e;
      private bz.c b = bz.c.e;
      private bg c = bg.a;
      private Boolean d;
      private aw e = aw.a;

      public a() {
      }

      public static av.a a() {
         return new av.a();
      }

      public av.a a(Boolean var1) {
         this.d = _snowman;
         return this;
      }

      public av.a a(aw.a var1) {
         this.e = _snowman.b();
         return this;
      }

      public av b() {
         return new av(this.a, this.b, this.c, this.d, this.e);
      }
   }
}
