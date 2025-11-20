package net.minecraft.client.font;

import com.google.common.collect.Lists;
import java.util.List;
import java.util.ListIterator;
import java.util.Optional;
import java.util.function.BiConsumer;
import java.util.stream.Collectors;
import javax.annotation.Nullable;
import net.minecraft.client.util.TextCollector;
import net.minecraft.text.CharacterVisitor;
import net.minecraft.text.OrderedText;
import net.minecraft.text.StringVisitable;
import net.minecraft.text.Style;
import org.apache.commons.lang3.mutable.MutableFloat;
import org.apache.commons.lang3.mutable.MutableInt;
import org.apache.commons.lang3.mutable.MutableObject;

public class TextHandler {
   private final TextHandler.WidthRetriever widthRetriever;

   public TextHandler(TextHandler.WidthRetriever widthRetriever) {
      this.widthRetriever = widthRetriever;
   }

   public float getWidth(@Nullable String text) {
      if (text == null) {
         return 0.0F;
      } else {
         MutableFloat _snowman = new MutableFloat();
         TextVisitFactory.visitFormatted(text, Style.EMPTY, (unused, style, codePoint) -> {
            _snowman.add(this.widthRetriever.getWidth(codePoint, style));
            return true;
         });
         return _snowman.floatValue();
      }
   }

   public float getWidth(StringVisitable text) {
      MutableFloat _snowman = new MutableFloat();
      TextVisitFactory.visitFormatted(text, Style.EMPTY, (unused, style, codePoint) -> {
         _snowman.add(this.widthRetriever.getWidth(codePoint, style));
         return true;
      });
      return _snowman.floatValue();
   }

   public float getWidth(OrderedText text) {
      MutableFloat _snowman = new MutableFloat();
      text.accept((_snowmanx, _snowmanxx, _snowmanxxx) -> {
         _snowman.add(this.widthRetriever.getWidth(_snowmanxxx, _snowmanxx));
         return true;
      });
      return _snowman.floatValue();
   }

   public int getTrimmedLength(String text, int maxWidth, Style style) {
      TextHandler.WidthLimitingVisitor _snowman = new TextHandler.WidthLimitingVisitor((float)maxWidth);
      TextVisitFactory.visitForwards(text, style, _snowman);
      return _snowman.getLength();
   }

   public String trimToWidth(String text, int maxWidth, Style style) {
      return text.substring(0, this.getTrimmedLength(text, maxWidth, style));
   }

   public String trimToWidthBackwards(String text, int maxWidth, Style style) {
      MutableFloat _snowman = new MutableFloat();
      MutableInt _snowmanx = new MutableInt(text.length());
      TextVisitFactory.visitBackwards(text, style, (_snowmanxxx, stylex, codePoint) -> {
         float _snowmanxx = _snowman.addAndGet(this.widthRetriever.getWidth(codePoint, stylex));
         if (_snowmanxx > (float)maxWidth) {
            return false;
         } else {
            _snowman.setValue(_snowmanxxx);
            return true;
         }
      });
      return text.substring(_snowmanx.intValue());
   }

   @Nullable
   public Style getStyleAt(StringVisitable text, int x) {
      TextHandler.WidthLimitingVisitor _snowman = new TextHandler.WidthLimitingVisitor((float)x);
      return text.<Style>visit((_snowmanx, _snowmanxx) -> TextVisitFactory.visitFormatted(_snowmanxx, _snowmanx, _snowman) ? Optional.empty() : Optional.of(_snowmanx), Style.EMPTY).orElse(null);
   }

   @Nullable
   public Style getStyleAt(OrderedText text, int x) {
      TextHandler.WidthLimitingVisitor _snowman = new TextHandler.WidthLimitingVisitor((float)x);
      MutableObject<Style> _snowmanx = new MutableObject();
      text.accept((_snowmanxx, _snowmanxxx, _snowmanxxxx) -> {
         if (!_snowman.accept(_snowmanxx, _snowmanxxx, _snowmanxxxx)) {
            _snowman.setValue(_snowmanxxx);
            return false;
         } else {
            return true;
         }
      });
      return (Style)_snowmanx.getValue();
   }

