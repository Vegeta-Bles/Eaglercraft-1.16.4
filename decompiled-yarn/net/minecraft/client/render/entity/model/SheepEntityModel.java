package net.minecraft.client.render.entity.model;

import net.minecraft.client.model.ModelPart;
import net.minecraft.entity.passive.SheepEntity;

public class SheepEntityModel<T extends SheepEntity> extends QuadrupedEntityModel<T> {
   private float headPitchModifier;

   public SheepEntityModel() {
      super(12, 0.0F, false, 8.0F, 4.0F, 2.0F, 2.0F, 24);
      this.head = new ModelPart(this, 0, 0);
      this.head.addCuboid(-3.0F, -4.0F, -6.0F, 6.0F, 6.0F, 8.0F, 0.0F);
      this.head.setPivot(0.0F, 6.0F, -8.0F);
      this.torso = new ModelPart(this, 28, 8);
      this.torso.addCuboid(-4.0F, -10.0F, -7.0F, 8.0F, 16.0F, 6.0F, 0.0F);
      this.torso.setPivot(0.0F, 5.0F, 2.0F);
   }

   public void animateModel(T _snowman, float _snowman, float _snowman, float _snowman) {
      super.animateModel(_snowman, _snowman, _snowman, _snowman);
      this.head.pivotY = 6.0F + _snowman.getNeckAngle(_snowman) * 9.0F;
      this.headPitchModifier = _snowman.getHeadAngle(_snowman);
   }

   public void setAngles(T _snowman, float _snowman, float _snowman, float _snowman, float _snowman, float _snowman) {
      super.setAngles(_snowman, _snowman, _snowman, _snowman, _snowman, _snowman);
      this.head.pitch = this.headPitchModifier;
   }
}
