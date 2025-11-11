import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import java.util.function.Consumer;

public class czn extends czs {
   private final blx g;

   private czn(blx var1, int var2, int var3, dbo[] var4, daj[] var5) {
      super(_snowman, _snowman, _snowman, _snowman);
      this.g = _snowman;
   }

   @Override
   public czr a() {
      return czo.b;
   }

   @Override
   public void a(Consumer<bmb> var1, cyv var2) {
      _snowman.accept(new bmb(this.g));
   }

   public static czs.a<?> a(brw var0) {
      return a((var1, var2, var3, var4) -> new czn(_snowman.h(), var1, var2, var3, var4));
   }

   public static class a extends czs.e<czn> {
      public a() {
      }

      public void a(JsonObject var1, czn var2, JsonSerializationContext var3) {
         super.a(_snowman, _snowman, _snowman);
         vk _snowman = gm.T.b(_snowman.g);
         if (_snowman == null) {
            throw new IllegalArgumentException("Can't serialize unknown item " + _snowman.g);
         } else {
            _snowman.addProperty("name", _snowman.toString());
         }
      }

      protected czn a(JsonObject var1, JsonDeserializationContext var2, int var3, int var4, dbo[] var5, daj[] var6) {
         blx _snowman = afd.i(_snowman, "name");
         return new czn(_snowman, _snowman, _snowman, _snowman, _snowman);
      }
   }
}
