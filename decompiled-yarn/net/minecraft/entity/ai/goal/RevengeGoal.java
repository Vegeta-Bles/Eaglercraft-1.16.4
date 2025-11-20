package net.minecraft.entity.ai.goal;

import java.util.EnumSet;
import java.util.Iterator;
import java.util.List;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.TargetPredicate;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.mob.PathAwareEntity;
import net.minecraft.entity.passive.TameableEntity;
import net.minecraft.util.math.Box;
import net.minecraft.world.GameRules;

public class RevengeGoal extends TrackTargetGoal {
   private static final TargetPredicate VALID_AVOIDABLES_PREDICATE = new TargetPredicate().includeHidden().ignoreDistanceScalingFactor();
   private boolean groupRevenge;
   private int lastAttackedTime;
   private final Class<?>[] noRevengeTypes;
   private Class<?>[] noHelpTypes;

   public RevengeGoal(PathAwareEntity mob, Class<?>... noRevengeTypes) {
      super(mob, true);
      this.noRevengeTypes = noRevengeTypes;
      this.setControls(EnumSet.of(Goal.Control.TARGET));
   }

   @Override
   public boolean canStart() {
      int _snowman = this.mob.getLastAttackedTime();
      LivingEntity _snowmanx = this.mob.getAttacker();
      if (_snowman != this.lastAttackedTime && _snowmanx != null) {
         if (_snowmanx.getType() == EntityType.PLAYER && this.mob.world.getGameRules().getBoolean(GameRules.UNIVERSAL_ANGER)) {
            return false;
         } else {
            for (Class<?> _snowmanxx : this.noRevengeTypes) {
               if (_snowmanxx.isAssignableFrom(_snowmanx.getClass())) {
                  return false;
               }
            }

            return this.canTrack(_snowmanx, VALID_AVOIDABLES_PREDICATE);
         }
      } else {
         return false;
      }
   }

   public RevengeGoal setGroupRevenge(Class<?>... noHelpTypes) {
      this.groupRevenge = true;
      this.noHelpTypes = noHelpTypes;
      return this;
   }

   @Override
   public void start() {
      this.mob.setTarget(this.mob.getAttacker());
      this.target = this.mob.getTarget();
      this.lastAttackedTime = this.mob.getLastAttackedTime();
      this.maxTimeWithoutVisibility = 300;
      if (this.groupRevenge) {
         this.callSameTypeForRevenge();
      }

      super.start();
   }

   protected void callSameTypeForRevenge() {
      double _snowman = this.getFollowRange();
      Box _snowmanx = Box.method_29968(this.mob.getPos()).expand(_snowman, 10.0, _snowman);
      List<MobEntity> _snowmanxx = this.mob.world.getEntitiesIncludingUngeneratedChunks((Class<? extends MobEntity>)this.mob.getClass(), _snowmanx);
      Iterator var5 = _snowmanxx.iterator();

      while (true) {
         MobEntity _snowmanxxx;
         while (true) {
            if (!var5.hasNext()) {
               return;
            }

            _snowmanxxx = (MobEntity)var5.next();
            if (this.mob != _snowmanxxx
               && _snowmanxxx.getTarget() == null
               && (!(this.mob instanceof TameableEntity) || ((TameableEntity)this.mob).getOwner() == ((TameableEntity)_snowmanxxx).getOwner())
               && !_snowmanxxx.isTeammate(this.mob.getAttacker())) {
               if (this.noHelpTypes == null) {
                  break;
               }

               boolean _snowmanxxxx = false;

               for (Class<?> _snowmanxxxxx : this.noHelpTypes) {
                  if (_snowmanxxx.getClass() == _snowmanxxxxx) {
                     _snowmanxxxx = true;
                     break;
                  }
               }

               if (!_snowmanxxxx) {
                  break;
               }
            }
         }

         this.setMobEntityTarget(_snowmanxxx, this.mob.getAttacker());
      }
   }

   protected void setMobEntityTarget(MobEntity mob, LivingEntity target) {
      mob.setTarget(target);
   }
}
