/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.collect.Lists
 *  javax.annotation.Nullable
 */
package net.minecraft.entity.damage;

import com.google.common.collect.Lists;
import java.util.List;
import java.util.Optional;
import javax.annotation.Nullable;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageRecord;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.tag.BlockTags;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.math.BlockPos;

public class DamageTracker {
    private final List<DamageRecord> recentDamage = Lists.newArrayList();
    private final LivingEntity entity;
    private int ageOnLastDamage;
    private int ageOnLastAttacked;
    private int ageOnLastUpdate;
    private boolean recentlyAttacked;
    private boolean hasDamage;
    private String fallDeathSuffix;

    public DamageTracker(LivingEntity entity) {
        this.entity = entity;
    }

    public void setFallDeathSuffix() {
        this.clearFallDeathSuffix();
        Optional<BlockPos> optional = this.entity.getClimbingPos();
        if (optional.isPresent()) {
            BlockState blockState = this.entity.world.getBlockState(optional.get());
            this.fallDeathSuffix = blockState.isOf(Blocks.LADDER) || blockState.isIn(BlockTags.TRAPDOORS) ? "ladder" : (blockState.isOf(Blocks.VINE) ? "vines" : (blockState.isOf(Blocks.WEEPING_VINES) || blockState.isOf(Blocks.WEEPING_VINES_PLANT) ? "weeping_vines" : (blockState.isOf(Blocks.TWISTING_VINES) || blockState.isOf(Blocks.TWISTING_VINES_PLANT) ? "twisting_vines" : (blockState.isOf(Blocks.SCAFFOLDING) ? "scaffolding" : "other_climbable"))));
        } else if (this.entity.isTouchingWater()) {
            this.fallDeathSuffix = "water";
        }
    }

    public void onDamage(DamageSource damageSource, float originalHealth, float f) {
        this.update();
        this.setFallDeathSuffix();
        DamageRecord damageRecord = new DamageRecord(damageSource, this.entity.age, originalHealth, f, this.fallDeathSuffix, this.entity.fallDistance);
        this.recentDamage.add(damageRecord);
        this.ageOnLastDamage = this.entity.age;
        this.hasDamage = true;
        if (damageRecord.isAttackerLiving() && !this.recentlyAttacked && this.entity.isAlive()) {
            this.recentlyAttacked = true;
            this.ageOnLastUpdate = this.ageOnLastAttacked = this.entity.age;
            this.entity.enterCombat();
        }
    }

    public Text getDeathMessage() {
        Text _snowman4;
        if (this.recentDamage.isEmpty()) {
            return new TranslatableText("death.attack.generic", this.entity.getDisplayName());
        }
        DamageRecord damageRecord = this.getBiggestFall();
        _snowman = this.recentDamage.get(this.recentDamage.size() - 1);
        Text _snowman2 = _snowman.getAttackerName();
        Entity _snowman3 = _snowman.getDamageSource().getAttacker();
        if (damageRecord != null && _snowman.getDamageSource() == DamageSource.FALL) {
            Text text = damageRecord.getAttackerName();
            if (damageRecord.getDamageSource() == DamageSource.FALL || damageRecord.getDamageSource() == DamageSource.OUT_OF_WORLD) {
                _snowman4 = new TranslatableText("death.fell.accident." + this.getFallDeathSuffix(damageRecord), this.entity.getDisplayName());
            } else if (!(text == null || _snowman2 != null && text.equals(_snowman2))) {
                Entity entity = damageRecord.getDamageSource().getAttacker();
                ItemStack itemStack = _snowman = entity instanceof LivingEntity ? ((LivingEntity)entity).getMainHandStack() : ItemStack.EMPTY;
                _snowman4 = !_snowman.isEmpty() && _snowman.hasCustomName() ? new TranslatableText("death.fell.assist.item", this.entity.getDisplayName(), text, _snowman.toHoverableText()) : new TranslatableText("death.fell.assist", this.entity.getDisplayName(), text);
            } else if (_snowman2 != null) {
                ItemStack itemStack = _snowman = _snowman3 instanceof LivingEntity ? ((LivingEntity)_snowman3).getMainHandStack() : ItemStack.EMPTY;
                _snowman4 = !_snowman.isEmpty() && _snowman.hasCustomName() ? new TranslatableText("death.fell.finish.item", this.entity.getDisplayName(), _snowman2, _snowman.toHoverableText()) : new TranslatableText("death.fell.finish", this.entity.getDisplayName(), _snowman2);
            } else {
                _snowman4 = new TranslatableText("death.fell.killer", this.entity.getDisplayName());
            }
        } else {
            _snowman4 = _snowman.getDamageSource().getDeathMessage(this.entity);
        }
        return _snowman4;
    }

