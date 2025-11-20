package net.minecraft.block.entity;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import it.unimi.dsi.fastutil.objects.Object2IntOpenHashMap;
import it.unimi.dsi.fastutil.objects.ObjectIterator;
import it.unimi.dsi.fastutil.objects.Object2IntMap.Entry;
import java.util.List;
import java.util.Map;
import javax.annotation.Nullable;
import net.minecraft.SharedConstants;
import net.minecraft.block.AbstractFurnaceBlock;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
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

public abstract class AbstractFurnaceBlockEntity extends LockableContainerBlockEntity implements SidedInventory, RecipeUnlocker, RecipeInputProvider, Tickable {
   private static final int[] TOP_SLOTS = new int[]{0};
   private static final int[] BOTTOM_SLOTS = new int[]{2, 1};
   private static final int[] SIDE_SLOTS = new int[]{1};
   protected DefaultedList<ItemStack> inventory = DefaultedList.ofSize(3, ItemStack.EMPTY);
   private int burnTime;
   private int fuelTime;
   private int cookTime;
   private int cookTimeTotal;
   protected final PropertyDelegate propertyDelegate = new PropertyDelegate() {
      @Override
      public int get(int index) {
         switch (index) {
            case 0:
               return AbstractFurnaceBlockEntity.this.burnTime;
            case 1:
               return AbstractFurnaceBlockEntity.this.fuelTime;
            case 2:
               return AbstractFurnaceBlockEntity.this.cookTime;
            case 3:
               return AbstractFurnaceBlockEntity.this.cookTimeTotal;
            default:
               return 0;
         }
      }

      @Override
      public void set(int index, int value) {
         switch (index) {
            case 0:
               AbstractFurnaceBlockEntity.this.burnTime = value;
               break;
            case 1:
               AbstractFurnaceBlockEntity.this.fuelTime = value;
               break;
            case 2:
               AbstractFurnaceBlockEntity.this.cookTime = value;
               break;
            case 3:
               AbstractFurnaceBlockEntity.this.cookTimeTotal = value;
         }
      }

      @Override
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
      Map<Item, Integer> _snowman = Maps.newLinkedHashMap();
      addFuel(_snowman, Items.LAVA_BUCKET, 20000);
      addFuel(_snowman, Blocks.COAL_BLOCK, 16000);
      addFuel(_snowman, Items.BLAZE_ROD, 2400);
      addFuel(_snowman, Items.COAL, 1600);
      addFuel(_snowman, Items.CHARCOAL, 1600);
      addFuel(_snowman, ItemTags.LOGS, 300);
      addFuel(_snowman, ItemTags.PLANKS, 300);
      addFuel(_snowman, ItemTags.WOODEN_STAIRS, 300);
      addFuel(_snowman, ItemTags.WOODEN_SLABS, 150);
      addFuel(_snowman, ItemTags.WOODEN_TRAPDOORS, 300);
      addFuel(_snowman, ItemTags.WOODEN_PRESSURE_PLATES, 300);
      addFuel(_snowman, Blocks.OAK_FENCE, 300);
      addFuel(_snowman, Blocks.BIRCH_FENCE, 300);
      addFuel(_snowman, Blocks.SPRUCE_FENCE, 300);
      addFuel(_snowman, Blocks.JUNGLE_FENCE, 300);
      addFuel(_snowman, Blocks.DARK_OAK_FENCE, 300);
      addFuel(_snowman, Blocks.ACACIA_FENCE, 300);
      addFuel(_snowman, Blocks.OAK_FENCE_GATE, 300);
      addFuel(_snowman, Blocks.BIRCH_FENCE_GATE, 300);
      addFuel(_snowman, Blocks.SPRUCE_FENCE_GATE, 300);
      addFuel(_snowman, Blocks.JUNGLE_FENCE_GATE, 300);
      addFuel(_snowman, Blocks.DARK_OAK_FENCE_GATE, 300);
      addFuel(_snowman, Blocks.ACACIA_FENCE_GATE, 300);
      addFuel(_snowman, Blocks.NOTE_BLOCK, 300);
      addFuel(_snowman, Blocks.BOOKSHELF, 300);
      addFuel(_snowman, Blocks.LECTERN, 300);
      addFuel(_snowman, Blocks.JUKEBOX, 300);
      addFuel(_snowman, Blocks.CHEST, 300);
      addFuel(_snowman, Blocks.TRAPPED_CHEST, 300);
      addFuel(_snowman, Blocks.CRAFTING_TABLE, 300);
      addFuel(_snowman, Blocks.DAYLIGHT_DETECTOR, 300);
      addFuel(_snowman, ItemTags.BANNERS, 300);
      addFuel(_snowman, Items.BOW, 300);
      addFuel(_snowman, Items.FISHING_ROD, 300);
      addFuel(_snowman, Blocks.LADDER, 300);
      addFuel(_snowman, ItemTags.SIGNS, 200);
      addFuel(_snowman, Items.WOODEN_SHOVEL, 200);
      addFuel(_snowman, Items.WOODEN_SWORD, 200);
      addFuel(_snowman, Items.WOODEN_HOE, 200);
      addFuel(_snowman, Items.WOODEN_AXE, 200);
      addFuel(_snowman, Items.WOODEN_PICKAXE, 200);
      addFuel(_snowman, ItemTags.WOODEN_DOORS, 200);
      addFuel(_snowman, ItemTags.BOATS, 1200);
      addFuel(_snowman, ItemTags.WOOL, 100);
      addFuel(_snowman, ItemTags.WOODEN_BUTTONS, 100);
      addFuel(_snowman, Items.STICK, 100);
      addFuel(_snowman, ItemTags.SAPLINGS, 100);
      addFuel(_snowman, Items.BOWL, 100);
      addFuel(_snowman, ItemTags.CARPETS, 67);
      addFuel(_snowman, Blocks.DRIED_KELP_BLOCK, 4001);
      addFuel(_snowman, Items.CROSSBOW, 300);
      addFuel(_snowman, Blocks.BAMBOO, 50);
      addFuel(_snowman, Blocks.DEAD_BUSH, 100);
      addFuel(_snowman, Blocks.SCAFFOLDING, 400);
      addFuel(_snowman, Blocks.LOOM, 300);
      addFuel(_snowman, Blocks.BARREL, 300);
      addFuel(_snowman, Blocks.CARTOGRAPHY_TABLE, 300);
      addFuel(_snowman, Blocks.FLETCHING_TABLE, 300);
      addFuel(_snowman, Blocks.SMITHING_TABLE, 300);
      addFuel(_snowman, Blocks.COMPOSTER, 300);
      return _snowman;
   }

