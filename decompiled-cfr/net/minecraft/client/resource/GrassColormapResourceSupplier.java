/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.client.resource;

import java.io.IOException;
import net.minecraft.client.color.world.GrassColors;
import net.minecraft.client.util.RawTextureDataLoader;
import net.minecraft.resource.ResourceManager;
import net.minecraft.resource.SinglePreparationResourceReloadListener;
import net.minecraft.util.Identifier;
import net.minecraft.util.profiler.Profiler;

public class GrassColormapResourceSupplier
extends SinglePreparationResourceReloadListener<int[]> {
    private static final Identifier GRASS_COLORMAP_LOC = new Identifier("textures/colormap/grass.png");

    protected int[] method_18662(ResourceManager resourceManager, Profiler profiler) {
        try {
            return RawTextureDataLoader.loadRawTextureData(resourceManager, GRASS_COLORMAP_LOC);
        }
        catch (IOException iOException) {
            throw new IllegalStateException("Failed to load grass color texture", iOException);
        }
    }

    @Override
    protected void apply(int[] nArray, ResourceManager resourceManager, Profiler profiler) {
        GrassColors.setColorMap(nArray);
    }

    @Override
    protected /* synthetic */ Object prepare(ResourceManager manager, Profiler profiler) {
        return this.method_18662(manager, profiler);
    }
}

