/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.collect.Lists
 *  javax.annotation.Nullable
 */
package net.minecraft.block.entity;

import com.google.common.collect.Lists;
import java.util.List;
import java.util.Random;
import java.util.UUID;
import javax.annotation.Nullable;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.mob.Monster;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.packet.s2c.play.BlockEntityUpdateS2CPacket;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.Tickable;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;

public class ConduitBlockEntity
extends BlockEntity
implements Tickable {
    private static final Block[] ACTIVATING_BLOCKS = new Block[]{Blocks.PRISMARINE, Blocks.PRISMARINE_BRICKS, Blocks.SEA_LANTERN, Blocks.DARK_PRISMARINE};
    public int ticks;
    private float ticksActive;
    private boolean active;
    private boolean eyeOpen;
    private final List<BlockPos> activatingBlocks = Lists.newArrayList();
    @Nullable
    private LivingEntity targetEntity;
    @Nullable
    private UUID targetUuid;
    private long nextAmbientSoundTime;

    public ConduitBlockEntity() {
        this(BlockEntityType.CONDUIT);
    }

    public ConduitBlockEntity(BlockEntityType<?> blockEntityType) {
        super(blockEntityType);
    }

    @Override
    public void fromTag(BlockState state, CompoundTag tag) {
        super.fromTag(state, tag);
        this.targetUuid = tag.containsUuid("Target") ? tag.getUuid("Target") : null;
    }

    @Override
    public CompoundTag toTag(CompoundTag tag) {
        super.toTag(tag);
        if (this.targetEntity != null) {
            tag.putUuid("Target", this.targetEntity.getUuid());
        }
        return tag;
    }

    @Override
    @Nullable
    public BlockEntityUpdateS2CPacket toUpdatePacket() {
        return new BlockEntityUpdateS2CPacket(this.pos, 5, this.toInitialChunkDataTag());
    }

    @Override
    public CompoundTag toInitialChunkDataTag() {
        return this.toTag(new CompoundTag());
    }

    @Override
    public void tick() {
        ++this.ticks;
        long l = this.world.getTime();
        if (l % 40L == 0L) {
            this.setActive(this.updateActivatingBlocks());
            if (!this.world.isClient && this.isActive()) {
                this.givePlayersEffects();
                this.attackHostileEntity();
            }
        }
        if (l % 80L == 0L && this.isActive()) {
            this.playSound(SoundEvents.BLOCK_CONDUIT_AMBIENT);
        }
        if (l > this.nextAmbientSoundTime && this.isActive()) {
            this.nextAmbientSoundTime = l + 60L + (long)this.world.getRandom().nextInt(40);
            this.playSound(SoundEvents.BLOCK_CONDUIT_AMBIENT_SHORT);
        }
        if (this.world.isClient) {
            this.updateTargetEntity();
            this.spawnNautilusParticles();
            if (this.isActive()) {
                this.ticksActive += 1.0f;
            }
        }
    }

    private boolean updateActivatingBlocks() {
        int n;
        this.activatingBlocks.clear();
        for (n = -1; n <= 1; ++n) {
            for (_snowman = -1; _snowman <= 1; ++_snowman) {
                for (_snowman = -1; _snowman <= 1; ++_snowman) {
                    BlockPos blockPos = this.pos.add(n, _snowman, _snowman);
                    if (this.world.isWater(blockPos)) continue;
                    return false;
                }
            }
        }
        for (n = -2; n <= 2; ++n) {
            for (_snowman = -2; _snowman <= 2; ++_snowman) {
                for (_snowman = -2; _snowman <= 2; ++_snowman) {
                    _snowman = Math.abs(n);
                    _snowman = Math.abs(_snowman);
                    _snowman = Math.abs(_snowman);
                    if (_snowman <= 1 && _snowman <= 1 && _snowman <= 1 || (n != 0 || _snowman != 2 && _snowman != 2) && (_snowman != 0 || _snowman != 2 && _snowman != 2) && (_snowman != 0 || _snowman != 2 && _snowman != 2)) continue;
                    BlockPos blockPos = this.pos.add(n, _snowman, _snowman);
                    BlockState _snowman2 = this.world.getBlockState(blockPos);
                    for (Block block : ACTIVATING_BLOCKS) {
                        if (!_snowman2.isOf(block)) continue;
                        this.activatingBlocks.add(blockPos);
                    }
                }
            }
        }
        this.setEyeOpen(this.activatingBlocks.size() >= 42);
        return this.activatingBlocks.size() >= 16;
    }

    private void givePlayersEffects() {
        int n = this.activatingBlocks.size();
        _snowman = n / 7 * 16;
        _snowman = this.pos.getX();
        Box _snowman2 = new Box(_snowman, _snowman = this.pos.getY(), _snowman = this.pos.getZ(), _snowman + 1, _snowman + 1, _snowman + 1).expand(_snowman).stretch(0.0, this.world.getHeight(), 0.0);
        List<PlayerEntity> _snowman3 = this.world.getNonSpectatingEntities(PlayerEntity.class, _snowman2);
        if (_snowman3.isEmpty()) {
            return;
        }
        for (PlayerEntity playerEntity : _snowman3) {
            if (!this.pos.isWithinDistance(playerEntity.getBlockPos(), (double)_snowman) || !playerEntity.isTouchingWaterOrRain()) continue;
            playerEntity.addStatusEffect(new StatusEffectInstance(StatusEffects.CONDUIT_POWER, 260, 0, true, true));
        }
    }

    private void attackHostileEntity() {
        List<LivingEntity> list;
        LivingEntity livingEntity2 = this.targetEntity;
        int _snowman2 = this.activatingBlocks.size();
        if (_snowman2 < 42) {
            this.targetEntity = null;
        } else if (this.targetEntity == null && this.targetUuid != null) {
            this.targetEntity = this.findTargetEntity();
            this.targetUuid = null;
        } else if (this.targetEntity == null) {
            list = this.world.getEntitiesByClass(LivingEntity.class, this.getAttackZone(), livingEntity -> livingEntity instanceof Monster && livingEntity.isTouchingWaterOrRain());
            if (!list.isEmpty()) {
                this.targetEntity = (LivingEntity)list.get(this.world.random.nextInt(list.size()));
            }
        } else if (!this.targetEntity.isAlive() || !this.pos.isWithinDistance(this.targetEntity.getBlockPos(), 8.0)) {
            this.targetEntity = null;
        }
        if (this.targetEntity != null) {
            this.world.playSound(null, this.targetEntity.getX(), this.targetEntity.getY(), this.targetEntity.getZ(), SoundEvents.BLOCK_CONDUIT_ATTACK_TARGET, SoundCategory.BLOCKS, 1.0f, 1.0f);
            this.targetEntity.damage(DamageSource.MAGIC, 4.0f);
        }
        if (livingEntity2 != this.targetEntity) {
            list = this.getCachedState();
            this.world.updateListeners(this.pos, (BlockState)((Object)list), (BlockState)((Object)list), 2);
        }
    }

    private void updateTargetEntity() {
        if (this.targetUuid == null) {
            this.targetEntity = null;
        } else if (this.targetEntity == null || !this.targetEntity.getUuid().equals(this.targetUuid)) {
            this.targetEntity = this.findTargetEntity();
            if (this.targetEntity == null) {
                this.targetUuid = null;
            }
        }
    }

    private Box getAttackZone() {
        int n = this.pos.getX();
        _snowman = this.pos.getY();
        _snowman = this.pos.getZ();
        return new Box(n, _snowman, _snowman, n + 1, _snowman + 1, _snowman + 1).expand(8.0);
    }

    @Nullable
    private LivingEntity findTargetEntity() {
        List<LivingEntity> list = this.world.getEntitiesByClass(LivingEntity.class, this.getAttackZone(), livingEntity -> livingEntity.getUuid().equals(this.targetUuid));
        if (list.size() == 1) {
            return list.get(0);
        }
        return null;
    }

    private void spawnNautilusParticles() {
        float _snowman7;
        Random random = this.world.random;
        double _snowman2 = MathHelper.sin((float)(this.ticks + 35) * 0.1f) / 2.0f + 0.5f;
        _snowman2 = (_snowman2 * _snowman2 + _snowman2) * (double)0.3f;
        Vec3d _snowman3 = new Vec3d((double)this.pos.getX() + 0.5, (double)this.pos.getY() + 1.5 + _snowman2, (double)this.pos.getZ() + 0.5);
        for (BlockPos blockPos : this.activatingBlocks) {
            if (random.nextInt(50) != 0) continue;
            _snowman7 = -0.5f + random.nextFloat();
            _snowman8 = -2.0f + random.nextFloat();
            _snowman = -0.5f + random.nextFloat();
            BlockPos _snowman4 = blockPos.subtract(this.pos);
            Vec3d _snowman5 = new Vec3d(_snowman7, _snowman8, _snowman).add(_snowman4.getX(), _snowman4.getY(), _snowman4.getZ());
            this.world.addParticle(ParticleTypes.NAUTILUS, _snowman3.x, _snowman3.y, _snowman3.z, _snowman5.x, _snowman5.y, _snowman5.z);
        }
        if (this.targetEntity != null) {
            Vec3d vec3d = new Vec3d(this.targetEntity.getX(), this.targetEntity.getEyeY(), this.targetEntity.getZ());
            float _snowman6 = (-0.5f + random.nextFloat()) * (3.0f + this.targetEntity.getWidth());
            _snowman7 = -1.0f + random.nextFloat() * this.targetEntity.getHeight();
            float _snowman8 = (-0.5f + random.nextFloat()) * (3.0f + this.targetEntity.getWidth());
            _snowman = new Vec3d(_snowman6, _snowman7, _snowman8);
            this.world.addParticle(ParticleTypes.NAUTILUS, vec3d.x, vec3d.y, vec3d.z, _snowman.x, _snowman.y, _snowman.z);
        }
    }

    public boolean isActive() {
        return this.active;
    }

    public boolean isEyeOpen() {
        return this.eyeOpen;
    }

    private void setActive(boolean active) {
        if (active != this.active) {
            this.playSound(active ? SoundEvents.BLOCK_CONDUIT_ACTIVATE : SoundEvents.BLOCK_CONDUIT_DEACTIVATE);
        }
        this.active = active;
    }

    private void setEyeOpen(boolean eyeOpen) {
        this.eyeOpen = eyeOpen;
    }

    public float getRotation(float tickDelta) {
        return (this.ticksActive + tickDelta) * -0.0375f;
    }

    public void playSound(SoundEvent soundEvent) {
        this.world.playSound(null, this.pos, soundEvent, SoundCategory.BLOCKS, 1.0f, 1.0f);
    }
}