   private static boolean isNonFlammableWood(Item item) {
      return ItemTags.NON_FLAMMABLE_WOOD.contains(item);
   }

   private static void addFuel(Map<Item, Integer> fuelTimes, Tag<Item> tag, int fuelTime) {
      for (Item _snowman : tag.values()) {
         if (!isNonFlammableWood(_snowman)) {
            fuelTimes.put(_snowman, fuelTime);
         }
      }
   }

   private static void addFuel(Map<Item, Integer> _snowman, ItemConvertible item, int fuelTime) {
      Item _snowmanx = item.asItem();
      if (isNonFlammableWood(_snowmanx)) {
         if (SharedConstants.isDevelopment) {
            throw (IllegalStateException)Util.throwOrPause(
               new IllegalStateException(
                  "A developer tried to explicitly make fire resistant item " + _snowmanx.getName(null).getString() + " a furnace fuel. That will not work!"
               )
            );
         }
      } else {
         _snowman.put(_snowmanx, fuelTime);
      }
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
      CompoundTag _snowman = tag.getCompound("RecipesUsed");

      for (String _snowmanx : _snowman.getKeys()) {
         this.recipesUsed.put(new Identifier(_snowmanx), _snowman.getInt(_snowmanx));
      }
   }

   @Override
   public CompoundTag toTag(CompoundTag tag) {
      super.toTag(tag);
      tag.putShort("BurnTime", (short)this.burnTime);
      tag.putShort("CookTime", (short)this.cookTime);
      tag.putShort("CookTimeTotal", (short)this.cookTimeTotal);
      Inventories.toTag(tag, this.inventory);
      CompoundTag _snowman = new CompoundTag();
      this.recipesUsed.forEach((_snowmanx, _snowmanxx) -> _snowman.putInt(_snowmanx.toString(), _snowmanxx));
      tag.put("RecipesUsed", _snowman);
      return tag;
   }

