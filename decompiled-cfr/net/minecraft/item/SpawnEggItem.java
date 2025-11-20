/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.collect.Iterables
 *  com.google.common.collect.Maps
 *  javax.annotation.Nullable
 */
package net.minecraft.item;

import com.google.common.collect.Iterables;
import com.google.common.collect.Maps;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import javax.annotation.Nullable;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.FluidBlock;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.MobSpawnerBlockEntity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.passive.PassiveEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUsageContext;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.stat.Stats;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.MobSpawnerLogic;
import net.minecraft.world.RaycastContext;
import net.minecraft.world.World;

public class SpawnEggItem
extends Item {
    private static final Map<EntityType<?>, SpawnEggItem> SPAWN_EGGS = Maps.newIdentityHashMap();
    private final int primaryColor;
    private final int secondaryColor;
    private final EntityType<?> type;

    public SpawnEggItem(EntityType<?> type, int primaryColor, int secondaryColor, Item.Settings settings) {
        super(settings);
        this.type = type;
        this.primaryColor = primaryColor;
        this.secondaryColor = secondaryColor;
        SPAWN_EGGS.put(type, this);
    }

    @Override
    public ActionResult useOnBlock(ItemUsageContext context) {
        World world = context.getWorld();
        if (!(world instanceof ServerWorld)) {
            return ActionResult.SUCCESS;
        }
        ItemStack _snowman2 = context.getStack();
        BlockPos _snowman3 = context.getBlockPos();
        Direction _snowman4 = context.getSide();
        BlockState _snowman5 = world.getBlockState(_snowman3);
        if (_snowman5.isOf(Blocks.SPAWNER) && (object = world.getBlockEntity(_snowman3)) instanceof MobSpawnerBlockEntity) {
            MobSpawnerLogic mobSpawnerLogic = ((MobSpawnerBlockEntity)object).getLogic();
            EntityType<?> _snowman6 = this.getEntityType(_snowman2.getTag());
            mobSpawnerLogic.setEntityId(_snowman6);
            ((BlockEntity)object).markDirty();
            world.updateListeners(_snowman3, _snowman5, _snowman5, 3);
            _snowman2.decrement(1);
            return ActionResult.CONSUME;
        }
        Object object = _snowman5.getCollisionShape(world, _snowman3).isEmpty() ? _snowman3 : _snowman3.offset(_snowman4);
        EntityType<?> _snowman7 = this.getEntityType(_snowman2.getTag());
        if (_snowman7.spawnFromItemStack((ServerWorld)world, _snowman2, context.getPlayer(), (BlockPos)object, SpawnReason.SPAWN_EGG, true, !Objects.equals(_snowman3, object) && _snowman4 == Direction.UP) != null) {
            _snowman2.decrement(1);
        }
        return ActionResult.CONSUME;
    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        ItemStack itemStack = user.getStackInHand(hand);
        BlockHitResult _snowman2 = SpawnEggItem.raycast(world, user, RaycastContext.FluidHandling.SOURCE_ONLY);
        if (((HitResult)_snowman2).getType() != HitResult.Type.BLOCK) {
            return TypedActionResult.pass(itemStack);
        }
        if (!(world instanceof ServerWorld)) {
            return TypedActionResult.success(itemStack);
        }
        BlockHitResult _snowman3 = _snowman2;
        BlockPos _snowman4 = _snowman3.getBlockPos();
        if (!(world.getBlockState(_snowman4).getBlock() instanceof FluidBlock)) {
            return TypedActionResult.pass(itemStack);
        }
        if (!world.canPlayerModifyAt(user, _snowman4) || !user.canPlaceOn(_snowman4, _snowman3.getSide(), itemStack)) {
            return TypedActionResult.fail(itemStack);
        }
        EntityType<?> _snowman5 = this.getEntityType(itemStack.getTag());
        if (_snowman5.spawnFromItemStack((ServerWorld)world, itemStack, user, _snowman4, SpawnReason.SPAWN_EGG, false, false) == null) {
            return TypedActionResult.pass(itemStack);
        }
        if (!user.abilities.creativeMode) {
            itemStack.decrement(1);
        }
        user.incrementStat(Stats.USED.getOrCreateStat(this));
        return TypedActionResult.consume(itemStack);
    }

    public boolean isOfSameEntityType(@Nullable CompoundTag tag, EntityType<?> type) {
        return Objects.equals(this.getEntityType(tag), type);
    }

    public int getColor(int num) {
        return num == 0 ? this.primaryColor : this.secondaryColor;
    }

    @Nullable
    public static SpawnEggItem forEntity(@Nullable EntityType<?> type) {
        return SPAWN_EGGS.get(type);
    }

    public static Iterable<SpawnEggItem> getAll() {
        return Iterables.unmodifiableIterable(SPAWN_EGGS.values());
    }

    public EntityType<?> getEntityType(@Nullable CompoundTag tag) {
        CompoundTag compoundTag;
        if (tag != null && tag.contains("EntityTag", 10) && (compoundTag = tag.getCompound("EntityTag")).contains("id", 8)) {
            return EntityType.get(compoundTag.getString("id")).orElse(this.type);
        }
        return this.type;
    }

    public Optional<MobEntity> spawnBaby(PlayerEntity user, MobEntity mobEntity, EntityType<? extends MobEntity> entityType, ServerWorld serverWorld, Vec3d vec3d, ItemStack itemStack) {
        if (!this.isOfSameEntityType(itemStack.getTag(), entityType)) {
            return Optional.empty();
        }
        MobEntity mobEntity2 = mobEntity instanceof PassiveEntity ? ((PassiveEntity)mobEntity).createChild(serverWorld, (PassiveEntity)mobEntity) : entityType.create(serverWorld);
        if (mobEntity2 == null) {
            return Optional.empty();
        }
        mobEntity2.setBaby(true);
        if (!mobEntity2.isBaby()) {
            return Optional.empty();
        }
        mobEntity2.refreshPositionAndAngles(vec3d.getX(), vec3d.getY(), vec3d.getZ(), 0.0f, 0.0f);
        serverWorld.spawnEntityAndPassengers(mobEntity2);
        if (itemStack.hasCustomName()) {
            mobEntity2.setCustomName(itemStack.getName());
        }
        if (!user.abilities.creativeMode) {
            itemStack.decrement(1);
        }
        return Optional.of(mobEntity2);
    }
}

