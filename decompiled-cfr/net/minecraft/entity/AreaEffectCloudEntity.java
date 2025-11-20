/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.collect.Lists
 *  com.google.common.collect.Maps
 *  com.mojang.brigadier.StringReader
 *  com.mojang.brigadier.exceptions.CommandSyntaxException
 *  javax.annotation.Nullable
 *  org.apache.logging.log4j.LogManager
 *  org.apache.logging.log4j.Logger
 */
package net.minecraft.entity;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.mojang.brigadier.StringReader;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import javax.annotation.Nullable;
import net.minecraft.block.piston.PistonBehavior;
import net.minecraft.command.argument.ParticleArgumentType;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityDimensions;
import net.minecraft.entity.EntityPose;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.network.Packet;
import net.minecraft.network.packet.s2c.play.EntitySpawnS2CPacket;
import net.minecraft.particle.ParticleEffect;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionUtil;
import net.minecraft.potion.Potions;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.World;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class AreaEffectCloudEntity
extends Entity {
    private static final Logger LOGGER = LogManager.getLogger();
    private static final TrackedData<Float> RADIUS = DataTracker.registerData(AreaEffectCloudEntity.class, TrackedDataHandlerRegistry.FLOAT);
    private static final TrackedData<Integer> COLOR = DataTracker.registerData(AreaEffectCloudEntity.class, TrackedDataHandlerRegistry.INTEGER);
    private static final TrackedData<Boolean> WAITING = DataTracker.registerData(AreaEffectCloudEntity.class, TrackedDataHandlerRegistry.BOOLEAN);
    private static final TrackedData<ParticleEffect> PARTICLE_ID = DataTracker.registerData(AreaEffectCloudEntity.class, TrackedDataHandlerRegistry.PARTICLE);
    private Potion potion = Potions.EMPTY;
    private final List<StatusEffectInstance> effects = Lists.newArrayList();
    private final Map<Entity, Integer> affectedEntities = Maps.newHashMap();
    private int duration = 600;
    private int waitTime = 20;
    private int reapplicationDelay = 20;
    private boolean customColor;
    private int durationOnUse;
    private float radiusOnUse;
    private float radiusGrowth;
    private LivingEntity owner;
    private UUID ownerUuid;

    public AreaEffectCloudEntity(EntityType<? extends AreaEffectCloudEntity> entityType, World world) {
        super(entityType, world);
        this.noClip = true;
        this.setRadius(3.0f);
    }

    public AreaEffectCloudEntity(World world, double x, double y, double z) {
        this((EntityType<? extends AreaEffectCloudEntity>)EntityType.AREA_EFFECT_CLOUD, world);
        this.updatePosition(x, y, z);
    }

    @Override
    protected void initDataTracker() {
        this.getDataTracker().startTracking(COLOR, 0);
        this.getDataTracker().startTracking(RADIUS, Float.valueOf(0.5f));
        this.getDataTracker().startTracking(WAITING, false);
        this.getDataTracker().startTracking(PARTICLE_ID, ParticleTypes.ENTITY_EFFECT);
    }

    public void setRadius(float radius) {
        if (!this.world.isClient) {
            this.getDataTracker().set(RADIUS, Float.valueOf(radius));
        }
    }

    @Override
    public void calculateDimensions() {
        double d = this.getX();
        _snowman = this.getY();
        _snowman = this.getZ();
        super.calculateDimensions();
        this.updatePosition(d, _snowman, _snowman);
    }

    public float getRadius() {
        return this.getDataTracker().get(RADIUS).floatValue();
    }

    public void setPotion(Potion potion) {
        this.potion = potion;
        if (!this.customColor) {
            this.updateColor();
        }
    }

    private void updateColor() {
        if (this.potion == Potions.EMPTY && this.effects.isEmpty()) {
            this.getDataTracker().set(COLOR, 0);
        } else {
            this.getDataTracker().set(COLOR, PotionUtil.getColor(PotionUtil.getPotionEffects(this.potion, this.effects)));
        }
    }

    public void addEffect(StatusEffectInstance effect) {
        this.effects.add(effect);
        if (!this.customColor) {
            this.updateColor();
        }
    }

    public int getColor() {
        return this.getDataTracker().get(COLOR);
    }

    public void setColor(int rgb) {
        this.customColor = true;
        this.getDataTracker().set(COLOR, rgb);
    }

    public ParticleEffect getParticleType() {
        return this.getDataTracker().get(PARTICLE_ID);
    }

    public void setParticleType(ParticleEffect particle) {
        this.getDataTracker().set(PARTICLE_ID, particle);
    }

    protected void setWaiting(boolean waiting) {
        this.getDataTracker().set(WAITING, waiting);
    }

    public boolean isWaiting() {
        return this.getDataTracker().get(WAITING);
    }

    public int getDuration() {
        return this.duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    @Override
    public void tick() {
        block23: {
            boolean bl;
            float _snowman2;
            block21: {
                ParticleEffect _snowman3;
                block22: {
                    super.tick();
                    boolean bl2 = this.isWaiting();
                    _snowman2 = this.getRadius();
                    if (!this.world.isClient) break block21;
                    _snowman3 = this.getParticleType();
                    if (!bl2) break block22;
                    if (!this.random.nextBoolean()) break block23;
                    for (int i = 0; i < 2; ++i) {
                        float f;
                        float f2 = this.random.nextFloat() * ((float)Math.PI * 2);
                        _snowman = MathHelper.sqrt(this.random.nextFloat()) * 0.2f;
                        f = MathHelper.cos(f2) * _snowman;
                        _snowman = MathHelper.sin(f2) * _snowman;
                        if (_snowman3.getType() == ParticleTypes.ENTITY_EFFECT) {
                            int n = this.random.nextBoolean() ? 0xFFFFFF : this.getColor();
                            _snowman = n >> 16 & 0xFF;
                            _snowman = n >> 8 & 0xFF;
                            _snowman = n & 0xFF;
                            this.world.addImportantParticle(_snowman3, this.getX() + (double)f, this.getY(), this.getZ() + (double)_snowman, (float)_snowman / 255.0f, (float)_snowman / 255.0f, (float)_snowman / 255.0f);
                            continue;
                        }
                        this.world.addImportantParticle(_snowman3, this.getX() + (double)f, this.getY(), this.getZ() + (double)_snowman, 0.0, 0.0, 0.0);
                    }
                    break block23;
                }
                float f = (float)Math.PI * _snowman2 * _snowman2;
                int _snowman4 = 0;
                while ((float)_snowman4 < f) {
                    _snowman = this.random.nextFloat() * ((float)Math.PI * 2);
                    _snowman = MathHelper.sqrt(this.random.nextFloat()) * _snowman2;
                    f3 = MathHelper.cos(_snowman) * _snowman;
                    _snowman = MathHelper.sin(_snowman) * _snowman;
                    if (_snowman3.getType() == ParticleTypes.ENTITY_EFFECT) {
                        int n = this.getColor();
                        _snowman = n >> 16 & 0xFF;
                        _snowman = n >> 8 & 0xFF;
                        _snowman = n & 0xFF;
                        this.world.addImportantParticle(_snowman3, this.getX() + (double)f3, this.getY(), this.getZ() + (double)_snowman, (float)_snowman / 255.0f, (float)_snowman / 255.0f, (float)_snowman / 255.0f);
                    } else {
                        float f3;
                        this.world.addImportantParticle(_snowman3, this.getX() + (double)f3, this.getY(), this.getZ() + (double)_snowman, (0.5 - this.random.nextDouble()) * 0.15, 0.01f, (0.5 - this.random.nextDouble()) * 0.15);
                    }
                    ++_snowman4;
                }
                break block23;
            }
            if (this.age >= this.waitTime + this.duration) {
                this.remove();
                return;
            }
            boolean bl3 = bl = this.age < this.waitTime;
            if (bl2 != bl) {
                this.setWaiting(bl);
            }
            if (bl) {
                return;
            }
            if (this.radiusGrowth != 0.0f) {
                if ((_snowman2 += this.radiusGrowth) < 0.5f) {
                    this.remove();
                    return;
                }
                this.setRadius(_snowman2);
            }
            if (this.age % 5 == 0) {
                List<LivingEntity> list;
                Object object = this.affectedEntities.entrySet().iterator();
                while (object.hasNext()) {
                    list = object.next();
                    if (this.age < list.getValue()) continue;
                    object.remove();
                }
                object = Lists.newArrayList();
                for (StatusEffectInstance statusEffectInstance : this.potion.getEffects()) {
                    object.add(new StatusEffectInstance(statusEffectInstance.getEffectType(), statusEffectInstance.getDuration() / 4, statusEffectInstance.getAmplifier(), statusEffectInstance.isAmbient(), statusEffectInstance.shouldShowParticles()));
                }
                object.addAll(this.effects);
                if (object.isEmpty()) {
                    this.affectedEntities.clear();
                } else {
                    list = this.world.getNonSpectatingEntities(LivingEntity.class, this.getBoundingBox());
                    if (!list.isEmpty()) {
                        for (LivingEntity livingEntity : list) {
                            if (this.affectedEntities.containsKey(livingEntity) || !livingEntity.isAffectedBySplashPotions() || !((_snowman = (_snowman = livingEntity.getX() - this.getX()) * _snowman + (_snowman = livingEntity.getZ() - this.getZ()) * _snowman) <= (double)(_snowman2 * _snowman2))) continue;
                            this.affectedEntities.put(livingEntity, this.age + this.reapplicationDelay);
                            Iterator iterator = object.iterator();
                            while (iterator.hasNext()) {
                                StatusEffectInstance statusEffectInstance = (StatusEffectInstance)iterator.next();
                                if (statusEffectInstance.getEffectType().isInstant()) {
                                    statusEffectInstance.getEffectType().applyInstantEffect(this, this.getOwner(), livingEntity, statusEffectInstance.getAmplifier(), 0.5);
                                    continue;
                                }
                                livingEntity.addStatusEffect(new StatusEffectInstance(statusEffectInstance));
                            }
                            if (this.radiusOnUse != 0.0f) {
                                if ((_snowman2 += this.radiusOnUse) < 0.5f) {
                                    this.remove();
                                    return;
                                }
                                this.setRadius(_snowman2);
                            }
                            if (this.durationOnUse == 0) continue;
                            this.duration += this.durationOnUse;
                            if (this.duration > 0) continue;
                            this.remove();
                            return;
                        }
                    }
                }
            }
        }
    }

    public void setRadiusOnUse(float radius) {
        this.radiusOnUse = radius;
    }

    public void setRadiusGrowth(float growth) {
        this.radiusGrowth = growth;
    }

    public void setWaitTime(int ticks) {
        this.waitTime = ticks;
    }

    public void setOwner(@Nullable LivingEntity owner) {
        this.owner = owner;
        this.ownerUuid = owner == null ? null : owner.getUuid();
    }

    @Nullable
    public LivingEntity getOwner() {
        Entity entity;
        if (this.owner == null && this.ownerUuid != null && this.world instanceof ServerWorld && (entity = ((ServerWorld)this.world).getEntity(this.ownerUuid)) instanceof LivingEntity) {
            this.owner = (LivingEntity)entity;
        }
        return this.owner;
    }

    @Override
    protected void readCustomDataFromTag(CompoundTag tag) {
        this.age = tag.getInt("Age");
        this.duration = tag.getInt("Duration");
        this.waitTime = tag.getInt("WaitTime");
        this.reapplicationDelay = tag.getInt("ReapplicationDelay");
        this.durationOnUse = tag.getInt("DurationOnUse");
        this.radiusOnUse = tag.getFloat("RadiusOnUse");
        this.radiusGrowth = tag.getFloat("RadiusPerTick");
        this.setRadius(tag.getFloat("Radius"));
        if (tag.containsUuid("Owner")) {
            this.ownerUuid = tag.getUuid("Owner");
        }
        if (tag.contains("Particle", 8)) {
            try {
                this.setParticleType(ParticleArgumentType.readParameters(new StringReader(tag.getString("Particle"))));
            }
            catch (CommandSyntaxException commandSyntaxException) {
                LOGGER.warn("Couldn't load custom particle {}", (Object)tag.getString("Particle"), (Object)commandSyntaxException);
            }
        }
        if (tag.contains("Color", 99)) {
            this.setColor(tag.getInt("Color"));
        }
        if (tag.contains("Potion", 8)) {
            this.setPotion(PotionUtil.getPotion(tag));
        }
        if (tag.contains("Effects", 9)) {
            ListTag listTag = tag.getList("Effects", 10);
            this.effects.clear();
            for (int i = 0; i < listTag.size(); ++i) {
                StatusEffectInstance statusEffectInstance = StatusEffectInstance.fromTag(listTag.getCompound(i));
                if (statusEffectInstance == null) continue;
                this.addEffect(statusEffectInstance);
            }
        }
    }

    @Override
    protected void writeCustomDataToTag(CompoundTag tag) {
        tag.putInt("Age", this.age);
        tag.putInt("Duration", this.duration);
        tag.putInt("WaitTime", this.waitTime);
        tag.putInt("ReapplicationDelay", this.reapplicationDelay);
        tag.putInt("DurationOnUse", this.durationOnUse);
        tag.putFloat("RadiusOnUse", this.radiusOnUse);
        tag.putFloat("RadiusPerTick", this.radiusGrowth);
        tag.putFloat("Radius", this.getRadius());
        tag.putString("Particle", this.getParticleType().asString());
        if (this.ownerUuid != null) {
            tag.putUuid("Owner", this.ownerUuid);
        }
        if (this.customColor) {
            tag.putInt("Color", this.getColor());
        }
        if (this.potion != Potions.EMPTY && this.potion != null) {
            tag.putString("Potion", Registry.POTION.getId(this.potion).toString());
        }
        if (!this.effects.isEmpty()) {
            ListTag listTag = new ListTag();
            for (StatusEffectInstance statusEffectInstance : this.effects) {
                listTag.add(statusEffectInstance.toTag(new CompoundTag()));
            }
            tag.put("Effects", listTag);
        }
    }

    @Override
    public void onTrackedDataSet(TrackedData<?> data) {
        if (RADIUS.equals(data)) {
            this.calculateDimensions();
        }
        super.onTrackedDataSet(data);
    }

    @Override
    public PistonBehavior getPistonBehavior() {
        return PistonBehavior.IGNORE;
    }

    @Override
    public Packet<?> createSpawnPacket() {
        return new EntitySpawnS2CPacket(this);
    }

    @Override
    public EntityDimensions getDimensions(EntityPose pose) {
        return EntityDimensions.changing(this.getRadius() * 2.0f, 0.5f);
    }
}

