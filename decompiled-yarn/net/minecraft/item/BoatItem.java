package net.minecraft.item;

import java.util.List;
import java.util.function.Predicate;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.vehicle.BoatEntity;
import net.minecraft.predicate.entity.EntityPredicates;
import net.minecraft.stat.Stats;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.RaycastContext;
import net.minecraft.world.World;

public class BoatItem extends Item {
   private static final Predicate<Entity> RIDERS = EntityPredicates.EXCEPT_SPECTATOR.and(Entity::collides);
   private final BoatEntity.Type type;

   public BoatItem(BoatEntity.Type type, Item.Settings settings) {
      super(settings);
      this.type = type;
   }

   @Override
   public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
      ItemStack _snowman = user.getStackInHand(hand);
      HitResult _snowmanx = raycast(world, user, RaycastContext.FluidHandling.ANY);
      if (_snowmanx.getType() == HitResult.Type.MISS) {
         return TypedActionResult.pass(_snowman);
      } else {
         Vec3d _snowmanxx = user.getRotationVec(1.0F);
         double _snowmanxxx = 5.0;
         List<Entity> _snowmanxxxx = world.getOtherEntities(user, user.getBoundingBox().stretch(_snowmanxx.multiply(5.0)).expand(1.0), RIDERS);
         if (!_snowmanxxxx.isEmpty()) {
            Vec3d _snowmanxxxxx = user.getCameraPosVec(1.0F);

            for (Entity _snowmanxxxxxx : _snowmanxxxx) {
               Box _snowmanxxxxxxx = _snowmanxxxxxx.getBoundingBox().expand((double)_snowmanxxxxxx.getTargetingMargin());
               if (_snowmanxxxxxxx.contains(_snowmanxxxxx)) {
                  return TypedActionResult.pass(_snowman);
               }
            }
         }

         if (_snowmanx.getType() == HitResult.Type.BLOCK) {
            BoatEntity _snowmanxxxxx = new BoatEntity(world, _snowmanx.getPos().x, _snowmanx.getPos().y, _snowmanx.getPos().z);
            _snowmanxxxxx.setBoatType(this.type);
            _snowmanxxxxx.yaw = user.yaw;
            if (!world.isSpaceEmpty(_snowmanxxxxx, _snowmanxxxxx.getBoundingBox().expand(-0.1))) {
               return TypedActionResult.fail(_snowman);
            } else {
               if (!world.isClient) {
                  world.spawnEntity(_snowmanxxxxx);
                  if (!user.abilities.creativeMode) {
                     _snowman.decrement(1);
                  }
               }

               user.incrementStat(Stats.USED.getOrCreateStat(this));
               return TypedActionResult.success(_snowman, world.isClient());
            }
         } else {
            return TypedActionResult.pass(_snowman);
         }
      }
   }
}
