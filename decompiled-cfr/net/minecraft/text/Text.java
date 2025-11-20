/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.gson.Gson
 *  com.google.gson.GsonBuilder
 *  com.google.gson.JsonArray
 *  com.google.gson.JsonDeserializationContext
 *  com.google.gson.JsonDeserializer
 *  com.google.gson.JsonElement
 *  com.google.gson.JsonObject
 *  com.google.gson.JsonParseException
 *  com.google.gson.JsonPrimitive
 *  com.google.gson.JsonSerializationContext
 *  com.google.gson.JsonSerializer
 *  com.google.gson.TypeAdapterFactory
 *  com.google.gson.stream.JsonReader
 *  com.mojang.brigadier.Message
 *  com.mojang.brigadier.StringReader
 *  javax.annotation.Nullable
 */
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
import com.google.gson.TypeAdapterFactory;
import com.google.gson.stream.JsonReader;
import com.mojang.brigadier.Message;
import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;
import java.lang.reflect.Field;
import java.lang.reflect.Type;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import javax.annotation.Nullable;
import net.minecraft.text.KeybindText;
import net.minecraft.text.LiteralText;
import net.minecraft.text.MutableText;
import net.minecraft.text.NbtText;
import net.minecraft.text.OrderedText;
import net.minecraft.text.ScoreText;
import net.minecraft.text.SelectorText;
import net.minecraft.text.StringVisitable;
import net.minecraft.text.Style;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.Identifier;
import net.minecraft.util.JsonHelper;
import net.minecraft.util.LowercaseEnumTypeAdapterFactory;
import net.minecraft.util.Util;

