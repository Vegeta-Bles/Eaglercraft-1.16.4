package net.minecraft.client.render.entity.model;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableList.Builder;
import java.util.Arrays;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.model.ModelPart;
import net.minecraft.entity.vehicle.BoatEntity;
import net.minecraft.util.math.MathHelper;

@Environment(EnvType.CLIENT)
public class BoatEntityModel extends CompositeEntityModel<BoatEntity> {
   private final ModelPart[] paddles = new ModelPart[2];
   private final ModelPart bottom;
   private final ImmutableList<ModelPart> parts;

   public BoatEntityModel() {
      ModelPart[] lvs = new ModelPart[]{
         new ModelPart(this, 0, 0).setTextureSize(128, 64),
         new ModelPart(this, 0, 19).setTextureSize(128, 64),
         new ModelPart(this, 0, 27).setTextureSize(128, 64),
         new ModelPart(this, 0, 35).setTextureSize(128, 64),
         new ModelPart(this, 0, 43).setTextureSize(128, 64)
      };
      int i = 32;
      int j = 6;
      int k = 20;
      int l = 4;
      int m = 28;
      lvs[0].addCuboid(-14.0F, -9.0F, -3.0F, 28.0F, 16.0F, 3.0F, 0.0F);
      lvs[0].setPivot(0.0F, 3.0F, 1.0F);
      lvs[1].addCuboid(-13.0F, -7.0F, -1.0F, 18.0F, 6.0F, 2.0F, 0.0F);
      lvs[1].setPivot(-15.0F, 4.0F, 4.0F);
      lvs[2].addCuboid(-8.0F, -7.0F, -1.0F, 16.0F, 6.0F, 2.0F, 0.0F);
      lvs[2].setPivot(15.0F, 4.0F, 0.0F);
      lvs[3].addCuboid(-14.0F, -7.0F, -1.0F, 28.0F, 6.0F, 2.0F, 0.0F);
      lvs[3].setPivot(0.0F, 4.0F, -9.0F);
      lvs[4].addCuboid(-14.0F, -7.0F, -1.0F, 28.0F, 6.0F, 2.0F, 0.0F);
      lvs[4].setPivot(0.0F, 4.0F, 9.0F);
      lvs[0].pitch = (float) (Math.PI / 2);
      lvs[1].yaw = (float) (Math.PI * 3.0 / 2.0);
      lvs[2].yaw = (float) (Math.PI / 2);
      lvs[3].yaw = (float) Math.PI;
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
      Builder<ModelPart> builder = ImmutableList.builder();
      builder.addAll(Arrays.asList(lvs));
      builder.addAll(Arrays.asList(this.paddles));
      this.parts = builder.build();
   }

   public void setAngles(BoatEntity arg, float f, float g, float h, float i, float j) {
      this.setPaddleAngle(arg, 0, f);
      this.setPaddleAngle(arg, 1, f);
   }

   public ImmutableList<ModelPart> getParts() {
      return this.parts;
   }

   public ModelPart getBottom() {
      return this.bottom;
   }

   protected ModelPart makePaddle(boolean isLeft) {
      ModelPart lv = new ModelPart(this, 62, isLeft ? 0 : 20).setTextureSize(128, 64);
      int i = 20;
      int j = 7;
      int k = 6;
      float f = -5.0F;
      lv.addCuboid(-1.0F, 0.0F, -5.0F, 2.0F, 2.0F, 18.0F);
      lv.addCuboid(isLeft ? -1.001F : 0.001F, -3.0F, 8.0F, 1.0F, 6.0F, 7.0F);
      return lv;
   }

   protected void setPaddleAngle(BoatEntity boat, int paddle, float angle) {
      float g = boat.interpolatePaddlePhase(paddle, angle);
      ModelPart lv = this.paddles[paddle];
      lv.pitch = (float)MathHelper.clampedLerp((float) (-Math.PI / 3), (float) (-Math.PI / 12), (double)((MathHelper.sin(-g) + 1.0F) / 2.0F));
      lv.yaw = (float)MathHelper.clampedLerp((float) (-Math.PI / 4), (float) (Math.PI / 4), (double)((MathHelper.sin(-g + 1.0F) + 1.0F) / 2.0F));
      if (paddle == 1) {
         lv.yaw = (float) Math.PI - lv.yaw;
      }
   }
}
