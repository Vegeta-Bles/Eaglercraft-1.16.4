package net.minecraft.client.render.entity.model;

import net.minecraft.entity.mob.GiantEntity;

public class GiantEntityModel extends AbstractZombieModel<GiantEntity> {
   public GiantEntityModel() {
      this(0.0F, false);
   }

   public GiantEntityModel(float scale, boolean _snowman) {
      super(scale, 0.0F, 64, _snowman ? 32 : 64);
   }

   public boolean isAttacking(GiantEntity _snowman) {
      return false;
   }
}
