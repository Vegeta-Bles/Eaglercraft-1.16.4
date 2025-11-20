package net.minecraft.client.resource.language;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.Lists;
import it.unimi.dsi.fastutil.ints.Int2IntFunction;
import java.util.List;
import java.util.Optional;
import java.util.function.UnaryOperator;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.font.TextVisitFactory;
import net.minecraft.text.OrderedText;
import net.minecraft.text.StringVisitable;
import net.minecraft.text.Style;

@Environment(EnvType.CLIENT)
public class TextReorderingProcessor {
   private final String string;
   private final List<Style> styles;
   private final Int2IntFunction reverser;

   private TextReorderingProcessor(String string, List<Style> styles, Int2IntFunction reverser) {
      this.string = string;
      this.styles = ImmutableList.copyOf(styles);
      this.reverser = reverser;
   }

   public String getString() {
      return this.string;
   }

   public List<OrderedText> process(int start, int length, boolean reverse) {
      if (length == 0) {
         return ImmutableList.of();
      } else {
         List<OrderedText> list = Lists.newArrayList();
         Style lv = this.styles.get(start);
         int k = start;

         for (int l = 1; l < length; l++) {
            int m = start + l;
            Style lv2 = this.styles.get(m);
            if (!lv2.equals(lv)) {
               String string = this.string.substring(k, m);
               list.add(reverse ? OrderedText.styledStringMapped(string, lv, this.reverser) : OrderedText.styledString(string, lv));
               lv = lv2;
               k = m;
            }
         }

         if (k < start + length) {
            String string2 = this.string.substring(k, start + length);
            list.add(reverse ? OrderedText.styledStringMapped(string2, lv, this.reverser) : OrderedText.styledString(string2, lv));
         }

         return reverse ? Lists.reverse(list) : list;
      }
   }

   public static TextReorderingProcessor create(StringVisitable visitable, Int2IntFunction reverser, UnaryOperator<String> unaryOperator) {
      StringBuilder stringBuilder = new StringBuilder();
      List<Style> list = Lists.newArrayList();
      visitable.visit((style, text) -> {
         TextVisitFactory.visitFormatted(text, style, (charIndex, stylex, codePoint) -> {
            stringBuilder.appendCodePoint(codePoint);
            int k = Character.charCount(codePoint);

            for (int l = 0; l < k; l++) {
               list.add(stylex);
            }

            return true;
         });
         return Optional.empty();
      }, Style.EMPTY);
      return new TextReorderingProcessor(unaryOperator.apply(stringBuilder.toString()), list, reverser);
   }
}
