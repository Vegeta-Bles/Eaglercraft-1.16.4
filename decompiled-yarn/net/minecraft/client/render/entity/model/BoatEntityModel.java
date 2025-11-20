package net.minecraft.client.render.entity.model;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableList.Builder;
import java.util.Arrays;
import net.minecraft.client.model.ModelPart;
import net.minecraft.entity.vehicle.BoatEntity;
import net.minecraft.util.math.MathHelper;

public class BoatEntityModel extends CompositeEntityModel<BoatEntity> {
   private final ModelPart[] paddles = new ModelPart[2];
   private final ModelPart bottom;
   private final ImmutableList<ModelPart> parts;

   public BoatEntityModel() {
      ModelPart[] _snowman = new ModelPart[]{
         new ModelPart(this, 0, 0).setTextureSize(128, 64),
         new ModelPart(this, 0, 19).setTextureSize(128, 64),
         new ModelPart(this, 0, 27).setTextureSize(128, 64),
         new ModelPart(this, 0, 35).setTextureSize(128, 64),
         new ModelPart(this, 0, 43).setTextureSize(128, 64)
      };
      int _snowmanx = 32;
      int _snowmanxx = 6;
      int _snowmanxxx = 20;
      int _snowmanxxxx = 4;
      int _snowmanxxxxx = 28;
      _snowman[0].addCuboid(-14.0F, -9.0F, -3.0F, 28.0F, 16.0F, 3.0F, 0.0F);
      _snowman[0].setPivot(0.0F, 3.0F, 1.0F);
      _snowman[1].addCuboid(-13.0F, -7.0F, -1.0F, 18.0F, 6.0F, 2.0F, 0.0F);
      _snowman[1].setPivot(-15.0F, 4.0F, 4.0F);
      _snowman[2].addCuboid(-8.0F, -7.0F, -1.0F, 16.0F, 6.0F, 2.0F, 0.0F);
      _snowman[2].setPivot(15.0F, 4.0F, 0.0F);
      _snowman[3].addCuboid(-14.0F, -7.0F, -1.0F, 28.0F, 6.0F, 2.0F, 0.0F);
      _snowman[3].setPivot(0.0F, 4.0F, -9.0F);
      _snowman[4].addCuboid(-14.0F, -7.0F, -1.0F, 28.0F, 6.0F, 2.0F, 0.0F);
      _snowman[4].setPivot(0.0F, 4.0F, 9.0F);
      _snowman[0].pitch = (float) (Math.PI / 2);
      _snowman[1].yaw = (float) (Math.PI * 3.0 / 2.0);
      _snowman[2].yaw = (float) (Math.PI / 2);
      _snowman[3].yaw = (float) Math.PI;
      this.paddles[0] = this.makePaddle(true);
      this.paddles[0].setPivot(3.0F, -5.0F, 9.0F);
      this.paddles[1] = this.makePaddle(false);
      this.paddles[1].setPivot(3.0F, -5.0F, -9.0F);
      this.paddles[1].yaw = (float) Math.PI;
      this.paddles[0].roll = (float) (Math.PI / 16);
      this.paddles[1].roll = (float) (Math.PI / 16);
      this.bottom = new ModelPart(this, 0, 0).setTextureSize(128, 64);
      this.bottom.addCuboid(-14.0F, -9.0F, -3.0F, 28.0F, 16.0F, 3.0F, 0.0F);
      this.bottom.setPivot(0.0F, -3.0F, 1.0F);
      this.bottom.pitch = (float) (Math.PI / 2);
      Builder<ModelPart> _snowmanxxxxxx = ImmutableList.builder();
      _snowmanxxxxxx.addAll(Arrays.asList(_snowman));
      _snowmanxxxxxx.addAll(Arrays.asList(this.paddles));
      this.parts = _snowmanxxxxxx.build();
   }

   public void setAngles(BoatEntity _snowman, float _snowman, float _snowman, float _snowman, float _snowman, float _snowman) {
      this.setPaddleAngle(_snowman, 0, _snowman);
      this.setPaddleAngle(_snowman, 1, _snowman);
   }

   public ImmutableList<ModelPart> getParts() {
      return this.parts;
   }

   public ModelPart getBottom() {
      return this.bottom;
   }

   protected ModelPart makePaddle(boolean isLeft) {
      ModelPart _snowman = new ModelPart(this, 62, isLeft ? 0 : 20).setTextureSize(128, 64);
      int _snowmanx = 20;
      int _snowmanxx = 7;
      int _snowmanxxx = 6;
      float _snowmanxxxx = -5.0F;
      _snowman.addCuboid(-1.0F, 0.0F, -5.0F, 2.0F, 2.0F, 18.0F);
      _snowman.addCuboid(isLeft ? -1.001F : 0.001F, -3.0F, 8.0F, 1.0F, 6.0F, 7.0F);
      return _snowman;
   }

   protected void setPaddleAngle(BoatEntity boat, int paddle, float angle) {
      float _snowman = boat.interpolatePaddlePhase(paddle, angle);
      ModelPart _snowmanx = this.paddles[paddle];
      _snowmanx.pitch = (float)MathHelper.clampedLerp((float) (-Math.PI / 3), (float) (-Math.PI / 12), (double)((MathHelper.sin(-_snowman) + 1.0F) / 2.0F));
      _snowmanx.yaw = (float)MathHelper.clampedLerp((float) (-Math.PI / 4), (float) (Math.PI / 4), (double)((MathHelper.sin(-_snowman + 1.0F) + 1.0F) / 2.0F));
      if (paddle == 1) {
         _snowmanx.yaw = (float) Math.PI - _snowmanx.yaw;
      }
   }
}
