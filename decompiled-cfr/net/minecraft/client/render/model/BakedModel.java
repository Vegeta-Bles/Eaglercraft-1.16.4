/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.annotation.Nullable
 */
package net.minecraft.client.render.model;

import java.util.List;
import java.util.Random;
import javax.annotation.Nullable;
import net.minecraft.block.BlockState;
import net.minecraft.client.render.model.BakedQuad;
import net.minecraft.client.render.model.json.ModelOverrideList;
import net.minecraft.client.render.model.json.ModelTransformation;
import net.minecraft.client.texture.Sprite;
import net.minecraft.util.math.Direction;

public interface BakedModel {
    public List<BakedQuad> getQuads(@Nullable BlockState var1, @Nullable Direction var2, Random var3);

    public boolean useAmbientOcclusion();

    public boolean hasDepth();

    public boolean isSideLit();

    public boolean isBuiltin();

    public Sprite getSprite();

    public ModelTransformation getTransformation();

    public ModelOverrideList getOverrides();
}

