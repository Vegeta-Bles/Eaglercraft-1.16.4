package net.minecraft.text;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import com.google.gson.stream.JsonReader;
import com.mojang.brigadier.Message;
import java.io.IOException;
import java.io.StringReader;
import java.lang.reflect.Field;
import java.lang.reflect.Type;
import java.util.List;
import java.util.Optional;
import java.util.Map.Entry;
import javax.annotation.Nullable;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.util.Identifier;
import net.minecraft.util.JsonHelper;
import net.minecraft.util.LowercaseEnumTypeAdapterFactory;
import net.minecraft.util.Util;

public interface Text extends Message, StringVisitable {
   Style getStyle();

   String asString();

   @Override
   default String getString() {
      return StringVisitable.super.getString();
   }

   default String asTruncatedString(int length) {
      StringBuilder stringBuilder = new StringBuilder();
      this.visit(string -> {
         int j = length - stringBuilder.length();
         if (j <= 0) {
            return TERMINATE_VISIT;
         } else {
            stringBuilder.append(string.length() <= j ? string : string.substring(0, j));
            return Optional.empty();
         }
      });
      return stringBuilder.toString();
   }

   List<Text> getSiblings();

   MutableText copy();

   MutableText shallowCopy();

   @Environment(EnvType.CLIENT)
   OrderedText asOrderedText();

   @Environment(EnvType.CLIENT)
   @Override
   default <T> Optional<T> visit(StringVisitable.StyledVisitor<T> styledVisitor, Style style) {
      Style lv = this.getStyle().withParent(style);
      Optional<T> optional = this.visitSelf(styledVisitor, lv);
      if (optional.isPresent()) {
         return optional;
      } else {
         for (Text lv2 : this.getSiblings()) {
            Optional<T> optional2 = lv2.visit(styledVisitor, lv);
            if (optional2.isPresent()) {
               return optional2;
            }
         }

         return Optional.empty();
      }
   }

   @Override
   default <T> Optional<T> visit(StringVisitable.Visitor<T> visitor) {
      Optional<T> optional = this.visitSelf(visitor);
      if (optional.isPresent()) {
         return optional;
      } else {
         for (Text lv : this.getSiblings()) {
            Optional<T> optional2 = lv.visit(visitor);
            if (optional2.isPresent()) {
               return optional2;
            }
         }

         return Optional.empty();
      }
   }

   @Environment(EnvType.CLIENT)
   default <T> Optional<T> visitSelf(StringVisitable.StyledVisitor<T> visitor, Style style) {
      return visitor.accept(style, this.asString());
   }

   default <T> Optional<T> visitSelf(StringVisitable.Visitor<T> visitor) {
      return visitor.accept(this.asString());
   }

   @Environment(EnvType.CLIENT)
   static Text of(@Nullable String string) {
      return (Text)(string != null ? new LiteralText(string) : LiteralText.EMPTY);
   }

   public static class Serializer implements JsonDeserializer<MutableText>, JsonSerializer<Text> {
      private static final Gson GSON = Util.make(() -> {
         GsonBuilder gsonBuilder = new GsonBuilder();
         gsonBuilder.disableHtmlEscaping();
         gsonBuilder.registerTypeHierarchyAdapter(Text.class, new Text.Serializer());
         gsonBuilder.registerTypeHierarchyAdapter(Style.class, new Style.Serializer());
         gsonBuilder.registerTypeAdapterFactory(new LowercaseEnumTypeAdapterFactory());
         return gsonBuilder.create();
      });
      private static final Field JSON_READER_POS = Util.make(() -> {
         try {
            new JsonReader(new StringReader(""));
            Field field = JsonReader.class.getDeclaredField("pos");
            field.setAccessible(true);
            return field;
         } catch (NoSuchFieldException var1) {
            throw new IllegalStateException("Couldn't get field 'pos' for JsonReader", var1);
         }
      });
      private static final Field JSON_READER_LINE_START = Util.make(() -> {
         try {
            new JsonReader(new StringReader(""));
            Field field = JsonReader.class.getDeclaredField("lineStart");
            field.setAccessible(true);
            return field;
         } catch (NoSuchFieldException var1) {
            throw new IllegalStateException("Couldn't get field 'lineStart' for JsonReader", var1);
         }
      });

      public Serializer() {
      }

