package net.minecraft.util;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSyntaxException;
import java.lang.reflect.Type;
import java.util.function.Function;
import javax.annotation.Nullable;
import net.minecraft.util.registry.Registry;

public class JsonSerializing {
   public static <E, T extends JsonSerializableType<E>> JsonSerializing.TypeHandler<E, T> createTypeHandler(
      Registry<T> registry, String rootFieldName, String idFieldName, Function<E, T> typeIdentification
   ) {
      return new JsonSerializing.TypeHandler<>(registry, rootFieldName, idFieldName, typeIdentification);
   }

   public interface CustomSerializer<T> {
      JsonElement toJson(T object, JsonSerializationContext context);

      T fromJson(JsonElement json, JsonDeserializationContext context);
   }

   static class GsonSerializer<E, T extends JsonSerializableType<E>> implements JsonDeserializer<E>, com.google.gson.JsonSerializer<E> {
      private final Registry<T> registry;
      private final String rootFieldName;
      private final String idFieldName;
      private final Function<E, T> typeIdentification;
      @Nullable
      private final com.mojang.datafixers.util.Pair<T, JsonSerializing.CustomSerializer<? extends E>> elementSerializer;

      private GsonSerializer(
         Registry<T> _snowman,
         String rootFieldName,
         String idFieldName,
         Function<E, T> typeIdentification,
         @Nullable com.mojang.datafixers.util.Pair<T, JsonSerializing.CustomSerializer<? extends E>> _snowman
      ) {
         this.registry = _snowman;
         this.rootFieldName = rootFieldName;
         this.idFieldName = idFieldName;
         this.typeIdentification = typeIdentification;
         this.elementSerializer = _snowman;
      }

      public E deserialize(JsonElement _snowman, Type _snowman, JsonDeserializationContext _snowman) throws JsonParseException {
         if (_snowman.isJsonObject()) {
            JsonObject _snowmanxxx = JsonHelper.asObject(_snowman, this.rootFieldName);
            Identifier _snowmanxxxx = new Identifier(JsonHelper.getString(_snowmanxxx, this.idFieldName));
            T _snowmanxxxxx = this.registry.get(_snowmanxxxx);
            if (_snowmanxxxxx == null) {
               throw new JsonSyntaxException("Unknown type '" + _snowmanxxxx + "'");
            } else {
               return (E)_snowmanxxxxx.getJsonSerializer().fromJson(_snowmanxxx, _snowman);
            }
         } else if (this.elementSerializer == null) {
            throw new UnsupportedOperationException("Object " + _snowman + " can't be deserialized");
         } else {
            return (E)((JsonSerializing.CustomSerializer)this.elementSerializer.getSecond()).fromJson(_snowman, _snowman);
         }
      }

      public JsonElement serialize(E _snowman, Type _snowman, JsonSerializationContext _snowman) {
         T _snowmanxxx = this.typeIdentification.apply(_snowman);
         if (this.elementSerializer != null && this.elementSerializer.getFirst() == _snowmanxxx) {
            return ((JsonSerializing.CustomSerializer)this.elementSerializer.getSecond()).toJson(_snowman, _snowman);
         } else if (_snowmanxxx == null) {
            throw new JsonSyntaxException("Unknown type: " + _snowman);
         } else {
            JsonObject _snowmanxxxx = new JsonObject();
            _snowmanxxxx.addProperty(this.idFieldName, this.registry.getId(_snowmanxxx).toString());
            _snowmanxxx.getJsonSerializer().toJson(_snowmanxxxx, _snowman, _snowman);
            return _snowmanxxxx;
         }
      }
   }

   public static class TypeHandler<E, T extends JsonSerializableType<E>> {
      private final Registry<T> registry;
      private final String rootFieldName;
      private final String idFieldName;
      private final Function<E, T> typeIdentification;
      @Nullable
      private com.mojang.datafixers.util.Pair<T, JsonSerializing.CustomSerializer<? extends E>> customSerializer;

      private TypeHandler(Registry<T> registry, String rootFieldName, String idFieldName, Function<E, T> typeIdentification) {
         this.registry = registry;
         this.rootFieldName = rootFieldName;
         this.idFieldName = idFieldName;
         this.typeIdentification = typeIdentification;
      }

      public Object createGsonSerializer() {
         return new JsonSerializing.GsonSerializer(this.registry, this.rootFieldName, this.idFieldName, this.typeIdentification, this.customSerializer);
      }
   }
}
