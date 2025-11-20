/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.gson.JsonDeserializationContext
 *  com.google.gson.JsonDeserializer
 *  com.google.gson.JsonElement
 *  com.google.gson.JsonObject
 *  com.google.gson.JsonParseException
 *  com.google.gson.JsonSerializationContext
 *  com.google.gson.JsonSerializer
 *  com.google.gson.JsonSyntaxException
 *  javax.annotation.Nullable
 */
package net.minecraft.text;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import com.google.gson.JsonSyntaxException;
import java.lang.reflect.Type;
import java.util.Objects;
import javax.annotation.Nullable;
import net.minecraft.text.ClickEvent;
import net.minecraft.text.HoverEvent;
import net.minecraft.text.TextColor;
import net.minecraft.util.Formatting;
import net.minecraft.util.Identifier;
import net.minecraft.util.InvalidIdentifierException;
import net.minecraft.util.JsonHelper;

public class Style {
    public static final Style EMPTY = new Style(null, null, null, null, null, null, null, null, null, null);
    public static final Identifier DEFAULT_FONT_ID = new Identifier("minecraft", "default");
    @Nullable
    private final TextColor color;
    @Nullable
    private final Boolean bold;
    @Nullable
    private final Boolean italic;
    @Nullable
    private final Boolean underlined;
    @Nullable
    private final Boolean strikethrough;
    @Nullable
    private final Boolean obfuscated;
    @Nullable
    private final ClickEvent clickEvent;
    @Nullable
    private final HoverEvent hoverEvent;
    @Nullable
    private final String insertion;
    @Nullable
    private final Identifier font;

    private Style(@Nullable TextColor color, @Nullable Boolean bold, @Nullable Boolean italic, @Nullable Boolean underlined, @Nullable Boolean strikethrough, @Nullable Boolean obfuscated, @Nullable ClickEvent clickEvent, @Nullable HoverEvent hoverEvent, @Nullable String insertion, @Nullable Identifier font) {
        this.color = color;
        this.bold = bold;
        this.italic = italic;
        this.underlined = underlined;
        this.strikethrough = strikethrough;
        this.obfuscated = obfuscated;
        this.clickEvent = clickEvent;
        this.hoverEvent = hoverEvent;
        this.insertion = insertion;
        this.font = font;
    }

    @Nullable
    public TextColor getColor() {
        return this.color;
    }

    public boolean isBold() {
        return this.bold == Boolean.TRUE;
    }

    public boolean isItalic() {
        return this.italic == Boolean.TRUE;
    }

    public boolean isStrikethrough() {
        return this.strikethrough == Boolean.TRUE;
    }

    public boolean isUnderlined() {
        return this.underlined == Boolean.TRUE;
    }

    public boolean isObfuscated() {
        return this.obfuscated == Boolean.TRUE;
    }

    public boolean isEmpty() {
        return this == EMPTY;
    }

    @Nullable
    public ClickEvent getClickEvent() {
        return this.clickEvent;
    }

    @Nullable
    public HoverEvent getHoverEvent() {
        return this.hoverEvent;
    }

    @Nullable
    public String getInsertion() {
        return this.insertion;
    }

    public Identifier getFont() {
        return this.font != null ? this.font : DEFAULT_FONT_ID;
    }

    public Style withColor(@Nullable TextColor color) {
        return new Style(color, this.bold, this.italic, this.underlined, this.strikethrough, this.obfuscated, this.clickEvent, this.hoverEvent, this.insertion, this.font);
    }

    public Style withColor(@Nullable Formatting color) {
        return this.withColor(color != null ? TextColor.fromFormatting(color) : null);
    }

    public Style withBold(@Nullable Boolean bold) {
        return new Style(this.color, bold, this.italic, this.underlined, this.strikethrough, this.obfuscated, this.clickEvent, this.hoverEvent, this.insertion, this.font);
    }

    public Style withItalic(@Nullable Boolean italic) {
        return new Style(this.color, this.bold, italic, this.underlined, this.strikethrough, this.obfuscated, this.clickEvent, this.hoverEvent, this.insertion, this.font);
    }

