package net.minecraft.block.entity;

import com.google.common.collect.Lists;
import java.util.Iterator;
import java.util.List;
import javax.annotation.Nullable;
import net.minecraft.block.BeehiveBlock;
import net.minecraft.block.BlockState;
import net.minecraft.block.CampfireBlock;
import net.minecraft.block.FireBlock;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.passive.BeeEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.NbtHelper;
import net.minecraft.server.network.DebugInfoSender;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.tag.BlockTags;
import net.minecraft.tag.EntityTypeTags;
import net.minecraft.util.Tickable;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;

public class BeehiveBlockEntity extends BlockEntity implements Tickable {
   private final List<BeehiveBlockEntity.Bee> bees = Lists.newArrayList();
   @Nullable
   private BlockPos flowerPos = null;

   public BeehiveBlockEntity() {
      super(BlockEntityType.BEEHIVE);
   }

   @Override
   public void markDirty() {
      if (this.isNearFire()) {
         this.angerBees(null, this.world.getBlockState(this.getPos()), BeehiveBlockEntity.BeeState.EMERGENCY);
      }

      super.markDirty();
   }

   public boolean isNearFire() {
      if (this.world == null) {
         return false;
      } else {
         for (BlockPos _snowman : BlockPos.iterate(this.pos.add(-1, -1, -1), this.pos.add(1, 1, 1))) {
            if (this.world.getBlockState(_snowman).getBlock() instanceof FireBlock) {
               return true;
            }
         }

         return false;
      }
   }

   public boolean hasNoBees() {
      return this.bees.isEmpty();
   }

   public boolean isFullOfBees() {
      return this.bees.size() == 3;
   }

   public void angerBees(@Nullable PlayerEntity player, BlockState state, BeehiveBlockEntity.BeeState _snowman) {
      List<Entity> _snowmanx = this.tryReleaseBee(state, _snowman);
      if (player != null) {
         for (Entity _snowmanxx : _snowmanx) {
            if (_snowmanxx instanceof BeeEntity) {
               BeeEntity _snowmanxxx = (BeeEntity)_snowmanxx;
               if (player.getPos().squaredDistanceTo(_snowmanxx.getPos()) <= 16.0) {
                  if (!this.isSmoked()) {
                     _snowmanxxx.setTarget(player);
                  } else {
                     _snowmanxxx.setCannotEnterHiveTicks(400);
                  }
               }
            }
         }
      }
   }

   private List<Entity> tryReleaseBee(BlockState state, BeehiveBlockEntity.BeeState _snowman) {
      List<Entity> _snowmanx = Lists.newArrayList();
      this.bees.removeIf(bee -> this.releaseBee(state, bee, _snowman, _snowman));
      return _snowmanx;
   }

   public void tryEnterHive(Entity entity, boolean hasNectar) {
      this.tryEnterHive(entity, hasNectar, 0);
   }

   public int getBeeCount() {
      return this.bees.size();
   }

   public static int getHoneyLevel(BlockState state) {
      return state.get(BeehiveBlock.HONEY_LEVEL);
   }

   public boolean isSmoked() {
      return CampfireBlock.isLitCampfireInRange(this.world, this.getPos());
   }

   protected void sendDebugData() {
      DebugInfoSender.sendBeehiveDebugData(this);
   }

   public void tryEnterHive(Entity entity, boolean hasNectar, int ticksInHive) {
      if (this.bees.size() < 3) {
         entity.stopRiding();
         entity.removeAllPassengers();
         CompoundTag _snowman = new CompoundTag();
         entity.saveToTag(_snowman);
         this.bees.add(new BeehiveBlockEntity.Bee(_snowman, ticksInHive, hasNectar ? 2400 : 600));
         if (this.world != null) {
            if (entity instanceof BeeEntity) {
               BeeEntity _snowmanx = (BeeEntity)entity;
               if (_snowmanx.hasFlower() && (!this.hasFlowerPos() || this.world.random.nextBoolean())) {
                  this.flowerPos = _snowmanx.getFlowerPos();
               }
            }

            BlockPos _snowmanx = this.getPos();
            this.world
               .playSound(null, (double)_snowmanx.getX(), (double)_snowmanx.getY(), (double)_snowmanx.getZ(), SoundEvents.BLOCK_BEEHIVE_ENTER, SoundCategory.BLOCKS, 1.0F, 1.0F);
         }

         entity.remove();
      }
   }