   @Override
   public void tick() {
      boolean _snowman = this.isBurning();
      boolean _snowmanx = false;
      if (this.isBurning()) {
         this.burnTime--;
      }

      if (!this.world.isClient) {
         ItemStack _snowmanxx = this.inventory.get(1);
         if (this.isBurning() || !_snowmanxx.isEmpty() && !this.inventory.get(0).isEmpty()) {
            Recipe<?> _snowmanxxx = (Recipe<?>)this.world.getRecipeManager().getFirstMatch(this.recipeType, this, this.world).orElse(null);
            if (!this.isBurning() && this.canAcceptRecipeOutput(_snowmanxxx)) {
               this.burnTime = this.getFuelTime(_snowmanxx);
               this.fuelTime = this.burnTime;
               if (this.isBurning()) {
                  _snowmanx = true;
                  if (!_snowmanxx.isEmpty()) {
                     Item _snowmanxxxx = _snowmanxx.getItem();
                     _snowmanxx.decrement(1);
                     if (_snowmanxx.isEmpty()) {
                        Item _snowmanxxxxx = _snowmanxxxx.getRecipeRemainder();
                        this.inventory.set(1, _snowmanxxxxx == null ? ItemStack.EMPTY : new ItemStack(_snowmanxxxxx));
                     }
                  }
               }
            }

            if (this.isBurning() && this.canAcceptRecipeOutput(_snowmanxxx)) {
               this.cookTime++;
               if (this.cookTime == this.cookTimeTotal) {
                  this.cookTime = 0;
                  this.cookTimeTotal = this.getCookTime();
                  this.craftRecipe(_snowmanxxx);
                  _snowmanx = true;
               }
            } else {
               this.cookTime = 0;
            }
         } else if (!this.isBurning() && this.cookTime > 0) {
            this.cookTime = MathHelper.clamp(this.cookTime - 2, 0, this.cookTimeTotal);
         }

         if (_snowman != this.isBurning()) {
            _snowmanx = true;
            this.world.setBlockState(this.pos, this.world.getBlockState(this.pos).with(AbstractFurnaceBlock.LIT, Boolean.valueOf(this.isBurning())), 3);
         }
      }

      if (_snowmanx) {
         this.markDirty();
      }
   }

   protected boolean canAcceptRecipeOutput(@Nullable Recipe<?> recipe) {
      if (!this.inventory.get(0).isEmpty() && recipe != null) {
         ItemStack _snowman = recipe.getOutput();
         if (_snowman.isEmpty()) {
            return false;
         } else {
            ItemStack _snowmanx = this.inventory.get(2);
            if (_snowmanx.isEmpty()) {
               return true;
            } else if (!_snowmanx.isItemEqualIgnoreDamage(_snowman)) {
               return false;
            } else {
               return _snowmanx.getCount() < this.getMaxCountPerStack() && _snowmanx.getCount() < _snowmanx.getMaxCount() ? true : _snowmanx.getCount() < _snowman.getMaxCount();
            }
         }
      } else {
         return false;
      }
   }

   private void craftRecipe(@Nullable Recipe<?> recipe) {
      if (recipe != null && this.canAcceptRecipeOutput(recipe)) {
         ItemStack _snowman = this.inventory.get(0);
         ItemStack _snowmanx = recipe.getOutput();
         ItemStack _snowmanxx = this.inventory.get(2);
         if (_snowmanxx.isEmpty()) {
            this.inventory.set(2, _snowmanx.copy());
         } else if (_snowmanxx.getItem() == _snowmanx.getItem()) {
            _snowmanxx.increment(1);
         }

         if (!this.world.isClient) {
            this.setLastRecipe(recipe);
         }

         if (_snowman.getItem() == Blocks.WET_SPONGE.asItem() && !this.inventory.get(1).isEmpty() && this.inventory.get(1).getItem() == Items.BUCKET) {
            this.inventory.set(1, new ItemStack(Items.WATER_BUCKET));
         }

         _snowman.decrement(1);
      }
   }

   protected int getFuelTime(ItemStack fuel) {
      if (fuel.isEmpty()) {
         return 0;
      } else {
         Item _snowman = fuel.getItem();
         return createFuelTimeMap().getOrDefault(_snowman, 0);
      }
   }

   protected int getCookTime() {
      return this.world.getRecipeManager().getFirstMatch(this.recipeType, this, this.world).map(AbstractCookingRecipe::getCookTime).orElse(200);
   }

   public static boolean canUseAsFuel(ItemStack stack) {
      return createFuelTimeMap().containsKey(stack.getItem());
   }

   @Override
   public int[] getAvailableSlots(Direction side) {
      if (side == Direction.DOWN) {
         return BOTTOM_SLOTS;
      } else {
         return side == Direction.UP ? TOP_SLOTS : SIDE_SLOTS;
      }
   }