    public Style withUnderline(@Nullable Boolean underline) {
        return new Style(this.color, this.bold, this.italic, underline, this.strikethrough, this.obfuscated, this.clickEvent, this.hoverEvent, this.insertion, this.font);
    }

    public Style withClickEvent(@Nullable ClickEvent clickEvent) {
        return new Style(this.color, this.bold, this.italic, this.underlined, this.strikethrough, this.obfuscated, clickEvent, this.hoverEvent, this.insertion, this.font);
    }

    public Style withHoverEvent(@Nullable HoverEvent hoverEvent) {
        return new Style(this.color, this.bold, this.italic, this.underlined, this.strikethrough, this.obfuscated, this.clickEvent, hoverEvent, this.insertion, this.font);
    }

    public Style withInsertion(@Nullable String insertion) {
        return new Style(this.color, this.bold, this.italic, this.underlined, this.strikethrough, this.obfuscated, this.clickEvent, this.hoverEvent, insertion, this.font);
    }

    public Style withFont(@Nullable Identifier font) {
        return new Style(this.color, this.bold, this.italic, this.underlined, this.strikethrough, this.obfuscated, this.clickEvent, this.hoverEvent, this.insertion, font);
    }

    public Style withFormatting(Formatting formatting) {
        TextColor textColor = this.color;
        Boolean _snowman2 = this.bold;
        Boolean _snowman3 = this.italic;
        Boolean _snowman4 = this.strikethrough;
        Boolean _snowman5 = this.underlined;
        Boolean _snowman6 = this.obfuscated;
        switch (formatting) {
            case OBFUSCATED: {
                _snowman6 = true;
                break;
            }
            case BOLD: {
                _snowman2 = true;
                break;
            }
            case STRIKETHROUGH: {
                _snowman4 = true;
                break;
            }
            case UNDERLINE: {
                _snowman5 = true;
                break;
            }
            case ITALIC: {
                _snowman3 = true;
                break;
            }
            case RESET: {
                return EMPTY;
            }
            default: {
                textColor = TextColor.fromFormatting(formatting);
            }
        }
        return new Style(textColor, _snowman2, _snowman3, _snowman5, _snowman4, _snowman6, this.clickEvent, this.hoverEvent, this.insertion, this.font);
    }

    public Style withExclusiveFormatting(Formatting formatting) {
        TextColor textColor = this.color;
        Boolean _snowman2 = this.bold;
        Boolean _snowman3 = this.italic;
        Boolean _snowman4 = this.strikethrough;
        Boolean _snowman5 = this.underlined;
        Boolean _snowman6 = this.obfuscated;
        switch (formatting) {
            case OBFUSCATED: {
                _snowman6 = true;
                break;
            }
            case BOLD: {
                _snowman2 = true;
                break;
            }
            case STRIKETHROUGH: {
                _snowman4 = true;
                break;
            }
            case UNDERLINE: {
                _snowman5 = true;
                break;
            }
            case ITALIC: {
                _snowman3 = true;
                break;
            }
            case RESET: {
                return EMPTY;
            }
            default: {
                _snowman6 = false;
                _snowman2 = false;
                _snowman4 = false;
                _snowman5 = false;
                _snowman3 = false;
                textColor = TextColor.fromFormatting(formatting);
            }
        }
        return new Style(textColor, _snowman2, _snowman3, _snowman5, _snowman4, _snowman6, this.clickEvent, this.hoverEvent, this.insertion, this.font);
    }

