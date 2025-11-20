/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.client.resource;

import java.io.IOException;
import net.minecraft.client.color.world.FoliageColors;
import net.minecraft.client.util.RawTextureDataLoader;
import net.minecraft.resource.ResourceManager;
import net.minecraft.resource.SinglePreparationResourceReloadListener;
import net.minecraft.util.Identifier;
import net.minecraft.util.profiler.Profiler;

public class FoliageColormapResourceSupplier
extends SinglePreparationResourceReloadListener<int[]> {
    private static final Identifier FOLIAGE_COLORMAP = new Identifier("textures/colormap/foliage.png");

    protected int[] reload(ResourceManager resourceManager, Profiler profiler) {
        try {
            return RawTextureDataLoader.loadRawTextureData(resourceManager, FOLIAGE_COLORMAP);
        }
        catch (IOException iOException) {
            throw new IllegalStateException("Failed to load foliage color texture", iOException);
        }
    }

    @Override
    protected void apply(int[] nArray, ResourceManager resourceManager, Profiler profiler) {
        FoliageColors.setColorMap(nArray);
    }

    @Override
    protected /* synthetic */ Object prepare(ResourceManager manager, Profiler profiler) {
        return this.reload(manager, profiler);
    }
}

