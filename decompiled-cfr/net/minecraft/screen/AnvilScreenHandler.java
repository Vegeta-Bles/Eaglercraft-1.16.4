/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.apache.commons.lang3.StringUtils
 *  org.apache.logging.log4j.LogManager
 *  org.apache.logging.log4j.Logger
 */
package net.minecraft.screen;

import java.util.Map;
import net.minecraft.block.AnvilBlock;
import net.minecraft.block.BlockState;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.item.EnchantedBookItem;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.screen.ForgingScreenHandler;
import net.minecraft.screen.Property;
import net.minecraft.screen.ScreenHandlerContext;
import net.minecraft.screen.ScreenHandlerType;
import net.minecraft.tag.BlockTags;
import net.minecraft.text.LiteralText;
import net.minecraft.util.math.BlockPos;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class AnvilScreenHandler
extends ForgingScreenHandler {
    private static final Logger LOGGER = LogManager.getLogger();
    private int repairItemUsage;
    private String newItemName;
    private final Property levelCost = Property.create();

    public AnvilScreenHandler(int syncId, PlayerInventory inventory) {
        this(syncId, inventory, ScreenHandlerContext.EMPTY);
    }

    public AnvilScreenHandler(int syncId, PlayerInventory inventory, ScreenHandlerContext context) {
        super(ScreenHandlerType.ANVIL, syncId, inventory, context);
        this.addProperty(this.levelCost);
    }

    @Override
    protected boolean canUse(BlockState state) {
        return state.isIn(BlockTags.ANVIL);
    }

    @Override
    protected boolean canTakeOutput(PlayerEntity player, boolean present) {
        return (player.abilities.creativeMode || player.experienceLevel >= this.levelCost.get()) && this.levelCost.get() > 0;
    }

    @Override
    protected ItemStack onTakeOutput(PlayerEntity player, ItemStack stack) {
        if (!player.abilities.creativeMode) {
            player.addExperienceLevels(-this.levelCost.get());
        }
        this.input.setStack(0, ItemStack.EMPTY);
        if (this.repairItemUsage > 0) {
            ItemStack itemStack = this.input.getStack(1);
            if (!itemStack.isEmpty() && itemStack.getCount() > this.repairItemUsage) {
                itemStack.decrement(this.repairItemUsage);
                this.input.setStack(1, itemStack);
            } else {
                this.input.setStack(1, ItemStack.EMPTY);
            }
        } else {
            this.input.setStack(1, ItemStack.EMPTY);
        }
        this.levelCost.set(0);
        this.context.run((world, blockPos) -> {
            BlockState blockState = world.getBlockState((BlockPos)blockPos);
            if (!playerEntity.abilities.creativeMode && blockState.isIn(BlockTags.ANVIL) && player.getRandom().nextFloat() < 0.12f) {
                _snowman = AnvilBlock.getLandingState(blockState);
                if (_snowman == null) {
                    world.removeBlock((BlockPos)blockPos, false);
                    world.syncWorldEvent(1029, (BlockPos)blockPos, 0);
                } else {
                    world.setBlockState((BlockPos)blockPos, _snowman, 2);
                    world.syncWorldEvent(1030, (BlockPos)blockPos, 0);
                }
            } else {
                world.syncWorldEvent(1030, (BlockPos)blockPos, 0);
            }
        });
        return stack;
    }

    @Override
    public void updateResult() {
        ItemStack itemStack;
        int n;
        ItemStack itemStack2 = this.input.getStack(0);
        this.levelCost.set(1);
        int _snowman2 = 0;
        int _snowman3 = 0;
        int _snowman4 = 0;
        if (itemStack2.isEmpty()) {
            this.output.setStack(0, ItemStack.EMPTY);
            this.levelCost.set(0);
            return;
        }
        itemStack = itemStack2.copy();
        _snowman = this.input.getStack(1);
        Map<Enchantment, Integer> _snowman5 = EnchantmentHelper.get(itemStack);
        _snowman3 += itemStack2.getRepairCost() + (_snowman.isEmpty() ? 0 : _snowman.getRepairCost());
        this.repairItemUsage = 0;
        if (!_snowman.isEmpty()) {
            int n2 = n = _snowman.getItem() == Items.ENCHANTED_BOOK && !EnchantedBookItem.getEnchantmentTag(_snowman).isEmpty() ? 1 : 0;
            if (itemStack.isDamageable() && itemStack.getItem().canRepair(itemStack2, _snowman)) {
                int n3 = Math.min(itemStack.getDamage(), itemStack.getMaxDamage() / 4);
                if (n3 <= 0) {
                    this.output.setStack(0, ItemStack.EMPTY);
                    this.levelCost.set(0);
                    return;
                }
                for (_snowman = 0; n3 > 0 && _snowman < _snowman.getCount(); ++_snowman) {
                    _snowman = itemStack.getDamage() - n3;
                    itemStack.setDamage(_snowman);
                    ++_snowman2;
                    n3 = Math.min(itemStack.getDamage(), itemStack.getMaxDamage() / 4);
                }
                this.repairItemUsage = _snowman;
            } else {
                int n4;
                if (!(n != 0 || itemStack.getItem() == _snowman.getItem() && itemStack.isDamageable())) {
                    this.output.setStack(0, ItemStack.EMPTY);
                    this.levelCost.set(0);
                    return;
                }
                if (itemStack.isDamageable() && n == 0) {
                    _snowman = itemStack2.getMaxDamage() - itemStack2.getDamage();
                    n6 = _snowman.getMaxDamage() - _snowman.getDamage();
                    n4 = n6 + itemStack.getMaxDamage() * 12 / 100;
                    _snowman = _snowman + n4;
                    _snowman = itemStack.getMaxDamage() - _snowman;
                    if (_snowman < 0) {
                        _snowman = 0;
                    }
                    if (_snowman < itemStack.getDamage()) {
                        itemStack.setDamage(_snowman);
                        _snowman2 += 2;
                    }
                }
                Map<Enchantment, Integer> _snowman6 = EnchantmentHelper.get(_snowman);
                n6 = 0;
                n4 = 0;
                for (Enchantment enchantment : _snowman6.keySet()) {
                    if (enchantment == null) continue;
                    int n5 = _snowman5.getOrDefault(enchantment, 0);
                    _snowman = n5 == (_snowman = _snowman6.get(enchantment).intValue()) ? _snowman + 1 : Math.max(_snowman, n5);
                    boolean _snowman7 = enchantment.isAcceptableItem(itemStack2);
                    if (this.player.abilities.creativeMode || itemStack2.getItem() == Items.ENCHANTED_BOOK) {
                        _snowman7 = true;
                    }
                    for (Enchantment enchantment2 : _snowman5.keySet()) {
                        if (enchantment2 == enchantment || enchantment.canCombine(enchantment2)) continue;
                        _snowman7 = false;
                        ++_snowman2;
                    }
                    if (!_snowman7) {
                        n4 = 1;
                        continue;
                    }
                    int n6 = 1;
                    if (_snowman > enchantment.getMaxLevel()) {
                        _snowman = enchantment.getMaxLevel();
                    }
                    _snowman5.put(enchantment, _snowman);
                    _snowman = 0;
                    switch (enchantment.getRarity()) {
                        case COMMON: {
                            _snowman = 1;
                            break;
                        }
                        case UNCOMMON: {
                            _snowman = 2;
                            break;
                        }
                        case RARE: {
                            _snowman = 4;
                            break;
                        }
                        case VERY_RARE: {
                            _snowman = 8;
                        }
                    }
                    if (n != 0) {
                        _snowman = Math.max(1, _snowman / 2);
                    }
                    _snowman2 += _snowman * _snowman;
                    if (itemStack2.getCount() <= 1) continue;
                    _snowman2 = 40;
                }
                if (n4 != 0 && n6 == 0) {
                    this.output.setStack(0, ItemStack.EMPTY);
                    this.levelCost.set(0);
                    return;
                }
            }
        }
        if (StringUtils.isBlank((CharSequence)this.newItemName)) {
            if (itemStack2.hasCustomName()) {
                _snowman4 = 1;
                _snowman2 += _snowman4;
                itemStack.removeCustomName();
            }
        } else if (!this.newItemName.equals(itemStack2.getName().getString())) {
            _snowman4 = 1;
            _snowman2 += _snowman4;
            itemStack.setCustomName(new LiteralText(this.newItemName));
        }
        this.levelCost.set(_snowman3 + _snowman2);
        if (_snowman2 <= 0) {
            itemStack = ItemStack.EMPTY;
        }
        if (_snowman4 == _snowman2 && _snowman4 > 0 && this.levelCost.get() >= 40) {
            this.levelCost.set(39);
        }
        if (this.levelCost.get() >= 40 && !this.player.abilities.creativeMode) {
            itemStack = ItemStack.EMPTY;
        }
        if (!itemStack.isEmpty()) {
            n = itemStack.getRepairCost();
            if (!_snowman.isEmpty() && n < _snowman.getRepairCost()) {
                n = _snowman.getRepairCost();
            }
            if (_snowman4 != _snowman2 || _snowman4 == 0) {
                n = AnvilScreenHandler.getNextCost(n);
            }
            itemStack.setRepairCost(n);
            EnchantmentHelper.set(_snowman5, itemStack);
        }
        this.output.setStack(0, itemStack);
        this.sendContentUpdates();
    }

    public static int getNextCost(int cost) {
        return cost * 2 + 1;
    }

    public void setNewItemName(String string) {
        this.newItemName = string;
        if (this.getSlot(2).hasStack()) {
            ItemStack itemStack = this.getSlot(2).getStack();
            if (StringUtils.isBlank((CharSequence)string)) {
                itemStack.removeCustomName();
            } else {
                itemStack.setCustomName(new LiteralText(this.newItemName));
            }
        }
        this.updateResult();
    }

    public int getLevelCost() {
        return this.levelCost.get();
    }
}

