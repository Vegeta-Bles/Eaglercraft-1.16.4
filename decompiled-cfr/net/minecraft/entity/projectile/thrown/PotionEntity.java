/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.annotation.Nullable
 */
package net.minecraft.entity.projectile.thrown;

import java.util.List;
import java.util.function.Predicate;
import javax.annotation.Nullable;
import net.minecraft.block.BlockState;
import net.minecraft.block.CampfireBlock;
import net.minecraft.entity.AreaEffectCloudEntity;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.FlyingItemEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.projectile.thrown.ThrownItemEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionUtil;
import net.minecraft.potion.Potions;
import net.minecraft.tag.BlockTags;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.hit.EntityHitResult;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.Direction;
import net.minecraft.world.World;

public class PotionEntity
extends ThrownItemEntity
implements FlyingItemEntity {
    public static final Predicate<LivingEntity> WATER_HURTS = LivingEntity::hurtByWater;

    public PotionEntity(EntityType<? extends PotionEntity> entityType, World world) {
        super((EntityType<? extends ThrownItemEntity>)entityType, world);
    }

    public PotionEntity(World world, LivingEntity owner) {
        super((EntityType<? extends ThrownItemEntity>)EntityType.POTION, owner, world);
    }

    public PotionEntity(World world, double x, double y, double z) {
        super((EntityType<? extends ThrownItemEntity>)EntityType.POTION, x, y, z, world);
    }

    @Override
    protected Item getDefaultItem() {
        return Items.SPLASH_POTION;
    }

    @Override
    protected float getGravity() {
        return 0.05f;
    }

    @Override
    protected void onBlockHit(BlockHitResult blockHitResult) {
        super.onBlockHit(blockHitResult);
        if (this.world.isClient) {
            return;
        }
        ItemStack itemStack = this.getStack();
        Potion _snowman2 = PotionUtil.getPotion(itemStack);
        List<StatusEffectInstance> _snowman3 = PotionUtil.getPotionEffects(itemStack);
        boolean _snowman4 = _snowman2 == Potions.WATER && _snowman3.isEmpty();
        Direction _snowman5 = blockHitResult.getSide();
        BlockPos _snowman6 = blockHitResult.getBlockPos();
        BlockPos _snowman7 = _snowman6.offset(_snowman5);
        if (_snowman4) {
            this.extinguishFire(_snowman7, _snowman5);
            this.extinguishFire(_snowman7.offset(_snowman5.getOpposite()), _snowman5);
            for (Direction direction : Direction.Type.HORIZONTAL) {
                this.extinguishFire(_snowman7.offset(direction), direction);
            }
        }
    }

    @Override
    protected void onCollision(HitResult hitResult) {
        super.onCollision(hitResult);
        if (this.world.isClient) {
            return;
        }
        ItemStack itemStack = this.getStack();
        Potion _snowman2 = PotionUtil.getPotion(itemStack);
        List<StatusEffectInstance> _snowman3 = PotionUtil.getPotionEffects(itemStack);
        boolean bl = _snowman = _snowman2 == Potions.WATER && _snowman3.isEmpty();
        if (_snowman) {
            this.damageEntitiesHurtByWater();
        } else if (!_snowman3.isEmpty()) {
            if (this.isLingering()) {
                this.applyLingeringPotion(itemStack, _snowman2);
            } else {
                this.applySplashPotion(_snowman3, hitResult.getType() == HitResult.Type.ENTITY ? ((EntityHitResult)hitResult).getEntity() : null);
            }
        }
        int _snowman4 = _snowman2.hasInstantEffect() ? 2007 : 2002;
        this.world.syncWorldEvent(_snowman4, this.getBlockPos(), PotionUtil.getColor(itemStack));
        this.remove();
    }

    private void damageEntitiesHurtByWater() {
        Box box = this.getBoundingBox().expand(4.0, 2.0, 4.0);
        List<LivingEntity> _snowman2 = this.world.getEntitiesByClass(LivingEntity.class, box, WATER_HURTS);
        if (!_snowman2.isEmpty()) {
            for (LivingEntity livingEntity : _snowman2) {
                double d = this.squaredDistanceTo(livingEntity);
                if (!(d < 16.0) || !livingEntity.hurtByWater()) continue;
                livingEntity.damage(DamageSource.magic(livingEntity, this.getOwner()), 1.0f);
            }
        }
    }

    private void applySplashPotion(List<StatusEffectInstance> statusEffects, @Nullable Entity entity) {
        Box box = this.getBoundingBox().expand(4.0, 2.0, 4.0);
        List<LivingEntity> _snowman2 = this.world.getNonSpectatingEntities(LivingEntity.class, box);
        if (!_snowman2.isEmpty()) {
            for (LivingEntity livingEntity : _snowman2) {
                if (!livingEntity.isAffectedBySplashPotions() || !((_snowman = this.squaredDistanceTo(livingEntity)) < 16.0)) continue;
                double d = 1.0 - Math.sqrt(_snowman) / 4.0;
                if (livingEntity == entity) {
                    d = 1.0;
                }
                for (StatusEffectInstance statusEffectInstance : statusEffects) {
                    StatusEffect statusEffect = statusEffectInstance.getEffectType();
                    if (statusEffect.isInstant()) {
                        statusEffect.applyInstantEffect(this, this.getOwner(), livingEntity, statusEffectInstance.getAmplifier(), d);
                        continue;
                    }
                    int _snowman3 = (int)(d * (double)statusEffectInstance.getDuration() + 0.5);
                    if (_snowman3 <= 20) continue;
                    livingEntity.addStatusEffect(new StatusEffectInstance(statusEffect, _snowman3, statusEffectInstance.getAmplifier(), statusEffectInstance.isAmbient(), statusEffectInstance.shouldShowParticles()));
                }
            }
        }
    }

    private void applyLingeringPotion(ItemStack stack, Potion potion) {
        AreaEffectCloudEntity areaEffectCloudEntity = new AreaEffectCloudEntity(this.world, this.getX(), this.getY(), this.getZ());
        Entity _snowman2 = this.getOwner();
        if (_snowman2 instanceof LivingEntity) {
            areaEffectCloudEntity.setOwner((LivingEntity)_snowman2);
        }
        areaEffectCloudEntity.setRadius(3.0f);
        areaEffectCloudEntity.setRadiusOnUse(-0.5f);
        areaEffectCloudEntity.setWaitTime(10);
        areaEffectCloudEntity.setRadiusGrowth(-areaEffectCloudEntity.getRadius() / (float)areaEffectCloudEntity.getDuration());
        areaEffectCloudEntity.setPotion(potion);
        for (StatusEffectInstance statusEffectInstance : PotionUtil.getCustomPotionEffects(stack)) {
            areaEffectCloudEntity.addEffect(new StatusEffectInstance(statusEffectInstance));
        }
        CompoundTag compoundTag = stack.getTag();
        if (compoundTag != null && compoundTag.contains("CustomPotionColor", 99)) {
            areaEffectCloudEntity.setColor(compoundTag.getInt("CustomPotionColor"));
        }
        this.world.spawnEntity(areaEffectCloudEntity);
    }

    private boolean isLingering() {
        return this.getStack().getItem() == Items.LINGERING_POTION;
    }

    private void extinguishFire(BlockPos pos, Direction direction) {
        BlockState blockState = this.world.getBlockState(pos);
        if (blockState.isIn(BlockTags.FIRE)) {
            this.world.removeBlock(pos, false);
        } else if (CampfireBlock.isLitCampfire(blockState)) {
            this.world.syncWorldEvent(null, 1009, pos, 0);
            CampfireBlock.extinguish(this.world, pos, blockState);
            this.world.setBlockState(pos, (BlockState)blockState.with(CampfireBlock.LIT, false));
        }
    }
}

