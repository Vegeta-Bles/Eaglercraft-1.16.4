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
      Path _snowman = entity.getBrain().getOptionalMemory(MemoryModuleType.PATH).get();
      if (!_snowman.method_30849() && !_snowman.isFinished()) {
         if (!Objects.equals(this.field_26387, _snowman.method_29301())) {
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
      Path _snowman = entity.getBrain().getOptionalMemory(MemoryModuleType.PATH).get();
      this.field_26387 = _snowman.method_29301();
      PathNode _snowmanx = _snowman.method_30850();
      PathNode _snowmanxx = _snowman.method_29301();
      BlockPos _snowmanxxx = _snowmanx.getPos();
      BlockState _snowmanxxxx = world.getBlockState(_snowmanxxx);
      if (_snowmanxxxx.isIn(BlockTags.WOODEN_DOORS)) {
         DoorBlock _snowmanxxxxx = (DoorBlock)_snowmanxxxx.getBlock();
         if (!_snowmanxxxxx.method_30841(_snowmanxxxx)) {
            _snowmanxxxxx.setOpen(world, _snowmanxxxx, _snowmanxxx, true);
         }

         this.method_30767(world, entity, _snowmanxxx);
      }

      BlockPos _snowmanxxxxx = _snowmanxx.getPos();
      BlockState _snowmanxxxxxx = world.getBlockState(_snowmanxxxxx);
      if (_snowmanxxxxxx.isIn(BlockTags.WOODEN_DOORS)) {
         DoorBlock _snowmanxxxxxxx = (DoorBlock)_snowmanxxxxxx.getBlock();
         if (!_snowmanxxxxxxx.method_30841(_snowmanxxxxxx)) {
            _snowmanxxxxxxx.setOpen(world, _snowmanxxxxxx, _snowmanxxxxx, true);
            this.method_30767(world, entity, _snowmanxxxxx);
         }
      }

      method_30760(world, entity, _snowmanx, _snowmanxx);
   }

   public static void method_30760(ServerWorld _snowman, LivingEntity _snowman, @Nullable PathNode _snowman, @Nullable PathNode _snowman) {
      Brain<?> _snowmanxxxx = _snowman.getBrain();
      if (_snowmanxxxx.hasMemoryModule(MemoryModuleType.DOORS_TO_CLOSE)) {
         Iterator<GlobalPos> _snowmanxxxxx = _snowmanxxxx.getOptionalMemory(MemoryModuleType.DOORS_TO_CLOSE).get().iterator();

         while (_snowmanxxxxx.hasNext()) {
            GlobalPos _snowmanxxxxxx = _snowmanxxxxx.next();
            BlockPos _snowmanxxxxxxx = _snowmanxxxxxx.getPos();
            if ((_snowman == null || !_snowman.getPos().equals(_snowmanxxxxxxx)) && (_snowman == null || !_snowman.getPos().equals(_snowmanxxxxxxx))) {
               if (method_30762(_snowman, _snowman, _snowmanxxxxxx)) {
                  _snowmanxxxxx.remove();
               } else {
                  BlockState _snowmanxxxxxxxx = _snowman.getBlockState(_snowmanxxxxxxx);
                  if (!_snowmanxxxxxxxx.isIn(BlockTags.WOODEN_DOORS)) {
                     _snowmanxxxxx.remove();
                  } else {
                     DoorBlock _snowmanxxxxxxxxx = (DoorBlock)_snowmanxxxxxxxx.getBlock();
                     if (!_snowmanxxxxxxxxx.method_30841(_snowmanxxxxxxxx)) {
                        _snowmanxxxxx.remove();
                     } else if (method_30761(_snowman, _snowman, _snowmanxxxxxxx)) {
                        _snowmanxxxxx.remove();
                     } else {
                        _snowmanxxxxxxxxx.setOpen(_snowman, _snowmanxxxxxxxx, _snowmanxxxxxxx, false);
                        _snowmanxxxxx.remove();
                     }
                  }
               }
            }
         }
      }
   }

   private static boolean method_30761(ServerWorld _snowman, LivingEntity _snowman, BlockPos _snowman) {
      Brain<?> _snowmanxxx = _snowman.getBrain();
      return !_snowmanxxx.hasMemoryModule(MemoryModuleType.MOBS)
         ? false
         : _snowmanxxx.getOptionalMemory(MemoryModuleType.MOBS)
            .get()
            .stream()
            .filter(_snowmanxxxx -> _snowmanxxxx.getType() == _snowman.getType())
            .filter(_snowmanxxxx -> _snowman.isWithinDistance(_snowmanxxxx.getPos(), 2.0))
            .anyMatch(_snowmanxxxx -> method_30766(_snowman, _snowmanxxxx, _snowman));
   }

   private static boolean method_30766(ServerWorld _snowman, LivingEntity _snowman, BlockPos _snowman) {
      if (!_snowman.getBrain().hasMemoryModule(MemoryModuleType.PATH)) {
         return false;
      } else {
         Path _snowmanxxx = _snowman.getBrain().getOptionalMemory(MemoryModuleType.PATH).get();
         if (_snowmanxxx.isFinished()) {
            return false;
         } else {
            PathNode _snowmanxxxx = _snowmanxxx.method_30850();
            if (_snowmanxxxx == null) {
               return false;
            } else {
               PathNode _snowmanxxxxx = _snowmanxxx.method_29301();
               return _snowman.equals(_snowmanxxxx.getPos()) || _snowman.equals(_snowmanxxxxx.getPos());
            }
         }
      }
   }

   private static boolean method_30762(ServerWorld _snowman, LivingEntity _snowman, GlobalPos _snowman) {
      return _snowman.getDimension() != _snowman.getRegistryKey() || !_snowman.getPos().isWithinDistance(_snowman.getPos(), 2.0);
   }

   private void method_30767(ServerWorld _snowman, LivingEntity _snowman, BlockPos _snowman) {
      Brain<?> _snowmanxxx = _snowman.getBrain();
      GlobalPos _snowmanxxxx = GlobalPos.create(_snowman.getRegistryKey(), _snowman);
      if (_snowmanxxx.getOptionalMemory(MemoryModuleType.DOORS_TO_CLOSE).isPresent()) {
         _snowmanxxx.getOptionalMemory(MemoryModuleType.DOORS_TO_CLOSE).get().add(_snowmanxxxx);
      } else {
         _snowmanxxx.remember(MemoryModuleType.DOORS_TO_CLOSE, Sets.newHashSet(new GlobalPos[]{_snowmanxxxx}));
      }
   }
}
