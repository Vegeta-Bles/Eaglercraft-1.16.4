package net.minecraft.block.entity;

import java.util.Optional;
import java.util.Random;
import javax.annotation.Nullable;
import net.minecraft.block.BlockState;
import net.minecraft.block.CampfireBlock;
import net.minecraft.inventory.Inventories;
import net.minecraft.inventory.Inventory;
import net.minecraft.inventory.SimpleInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.packet.s2c.play.BlockEntityUpdateS2CPacket;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.recipe.CampfireCookingRecipe;
import net.minecraft.recipe.RecipeType;
import net.minecraft.util.Clearable;
import net.minecraft.util.ItemScatterer;
import net.minecraft.util.Tickable;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;

public class CampfireBlockEntity extends BlockEntity implements Clearable, Tickable {
   private final DefaultedList<ItemStack> itemsBeingCooked = DefaultedList.ofSize(4, ItemStack.EMPTY);
   private final int[] cookingTimes = new int[4];
   private final int[] cookingTotalTimes = new int[4];

   public CampfireBlockEntity() {
      super(BlockEntityType.CAMPFIRE);
   }

   @Override
   public void tick() {
      boolean _snowman = this.getCachedState().get(CampfireBlock.LIT);
      boolean _snowmanx = this.world.isClient;
      if (_snowmanx) {
         if (_snowman) {
            this.spawnSmokeParticles();
         }
      } else {
         if (_snowman) {
            this.updateItemsBeingCooked();
         } else {
            for (int _snowmanxx = 0; _snowmanxx < this.itemsBeingCooked.size(); _snowmanxx++) {
               if (this.cookingTimes[_snowmanxx] > 0) {
                  this.cookingTimes[_snowmanxx] = MathHelper.clamp(this.cookingTimes[_snowmanxx] - 2, 0, this.cookingTotalTimes[_snowmanxx]);
               }
            }
         }
      }
   }

   private void updateItemsBeingCooked() {
      for (int _snowman = 0; _snowman < this.itemsBeingCooked.size(); _snowman++) {
         ItemStack _snowmanx = this.itemsBeingCooked.get(_snowman);
         if (!_snowmanx.isEmpty()) {
            this.cookingTimes[_snowman]++;
            if (this.cookingTimes[_snowman] >= this.cookingTotalTimes[_snowman]) {
               Inventory _snowmanxx = new SimpleInventory(_snowmanx);
               ItemStack _snowmanxxx = this.world
                  .getRecipeManager()
                  .getFirstMatch(RecipeType.CAMPFIRE_COOKING, _snowmanxx, this.world)
                  .map(_snowmanxxxx -> _snowmanxxxx.craft(_snowman))
                  .orElse(_snowmanx);
               BlockPos _snowmanxxxx = this.getPos();
               ItemScatterer.spawn(this.world, (double)_snowmanxxxx.getX(), (double)_snowmanxxxx.getY(), (double)_snowmanxxxx.getZ(), _snowmanxxx);
               this.itemsBeingCooked.set(_snowman, ItemStack.EMPTY);
               this.updateListeners();
            }
         }
      }
   }

   private void spawnSmokeParticles() {
      World _snowman = this.getWorld();
      if (_snowman != null) {
         BlockPos _snowmanx = this.getPos();
         Random _snowmanxx = _snowman.random;
         if (_snowmanxx.nextFloat() < 0.11F) {
            for (int _snowmanxxx = 0; _snowmanxxx < _snowmanxx.nextInt(2) + 2; _snowmanxxx++) {
               CampfireBlock.spawnSmokeParticle(_snowman, _snowmanx, this.getCachedState().get(CampfireBlock.SIGNAL_FIRE), false);
            }
         }

         int _snowmanxxx = this.getCachedState().get(CampfireBlock.FACING).getHorizontal();

         for (int _snowmanxxxx = 0; _snowmanxxxx < this.itemsBeingCooked.size(); _snowmanxxxx++) {
            if (!this.itemsBeingCooked.get(_snowmanxxxx).isEmpty() && _snowmanxx.nextFloat() < 0.2F) {
               Direction _snowmanxxxxx = Direction.fromHorizontal(Math.floorMod(_snowmanxxxx + _snowmanxxx, 4));
               float _snowmanxxxxxx = 0.3125F;
               double _snowmanxxxxxxx = (double)_snowmanx.getX()
                  + 0.5
                  - (double)((float)_snowmanxxxxx.getOffsetX() * 0.3125F)
                  + (double)((float)_snowmanxxxxx.rotateYClockwise().getOffsetX() * 0.3125F);
               double _snowmanxxxxxxxx = (double)_snowmanx.getY() + 0.5;
               double _snowmanxxxxxxxxx = (double)_snowmanx.getZ()
                  + 0.5
                  - (double)((float)_snowmanxxxxx.getOffsetZ() * 0.3125F)
                  + (double)((float)_snowmanxxxxx.rotateYClockwise().getOffsetZ() * 0.3125F);

               for (int _snowmanxxxxxxxxxx = 0; _snowmanxxxxxxxxxx < 4; _snowmanxxxxxxxxxx++) {
                  _snowman.addParticle(ParticleTypes.SMOKE, _snowmanxxxxxxx, _snowmanxxxxxxxx, _snowmanxxxxxxxxx, 0.0, 5.0E-4, 0.0);
               }
            }
         }
      }
   }

