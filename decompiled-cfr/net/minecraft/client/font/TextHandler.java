/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.collect.Lists
 *  javax.annotation.Nullable
 *  org.apache.commons.lang3.mutable.MutableFloat
 *  org.apache.commons.lang3.mutable.MutableInt
 *  org.apache.commons.lang3.mutable.MutableObject
 */
package net.minecraft.client.font;

import com.google.common.collect.Lists;
import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;
import java.util.Optional;
import java.util.function.BiConsumer;
import java.util.stream.Collectors;
import javax.annotation.Nullable;
import net.minecraft.client.font.TextVisitFactory;
import net.minecraft.client.util.TextCollector;
import net.minecraft.text.CharacterVisitor;
import net.minecraft.text.OrderedText;
import net.minecraft.text.StringVisitable;
import net.minecraft.text.Style;
import org.apache.commons.lang3.mutable.MutableFloat;
import org.apache.commons.lang3.mutable.MutableInt;
import org.apache.commons.lang3.mutable.MutableObject;

public class TextHandler {
    private final WidthRetriever widthRetriever;

    public TextHandler(WidthRetriever widthRetriever) {
        this.widthRetriever = widthRetriever;
    }

    public float getWidth(@Nullable String text) {
        if (text == null) {
            return 0.0f;
        }
        MutableFloat mutableFloat = new MutableFloat();
        TextVisitFactory.visitFormatted(text, Style.EMPTY, (unused, style, codePoint) -> {
            mutableFloat.add(this.widthRetriever.getWidth(codePoint, style));
            return true;
        });
        return mutableFloat.floatValue();
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
        text.accept((n, style, n2) -> {
            mutableFloat.add(this.widthRetriever.getWidth(n2, style));
            return true;
        });
        return mutableFloat.floatValue();
    }

    public int getTrimmedLength(String text, int maxWidth, Style style) {
        WidthLimitingVisitor widthLimitingVisitor = new WidthLimitingVisitor(maxWidth);
        TextVisitFactory.visitForwards(text, style, widthLimitingVisitor);
        return widthLimitingVisitor.getLength();
    }

    public String trimToWidth(String text, int maxWidth, Style style) {
        return text.substring(0, this.getTrimmedLength(text, maxWidth, style));
    }

    public String trimToWidthBackwards(String text, int maxWidth, Style style2) {
        MutableFloat mutableFloat = new MutableFloat();
        MutableInt _snowman2 = new MutableInt(text.length());
        TextVisitFactory.visitBackwards(text, style2, (n2, style, codePoint) -> {
            float f = mutableFloat.addAndGet(this.widthRetriever.getWidth(codePoint, style));
            if (f > (float)maxWidth) {
                return false;
            }
            _snowman2.setValue(n2);
            return true;
        });
        return text.substring(_snowman2.intValue());
    }

    @Nullable
    public Style getStyleAt(StringVisitable text, int x) {
        WidthLimitingVisitor widthLimitingVisitor = new WidthLimitingVisitor(x);
        return text.visit((style, string) -> TextVisitFactory.visitFormatted(string, style, (CharacterVisitor)widthLimitingVisitor) ? Optional.empty() : Optional.of(style), Style.EMPTY).orElse(null);
    }

    @Nullable
    public Style getStyleAt(OrderedText text, int x) {
        WidthLimitingVisitor widthLimitingVisitor = new WidthLimitingVisitor(x);
        MutableObject _snowman2 = new MutableObject();
        text.accept((n, style, n2) -> {
            if (!widthLimitingVisitor.accept(n, style, n2)) {
                _snowman2.setValue((Object)style);
                return false;
            }
            return true;
        });
        return (Style)_snowman2.getValue();
    }

    public StringVisitable trimToWidth(StringVisitable text, int width, Style style) {
        final WidthLimitingVisitor widthLimitingVisitor = new WidthLimitingVisitor(width);
        return text.visit(new StringVisitable.StyledVisitor<StringVisitable>(){
            private final TextCollector collector = new TextCollector();

            @Override
            public Optional<StringVisitable> accept(Style style, String string) {
                widthLimitingVisitor.resetLength();
                if (!TextVisitFactory.visitFormatted(string, style, (CharacterVisitor)widthLimitingVisitor)) {
                    _snowman = string.substring(0, widthLimitingVisitor.getLength());
                    if (!_snowman.isEmpty()) {
                        this.collector.add(StringVisitable.styled(_snowman, style));
                    }
                    return Optional.of(this.collector.getCombined());
                }
                if (!string.isEmpty()) {
                    this.collector.add(StringVisitable.styled(string, style));
                }
                return Optional.empty();
            }
        }, style).orElse(text);
    }

