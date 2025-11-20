/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.annotation.Nullable
 *  org.apache.commons.lang3.StringUtils
 */
package net.minecraft.util;

import java.util.regex.Pattern;
import javax.annotation.Nullable;
import org.apache.commons.lang3.StringUtils;

public class ChatUtil {
    private static final Pattern PATTERN = Pattern.compile("(?i)\\u00A7[0-9A-FK-OR]");

    public static String ticksToString(int ticks) {
        int n = ticks / 20;
        _snowman = n / 60;
        if ((n %= 60) < 10) {
            return _snowman + ":0" + n;
        }
        return _snowman + ":" + n;
    }

    public static String stripTextFormat(String text) {
        return PATTERN.matcher(text).replaceAll("");
    }

    public static boolean isEmpty(@Nullable String string) {
        return StringUtils.isEmpty((CharSequence)string);
    }
}

