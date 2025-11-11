import com.google.gson.JsonElement;
import com.google.gson.JsonNull;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSyntaxException;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import javax.annotation.Nullable;

public class cb {
   public static final cb a = new cb(null);
   @Nullable
   private final md b;

   public cb(@Nullable md var1) {
      this.b = _snowman;
   }

   public boolean a(bmb var1) {
      return this == a ? true : this.a(_snowman.o());
   }

   public boolean a(aqa var1) {
      return this == a ? true : this.a(b(_snowman));
   }

   public boolean a(@Nullable mt var1) {
      return _snowman == null ? this == a : this.b == null || mp.a(this.b, _snowman, true);
   }

   public JsonElement a() {
      return (JsonElement)(this != a && this.b != null ? new JsonPrimitive(this.b.toString()) : JsonNull.INSTANCE);
   }

   public static cb a(@Nullable JsonElement var0) {
      if (_snowman != null && !_snowman.isJsonNull()) {
         md _snowman;
         try {
            _snowman = mu.a(afd.a(_snowman, "nbt"));
         } catch (CommandSyntaxException var3) {
            throw new JsonSyntaxException("Invalid nbt tag: " + var3.getMessage());
         }

         return new cb(_snowman);
      } else {
         return a;
      }
   }

   public static md b(aqa var0) {
      md _snowman = _snowman.e(new md());
      if (_snowman instanceof bfw) {
         bmb _snowmanx = ((bfw)_snowman).bm.f();
         if (!_snowmanx.a()) {
            _snowman.a("SelectedItem", _snowmanx.b(new md()));
         }
      }

      return _snowman;
   }
}
