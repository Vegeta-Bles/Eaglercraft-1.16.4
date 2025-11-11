import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;

public class dap extends dai {
   private final vk a;
   private final long b;

   private dap(dbo[] var1, vk var2, long var3) {
      super(_snowman);
      this.a = _snowman;
      this.b = _snowman;
   }

   @Override
   public dak b() {
      return dal.q;
   }

   @Override
   public bmb a(bmb var1, cyv var2) {
      if (_snowman.a()) {
         return _snowman;
      } else {
         md _snowman = new md();
         _snowman.a("LootTable", this.a.toString());
         if (this.b != 0L) {
            _snowman.a("LootTableSeed", this.b);
         }

         _snowman.p().a("BlockEntityTag", _snowman);
         return _snowman;
      }
   }

   @Override
   public void a(czg var1) {
      if (_snowman.a(this.a)) {
         _snowman.a("Table " + this.a + " is recursively called");
      } else {
         super.a(_snowman);
         cyy _snowman = _snowman.c(this.a);
         if (_snowman == null) {
            _snowman.a("Unknown loot table called " + this.a);
         } else {
            _snowman.a(_snowman.a("->{" + this.a + "}", this.a));
         }
      }
   }

   public static class a extends dai.c<dap> {
      public a() {
      }

      public void a(JsonObject var1, dap var2, JsonSerializationContext var3) {
         super.a(_snowman, _snowman, _snowman);
         _snowman.addProperty("name", _snowman.a.toString());
         if (_snowman.b != 0L) {
            _snowman.addProperty("seed", _snowman.b);
         }
      }

      public dap a(JsonObject var1, JsonDeserializationContext var2, dbo[] var3) {
         vk _snowman = new vk(afd.h(_snowman, "name"));
         long _snowmanx = afd.a(_snowman, "seed", 0L);
         return new dap(_snowman, _snowman, _snowmanx);
      }
   }
}
