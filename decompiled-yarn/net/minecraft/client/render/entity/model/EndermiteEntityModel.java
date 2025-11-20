package net.minecraft.client.render.entity.model;

import java.util.Arrays;
import net.minecraft.client.model.ModelPart;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.MathHelper;

public class EndermiteEntityModel<T extends Entity> extends CompositeEntityModel<T> {
   private static final int[][] field_3366 = new int[][]{{4, 3, 2}, {6, 4, 5}, {3, 3, 1}, {1, 2, 1}};
   private static final int[][] field_3369 = new int[][]{{0, 0}, {0, 5}, {0, 14}, {0, 18}};
   private static final int field_3367 = field_3366.length;
   private final ModelPart[] field_3368 = new ModelPart[field_3367];

   public EndermiteEntityModel() {
      float _snowman = -3.5F;

      for (int _snowmanx = 0; _snowmanx < this.field_3368.length; _snowmanx++) {
         this.field_3368[_snowmanx] = new ModelPart(this, field_3369[_snowmanx][0], field_3369[_snowmanx][1]);
         this.field_3368[_snowmanx]
            .addCuboid(
               (float)field_3366[_snowmanx][0] * -0.5F,
               0.0F,
               (float)field_3366[_snowmanx][2] * -0.5F,
               (float)field_3366[_snowmanx][0],
               (float)field_3366[_snowmanx][1],
               (float)field_3366[_snowmanx][2]
            );
         this.field_3368[_snowmanx].setPivot(0.0F, (float)(24 - field_3366[_snowmanx][1]), _snowman);
         if (_snowmanx < this.field_3368.length - 1) {
            _snowman += (float)(field_3366[_snowmanx][2] + field_3366[_snowmanx + 1][2]) * 0.5F;
         }
      }
   }

   @Override
   public Iterable<ModelPart> getParts() {
      return Arrays.asList(this.field_3368);
   }

   @Override
   public void setAngles(T entity, float limbAngle, float limbDistance, float animationProgress, float headYaw, float headPitch) {
      for (int _snowman = 0; _snowman < this.field_3368.length; _snowman++) {
         this.field_3368[_snowman].yaw = MathHelper.cos(animationProgress * 0.9F + (float)_snowman * 0.15F * (float) Math.PI)
            * (float) Math.PI
            * 0.01F
            * (float)(1 + Math.abs(_snowman - 2));
         this.field_3368[_snowman].pivotX = MathHelper.sin(animationProgress * 0.9F + (float)_snowman * 0.15F * (float) Math.PI)
            * (float) Math.PI
            * 0.1F
            * (float)Math.abs(_snowman - 2);
      }
   }
}
