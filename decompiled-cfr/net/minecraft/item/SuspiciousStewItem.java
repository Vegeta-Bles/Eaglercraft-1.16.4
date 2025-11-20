/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.item;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.world.World;

public class SuspiciousStewItem
extends Item {
    public SuspiciousStewItem(Item.Settings settings) {
        super(settings);
    }

    public static void addEffectToStew(ItemStack stew, StatusEffect effect, int duration) {
        CompoundTag compoundTag = stew.getOrCreateTag();
        ListTag _snowman2 = compoundTag.getList("Effects", 9);
        _snowman = new CompoundTag();
        _snowman.putByte("EffectId", (byte)StatusEffect.getRawId(effect));
        _snowman.putInt("EffectDuration", duration);
        _snowman2.add(_snowman);
        compoundTag.put("Effects", _snowman2);
    }

    @Override
    public ItemStack finishUsing(ItemStack stack, World world, LivingEntity user) {
        ItemStack itemStack = super.finishUsing(stack, world, user);
        CompoundTag _snowman2 = stack.getTag();
        if (_snowman2 != null && _snowman2.contains("Effects", 9)) {
            ListTag listTag = _snowman2.getList("Effects", 10);
            for (int i = 0; i < listTag.size(); ++i) {
                StatusEffect statusEffect;
                n = 160;
                CompoundTag compoundTag = listTag.getCompound(i);
                if (compoundTag.contains("EffectDuration", 3)) {
                    int n = compoundTag.getInt("EffectDuration");
                }
                if ((statusEffect = StatusEffect.byRawId(compoundTag.getByte("EffectId"))) == null) continue;
                user.addStatusEffect(new StatusEffectInstance(statusEffect, n));
            }
        }
        if (user instanceof PlayerEntity && ((PlayerEntity)user).abilities.creativeMode) {
            return itemStack;
        }
        return new ItemStack(Items.BOWL);
    }
}

