import com.google.common.collect.ImmutableSet;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSyntaxException;
import java.util.Set;

public class dbn implements dbo {
   private final buo a;
   private final cm b;

   private dbn(buo var1, cm var2) {
      this.a = _snowman;
      this.b = _snowman;
   }

   @Override
   public dbp b() {
      return dbq.h;
   }

   @Override
   public Set<daz<?>> a() {
      return ImmutableSet.of(dbc.g);
   }

   public boolean a(cyv var1) {
      ceh _snowman = _snowman.c(dbc.g);
      return _snowman != null && this.a == _snowman.b() && this.b.a(_snowman);
   }

   public static dbn.a a(buo var0) {
      return new dbn.a(_snowman);
   }

   public static class a implements dbo.a {
      private final buo a;
      private cm b = cm.a;

      public a(buo var1) {
         this.a = _snowman;
      }

      public dbn.a a(cm.a var1) {
         this.b = _snowman.b();
         return this;
      }

      @Override
      public dbo build() {
         return new dbn(this.a, this.b);
      }
   }

   public static class b implements cze<dbn> {
      public b() {
      }

      public void a(JsonObject var1, dbn var2, JsonSerializationContext var3) {
         _snowman.addProperty("block", gm.Q.b(_snowman.a).toString());
         _snowman.add("properties", _snowman.b.a());
      }

      public dbn b(JsonObject var1, JsonDeserializationContext var2) {
         vk _snowman = new vk(afd.h(_snowman, "block"));
         buo _snowmanx = gm.Q.b(_snowman).orElseThrow(() -> new IllegalArgumentException("Can't find block " + _snowman));
         cm _snowmanxx = cm.a(_snowman.get("properties"));
         _snowmanxx.a(_snowmanx.m(), var1x -> {
            throw new JsonSyntaxException("Block " + _snowman + " has no property " + var1x);
         });
         return new dbn(_snowmanx, _snowmanxx);
      }
   }
}
