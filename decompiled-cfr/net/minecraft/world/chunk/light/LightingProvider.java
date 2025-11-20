/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.annotation.Nullable
 */
package net.minecraft.world.chunk.light;

import javax.annotation.Nullable;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.util.math.ChunkSectionPos;
import net.minecraft.world.LightType;
import net.minecraft.world.chunk.ChunkNibbleArray;
import net.minecraft.world.chunk.ChunkProvider;
import net.minecraft.world.chunk.light.ChunkBlockLightProvider;
import net.minecraft.world.chunk.light.ChunkLightProvider;
import net.minecraft.world.chunk.light.ChunkLightingView;
import net.minecraft.world.chunk.light.ChunkSkyLightProvider;
import net.minecraft.world.chunk.light.LightingView;

public class LightingProvider
implements LightingView {
    @Nullable
    private final ChunkLightProvider<?, ?> blockLightProvider;
    @Nullable
    private final ChunkLightProvider<?, ?> skyLightProvider;

    public LightingProvider(ChunkProvider chunkProvider, boolean hasBlockLight, boolean hasSkyLight) {
        this.blockLightProvider = hasBlockLight ? new ChunkBlockLightProvider(chunkProvider) : null;
        this.skyLightProvider = hasSkyLight ? new ChunkSkyLightProvider(chunkProvider) : null;
    }

    public void checkBlock(BlockPos pos) {
        if (this.blockLightProvider != null) {
            this.blockLightProvider.checkBlock(pos);
        }
        if (this.skyLightProvider != null) {
            this.skyLightProvider.checkBlock(pos);
        }
    }

    public void addLightSource(BlockPos pos, int level) {
        if (this.blockLightProvider != null) {
            this.blockLightProvider.addLightSource(pos, level);
        }
    }

    public boolean hasUpdates() {
        if (this.skyLightProvider != null && this.skyLightProvider.hasUpdates()) {
            return true;
        }
        return this.blockLightProvider != null && this.blockLightProvider.hasUpdates();
    }

    public int doLightUpdates(int maxUpdateCount, boolean doSkylight, boolean skipEdgeLightPropagation) {
        if (this.blockLightProvider != null && this.skyLightProvider != null) {
            int n = maxUpdateCount / 2;
            _snowman = this.blockLightProvider.doLightUpdates(n, doSkylight, skipEdgeLightPropagation);
            _snowman = maxUpdateCount - n + _snowman;
            _snowman = this.skyLightProvider.doLightUpdates(_snowman, doSkylight, skipEdgeLightPropagation);
            if (_snowman == 0 && _snowman > 0) {
                return this.blockLightProvider.doLightUpdates(_snowman, doSkylight, skipEdgeLightPropagation);
            }
            return _snowman;
        }
        if (this.blockLightProvider != null) {
            return this.blockLightProvider.doLightUpdates(maxUpdateCount, doSkylight, skipEdgeLightPropagation);
        }
        if (this.skyLightProvider != null) {
            return this.skyLightProvider.doLightUpdates(maxUpdateCount, doSkylight, skipEdgeLightPropagation);
        }
        return maxUpdateCount;
    }

    @Override
    public void setSectionStatus(ChunkSectionPos pos, boolean notReady) {
        if (this.blockLightProvider != null) {
            this.blockLightProvider.setSectionStatus(pos, notReady);
        }
        if (this.skyLightProvider != null) {
            this.skyLightProvider.setSectionStatus(pos, notReady);
        }
    }

    public void setColumnEnabled(ChunkPos pos, boolean lightEnabled) {
        if (this.blockLightProvider != null) {
            this.blockLightProvider.setColumnEnabled(pos, lightEnabled);
        }
        if (this.skyLightProvider != null) {
            this.skyLightProvider.setColumnEnabled(pos, lightEnabled);
        }
    }

    public ChunkLightingView get(LightType lightType) {
        if (lightType == LightType.BLOCK) {
            if (this.blockLightProvider == null) {
                return ChunkLightingView.Empty.INSTANCE;
            }
            return this.blockLightProvider;
        }
        if (this.skyLightProvider == null) {
            return ChunkLightingView.Empty.INSTANCE;
        }
        return this.skyLightProvider;
    }

    public String displaySectionLevel(LightType lightType, ChunkSectionPos chunkSectionPos) {
        if (lightType == LightType.BLOCK) {
            if (this.blockLightProvider != null) {
                return this.blockLightProvider.displaySectionLevel(chunkSectionPos.asLong());
            }
        } else if (this.skyLightProvider != null) {
            return this.skyLightProvider.displaySectionLevel(chunkSectionPos.asLong());
        }
        return "n/a";
    }

    public void enqueueSectionData(LightType lightType, ChunkSectionPos pos, @Nullable ChunkNibbleArray nibbles, boolean bl) {
        if (lightType == LightType.BLOCK) {
            if (this.blockLightProvider != null) {
                this.blockLightProvider.enqueueSectionData(pos.asLong(), nibbles, bl);
            }
        } else if (this.skyLightProvider != null) {
            this.skyLightProvider.enqueueSectionData(pos.asLong(), nibbles, bl);
        }
    }

    public void setRetainData(ChunkPos pos, boolean retainData) {
        if (this.blockLightProvider != null) {
            this.blockLightProvider.setRetainColumn(pos, retainData);
        }
        if (this.skyLightProvider != null) {
            this.skyLightProvider.setRetainColumn(pos, retainData);
        }
    }

    public int getLight(BlockPos pos, int ambientDarkness) {
        int n = this.skyLightProvider == null ? 0 : this.skyLightProvider.getLightLevel(pos) - ambientDarkness;
        _snowman = this.blockLightProvider == null ? 0 : this.blockLightProvider.getLightLevel(pos);
        return Math.max(_snowman, n);
    }
}

