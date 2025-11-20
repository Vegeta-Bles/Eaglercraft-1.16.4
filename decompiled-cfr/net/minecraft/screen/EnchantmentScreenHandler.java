/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.screen;

import java.util.List;
import java.util.Random;
import net.minecraft.advancement.criterion.Criteria;
import net.minecraft.block.Blocks;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.EnchantmentLevelEntry;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.Inventory;
import net.minecraft.inventory.SimpleInventory;
import net.minecraft.item.EnchantedBookItem;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.screen.Property;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.screen.ScreenHandlerContext;
import net.minecraft.screen.ScreenHandlerType;
import net.minecraft.screen.slot.Slot;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.stat.Stats;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.registry.Registry;

public class EnchantmentScreenHandler
extends ScreenHandler {
    private final Inventory inventory = new SimpleInventory(this, 2){
        final /* synthetic */ EnchantmentScreenHandler field_7815;
        {
            this.field_7815 = enchantmentScreenHandler;
            super(n);
        }

        public void markDirty() {
            super.markDirty();
            this.field_7815.onContentChanged(this);
        }
    };
    private final ScreenHandlerContext context;
    private final Random random = new Random();
    private final Property seed = Property.create();
    public final int[] enchantmentPower = new int[3];
    public final int[] enchantmentId = new int[]{-1, -1, -1};
    public final int[] enchantmentLevel = new int[]{-1, -1, -1};

    public EnchantmentScreenHandler(int syncId, PlayerInventory playerInventory) {
        this(syncId, playerInventory, ScreenHandlerContext.EMPTY);
    }

    public EnchantmentScreenHandler(int syncId, PlayerInventory playerInventory, ScreenHandlerContext context) {
        super(ScreenHandlerType.ENCHANTMENT, syncId);
        int n;
        this.context = context;
        this.addSlot(new Slot(this, this.inventory, 0, 15, 47){
            final /* synthetic */ EnchantmentScreenHandler field_7816;
            {
                this.field_7816 = enchantmentScreenHandler;
                super(inventory, n, n2, n3);
            }

            public boolean canInsert(ItemStack stack) {
                return true;
            }

            public int getMaxItemCount() {
                return 1;
            }
        });
        this.addSlot(new Slot(this, this.inventory, 1, 35, 47){
            final /* synthetic */ EnchantmentScreenHandler field_7817;
            {
                this.field_7817 = enchantmentScreenHandler;
                super(inventory, n, n2, n3);
            }

            public boolean canInsert(ItemStack stack) {
                return stack.getItem() == Items.LAPIS_LAZULI;
            }
        });
        for (n = 0; n < 3; ++n) {
            for (_snowman = 0; _snowman < 9; ++_snowman) {
                this.addSlot(new Slot(playerInventory, _snowman + n * 9 + 9, 8 + _snowman * 18, 84 + n * 18));
            }
        }
        for (n = 0; n < 9; ++n) {
            this.addSlot(new Slot(playerInventory, n, 8 + n * 18, 142));
        }
        this.addProperty(Property.create(this.enchantmentPower, 0));
        this.addProperty(Property.create(this.enchantmentPower, 1));
        this.addProperty(Property.create(this.enchantmentPower, 2));
        this.addProperty(this.seed).set(playerInventory.player.getEnchantmentTableSeed());
        this.addProperty(Property.create(this.enchantmentId, 0));
        this.addProperty(Property.create(this.enchantmentId, 1));
        this.addProperty(Property.create(this.enchantmentId, 2));
        this.addProperty(Property.create(this.enchantmentLevel, 0));
        this.addProperty(Property.create(this.enchantmentLevel, 1));
        this.addProperty(Property.create(this.enchantmentLevel, 2));
    }

    @Override
    public void onContentChanged(Inventory inventory) {
        if (inventory == this.inventory) {
            ItemStack itemStack = inventory.getStack(0);
            if (itemStack.isEmpty() || !itemStack.isEnchantable()) {
                for (int i = 0; i < 3; ++i) {
                    this.enchantmentPower[i] = 0;
                    this.enchantmentId[i] = -1;
                    this.enchantmentLevel[i] = -1;
                }
            } else {
                this.context.run((world, blockPos) -> {
                    int n = 0;
                    for (_snowman = -1; _snowman <= 1; ++_snowman) {
                        for (_snowman = -1; _snowman <= 1; ++_snowman) {
                            if (_snowman == 0 && _snowman == 0 || !world.isAir(blockPos.add(_snowman, 0, _snowman)) || !world.isAir(blockPos.add(_snowman, 1, _snowman))) continue;
                            if (world.getBlockState(blockPos.add(_snowman * 2, 0, _snowman * 2)).isOf(Blocks.BOOKSHELF)) {
                                ++n;
                            }
                            if (world.getBlockState(blockPos.add(_snowman * 2, 1, _snowman * 2)).isOf(Blocks.BOOKSHELF)) {
                                ++n;
                            }
                            if (_snowman == 0 || _snowman == 0) continue;
                            if (world.getBlockState(blockPos.add(_snowman * 2, 0, _snowman)).isOf(Blocks.BOOKSHELF)) {
                                ++n;
                            }
                            if (world.getBlockState(blockPos.add(_snowman * 2, 1, _snowman)).isOf(Blocks.BOOKSHELF)) {
                                ++n;
                            }
                            if (world.getBlockState(blockPos.add(_snowman, 0, _snowman * 2)).isOf(Blocks.BOOKSHELF)) {
                                ++n;
                            }
                            if (!world.getBlockState(blockPos.add(_snowman, 1, _snowman * 2)).isOf(Blocks.BOOKSHELF)) continue;
                            ++n;
                        }
                    }
                    this.random.setSeed(this.seed.get());
                    for (_snowman = 0; _snowman < 3; ++_snowman) {
                        this.enchantmentPower[_snowman] = EnchantmentHelper.calculateRequiredExperienceLevel(this.random, _snowman, n, itemStack);
                        this.enchantmentId[_snowman] = -1;
                        this.enchantmentLevel[_snowman] = -1;
                        if (this.enchantmentPower[_snowman] >= _snowman + 1) continue;
                        this.enchantmentPower[_snowman] = 0;
                    }
                    for (_snowman = 0; _snowman < 3; ++_snowman) {
                        if (this.enchantmentPower[_snowman] <= 0 || (_snowman = this.generateEnchantments(itemStack, _snowman, this.enchantmentPower[_snowman])) == null || _snowman.isEmpty()) continue;
                        EnchantmentLevelEntry enchantmentLevelEntry = _snowman.get(this.random.nextInt(_snowman.size()));
                        this.enchantmentId[_snowman] = Registry.ENCHANTMENT.getRawId(enchantmentLevelEntry.enchantment);
                        this.enchantmentLevel[_snowman] = enchantmentLevelEntry.level;
                    }
                    this.sendContentUpdates();
                });
            }
        }
    }

    @Override
    public boolean onButtonClick(PlayerEntity player, int id) {
        ItemStack itemStack = this.inventory.getStack(0);
        _snowman = this.inventory.getStack(1);
        int _snowman2 = id + 1;
        if ((_snowman.isEmpty() || _snowman.getCount() < _snowman2) && !player.abilities.creativeMode) {
            return false;
        }
        if (this.enchantmentPower[id] > 0 && !itemStack.isEmpty() && (player.experienceLevel >= _snowman2 && player.experienceLevel >= this.enchantmentPower[id] || player.abilities.creativeMode)) {
            this.context.run((world, blockPos) -> {
                ItemStack itemStack3 = itemStack;
                List<EnchantmentLevelEntry> _snowman2 = this.generateEnchantments(itemStack3, id, this.enchantmentPower[id]);
                if (!_snowman2.isEmpty()) {
                    PlayerEntity playerEntity2;
                    player.applyEnchantmentCosts(itemStack3, _snowman2);
                    boolean bl = _snowman = itemStack3.getItem() == Items.BOOK;
                    if (_snowman) {
                        itemStack3 = new ItemStack(Items.ENCHANTED_BOOK);
                        CompoundTag compoundTag = itemStack.getTag();
                        if (compoundTag != null) {
                            itemStack3.setTag(compoundTag.copy());
                        }
                        this.inventory.setStack(0, itemStack3);
                    }
                    for (int i = 0; i < _snowman2.size(); ++i) {
                        EnchantmentLevelEntry enchantmentLevelEntry = _snowman2.get(i);
                        if (_snowman) {
                            EnchantedBookItem.addEnchantment(itemStack3, enchantmentLevelEntry);
                            continue;
                        }
                        itemStack3.addEnchantment(enchantmentLevelEntry.enchantment, enchantmentLevelEntry.level);
                    }
                    if (!playerEntity2.abilities.creativeMode) {
                        _snowman.decrement(_snowman2);
                        if (_snowman.isEmpty()) {
                            this.inventory.setStack(1, ItemStack.EMPTY);
                        }
                    }
                    player.incrementStat(Stats.ENCHANT_ITEM);
                    if (player instanceof ServerPlayerEntity) {
                        Criteria.ENCHANTED_ITEM.trigger((ServerPlayerEntity)player, itemStack3, _snowman2);
                    }
                    this.inventory.markDirty();
                    this.seed.set(player.getEnchantmentTableSeed());
                    this.onContentChanged(this.inventory);
                    world.playSound(null, (BlockPos)blockPos, SoundEvents.BLOCK_ENCHANTMENT_TABLE_USE, SoundCategory.BLOCKS, 1.0f, world.random.nextFloat() * 0.1f + 0.9f);
                }
            });
            return true;
        }
        return false;
    }

    private List<EnchantmentLevelEntry> generateEnchantments(ItemStack stack, int slot, int level) {
        this.random.setSeed(this.seed.get() + slot);
        List<EnchantmentLevelEntry> list = EnchantmentHelper.generateEnchantments(this.random, stack, level, false);
        if (stack.getItem() == Items.BOOK && list.size() > 1) {
            list.remove(this.random.nextInt(list.size()));
        }
        return list;
    }

    public int getLapisCount() {
        ItemStack itemStack = this.inventory.getStack(1);
        if (itemStack.isEmpty()) {
            return 0;
        }
        return itemStack.getCount();
    }

    public int getSeed() {
        return this.seed.get();
    }

    @Override
    public void close(PlayerEntity player) {
        super.close(player);
        this.context.run((world, blockPos) -> this.dropInventory(player, playerEntity.world, this.inventory));
    }

    @Override
    public boolean canUse(PlayerEntity player) {
        return EnchantmentScreenHandler.canUse(this.context, player, Blocks.ENCHANTING_TABLE);
    }

    @Override
    public ItemStack transferSlot(PlayerEntity player, int index) {
        ItemStack itemStack = ItemStack.EMPTY;
        Slot _snowman2 = (Slot)this.slots.get(index);
        if (_snowman2 != null && _snowman2.hasStack()) {
            _snowman = _snowman2.getStack();
            itemStack = _snowman.copy();
            if (index == 0) {
                if (!this.insertItem(_snowman, 2, 38, true)) {
                    return ItemStack.EMPTY;
                }
            } else if (index == 1) {
                if (!this.insertItem(_snowman, 2, 38, true)) {
                    return ItemStack.EMPTY;
                }
            } else if (_snowman.getItem() == Items.LAPIS_LAZULI) {
                if (!this.insertItem(_snowman, 1, 2, true)) {
                    return ItemStack.EMPTY;
                }
            } else if (!((Slot)this.slots.get(0)).hasStack() && ((Slot)this.slots.get(0)).canInsert(_snowman)) {
                _snowman = _snowman.copy();
                _snowman.setCount(1);
                _snowman.decrement(1);
                ((Slot)this.slots.get(0)).setStack(_snowman);
            } else {
                return ItemStack.EMPTY;
            }
            if (_snowman.isEmpty()) {
                _snowman2.setStack(ItemStack.EMPTY);
            } else {
                _snowman2.markDirty();
            }
            if (_snowman.getCount() == itemStack.getCount()) {
                return ItemStack.EMPTY;
            }
            _snowman2.onTakeItem(player, _snowman);
        }
        return itemStack;
    }
}

