package net.minecraft.client.resource.language;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.Lists;
import it.unimi.dsi.fastutil.ints.Int2IntFunction;
import java.util.List;
import java.util.Optional;
import java.util.function.UnaryOperator;
import net.minecraft.client.font.TextVisitFactory;
import net.minecraft.text.OrderedText;
import net.minecraft.text.StringVisitable;
import net.minecraft.text.Style;

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
         List<OrderedText> _snowman = Lists.newArrayList();
         Style _snowmanx = this.styles.get(start);
         int _snowmanxx = start;

         for (int _snowmanxxx = 1; _snowmanxxx < length; _snowmanxxx++) {
            int _snowmanxxxx = start + _snowmanxxx;
            Style _snowmanxxxxx = this.styles.get(_snowmanxxxx);
            if (!_snowmanxxxxx.equals(_snowmanx)) {
               String _snowmanxxxxxx = this.string.substring(_snowmanxx, _snowmanxxxx);
               _snowman.add(reverse ? OrderedText.styledStringMapped(_snowmanxxxxxx, _snowmanx, this.reverser) : OrderedText.styledString(_snowmanxxxxxx, _snowmanx));
               _snowmanx = _snowmanxxxxx;
               _snowmanxx = _snowmanxxxx;
            }
         }

         if (_snowmanxx < start + length) {
            String _snowmanxxxx = this.string.substring(_snowmanxx, start + length);
            _snowman.add(reverse ? OrderedText.styledStringMapped(_snowmanxxxx, _snowmanx, this.reverser) : OrderedText.styledString(_snowmanxxxx, _snowmanx));
         }

         return reverse ? Lists.reverse(_snowman) : _snowman;
      }
   }

   public static TextReorderingProcessor create(StringVisitable visitable, Int2IntFunction reverser, UnaryOperator<String> _snowman) {
      StringBuilder _snowmanx = new StringBuilder();
      List<Style> _snowmanxx = Lists.newArrayList();
      visitable.visit((style, text) -> {
         TextVisitFactory.visitFormatted(text, style, (charIndex, stylex, codePoint) -> {
            _snowman.appendCodePoint(codePoint);
            int _snowmanxx = Character.charCount(codePoint);

            for (int _snowmanxxx = 0; _snowmanxxx < _snowmanxx; _snowmanxxx++) {
               _snowman.add(stylex);
            }

            return true;
         });
         return Optional.empty();
      }, Style.EMPTY);
      return new TextReorderingProcessor(_snowman.apply(_snowmanx.toString()), _snowmanxx, reverser);
   }
}
