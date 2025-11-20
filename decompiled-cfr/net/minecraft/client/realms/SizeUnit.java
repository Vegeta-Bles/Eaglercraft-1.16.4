/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.client.realms;

import java.util.Locale;

public enum SizeUnit {
    B,
    KB,
    MB,
    GB;


    public static SizeUnit getLargestUnit(long bytes) {
        if (bytes < 1024L) {
            return B;
        }
        try {
            int n = (int)(Math.log(bytes) / Math.log(1024.0));
            String _snowman2 = String.valueOf("KMGTPE".charAt(n - 1));
            return SizeUnit.valueOf(_snowman2 + "B");
        }
        catch (Exception exception) {
            return GB;
        }
    }

    public static double convertToUnit(long bytes, SizeUnit unit) {
        if (unit == B) {
            return bytes;
        }
        return (double)bytes / Math.pow(1024.0, unit.ordinal());
    }

    public static String getUserFriendlyString(long bytes) {
        int n = 1024;
        if (bytes < 1024L) {
            return bytes + " B";
        }
        _snowman = (int)(Math.log(bytes) / Math.log(1024.0));
        String _snowman2 = "KMGTPE".charAt(_snowman - 1) + "";
        return String.format(Locale.ROOT, "%.1f %sB", (double)bytes / Math.pow(1024.0, _snowman), _snowman2);
    }

    public static String humanReadableSize(long bytes, SizeUnit unit) {
        return String.format("%." + (unit == GB ? "1" : "0") + "f %s", SizeUnit.convertToUnit(bytes, unit), unit.name());
    }
}

