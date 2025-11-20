/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.screen;

import java.util.Map;
import java.util.stream.Collectors;
import net.minecraft.block.Blocks;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.ExperienceOrbEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.CraftingResultInventory;
import net.minecraft.inventory.Inventory;
import net.minecraft.inventory.SimpleInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.screen.AnvilScreenHandler;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.screen.ScreenHandlerContext;
import net.minecraft.screen.ScreenHandlerType;
import net.minecraft.screen.slot.Slot;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class GrindstoneScreenHandler
extends ScreenHandler {
    private final Inventory result = new CraftingResultInventory();
    private final Inventory input = new SimpleInventory(this, 2){
        final /* synthetic */ GrindstoneScreenHandler field_16776;
        {
            this.field_16776 = grindstoneScreenHandler;
            super(n);
        }

        public void markDirty() {
            super.markDirty();
            this.field_16776.onContentChanged(this);
        }
    };
    private final ScreenHandlerContext context;

    public GrindstoneScreenHandler(int syncId, PlayerInventory playerInventory) {
        this(syncId, playerInventory, ScreenHandlerContext.EMPTY);
    }

    public GrindstoneScreenHandler(int syncId, PlayerInventory playerInventory, final ScreenHandlerContext context) {
        super(ScreenHandlerType.GRINDSTONE, syncId);
        int n;
        this.context = context;
        this.addSlot(new Slot(this, this.input, 0, 49, 19){
            final /* synthetic */ GrindstoneScreenHandler field_16777;
            {
                this.field_16777 = grindstoneScreenHandler;
                super(inventory, n, n2, n3);
            }

            public boolean canInsert(ItemStack stack) {
                return stack.isDamageable() || stack.getItem() == Items.ENCHANTED_BOOK || stack.hasEnchantments();
            }
        });
        this.addSlot(new Slot(this, this.input, 1, 49, 40){
            final /* synthetic */ GrindstoneScreenHandler field_16778;
            {
                this.field_16778 = grindstoneScreenHandler;
                super(inventory, n, n2, n3);
            }

            public boolean canInsert(ItemStack stack) {
                return stack.isDamageable() || stack.getItem() == Items.ENCHANTED_BOOK || stack.hasEnchantments();
            }
        });
        this.addSlot(new Slot(this.result, 2, 129, 34){

            @Override
            public boolean canInsert(ItemStack stack) {
                return false;
            }

            @Override
            public ItemStack onTakeItem(PlayerEntity player, ItemStack stack) {
                context.run((world2, blockPos) -> {
                    World world2;
                    for (int i = this.getExperience((World)world2); i > 0; i -= _snowman) {
                        _snowman = ExperienceOrbEntity.roundToOrbSize(i);
                        world2.spawnEntity(new ExperienceOrbEntity((World)world2, blockPos.getX(), (double)blockPos.getY() + 0.5, (double)blockPos.getZ() + 0.5, _snowman));
                    }
                    world2.syncWorldEvent(1042, (BlockPos)blockPos, 0);
                });
                GrindstoneScreenHandler.this.input.setStack(0, ItemStack.EMPTY);
                GrindstoneScreenHandler.this.input.setStack(1, ItemStack.EMPTY);
                return stack;
            }

            private int getExperience(World world) {
                int n = 0;
                n += this.getExperience(GrindstoneScreenHandler.this.input.getStack(0));
                if ((n += this.getExperience(GrindstoneScreenHandler.this.input.getStack(1))) > 0) {
                    _snowman = (int)Math.ceil((double)n / 2.0);
                    return _snowman + world.random.nextInt(_snowman);
                }
                return 0;
            }

            private int getExperience(ItemStack stack) {
                int n = 0;
                Map<Enchantment, Integer> _snowman2 = EnchantmentHelper.get(stack);
                for (Map.Entry<Enchantment, Integer> entry : _snowman2.entrySet()) {
                    Enchantment enchantment = entry.getKey();
                    Integer _snowman3 = entry.getValue();
                    if (enchantment.isCursed()) continue;
                    n += enchantment.getMinPower(_snowman3);
                }
                return n;
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
    }

    @Override
    public void onContentChanged(Inventory inventory) {
        super.onContentChanged(inventory);
        if (inventory == this.input) {
            this.updateResult();
        }
    }

    /*
     * Enabled aggressive block sorting
     */
    private void updateResult() {
        ItemStack itemStack = this.input.getStack(0);
        _snowman = this.input.getStack(1);
        boolean _snowman2 = !itemStack.isEmpty() || !_snowman.isEmpty();
        boolean bl = _snowman = !itemStack.isEmpty() && !_snowman.isEmpty();
        if (_snowman2) {
            ItemStack _snowman7;
            int _snowman6;
            boolean bl2 = _snowman = !itemStack.isEmpty() && itemStack.getItem() != Items.ENCHANTED_BOOK && !itemStack.hasEnchantments() || !_snowman.isEmpty() && _snowman.getItem() != Items.ENCHANTED_BOOK && !_snowman.hasEnchantments();
            if (itemStack.getCount() > 1 || _snowman.getCount() > 1 || !_snowman && _snowman) {
                this.result.setStack(0, ItemStack.EMPTY);
                this.sendContentUpdates();
                return;
            }
            int n = 1;
            if (_snowman) {
                if (itemStack.getItem() != _snowman.getItem()) {
                    this.result.setStack(0, ItemStack.EMPTY);
                    this.sendContentUpdates();
                    return;
                }
                Item item = itemStack.getItem();
                int _snowman3 = item.getMaxDamage() - itemStack.getDamage();
                int _snowman4 = item.getMaxDamage() - _snowman.getDamage();
                int _snowman5 = _snowman3 + _snowman4 + item.getMaxDamage() * 5 / 100;
                _snowman6 = Math.max(item.getMaxDamage() - _snowman5, 0);
                _snowman7 = this.transferEnchantments(itemStack, _snowman);
                if (!_snowman7.isDamageable()) {
                    if (!ItemStack.areEqual(itemStack, _snowman)) {
                        this.result.setStack(0, ItemStack.EMPTY);
                        this.sendContentUpdates();
                        return;
                    }
                    n = 2;
                }
            } else {
                boolean _snowman8 = !itemStack.isEmpty();
                _snowman6 = _snowman8 ? itemStack.getDamage() : _snowman.getDamage();
                _snowman7 = _snowman8 ? itemStack : _snowman;
            }
            this.result.setStack(0, this.grind(_snowman7, _snowman6, n));
        } else {
            this.result.setStack(0, ItemStack.EMPTY);
        }
        this.sendContentUpdates();
    }

    private ItemStack transferEnchantments(ItemStack target, ItemStack source) {
        ItemStack itemStack = target.copy();
        Map<Enchantment, Integer> _snowman2 = EnchantmentHelper.get(source);
        for (Map.Entry<Enchantment, Integer> entry : _snowman2.entrySet()) {
            Enchantment enchantment = entry.getKey();
            if (enchantment.isCursed() && EnchantmentHelper.getLevel(enchantment, itemStack) != 0) continue;
            itemStack.addEnchantment(enchantment, entry.getValue());
        }
        return itemStack;
    }

    private ItemStack grind(ItemStack item, int damage, int amount) {
        ItemStack itemStack = item.copy();
        itemStack.removeSubTag("Enchantments");
        itemStack.removeSubTag("StoredEnchantments");
        if (damage > 0) {
            itemStack.setDamage(damage);
        } else {
            itemStack.removeSubTag("Damage");
        }
        itemStack.setCount(amount);
        Map<Enchantment, Integer> _snowman2 = EnchantmentHelper.get(item).entrySet().stream().filter(entry -> ((Enchantment)entry.getKey()).isCursed()).collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
        EnchantmentHelper.set(_snowman2, itemStack);
        itemStack.setRepairCost(0);
        if (itemStack.getItem() == Items.ENCHANTED_BOOK && _snowman2.size() == 0) {
            itemStack = new ItemStack(Items.BOOK);
            if (item.hasCustomName()) {
                itemStack.setCustomName(item.getName());
            }
        }
        for (int i = 0; i < _snowman2.size(); ++i) {
            itemStack.setRepairCost(AnvilScreenHandler.getNextCost(itemStack.getRepairCost()));
        }
        return itemStack;
    }

    @Override
    public void close(PlayerEntity player) {
        super.close(player);
        this.context.run((world, blockPos) -> this.dropInventory(player, (World)world, this.input));
    }

    @Override
    public boolean canUse(PlayerEntity player) {
        return GrindstoneScreenHandler.canUse(this.context, player, Blocks.GRINDSTONE);
    }

    @Override
    public ItemStack transferSlot(PlayerEntity player, int index) {
        ItemStack itemStack = ItemStack.EMPTY;
        Slot _snowman2 = (Slot)this.slots.get(index);
        if (_snowman2 != null && _snowman2.hasStack()) {
            _snowman = _snowman2.getStack();
            itemStack = _snowman.copy();
            _snowman = this.input.getStack(0);
            _snowman = this.input.getStack(1);
            if (index == 2) {
                if (!this.insertItem(_snowman, 3, 39, true)) {
                    return ItemStack.EMPTY;
                }
                _snowman2.onStackChanged(_snowman, itemStack);
            } else if (index == 0 || index == 1 ? !this.insertItem(_snowman, 3, 39, false) : (_snowman.isEmpty() || _snowman.isEmpty() ? !this.insertItem(_snowman, 0, 2, false) : (index >= 3 && index < 30 ? !this.insertItem(_snowman, 30, 39, false) : index >= 30 && index < 39 && !this.insertItem(_snowman, 3, 30, false)))) {
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

