package net.minecraft.entity.decoration.painting;

import com.google.common.collect.Lists;
import java.util.Iterator;
import java.util.List;
import javax.annotation.Nullable;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.decoration.AbstractDecorationEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Items;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.Packet;
import net.minecraft.network.packet.s2c.play.PaintingSpawnS2CPacket;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.GameRules;
import net.minecraft.world.World;

public class PaintingEntity extends AbstractDecorationEntity {
   public PaintingMotive motive;

   public PaintingEntity(EntityType<? extends PaintingEntity> _snowman, World _snowman) {
      super(_snowman, _snowman);
   }

   public PaintingEntity(World world, BlockPos pos, Direction direction) {
      super(EntityType.PAINTING, world, pos);
      List<PaintingMotive> _snowman = Lists.newArrayList();
      int _snowmanx = 0;

      for (PaintingMotive _snowmanxx : Registry.PAINTING_MOTIVE) {
         this.motive = _snowmanxx;
         this.setFacing(direction);
         if (this.canStayAttached()) {
            _snowman.add(_snowmanxx);
            int _snowmanxxx = _snowmanxx.getWidth() * _snowmanxx.getHeight();
            if (_snowmanxxx > _snowmanx) {
               _snowmanx = _snowmanxxx;
            }
         }
      }

      if (!_snowman.isEmpty()) {
         Iterator<PaintingMotive> _snowmanxxx = _snowman.iterator();

         while (_snowmanxxx.hasNext()) {
            PaintingMotive _snowmanxxxx = _snowmanxxx.next();
            if (_snowmanxxxx.getWidth() * _snowmanxxxx.getHeight() < _snowmanx) {
               _snowmanxxx.remove();
            }
         }

         this.motive = _snowman.get(this.random.nextInt(_snowman.size()));
      }

      this.setFacing(direction);
   }

   public PaintingEntity(World world, BlockPos pos, Direction direction, PaintingMotive motive) {
      this(world, pos, direction);
      this.motive = motive;
      this.setFacing(direction);
   }

   @Override
   public void writeCustomDataToTag(CompoundTag tag) {
      tag.putString("Motive", Registry.PAINTING_MOTIVE.getId(this.motive).toString());
      tag.putByte("Facing", (byte)this.facing.getHorizontal());
      super.writeCustomDataToTag(tag);
   }

   @Override
   public void readCustomDataFromTag(CompoundTag tag) {
      this.motive = Registry.PAINTING_MOTIVE.get(Identifier.tryParse(tag.getString("Motive")));
      this.facing = Direction.fromHorizontal(tag.getByte("Facing"));
      super.readCustomDataFromTag(tag);
      this.setFacing(this.facing);
   }

   @Override
   public int getWidthPixels() {
      return this.motive == null ? 1 : this.motive.getWidth();
   }

   @Override
   public int getHeightPixels() {
      return this.motive == null ? 1 : this.motive.getHeight();
   }

   @Override
   public void onBreak(@Nullable Entity entity) {
      if (this.world.getGameRules().getBoolean(GameRules.DO_ENTITY_DROPS)) {
         this.playSound(SoundEvents.ENTITY_PAINTING_BREAK, 1.0F, 1.0F);
         if (entity instanceof PlayerEntity) {
            PlayerEntity _snowman = (PlayerEntity)entity;
            if (_snowman.abilities.creativeMode) {
               return;
            }
         }

         this.dropItem(Items.PAINTING);
      }
   }

   @Override
   public void onPlace() {
      this.playSound(SoundEvents.ENTITY_PAINTING_PLACE, 1.0F, 1.0F);
   }

   @Override
   public void refreshPositionAndAngles(double x, double y, double z, float yaw, float pitch) {
      this.updatePosition(x, y, z);
   }

   @Override
   public void updateTrackedPositionAndAngles(double x, double y, double z, float yaw, float pitch, int interpolationSteps, boolean interpolate) {
      BlockPos _snowman = this.attachmentPos.add(x - this.getX(), y - this.getY(), z - this.getZ());
      this.updatePosition((double)_snowman.getX(), (double)_snowman.getY(), (double)_snowman.getZ());
   }

   @Override
   public Packet<?> createSpawnPacket() {
      return new PaintingSpawnS2CPacket(this);
   }
}
