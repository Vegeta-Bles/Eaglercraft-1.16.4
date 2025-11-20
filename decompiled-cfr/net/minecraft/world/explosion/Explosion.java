/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.collect.Lists
 *  com.google.common.collect.Maps
 *  com.google.common.collect.Sets
 *  com.mojang.datafixers.util.Pair
 *  it.unimi.dsi.fastutil.objects.ObjectArrayList
 *  javax.annotation.Nullable
 */
package net.minecraft.world.explosion;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import com.mojang.datafixers.util.Pair;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Random;
import javax.annotation.Nullable;
import net.minecraft.block.AbstractFireBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.enchantment.ProtectionEnchantment;
import net.minecraft.entity.Entity;
import net.minecraft.entity.ItemEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.TntEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.ProjectileEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.loot.context.LootContext;
import net.minecraft.loot.context.LootContextParameters;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.RaycastContext;
import net.minecraft.world.World;
import net.minecraft.world.explosion.EntityExplosionBehavior;
import net.minecraft.world.explosion.ExplosionBehavior;

public class Explosion {
    private static final ExplosionBehavior field_25818 = new ExplosionBehavior();
    private final boolean createFire;
    private final DestructionType destructionType;
    private final Random random = new Random();
    private final World world;
    private final double x;
    private final double y;
    private final double z;
    @Nullable
    private final Entity entity;
    private final float power;
    private final DamageSource damageSource;
    private final ExplosionBehavior behavior;
    private final List<BlockPos> affectedBlocks = Lists.newArrayList();
    private final Map<PlayerEntity, Vec3d> affectedPlayers = Maps.newHashMap();

    public Explosion(World world, @Nullable Entity entity, double x, double y, double z, float power, List<BlockPos> affectedBlocks) {
        this(world, entity, x, y, z, power, false, DestructionType.DESTROY, affectedBlocks);
    }

    public Explosion(World world, @Nullable Entity entity, double x, double y, double z, float power, boolean createFire, DestructionType destructionType, List<BlockPos> affectedBlocks) {
        this(world, entity, x, y, z, power, createFire, destructionType);
        this.affectedBlocks.addAll(affectedBlocks);
    }

    public Explosion(World world, @Nullable Entity entity, double d, double d2, double d3, float f, boolean bl, DestructionType destructionType) {
        this(world, entity, null, null, d, d2, d3, f, bl, destructionType);
    }

    public Explosion(World world, @Nullable Entity entity, @Nullable DamageSource damageSource, @Nullable ExplosionBehavior explosionBehavior, double d, double d2, double d3, float f, boolean bl, DestructionType destructionType) {
        this.world = world;
        this.entity = entity;
        this.power = f;
        this.x = d;
        this.y = d2;
        this.z = d3;
        this.createFire = bl;
        this.destructionType = destructionType;
        this.damageSource = damageSource == null ? DamageSource.explosion(this) : damageSource;
        this.behavior = explosionBehavior == null ? this.chooseBehavior(entity) : explosionBehavior;
    }

    private ExplosionBehavior chooseBehavior(@Nullable Entity entity) {
        return entity == null ? field_25818 : new EntityExplosionBehavior(entity);
    }

    public static float getExposure(Vec3d source, Entity entity) {
        Box box = entity.getBoundingBox();
        double _snowman2 = 1.0 / ((box.maxX - box.minX) * 2.0 + 1.0);
        double _snowman3 = 1.0 / ((box.maxY - box.minY) * 2.0 + 1.0);
        double _snowman4 = 1.0 / ((box.maxZ - box.minZ) * 2.0 + 1.0);
        double _snowman5 = (1.0 - Math.floor(1.0 / _snowman2) * _snowman2) / 2.0;
        double _snowman6 = (1.0 - Math.floor(1.0 / _snowman4) * _snowman4) / 2.0;
        if (_snowman2 < 0.0 || _snowman3 < 0.0 || _snowman4 < 0.0) {
            return 0.0f;
        }
        int _snowman7 = 0;
        int _snowman8 = 0;
        float _snowman9 = 0.0f;
        while (_snowman9 <= 1.0f) {
            float f = 0.0f;
            while (f <= 1.0f) {
                _snowman11 = 0.0f;
                while (_snowman11 <= 1.0f) {
                    double d = MathHelper.lerp((double)_snowman9, box.minX, box.maxX);
                    Vec3d _snowman10 = new Vec3d(d + _snowman5, _snowman = MathHelper.lerp((double)f, box.minY, box.maxY), (_snowman = MathHelper.lerp((double)_snowman11, box.minZ, box.maxZ)) + _snowman6);
                    if (entity.world.raycast(new RaycastContext(_snowman10, source, RaycastContext.ShapeType.COLLIDER, RaycastContext.FluidHandling.NONE, entity)).getType() == HitResult.Type.MISS) {
                        ++_snowman7;
                    }
                    ++_snowman8;
                    float _snowman11 = (float)((double)_snowman11 + _snowman4);
                }
                f = (float)((double)f + _snowman3);
            }
            _snowman9 = (float)((double)_snowman9 + _snowman2);
        }
        return (float)_snowman7 / (float)_snowman8;
    }

