import com.google.gson.JsonElement;
import com.google.gson.JsonNull;
import com.google.gson.JsonObject;
import javax.annotation.Nullable;

public class bd {
   public static final bd a = new bd(bq.a, bq.a, bq.a, bq.a, bq.a, bq.a);
   public static final bd b = new bd(bq.a.a().a(bmd.pM).a(bhb.s().o()).b(), bq.a, bq.a, bq.a, bq.a, bq.a);
   private final bq c;
   private final bq d;
   private final bq e;
   private final bq f;
   private final bq g;
   private final bq h;

   public bd(bq var1, bq var2, bq var3, bq var4, bq var5, bq var6) {
      this.c = _snowman;
      this.d = _snowman;
      this.e = _snowman;
      this.f = _snowman;
      this.g = _snowman;
      this.h = _snowman;
   }

   public boolean a(@Nullable aqa var1) {
      if (this == a) {
         return true;
      } else if (!(_snowman instanceof aqm)) {
         return false;
      } else {
         aqm _snowman = (aqm)_snowman;
         if (!this.c.a(_snowman.b(aqf.f))) {
            return false;
         } else if (!this.d.a(_snowman.b(aqf.e))) {
            return false;
         } else if (!this.e.a(_snowman.b(aqf.d))) {
            return false;
         } else if (!this.f.a(_snowman.b(aqf.c))) {
            return false;
         } else {
            return !this.g.a(_snowman.b(aqf.a)) ? false : this.h.a(_snowman.b(aqf.b));
         }
      }
   }

   public static bd a(@Nullable JsonElement var0) {
      if (_snowman != null && !_snowman.isJsonNull()) {
         JsonObject _snowman = afd.m(_snowman, "equipment");
         bq _snowmanx = bq.a(_snowman.get("head"));
         bq _snowmanxx = bq.a(_snowman.get("chest"));
         bq _snowmanxxx = bq.a(_snowman.get("legs"));
         bq _snowmanxxxx = bq.a(_snowman.get("feet"));
         bq _snowmanxxxxx = bq.a(_snowman.get("mainhand"));
         bq _snowmanxxxxxx = bq.a(_snowman.get("offhand"));
         return new bd(_snowmanx, _snowmanxx, _snowmanxxx, _snowmanxxxx, _snowmanxxxxx, _snowmanxxxxxx);
      } else {
         return a;
      }
   }

   public JsonElement a() {
      if (this == a) {
         return JsonNull.INSTANCE;
      } else {
         JsonObject _snowman = new JsonObject();
         _snowman.add("head", this.c.a());
         _snowman.add("chest", this.d.a());
         _snowman.add("legs", this.e.a());
         _snowman.add("feet", this.f.a());
         _snowman.add("mainhand", this.g.a());
         _snowman.add("offhand", this.h.a());
         return _snowman;
      }
   }

   public static class a {
      private bq a;
      private bq b;
      private bq c;
      private bq d;
      private bq e;
      private bq f;

      public a() {
         this.a = bq.a;
         this.b = bq.a;
         this.c = bq.a;
         this.d = bq.a;
         this.e = bq.a;
         this.f = bq.a;
      }

      public static bd.a a() {
         return new bd.a();
      }

      public bd.a a(bq var1) {
         this.a = _snowman;
         return this;
      }

      public bd.a b(bq var1) {
         this.b = _snowman;
         return this;
      }

      public bd.a c(bq var1) {
         this.c = _snowman;
         return this;
      }

      public bd.a d(bq var1) {
         this.d = _snowman;
         return this;
      }

      public bd b() {
         return new bd(this.a, this.b, this.c, this.d, this.e, this.f);
      }
   }
}