   @Override
   public boolean canInsert(int slot, ItemStack stack, @Nullable Direction dir) {
      return this.isValid(slot, stack);
   }

   @Override
   public boolean canExtract(int slot, ItemStack stack, Direction dir) {
      if (dir == Direction.DOWN && slot == 1) {
         Item _snowman = stack.getItem();
         if (_snowman != Items.WATER_BUCKET && _snowman != Items.BUCKET) {
            return false;
         }
      }

      return true;
   }

   @Override
   public int size() {
      return this.inventory.size();
   }

   @Override
   public boolean isEmpty() {
      for (ItemStack _snowman : this.inventory) {
         if (!_snowman.isEmpty()) {
            return false;
         }
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
      ItemStack _snowman = this.inventory.get(slot);
      boolean _snowmanx = !stack.isEmpty() && stack.isItemEqualIgnoreDamage(_snowman) && ItemStack.areTagsEqual(stack, _snowman);
      this.inventory.set(slot, stack);
      if (stack.getCount() > this.getMaxCountPerStack()) {
         stack.setCount(this.getMaxCountPerStack());
      }

      if (slot == 0 && !_snowmanx) {
         this.cookTimeTotal = this.getCookTime();
         this.cookTime = 0;
         this.markDirty();
      }
   }

   @Override
   public boolean canPlayerUse(PlayerEntity player) {
      return this.world.getBlockEntity(this.pos) != this
         ? false
         : player.squaredDistanceTo((double)this.pos.getX() + 0.5, (double)this.pos.getY() + 0.5, (double)this.pos.getZ() + 0.5) <= 64.0;
   }

   @Override
   public boolean isValid(int slot, ItemStack stack) {
      if (slot == 2) {
         return false;
      } else if (slot != 1) {
         return true;
      } else {
         ItemStack _snowman = this.inventory.get(1);
         return canUseAsFuel(stack) || stack.getItem() == Items.BUCKET && _snowman.getItem() != Items.BUCKET;
      }
   }

   @Override
   public void clear() {
      this.inventory.clear();
   }

   @Override
   public void setLastRecipe(@Nullable Recipe<?> recipe) {
      if (recipe != null) {
         Identifier _snowman = recipe.getId();
         this.recipesUsed.addTo(_snowman, 1);
      }
   }

   @Nullable
   @Override
   public Recipe<?> getLastRecipe() {
      return null;
   }

   @Override
   public void unlockLastRecipe(PlayerEntity player) {
   }

   public void dropExperience(PlayerEntity player) {
      List<Recipe<?>> _snowman = this.method_27354(player.world, player.getPos());
      player.unlockRecipes(_snowman);
      this.recipesUsed.clear();
   }

   public List<Recipe<?>> method_27354(World _snowman, Vec3d _snowman) {
      List<Recipe<?>> _snowmanxx = Lists.newArrayList();
      ObjectIterator var4 = this.recipesUsed.object2IntEntrySet().iterator();

      while (var4.hasNext()) {
         Entry<Identifier> _snowmanxxx = (Entry<Identifier>)var4.next();
         _snowman.getRecipeManager().get((Identifier)_snowmanxxx.getKey()).ifPresent(_snowmanxxxx -> {
            _snowman.add(_snowmanxxxx);
            dropExperience(_snowman, _snowman, _snowman.getIntValue(), ((AbstractCookingRecipe)_snowmanxxxx).getExperience());
         });
      }

      return _snowmanxx;
   }

   private static void dropExperience(World _snowman, Vec3d _snowman, int _snowman, float _snowman) {
      int _snowmanxxxx = MathHelper.floor((float)_snowman * _snowman);
      float _snowmanxxxxx = MathHelper.fractionalPart((float)_snowman * _snowman);
      if (_snowmanxxxxx != 0.0F && Math.random() < (double)_snowmanxxxxx) {
         _snowmanxxxx++;
      }

      while (_snowmanxxxx > 0) {
         int _snowmanxxxxxx = ExperienceOrbEntity.roundToOrbSize(_snowmanxxxx);
         _snowmanxxxx -= _snowmanxxxxxx;
         _snowman.spawnEntity(new ExperienceOrbEntity(_snowman, _snowman.x, _snowman.y, _snowman.z, _snowmanxxxxxx));
      }
   }

   @Override
   public void provideRecipeInputs(RecipeFinder finder) {
      for (ItemStack _snowman : this.inventory) {
         finder.addItem(_snowman);
      }
   }
}
