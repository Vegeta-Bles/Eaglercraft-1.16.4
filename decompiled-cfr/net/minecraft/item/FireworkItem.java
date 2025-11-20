/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.collect.Lists
 *  javax.annotation.Nullable
 */
package net.minecraft.item;

import com.google.common.collect.Lists;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import javax.annotation.Nullable;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.FireworkRocketEntity;
import net.minecraft.item.FireworkChargeItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUsageContext;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.text.LiteralText;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Formatting;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

public class FireworkItem
extends Item {
    public FireworkItem(Item.Settings settings) {
        super(settings);
    }

    @Override
    public ActionResult useOnBlock(ItemUsageContext context) {
        World world = context.getWorld();
        if (!world.isClient) {
            ItemStack itemStack = context.getStack();
            Vec3d _snowman2 = context.getHitPos();
            Direction _snowman3 = context.getSide();
            FireworkRocketEntity _snowman4 = new FireworkRocketEntity(world, context.getPlayer(), _snowman2.x + (double)_snowman3.getOffsetX() * 0.15, _snowman2.y + (double)_snowman3.getOffsetY() * 0.15, _snowman2.z + (double)_snowman3.getOffsetZ() * 0.15, itemStack);
            world.spawnEntity(_snowman4);
            itemStack.decrement(1);
        }
        return ActionResult.success(world.isClient);
    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        if (user.isFallFlying()) {
            ItemStack itemStack = user.getStackInHand(hand);
            if (!world.isClient) {
                world.spawnEntity(new FireworkRocketEntity(world, itemStack, user));
                if (!user.abilities.creativeMode) {
                    itemStack.decrement(1);
                }
            }
            return TypedActionResult.success(user.getStackInHand(hand), world.isClient());
        }
        return TypedActionResult.pass(user.getStackInHand(hand));
    }

    @Override
    public void appendTooltip(ItemStack stack, @Nullable World world, List<Text> tooltip, TooltipContext context) {
        CompoundTag compoundTag = stack.getSubTag("Fireworks");
        if (compoundTag == null) {
            return;
        }
        if (compoundTag.contains("Flight", 99)) {
            tooltip.add(new TranslatableText("item.minecraft.firework_rocket.flight").append(" ").append(String.valueOf(compoundTag.getByte("Flight"))).formatted(Formatting.GRAY));
        }
        if (!(_snowman = compoundTag.getList("Explosions", 10)).isEmpty()) {
            for (int i = 0; i < _snowman.size(); ++i) {
                CompoundTag compoundTag2 = _snowman.getCompound(i);
                ArrayList _snowman2 = Lists.newArrayList();
                FireworkChargeItem.appendFireworkTooltip(compoundTag2, _snowman2);
                if (_snowman2.isEmpty()) continue;
                for (int j = 1; j < _snowman2.size(); ++j) {
                    _snowman2.set(j, new LiteralText("  ").append((Text)_snowman2.get(j)).formatted(Formatting.GRAY));
                }
                tooltip.addAll(_snowman2);
            }
        }
    }

    public static enum Type {
        SMALL_BALL(0, "small_ball"),
        LARGE_BALL(1, "large_ball"),
        STAR(2, "star"),
        CREEPER(3, "creeper"),
        BURST(4, "burst");

        private static final Type[] TYPES;
        private final int id;
        private final String name;

        private Type(int id, String name) {
            this.id = id;
            this.name = name;
        }

        public int getId() {
            return this.id;
        }

        public String getName() {
            return this.name;
        }

        public static Type byId(int id) {
            if (id < 0 || id >= TYPES.length) {
                return SMALL_BALL;
            }
            return TYPES[id];
        }

        static {
            TYPES = (Type[])Arrays.stream(Type.values()).sorted(Comparator.comparingInt(type -> type.id)).toArray(Type[]::new);
        }
    }
}

