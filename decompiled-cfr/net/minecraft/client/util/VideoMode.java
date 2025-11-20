/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.annotation.Nullable
 *  org.lwjgl.glfw.GLFWVidMode
 *  org.lwjgl.glfw.GLFWVidMode$Buffer
 */
package net.minecraft.client.util;

import java.util.Objects;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.annotation.Nullable;
import org.lwjgl.glfw.GLFWVidMode;

public final class VideoMode {
    private final int width;
    private final int height;
    private final int redBits;
    private final int greenBits;
    private final int blueBits;
    private final int refreshRate;
    private static final Pattern PATTERN = Pattern.compile("(\\d+)x(\\d+)(?:@(\\d+)(?::(\\d+))?)?");

    public VideoMode(int n, int n2, int n3, int n4, int n5, int n6) {
        this.width = n;
        this.height = n2;
        this.redBits = n3;
        this.greenBits = n4;
        this.blueBits = n5;
        this.refreshRate = n6;
    }

    public VideoMode(GLFWVidMode.Buffer buffer) {
        this.width = buffer.width();
        this.height = buffer.height();
        this.redBits = buffer.redBits();
        this.greenBits = buffer.greenBits();
        this.blueBits = buffer.blueBits();
        this.refreshRate = buffer.refreshRate();
    }

    public VideoMode(GLFWVidMode gLFWVidMode) {
        this.width = gLFWVidMode.width();
        this.height = gLFWVidMode.height();
        this.redBits = gLFWVidMode.redBits();
        this.greenBits = gLFWVidMode.greenBits();
        this.blueBits = gLFWVidMode.blueBits();
        this.refreshRate = gLFWVidMode.refreshRate();
    }

    public int getWidth() {
        return this.width;
    }

    public int getHeight() {
        return this.height;
    }

    public int getRedBits() {
        return this.redBits;
    }

    public int getGreenBits() {
        return this.greenBits;
    }

    public int getBlueBits() {
        return this.blueBits;
    }

    public int getRefreshRate() {
        return this.refreshRate;
    }

    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || this.getClass() != o.getClass()) {
            return false;
        }
        VideoMode videoMode = (VideoMode)o;
        return this.width == videoMode.width && this.height == videoMode.height && this.redBits == videoMode.redBits && this.greenBits == videoMode.greenBits && this.blueBits == videoMode.blueBits && this.refreshRate == videoMode.refreshRate;
    }

    public int hashCode() {
        return Objects.hash(this.width, this.height, this.redBits, this.greenBits, this.blueBits, this.refreshRate);
    }

    public String toString() {
        return String.format("%sx%s@%s (%sbit)", this.width, this.height, this.refreshRate, this.redBits + this.greenBits + this.blueBits);
    }

    public static Optional<VideoMode> fromString(@Nullable String string) {
        if (string == null) {
            return Optional.empty();
        }
        try {
            Matcher matcher = PATTERN.matcher(string);
            if (matcher.matches()) {
                int n = Integer.parseInt(matcher.group(1));
                _snowman = Integer.parseInt(matcher.group(2));
                String _snowman2 = matcher.group(3);
                _snowman = _snowman2 == null ? 60 : Integer.parseInt(_snowman2);
                String _snowman3 = matcher.group(4);
                _snowman = _snowman3 == null ? 24 : Integer.parseInt(_snowman3);
                _snowman = _snowman / 3;
                return Optional.of(new VideoMode(n, _snowman, _snowman, _snowman, _snowman, _snowman));
            }
        }
        catch (Exception exception) {
            // empty catch block
        }
        return Optional.empty();
    }

    public String asString() {
        return String.format("%sx%s@%s:%s", this.width, this.height, this.refreshRate, this.redBits + this.greenBits + this.blueBits);
    }
}

