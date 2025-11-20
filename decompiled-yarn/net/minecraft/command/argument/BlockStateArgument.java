package net.minecraft.command.argument;

import java.util.Set;
import java.util.function.Predicate;
import javax.annotation.Nullable;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.pattern.CachedBlockPosition;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.NbtHelper;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.state.property.Property;
import net.minecraft.util.math.BlockPos;

public class BlockStateArgument implements Predicate<CachedBlockPosition> {
   private final BlockState state;
   private final Set<Property<?>> properties;
   @Nullable
   private final CompoundTag data;

   public BlockStateArgument(BlockState state, Set<Property<?>> properties, @Nullable CompoundTag data) {
      this.state = state;
      this.properties = properties;
      this.data = data;
   }

   public BlockState getBlockState() {
      return this.state;
   }

   public boolean test(CachedBlockPosition _snowman) {
      BlockState _snowmanx = _snowman.getBlockState();
      if (!_snowmanx.isOf(this.state.getBlock())) {
         return false;
      } else {
         for (Property<?> _snowmanxx : this.properties) {
            if (_snowmanx.get(_snowmanxx) != this.state.get(_snowmanxx)) {
               return false;
            }
         }

         if (this.data == null) {
            return true;
         } else {
            BlockEntity _snowmanxxx = _snowman.getBlockEntity();
            return _snowmanxxx != null && NbtHelper.matches(this.data, _snowmanxxx.toTag(new CompoundTag()), true);
         }
      }
   }

   public boolean setBlockState(ServerWorld _snowman, BlockPos _snowman, int _snowman) {
      BlockState _snowmanxxx = Block.postProcessState(this.state, _snowman, _snowman);
      if (_snowmanxxx.isAir()) {
         _snowmanxxx = this.state;
      }

      if (!_snowman.setBlockState(_snowman, _snowmanxxx, _snowman)) {
         return false;
      } else {
         if (this.data != null) {
            BlockEntity _snowmanxxxx = _snowman.getBlockEntity(_snowman);
            if (_snowmanxxxx != null) {
               CompoundTag _snowmanxxxxx = this.data.copy();
               _snowmanxxxxx.putInt("x", _snowman.getX());
               _snowmanxxxxx.putInt("y", _snowman.getY());
               _snowmanxxxxx.putInt("z", _snowman.getZ());
               _snowmanxxxx.fromTag(_snowmanxxx, _snowmanxxxxx);
            }
         }

         return true;
      }
   }
}
