package net.minecraft.client.render.entity.model;

import net.minecraft.entity.mob.ZombieEntity;

public class ZombieEntityModel<T extends ZombieEntity> extends AbstractZombieModel<T> {
   public ZombieEntityModel(float scale, boolean _snowman) {
      this(scale, 0.0F, 64, _snowman ? 32 : 64);
   }

   protected ZombieEntityModel(float _snowman, float _snowman, int _snowman, int _snowman) {
      super(_snowman, _snowman, _snowman, _snowman);
   }

   public boolean isAttacking(T _snowman) {
      return _snowman.isAttacking();
   }
}
