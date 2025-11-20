package net.minecraft.entity;

import com.google.common.collect.Lists;
import java.util.List;
import net.minecraft.block.AnvilBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockEntityProvider;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.ConcretePowderBlock;
import net.minecraft.block.FallingBlock;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;
import net.minecraft.fluid.Fluids;
import net.minecraft.item.AutomaticItemPlacementContext;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.NbtHelper;
import net.minecraft.nbt.Tag;
import net.minecraft.network.Packet;
import net.minecraft.network.packet.s2c.play.EntitySpawnS2CPacket;
import net.minecraft.state.property.Properties;
import net.minecraft.tag.BlockTags;
import net.minecraft.tag.FluidTags;
import net.minecraft.util.crash.CrashReportSection;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.GameRules;
import net.minecraft.world.RaycastContext;
import net.minecraft.world.World;

public class FallingBlockEntity extends Entity {
   private BlockState block = Blocks.SAND.getDefaultState();
   public int timeFalling;
   public boolean dropItem = true;
   private boolean destroyedOnLanding;
   private boolean hurtEntities;
   private int fallHurtMax = 40;
   private float fallHurtAmount = 2.0F;
   public CompoundTag blockEntityData;
   protected static final TrackedData<BlockPos> BLOCK_POS = DataTracker.registerData(FallingBlockEntity.class, TrackedDataHandlerRegistry.BLOCK_POS);

   public FallingBlockEntity(EntityType<? extends FallingBlockEntity> _snowman, World _snowman) {
      super(_snowman, _snowman);
   }

   public FallingBlockEntity(World world, double x, double y, double z, BlockState block) {
      this(EntityType.FALLING_BLOCK, world);
      this.block = block;
      this.inanimate = true;
      this.updatePosition(x, y + (double)((1.0F - this.getHeight()) / 2.0F), z);
      this.setVelocity(Vec3d.ZERO);
      this.prevX = x;
      this.prevY = y;
      this.prevZ = z;
      this.setFallingBlockPos(this.getBlockPos());
   }

   @Override
   public boolean isAttackable() {
      return false;
   }

   public void setFallingBlockPos(BlockPos pos) {
      this.dataTracker.set(BLOCK_POS, pos);
   }

   public BlockPos getFallingBlockPos() {
      return this.dataTracker.get(BLOCK_POS);
   }

   @Override
   protected boolean canClimb() {
      return false;
   }

   @Override
   protected void initDataTracker() {
      this.dataTracker.startTracking(BLOCK_POS, BlockPos.ORIGIN);
   }

   @Override
   public boolean collides() {
      return !this.removed;
   }

   @Override
   public void tick() {
      if (this.block.isAir()) {
         this.remove();
      } else {
         Block _snowman = this.block.getBlock();
         if (this.timeFalling++ == 0) {
            BlockPos _snowmanx = this.getBlockPos();
            if (this.world.getBlockState(_snowmanx).isOf(_snowman)) {
               this.world.removeBlock(_snowmanx, false);
            } else if (!this.world.isClient) {
               this.remove();
               return;
            }
         }

         if (!this.hasNoGravity()) {
            this.setVelocity(this.getVelocity().add(0.0, -0.04, 0.0));
         }

         this.move(MovementType.SELF, this.getVelocity());
         if (!this.world.isClient) {
            BlockPos _snowmanx = this.getBlockPos();
            boolean _snowmanxx = this.block.getBlock() instanceof ConcretePowderBlock;
            boolean _snowmanxxx = _snowmanxx && this.world.getFluidState(_snowmanx).isIn(FluidTags.WATER);
            double _snowmanxxxx = this.getVelocity().lengthSquared();
            if (_snowmanxx && _snowmanxxxx > 1.0) {
               BlockHitResult _snowmanxxxxx = this.world
                  .raycast(
                     new RaycastContext(
                        new Vec3d(this.prevX, this.prevY, this.prevZ),
                        this.getPos(),
                        RaycastContext.ShapeType.COLLIDER,
                        RaycastContext.FluidHandling.SOURCE_ONLY,
                        this
                     )
                  );
               if (_snowmanxxxxx.getType() != HitResult.Type.MISS && this.world.getFluidState(_snowmanxxxxx.getBlockPos()).isIn(FluidTags.WATER)) {
                  _snowmanx = _snowmanxxxxx.getBlockPos();
                  _snowmanxxx = true;
               }
            }

            if (this.onGround || _snowmanxxx) {
               BlockState _snowmanxxxxx = this.world.getBlockState(_snowmanx);
               this.setVelocity(this.getVelocity().multiply(0.7, -0.5, 0.7));
               if (!_snowmanxxxxx.isOf(Blocks.MOVING_PISTON)) {
                  this.remove();
                  if (!this.destroyedOnLanding) {
                     boolean _snowmanxxxxxx = _snowmanxxxxx.canReplace(new AutomaticItemPlacementContext(this.world, _snowmanx, Direction.DOWN, ItemStack.EMPTY, Direction.UP));
                     boolean _snowmanxxxxxxx = FallingBlock.canFallThrough(this.world.getBlockState(_snowmanx.down())) && (!_snowmanxx || !_snowmanxxx);
                     boolean _snowmanxxxxxxxx = this.block.canPlaceAt(this.world, _snowmanx) && !_snowmanxxxxxxx;
                     if (_snowmanxxxxxx && _snowmanxxxxxxxx) {
                        if (this.block.contains(Properties.WATERLOGGED) && this.world.getFluidState(_snowmanx).getFluid() == Fluids.WATER) {
                           this.block = this.block.with(Properties.WATERLOGGED, Boolean.valueOf(true));
                        }

                        if (this.world.setBlockState(_snowmanx, this.block, 3)) {
                           if (_snowman instanceof FallingBlock) {
                              ((FallingBlock)_snowman).onLanding(this.world, _snowmanx, this.block, _snowmanxxxxx, this);
                           }

                           if (this.blockEntityData != null && _snowman instanceof BlockEntityProvider) {
                              BlockEntity _snowmanxxxxxxxxx = this.world.getBlockEntity(_snowmanx);
                              if (_snowmanxxxxxxxxx != null) {
                                 CompoundTag _snowmanxxxxxxxxxx = _snowmanxxxxxxxxx.toTag(new CompoundTag());

                                 for (String _snowmanxxxxxxxxxxx : this.blockEntityData.getKeys()) {
                                    Tag _snowmanxxxxxxxxxxxx = this.blockEntityData.get(_snowmanxxxxxxxxxxx);
                                    if (!"x".equals(_snowmanxxxxxxxxxxx) && !"y".equals(_snowmanxxxxxxxxxxx) && !"z".equals(_snowmanxxxxxxxxxxx)) {
                                       _snowmanxxxxxxxxxx.put(_snowmanxxxxxxxxxxx, _snowmanxxxxxxxxxxxx.copy());
                                    }
                                 }

                                 _snowmanxxxxxxxxx.fromTag(this.block, _snowmanxxxxxxxxxx);
                                 _snowmanxxxxxxxxx.markDirty();
                              }
                           }
                        } else if (this.dropItem && this.world.getGameRules().getBoolean(GameRules.DO_ENTITY_DROPS)) {
                           this.dropItem(_snowman);
                        }
                     } else if (this.dropItem && this.world.getGameRules().getBoolean(GameRules.DO_ENTITY_DROPS)) {
                        this.dropItem(_snowman);
                     }
                  } else if (_snowman instanceof FallingBlock) {
                     ((FallingBlock)_snowman).onDestroyedOnLanding(this.world, _snowmanx, this);
                  }
               }
            } else if (!this.world.isClient && (this.timeFalling > 100 && (_snowmanx.getY() < 1 || _snowmanx.getY() > 256) || this.timeFalling > 600)) {
               if (this.dropItem && this.world.getGameRules().getBoolean(GameRules.DO_ENTITY_DROPS)) {
                  this.dropItem(_snowman);
               }

               this.remove();
            }
         }

         this.setVelocity(this.getVelocity().multiply(0.98));
      }
   }

