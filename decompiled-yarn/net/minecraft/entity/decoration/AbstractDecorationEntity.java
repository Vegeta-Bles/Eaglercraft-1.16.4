package net.minecraft.entity.decoration;

import java.util.function.Predicate;
import javax.annotation.Nullable;
import net.minecraft.block.AbstractRedstoneGateBlock;
import net.minecraft.block.BlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.ItemEntity;
import net.minecraft.entity.LightningEntity;
import net.minecraft.entity.MovementType;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.BlockMirror;
import net.minecraft.util.BlockRotation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import org.apache.commons.lang3.Validate;

public abstract class AbstractDecorationEntity extends Entity {
   protected static final Predicate<Entity> PREDICATE = _snowman -> _snowman instanceof AbstractDecorationEntity;
   private int obstructionCheckCounter;
   protected BlockPos attachmentPos;
   protected Direction facing = Direction.SOUTH;

   protected AbstractDecorationEntity(EntityType<? extends AbstractDecorationEntity> _snowman, World _snowman) {
      super(_snowman, _snowman);
   }

   protected AbstractDecorationEntity(EntityType<? extends AbstractDecorationEntity> type, World world, BlockPos pos) {
      this(type, world);
      this.attachmentPos = pos;
   }

   @Override
   protected void initDataTracker() {
   }

   protected void setFacing(Direction facing) {
      Validate.notNull(facing);
      Validate.isTrue(facing.getAxis().isHorizontal());
      this.facing = facing;
      this.yaw = (float)(this.facing.getHorizontal() * 90);
      this.prevYaw = this.yaw;
      this.updateAttachmentPosition();
   }

   protected void updateAttachmentPosition() {
      if (this.facing != null) {
         double _snowman = (double)this.attachmentPos.getX() + 0.5;
         double _snowmanx = (double)this.attachmentPos.getY() + 0.5;
         double _snowmanxx = (double)this.attachmentPos.getZ() + 0.5;
         double _snowmanxxx = 0.46875;
         double _snowmanxxxx = this.method_6893(this.getWidthPixels());
         double _snowmanxxxxx = this.method_6893(this.getHeightPixels());
         _snowman -= (double)this.facing.getOffsetX() * 0.46875;
         _snowmanxx -= (double)this.facing.getOffsetZ() * 0.46875;
         _snowmanx += _snowmanxxxxx;
         Direction _snowmanxxxxxx = this.facing.rotateYCounterclockwise();
         _snowman += _snowmanxxxx * (double)_snowmanxxxxxx.getOffsetX();
         _snowmanxx += _snowmanxxxx * (double)_snowmanxxxxxx.getOffsetZ();
         this.setPos(_snowman, _snowmanx, _snowmanxx);
         double _snowmanxxxxxxx = (double)this.getWidthPixels();
         double _snowmanxxxxxxxx = (double)this.getHeightPixels();
         double _snowmanxxxxxxxxx = (double)this.getWidthPixels();
         if (this.facing.getAxis() == Direction.Axis.Z) {
            _snowmanxxxxxxxxx = 1.0;
         } else {
            _snowmanxxxxxxx = 1.0;
         }

         _snowmanxxxxxxx /= 32.0;
         _snowmanxxxxxxxx /= 32.0;
         _snowmanxxxxxxxxx /= 32.0;
         this.setBoundingBox(new Box(_snowman - _snowmanxxxxxxx, _snowmanx - _snowmanxxxxxxxx, _snowmanxx - _snowmanxxxxxxxxx, _snowman + _snowmanxxxxxxx, _snowmanx + _snowmanxxxxxxxx, _snowmanxx + _snowmanxxxxxxxxx));
      }
   }

   private double method_6893(int _snowman) {
      return _snowman % 32 == 0 ? 0.5 : 0.0;
   }

   @Override
   public void tick() {
      if (!this.world.isClient) {
         if (this.getY() < -64.0) {
            this.destroy();
         }

         if (this.obstructionCheckCounter++ == 100) {
            this.obstructionCheckCounter = 0;
            if (!this.removed && !this.canStayAttached()) {
               this.remove();
               this.onBreak(null);
            }
         }
      }
   }

