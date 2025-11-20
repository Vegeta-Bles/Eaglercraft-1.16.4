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
      Brain<?> lv = entity.getBrain();
      lv.remember(MemoryModuleType.NEAREST_REPELLENT, findSoulFire(world, entity));
      Optional<MobEntity> optional = Optional.empty();
      Optional<HoglinEntity> optional2 = Optional.empty();
      Optional<HoglinEntity> optional3 = Optional.empty();
      Optional<PiglinEntity> optional4 = Optional.empty();
      Optional<LivingEntity> optional5 = Optional.empty();
      Optional<PlayerEntity> optional6 = Optional.empty();
      Optional<PlayerEntity> optional7 = Optional.empty();
      int i = 0;
      List<AbstractPiglinEntity> list = Lists.newArrayList();
      List<AbstractPiglinEntity> list2 = Lists.newArrayList();

      for (LivingEntity lv2 : lv.getOptionalMemory(MemoryModuleType.VISIBLE_MOBS).orElse(ImmutableList.of())) {
         if (lv2 instanceof HoglinEntity) {
            HoglinEntity lv3 = (HoglinEntity)lv2;
            if (lv3.isBaby() && !optional3.isPresent()) {
               optional3 = Optional.of(lv3);
            } else if (lv3.isAdult()) {
               i++;
               if (!optional2.isPresent() && lv3.canBeHunted()) {
                  optional2 = Optional.of(lv3);
               }
            }
         } else if (lv2 instanceof PiglinBruteEntity) {
            list.add((PiglinBruteEntity)lv2);
         } else if (lv2 instanceof PiglinEntity) {
            PiglinEntity lv4 = (PiglinEntity)lv2;
            if (lv4.isBaby() && !optional4.isPresent()) {
               optional4 = Optional.of(lv4);
            } else if (lv4.isAdult()) {
               list.add(lv4);
            }
         } else if (lv2 instanceof PlayerEntity) {
            PlayerEntity lv5 = (PlayerEntity)lv2;
            if (!optional6.isPresent() && EntityPredicates.EXCEPT_CREATIVE_SPECTATOR_OR_PEACEFUL.test(lv2) && !PiglinBrain.wearsGoldArmor(lv5)) {
               optional6 = Optional.of(lv5);
            }

            if (!optional7.isPresent() && !lv5.isSpectator() && PiglinBrain.isGoldHoldingPlayer(lv5)) {
               optional7 = Optional.of(lv5);
            }
         } else if (optional.isPresent() || !(lv2 instanceof WitherSkeletonEntity) && !(lv2 instanceof WitherEntity)) {
            if (!optional5.isPresent() && PiglinBrain.isZombified(lv2.getType())) {
               optional5 = Optional.of(lv2);
            }
         } else {
            optional = Optional.of((MobEntity)lv2);
         }
      }

      for (LivingEntity lv6 : lv.getOptionalMemory(MemoryModuleType.MOBS).orElse(ImmutableList.of())) {
         if (lv6 instanceof AbstractPiglinEntity && ((AbstractPiglinEntity)lv6).isAdult()) {
            list2.add((AbstractPiglinEntity)lv6);
         }
      }

      lv.remember(MemoryModuleType.NEAREST_VISIBLE_NEMESIS, optional);
      lv.remember(MemoryModuleType.NEAREST_VISIBLE_HUNTABLE_HOGLIN, optional2);
      lv.remember(MemoryModuleType.NEAREST_VISIBLE_BABY_HOGLIN, optional3);
      lv.remember(MemoryModuleType.NEAREST_VISIBLE_ZOMBIFIED, optional5);
      lv.remember(MemoryModuleType.NEAREST_TARGETABLE_PLAYER_NOT_WEARING_GOLD, optional6);
      lv.remember(MemoryModuleType.NEAREST_PLAYER_HOLDING_WANTED_ITEM, optional7);
      lv.remember(MemoryModuleType.NEARBY_ADULT_PIGLINS, list2);
      lv.remember(MemoryModuleType.NEAREST_VISIBLE_ADULT_PIGLINS, list);
      lv.remember(MemoryModuleType.VISIBLE_ADULT_PIGLIN_COUNT, list.size());
      lv.remember(MemoryModuleType.VISIBLE_ADULT_HOGLIN_COUNT, i);
   }

   private static Optional<BlockPos> findSoulFire(ServerWorld world, LivingEntity entity) {
      return BlockPos.findClosest(entity.getBlockPos(), 8, 4, arg2 -> method_24648(world, arg2));
   }

   private static boolean method_24648(ServerWorld arg, BlockPos arg2) {
      BlockState lv = arg.getBlockState(arg2);
      boolean bl = lv.isIn(BlockTags.PIGLIN_REPELLENTS);
      return bl && lv.isOf(Blocks.SOUL_CAMPFIRE) ? CampfireBlock.isLitCampfire(lv) : bl;
   }
}
