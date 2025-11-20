package net.minecraft.client.font;

import com.google.common.collect.Lists;
import java.util.List;
import java.util.ListIterator;
import java.util.Optional;
import java.util.function.BiConsumer;
import java.util.stream.Collectors;
import javax.annotation.Nullable;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.util.TextCollector;
import net.minecraft.text.CharacterVisitor;
import net.minecraft.text.OrderedText;
import net.minecraft.text.StringVisitable;
import net.minecraft.text.Style;
import org.apache.commons.lang3.mutable.MutableFloat;
import org.apache.commons.lang3.mutable.MutableInt;
import org.apache.commons.lang3.mutable.MutableObject;

@Environment(EnvType.CLIENT)
public class TextHandler {
   private final TextHandler.WidthRetriever widthRetriever;

   public TextHandler(TextHandler.WidthRetriever widthRetriever) {
      this.widthRetriever = widthRetriever;
   }

   public float getWidth(@Nullable String text) {
      if (text == null) {
         return 0.0F;
      } else {
         MutableFloat mutableFloat = new MutableFloat();
         TextVisitFactory.visitFormatted(text, Style.EMPTY, (unused, style, codePoint) -> {
            mutableFloat.add(this.widthRetriever.getWidth(codePoint, style));
            return true;
         });
         return mutableFloat.floatValue();
      }
   }

   public float getWidth(StringVisitable text) {
      MutableFloat mutableFloat = new MutableFloat();
      TextVisitFactory.visitFormatted(text, Style.EMPTY, (unused, style, codePoint) -> {
         mutableFloat.add(this.widthRetriever.getWidth(codePoint, style));
         return true;
      });
      return mutableFloat.floatValue();
   }

   public float getWidth(OrderedText text) {
      MutableFloat mutableFloat = new MutableFloat();
      text.accept((i, arg, j) -> {
         mutableFloat.add(this.widthRetriever.getWidth(j, arg));
         return true;
      });
      return mutableFloat.floatValue();
   }

   public int getTrimmedLength(String text, int maxWidth, Style style) {
      TextHandler.WidthLimitingVisitor lv = new TextHandler.WidthLimitingVisitor((float)maxWidth);
      TextVisitFactory.visitForwards(text, style, lv);
      return lv.getLength();
   }

   public String trimToWidth(String text, int maxWidth, Style style) {
      return text.substring(0, this.getTrimmedLength(text, maxWidth, style));
   }

   public String trimToWidthBackwards(String text, int maxWidth, Style style) {
      MutableFloat mutableFloat = new MutableFloat();
      MutableInt mutableInt = new MutableInt(text.length());
      TextVisitFactory.visitBackwards(text, style, (j, stylex, codePoint) -> {
         float f = mutableFloat.addAndGet(this.widthRetriever.getWidth(codePoint, stylex));
         if (f > (float)maxWidth) {
            return false;
         } else {
            mutableInt.setValue(j);
            return true;
         }
      });
      return text.substring(mutableInt.intValue());
   }

   @Nullable
   public Style getStyleAt(StringVisitable text, int x) {
      TextHandler.WidthLimitingVisitor lv = new TextHandler.WidthLimitingVisitor((float)x);
      return text.<Style>visit((arg2, string) -> TextVisitFactory.visitFormatted(string, arg2, lv) ? Optional.empty() : Optional.of(arg2), Style.EMPTY)
         .orElse(null);
   }

   @Nullable
   public Style getStyleAt(OrderedText text, int x) {
      TextHandler.WidthLimitingVisitor lv = new TextHandler.WidthLimitingVisitor((float)x);
      MutableObject<Style> mutableObject = new MutableObject();
      text.accept((i, arg2, j) -> {
         if (!lv.accept(i, arg2, j)) {
            mutableObject.setValue(arg2);
            return false;
         } else {
            return true;
         }
      });
      return (Style)mutableObject.getValue();
   }

   public StringVisitable trimToWidth(StringVisitable text, int width, Style style) {
      final TextHandler.WidthLimitingVisitor lv = new TextHandler.WidthLimitingVisitor((float)width);
      return text.visit(new StringVisitable.StyledVisitor<StringVisitable>() {
         private final TextCollector collector = new TextCollector();

         @Override
         public Optional<StringVisitable> accept(Style arg, String string) {
            lv.resetLength();
            if (!TextVisitFactory.visitFormatted(string, arg, lv)) {
               String string2 = string.substring(0, lv.getLength());
               if (!string2.isEmpty()) {
                  this.collector.add(StringVisitable.styled(string2, arg));
               }

               return Optional.of(this.collector.getCombined());
            } else {
               if (!string.isEmpty()) {
                  this.collector.add(StringVisitable.styled(string, arg));
               }

               return Optional.empty();
            }
         }
      }, style).orElse(text);
   }

