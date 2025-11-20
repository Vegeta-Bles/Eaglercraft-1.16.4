/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.stat;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.NumberFormat;
import java.util.Locale;
import net.minecraft.util.Util;

public interface StatFormatter {
    public static final DecimalFormat DECIMAL_FORMAT = Util.make(new DecimalFormat("########0.00"), decimalFormat -> decimalFormat.setDecimalFormatSymbols(DecimalFormatSymbols.getInstance(Locale.ROOT)));
    public static final StatFormatter DEFAULT = NumberFormat.getIntegerInstance(Locale.US)::format;
    public static final StatFormatter DIVIDE_BY_TEN = n -> DECIMAL_FORMAT.format((double)n * 0.1);
    public static final StatFormatter DISTANCE = n -> {
        double d = (double)n / 100.0;
        _snowman = d / 1000.0;
        if (_snowman > 0.5) {
            return DECIMAL_FORMAT.format(_snowman) + " km";
        }
        if (d > 0.5) {
            return DECIMAL_FORMAT.format(d) + " m";
        }
        return n + " cm";
    };
    public static final StatFormatter TIME = n -> {
        double d = (double)n / 20.0;
        _snowman = d / 60.0;
        _snowman = _snowman / 60.0;
        _snowman = _snowman / 24.0;
        _snowman = _snowman / 365.0;
        if (_snowman > 0.5) {
            return DECIMAL_FORMAT.format(_snowman) + " y";
        }
        if (_snowman > 0.5) {
            return DECIMAL_FORMAT.format(_snowman) + " d";
        }
        if (_snowman > 0.5) {
            return DECIMAL_FORMAT.format(_snowman) + " h";
        }
        if (_snowman > 0.5) {
            return DECIMAL_FORMAT.format(_snowman) + " m";
        }
        return d + " s";
    };

    public String format(int var1);
}

