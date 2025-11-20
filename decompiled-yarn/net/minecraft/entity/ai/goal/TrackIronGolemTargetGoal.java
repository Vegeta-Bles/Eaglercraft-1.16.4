package net.minecraft.entity.ai.goal;

import java.util.EnumSet;
import java.util.List;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.TargetPredicate;
import net.minecraft.entity.passive.IronGolemEntity;
import net.minecraft.entity.passive.VillagerEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.math.Box;

public class TrackIronGolemTargetGoal extends TrackTargetGoal {
   private final IronGolemEntity golem;
   private LivingEntity target;
   private final TargetPredicate targetPredicate = new TargetPredicate().setBaseMaxDistance(64.0);

   public TrackIronGolemTargetGoal(IronGolemEntity golem) {
      super(golem, false, true);
      this.golem = golem;
      this.setControls(EnumSet.of(Goal.Control.TARGET));
   }

   @Override
   public boolean canStart() {
      Box _snowman = this.golem.getBoundingBox().expand(10.0, 8.0, 10.0);
      List<LivingEntity> _snowmanx = this.golem.world.getTargets(VillagerEntity.class, this.targetPredicate, this.golem, _snowman);
      List<PlayerEntity> _snowmanxx = this.golem.world.getPlayers(this.targetPredicate, this.golem, _snowman);

      for (LivingEntity _snowmanxxx : _snowmanx) {
         VillagerEntity _snowmanxxxx = (VillagerEntity)_snowmanxxx;

         for (PlayerEntity _snowmanxxxxx : _snowmanxx) {
            int _snowmanxxxxxx = _snowmanxxxx.getReputation(_snowmanxxxxx);
            if (_snowmanxxxxxx <= -100) {
               this.target = _snowmanxxxxx;
            }
         }
      }

      return this.target == null ? false : !(this.target instanceof PlayerEntity) || !this.target.isSpectator() && !((PlayerEntity)this.target).isCreative();
   }

   @Override
   public void start() {
      this.golem.setTarget(this.target);
      super.start();
   }
}
