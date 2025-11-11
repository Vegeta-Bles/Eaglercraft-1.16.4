import com.google.common.collect.Maps;
import com.google.gson.Gson;
import com.google.gson.TypeAdapter;
import com.google.gson.TypeAdapterFactory;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonToken;
import com.google.gson.stream.JsonWriter;
import java.io.IOException;
import java.util.Locale;
import java.util.Map;
import javax.annotation.Nullable;

public class afl implements TypeAdapterFactory {
   public afl() {
   }

   @Nullable
   public <T> TypeAdapter<T> create(Gson var1, TypeToken<T> var2) {
      Class<T> _snowman = _snowman.getRawType();
      if (!_snowman.isEnum()) {
         return null;
      } else {
         final Map<String, T> _snowmanx = Maps.newHashMap();

         for (T _snowmanxx : _snowman.getEnumConstants()) {
            _snowmanx.put(this.a(_snowmanxx), _snowmanxx);
         }

         return new TypeAdapter<T>() {
            public void write(JsonWriter var1, T var2) throws IOException {
               if (_snowman == null) {
                  _snowman.nullValue();
               } else {
                  _snowman.value(afl.this.a(_snowman));
               }
            }

            @Nullable
            public T read(JsonReader var1) throws IOException {
               if (_snowman.peek() == JsonToken.NULL) {
                  _snowman.nextNull();
                  return null;
               } else {
                  return _snowman.get(_snowman.nextString());
               }
            }
         };
      }
   }

   private String a(Object var1) {
      return _snowman instanceof Enum ? ((Enum)_snowman).name().toLowerCase(Locale.ROOT) : _snowman.toString().toLowerCase(Locale.ROOT);
   }
}