    public void collectBlocksAndDamageEntities() {
        HashSet hashSet = Sets.newHashSet();
        int _snowman2 = 16;
        for (int i = 0; i < 16; ++i) {
            for (_snowman6 = 0; _snowman6 < 16; ++_snowman6) {
                for (_snowman7 = 0; _snowman7 < 16; ++_snowman7) {
                    if (i != 0 && i != 15 && _snowman6 != 0 && _snowman6 != 15 && _snowman7 != 0 && _snowman7 != 15) continue;
                    double d = (float)i / 15.0f * 2.0f - 1.0f;
                    _snowman = (float)_snowman6 / 15.0f * 2.0f - 1.0f;
                    _snowman = (float)_snowman7 / 15.0f * 2.0f - 1.0f;
                    _snowman = Math.sqrt(d * d + _snowman * _snowman + _snowman * _snowman);
                    d /= _snowman;
                    _snowman /= _snowman;
                    _snowman /= _snowman;
                    _snowman = this.x;
                    _snowman = this.y;
                    _snowman = this.z;
                    float _snowman3 = 0.3f;
                    for (float f = this.power * (0.7f + this.world.random.nextFloat() * 0.6f); f > 0.0f; f -= 0.22500001f) {
                        BlockPos blockPos = new BlockPos(_snowman, _snowman, _snowman);
                        BlockState _snowman4 = this.world.getBlockState(blockPos);
                        Optional<Float> _snowman5 = this.behavior.getBlastResistance(this, this.world, blockPos, _snowman4, _snowman = this.world.getFluidState(blockPos));
                        if (_snowman5.isPresent()) {
                            f -= (_snowman5.get().floatValue() + 0.3f) * 0.3f;
                        }
                        if (f > 0.0f && this.behavior.canDestroyBlock(this, this.world, blockPos, _snowman4, f)) {
                            hashSet.add(blockPos);
                        }
                        _snowman += d * (double)0.3f;
                        _snowman += _snowman * (double)0.3f;
                        _snowman += _snowman * (double)0.3f;
                    }
                }
            }
        }
        this.affectedBlocks.addAll(hashSet);
        float f = this.power * 2.0f;
        int _snowman6 = MathHelper.floor(this.x - (double)f - 1.0);
        int _snowman7 = MathHelper.floor(this.x + (double)f + 1.0);
        int _snowman8 = MathHelper.floor(this.y - (double)f - 1.0);
        int _snowman9 = MathHelper.floor(this.y + (double)f + 1.0);
        int _snowman10 = MathHelper.floor(this.z - (double)f - 1.0);
        int _snowman11 = MathHelper.floor(this.z + (double)f + 1.0);
        List<Entity> _snowman12 = this.world.getOtherEntities(this.entity, new Box(_snowman6, _snowman8, _snowman10, _snowman7, _snowman9, _snowman11));
        Vec3d _snowman13 = new Vec3d(this.x, this.y, this.z);
        for (int i = 0; i < _snowman12.size(); ++i) {
            Entity entity = _snowman12.get(i);
            if (entity.isImmuneToExplosion() || !((_snowman = (double)(MathHelper.sqrt(entity.squaredDistanceTo(_snowman13)) / f)) <= 1.0) || (_snowman = (double)MathHelper.sqrt((_snowman = entity.getX() - this.x) * _snowman + (_snowman = (entity instanceof TntEntity ? entity.getY() : entity.getEyeY()) - this.y) * _snowman + (_snowman = entity.getZ() - this.z) * _snowman)) == 0.0) continue;
            _snowman /= _snowman;
            _snowman /= _snowman;
            _snowman /= _snowman;
            double _snowman14 = Explosion.getExposure(_snowman13, entity);
            double _snowman15 = (1.0 - _snowman) * _snowman14;
            entity.damage(this.getDamageSource(), (int)((_snowman15 * _snowman15 + _snowman15) / 2.0 * 7.0 * (double)f + 1.0));
            double _snowman16 = _snowman15;
            if (entity instanceof LivingEntity) {
                _snowman16 = ProtectionEnchantment.transformExplosionKnockback((LivingEntity)entity, _snowman15);
            }
            entity.setVelocity(entity.getVelocity().add(_snowman * _snowman16, _snowman * _snowman16, _snowman * _snowman16));
            if (!(entity instanceof PlayerEntity) || (_snowman = (PlayerEntity)entity).isSpectator() || _snowman.isCreative() && _snowman.abilities.flying) continue;
            this.affectedPlayers.put(_snowman, new Vec3d(_snowman * _snowman15, _snowman * _snowman15, _snowman * _snowman15));
        }
    }

