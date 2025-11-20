package net.minecraft.entity.mob;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.world.World;

public abstract class AmbientEntity extends MobEntity {
   protected AmbientEntity(EntityType<? extends AmbientEntity> _snowman, World _snowman) {
      super(_snowman, _snowman);
   }

   @Override
   public boolean canBeLeashedBy(PlayerEntity player) {
      return false;
   }
}
