/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.item;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.passive.FoxEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;

public class ChorusFruitItem
extends Item {
    public ChorusFruitItem(Item.Settings settings) {
        super(settings);
    }

    @Override
    public ItemStack finishUsing(ItemStack stack, World world, LivingEntity user) {
        ItemStack itemStack = super.finishUsing(stack, world, user);
        if (!world.isClient) {
            double d = user.getX();
            _snowman = user.getY();
            _snowman = user.getZ();
            for (int i = 0; i < 16; ++i) {
                double d2 = user.getX() + (user.getRandom().nextDouble() - 0.5) * 16.0;
                _snowman = MathHelper.clamp(user.getY() + (double)(user.getRandom().nextInt(16) - 8), 0.0, (double)(world.getDimensionHeight() - 1));
                _snowman = user.getZ() + (user.getRandom().nextDouble() - 0.5) * 16.0;
                if (user.hasVehicle()) {
                    user.stopRiding();
                }
                if (!user.teleport(d2, _snowman, _snowman, true)) continue;
                SoundEvent _snowman2 = user instanceof FoxEntity ? SoundEvents.ENTITY_FOX_TELEPORT : SoundEvents.ITEM_CHORUS_FRUIT_TELEPORT;
                world.playSound(null, d, _snowman, _snowman, _snowman2, SoundCategory.PLAYERS, 1.0f, 1.0f);
                user.playSound(_snowman2, 1.0f, 1.0f);
                break;
            }
            if (user instanceof PlayerEntity) {
                ((PlayerEntity)user).getItemCooldownManager().set(this, 20);
            }
        }
        return itemStack;
    }
}

