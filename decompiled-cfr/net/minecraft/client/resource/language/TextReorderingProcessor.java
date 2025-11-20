/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.collect.ImmutableList
 *  com.google.common.collect.Lists
 *  it.unimi.dsi.fastutil.ints.Int2IntFunction
 */
package net.minecraft.client.resource.language;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.Lists;
import it.unimi.dsi.fastutil.ints.Int2IntFunction;
import java.util.ArrayList;
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
        }
        ArrayList arrayList = Lists.newArrayList();
        Style _snowman2 = this.styles.get(start);
        int _snowman3 = start;
        for (int i = 1; i < length; ++i) {
            _snowman = start + i;
            Style style = this.styles.get(_snowman);
            if (style.equals(_snowman2)) continue;
            String _snowman4 = this.string.substring(_snowman3, _snowman);
            arrayList.add(reverse ? OrderedText.styledStringMapped(_snowman4, _snowman2, this.reverser) : OrderedText.styledString(_snowman4, _snowman2));
            _snowman2 = style;
            _snowman3 = _snowman;
        }
        if (_snowman3 < start + length) {
            String string = this.string.substring(_snowman3, start + length);
            arrayList.add(reverse ? OrderedText.styledStringMapped(string, _snowman2, this.reverser) : OrderedText.styledString(string, _snowman2));
        }
        return reverse ? Lists.reverse((List)arrayList) : arrayList;
    }

    public static TextReorderingProcessor create(StringVisitable visitable, Int2IntFunction reverser, UnaryOperator<String> unaryOperator) {
        StringBuilder stringBuilder = new StringBuilder();
        ArrayList _snowman2 = Lists.newArrayList();
        visitable.visit((style2, text) -> {
            TextVisitFactory.visitFormatted(text, style2, (charIndex, style, codePoint) -> {
                stringBuilder.appendCodePoint(codePoint);
                int n = Character.charCount(codePoint);
                for (_snowman = 0; _snowman < n; ++_snowman) {
                    _snowman2.add(style);
                }
                return true;
            });
            return Optional.empty();
        }, Style.EMPTY);
        return new TextReorderingProcessor((String)unaryOperator.apply(stringBuilder.toString()), _snowman2, reverser);
    }
}

