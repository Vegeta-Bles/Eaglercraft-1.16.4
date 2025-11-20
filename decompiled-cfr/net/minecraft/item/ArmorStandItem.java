/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.item;

import java.util.Random;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.decoration.ArmorStandEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUsageContext;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.ActionResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.EulerAngle;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

public class ArmorStandItem
extends Item {
    public ArmorStandItem(Item.Settings settings) {
        super(settings);
    }

    @Override
    public ActionResult useOnBlock(ItemUsageContext context) {
        Direction direction = context.getSide();
        if (direction == Direction.DOWN) {
            return ActionResult.FAIL;
        }
        World _snowman2 = context.getWorld();
        ItemPlacementContext _snowman3 = new ItemPlacementContext(context);
        BlockPos _snowman4 = _snowman3.getBlockPos();
        ItemStack _snowman5 = context.getStack();
        Vec3d _snowman6 = Vec3d.ofBottomCenter(_snowman4);
        Box _snowman7 = EntityType.ARMOR_STAND.getDimensions().method_30231(_snowman6.getX(), _snowman6.getY(), _snowman6.getZ());
        if (!_snowman2.isSpaceEmpty(null, _snowman7, entity -> true) || !_snowman2.getOtherEntities(null, _snowman7).isEmpty()) {
            return ActionResult.FAIL;
        }
        if (_snowman2 instanceof ServerWorld) {
            ServerWorld serverWorld = (ServerWorld)_snowman2;
            ArmorStandEntity _snowman8 = EntityType.ARMOR_STAND.create(serverWorld, _snowman5.getTag(), null, context.getPlayer(), _snowman4, SpawnReason.SPAWN_EGG, true, true);
            if (_snowman8 == null) {
                return ActionResult.FAIL;
            }
            serverWorld.spawnEntityAndPassengers(_snowman8);
            float _snowman9 = (float)MathHelper.floor((MathHelper.wrapDegrees(context.getPlayerYaw() - 180.0f) + 22.5f) / 45.0f) * 45.0f;
            _snowman8.refreshPositionAndAngles(_snowman8.getX(), _snowman8.getY(), _snowman8.getZ(), _snowman9, 0.0f);
            this.setRotations(_snowman8, _snowman2.random);
            _snowman2.spawnEntity(_snowman8);
            _snowman2.playSound(null, _snowman8.getX(), _snowman8.getY(), _snowman8.getZ(), SoundEvents.ENTITY_ARMOR_STAND_PLACE, SoundCategory.BLOCKS, 0.75f, 0.8f);
        }
        _snowman5.decrement(1);
        return ActionResult.success(_snowman2.isClient);
    }

    private void setRotations(ArmorStandEntity stand, Random random) {
        EulerAngle eulerAngle = stand.getHeadRotation();
        float _snowman2 = random.nextFloat() * 5.0f;
        float _snowman3 = random.nextFloat() * 20.0f - 10.0f;
        _snowman = new EulerAngle(eulerAngle.getPitch() + _snowman2, eulerAngle.getYaw() + _snowman3, eulerAngle.getRoll());
        stand.setHeadRotation(_snowman);
        eulerAngle = stand.getBodyRotation();
        _snowman2 = random.nextFloat() * 10.0f - 5.0f;
        _snowman = new EulerAngle(eulerAngle.getPitch(), eulerAngle.getYaw() + _snowman2, eulerAngle.getRoll());
        stand.setBodyRotation(_snowman);
    }
}

