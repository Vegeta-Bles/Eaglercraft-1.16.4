package net.minecraft.entity.ai.brain.task;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Sets;
import java.util.Iterator;
import java.util.Objects;
import javax.annotation.Nullable;
import net.minecraft.block.BlockState;
import net.minecraft.block.DoorBlock;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.brain.Brain;
import net.minecraft.entity.ai.brain.MemoryModuleState;
import net.minecraft.entity.ai.brain.MemoryModuleType;
import net.minecraft.entity.ai.pathing.Path;
import net.minecraft.entity.ai.pathing.PathNode;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.tag.BlockTags;
import net.minecraft.util.dynamic.GlobalPos;
import net.minecraft.util.math.BlockPos;

public class OpenDoorsTask extends Task<LivingEntity> {
   @Nullable
   private PathNode field_26387;
   private int field_26388;

   public OpenDoorsTask() {
      super(ImmutableMap.of(MemoryModuleType.PATH, MemoryModuleState.VALUE_PRESENT, MemoryModuleType.DOORS_TO_CLOSE, MemoryModuleState.REGISTERED));
   }

   @Override
   protected boolean shouldRun(ServerWorld world, LivingEntity entity) {
      Path lv = entity.getBrain().getOptionalMemory(MemoryModuleType.PATH).get();
      if (!lv.method_30849() && !lv.isFinished()) {
         if (!Objects.equals(this.field_26387, lv.method_29301())) {
            this.field_26388 = 20;
            return true;
         } else {
            if (this.field_26388 > 0) {
               this.field_26388--;
            }

            return this.field_26388 == 0;
         }
      } else {
         return false;
      }
   }

   @Override
   protected void run(ServerWorld world, LivingEntity entity, long time) {
      Path lv = entity.getBrain().getOptionalMemory(MemoryModuleType.PATH).get();
      this.field_26387 = lv.method_29301();
      PathNode lv2 = lv.method_30850();
      PathNode lv3 = lv.method_29301();
      BlockPos lv4 = lv2.getPos();
      BlockState lv5 = world.getBlockState(lv4);
      if (lv5.isIn(BlockTags.WOODEN_DOORS)) {
         DoorBlock lv6 = (DoorBlock)lv5.getBlock();
         if (!lv6.method_30841(lv5)) {
            lv6.setOpen(world, lv5, lv4, true);
         }

         this.method_30767(world, entity, lv4);
      }

      BlockPos lv7 = lv3.getPos();
      BlockState lv8 = world.getBlockState(lv7);
      if (lv8.isIn(BlockTags.WOODEN_DOORS)) {
         DoorBlock lv9 = (DoorBlock)lv8.getBlock();
         if (!lv9.method_30841(lv8)) {
            lv9.setOpen(world, lv8, lv7, true);
            this.method_30767(world, entity, lv7);
         }
      }

      method_30760(world, entity, lv2, lv3);
   }

   public static void method_30760(ServerWorld arg, LivingEntity arg2, @Nullable PathNode arg3, @Nullable PathNode arg4) {
      Brain<?> lv = arg2.getBrain();
      if (lv.hasMemoryModule(MemoryModuleType.DOORS_TO_CLOSE)) {
         Iterator<GlobalPos> iterator = lv.getOptionalMemory(MemoryModuleType.DOORS_TO_CLOSE).get().iterator();

         while (iterator.hasNext()) {
            GlobalPos lv2 = iterator.next();
            BlockPos lv3 = lv2.getPos();
            if ((arg3 == null || !arg3.getPos().equals(lv3)) && (arg4 == null || !arg4.getPos().equals(lv3))) {
               if (method_30762(arg, arg2, lv2)) {
                  iterator.remove();
               } else {
                  BlockState lv4 = arg.getBlockState(lv3);
                  if (!lv4.isIn(BlockTags.WOODEN_DOORS)) {
                     iterator.remove();
                  } else {
                     DoorBlock lv5 = (DoorBlock)lv4.getBlock();
                     if (!lv5.method_30841(lv4)) {
                        iterator.remove();
                     } else if (method_30761(arg, arg2, lv3)) {
                        iterator.remove();
                     } else {
                        lv5.setOpen(arg, lv4, lv3, false);
                        iterator.remove();
                     }
                  }
               }
            }
         }
      }
   }

   private static boolean method_30761(ServerWorld arg, LivingEntity arg2, BlockPos arg3) {
      Brain<?> lv = arg2.getBrain();
      return !lv.hasMemoryModule(MemoryModuleType.MOBS)
         ? false
         : lv.getOptionalMemory(MemoryModuleType.MOBS)
            .get()
            .stream()
            .filter(arg2x -> arg2x.getType() == arg2.getType())
            .filter(arg2x -> arg3.isWithinDistance(arg2x.getPos(), 2.0))
            .anyMatch(arg3x -> method_30766(arg, arg3x, arg3));
   }

   private static boolean method_30766(ServerWorld arg, LivingEntity arg2, BlockPos arg3) {
      if (!arg2.getBrain().hasMemoryModule(MemoryModuleType.PATH)) {
         return false;
      } else {
         Path lv = arg2.getBrain().getOptionalMemory(MemoryModuleType.PATH).get();
         if (lv.isFinished()) {
            return false;
         } else {
            PathNode lv2 = lv.method_30850();
            if (lv2 == null) {
               return false;
            } else {
               PathNode lv3 = lv.method_29301();
               return arg3.equals(lv2.getPos()) || arg3.equals(lv3.getPos());
            }
         }
      }
   }

   private static boolean method_30762(ServerWorld arg, LivingEntity arg2, GlobalPos arg3) {
      return arg3.getDimension() != arg.getRegistryKey() || !arg3.getPos().isWithinDistance(arg2.getPos(), 2.0);
   }

   private void method_30767(ServerWorld arg, LivingEntity arg2, BlockPos arg3) {
      Brain<?> lv = arg2.getBrain();
      GlobalPos lv2 = GlobalPos.create(arg.getRegistryKey(), arg3);
      if (lv.getOptionalMemory(MemoryModuleType.DOORS_TO_CLOSE).isPresent()) {
         lv.getOptionalMemory(MemoryModuleType.DOORS_TO_CLOSE).get().add(lv2);
      } else {
         lv.remember(MemoryModuleType.DOORS_TO_CLOSE, Sets.newHashSet(new GlobalPos[]{lv2}));
      }
   }
}
