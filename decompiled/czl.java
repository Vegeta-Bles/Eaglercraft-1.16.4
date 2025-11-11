import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonObject;
import java.util.function.Consumer;

public class czl extends czs {
   private czl(int var1, int var2, dbo[] var3, daj[] var4) {
      super(_snowman, _snowman, _snowman, _snowman);
   }

   @Override
   public czr a() {
      return czo.a;
   }

   @Override
   public void a(Consumer<bmb> var1, cyv var2) {
   }

   public static czs.a<?> b() {
      return a(czl::new);
   }

   public static class a extends czs.e<czl> {
      public a() {
      }

      public czl a(JsonObject var1, JsonDeserializationContext var2, int var3, int var4, dbo[] var5, daj[] var6) {
         return new czl(_snowman, _snowman, _snowman, _snowman);
      }
   }
}
