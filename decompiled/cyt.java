import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import com.google.gson.JsonSyntaxException;
import com.mojang.datafixers.util.Pair;
import java.lang.reflect.Type;
import java.util.function.Function;
import javax.annotation.Nullable;

public class cyt {
   public static <E, T extends czf<E>> cyt.a<E, T> a(gm<T> var0, String var1, String var2, Function<E, T> var3) {
      return new cyt.a<>(_snowman, _snowman, _snowman, _snowman);
   }

   public static class a<E, T extends czf<E>> {
      private final gm<T> a;
      private final String b;
      private final String c;
      private final Function<E, T> d;
      @Nullable
      private Pair<T, cyt.b<? extends E>> e;

      private a(gm<T> var1, String var2, String var3, Function<E, T> var4) {
         this.a = _snowman;
         this.b = _snowman;
         this.c = _snowman;
         this.d = _snowman;
      }

      public Object a() {
         return new cyt.c(this.a, this.b, this.c, this.d, this.e);
      }
   }

   public interface b<T> {
      JsonElement a(T var1, JsonSerializationContext var2);

      T a(JsonElement var1, JsonDeserializationContext var2);
   }

   static class c<E, T extends czf<E>> implements JsonDeserializer<E>, JsonSerializer<E> {
      private final gm<T> a;
      private final String b;
      private final String c;
      private final Function<E, T> d;
      @Nullable
      private final Pair<T, cyt.b<? extends E>> e;

      private c(gm<T> var1, String var2, String var3, Function<E, T> var4, @Nullable Pair<T, cyt.b<? extends E>> var5) {
         this.a = _snowman;
         this.b = _snowman;
         this.c = _snowman;
         this.d = _snowman;
         this.e = _snowman;
      }

      public E deserialize(JsonElement var1, Type var2, JsonDeserializationContext var3) throws JsonParseException {
         if (_snowman.isJsonObject()) {
            JsonObject _snowman = afd.m(_snowman, this.b);
            vk _snowmanx = new vk(afd.h(_snowman, this.c));
            T _snowmanxx = this.a.a(_snowmanx);
            if (_snowmanxx == null) {
               throw new JsonSyntaxException("Unknown type '" + _snowmanx + "'");
            } else {
               return (E)_snowmanxx.a().a(_snowman, _snowman);
            }
         } else if (this.e == null) {
            throw new UnsupportedOperationException("Object " + _snowman + " can't be deserialized");
         } else {
            return (E)((cyt.b)this.e.getSecond()).a(_snowman, _snowman);
         }
      }

      public JsonElement serialize(E var1, Type var2, JsonSerializationContext var3) {
         T _snowman = this.d.apply(_snowman);
         if (this.e != null && this.e.getFirst() == _snowman) {
            return ((cyt.b)this.e.getSecond()).a(_snowman, _snowman);
         } else if (_snowman == null) {
            throw new JsonSyntaxException("Unknown type: " + _snowman);
         } else {
            JsonObject _snowmanx = new JsonObject();
            _snowmanx.addProperty(this.c, this.a.b(_snowman).toString());
            _snowman.a().a(_snowmanx, _snowman, _snowman);
            return _snowmanx;
         }
      }
   }
}
