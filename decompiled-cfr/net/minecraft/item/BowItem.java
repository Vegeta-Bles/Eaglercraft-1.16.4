/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.item;

import java.util.function.Predicate;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.PersistentProjectileEntity;
import net.minecraft.item.ArrowItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.item.RangedWeaponItem;
import net.minecraft.item.Vanishable;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.stat.Stats;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.util.UseAction;
import net.minecraft.world.World;

public class BowItem
extends RangedWeaponItem
implements Vanishable {
    public BowItem(Item.Settings settings) {
        super(settings);
    }

    @Override
    public void onStoppedUsing(ItemStack stack, World world, LivingEntity user, int remainingUseTicks) {
        if (!(user instanceof PlayerEntity)) {
            return;
        }
        PlayerEntity playerEntity = (PlayerEntity)user;
        boolean _snowman2 = playerEntity.abilities.creativeMode || EnchantmentHelper.getLevel(Enchantments.INFINITY, stack) > 0;
        ItemStack _snowman3 = playerEntity.getArrowType(stack);
        if (_snowman3.isEmpty() && !_snowman2) {
            return;
        }
        if (_snowman3.isEmpty()) {
            _snowman3 = new ItemStack(Items.ARROW);
        }
        if ((double)(_snowman = BowItem.getPullProgress(_snowman = this.getMaxUseTime(stack) - remainingUseTicks)) < 0.1) {
            return;
        }
        boolean bl = _snowman = _snowman2 && _snowman3.getItem() == Items.ARROW;
        if (!world.isClient) {
            ArrowItem arrowItem = (ArrowItem)(_snowman3.getItem() instanceof ArrowItem ? _snowman3.getItem() : Items.ARROW);
            PersistentProjectileEntity _snowman4 = arrowItem.createArrow(world, _snowman3, playerEntity);
            _snowman4.setProperties(playerEntity, playerEntity.pitch, playerEntity.yaw, 0.0f, _snowman * 3.0f, 1.0f);
            if (_snowman == 1.0f) {
                _snowman4.setCritical(true);
            }
            if ((_snowman = EnchantmentHelper.getLevel(Enchantments.POWER, stack)) > 0) {
                _snowman4.setDamage(_snowman4.getDamage() + (double)_snowman * 0.5 + 0.5);
            }
            if ((_snowman = EnchantmentHelper.getLevel(Enchantments.PUNCH, stack)) > 0) {
                _snowman4.setPunch(_snowman);
            }
            if (EnchantmentHelper.getLevel(Enchantments.FLAME, stack) > 0) {
                _snowman4.setOnFireFor(100);
            }
            stack.damage(1, playerEntity, p -> p.sendToolBreakStatus(playerEntity.getActiveHand()));
            if (_snowman || playerEntity.abilities.creativeMode && (_snowman3.getItem() == Items.SPECTRAL_ARROW || _snowman3.getItem() == Items.TIPPED_ARROW)) {
                _snowman4.pickupType = PersistentProjectileEntity.PickupPermission.CREATIVE_ONLY;
            }
            world.spawnEntity(_snowman4);
        }
        world.playSound(null, playerEntity.getX(), playerEntity.getY(), playerEntity.getZ(), SoundEvents.ENTITY_ARROW_SHOOT, SoundCategory.PLAYERS, 1.0f, 1.0f / (RANDOM.nextFloat() * 0.4f + 1.2f) + _snowman * 0.5f);
        if (!_snowman && !playerEntity.abilities.creativeMode) {
            _snowman3.decrement(1);
            if (_snowman3.isEmpty()) {
                playerEntity.inventory.removeOne(_snowman3);
            }
        }
        playerEntity.incrementStat(Stats.USED.getOrCreateStat(this));
    }

    public static float getPullProgress(int useTicks) {
        float f = (float)useTicks / 20.0f;
        if ((f = (f * f + f * 2.0f) / 3.0f) > 1.0f) {
            f = 1.0f;
        }
        return f;
    }

    @Override
    public int getMaxUseTime(ItemStack stack) {
        return 72000;
    }

    @Override
    public UseAction getUseAction(ItemStack stack) {
        return UseAction.BOW;
    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        ItemStack itemStack = user.getStackInHand(hand);
        boolean bl = _snowman = !user.getArrowType(itemStack).isEmpty();
        if (user.abilities.creativeMode || _snowman) {
            user.setCurrentHand(hand);
            return TypedActionResult.consume(itemStack);
        }
        return TypedActionResult.fail(itemStack);
    }

    @Override
    public Predicate<ItemStack> getProjectiles() {
        return BOW_PROJECTILES;
    }

    @Override
    public int getRange() {
        return 15;
    }
}

