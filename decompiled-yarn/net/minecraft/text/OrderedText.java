package net.minecraft.text;

import com.google.common.collect.ImmutableList;
import it.unimi.dsi.fastutil.ints.Int2IntFunction;
import java.util.List;
import net.minecraft.client.font.TextVisitFactory;

@FunctionalInterface
public interface OrderedText {
   OrderedText EMPTY = _snowman -> true;

   boolean accept(CharacterVisitor visitor);

   static OrderedText styled(int codePoint, Style style) {
      return visitor -> visitor.accept(0, style, codePoint);
   }

   static OrderedText styledString(String string, Style style) {
      return string.isEmpty() ? EMPTY : visitor -> TextVisitFactory.visitForwards(string, style, visitor);
   }

   static OrderedText styledStringMapped(String string, Style style, Int2IntFunction codePointMapper) {
      return string.isEmpty() ? EMPTY : visitor -> TextVisitFactory.visitBackwards(string, style, map(visitor, codePointMapper));
   }

   static CharacterVisitor map(CharacterVisitor visitor, Int2IntFunction codePointMapper) {
      return (charIndex, style, charPoint) -> visitor.accept(charIndex, style, (Integer)codePointMapper.apply(charPoint));
   }

   static OrderedText concat(OrderedText first, OrderedText second) {
      return innerConcat(first, second);
   }

   static OrderedText concat(List<OrderedText> texts) {
      int _snowman = texts.size();
      switch (_snowman) {
         case 0:
            return EMPTY;
         case 1:
            return texts.get(0);
         case 2:
            return innerConcat(texts.get(0), texts.get(1));
         default:
            return innerConcat(ImmutableList.copyOf(texts));
      }
   }

   static OrderedText innerConcat(OrderedText text1, OrderedText text2) {
      return visitor -> text1.accept(visitor) && text2.accept(visitor);
   }

   static OrderedText innerConcat(List<OrderedText> texts) {
      return visitor -> {
         for (OrderedText _snowmanx : texts) {
            if (!_snowmanx.accept(visitor)) {
               return false;
            }
         }

         return true;
      };
   }
}