   public static int moveCursorByWords(String text, int offset, int cursor, boolean consumeSpaceOrBreak) {
      int k = cursor;
      boolean bl2 = offset < 0;
      int l = Math.abs(offset);

      for (int m = 0; m < l; m++) {
         if (bl2) {
            while (consumeSpaceOrBreak && k > 0 && (text.charAt(k - 1) == ' ' || text.charAt(k - 1) == '\n')) {
               k--;
            }

            while (k > 0 && text.charAt(k - 1) != ' ' && text.charAt(k - 1) != '\n') {
               k--;
            }
         } else {
            int n = text.length();
            int o = text.indexOf(32, k);
            int p = text.indexOf(10, k);
            if (o == -1 && p == -1) {
               k = -1;
            } else if (o != -1 && p != -1) {
               k = Math.min(o, p);
            } else if (o != -1) {
               k = o;
            } else {
               k = p;
            }

            if (k == -1) {
               k = n;
            } else {
               while (consumeSpaceOrBreak && k < n && (text.charAt(k) == ' ' || text.charAt(k) == '\n')) {
                  k++;
               }
            }
         }
      }

      return k;
   }

   public void wrapLines(String text, int maxWidth, Style style, boolean retainTrailingWordSplit, TextHandler.LineWrappingConsumer consumer) {
      int j = 0;
      int k = text.length();
      Style lv = style;

      while (j < k) {
         TextHandler.LineBreakingVisitor lv2 = new TextHandler.LineBreakingVisitor((float)maxWidth);
         boolean bl2 = TextVisitFactory.visitFormatted(text, j, lv, style, lv2);
         if (bl2) {
            consumer.accept(lv, j, k);
            break;
         }

         int l = lv2.getEndingIndex();
         char c = text.charAt(l);
         int m = c != '\n' && c != ' ' ? l : l + 1;
         consumer.accept(lv, j, retainTrailingWordSplit ? m : l);
         j = m;
         lv = lv2.getEndingStyle();
      }
   }

   public List<StringVisitable> wrapLines(String text, int maxWidth, Style style) {
      List<StringVisitable> list = Lists.newArrayList();
      this.wrapLines(text, maxWidth, style, false, (arg, i, j) -> list.add(StringVisitable.styled(text.substring(i, j), arg)));
      return list;
   }

   public List<StringVisitable> wrapLines(StringVisitable arg, int maxWidth, Style arg2) {
      List<StringVisitable> list = Lists.newArrayList();
      this.method_29971(arg, maxWidth, arg2, (argx, boolean_) -> list.add(argx));
      return list;
   }

   public void method_29971(StringVisitable arg, int i, Style arg2, BiConsumer<StringVisitable, Boolean> biConsumer) {
      List<TextHandler.StyledString> list = Lists.newArrayList();
      arg.visit((argx, string) -> {
         if (!string.isEmpty()) {
            list.add(new TextHandler.StyledString(string, argx));
         }

         return Optional.empty();
      }, arg2);
      TextHandler.LineWrappingCollector lv = new TextHandler.LineWrappingCollector(list);
      boolean bl = true;
      boolean bl2 = false;
      boolean bl3 = false;

      while (bl) {
         bl = false;
         TextHandler.LineBreakingVisitor lv2 = new TextHandler.LineBreakingVisitor((float)i);

         for (TextHandler.StyledString lv3 : lv.parts) {
            boolean bl4 = TextVisitFactory.visitFormatted(lv3.literal, 0, lv3.style, arg2, lv2);
            if (!bl4) {
               int j = lv2.getEndingIndex();
               Style lv4 = lv2.getEndingStyle();
               char c = lv.charAt(j);
               boolean bl5 = c == '\n';
               boolean bl6 = bl5 || c == ' ';
               bl2 = bl5;
               StringVisitable lv5 = lv.collectLine(j, bl6 ? 1 : 0, lv4);
               biConsumer.accept(lv5, bl3);
               bl3 = !bl5;
               bl = true;
               break;
            }

            lv2.offset(lv3.literal.length());
         }
      }

      StringVisitable lv6 = lv.collectRemainers();
      if (lv6 != null) {
         biConsumer.accept(lv6, bl3);
      } else if (bl2) {
         biConsumer.accept(StringVisitable.EMPTY, false);
      }
   }

   @Environment(EnvType.CLIENT)
   class LineBreakingVisitor implements CharacterVisitor {
      private final float maxWidth;
      private int endIndex = -1;
      private Style endStyle = Style.EMPTY;
      private boolean nonEmpty;
      private float totalWidth;
      private int lastSpaceBreak = -1;
      private Style lastSpaceStyle = Style.EMPTY;
      private int count;
      private int startOffset;

