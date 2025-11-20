package net.minecraft.client.render.entity.model;

import com.google.common.collect.ImmutableList;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.model.ModelPart;
import net.minecraft.entity.Entity;
import net.minecraft.entity.mob.GuardianEntity;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;

public class GuardianEntityModel extends CompositeEntityModel<GuardianEntity> {
   private static final float[] field_17131 = new float[]{1.75F, 0.25F, 0.0F, 0.0F, 0.5F, 0.5F, 0.5F, 0.5F, 1.25F, 0.75F, 0.0F, 0.0F};
   private static final float[] field_17132 = new float[]{0.0F, 0.0F, 0.0F, 0.0F, 0.25F, 1.75F, 1.25F, 0.75F, 0.0F, 0.0F, 0.0F, 0.0F};
   private static final float[] field_17133 = new float[]{0.0F, 0.0F, 0.25F, 1.75F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.75F, 1.25F};
   private static final float[] field_17134 = new float[]{0.0F, 0.0F, 8.0F, -8.0F, -8.0F, 8.0F, 8.0F, -8.0F, 0.0F, 0.0F, 8.0F, -8.0F};
   private static final float[] field_17135 = new float[]{-8.0F, -8.0F, -8.0F, -8.0F, 0.0F, 0.0F, 0.0F, 0.0F, 8.0F, 8.0F, 8.0F, 8.0F};
   private static final float[] field_17136 = new float[]{8.0F, -8.0F, 0.0F, 0.0F, -8.0F, -8.0F, 8.0F, 8.0F, 8.0F, -8.0F, 0.0F, 0.0F};
   private final ModelPart body;
   private final ModelPart eye;
   private final ModelPart[] field_3380;
   private final ModelPart[] field_3378;

   public GuardianEntityModel() {
      this.textureWidth = 64;
      this.textureHeight = 64;
      this.field_3380 = new ModelPart[12];
      this.body = new ModelPart(this);
      this.body.setTextureOffset(0, 0).addCuboid(-6.0F, 10.0F, -8.0F, 12.0F, 12.0F, 16.0F);
      this.body.setTextureOffset(0, 28).addCuboid(-8.0F, 10.0F, -6.0F, 2.0F, 12.0F, 12.0F);
      this.body.setTextureOffset(0, 28).addCuboid(6.0F, 10.0F, -6.0F, 2.0F, 12.0F, 12.0F, true);
      this.body.setTextureOffset(16, 40).addCuboid(-6.0F, 8.0F, -6.0F, 12.0F, 2.0F, 12.0F);
      this.body.setTextureOffset(16, 40).addCuboid(-6.0F, 22.0F, -6.0F, 12.0F, 2.0F, 12.0F);

      for (int _snowman = 0; _snowman < this.field_3380.length; _snowman++) {
         this.field_3380[_snowman] = new ModelPart(this, 0, 0);
         this.field_3380[_snowman].addCuboid(-1.0F, -4.5F, -1.0F, 2.0F, 9.0F, 2.0F);
         this.body.addChild(this.field_3380[_snowman]);
      }

      this.eye = new ModelPart(this, 8, 0);
      this.eye.addCuboid(-1.0F, 15.0F, 0.0F, 2.0F, 2.0F, 1.0F);
      this.body.addChild(this.eye);
      this.field_3378 = new ModelPart[3];
      this.field_3378[0] = new ModelPart(this, 40, 0);
      this.field_3378[0].addCuboid(-2.0F, 14.0F, 7.0F, 4.0F, 4.0F, 8.0F);
      this.field_3378[1] = new ModelPart(this, 0, 54);
      this.field_3378[1].addCuboid(0.0F, 14.0F, 0.0F, 3.0F, 3.0F, 7.0F);
      this.field_3378[2] = new ModelPart(this);
      this.field_3378[2].setTextureOffset(41, 32).addCuboid(0.0F, 14.0F, 0.0F, 2.0F, 2.0F, 6.0F);
      this.field_3378[2].setTextureOffset(25, 19).addCuboid(1.0F, 10.5F, 3.0F, 1.0F, 9.0F, 9.0F);
      this.body.addChild(this.field_3378[0]);
      this.field_3378[0].addChild(this.field_3378[1]);
      this.field_3378[1].addChild(this.field_3378[2]);
      this.method_24185(0.0F, 0.0F);
   }

   @Override
   public Iterable<ModelPart> getParts() {
      return ImmutableList.of(this.body);
   }