    public Style withFormatting(Formatting ... formattings) {
        TextColor textColor = this.color;
        Boolean _snowman2 = this.bold;
        Boolean _snowman3 = this.italic;
        Boolean _snowman4 = this.strikethrough;
        Boolean _snowman5 = this.underlined;
        Boolean _snowman6 = this.obfuscated;
        block8: for (Formatting formatting : formattings) {
            switch (formatting) {
                case OBFUSCATED: {
                    _snowman6 = true;
                    continue block8;
                }
                case BOLD: {
                    _snowman2 = true;
                    continue block8;
                }
                case STRIKETHROUGH: {
                    _snowman4 = true;
                    continue block8;
                }
                case UNDERLINE: {
                    _snowman5 = true;
                    continue block8;
                }
                case ITALIC: {
                    _snowman3 = true;
                    continue block8;
                }
                case RESET: {
                    return EMPTY;
                }
                default: {
                    textColor = TextColor.fromFormatting(formatting);
                }
            }
        }
        return new Style(textColor, _snowman2, _snowman3, _snowman5, _snowman4, _snowman6, this.clickEvent, this.hoverEvent, this.insertion, this.font);
    }

    public Style withParent(Style parent) {
        if (this == EMPTY) {
            return parent;
        }
        if (parent == EMPTY) {
            return this;
        }
        return new Style(this.color != null ? this.color : parent.color, this.bold != null ? this.bold : parent.bold, this.italic != null ? this.italic : parent.italic, this.underlined != null ? this.underlined : parent.underlined, this.strikethrough != null ? this.strikethrough : parent.strikethrough, this.obfuscated != null ? this.obfuscated : parent.obfuscated, this.clickEvent != null ? this.clickEvent : parent.clickEvent, this.hoverEvent != null ? this.hoverEvent : parent.hoverEvent, this.insertion != null ? this.insertion : parent.insertion, this.font != null ? this.font : parent.font);
    }

