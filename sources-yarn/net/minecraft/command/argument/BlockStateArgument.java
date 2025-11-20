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

   public boolean test(CachedBlockPosition arg) {
      BlockState lv = arg.getBlockState();
      if (!lv.isOf(this.state.getBlock())) {
         return false;
      } else {
         for (Property<?> lv2 : this.properties) {
            if (lv.get(lv2) != this.state.get(lv2)) {
               return false;
            }
         }

         if (this.data == null) {
            return true;
         } else {
            BlockEntity lv3 = arg.getBlockEntity();
            return lv3 != null && NbtHelper.matches(this.data, lv3.toTag(new CompoundTag()), true);
         }
      }
   }

   public boolean setBlockState(ServerWorld arg, BlockPos arg2, int i) {
      BlockState lv = Block.postProcessState(this.state, arg, arg2);
      if (lv.isAir()) {
         lv = this.state;
      }

      if (!arg.setBlockState(arg2, lv, i)) {
         return false;
      } else {
         if (this.data != null) {
            BlockEntity lv2 = arg.getBlockEntity(arg2);
            if (lv2 != null) {
               CompoundTag lv3 = this.data.copy();
               lv3.putInt("x", arg2.getX());
               lv3.putInt("y", arg2.getY());
               lv3.putInt("z", arg2.getZ());
               lv2.fromTag(lv, lv3);
            }
         }

         return true;
      }
   }
}