   public boolean canStayAttached() {
      if (!this.world.isSpaceEmpty(this)) {
         return false;
      } else {
         int _snowman = Math.max(1, this.getWidthPixels() / 16);
         int _snowmanx = Math.max(1, this.getHeightPixels() / 16);
         BlockPos _snowmanxx = this.attachmentPos.offset(this.facing.getOpposite());
         Direction _snowmanxxx = this.facing.rotateYCounterclockwise();
         BlockPos.Mutable _snowmanxxxx = new BlockPos.Mutable();

         for (int _snowmanxxxxx = 0; _snowmanxxxxx < _snowman; _snowmanxxxxx++) {
            for (int _snowmanxxxxxx = 0; _snowmanxxxxxx < _snowmanx; _snowmanxxxxxx++) {
               int _snowmanxxxxxxx = (_snowman - 1) / -2;
               int _snowmanxxxxxxxx = (_snowmanx - 1) / -2;
               _snowmanxxxx.set(_snowmanxx).move(_snowmanxxx, _snowmanxxxxx + _snowmanxxxxxxx).move(Direction.UP, _snowmanxxxxxx + _snowmanxxxxxxxx);
               BlockState _snowmanxxxxxxxxx = this.world.getBlockState(_snowmanxxxx);
               if (!_snowmanxxxxxxxxx.getMaterial().isSolid() && !AbstractRedstoneGateBlock.isRedstoneGate(_snowmanxxxxxxxxx)) {
                  return false;
               }
            }
         }

         return this.world.getOtherEntities(this, this.getBoundingBox(), PREDICATE).isEmpty();
      }
   }

   @Override
   public boolean collides() {
      return true;
   }

   @Override
   public boolean handleAttack(Entity attacker) {
      if (attacker instanceof PlayerEntity) {
         PlayerEntity _snowman = (PlayerEntity)attacker;
         return !this.world.canPlayerModifyAt(_snowman, this.attachmentPos) ? true : this.damage(DamageSource.player(_snowman), 0.0F);
      } else {
         return false;
      }
   }

   @Override
   public Direction getHorizontalFacing() {
      return this.facing;
   }

   @Override
   public boolean damage(DamageSource source, float amount) {
      if (this.isInvulnerableTo(source)) {
         return false;
      } else {
         if (!this.removed && !this.world.isClient) {
            this.remove();
            this.scheduleVelocityUpdate();
            this.onBreak(source.getAttacker());
         }

         return true;
      }
   }

   @Override
   public void move(MovementType type, Vec3d movement) {
      if (!this.world.isClient && !this.removed && movement.lengthSquared() > 0.0) {
         this.remove();
         this.onBreak(null);
      }
   }

   @Override
   public void addVelocity(double deltaX, double deltaY, double deltaZ) {
      if (!this.world.isClient && !this.removed && deltaX * deltaX + deltaY * deltaY + deltaZ * deltaZ > 0.0) {
         this.remove();
         this.onBreak(null);
      }
   }

   @Override
   public void writeCustomDataToTag(CompoundTag tag) {
      BlockPos _snowman = this.getDecorationBlockPos();
      tag.putInt("TileX", _snowman.getX());
      tag.putInt("TileY", _snowman.getY());
      tag.putInt("TileZ", _snowman.getZ());
   }

   @Override
   public void readCustomDataFromTag(CompoundTag tag) {
      this.attachmentPos = new BlockPos(tag.getInt("TileX"), tag.getInt("TileY"), tag.getInt("TileZ"));
   }

   public abstract int getWidthPixels();

   public abstract int getHeightPixels();

   public abstract void onBreak(@Nullable Entity entity);

   public abstract void onPlace();

   @Override
   public ItemEntity dropStack(ItemStack stack, float yOffset) {
      ItemEntity _snowman = new ItemEntity(
         this.world,
         this.getX() + (double)((float)this.facing.getOffsetX() * 0.15F),
         this.getY() + (double)yOffset,
         this.getZ() + (double)((float)this.facing.getOffsetZ() * 0.15F),
         stack
      );
      _snowman.setToDefaultPickupDelay();
      this.world.spawnEntity(_snowman);
      return _snowman;
   }

   @Override
   protected boolean shouldSetPositionOnLoad() {
      return false;
   }

   @Override
   public void updatePosition(double x, double y, double z) {
      this.attachmentPos = new BlockPos(x, y, z);
      this.updateAttachmentPosition();
      this.velocityDirty = true;
   }

   public BlockPos getDecorationBlockPos() {
      return this.attachmentPos;
   }

   @Override
   public float applyRotation(BlockRotation rotation) {
      if (this.facing.getAxis() != Direction.Axis.Y) {
         switch (rotation) {
            case CLOCKWISE_180:
               this.facing = this.facing.getOpposite();
               break;
            case COUNTERCLOCKWISE_90:
               this.facing = this.facing.rotateYCounterclockwise();
               break;
            case CLOCKWISE_90:
               this.facing = this.facing.rotateYClockwise();
         }
      }

      float _snowman = MathHelper.wrapDegrees(this.yaw);
      switch (rotation) {
         case CLOCKWISE_180:
            return _snowman + 180.0F;
         case COUNTERCLOCKWISE_90:
            return _snowman + 90.0F;
         case CLOCKWISE_90:
            return _snowman + 270.0F;
         default:
            return _snowman;
      }
   }

   @Override
   public float applyMirror(BlockMirror mirror) {
      return this.applyRotation(mirror.getRotation(this.facing));
   }

   @Override
   public void onStruckByLightning(ServerWorld world, LightningEntity lightning) {
   }

   @Override
   public void calculateDimensions() {
   }
}
