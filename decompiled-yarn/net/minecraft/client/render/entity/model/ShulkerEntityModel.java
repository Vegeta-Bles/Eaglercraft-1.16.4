package net.minecraft.client.render.entity.model;

import com.google.common.collect.ImmutableList;
import net.minecraft.client.model.ModelPart;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.entity.mob.ShulkerEntity;
import net.minecraft.util.math.MathHelper;

public class ShulkerEntityModel<T extends ShulkerEntity> extends CompositeEntityModel<T> {
   private final ModelPart bottomShell;
   private final ModelPart topShell = new ModelPart(64, 64, 0, 0);
   private final ModelPart head;

   public ShulkerEntityModel() {
      super(RenderLayer::getEntityCutoutNoCullZOffset);
      this.bottomShell = new ModelPart(64, 64, 0, 28);
      this.head = new ModelPart(64, 64, 0, 52);
      this.topShell.addCuboid(-8.0F, -16.0F, -8.0F, 16.0F, 12.0F, 16.0F);
      this.topShell.setPivot(0.0F, 24.0F, 0.0F);
      this.bottomShell.addCuboid(-8.0F, -8.0F, -8.0F, 16.0F, 8.0F, 16.0F);
      this.bottomShell.setPivot(0.0F, 24.0F, 0.0F);
      this.head.addCuboid(-3.0F, 0.0F, -3.0F, 6.0F, 6.0F, 6.0F);
      this.head.setPivot(0.0F, 12.0F, 0.0F);
   }

   public void setAngles(T _snowman, float _snowman, float _snowman, float _snowman, float _snowman, float _snowman) {
      float _snowmanxxxxxx = _snowman - (float)_snowman.age;
      float _snowmanxxxxxxx = (0.5F + _snowman.getOpenProgress(_snowmanxxxxxx)) * (float) Math.PI;
      float _snowmanxxxxxxxx = -1.0F + MathHelper.sin(_snowmanxxxxxxx);
      float _snowmanxxxxxxxxx = 0.0F;
      if (_snowmanxxxxxxx > (float) Math.PI) {
         _snowmanxxxxxxxxx = MathHelper.sin(_snowman * 0.1F) * 0.7F;
      }

      this.topShell.setPivot(0.0F, 16.0F + MathHelper.sin(_snowmanxxxxxxx) * 8.0F + _snowmanxxxxxxxxx, 0.0F);
      if (_snowman.getOpenProgress(_snowmanxxxxxx) > 0.3F) {
         this.topShell.yaw = _snowmanxxxxxxxx * _snowmanxxxxxxxx * _snowmanxxxxxxxx * _snowmanxxxxxxxx * (float) Math.PI * 0.125F;
      } else {
         this.topShell.yaw = 0.0F;
      }

      this.head.pitch = _snowman * (float) (Math.PI / 180.0);
      this.head.yaw = (_snowman.headYaw - 180.0F - _snowman.bodyYaw) * (float) (Math.PI / 180.0);
   }

   @Override
   public Iterable<ModelPart> getParts() {
      return ImmutableList.of(this.bottomShell, this.topShell);
   }

   public ModelPart getBottomShell() {
      return this.bottomShell;
   }

   public ModelPart getTopShell() {
      return this.topShell;
   }

   public ModelPart getHead() {
      return this.head;
   }
}
