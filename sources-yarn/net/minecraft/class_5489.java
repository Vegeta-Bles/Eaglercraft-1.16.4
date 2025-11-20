package net.minecraft;

import com.google.common.collect.ImmutableList;
import java.util.Arrays;
import java.util.List;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.text.OrderedText;
import net.minecraft.text.StringVisitable;
import net.minecraft.text.Text;

@Environment(EnvType.CLIENT)
public interface class_5489 {
   class_5489 field_26528 = new class_5489() {
      @Override
      public int method_30888(MatrixStack arg, int i, int j) {
         return j;
      }

      @Override
      public int method_30889(MatrixStack arg, int i, int j, int k, int l) {
         return j;
      }

      @Override
      public int method_30893(MatrixStack arg, int i, int j, int k, int l) {
         return j;
      }

      @Override
      public int method_30896(MatrixStack arg, int i, int j, int k, int l) {
         return j;
      }

      @Override
      public int method_30887() {
         return 0;
      }
   };

   static class_5489 method_30890(TextRenderer arg, StringVisitable arg2, int i) {
      return method_30895(
         arg, arg.wrapLines(arg2, i).stream().map(arg2x -> new class_5489.class_5490(arg2x, arg.getWidth(arg2x))).collect(ImmutableList.toImmutableList())
      );
   }

   static class_5489 method_30891(TextRenderer arg, StringVisitable arg2, int i, int j) {
      return method_30895(
         arg,
         arg.wrapLines(arg2, i)
            .stream()
            .limit((long)j)
            .map(arg2x -> new class_5489.class_5490(arg2x, arg.getWidth(arg2x)))
            .collect(ImmutableList.toImmutableList())
      );
   }

   static class_5489 method_30892(TextRenderer arg, Text... args) {
      return method_30895(
         arg,
         Arrays.stream(args).map(Text::asOrderedText).map(arg2 -> new class_5489.class_5490(arg2, arg.getWidth(arg2))).collect(ImmutableList.toImmutableList())
      );
   }

   static class_5489 method_30895(final TextRenderer arg, final List<class_5489.class_5490> list) {
      return list.isEmpty() ? field_26528 : new class_5489() {
         @Override
         public int method_30888(MatrixStack argx, int i, int j) {
            return this.method_30889(argx, i, j, 9, 16777215);
         }

         @Override
         public int method_30889(MatrixStack argx, int i, int j, int k, int l) {
            int m = j;

            for (class_5489.class_5490 lv : list) {
               arg.drawWithShadow(argx, lv.field_26531, (float)(i - lv.field_26532 / 2), (float)m, l);
               m += k;
            }

            return m;
         }

         @Override
         public int method_30893(MatrixStack argx, int i, int j, int k, int l) {
            int m = j;

            for (class_5489.class_5490 lv : list) {
               arg.drawWithShadow(argx, lv.field_26531, (float)i, (float)m, l);
               m += k;
            }

            return m;
         }

         @Override
         public int method_30896(MatrixStack argx, int i, int j, int k, int l) {
            int m = j;

            for (class_5489.class_5490 lv : list) {
               arg.draw(argx, lv.field_26531, (float)i, (float)m, l);
               m += k;
            }

            return m;
         }

         @Override
         public int method_30887() {
            return list.size();
         }
      };
   }

   int method_30888(MatrixStack arg, int i, int j);

   int method_30889(MatrixStack arg, int i, int j, int k, int l);

   int method_30893(MatrixStack arg, int i, int j, int k, int l);

   int method_30896(MatrixStack arg, int i, int j, int k, int l);

   int method_30887();

   @Environment(EnvType.CLIENT)
   public static class class_5490 {
      private final OrderedText field_26531;
      private final int field_26532;

      private class_5490(OrderedText arg, int i) {
         this.field_26531 = arg;
         this.field_26532 = i;
      }
   }
}
