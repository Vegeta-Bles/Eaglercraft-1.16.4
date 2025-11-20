package net.minecraft;

import com.google.common.collect.ImmutableList;
import java.util.Arrays;
import java.util.List;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.text.OrderedText;
import net.minecraft.text.StringVisitable;
import net.minecraft.text.Text;

public interface class_5489 {
   class_5489 field_26528 = new class_5489() {
      @Override
      public int method_30888(MatrixStack _snowman, int _snowman, int _snowman) {
         return _snowman;
      }

      @Override
      public int method_30889(MatrixStack _snowman, int _snowman, int _snowman, int _snowman, int _snowman) {
         return _snowman;
      }

      @Override
      public int method_30893(MatrixStack _snowman, int _snowman, int _snowman, int _snowman, int _snowman) {
         return _snowman;
      }

      @Override
      public int method_30896(MatrixStack _snowman, int _snowman, int _snowman, int _snowman, int _snowman) {
         return _snowman;
      }

      @Override
      public int method_30887() {
         return 0;
      }
   };

   static class_5489 method_30890(TextRenderer _snowman, StringVisitable _snowman, int _snowman) {
      return method_30895(
         _snowman, _snowman.wrapLines(_snowman, _snowman).stream().map(_snowmanxxxx -> new class_5489.class_5490(_snowmanxxxx, _snowman.getWidth(_snowmanxxxx))).collect(ImmutableList.toImmutableList())
      );
   }

   static class_5489 method_30891(TextRenderer _snowman, StringVisitable _snowman, int _snowman, int _snowman) {
      return method_30895(
         _snowman,
         _snowman.wrapLines(_snowman, _snowman)
            .stream()
            .limit((long)_snowman)
            .map(_snowmanxxxxx -> new class_5489.class_5490(_snowmanxxxxx, _snowman.getWidth(_snowmanxxxxx)))
            .collect(ImmutableList.toImmutableList())
      );
   }

   static class_5489 method_30892(TextRenderer _snowman, Text... _snowman) {
      return method_30895(
         _snowman, Arrays.stream(_snowman).map(Text::asOrderedText).map(_snowmanxxx -> new class_5489.class_5490(_snowmanxxx, _snowman.getWidth(_snowmanxxx))).collect(ImmutableList.toImmutableList())
      );
   }

   static class_5489 method_30895(TextRenderer _snowman, List<class_5489.class_5490> _snowman) {
      return _snowman.isEmpty() ? field_26528 : new class_5489() {
         @Override
         public int method_30888(MatrixStack _snowman, int _snowman, int _snowman) {
            return this.method_30889(_snowman, _snowman, _snowman, 9, 16777215);
         }

         @Override
         public int method_30889(MatrixStack _snowman, int _snowman, int _snowman, int _snowman, int _snowman) {
            int _snowmanxxxxx = _snowman;

            for (class_5489.class_5490 _snowmanx : _snowman) {
               _snowman.drawWithShadow(_snowman, _snowmanx.field_26531, (float)(_snowman - _snowmanx.field_26532 / 2), (float)_snowmanxxxxx, _snowman);
               _snowmanxxxxx += _snowman;
            }

            return _snowmanxxxxx;
         }

         @Override
         public int method_30893(MatrixStack _snowman, int _snowman, int _snowman, int _snowman, int _snowman) {
            int _snowmanxxxxx = _snowman;

            for (class_5489.class_5490 _snowmanx : _snowman) {
               _snowman.drawWithShadow(_snowman, _snowmanx.field_26531, (float)_snowman, (float)_snowmanxxxxx, _snowman);
               _snowmanxxxxx += _snowman;
            }

            return _snowmanxxxxx;
         }

         @Override
         public int method_30896(MatrixStack _snowman, int _snowman, int _snowman, int _snowman, int _snowman) {
            int _snowmanxxxxx = _snowman;

            for (class_5489.class_5490 _snowmanx : _snowman) {
               _snowman.draw(_snowman, _snowmanx.field_26531, (float)_snowman, (float)_snowmanxxxxx, _snowman);
               _snowmanxxxxx += _snowman;
            }

            return _snowmanxxxxx;
         }

         @Override
         public int method_30887() {
            return _snowman.size();
         }
      };
   }

   int method_30888(MatrixStack var1, int var2, int var3);

   int method_30889(MatrixStack var1, int var2, int var3, int var4, int var5);

   int method_30893(MatrixStack var1, int var2, int var3, int var4, int var5);

   int method_30896(MatrixStack var1, int var2, int var3, int var4, int var5);

   int method_30887();

   public static class class_5490 {
      private final OrderedText field_26531;
      private final int field_26532;

      private class_5490(OrderedText _snowman, int _snowman) {
         this.field_26531 = _snowman;
         this.field_26532 = _snowman;
      }
   }
}