   public StringVisitable trimToWidth(StringVisitable text, int width, Style style) {
      final TextHandler.WidthLimitingVisitor _snowman = new TextHandler.WidthLimitingVisitor((float)width);
      return text.visit(new StringVisitable.StyledVisitor<StringVisitable>() {
         private final TextCollector collector = new TextCollector();

         @Override
         public Optional<StringVisitable> accept(Style _snowman, String _snowman) {
            _snowman.resetLength();
            if (!TextVisitFactory.visitFormatted(_snowman, _snowman, _snowman)) {
               String _snowmanxx = _snowman.substring(0, _snowman.getLength());
               if (!_snowmanxx.isEmpty()) {
                  this.collector.add(StringVisitable.styled(_snowmanxx, _snowman));
               }

               return Optional.of(this.collector.getCombined());
            } else {
               if (!_snowman.isEmpty()) {
                  this.collector.add(StringVisitable.styled(_snowman, _snowman));
               }

               return Optional.empty();
            }
         }
      }, style).orElse(text);
   }

   public static int moveCursorByWords(String text, int offset, int cursor, boolean consumeSpaceOrBreak) {
      int _snowman = cursor;
      boolean _snowmanx = offset < 0;
      int _snowmanxx = Math.abs(offset);

      for (int _snowmanxxx = 0; _snowmanxxx < _snowmanxx; _snowmanxxx++) {
         if (_snowmanx) {
            while (consumeSpaceOrBreak && _snowman > 0 && (text.charAt(_snowman - 1) == ' ' || text.charAt(_snowman - 1) == '\n')) {
               _snowman--;
            }

            while (_snowman > 0 && text.charAt(_snowman - 1) != ' ' && text.charAt(_snowman - 1) != '\n') {
               _snowman--;
            }
         } else {
            int _snowmanxxxx = text.length();
            int _snowmanxxxxx = text.indexOf(32, _snowman);
            int _snowmanxxxxxx = text.indexOf(10, _snowman);
            if (_snowmanxxxxx == -1 && _snowmanxxxxxx == -1) {
               _snowman = -1;
            } else if (_snowmanxxxxx != -1 && _snowmanxxxxxx != -1) {
               _snowman = Math.min(_snowmanxxxxx, _snowmanxxxxxx);
            } else if (_snowmanxxxxx != -1) {
               _snowman = _snowmanxxxxx;
            } else {
               _snowman = _snowmanxxxxxx;
            }

            if (_snowman == -1) {
               _snowman = _snowmanxxxx;
            } else {
               while (consumeSpaceOrBreak && _snowman < _snowmanxxxx && (text.charAt(_snowman) == ' ' || text.charAt(_snowman) == '\n')) {
                  _snowman++;
               }
            }
         }
      }

      return _snowman;
   }

   public void wrapLines(String text, int maxWidth, Style style, boolean retainTrailingWordSplit, TextHandler.LineWrappingConsumer consumer) {
      int _snowman = 0;
      int _snowmanx = text.length();
      Style _snowmanxx = style;

      while (_snowman < _snowmanx) {
         TextHandler.LineBreakingVisitor _snowmanxxx = new TextHandler.LineBreakingVisitor((float)maxWidth);
         boolean _snowmanxxxx = TextVisitFactory.visitFormatted(text, _snowman, _snowmanxx, style, _snowmanxxx);
         if (_snowmanxxxx) {
            consumer.accept(_snowmanxx, _snowman, _snowmanx);
            break;
         }

         int _snowmanxxxxx = _snowmanxxx.getEndingIndex();
         char _snowmanxxxxxx = text.charAt(_snowmanxxxxx);
         int _snowmanxxxxxxx = _snowmanxxxxxx != '\n' && _snowmanxxxxxx != ' ' ? _snowmanxxxxx : _snowmanxxxxx + 1;
         consumer.accept(_snowmanxx, _snowman, retainTrailingWordSplit ? _snowmanxxxxxxx : _snowmanxxxxx);
         _snowman = _snowmanxxxxxxx;
         _snowmanxx = _snowmanxxx.getEndingStyle();
      }
   }

