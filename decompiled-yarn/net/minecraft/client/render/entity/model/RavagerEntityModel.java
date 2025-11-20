package net.minecraft.client.render.entity.model;

import com.google.common.collect.ImmutableList;
import net.minecraft.client.model.ModelPart;
import net.minecraft.entity.mob.RavagerEntity;
import net.minecraft.util.math.MathHelper;

public class RavagerEntityModel extends CompositeEntityModel<RavagerEntity> {
   private final ModelPart head;
   private final ModelPart jaw;
   private final ModelPart torso;
   private final ModelPart rightBackLeg;
   private final ModelPart leftBackLeg;
   private final ModelPart rightFrontLeg;
   private final ModelPart leftFrontLeg;
   private final ModelPart neck;

   public RavagerEntityModel() {
      this.textureWidth = 128;
      this.textureHeight = 128;
      int _snowman = 16;
      float _snowmanx = 0.0F;
      this.neck = new ModelPart(this);
      this.neck.setPivot(0.0F, -7.0F, -1.5F);
      this.neck.setTextureOffset(68, 73).addCuboid(-5.0F, -1.0F, -18.0F, 10.0F, 10.0F, 18.0F, 0.0F);
      this.head = new ModelPart(this);
      this.head.setPivot(0.0F, 16.0F, -17.0F);
      this.head.setTextureOffset(0, 0).addCuboid(-8.0F, -20.0F, -14.0F, 16.0F, 20.0F, 16.0F, 0.0F);
      this.head.setTextureOffset(0, 0).addCuboid(-2.0F, -6.0F, -18.0F, 4.0F, 8.0F, 4.0F, 0.0F);
      ModelPart _snowmanxx = new ModelPart(this);
      _snowmanxx.setPivot(-10.0F, -14.0F, -8.0F);
      _snowmanxx.setTextureOffset(74, 55).addCuboid(0.0F, -14.0F, -2.0F, 2.0F, 14.0F, 4.0F, 0.0F);
      _snowmanxx.pitch = 1.0995574F;
      this.head.addChild(_snowmanxx);
      ModelPart _snowmanxxx = new ModelPart(this);
      _snowmanxxx.mirror = true;
      _snowmanxxx.setPivot(8.0F, -14.0F, -8.0F);
      _snowmanxxx.setTextureOffset(74, 55).addCuboid(0.0F, -14.0F, -2.0F, 2.0F, 14.0F, 4.0F, 0.0F);
      _snowmanxxx.pitch = 1.0995574F;
      this.head.addChild(_snowmanxxx);
      this.jaw = new ModelPart(this);
      this.jaw.setPivot(0.0F, -2.0F, 2.0F);
      this.jaw.setTextureOffset(0, 36).addCuboid(-8.0F, 0.0F, -16.0F, 16.0F, 3.0F, 16.0F, 0.0F);
      this.head.addChild(this.jaw);
      this.neck.addChild(this.head);
      this.torso = new ModelPart(this);
      this.torso.setTextureOffset(0, 55).addCuboid(-7.0F, -10.0F, -7.0F, 14.0F, 16.0F, 20.0F, 0.0F);
      this.torso.setTextureOffset(0, 91).addCuboid(-6.0F, 6.0F, -7.0F, 12.0F, 13.0F, 18.0F, 0.0F);
      this.torso.setPivot(0.0F, 1.0F, 2.0F);
      this.rightBackLeg = new ModelPart(this, 96, 0);
      this.rightBackLeg.addCuboid(-4.0F, 0.0F, -4.0F, 8.0F, 37.0F, 8.0F, 0.0F);
      this.rightBackLeg.setPivot(-8.0F, -13.0F, 18.0F);
      this.leftBackLeg = new ModelPart(this, 96, 0);
      this.leftBackLeg.mirror = true;
      this.leftBackLeg.addCuboid(-4.0F, 0.0F, -4.0F, 8.0F, 37.0F, 8.0F, 0.0F);
      this.leftBackLeg.setPivot(8.0F, -13.0F, 18.0F);
      this.rightFrontLeg = new ModelPart(this, 64, 0);
      this.rightFrontLeg.addCuboid(-4.0F, 0.0F, -4.0F, 8.0F, 37.0F, 8.0F, 0.0F);
      this.rightFrontLeg.setPivot(-8.0F, -13.0F, -5.0F);
      this.leftFrontLeg = new ModelPart(this, 64, 0);
      this.leftFrontLeg.mirror = true;
      this.leftFrontLeg.addCuboid(-4.0F, 0.0F, -4.0F, 8.0F, 37.0F, 8.0F, 0.0F);
      this.leftFrontLeg.setPivot(8.0F, -13.0F, -5.0F);
   }

