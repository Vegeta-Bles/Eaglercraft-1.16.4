package net.minecraft.block;

import java.util.Random;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.world.BlockView;
import net.minecraft.world.Difficulty;
import net.minecraft.world.World;

public class WitherRoseBlock extends FlowerBlock {
   public WitherRoseBlock(StatusEffect effect, AbstractBlock.Settings settings) {
      super(effect, 8, settings);
   }

   @Override
   protected boolean canPlantOnTop(BlockState floor, BlockView world, BlockPos pos) {
      return super.canPlantOnTop(floor, world, pos) || floor.isOf(Blocks.NETHERRACK) || floor.isOf(Blocks.SOUL_SAND) || floor.isOf(Blocks.SOUL_SOIL);
   }

   @Override
   public void randomDisplayTick(BlockState state, World world, BlockPos pos, Random random) {
      VoxelShape _snowman = this.getOutlineShape(state, world, pos, ShapeContext.absent());
      Vec3d _snowmanx = _snowman.getBoundingBox().getCenter();
      double _snowmanxx = (double)pos.getX() + _snowmanx.x;
      double _snowmanxxx = (double)pos.getZ() + _snowmanx.z;

      for (int _snowmanxxxx = 0; _snowmanxxxx < 3; _snowmanxxxx++) {
         if (random.nextBoolean()) {
            world.addParticle(
               ParticleTypes.SMOKE,
               _snowmanxx + random.nextDouble() / 5.0,
               (double)pos.getY() + (0.5 - random.nextDouble()),
               _snowmanxxx + random.nextDouble() / 5.0,
               0.0,
               0.0,
               0.0
            );
         }
      }
   }

   @Override
   public void onEntityCollision(BlockState state, World world, BlockPos pos, Entity entity) {
      if (!world.isClient && world.getDifficulty() != Difficulty.PEACEFUL) {
         if (entity instanceof LivingEntity) {
            LivingEntity _snowman = (LivingEntity)entity;
            if (!_snowman.isInvulnerableTo(DamageSource.WITHER)) {
               _snowman.addStatusEffect(new StatusEffectInstance(StatusEffects.WITHER, 40));
            }
         }
      }
   }
}
