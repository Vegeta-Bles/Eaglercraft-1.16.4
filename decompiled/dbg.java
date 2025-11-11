import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class dbg implements dbo {
   private static final Logger a = LogManager.getLogger();
   private final vk b;

   private dbg(vk var1) {
      this.b = _snowman;
   }

   @Override
   public dbp b() {
      return dbq.o;
   }

   @Override
   public void a(czg var1) {
      if (_snowman.b(this.b)) {
         _snowman.a("Condition " + this.b + " is recursively called");
      } else {
         dbo.super.a(_snowman);
         dbo _snowman = _snowman.d(this.b);
         if (_snowman == null) {
            _snowman.a("Unknown condition table called " + this.b);
         } else {
            _snowman.a(_snowman.a(".{" + this.b + "}", this.b));
         }
      }
   }

   public boolean a(cyv var1) {
      dbo _snowman = _snowman.b(this.b);
      if (_snowman.a(_snowman)) {
         boolean var3;
         try {
            var3 = _snowman.test(_snowman);
         } finally {
            _snowman.b(_snowman);
         }

         return var3;
      } else {
         a.warn("Detected infinite loop in loot tables");
         return false;
      }
   }

   public static class a implements cze<dbg> {
      public a() {
      }

      public void a(JsonObject var1, dbg var2, JsonSerializationContext var3) {
         _snowman.addProperty("name", _snowman.b.toString());
      }

      public dbg b(JsonObject var1, JsonDeserializationContext var2) {
         vk _snowman = new vk(afd.h(_snowman, "name"));
         return new dbg(_snowman);
      }
   }
}