   public List<StringVisitable> wrapLines(String text, int maxWidth, Style style) {
      List<StringVisitable> _snowman = Lists.newArrayList();
      this.wrapLines(text, maxWidth, style, false, (_snowmanxx, _snowmanxxx, _snowmanxxxx) -> _snowman.add(StringVisitable.styled(text.substring(_snowmanxxx, _snowmanxxxx), _snowmanxx)));
      return _snowman;
   }

   public List<StringVisitable> wrapLines(StringVisitable _snowman, int maxWidth, Style _snowman) {
      List<StringVisitable> _snowmanxx = Lists.newArrayList();
      this.method_29971(_snowman, maxWidth, _snowman, (_snowmanxxxx, _snowmanxxx) -> _snowman.add(_snowmanxxxx));
      return _snowmanxx;
   }

   public void method_29971(StringVisitable _snowman, int _snowman, Style _snowman, BiConsumer<StringVisitable, Boolean> _snowman) {
      List<TextHandler.StyledString> _snowmanxxxx = Lists.newArrayList();
      _snowman.visit((_snowmanxxxxxx, _snowmanxxxxx) -> {
         if (!_snowmanxxxxx.isEmpty()) {
            _snowman.add(new TextHandler.StyledString(_snowmanxxxxx, _snowmanxxxxxx));
         }

         return Optional.empty();
      }, _snowman);
      TextHandler.LineWrappingCollector _snowmanxxxxx = new TextHandler.LineWrappingCollector(_snowmanxxxx);
      boolean _snowmanxxxxxx = true;
      boolean _snowmanxxxxxxx = false;
      boolean _snowmanxxxxxxxx = false;

      while (_snowmanxxxxxx) {
         _snowmanxxxxxx = false;
         TextHandler.LineBreakingVisitor _snowmanxxxxxxxxx = new TextHandler.LineBreakingVisitor((float)_snowman);

         for (TextHandler.StyledString _snowmanxxxxxxxxxx : _snowmanxxxxx.parts) {
            boolean _snowmanxxxxxxxxxxx = TextVisitFactory.visitFormatted(_snowmanxxxxxxxxxx.literal, 0, _snowmanxxxxxxxxxx.style, _snowman, _snowmanxxxxxxxxx);
            if (!_snowmanxxxxxxxxxxx) {
               int _snowmanxxxxxxxxxxxx = _snowmanxxxxxxxxx.getEndingIndex();
               Style _snowmanxxxxxxxxxxxxx = _snowmanxxxxxxxxx.getEndingStyle();
               char _snowmanxxxxxxxxxxxxxx = _snowmanxxxxx.charAt(_snowmanxxxxxxxxxxxx);
               boolean _snowmanxxxxxxxxxxxxxxx = _snowmanxxxxxxxxxxxxxx == '\n';
               boolean _snowmanxxxxxxxxxxxxxxxx = _snowmanxxxxxxxxxxxxxxx || _snowmanxxxxxxxxxxxxxx == ' ';
               _snowmanxxxxxxx = _snowmanxxxxxxxxxxxxxxx;
               StringVisitable _snowmanxxxxxxxxxxxxxxxxx = _snowmanxxxxx.collectLine(_snowmanxxxxxxxxxxxx, _snowmanxxxxxxxxxxxxxxxx ? 1 : 0, _snowmanxxxxxxxxxxxxx);
               _snowman.accept(_snowmanxxxxxxxxxxxxxxxxx, _snowmanxxxxxxxx);
               _snowmanxxxxxxxx = !_snowmanxxxxxxxxxxxxxxx;
               _snowmanxxxxxx = true;
               break;
            }

            _snowmanxxxxxxxxx.offset(_snowmanxxxxxxxxxx.literal.length());
         }
      }

      StringVisitable _snowmanxxxxxxxxx = _snowmanxxxxx.collectRemainers();
      if (_snowmanxxxxxxxxx != null) {
         _snowman.accept(_snowmanxxxxxxxxx, _snowmanxxxxxxxx);
      } else if (_snowmanxxxxxxx) {
         _snowman.accept(StringVisitable.EMPTY, false);
      }
   }

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
      public boolean accept(int _snowman, Style _snowman, int _snowman) {
         int _snowmanxxx = _snowman + this.startOffset;
         switch (_snowman) {
            case 10:
               return this.breakLine(_snowmanxxx, _snowman);
            case 32:
               this.lastSpaceBreak = _snowmanxxx;
               this.lastSpaceStyle = _snowman;
            default:
               float _snowmanxxxx = TextHandler.this.widthRetriever.getWidth(_snowman, _snowman);
               this.totalWidth += _snowmanxxxx;
               if (!this.nonEmpty || !(this.totalWidth > this.maxWidth)) {
                  this.nonEmpty |= _snowmanxxxx != 0.0F;
                  this.count = _snowmanxxx + Character.charCount(_snowman);
                  return true;
               } else {
                  return this.lastSpaceBreak != -1 ? this.breakLine(this.lastSpaceBreak, this.lastSpaceStyle) : this.breakLine(_snowmanxxx, _snowman);
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

   static class LineWrappingCollector {
      private final List<TextHandler.StyledString> parts;
      private String joined;

      public LineWrappingCollector(List<TextHandler.StyledString> parts) {
         this.parts = parts;
         this.joined = parts.stream().map(_snowman -> _snowman.literal).collect(Collectors.joining());
      }

      public char charAt(int index) {
         return this.joined.charAt(index);
      }

      public StringVisitable collectLine(int lineLength, int skippedLength, Style style) {
         TextCollector _snowman = new TextCollector();
         ListIterator<TextHandler.StyledString> _snowmanx = this.parts.listIterator();
         int _snowmanxx = lineLength;
         boolean _snowmanxxx = false;

         while (_snowmanx.hasNext()) {
            TextHandler.StyledString _snowmanxxxx = _snowmanx.next();
            String _snowmanxxxxx = _snowmanxxxx.literal;
            int _snowmanxxxxxx = _snowmanxxxxx.length();
            if (!_snowmanxxx) {
               if (_snowmanxx > _snowmanxxxxxx) {
                  _snowman.add(_snowmanxxxx);
                  _snowmanx.remove();
                  _snowmanxx -= _snowmanxxxxxx;
               } else {
                  String _snowmanxxxxxxx = _snowmanxxxxx.substring(0, _snowmanxx);
                  if (!_snowmanxxxxxxx.isEmpty()) {
                     _snowman.add(StringVisitable.styled(_snowmanxxxxxxx, _snowmanxxxx.style));
                  }

                  _snowmanxx += skippedLength;
                  _snowmanxxx = true;
               }
            }

            if (_snowmanxxx) {
               if (_snowmanxx <= _snowmanxxxxxx) {
                  String _snowmanxxxxxxx = _snowmanxxxxx.substring(_snowmanxx);
                  if (_snowmanxxxxxxx.isEmpty()) {
                     _snowmanx.remove();
                  } else {
                     _snowmanx.set(new TextHandler.StyledString(_snowmanxxxxxxx, style));
                  }
                  break;
               }

               _snowmanx.remove();
               _snowmanxx -= _snowmanxxxxxx;
            }
         }

         this.joined = this.joined.substring(lineLength + skippedLength);
         return _snowman.getCombined();
      }

      @Nullable
      public StringVisitable collectRemainers() {
         TextCollector _snowman = new TextCollector();
         this.parts.forEach(_snowman::add);
         this.parts.clear();
         return _snowman.getRawCombined();
      }
   }

   @FunctionalInterface
   public interface LineWrappingConsumer {
      void accept(Style style, int start, int end);
   }

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

   class WidthLimitingVisitor implements CharacterVisitor {
      private float widthLeft;
      private int length;

      public WidthLimitingVisitor(float maxWidth) {
         this.widthLeft = maxWidth;
      }

      @Override
      public boolean accept(int _snowman, Style _snowman, int _snowman) {
         this.widthLeft = this.widthLeft - TextHandler.this.widthRetriever.getWidth(_snowman, _snowman);
         if (this.widthLeft >= 0.0F) {
            this.length = _snowman + Character.charCount(_snowman);
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
   public interface WidthRetriever {
      float getWidth(int codePoint, Style style);
   }
}