   public void setAngles(GuardianEntity _snowman, float _snowman, float _snowman, float _snowman, float _snowman, float _snowman) {
      float _snowmanxxxxxx = _snowman - (float)_snowman.age;
      this.body.yaw = _snowman * (float) (Math.PI / 180.0);
      this.body.pitch = _snowman * (float) (Math.PI / 180.0);
      float _snowmanxxxxxxx = (1.0F - _snowman.getTailAngle(_snowmanxxxxxx)) * 0.55F;
      this.method_24185(_snowman, _snowmanxxxxxxx);
      this.eye.pivotZ = -8.25F;
      Entity _snowmanxxxxxxxx = MinecraftClient.getInstance().getCameraEntity();
      if (_snowman.hasBeamTarget()) {
         _snowmanxxxxxxxx = _snowman.getBeamTarget();
      }

      if (_snowmanxxxxxxxx != null) {
         Vec3d _snowmanxxxxxxxxx = _snowmanxxxxxxxx.getCameraPosVec(0.0F);
         Vec3d _snowmanxxxxxxxxxx = _snowman.getCameraPosVec(0.0F);
         double _snowmanxxxxxxxxxxx = _snowmanxxxxxxxxx.y - _snowmanxxxxxxxxxx.y;
         if (_snowmanxxxxxxxxxxx > 0.0) {
            this.eye.pivotY = 0.0F;
         } else {
            this.eye.pivotY = 1.0F;
         }

         Vec3d _snowmanxxxxxxxxxxxx = _snowman.getRotationVec(0.0F);
         _snowmanxxxxxxxxxxxx = new Vec3d(_snowmanxxxxxxxxxxxx.x, 0.0, _snowmanxxxxxxxxxxxx.z);
         Vec3d _snowmanxxxxxxxxxxxxx = new Vec3d(_snowmanxxxxxxxxxx.x - _snowmanxxxxxxxxx.x, 0.0, _snowmanxxxxxxxxxx.z - _snowmanxxxxxxxxx.z).normalize().rotateY((float) (Math.PI / 2));
         double _snowmanxxxxxxxxxxxxxx = _snowmanxxxxxxxxxxxx.dotProduct(_snowmanxxxxxxxxxxxxx);
         this.eye.pivotX = MathHelper.sqrt((float)Math.abs(_snowmanxxxxxxxxxxxxxx)) * 2.0F * (float)Math.signum(_snowmanxxxxxxxxxxxxxx);
      }

      this.eye.visible = true;
      float _snowmanxxxxxxxxx = _snowman.getSpikesExtension(_snowmanxxxxxx);
      this.field_3378[0].yaw = MathHelper.sin(_snowmanxxxxxxxxx) * (float) Math.PI * 0.05F;
      this.field_3378[1].yaw = MathHelper.sin(_snowmanxxxxxxxxx) * (float) Math.PI * 0.1F;
      this.field_3378[1].pivotX = -1.5F;
      this.field_3378[1].pivotY = 0.5F;
      this.field_3378[1].pivotZ = 14.0F;
      this.field_3378[2].yaw = MathHelper.sin(_snowmanxxxxxxxxx) * (float) Math.PI * 0.15F;
      this.field_3378[2].pivotX = 0.5F;
      this.field_3378[2].pivotY = 0.5F;
      this.field_3378[2].pivotZ = 6.0F;
   }

   private void method_24185(float _snowman, float _snowman) {
      for (int _snowmanxx = 0; _snowmanxx < 12; _snowmanxx++) {
         this.field_3380[_snowmanxx].pitch = (float) Math.PI * field_17131[_snowmanxx];
         this.field_3380[_snowmanxx].yaw = (float) Math.PI * field_17132[_snowmanxx];
         this.field_3380[_snowmanxx].roll = (float) Math.PI * field_17133[_snowmanxx];
         this.field_3380[_snowmanxx].pivotX = field_17134[_snowmanxx] * (1.0F + MathHelper.cos(_snowman * 1.5F + (float)_snowmanxx) * 0.01F - _snowman);
         this.field_3380[_snowmanxx].pivotY = 16.0F + field_17135[_snowmanxx] * (1.0F + MathHelper.cos(_snowman * 1.5F + (float)_snowmanxx) * 0.01F - _snowman);
         this.field_3380[_snowmanxx].pivotZ = field_17136[_snowmanxx] * (1.0F + MathHelper.cos(_snowman * 1.5F + (float)_snowmanxx) * 0.01F - _snowman);
      }
   }
}