   @Override
   public boolean handleFallDamage(float fallDistance, float damageMultiplier) {
      if (this.hurtEntities) {
         int _snowman = MathHelper.ceil(fallDistance - 1.0F);
         if (_snowman > 0) {
            List<Entity> _snowmanx = Lists.newArrayList(this.world.getOtherEntities(this, this.getBoundingBox()));
            boolean _snowmanxx = this.block.isIn(BlockTags.ANVIL);
            DamageSource _snowmanxxx = _snowmanxx ? DamageSource.ANVIL : DamageSource.FALLING_BLOCK;

            for (Entity _snowmanxxxx : _snowmanx) {
               _snowmanxxxx.damage(_snowmanxxx, (float)Math.min(MathHelper.floor((float)_snowman * this.fallHurtAmount), this.fallHurtMax));
            }

            if (_snowmanxx && (double)this.random.nextFloat() < 0.05F + (double)_snowman * 0.05) {
               BlockState _snowmanxxxx = AnvilBlock.getLandingState(this.block);
               if (_snowmanxxxx == null) {
                  this.destroyedOnLanding = true;
               } else {
                  this.block = _snowmanxxxx;
               }
            }
         }
      }

      return false;
   }

   @Override
   protected void writeCustomDataToTag(CompoundTag tag) {
      tag.put("BlockState", NbtHelper.fromBlockState(this.block));
      tag.putInt("Time", this.timeFalling);
      tag.putBoolean("DropItem", this.dropItem);
      tag.putBoolean("HurtEntities", this.hurtEntities);
      tag.putFloat("FallHurtAmount", this.fallHurtAmount);
      tag.putInt("FallHurtMax", this.fallHurtMax);
      if (this.blockEntityData != null) {
         tag.put("TileEntityData", this.blockEntityData);
      }
   }

   @Override
   protected void readCustomDataFromTag(CompoundTag tag) {
      this.block = NbtHelper.toBlockState(tag.getCompound("BlockState"));
      this.timeFalling = tag.getInt("Time");
      if (tag.contains("HurtEntities", 99)) {
         this.hurtEntities = tag.getBoolean("HurtEntities");
         this.fallHurtAmount = tag.getFloat("FallHurtAmount");
         this.fallHurtMax = tag.getInt("FallHurtMax");
      } else if (this.block.isIn(BlockTags.ANVIL)) {
         this.hurtEntities = true;
      }

      if (tag.contains("DropItem", 99)) {
         this.dropItem = tag.getBoolean("DropItem");
      }

      if (tag.contains("TileEntityData", 10)) {
         this.blockEntityData = tag.getCompound("TileEntityData");
      }

      if (this.block.isAir()) {
         this.block = Blocks.SAND.getDefaultState();
      }
   }

   public World getWorldClient() {
      return this.world;
   }

   public void setHurtEntities(boolean hurtEntities) {
      this.hurtEntities = hurtEntities;
   }

   @Override
   public boolean doesRenderOnFire() {
      return false;
   }

   @Override
   public void populateCrashReport(CrashReportSection section) {
      super.populateCrashReport(section);
      section.add("Immitating BlockState", this.block.toString());
   }

   public BlockState getBlockState() {
      return this.block;
   }

   @Override
   public boolean entityDataRequiresOperator() {
      return true;
   }

   @Override
   public Packet<?> createSpawnPacket() {
      return new EntitySpawnS2CPacket(this, Block.getRawIdFromState(this.getBlockState()));
   }
}