   private boolean releaseBee(BlockState state, BeehiveBlockEntity.Bee bee, @Nullable List<Entity> _snowman, BeehiveBlockEntity.BeeState beeState) {
      if ((this.world.isNight() || this.world.isRaining()) && beeState != BeehiveBlockEntity.BeeState.EMERGENCY) {
         return false;
      } else {
         BlockPos _snowmanx = this.getPos();
         CompoundTag _snowmanxx = bee.entityData;
         _snowmanxx.remove("Passengers");
         _snowmanxx.remove("Leash");
         _snowmanxx.remove("UUID");
         Direction _snowmanxxx = state.get(BeehiveBlock.FACING);
         BlockPos _snowmanxxxx = _snowmanx.offset(_snowmanxxx);
         boolean _snowmanxxxxx = !this.world.getBlockState(_snowmanxxxx).getCollisionShape(this.world, _snowmanxxxx).isEmpty();
         if (_snowmanxxxxx && beeState != BeehiveBlockEntity.BeeState.EMERGENCY) {
            return false;
         } else {
            Entity _snowmanxxxxxx = EntityType.loadEntityWithPassengers(_snowmanxx, this.world, _snowmanxxxxxxx -> _snowmanxxxxxxx);
            if (_snowmanxxxxxx != null) {
               if (!_snowmanxxxxxx.getType().isIn(EntityTypeTags.BEEHIVE_INHABITORS)) {
                  return false;
               } else {
                  if (_snowmanxxxxxx instanceof BeeEntity) {
                     BeeEntity _snowmanxxxxxxx = (BeeEntity)_snowmanxxxxxx;
                     if (this.hasFlowerPos() && !_snowmanxxxxxxx.hasFlower() && this.world.random.nextFloat() < 0.9F) {
                        _snowmanxxxxxxx.setFlowerPos(this.flowerPos);
                     }

                     if (beeState == BeehiveBlockEntity.BeeState.HONEY_DELIVERED) {
                        _snowmanxxxxxxx.onHoneyDelivered();
                        if (state.getBlock().isIn(BlockTags.BEEHIVES)) {
                           int _snowmanxxxxxxxx = getHoneyLevel(state);
                           if (_snowmanxxxxxxxx < 5) {
                              int _snowmanxxxxxxxxx = this.world.random.nextInt(100) == 0 ? 2 : 1;
                              if (_snowmanxxxxxxxx + _snowmanxxxxxxxxx > 5) {
                                 _snowmanxxxxxxxxx--;
                              }

                              this.world.setBlockState(this.getPos(), state.with(BeehiveBlock.HONEY_LEVEL, Integer.valueOf(_snowmanxxxxxxxx + _snowmanxxxxxxxxx)));
                           }
                        }
                     }

                     this.ageBee(bee.ticksInHive, _snowmanxxxxxxx);
                     if (_snowman != null) {
                        _snowman.add(_snowmanxxxxxxx);
                     }

                     float _snowmanxxxxxxxx = _snowmanxxxxxx.getWidth();
                     double _snowmanxxxxxxxxx = _snowmanxxxxx ? 0.0 : 0.55 + (double)(_snowmanxxxxxxxx / 2.0F);
                     double _snowmanxxxxxxxxxx = (double)_snowmanx.getX() + 0.5 + _snowmanxxxxxxxxx * (double)_snowmanxxx.getOffsetX();
                     double _snowmanxxxxxxxxxxx = (double)_snowmanx.getY() + 0.5 - (double)(_snowmanxxxxxx.getHeight() / 2.0F);
                     double _snowmanxxxxxxxxxxxx = (double)_snowmanx.getZ() + 0.5 + _snowmanxxxxxxxxx * (double)_snowmanxxx.getOffsetZ();
                     _snowmanxxxxxx.refreshPositionAndAngles(_snowmanxxxxxxxxxx, _snowmanxxxxxxxxxxx, _snowmanxxxxxxxxxxxx, _snowmanxxxxxx.yaw, _snowmanxxxxxx.pitch);
                  }

                  this.world.playSound(null, _snowmanx, SoundEvents.BLOCK_BEEHIVE_EXIT, SoundCategory.BLOCKS, 1.0F, 1.0F);
                  return this.world.spawnEntity(_snowmanxxxxxx);
               }
            } else {
               return false;
            }
         }
      }
   }