      public LineBreakingVisitor(float maxWidth) {
         this.maxWidth = Math.max(maxWidth, 1.0F);
      }

      @Override
      public boolean accept(int i, Style arg, int j) {
         int k = i + this.startOffset;
         switch (j) {
            case 10:
               return this.breakLine(k, arg);
            case 32:
               this.lastSpaceBreak = k;
               this.lastSpaceStyle = arg;
            default:
               float f = TextHandler.this.widthRetriever.getWidth(j, arg);
               this.totalWidth += f;
               if (!this.nonEmpty || !(this.totalWidth > this.maxWidth)) {
                  this.nonEmpty |= f != 0.0F;
                  this.count = k + Character.charCount(j);
                  return true;
               } else {
                  return this.lastSpaceBreak != -1 ? this.breakLine(this.lastSpaceBreak, this.lastSpaceStyle) : this.breakLine(k, arg);
               }
         }
      }

      private boolean breakLine(int finishIndex, Style finishStyle) {
         this.endIndex = finishIndex;
         this.endStyle = finishStyle;
         return false;
      }

      private boolean hasLineBreak() {
         return this.endIndex != -1;
      }

      public int getEndingIndex() {
         return this.hasLineBreak() ? this.endIndex : this.count;
      }

      public Style getEndingStyle() {
         return this.endStyle;
      }

      public void offset(int extraOffset) {
         this.startOffset += extraOffset;
      }
   }

   @Environment(EnvType.CLIENT)
   static class LineWrappingCollector {
      private final List<TextHandler.StyledString> parts;
      private String joined;

      public LineWrappingCollector(List<TextHandler.StyledString> parts) {
         this.parts = parts;
         this.joined = parts.stream().map(arg -> arg.literal).collect(Collectors.joining());
      }

      public char charAt(int index) {
         return this.joined.charAt(index);
      }

      public StringVisitable collectLine(int lineLength, int skippedLength, Style style) {
         TextCollector lv = new TextCollector();
         ListIterator<TextHandler.StyledString> listIterator = this.parts.listIterator();
         int k = lineLength;
         boolean bl = false;

         while (listIterator.hasNext()) {
            TextHandler.StyledString lv2 = listIterator.next();
            String string = lv2.literal;
            int l = string.length();
            if (!bl) {
               if (k > l) {
                  lv.add(lv2);
                  listIterator.remove();
                  k -= l;
               } else {
                  String string2 = string.substring(0, k);
                  if (!string2.isEmpty()) {
                     lv.add(StringVisitable.styled(string2, lv2.style));
                  }

                  k += skippedLength;
                  bl = true;
               }
            }

            if (bl) {
               if (k <= l) {
                  String string3 = string.substring(k);
                  if (string3.isEmpty()) {
                     listIterator.remove();
                  } else {
                     listIterator.set(new TextHandler.StyledString(string3, style));
                  }
                  break;
               }

               listIterator.remove();
               k -= l;
            }
         }

         this.joined = this.joined.substring(lineLength + skippedLength);
         return lv.getCombined();
      }

      @Nullable
      public StringVisitable collectRemainers() {
         TextCollector lv = new TextCollector();
         this.parts.forEach(lv::add);
         this.parts.clear();
         return lv.getRawCombined();
      }
   }

   @FunctionalInterface
   @Environment(EnvType.CLIENT)
   public interface LineWrappingConsumer {
      void accept(Style style, int start, int end);
   }

   @Environment(EnvType.CLIENT)
   static class StyledString implements StringVisitable {
      private final String literal;
      private final Style style;

      public StyledString(String literal, Style style) {
         this.literal = literal;
         this.style = style;
      }

      @Override
      public <T> Optional<T> visit(StringVisitable.Visitor<T> visitor) {
         return visitor.accept(this.literal);
      }

      @Override
      public <T> Optional<T> visit(StringVisitable.StyledVisitor<T> styledVisitor, Style style) {
         return styledVisitor.accept(this.style.withParent(style), this.literal);
      }
   }

   @Environment(EnvType.CLIENT)
   class WidthLimitingVisitor implements CharacterVisitor {
      private float widthLeft;
      private int length;

      public WidthLimitingVisitor(float maxWidth) {
         this.widthLeft = maxWidth;
      }

      @Override
      public boolean accept(int i, Style arg, int j) {
         this.widthLeft = this.widthLeft - TextHandler.this.widthRetriever.getWidth(j, arg);
         if (this.widthLeft >= 0.0F) {
            this.length = i + Character.charCount(j);
            return true;
         } else {
            return false;
         }
      }

      public int getLength() {
         return this.length;
      }

      public void resetLength() {
         this.length = 0;
      }
   }

   @FunctionalInterface
   @Environment(EnvType.CLIENT)
   public interface WidthRetriever {
      float getWidth(int codePoint, Style style);
   }
}
