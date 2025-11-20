/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  it.unimi.dsi.fastutil.ints.IntSet
 *  it.unimi.dsi.fastutil.ints.IntSets
 *  javax.annotation.Nullable
 */
package net.minecraft.client.font;

import it.unimi.dsi.fastutil.ints.IntSet;
import it.unimi.dsi.fastutil.ints.IntSets;
import javax.annotation.Nullable;
import net.minecraft.client.font.BlankGlyph;
import net.minecraft.client.font.Font;
import net.minecraft.client.font.RenderableGlyph;

public class BlankFont
implements Font {
    @Override
    @Nullable
    public RenderableGlyph getGlyph(int codePoint) {
        return BlankGlyph.INSTANCE;
    }

    @Override
    public IntSet getProvidedGlyphs() {
        return IntSets.EMPTY_SET;
    }
}

