package net.minecraft.client.render.entity.model;

import net.minecraft.client.model.ModelPart;
import net.minecraft.entity.passive.AbstractDonkeyEntity;

public class DonkeyEntityModel<T extends AbstractDonkeyEntity> extends HorseEntityModel<T> {
   private final ModelPart leftChest = new ModelPart(this, 26, 21);
   private final ModelPart rightChest;

   public DonkeyEntityModel(float _snowman) {
      super(_snowman);
      this.leftChest.addCuboid(-4.0F, 0.0F, -2.0F, 8.0F, 8.0F, 3.0F);
      this.rightChest = new ModelPart(this, 26, 21);
      this.rightChest.addCuboid(-4.0F, 0.0F, -2.0F, 8.0F, 8.0F, 3.0F);
      this.leftChest.yaw = (float) (-Math.PI / 2);
      this.rightChest.yaw = (float) (Math.PI / 2);
      this.leftChest.setPivot(6.0F, -8.0F, 0.0F);
      this.rightChest.setPivot(-6.0F, -8.0F, 0.0F);
      this.torso.addChild(this.leftChest);
      this.torso.addChild(this.rightChest);
   }

   @Override
   protected void method_2789(ModelPart _snowman) {
      ModelPart _snowmanx = new ModelPart(this, 0, 12);
      _snowmanx.addCuboid(-1.0F, -7.0F, 0.0F, 2.0F, 7.0F, 1.0F);
      _snowmanx.setPivot(1.25F, -10.0F, 4.0F);
      ModelPart _snowmanxx = new ModelPart(this, 0, 12);
      _snowmanxx.addCuboid(-1.0F, -7.0F, 0.0F, 2.0F, 7.0F, 1.0F);
      _snowmanxx.setPivot(-1.25F, -10.0F, 4.0F);
      _snowmanx.pitch = (float) (Math.PI / 12);
      _snowmanx.roll = (float) (Math.PI / 12);
      _snowmanxx.pitch = (float) (Math.PI / 12);
      _snowmanxx.roll = (float) (-Math.PI / 12);
      _snowman.addChild(_snowmanx);
      _snowman.addChild(_snowmanxx);
   }

   public void setAngles(T _snowman, float _snowman, float _snowman, float _snowman, float _snowman, float _snowman) {
      super.setAngles(_snowman, _snowman, _snowman, _snowman, _snowman, _snowman);
      if (_snowman.hasChest()) {
         this.leftChest.visible = true;
         this.rightChest.visible = true;
      } else {
         this.leftChest.visible = false;
         this.rightChest.visible = false;
      }
   }
}