    public void affectWorld(boolean bl) {
        if (this.world.isClient) {
            this.world.playSound(this.x, this.y, this.z, SoundEvents.ENTITY_GENERIC_EXPLODE, SoundCategory.BLOCKS, 4.0f, (1.0f + (this.world.random.nextFloat() - this.world.random.nextFloat()) * 0.2f) * 0.7f, false);
        }
        boolean bl2 = _snowman = this.destructionType != DestructionType.NONE;
        if (bl) {
            if (this.power < 2.0f || !_snowman) {
                this.world.addParticle(ParticleTypes.EXPLOSION, this.x, this.y, this.z, 1.0, 0.0, 0.0);
            } else {
                this.world.addParticle(ParticleTypes.EXPLOSION_EMITTER, this.x, this.y, this.z, 1.0, 0.0, 0.0);
            }
        }
        if (_snowman) {
            ObjectArrayList objectArrayList = new ObjectArrayList();
            Collections.shuffle(this.affectedBlocks, this.world.random);
            for (BlockPos blockPos : this.affectedBlocks) {
                BlockState blockState = this.world.getBlockState(blockPos);
                Block _snowman2 = blockState.getBlock();
                if (blockState.isAir()) continue;
                BlockPos _snowman3 = blockPos.toImmutable();
                this.world.getProfiler().push("explosion_blocks");
                if (_snowman2.shouldDropItemsOnExplosion(this) && this.world instanceof ServerWorld) {
                    BlockEntity blockEntity = _snowman2.hasBlockEntity() ? this.world.getBlockEntity(blockPos) : null;
                    LootContext.Builder _snowman4 = new LootContext.Builder((ServerWorld)this.world).random(this.world.random).parameter(LootContextParameters.ORIGIN, Vec3d.ofCenter(blockPos)).parameter(LootContextParameters.TOOL, ItemStack.EMPTY).optionalParameter(LootContextParameters.BLOCK_ENTITY, blockEntity).optionalParameter(LootContextParameters.THIS_ENTITY, this.entity);
                    if (this.destructionType == DestructionType.DESTROY) {
                        _snowman4.parameter(LootContextParameters.EXPLOSION_RADIUS, Float.valueOf(this.power));
                    }
                    blockState.getDroppedStacks(_snowman4).forEach(itemStack -> Explosion.method_24023((ObjectArrayList<Pair<ItemStack, BlockPos>>)objectArrayList, itemStack, _snowman3));
                }
                this.world.setBlockState(blockPos, Blocks.AIR.getDefaultState(), 3);
                _snowman2.onDestroyedByExplosion(this.world, blockPos, this);
                this.world.getProfiler().pop();
            }
            for (BlockPos blockPos : objectArrayList) {
                Block.dropStack(this.world, (BlockPos)blockPos.getSecond(), (ItemStack)blockPos.getFirst());
            }
        }
        if (this.createFire) {
            for (BlockPos blockPos : this.affectedBlocks) {
                if (this.random.nextInt(3) != 0 || !this.world.getBlockState(blockPos).isAir() || !this.world.getBlockState(blockPos.down()).isOpaqueFullCube(this.world, blockPos.down())) continue;
                this.world.setBlockState(blockPos, AbstractFireBlock.getState(this.world, blockPos));
            }
        }
    }

    private static void method_24023(ObjectArrayList<Pair<ItemStack, BlockPos>> objectArrayList, ItemStack itemStack, BlockPos blockPos) {
        int n = objectArrayList.size();
        for (_snowman = 0; _snowman < n; ++_snowman) {
            Pair pair = (Pair)objectArrayList.get(_snowman);
            ItemStack _snowman2 = (ItemStack)pair.getFirst();
            if (!ItemEntity.canMerge(_snowman2, itemStack)) continue;
            ItemStack _snowman3 = ItemEntity.merge(_snowman2, itemStack, 16);
            objectArrayList.set(_snowman, (Object)Pair.of((Object)_snowman3, (Object)pair.getSecond()));
            if (!itemStack.isEmpty()) continue;
            return;
        }
        objectArrayList.add((Object)Pair.of((Object)itemStack, (Object)blockPos));
    }

    public DamageSource getDamageSource() {
        return this.damageSource;
    }

    public Map<PlayerEntity, Vec3d> getAffectedPlayers() {
        return this.affectedPlayers;
    }

    @Nullable
    public LivingEntity getCausingEntity() {
        Entity entity;
        if (this.entity == null) {
            return null;
        }
        if (this.entity instanceof TntEntity) {
            return ((TntEntity)this.entity).getCausingEntity();
        }
        if (this.entity instanceof LivingEntity) {
            return (LivingEntity)this.entity;
        }
        if (this.entity instanceof ProjectileEntity && (entity = ((ProjectileEntity)this.entity).getOwner()) instanceof LivingEntity) {
            return (LivingEntity)entity;
        }
        return null;
    }

    public void clearAffectedBlocks() {
        this.affectedBlocks.clear();
    }

    public List<BlockPos> getAffectedBlocks() {
        return this.affectedBlocks;
    }

    public static enum DestructionType {
        NONE,
        BREAK,
        DESTROY;

    }
}

