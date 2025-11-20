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
      Map<Item, Integer> map = Maps.newLinkedHashMap();
      addFuel(map, Items.LAVA_BUCKET, 20000);
      addFuel(map, Blocks.COAL_BLOCK, 16000);
      addFuel(map, Items.BLAZE_ROD, 2400);
      addFuel(map, Items.COAL, 1600);
      addFuel(map, Items.CHARCOAL, 1600);
      addFuel(map, ItemTags.LOGS, 300);
      addFuel(map, ItemTags.PLANKS, 300);
      addFuel(map, ItemTags.WOODEN_STAIRS, 300);
      addFuel(map, ItemTags.WOODEN_SLABS, 150);
      addFuel(map, ItemTags.WOODEN_TRAPDOORS, 300);
      addFuel(map, ItemTags.WOODEN_PRESSURE_PLATES, 300);
      addFuel(map, Blocks.OAK_FENCE, 300);
      addFuel(map, Blocks.BIRCH_FENCE, 300);
      addFuel(map, Blocks.SPRUCE_FENCE, 300);
      addFuel(map, Blocks.JUNGLE_FENCE, 300);
      addFuel(map, Blocks.DARK_OAK_FENCE, 300);
      addFuel(map, Blocks.ACACIA_FENCE, 300);
      addFuel(map, Blocks.OAK_FENCE_GATE, 300);
      addFuel(map, Blocks.BIRCH_FENCE_GATE, 300);
      addFuel(map, Blocks.SPRUCE_FENCE_GATE, 300);
      addFuel(map, Blocks.JUNGLE_FENCE_GATE, 300);
      addFuel(map, Blocks.DARK_OAK_FENCE_GATE, 300);
      addFuel(map, Blocks.ACACIA_FENCE_GATE, 300);
      addFuel(map, Blocks.NOTE_BLOCK, 300);
      addFuel(map, Blocks.BOOKSHELF, 300);
      addFuel(map, Blocks.LECTERN, 300);
      addFuel(map, Blocks.JUKEBOX, 300);
      addFuel(map, Blocks.CHEST, 300);
      addFuel(map, Blocks.TRAPPED_CHEST, 300);
      addFuel(map, Blocks.CRAFTING_TABLE, 300);
      addFuel(map, Blocks.DAYLIGHT_DETECTOR, 300);
      addFuel(map, ItemTags.BANNERS, 300);
      addFuel(map, Items.BOW, 300);
      addFuel(map, Items.FISHING_ROD, 300);
      addFuel(map, Blocks.LADDER, 300);
      addFuel(map, ItemTags.SIGNS, 200);
      addFuel(map, Items.WOODEN_SHOVEL, 200);
      addFuel(map, Items.WOODEN_SWORD, 200);
      addFuel(map, Items.WOODEN_HOE, 200);
      addFuel(map, Items.WOODEN_AXE, 200);
      addFuel(map, Items.WOODEN_PICKAXE, 200);
      addFuel(map, ItemTags.WOODEN_DOORS, 200);
      addFuel(map, ItemTags.BOATS, 1200);
      addFuel(map, ItemTags.WOOL, 100);
      addFuel(map, ItemTags.WOODEN_BUTTONS, 100);
      addFuel(map, Items.STICK, 100);
      addFuel(map, ItemTags.SAPLINGS, 100);
      addFuel(map, Items.BOWL, 100);
      addFuel(map, ItemTags.CARPETS, 67);
      addFuel(map, Blocks.DRIED_KELP_BLOCK, 4001);
      addFuel(map, Items.CROSSBOW, 300);
      addFuel(map, Blocks.BAMBOO, 50);
      addFuel(map, Blocks.DEAD_BUSH, 100);
      addFuel(map, Blocks.SCAFFOLDING, 400);
      addFuel(map, Blocks.LOOM, 300);
      addFuel(map, Blocks.BARREL, 300);
      addFuel(map, Blocks.CARTOGRAPHY_TABLE, 300);
      addFuel(map, Blocks.FLETCHING_TABLE, 300);
      addFuel(map, Blocks.SMITHING_TABLE, 300);
      addFuel(map, Blocks.COMPOSTER, 300);
      return map;
   }

   private static boolean isNonFlammableWood(Item item) {
      return ItemTags.NON_FLAMMABLE_WOOD.contains(item);
   }

   private static void addFuel(Map<Item, Integer> fuelTimes, Tag<Item> tag, int fuelTime) {
      for (Item lv : tag.values()) {
         if (!isNonFlammableWood(lv)) {
            fuelTimes.put(lv, fuelTime);
         }
      }
   }

   private static void addFuel(Map<Item, Integer> map, ItemConvertible item, int fuelTime) {
      Item lv = item.asItem();
      if (isNonFlammableWood(lv)) {
         if (SharedConstants.isDevelopment) {
            throw (IllegalStateException)Util.throwOrPause(
               new IllegalStateException(
                  "A developer tried to explicitly make fire resistant item " + lv.getName(null).getString() + " a furnace fuel. That will not work!"
               )
            );
         }
      } else {
         map.put(lv, fuelTime);
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
      CompoundTag lv = tag.getCompound("RecipesUsed");

      for (String string : lv.getKeys()) {
         this.recipesUsed.put(new Identifier(string), lv.getInt(string));
      }
   }

   @Override
   public CompoundTag toTag(CompoundTag tag) {
      super.toTag(tag);
      tag.putShort("BurnTime", (short)this.burnTime);
      tag.putShort("CookTime", (short)this.cookTime);
      tag.putShort("CookTimeTotal", (short)this.cookTimeTotal);
      Inventories.toTag(tag, this.inventory);
      CompoundTag lv = new CompoundTag();
      this.recipesUsed.forEach((arg2, integer) -> lv.putInt(arg2.toString(), integer));
      tag.put("RecipesUsed", lv);
      return tag;
   }

   @Override
   public void tick() {
      boolean bl = this.isBurning();
      boolean bl2 = false;
      if (this.isBurning()) {
         this.burnTime--;
      }

      if (!this.world.isClient) {
         ItemStack lv = this.inventory.get(1);
         if (this.isBurning() || !lv.isEmpty() && !this.inventory.get(0).isEmpty()) {
            Recipe<?> lv2 = (Recipe<?>)this.world.getRecipeManager().getFirstMatch(this.recipeType, this, this.world).orElse(null);
            if (!this.isBurning() && this.canAcceptRecipeOutput(lv2)) {
               this.burnTime = this.getFuelTime(lv);
               this.fuelTime = this.burnTime;
               if (this.isBurning()) {
                  bl2 = true;
                  if (!lv.isEmpty()) {
                     Item lv3 = lv.getItem();
                     lv.decrement(1);
                     if (lv.isEmpty()) {
                        Item lv4 = lv3.getRecipeRemainder();
                        this.inventory.set(1, lv4 == null ? ItemStack.EMPTY : new ItemStack(lv4));
                     }
                  }
               }
            }

            if (this.isBurning() && this.canAcceptRecipeOutput(lv2)) {
               this.cookTime++;
               if (this.cookTime == this.cookTimeTotal) {
                  this.cookTime = 0;
                  this.cookTimeTotal = this.getCookTime();
                  this.craftRecipe(lv2);
                  bl2 = true;
               }
            } else {
               this.cookTime = 0;
            }
         } else if (!this.isBurning() && this.cookTime > 0) {
            this.cookTime = MathHelper.clamp(this.cookTime - 2, 0, this.cookTimeTotal);
         }

         if (bl != this.isBurning()) {
            bl2 = true;
            this.world.setBlockState(this.pos, this.world.getBlockState(this.pos).with(AbstractFurnaceBlock.LIT, Boolean.valueOf(this.isBurning())), 3);
         }
      }

      if (bl2) {
         this.markDirty();
      }
   }

   protected boolean canAcceptRecipeOutput(@Nullable Recipe<?> recipe) {
      if (!this.inventory.get(0).isEmpty() && recipe != null) {
         ItemStack lv = recipe.getOutput();
         if (lv.isEmpty()) {
            return false;
         } else {
            ItemStack lv2 = this.inventory.get(2);
            if (lv2.isEmpty()) {
               return true;
            } else if (!lv2.isItemEqualIgnoreDamage(lv)) {
               return false;
            } else {
               return lv2.getCount() < this.getMaxCountPerStack() && lv2.getCount() < lv2.getMaxCount() ? true : lv2.getCount() < lv.getMaxCount();
            }
         }
      } else {
         return false;
      }
   }

   private void craftRecipe(@Nullable Recipe<?> recipe) {
      if (recipe != null && this.canAcceptRecipeOutput(recipe)) {
         ItemStack lv = this.inventory.get(0);
         ItemStack lv2 = recipe.getOutput();
         ItemStack lv3 = this.inventory.get(2);
         if (lv3.isEmpty()) {
            this.inventory.set(2, lv2.copy());
         } else if (lv3.getItem() == lv2.getItem()) {
            lv3.increment(1);
         }

         if (!this.world.isClient) {
            this.setLastRecipe(recipe);
         }

         if (lv.getItem() == Blocks.WET_SPONGE.asItem() && !this.inventory.get(1).isEmpty() && this.inventory.get(1).getItem() == Items.BUCKET) {
            this.inventory.set(1, new ItemStack(Items.WATER_BUCKET));
         }

         lv.decrement(1);
      }
   }

   protected int getFuelTime(ItemStack fuel) {
      if (fuel.isEmpty()) {
         return 0;
      } else {
         Item lv = fuel.getItem();
         return createFuelTimeMap().getOrDefault(lv, 0);
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
         Item lv = stack.getItem();
         if (lv != Items.WATER_BUCKET && lv != Items.BUCKET) {
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
      for (ItemStack lv : this.inventory) {
         if (!lv.isEmpty()) {
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
      ItemStack lv = this.inventory.get(slot);
      boolean bl = !stack.isEmpty() && stack.isItemEqualIgnoreDamage(lv) && ItemStack.areTagsEqual(stack, lv);
      this.inventory.set(slot, stack);
      if (stack.getCount() > this.getMaxCountPerStack()) {
         stack.setCount(this.getMaxCountPerStack());
      }

      if (slot == 0 && !bl) {
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
         ItemStack lv = this.inventory.get(1);
         return canUseAsFuel(stack) || stack.getItem() == Items.BUCKET && lv.getItem() != Items.BUCKET;
      }
   }

   @Override
   public void clear() {
      this.inventory.clear();
   }

   @Override
   public void setLastRecipe(@Nullable Recipe<?> recipe) {
      if (recipe != null) {
         Identifier lv = recipe.getId();
         this.recipesUsed.addTo(lv, 1);
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
      List<Recipe<?>> list = this.method_27354(player.world, player.getPos());
      player.unlockRecipes(list);
      this.recipesUsed.clear();
   }

   public List<Recipe<?>> method_27354(World arg, Vec3d arg2) {
      List<Recipe<?>> list = Lists.newArrayList();
      ObjectIterator var4 = this.recipesUsed.object2IntEntrySet().iterator();

      while (var4.hasNext()) {
         Entry<Identifier> entry = (Entry<Identifier>)var4.next();
         arg.getRecipeManager().get((Identifier)entry.getKey()).ifPresent(arg3 -> {
            list.add((Recipe<?>)arg3);
            dropExperience(arg, arg2, entry.getIntValue(), ((AbstractCookingRecipe)arg3).getExperience());
         });
      }

      return list;
   }

   private static void dropExperience(World arg, Vec3d arg2, int i, float f) {
      int j = MathHelper.floor((float)i * f);
      float g = MathHelper.fractionalPart((float)i * f);
      if (g != 0.0F && Math.random() < (double)g) {
         j++;
      }

      while (j > 0) {
         int k = ExperienceOrbEntity.roundToOrbSize(j);
         j -= k;
         arg.spawnEntity(new ExperienceOrbEntity(arg, arg2.x, arg2.y, arg2.z, k));
      }
   }

   @Override
   public void provideRecipeInputs(RecipeFinder finder) {
      for (ItemStack lv : this.inventory) {
         finder.addItem(lv);
      }
   }
}