    public static int moveCursorByWords(String text, int offset, int cursor, boolean consumeSpaceOrBreak) {
        int n = cursor;
        boolean _snowman2 = offset < 0;
        _snowman = Math.abs(offset);
        for (_snowman = 0; _snowman < _snowman; ++_snowman) {
            if (_snowman2) {
                while (consumeSpaceOrBreak && n > 0 && (text.charAt(n - 1) == ' ' || text.charAt(n - 1) == '\n')) {
                    --n;
                }
                while (n > 0 && text.charAt(n - 1) != ' ' && text.charAt(n - 1) != '\n') {
                    --n;
                }
                continue;
            }
            _snowman = text.length();
            _snowman = text.indexOf(32, n);
            _snowman = text.indexOf(10, n);
            n = _snowman == -1 && _snowman == -1 ? -1 : (_snowman != -1 && _snowman != -1 ? Math.min(_snowman, _snowman) : (_snowman != -1 ? _snowman : _snowman));
            if (n == -1) {
                n = _snowman;
                continue;
            }
            while (consumeSpaceOrBreak && n < _snowman && (text.charAt(n) == ' ' || text.charAt(n) == '\n')) {
                ++n;
            }
        }
        return n;
    }

    public void wrapLines(String text, int maxWidth, Style style, boolean retainTrailingWordSplit, LineWrappingConsumer consumer) {
        int _snowman7 = 0;
        _snowman = text.length();
        Style _snowman2 = style;
        while (_snowman7 < _snowman) {
            LineBreakingVisitor lineBreakingVisitor = new LineBreakingVisitor(maxWidth);
            boolean _snowman3 = TextVisitFactory.visitFormatted(text, _snowman7, _snowman2, style, lineBreakingVisitor);
            if (_snowman3) {
                consumer.accept(_snowman2, _snowman7, _snowman);
                break;
            }
            int _snowman4 = lineBreakingVisitor.getEndingIndex();
            char _snowman5 = text.charAt(_snowman4);
            int _snowman6 = _snowman5 == '\n' || _snowman5 == ' ' ? _snowman4 + 1 : _snowman4;
            consumer.accept(_snowman2, _snowman7, retainTrailingWordSplit ? _snowman6 : _snowman4);
            _snowman7 = _snowman6;
            _snowman2 = lineBreakingVisitor.getEndingStyle();
        }
    }

    public List<StringVisitable> wrapLines(String text, int maxWidth, Style style2) {
        ArrayList arrayList = Lists.newArrayList();
        this.wrapLines(text, maxWidth, style2, false, (style, n, n2) -> arrayList.add(StringVisitable.styled(text.substring(n, n2), style)));
        return arrayList;
    }

    public List<StringVisitable> wrapLines(StringVisitable stringVisitable2, int maxWidth, Style style) {
        ArrayList arrayList = Lists.newArrayList();
        this.method_29971(stringVisitable2, maxWidth, style, (stringVisitable, bl) -> arrayList.add(stringVisitable));
        return arrayList;
    }

    public void method_29971(StringVisitable stringVisitable, int n, Style style2, BiConsumer<StringVisitable, Boolean> biConsumer) {
        Object object;
        ArrayList arrayList = Lists.newArrayList();
        stringVisitable.visit((style, string) -> {
            if (!string.isEmpty()) {
                arrayList.add(new StyledString(string, style));
            }
            return Optional.empty();
        }, style2);
        LineWrappingCollector _snowman2 = new LineWrappingCollector(arrayList);
        boolean _snowman3 = true;
        boolean _snowman4 = false;
        boolean _snowman5 = false;
        block0: while (_snowman3) {
            _snowman3 = false;
            object = new LineBreakingVisitor(n);
            for (StyledString styledString : _snowman2.parts) {
                boolean bl = TextVisitFactory.visitFormatted(styledString.literal, 0, styledString.style, style2, (CharacterVisitor)object);
                if (!bl) {
                    int n2 = ((LineBreakingVisitor)object).getEndingIndex();
                    Style _snowman6 = ((LineBreakingVisitor)object).getEndingStyle();
                    char _snowman7 = _snowman2.charAt(n2);
                    boolean _snowman8 = _snowman7 == '\n';
                    boolean _snowman9 = _snowman8 || _snowman7 == ' ';
                    _snowman4 = _snowman8;
                    StringVisitable _snowman10 = _snowman2.collectLine(n2, _snowman9 ? 1 : 0, _snowman6);
                    biConsumer.accept(_snowman10, _snowman5);
                    _snowman5 = !_snowman8;
                    _snowman3 = true;
                    continue block0;
                }
                ((LineBreakingVisitor)object).offset(styledString.literal.length());
            }
        }
        object = _snowman2.collectRemainers();
        if (object != null) {
            biConsumer.accept((StringVisitable)object, _snowman5);
        } else if (_snowman4) {
            biConsumer.accept(StringVisitable.EMPTY, false);
        }
    }

