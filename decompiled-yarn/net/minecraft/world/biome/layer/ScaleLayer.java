package net.minecraft.world.biome.layer;

import net.minecraft.world.biome.layer.type.ParentedLayer;
import net.minecraft.world.biome.layer.util.LayerSampleContext;
import net.minecraft.world.biome.layer.util.LayerSampler;

public enum ScaleLayer implements ParentedLayer {
   NORMAL,
   FUZZY {
      @Override
      protected int sample(LayerSampleContext<?> context, int _snowman, int _snowman, int _snowman, int _snowman) {
         return context.choose(_snowman, _snowman, _snowman, _snowman);
      }
   };

   private ScaleLayer() {
   }

   @Override
   public int transformX(int x) {
      return x >> 1;
   }

   @Override
   public int transformZ(int y) {
      return y >> 1;
   }

   @Override
   public int sample(LayerSampleContext<?> context, LayerSampler parent, int x, int z) {
      int _snowman = parent.sample(this.transformX(x), this.transformZ(z));
      context.initSeed((long)(x >> 1 << 1), (long)(z >> 1 << 1));
      int _snowmanx = x & 1;
      int _snowmanxx = z & 1;
      if (_snowmanx == 0 && _snowmanxx == 0) {
         return _snowman;
      } else {
         int _snowmanxxx = parent.sample(this.transformX(x), this.transformZ(z + 1));
         int _snowmanxxxx = context.choose(_snowman, _snowmanxxx);
         if (_snowmanx == 0 && _snowmanxx == 1) {
            return _snowmanxxxx;
         } else {
            int _snowmanxxxxx = parent.sample(this.transformX(x + 1), this.transformZ(z));
            int _snowmanxxxxxx = context.choose(_snowman, _snowmanxxxxx);
            if (_snowmanx == 1 && _snowmanxx == 0) {
               return _snowmanxxxxxx;
            } else {
               int _snowmanxxxxxxx = parent.sample(this.transformX(x + 1), this.transformZ(z + 1));
               return this.sample(context, _snowman, _snowmanxxxxx, _snowmanxxx, _snowmanxxxxxxx);
            }
         }
      }
   }

   protected int sample(LayerSampleContext<?> context, int _snowman, int _snowman, int _snowman, int _snowman) {
      if (_snowman == _snowman && _snowman == _snowman) {
         return _snowman;
      } else if (_snowman == _snowman && _snowman == _snowman) {
         return _snowman;
      } else if (_snowman == _snowman && _snowman == _snowman) {
         return _snowman;
      } else if (_snowman == _snowman && _snowman == _snowman) {
         return _snowman;
      } else if (_snowman == _snowman && _snowman != _snowman) {
         return _snowman;
      } else if (_snowman == _snowman && _snowman != _snowman) {
         return _snowman;
      } else if (_snowman == _snowman && _snowman != _snowman) {
         return _snowman;
      } else if (_snowman == _snowman && _snowman != _snowman) {
         return _snowman;
      } else if (_snowman == _snowman && _snowman != _snowman) {
         return _snowman;
      } else {
         return _snowman == _snowman && _snowman != _snowman ? _snowman : context.choose(_snowman, _snowman, _snowman, _snowman);
      }
   }
}