      public MutableText deserialize(JsonElement jsonElement, Type type, JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {
         if (jsonElement.isJsonPrimitive()) {
            return new LiteralText(jsonElement.getAsString());
         } else if (!jsonElement.isJsonObject()) {
            if (jsonElement.isJsonArray()) {
               JsonArray jsonArray3 = jsonElement.getAsJsonArray();
               MutableText lv14 = null;

               for (JsonElement jsonElement2 : jsonArray3) {
                  MutableText lv15 = this.deserialize(jsonElement2, jsonElement2.getClass(), jsonDeserializationContext);
                  if (lv14 == null) {
                     lv14 = lv15;
                  } else {
                     lv14.append(lv15);
                  }
               }

               return lv14;
            } else {
               throw new JsonParseException("Don't know how to turn " + jsonElement + " into a Component");
            }
         } else {
            JsonObject jsonObject = jsonElement.getAsJsonObject();
            MutableText lv;
            if (jsonObject.has("text")) {
               lv = new LiteralText(JsonHelper.getString(jsonObject, "text"));
            } else if (jsonObject.has("translate")) {
               String string = JsonHelper.getString(jsonObject, "translate");
               if (jsonObject.has("with")) {
                  JsonArray jsonArray = JsonHelper.getArray(jsonObject, "with");
                  Object[] objects = new Object[jsonArray.size()];

                  for (int i = 0; i < objects.length; i++) {
                     objects[i] = this.deserialize(jsonArray.get(i), type, jsonDeserializationContext);
                     if (objects[i] instanceof LiteralText) {
                        LiteralText lv2 = (LiteralText)objects[i];
                        if (lv2.getStyle().isEmpty() && lv2.getSiblings().isEmpty()) {
                           objects[i] = lv2.getRawString();
                        }
                     }
                  }

                  lv = new TranslatableText(string, objects);
               } else {
                  lv = new TranslatableText(string);
               }
            } else if (jsonObject.has("score")) {
               JsonObject jsonObject2 = JsonHelper.getObject(jsonObject, "score");
               if (!jsonObject2.has("name") || !jsonObject2.has("objective")) {
                  throw new JsonParseException("A score component needs a least a name and an objective");
               }

               lv = new ScoreText(JsonHelper.getString(jsonObject2, "name"), JsonHelper.getString(jsonObject2, "objective"));
            } else if (jsonObject.has("selector")) {
               lv = new SelectorText(JsonHelper.getString(jsonObject, "selector"));
            } else if (jsonObject.has("keybind")) {
               lv = new KeybindText(JsonHelper.getString(jsonObject, "keybind"));
            } else {
               if (!jsonObject.has("nbt")) {
                  throw new JsonParseException("Don't know how to turn " + jsonElement + " into a Component");
               }

               String string2 = JsonHelper.getString(jsonObject, "nbt");
               boolean bl = JsonHelper.getBoolean(jsonObject, "interpret", false);
               if (jsonObject.has("block")) {
                  lv = new NbtText.BlockNbtText(string2, bl, JsonHelper.getString(jsonObject, "block"));
               } else if (jsonObject.has("entity")) {
                  lv = new NbtText.EntityNbtText(string2, bl, JsonHelper.getString(jsonObject, "entity"));
               } else {
                  if (!jsonObject.has("storage")) {
                     throw new JsonParseException("Don't know how to turn " + jsonElement + " into a Component");
                  }

                  lv = new NbtText.StorageNbtText(string2, bl, new Identifier(JsonHelper.getString(jsonObject, "storage")));
               }
            }

            if (jsonObject.has("extra")) {
               JsonArray jsonArray2 = JsonHelper.getArray(jsonObject, "extra");
               if (jsonArray2.size() <= 0) {
                  throw new JsonParseException("Unexpected empty array of components");
               }

               for (int j = 0; j < jsonArray2.size(); j++) {
                  lv.append(this.deserialize(jsonArray2.get(j), type, jsonDeserializationContext));
               }
            }

            lv.setStyle((Style)jsonDeserializationContext.deserialize(jsonElement, Style.class));
            return lv;
         }
      }

      private void addStyle(Style style, JsonObject json, JsonSerializationContext context) {
         JsonElement jsonElement = context.serialize(style);
         if (jsonElement.isJsonObject()) {
            JsonObject jsonObject2 = (JsonObject)jsonElement;

            for (Entry<String, JsonElement> entry : jsonObject2.entrySet()) {
               json.add(entry.getKey(), entry.getValue());
            }
         }
      }

      public JsonElement serialize(Text arg, Type type, JsonSerializationContext jsonSerializationContext) {
         JsonObject jsonObject = new JsonObject();
         if (!arg.getStyle().isEmpty()) {
            this.addStyle(arg.getStyle(), jsonObject, jsonSerializationContext);
         }

         if (!arg.getSiblings().isEmpty()) {
            JsonArray jsonArray = new JsonArray();

            for (Text lv : arg.getSiblings()) {
               jsonArray.add(this.serialize(lv, lv.getClass(), jsonSerializationContext));
            }

            jsonObject.add("extra", jsonArray);
         }

         if (arg instanceof LiteralText) {
            jsonObject.addProperty("text", ((LiteralText)arg).getRawString());
         } else if (arg instanceof TranslatableText) {
            TranslatableText lv2 = (TranslatableText)arg;
            jsonObject.addProperty("translate", lv2.getKey());
            if (lv2.getArgs() != null && lv2.getArgs().length > 0) {
               JsonArray jsonArray2 = new JsonArray();

               for (Object object : lv2.getArgs()) {
                  if (object instanceof Text) {
                     jsonArray2.add(this.serialize((Text)object, object.getClass(), jsonSerializationContext));
                  } else {
                     jsonArray2.add(new JsonPrimitive(String.valueOf(object)));
                  }
               }

               jsonObject.add("with", jsonArray2);
            }
         } else if (arg instanceof ScoreText) {
            ScoreText lv3 = (ScoreText)arg;
            JsonObject jsonObject2 = new JsonObject();
            jsonObject2.addProperty("name", lv3.getName());
            jsonObject2.addProperty("objective", lv3.getObjective());
            jsonObject.add("score", jsonObject2);
         } else if (arg instanceof SelectorText) {
            SelectorText lv4 = (SelectorText)arg;
            jsonObject.addProperty("selector", lv4.getPattern());
         } else if (arg instanceof KeybindText) {
            KeybindText lv5 = (KeybindText)arg;
            jsonObject.addProperty("keybind", lv5.getKey());
         } else {
            if (!(arg instanceof NbtText)) {
               throw new IllegalArgumentException("Don't know how to serialize " + arg + " as a Component");
            }

            NbtText lv6 = (NbtText)arg;
            jsonObject.addProperty("nbt", lv6.getPath());
            jsonObject.addProperty("interpret", lv6.shouldInterpret());
            if (arg instanceof NbtText.BlockNbtText) {
               NbtText.BlockNbtText lv7 = (NbtText.BlockNbtText)arg;
               jsonObject.addProperty("block", lv7.getPos());
            } else if (arg instanceof NbtText.EntityNbtText) {
               NbtText.EntityNbtText lv8 = (NbtText.EntityNbtText)arg;
               jsonObject.addProperty("entity", lv8.getSelector());
            } else {
               if (!(arg instanceof NbtText.StorageNbtText)) {
                  throw new IllegalArgumentException("Don't know how to serialize " + arg + " as a Component");
               }

               NbtText.StorageNbtText lv9 = (NbtText.StorageNbtText)arg;
               jsonObject.addProperty("storage", lv9.getId().toString());
            }
         }

         return jsonObject;
      }

      public static String toJson(Text text) {
         return GSON.toJson(text);
      }

      public static JsonElement toJsonTree(Text text) {
         return GSON.toJsonTree(text);
      }

      @Nullable
      public static MutableText fromJson(String json) {
         return JsonHelper.deserialize(GSON, json, MutableText.class, false);
      }

      @Nullable
      public static MutableText fromJson(JsonElement json) {
         return (MutableText)GSON.fromJson(json, MutableText.class);
      }

      @Nullable
      public static MutableText fromLenientJson(String json) {
         return JsonHelper.deserialize(GSON, json, MutableText.class, true);
      }

      public static MutableText fromJson(com.mojang.brigadier.StringReader reader) {
         try {
            JsonReader jsonReader = new JsonReader(new StringReader(reader.getRemaining()));
            jsonReader.setLenient(false);
            MutableText lv = (MutableText)GSON.getAdapter(MutableText.class).read(jsonReader);
            reader.setCursor(reader.getCursor() + getPosition(jsonReader));
            return lv;
         } catch (StackOverflowError | IOException var3) {
            throw new JsonParseException(var3);
         }
      }

      private static int getPosition(JsonReader reader) {
         try {
            return JSON_READER_POS.getInt(reader) - JSON_READER_LINE_START.getInt(reader) + 1;
         } catch (IllegalAccessException var2) {
            throw new IllegalStateException("Couldn't read position of JsonReader", var2);
         }
      }
   }
}