    public String toString() {
        return "Style{ color=" + this.color + ", bold=" + this.bold + ", italic=" + this.italic + ", underlined=" + this.underlined + ", strikethrough=" + this.strikethrough + ", obfuscated=" + this.obfuscated + ", clickEvent=" + this.getClickEvent() + ", hoverEvent=" + this.getHoverEvent() + ", insertion=" + this.getInsertion() + ", font=" + this.getFont() + '}';
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj instanceof Style) {
            Style style = (Style)obj;
            return this.isBold() == style.isBold() && Objects.equals(this.getColor(), style.getColor()) && this.isItalic() == style.isItalic() && this.isObfuscated() == style.isObfuscated() && this.isStrikethrough() == style.isStrikethrough() && this.isUnderlined() == style.isUnderlined() && Objects.equals(this.getClickEvent(), style.getClickEvent()) && Objects.equals(this.getHoverEvent(), style.getHoverEvent()) && Objects.equals(this.getInsertion(), style.getInsertion()) && Objects.equals(this.getFont(), style.getFont());
        }
        return false;
    }

    public int hashCode() {
        return Objects.hash(this.color, this.bold, this.italic, this.underlined, this.strikethrough, this.obfuscated, this.clickEvent, this.hoverEvent, this.insertion);
    }

    public static class Serializer
    implements JsonDeserializer<Style>,
    JsonSerializer<Style> {
        @Nullable
        public Style deserialize(JsonElement jsonElement, Type type, JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {
            if (jsonElement.isJsonObject()) {
                JsonObject jsonObject = jsonElement.getAsJsonObject();
                if (jsonObject == null) {
                    return null;
                }
                Boolean _snowman2 = Serializer.parseNullableBoolean(jsonObject, "bold");
                Boolean _snowman3 = Serializer.parseNullableBoolean(jsonObject, "italic");
                Boolean _snowman4 = Serializer.parseNullableBoolean(jsonObject, "underlined");
                Boolean _snowman5 = Serializer.parseNullableBoolean(jsonObject, "strikethrough");
                Boolean _snowman6 = Serializer.parseNullableBoolean(jsonObject, "obfuscated");
                TextColor _snowman7 = Serializer.parseColor(jsonObject);
                String _snowman8 = Serializer.parseInsertion(jsonObject);
                ClickEvent _snowman9 = Serializer.getClickEvent(jsonObject);
                HoverEvent _snowman10 = Serializer.getHoverEvent(jsonObject);
                Identifier _snowman11 = Serializer.getFont(jsonObject);
                return new Style(_snowman7, _snowman2, _snowman3, _snowman4, _snowman5, _snowman6, _snowman9, _snowman10, _snowman8, _snowman11);
            }
            return null;
        }

        @Nullable
        private static Identifier getFont(JsonObject root) {
            if (root.has("font")) {
                String string = JsonHelper.getString(root, "font");
                try {
                    return new Identifier(string);
                }
                catch (InvalidIdentifierException _snowman2) {
                    throw new JsonSyntaxException("Invalid font name: " + string);
                }
            }
            return null;
        }

        @Nullable
        private static HoverEvent getHoverEvent(JsonObject root) {
            HoverEvent hoverEvent;
            if (root.has("hoverEvent") && (hoverEvent = HoverEvent.fromJson(_snowman = JsonHelper.getObject(root, "hoverEvent"))) != null && hoverEvent.getAction().isParsable()) {
                return hoverEvent;
            }
            return null;
        }

        @Nullable
        private static ClickEvent getClickEvent(JsonObject root) {
            if (root.has("clickEvent")) {
                JsonObject jsonObject = JsonHelper.getObject(root, "clickEvent");
                String _snowman2 = JsonHelper.getString(jsonObject, "action", null);
                ClickEvent.Action _snowman3 = _snowman2 == null ? null : ClickEvent.Action.byName(_snowman2);
                String _snowman4 = JsonHelper.getString(jsonObject, "value", null);
                if (_snowman3 != null && _snowman4 != null && _snowman3.isUserDefinable()) {
                    return new ClickEvent(_snowman3, _snowman4);
                }
            }
            return null;
        }

        @Nullable
        private static String parseInsertion(JsonObject root) {
            return JsonHelper.getString(root, "insertion", null);
        }

        @Nullable
        private static TextColor parseColor(JsonObject root) {
            if (root.has("color")) {
                String string = JsonHelper.getString(root, "color");
                return TextColor.parse(string);
            }
            return null;
        }

        @Nullable
        private static Boolean parseNullableBoolean(JsonObject root, String key) {
            if (root.has(key)) {
                return root.get(key).getAsBoolean();
            }
            return null;
        }

        @Nullable
        public JsonElement serialize(Style style, Type type, JsonSerializationContext jsonSerializationContext) {
            if (style.isEmpty()) {
                return null;
            }
            JsonObject jsonObject = new JsonObject();
            if (style.bold != null) {
                jsonObject.addProperty("bold", style.bold);
            }
            if (style.italic != null) {
                jsonObject.addProperty("italic", style.italic);
            }
            if (style.underlined != null) {
                jsonObject.addProperty("underlined", style.underlined);
            }
            if (style.strikethrough != null) {
                jsonObject.addProperty("strikethrough", style.strikethrough);
            }
            if (style.obfuscated != null) {
                jsonObject.addProperty("obfuscated", style.obfuscated);
            }
            if (style.color != null) {
                jsonObject.addProperty("color", style.color.getName());
            }
            if (style.insertion != null) {
                jsonObject.add("insertion", jsonSerializationContext.serialize((Object)style.insertion));
            }
            if (style.clickEvent != null) {
                _snowman = new JsonObject();
                _snowman.addProperty("action", style.clickEvent.getAction().getName());
                _snowman.addProperty("value", style.clickEvent.getValue());
                jsonObject.add("clickEvent", (JsonElement)_snowman);
            }
            if (style.hoverEvent != null) {
                jsonObject.add("hoverEvent", (JsonElement)style.hoverEvent.toJson());
            }
            if (style.font != null) {
                jsonObject.addProperty("font", style.font.toString());
            }
            return jsonObject;
        }

        @Nullable
        public /* synthetic */ JsonElement serialize(Object style, Type type, JsonSerializationContext context) {
            return this.serialize((Style)style, type, context);
        }

        @Nullable
        public /* synthetic */ Object deserialize(JsonElement json, Type type, JsonDeserializationContext context) throws JsonParseException {
            return this.deserialize(json, type, context);
        }
    }
}

