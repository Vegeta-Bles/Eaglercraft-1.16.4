package net.minecraft.block.entity;

import javax.annotation.Nullable;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.CommandBlock;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.packet.s2c.play.BlockEntityUpdateS2CPacket;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec2f;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.CommandBlockExecutor;

public class CommandBlockBlockEntity extends BlockEntity {
   private boolean powered;
   private boolean auto;
   private boolean conditionMet;
   private boolean needsUpdatePacket;
   private final CommandBlockExecutor commandExecutor = new CommandBlockExecutor() {
      @Override
      public void setCommand(String command) {
         super.setCommand(command);
         CommandBlockBlockEntity.this.markDirty();
      }

      @Override
      public ServerWorld getWorld() {
         return (ServerWorld)CommandBlockBlockEntity.this.world;
      }

      @Override
      public void markDirty() {
         BlockState _snowman = CommandBlockBlockEntity.this.world.getBlockState(CommandBlockBlockEntity.this.pos);
         this.getWorld().updateListeners(CommandBlockBlockEntity.this.pos, _snowman, _snowman, 3);
      }

      @Override
      public Vec3d getPos() {
         return Vec3d.ofCenter(CommandBlockBlockEntity.this.pos);
      }

      @Override
      public ServerCommandSource getSource() {
         return new ServerCommandSource(
            this,
            Vec3d.ofCenter(CommandBlockBlockEntity.this.pos),
            Vec2f.ZERO,
            this.getWorld(),
            2,
            this.getCustomName().getString(),
            this.getCustomName(),
            this.getWorld().getServer(),
            null
         );
      }
   };

   public CommandBlockBlockEntity() {
      super(BlockEntityType.COMMAND_BLOCK);
   }

   @Override
   public CompoundTag toTag(CompoundTag tag) {
      super.toTag(tag);
      this.commandExecutor.serialize(tag);
      tag.putBoolean("powered", this.isPowered());
      tag.putBoolean("conditionMet", this.isConditionMet());
      tag.putBoolean("auto", this.isAuto());
      return tag;
   }

   @Override
   public void fromTag(BlockState state, CompoundTag tag) {
      super.fromTag(state, tag);
      this.commandExecutor.deserialize(tag);
      this.powered = tag.getBoolean("powered");
      this.conditionMet = tag.getBoolean("conditionMet");
      this.setAuto(tag.getBoolean("auto"));
   }

   @Nullable
   @Override
   public BlockEntityUpdateS2CPacket toUpdatePacket() {
      if (this.needsUpdatePacket()) {
         this.setNeedsUpdatePacket(false);
         CompoundTag _snowman = this.toTag(new CompoundTag());
         return new BlockEntityUpdateS2CPacket(this.pos, 2, _snowman);
      } else {
         return null;
      }
   }

   @Override
   public boolean copyItemDataRequiresOperator() {
      return true;
   }

   public CommandBlockExecutor getCommandExecutor() {
      return this.commandExecutor;
   }

   public void setPowered(boolean powered) {
      this.powered = powered;
   }

   public boolean isPowered() {
      return this.powered;
   }

   public boolean isAuto() {
      return this.auto;
   }

   public void setAuto(boolean auto) {
      boolean _snowman = this.auto;
      this.auto = auto;
      if (!_snowman && auto && !this.powered && this.world != null && this.getCommandBlockType() != CommandBlockBlockEntity.Type.SEQUENCE) {
         this.method_23360();
      }
   }

   public void method_23359() {
      CommandBlockBlockEntity.Type _snowman = this.getCommandBlockType();
      if (_snowman == CommandBlockBlockEntity.Type.AUTO && (this.powered || this.auto) && this.world != null) {
         this.method_23360();
      }
   }

   private void method_23360() {
      Block _snowman = this.getCachedState().getBlock();
      if (_snowman instanceof CommandBlock) {
         this.updateConditionMet();
         this.world.getBlockTickScheduler().schedule(this.pos, _snowman, 1);
      }
   }

   public boolean isConditionMet() {
      return this.conditionMet;
   }

   public boolean updateConditionMet() {
      this.conditionMet = true;
      if (this.isConditionalCommandBlock()) {
         BlockPos _snowman = this.pos.offset(this.world.getBlockState(this.pos).get(CommandBlock.FACING).getOpposite());
         if (this.world.getBlockState(_snowman).getBlock() instanceof CommandBlock) {
            BlockEntity _snowmanx = this.world.getBlockEntity(_snowman);
            this.conditionMet = _snowmanx instanceof CommandBlockBlockEntity && ((CommandBlockBlockEntity)_snowmanx).getCommandExecutor().getSuccessCount() > 0;
         } else {
            this.conditionMet = false;
         }
      }

      return this.conditionMet;
   }

   public boolean needsUpdatePacket() {
      return this.needsUpdatePacket;
   }

   public void setNeedsUpdatePacket(boolean needsUpdatePacket) {
      this.needsUpdatePacket = needsUpdatePacket;
   }

   public CommandBlockBlockEntity.Type getCommandBlockType() {
      BlockState _snowman = this.getCachedState();
      if (_snowman.isOf(Blocks.COMMAND_BLOCK)) {
         return CommandBlockBlockEntity.Type.REDSTONE;
      } else if (_snowman.isOf(Blocks.REPEATING_COMMAND_BLOCK)) {
         return CommandBlockBlockEntity.Type.AUTO;
      } else {
         return _snowman.isOf(Blocks.CHAIN_COMMAND_BLOCK) ? CommandBlockBlockEntity.Type.SEQUENCE : CommandBlockBlockEntity.Type.REDSTONE;
      }
   }

   public boolean isConditionalCommandBlock() {
      BlockState _snowman = this.world.getBlockState(this.getPos());
      return _snowman.getBlock() instanceof CommandBlock ? _snowman.get(CommandBlock.CONDITIONAL) : false;
   }

   @Override
   public void cancelRemoval() {
      this.resetBlock();
      super.cancelRemoval();
   }

   public static enum Type {
      SEQUENCE,
      AUTO,
      REDSTONE;

      private Type() {
      }
   }
}
