package net.minecraft.entity.ai.goal;

import java.util.Random;
import javax.annotation.Nullable;
import net.minecraft.block.Block;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.mob.PathAwareEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.particle.ItemStackParticleEffect;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.BlockView;
import net.minecraft.world.GameRules;
import net.minecraft.world.World;
import net.minecraft.world.WorldAccess;
import net.minecraft.world.WorldView;
import net.minecraft.world.chunk.Chunk;
import net.minecraft.world.chunk.ChunkStatus;

public class StepAndDestroyBlockGoal extends MoveToTargetPosGoal {
   private final Block targetBlock;
   private final MobEntity stepAndDestroyMob;
   private int counter;

   public StepAndDestroyBlockGoal(Block targetBlock, PathAwareEntity mob, double speed, int maxYDifference) {
      super(mob, speed, 24, maxYDifference);
      this.targetBlock = targetBlock;
      this.stepAndDestroyMob = mob;
   }

   @Override
   public boolean canStart() {
      if (!this.stepAndDestroyMob.world.getGameRules().getBoolean(GameRules.DO_MOB_GRIEFING)) {
         return false;
      } else if (this.cooldown > 0) {
         this.cooldown--;
         return false;
      } else if (this.hasAvailableTarget()) {
         this.cooldown = 20;
         return true;
      } else {
         this.cooldown = this.getInterval(this.mob);
         return false;
      }
   }

   private boolean hasAvailableTarget() {
      return this.targetPos != null && this.isTargetPos(this.mob.world, this.targetPos) ? true : this.findTargetPos();
   }

   @Override
   public void stop() {
      super.stop();
      this.stepAndDestroyMob.fallDistance = 1.0F;
   }

   @Override
   public void start() {
      super.start();
      this.counter = 0;
   }

   public void tickStepping(WorldAccess world, BlockPos pos) {
   }

   public void onDestroyBlock(World world, BlockPos pos) {
   }

   @Override
   public void tick() {
      super.tick();
      World _snowman = this.stepAndDestroyMob.world;
      BlockPos _snowmanx = this.stepAndDestroyMob.getBlockPos();
      BlockPos _snowmanxx = this.tweakToProperPos(_snowmanx, _snowman);
      Random _snowmanxxx = this.stepAndDestroyMob.getRandom();
      if (this.hasReached() && _snowmanxx != null) {
         if (this.counter > 0) {
            Vec3d _snowmanxxxx = this.stepAndDestroyMob.getVelocity();
            this.stepAndDestroyMob.setVelocity(_snowmanxxxx.x, 0.3, _snowmanxxxx.z);
            if (!_snowman.isClient) {
               double _snowmanxxxxx = 0.08;
               ((ServerWorld)_snowman)
                  .spawnParticles(
                     new ItemStackParticleEffect(ParticleTypes.ITEM, new ItemStack(Items.EGG)),
                     (double)_snowmanxx.getX() + 0.5,
                     (double)_snowmanxx.getY() + 0.7,
                     (double)_snowmanxx.getZ() + 0.5,
                     3,
                     ((double)_snowmanxxx.nextFloat() - 0.5) * 0.08,
                     ((double)_snowmanxxx.nextFloat() - 0.5) * 0.08,
                     ((double)_snowmanxxx.nextFloat() - 0.5) * 0.08,
                     0.15F
                  );
            }
         }

         if (this.counter % 2 == 0) {
            Vec3d _snowmanxxxx = this.stepAndDestroyMob.getVelocity();
            this.stepAndDestroyMob.setVelocity(_snowmanxxxx.x, -0.3, _snowmanxxxx.z);
            if (this.counter % 6 == 0) {
               this.tickStepping(_snowman, this.targetPos);
            }
         }

         if (this.counter > 60) {
            _snowman.removeBlock(_snowmanxx, false);
            if (!_snowman.isClient) {
               for (int _snowmanxxxx = 0; _snowmanxxxx < 20; _snowmanxxxx++) {
                  double _snowmanxxxxx = _snowmanxxx.nextGaussian() * 0.02;
                  double _snowmanxxxxxx = _snowmanxxx.nextGaussian() * 0.02;
                  double _snowmanxxxxxxx = _snowmanxxx.nextGaussian() * 0.02;
                  ((ServerWorld)_snowman)
                     .spawnParticles(
                        ParticleTypes.POOF, (double)_snowmanxx.getX() + 0.5, (double)_snowmanxx.getY(), (double)_snowmanxx.getZ() + 0.5, 1, _snowmanxxxxx, _snowmanxxxxxx, _snowmanxxxxxxx, 0.15F
                     );
               }

               this.onDestroyBlock(_snowman, _snowmanxx);
            }
         }

         this.counter++;
      }
   }

   @Nullable
   private BlockPos tweakToProperPos(BlockPos pos, BlockView world) {
      if (world.getBlockState(pos).isOf(this.targetBlock)) {
         return pos;
      } else {
         BlockPos[] _snowman = new BlockPos[]{pos.down(), pos.west(), pos.east(), pos.north(), pos.south(), pos.down().down()};

         for (BlockPos _snowmanx : _snowman) {
            if (world.getBlockState(_snowmanx).isOf(this.targetBlock)) {
               return _snowmanx;
            }
         }

         return null;
      }
   }

   @Override
   protected boolean isTargetPos(WorldView world, BlockPos pos) {
      Chunk _snowman = world.getChunk(pos.getX() >> 4, pos.getZ() >> 4, ChunkStatus.FULL, false);
      return _snowman == null ? false : _snowman.getBlockState(pos).isOf(this.targetBlock) && _snowman.getBlockState(pos.up()).isAir() && _snowman.getBlockState(pos.up(2)).isAir();
   }
}
