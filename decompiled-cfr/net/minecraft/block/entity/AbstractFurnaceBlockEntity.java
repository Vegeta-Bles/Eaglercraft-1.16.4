/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.collect.Lists
 *  com.google.common.collect.Maps
 *  it.unimi.dsi.fastutil.objects.Object2IntMap$Entry
 *  it.unimi.dsi.fastutil.objects.Object2IntOpenHashMap
 *  javax.annotation.Nullable
 */
package net.minecraft.block.entity;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import it.unimi.dsi.fastutil.objects.Object2IntMap;
import it.unimi.dsi.fastutil.objects.Object2IntOpenHashMap;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import javax.annotation.Nullable;
import net.minecraft.SharedConstants;
import net.minecraft.block.AbstractFurnaceBlock;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.block.entity.LockableContainerBlockEntity;
import net.minecraft.entity.ExperienceOrbEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.Inventories;
import net.minecraft.inventory.SidedInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemConvertible;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.recipe.AbstractCookingRecipe;
import net.minecraft.recipe.Recipe;
import net.minecraft.recipe.RecipeFinder;
import net.minecraft.recipe.RecipeInputProvider;
import net.minecraft.recipe.RecipeType;
import net.minecraft.recipe.RecipeUnlocker;
import net.minecraft.screen.PropertyDelegate;
import net.minecraft.tag.ItemTags;
import net.minecraft.tag.Tag;
import net.minecraft.util.Identifier;
import net.minecraft.util.Tickable;
import net.minecraft.util.Util;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

