package net.minecraft.entity.boss.dragon.phase;

import net.minecraft.entity.boss.dragon.EnderDragonEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.projectile.PersistentProjectileEntity;

public abstract class AbstractSittingPhase extends AbstractPhase {
   public AbstractSittingPhase(EnderDragonEntity _snowman) {
      super(_snowman);
   }

   @Override
   public boolean isSittingOrHovering() {
      return true;
   }

   @Override
   public float modifyDamageTaken(DamageSource _snowman, float _snowman) {
      if (_snowman.getSource() instanceof PersistentProjectileEntity) {
         _snowman.getSource().setOnFireFor(1);
         return 0.0F;
      } else {
         return super.modifyDamageTaken(_snowman, _snowman);
      }
   }
}
