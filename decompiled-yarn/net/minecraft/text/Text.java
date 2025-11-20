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
      StringBuilder _snowman = new StringBuilder();
      this.visit(_snowmanxx -> {
         int _snowmanx = length - _snowman.length();
         if (_snowmanx <= 0) {
            return TERMINATE_VISIT;
         } else {
            _snowman.append(_snowmanxx.length() <= _snowmanx ? _snowmanxx : _snowmanxx.substring(0, _snowmanx));
            return Optional.empty();
         }
      });
      return _snowman.toString();
   }

   List<Text> getSiblings();

   MutableText copy();

   MutableText shallowCopy();

   OrderedText asOrderedText();

   @Override
   default <T> Optional<T> visit(StringVisitable.StyledVisitor<T> styledVisitor, Style style) {
      Style _snowman = this.getStyle().withParent(style);
      Optional<T> _snowmanx = this.visitSelf(styledVisitor, _snowman);
      if (_snowmanx.isPresent()) {
         return _snowmanx;
      } else {
         for (Text _snowmanxx : this.getSiblings()) {
            Optional<T> _snowmanxxx = _snowmanxx.visit(styledVisitor, _snowman);
            if (_snowmanxxx.isPresent()) {
               return _snowmanxxx;
            }
         }

         return Optional.empty();
      }
   }

   @Override
   default <T> Optional<T> visit(StringVisitable.Visitor<T> visitor) {
      Optional<T> _snowman = this.visitSelf(visitor);
      if (_snowman.isPresent()) {
         return _snowman;
      } else {
         for (Text _snowmanx : this.getSiblings()) {
            Optional<T> _snowmanxx = _snowmanx.visit(visitor);
            if (_snowmanxx.isPresent()) {
               return _snowmanxx;
            }
         }

         return Optional.empty();
      }
   }

   default <T> Optional<T> visitSelf(StringVisitable.StyledVisitor<T> visitor, Style style) {
      return visitor.accept(style, this.asString());
   }

   default <T> Optional<T> visitSelf(StringVisitable.Visitor<T> visitor) {
      return visitor.accept(this.asString());
   }

   static Text of(@Nullable String string) {
      return (Text)(string != null ? new LiteralText(string) : LiteralText.EMPTY);
   }

   public static class Serializer implements JsonDeserializer<MutableText>, JsonSerializer<Text> {
      private static final Gson GSON = Util.make(() -> {
         GsonBuilder _snowman = new GsonBuilder();
         _snowman.disableHtmlEscaping();
         _snowman.registerTypeHierarchyAdapter(Text.class, new Text.Serializer());
         _snowman.registerTypeHierarchyAdapter(Style.class, new Style.Serializer());
         _snowman.registerTypeAdapterFactory(new LowercaseEnumTypeAdapterFactory());
         return _snowman.create();
      });
      private static final Field JSON_READER_POS = Util.make(() -> {
         try {
            new JsonReader(new StringReader(""));
            Field _snowman = JsonReader.class.getDeclaredField("pos");
            _snowman.setAccessible(true);
            return _snowman;
         } catch (NoSuchFieldException var1) {
            throw new IllegalStateException("Couldn't get field 'pos' for JsonReader", var1);
         }
      });
      private static final Field JSON_READER_LINE_START = Util.make(() -> {
         try {
            new JsonReader(new StringReader(""));
            Field _snowman = JsonReader.class.getDeclaredField("lineStart");
            _snowman.setAccessible(true);
            return _snowman;
         } catch (NoSuchFieldException var1) {
            throw new IllegalStateException("Couldn't get field 'lineStart' for JsonReader", var1);
         }
      });

      public Serializer() {
      }

      public MutableText deserialize(JsonElement _snowman, Type _snowman, JsonDeserializationContext _snowman) throws JsonParseException {
         if (_snowman.isJsonPrimitive()) {
            return new LiteralText(_snowman.getAsString());
         } else if (!_snowman.isJsonObject()) {
            if (_snowman.isJsonArray()) {
               JsonArray _snowmanxxx = _snowman.getAsJsonArray();
               MutableText _snowmanxxxx = null;

               for (JsonElement _snowmanxxxxx : _snowmanxxx) {
                  MutableText _snowmanxxxxxx = this.deserialize(_snowmanxxxxx, _snowmanxxxxx.getClass(), _snowman);
                  if (_snowmanxxxx == null) {
                     _snowmanxxxx = _snowmanxxxxxx;
                  } else {
                     _snowmanxxxx.append(_snowmanxxxxxx);
                  }
               }

               return _snowmanxxxx;
            } else {
               throw new JsonParseException("Don't know how to turn " + _snowman + " into a Component");
            }
         } else {
            JsonObject _snowmanxxx = _snowman.getAsJsonObject();
            MutableText _snowmanxxxx;
            if (_snowmanxxx.has("text")) {
               _snowmanxxxx = new LiteralText(JsonHelper.getString(_snowmanxxx, "text"));
            } else if (_snowmanxxx.has("translate")) {
               String _snowmanxxxxxx = JsonHelper.getString(_snowmanxxx, "translate");
               if (_snowmanxxx.has("with")) {
                  JsonArray _snowmanxxxxxxx = JsonHelper.getArray(_snowmanxxx, "with");
                  Object[] _snowmanxxxxxxxx = new Object[_snowmanxxxxxxx.size()];

                  for (int _snowmanxxxxxxxxx = 0; _snowmanxxxxxxxxx < _snowmanxxxxxxxx.length; _snowmanxxxxxxxxx++) {
                     _snowmanxxxxxxxx[_snowmanxxxxxxxxx] = this.deserialize(_snowmanxxxxxxx.get(_snowmanxxxxxxxxx), _snowman, _snowman);
                     if (_snowmanxxxxxxxx[_snowmanxxxxxxxxx] instanceof LiteralText) {
                        LiteralText _snowmanxxxxxxxxxx = (LiteralText)_snowmanxxxxxxxx[_snowmanxxxxxxxxx];
                        if (_snowmanxxxxxxxxxx.getStyle().isEmpty() && _snowmanxxxxxxxxxx.getSiblings().isEmpty()) {
                           _snowmanxxxxxxxx[_snowmanxxxxxxxxx] = _snowmanxxxxxxxxxx.getRawString();
                        }
                     }
                  }

                  _snowmanxxxx = new TranslatableText(_snowmanxxxxxx, _snowmanxxxxxxxx);
               } else {
                  _snowmanxxxx = new TranslatableText(_snowmanxxxxxx);
               }
            } else if (_snowmanxxx.has("score")) {
               JsonObject _snowmanxxxxxx = JsonHelper.getObject(_snowmanxxx, "score");
               if (!_snowmanxxxxxx.has("name") || !_snowmanxxxxxx.has("objective")) {
                  throw new JsonParseException("A score component needs a least a name and an objective");
               }

               _snowmanxxxx = new ScoreText(JsonHelper.getString(_snowmanxxxxxx, "name"), JsonHelper.getString(_snowmanxxxxxx, "objective"));
            } else if (_snowmanxxx.has("selector")) {
               _snowmanxxxx = new SelectorText(JsonHelper.getString(_snowmanxxx, "selector"));
            } else if (_snowmanxxx.has("keybind")) {
               _snowmanxxxx = new KeybindText(JsonHelper.getString(_snowmanxxx, "keybind"));
            } else {
               if (!_snowmanxxx.has("nbt")) {
                  throw new JsonParseException("Don't know how to turn " + _snowman + " into a Component");
               }

               String _snowmanxxxxxx = JsonHelper.getString(_snowmanxxx, "nbt");
               boolean _snowmanxxxxxxx = JsonHelper.getBoolean(_snowmanxxx, "interpret", false);
               if (_snowmanxxx.has("block")) {
                  _snowmanxxxx = new NbtText.BlockNbtText(_snowmanxxxxxx, _snowmanxxxxxxx, JsonHelper.getString(_snowmanxxx, "block"));
               } else if (_snowmanxxx.has("entity")) {
                  _snowmanxxxx = new NbtText.EntityNbtText(_snowmanxxxxxx, _snowmanxxxxxxx, JsonHelper.getString(_snowmanxxx, "entity"));
               } else {
                  if (!_snowmanxxx.has("storage")) {
                     throw new JsonParseException("Don't know how to turn " + _snowman + " into a Component");
                  }

                  _snowmanxxxx = new NbtText.StorageNbtText(_snowmanxxxxxx, _snowmanxxxxxxx, new Identifier(JsonHelper.getString(_snowmanxxx, "storage")));
               }
            }

            if (_snowmanxxx.has("extra")) {
               JsonArray _snowmanxxxxxx = JsonHelper.getArray(_snowmanxxx, "extra");
               if (_snowmanxxxxxx.size() <= 0) {
                  throw new JsonParseException("Unexpected empty array of components");
               }

               for (int _snowmanxxxxxxx = 0; _snowmanxxxxxxx < _snowmanxxxxxx.size(); _snowmanxxxxxxx++) {
                  _snowmanxxxx.append(this.deserialize(_snowmanxxxxxx.get(_snowmanxxxxxxx), _snowman, _snowman));
               }
            }

            _snowmanxxxx.setStyle((Style)_snowman.deserialize(_snowman, Style.class));
            return _snowmanxxxx;
         }
      }

      private void addStyle(Style style, JsonObject json, JsonSerializationContext context) {
         JsonElement _snowman = context.serialize(style);
         if (_snowman.isJsonObject()) {
            JsonObject _snowmanx = (JsonObject)_snowman;

            for (Entry<String, JsonElement> _snowmanxx : _snowmanx.entrySet()) {
               json.add(_snowmanxx.getKey(), _snowmanxx.getValue());
            }
         }
      }

      public JsonElement serialize(Text _snowman, Type _snowman, JsonSerializationContext _snowman) {
         JsonObject _snowmanxxx = new JsonObject();
         if (!_snowman.getStyle().isEmpty()) {
            this.addStyle(_snowman.getStyle(), _snowmanxxx, _snowman);
         }

         if (!_snowman.getSiblings().isEmpty()) {
            JsonArray _snowmanxxxx = new JsonArray();

            for (Text _snowmanxxxxx : _snowman.getSiblings()) {
               _snowmanxxxx.add(this.serialize(_snowmanxxxxx, _snowmanxxxxx.getClass(), _snowman));
            }

            _snowmanxxx.add("extra", _snowmanxxxx);
         }

         if (_snowman instanceof LiteralText) {
            _snowmanxxx.addProperty("text", ((LiteralText)_snowman).getRawString());
         } else if (_snowman instanceof TranslatableText) {
            TranslatableText _snowmanxxxx = (TranslatableText)_snowman;
            _snowmanxxx.addProperty("translate", _snowmanxxxx.getKey());
            if (_snowmanxxxx.getArgs() != null && _snowmanxxxx.getArgs().length > 0) {
               JsonArray _snowmanxxxxx = new JsonArray();

               for (Object _snowmanxxxxxx : _snowmanxxxx.getArgs()) {
                  if (_snowmanxxxxxx instanceof Text) {
                     _snowmanxxxxx.add(this.serialize((Text)_snowmanxxxxxx, _snowmanxxxxxx.getClass(), _snowman));
                  } else {
                     _snowmanxxxxx.add(new JsonPrimitive(String.valueOf(_snowmanxxxxxx)));
                  }
               }

               _snowmanxxx.add("with", _snowmanxxxxx);
            }
         } else if (_snowman instanceof ScoreText) {
            ScoreText _snowmanxxxx = (ScoreText)_snowman;
            JsonObject _snowmanxxxxx = new JsonObject();
            _snowmanxxxxx.addProperty("name", _snowmanxxxx.getName());
            _snowmanxxxxx.addProperty("objective", _snowmanxxxx.getObjective());
            _snowmanxxx.add("score", _snowmanxxxxx);
         } else if (_snowman instanceof SelectorText) {
            SelectorText _snowmanxxxx = (SelectorText)_snowman;
            _snowmanxxx.addProperty("selector", _snowmanxxxx.getPattern());
         } else if (_snowman instanceof KeybindText) {
            KeybindText _snowmanxxxx = (KeybindText)_snowman;
            _snowmanxxx.addProperty("keybind", _snowmanxxxx.getKey());
         } else {
            if (!(_snowman instanceof NbtText)) {
               throw new IllegalArgumentException("Don't know how to serialize " + _snowman + " as a Component");
            }

            NbtText _snowmanxxxx = (NbtText)_snowman;
            _snowmanxxx.addProperty("nbt", _snowmanxxxx.getPath());
            _snowmanxxx.addProperty("interpret", _snowmanxxxx.shouldInterpret());
            if (_snowman instanceof NbtText.BlockNbtText) {
               NbtText.BlockNbtText _snowmanxxxxx = (NbtText.BlockNbtText)_snowman;
               _snowmanxxx.addProperty("block", _snowmanxxxxx.getPos());
            } else if (_snowman instanceof NbtText.EntityNbtText) {
               NbtText.EntityNbtText _snowmanxxxxx = (NbtText.EntityNbtText)_snowman;
               _snowmanxxx.addProperty("entity", _snowmanxxxxx.getSelector());
            } else {
               if (!(_snowman instanceof NbtText.StorageNbtText)) {
                  throw new IllegalArgumentException("Don't know how to serialize " + _snowman + " as a Component");
               }

               NbtText.StorageNbtText _snowmanxxxxx = (NbtText.StorageNbtText)_snowman;
               _snowmanxxx.addProperty("storage", _snowmanxxxxx.getId().toString());
            }
         }

         return _snowmanxxx;
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
            JsonReader _snowman = new JsonReader(new StringReader(reader.getRemaining()));
            _snowman.setLenient(false);
            MutableText _snowmanx = (MutableText)GSON.getAdapter(MutableText.class).read(_snowman);
            reader.setCursor(reader.getCursor() + getPosition(_snowman));
            return _snowmanx;
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
