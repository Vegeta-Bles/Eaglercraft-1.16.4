package net.minecraft.client.render.entity.model;

import com.google.common.collect.ImmutableList;
import net.minecraft.client.model.ModelPart;
import net.minecraft.entity.passive.StriderEntity;
import net.minecraft.util.math.MathHelper;

public class StriderEntityModel<T extends StriderEntity> extends CompositeEntityModel<T> {
   private final ModelPart field_23353;
   private final ModelPart field_23354;
   private final ModelPart field_23355;
   private final ModelPart field_23356;
   private final ModelPart field_23357;
   private final ModelPart field_23358;
   private final ModelPart field_23359;
   private final ModelPart field_23360;
   private final ModelPart field_23361;

   public StriderEntityModel() {
      this.textureWidth = 64;
      this.textureHeight = 128;
      this.field_23353 = new ModelPart(this, 0, 32);
      this.field_23353.setPivot(-4.0F, 8.0F, 0.0F);
      this.field_23353.addCuboid(-2.0F, 0.0F, -2.0F, 4.0F, 16.0F, 4.0F, 0.0F);
      this.field_23354 = new ModelPart(this, 0, 55);
      this.field_23354.setPivot(4.0F, 8.0F, 0.0F);
      this.field_23354.addCuboid(-2.0F, 0.0F, -2.0F, 4.0F, 16.0F, 4.0F, 0.0F);
      this.field_23355 = new ModelPart(this, 0, 0);
      this.field_23355.setPivot(0.0F, 1.0F, 0.0F);
      this.field_23355.addCuboid(-8.0F, -6.0F, -8.0F, 16.0F, 14.0F, 16.0F, 0.0F);
      this.field_23356 = new ModelPart(this, 16, 65);
      this.field_23356.setPivot(-8.0F, 4.0F, -8.0F);
      this.field_23356.addCuboid(-12.0F, 0.0F, 0.0F, 12.0F, 0.0F, 16.0F, 0.0F, true);
      this.method_26415(this.field_23356, 0.0F, 0.0F, -1.2217305F);
      this.field_23357 = new ModelPart(this, 16, 49);
      this.field_23357.setPivot(-8.0F, -1.0F, -8.0F);
      this.field_23357.addCuboid(-12.0F, 0.0F, 0.0F, 12.0F, 0.0F, 16.0F, 0.0F, true);
      this.method_26415(this.field_23357, 0.0F, 0.0F, -1.134464F);
      this.field_23358 = new ModelPart(this, 16, 33);
      this.field_23358.setPivot(-8.0F, -5.0F, -8.0F);
      this.field_23358.addCuboid(-12.0F, 0.0F, 0.0F, 12.0F, 0.0F, 16.0F, 0.0F, true);
      this.method_26415(this.field_23358, 0.0F, 0.0F, -0.87266463F);
      this.field_23359 = new ModelPart(this, 16, 33);
      this.field_23359.setPivot(8.0F, -6.0F, -8.0F);
      this.field_23359.addCuboid(0.0F, 0.0F, 0.0F, 12.0F, 0.0F, 16.0F, 0.0F);
      this.method_26415(this.field_23359, 0.0F, 0.0F, 0.87266463F);
      this.field_23360 = new ModelPart(this, 16, 49);
      this.field_23360.setPivot(8.0F, -2.0F, -8.0F);
      this.field_23360.addCuboid(0.0F, 0.0F, 0.0F, 12.0F, 0.0F, 16.0F, 0.0F);
      this.method_26415(this.field_23360, 0.0F, 0.0F, 1.134464F);
      this.field_23361 = new ModelPart(this, 16, 65);
      this.field_23361.setPivot(8.0F, 3.0F, -8.0F);
      this.field_23361.addCuboid(0.0F, 0.0F, 0.0F, 12.0F, 0.0F, 16.0F, 0.0F);
      this.method_26415(this.field_23361, 0.0F, 0.0F, 1.2217305F);
      this.field_23355.addChild(this.field_23356);
      this.field_23355.addChild(this.field_23357);
      this.field_23355.addChild(this.field_23358);
      this.field_23355.addChild(this.field_23359);
      this.field_23355.addChild(this.field_23360);
      this.field_23355.addChild(this.field_23361);
   }

