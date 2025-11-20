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
         Registry<T> arg,
         String rootFieldName,
         String idFieldName,
         Function<E, T> typeIdentification,
         @Nullable com.mojang.datafixers.util.Pair<T, JsonSerializing.CustomSerializer<? extends E>> pair
      ) {
         this.registry = arg;
         this.rootFieldName = rootFieldName;
         this.idFieldName = idFieldName;
         this.typeIdentification = typeIdentification;
         this.elementSerializer = pair;
      }

      public E deserialize(JsonElement jsonElement, Type type, JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {
         if (jsonElement.isJsonObject()) {
            JsonObject jsonObject = JsonHelper.asObject(jsonElement, this.rootFieldName);
            Identifier lv = new Identifier(JsonHelper.getString(jsonObject, this.idFieldName));
            T lv2 = this.registry.get(lv);
            if (lv2 == null) {
               throw new JsonSyntaxException("Unknown type '" + lv + "'");
            } else {
               JsonSerializer<? extends E> serializer = (JsonSerializer<? extends E>)lv2.getJsonSerializer();
               return serializer.fromJson(jsonObject, jsonDeserializationContext);
            }
         } else if (this.elementSerializer == null) {
            throw new UnsupportedOperationException("Object " + jsonElement + " can't be deserialized");
         } else {
            return (E)((JsonSerializing.CustomSerializer)this.elementSerializer.getSecond()).fromJson(jsonElement, jsonDeserializationContext);
         }
      }

      public JsonElement serialize(E object, Type type, JsonSerializationContext jsonSerializationContext) {
         T lv = this.typeIdentification.apply(object);
         if (this.elementSerializer != null && this.elementSerializer.getFirst() == lv) {
            return ((JsonSerializing.CustomSerializer)this.elementSerializer.getSecond()).toJson(object, jsonSerializationContext);
         } else if (lv == null) {
            throw new JsonSyntaxException("Unknown type: " + object);
         } else {
            JsonObject jsonObject = new JsonObject();
            jsonObject.addProperty(this.idFieldName, this.registry.getId(lv).toString());
            JsonSerializer<? super E> serializer = (JsonSerializer<? super E>)lv.getJsonSerializer();
            serializer.toJson(jsonObject, object, jsonSerializationContext);
            return jsonObject;
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
