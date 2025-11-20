/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.collect.Lists
 *  com.ibm.icu.lang.UCharacter
 *  com.ibm.icu.text.ArabicShaping
 *  com.ibm.icu.text.Bidi
 *  com.ibm.icu.text.BidiRun
 */
package net.minecraft.client.resource.language;

import com.google.common.collect.Lists;
import com.ibm.icu.lang.UCharacter;
import com.ibm.icu.text.ArabicShaping;
import com.ibm.icu.text.Bidi;
import com.ibm.icu.text.BidiRun;
import java.util.ArrayList;
import net.minecraft.client.resource.language.TextReorderingProcessor;
import net.minecraft.text.OrderedText;
import net.minecraft.text.StringVisitable;

public class ReorderingUtil {
    public static OrderedText reorder(StringVisitable text, boolean rightToLeft) {
        TextReorderingProcessor textReorderingProcessor = TextReorderingProcessor.create(text, UCharacter::getMirror, ReorderingUtil::shapeArabic);
        Bidi _snowman2 = new Bidi(textReorderingProcessor.getString(), rightToLeft ? 127 : 126);
        _snowman2.setReorderingMode(0);
        ArrayList _snowman3 = Lists.newArrayList();
        int _snowman4 = _snowman2.countRuns();
        for (int i = 0; i < _snowman4; ++i) {
            BidiRun bidiRun = _snowman2.getVisualRun(i);
            _snowman3.addAll(textReorderingProcessor.process(bidiRun.getStart(), bidiRun.getLength(), bidiRun.isOddRun()));
        }
        return OrderedText.concat(_snowman3);
    }

    private static String shapeArabic(String string) {
        try {
            return new ArabicShaping(8).shape(string);
        }
        catch (Exception exception) {
            return string;
        }
    }
}

