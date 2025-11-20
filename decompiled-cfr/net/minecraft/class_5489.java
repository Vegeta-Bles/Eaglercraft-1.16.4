/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.collect.ImmutableList
 */
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
    public static final class_5489 field_26528 = new class_5489(){

        @Override
        public int method_30888(MatrixStack matrixStack, int n, int n2) {
            return n2;
        }

        @Override
        public int method_30889(MatrixStack matrixStack, int n, int n2, int n3, int n4) {
            return n2;
        }

        @Override
        public int method_30893(MatrixStack matrixStack, int n, int n2, int n3, int n4) {
            return n2;
        }

        @Override
        public int method_30896(MatrixStack matrixStack, int n, int n2, int n3, int n4) {
            return n2;
        }

        @Override
        public int method_30887() {
            return 0;
        }
    };

    public static class_5489 method_30890(TextRenderer textRenderer, StringVisitable stringVisitable, int n) {
        return class_5489.method_30895(textRenderer, (List)textRenderer.wrapLines(stringVisitable, n).stream().map(orderedText -> new class_5490((OrderedText)orderedText, textRenderer.getWidth((OrderedText)orderedText))).collect(ImmutableList.toImmutableList()));
    }

    public static class_5489 method_30891(TextRenderer textRenderer, StringVisitable stringVisitable, int n, int n2) {
        return class_5489.method_30895(textRenderer, (List)textRenderer.wrapLines(stringVisitable, n).stream().limit(n2).map(orderedText -> new class_5490((OrderedText)orderedText, textRenderer.getWidth((OrderedText)orderedText))).collect(ImmutableList.toImmutableList()));
    }

    public static class_5489 method_30892(TextRenderer textRenderer, Text ... textArray) {
        return class_5489.method_30895(textRenderer, (List)Arrays.stream(textArray).map(Text::asOrderedText).map(orderedText -> new class_5490((OrderedText)orderedText, textRenderer.getWidth((OrderedText)orderedText))).collect(ImmutableList.toImmutableList()));
    }

    public static class_5489 method_30895(final TextRenderer textRenderer, final List<class_5490> list) {
        if (list.isEmpty()) {
            return field_26528;
        }
        return new class_5489(){

            @Override
            public int method_30888(MatrixStack matrixStack, int n, int n2) {
                return this.method_30889(matrixStack, n, n2, textRenderer.fontHeight, 0xFFFFFF);
            }

            @Override
            public int method_30889(MatrixStack matrixStack, int n, int n2, int n3, int n4) {
                int n5;
                n5 = n2;
                for (class_5490 class_54902 : list) {
                    textRenderer.drawWithShadow(matrixStack, class_54902.field_26531, (float)(n - class_54902.field_26532 / 2), (float)n5, n4);
                    n5 += n3;
                }
                return n5;
            }

            @Override
            public int method_30893(MatrixStack matrixStack, int n, int n2, int n3, int n4) {
                int n5;
                n5 = n2;
                for (class_5490 class_54902 : list) {
                    textRenderer.drawWithShadow(matrixStack, class_54902.field_26531, (float)n, (float)n5, n4);
                    n5 += n3;
                }
                return n5;
            }

            @Override
            public int method_30896(MatrixStack matrixStack, int n, int n2, int n3, int n4) {
                int n5;
                n5 = n2;
                for (class_5490 class_54902 : list) {
                    textRenderer.draw(matrixStack, class_54902.field_26531, (float)n, (float)n5, n4);
                    n5 += n3;
                }
                return n5;
            }

            @Override
            public int method_30887() {
                return list.size();
            }
        };
    }

    public int method_30888(MatrixStack var1, int var2, int var3);

    public int method_30889(MatrixStack var1, int var2, int var3, int var4, int var5);

    public int method_30893(MatrixStack var1, int var2, int var3, int var4, int var5);

    public int method_30896(MatrixStack var1, int var2, int var3, int var4, int var5);

    public int method_30887();

    public static class class_5490 {
        private final OrderedText field_26531;
        private final int field_26532;

        private class_5490(OrderedText orderedText, int n) {
            this.field_26531 = orderedText;
            this.field_26532 = n;
        }
    }
}

