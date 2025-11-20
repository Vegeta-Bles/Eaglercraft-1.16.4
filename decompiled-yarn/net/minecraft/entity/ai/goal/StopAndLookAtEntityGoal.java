package net.minecraft.entity.ai.goal;

import java.util.EnumSet;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.mob.MobEntity;

public class StopAndLookAtEntityGoal extends LookAtEntityGoal {
   public StopAndLookAtEntityGoal(MobEntity _snowman, Class<? extends LivingEntity> _snowman, float _snowman, float _snowman) {
      super(_snowman, _snowman, _snowman, _snowman);
      this.setControls(EnumSet.of(Goal.Control.LOOK, Goal.Control.MOVE));
   }
}
