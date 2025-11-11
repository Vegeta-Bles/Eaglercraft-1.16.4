import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import com.mojang.brigadier.StringReader;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.exceptions.SimpleCommandExceptionType;
import com.mojang.serialization.Codec;
import com.mojang.serialization.DataResult;
import java.lang.reflect.Type;
import javax.annotation.Nullable;
import org.apache.commons.lang3.StringUtils;

public class vk implements Comparable<vk> {
   public static final Codec<vk> a = Codec.STRING.comapFlatMap(vk::c, vk::toString).stable();
   private static final SimpleCommandExceptionType d = new SimpleCommandExceptionType(new of("argument.id.invalid"));
   protected final String b;
   protected final String c;

   protected vk(String[] var1) {
      this.b = StringUtils.isEmpty(_snowman[0]) ? "minecraft" : _snowman[0];
      this.c = _snowman[1];
      if (!e(this.b)) {
         throw new v("Non [a-z0-9_.-] character in namespace of location: " + this.b + ':' + this.c);
      } else if (!d(this.c)) {
         throw new v("Non [a-z0-9/._-] character in path of location: " + this.b + ':' + this.c);
      }
   }

   public vk(String var1) {
      this(b(_snowman, ':'));
   }

   public vk(String var1, String var2) {
      this(new String[]{_snowman, _snowman});
   }

   public static vk a(String var0, char var1) {
      return new vk(b(_snowman, _snowman));
   }

   @Nullable
   public static vk a(String var0) {
      try {
         return new vk(_snowman);
      } catch (v var2) {
         return null;
      }
   }

   protected static String[] b(String var0, char var1) {
      String[] _snowman = new String[]{"minecraft", _snowman};
      int _snowmanx = _snowman.indexOf(_snowman);
      if (_snowmanx >= 0) {
         _snowman[1] = _snowman.substring(_snowmanx + 1, _snowman.length());
         if (_snowmanx >= 1) {
            _snowman[0] = _snowman.substring(0, _snowmanx);
         }
      }

      return _snowman;
   }

   private static DataResult<vk> c(String var0) {
      try {
         return DataResult.success(new vk(_snowman));
      } catch (v var2) {
         return DataResult.error("Not a valid resource location: " + _snowman + " " + var2.getMessage());
      }
   }

   public String a() {
      return this.c;
   }

   public String b() {
      return this.b;
   }

   @Override
   public String toString() {
      return this.b + ':' + this.c;
   }

   @Override
   public boolean equals(Object var1) {
      if (this == _snowman) {
         return true;
      } else if (!(_snowman instanceof vk)) {
         return false;
      } else {
         vk _snowman = (vk)_snowman;
         return this.b.equals(_snowman.b) && this.c.equals(_snowman.c);
      }
   }

   @Override
   public int hashCode() {
      return 31 * this.b.hashCode() + this.c.hashCode();
   }

   public int a(vk var1) {
      int _snowman = this.c.compareTo(_snowman.c);
      if (_snowman == 0) {
         _snowman = this.b.compareTo(_snowman.b);
      }

      return _snowman;
   }

   public static vk a(StringReader var0) throws CommandSyntaxException {
      int _snowman = _snowman.getCursor();

      while (_snowman.canRead() && a(_snowman.peek())) {
         _snowman.skip();
      }

      String _snowmanx = _snowman.getString().substring(_snowman, _snowman.getCursor());

      try {
         return new vk(_snowmanx);
      } catch (v var4) {
         _snowman.setCursor(_snowman);
         throw d.createWithContext(_snowman);
      }
   }

   public static boolean a(char var0) {
      return _snowman >= '0' && _snowman <= '9' || _snowman >= 'a' && _snowman <= 'z' || _snowman == '_' || _snowman == ':' || _snowman == '/' || _snowman == '.' || _snowman == '-';
   }

   private static boolean d(String var0) {
      for (int _snowman = 0; _snowman < _snowman.length(); _snowman++) {
         if (!b(_snowman.charAt(_snowman))) {
            return false;
         }
      }

      return true;
   }

   private static boolean e(String var0) {
      for (int _snowman = 0; _snowman < _snowman.length(); _snowman++) {
         if (!c(_snowman.charAt(_snowman))) {
            return false;
         }
      }

      return true;
   }

   public static boolean b(char var0) {
      return _snowman == '_' || _snowman == '-' || _snowman >= 'a' && _snowman <= 'z' || _snowman >= '0' && _snowman <= '9' || _snowman == '/' || _snowman == '.';
   }

   private static boolean c(char var0) {
      return _snowman == '_' || _snowman == '-' || _snowman >= 'a' && _snowman <= 'z' || _snowman >= '0' && _snowman <= '9' || _snowman == '.';
   }

   public static boolean b(String var0) {
      String[] _snowman = b(_snowman, ':');
      return e(StringUtils.isEmpty(_snowman[0]) ? "minecraft" : _snowman[0]) && d(_snowman[1]);
   }

   public static class a implements JsonDeserializer<vk>, JsonSerializer<vk> {
      public a() {
      }

      public vk a(JsonElement var1, Type var2, JsonDeserializationContext var3) throws JsonParseException {
         return new vk(afd.a(_snowman, "location"));
      }

      public JsonElement a(vk var1, Type var2, JsonSerializationContext var3) {
         return new JsonPrimitive(_snowman.toString());
      }
   }
}
