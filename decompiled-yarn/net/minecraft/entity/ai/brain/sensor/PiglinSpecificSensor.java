package net.minecraft.entity.ai.brain.sensor;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Lists;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.CampfireBlock;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.brain.Brain;
import net.minecraft.entity.ai.brain.MemoryModuleType;
import net.minecraft.entity.boss.WitherEntity;
import net.minecraft.entity.mob.AbstractPiglinEntity;
import net.minecraft.entity.mob.HoglinEntity;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.mob.PiglinBrain;
import net.minecraft.entity.mob.PiglinBruteEntity;
import net.minecraft.entity.mob.PiglinEntity;
import net.minecraft.entity.mob.WitherSkeletonEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.predicate.entity.EntityPredicates;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.tag.BlockTags;
import net.minecraft.util.math.BlockPos;

public class PiglinSpecificSensor extends Sensor<LivingEntity> {
   public PiglinSpecificSensor() {
   }

   @Override
   public Set<MemoryModuleType<?>> getOutputMemoryModules() {
      return ImmutableSet.of(
         MemoryModuleType.VISIBLE_MOBS,
         MemoryModuleType.MOBS,
         MemoryModuleType.NEAREST_VISIBLE_NEMESIS,
         MemoryModuleType.NEAREST_TARGETABLE_PLAYER_NOT_WEARING_GOLD,
         MemoryModuleType.NEAREST_PLAYER_HOLDING_WANTED_ITEM,
         MemoryModuleType.NEAREST_VISIBLE_HUNTABLE_HOGLIN,
         new MemoryModuleType[]{
            MemoryModuleType.NEAREST_VISIBLE_BABY_HOGLIN,
            MemoryModuleType.NEAREST_VISIBLE_ADULT_PIGLINS,
            MemoryModuleType.NEARBY_ADULT_PIGLINS,
            MemoryModuleType.VISIBLE_ADULT_PIGLIN_COUNT,
            MemoryModuleType.VISIBLE_ADULT_HOGLIN_COUNT,
            MemoryModuleType.NEAREST_REPELLENT
         }
      );
   }

