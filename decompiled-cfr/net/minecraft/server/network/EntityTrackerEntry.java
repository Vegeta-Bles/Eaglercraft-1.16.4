/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.collect.Lists
 *  com.mojang.datafixers.util.Pair
 *  org.apache.logging.log4j.LogManager
 *  org.apache.logging.log4j.Logger
 */
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
import net.minecraft.server.network.ServerPlayerEntity;
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
        this.lastYaw = MathHelper.floor(entity.yaw * 256.0f / 360.0f);
        this.lastPitch = MathHelper.floor(entity.pitch * 256.0f / 360.0f);
        this.lastHeadPitch = MathHelper.floor(entity.getHeadYaw() * 256.0f / 360.0f);
        this.lastOnGround = entity.isOnGround();
    }

    /*
     * WARNING - void declaration
     */
    public void tick() {
        List<Entity> list = this.entity.getPassengerList();
        if (!list.equals(this.lastPassengers)) {
            this.lastPassengers = list;
            this.receiver.accept(new EntityPassengersSetS2CPacket(this.entity));
        }
        if (this.entity instanceof ItemFrameEntity && this.trackingTick % 10 == 0) {
            ItemFrameEntity itemFrameEntity = (ItemFrameEntity)this.entity;
            ItemStack _snowman2 = itemFrameEntity.getHeldItemStack();
            if (_snowman2.getItem() instanceof FilledMapItem) {
                MapState _snowman3 = FilledMapItem.getOrCreateMapState(_snowman2, this.world);
                for (ServerPlayerEntity serverPlayerEntity : this.world.getPlayers()) {
                    _snowman3.update(serverPlayerEntity, _snowman2);
                    Packet<?> packet = ((FilledMapItem)_snowman2.getItem()).createSyncPacket(_snowman2, this.world, serverPlayerEntity);
                    if (packet == null) continue;
                    serverPlayerEntity.networkHandler.sendPacket(packet);
                }
            }
            this.syncEntityData();
        }
        if (this.trackingTick % this.tickInterval == 0 || this.entity.velocityDirty || this.entity.getDataTracker().isDirty()) {
            int n;
            if (this.entity.hasVehicle()) {
                int n2 = MathHelper.floor(this.entity.yaw * 256.0f / 360.0f);
                int n3 = MathHelper.floor(this.entity.pitch * 256.0f / 360.0f);
                boolean bl = _snowman = Math.abs(n2 - this.lastYaw) >= 1 || Math.abs(n3 - this.lastPitch) >= 1;
                if (_snowman) {
                    this.receiver.accept(new EntityS2CPacket.Rotate(this.entity.getEntityId(), (byte)n2, (byte)n3, this.entity.isOnGround()));
                    this.lastYaw = n2;
                    this.lastPitch = n3;
                }
                this.storeEncodedCoordinates();
                this.syncEntityData();
                this.hadVehicle = true;
            } else {
                void var6_17;
                Vec3d vec3d;
                double d;
                ++this.updatesWithoutVehicle;
                n = MathHelper.floor(this.entity.yaw * 256.0f / 360.0f);
                _snowman = MathHelper.floor(this.entity.pitch * 256.0f / 360.0f);
                Vec3d _snowman7 = this.entity.getPos().subtract(EntityS2CPacket.decodePacketCoordinates(this.lastX, this.lastY, this.lastZ));
                boolean _snowman4 = _snowman7.lengthSquared() >= 7.62939453125E-6;
                Object var6_12 = null;
                boolean _snowman6 = _snowman4 || this.trackingTick % 60 == 0;
                boolean bl = _snowman = Math.abs(n - this.lastYaw) >= 1 || Math.abs(_snowman - this.lastPitch) >= 1;
                if (this.trackingTick > 0 || this.entity instanceof PersistentProjectileEntity) {
                    long l = EntityS2CPacket.encodePacketCoordinate(_snowman7.x);
                    long l2 = EntityS2CPacket.encodePacketCoordinate(_snowman7.y);
                    _snowman = EntityS2CPacket.encodePacketCoordinate(_snowman7.z);
                    boolean bl2 = _snowman = l < -32768L || l > 32767L || l2 < -32768L || l2 > 32767L || _snowman < -32768L || _snowman > 32767L;
                    if (_snowman || this.updatesWithoutVehicle > 400 || this.hadVehicle || this.lastOnGround != this.entity.isOnGround()) {
                        this.lastOnGround = this.entity.isOnGround();
                        this.updatesWithoutVehicle = 0;
                        EntityPositionS2CPacket entityPositionS2CPacket = new EntityPositionS2CPacket(this.entity);
                    } else if (_snowman6 && _snowman || this.entity instanceof PersistentProjectileEntity) {
                        EntityS2CPacket.RotateAndMoveRelative rotateAndMoveRelative = new EntityS2CPacket.RotateAndMoveRelative(this.entity.getEntityId(), (short)l, (short)l2, (short)_snowman, (byte)n, (byte)_snowman, this.entity.isOnGround());
                    } else if (_snowman6) {
                        EntityS2CPacket.MoveRelative moveRelative = new EntityS2CPacket.MoveRelative(this.entity.getEntityId(), (short)l, (short)l2, (short)_snowman, this.entity.isOnGround());
                    } else if (_snowman) {
                        EntityS2CPacket.Rotate rotate = new EntityS2CPacket.Rotate(this.entity.getEntityId(), (byte)n, (byte)_snowman, this.entity.isOnGround());
                    }
                }
                if ((this.alwaysUpdateVelocity || this.entity.velocityDirty || this.entity instanceof LivingEntity && ((LivingEntity)this.entity).isFallFlying()) && this.trackingTick > 0 && ((d = (vec3d = this.entity.getVelocity()).squaredDistanceTo(this.velocity)) > 1.0E-7 || d > 0.0 && vec3d.lengthSquared() == 0.0)) {
                    this.velocity = vec3d;
                    this.receiver.accept(new EntityVelocityUpdateS2CPacket(this.entity.getEntityId(), this.velocity));
                }
                if (var6_17 != null) {
                    this.receiver.accept((Packet<?>)var6_17);
                }
                this.syncEntityData();
                if (_snowman6) {
                    this.storeEncodedCoordinates();
                }
                if (_snowman) {
                    this.lastYaw = n;
                    this.lastPitch = _snowman;
                }
                this.hadVehicle = false;
            }
            n = MathHelper.floor(this.entity.getHeadYaw() * 256.0f / 360.0f);
            if (Math.abs(n - this.lastHeadPitch) >= 1) {
                this.receiver.accept(new EntitySetHeadYawS2CPacket(this.entity, (byte)n));
                this.lastHeadPitch = n;
            }
            this.entity.velocityDirty = false;
        }
        ++this.trackingTick;
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
        Object object;
        if (this.entity.removed) {
            LOGGER.warn("Fetching packet for removed entity " + this.entity);
        }
        Packet<?> packet = this.entity.createSpawnPacket();
        this.lastHeadPitch = MathHelper.floor(this.entity.getHeadYaw() * 256.0f / 360.0f);
        sender.accept(packet);
        if (!this.entity.getDataTracker().isEmpty()) {
            sender.accept(new EntityTrackerUpdateS2CPacket(this.entity.getEntityId(), this.entity.getDataTracker(), true));
        }
        boolean _snowman2 = this.alwaysUpdateVelocity;
        if (this.entity instanceof LivingEntity) {
            object = ((LivingEntity)this.entity).getAttributes().getAttributesToSend();
            if (!object.isEmpty()) {
                sender.accept(new EntityAttributesS2CPacket(this.entity.getEntityId(), (Collection<EntityAttributeInstance>)object));
            }
            if (((LivingEntity)this.entity).isFallFlying()) {
                _snowman2 = true;
            }
        }
        this.velocity = this.entity.getVelocity();
        if (_snowman2 && !(packet instanceof MobSpawnS2CPacket)) {
            sender.accept(new EntityVelocityUpdateS2CPacket(this.entity.getEntityId(), this.velocity));
        }
        if (this.entity instanceof LivingEntity) {
            object = Lists.newArrayList();
            for (EquipmentSlot equipmentSlot : EquipmentSlot.values()) {
                ItemStack itemStack = ((LivingEntity)this.entity).getEquippedStack(equipmentSlot);
                if (itemStack.isEmpty()) continue;
                object.add(Pair.of((Object)((Object)equipmentSlot), (Object)itemStack.copy()));
            }
            if (!object.isEmpty()) {
                sender.accept(new EntityEquipmentUpdateS2CPacket(this.entity.getEntityId(), (List<Pair<EquipmentSlot, ItemStack>>)object));
            }
        }
        if (this.entity instanceof LivingEntity) {
            object = (LivingEntity)this.entity;
            for (StatusEffectInstance statusEffectInstance : ((LivingEntity)object).getStatusEffects()) {
                sender.accept(new EntityStatusEffectS2CPacket(this.entity.getEntityId(), statusEffectInstance));
            }
        }
        if (!this.entity.getPassengerList().isEmpty()) {
            sender.accept(new EntityPassengersSetS2CPacket(this.entity));
        }
        if (this.entity.hasVehicle()) {
            sender.accept(new EntityPassengersSetS2CPacket(this.entity.getVehicle()));
        }
        if (this.entity instanceof MobEntity && ((MobEntity)(object = (MobEntity)this.entity)).isLeashed()) {
            sender.accept(new EntityAttachS2CPacket((Entity)object, ((MobEntity)object).getHoldingEntity()));
        }
    }

    private void syncEntityData() {
        DataTracker dataTracker = this.entity.getDataTracker();
        if (dataTracker.isDirty()) {
            this.sendSyncPacket(new EntityTrackerUpdateS2CPacket(this.entity.getEntityId(), dataTracker, false));
        }
        if (this.entity instanceof LivingEntity) {
            Set<EntityAttributeInstance> set = ((LivingEntity)this.entity).getAttributes().getTracked();
            if (!set.isEmpty()) {
                this.sendSyncPacket(new EntityAttributesS2CPacket(this.entity.getEntityId(), set));
            }
            set.clear();
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