   public void setAngles(StriderEntity _snowman, float _snowman, float _snowman, float _snowman, float _snowman, float _snowman) {
      _snowman = Math.min(0.25F, _snowman);
      if (_snowman.getPassengerList().size() <= 0) {
         this.field_23355.pitch = _snowman * (float) (Math.PI / 180.0);
         this.field_23355.yaw = _snowman * (float) (Math.PI / 180.0);
      } else {
         this.field_23355.pitch = 0.0F;
         this.field_23355.yaw = 0.0F;
      }

      float _snowmanxxxxxx = 1.5F;
      this.field_23355.roll = 0.1F * MathHelper.sin(_snowman * 1.5F) * 4.0F * _snowman;
      this.field_23355.pivotY = 2.0F;
      this.field_23355.pivotY = this.field_23355.pivotY - 2.0F * MathHelper.cos(_snowman * 1.5F) * 2.0F * _snowman;
      this.field_23354.pitch = MathHelper.sin(_snowman * 1.5F * 0.5F) * 2.0F * _snowman;
      this.field_23353.pitch = MathHelper.sin(_snowman * 1.5F * 0.5F + (float) Math.PI) * 2.0F * _snowman;
      this.field_23354.roll = (float) (Math.PI / 18) * MathHelper.cos(_snowman * 1.5F * 0.5F) * _snowman;
      this.field_23353.roll = (float) (Math.PI / 18) * MathHelper.cos(_snowman * 1.5F * 0.5F + (float) Math.PI) * _snowman;
      this.field_23354.pivotY = 8.0F + 2.0F * MathHelper.sin(_snowman * 1.5F * 0.5F + (float) Math.PI) * 2.0F * _snowman;
      this.field_23353.pivotY = 8.0F + 2.0F * MathHelper.sin(_snowman * 1.5F * 0.5F) * 2.0F * _snowman;
      this.field_23356.roll = -1.2217305F;
      this.field_23357.roll = -1.134464F;
      this.field_23358.roll = -0.87266463F;
      this.field_23359.roll = 0.87266463F;
      this.field_23360.roll = 1.134464F;
      this.field_23361.roll = 1.2217305F;
      float _snowmanxxxxxxx = MathHelper.cos(_snowman * 1.5F + (float) Math.PI) * _snowman;
      this.field_23356.roll += _snowmanxxxxxxx * 1.3F;
      this.field_23357.roll += _snowmanxxxxxxx * 1.2F;
      this.field_23358.roll += _snowmanxxxxxxx * 0.6F;
      this.field_23359.roll += _snowmanxxxxxxx * 0.6F;
      this.field_23360.roll += _snowmanxxxxxxx * 1.2F;
      this.field_23361.roll += _snowmanxxxxxxx * 1.3F;
      float _snowmanxxxxxxxx = 1.0F;
      float _snowmanxxxxxxxxx = 1.0F;
      this.field_23356.roll = this.field_23356.roll + 0.05F * MathHelper.sin(_snowman * 1.0F * -0.4F);
      this.field_23357.roll = this.field_23357.roll + 0.1F * MathHelper.sin(_snowman * 1.0F * 0.2F);
      this.field_23358.roll = this.field_23358.roll + 0.1F * MathHelper.sin(_snowman * 1.0F * 0.4F);
      this.field_23359.roll = this.field_23359.roll + 0.1F * MathHelper.sin(_snowman * 1.0F * 0.4F);
      this.field_23360.roll = this.field_23360.roll + 0.1F * MathHelper.sin(_snowman * 1.0F * 0.2F);
      this.field_23361.roll = this.field_23361.roll + 0.05F * MathHelper.sin(_snowman * 1.0F * -0.4F);
   }

   public void method_26415(ModelPart _snowman, float _snowman, float _snowman, float _snowman) {
      _snowman.pitch = _snowman;
      _snowman.yaw = _snowman;
      _snowman.roll = _snowman;
   }

   @Override
   public Iterable<ModelPart> getParts() {
      return ImmutableList.of(this.field_23355, this.field_23354, this.field_23353);
   }
}
