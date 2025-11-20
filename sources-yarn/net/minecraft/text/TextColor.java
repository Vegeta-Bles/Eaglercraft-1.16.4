package net.minecraft.text;

import com.google.common.collect.ImmutableMap;
import java.util.Map;
import java.util.Objects;
import java.util.function.Function;
import java.util.stream.Stream;
import javax.annotation.Nullable;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.util.Formatting;

public final class TextColor {
   private static final Map<Formatting, TextColor> FORMATTING_TO_COLOR = Stream.of(Formatting.values())
      .filter(Formatting::isColor)
      .collect(ImmutableMap.toImmutableMap(Function.identity(), arg -> new TextColor(arg.getColorValue(), arg.getName())));
   private static final Map<String, TextColor> BY_NAME = FORMATTING_TO_COLOR.values()
      .stream()
      .collect(ImmutableMap.toImmutableMap(arg -> arg.name, Function.identity()));
   private final int rgb;
   @Nullable
   private final String name;

   private TextColor(int rgb, String name) {
      this.rgb = rgb;
      this.name = name;
   }

   private TextColor(int rgb) {
      this.rgb = rgb;
      this.name = null;
   }

   @Environment(EnvType.CLIENT)
   public int getRgb() {
      return this.rgb;
   }

   public String getName() {
      return this.name != null ? this.name : this.getHexCode();
   }

   private String getHexCode() {
      return String.format("#%06X", this.rgb);
   }

   @Override
   public boolean equals(Object object) {
      if (this == object) {
         return true;
      } else if (object != null && this.getClass() == object.getClass()) {
         TextColor lv = (TextColor)object;
         return this.rgb == lv.rgb;
      } else {
         return false;
      }
   }

   @Override
   public int hashCode() {
      return Objects.hash(this.rgb, this.name);
   }

   @Override
   public String toString() {
      return this.name != null ? this.name : this.getHexCode();
   }

   @Nullable
   public static TextColor fromFormatting(Formatting formatting) {
      return FORMATTING_TO_COLOR.get(formatting);
   }

   public static TextColor fromRgb(int rgb) {
      return new TextColor(rgb);
   }

   @Nullable
   public static TextColor parse(String name) {
      if (name.startsWith("#")) {
         try {
            int i = Integer.parseInt(name.substring(1), 16);
            return fromRgb(i);
         } catch (NumberFormatException var2) {
            return null;
         }
      } else {
         return BY_NAME.get(name);
      }
   }
}
