import com.google.gson.JsonElement;
import com.google.gson.JsonNull;
import com.google.gson.JsonObject;
import javax.annotation.Nullable;

public class be {
   public static final be a = new be.a().b();
   @Nullable
   private final Boolean b;
   @Nullable
   private final Boolean c;
   @Nullable
   private final Boolean d;
   @Nullable
   private final Boolean e;
   @Nullable
   private final Boolean f;

   public be(@Nullable Boolean var1, @Nullable Boolean var2, @Nullable Boolean var3, @Nullable Boolean var4, @Nullable Boolean var5) {
      this.b = _snowman;
      this.c = _snowman;
      this.d = _snowman;
      this.e = _snowman;
      this.f = _snowman;
   }

   public boolean a(aqa var1) {
      if (this.b != null && _snowman.bq() != this.b) {
         return false;
      } else if (this.c != null && _snowman.bz() != this.c) {
         return false;
      } else if (this.d != null && _snowman.bA() != this.d) {
         return false;
      } else {
         return this.e != null && _snowman.bB() != this.e ? false : this.f == null || !(_snowman instanceof aqm) || ((aqm)_snowman).w_() == this.f;
      }
   }

   @Nullable
   private static Boolean a(JsonObject var0, String var1) {
      return _snowman.has(_snowman) ? afd.j(_snowman, _snowman) : null;
   }

   public static be a(@Nullable JsonElement var0) {
      if (_snowman != null && !_snowman.isJsonNull()) {
         JsonObject _snowman = afd.m(_snowman, "entity flags");
         Boolean _snowmanx = a(_snowman, "is_on_fire");
         Boolean _snowmanxx = a(_snowman, "is_sneaking");
         Boolean _snowmanxxx = a(_snowman, "is_sprinting");
         Boolean _snowmanxxxx = a(_snowman, "is_swimming");
         Boolean _snowmanxxxxx = a(_snowman, "is_baby");
         return new be(_snowmanx, _snowmanxx, _snowmanxxx, _snowmanxxxx, _snowmanxxxxx);
      } else {
         return a;
      }
   }

   private void a(JsonObject var1, String var2, @Nullable Boolean var3) {
      if (_snowman != null) {
         _snowman.addProperty(_snowman, _snowman);
      }
   }

   public JsonElement a() {
      if (this == a) {
         return JsonNull.INSTANCE;
      } else {
         JsonObject _snowman = new JsonObject();
         this.a(_snowman, "is_on_fire", this.b);
         this.a(_snowman, "is_sneaking", this.c);
         this.a(_snowman, "is_sprinting", this.d);
         this.a(_snowman, "is_swimming", this.e);
         this.a(_snowman, "is_baby", this.f);
         return _snowman;
      }
   }

   public static class a {
      @Nullable
      private Boolean a;
      @Nullable
      private Boolean b;
      @Nullable
      private Boolean c;
      @Nullable
      private Boolean d;
      @Nullable
      private Boolean e;

      public a() {
      }

      public static be.a a() {
         return new be.a();
      }

      public be.a a(@Nullable Boolean var1) {
         this.a = _snowman;
         return this;
      }

      public be.a e(@Nullable Boolean var1) {
         this.e = _snowman;
         return this;
      }

      public be b() {
         return new be(this.a, this.b, this.c, this.d, this.e);
      }
   }
}
