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

   private Style(
      @Nullable TextColor color,
      @Nullable Boolean bold,
      @Nullable Boolean italic,
      @Nullable Boolean underlined,
      @Nullable Boolean strikethrough,
      @Nullable Boolean obfuscated,
      @Nullable ClickEvent clickEvent,
      @Nullable HoverEvent hoverEvent,
      @Nullable String insertion,
      @Nullable Identifier font
   ) {
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
      return new Style(
         color, this.bold, this.italic, this.underlined, this.strikethrough, this.obfuscated, this.clickEvent, this.hoverEvent, this.insertion, this.font
      );
   }

   public Style withColor(@Nullable Formatting color) {
      return this.withColor(color != null ? TextColor.fromFormatting(color) : null);
   }

   public Style withBold(@Nullable Boolean bold) {
      return new Style(
         this.color, bold, this.italic, this.underlined, this.strikethrough, this.obfuscated, this.clickEvent, this.hoverEvent, this.insertion, this.font
      );
   }

   public Style withItalic(@Nullable Boolean italic) {
      return new Style(
         this.color, this.bold, italic, this.underlined, this.strikethrough, this.obfuscated, this.clickEvent, this.hoverEvent, this.insertion, this.font
      );
   }

   public Style withUnderline(@Nullable Boolean underline) {
      return new Style(
         this.color, this.bold, this.italic, underline, this.strikethrough, this.obfuscated, this.clickEvent, this.hoverEvent, this.insertion, this.font
      );
   }

   public Style withClickEvent(@Nullable ClickEvent clickEvent) {
      return new Style(
         this.color, this.bold, this.italic, this.underlined, this.strikethrough, this.obfuscated, clickEvent, this.hoverEvent, this.insertion, this.font
      );
   }

   public Style withHoverEvent(@Nullable HoverEvent hoverEvent) {
      return new Style(
         this.color, this.bold, this.italic, this.underlined, this.strikethrough, this.obfuscated, this.clickEvent, hoverEvent, this.insertion, this.font
      );
   }

   public Style withInsertion(@Nullable String insertion) {
      return new Style(
         this.color, this.bold, this.italic, this.underlined, this.strikethrough, this.obfuscated, this.clickEvent, this.hoverEvent, insertion, this.font
      );
   }

   public Style withFont(@Nullable Identifier font) {
      return new Style(
         this.color, this.bold, this.italic, this.underlined, this.strikethrough, this.obfuscated, this.clickEvent, this.hoverEvent, this.insertion, font
      );
   }

   public Style withFormatting(Formatting formatting) {
      TextColor _snowman = this.color;
      Boolean _snowmanx = this.bold;
      Boolean _snowmanxx = this.italic;
      Boolean _snowmanxxx = this.strikethrough;
      Boolean _snowmanxxxx = this.underlined;
      Boolean _snowmanxxxxx = this.obfuscated;
      switch (formatting) {
         case OBFUSCATED:
            _snowmanxxxxx = true;
            break;
         case BOLD:
            _snowmanx = true;
            break;
         case STRIKETHROUGH:
            _snowmanxxx = true;
            break;
         case UNDERLINE:
            _snowmanxxxx = true;
            break;
         case ITALIC:
            _snowmanxx = true;
            break;
         case RESET:
            return EMPTY;
         default:
            _snowman = TextColor.fromFormatting(formatting);
      }

      return new Style(_snowman, _snowmanx, _snowmanxx, _snowmanxxxx, _snowmanxxx, _snowmanxxxxx, this.clickEvent, this.hoverEvent, this.insertion, this.font);
   }

   public Style withExclusiveFormatting(Formatting formatting) {
      TextColor _snowman = this.color;
      Boolean _snowmanx = this.bold;
      Boolean _snowmanxx = this.italic;
      Boolean _snowmanxxx = this.strikethrough;
      Boolean _snowmanxxxx = this.underlined;
      Boolean _snowmanxxxxx = this.obfuscated;
      switch (formatting) {
         case OBFUSCATED:
            _snowmanxxxxx = true;
            break;
         case BOLD:
            _snowmanx = true;
            break;
         case STRIKETHROUGH:
            _snowmanxxx = true;
            break;
         case UNDERLINE:
            _snowmanxxxx = true;
            break;
         case ITALIC:
            _snowmanxx = true;
            break;
         case RESET:
            return EMPTY;
         default:
            _snowmanxxxxx = false;
            _snowmanx = false;
            _snowmanxxx = false;
            _snowmanxxxx = false;
            _snowmanxx = false;
            _snowman = TextColor.fromFormatting(formatting);
      }

      return new Style(_snowman, _snowmanx, _snowmanxx, _snowmanxxxx, _snowmanxxx, _snowmanxxxxx, this.clickEvent, this.hoverEvent, this.insertion, this.font);
   }

   public Style withFormatting(Formatting... formattings) {
      TextColor _snowman = this.color;
      Boolean _snowmanx = this.bold;
      Boolean _snowmanxx = this.italic;
      Boolean _snowmanxxx = this.strikethrough;
      Boolean _snowmanxxxx = this.underlined;
      Boolean _snowmanxxxxx = this.obfuscated;

      for (Formatting _snowmanxxxxxx : formattings) {
         switch (_snowmanxxxxxx) {
            case OBFUSCATED:
               _snowmanxxxxx = true;
               break;
            case BOLD:
               _snowmanx = true;
               break;
            case STRIKETHROUGH:
               _snowmanxxx = true;
               break;
            case UNDERLINE:
               _snowmanxxxx = true;
               break;
            case ITALIC:
               _snowmanxx = true;
               break;
            case RESET:
               return EMPTY;
            default:
               _snowman = TextColor.fromFormatting(_snowmanxxxxxx);
         }
      }

      return new Style(_snowman, _snowmanx, _snowmanxx, _snowmanxxxx, _snowmanxxx, _snowmanxxxxx, this.clickEvent, this.hoverEvent, this.insertion, this.font);
   }

   public Style withParent(Style parent) {
      if (this == EMPTY) {
         return parent;
      } else {
         return parent == EMPTY
            ? this
            : new Style(
               this.color != null ? this.color : parent.color,
               this.bold != null ? this.bold : parent.bold,
               this.italic != null ? this.italic : parent.italic,
               this.underlined != null ? this.underlined : parent.underlined,
               this.strikethrough != null ? this.strikethrough : parent.strikethrough,
               this.obfuscated != null ? this.obfuscated : parent.obfuscated,
               this.clickEvent != null ? this.clickEvent : parent.clickEvent,
               this.hoverEvent != null ? this.hoverEvent : parent.hoverEvent,
               this.insertion != null ? this.insertion : parent.insertion,
               this.font != null ? this.font : parent.font
            );
      }
   }

   @Override
   public String toString() {
      return "Style{ color="
         + this.color
         + ", bold="
         + this.bold
         + ", italic="
         + this.italic
         + ", underlined="
         + this.underlined
         + ", strikethrough="
         + this.strikethrough
         + ", obfuscated="
         + this.obfuscated
         + ", clickEvent="
         + this.getClickEvent()
         + ", hoverEvent="
         + this.getHoverEvent()
         + ", insertion="
         + this.getInsertion()
         + ", font="
         + this.getFont()
         + '}';
   }

   @Override
   public boolean equals(Object obj) {
      if (this == obj) {
         return true;
      } else if (!(obj instanceof Style)) {
         return false;
      } else {
         Style _snowman = (Style)obj;
         return this.isBold() == _snowman.isBold()
            && Objects.equals(this.getColor(), _snowman.getColor())
            && this.isItalic() == _snowman.isItalic()
            && this.isObfuscated() == _snowman.isObfuscated()
            && this.isStrikethrough() == _snowman.isStrikethrough()
            && this.isUnderlined() == _snowman.isUnderlined()
            && Objects.equals(this.getClickEvent(), _snowman.getClickEvent())
            && Objects.equals(this.getHoverEvent(), _snowman.getHoverEvent())
            && Objects.equals(this.getInsertion(), _snowman.getInsertion())
            && Objects.equals(this.getFont(), _snowman.getFont());
      }
   }

   @Override
   public int hashCode() {
      return Objects.hash(
         this.color, this.bold, this.italic, this.underlined, this.strikethrough, this.obfuscated, this.clickEvent, this.hoverEvent, this.insertion
      );
   }

   public static class Serializer implements JsonDeserializer<Style>, JsonSerializer<Style> {
      public Serializer() {
      }

      @Nullable
      public Style deserialize(JsonElement _snowman, Type _snowman, JsonDeserializationContext _snowman) throws JsonParseException {
         if (_snowman.isJsonObject()) {
            JsonObject _snowmanxxx = _snowman.getAsJsonObject();
            if (_snowmanxxx == null) {
               return null;
            } else {
               Boolean _snowmanxxxx = parseNullableBoolean(_snowmanxxx, "bold");
               Boolean _snowmanxxxxx = parseNullableBoolean(_snowmanxxx, "italic");
               Boolean _snowmanxxxxxx = parseNullableBoolean(_snowmanxxx, "underlined");
               Boolean _snowmanxxxxxxx = parseNullableBoolean(_snowmanxxx, "strikethrough");
               Boolean _snowmanxxxxxxxx = parseNullableBoolean(_snowmanxxx, "obfuscated");
               TextColor _snowmanxxxxxxxxx = parseColor(_snowmanxxx);
               String _snowmanxxxxxxxxxx = parseInsertion(_snowmanxxx);
               ClickEvent _snowmanxxxxxxxxxxx = getClickEvent(_snowmanxxx);
               HoverEvent _snowmanxxxxxxxxxxxx = getHoverEvent(_snowmanxxx);
               Identifier _snowmanxxxxxxxxxxxxx = getFont(_snowmanxxx);
               return new Style(_snowmanxxxxxxxxx, _snowmanxxxx, _snowmanxxxxx, _snowmanxxxxxx, _snowmanxxxxxxx, _snowmanxxxxxxxx, _snowmanxxxxxxxxxxx, _snowmanxxxxxxxxxxxx, _snowmanxxxxxxxxxx, _snowmanxxxxxxxxxxxxx);
            }
         } else {
            return null;
         }
      }

      @Nullable
      private static Identifier getFont(JsonObject root) {
         if (root.has("font")) {
            String _snowman = JsonHelper.getString(root, "font");

            try {
               return new Identifier(_snowman);
            } catch (InvalidIdentifierException var3) {
               throw new JsonSyntaxException("Invalid font name: " + _snowman);
            }
         } else {
            return null;
         }
      }

      @Nullable
      private static HoverEvent getHoverEvent(JsonObject root) {
         if (root.has("hoverEvent")) {
            JsonObject _snowman = JsonHelper.getObject(root, "hoverEvent");
            HoverEvent _snowmanx = HoverEvent.fromJson(_snowman);
            if (_snowmanx != null && _snowmanx.getAction().isParsable()) {
               return _snowmanx;
            }
         }

         return null;
      }

      @Nullable
      private static ClickEvent getClickEvent(JsonObject root) {
         if (root.has("clickEvent")) {
            JsonObject _snowman = JsonHelper.getObject(root, "clickEvent");
            String _snowmanx = JsonHelper.getString(_snowman, "action", null);
            ClickEvent.Action _snowmanxx = _snowmanx == null ? null : ClickEvent.Action.byName(_snowmanx);
            String _snowmanxxx = JsonHelper.getString(_snowman, "value", null);
            if (_snowmanxx != null && _snowmanxxx != null && _snowmanxx.isUserDefinable()) {
               return new ClickEvent(_snowmanxx, _snowmanxxx);
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
            String _snowman = JsonHelper.getString(root, "color");
            return TextColor.parse(_snowman);
         } else {
            return null;
         }
      }

      @Nullable
      private static Boolean parseNullableBoolean(JsonObject root, String key) {
         return root.has(key) ? root.get(key).getAsBoolean() : null;
      }

      @Nullable
      public JsonElement serialize(Style _snowman, Type _snowman, JsonSerializationContext _snowman) {
         if (_snowman.isEmpty()) {
            return null;
         } else {
            JsonObject _snowmanxxx = new JsonObject();
            if (_snowman.bold != null) {
               _snowmanxxx.addProperty("bold", _snowman.bold);
            }

            if (_snowman.italic != null) {
               _snowmanxxx.addProperty("italic", _snowman.italic);
            }

            if (_snowman.underlined != null) {
               _snowmanxxx.addProperty("underlined", _snowman.underlined);
            }

            if (_snowman.strikethrough != null) {
               _snowmanxxx.addProperty("strikethrough", _snowman.strikethrough);
            }

            if (_snowman.obfuscated != null) {
               _snowmanxxx.addProperty("obfuscated", _snowman.obfuscated);
            }

            if (_snowman.color != null) {
               _snowmanxxx.addProperty("color", _snowman.color.getName());
            }

            if (_snowman.insertion != null) {
               _snowmanxxx.add("insertion", _snowman.serialize(_snowman.insertion));
            }

            if (_snowman.clickEvent != null) {
               JsonObject _snowmanxxxx = new JsonObject();
               _snowmanxxxx.addProperty("action", _snowman.clickEvent.getAction().getName());
               _snowmanxxxx.addProperty("value", _snowman.clickEvent.getValue());
               _snowmanxxx.add("clickEvent", _snowmanxxxx);
            }

            if (_snowman.hoverEvent != null) {
               _snowmanxxx.add("hoverEvent", _snowman.hoverEvent.toJson());
            }

            if (_snowman.font != null) {
               _snowmanxxx.addProperty("font", _snowman.font.toString());
            }

            return _snowmanxxx;
         }
      }
   }
}