public interface Text
extends Message,
StringVisitable {
    public Style getStyle();

    public String asString();

    @Override
    default public String getString() {
        return StringVisitable.super.getString();
    }

    default public String asTruncatedString(int length) {
        StringBuilder stringBuilder = new StringBuilder();
        this.visit(string -> {
            int n2 = length - stringBuilder.length();
            if (n2 <= 0) {
                return TERMINATE_VISIT;
            }
            stringBuilder.append(string.length() <= n2 ? string : string.substring(0, n2));
            return Optional.empty();
        });
        return stringBuilder.toString();
    }

    public List<Text> getSiblings();

    public MutableText copy();

    public MutableText shallowCopy();

    public OrderedText asOrderedText();

    @Override
    default public <T> Optional<T> visit(StringVisitable.StyledVisitor<T> styledVisitor, Style style) {
        Style style2 = this.getStyle().withParent(style);
        Optional<T> _snowman2 = this.visitSelf(styledVisitor, style2);
        if (_snowman2.isPresent()) {
            return _snowman2;
        }
        for (Text text : this.getSiblings()) {
            Optional<T> optional = text.visit(styledVisitor, style2);
            if (!optional.isPresent()) continue;
            return optional;
        }
        return Optional.empty();
    }

    @Override
    default public <T> Optional<T> visit(StringVisitable.Visitor<T> visitor) {
        Optional<T> optional = this.visitSelf(visitor);
        if (optional.isPresent()) {
            return optional;
        }
        for (Text text : this.getSiblings()) {
            Optional<T> optional2 = text.visit(visitor);
            if (!optional2.isPresent()) continue;
            return optional2;
        }
        return Optional.empty();
    }

    default public <T> Optional<T> visitSelf(StringVisitable.StyledVisitor<T> visitor, Style style) {
        return visitor.accept(style, this.asString());
    }

    default public <T> Optional<T> visitSelf(StringVisitable.Visitor<T> visitor) {
        return visitor.accept(this.asString());
    }

    public static Text of(@Nullable String string) {
        return string != null ? new LiteralText(string) : LiteralText.EMPTY;
    }

    public static class Serializer
    implements JsonDeserializer<MutableText>,
    JsonSerializer<Text> {
        private static final Gson GSON = Util.make(() -> {
            GsonBuilder gsonBuilder = new GsonBuilder();
            gsonBuilder.disableHtmlEscaping();
            gsonBuilder.registerTypeHierarchyAdapter(Text.class, (Object)new Serializer());
            gsonBuilder.registerTypeHierarchyAdapter(Style.class, (Object)new Style.Serializer());
            gsonBuilder.registerTypeAdapterFactory((TypeAdapterFactory)new LowercaseEnumTypeAdapterFactory());
            return gsonBuilder.create();
        });
        private static final Field JSON_READER_POS = Util.make(() -> {
            try {
                new JsonReader((Reader)new StringReader(""));
                Field field = JsonReader.class.getDeclaredField("pos");
                field.setAccessible(true);
                return field;
            }
            catch (NoSuchFieldException noSuchFieldException) {
                throw new IllegalStateException("Couldn't get field 'pos' for JsonReader", noSuchFieldException);
            }
        });
        private static final Field JSON_READER_LINE_START = Util.make(() -> {
            try {
                new JsonReader((Reader)new StringReader(""));
                Field field = JsonReader.class.getDeclaredField("lineStart");
                field.setAccessible(true);
                return field;
            }
            catch (NoSuchFieldException noSuchFieldException) {
                throw new IllegalStateException("Couldn't get field 'lineStart' for JsonReader", noSuchFieldException);
            }
        });

        /*
         * WARNING - void declaration
         * Enabled force condition propagation
         * Lifted jumps to return sites
         */
        public MutableText deserialize(JsonElement jsonElement2, Type type, JsonDeserializationContext jsonDeserializationContext2) throws JsonParseException {
            void var5_19;
            JsonElement jsonElement2;
            if (jsonElement2.isJsonPrimitive()) {
                return new LiteralText(jsonElement2.getAsString());
            }
            if (jsonElement2.isJsonObject()) {
                JsonDeserializationContext jsonDeserializationContext2;
                void var5_17;
                String string;
                JsonObject jsonObject = jsonElement2.getAsJsonObject();
                if (jsonObject.has("text")) {
                    LiteralText literalText = new LiteralText(JsonHelper.getString(jsonObject, "text"));
                } else if (jsonObject.has("translate")) {
                    string = JsonHelper.getString(jsonObject, "translate");
                    if (jsonObject.has("with")) {
                        JsonArray jsonArray = JsonHelper.getArray(jsonObject, "with");
                        Object[] _snowman2 = new Object[jsonArray.size()];
                        for (int i = 0; i < _snowman2.length; ++i) {
                            _snowman2[i] = this.deserialize(jsonArray.get(i), type, jsonDeserializationContext2);
                            if (!(_snowman2[i] instanceof LiteralText) || !(_snowman = (LiteralText)_snowman2[i]).getStyle().isEmpty() || !_snowman.getSiblings().isEmpty()) continue;
                            _snowman2[i] = _snowman.getRawString();
                        }
                        TranslatableText translatableText = new TranslatableText(string, _snowman2);
                    } else {
                        TranslatableText translatableText = new TranslatableText(string);
                    }
                } else if (jsonObject.has("score")) {
                    string = JsonHelper.getObject(jsonObject, "score");
                    if (!string.has("name") || !string.has("objective")) throw new JsonParseException("A score component needs a least a name and an objective");
                    ScoreText scoreText = new ScoreText(JsonHelper.getString((JsonObject)string, "name"), JsonHelper.getString((JsonObject)string, "objective"));
                } else if (jsonObject.has("selector")) {
                    SelectorText selectorText = new SelectorText(JsonHelper.getString(jsonObject, "selector"));
                } else if (jsonObject.has("keybind")) {
                    KeybindText keybindText = new KeybindText(JsonHelper.getString(jsonObject, "keybind"));
                } else {
                    if (!jsonObject.has("nbt")) throw new JsonParseException("Don't know how to turn " + jsonElement2 + " into a Component");
                    string = JsonHelper.getString(jsonObject, "nbt");
                    boolean _snowman3 = JsonHelper.getBoolean(jsonObject, "interpret", false);
                    if (jsonObject.has("block")) {
                        NbtText.BlockNbtText blockNbtText = new NbtText.BlockNbtText(string, _snowman3, JsonHelper.getString(jsonObject, "block"));
                    } else if (jsonObject.has("entity")) {
                        NbtText.EntityNbtText entityNbtText = new NbtText.EntityNbtText(string, _snowman3, JsonHelper.getString(jsonObject, "entity"));
                    } else {
                        if (!jsonObject.has("storage")) throw new JsonParseException("Don't know how to turn " + jsonElement2 + " into a Component");
                        NbtText.StorageNbtText storageNbtText = new NbtText.StorageNbtText(string, _snowman3, new Identifier(JsonHelper.getString(jsonObject, "storage")));
                    }
                }
                if (jsonObject.has("extra")) {
                    string = JsonHelper.getArray(jsonObject, "extra");
                    if (string.size() <= 0) throw new JsonParseException("Unexpected empty array of components");
                    for (int i = 0; i < string.size(); ++i) {
                        var5_17.append(this.deserialize(string.get(i), type, jsonDeserializationContext2));
                    }
                }
                var5_17.setStyle((Style)jsonDeserializationContext2.deserialize(jsonElement2, Style.class));
                return var5_17;
            }
            if (!jsonElement2.isJsonArray()) throw new JsonParseException("Don't know how to turn " + jsonElement2 + " into a Component");
            JsonArray _snowman4 = jsonElement2.getAsJsonArray();
            Object var5_18 = null;
            for (JsonElement jsonElement3 : _snowman4) {
                MutableText mutableText = this.deserialize(jsonElement3, jsonElement3.getClass(), jsonDeserializationContext2);
                if (var5_19 == null) {
                    MutableText mutableText2 = mutableText;
                    continue;
                }
                var5_19.append(mutableText);
            }
            return var5_19;
        }

        private void addStyle(Style style, JsonObject json, JsonSerializationContext context) {
            JsonElement jsonElement = context.serialize((Object)style);
            if (jsonElement.isJsonObject()) {
                JsonObject jsonObject = (JsonObject)jsonElement;
                for (Map.Entry entry : jsonObject.entrySet()) {
                    json.add((String)entry.getKey(), (JsonElement)entry.getValue());
                }
            }
        }

        /*
         * Enabled force condition propagation
         * Lifted jumps to return sites
         */
        public JsonElement serialize(Text text2, Type type, JsonSerializationContext jsonSerializationContext) {
            Text text2;
            Object _snowman2;
            JsonObject jsonObject = new JsonObject();
            if (!text2.getStyle().isEmpty()) {
                this.addStyle(text2.getStyle(), jsonObject, jsonSerializationContext);
            }
            if (!text2.getSiblings().isEmpty()) {
                _snowman2 = new JsonArray();
                for (Text text3 : text2.getSiblings()) {
                    _snowman2.add(this.serialize(text3, text3.getClass(), jsonSerializationContext));
                }
                jsonObject.add("extra", (JsonElement)_snowman2);
            }
            if (text2 instanceof LiteralText) {
                jsonObject.addProperty("text", ((LiteralText)text2).getRawString());
                return jsonObject;
            } else if (text2 instanceof TranslatableText) {
                _snowman2 = (TranslatableText)text2;
                jsonObject.addProperty("translate", ((TranslatableText)_snowman2).getKey());
                if (((TranslatableText)_snowman2).getArgs() == null || ((TranslatableText)_snowman2).getArgs().length <= 0) return jsonObject;
                _snowman3 = new JsonArray();
                for (Object object : ((TranslatableText)_snowman2).getArgs()) {
                    if (object instanceof Text) {
                        _snowman3.add(this.serialize((Text)object, object.getClass(), jsonSerializationContext));
                        continue;
                    }
                    _snowman3.add((JsonElement)new JsonPrimitive(String.valueOf(object)));
                }
                jsonObject.add("with", (JsonElement)_snowman3);
                return jsonObject;
            } else if (text2 instanceof ScoreText) {
                _snowman2 = (ScoreText)text2;
                _snowman3 = new JsonObject();
                _snowman3.addProperty("name", ((ScoreText)_snowman2).getName());
                _snowman3.addProperty("objective", ((ScoreText)_snowman2).getObjective());
                jsonObject.add("score", (JsonElement)_snowman3);
                return jsonObject;
            } else if (text2 instanceof SelectorText) {
                _snowman2 = (SelectorText)text2;
                jsonObject.addProperty("selector", ((SelectorText)_snowman2).getPattern());
                return jsonObject;
            } else if (text2 instanceof KeybindText) {
                _snowman2 = (KeybindText)text2;
                jsonObject.addProperty("keybind", ((KeybindText)_snowman2).getKey());
                return jsonObject;
            } else {
                Object _snowman3;
                if (!(text2 instanceof NbtText)) throw new IllegalArgumentException("Don't know how to serialize " + text2 + " as a Component");
                _snowman2 = (NbtText)text2;
                jsonObject.addProperty("nbt", ((NbtText)_snowman2).getPath());
                jsonObject.addProperty("interpret", Boolean.valueOf(((NbtText)_snowman2).shouldInterpret()));
                if (text2 instanceof NbtText.BlockNbtText) {
                    _snowman3 = (NbtText.BlockNbtText)text2;
                    jsonObject.addProperty("block", ((NbtText.BlockNbtText)_snowman3).getPos());
                    return jsonObject;
                } else if (text2 instanceof NbtText.EntityNbtText) {
                    _snowman3 = (NbtText.EntityNbtText)text2;
                    jsonObject.addProperty("entity", ((NbtText.EntityNbtText)_snowman3).getSelector());
                    return jsonObject;
                } else {
                    if (!(text2 instanceof NbtText.StorageNbtText)) throw new IllegalArgumentException("Don't know how to serialize " + text2 + " as a Component");
                    _snowman3 = (NbtText.StorageNbtText)text2;
                    jsonObject.addProperty("storage", ((NbtText.StorageNbtText)_snowman3).getId().toString());
                }
            }
            return jsonObject;
        }

        public static String toJson(Text text) {
            return GSON.toJson((Object)text);
        }

        public static JsonElement toJsonTree(Text text) {
            return GSON.toJsonTree((Object)text);
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
                JsonReader jsonReader = new JsonReader((Reader)new StringReader(reader.getRemaining()));
                jsonReader.setLenient(false);
                MutableText _snowman2 = (MutableText)GSON.getAdapter(MutableText.class).read(jsonReader);
                reader.setCursor(reader.getCursor() + Serializer.getPosition(jsonReader));
                return _snowman2;
            }
            catch (IOException | StackOverflowError throwable) {
                throw new JsonParseException(throwable);
            }
        }

        private static int getPosition(JsonReader reader) {
            try {
                return JSON_READER_POS.getInt(reader) - JSON_READER_LINE_START.getInt(reader) + 1;
            }
            catch (IllegalAccessException illegalAccessException) {
                throw new IllegalStateException("Couldn't read position of JsonReader", illegalAccessException);
            }
        }

        public /* synthetic */ JsonElement serialize(Object text, Type type, JsonSerializationContext context) {
            return this.serialize((Text)text, type, context);
        }

        public /* synthetic */ Object deserialize(JsonElement json, Type type, JsonDeserializationContext context) throws JsonParseException {
            return this.deserialize(json, type, context);
        }
    }
}