    @Nullable
    public LivingEntity getBiggestAttacker() {
        LivingEntity livingEntity = null;
        PlayerEntity _snowman2 = null;
        float _snowman3 = 0.0f;
        float _snowman4 = 0.0f;
        for (DamageRecord damageRecord : this.recentDamage) {
            if (damageRecord.getDamageSource().getAttacker() instanceof PlayerEntity && (_snowman2 == null || damageRecord.getDamage() > _snowman4)) {
                _snowman4 = damageRecord.getDamage();
                _snowman2 = (PlayerEntity)damageRecord.getDamageSource().getAttacker();
            }
            if (!(damageRecord.getDamageSource().getAttacker() instanceof LivingEntity) || livingEntity != null && !(damageRecord.getDamage() > _snowman3)) continue;
            _snowman3 = damageRecord.getDamage();
            livingEntity = (LivingEntity)damageRecord.getDamageSource().getAttacker();
        }
        if (_snowman2 != null && _snowman4 >= _snowman3 / 3.0f) {
            return _snowman2;
        }
        return livingEntity;
    }

    @Nullable
    private DamageRecord getBiggestFall() {
        DamageRecord damageRecord = null;
        _snowman = null;
        float _snowman2 = 0.0f;
        float _snowman3 = 0.0f;
        for (int i = 0; i < this.recentDamage.size(); ++i) {
            DamageRecord damageRecord2 = this.recentDamage.get(i);
            DamageRecord damageRecord3 = _snowman = i > 0 ? this.recentDamage.get(i - 1) : null;
            if ((damageRecord2.getDamageSource() == DamageSource.FALL || damageRecord2.getDamageSource() == DamageSource.OUT_OF_WORLD) && damageRecord2.getFallDistance() > 0.0f && (damageRecord == null || damageRecord2.getFallDistance() > _snowman3)) {
                damageRecord = i > 0 ? _snowman : damageRecord2;
                _snowman3 = damageRecord2.getFallDistance();
            }
            if (damageRecord2.getFallDeathSuffix() == null || _snowman != null && !(damageRecord2.getDamage() > _snowman2)) continue;
            _snowman = damageRecord2;
            _snowman2 = damageRecord2.getDamage();
        }
        if (_snowman3 > 5.0f && damageRecord != null) {
            return damageRecord;
        }
        if (_snowman2 > 5.0f && _snowman != null) {
            return _snowman;
        }
        return null;
    }

    private String getFallDeathSuffix(DamageRecord damageRecord) {
        return damageRecord.getFallDeathSuffix() == null ? "generic" : damageRecord.getFallDeathSuffix();
    }

    public int getTimeSinceLastAttack() {
        if (this.recentlyAttacked) {
            return this.entity.age - this.ageOnLastAttacked;
        }
        return this.ageOnLastUpdate - this.ageOnLastAttacked;
    }

    private void clearFallDeathSuffix() {
        this.fallDeathSuffix = null;
    }

    public void update() {
        int n;
        int n2 = n = this.recentlyAttacked ? 300 : 100;
        if (this.hasDamage && (!this.entity.isAlive() || this.entity.age - this.ageOnLastDamage > n)) {
            boolean bl = this.recentlyAttacked;
            this.hasDamage = false;
            this.recentlyAttacked = false;
            this.ageOnLastUpdate = this.entity.age;
            if (bl) {
                this.entity.endCombat();
            }
            this.recentDamage.clear();
        }
    }

    public LivingEntity getEntity() {
        return this.entity;
    }
}

