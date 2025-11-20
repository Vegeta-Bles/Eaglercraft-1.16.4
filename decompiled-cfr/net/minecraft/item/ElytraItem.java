/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.item;

import net.minecraft.block.DispenserBlock;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ArmorItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.item.Wearable;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.world.World;

public class ElytraItem
extends Item
implements Wearable {
    public ElytraItem(Item.Settings settings) {
        super(settings);
        DispenserBlock.registerBehavior(this, ArmorItem.DISPENSER_BEHAVIOR);
    }

    public static boolean isUsable(ItemStack stack) {
        return stack.getDamage() < stack.getMaxDamage() - 1;
    }

    @Override
    public boolean canRepair(ItemStack stack, ItemStack ingredient) {
        return ingredient.getItem() == Items.PHANTOM_MEMBRANE;
    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        ItemStack itemStack = user.getStackInHand(hand);
        EquipmentSlot _snowman2 = MobEntity.getPreferredEquipmentSlot(itemStack);
        _snowman = user.getEquippedStack(_snowman2);
        if (_snowman.isEmpty()) {
            user.equipStack(_snowman2, itemStack.copy());
            itemStack.setCount(0);
            return TypedActionResult.success(itemStack, world.isClient());
        }
        return TypedActionResult.fail(itemStack);
    }
}

