package net.minecraft.entity.ai.goal;

import com.google.common.collect.Sets;
import java.util.EnumSet;
import java.util.List;
import java.util.Set;
import net.minecraft.entity.ai.TargetFinder;
import net.minecraft.entity.raid.RaiderEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.Vec3d;
import net.minecraft.village.raid.Raid;
import net.minecraft.village.raid.RaidManager;

public class MoveToRaidCenterGoal<T extends RaiderEntity> extends Goal {
   private final T actor;

   public MoveToRaidCenterGoal(T actor) {
      this.actor = actor;
      this.setControls(EnumSet.of(Goal.Control.MOVE));
   }

   @Override
   public boolean canStart() {
      return this.actor.getTarget() == null
         && !this.actor.hasPassengers()
         && this.actor.hasActiveRaid()
         && !this.actor.getRaid().isFinished()
         && !((ServerWorld)this.actor.world).isNearOccupiedPointOfInterest(this.actor.getBlockPos());
   }

   @Override
   public boolean shouldContinue() {
      return this.actor.hasActiveRaid()
         && !this.actor.getRaid().isFinished()
         && this.actor.world instanceof ServerWorld
         && !((ServerWorld)this.actor.world).isNearOccupiedPointOfInterest(this.actor.getBlockPos());
   }

   @Override
   public void tick() {
      if (this.actor.hasActiveRaid()) {
         Raid _snowman = this.actor.getRaid();
         if (this.actor.age % 20 == 0) {
            this.includeFreeRaiders(_snowman);
         }

         if (!this.actor.isNavigating()) {
            Vec3d _snowmanx = TargetFinder.findTargetTowards(this.actor, 15, 4, Vec3d.ofBottomCenter(_snowman.getCenter()));
            if (_snowmanx != null) {
               this.actor.getNavigation().startMovingTo(_snowmanx.x, _snowmanx.y, _snowmanx.z, 1.0);
            }
         }
      }
   }

   private void includeFreeRaiders(Raid raid) {
      if (raid.isActive()) {
         Set<RaiderEntity> _snowman = Sets.newHashSet();
         List<RaiderEntity> _snowmanx = this.actor
            .world
            .getEntitiesByClass(
               RaiderEntity.class, this.actor.getBoundingBox().expand(16.0), _snowmanxx -> !_snowmanxx.hasActiveRaid() && RaidManager.isValidRaiderFor(_snowmanxx, raid)
            );
         _snowman.addAll(_snowmanx);

         for (RaiderEntity _snowmanxx : _snowman) {
            raid.addRaider(raid.getGroupsSpawned(), _snowmanxx, null, true);
         }
      }
   }
}