public abstract class AbstractFurnaceBlockEntity
extends LockableContainerBlockEntity
implements SidedInventory,
RecipeUnlocker,
RecipeInputProvider,
Tickable {
    private static final int[] TOP_SLOTS = new int[]{0};
    private static final int[] BOTTOM_SLOTS = new int[]{2, 1};
    private static final int[] SIDE_SLOTS = new int[]{1};
    protected DefaultedList<ItemStack> inventory = DefaultedList.ofSize(3, ItemStack.EMPTY);
    private int burnTime;
    private int fuelTime;
    private int cookTime;
    private int cookTimeTotal;
    protected final PropertyDelegate propertyDelegate = new PropertyDelegate(this){
        final /* synthetic */ AbstractFurnaceBlockEntity field_17375;
        {
            this.field_17375 = abstractFurnaceBlockEntity;
        }

        public int get(int index) {
            switch (index) {
                case 0: {
                    return AbstractFurnaceBlockEntity.method_17479(this.field_17375);
                }
                case 1: {
                    return AbstractFurnaceBlockEntity.method_17481(this.field_17375);
                }
                case 2: {
                    return AbstractFurnaceBlockEntity.method_17483(this.field_17375);
                }
                case 3: {
                    return AbstractFurnaceBlockEntity.method_17485(this.field_17375);
                }
            }
            return 0;
        }

        public void set(int index, int value) {
            switch (index) {
                case 0: {
                    AbstractFurnaceBlockEntity.method_17480(this.field_17375, value);
                    break;
                }
                case 1: {
                    AbstractFurnaceBlockEntity.method_17482(this.field_17375, value);
                    break;
                }
                case 2: {
                    AbstractFurnaceBlockEntity.method_17484(this.field_17375, value);
                    break;
                }
                case 3: {
                    AbstractFurnaceBlockEntity.method_17486(this.field_17375, value);
                    break;
                }
            }
        }

        public int size() {
            return 4;
        }
    };
    private final Object2IntOpenHashMap<Identifier> recipesUsed = new Object2IntOpenHashMap();
    protected final RecipeType<? extends AbstractCookingRecipe> recipeType;

    protected AbstractFurnaceBlockEntity(BlockEntityType<?> blockEntityType, RecipeType<? extends AbstractCookingRecipe> recipeType) {
        super(blockEntityType);
        this.recipeType = recipeType;
    }

    public static Map<Item, Integer> createFuelTimeMap() {
        LinkedHashMap linkedHashMap = Maps.newLinkedHashMap();
        AbstractFurnaceBlockEntity.addFuel((Map<Item, Integer>)linkedHashMap, Items.LAVA_BUCKET, 20000);
        AbstractFurnaceBlockEntity.addFuel((Map<Item, Integer>)linkedHashMap, Blocks.COAL_BLOCK, 16000);
        AbstractFurnaceBlockEntity.addFuel((Map<Item, Integer>)linkedHashMap, Items.BLAZE_ROD, 2400);
        AbstractFurnaceBlockEntity.addFuel((Map<Item, Integer>)linkedHashMap, Items.COAL, 1600);
        AbstractFurnaceBlockEntity.addFuel((Map<Item, Integer>)linkedHashMap, Items.CHARCOAL, 1600);
        AbstractFurnaceBlockEntity.addFuel((Map<Item, Integer>)linkedHashMap, ItemTags.LOGS, 300);
        AbstractFurnaceBlockEntity.addFuel((Map<Item, Integer>)linkedHashMap, ItemTags.PLANKS, 300);
        AbstractFurnaceBlockEntity.addFuel((Map<Item, Integer>)linkedHashMap, ItemTags.WOODEN_STAIRS, 300);
        AbstractFurnaceBlockEntity.addFuel((Map<Item, Integer>)linkedHashMap, ItemTags.WOODEN_SLABS, 150);
        AbstractFurnaceBlockEntity.addFuel((Map<Item, Integer>)linkedHashMap, ItemTags.WOODEN_TRAPDOORS, 300);
        AbstractFurnaceBlockEntity.addFuel((Map<Item, Integer>)linkedHashMap, ItemTags.WOODEN_PRESSURE_PLATES, 300);
        AbstractFurnaceBlockEntity.addFuel((Map<Item, Integer>)linkedHashMap, Blocks.OAK_FENCE, 300);
        AbstractFurnaceBlockEntity.addFuel((Map<Item, Integer>)linkedHashMap, Blocks.BIRCH_FENCE, 300);
        AbstractFurnaceBlockEntity.addFuel((Map<Item, Integer>)linkedHashMap, Blocks.SPRUCE_FENCE, 300);
        AbstractFurnaceBlockEntity.addFuel((Map<Item, Integer>)linkedHashMap, Blocks.JUNGLE_FENCE, 300);
        AbstractFurnaceBlockEntity.addFuel((Map<Item, Integer>)linkedHashMap, Blocks.DARK_OAK_FENCE, 300);
        AbstractFurnaceBlockEntity.addFuel((Map<Item, Integer>)linkedHashMap, Blocks.ACACIA_FENCE, 300);
        AbstractFurnaceBlockEntity.addFuel((Map<Item, Integer>)linkedHashMap, Blocks.OAK_FENCE_GATE, 300);
        AbstractFurnaceBlockEntity.addFuel((Map<Item, Integer>)linkedHashMap, Blocks.BIRCH_FENCE_GATE, 300);
        AbstractFurnaceBlockEntity.addFuel((Map<Item, Integer>)linkedHashMap, Blocks.SPRUCE_FENCE_GATE, 300);
        AbstractFurnaceBlockEntity.addFuel((Map<Item, Integer>)linkedHashMap, Blocks.JUNGLE_FENCE_GATE, 300);
        AbstractFurnaceBlockEntity.addFuel((Map<Item, Integer>)linkedHashMap, Blocks.DARK_OAK_FENCE_GATE, 300);
        AbstractFurnaceBlockEntity.addFuel((Map<Item, Integer>)linkedHashMap, Blocks.ACACIA_FENCE_GATE, 300);
        AbstractFurnaceBlockEntity.addFuel((Map<Item, Integer>)linkedHashMap, Blocks.NOTE_BLOCK, 300);
        AbstractFurnaceBlockEntity.addFuel((Map<Item, Integer>)linkedHashMap, Blocks.BOOKSHELF, 300);
        AbstractFurnaceBlockEntity.addFuel((Map<Item, Integer>)linkedHashMap, Blocks.LECTERN, 300);
        AbstractFurnaceBlockEntity.addFuel((Map<Item, Integer>)linkedHashMap, Blocks.JUKEBOX, 300);
        AbstractFurnaceBlockEntity.addFuel((Map<Item, Integer>)linkedHashMap, Blocks.CHEST, 300);
        AbstractFurnaceBlockEntity.addFuel((Map<Item, Integer>)linkedHashMap, Blocks.TRAPPED_CHEST, 300);
        AbstractFurnaceBlockEntity.addFuel((Map<Item, Integer>)linkedHashMap, Blocks.CRAFTING_TABLE, 300);
        AbstractFurnaceBlockEntity.addFuel((Map<Item, Integer>)linkedHashMap, Blocks.DAYLIGHT_DETECTOR, 300);
        AbstractFurnaceBlockEntity.addFuel((Map<Item, Integer>)linkedHashMap, ItemTags.BANNERS, 300);
        AbstractFurnaceBlockEntity.addFuel((Map<Item, Integer>)linkedHashMap, Items.BOW, 300);
        AbstractFurnaceBlockEntity.addFuel((Map<Item, Integer>)linkedHashMap, Items.FISHING_ROD, 300);
        AbstractFurnaceBlockEntity.addFuel((Map<Item, Integer>)linkedHashMap, Blocks.LADDER, 300);
        AbstractFurnaceBlockEntity.addFuel((Map<Item, Integer>)linkedHashMap, ItemTags.SIGNS, 200);
        AbstractFurnaceBlockEntity.addFuel((Map<Item, Integer>)linkedHashMap, Items.WOODEN_SHOVEL, 200);
        AbstractFurnaceBlockEntity.addFuel((Map<Item, Integer>)linkedHashMap, Items.WOODEN_SWORD, 200);
        AbstractFurnaceBlockEntity.addFuel((Map<Item, Integer>)linkedHashMap, Items.WOODEN_HOE, 200);
        AbstractFurnaceBlockEntity.addFuel((Map<Item, Integer>)linkedHashMap, Items.WOODEN_AXE, 200);
        AbstractFurnaceBlockEntity.addFuel((Map<Item, Integer>)linkedHashMap, Items.WOODEN_PICKAXE, 200);
        AbstractFurnaceBlockEntity.addFuel((Map<Item, Integer>)linkedHashMap, ItemTags.WOODEN_DOORS, 200);
        AbstractFurnaceBlockEntity.addFuel((Map<Item, Integer>)linkedHashMap, ItemTags.BOATS, 1200);
        AbstractFurnaceBlockEntity.addFuel((Map<Item, Integer>)linkedHashMap, ItemTags.WOOL, 100);
        AbstractFurnaceBlockEntity.addFuel((Map<Item, Integer>)linkedHashMap, ItemTags.WOODEN_BUTTONS, 100);
        AbstractFurnaceBlockEntity.addFuel((Map<Item, Integer>)linkedHashMap, Items.STICK, 100);
        AbstractFurnaceBlockEntity.addFuel((Map<Item, Integer>)linkedHashMap, ItemTags.SAPLINGS, 100);
        AbstractFurnaceBlockEntity.addFuel((Map<Item, Integer>)linkedHashMap, Items.BOWL, 100);
        AbstractFurnaceBlockEntity.addFuel((Map<Item, Integer>)linkedHashMap, ItemTags.CARPETS, 67);
        AbstractFurnaceBlockEntity.addFuel((Map<Item, Integer>)linkedHashMap, Blocks.DRIED_KELP_BLOCK, 4001);
        AbstractFurnaceBlockEntity.addFuel((Map<Item, Integer>)linkedHashMap, Items.CROSSBOW, 300);
        AbstractFurnaceBlockEntity.addFuel((Map<Item, Integer>)linkedHashMap, Blocks.BAMBOO, 50);
        AbstractFurnaceBlockEntity.addFuel((Map<Item, Integer>)linkedHashMap, Blocks.DEAD_BUSH, 100);
        AbstractFurnaceBlockEntity.addFuel((Map<Item, Integer>)linkedHashMap, Blocks.SCAFFOLDING, 400);
        AbstractFurnaceBlockEntity.addFuel((Map<Item, Integer>)linkedHashMap, Blocks.LOOM, 300);
        AbstractFurnaceBlockEntity.addFuel((Map<Item, Integer>)linkedHashMap, Blocks.BARREL, 300);
        AbstractFurnaceBlockEntity.addFuel((Map<Item, Integer>)linkedHashMap, Blocks.CARTOGRAPHY_TABLE, 300);
        AbstractFurnaceBlockEntity.addFuel((Map<Item, Integer>)linkedHashMap, Blocks.FLETCHING_TABLE, 300);
        AbstractFurnaceBlockEntity.addFuel((Map<Item, Integer>)linkedHashMap, Blocks.SMITHING_TABLE, 300);
        AbstractFurnaceBlockEntity.addFuel((Map<Item, Integer>)linkedHashMap, Blocks.COMPOSTER, 300);
        return linkedHashMap;
    }

    private static boolean isNonFlammableWood(Item item) {
        return ItemTags.NON_FLAMMABLE_WOOD.contains(item);
    }

    private static void addFuel(Map<Item, Integer> fuelTimes, Tag<Item> tag, int fuelTime) {
        for (Item item : tag.values()) {
            if (AbstractFurnaceBlockEntity.isNonFlammableWood(item)) continue;
            fuelTimes.put(item, fuelTime);
        }
    }

    private static void addFuel(Map<Item, Integer> map, ItemConvertible item, int fuelTime) {
        Item item2 = item.asItem();
        if (AbstractFurnaceBlockEntity.isNonFlammableWood(item2)) {
            if (SharedConstants.isDevelopment) {
                throw Util.throwOrPause(new IllegalStateException("A developer tried to explicitly make fire resistant item " + item2.getName(null).getString() + " a furnace fuel. That will not work!"));
            }
            return;
        }
        map.put(item2, fuelTime);
    }

    private boolean isBurning() {
        return this.burnTime > 0;
    }

    @Override
    public void fromTag(BlockState state, CompoundTag tag) {
        super.fromTag(state, tag);
        this.inventory = DefaultedList.ofSize(this.size(), ItemStack.EMPTY);
        Inventories.fromTag(tag, this.inventory);
        this.burnTime = tag.getShort("BurnTime");
        this.cookTime = tag.getShort("CookTime");
        this.cookTimeTotal = tag.getShort("CookTimeTotal");
        this.fuelTime = this.getFuelTime(this.inventory.get(1));
        CompoundTag compoundTag = tag.getCompound("RecipesUsed");
        for (String string : compoundTag.getKeys()) {
            this.recipesUsed.put((Object)new Identifier(string), compoundTag.getInt(string));
        }
    }

    @Override
    public CompoundTag toTag(CompoundTag tag) {
        super.toTag(tag);
        tag.putShort("BurnTime", (short)this.burnTime);
        tag.putShort("CookTime", (short)this.cookTime);
        tag.putShort("CookTimeTotal", (short)this.cookTimeTotal);
        Inventories.toTag(tag, this.inventory);
        CompoundTag compoundTag = new CompoundTag();
        this.recipesUsed.forEach((identifier, n) -> compoundTag.putInt(identifier.toString(), (int)n));
        tag.put("RecipesUsed", compoundTag);
        return tag;
    }

    @Override
    public void tick() {
        boolean bl;
        boolean bl2 = this.isBurning();
        bl = false;
        if (this.isBurning()) {
            --this.burnTime;
        }
        if (!this.world.isClient) {
            ItemStack itemStack = this.inventory.get(1);
            if (this.isBurning() || !itemStack.isEmpty() && !this.inventory.get(0).isEmpty()) {
                Recipe recipe = this.world.getRecipeManager().getFirstMatch(this.recipeType, this, this.world).orElse(null);
                if (!this.isBurning() && this.canAcceptRecipeOutput(recipe)) {
                    this.fuelTime = this.burnTime = this.getFuelTime(itemStack);
                    if (this.isBurning()) {
                        bl = true;
                        if (!itemStack.isEmpty()) {
                            Item item = itemStack.getItem();
                            itemStack.decrement(1);
                            if (itemStack.isEmpty()) {
                                _snowman = item.getRecipeRemainder();
                                this.inventory.set(1, _snowman == null ? ItemStack.EMPTY : new ItemStack(_snowman));
                            }
                        }
                    }
                }
                if (this.isBurning() && this.canAcceptRecipeOutput(recipe)) {
                    ++this.cookTime;
                    if (this.cookTime == this.cookTimeTotal) {
                        this.cookTime = 0;
                        this.cookTimeTotal = this.getCookTime();
                        this.craftRecipe(recipe);
                        bl = true;
                    }
                } else {
                    this.cookTime = 0;
                }
            } else if (!this.isBurning() && this.cookTime > 0) {
                this.cookTime = MathHelper.clamp(this.cookTime - 2, 0, this.cookTimeTotal);
            }
            if (bl2 != this.isBurning()) {
                bl = true;
                this.world.setBlockState(this.pos, (BlockState)this.world.getBlockState(this.pos).with(AbstractFurnaceBlock.LIT, this.isBurning()), 3);
            }
        }
        if (bl) {
            this.markDirty();
        }
    }

    protected boolean canAcceptRecipeOutput(@Nullable Recipe<?> recipe) {
        if (this.inventory.get(0).isEmpty() || recipe == null) {
            return false;
        }
        ItemStack itemStack = recipe.getOutput();
        if (itemStack.isEmpty()) {
            return false;
        }
        _snowman = this.inventory.get(2);
        if (_snowman.isEmpty()) {
            return true;
        }
        if (!_snowman.isItemEqualIgnoreDamage(itemStack)) {
            return false;
        }
        if (_snowman.getCount() < this.getMaxCountPerStack() && _snowman.getCount() < _snowman.getMaxCount()) {
            return true;
        }
        return _snowman.getCount() < itemStack.getMaxCount();
    }

    private void craftRecipe(@Nullable Recipe<?> recipe) {
        if (recipe == null || !this.canAcceptRecipeOutput(recipe)) {
            return;
        }
        ItemStack itemStack = this.inventory.get(0);
        _snowman = recipe.getOutput();
        _snowman = this.inventory.get(2);
        if (_snowman.isEmpty()) {
            this.inventory.set(2, _snowman.copy());
        } else if (_snowman.getItem() == _snowman.getItem()) {
            _snowman.increment(1);
        }
        if (!this.world.isClient) {
            this.setLastRecipe(recipe);
        }
        if (itemStack.getItem() == Blocks.WET_SPONGE.asItem() && !this.inventory.get(1).isEmpty() && this.inventory.get(1).getItem() == Items.BUCKET) {
            this.inventory.set(1, new ItemStack(Items.WATER_BUCKET));
        }
        itemStack.decrement(1);
    }

    protected int getFuelTime(ItemStack fuel) {
        if (fuel.isEmpty()) {
            return 0;
        }
        Item item = fuel.getItem();
        return AbstractFurnaceBlockEntity.createFuelTimeMap().getOrDefault(item, 0);
    }

    protected int getCookTime() {
        return this.world.getRecipeManager().getFirstMatch(this.recipeType, this, this.world).map(AbstractCookingRecipe::getCookTime).orElse(200);
    }

    public static boolean canUseAsFuel(ItemStack stack) {
        return AbstractFurnaceBlockEntity.createFuelTimeMap().containsKey(stack.getItem());
    }

    @Override
    public int[] getAvailableSlots(Direction side) {
        if (side == Direction.DOWN) {
            return BOTTOM_SLOTS;
        }
        if (side == Direction.UP) {
            return TOP_SLOTS;
        }
        return SIDE_SLOTS;
    }

    @Override
    public boolean canInsert(int slot, ItemStack stack, @Nullable Direction dir) {
        return this.isValid(slot, stack);
    }

    @Override
    public boolean canExtract(int slot, ItemStack stack, Direction dir) {
        Item item;
        return dir != Direction.DOWN || slot != 1 || (item = stack.getItem()) == Items.WATER_BUCKET || item == Items.BUCKET;
    }

    @Override
    public int size() {
        return this.inventory.size();
    }

    @Override
    public boolean isEmpty() {
        for (ItemStack itemStack : this.inventory) {
            if (itemStack.isEmpty()) continue;
            return false;
        }
        return true;
    }

    @Override
    public ItemStack getStack(int slot) {
        return this.inventory.get(slot);
    }

    @Override
    public ItemStack removeStack(int slot, int amount) {
        return Inventories.splitStack(this.inventory, slot, amount);
    }

    @Override
    public ItemStack removeStack(int slot) {
        return Inventories.removeStack(this.inventory, slot);
    }

    @Override
    public void setStack(int slot, ItemStack stack) {
        ItemStack itemStack = this.inventory.get(slot);
        boolean _snowman2 = !stack.isEmpty() && stack.isItemEqualIgnoreDamage(itemStack) && ItemStack.areTagsEqual(stack, itemStack);
        this.inventory.set(slot, stack);
        if (stack.getCount() > this.getMaxCountPerStack()) {
            stack.setCount(this.getMaxCountPerStack());
        }
        if (slot == 0 && !_snowman2) {
            this.cookTimeTotal = this.getCookTime();
            this.cookTime = 0;
            this.markDirty();
        }
    }

    @Override
    public boolean canPlayerUse(PlayerEntity player) {
        if (this.world.getBlockEntity(this.pos) != this) {
            return false;
        }
        return player.squaredDistanceTo((double)this.pos.getX() + 0.5, (double)this.pos.getY() + 0.5, (double)this.pos.getZ() + 0.5) <= 64.0;
    }

    @Override
    public boolean isValid(int slot, ItemStack stack) {
        if (slot == 2) {
            return false;
        }
        if (slot == 1) {
            ItemStack itemStack = this.inventory.get(1);
            return AbstractFurnaceBlockEntity.canUseAsFuel(stack) || stack.getItem() == Items.BUCKET && itemStack.getItem() != Items.BUCKET;
        }
        return true;
    }

    @Override
    public void clear() {
        this.inventory.clear();
    }

    @Override
    public void setLastRecipe(@Nullable Recipe<?> recipe) {
        if (recipe != null) {
            Identifier identifier = recipe.getId();
            this.recipesUsed.addTo((Object)identifier, 1);
        }
    }

    @Override
    @Nullable
    public Recipe<?> getLastRecipe() {
        return null;
    }

    @Override
    public void unlockLastRecipe(PlayerEntity player) {
    }

    public void dropExperience(PlayerEntity player) {
        List<Recipe<?>> list = this.method_27354(player.world, player.getPos());
        player.unlockRecipes(list);
        this.recipesUsed.clear();
    }

    public List<Recipe<?>> method_27354(World world, Vec3d vec3d) {
        ArrayList arrayList = Lists.newArrayList();
        for (Object2IntMap.Entry entry : this.recipesUsed.object2IntEntrySet()) {
            world.getRecipeManager().get((Identifier)entry.getKey()).ifPresent(recipe -> {
                arrayList.add(recipe);
                AbstractFurnaceBlockEntity.dropExperience(world, vec3d, entry.getIntValue(), ((AbstractCookingRecipe)recipe).getExperience());
            });
        }
        return arrayList;
    }

    private static void dropExperience(World world, Vec3d vec3d, int n, float f) {
        int n2 = MathHelper.floor((float)n * f);
        float _snowman2 = MathHelper.fractionalPart((float)n * f);
        if (_snowman2 != 0.0f && Math.random() < (double)_snowman2) {
            ++n2;
        }
        while (n2 > 0) {
            _snowman = ExperienceOrbEntity.roundToOrbSize(n2);
            n2 -= _snowman;
            world.spawnEntity(new ExperienceOrbEntity(world, vec3d.x, vec3d.y, vec3d.z, _snowman));
        }
    }

    @Override
    public void provideRecipeInputs(RecipeFinder finder) {
        for (ItemStack itemStack : this.inventory) {
            finder.addItem(itemStack);
        }
    }

    static /* synthetic */ int method_17479(AbstractFurnaceBlockEntity abstractFurnaceBlockEntity) {
        return abstractFurnaceBlockEntity.burnTime;
    }

    static /* synthetic */ int method_17481(AbstractFurnaceBlockEntity abstractFurnaceBlockEntity) {
        return abstractFurnaceBlockEntity.fuelTime;
    }

    static /* synthetic */ int method_17483(AbstractFurnaceBlockEntity abstractFurnaceBlockEntity) {
        return abstractFurnaceBlockEntity.cookTime;
    }

    static /* synthetic */ int method_17485(AbstractFurnaceBlockEntity abstractFurnaceBlockEntity) {
        return abstractFurnaceBlockEntity.cookTimeTotal;
    }

    static /* synthetic */ int method_17480(AbstractFurnaceBlockEntity abstractFurnaceBlockEntity, int n) {
        abstractFurnaceBlockEntity.burnTime = n;
        return abstractFurnaceBlockEntity.burnTime;
    }

    static /* synthetic */ int method_17482(AbstractFurnaceBlockEntity abstractFurnaceBlockEntity, int n) {
        abstractFurnaceBlockEntity.fuelTime = n;
        return abstractFurnaceBlockEntity.fuelTime;
    }

    static /* synthetic */ int method_17484(AbstractFurnaceBlockEntity abstractFurnaceBlockEntity, int n) {
        abstractFurnaceBlockEntity.cookTime = n;
        return abstractFurnaceBlockEntity.cookTime;
    }

    static /* synthetic */ int method_17486(AbstractFurnaceBlockEntity abstractFurnaceBlockEntity, int n) {
        abstractFurnaceBlockEntity.cookTimeTotal = n;
        return abstractFurnaceBlockEntity.cookTimeTotal;
    }
}

