package net.minecraft.client.render.entity.model;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableList.Builder;
import java.util.Arrays;
import net.minecraft.client.model.ModelPart;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.MathHelper;

public class SilverfishEntityModel<T extends Entity> extends CompositeEntityModel<T> {
   private final ModelPart[] body;
   private final ModelPart[] scales;
   private final ImmutableList<ModelPart> parts;
   private final float[] scaleSizes = new float[7];
   private static final int[][] segmentLocations = new int[][]{{3, 2, 2}, {4, 3, 2}, {6, 4, 3}, {3, 3, 3}, {2, 2, 3}, {2, 1, 2}, {1, 1, 2}};
   private static final int[][] segmentSizes = new int[][]{{0, 0}, {0, 4}, {0, 9}, {0, 16}, {0, 22}, {11, 0}, {13, 4}};

   public SilverfishEntityModel() {
      this.body = new ModelPart[7];
      float _snowman = -3.5F;

      for (int _snowmanx = 0; _snowmanx < this.body.length; _snowmanx++) {
         this.body[_snowmanx] = new ModelPart(this, segmentSizes[_snowmanx][0], segmentSizes[_snowmanx][1]);
         this.body[_snowmanx]
            .addCuboid(
               (float)segmentLocations[_snowmanx][0] * -0.5F,
               0.0F,
               (float)segmentLocations[_snowmanx][2] * -0.5F,
               (float)segmentLocations[_snowmanx][0],
               (float)segmentLocations[_snowmanx][1],
               (float)segmentLocations[_snowmanx][2]
            );
         this.body[_snowmanx].setPivot(0.0F, (float)(24 - segmentLocations[_snowmanx][1]), _snowman);
         this.scaleSizes[_snowmanx] = _snowman;
         if (_snowmanx < this.body.length - 1) {
            _snowman += (float)(segmentLocations[_snowmanx][2] + segmentLocations[_snowmanx + 1][2]) * 0.5F;
         }
      }

      this.scales = new ModelPart[3];
      this.scales[0] = new ModelPart(this, 20, 0);
      this.scales[0].addCuboid(-5.0F, 0.0F, (float)segmentLocations[2][2] * -0.5F, 10.0F, 8.0F, (float)segmentLocations[2][2]);
      this.scales[0].setPivot(0.0F, 16.0F, this.scaleSizes[2]);
      this.scales[1] = new ModelPart(this, 20, 11);
      this.scales[1].addCuboid(-3.0F, 0.0F, (float)segmentLocations[4][2] * -0.5F, 6.0F, 4.0F, (float)segmentLocations[4][2]);
      this.scales[1].setPivot(0.0F, 20.0F, this.scaleSizes[4]);
      this.scales[2] = new ModelPart(this, 20, 18);
      this.scales[2].addCuboid(-3.0F, 0.0F, (float)segmentLocations[4][2] * -0.5F, 6.0F, 5.0F, (float)segmentLocations[1][2]);
      this.scales[2].setPivot(0.0F, 19.0F, this.scaleSizes[1]);
      Builder<ModelPart> _snowmanxx = ImmutableList.builder();
      _snowmanxx.addAll(Arrays.asList(this.body));
      _snowmanxx.addAll(Arrays.asList(this.scales));
      this.parts = _snowmanxx.build();
   }

   public ImmutableList<ModelPart> getParts() {
      return this.parts;
   }

   @Override
   public void setAngles(T entity, float limbAngle, float limbDistance, float animationProgress, float headYaw, float headPitch) {
      for (int _snowman = 0; _snowman < this.body.length; _snowman++) {
         this.body[_snowman].yaw = MathHelper.cos(animationProgress * 0.9F + (float)_snowman * 0.15F * (float) Math.PI)
            * (float) Math.PI
            * 0.05F
            * (float)(1 + Math.abs(_snowman - 2));
         this.body[_snowman].pivotX = MathHelper.sin(animationProgress * 0.9F + (float)_snowman * 0.15F * (float) Math.PI) * (float) Math.PI * 0.2F * (float)Math.abs(_snowman - 2);
      }

      this.scales[0].yaw = this.body[2].yaw;
      this.scales[1].yaw = this.body[4].yaw;
      this.scales[1].pivotX = this.body[4].pivotX;
      this.scales[2].yaw = this.body[1].yaw;
      this.scales[2].pivotX = this.body[1].pivotX;
   }
}
