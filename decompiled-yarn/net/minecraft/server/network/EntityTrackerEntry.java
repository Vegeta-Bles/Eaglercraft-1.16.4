package net.minecraft.server.network;

import com.google.common.collect.Lists;
import com.mojang.datafixers.util.Pair;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.function.Consumer;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.attribute.EntityAttributeInstance;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.decoration.ItemFrameEntity;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.projectile.PersistentProjectileEntity;
import net.minecraft.item.FilledMapItem;
import net.minecraft.item.ItemStack;
import net.minecraft.item.map.MapState;
import net.minecraft.network.Packet;
import net.minecraft.network.packet.s2c.play.EntityAttachS2CPacket;
import net.minecraft.network.packet.s2c.play.EntityAttributesS2CPacket;
import net.minecraft.network.packet.s2c.play.EntityEquipmentUpdateS2CPacket;
import net.minecraft.network.packet.s2c.play.EntityPassengersSetS2CPacket;
import net.minecraft.network.packet.s2c.play.EntityPositionS2CPacket;
import net.minecraft.network.packet.s2c.play.EntityS2CPacket;
import net.minecraft.network.packet.s2c.play.EntitySetHeadYawS2CPacket;
import net.minecraft.network.packet.s2c.play.EntityStatusEffectS2CPacket;
import net.minecraft.network.packet.s2c.play.EntityTrackerUpdateS2CPacket;
import net.minecraft.network.packet.s2c.play.EntityVelocityUpdateS2CPacket;
import net.minecraft.network.packet.s2c.play.MobSpawnS2CPacket;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class EntityTrackerEntry {
   private static final Logger LOGGER = LogManager.getLogger();
   private final ServerWorld world;
   private final Entity entity;
   private final int tickInterval;
   private final boolean alwaysUpdateVelocity;
   private final Consumer<Packet<?>> receiver;
   private long lastX;
   private long lastY;
   private long lastZ;
   private int lastYaw;
   private int lastPitch;
   private int lastHeadPitch;
   private Vec3d velocity = Vec3d.ZERO;
   private int trackingTick;
   private int updatesWithoutVehicle;
   private List<Entity> lastPassengers = Collections.emptyList();
   private boolean hadVehicle;
   private boolean lastOnGround;

   public EntityTrackerEntry(ServerWorld world, Entity entity, int tickInterval, boolean alwaysUpdateVelocity, Consumer<Packet<?>> receiver) {
      this.world = world;
      this.receiver = receiver;
      this.entity = entity;
      this.tickInterval = tickInterval;
      this.alwaysUpdateVelocity = alwaysUpdateVelocity;
      this.storeEncodedCoordinates();
      this.lastYaw = MathHelper.floor(entity.yaw * 256.0F / 360.0F);
      this.lastPitch = MathHelper.floor(entity.pitch * 256.0F / 360.0F);
      this.lastHeadPitch = MathHelper.floor(entity.getHeadYaw() * 256.0F / 360.0F);
      this.lastOnGround = entity.isOnGround();
   }

   public void tick() {
      List<Entity> _snowman = this.entity.getPassengerList();
      if (!_snowman.equals(this.lastPassengers)) {
         this.lastPassengers = _snowman;
         this.receiver.accept(new EntityPassengersSetS2CPacket(this.entity));
      }

      if (this.entity instanceof ItemFrameEntity && this.trackingTick % 10 == 0) {
         ItemFrameEntity _snowmanx = (ItemFrameEntity)this.entity;
         ItemStack _snowmanxx = _snowmanx.getHeldItemStack();
         if (_snowmanxx.getItem() instanceof FilledMapItem) {
            MapState _snowmanxxx = FilledMapItem.getOrCreateMapState(_snowmanxx, this.world);

            for (ServerPlayerEntity _snowmanxxxx : this.world.getPlayers()) {
               _snowmanxxx.update(_snowmanxxxx, _snowmanxx);
               Packet<?> _snowmanxxxxx = ((FilledMapItem)_snowmanxx.getItem()).createSyncPacket(_snowmanxx, this.world, _snowmanxxxx);
               if (_snowmanxxxxx != null) {
                  _snowmanxxxx.networkHandler.sendPacket(_snowmanxxxxx);
               }
            }
         }

         this.syncEntityData();
      }

      if (this.trackingTick % this.tickInterval == 0 || this.entity.velocityDirty || this.entity.getDataTracker().isDirty()) {
         if (this.entity.hasVehicle()) {
            int _snowmanx = MathHelper.floor(this.entity.yaw * 256.0F / 360.0F);
            int _snowmanxx = MathHelper.floor(this.entity.pitch * 256.0F / 360.0F);
            boolean _snowmanxxx = Math.abs(_snowmanx - this.lastYaw) >= 1 || Math.abs(_snowmanxx - this.lastPitch) >= 1;
            if (_snowmanxxx) {
               this.receiver.accept(new EntityS2CPacket.Rotate(this.entity.getEntityId(), (byte)_snowmanx, (byte)_snowmanxx, this.entity.isOnGround()));
               this.lastYaw = _snowmanx;
               this.lastPitch = _snowmanxx;
            }

            this.storeEncodedCoordinates();
            this.syncEntityData();
            this.hadVehicle = true;
         } else {
            this.updatesWithoutVehicle++;
            int _snowmanx = MathHelper.floor(this.entity.yaw * 256.0F / 360.0F);
            int _snowmanxx = MathHelper.floor(this.entity.pitch * 256.0F / 360.0F);
            Vec3d _snowmanxxx = this.entity.getPos().subtract(EntityS2CPacket.decodePacketCoordinates(this.lastX, this.lastY, this.lastZ));
            boolean _snowmanxxxxx = _snowmanxxx.lengthSquared() >= 7.6293945E-6F;
            Packet<?> _snowmanxxxxxx = null;
            boolean _snowmanxxxxxxx = _snowmanxxxxx || this.trackingTick % 60 == 0;
            boolean _snowmanxxxxxxxx = Math.abs(_snowmanx - this.lastYaw) >= 1 || Math.abs(_snowmanxx - this.lastPitch) >= 1;
            if (this.trackingTick > 0 || this.entity instanceof PersistentProjectileEntity) {
               long _snowmanxxxxxxxxx = EntityS2CPacket.encodePacketCoordinate(_snowmanxxx.x);
               long _snowmanxxxxxxxxxx = EntityS2CPacket.encodePacketCoordinate(_snowmanxxx.y);
               long _snowmanxxxxxxxxxxx = EntityS2CPacket.encodePacketCoordinate(_snowmanxxx.z);
               boolean _snowmanxxxxxxxxxxxx = _snowmanxxxxxxxxx < -32768L
                  || _snowmanxxxxxxxxx > 32767L
                  || _snowmanxxxxxxxxxx < -32768L
                  || _snowmanxxxxxxxxxx > 32767L
                  || _snowmanxxxxxxxxxxx < -32768L
                  || _snowmanxxxxxxxxxxx > 32767L;
               if (_snowmanxxxxxxxxxxxx || this.updatesWithoutVehicle > 400 || this.hadVehicle || this.lastOnGround != this.entity.isOnGround()) {
                  this.lastOnGround = this.entity.isOnGround();
                  this.updatesWithoutVehicle = 0;
                  _snowmanxxxxxx = new EntityPositionS2CPacket(this.entity);
               } else if ((!_snowmanxxxxxxx || !_snowmanxxxxxxxx) && !(this.entity instanceof PersistentProjectileEntity)) {
                  if (_snowmanxxxxxxx) {
                     _snowmanxxxxxx = new EntityS2CPacket.MoveRelative(
                        this.entity.getEntityId(), (short)((int)_snowmanxxxxxxxxx), (short)((int)_snowmanxxxxxxxxxx), (short)((int)_snowmanxxxxxxxxxxx), this.entity.isOnGround()
                     );
                  } else if (_snowmanxxxxxxxx) {
                     _snowmanxxxxxx = new EntityS2CPacket.Rotate(this.entity.getEntityId(), (byte)_snowmanx, (byte)_snowmanxx, this.entity.isOnGround());
                  }
               } else {
                  _snowmanxxxxxx = new EntityS2CPacket.RotateAndMoveRelative(
                     this.entity.getEntityId(),
                     (short)((int)_snowmanxxxxxxxxx),
                     (short)((int)_snowmanxxxxxxxxxx),
                     (short)((int)_snowmanxxxxxxxxxxx),
                     (byte)_snowmanx,
                     (byte)_snowmanxx,
                     this.entity.isOnGround()
                  );
               }
            }

            if ((this.alwaysUpdateVelocity || this.entity.velocityDirty || this.entity instanceof LivingEntity && ((LivingEntity)this.entity).isFallFlying())
               && this.trackingTick > 0) {
               Vec3d _snowmanxxxxxxxxx = this.entity.getVelocity();
               double _snowmanxxxxxxxxxx = _snowmanxxxxxxxxx.squaredDistanceTo(this.velocity);
               if (_snowmanxxxxxxxxxx > 1.0E-7 || _snowmanxxxxxxxxxx > 0.0 && _snowmanxxxxxxxxx.lengthSquared() == 0.0) {
                  this.velocity = _snowmanxxxxxxxxx;
                  this.receiver.accept(new EntityVelocityUpdateS2CPacket(this.entity.getEntityId(), this.velocity));
               }
            }

            if (_snowmanxxxxxx != null) {
               this.receiver.accept(_snowmanxxxxxx);
            }

            this.syncEntityData();
            if (_snowmanxxxxxxx) {
               this.storeEncodedCoordinates();
            }

            if (_snowmanxxxxxxxx) {
               this.lastYaw = _snowmanx;
               this.lastPitch = _snowmanxx;
            }

            this.hadVehicle = false;
         }

         int _snowmanxxxxxxxxx = MathHelper.floor(this.entity.getHeadYaw() * 256.0F / 360.0F);
         if (Math.abs(_snowmanxxxxxxxxx - this.lastHeadPitch) >= 1) {
            this.receiver.accept(new EntitySetHeadYawS2CPacket(this.entity, (byte)_snowmanxxxxxxxxx));
            this.lastHeadPitch = _snowmanxxxxxxxxx;
         }

         this.entity.velocityDirty = false;
      }

      this.trackingTick++;
      if (this.entity.velocityModified) {
         this.sendSyncPacket(new EntityVelocityUpdateS2CPacket(this.entity));
         this.entity.velocityModified = false;
      }
   }

   public void stopTracking(ServerPlayerEntity player) {
      this.entity.onStoppedTrackingBy(player);
      player.onStoppedTracking(this.entity);
   }

   public void startTracking(ServerPlayerEntity player) {
      this.sendPackets(player.networkHandler::sendPacket);
      this.entity.onStartedTrackingBy(player);
      player.onStartedTracking(this.entity);
   }

   public void sendPackets(Consumer<Packet<?>> sender) {
      if (this.entity.removed) {
         LOGGER.warn("Fetching packet for removed entity " + this.entity);
      }

      Packet<?> _snowman = this.entity.createSpawnPacket();
      this.lastHeadPitch = MathHelper.floor(this.entity.getHeadYaw() * 256.0F / 360.0F);
      sender.accept(_snowman);
      if (!this.entity.getDataTracker().isEmpty()) {
         sender.accept(new EntityTrackerUpdateS2CPacket(this.entity.getEntityId(), this.entity.getDataTracker(), true));
      }

      boolean _snowmanx = this.alwaysUpdateVelocity;
      if (this.entity instanceof LivingEntity) {
         Collection<EntityAttributeInstance> _snowmanxx = ((LivingEntity)this.entity).getAttributes().getAttributesToSend();
         if (!_snowmanxx.isEmpty()) {
            sender.accept(new EntityAttributesS2CPacket(this.entity.getEntityId(), _snowmanxx));
         }

         if (((LivingEntity)this.entity).isFallFlying()) {
            _snowmanx = true;
         }
      }

      this.velocity = this.entity.getVelocity();
      if (_snowmanx && !(_snowman instanceof MobSpawnS2CPacket)) {
         sender.accept(new EntityVelocityUpdateS2CPacket(this.entity.getEntityId(), this.velocity));
      }

      if (this.entity instanceof LivingEntity) {
         List<Pair<EquipmentSlot, ItemStack>> _snowmanxxx = Lists.newArrayList();

         for (EquipmentSlot _snowmanxxxx : EquipmentSlot.values()) {
            ItemStack _snowmanxxxxx = ((LivingEntity)this.entity).getEquippedStack(_snowmanxxxx);
            if (!_snowmanxxxxx.isEmpty()) {
               _snowmanxxx.add(Pair.of(_snowmanxxxx, _snowmanxxxxx.copy()));
            }
         }

         if (!_snowmanxxx.isEmpty()) {
            sender.accept(new EntityEquipmentUpdateS2CPacket(this.entity.getEntityId(), _snowmanxxx));
         }
      }

      if (this.entity instanceof LivingEntity) {
         LivingEntity _snowmanxxx = (LivingEntity)this.entity;

         for (StatusEffectInstance _snowmanxxxxx : _snowmanxxx.getStatusEffects()) {
            sender.accept(new EntityStatusEffectS2CPacket(this.entity.getEntityId(), _snowmanxxxxx));
         }
      }

      if (!this.entity.getPassengerList().isEmpty()) {
         sender.accept(new EntityPassengersSetS2CPacket(this.entity));
      }

      if (this.entity.hasVehicle()) {
         sender.accept(new EntityPassengersSetS2CPacket(this.entity.getVehicle()));
      }

      if (this.entity instanceof MobEntity) {
         MobEntity _snowmanxxx = (MobEntity)this.entity;
         if (_snowmanxxx.isLeashed()) {
            sender.accept(new EntityAttachS2CPacket(_snowmanxxx, _snowmanxxx.getHoldingEntity()));
         }
      }
   }

   private void syncEntityData() {
      DataTracker _snowman = this.entity.getDataTracker();
      if (_snowman.isDirty()) {
         this.sendSyncPacket(new EntityTrackerUpdateS2CPacket(this.entity.getEntityId(), _snowman, false));
      }

      if (this.entity instanceof LivingEntity) {
         Set<EntityAttributeInstance> _snowmanx = ((LivingEntity)this.entity).getAttributes().getTracked();
         if (!_snowmanx.isEmpty()) {
            this.sendSyncPacket(new EntityAttributesS2CPacket(this.entity.getEntityId(), _snowmanx));
         }

         _snowmanx.clear();
      }
   }

   private void storeEncodedCoordinates() {
      this.lastX = EntityS2CPacket.encodePacketCoordinate(this.entity.getX());
      this.lastY = EntityS2CPacket.encodePacketCoordinate(this.entity.getY());
      this.lastZ = EntityS2CPacket.encodePacketCoordinate(this.entity.getZ());
   }

   public Vec3d getLastPos() {
      return EntityS2CPacket.decodePacketCoordinates(this.lastX, this.lastY, this.lastZ);
   }

   private void sendSyncPacket(Packet<?> packet) {
      this.receiver.accept(packet);
      if (this.entity instanceof ServerPlayerEntity) {
         ((ServerPlayerEntity)this.entity).networkHandler.sendPacket(packet);
      }
   }
}
