package net.minecraft.structure;

import net.minecraft.util.math.BlockBox;
import net.minecraft.world.gen.feature.FeatureConfig;
import net.minecraft.world.gen.feature.StructureFeature;

public abstract class MarginedStructureStart<C extends FeatureConfig> extends StructureStart<C> {
   public MarginedStructureStart(StructureFeature<C> _snowman, int _snowman, int _snowman, BlockBox _snowman, int _snowman, long _snowman) {
      super(_snowman, _snowman, _snowman, _snowman, _snowman, _snowman);
   }

   @Override
   protected void setBoundingBoxFromChildren() {
      super.setBoundingBoxFromChildren();
      int _snowman = 12;
      this.boundingBox.minX -= 12;
      this.boundingBox.minY -= 12;
      this.boundingBox.minZ -= 12;
      this.boundingBox.maxX += 12;
      this.boundingBox.maxY += 12;
      this.boundingBox.maxZ += 12;
   }
}