    static class LineWrappingCollector {
        private final List<StyledString> parts;
        private String joined;

        public LineWrappingCollector(List<StyledString> parts) {
            this.parts = parts;
            this.joined = parts.stream().map(styledString -> ((StyledString)styledString).literal).collect(Collectors.joining());
        }

        public char charAt(int index) {
            return this.joined.charAt(index);
        }

        public StringVisitable collectLine(int lineLength, int skippedLength, Style style) {
            TextCollector textCollector = new TextCollector();
            ListIterator<StyledString> _snowman2 = this.parts.listIterator();
            int _snowman3 = lineLength;
            boolean _snowman4 = false;
            while (_snowman2.hasNext()) {
                String string;
                StyledString styledString = _snowman2.next();
                String _snowman5 = styledString.literal;
                int _snowman6 = _snowman5.length();
                if (!_snowman4) {
                    if (_snowman3 > _snowman6) {
                        textCollector.add(styledString);
                        _snowman2.remove();
                        _snowman3 -= _snowman6;
                    } else {
                        string = _snowman5.substring(0, _snowman3);
                        if (!string.isEmpty()) {
                            textCollector.add(StringVisitable.styled(string, styledString.style));
                        }
                        _snowman3 += skippedLength;
                        _snowman4 = true;
                    }
                }
                if (!_snowman4) continue;
                if (_snowman3 > _snowman6) {
                    _snowman2.remove();
                    _snowman3 -= _snowman6;
                    continue;
                }
                string = _snowman5.substring(_snowman3);
                if (string.isEmpty()) {
                    _snowman2.remove();
                    break;
                }
                _snowman2.set(new StyledString(string, style));
                break;
            }
            this.joined = this.joined.substring(lineLength + skippedLength);
            return textCollector.getCombined();
        }

        @Nullable
        public StringVisitable collectRemainers() {
            TextCollector textCollector = new TextCollector();
            this.parts.forEach(textCollector::add);
            this.parts.clear();
            return textCollector.getRawCombined();
        }
    }

    static class StyledString
    implements StringVisitable {
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

    @FunctionalInterface
    public static interface LineWrappingConsumer {
        public void accept(Style var1, int var2, int var3);
    }

    class LineBreakingVisitor
    implements CharacterVisitor {
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
            this.maxWidth = Math.max(maxWidth, 1.0f);
        }

        @Override
        public boolean accept(int n, Style style, int n2) {
            _snowman = n + this.startOffset;
            switch (n2) {
                case 10: {
                    return this.breakLine(_snowman, style);
                }
                case 32: {
                    this.lastSpaceBreak = _snowman;
                    this.lastSpaceStyle = style;
                }
            }
            float f = TextHandler.this.widthRetriever.getWidth(n2, style);
            this.totalWidth += f;
            if (this.nonEmpty && this.totalWidth > this.maxWidth) {
                if (this.lastSpaceBreak != -1) {
                    return this.breakLine(this.lastSpaceBreak, this.lastSpaceStyle);
                }
                return this.breakLine(_snowman, style);
            }
            this.nonEmpty |= f != 0.0f;
            this.count = _snowman + Character.charCount(n2);
            return true;
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

    class WidthLimitingVisitor
    implements CharacterVisitor {
        private float widthLeft;
        private int length;

        public WidthLimitingVisitor(float maxWidth) {
            this.widthLeft = maxWidth;
        }

        @Override
        public boolean accept(int n, Style style, int n2) {
            this.widthLeft -= TextHandler.this.widthRetriever.getWidth(n2, style);
            if (this.widthLeft >= 0.0f) {
                this.length = n + Character.charCount(n2);
                return true;
            }
            return false;
        }

        public int getLength() {
            return this.length;
        }

        public void resetLength() {
            this.length = 0;
        }
    }

    @FunctionalInterface
    public static interface WidthRetriever {
        public float getWidth(int var1, Style var2);
    }
}