   @Override
   protected void sense(ServerWorld world, LivingEntity entity) {
      Brain<?> _snowman = entity.getBrain();
      _snowman.remember(MemoryModuleType.NEAREST_REPELLENT, findSoulFire(world, entity));
      Optional<MobEntity> _snowmanx = Optional.empty();
      Optional<HoglinEntity> _snowmanxx = Optional.empty();
      Optional<HoglinEntity> _snowmanxxx = Optional.empty();
      Optional<PiglinEntity> _snowmanxxxx = Optional.empty();
      Optional<LivingEntity> _snowmanxxxxx = Optional.empty();
      Optional<PlayerEntity> _snowmanxxxxxx = Optional.empty();
      Optional<PlayerEntity> _snowmanxxxxxxx = Optional.empty();
      int _snowmanxxxxxxxx = 0;
      List<AbstractPiglinEntity> _snowmanxxxxxxxxx = Lists.newArrayList();
      List<AbstractPiglinEntity> _snowmanxxxxxxxxxx = Lists.newArrayList();

      for (LivingEntity _snowmanxxxxxxxxxxx : _snowman.getOptionalMemory(MemoryModuleType.VISIBLE_MOBS).orElse(ImmutableList.of())) {
         if (_snowmanxxxxxxxxxxx instanceof HoglinEntity) {
            HoglinEntity _snowmanxxxxxxxxxxxx = (HoglinEntity)_snowmanxxxxxxxxxxx;
            if (_snowmanxxxxxxxxxxxx.isBaby() && !_snowmanxxx.isPresent()) {
               _snowmanxxx = Optional.of(_snowmanxxxxxxxxxxxx);
            } else if (_snowmanxxxxxxxxxxxx.isAdult()) {
               _snowmanxxxxxxxx++;
               if (!_snowmanxx.isPresent() && _snowmanxxxxxxxxxxxx.canBeHunted()) {
                  _snowmanxx = Optional.of(_snowmanxxxxxxxxxxxx);
               }
            }
         } else if (_snowmanxxxxxxxxxxx instanceof PiglinBruteEntity) {
            _snowmanxxxxxxxxx.add((PiglinBruteEntity)_snowmanxxxxxxxxxxx);
         } else if (_snowmanxxxxxxxxxxx instanceof PiglinEntity) {
            PiglinEntity _snowmanxxxxxxxxxxxx = (PiglinEntity)_snowmanxxxxxxxxxxx;
            if (_snowmanxxxxxxxxxxxx.isBaby() && !_snowmanxxxx.isPresent()) {
               _snowmanxxxx = Optional.of(_snowmanxxxxxxxxxxxx);
            } else if (_snowmanxxxxxxxxxxxx.isAdult()) {
               _snowmanxxxxxxxxx.add(_snowmanxxxxxxxxxxxx);
            }
         } else if (_snowmanxxxxxxxxxxx instanceof PlayerEntity) {
            PlayerEntity _snowmanxxxxxxxxxxxx = (PlayerEntity)_snowmanxxxxxxxxxxx;
            if (!_snowmanxxxxxx.isPresent() && EntityPredicates.EXCEPT_CREATIVE_SPECTATOR_OR_PEACEFUL.test(_snowmanxxxxxxxxxxx) && !PiglinBrain.wearsGoldArmor(_snowmanxxxxxxxxxxxx)
               )
             {
               _snowmanxxxxxx = Optional.of(_snowmanxxxxxxxxxxxx);
            }

            if (!_snowmanxxxxxxx.isPresent() && !_snowmanxxxxxxxxxxxx.isSpectator() && PiglinBrain.isGoldHoldingPlayer(_snowmanxxxxxxxxxxxx)) {
               _snowmanxxxxxxx = Optional.of(_snowmanxxxxxxxxxxxx);
            }
         } else if (_snowmanx.isPresent() || !(_snowmanxxxxxxxxxxx instanceof WitherSkeletonEntity) && !(_snowmanxxxxxxxxxxx instanceof WitherEntity)) {
            if (!_snowmanxxxxx.isPresent() && PiglinBrain.isZombified(_snowmanxxxxxxxxxxx.getType())) {
               _snowmanxxxxx = Optional.of(_snowmanxxxxxxxxxxx);
            }
         } else {
            _snowmanx = Optional.of((MobEntity)_snowmanxxxxxxxxxxx);
         }
      }

      for (LivingEntity _snowmanxxxxxxxxxxxxx : _snowman.getOptionalMemory(MemoryModuleType.MOBS).orElse(ImmutableList.of())) {
         if (_snowmanxxxxxxxxxxxxx instanceof AbstractPiglinEntity && ((AbstractPiglinEntity)_snowmanxxxxxxxxxxxxx).isAdult()) {
            _snowmanxxxxxxxxxx.add((AbstractPiglinEntity)_snowmanxxxxxxxxxxxxx);
         }
      }

      _snowman.remember(MemoryModuleType.NEAREST_VISIBLE_NEMESIS, _snowmanx);
      _snowman.remember(MemoryModuleType.NEAREST_VISIBLE_HUNTABLE_HOGLIN, _snowmanxx);
      _snowman.remember(MemoryModuleType.NEAREST_VISIBLE_BABY_HOGLIN, _snowmanxxx);
      _snowman.remember(MemoryModuleType.NEAREST_VISIBLE_ZOMBIFIED, _snowmanxxxxx);
      _snowman.remember(MemoryModuleType.NEAREST_TARGETABLE_PLAYER_NOT_WEARING_GOLD, _snowmanxxxxxx);
      _snowman.remember(MemoryModuleType.NEAREST_PLAYER_HOLDING_WANTED_ITEM, _snowmanxxxxxxx);
      _snowman.remember(MemoryModuleType.NEARBY_ADULT_PIGLINS, _snowmanxxxxxxxxxx);
      _snowman.remember(MemoryModuleType.NEAREST_VISIBLE_ADULT_PIGLINS, _snowmanxxxxxxxxx);
      _snowman.remember(MemoryModuleType.VISIBLE_ADULT_PIGLIN_COUNT, _snowmanxxxxxxxxx.size());
      _snowman.remember(MemoryModuleType.VISIBLE_ADULT_HOGLIN_COUNT, _snowmanxxxxxxxx);
   }

   private static Optional<BlockPos> findSoulFire(ServerWorld world, LivingEntity entity) {
      return BlockPos.findClosest(entity.getBlockPos(), 8, 4, _snowmanx -> method_24648(world, _snowmanx));
   }

   private static boolean method_24648(ServerWorld _snowman, BlockPos _snowman) {
      BlockState _snowmanxx = _snowman.getBlockState(_snowman);
      boolean _snowmanxxx = _snowmanxx.isIn(BlockTags.PIGLIN_REPELLENTS);
      return _snowmanxxx && _snowmanxx.isOf(Blocks.SOUL_CAMPFIRE) ? CampfireBlock.isLitCampfire(_snowmanxx) : _snowmanxxx;
   }
}