   public DefaultedList<ItemStack> getItemsBeingCooked() {
      return this.itemsBeingCooked;
   }

   @Override
   public void fromTag(BlockState state, CompoundTag tag) {
      super.fromTag(state, tag);
      this.itemsBeingCooked.clear();
      Inventories.fromTag(tag, this.itemsBeingCooked);
      if (tag.contains("CookingTimes", 11)) {
         int[] _snowman = tag.getIntArray("CookingTimes");
         System.arraycopy(_snowman, 0, this.cookingTimes, 0, Math.min(this.cookingTotalTimes.length, _snowman.length));
      }

      if (tag.contains("CookingTotalTimes", 11)) {
         int[] _snowman = tag.getIntArray("CookingTotalTimes");
         System.arraycopy(_snowman, 0, this.cookingTotalTimes, 0, Math.min(this.cookingTotalTimes.length, _snowman.length));
      }
   }

   @Override
   public CompoundTag toTag(CompoundTag tag) {
      this.saveInitialChunkData(tag);
      tag.putIntArray("CookingTimes", this.cookingTimes);
      tag.putIntArray("CookingTotalTimes", this.cookingTotalTimes);
      return tag;
   }

   private CompoundTag saveInitialChunkData(CompoundTag tag) {
      super.toTag(tag);
      Inventories.toTag(tag, this.itemsBeingCooked, true);
      return tag;
   }

   @Nullable
   @Override
   public BlockEntityUpdateS2CPacket toUpdatePacket() {
      return new BlockEntityUpdateS2CPacket(this.pos, 13, this.toInitialChunkDataTag());
   }

   @Override
   public CompoundTag toInitialChunkDataTag() {
      return this.saveInitialChunkData(new CompoundTag());
   }

   public Optional<CampfireCookingRecipe> getRecipeFor(ItemStack item) {
      return this.itemsBeingCooked.stream().noneMatch(ItemStack::isEmpty)
         ? Optional.empty()
         : this.world.getRecipeManager().getFirstMatch(RecipeType.CAMPFIRE_COOKING, new SimpleInventory(item), this.world);
   }

   public boolean addItem(ItemStack item, int integer) {
      for (int _snowman = 0; _snowman < this.itemsBeingCooked.size(); _snowman++) {
         ItemStack _snowmanx = this.itemsBeingCooked.get(_snowman);
         if (_snowmanx.isEmpty()) {
            this.cookingTotalTimes[_snowman] = integer;
            this.cookingTimes[_snowman] = 0;
            this.itemsBeingCooked.set(_snowman, item.split(1));
            this.updateListeners();
            return true;
         }
      }

      return false;
   }

   private void updateListeners() {
      this.markDirty();
      this.getWorld().updateListeners(this.getPos(), this.getCachedState(), this.getCachedState(), 3);
   }

   @Override
   public void clear() {
      this.itemsBeingCooked.clear();
   }

   public void spawnItemsBeingCooked() {
      if (this.world != null) {
         if (!this.world.isClient) {
            ItemScatterer.spawn(this.world, this.getPos(), this.getItemsBeingCooked());
         }

         this.updateListeners();
      }
   }
}
