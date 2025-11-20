package net.minecraft.world.biome.source;

import net.minecraft.SharedConstants;
import net.minecraft.util.Util;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.registry.RegistryKey;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.BuiltinBiomes;
import net.minecraft.world.biome.layer.util.CachingLayerSampler;
import net.minecraft.world.biome.layer.util.LayerFactory;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class BiomeLayerSampler {
   private static final Logger LOGGER = LogManager.getLogger();
   private final CachingLayerSampler sampler;

   public BiomeLayerSampler(LayerFactory<CachingLayerSampler> layerFactory) {
      this.sampler = layerFactory.make();
   }

   public Biome sample(Registry<Biome> _snowman, int _snowman, int _snowman) {
      int _snowmanxxx = this.sampler.sample(_snowman, _snowman);
      RegistryKey<Biome> _snowmanxxxx = BuiltinBiomes.fromRawId(_snowmanxxx);
      if (_snowmanxxxx == null) {
         throw new IllegalStateException("Unknown biome id emitted by layers: " + _snowmanxxx);
      } else {
         Biome _snowmanxxxxx = _snowman.get(_snowmanxxxx);
         if (_snowmanxxxxx == null) {
            if (SharedConstants.isDevelopment) {
               throw (IllegalStateException)Util.throwOrPause(new IllegalStateException("Unknown biome id: " + _snowmanxxx));
            } else {
               LOGGER.warn("Unknown biome id: ", _snowmanxxx);
               return _snowman.get(BuiltinBiomes.fromRawId(0));
            }
         } else {
            return _snowmanxxxxx;
         }
      }
   }
}
