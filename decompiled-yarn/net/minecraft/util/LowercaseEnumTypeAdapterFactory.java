package net.minecraft.util;

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

public class LowercaseEnumTypeAdapterFactory implements TypeAdapterFactory {
   public LowercaseEnumTypeAdapterFactory() {
   }

   @Nullable
   public <T> TypeAdapter<T> create(Gson _snowman, TypeToken<T> _snowman) {
      Class<T> _snowmanxx = _snowman.getRawType();
      if (!_snowmanxx.isEnum()) {
         return null;
      } else {
         final Map<String, T> _snowmanxxx = Maps.newHashMap();

         for (T _snowmanxxxx : _snowmanxx.getEnumConstants()) {
            _snowmanxxx.put(this.getKey(_snowmanxxxx), _snowmanxxxx);
         }

         return new TypeAdapter<T>() {
            public void write(JsonWriter _snowman, T _snowman) throws IOException {
               if (_snowman == null) {
                  _snowman.nullValue();
               } else {
                  _snowman.value(LowercaseEnumTypeAdapterFactory.this.getKey(_snowman));
               }
            }

            @Nullable
            public T read(JsonReader _snowman) throws IOException {
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

   private String getKey(Object o) {
      return o instanceof Enum ? ((Enum)o).name().toLowerCase(Locale.ROOT) : o.toString().toLowerCase(Locale.ROOT);
   }
}
