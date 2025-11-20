package net.minecraft.block;

import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;

public class SlimeBlock extends TransparentBlock {
   public SlimeBlock(AbstractBlock.Settings _snowman) {
      super(_snowman);
   }

   @Override
   public void onLandedUpon(World world, BlockPos pos, Entity entity, float distance) {
      if (entity.bypassesLandingEffects()) {
         super.onLandedUpon(world, pos, entity, distance);
      } else {
         entity.handleFallDamage(distance, 0.0F);
      }
   }

   @Override
   public void onEntityLand(BlockView world, Entity entity) {
      if (entity.bypassesLandingEffects()) {
         super.onEntityLand(world, entity);
      } else {
         this.bounce(entity);
      }
   }

   private void bounce(Entity entity) {
      Vec3d _snowman = entity.getVelocity();
      if (_snowman.y < 0.0) {
         double _snowmanx = entity instanceof LivingEntity ? 1.0 : 0.8;
         entity.setVelocity(_snowman.x, -_snowman.y * _snowmanx, _snowman.z);
      }
   }

   @Override
   public void onSteppedOn(World world, BlockPos pos, Entity entity) {
      double _snowman = Math.abs(entity.getVelocity().y);
      if (_snowman < 0.1 && !entity.bypassesSteppingEffects()) {
         double _snowmanx = 0.4 + _snowman * 0.2;
         entity.setVelocity(entity.getVelocity().multiply(_snowmanx, 1.0, _snowmanx));
      }

      super.onSteppedOn(world, pos, entity);
   }
}
