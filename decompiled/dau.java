import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSyntaxException;
import com.mojang.brigadier.exceptions.CommandSyntaxException;

public class dau extends dai {
   private final md a;

   private dau(dbo[] var1, md var2) {
      super(_snowman);
      this.a = _snowman;
   }

   @Override
   public dak b() {
      return dal.e;
   }

   @Override
   public bmb a(bmb var1, cyv var2) {
      _snowman.p().a(this.a);
      return _snowman;
   }

   public static dai.a<?> a(md var0) {
      return a(var1 -> new dau(var1, _snowman));
   }

   public static class a extends dai.c<dau> {
      public a() {
      }

      public void a(JsonObject var1, dau var2, JsonSerializationContext var3) {
         super.a(_snowman, _snowman, _snowman);
         _snowman.addProperty("tag", _snowman.a.toString());
      }

      public dau a(JsonObject var1, JsonDeserializationContext var2, dbo[] var3) {
         try {
            md _snowman = mu.a(afd.h(_snowman, "tag"));
            return new dau(_snowman, _snowman);
         } catch (CommandSyntaxException var5) {
            throw new JsonSyntaxException(var5.getMessage());
         }
      }
   }
}
