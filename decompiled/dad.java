import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import java.util.Random;

public class dad extends dai {
   private final czb a;
   private final boolean b;

   private dad(dbo[] var1, czb var2, boolean var3) {
      super(_snowman);
      this.a = _snowman;
      this.b = _snowman;
   }

   @Override
   public dak b() {
      return dal.c;
   }

   @Override
   public bmb a(bmb var1, cyv var2) {
      Random _snowman = _snowman.a();
      return bpu.a(_snowman, _snowman, this.a.a(_snowman), this.b);
   }

   public static dad.a a(czb var0) {
      return new dad.a(_snowman);
   }

   public static class a extends dai.a<dad.a> {
      private final czb a;
      private boolean b;

      public a(czb var1) {
         this.a = _snowman;
      }

      protected dad.a a() {
         return this;
      }

      public dad.a e() {
         this.b = true;
         return this;
      }

      @Override
      public daj b() {
         return new dad(this.g(), this.a, this.b);
      }
   }

   public static class b extends dai.c<dad> {
      public b() {
      }

      public void a(JsonObject var1, dad var2, JsonSerializationContext var3) {
         super.a(_snowman, _snowman, _snowman);
         _snowman.add("levels", czc.a(_snowman.a, _snowman));
         _snowman.addProperty("treasure", _snowman.b);
      }

      public dad a(JsonObject var1, JsonDeserializationContext var2, dbo[] var3) {
         czb _snowman = czc.a(_snowman.get("levels"), _snowman);
         boolean _snowmanx = afd.a(_snowman, "treasure", false);
         return new dad(_snowman, _snowman, _snowmanx);
      }
   }
}