   private void ageBee(int ticks, BeeEntity bee) {
      int _snowman = bee.getBreedingAge();
      if (_snowman < 0) {
         bee.setBreedingAge(Math.min(0, _snowman + ticks));
      } else if (_snowman > 0) {
         bee.setBreedingAge(Math.max(0, _snowman - ticks));
      }

      bee.setLoveTicks(Math.max(0, bee.getLoveTicks() - ticks));
      bee.resetPollinationTicks();
   }

   private boolean hasFlowerPos() {
      return this.flowerPos != null;
   }

   private void tickBees() {
      Iterator<BeehiveBlockEntity.Bee> _snowman = this.bees.iterator();
      BlockState _snowmanx = this.getCachedState();

      while (_snowman.hasNext()) {
         BeehiveBlockEntity.Bee _snowmanxx = _snowman.next();
         if (_snowmanxx.ticksInHive > _snowmanxx.minOccupationTicks) {
            BeehiveBlockEntity.BeeState _snowmanxxx = _snowmanxx.entityData.getBoolean("HasNectar")
               ? BeehiveBlockEntity.BeeState.HONEY_DELIVERED
               : BeehiveBlockEntity.BeeState.BEE_RELEASED;
            if (this.releaseBee(_snowmanx, _snowmanxx, null, _snowmanxxx)) {
               _snowman.remove();
            }
         }

         _snowmanxx.ticksInHive++;
      }
   }

   @Override
   public void tick() {
      if (!this.world.isClient) {
         this.tickBees();
         BlockPos _snowman = this.getPos();
         if (this.bees.size() > 0 && this.world.getRandom().nextDouble() < 0.005) {
            double _snowmanx = (double)_snowman.getX() + 0.5;
            double _snowmanxx = (double)_snowman.getY();
            double _snowmanxxx = (double)_snowman.getZ() + 0.5;
            this.world.playSound(null, _snowmanx, _snowmanxx, _snowmanxxx, SoundEvents.BLOCK_BEEHIVE_WORK, SoundCategory.BLOCKS, 1.0F, 1.0F);
         }

         this.sendDebugData();
      }
   }

   @Override
   public void fromTag(BlockState state, CompoundTag tag) {
      super.fromTag(state, tag);
      this.bees.clear();
      ListTag _snowman = tag.getList("Bees", 10);

      for (int _snowmanx = 0; _snowmanx < _snowman.size(); _snowmanx++) {
         CompoundTag _snowmanxx = _snowman.getCompound(_snowmanx);
         BeehiveBlockEntity.Bee _snowmanxxx = new BeehiveBlockEntity.Bee(_snowmanxx.getCompound("EntityData"), _snowmanxx.getInt("TicksInHive"), _snowmanxx.getInt("MinOccupationTicks"));
         this.bees.add(_snowmanxxx);
      }

      this.flowerPos = null;
      if (tag.contains("FlowerPos")) {
         this.flowerPos = NbtHelper.toBlockPos(tag.getCompound("FlowerPos"));
      }
   }

   @Override
   public CompoundTag toTag(CompoundTag tag) {
      super.toTag(tag);
      tag.put("Bees", this.getBees());
      if (this.hasFlowerPos()) {
         tag.put("FlowerPos", NbtHelper.fromBlockPos(this.flowerPos));
      }

      return tag;
   }

   public ListTag getBees() {
      ListTag _snowman = new ListTag();

      for (BeehiveBlockEntity.Bee _snowmanx : this.bees) {
         _snowmanx.entityData.remove("UUID");
         CompoundTag _snowmanxx = new CompoundTag();
         _snowmanxx.put("EntityData", _snowmanx.entityData);
         _snowmanxx.putInt("TicksInHive", _snowmanx.ticksInHive);
         _snowmanxx.putInt("MinOccupationTicks", _snowmanx.minOccupationTicks);
         _snowman.add(_snowmanxx);
      }

      return _snowman;
   }

   static class Bee {
      private final CompoundTag entityData;
      private int ticksInHive;
      private final int minOccupationTicks;

      private Bee(CompoundTag entityData, int ticksInHive, int minOccupationTicks) {
         entityData.remove("UUID");
         this.entityData = entityData;
         this.ticksInHive = ticksInHive;
         this.minOccupationTicks = minOccupationTicks;
      }
   }

   public static enum BeeState {
      HONEY_DELIVERED,
      BEE_RELEASED,
      EMERGENCY;

      private BeeState() {
      }
   }
}
