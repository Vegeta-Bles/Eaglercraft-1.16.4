/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.server.rcon;

import java.nio.charset.StandardCharsets;

public class BufferHelper {
    public static final char[] HEX_CHARS_LOOKUP = new char[]{'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f'};

    public static String getString(byte[] buf, int n, int n2) {
        _snowman = n2 - 1;
        int n3 = _snowman = n > _snowman ? _snowman : n;
        while (0 != buf[_snowman] && _snowman < _snowman) {
            ++_snowman;
        }
        return new String(buf, n, _snowman - n, StandardCharsets.UTF_8);
    }

    public static int getIntLE(byte[] buf, int start) {
        return BufferHelper.getIntLE(buf, start, buf.length);
    }

    public static int getIntLE(byte[] buf, int start, int limit) {
        if (0 > limit - start - 4) {
            return 0;
        }
        return buf[start + 3] << 24 | (buf[start + 2] & 0xFF) << 16 | (buf[start + 1] & 0xFF) << 8 | buf[start] & 0xFF;
    }

    public static int getIntBE(byte[] buf, int start, int limit) {
        if (0 > limit - start - 4) {
            return 0;
        }
        return buf[start] << 24 | (buf[start + 1] & 0xFF) << 16 | (buf[start + 2] & 0xFF) << 8 | buf[start + 3] & 0xFF;
    }

    public static String toHex(byte b) {
        return "" + HEX_CHARS_LOOKUP[(b & 0xF0) >>> 4] + HEX_CHARS_LOOKUP[b & 0xF];
    }
}

