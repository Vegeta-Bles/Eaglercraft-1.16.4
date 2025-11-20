/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  it.unimi.dsi.fastutil.ints.Int2IntFunction
 */
package net.minecraft.client.render.block.entity;

import it.unimi.dsi.fastutil.ints.Int2IntFunction;
import net.minecraft.block.DoubleBlockProperties;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.client.render.LightmapTextureManager;
import net.minecraft.client.render.WorldRenderer;

public class LightmapCoordinatesRetriever<S extends BlockEntity>
implements DoubleBlockProperties.PropertyRetriever<S, Int2IntFunction> {
    @Override
    public Int2IntFunction getFromBoth(S s, S s2) {
        return n -> {
            _snowman = WorldRenderer.getLightmapCoordinates(s.getWorld(), s.getPos());
            _snowman = WorldRenderer.getLightmapCoordinates(s2.getWorld(), s2.getPos());
            _snowman = LightmapTextureManager.getBlockLightCoordinates(_snowman);
            _snowman = LightmapTextureManager.getBlockLightCoordinates(_snowman);
            _snowman = LightmapTextureManager.getSkyLightCoordinates(_snowman);
            _snowman = LightmapTextureManager.getSkyLightCoordinates(_snowman);
            return LightmapTextureManager.pack(Math.max(_snowman, _snowman), Math.max(_snowman, _snowman));
        };
    }

    @Override
    public Int2IntFunction getFrom(S s) {
        return n -> n;
    }

    @Override
    public Int2IntFunction getFallback() {
        return n -> n;
    }

    @Override
    public /* synthetic */ Object getFallback() {
        return this.getFallback();
    }
}