   @Override
   public Iterable<ModelPart> getParts() {
      return ImmutableList.of(this.neck, this.torso, this.rightBackLeg, this.leftBackLeg, this.rightFrontLeg, this.leftFrontLeg);
   }

   public void setAngles(RavagerEntity _snowman, float _snowman, float _snowman, float _snowman, float _snowman, float _snowman) {
      this.head.pitch = _snowman * (float) (Math.PI / 180.0);
      this.head.yaw = _snowman * (float) (Math.PI / 180.0);
      this.torso.pitch = (float) (Math.PI / 2);
      float _snowmanxxxxxx = 0.4F * _snowman;
      this.rightBackLeg.pitch = MathHelper.cos(_snowman * 0.6662F) * _snowmanxxxxxx;
      this.leftBackLeg.pitch = MathHelper.cos(_snowman * 0.6662F + (float) Math.PI) * _snowmanxxxxxx;
      this.rightFrontLeg.pitch = MathHelper.cos(_snowman * 0.6662F + (float) Math.PI) * _snowmanxxxxxx;
      this.leftFrontLeg.pitch = MathHelper.cos(_snowman * 0.6662F) * _snowmanxxxxxx;
   }

   public void animateModel(RavagerEntity _snowman, float _snowman, float _snowman, float _snowman) {
      super.animateModel(_snowman, _snowman, _snowman, _snowman);
      int _snowmanxxxx = _snowman.getStunTick();
      int _snowmanxxxxx = _snowman.getRoarTick();
      int _snowmanxxxxxx = 20;
      int _snowmanxxxxxxx = _snowman.getAttackTick();
      int _snowmanxxxxxxxx = 10;
      if (_snowmanxxxxxxx > 0) {
         float _snowmanxxxxxxxxx = MathHelper.method_24504((float)_snowmanxxxxxxx - _snowman, 10.0F);
         float _snowmanxxxxxxxxxx = (1.0F + _snowmanxxxxxxxxx) * 0.5F;
         float _snowmanxxxxxxxxxxx = _snowmanxxxxxxxxxx * _snowmanxxxxxxxxxx * _snowmanxxxxxxxxxx * 12.0F;
         float _snowmanxxxxxxxxxxxx = _snowmanxxxxxxxxxxx * MathHelper.sin(this.neck.pitch);
         this.neck.pivotZ = -6.5F + _snowmanxxxxxxxxxxx;
         this.neck.pivotY = -7.0F - _snowmanxxxxxxxxxxxx;
         float _snowmanxxxxxxxxxxxxx = MathHelper.sin(((float)_snowmanxxxxxxx - _snowman) / 10.0F * (float) Math.PI * 0.25F);
         this.jaw.pitch = (float) (Math.PI / 2) * _snowmanxxxxxxxxxxxxx;
         if (_snowmanxxxxxxx > 5) {
            this.jaw.pitch = MathHelper.sin(((float)(-4 + _snowmanxxxxxxx) - _snowman) / 4.0F) * (float) Math.PI * 0.4F;
         } else {
            this.jaw.pitch = (float) (Math.PI / 20) * MathHelper.sin((float) Math.PI * ((float)_snowmanxxxxxxx - _snowman) / 10.0F);
         }
      } else {
         float _snowmanxxxxxxxxx = -1.0F;
         float _snowmanxxxxxxxxxx = -1.0F * MathHelper.sin(this.neck.pitch);
         this.neck.pivotX = 0.0F;
         this.neck.pivotY = -7.0F - _snowmanxxxxxxxxxx;
         this.neck.pivotZ = 5.5F;
         boolean _snowmanxxxxxxxxxxx = _snowmanxxxx > 0;
         this.neck.pitch = _snowmanxxxxxxxxxxx ? 0.21991149F : 0.0F;
         this.jaw.pitch = (float) Math.PI * (_snowmanxxxxxxxxxxx ? 0.05F : 0.01F);
         if (_snowmanxxxxxxxxxxx) {
            double _snowmanxxxxxxxxxxxx = (double)_snowmanxxxx / 40.0;
            this.neck.pivotX = (float)Math.sin(_snowmanxxxxxxxxxxxx * 10.0) * 3.0F;
         } else if (_snowmanxxxxx > 0) {
            float _snowmanxxxxxxxxxxxx = MathHelper.sin(((float)(20 - _snowmanxxxxx) - _snowman) / 20.0F * (float) Math.PI * 0.25F);
            this.jaw.pitch = (float) (Math.PI / 2) * _snowmanxxxxxxxxxxxx;
         }
      }
   }
}
