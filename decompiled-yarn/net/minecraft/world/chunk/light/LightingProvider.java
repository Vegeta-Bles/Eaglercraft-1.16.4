package net.minecraft.world.chunk.light;

import javax.annotation.Nullable;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.util.math.ChunkSectionPos;
import net.minecraft.world.LightType;
import net.minecraft.world.chunk.ChunkNibbleArray;
import net.minecraft.world.chunk.ChunkProvider;

public class LightingProvider implements LightingView {
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
      return this.skyLightProvider != null && this.skyLightProvider.hasUpdates()
         ? true
         : this.blockLightProvider != null && this.blockLightProvider.hasUpdates();
   }

   public int doLightUpdates(int maxUpdateCount, boolean doSkylight, boolean skipEdgeLightPropagation) {
      if (this.blockLightProvider != null && this.skyLightProvider != null) {
         int _snowman = maxUpdateCount / 2;
         int _snowmanx = this.blockLightProvider.doLightUpdates(_snowman, doSkylight, skipEdgeLightPropagation);
         int _snowmanxx = maxUpdateCount - _snowman + _snowmanx;
         int _snowmanxxx = this.skyLightProvider.doLightUpdates(_snowmanxx, doSkylight, skipEdgeLightPropagation);
         return _snowmanx == 0 && _snowmanxxx > 0 ? this.blockLightProvider.doLightUpdates(_snowmanxxx, doSkylight, skipEdgeLightPropagation) : _snowmanxxx;
      } else if (this.blockLightProvider != null) {
         return this.blockLightProvider.doLightUpdates(maxUpdateCount, doSkylight, skipEdgeLightPropagation);
      } else {
         return this.skyLightProvider != null ? this.skyLightProvider.doLightUpdates(maxUpdateCount, doSkylight, skipEdgeLightPropagation) : maxUpdateCount;
      }
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
         return (ChunkLightingView)(this.blockLightProvider == null ? ChunkLightingView.Empty.INSTANCE : this.blockLightProvider);
      } else {
         return (ChunkLightingView)(this.skyLightProvider == null ? ChunkLightingView.Empty.INSTANCE : this.skyLightProvider);
      }
   }

   public String displaySectionLevel(LightType _snowman, ChunkSectionPos _snowman) {
      if (_snowman == LightType.BLOCK) {
         if (this.blockLightProvider != null) {
            return this.blockLightProvider.displaySectionLevel(_snowman.asLong());
         }
      } else if (this.skyLightProvider != null) {
         return this.skyLightProvider.displaySectionLevel(_snowman.asLong());
      }

      return "n/a";
   }

   public void enqueueSectionData(LightType lightType, ChunkSectionPos pos, @Nullable ChunkNibbleArray nibbles, boolean _snowman) {
      if (lightType == LightType.BLOCK) {
         if (this.blockLightProvider != null) {
            this.blockLightProvider.enqueueSectionData(pos.asLong(), nibbles, _snowman);
         }
      } else if (this.skyLightProvider != null) {
         this.skyLightProvider.enqueueSectionData(pos.asLong(), nibbles, _snowman);
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
      int _snowman = this.skyLightProvider == null ? 0 : this.skyLightProvider.getLightLevel(pos) - ambientDarkness;
      int _snowmanx = this.blockLightProvider == null ? 0 : this.blockLightProvider.getLightLevel(pos);
      return Math.max(_snowmanx, _snowman);
   }
}
