/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  it.unimi.dsi.fastutil.ints.IntSet
 *  javax.annotation.Nullable
 */
package net.minecraft.client.font;

import it.unimi.dsi.fastutil.ints.IntSet;
import java.io.Closeable;
import javax.annotation.Nullable;
import net.minecraft.client.font.RenderableGlyph;

public interface Font
extends Closeable {
    @Override
    default public void close() {
    }

    @Nullable
    default public RenderableGlyph getGlyph(int codePoint) {
        return null;
    }

    public IntSet getProvidedGlyphs();
}

