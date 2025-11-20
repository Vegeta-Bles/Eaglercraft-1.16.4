/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.item;

import java.util.List;
import java.util.function.Predicate;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.vehicle.BoatEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.predicate.entity.EntityPredicates;
import net.minecraft.stat.Stats;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.RaycastContext;
import net.minecraft.world.World;

public class BoatItem
extends Item {
    private static final Predicate<Entity> RIDERS = EntityPredicates.EXCEPT_SPECTATOR.and(Entity::collides);
    private final BoatEntity.Type type;

    public BoatItem(BoatEntity.Type type, Item.Settings settings) {
        super(settings);
        this.type = type;
    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        Object object;
        ItemStack itemStack = user.getStackInHand(hand);
        BlockHitResult _snowman2 = BoatItem.raycast(world, user, RaycastContext.FluidHandling.ANY);
        if (((HitResult)_snowman2).getType() == HitResult.Type.MISS) {
            return TypedActionResult.pass(itemStack);
        }
        Vec3d _snowman3 = user.getRotationVec(1.0f);
        double _snowman4 = 5.0;
        List<Entity> _snowman5 = world.getOtherEntities(user, user.getBoundingBox().stretch(_snowman3.multiply(5.0)).expand(1.0), RIDERS);
        if (!_snowman5.isEmpty()) {
            object = user.getCameraPosVec(1.0f);
            for (Entity entity : _snowman5) {
                Box box = entity.getBoundingBox().expand(entity.getTargetingMargin());
                if (!box.contains((Vec3d)object)) continue;
                return TypedActionResult.pass(itemStack);
            }
        }
        if (((HitResult)_snowman2).getType() == HitResult.Type.BLOCK) {
            object = new BoatEntity(world, _snowman2.getPos().x, _snowman2.getPos().y, _snowman2.getPos().z);
            ((BoatEntity)object).setBoatType(this.type);
            ((BoatEntity)object).yaw = user.yaw;
            if (!world.isSpaceEmpty((Entity)object, ((Entity)object).getBoundingBox().expand(-0.1))) {
                return TypedActionResult.fail(itemStack);
            }
            if (!world.isClient) {
                world.spawnEntity((Entity)object);
                if (!user.abilities.creativeMode) {
                    itemStack.decrement(1);
                }
            }
            user.incrementStat(Stats.USED.getOrCreateStat(this));
            return TypedActionResult.success(itemStack, world.isClient());
        }
        return TypedActionResult.pass(itemStack);
    }
}

